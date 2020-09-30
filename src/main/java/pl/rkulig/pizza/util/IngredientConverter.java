//package pl.rkulig.pizza.util;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.core.convert.converter.Converter;
//import org.springframework.stereotype.Component;
//import pl.rkulig.pizza.data.IngredientRepository;
//import pl.rkulig.pizza.model.Ingredient;
//
//import java.util.ArrayList;
//import java.util.List;
//
//@Component
//public class IngredientConverter implements Converter<String, Ingredient> {
//    private final IngredientRepository ingredientRepo;
//    private final List<Ingredient> ingredients = new ArrayList<>();
//
//    @Autowired
//    public IngredientConverter(IngredientRepository ingredientRepo) {
//        this.ingredientRepo = ingredientRepo;
//
//        ingredientRepo.findAll().forEach(ingredients::add);
//    }
//
//    @Override
//    public Ingredient convert(String ingredientId) {
//        return ingredients
//                .stream().filter( i -> i.getId().equals(ingredientId))
//                .findFirst()
//                .orElseThrow(() -> new IllegalArgumentException("Ingredient with ID '" + ingredientId + "' not found!"));
//    }
//}