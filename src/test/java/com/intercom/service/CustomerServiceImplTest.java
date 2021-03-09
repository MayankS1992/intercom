package com.intercom.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.intercom.model.Customer;
import com.intercom.service.Writer.FileWriter;
import com.intercom.service.Writer.IFileWriter;
import com.intercom.service.factory.LocationFactory;
import com.intercom.service.strategy.ILocationStrategy;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.hamcrest.MatcherAssert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import javax.servlet.UnavailableException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;

@RunWith(MockitoJUnitRunner.class)
@Slf4j
class CustomerServiceImplTest {

    @Mock
    private ObjectMapper objectMapper = new ObjectMapper();
    @Mock
    private IFileWriter fileWriter;
    @Mock
    private ILocationStrategy locationStrategy;
    @InjectMocks
    private CustomerServiceImpl customerService;

    @BeforeEach
    void init() {
        this.fileWriter = new FileWriter(objectMapper);
        this.fileWriter = mock(FileWriter.class);
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
        customers = this.customerService.getCustomersWithinRange(100, "DUBLIN");
        MatcherAssert.assertThat(customers, equalTo(expectedCustomers));
    }

    @Test
    void getGPScoOrdinates() throws UnavailableException {
        LocationFactory factory = new LocationFactory();
        locationStrategy = factory.getStrategy("DUBLIN");
        locationStrategy = mock(ILocationStrategy.class);
        Mockito.when(locationStrategy.getCoOrdinates()).thenReturn(Stream.of(52.339428, -6.257664)
                .collect(Collectors.toList()));
        List<Double> actualLocation = locationStrategy.getCoOrdinates();
        List<Double> expectedLocation = Stream.of(52.339428, -6.257664)
                .collect(Collectors.toList());
        MatcherAssert.assertThat(actualLocation, equalTo(expectedLocation));
    }

    @Test
    public void testUnavailableLocationException() {
        assertThrows(UnavailableException.class, () -> {
            LocationFactory factory = new LocationFactory();
            locationStrategy = mock(ILocationStrategy.class);
            locationStrategy = factory.getStrategy("LIMERICK");
        });
    }

    // demonstrates of the spy function
    @Test
    public void testMockitoThrows() {
        Properties properties = new Properties();
        Properties spyProperties = spy(properties);
        Mockito.doReturn(Stream.of(53.339428, -6.257664)
                .collect(Collectors.toList())).when(spyProperties).get("DUBLIN");
        List<Double> value = (List<Double>) spyProperties.get("DUBLIN");

        assertEquals(Stream.of(53.339428, -6.257664)
                .collect(Collectors.toList()), value);
    }

}