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
@Entity(name = "rental")
public class RentalEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer rentalId;

    @ManyToOne
    @JoinColumn(name = "id", referencedColumnName = "id")
    private ItemEntity item;

    @Temporal(TemporalType.DATE)
    @JsonFormat(pattern="yyyy-MM-dd")
    private Date issueDate;

    @Temporal(TemporalType.DATE)
    @JsonFormat(pattern="yyyy-MM-dd")
    private Date returnDate;

    @Temporal(TemporalType.DATE)
    private Date dueDate;

    private double fine;
    private boolean isReturned;
}
