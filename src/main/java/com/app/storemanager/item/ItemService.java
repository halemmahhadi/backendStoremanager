package com.app.storemanager.item;

import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Data
@Service
public class ItemService {
    @Autowired
    private ItemRepository itemRepository;

    //add Item
    public Item addItem(Item item) {
        return itemRepository.save(item);
    }

    // get all Items
    public List<Item> getAllItems() {
        return itemRepository.findAll();
    }

    // get item by id
    public Item getItemById(Long id) {
        return itemRepository.findById(id)
                .orElseThrow(() ->
                        new ItemNotFoundException(id));
    }

    // delete item by his id
    public Item deleteItem(Long id) {
        Item item = getItemById(id);
        itemRepository.delete(item);
        return item;
    }

    // edit item
    public Item editItem(Long id, Item item) {
        Item itemToEdit = getItemById(id);
        itemToEdit.setItemName(item.getItemName());
        itemToEdit.setPrice(item.getPrice());
        itemToEdit.setQuantity(item.getQuantity());
        itemToEdit.setCreationDate(item.getCreationDate());
        return itemToEdit;


    }

    public void createItems(Item... itemslist) {
        Arrays.stream(itemslist).forEach(item -> addItem(item));
    }


}
