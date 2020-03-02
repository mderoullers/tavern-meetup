package tech.ippon.service;

import tech.ippon.domain.Food;

import javax.enterprise.context.ApplicationScoped;
import java.math.BigDecimal;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Set;

@ApplicationScoped
public class FoodService {

    private Set<Food> foods = Collections.newSetFromMap(Collections.synchronizedMap(new LinkedHashMap<>()));

    public FoodService() {
        foods.add(new Food("Welsh", BigDecimal.TEN));
        foods.add(new Food("Carbonade flamande", BigDecimal.valueOf(15)));
        foods.add(new Food("Ch'ti burger", BigDecimal.valueOf(12)));
    }

    public Set<Food> getFoods() {
        return foods;
    }

    public Set<Food> postFood(final Food food) {
        foods.add(food);
        return foods;
    }

    public Set<Food> deleteFood(Food food) {
        foods.removeIf(existingFruit -> existingFruit.name.contentEquals(food.name));
        return foods;
    }
}
