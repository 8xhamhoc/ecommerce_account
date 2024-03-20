package com.mycompany.myapp.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.mycompany.myapp.IntegrationTest;
import com.mycompany.myapp.domain.TblRole;
import com.mycompany.myapp.repository.TblRoleRepository;
import com.mycompany.myapp.service.dto.TblRoleDTO;
import com.mycompany.myapp.service.mapper.TblRoleMapper;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link TblRoleResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class TblRoleResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/tbl-roles";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private TblRoleRepository tblRoleRepository;

    @Autowired
    private TblRoleMapper tblRoleMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restTblRoleMockMvc;

    private TblRole tblRole;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TblRole createEntity(EntityManager em) {
        TblRole tblRole = new TblRole().name(DEFAULT_NAME);
        return tblRole;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TblRole createUpdatedEntity(EntityManager em) {
        TblRole tblRole = new TblRole().name(UPDATED_NAME);
        return tblRole;
    }

    @BeforeEach
    public void initTest() {
        tblRole = createEntity(em);
    }

    @Test
    @Transactional
    void createTblRole() throws Exception {
        int databaseSizeBeforeCreate = tblRoleRepository.findAll().size();
        // Create the TblRole
        TblRoleDTO tblRoleDTO = tblRoleMapper.toDto(tblRole);
        restTblRoleMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(tblRoleDTO)))
            .andExpect(status().isCreated());

        // Validate the TblRole in the database
        List<TblRole> tblRoleList = tblRoleRepository.findAll();
        assertThat(tblRoleList).hasSize(databaseSizeBeforeCreate + 1);
        TblRole testTblRole = tblRoleList.get(tblRoleList.size() - 1);
        assertThat(testTblRole.getName()).isEqualTo(DEFAULT_NAME);
    }

    @Test
    @Transactional
    void createTblRoleWithExistingId() throws Exception {
        // Create the TblRole with an existing ID
        tblRole.setId(1L);
        TblRoleDTO tblRoleDTO = tblRoleMapper.toDto(tblRole);

        int databaseSizeBeforeCreate = tblRoleRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restTblRoleMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(tblRoleDTO)))
            .andExpect(status().isBadRequest());

        // Validate the TblRole in the database
        List<TblRole> tblRoleList = tblRoleRepository.findAll();
        assertThat(tblRoleList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = tblRoleRepository.findAll().size();
        // set the field null
        tblRole.setName(null);

        // Create the TblRole, which fails.
        TblRoleDTO tblRoleDTO = tblRoleMapper.toDto(tblRole);

        restTblRoleMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(tblRoleDTO)))
            .andExpect(status().isBadRequest());

        List<TblRole> tblRoleList = tblRoleRepository.findAll();
        assertThat(tblRoleList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllTblRoles() throws Exception {
        // Initialize the database
        tblRoleRepository.saveAndFlush(tblRole);

        // Get all the tblRoleList
        restTblRoleMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(tblRole.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)));
    }

    @Test
    @Transactional
    void getTblRole() throws Exception {
        // Initialize the database
        tblRoleRepository.saveAndFlush(tblRole);

        // Get the tblRole
        restTblRoleMockMvc
            .perform(get(ENTITY_API_URL_ID, tblRole.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(tblRole.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME));
    }

    @Test
    @Transactional
    void getNonExistingTblRole() throws Exception {
        // Get the tblRole
        restTblRoleMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingTblRole() throws Exception {
        // Initialize the database
        tblRoleRepository.saveAndFlush(tblRole);

        int databaseSizeBeforeUpdate = tblRoleRepository.findAll().size();

        // Update the tblRole
        TblRole updatedTblRole = tblRoleRepository.findById(tblRole.getId()).get();
        // Disconnect from session so that the updates on updatedTblRole are not directly saved in db
        em.detach(updatedTblRole);
        updatedTblRole.name(UPDATED_NAME);
        TblRoleDTO tblRoleDTO = tblRoleMapper.toDto(updatedTblRole);

        restTblRoleMockMvc
            .perform(
                put(ENTITY_API_URL_ID, tblRoleDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(tblRoleDTO))
            )
            .andExpect(status().isOk());

        // Validate the TblRole in the database
        List<TblRole> tblRoleList = tblRoleRepository.findAll();
        assertThat(tblRoleList).hasSize(databaseSizeBeforeUpdate);
        TblRole testTblRole = tblRoleList.get(tblRoleList.size() - 1);
        assertThat(testTblRole.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    void putNonExistingTblRole() throws Exception {
        int databaseSizeBeforeUpdate = tblRoleRepository.findAll().size();
        tblRole.setId(count.incrementAndGet());

        // Create the TblRole
        TblRoleDTO tblRoleDTO = tblRoleMapper.toDto(tblRole);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTblRoleMockMvc
            .perform(
                put(ENTITY_API_URL_ID, tblRoleDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(tblRoleDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the TblRole in the database
        List<TblRole> tblRoleList = tblRoleRepository.findAll();
        assertThat(tblRoleList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchTblRole() throws Exception {
        int databaseSizeBeforeUpdate = tblRoleRepository.findAll().size();
        tblRole.setId(count.incrementAndGet());

        // Create the TblRole
        TblRoleDTO tblRoleDTO = tblRoleMapper.toDto(tblRole);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTblRoleMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(tblRoleDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the TblRole in the database
        List<TblRole> tblRoleList = tblRoleRepository.findAll();
        assertThat(tblRoleList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamTblRole() throws Exception {
        int databaseSizeBeforeUpdate = tblRoleRepository.findAll().size();
        tblRole.setId(count.incrementAndGet());

        // Create the TblRole
        TblRoleDTO tblRoleDTO = tblRoleMapper.toDto(tblRole);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTblRoleMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(tblRoleDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the TblRole in the database
        List<TblRole> tblRoleList = tblRoleRepository.findAll();
        assertThat(tblRoleList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateTblRoleWithPatch() throws Exception {
        // Initialize the database
        tblRoleRepository.saveAndFlush(tblRole);

        int databaseSizeBeforeUpdate = tblRoleRepository.findAll().size();

        // Update the tblRole using partial update
        TblRole partialUpdatedTblRole = new TblRole();
        partialUpdatedTblRole.setId(tblRole.getId());

        restTblRoleMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTblRole.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedTblRole))
            )
            .andExpect(status().isOk());

        // Validate the TblRole in the database
        List<TblRole> tblRoleList = tblRoleRepository.findAll();
        assertThat(tblRoleList).hasSize(databaseSizeBeforeUpdate);
        TblRole testTblRole = tblRoleList.get(tblRoleList.size() - 1);
        assertThat(testTblRole.getName()).isEqualTo(DEFAULT_NAME);
    }

    @Test
    @Transactional
    void fullUpdateTblRoleWithPatch() throws Exception {
        // Initialize the database
        tblRoleRepository.saveAndFlush(tblRole);

        int databaseSizeBeforeUpdate = tblRoleRepository.findAll().size();

        // Update the tblRole using partial update
        TblRole partialUpdatedTblRole = new TblRole();
        partialUpdatedTblRole.setId(tblRole.getId());

        partialUpdatedTblRole.name(UPDATED_NAME);

        restTblRoleMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTblRole.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedTblRole))
            )
            .andExpect(status().isOk());

        // Validate the TblRole in the database
        List<TblRole> tblRoleList = tblRoleRepository.findAll();
        assertThat(tblRoleList).hasSize(databaseSizeBeforeUpdate);
        TblRole testTblRole = tblRoleList.get(tblRoleList.size() - 1);
        assertThat(testTblRole.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    void patchNonExistingTblRole() throws Exception {
        int databaseSizeBeforeUpdate = tblRoleRepository.findAll().size();
        tblRole.setId(count.incrementAndGet());

        // Create the TblRole
        TblRoleDTO tblRoleDTO = tblRoleMapper.toDto(tblRole);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTblRoleMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, tblRoleDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(tblRoleDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the TblRole in the database
        List<TblRole> tblRoleList = tblRoleRepository.findAll();
        assertThat(tblRoleList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchTblRole() throws Exception {
        int databaseSizeBeforeUpdate = tblRoleRepository.findAll().size();
        tblRole.setId(count.incrementAndGet());

        // Create the TblRole
        TblRoleDTO tblRoleDTO = tblRoleMapper.toDto(tblRole);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTblRoleMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(tblRoleDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the TblRole in the database
        List<TblRole> tblRoleList = tblRoleRepository.findAll();
        assertThat(tblRoleList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamTblRole() throws Exception {
        int databaseSizeBeforeUpdate = tblRoleRepository.findAll().size();
        tblRole.setId(count.incrementAndGet());

        // Create the TblRole
        TblRoleDTO tblRoleDTO = tblRoleMapper.toDto(tblRole);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTblRoleMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(tblRoleDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the TblRole in the database
        List<TblRole> tblRoleList = tblRoleRepository.findAll();
        assertThat(tblRoleList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteTblRole() throws Exception {
        // Initialize the database
        tblRoleRepository.saveAndFlush(tblRole);

        int databaseSizeBeforeDelete = tblRoleRepository.findAll().size();

        // Delete the tblRole
        restTblRoleMockMvc
            .perform(delete(ENTITY_API_URL_ID, tblRole.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<TblRole> tblRoleList = tblRoleRepository.findAll();
        assertThat(tblRoleList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
