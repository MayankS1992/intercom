package com.intercom.service.factory;

import com.intercom.service.strategy.DublinLocation;
import com.intercom.service.strategy.ILocationStrategy;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.servlet.UnavailableException;

@Component
@Slf4j
public class LocationFactory {
    public ILocationStrategy getStrategy(String location) throws UnavailableException {
        if (location.equals("DUBLIN")) {
            return new DublinLocation();
        } else {
            throw new UnavailableException("Location Not Defined");
        }
    }
}
