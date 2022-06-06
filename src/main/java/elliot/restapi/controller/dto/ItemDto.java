package elliot.restapi.controller.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@Accessors(chain = true)
public class ItemDto {
    private String itemId;
    @NotBlank
    private String itemName;
    private String description;
    private String itemType;
}
