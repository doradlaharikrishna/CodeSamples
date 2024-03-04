package org.harikrishna.StateMachine;

import com.google.common.collect.Sets;
import io.github.fsm.MachineBuilder;
import io.github.fsm.StateMachine;
import io.github.fsm.models.entities.Context;

public class OrderStateMachine {

    private static final StateMachine<Context> orderStateMachine = MachineBuilder.start(OrderState.STARTED)
            .onTransition(OrderEvent.MOVE_TO_IN_PROGRESS, OrderState.STARTED, OrderState.IN_PROGRESS)
            .onTransition(OrderEvent.SUBMIT, OrderState.IN_PROGRESS, OrderState.SUBMITTED)
            .onTransition(OrderEvent.SHIP_COMPLETE, OrderState.SUBMITTED, OrderState.SHIPPED)
            .onTransition(OrderEvent.CANCEL, Sets.newHashSet(OrderState.SUBMITTED, OrderState.SHIPPED), OrderState.CANCELLED)
            .onTransition(OrderEvent.DELIVER_COMPLETE, OrderState.SHIPPED, OrderState.DELIVERED)
            .onTransition(OrderEvent.START_RETURN, OrderState.DELIVERED, OrderState.RETURN_IN_PROGRESS)
            .onTransition(OrderEvent.RETURN_COMPLETED, OrderState.DELIVERED, OrderState.RETURNED)
            .onTransition(OrderEvent.COMPLETE, Sets.newHashSet(OrderState.DELIVERED, OrderState.RETURNED, OrderState.CANCELLED), OrderState.COMPLETED)
            .end(Sets.newHashSet(OrderState.COMPLETED));


}
