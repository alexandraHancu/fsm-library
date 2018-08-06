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

import com.ullink.Transition
import spock.lang.Specification

import java.util.function.Consumer

class TestTransition extends Specification{

    def 'should create transition'(){
        when:"transition is created"
        Transition t1 = new Transition('t1')

        then:"t1 object not null and has specified name"
        t1.getName() == 't1'
    }

    def 'should create transition that has a given consumer'(){
        given:"a consumer"
        Consumer<Object> consumer = Mock()

        when:'transition is created'
        Transition<Object> t2 = new Transition<>('t2',consumer)

        then:"t2 not null and has specified name"
        t2.getName() == 't2' && t2.getConsumer().equals(consumer)
    }

    def 'should return name when method getName is called'(){
        given:"a transition"
        String name = 't4'
        Transition<Object> t4 = new Transition<>(name)

        when: 'call get name method'
        String response = t4.getName()

        then: 'response is the correct name of the obj'
        response.equals(name)
    }

    def 'should apply transition\'s consumer'(){
        given:"a transition that has a specific consumer"
        Consumer<Object> consumer = Mock()
        Transition<Object> t3 = new Transition<>('t3', consumer)
        Object obj = new Object()

        when:'apply consumer function'
        t3.apply(obj)

        then:'consumer func was called'
        1*consumer.accept(obj)
    }

    def 'should be able to call apply on transition with no consumer'(){
        given:'transition obj with no consumer'
        Transition<Object> t5 = new Transition<>('t5')

        expect:'consumer object supports accept call'
        t5.apply(new Object())
    }

}
