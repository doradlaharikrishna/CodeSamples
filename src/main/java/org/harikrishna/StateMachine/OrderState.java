package org.harikrishna.StateMachine;

import io.github.fsm.models.entities.State;

public enum OrderState implements State {

    STARTED,

    IN_PROGRESS,

    SUBMITTED,

    SHIPPED,

    DELIVERED,

    RETURN_IN_PROGRESS,

    RETURNED,

    CANCELLED,

    COMPLETED;
}
