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

import java.util.Objects;

public class StateTransitionTuple<T, K>
{

    private State<T> state;
    private Transition<K> transition;

    public StateTransitionTuple(State<T> state, Transition<K> transition)
    {
        this.state = state;
        this.transition = transition;
    }

    public State<T> getState()
    {
        return state;
    }

    public Transition<K> getTransition()
    {
        return transition;
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o)
        {
            return true;
        }
        if (o == null || getClass() != o.getClass())
        {
            return false;
        }
        StateTransitionTuple<?, ?> tuple = (StateTransitionTuple<?, ?>) o;
        return Objects.equals(state, tuple.state) &&
            Objects.equals(transition, tuple.transition);
    }

    @Override
    public int hashCode()
    {

        return Objects.hash(state, transition);
    }
}
