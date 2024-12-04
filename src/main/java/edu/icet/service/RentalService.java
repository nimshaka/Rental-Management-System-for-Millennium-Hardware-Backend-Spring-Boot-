package edu.icet.service;

import edu.icet.dto.Rental;

import java.util.List;

public interface RentalService {
    List<Rental> getAll();
    void addRental(Rental rental);

    void updateRental(Rental rental);
    void returnRental(Integer rentalId);

    void deleteRental(Integer id);

    String calculateOverdue(Integer rentalId);

    boolean markAsReturned(Integer rentalId);
}
