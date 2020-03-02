package tech.ippon.resource;

import tech.ippon.domain.Food;
import tech.ippon.service.FoodService;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.Set;

@Path("/foods")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class FoodResource {

    @Inject
    private FoodService foodService;

    @GET
    public Set<Food> getFoods() {
        return foodService.getFoods();
    }

    @POST
    public Set<Food> postFood(Food food) {
        return foodService.postFood(food);
    }

    @DELETE
    public Set<Food> deleteFood(Food food) {
        return foodService.deleteFood(food);
    }
}
