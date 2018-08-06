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

public interface FSM<T, K>
{
    void addState(State<T> current, Transition<K>input , State<T> next);

    /**
     * method that returns next state WITHOUT performing any action or causing any side-effects
     * @param current - current state of machine
     * @param input - transition
     * @return possible next state, given current state and transition
     */
    State<T> getNextState(State<T> current, Transition<K>input);

    /**
     * Use this method to get the next state AND perform
     * @param currentState
     * @param stateActionParam object to be used when performing the specified side effect on NEXT state
     * @param input transition
     * @param transitionActionParam object to be used when performing the specified side effect on transition
     * @return next state specified by the current state and transition
     */
    State<T> react(State<T> currentState, T stateActionParam, Transition<K> input, K transitionActionParam);

    /**
     * use this method to check if a state belongs to the fsm
     * @param s state to be verified
     * @return boolean value that state if the state apears in the fsm or not.
     */
    boolean isValidState(State<T> s);


    State<T> getInitialState();
    State<T> getFinalState();
}
