package com.ftn.sbnz.model.cocktail;

import org.kie.api.definition.type.Role;
import org.kie.api.definition.type.Timestamp;
import java.util.Date;

@Role(Role.Type.EVENT)
@Timestamp("executionTime")
public class OrderEvent {
    private Date executionTime;
    private Cocktail cocktail;

    public OrderEvent() {
    }

    public OrderEvent(Date executionTime, Cocktail cocktail) {
        this.executionTime = executionTime;
        this.cocktail = cocktail;
    }

    public Date getExecutionTime() {
        return executionTime;
    }

    public void setExecutionTime(Date executionTime) {
        this.executionTime = executionTime;
    }

    public Cocktail getCocktail() {
        return cocktail;
    }

    public void setCocktail(Cocktail cocktail) {
        this.cocktail = cocktail;
    }

}
