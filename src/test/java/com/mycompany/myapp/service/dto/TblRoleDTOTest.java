package com.mycompany.myapp.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class TblRoleDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(TblRoleDTO.class);
        TblRoleDTO tblRoleDTO1 = new TblRoleDTO();
        tblRoleDTO1.setId(1L);
        TblRoleDTO tblRoleDTO2 = new TblRoleDTO();
        assertThat(tblRoleDTO1).isNotEqualTo(tblRoleDTO2);
        tblRoleDTO2.setId(tblRoleDTO1.getId());
        assertThat(tblRoleDTO1).isEqualTo(tblRoleDTO2);
        tblRoleDTO2.setId(2L);
        assertThat(tblRoleDTO1).isNotEqualTo(tblRoleDTO2);
        tblRoleDTO1.setId(null);
        assertThat(tblRoleDTO1).isNotEqualTo(tblRoleDTO2);
    }
}
