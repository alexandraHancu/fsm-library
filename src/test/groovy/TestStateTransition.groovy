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

import com.ullink.State
import com.ullink.StateTransitionTuple
import com.ullink.Transition
import spock.lang.Specification

import java.util.function.Consumer

class TestStateTransition extends Specification{

    def 'should create touple from existing state & transition'(){
        given: 'state and trasition objects'
        Consumer<Object> consumer = new Consumer<Object>() {
            @Override
            void accept(Object o) {

            }
        }
        State<Object> state = new State<>('s',consumer)
        Transition<Object> transition = new Transition<>('t')

        when: 'create touple'
        StateTransitionTuple<Object, Object> touple = new StateTransitionTuple<>(state, transition)

        then: 'not null object'
        touple!=null
    }

    def 'should return valid state and transition after init'(){
        given: 'state and trasition objects'
        Consumer<Object> consumer = new Consumer<Object>() {
            @Override
            void accept(Object o) {

            }
        }
        State<Object> state = new State<>('s',consumer)
        Transition<Object> transition = new Transition<>('t')
        StateTransitionTuple<Object, Object> touple = new StateTransitionTuple<>(state, transition)

        when: 'call getters for state and transition'
        State s = touple.getState()
        Transition t = touple.getTransition()

        then: 's & t valid objects'
        s.equals(state) && t.equals(transition)
    }

    def 'compairing an object to itself should return true'(){
        given: 'a state transition object'
        State<Object> state = new State<>('s')
        Transition<Object> transition = new Transition<>('t')
        StateTransitionTuple<Object, Object> touple = new StateTransitionTuple<>(state, transition)

        when: 'equals method is called'
        boolean response = touple.equals(touple)

        then: 'response should be true'
        response
    }

    def 'compairing an object to null should return false'(){
        given: 'a state transition object'
        State<Object> state = new State<>('s')
        Transition<Object> transition = new Transition<>('t')
        StateTransitionTuple<Object, Object> touple = new StateTransitionTuple<>(state, transition)

        when: 'equals method is called'
        boolean response = touple.equals(null)

        then: 'response should be false'
        !response
    }
}
