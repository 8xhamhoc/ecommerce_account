package com.mycompany.myapp.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.mycompany.myapp.IntegrationTest;
import com.mycompany.myapp.domain.TblUser;
import com.mycompany.myapp.repository.TblUserRepository;
import com.mycompany.myapp.service.TblUserService;
import com.mycompany.myapp.service.dto.TblUserDTO;
import com.mycompany.myapp.service.mapper.TblUserMapper;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link TblUserResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class TblUserResourceIT {

    private static final String DEFAULT_USERNAME = "AAAAAAAAAA";
    private static final String UPDATED_USERNAME = "BBBBBBBBBB";

    private static final String DEFAULT_EMAIL = "AAAAAAAAAA";
    private static final String UPDATED_EMAIL = "BBBBBBBBBB";

    private static final String DEFAULT_FIRST_NAME = "AAAAAAAAAA";
    private static final String UPDATED_FIRST_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_LAST_NAME = "AAAAAAAAAA";
    private static final String UPDATED_LAST_NAME = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/tbl-users";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private TblUserRepository tblUserRepository;

    @Mock
    private TblUserRepository tblUserRepositoryMock;

    @Autowired
    private TblUserMapper tblUserMapper;

    @Mock
    private TblUserService tblUserServiceMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restTblUserMockMvc;

    private TblUser tblUser;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TblUser createEntity(EntityManager em) {
        TblUser tblUser = new TblUser()
            .username(DEFAULT_USERNAME)
            .email(DEFAULT_EMAIL)
            .firstName(DEFAULT_FIRST_NAME)
            .lastName(DEFAULT_LAST_NAME);
        return tblUser;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TblUser createUpdatedEntity(EntityManager em) {
        TblUser tblUser = new TblUser()
            .username(UPDATED_USERNAME)
            .email(UPDATED_EMAIL)
            .firstName(UPDATED_FIRST_NAME)
            .lastName(UPDATED_LAST_NAME);
        return tblUser;
    }

    @BeforeEach
    public void initTest() {
        tblUser = createEntity(em);
    }

    @Test
    @Transactional
    void createTblUser() throws Exception {
        int databaseSizeBeforeCreate = tblUserRepository.findAll().size();
        // Create the TblUser
        TblUserDTO tblUserDTO = tblUserMapper.toDto(tblUser);
        restTblUserMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(tblUserDTO)))
            .andExpect(status().isCreated());

        // Validate the TblUser in the database
        List<TblUser> tblUserList = tblUserRepository.findAll();
        assertThat(tblUserList).hasSize(databaseSizeBeforeCreate + 1);
        TblUser testTblUser = tblUserList.get(tblUserList.size() - 1);
        assertThat(testTblUser.getUsername()).isEqualTo(DEFAULT_USERNAME);
        assertThat(testTblUser.getEmail()).isEqualTo(DEFAULT_EMAIL);
        assertThat(testTblUser.getFirstName()).isEqualTo(DEFAULT_FIRST_NAME);
        assertThat(testTblUser.getLastName()).isEqualTo(DEFAULT_LAST_NAME);
    }

    @Test
    @Transactional
    void createTblUserWithExistingId() throws Exception {
        // Create the TblUser with an existing ID
        tblUser.setId(1L);
        TblUserDTO tblUserDTO = tblUserMapper.toDto(tblUser);

        int databaseSizeBeforeCreate = tblUserRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restTblUserMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(tblUserDTO)))
            .andExpect(status().isBadRequest());

        // Validate the TblUser in the database
        List<TblUser> tblUserList = tblUserRepository.findAll();
        assertThat(tblUserList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkUsernameIsRequired() throws Exception {
        int databaseSizeBeforeTest = tblUserRepository.findAll().size();
        // set the field null
        tblUser.setUsername(null);

        // Create the TblUser, which fails.
        TblUserDTO tblUserDTO = tblUserMapper.toDto(tblUser);

        restTblUserMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(tblUserDTO)))
            .andExpect(status().isBadRequest());

        List<TblUser> tblUserList = tblUserRepository.findAll();
        assertThat(tblUserList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkEmailIsRequired() throws Exception {
        int databaseSizeBeforeTest = tblUserRepository.findAll().size();
        // set the field null
        tblUser.setEmail(null);

        // Create the TblUser, which fails.
        TblUserDTO tblUserDTO = tblUserMapper.toDto(tblUser);

        restTblUserMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(tblUserDTO)))
            .andExpect(status().isBadRequest());

        List<TblUser> tblUserList = tblUserRepository.findAll();
        assertThat(tblUserList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllTblUsers() throws Exception {
        // Initialize the database
        tblUserRepository.saveAndFlush(tblUser);

        // Get all the tblUserList
        restTblUserMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(tblUser.getId().intValue())))
            .andExpect(jsonPath("$.[*].username").value(hasItem(DEFAULT_USERNAME)))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL)))
            .andExpect(jsonPath("$.[*].firstName").value(hasItem(DEFAULT_FIRST_NAME)))
            .andExpect(jsonPath("$.[*].lastName").value(hasItem(DEFAULT_LAST_NAME)));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllTblUsersWithEagerRelationshipsIsEnabled() throws Exception {
        when(tblUserServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restTblUserMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(tblUserServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllTblUsersWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(tblUserServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restTblUserMockMvc.perform(get(ENTITY_API_URL + "?eagerload=false")).andExpect(status().isOk());
        verify(tblUserRepositoryMock, times(1)).findAll(any(Pageable.class));
    }

    @Test
    @Transactional
    void getTblUser() throws Exception {
        // Initialize the database
        tblUserRepository.saveAndFlush(tblUser);

        // Get the tblUser
        restTblUserMockMvc
            .perform(get(ENTITY_API_URL_ID, tblUser.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(tblUser.getId().intValue()))
            .andExpect(jsonPath("$.username").value(DEFAULT_USERNAME))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL))
            .andExpect(jsonPath("$.firstName").value(DEFAULT_FIRST_NAME))
            .andExpect(jsonPath("$.lastName").value(DEFAULT_LAST_NAME));
    }

    @Test
    @Transactional
    void getNonExistingTblUser() throws Exception {
        // Get the tblUser
        restTblUserMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingTblUser() throws Exception {
        // Initialize the database
        tblUserRepository.saveAndFlush(tblUser);

        int databaseSizeBeforeUpdate = tblUserRepository.findAll().size();

        // Update the tblUser
        TblUser updatedTblUser = tblUserRepository.findById(tblUser.getId()).get();
        // Disconnect from session so that the updates on updatedTblUser are not directly saved in db
        em.detach(updatedTblUser);
        updatedTblUser.username(UPDATED_USERNAME).email(UPDATED_EMAIL).firstName(UPDATED_FIRST_NAME).lastName(UPDATED_LAST_NAME);
        TblUserDTO tblUserDTO = tblUserMapper.toDto(updatedTblUser);

        restTblUserMockMvc
            .perform(
                put(ENTITY_API_URL_ID, tblUserDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(tblUserDTO))
            )
            .andExpect(status().isOk());

        // Validate the TblUser in the database
        List<TblUser> tblUserList = tblUserRepository.findAll();
        assertThat(tblUserList).hasSize(databaseSizeBeforeUpdate);
        TblUser testTblUser = tblUserList.get(tblUserList.size() - 1);
        assertThat(testTblUser.getUsername()).isEqualTo(UPDATED_USERNAME);
        assertThat(testTblUser.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testTblUser.getFirstName()).isEqualTo(UPDATED_FIRST_NAME);
        assertThat(testTblUser.getLastName()).isEqualTo(UPDATED_LAST_NAME);
    }

    @Test
    @Transactional
    void putNonExistingTblUser() throws Exception {
        int databaseSizeBeforeUpdate = tblUserRepository.findAll().size();
        tblUser.setId(count.incrementAndGet());

        // Create the TblUser
        TblUserDTO tblUserDTO = tblUserMapper.toDto(tblUser);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTblUserMockMvc
            .perform(
                put(ENTITY_API_URL_ID, tblUserDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(tblUserDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the TblUser in the database
        List<TblUser> tblUserList = tblUserRepository.findAll();
        assertThat(tblUserList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchTblUser() throws Exception {
        int databaseSizeBeforeUpdate = tblUserRepository.findAll().size();
        tblUser.setId(count.incrementAndGet());

        // Create the TblUser
        TblUserDTO tblUserDTO = tblUserMapper.toDto(tblUser);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTblUserMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(tblUserDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the TblUser in the database
        List<TblUser> tblUserList = tblUserRepository.findAll();
        assertThat(tblUserList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamTblUser() throws Exception {
        int databaseSizeBeforeUpdate = tblUserRepository.findAll().size();
        tblUser.setId(count.incrementAndGet());

        // Create the TblUser
        TblUserDTO tblUserDTO = tblUserMapper.toDto(tblUser);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTblUserMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(tblUserDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the TblUser in the database
        List<TblUser> tblUserList = tblUserRepository.findAll();
        assertThat(tblUserList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateTblUserWithPatch() throws Exception {
        // Initialize the database
        tblUserRepository.saveAndFlush(tblUser);

        int databaseSizeBeforeUpdate = tblUserRepository.findAll().size();

        // Update the tblUser using partial update
        TblUser partialUpdatedTblUser = new TblUser();
        partialUpdatedTblUser.setId(tblUser.getId());

        partialUpdatedTblUser.email(UPDATED_EMAIL).lastName(UPDATED_LAST_NAME);

        restTblUserMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTblUser.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedTblUser))
            )
            .andExpect(status().isOk());

        // Validate the TblUser in the database
        List<TblUser> tblUserList = tblUserRepository.findAll();
        assertThat(tblUserList).hasSize(databaseSizeBeforeUpdate);
        TblUser testTblUser = tblUserList.get(tblUserList.size() - 1);
        assertThat(testTblUser.getUsername()).isEqualTo(DEFAULT_USERNAME);
        assertThat(testTblUser.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testTblUser.getFirstName()).isEqualTo(DEFAULT_FIRST_NAME);
        assertThat(testTblUser.getLastName()).isEqualTo(UPDATED_LAST_NAME);
    }

    @Test
    @Transactional
    void fullUpdateTblUserWithPatch() throws Exception {
        // Initialize the database
        tblUserRepository.saveAndFlush(tblUser);

        int databaseSizeBeforeUpdate = tblUserRepository.findAll().size();

        // Update the tblUser using partial update
        TblUser partialUpdatedTblUser = new TblUser();
        partialUpdatedTblUser.setId(tblUser.getId());

        partialUpdatedTblUser.username(UPDATED_USERNAME).email(UPDATED_EMAIL).firstName(UPDATED_FIRST_NAME).lastName(UPDATED_LAST_NAME);

        restTblUserMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTblUser.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedTblUser))
            )
            .andExpect(status().isOk());

        // Validate the TblUser in the database
        List<TblUser> tblUserList = tblUserRepository.findAll();
        assertThat(tblUserList).hasSize(databaseSizeBeforeUpdate);
        TblUser testTblUser = tblUserList.get(tblUserList.size() - 1);
        assertThat(testTblUser.getUsername()).isEqualTo(UPDATED_USERNAME);
        assertThat(testTblUser.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testTblUser.getFirstName()).isEqualTo(UPDATED_FIRST_NAME);
        assertThat(testTblUser.getLastName()).isEqualTo(UPDATED_LAST_NAME);
    }

    @Test
    @Transactional
    void patchNonExistingTblUser() throws Exception {
        int databaseSizeBeforeUpdate = tblUserRepository.findAll().size();
        tblUser.setId(count.incrementAndGet());

        // Create the TblUser
        TblUserDTO tblUserDTO = tblUserMapper.toDto(tblUser);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTblUserMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, tblUserDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(tblUserDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the TblUser in the database
        List<TblUser> tblUserList = tblUserRepository.findAll();
        assertThat(tblUserList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchTblUser() throws Exception {
        int databaseSizeBeforeUpdate = tblUserRepository.findAll().size();
        tblUser.setId(count.incrementAndGet());

        // Create the TblUser
        TblUserDTO tblUserDTO = tblUserMapper.toDto(tblUser);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTblUserMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(tblUserDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the TblUser in the database
        List<TblUser> tblUserList = tblUserRepository.findAll();
        assertThat(tblUserList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamTblUser() throws Exception {
        int databaseSizeBeforeUpdate = tblUserRepository.findAll().size();
        tblUser.setId(count.incrementAndGet());

        // Create the TblUser
        TblUserDTO tblUserDTO = tblUserMapper.toDto(tblUser);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTblUserMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(tblUserDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the TblUser in the database
        List<TblUser> tblUserList = tblUserRepository.findAll();
        assertThat(tblUserList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteTblUser() throws Exception {
        // Initialize the database
        tblUserRepository.saveAndFlush(tblUser);

        int databaseSizeBeforeDelete = tblUserRepository.findAll().size();

        // Delete the tblUser
        restTblUserMockMvc
            .perform(delete(ENTITY_API_URL_ID, tblUser.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<TblUser> tblUserList = tblUserRepository.findAll();
        assertThat(tblUserList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
