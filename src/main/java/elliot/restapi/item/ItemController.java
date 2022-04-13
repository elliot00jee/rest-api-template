package elliot.restapi.item;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@Controller
public class ItemController {

    @ResponseBody
    @PostMapping("/item")
    public void createItem(ItemRequest itemRequest) {
        System.out.println("itemRequest = " + itemRequest);
    }
}
