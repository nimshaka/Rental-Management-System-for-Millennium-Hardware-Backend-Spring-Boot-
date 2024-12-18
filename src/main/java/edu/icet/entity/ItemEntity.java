package edu.icet.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity(name = "item")
public class ItemEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String itemName;

    @Temporal(TemporalType.DATE)
    @JsonFormat(pattern="yyyy-MM-dd")
    private Double price;
    private Integer qty;
    private boolean returned;

    public void rentItem() {
        if (this.qty > 0) {
            this.qty--;
        } else {
            throw new IllegalStateException("No Item available to rent.");
        }
    }


    public void returnItem() {
        this.qty++;
    }
}
