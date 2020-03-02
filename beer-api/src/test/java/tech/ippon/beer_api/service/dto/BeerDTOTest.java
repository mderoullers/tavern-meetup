package tech.ippon.beer_api.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import tech.ippon.beer_api.web.rest.TestUtil;

public class BeerDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(BeerDTO.class);
        BeerDTO beerDTO1 = new BeerDTO();
        beerDTO1.setId(1L);
        BeerDTO beerDTO2 = new BeerDTO();
        assertThat(beerDTO1).isNotEqualTo(beerDTO2);
        beerDTO2.setId(beerDTO1.getId());
        assertThat(beerDTO1).isEqualTo(beerDTO2);
        beerDTO2.setId(2L);
        assertThat(beerDTO1).isNotEqualTo(beerDTO2);
        beerDTO1.setId(null);
        assertThat(beerDTO1).isNotEqualTo(beerDTO2);
    }
}
