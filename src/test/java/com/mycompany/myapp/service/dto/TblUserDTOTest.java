package com.mycompany.myapp.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class TblUserDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(TblUserDTO.class);
        TblUserDTO tblUserDTO1 = new TblUserDTO();
        tblUserDTO1.setId(1L);
        TblUserDTO tblUserDTO2 = new TblUserDTO();
        assertThat(tblUserDTO1).isNotEqualTo(tblUserDTO2);
        tblUserDTO2.setId(tblUserDTO1.getId());
        assertThat(tblUserDTO1).isEqualTo(tblUserDTO2);
        tblUserDTO2.setId(2L);
        assertThat(tblUserDTO1).isNotEqualTo(tblUserDTO2);
        tblUserDTO1.setId(null);
        assertThat(tblUserDTO1).isNotEqualTo(tblUserDTO2);
    }
}
