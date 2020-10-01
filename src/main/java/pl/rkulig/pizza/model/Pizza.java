package pl.rkulig.pizza.model;


import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@Entity
public class Pizza {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private Date createdAt;

    @Size(min=3 , message = "Nazwa musi się składać przynamniej z trzech znaków")
    @NotNull
    private String pizzaName;

    @ManyToMany(targetEntity = Ingredient.class)
    @Size(min = 1 , message = "Musisz wybrać minimum jeden składnik")
    private List<Ingredient> ingredients = new ArrayList<>();

    @PrePersist
    void createdAt() {
        this.createdAt = new Date();
    }
}
