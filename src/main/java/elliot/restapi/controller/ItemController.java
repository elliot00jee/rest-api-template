package elliot.restapi.controller;


import elliot.restapi.controller.dto.ItemDto;
import elliot.restapi.entity.Item;
import elliot.restapi.service.ItemService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

import static elliot.restapi.util.ResponseUtils.success;

@RequiredArgsConstructor
@RestController
@RequestMapping("/v1/items")
public class ItemController {
    private final ItemService itemService;
    private final ModelMapper modelMapper;

    @PostMapping("")
    public ResponseEntity<?> createItem(@RequestBody ItemDto itemDto) {
        Item result = itemService.createItem(
                modelMapper.map(itemDto, Item.class));

        return success("itemId", result.getItemId());
    }

    @GetMapping("/{itemId}")
    public ResponseEntity<?> getItem(@PathVariable String itemId) {
        Item item = itemService.getItem(itemId);
        return success(modelMapper.map(item, ItemDto.class));
    }

    @GetMapping("")
    public ResponseEntity<?> getAllItem() {
        List<Item> items = itemService.getAllItem();
        return success(items.stream()
                .map(item -> modelMapper.map(item, ItemDto.class))
                .collect(Collectors.toList()));
    }
}
