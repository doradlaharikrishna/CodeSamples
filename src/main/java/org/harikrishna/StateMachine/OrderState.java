package org.harikrishna.StateMachine;

import io.github.fsm.models.entities.State;
import lombok.Getter;

public enum OrderState implements State {

    STARTED("STARTED"),

    IN_PROGRESS("IN_PROGRESS"),

    SUBMITTED("SUBMITTED"),

    SHIP_IN_PROGRESS("SHIP_IN_PROGRESS"),

    SHIPPED("SHIPPED"),

    DELIVERED("DELIVERED"),

    RETURN_IN_PROGRESS("RETURN_IN_PROGRESS"),

    RETURNED("RETURNED"),

    CANCELLED("CANCELLED"),

    COMPLETED("COMPLETED");

    @Getter
    private final String stateName;

    OrderState(String stateName) {
        this.stateName = stateName;
    }

    public static final String STARTED_STRING = "STARTED";
    public static final String IN_PROGRESS_STRING = "IN_PROGRESS";
    public static final String SUBMITTED_STRING = "SUBMITTED";
    public static final String SHIP_IN_PROGRESS_STRING = "SHIP_IN_PROGRESS";
    public static final String SHIPPED_STRING = "SHIPPED";
    public static final String DELIVERED_STRING = "DELIVERED";
    public static final String RETURN_IN_PROGRESS_STRING = "RETURN_IN_PROGRESS";
    public static final String RETURNED_STRING = "RETURNED";
    public static final String CANCELLED_STRING = "CANCELLED";
    public static final String COMPLETED_STRING = "COMPLETED";
}
