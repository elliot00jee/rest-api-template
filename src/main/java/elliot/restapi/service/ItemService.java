package elliot.restapi.service;

import elliot.restapi.entity.Item;
import elliot.restapi.repository.ItemARespository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * Slf4j는 다양한 로그 구현체(Logback, Log4j 등)을 사용할 수 있게 인터페이스를 제공하는 라이브러리.
 * private static fianl Logger log = LoggerFactory.getLogger(ItemService.class);
 * 롬복 사용 => @Slf4j
 */
@Slf4j
@RequiredArgsConstructor
@Service
public class ItemService {
    private final ItemARespository itemARespository;

    public Item createItemA(Item itemA) {
        itemA.createAndSetItemId(
                itemARespository.findTopByOrderByItemIdDesc()
                    .orElseGet(() -> Item.builder().itemId("ITEM0000").build())
                .getItemId());
        itemARespository.insert(itemA);
        return itemA;
    }
}
