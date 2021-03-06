package com.intercom.service.strategy;

import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
public class DublinLocation implements ILocationStrategy {
    @Override
    public List<Double> getCoOrdinates() {
        return Stream.of(53.339428, -6.257664)
                .collect(Collectors.toList());
    }
}
