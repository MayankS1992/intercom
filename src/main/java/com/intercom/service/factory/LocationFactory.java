package com.intercom.service.factory;

import com.intercom.service.strategy.DublinLocation;
import com.intercom.service.strategy.ILocationStrategy;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.servlet.UnavailableException;

@Component
@Slf4j
public class LocationFactory {
    public ILocationStrategy getStrategy(String location) {
        if (location.equals("DUBLIN")) {
            return new DublinLocation();
        } else {
            try {
                throw new UnavailableException("Location Not Defined");
            } catch (UnavailableException e) {
                log.error("Location not defined: " + e);
            }
        }
        return null;
    }
}
