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
import java.util.function.Consumer;

public class Unit<T>
{
    private String name;
    private Consumer<T> consumer = v -> {};

    public Unit(String name, Consumer<T> consumer){
        this.name = name;
        this.consumer = consumer;
    }

    public Unit(String name) {
        this.name = name;
    }

    /**
     * perform a action by calling the function specified by the consumer object
     * @param param to be used when calling the consumer's apply function
     */
    public void apply(T param){
        consumer.accept(param);
    }


    public String getName() {
        return name;
    }

    public Consumer<T> getConsumer() {
        return consumer;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof Unit<?>){
            Unit<?> transition = (Unit<?>) obj;
            return transition.getConsumer().equals(consumer) && transition.getName().equals(name);
        }
        return false;
    }

    @Override
    public int hashCode()
    {

        return Objects.hash(name, consumer);
    }
}
