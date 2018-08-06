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
import spock.lang.Specification

import java.util.function.Consumer

class TestState extends Specification{

    def 'should create a state'()
    {
        given:"a function and name"
        Consumer<Integer> consumer = new Consumer<Integer>() {
            @Override
            void accept(Integer integer) {
                println(++integer);
            }
        }
        String name = "Initial"

        when:"a state is created"
        State state = new State(name, consumer)

        then:"action object not null"
        state != null && state.name == name
    }

    def 'should do specified func for consumer'(){
        given:"a state with a function and name"
        Boolean flag= false;
        Consumer<Integer> consumer = Mock()
        State state = new State("Test", consumer)

        when:"the method accept is called"
        state.apply(2)

        then:"flag is true because "
        1*consumer.accept(2)
    }


}

