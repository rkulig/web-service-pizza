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
                        repo.save(new Ingredient("ROST", "rostbef", Type.PROTEIN));
                        repo.save(new Ingredient("CHOR", "chorizo", Type.PROTEIN));
                        repo.save(new Ingredient("SZYN", "szynka gotowana", Type.PROTEIN));
                        repo.save(new Ingredient("SZYN", "boczek wędzony", Type.PROTEIN));
                        repo.save(new Ingredient("SALA", "salami", Type.PROTEIN));
                        repo.save(new Ingredient("PAPR", "papryka", Type.VEGGIES));
                        repo.save(new Ingredient("BURA", "burak", Type.VEGGIES));
                        repo.save(new Ingredient("POMI", "pomidor", Type.VEGGIES));
                        repo.save(new Ingredient("CEBU", "cebula czerwona", Type.VEGGIES));
                        repo.save(new Ingredient("BROK", "brokuły", Type.VEGGIES));
                        repo.save(new Ingredient("OLI", "oliwki czarne", Type.VEGGIES));
                        repo.save(new Ingredient("CUKI", "cukinia", Type.VEGGIES));
                        repo.save(new Ingredient("ZIEM", "ziemniaki", Type.VEGGIES));
                        repo.save(new Ingredient("KUKU", "kukurydza", Type.VEGGIES));
                        repo.save(new Ingredient("SZPI", "szpinak", Type.VEGGIES));
                        repo.save(new Ingredient("JALA", "jalapenos", Type.VEGGIES));
                        repo.save(new Ingredient("PIEC", "pieczarki", Type.VEGGIES));
                        repo.save(new Ingredient("KETC", "ketchup", Type.SAUCE));
                        repo.save(new Ingredient("KETC", "ketchup", Type.SAUCE));
                        repo.save(new Ingredient("MEKS", "meksykański", Type.SAUCE));
                        repo.save(new Ingredient("ZIOL", "ziołowy", Type.SAUCE));
                        repo.save(new Ingredient("CZOS", "czosnkowy", Type.SAUCE));
            }
        };
    }

}

