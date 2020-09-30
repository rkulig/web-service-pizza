package pl.rkulig.pizza.data;

import org.springframework.data.repository.CrudRepository;
import pl.rkulig.pizza.model.Order;

public interface OrderRepository extends CrudRepository <Order, Long> {

}
