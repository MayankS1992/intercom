package com.intercom.service;

import com.intercom.model.Customer;
import com.intercom.service.Writer.IFileWriter;
import com.intercom.service.factory.LocationFactory;
import com.intercom.service.strategy.ILocationStrategy;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Slf4j
public class CustomerServiceImpl implements CustomerService {

    private final IFileWriter fileIO;

    @Autowired
    public CustomerServiceImpl(IFileWriter fileIO) {
        this.fileIO = fileIO;
    }

    @SneakyThrows
    @Override
    public List<Customer> getCustomersWithinRadius(long distance, String location) {
        LocationFactory factory = new LocationFactory();
        ILocationStrategy personStrategy = factory.getStrategy(location);
        List<Double> intercomLocation = personStrategy.getCoOrdinates();
        List<Customer> customers = fileIO.getCustomers();
        customers = getCustomerBasedOnDistance(intercomLocation, customers, distance);
        if(!customers.isEmpty()){
            log.debug("Creating Customer records");
            customers.sort(Comparator.comparing(Customer::getUser_id));
            fileIO.write(customers);
        }
        return customers;
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
}
