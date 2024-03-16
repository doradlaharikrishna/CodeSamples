package org.harikrishna.StateMachine;

import com.google.common.collect.Sets;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import io.github.fsm.MachineBuilder;
import io.github.fsm.StateMachine;
import io.github.fsm.models.entities.Context;
import io.github.fsm.models.executors.EventAction;

@Singleton
public class OrderExecutionEngine {

    private static final StateMachine<Context> orderStateMachine = MachineBuilder.start(OrderState.STARTED)
            .onTransition(OrderEvent.MOVE_TO_IN_PROGRESS, OrderState.STARTED, OrderState.IN_PROGRESS)
            .onTransition(OrderEvent.SUBMIT, OrderState.IN_PROGRESS, OrderState.SUBMITTED)
            .onTransition(OrderEvent.SHIP_IN_PROGRESS, OrderState.SUBMITTED, OrderState.SHIP_IN_PROGRESS)
            .onTransition(OrderEvent.SHIP_COMPLETE, OrderState.SHIP_IN_PROGRESS, OrderState.SHIPPED)
            .onTransition(OrderEvent.CANCEL, Sets.newHashSet(OrderState.SUBMITTED, OrderState.SHIP_IN_PROGRESS, OrderState.SHIPPED), OrderState.CANCELLED)
            .onTransition(OrderEvent.DELIVER_COMPLETE, OrderState.SHIPPED, OrderState.DELIVERED)
            .onTransition(OrderEvent.START_RETURN, OrderState.DELIVERED, OrderState.RETURN_IN_PROGRESS)
            .onTransition(OrderEvent.RETURN_COMPLETED, OrderState.RETURN_IN_PROGRESS, OrderState.RETURNED)
            .onTransition(OrderEvent.COMPLETE, Sets.newHashSet(OrderState.DELIVERED, OrderState.RETURNED, OrderState.CANCELLED), OrderState.COMPLETED)
            .end(Sets.newHashSet(OrderState.COMPLETED));

    @Inject
    public OrderExecutionEngine(final TransitionRegistryManager transitionRegistryManager) throws Exception {
        if (orderStateMachine != null) {
            orderStateMachine.validate();
            this.bind(transitionRegistryManager);
        }
    }

    public void moveToInProgress(Order order) throws Exception {
        CustomContext context = getContext(order.getOrderState(), OrderState.IN_PROGRESS, OrderEvent.MOVE_TO_IN_PROGRESS);
        context.setOrder(order);
        this.fire(context);
    }

    public void moveToSubmitted(Order order) throws Exception {
        CustomContext context = getContext(order.getOrderState(), OrderState.SUBMITTED, OrderEvent.SUBMIT);
        context.setOrder(order);
        this.fire(context);

        // We can start ship in progress as no action pending after Submitted
        moveToShipInProgress(order);
    }

    public void moveToShipInProgress(Order order) throws Exception {
        CustomContext context = getContext(order.getOrderState(), OrderState.SHIP_IN_PROGRESS, OrderEvent.SHIP_IN_PROGRESS);
        context.setOrder(order);
        this.fire(context);
    }

    public void moveToUnReachableState(Order order) throws Exception {
        CustomContext context = getContext(order.getOrderState(), OrderState.COMPLETED, OrderEvent.COMPLETE);
        context.setOrder(order);
        this.fire(context);
    }

    public static CustomContext getContext(OrderState fromState, OrderState toState, OrderEvent event) {
        CustomContext context = new CustomContext();
        context.setFrom(fromState);
        context.setTo(toState);
        context.setCausedEvent(event);
        return context;
    }

    private void bind(final TransitionRegistryManager transitionRegistryManager) throws Exception {
        orderStateMachine.anyTransition((EventAction<CustomContext>) context -> {
            transitionRegistryManager.getProcessor(context).process(context);
        } );
    }

    public void fire(final CustomContext context) throws Exception {
        orderStateMachine.fire(context.getCausedEvent(), context);
    }
}
