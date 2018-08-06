/*************************************************************************
 * ULLINK CONFIDENTIAL INFORMATION
 * _______________________________
 *
 * All Rights Reserved.
 *
 * NOTICE: This file and its content are the property of Ullink. The
 * information included has been classified as Confidential and may
 * not be copied, modified, distributed, or otherwise disseminated, in
 * whole or part, without the express written permission of Ullink.
 ************************************************************************/

package com.ullink;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import io.reactivex.annotations.NonNull;
import com.ullink.ultools.log.Log;

public class FiniteStateMachine<T, K> implements FSM<T, K>
{
    private State<T> initialState;
    private State<T> finalState;
    private Log logger;

    private Map<StateTransitionTuple<T, K>, State<T>> transitionsMap;

    public FiniteStateMachine(Log logger)
    {
        this.logger = logger;
        transitionsMap = new HashMap<>();
    }

    public void addState(State<T> current, Transition<K> transition, State<T> next)
    {
        StateTransitionTuple<T, K> tuple = new StateTransitionTuple<>(current, transition);
        transitionsMap.put(tuple, next);
        setInitialState();
        setFinalState();
    }

    private void setInitialState()
    {
        List<State<T>> possibleInitialStates = getAllStates();
        possibleInitialStates.removeAll(transitionsMap.values());
        if (possibleInitialStates.size() > 1)
        {
            logger.warn("More than one state not reachable! Initial state not set!");
        }
        else
        {
            initialState = possibleInitialStates.get(0);
        }
    }

    private List<State<T>> getAllStates()
    {
        List<State<T>> states = transitionsMap.keySet().stream()
            .map(StateTransitionTuple::getState)
            .distinct()
            .collect(Collectors.toList());
        states.addAll(transitionsMap.values().stream().distinct().collect(Collectors.toList()));
        return states;
    }

    private void setFinalState()
    {
        List<State<T>> possibleInitialStates = getAllStates();
        possibleInitialStates.removeAll(
            transitionsMap.keySet().stream()
                .map(StateTransitionTuple::getState)
                .distinct()
                .collect(Collectors.toList()));
        if (possibleInitialStates.size() > 1)
        {
            logger.warn("More than one final state! Final state must be unique! Final state not set!");
        }
        else
        {
            finalState = possibleInitialStates.get(0);
        }
    }

    public State<T> getNextState(@NonNull State<T> currentState, @NonNull Transition<K> input)
    {
        return react(currentState, null, input, null);
    }

    public State<T> react(@NonNull State<T> currentState, T stateActionParam, @NonNull Transition<K> input, K transitionActionParam)
    {
        StateTransitionTuple<T, K> tuple = new StateTransitionTuple<>(currentState, input);
        if(currentState==finalState){
            logger.warn("Already in the final state. There is no way back. :))");
            return currentState;
        }
        State<T> state = transitionsMap.get(tuple);
        if (state == null){
            logger.info("invalid transition "+input+ " from current state "+ currentState+ ". Will keep current state.");
            return currentState;
        }
        if (transitionActionParam != null)
        {
            input.apply(transitionActionParam);
        }
        if (stateActionParam != null)
        {
            state.apply(stateActionParam);
        }
        return state;
    }

    public boolean isValidState(State<T> s)
    {
        return getAllStates().contains(s);
    }

    public State<T> getInitialState()
    {
        return initialState;
    }

    public State<T> getFinalState()
    {
        return finalState;
    }
}