package com.assignment.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

import com.assignment.web.rest.TestUtil;

public class PassBookDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(PassBookDTO.class);
        PassBookDTO passBookDTO1 = new PassBookDTO();
        passBookDTO1.setId(1L);
        PassBookDTO passBookDTO2 = new PassBookDTO();
        assertThat(passBookDTO1).isNotEqualTo(passBookDTO2);
        passBookDTO2.setId(passBookDTO1.getId());
        assertThat(passBookDTO1).isEqualTo(passBookDTO2);
        passBookDTO2.setId(2L);
        assertThat(passBookDTO1).isNotEqualTo(passBookDTO2);
        passBookDTO1.setId(null);
        assertThat(passBookDTO1).isNotEqualTo(passBookDTO2);
    }
}
