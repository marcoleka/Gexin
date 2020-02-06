package it.contrader.gexin.web.rest;

import it.contrader.gexin.GexinApp;

import it.contrader.gexin.domain.Door;
import it.contrader.gexin.repository.DoorRepository;
import it.contrader.gexin.service.DoorService;
import it.contrader.gexin.service.dto.DoorDTO;
import it.contrader.gexin.service.mapper.DoorMapper;
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
 * Test class for the DoorResource REST controller.
 *
 * @see DoorResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = GexinApp.class)
public class DoorResourceIntTest {

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final String DEFAULT_DOOR_NAME = "AAAAAAAAAA";
    private static final String UPDATED_DOOR_NAME = "BBBBBBBBBB";

    @Autowired
    private DoorRepository doorRepository;


    @Autowired
    private DoorMapper doorMapper;
    

    @Autowired
    private DoorService doorService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restDoorMockMvc;

    private Door door;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final DoorResource doorResource = new DoorResource(doorService);
        this.restDoorMockMvc = MockMvcBuilders.standaloneSetup(doorResource)
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
    public static Door createEntity(EntityManager em) {
        Door door = new Door()
            .description(DEFAULT_DESCRIPTION)
            .doorName(DEFAULT_DOOR_NAME);
        return door;
    }

    @Before
    public void initTest() {
        door = createEntity(em);
    }

    @Test
    @Transactional
    public void createDoor() throws Exception {
        int databaseSizeBeforeCreate = doorRepository.findAll().size();

        // Create the Door
        DoorDTO doorDTO = doorMapper.toDto(door);
        restDoorMockMvc.perform(post("/api/doors")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(doorDTO)))
            .andExpect(status().isCreated());

        // Validate the Door in the database
        List<Door> doorList = doorRepository.findAll();
        assertThat(doorList).hasSize(databaseSizeBeforeCreate + 1);
        Door testDoor = doorList.get(doorList.size() - 1);
        assertThat(testDoor.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testDoor.getDoorName()).isEqualTo(DEFAULT_DOOR_NAME);
    }

    @Test
    @Transactional
    public void createDoorWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = doorRepository.findAll().size();

        // Create the Door with an existing ID
        door.setId(1L);
        DoorDTO doorDTO = doorMapper.toDto(door);

        // An entity with an existing ID cannot be created, so this API call must fail
        restDoorMockMvc.perform(post("/api/doors")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(doorDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Door in the database
        List<Door> doorList = doorRepository.findAll();
        assertThat(doorList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllDoors() throws Exception {
        // Initialize the database
        doorRepository.saveAndFlush(door);

        // Get all the doorList
        restDoorMockMvc.perform(get("/api/doors?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(door.getId().intValue())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].doorName").value(hasItem(DEFAULT_DOOR_NAME.toString())));
    }
    

    @Test
    @Transactional
    public void getDoor() throws Exception {
        // Initialize the database
        doorRepository.saveAndFlush(door);

        // Get the door
        restDoorMockMvc.perform(get("/api/doors/{id}", door.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(door.getId().intValue()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.doorName").value(DEFAULT_DOOR_NAME.toString()));
    }
    @Test
    @Transactional
    public void getNonExistingDoor() throws Exception {
        // Get the door
        restDoorMockMvc.perform(get("/api/doors/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateDoor() throws Exception {
        // Initialize the database
        doorRepository.saveAndFlush(door);

        int databaseSizeBeforeUpdate = doorRepository.findAll().size();

        // Update the door
        Door updatedDoor = doorRepository.findById(door.getId()).get();
        // Disconnect from session so that the updates on updatedDoor are not directly saved in db
        em.detach(updatedDoor);
        updatedDoor
            .description(UPDATED_DESCRIPTION)
            .doorName(UPDATED_DOOR_NAME);
        DoorDTO doorDTO = doorMapper.toDto(updatedDoor);

        restDoorMockMvc.perform(put("/api/doors")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(doorDTO)))
            .andExpect(status().isOk());

        // Validate the Door in the database
        List<Door> doorList = doorRepository.findAll();
        assertThat(doorList).hasSize(databaseSizeBeforeUpdate);
        Door testDoor = doorList.get(doorList.size() - 1);
        assertThat(testDoor.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testDoor.getDoorName()).isEqualTo(UPDATED_DOOR_NAME);
    }

    @Test
    @Transactional
    public void updateNonExistingDoor() throws Exception {
        int databaseSizeBeforeUpdate = doorRepository.findAll().size();

        // Create the Door
        DoorDTO doorDTO = doorMapper.toDto(door);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException 
        restDoorMockMvc.perform(put("/api/doors")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(doorDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Door in the database
        List<Door> doorList = doorRepository.findAll();
        assertThat(doorList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteDoor() throws Exception {
        // Initialize the database
        doorRepository.saveAndFlush(door);

        int databaseSizeBeforeDelete = doorRepository.findAll().size();

        // Get the door
        restDoorMockMvc.perform(delete("/api/doors/{id}", door.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Door> doorList = doorRepository.findAll();
        assertThat(doorList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Door.class);
        Door door1 = new Door();
        door1.setId(1L);
        Door door2 = new Door();
        door2.setId(door1.getId());
        assertThat(door1).isEqualTo(door2);
        door2.setId(2L);
        assertThat(door1).isNotEqualTo(door2);
        door1.setId(null);
        assertThat(door1).isNotEqualTo(door2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(DoorDTO.class);
        DoorDTO doorDTO1 = new DoorDTO();
        doorDTO1.setId(1L);
        DoorDTO doorDTO2 = new DoorDTO();
        assertThat(doorDTO1).isNotEqualTo(doorDTO2);
        doorDTO2.setId(doorDTO1.getId());
        assertThat(doorDTO1).isEqualTo(doorDTO2);
        doorDTO2.setId(2L);
        assertThat(doorDTO1).isNotEqualTo(doorDTO2);
        doorDTO1.setId(null);
        assertThat(doorDTO1).isNotEqualTo(doorDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(doorMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(doorMapper.fromId(null)).isNull();
    }
}
