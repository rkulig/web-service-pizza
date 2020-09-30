package pl.rkulig.pizza.data;

import org.springframework.data.repository.CrudRepository;
import pl.rkulig.pizza.model.Ingredient;

public interface IngredientRepository extends CrudRepository<Ingredient, String> {

}
