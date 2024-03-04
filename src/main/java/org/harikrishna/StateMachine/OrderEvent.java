package org.harikrishna.StateMachine;

import io.github.fsm.models.entities.Event;

public enum OrderEvent implements Event {

    MOVE_TO_IN_PROGRESS,

    SUBMIT,

    CANCEL,

    SHIP_COMPLETE,

    DELIVER_COMPLETE,

    START_RETURN,

    RETURN_COMPLETED,

    COMPLETE;
}
