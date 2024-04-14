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
public class TransitionDataManager {
    private Map<TransitionDataKey, ITransition> processors = Maps.newHashMap();

    @Inject
    public TransitionDataManager(Set<ITransition> transitions) {
        try {
            transitions.forEach(transition -> {
                TransitionData transitionData = transition.getClass().getAnnotation(TransitionData.class);
                if (transitionData != null) {
                    updateProcessorsMap(transitionData, transition);
                }
            });
        } catch (Exception e) {
            log.info("Exception while initializing transition data manager: ", e.getCause());
        }
    }

    private void updateProcessorsMap(TransitionData transitionData,
                                     ITransition iTransition) {
        Arrays.stream(transitionData.from()).forEach(from -> {
            processors.put(TransitionDataKey.builder().from(from).event(transitionData.event()).build(), iTransition);
        });
    }

    public ITransition getProcessor(Context context) {
        return processors.get(TransitionDataKey.builder().event(context.getCausedEvent().name()).from(context.getFrom().name()).build());
    }
}
