package org.harikrishna.StateMachine;

import com.google.inject.Guice;
import com.google.inject.Injector;
import lombok.extern.slf4j.Slf4j;
import org.harikrishna.AppModule;

@Slf4j
public class Driver {

    public static void main(String[] args) throws Exception {
        new Driver().run();
    }   

    private void run() throws Exception {

        Injector injector = Guice.createInjector(new AppModule());
        Order order = new Order();
        order.setOrderId("Order1");
        order.setCustomerId("User1");
        order.setOrderState(OrderState.STARTED);

        OrderExecutionEngine executionEngine = injector.getInstance(OrderExecutionEngine.class);

        executionEngine.moveToInProgress(order);
        System.out.println("Order State after moveToInProgress transition: " + order.getOrderState());

        executionEngine.moveToSubmitted(order);
        System.out.println("Order State after moveToSubmitted transition: " + order.getOrderState());

        //Test to verify transition fails when we try to move from SHIP_IN_PROGRESS to COMPLETED state directly
        //As this is not a valid transition in OrderStateMachine defined.
        executionEngine.moveToUnReachableState(order);
    }
}
