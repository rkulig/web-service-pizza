package pl.rkulig.pizza;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import pl.rkulig.pizza.data.IngredientRepository;
import pl.rkulig.pizza.model.Ingredient;
import pl.rkulig.pizza.model.Ingredient.Type;

@SpringBootApplication
public class PizzaApplication {

    public static void main(String[] args) {
        SpringApplication.run(PizzaApplication.class, args);
    }

    @Bean
    public CommandLineRunner dataLoader(IngredientRepository repo) {
        return new CommandLineRunner() {
            @Override
            public void run(String... args) throws Exception {
                        repo.save(new Ingredient("CIEN", "cienkie", Type.DOUGH));
                        repo.save(new Ingredient("GRUB", "grube", Type.DOUGH));
                        repo.save(new Ingredient("KURC", "kurczak", Type.PROTEIN));
                        repo.save(new Ingredient("SALA", "salami", Type.PROTEIN));
                        repo.save(new Ingredient("PAPR", "papryka", Type.VEGGIES));
                        repo.save(new Ingredient("PIEC", "pieczarki", Type.VEGGIES));
                        repo.save(new Ingredient("KETC", "ketchup", Type.SAUCE));
                        repo.save(new Ingredient("CZOS", "czosnkowy", Type.SAUCE));
            }
        };
    }

}

