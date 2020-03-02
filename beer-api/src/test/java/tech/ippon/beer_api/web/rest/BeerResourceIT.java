package tech.ippon.beer_api.web.rest;

import tech.ippon.beer_api.BeerApiApp;
import tech.ippon.beer_api.domain.Beer;
import tech.ippon.beer_api.repository.BeerRepository;
import tech.ippon.beer_api.service.BeerService;
import tech.ippon.beer_api.service.dto.BeerDTO;
import tech.ippon.beer_api.service.mapper.BeerMapper;
import tech.ippon.beer_api.web.rest.errors.ExceptionTranslator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Validator;

import javax.persistence.EntityManager;
import java.math.BigDecimal;
import java.util.List;

import static tech.ippon.beer_api.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link BeerResource} REST controller.
 */
@SpringBootTest(classes = BeerApiApp.class)
public class BeerResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_BREWERY = "AAAAAAAAAA";
    private static final String UPDATED_BREWERY = "BBBBBBBBBB";

    private static final BigDecimal DEFAULT_PRICE = new BigDecimal(0);
    private static final BigDecimal UPDATED_PRICE = new BigDecimal(1);

    @Autowired
    private BeerRepository beerRepository;

    @Autowired
    private BeerMapper beerMapper;

    @Autowired
    private BeerService beerService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    @Autowired
    private Validator validator;

    private MockMvc restBeerMockMvc;

    private Beer beer;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final BeerResource beerResource = new BeerResource(beerService);
        this.restBeerMockMvc = MockMvcBuilders.standaloneSetup(beerResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter)
            .setValidator(validator).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Beer createEntity(EntityManager em) {
        Beer beer = new Beer()
            .name(DEFAULT_NAME)
            .brewery(DEFAULT_BREWERY)
            .price(DEFAULT_PRICE);
        return beer;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Beer createUpdatedEntity(EntityManager em) {
        Beer beer = new Beer()
            .name(UPDATED_NAME)
            .brewery(UPDATED_BREWERY)
            .price(UPDATED_PRICE);
        return beer;
    }

    @BeforeEach
    public void initTest() {
        beer = createEntity(em);
    }

    @Test
    @Transactional
    public void createBeer() throws Exception {
        int databaseSizeBeforeCreate = beerRepository.findAll().size();

        // Create the Beer
        BeerDTO beerDTO = beerMapper.toDto(beer);
        restBeerMockMvc.perform(post("/api/beers")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(beerDTO)))
            .andExpect(status().isCreated());

        // Validate the Beer in the database
        List<Beer> beerList = beerRepository.findAll();
        assertThat(beerList).hasSize(databaseSizeBeforeCreate + 1);
        Beer testBeer = beerList.get(beerList.size() - 1);
        assertThat(testBeer.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testBeer.getBrewery()).isEqualTo(DEFAULT_BREWERY);
        assertThat(testBeer.getPrice()).isEqualTo(DEFAULT_PRICE);
    }

    @Test
    @Transactional
    public void createBeerWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = beerRepository.findAll().size();

        // Create the Beer with an existing ID
        beer.setId(1L);
        BeerDTO beerDTO = beerMapper.toDto(beer);

        // An entity with an existing ID cannot be created, so this API call must fail
        restBeerMockMvc.perform(post("/api/beers")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(beerDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Beer in the database
        List<Beer> beerList = beerRepository.findAll();
        assertThat(beerList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = beerRepository.findAll().size();
        // set the field null
        beer.setName(null);

        // Create the Beer, which fails.
        BeerDTO beerDTO = beerMapper.toDto(beer);

        restBeerMockMvc.perform(post("/api/beers")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(beerDTO)))
            .andExpect(status().isBadRequest());

        List<Beer> beerList = beerRepository.findAll();
        assertThat(beerList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkPriceIsRequired() throws Exception {
        int databaseSizeBeforeTest = beerRepository.findAll().size();
        // set the field null
        beer.setPrice(null);

        // Create the Beer, which fails.
        BeerDTO beerDTO = beerMapper.toDto(beer);

        restBeerMockMvc.perform(post("/api/beers")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(beerDTO)))
            .andExpect(status().isBadRequest());

        List<Beer> beerList = beerRepository.findAll();
        assertThat(beerList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllBeers() throws Exception {
        // Initialize the database
        beerRepository.saveAndFlush(beer);

        // Get all the beerList
        restBeerMockMvc.perform(get("/api/beers?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(beer.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].brewery").value(hasItem(DEFAULT_BREWERY)))
            .andExpect(jsonPath("$.[*].price").value(hasItem(DEFAULT_PRICE.intValue())));
    }
    
    @Test
    @Transactional
    public void getBeer() throws Exception {
        // Initialize the database
        beerRepository.saveAndFlush(beer);

        // Get the beer
        restBeerMockMvc.perform(get("/api/beers/{id}", beer.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(beer.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.brewery").value(DEFAULT_BREWERY))
            .andExpect(jsonPath("$.price").value(DEFAULT_PRICE.intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingBeer() throws Exception {
        // Get the beer
        restBeerMockMvc.perform(get("/api/beers/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateBeer() throws Exception {
        // Initialize the database
        beerRepository.saveAndFlush(beer);

        int databaseSizeBeforeUpdate = beerRepository.findAll().size();

        // Update the beer
        Beer updatedBeer = beerRepository.findById(beer.getId()).get();
        // Disconnect from session so that the updates on updatedBeer are not directly saved in db
        em.detach(updatedBeer);
        updatedBeer
            .name(UPDATED_NAME)
            .brewery(UPDATED_BREWERY)
            .price(UPDATED_PRICE);
        BeerDTO beerDTO = beerMapper.toDto(updatedBeer);

        restBeerMockMvc.perform(put("/api/beers")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(beerDTO)))
            .andExpect(status().isOk());

        // Validate the Beer in the database
        List<Beer> beerList = beerRepository.findAll();
        assertThat(beerList).hasSize(databaseSizeBeforeUpdate);
        Beer testBeer = beerList.get(beerList.size() - 1);
        assertThat(testBeer.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testBeer.getBrewery()).isEqualTo(UPDATED_BREWERY);
        assertThat(testBeer.getPrice()).isEqualTo(UPDATED_PRICE);
    }

    @Test
    @Transactional
    public void updateNonExistingBeer() throws Exception {
        int databaseSizeBeforeUpdate = beerRepository.findAll().size();

        // Create the Beer
        BeerDTO beerDTO = beerMapper.toDto(beer);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBeerMockMvc.perform(put("/api/beers")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(beerDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Beer in the database
        List<Beer> beerList = beerRepository.findAll();
        assertThat(beerList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteBeer() throws Exception {
        // Initialize the database
        beerRepository.saveAndFlush(beer);

        int databaseSizeBeforeDelete = beerRepository.findAll().size();

        // Delete the beer
        restBeerMockMvc.perform(delete("/api/beers/{id}", beer.getId())
            .accept(TestUtil.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Beer> beerList = beerRepository.findAll();
        assertThat(beerList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
