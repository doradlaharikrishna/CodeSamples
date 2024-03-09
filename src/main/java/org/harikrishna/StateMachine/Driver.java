package org.harikrishna.StateMachine;

import com.google.inject.Inject;

public class Driver {

    @Inject
    private static TransitionRegistryManager transitionRegistryManager;

    public static void main(String[] args) throws Exception {
        Order order = new Order();
        order.setOrderId("Order1");
        order.setCustomerId("User1");
        order.setOrderState(OrderState.STARTED);

        OrderExecutionEngine executionEngine = new OrderExecutionEngine(transitionRegistryManager);
        executionEngine.moveToInProgress(order);

        System.out.println("Order State after moveToInProgress action: " + order.getOrderState());
    }
}
