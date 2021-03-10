package com.intercom.service.files;

import com.intercom.model.Customer;
import lombok.SneakyThrows;

import java.util.List;

public interface IFiles {

    /**
     * Writes the customer details to a file
     *
     * @param customerList
     */
    @SneakyThrows
    void write(List<Customer> customerList);

    /**
     * Retrives the customer details from the json Input file
     *
     * @return
     */
    List<Customer> getCustomers();
}
