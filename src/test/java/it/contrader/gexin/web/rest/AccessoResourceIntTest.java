package it.contrader.gexin.web.rest;

import it.contrader.gexin.GexinApp;

import it.contrader.gexin.domain.Accesso;
import it.contrader.gexin.repository.AccessoRepository;
import it.contrader.gexin.service.AccessoService;
import it.contrader.gexin.service.dto.AccessoDTO;
import it.contrader.gexin.service.mapper.AccessoMapper;
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
 * Test class for the AccessoResource REST controller.
 *
 * @see AccessoResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = GexinApp.class)
public class AccessoResourceIntTest {

    @Autowired
    private AccessoRepository accessoRepository;


    @Autowired
    private AccessoMapper accessoMapper;
    

    @Autowired
    private AccessoService accessoService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restAccessoMockMvc;

    private Accesso accesso;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final AccessoResource accessoResource = new AccessoResource(accessoService);
        this.restAccessoMockMvc = MockMvcBuilders.standaloneSetup(accessoResource)
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
    public static Accesso createEntity(EntityManager em) {
        Accesso accesso = new Accesso();
        return accesso;
    }

    @Before
    public void initTest() {
        accesso = createEntity(em);
    }

    @Test
    @Transactional
    public void createAccesso() throws Exception {
        int databaseSizeBeforeCreate = accessoRepository.findAll().size();

        // Create the Accesso
        AccessoDTO accessoDTO = accessoMapper.toDto(accesso);
        restAccessoMockMvc.perform(post("/api/accessos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(accessoDTO)))
            .andExpect(status().isCreated());

        // Validate the Accesso in the database
        List<Accesso> accessoList = accessoRepository.findAll();
        assertThat(accessoList).hasSize(databaseSizeBeforeCreate + 1);
        Accesso testAccesso = accessoList.get(accessoList.size() - 1);
    }

    @Test
    @Transactional
    public void createAccessoWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = accessoRepository.findAll().size();

        // Create the Accesso with an existing ID
        accesso.setId(1L);
        AccessoDTO accessoDTO = accessoMapper.toDto(accesso);

        // An entity with an existing ID cannot be created, so this API call must fail
        restAccessoMockMvc.perform(post("/api/accessos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(accessoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Accesso in the database
        List<Accesso> accessoList = accessoRepository.findAll();
        assertThat(accessoList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllAccessos() throws Exception {
        // Initialize the database
        accessoRepository.saveAndFlush(accesso);

        // Get all the accessoList
        restAccessoMockMvc.perform(get("/api/accessos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(accesso.getId().intValue())));
    }
    

    @Test
    @Transactional
    public void getAccesso() throws Exception {
        // Initialize the database
        accessoRepository.saveAndFlush(accesso);

        // Get the accesso
        restAccessoMockMvc.perform(get("/api/accessos/{id}", accesso.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(accesso.getId().intValue()));
    }
    @Test
    @Transactional
    public void getNonExistingAccesso() throws Exception {
        // Get the accesso
        restAccessoMockMvc.perform(get("/api/accessos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAccesso() throws Exception {
        // Initialize the database
        accessoRepository.saveAndFlush(accesso);

        int databaseSizeBeforeUpdate = accessoRepository.findAll().size();

        // Update the accesso
        Accesso updatedAccesso = accessoRepository.findById(accesso.getId()).get();
        // Disconnect from session so that the updates on updatedAccesso are not directly saved in db
        em.detach(updatedAccesso);
        AccessoDTO accessoDTO = accessoMapper.toDto(updatedAccesso);

        restAccessoMockMvc.perform(put("/api/accessos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(accessoDTO)))
            .andExpect(status().isOk());

        // Validate the Accesso in the database
        List<Accesso> accessoList = accessoRepository.findAll();
        assertThat(accessoList).hasSize(databaseSizeBeforeUpdate);
        Accesso testAccesso = accessoList.get(accessoList.size() - 1);
    }

    @Test
    @Transactional
    public void updateNonExistingAccesso() throws Exception {
        int databaseSizeBeforeUpdate = accessoRepository.findAll().size();

        // Create the Accesso
        AccessoDTO accessoDTO = accessoMapper.toDto(accesso);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException 
        restAccessoMockMvc.perform(put("/api/accessos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(accessoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Accesso in the database
        List<Accesso> accessoList = accessoRepository.findAll();
        assertThat(accessoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteAccesso() throws Exception {
        // Initialize the database
        accessoRepository.saveAndFlush(accesso);

        int databaseSizeBeforeDelete = accessoRepository.findAll().size();

        // Get the accesso
        restAccessoMockMvc.perform(delete("/api/accessos/{id}", accesso.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Accesso> accessoList = accessoRepository.findAll();
        assertThat(accessoList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Accesso.class);
        Accesso accesso1 = new Accesso();
        accesso1.setId(1L);
        Accesso accesso2 = new Accesso();
        accesso2.setId(accesso1.getId());
        assertThat(accesso1).isEqualTo(accesso2);
        accesso2.setId(2L);
        assertThat(accesso1).isNotEqualTo(accesso2);
        accesso1.setId(null);
        assertThat(accesso1).isNotEqualTo(accesso2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(AccessoDTO.class);
        AccessoDTO accessoDTO1 = new AccessoDTO();
        accessoDTO1.setId(1L);
        AccessoDTO accessoDTO2 = new AccessoDTO();
        assertThat(accessoDTO1).isNotEqualTo(accessoDTO2);
        accessoDTO2.setId(accessoDTO1.getId());
        assertThat(accessoDTO1).isEqualTo(accessoDTO2);
        accessoDTO2.setId(2L);
        assertThat(accessoDTO1).isNotEqualTo(accessoDTO2);
        accessoDTO1.setId(null);
        assertThat(accessoDTO1).isNotEqualTo(accessoDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(accessoMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(accessoMapper.fromId(null)).isNull();
    }
}
