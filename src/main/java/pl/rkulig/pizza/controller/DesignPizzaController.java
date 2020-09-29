package pl.rkulig.pizza.controller;


import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import pl.rkulig.pizza.model.Ingredient;
import pl.rkulig.pizza.model.Ingredient.Type;
import pl.rkulig.pizza.model.Pizza;

import javax.persistence.criteria.CriteriaBuilder;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Controller
@RequestMapping("/design")
public class DesignPizzaController {

    @ModelAttribute
    public void addIngredientToModel(Model model) {
        List<Ingredient> ingredients = Arrays.asList(
                new Ingredient("CIEN", "cienkie", Type.DOUGH),
                new Ingredient("GRUB", "grube", Type.DOUGH),
                new Ingredient("KURC", "kurczak", Type.PROTEIN),
                new Ingredient("SALA", "salami", Type.PROTEIN),
                new Ingredient("PAPR", "papryka", Type.VEGGIES),
                new Ingredient("PIEC", "pieczarki", Type.VEGGIES),
                new Ingredient("KETC", "ketchup", Type.SAUCE),
                new Ingredient("CZOS", "czosnkowy", Type.SAUCE)
        );

        Type[] types = Ingredient.Type.values();
        for (Type type : types) {
            model.addAttribute(type.toString().toLowerCase(), filterByType(ingredients,type));
        }
    }
     @GetMapping
    public String showDesignform(Model model){
        model.addAttribute("design", new Pizza());
        return "design";
    }
@PostMapping
public String processDesign(Pizza design){
        log.info("Przetwarzanie projektu pizza:" + design);
        return "redirect:/orders/current";
}

    private List<Ingredient> filterByType(List<Ingredient> ingredients, Type type) {
        return ingredients.stream().filter(x -> x.getType().equals(type)).collect(Collectors.toList());
    }
}
