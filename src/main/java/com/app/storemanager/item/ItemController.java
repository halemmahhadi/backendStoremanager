package com.app.storemanager.item;

import com.app.storemanager.warehouse.Warehouse;
import com.app.storemanager.warehouse.WarehouseService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;


@Data
@RestController
@CrossOrigin
@RequestMapping("/items/")
public class ItemController {
    @Autowired
    private ItemService itemService;
    @Autowired
    private WarehouseService warehouseService;

    @PreAuthorize("hasAnyRole('Admin','StandardUser','SuperAdmin')")
    @PostMapping("createItem/" )
    @ApiOperation(value = "add item",
            notes = "add new item to DB",
            response = List.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully added item"),
            @ApiResponse(code = 400, message = "Errors during input"),
    })
    public ResponseEntity<ItemDto> addItem(@RequestBody final ItemDto itemDto) {
        try {
            Item item = itemService.addItem(Item.from(itemDto));
            return new ResponseEntity<>(ItemDto.from(item), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }

    }

    // get items
    @PreAuthorize("hasAnyRole('Admin','StandardUser','SuperAdmin')")
    @GetMapping("/getItem/")
    @ApiOperation(value = "view a list of available Items present in the store",
            notes = "get all items",
            response = List.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully displayed all items"),
            @ApiResponse(code = 404, message = "There is no items to display ")

    })
    public ResponseEntity<List<ItemDto>> getAllItems() {
        try {
            List<Item> items = itemService.getAllItems();
            List<ItemDto> itemDto = items.stream().map(ItemDto::from).collect(Collectors.toList());
            return new ResponseEntity<>(itemDto, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);

        }

    }
    // get item by  id

    @PreAuthorize("hasAnyRole('Admin','StandardUser','SuperAdmin')")
    @GetMapping(value = "get_item/id/{id}")
    @ApiOperation(value = "search for item",
            notes = "get item by id",
            response = List.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully displayed  the item with specific Id"),
            @ApiResponse(code = 404, message = "There is no item  with this ID")

    })
    public ResponseEntity<ItemDto> getItemById(@PathVariable final Long id) {
        Item item = itemService.getItemById(id);
        if (item != null) {
            return new ResponseEntity<>(ItemDto.from(item), HttpStatus.OK);
        } else
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);

    }

    // delete item

    @PreAuthorize("hasAnyRole('Admin','StandardUser','SuperAdmin')")
    @DeleteMapping(value = "delete_item/id/{id}")
    @ApiOperation(value = "Delete Item",
            notes = " delete item by id",
            response = List.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully deleted item"),
            @ApiResponse(code = 404, message = "Can not found this item with this ID"),

    })
    public ResponseEntity<ItemDto> deleteItem(@PathVariable final Long id) {
        try {
            Item item = itemService.deleteItem(id);
            return new ResponseEntity<>(ItemDto.from(item), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.OK);
        }

    }
    // edit item

    @PreAuthorize("hasAnyRole('Admin','StandardUser','SuperAdmin')")
    @PutMapping(value = "update_item/id/{id}")
    @ApiOperation(value = "Update Item",
            notes = "edit items by id",
            response = List.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully updated item"),
            @ApiResponse(code = 404, message = "can not found this item with this ID"),

    })
    public ResponseEntity<ItemDto> editItem(@PathVariable final Long id, @RequestBody final ItemDto itemDto) {
        try {
            Item editItem = itemService.editItem(id, Item.from(itemDto));
            return new ResponseEntity<>(ItemDto.from(editItem), HttpStatus.OK);
        } catch (Exception e) {

        }
        return new ResponseEntity<>(null, HttpStatus.OK);
    }

    public void createDummyItems() {
        Warehouse warehouse1 = warehouseService.getWarehouseByIdToCreateItems(Long.valueOf(1));
        Warehouse warehouse2 = warehouseService.getWarehouseByIdToCreateItems(Long.valueOf(2));
        Warehouse warehouse3 = warehouseService.getWarehouseByIdToCreateItems(Long.valueOf(3));
        Item[] itemsToCreate = {
                new Item("Blackcurrant", 50, 1, LocalDate.now(), warehouse1),
                new Item("Blueberries", 40, 2, LocalDate.now(), warehouse1),
                new Item("Grapefruit", 77, 3, LocalDate.now(), warehouse1),
                new Item("Kiwifruit", 55, 2, LocalDate.now(), warehouse1),
                new Item("carrot", 10, 1.5, LocalDate.now(), warehouse2),
                new Item("parsnip", 60, 0.5, LocalDate.now(), warehouse2),
                new Item("celtuce", 50, 0.99, LocalDate.now(), warehouse2),
                new Item("potato", 4, 0.60, LocalDate.now(), warehouse2),
                new Item("tables", 50, 100, LocalDate.now(), warehouse3),
                new Item("chests", 20, 250, LocalDate.now(), warehouse3),
                new Item("cabinets", 2, 307, LocalDate.now(), warehouse3),
                new Item("beds", 55, 250, LocalDate.now(), warehouse3)

        };

        itemService.createItems(itemsToCreate);

    }
}
