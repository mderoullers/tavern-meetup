package tech.ippon.beer_api.service.mapper;


import tech.ippon.beer_api.domain.*;
import tech.ippon.beer_api.service.dto.BeerDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Beer} and its DTO {@link BeerDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface BeerMapper extends EntityMapper<BeerDTO, Beer> {



    default Beer fromId(Long id) {
        if (id == null) {
            return null;
        }
        Beer beer = new Beer();
        beer.setId(id);
        return beer;
    }
}
