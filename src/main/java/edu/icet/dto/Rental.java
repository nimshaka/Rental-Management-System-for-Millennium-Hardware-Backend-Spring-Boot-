package edu.icet.dto;

import lombok.Data;

import java.util.Date;

@Data
public class Rental {
    private Integer rentalId;
    private Item item;
    private Date issueDate;
    private Date returnDate;
    private Date dueDate;
    private boolean returned;
    private double fine;
}
