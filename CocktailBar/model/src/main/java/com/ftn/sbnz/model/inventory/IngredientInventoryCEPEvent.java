package com.ftn.sbnz.model.inventory;

import org.kie.api.definition.type.Role;
import org.kie.api.definition.type.Timestamp;

import java.util.Date;


@Role(Role.Type.EVENT)
@Timestamp("inserted")
public class IngredientInventoryCEPEvent {
   private Date inserted;
   private Long ingredientInventoryId;
   private double amount;
   private Long id;

    public IngredientInventoryCEPEvent() {
    }

    public IngredientInventoryCEPEvent(Date inserted, Long ingredientInventoryId, double amount, Long id) {
        this.inserted = inserted;
        this.ingredientInventoryId = ingredientInventoryId;
        this.amount = amount;
        this.id = id;
    }

    public Date getInserted() {
        return inserted;
    }

    public void setInserted(Date inserted) {
        this.inserted = inserted;
    }

    public Long getIngredientInventoryId() {
        return ingredientInventoryId;
    }

    public void setIngredientInventoryId(Long ingredientInventoryId) {
        this.ingredientInventoryId = ingredientInventoryId;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void updateTime(){
        this.inserted=new Date();
        this.inserted.setTime(System.currentTimeMillis());
    }
}
