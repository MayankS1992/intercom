package com.intercom.service;

import java.util.List;

/**
 * @Author: Mayank Srivastava
 */
public interface CustomerService {

    /**
     * Gets the customer within the provided Range and Location
     *
     * @param distance Max distance between Intercom office and customers
     * @param location Intercom Office Location
     */
    void getCustomersWithinRange(long distance, String location);

    /**
     * Gets the GPS Co-ordinates
     *
     * @param location Intercom Office (Name of the City)
     * @return The latitude and longitude details of the office
     */
    List<Double> getGPScoOrdinates(String location);
}
