package elliot.restapi.item;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class ItemRequest {

    @NotBlank(message = "id는 필수값입니다.")
    private String id;
    private String name;
}
