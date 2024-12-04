package edu.icet.dto;

import lombok.Data;

import java.util.Date;

@Data
public class Item {
    private Integer id;
    private String itemName;
    private Double price;
    private Integer qty;
    private boolean returned;

    public void rentItem() {

        this.qty--;
    }

    public void returnItem() {

        this.qty++;
    }
}
