package org.harikrishna.StateMachine;

import com.google.common.collect.Sets;
import com.google.inject.Inject;
import io.github.fsm.MachineBuilder;
import io.github.fsm.StateMachine;
import io.github.fsm.exceptions.StateNotFoundException;
import io.github.fsm.models.entities.Context;
import io.github.fsm.models.executors.EventAction;

import java.util.Optional;

import static org.reflections.Reflections.log;

public class OrderExecutionEngine {

    public static final StateMachine<Context> orderStateMachine = MachineBuilder.start(OrderState.STARTED)
            .onTransition(OrderEvent.MOVE_TO_IN_PROGRESS, OrderState.STARTED, OrderState.IN_PROGRESS)
            .onTransition(OrderEvent.SUBMIT, OrderState.IN_PROGRESS, OrderState.SUBMITTED)
            .onTransition(OrderEvent.SHIP_COMPLETE, OrderState.SUBMITTED, OrderState.SHIPPED)
            .onTransition(OrderEvent.CANCEL, Sets.newHashSet(OrderState.SUBMITTED, OrderState.SHIPPED), OrderState.CANCELLED)
            .onTransition(OrderEvent.DELIVER_COMPLETE, OrderState.SHIPPED, OrderState.DELIVERED)
            .onTransition(OrderEvent.START_RETURN, OrderState.DELIVERED, OrderState.RETURN_IN_PROGRESS)
            .onTransition(OrderEvent.RETURN_COMPLETED, OrderState.DELIVERED, OrderState.RETURNED)
            .onTransition(OrderEvent.COMPLETE, Sets.newHashSet(OrderState.DELIVERED, OrderState.RETURNED, OrderState.CANCELLED), OrderState.COMPLETED)
            .end(Sets.newHashSet(OrderState.COMPLETED));

    @Inject
    public OrderExecutionEngine(TransitionRegistryManager transitionRegistryManager) throws Exception {
        if (orderStateMachine != null) {
            orderStateMachine.validate();

        }
    }

    public boolean moveToInProgress(Order order) throws Exception {
        Context context = getContext(order.getOrderState(), OrderState.IN_PROGRESS, OrderEvent.MOVE_TO_IN_PROGRESS);
        orderStateMachine.fire(OrderEvent.MOVE_TO_IN_PROGRESS, context);
        return true;
    }

    private Context getContext(OrderState fromState, OrderState toState, OrderEvent event) {
        Context stateContext = new Context();
        stateContext.setFrom(fromState);
        stateContext.setTo(toState);
        stateContext.setCausedEvent(event);
        return stateContext;
    }

    private void bind(final TransitionRegistryManager transitionRegistryManager) throws Exception {
        orderStateMachine.anyTransition((EventAction<Context>) context -> {
            transitionRegistryManager.getProcessor(context).process(context);
        } );
    }

    public void fire(final Context context) throws Exception {
        orderStateMachine.fire(context.getCausedEvent(), context);
    }
}
