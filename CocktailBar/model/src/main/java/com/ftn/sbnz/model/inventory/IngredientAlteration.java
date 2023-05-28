package com.ftn.sbnz.model.inventory;

import org.kie.api.definition.type.Role;
import org.kie.api.definition.type.Timestamp;

import java.util.Date;

@Role(Role.Type.EVENT)
@Timestamp("inserted")
public class IngredientAlteration {
    private Long ingredientId;
    private double amount;
    private String name;
    private Date inserted;

    public IngredientAlteration() {
        this.inserted=new Date();
        this.inserted.setTime(System.currentTimeMillis());
    }

    public IngredientAlteration(Long ingredientId, double amount, String name, Date inserted) {
        this.ingredientId = ingredientId;
        this.amount = amount;
        this.name = name;
        this.inserted = inserted;
    }

    public Date getInserted() {
        return inserted;
    }

    public void setInserted(Date inserted) {
        this.inserted = inserted;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getIngredientId() {
        return ingredientId;
    }

    public void setIngredientId(Long ingredientId) {
        this.ingredientId = ingredientId;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }


    @Override
    public String toString() {
        return "IngredientAlteration{" +
                "ingredientId=" + ingredientId +
                ", amount=" + amount +
                ", name='" + name + '\'' +
                '}';
    }
}
