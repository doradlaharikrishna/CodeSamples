package org.harikrishna.StateMachine;

import com.google.common.collect.Maps;
import com.google.inject.Inject;
import com.google.inject.Injector;
import com.google.inject.Singleton;
import io.github.fsm.models.entities.Context;
import lombok.extern.slf4j.Slf4j;
import org.reflections.Reflections;

import java.util.Arrays;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

@Slf4j
@Singleton
public class TransitionRegistryManager {
    private Map<TransitionRegistryKey, OrchestratorTransition> processors = Maps.newConcurrentMap();

    @Inject
    public TransitionRegistryManager(Injector injector) {
        Reflections reflections = new Reflections("org.harikrishna");
        final Set<Class<?>> annotatedClasses = reflections.getTypesAnnotatedWith(TransitionDefinition.class);

        annotatedClasses.forEach(annotatedClass -> {
            if (OrchestratorTransition.class.isAssignableFrom(annotatedClass)) {
                TransitionDefinition transitionDefinition = annotatedClass.getAnnotation(TransitionDefinition.class);
                final OrchestratorTransition orchestratorTransition = OrchestratorTransition.class.cast(injector.getInstance(annotatedClass));
                updateProcessorsMap(transitionDefinition, orchestratorTransition);
            }
        });
    }

    private void updateProcessorsMap(TransitionDefinition transitionDefinition,
                                     OrchestratorTransition orchestratorTransition) {
        Arrays.stream(transitionDefinition.from()).forEach(from -> {
            processors.put(TransitionRegistryKey.builder().from(from).event(transitionDefinition.event()).build(), orchestratorTransition);
        });
    }

    public OrchestratorTransition getProcessor(Context context) {
        return processors.get(TransitionRegistryKey.builder().event(context.getCausedEvent().name()).from(context.getFrom().name()).build());
    }
}
