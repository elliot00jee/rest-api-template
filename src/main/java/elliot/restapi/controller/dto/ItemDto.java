package elliot.restapi.controller.dto;

import elliot.restapi.entity.BaseEntity;
import elliot.restapi.entity.ItemType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ItemDto extends BaseEntity {
    private String id;
    private String itemId;
    private String description;
    private ItemType itemType;
}
