package com.intercom.model;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class Customer {
    private long user_id;
    private String name;
    private Double latitude;
    private Double longitude;
    private Integer distance;
}
