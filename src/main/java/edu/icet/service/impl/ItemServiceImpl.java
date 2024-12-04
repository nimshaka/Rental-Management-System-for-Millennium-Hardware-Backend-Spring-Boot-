package edu.icet.service.impl;


import edu.icet.dto.Item;
import edu.icet.entity.ItemEntity;
import edu.icet.repository.ItemRepository;
import edu.icet.service.ItemService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@CrossOrigin
@Service
@RequiredArgsConstructor
public class ItemServiceImpl implements ItemService {
    private final ModelMapper mapper;
    private final ItemRepository repository;
    @Override
    public List<Item> getAll() {
        return repository.findAll().stream()
                .map(entity -> mapper.map(entity, Item.class))
                .collect(Collectors.toList());
    }

    @Override
    public void addItem(Item item) {
        ItemEntity itemEntity = mapper.map(item, ItemEntity.class);
        repository.save(itemEntity);
    }

    @Override
    public void deleteItem(Integer id) {
        repository.deleteById(id);
    }

    @Override
    public Item searchItemById(Integer id) {
        Optional<ItemEntity> itemEntity = repository.findById(id);
        return itemEntity.map(entity -> mapper.map(entity, Item.class))
                .orElse(null);
    }

    @Override
    public void updateItemById(Item item) {
        ItemEntity itemEntity = mapper.map(item, ItemEntity.class);
        repository.save(itemEntity);
    }

    @Override
    public List<Item> searchByName(String itemName) {
        List<Item> items = new ArrayList<>();
        repository.findByItemName(itemName).forEach(entity->{
            items.add(mapper.map(entity,Item.class));
        });

        return items;
    }
}
