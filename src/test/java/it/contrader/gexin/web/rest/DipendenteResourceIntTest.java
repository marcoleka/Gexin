package it.contrader.gexin.web.rest;

import it.contrader.gexin.GexinApp;

import it.contrader.gexin.domain.Dipendente;
import it.contrader.gexin.repository.DipendenteRepository;
import it.contrader.gexin.service.DipendenteService;
import it.contrader.gexin.service.dto.DipendenteDTO;
import it.contrader.gexin.service.mapper.DipendenteMapper;
import it.contrader.gexin.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;


import static it.contrader.gexin.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the DipendenteResource REST controller.
 *
 * @see DipendenteResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = GexinApp.class)
public class DipendenteResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_SURNAME = "AAAAAAAAAA";
    private static final String UPDATED_SURNAME = "BBBBBBBBBB";

    private static final String DEFAULT_RUOLO = "AAAAAAAAAA";
    private static final String UPDATED_RUOLO = "BBBBBBBBBB";

    private static final String DEFAULT_CODICE_FISCALE = "AAAAAAAAAA";
    private static final String UPDATED_CODICE_FISCALE = "BBBBBBBBBB";

    private static final String DEFAULT_REPARTO = "AAAAAAAAAA";
    private static final String UPDATED_REPARTO = "BBBBBBBBBB";

    private static final String DEFAULT_NUMERO_SCHEDA = "AAAAAAAAAA";
    private static final String UPDATED_NUMERO_SCHEDA = "BBBBBBBBBB";

    private static final Boolean DEFAULT_SCHEDA_PERSA = false;
    private static final Boolean UPDATED_SCHEDA_PERSA = true;

    @Autowired
    private DipendenteRepository dipendenteRepository;


    @Autowired
    private DipendenteMapper dipendenteMapper;
    

    @Autowired
    private DipendenteService dipendenteService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restDipendenteMockMvc;

    private Dipendente dipendente;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final DipendenteResource dipendenteResource = new DipendenteResource(dipendenteService);
        this.restDipendenteMockMvc = MockMvcBuilders.standaloneSetup(dipendenteResource)
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
    public static Dipendente createEntity(EntityManager em) {
        Dipendente dipendente = new Dipendente()
            .name(DEFAULT_NAME)
            .surname(DEFAULT_SURNAME)
            .ruolo(DEFAULT_RUOLO)
            .codiceFiscale(DEFAULT_CODICE_FISCALE)
            .reparto(DEFAULT_REPARTO)
            .numeroScheda(DEFAULT_NUMERO_SCHEDA)
            .schedaPersa(DEFAULT_SCHEDA_PERSA);
        return dipendente;
    }

    @Before
    public void initTest() {
        dipendente = createEntity(em);
    }

    @Test
    @Transactional
    public void createDipendente() throws Exception {
        int databaseSizeBeforeCreate = dipendenteRepository.findAll().size();

        // Create the Dipendente
        DipendenteDTO dipendenteDTO = dipendenteMapper.toDto(dipendente);
        restDipendenteMockMvc.perform(post("/api/dipendentes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(dipendenteDTO)))
            .andExpect(status().isCreated());

        // Validate the Dipendente in the database
        List<Dipendente> dipendenteList = dipendenteRepository.findAll();
        assertThat(dipendenteList).hasSize(databaseSizeBeforeCreate + 1);
        Dipendente testDipendente = dipendenteList.get(dipendenteList.size() - 1);
        assertThat(testDipendente.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testDipendente.getSurname()).isEqualTo(DEFAULT_SURNAME);
        assertThat(testDipendente.getRuolo()).isEqualTo(DEFAULT_RUOLO);
        assertThat(testDipendente.getCodiceFiscale()).isEqualTo(DEFAULT_CODICE_FISCALE);
        assertThat(testDipendente.getReparto()).isEqualTo(DEFAULT_REPARTO);
        assertThat(testDipendente.getNumeroScheda()).isEqualTo(DEFAULT_NUMERO_SCHEDA);
        assertThat(testDipendente.isSchedaPersa()).isEqualTo(DEFAULT_SCHEDA_PERSA);
    }

    @Test
    @Transactional
    public void createDipendenteWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = dipendenteRepository.findAll().size();

        // Create the Dipendente with an existing ID
        dipendente.setId(1L);
        DipendenteDTO dipendenteDTO = dipendenteMapper.toDto(dipendente);

        // An entity with an existing ID cannot be created, so this API call must fail
        restDipendenteMockMvc.perform(post("/api/dipendentes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(dipendenteDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Dipendente in the database
        List<Dipendente> dipendenteList = dipendenteRepository.findAll();
        assertThat(dipendenteList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllDipendentes() throws Exception {
        // Initialize the database
        dipendenteRepository.saveAndFlush(dipendente);

        // Get all the dipendenteList
        restDipendenteMockMvc.perform(get("/api/dipendentes?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(dipendente.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].surname").value(hasItem(DEFAULT_SURNAME.toString())))
            .andExpect(jsonPath("$.[*].ruolo").value(hasItem(DEFAULT_RUOLO.toString())))
            .andExpect(jsonPath("$.[*].codiceFiscale").value(hasItem(DEFAULT_CODICE_FISCALE.toString())))
            .andExpect(jsonPath("$.[*].reparto").value(hasItem(DEFAULT_REPARTO.toString())))
            .andExpect(jsonPath("$.[*].numeroScheda").value(hasItem(DEFAULT_NUMERO_SCHEDA.toString())))
            .andExpect(jsonPath("$.[*].schedaPersa").value(hasItem(DEFAULT_SCHEDA_PERSA.booleanValue())));
    }
    

    @Test
    @Transactional
    public void getDipendente() throws Exception {
        // Initialize the database
        dipendenteRepository.saveAndFlush(dipendente);

        // Get the dipendente
        restDipendenteMockMvc.perform(get("/api/dipendentes/{id}", dipendente.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(dipendente.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.surname").value(DEFAULT_SURNAME.toString()))
            .andExpect(jsonPath("$.ruolo").value(DEFAULT_RUOLO.toString()))
            .andExpect(jsonPath("$.codiceFiscale").value(DEFAULT_CODICE_FISCALE.toString()))
            .andExpect(jsonPath("$.reparto").value(DEFAULT_REPARTO.toString()))
            .andExpect(jsonPath("$.numeroScheda").value(DEFAULT_NUMERO_SCHEDA.toString()))
            .andExpect(jsonPath("$.schedaPersa").value(DEFAULT_SCHEDA_PERSA.booleanValue()));
    }
    @Test
    @Transactional
    public void getNonExistingDipendente() throws Exception {
        // Get the dipendente
        restDipendenteMockMvc.perform(get("/api/dipendentes/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateDipendente() throws Exception {
        // Initialize the database
        dipendenteRepository.saveAndFlush(dipendente);

        int databaseSizeBeforeUpdate = dipendenteRepository.findAll().size();

        // Update the dipendente
        Dipendente updatedDipendente = dipendenteRepository.findById(dipendente.getId()).get();
        // Disconnect from session so that the updates on updatedDipendente are not directly saved in db
        em.detach(updatedDipendente);
        updatedDipendente
            .name(UPDATED_NAME)
            .surname(UPDATED_SURNAME)
            .ruolo(UPDATED_RUOLO)
            .codiceFiscale(UPDATED_CODICE_FISCALE)
            .reparto(UPDATED_REPARTO)
            .numeroScheda(UPDATED_NUMERO_SCHEDA)
            .schedaPersa(UPDATED_SCHEDA_PERSA);
        DipendenteDTO dipendenteDTO = dipendenteMapper.toDto(updatedDipendente);

        restDipendenteMockMvc.perform(put("/api/dipendentes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(dipendenteDTO)))
            .andExpect(status().isOk());

        // Validate the Dipendente in the database
        List<Dipendente> dipendenteList = dipendenteRepository.findAll();
        assertThat(dipendenteList).hasSize(databaseSizeBeforeUpdate);
        Dipendente testDipendente = dipendenteList.get(dipendenteList.size() - 1);
        assertThat(testDipendente.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testDipendente.getSurname()).isEqualTo(UPDATED_SURNAME);
        assertThat(testDipendente.getRuolo()).isEqualTo(UPDATED_RUOLO);
        assertThat(testDipendente.getCodiceFiscale()).isEqualTo(UPDATED_CODICE_FISCALE);
        assertThat(testDipendente.getReparto()).isEqualTo(UPDATED_REPARTO);
        assertThat(testDipendente.getNumeroScheda()).isEqualTo(UPDATED_NUMERO_SCHEDA);
        assertThat(testDipendente.isSchedaPersa()).isEqualTo(UPDATED_SCHEDA_PERSA);
    }

    @Test
    @Transactional
    public void updateNonExistingDipendente() throws Exception {
        int databaseSizeBeforeUpdate = dipendenteRepository.findAll().size();

        // Create the Dipendente
        DipendenteDTO dipendenteDTO = dipendenteMapper.toDto(dipendente);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException 
        restDipendenteMockMvc.perform(put("/api/dipendentes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(dipendenteDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Dipendente in the database
        List<Dipendente> dipendenteList = dipendenteRepository.findAll();
        assertThat(dipendenteList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteDipendente() throws Exception {
        // Initialize the database
        dipendenteRepository.saveAndFlush(dipendente);

        int databaseSizeBeforeDelete = dipendenteRepository.findAll().size();

        // Get the dipendente
        restDipendenteMockMvc.perform(delete("/api/dipendentes/{id}", dipendente.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Dipendente> dipendenteList = dipendenteRepository.findAll();
        assertThat(dipendenteList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Dipendente.class);
        Dipendente dipendente1 = new Dipendente();
        dipendente1.setId(1L);
        Dipendente dipendente2 = new Dipendente();
        dipendente2.setId(dipendente1.getId());
        assertThat(dipendente1).isEqualTo(dipendente2);
        dipendente2.setId(2L);
        assertThat(dipendente1).isNotEqualTo(dipendente2);
        dipendente1.setId(null);
        assertThat(dipendente1).isNotEqualTo(dipendente2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(DipendenteDTO.class);
        DipendenteDTO dipendenteDTO1 = new DipendenteDTO();
        dipendenteDTO1.setId(1L);
        DipendenteDTO dipendenteDTO2 = new DipendenteDTO();
        assertThat(dipendenteDTO1).isNotEqualTo(dipendenteDTO2);
        dipendenteDTO2.setId(dipendenteDTO1.getId());
        assertThat(dipendenteDTO1).isEqualTo(dipendenteDTO2);
        dipendenteDTO2.setId(2L);
        assertThat(dipendenteDTO1).isNotEqualTo(dipendenteDTO2);
        dipendenteDTO1.setId(null);
        assertThat(dipendenteDTO1).isNotEqualTo(dipendenteDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(dipendenteMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(dipendenteMapper.fromId(null)).isNull();
    }
}
