package tech.ippon.beer_api.service.dto;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

/**
 * A DTO for the {@link tech.ippon.beer_api.domain.Beer} entity.
 */
public class BeerDTO implements Serializable {

    private Long id;

    @NotNull
    private String name;

    private String brewery;

    @NotNull
    @DecimalMin(value = "0")
    private BigDecimal price;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBrewery() {
        return brewery;
    }

    public void setBrewery(String brewery) {
        this.brewery = brewery;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        BeerDTO beerDTO = (BeerDTO) o;
        if (beerDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), beerDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "BeerDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", brewery='" + getBrewery() + "'" +
            ", price=" + getPrice() +
            "}";
    }
}
