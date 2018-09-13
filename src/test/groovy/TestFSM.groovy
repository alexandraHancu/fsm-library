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

import com.ullink.FiniteStateMachine
import com.ullink.State
import com.ullink.Transition
import com.ullink.ultools.log.Log
import spock.lang.Specification

import java.util.function.Consumer

class TestFSM extends Specification{
    def'should correctly set initial state'(){
        given:'initialized FSM'
        Consumer<Object> consumer = new Consumer<Object>() {
            @Override
            void accept(Object o) {

            }
        }
        State<Object> s1 = new State("s1")
        State<Object> s2 = new State("s2")
        State<Object> s3 = new State("s3",consumer)
        Transition<Object> t1 = new Transition("t1")
        Transition<Object> t2 = new Transition("t2")

        FiniteStateMachine<Object,Object> fsm = new FiniteStateMachine<>()
        fsm.addTransition(s1, t1, s2)
        fsm.addTransition(s2, t2, s3)

        when:'call method to automatically determine the initial state'
        fsm.setInitialState()

        then:'initial state correctly initialized'
        new State("s1") == fsm.getInitialState()
    }

    def'should correctly set final state'(){
        given:'initialized FSM'
        Consumer<Object> consumer = new Consumer<Object>() {
            @Override
            void accept(Object o) {

            }
        }
        State<Object> s1 = new State("s1")
        State<Object> s2 = new State("s2")
        State<Object> s3 = new State("s3",consumer)
        Transition<Object> t1 = new Transition("t1")
        Transition<Object> t2 = new Transition("t2")

        FiniteStateMachine<Object,Object> fsm = new FiniteStateMachine<>()
        fsm.addTransition(s1, t1, s2)
        fsm.addTransition(s2, t2, s3)


        when:'call method to automatically determine the final state'
        fsm.setFinalState()

        then:'initial state correctly initialized'
        s3 == fsm.getFinalState()
    }




    def'should call state\'s consumer apply function'(){
        given: 'initialized finite state machine'
        Consumer<Object> consumer = Mock(Consumer)
        State<Object> s1 = new State("s1",consumer)

        FiniteStateMachine<Object,Object> fsm = new FiniteStateMachine<>()

        Object testObj = new Object()

        when: 'call method react with valid state-transition input & not null state action param'
        fsm.doSideEffects(s1, testObj)

        then: 'consumer from statemethod apply was called'
        1*consumer.accept(testObj)
    }

    def'should call transition\'s consumer apply function'(){
        given: 'initialized finite state machine'
        Consumer<Object> consumer = Mock(Consumer)
        Transition<Object> t1 = new Transition("t1",consumer)

        FiniteStateMachine<Object,Object> fsm = new FiniteStateMachine<>()

        Object testObj = new Object()

        when: 'call method react with valid state-transition input & not null state action param'
        fsm.doSideEffects(t1, testObj)

        then: 'consumer from transition method apply was called'
        1*consumer.accept(testObj)
    }

    def 'should return false if search for a state not in fsm'(){
        given: 'initialized finite state machine'
        Consumer<Object> consumer = Mock(Consumer)
        State<Object> s1 = new State("s1")
        State<Object> s2 = new State("s2")
        State<Object> s3 = new State("s3")
        Transition<Object> t1 = new Transition("t1",consumer)

        FiniteStateMachine<Object,Object> fsm = new FiniteStateMachine<>()
        fsm.addTransition(s1, t1, s2)

        when: 'call isValidState method to check if given state is part of the fsm'
        boolean response = fsm.isValidState(s3)

        then: 'response is false'
        !response
    }

    def 'should return true if you search for a state in fsm even if is the final/ initial states'(){
        given: 'initialized finite state machine'
        Consumer<Object> consumer = Mock(Consumer)
        State<Object> s1 = new State("s1")
        State<Object> s2 = new State("s2")
        State<Object> s3 = new State("s3")
        Transition<Object> t1 = new Transition("t1",consumer)

        FiniteStateMachine<Object,Object> fsm = new FiniteStateMachine<>()
        fsm.addTransition(s1, t1, s2)
        fsm.addTransition(s2, t1, s3)

        expect: 'method isValidState returns true for any of the states in fsm'
        fsm.isValidState(s1)
        fsm.isValidState(s2)
        fsm.isValidState(s3)
    }

    def'should return correct next state after valid state-transition input and not do nothing'(){
        given: 'initialized fsm'
        Consumer<Object> stateConsumer = Mock(Consumer)
        Consumer<Object> transitionConsumer = Mock(Consumer)
        Object testObj = new Object()

        State<Object> s1 = new State("s1")
        State<Object> s2 = new State("s2", stateConsumer)
        Transition<Object> t1 = new Transition("t1", transitionConsumer)

        FiniteStateMachine<Object,Object> fsm = new FiniteStateMachine<>()
        fsm.addTransition(s1, t1, s2)

        when: 'call method react with valid state-transition input'
        State nextState = fsm.getNextState(s1, t1)

        then: 'new current state should be s2 consumers should never be applied'
        nextState == s2
        0*stateConsumer.accept(testObj)
        0*transitionConsumer.accept(testObj)
    }

    def'should return current state if given valid transition input and not do nothing'(){
        given: 'initialized fsm'
        Consumer<Object> stateConsumer = Mock(Consumer)
        Consumer<Object> transitionConsumer = Mock(Consumer)
        Log logger = Mock(Log)
        Object testObj = new Object()

        State<Object> s1 = new State("s1")
        State<Object> s2 = new State("s2", stateConsumer)
        Transition<Object> t1 = new Transition("t1", transitionConsumer)
        Transition<Object> t4 = new Transition("t4", transitionConsumer)

        FiniteStateMachine<Object,Object> fsm = new FiniteStateMachine<>(logger)
        fsm.addTransition(s1, t1, s2)

        when: 'call method react with valid state-transition input'
        State nextState = fsm.getNextState(s1, t4)

        then: 'new current state should be s2 consumers should never be applied'
        nextState == s1
        0*stateConsumer.accept(testObj)
        0*transitionConsumer.accept(testObj)
    }
}
