package elliot.restapi.controller;


import elliot.restapi.controller.dto.ItemDto;
import elliot.restapi.entity.Item;
import elliot.restapi.service.ItemService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static elliot.restapi.util.ResponseUtils.ApiResponse;
import static elliot.restapi.util.ResponseUtils.success;

@RequiredArgsConstructor
@RestController
@RequestMapping("/v1/item")
public class ItemController {
    private final ItemService itemService;
    // private final ObjectMapper objectMapper;

    private final ModelMapper modelMapper;

    @PostMapping("")
    public ApiResponse<?> createItem(@RequestBody ItemDto itemDto){
        Item result = itemService.createItemA(
                modelMapper.map(itemDto, Item.class));

        return success(modelMapper.map(result, ItemDto.class));
    }
}