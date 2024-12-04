package edu.icet.controller;


import edu.icet.dto.Item;
import edu.icet.service.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RequiredArgsConstructor
@RestController
@RequestMapping("/item")
public class ItemController {
    private final ItemService itemService;

    @GetMapping("/get-item")
    public List<Item> getItem() {
        return itemService.getAll();
    }

    @PostMapping("/add-item")
    @ResponseStatus(HttpStatus.CREATED)
    public void addItem(@RequestBody Item item) {
        itemService.addItem(item);
    }

    @GetMapping("/search-by-id/{id}")
    public Item getItemById(@PathVariable Integer id) {
        return itemService.searchItemById(id);
    }


    @GetMapping("/search-by-name/{name}")
    public List<Item> searchByName(@PathVariable String itemName) {
        return itemService.searchByName(itemName);
    }

    @DeleteMapping("/delete-by-id/{id}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void deleteItemById(@PathVariable Integer id) {
        itemService.deleteItem(id);
    }

    @PutMapping("/update-item")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void updateItem(@RequestBody Item item) {
        itemService.updateItemById(item);
    }
}
