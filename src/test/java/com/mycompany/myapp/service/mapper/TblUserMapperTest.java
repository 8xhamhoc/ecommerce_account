package com.mycompany.myapp.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TblUserMapperTest {

    private TblUserMapper tblUserMapper;

    @BeforeEach
    public void setUp() {
        tblUserMapper = new TblUserMapperImpl();
    }
}
