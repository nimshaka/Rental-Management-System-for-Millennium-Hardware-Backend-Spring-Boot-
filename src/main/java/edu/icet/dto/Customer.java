package edu.icet.dto;

import lombok.Data;

@Data
public class Customer {
    private Integer id;
    private String customerName;
    private String city;
    private Integer contact;
}
