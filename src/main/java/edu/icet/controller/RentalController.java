package edu.icet.controller;

import edu.icet.dto.Rental;
import edu.icet.service.RentalService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/rent")
@CrossOrigin
public class RentalController {

    private final RentalService rentalService;


    @GetMapping("/get-rent")
    public List<Rental> getAllRental() {
        return rentalService.getAll();

    }


    @PostMapping("/add-rent")
    @ResponseStatus(HttpStatus.CREATED)
    public void addRent(@RequestBody Rental rental) {
        rentalService.addRental(rental);
    }



    @PutMapping("/update-rent")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void updateRent(@RequestBody Rental rental) {
        rentalService.updateRental(rental);

    }
    @DeleteMapping("/delete-by-id/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteRentById(@PathVariable Integer id) {
        rentalService.deleteRental(id);
    }

    @PutMapping("/rent/mark-returned/{id}")
    public ResponseEntity<Void> markReturned(@PathVariable Integer id) {
        boolean isUpdated = rentalService.markAsReturned(id);
        if (isUpdated) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }



    @GetMapping("/calculate-overdue/{rentId}")
    public ResponseEntity<String> calculateOverdue(@PathVariable Integer rentalId) {
        String overdueStatus = rentalService.calculateOverdue(rentalId);
        if ("Rent record not found".equals(overdueStatus)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(overdueStatus);
        }
        return ResponseEntity.status(HttpStatus.OK).body(overdueStatus);


    }
}
