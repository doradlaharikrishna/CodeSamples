package org.harikrishna.StateMachine.transitions;

import lombok.extern.slf4j.Slf4j;
import org.harikrishna.StateMachine.*;

@TransitionDefinition(
        event = OrderEvent.MOVE_TO_IN_PROGRESS_STRING,
        from = OrderState.STARTED_STRING
)
@Slf4j
public class MoveToInProgressTransition implements OrchestratorTransition {

    @Override
    public void process(CustomContext context) {
        log.info("Processing completed for event: {} from state: {} and move to new state: {}",
                context.getCausedEvent(), context.getFrom(), context.getTo());
        context.getOrder().setOrderState(OrderState.IN_PROGRESS);
    }
}
