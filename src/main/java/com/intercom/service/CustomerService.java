package com.intercom.service;

import com.intercom.model.Customer;

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
     * @return
     */
    List<Customer> getCustomersWithinRadius(long distance, String location);
}
