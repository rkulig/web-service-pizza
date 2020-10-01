package pl.rkulig.pizza.data;

import org.springframework.data.repository.CrudRepository;
import pl.rkulig.pizza.model.User;

public interface UserRepository extends CrudRepository<User, Long> {
    User findByUsername(String username);
}
