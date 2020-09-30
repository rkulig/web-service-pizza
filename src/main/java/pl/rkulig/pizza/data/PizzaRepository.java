package pl.rkulig.pizza.data;

import org.springframework.data.repository.CrudRepository;
import pl.rkulig.pizza.model.Pizza;

public interface PizzaRepository extends CrudRepository<Pizza, Long> {

}
