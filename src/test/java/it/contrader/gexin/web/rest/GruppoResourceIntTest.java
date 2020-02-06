package it.contrader.gexin.web.rest;

import it.contrader.gexin.GexinApp;

import it.contrader.gexin.domain.Gruppo;
import it.contrader.gexin.repository.GruppoRepository;
import it.contrader.gexin.service.GruppoService;
import it.contrader.gexin.service.dto.GruppoDTO;
import it.contrader.gexin.service.mapper.GruppoMapper;
import it.contrader.gexin.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;


import static it.contrader.gexin.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the GruppoResource REST controller.
 *
 * @see GruppoResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = GexinApp.class)
public class GruppoResourceIntTest {

    private static final String DEFAULT_NOME_GRUPPO = "AAAAAAAAAA";
    private static final String UPDATED_NOME_GRUPPO = "BBBBBBBBBB";

    @Autowired
    private GruppoRepository gruppoRepository;
    @Mock
    private GruppoRepository gruppoRepositoryMock;

    @Autowired
    private GruppoMapper gruppoMapper;
    
    @Mock
    private GruppoService gruppoServiceMock;

    @Autowired
    private GruppoService gruppoService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restGruppoMockMvc;

    private Gruppo gruppo;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final GruppoResource gruppoResource = new GruppoResource(gruppoService);
        this.restGruppoMockMvc = MockMvcBuilders.standaloneSetup(gruppoResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Gruppo createEntity(EntityManager em) {
        Gruppo gruppo = new Gruppo()
            .nomeGruppo(DEFAULT_NOME_GRUPPO);
        return gruppo;
    }

    @Before
    public void initTest() {
        gruppo = createEntity(em);
    }

    @Test
    @Transactional
    public void createGruppo() throws Exception {
        int databaseSizeBeforeCreate = gruppoRepository.findAll().size();

        // Create the Gruppo
        GruppoDTO gruppoDTO = gruppoMapper.toDto(gruppo);
        restGruppoMockMvc.perform(post("/api/gruppos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(gruppoDTO)))
            .andExpect(status().isCreated());

        // Validate the Gruppo in the database
        List<Gruppo> gruppoList = gruppoRepository.findAll();
        assertThat(gruppoList).hasSize(databaseSizeBeforeCreate + 1);
        Gruppo testGruppo = gruppoList.get(gruppoList.size() - 1);
        assertThat(testGruppo.getNomeGruppo()).isEqualTo(DEFAULT_NOME_GRUPPO);
    }

    @Test
    @Transactional
    public void createGruppoWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = gruppoRepository.findAll().size();

        // Create the Gruppo with an existing ID
        gruppo.setId(1L);
        GruppoDTO gruppoDTO = gruppoMapper.toDto(gruppo);

        // An entity with an existing ID cannot be created, so this API call must fail
        restGruppoMockMvc.perform(post("/api/gruppos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(gruppoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Gruppo in the database
        List<Gruppo> gruppoList = gruppoRepository.findAll();
        assertThat(gruppoList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllGruppos() throws Exception {
        // Initialize the database
        gruppoRepository.saveAndFlush(gruppo);

        // Get all the gruppoList
        restGruppoMockMvc.perform(get("/api/gruppos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(gruppo.getId().intValue())))
            .andExpect(jsonPath("$.[*].nomeGruppo").value(hasItem(DEFAULT_NOME_GRUPPO.toString())));
    }
    
    public void getAllGrupposWithEagerRelationshipsIsEnabled() throws Exception {
        GruppoResource gruppoResource = new GruppoResource(gruppoServiceMock);
        when(gruppoServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        MockMvc restGruppoMockMvc = MockMvcBuilders.standaloneSetup(gruppoResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();

        restGruppoMockMvc.perform(get("/api/gruppos?eagerload=true"))
        .andExpect(status().isOk());

        verify(gruppoServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    public void getAllGrupposWithEagerRelationshipsIsNotEnabled() throws Exception {
        GruppoResource gruppoResource = new GruppoResource(gruppoServiceMock);
            when(gruppoServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));
            MockMvc restGruppoMockMvc = MockMvcBuilders.standaloneSetup(gruppoResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();

        restGruppoMockMvc.perform(get("/api/gruppos?eagerload=true"))
        .andExpect(status().isOk());

            verify(gruppoServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @Test
    @Transactional
    public void getGruppo() throws Exception {
        // Initialize the database
        gruppoRepository.saveAndFlush(gruppo);

        // Get the gruppo
        restGruppoMockMvc.perform(get("/api/gruppos/{id}", gruppo.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(gruppo.getId().intValue()))
            .andExpect(jsonPath("$.nomeGruppo").value(DEFAULT_NOME_GRUPPO.toString()));
    }
    @Test
    @Transactional
    public void getNonExistingGruppo() throws Exception {
        // Get the gruppo
        restGruppoMockMvc.perform(get("/api/gruppos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateGruppo() throws Exception {
        // Initialize the database
        gruppoRepository.saveAndFlush(gruppo);

        int databaseSizeBeforeUpdate = gruppoRepository.findAll().size();

        // Update the gruppo
        Gruppo updatedGruppo = gruppoRepository.findById(gruppo.getId()).get();
        // Disconnect from session so that the updates on updatedGruppo are not directly saved in db
        em.detach(updatedGruppo);
        updatedGruppo
            .nomeGruppo(UPDATED_NOME_GRUPPO);
        GruppoDTO gruppoDTO = gruppoMapper.toDto(updatedGruppo);

        restGruppoMockMvc.perform(put("/api/gruppos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(gruppoDTO)))
            .andExpect(status().isOk());

        // Validate the Gruppo in the database
        List<Gruppo> gruppoList = gruppoRepository.findAll();
        assertThat(gruppoList).hasSize(databaseSizeBeforeUpdate);
        Gruppo testGruppo = gruppoList.get(gruppoList.size() - 1);
        assertThat(testGruppo.getNomeGruppo()).isEqualTo(UPDATED_NOME_GRUPPO);
    }

    @Test
    @Transactional
    public void updateNonExistingGruppo() throws Exception {
        int databaseSizeBeforeUpdate = gruppoRepository.findAll().size();

        // Create the Gruppo
        GruppoDTO gruppoDTO = gruppoMapper.toDto(gruppo);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException 
        restGruppoMockMvc.perform(put("/api/gruppos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(gruppoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Gruppo in the database
        List<Gruppo> gruppoList = gruppoRepository.findAll();
        assertThat(gruppoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteGruppo() throws Exception {
        // Initialize the database
        gruppoRepository.saveAndFlush(gruppo);

        int databaseSizeBeforeDelete = gruppoRepository.findAll().size();

        // Get the gruppo
        restGruppoMockMvc.perform(delete("/api/gruppos/{id}", gruppo.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Gruppo> gruppoList = gruppoRepository.findAll();
        assertThat(gruppoList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Gruppo.class);
        Gruppo gruppo1 = new Gruppo();
        gruppo1.setId(1L);
        Gruppo gruppo2 = new Gruppo();
        gruppo2.setId(gruppo1.getId());
        assertThat(gruppo1).isEqualTo(gruppo2);
        gruppo2.setId(2L);
        assertThat(gruppo1).isNotEqualTo(gruppo2);
        gruppo1.setId(null);
        assertThat(gruppo1).isNotEqualTo(gruppo2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(GruppoDTO.class);
        GruppoDTO gruppoDTO1 = new GruppoDTO();
        gruppoDTO1.setId(1L);
        GruppoDTO gruppoDTO2 = new GruppoDTO();
        assertThat(gruppoDTO1).isNotEqualTo(gruppoDTO2);
        gruppoDTO2.setId(gruppoDTO1.getId());
        assertThat(gruppoDTO1).isEqualTo(gruppoDTO2);
        gruppoDTO2.setId(2L);
        assertThat(gruppoDTO1).isNotEqualTo(gruppoDTO2);
        gruppoDTO1.setId(null);
        assertThat(gruppoDTO1).isNotEqualTo(gruppoDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(gruppoMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(gruppoMapper.fromId(null)).isNull();
    }
}
