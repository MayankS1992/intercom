package com.intercom.service.Writer;

import com.intercom.model.Customer;
import lombok.SneakyThrows;

import java.util.List;

public interface IFileWriter {
    @SneakyThrows
    void write(List<Customer> customerList);

    List<Customer> getCustomers();
}
