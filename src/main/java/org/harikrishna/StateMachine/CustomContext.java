package org.harikrishna.StateMachine;

import io.github.fsm.models.entities.Context;

public class CustomContext extends Context {
    private Order order;

    public Order getOrder() {
        return this.order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }
}
