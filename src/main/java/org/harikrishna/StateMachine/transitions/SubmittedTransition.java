package org.harikrishna.StateMachine.transitions;

import lombok.extern.slf4j.Slf4j;
import org.harikrishna.StateMachine.*;

@TransitionData(
        event = OrderEvent.SUBMIT_STRING,
        from = OrderState.IN_PROGRESS_STRING
)
@Slf4j
public class SubmittedTransition implements ITransition {

    @Override
    public void process(CustomContext context) {
        log.info("Processing completed for event: {} from state: {} and move to new state: {}",
                context.getCausedEvent(), context.getFrom(), context.getTo());
        context.getOrder().setOrderState(OrderState.SUBMITTED);

        log.info("Order is SUBMITTED so Moving to SHIP_IN_PROGRESS state.");
    }
}
