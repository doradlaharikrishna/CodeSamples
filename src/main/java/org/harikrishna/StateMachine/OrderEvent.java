package org.harikrishna.StateMachine;

import io.github.fsm.models.entities.Event;
import lombok.Getter;

public enum OrderEvent implements Event {

    MOVE_TO_IN_PROGRESS("MOVE_TO_IN_PROGRESS"),

    SUBMIT("SUBMIT"),

    SHIP_IN_PROGRESS("SHIP_IN_PROGRESS"),

    CANCEL("CANCEL"),

    SHIP_COMPLETE("SHIP_COMPLETE"),

    DELIVER_COMPLETE("DELIVER_COMPLETE"),

    START_RETURN("START_RETURN"),

    RETURN_COMPLETED("RETURN_COMPLETED"),

    COMPLETE("COMPLETE");

    @Getter
    private final String eventName;

    OrderEvent(final String eventName) {
        this.eventName = eventName;
    }

    public static final String MOVE_TO_IN_PROGRESS_STRING = "MOVE_TO_IN_PROGRESS";
    public static final String SUBMIT_STRING = "SUBMIT";
    public static final String SHIP_IN_PROGRESS_STRING = "SHIP_IN_PROGRESS";
    public static final String CANCEL_STRING = "CANCEL";
    public static final String SHIP_COMPLETE_STRING = "SHIP_COMPLETE";
    public static final String DELIVER_COMPLETE_STRING = "DELIVER_COMPLETE";
    public static final String START_RETURN_STRING = "START_RETURN";
    public static final String RETURN_COMPLETED_STRING = "RETURN_COMPLETED";
    public static final String COMPLETE_STRING = "COMPLETE";
}
