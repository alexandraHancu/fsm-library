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

public class State<T> extends Unit<T>{


    public State(String name, Consumer<T> consumer) {
        super(name,consumer);
    }

    public State(String name)
    {
        super(name);
    }


}
