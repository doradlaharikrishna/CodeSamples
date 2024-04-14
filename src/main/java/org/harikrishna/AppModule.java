package org.harikrishna;

import com.google.inject.AbstractModule;
import javax.inject.Singleton;

import com.google.inject.multibindings.Multibinder;
import org.harikrishna.StateMachine.ITransition;
import org.harikrishna.StateMachine.TransitionDataManager;
import org.harikrishna.StateMachine.transitions.MoveToInProgressTransition;
import org.harikrishna.StateMachine.transitions.ShipInProgressTransition;
import org.harikrishna.StateMachine.transitions.SubmittedTransition;

public class AppModule extends AbstractModule {

    @Override
    protected void configure() {
        bind(TransitionDataManager.class).in(Singleton.class);

        // Creating a Multibinder for OrchestratorTransition to bind multiple implementations
        Multibinder<ITransition> transitionBinder = Multibinder.newSetBinder(binder(), ITransition.class);
        transitionBinder.addBinding().to(MoveToInProgressTransition.class).in(Singleton.class);
        transitionBinder.addBinding().to(SubmittedTransition.class).in(Singleton.class);
        transitionBinder.addBinding().to(ShipInProgressTransition.class).in(Singleton.class);
    }
}

