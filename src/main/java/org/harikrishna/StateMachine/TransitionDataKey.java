package org.harikrishna.StateMachine;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TransitionDataKey {
    private String event;
    private String from;
}
