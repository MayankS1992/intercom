package com.intercom.controller;

import com.intercom.service.CustomerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

@RestController
@RequestMapping(value = "/customerInfo")
@Slf4j
public class CustomerController {

    private final CustomerService customerService;

    @Autowired
    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @RequestMapping(value = {"/distance/{distance}/{location}"}, method = GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getCustomerWithinRange(@PathVariable("distance") @Valid long distance,
                                                    @PathVariable("location") @Valid String location) {
        log.debug("Get Request: " + distance);
        customerService.getCustomersWithinRange(distance, location);
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }
}
