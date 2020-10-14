package com.assignment.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.assignment.web.rest.TestUtil;

public class PassBookTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PassBook.class);
        PassBook passBook1 = new PassBook();
        passBook1.setId(1L);
        PassBook passBook2 = new PassBook();
        passBook2.setId(passBook1.getId());
        assertThat(passBook1).isEqualTo(passBook2);
        passBook2.setId(2L);
        assertThat(passBook1).isNotEqualTo(passBook2);
        passBook1.setId(null);
        assertThat(passBook1).isNotEqualTo(passBook2);
    }
}
