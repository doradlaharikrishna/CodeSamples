package org.harikrishna.StateMachine.transitions;

import lombok.extern.slf4j.Slf4j;
import org.harikrishna.StateMachine.*;

@TransitionDefinition(
        event = OrderEvent.SHIP_IN_PROGRESS_STRING,
        from = OrderState.SUBMITTED_STRING
)
@Slf4j
public class ShipInProgressTransition implements OrchestratorTransition {

    @Override
    public void process(CustomContext context) {
        log.info("Processing completed for event: {} from state: {} and move to new state: {}",
                context.getCausedEvent(), context.getFrom(), context.getTo());
        context.getOrder().setOrderState(OrderState.SHIP_IN_PROGRESS);
    }
}
