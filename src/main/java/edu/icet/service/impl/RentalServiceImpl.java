package edu.icet.service.impl;

import edu.icet.dto.Rental;
import edu.icet.entity.ItemEntity;
import edu.icet.entity.RentalEntity;
import edu.icet.repository.ItemRepository;
import edu.icet.repository.RentalRepository;
import edu.icet.service.RentalService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@CrossOrigin
@Service
@RequiredArgsConstructor
public class RentalServiceImpl implements RentalService {
    private final ModelMapper mapper;
    private final ItemRepository repository;
    private final RentalRepository rentalRepository;
    @Override
    public List<Rental> getAll() {
        return rentalRepository.findAll().stream()
                .map(entity -> mapper.map(entity, Rental.class))
                .collect(Collectors.toList());
    }

    @Override
    public void addRental(Rental rental) {
        ItemEntity itemEntity = repository.findById(rental.getItem().getId())
                .orElseThrow(() -> new IllegalArgumentException("Item not found with ID: " + rental.getItem().getId()));

        if (itemEntity.getQty() > 0) {
            itemEntity.setQty(itemEntity.getQty() - 1);
            repository.save(itemEntity);

            RentalEntity rentalEntity = mapper.map(rental, RentalEntity.class);
            rentalEntity.setItem(itemEntity);
            rentalRepository.save(rentalEntity);
        } else {
            throw new IllegalStateException("Item is out of stock.");
        }
    }

    @Override
    public void updateRental(Rental rental) {
        if (rental == null) {
            throw new IllegalArgumentException("Rent object cannot be null");
        }

        RentalEntity existingBorrowEntity = rentalRepository.findById(rental.getRentalId())
                .orElseThrow(() -> new IllegalArgumentException("Rent record not found with ID: " + rental.getRentalId()));

        if (rental.getIssueDate() != null) {
            existingBorrowEntity.setIssueDate(rental.getIssueDate());
        }

        if (rental.getReturnDate() != null) {
            existingBorrowEntity.setReturnDate(rental.getReturnDate());
        }

        if (rental.getItem() != null) {
            ItemEntity itemEntity = repository.findById(rental.getItem().getId())
                    .orElseThrow(() -> new IllegalArgumentException("Item not found with ID: " + rental.getItem().getId()));
            existingBorrowEntity.setItem(itemEntity);
        }

        rentalRepository.save(existingBorrowEntity);

    }

    @Override
    public void returnRental(Integer rentalId) {
        RentalEntity rentalEntity = rentalRepository.findById(rentalId)
                .orElseThrow(() -> new IllegalArgumentException("Rent record not found with ID: " + rentalId));

        ItemEntity itemEntity = rentalEntity.getItem();
        itemEntity.setQty(itemEntity.getQty() + 1);
        repository.save(itemEntity);

        rentalEntity.setReturnDate(new Date());
        rentalEntity.setReturned(true);

        double fine = calculateFine(rentalEntity);
        rentalEntity.setFine(fine);

        rentalRepository.save(rentalEntity);
    }

    @Override
    public void deleteRental(Integer id) {
        rentalRepository.deleteById(id);
    }

    @Override
    public String calculateOverdue(Integer rentalId) {
        Optional<RentalEntity> rentalEntityOptional = rentalRepository.findById(rentalId);
        if (rentalEntityOptional.isPresent()) {
            RentalEntity rentalEntity = rentalEntityOptional.get();
            if (!rentalEntity.isReturned()) {

                rentalEntity.setFine(calculateFine(rentalEntity));
                rentalRepository.save(rentalEntity);
                return "Overdue - Fine: " + rentalEntity.getFine();
            } else {
                return "Item already returned.";
            }
        } else {
            throw new IllegalArgumentException("Item record not found.");
        }
    }

    @Override
    public boolean markAsReturned(Integer rentalId) {
        RentalEntity rental= rentalRepository.findById(rentalId).orElse(null);

        if (rental == null) {
            return false;
        }

        if (rental.isReturned()) {

            return false;
        }


        rental.setReturned(true);

        rentalRepository.save(rental);
        return true;
    }

    private long getOverdueDays(RentalEntity rentalEntity) {
        if (rentalEntity.getReturnDate() == null) {
            return 0;
        }
        long diffInMillis = System.currentTimeMillis() - rentalEntity.getDueDate().getTime();
        long diffInDays = diffInMillis / (1000 * 60 * 60 * 24);
        return diffInDays > 0 ? diffInDays : 0;
    }
    private double calculateFine(RentalEntity rentalEntity) {
        long overdueDays = getOverdueDays(rentalEntity);
        if (overdueDays > 0) {
            return overdueDays * 2.0;
        }
        return 0.0;
    }

}
