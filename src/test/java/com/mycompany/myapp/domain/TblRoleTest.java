package com.mycompany.myapp.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class TblRoleTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TblRole.class);
        TblRole tblRole1 = new TblRole();
        tblRole1.setId(1L);
        TblRole tblRole2 = new TblRole();
        tblRole2.setId(tblRole1.getId());
        assertThat(tblRole1).isEqualTo(tblRole2);
        tblRole2.setId(2L);
        assertThat(tblRole1).isNotEqualTo(tblRole2);
        tblRole1.setId(null);
        assertThat(tblRole1).isNotEqualTo(tblRole2);
    }
}
