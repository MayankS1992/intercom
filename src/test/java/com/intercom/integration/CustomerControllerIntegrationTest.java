package com.intercom.integration;

import com.intercom.model.Customer;
import com.intercom.service.CustomerServiceImpl;
import com.intercom.service.files.IFiles;
import lombok.extern.slf4j.Slf4j;
import org.hamcrest.MatcherAssert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertTrue;

@ExtendWith(MockitoExtension.class)
@Slf4j
public class CustomerControllerIntegrationTest {
    @Mock
    private IFiles fileWriter;
    @InjectMocks
    private CustomerServiceImpl customerService;

    @BeforeEach
    void init() {
        this.customerService = new CustomerServiceImpl(fileWriter);
    }

    @Test
    public void getCustomerWithinRange() {
        Customer customer = new Customer();
        customer.setUser_id(1);
        customer.setLatitude(54.080556);
        customer.setLongitude(-6.361944);
        customer.setName("Rashid Khan");
        List<Customer> customers = new ArrayList<>();
        List<Customer> expectedCustomers = customers;
        customers.add(customer);
        Mockito.when(fileWriter.getCustomers()).thenReturn(customers);
        customers = this.customerService.getCustomersWithinRadius(100, "DUBLIN");
        MatcherAssert.assertThat(customers, equalTo(expectedCustomers));
    }

    @Test
    public void checkReturnIfNoCustomersFoundWithinRange() {
        Customer customer = new Customer();
        customer.setUser_id(1);
        customer.setLatitude(54.080556);
        customer.setLongitude(-6.361944);
        customer.setName("Rashid Khan");
        List<Customer> customers = new ArrayList<>();
        customers.add(customer);
        Mockito.when(fileWriter.getCustomers()).thenReturn(customers);
        customers = this.customerService.getCustomersWithinRadius(20, "DUBLIN");
        assertTrue(customers.isEmpty());
    }

}
