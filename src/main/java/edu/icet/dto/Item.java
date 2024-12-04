package edu.icet.dto;

import lombok.Data;

import java.util.Date;

@Data
public class Item {
    private Integer id;
    private String itemName;
    private Date addDate;
    private Integer price;
}
