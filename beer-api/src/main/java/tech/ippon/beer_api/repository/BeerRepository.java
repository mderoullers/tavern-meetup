package tech.ippon.beer_api.repository;

import tech.ippon.beer_api.domain.Beer;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Beer entity.
 */
@SuppressWarnings("unused")
@Repository
public interface BeerRepository extends JpaRepository<Beer, Long> {

}
