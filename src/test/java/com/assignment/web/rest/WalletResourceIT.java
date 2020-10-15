package com.assignment.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.math.BigDecimal;
import java.util.List;

import javax.persistence.EntityManager;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import com.assignment.NovopayassignmentApp;
import com.assignment.domain.Wallet;
import com.assignment.repository.WalletRepository;
import com.assignment.service.WalletService;
import com.assignment.service.dto.WalletDTO;
import com.assignment.service.mapper.WalletMapper;

/**
 * Integration tests for the {@link WalletResource} REST controller.
 */
@SpringBootTest(classes = NovopayassignmentApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class WalletResourceIT {

    private static final BigDecimal DEFAULT_BALANCE = new BigDecimal(1);
    private static final BigDecimal UPDATED_BALANCE = new BigDecimal(2);

    private static final Long DEFAULT_OWENER_ID = 1L;
    private static final Long UPDATED_OWENER_ID = 2L;

    @Autowired
    private WalletRepository walletRepository;

    @Autowired
    private WalletMapper walletMapper;

    @Autowired
    private WalletService walletService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restWalletMockMvc;

    private Wallet wallet;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Wallet createEntity(EntityManager em) {
        Wallet wallet = new Wallet()
            .balance(DEFAULT_BALANCE)
            .owenerId(DEFAULT_OWENER_ID);
        return wallet;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Wallet createUpdatedEntity(EntityManager em) {
        Wallet wallet = new Wallet()
            .balance(UPDATED_BALANCE)
            .owenerId(UPDATED_OWENER_ID);
        return wallet;
    }

    @BeforeEach
    public void initTest() {
        wallet = createEntity(em);
    }

    @Test
    @Transactional
    public void createWallet() throws Exception {
        int databaseSizeBeforeCreate = walletRepository.findAll().size();
        // Create the Wallet
        WalletDTO walletDTO = walletMapper.toDto(wallet);
        restWalletMockMvc.perform(post("/api/wallets").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(walletDTO)))
            .andExpect(status().isCreated());

        // Validate the Wallet in the database
        List<Wallet> walletList = walletRepository.findAll();
        assertThat(walletList).hasSize(databaseSizeBeforeCreate + 1);
        Wallet testWallet = walletList.get(walletList.size() - 1);
        assertThat(testWallet.getBalance()).isEqualTo(DEFAULT_BALANCE);
        assertThat(testWallet.getOwenerId()).isEqualTo(DEFAULT_OWENER_ID);
    }

    @Test
    @Transactional
    public void createWalletWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = walletRepository.findAll().size();

        // Create the Wallet with an existing ID
        wallet.setId(1L);
        WalletDTO walletDTO = walletMapper.toDto(wallet);

        // An entity with an existing ID cannot be created, so this API call must fail
        restWalletMockMvc.perform(post("/api/wallets").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(walletDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Wallet in the database
        List<Wallet> walletList = walletRepository.findAll();
        assertThat(walletList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllWallets() throws Exception {
        // Initialize the database
        walletRepository.saveAndFlush(wallet);

        // Get all the walletList
        restWalletMockMvc.perform(get("/api/wallets?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(wallet.getId().intValue())))
            .andExpect(jsonPath("$.[*].balance").value(hasItem(DEFAULT_BALANCE.intValue())))
            .andExpect(jsonPath("$.[*].owenerId").value(hasItem(DEFAULT_OWENER_ID.intValue())));
    }
    
    @Test
    @Transactional
    public void getWallet() throws Exception {
        // Initialize the database
        walletRepository.saveAndFlush(wallet);

        // Get the wallet
        restWalletMockMvc.perform(get("/api/wallets/{id}", wallet.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(wallet.getId().intValue()))
            .andExpect(jsonPath("$.balance").value(DEFAULT_BALANCE.intValue()))
            .andExpect(jsonPath("$.owenerId").value(DEFAULT_OWENER_ID.intValue()));
    }
    @Test
    @Transactional
    public void getNonExistingWallet() throws Exception {
        // Get the wallet
        restWalletMockMvc.perform(get("/api/wallets/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateWallet() throws Exception {
        // Initialize the database
        walletRepository.saveAndFlush(wallet);

        int databaseSizeBeforeUpdate = walletRepository.findAll().size();

        // Update the wallet
        Wallet updatedWallet = walletRepository.findById(wallet.getId()).get();
        // Disconnect from session so that the updates on updatedWallet are not directly saved in db
        em.detach(updatedWallet);
        updatedWallet
            .balance(UPDATED_BALANCE)
            .owenerId(UPDATED_OWENER_ID);
        WalletDTO walletDTO = walletMapper.toDto(updatedWallet);

        restWalletMockMvc.perform(put("/api/wallets").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(walletDTO)))
            .andExpect(status().isOk());

        // Validate the Wallet in the database
        List<Wallet> walletList = walletRepository.findAll();
        assertThat(walletList).hasSize(databaseSizeBeforeUpdate);
        Wallet testWallet = walletList.get(walletList.size() - 1);
        assertThat(testWallet.getBalance()).isEqualTo(UPDATED_BALANCE);
        assertThat(testWallet.getOwenerId()).isEqualTo(UPDATED_OWENER_ID);
    }

    @Test
    @Transactional
    public void updateNonExistingWallet() throws Exception {
        int databaseSizeBeforeUpdate = walletRepository.findAll().size();

        // Create the Wallet
        WalletDTO walletDTO = walletMapper.toDto(wallet);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restWalletMockMvc.perform(put("/api/wallets").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(walletDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Wallet in the database
        List<Wallet> walletList = walletRepository.findAll();
        assertThat(walletList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteWallet() throws Exception {
        // Initialize the database
        walletRepository.saveAndFlush(wallet);

        int databaseSizeBeforeDelete = walletRepository.findAll().size();

        // Delete the wallet
        restWalletMockMvc.perform(delete("/api/wallets/{id}", wallet.getId()).with(csrf())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Wallet> walletList = walletRepository.findAll();
        assertThat(walletList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
