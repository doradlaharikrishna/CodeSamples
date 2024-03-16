package org.harikrishna.StateMachine;

import io.github.fsm.models.entities.Context;

public interface OrchestratorTransition {
    void process(CustomContext context);
}
