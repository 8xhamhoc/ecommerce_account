package com.mycompany.myapp.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class TblUserTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TblUser.class);
        TblUser tblUser1 = new TblUser();
        tblUser1.setId(1L);
        TblUser tblUser2 = new TblUser();
        tblUser2.setId(tblUser1.getId());
        assertThat(tblUser1).isEqualTo(tblUser2);
        tblUser2.setId(2L);
        assertThat(tblUser1).isNotEqualTo(tblUser2);
        tblUser1.setId(null);
        assertThat(tblUser1).isNotEqualTo(tblUser2);
    }
}
