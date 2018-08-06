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

public interface StateMapper<T>
{
    /**
     * method to wrap a business specific object into a state
     * @param obj business specific object
     * @return finite state machine state
     */
    State<T> mapObjectToState(T obj);

    /**
     * method to unwrap an object from a state
     * @param state finite state machine state
     * @return business specific object
     */
    T        mapStateToObject(State<T> state);

}
