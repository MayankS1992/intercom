package com.intercom.service.strategy;

import java.util.List;

@FunctionalInterface
public interface ILocationStrategy {

    /**
     * Get the co-ordinate details of an Intercom Office
     *
     * @return
     */
    List<Double> getCoOrdinates();
}
