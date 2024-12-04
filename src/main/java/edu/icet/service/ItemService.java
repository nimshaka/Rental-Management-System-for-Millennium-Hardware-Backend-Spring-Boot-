package edu.icet.service;


import edu.icet.dto.Item;

import java.util.List;

public interface ItemService {
    List<Item> getAll();
    void addItem(Item item);
    void deleteItem(Integer id);
    Item searchItemById(Integer id);
    void updateItemById(Item item);
    List<Item> searchByName(String itemName);
}
