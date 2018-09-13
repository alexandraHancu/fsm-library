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
    private Log logger;
    private final State<T> error;

    private Map<StateTransitionTuple<T, K>, State<T>> transitionsMap;

    public FiniteStateMachine(Log logger)
    {
        this.logger = logger;
        transitionsMap = new HashMap<>();
        error = new State<>("Error");
    }

    public void addTransition(State<T> current, Transition<K> transition, State<T> next)
    {
        StateTransitionTuple<T, K> tuple = new StateTransitionTuple<>(current, transition);
        transitionsMap.put(tuple, next);
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


    public State<T> getNextState(@NonNull State<T> currentState, @NonNull Transition<K> input)
    {
        StateTransitionTuple<T, K> tuple = new StateTransitionTuple<>(currentState, input);
        State<T> state = transitionsMap.get(tuple);
        if (state == null){
            logger.info("invalid transition "+input+ " from current state "+ currentState+ ".");
            return error;
        }
        return state;
    }

    @Override
    public void doSideEffects(@NonNull State<T> currentState,@NonNull T stateActionParam)
    {
        currentState.apply(stateActionParam);
    }

    @Override
    public void doSideEffects(@NonNull Transition<K> transition,@NonNull K transitionActionParam)
    {
        transition.apply(transitionActionParam);
    }

    public boolean isValidState(State<T> s)
    {
        return getAllStates().contains(s);
    }
    @Override
    public State<T> getErrorState()
    {
        return error;
    }

}