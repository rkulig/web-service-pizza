package pl.rkulig.pizza.model;


import lombok.Data;

@Data
public class Ingredient {

    private final String id;
    private final String name;
    private final Type type;

    public static enum Type{
        DOUGH, PROTEIN, VEGGIES, CHEESE, SAUCE
    }

}
