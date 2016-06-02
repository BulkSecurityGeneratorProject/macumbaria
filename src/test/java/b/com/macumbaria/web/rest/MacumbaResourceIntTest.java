package b.com.macumbaria.web.rest;

import b.com.macumbaria.Application;
import b.com.macumbaria.domain.Macumba;
import b.com.macumbaria.repository.MacumbaRepository;
import b.com.macumbaria.service.MacumbaService;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.hamcrest.Matchers.hasItem;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


/**
 * Test class for the MacumbaResource REST controller.
 *
 * @see MacumbaResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class MacumbaResourceIntTest {

    private static final String DEFAULT_DESTINATARIO = "AA";
    private static final String UPDATED_DESTINATARIO = "BB";

    @Inject
    private MacumbaRepository macumbaRepository;

    @Inject
    private MacumbaService macumbaService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restMacumbaMockMvc;

    private Macumba macumba;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        MacumbaResource macumbaResource = new MacumbaResource();
        ReflectionTestUtils.setField(macumbaResource, "macumbaService", macumbaService);
        this.restMacumbaMockMvc = MockMvcBuilders.standaloneSetup(macumbaResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        macumba = new Macumba();
        macumba.setDestinatario(DEFAULT_DESTINATARIO);
    }

    @Test
    @Transactional
    public void createMacumba() throws Exception {
        int databaseSizeBeforeCreate = macumbaRepository.findAll().size();

        // Create the Macumba

        restMacumbaMockMvc.perform(post("/api/macumbas")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(macumba)))
                .andExpect(status().isCreated());

        // Validate the Macumba in the database
        List<Macumba> macumbas = macumbaRepository.findAll();
        assertThat(macumbas).hasSize(databaseSizeBeforeCreate + 1);
        Macumba testMacumba = macumbas.get(macumbas.size() - 1);
        assertThat(testMacumba.getDestinatario()).isEqualTo(DEFAULT_DESTINATARIO);
    }

    @Test
    @Transactional
    public void checkDestinatarioIsRequired() throws Exception {
        int databaseSizeBeforeTest = macumbaRepository.findAll().size();
        // set the field null
        macumba.setDestinatario(null);

        // Create the Macumba, which fails.

        restMacumbaMockMvc.perform(post("/api/macumbas")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(macumba)))
                .andExpect(status().isBadRequest());

        List<Macumba> macumbas = macumbaRepository.findAll();
        assertThat(macumbas).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllMacumbas() throws Exception {
        // Initialize the database
        macumbaRepository.saveAndFlush(macumba);

        // Get all the macumbas
        restMacumbaMockMvc.perform(get("/api/macumbas?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(macumba.getId().intValue())))
                .andExpect(jsonPath("$.[*].destinatario").value(hasItem(DEFAULT_DESTINATARIO.toString())));
    }

    @Test
    @Transactional
    public void getMacumba() throws Exception {
        // Initialize the database
        macumbaRepository.saveAndFlush(macumba);

        // Get the macumba
        restMacumbaMockMvc.perform(get("/api/macumbas/{id}", macumba.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(macumba.getId().intValue()))
            .andExpect(jsonPath("$.destinatario").value(DEFAULT_DESTINATARIO.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingMacumba() throws Exception {
        // Get the macumba
        restMacumbaMockMvc.perform(get("/api/macumbas/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateMacumba() throws Exception {
        // Initialize the database
        macumbaRepository.saveAndFlush(macumba);

		int databaseSizeBeforeUpdate = macumbaRepository.findAll().size();

        // Update the macumba
        macumba.setDestinatario(UPDATED_DESTINATARIO);

        restMacumbaMockMvc.perform(put("/api/macumbas")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(macumba)))
                .andExpect(status().isOk());

        // Validate the Macumba in the database
        List<Macumba> macumbas = macumbaRepository.findAll();
        assertThat(macumbas).hasSize(databaseSizeBeforeUpdate);
        Macumba testMacumba = macumbas.get(macumbas.size() - 1);
        assertThat(testMacumba.getDestinatario()).isEqualTo(UPDATED_DESTINATARIO);
    }

    @Test
    @Transactional
    public void deleteMacumba() throws Exception {
        // Initialize the database
        macumbaRepository.saveAndFlush(macumba);

		int databaseSizeBeforeDelete = macumbaRepository.findAll().size();

        // Get the macumba
        restMacumbaMockMvc.perform(delete("/api/macumbas/{id}", macumba.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Macumba> macumbas = macumbaRepository.findAll();
        assertThat(macumbas).hasSize(databaseSizeBeforeDelete - 1);
    }
}
