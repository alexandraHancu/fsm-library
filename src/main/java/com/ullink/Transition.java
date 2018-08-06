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

import java.util.function.Consumer;

public class Transition<T> extends Unit<T>
{

    public Transition(String name, Consumer<T> consumer){
        super(name,consumer);
    }

    public Transition(String name) {
        super(name);
    }


}
