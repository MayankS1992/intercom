package com.intercom.service;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Charsets;
import com.google.common.io.Resources;
import com.intercom.model.Customer;
import com.intercom.service.Writer.IFileWriter;
import com.intercom.service.factory.LocationFactory;
import com.intercom.service.strategy.ILocationStrategy;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Slf4j
public class CustomerServiceImpl implements CustomerService {

    private static final String CUSTOMER_RESOURCE_FILE_NAME = "customer-info-data.json";
    private final ObjectMapper objectMapper;
    private final IFileWriter fileWriter;

    @Autowired
    public CustomerServiceImpl(ObjectMapper objectMapper, IFileWriter fileWriter) {
        this.objectMapper = objectMapper;
        this.fileWriter = fileWriter;
    }

    @Override
    public void getCustomersWithinRange(long distance, String location) {
        List<Double> intercomLocation = getGPScoOrdinates(location);
        List<Customer> customers = getCustomers();
        assert customers != null;
        fileWriter.write(getCustomerBasedOnDistance(intercomLocation, customers, distance));
    }

    private List<Customer> getCustomerBasedOnDistance(List<Double> intercomLocation, List<Customer> customers, long radius) {
        Map<Customer, Integer> customerMap = customers
                .stream()
                .map(customer -> getDistance(customer, intercomLocation))
                .collect(Collectors.toMap(customer -> customer, Customer::getDistance));

        return customerMap
                .entrySet()
                .stream()
                .filter(entry -> entry.getValue() < radius)
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());
    }

    private Customer getDistance(Customer customer, List<Double> intercomLocation) {

        final double meanEarthRadius = 6371.00;
        int distance;
        double lat1_rad, long1_rad, lat2_rad, long2_rad, x1, z1, x2, z2, x3, a;

        lat1_rad = Math.toRadians(customer.getLatitude());
        long1_rad = Math.toRadians(customer.getLongitude());
        lat2_rad = Math.toRadians(intercomLocation.get(0));
        long2_rad = Math.toRadians(intercomLocation.get(1));

        x1 = Math.sin(lat1_rad);
        z1 = Math.cos(lat1_rad);
        x2 = Math.sin(lat2_rad);
        z2 = Math.cos(lat2_rad);

        x3 = Math.cos(long2_rad - long1_rad);

        a = (x1 * x2) + (z1 * z2 * x3);
        distance = (int) ((meanEarthRadius * Math.acos(a)) + 0.5);
        customer.setDistance(distance);
        return customer;
    }

    private List<Customer> getCustomers() {
        log.info("Getting Customers");
        try {
            URL url = Resources.getResource(CUSTOMER_RESOURCE_FILE_NAME);
            JavaType type = objectMapper.getTypeFactory().constructCollectionType(List.class, Customer.class);
            return objectMapper.readValue(Resources.toString(url, Charsets.UTF_8), type);
        } catch (IOException e) {
            log.error("Failed to read customers entries from file={}", CUSTOMER_RESOURCE_FILE_NAME, e);
        }
        return null;
    }

    @Override
    public List<Double> getGPScoOrdinates(String location) {
        LocationFactory factory = new LocationFactory();
        ILocationStrategy personStrategy = factory.getStrategy(location);
        return personStrategy.getCoOrdinates();
    }
}
