package pl.rkulig.pizza;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import pl.rkulig.pizza.data.IngredientRepository;
import pl.rkulig.pizza.data.OrderRepository;
import pl.rkulig.pizza.data.PizzaRepository;
import pl.rkulig.pizza.model.Ingredient;
import pl.rkulig.pizza.model.Ingredient.Type;
import pl.rkulig.pizza.model.Pizza;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import javax.persistence.criteria.CriteriaBuilder;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@WebMvcTest
public class DesignPizzaControllerTest {

    @Autowired
    private MockMvc mockMvc;

    private List<Ingredient> ingredients;

    private Pizza design;

    @MockBean
    private IngredientRepository ingredientRepository;

    @MockBean
    private PizzaRepository designRepository;

    @MockBean
    private OrderRepository orderRepository;

    @Before
    public void setup() {
        ingredients = Arrays.asList(
                new Ingredient("CIEN", "cienkie", Type.DOUGH),
                new Ingredient("GRUB", "grube", Type.DOUGH),
                new Ingredient("KURC", "kurczak", Type.PROTEIN),
                new Ingredient("SALA", "salami", Type.PROTEIN),
                new Ingredient("PAPR", "papryka", Type.VEGGIES),
                new Ingredient("PIEC", "pieczarki", Type.VEGGIES),
                new Ingredient("KETC", "ketchup", Type.SAUCE),
                new Ingredient("CZOS", "czosnkowy", Type.SAUCE)
        );

        when(ingredientRepository.findAll()).thenReturn(ingredients);

        when(ingredientRepository.findById("CIEN")).thenReturn(Optional.of(new Ingredient("CIEN", "cienkie", Type.DOUGH)));
        when(ingredientRepository.findById("KURC")).thenReturn(Optional.of(new Ingredient("KURC", "kurczak", Type.PROTEIN)));
        when(ingredientRepository.findById("PAPR")).thenReturn(Optional.of(new Ingredient("PAPR", "papryka", Type.VEGGIES)));

        design = new Pizza();
        design.setPizzaName("Test Pizza");

        design.setIngredients(Arrays.asList(
                new Ingredient("CIEN", "cienkie", Type.DOUGH),
                new Ingredient("KURC", "kurczak", Type.PROTEIN),
                new Ingredient("PAPR", "papryka", Type.VEGGIES)
        ));
    }

    @Test
    public void testShowDesignForm() throws Exception {
        mockMvc.perform(get("/design"))
                .andExpect(status().isOk())
                .andExpect(view().name("design"))
                .andExpect(model().attribute("dough", ingredients.subList(0,2)))
                .andExpect(model().attribute("protein", ingredients.subList(2,4)))
                .andExpect(model().attribute("veggies", ingredients.subList(4,6)))
                .andExpect(model().attribute("sauce", ingredients.subList(6,8)));
    }

    public void processDesign() throws Exception{
        when(designRepository.save(design))
                .thenReturn(design);

        mockMvc.perform(post("/design")
       .content("name=Test+Pizza&ingredients=CIEN, KURC, PAPR")
        .contentType(MediaType.APPLICATION_FORM_URLENCODED))
                .andExpect(status().is3xxRedirection())
                .andExpect(header().stringValues("Location", "/orders/current"));
    }

}
