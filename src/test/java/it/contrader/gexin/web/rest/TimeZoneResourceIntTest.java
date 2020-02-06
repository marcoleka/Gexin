package it.contrader.gexin.web.rest;

import it.contrader.gexin.GexinApp;

import it.contrader.gexin.domain.TimeZone;
import it.contrader.gexin.repository.TimeZoneRepository;
import it.contrader.gexin.service.TimeZoneService;
import it.contrader.gexin.service.dto.TimeZoneDTO;
import it.contrader.gexin.service.mapper.TimeZoneMapper;
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
import java.time.Instant;
import java.time.ZonedDateTime;
import java.time.ZoneOffset;
import java.time.ZoneId;
import java.util.List;


import static it.contrader.gexin.web.rest.TestUtil.sameInstant;
import static it.contrader.gexin.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import it.contrader.gexin.domain.enumeration.Day;
/**
 * Test class for the TimeZoneResource REST controller.
 *
 * @see TimeZoneResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = GexinApp.class)
public class TimeZoneResourceIntTest {

    private static final ZonedDateTime DEFAULT_DA_1 = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_DA_1 = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final ZonedDateTime DEFAULT_DA_2 = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_DA_2 = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final ZonedDateTime DEFAULT_DA_3 = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_DA_3 = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final ZonedDateTime DEFAULT_DA_4 = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_DA_4 = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final ZonedDateTime DEFAULT_A_1 = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_A_1 = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final ZonedDateTime DEFAULT_A_2 = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_A_2 = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final ZonedDateTime DEFAULT_A_3 = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_A_3 = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final ZonedDateTime DEFAULT_A_4 = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_A_4 = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final Day DEFAULT_GIORNO = Day.LUNEDI;
    private static final Day UPDATED_GIORNO = Day.MARTEDI;

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    @Autowired
    private TimeZoneRepository timeZoneRepository;


    @Autowired
    private TimeZoneMapper timeZoneMapper;
    

    @Autowired
    private TimeZoneService timeZoneService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restTimeZoneMockMvc;

    private TimeZone timeZone;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final TimeZoneResource timeZoneResource = new TimeZoneResource(timeZoneService);
        this.restTimeZoneMockMvc = MockMvcBuilders.standaloneSetup(timeZoneResource)
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
    public static TimeZone createEntity(EntityManager em) {
        TimeZone timeZone = new TimeZone()
            .da1(DEFAULT_DA_1)
            .da2(DEFAULT_DA_2)
            .da3(DEFAULT_DA_3)
            .da4(DEFAULT_DA_4)
            .a1(DEFAULT_A_1)
            .a2(DEFAULT_A_2)
            .a3(DEFAULT_A_3)
            .a4(DEFAULT_A_4)
            .giorno(DEFAULT_GIORNO)
            .description(DEFAULT_DESCRIPTION);
        return timeZone;
    }

    @Before
    public void initTest() {
        timeZone = createEntity(em);
    }

    @Test
    @Transactional
    public void createTimeZone() throws Exception {
        int databaseSizeBeforeCreate = timeZoneRepository.findAll().size();

        // Create the TimeZone
        TimeZoneDTO timeZoneDTO = timeZoneMapper.toDto(timeZone);
        restTimeZoneMockMvc.perform(post("/api/time-zones")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(timeZoneDTO)))
            .andExpect(status().isCreated());

        // Validate the TimeZone in the database
        List<TimeZone> timeZoneList = timeZoneRepository.findAll();
        assertThat(timeZoneList).hasSize(databaseSizeBeforeCreate + 1);
        TimeZone testTimeZone = timeZoneList.get(timeZoneList.size() - 1);
        assertThat(testTimeZone.getDa1()).isEqualTo(DEFAULT_DA_1);
        assertThat(testTimeZone.getDa2()).isEqualTo(DEFAULT_DA_2);
        assertThat(testTimeZone.getDa3()).isEqualTo(DEFAULT_DA_3);
        assertThat(testTimeZone.getDa4()).isEqualTo(DEFAULT_DA_4);
        assertThat(testTimeZone.geta1()).isEqualTo(DEFAULT_A_1);
        assertThat(testTimeZone.geta2()).isEqualTo(DEFAULT_A_2);
        assertThat(testTimeZone.geta3()).isEqualTo(DEFAULT_A_3);
        assertThat(testTimeZone.geta4()).isEqualTo(DEFAULT_A_4);
        assertThat(testTimeZone.getGiorno()).isEqualTo(DEFAULT_GIORNO);
        assertThat(testTimeZone.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
    }

    @Test
    @Transactional
    public void createTimeZoneWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = timeZoneRepository.findAll().size();

        // Create the TimeZone with an existing ID
        timeZone.setId(1L);
        TimeZoneDTO timeZoneDTO = timeZoneMapper.toDto(timeZone);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTimeZoneMockMvc.perform(post("/api/time-zones")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(timeZoneDTO)))
            .andExpect(status().isBadRequest());

        // Validate the TimeZone in the database
        List<TimeZone> timeZoneList = timeZoneRepository.findAll();
        assertThat(timeZoneList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllTimeZones() throws Exception {
        // Initialize the database
        timeZoneRepository.saveAndFlush(timeZone);

        // Get all the timeZoneList
        restTimeZoneMockMvc.perform(get("/api/time-zones?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(timeZone.getId().intValue())))
            .andExpect(jsonPath("$.[*].da1").value(hasItem(sameInstant(DEFAULT_DA_1))))
            .andExpect(jsonPath("$.[*].da2").value(hasItem(sameInstant(DEFAULT_DA_2))))
            .andExpect(jsonPath("$.[*].da3").value(hasItem(sameInstant(DEFAULT_DA_3))))
            .andExpect(jsonPath("$.[*].da4").value(hasItem(sameInstant(DEFAULT_DA_4))))
            .andExpect(jsonPath("$.[*].a1").value(hasItem(sameInstant(DEFAULT_A_1))))
            .andExpect(jsonPath("$.[*].a2").value(hasItem(sameInstant(DEFAULT_A_2))))
            .andExpect(jsonPath("$.[*].a3").value(hasItem(sameInstant(DEFAULT_A_3))))
            .andExpect(jsonPath("$.[*].a4").value(hasItem(sameInstant(DEFAULT_A_4))))
            .andExpect(jsonPath("$.[*].giorno").value(hasItem(DEFAULT_GIORNO.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())));
    }
    

    @Test
    @Transactional
    public void getTimeZone() throws Exception {
        // Initialize the database
        timeZoneRepository.saveAndFlush(timeZone);

        // Get the timeZone
        restTimeZoneMockMvc.perform(get("/api/time-zones/{id}", timeZone.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(timeZone.getId().intValue()))
            .andExpect(jsonPath("$.da1").value(sameInstant(DEFAULT_DA_1)))
            .andExpect(jsonPath("$.da2").value(sameInstant(DEFAULT_DA_2)))
            .andExpect(jsonPath("$.da3").value(sameInstant(DEFAULT_DA_3)))
            .andExpect(jsonPath("$.da4").value(sameInstant(DEFAULT_DA_4)))
            .andExpect(jsonPath("$.a1").value(sameInstant(DEFAULT_A_1)))
            .andExpect(jsonPath("$.a2").value(sameInstant(DEFAULT_A_2)))
            .andExpect(jsonPath("$.a3").value(sameInstant(DEFAULT_A_3)))
            .andExpect(jsonPath("$.a4").value(sameInstant(DEFAULT_A_4)))
            .andExpect(jsonPath("$.giorno").value(DEFAULT_GIORNO.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()));
    }
    @Test
    @Transactional
    public void getNonExistingTimeZone() throws Exception {
        // Get the timeZone
        restTimeZoneMockMvc.perform(get("/api/time-zones/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTimeZone() throws Exception {
        // Initialize the database
        timeZoneRepository.saveAndFlush(timeZone);

        int databaseSizeBeforeUpdate = timeZoneRepository.findAll().size();

        // Update the timeZone
        TimeZone updatedTimeZone = timeZoneRepository.findById(timeZone.getId()).get();
        // Disconnect from session so that the updates on updatedTimeZone are not directly saved in db
        em.detach(updatedTimeZone);
        updatedTimeZone
            .da1(UPDATED_DA_1)
            .da2(UPDATED_DA_2)
            .da3(UPDATED_DA_3)
            .da4(UPDATED_DA_4)
            .a1(UPDATED_A_1)
            .a2(UPDATED_A_2)
            .a3(UPDATED_A_3)
            .a4(UPDATED_A_4)
            .giorno(UPDATED_GIORNO)
            .description(UPDATED_DESCRIPTION);
        TimeZoneDTO timeZoneDTO = timeZoneMapper.toDto(updatedTimeZone);

        restTimeZoneMockMvc.perform(put("/api/time-zones")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(timeZoneDTO)))
            .andExpect(status().isOk());

        // Validate the TimeZone in the database
        List<TimeZone> timeZoneList = timeZoneRepository.findAll();
        assertThat(timeZoneList).hasSize(databaseSizeBeforeUpdate);
        TimeZone testTimeZone = timeZoneList.get(timeZoneList.size() - 1);
        assertThat(testTimeZone.getDa1()).isEqualTo(UPDATED_DA_1);
        assertThat(testTimeZone.getDa2()).isEqualTo(UPDATED_DA_2);
        assertThat(testTimeZone.getDa3()).isEqualTo(UPDATED_DA_3);
        assertThat(testTimeZone.getDa4()).isEqualTo(UPDATED_DA_4);
        assertThat(testTimeZone.geta1()).isEqualTo(UPDATED_A_1);
        assertThat(testTimeZone.geta2()).isEqualTo(UPDATED_A_2);
        assertThat(testTimeZone.geta3()).isEqualTo(UPDATED_A_3);
        assertThat(testTimeZone.geta4()).isEqualTo(UPDATED_A_4);
        assertThat(testTimeZone.getGiorno()).isEqualTo(UPDATED_GIORNO);
        assertThat(testTimeZone.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void updateNonExistingTimeZone() throws Exception {
        int databaseSizeBeforeUpdate = timeZoneRepository.findAll().size();

        // Create the TimeZone
        TimeZoneDTO timeZoneDTO = timeZoneMapper.toDto(timeZone);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException 
        restTimeZoneMockMvc.perform(put("/api/time-zones")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(timeZoneDTO)))
            .andExpect(status().isBadRequest());

        // Validate the TimeZone in the database
        List<TimeZone> timeZoneList = timeZoneRepository.findAll();
        assertThat(timeZoneList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteTimeZone() throws Exception {
        // Initialize the database
        timeZoneRepository.saveAndFlush(timeZone);

        int databaseSizeBeforeDelete = timeZoneRepository.findAll().size();

        // Get the timeZone
        restTimeZoneMockMvc.perform(delete("/api/time-zones/{id}", timeZone.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<TimeZone> timeZoneList = timeZoneRepository.findAll();
        assertThat(timeZoneList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TimeZone.class);
        TimeZone timeZone1 = new TimeZone();
        timeZone1.setId(1L);
        TimeZone timeZone2 = new TimeZone();
        timeZone2.setId(timeZone1.getId());
        assertThat(timeZone1).isEqualTo(timeZone2);
        timeZone2.setId(2L);
        assertThat(timeZone1).isNotEqualTo(timeZone2);
        timeZone1.setId(null);
        assertThat(timeZone1).isNotEqualTo(timeZone2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(TimeZoneDTO.class);
        TimeZoneDTO timeZoneDTO1 = new TimeZoneDTO();
        timeZoneDTO1.setId(1L);
        TimeZoneDTO timeZoneDTO2 = new TimeZoneDTO();
        assertThat(timeZoneDTO1).isNotEqualTo(timeZoneDTO2);
        timeZoneDTO2.setId(timeZoneDTO1.getId());
        assertThat(timeZoneDTO1).isEqualTo(timeZoneDTO2);
        timeZoneDTO2.setId(2L);
        assertThat(timeZoneDTO1).isNotEqualTo(timeZoneDTO2);
        timeZoneDTO1.setId(null);
        assertThat(timeZoneDTO1).isNotEqualTo(timeZoneDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(timeZoneMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(timeZoneMapper.fromId(null)).isNull();
    }
}
