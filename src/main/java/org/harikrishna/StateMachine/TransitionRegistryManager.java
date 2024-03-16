package org.harikrishna.StateMachine;

import com.google.common.collect.Maps;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import io.github.fsm.models.entities.Context;
import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;
import java.util.Map;
import java.util.Set;

@Slf4j
@Singleton
public class TransitionRegistryManager {
    private Map<TransitionRegistryKey, OrchestratorTransition> processors = Maps.newConcurrentMap();

    @Inject
    public TransitionRegistryManager(Set<OrchestratorTransition> transitions) {
        try {
            transitions.forEach(transition -> {
                TransitionDefinition transitionDefinition = transition.getClass().getAnnotation(TransitionDefinition.class);
                if (transitionDefinition != null) {
                    updateProcessorsMap(transitionDefinition, transition);
                }
            });
        } catch (Exception e) {
            log.info("exception while initializing transition registry manager: ", e.getCause());
        }
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
