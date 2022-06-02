package elliot.restapi.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * modelMapper는 기본적으로 변환되는(to) 클래스에 기본 생성자가 있어야 한다.
 * 롬복의 @Builder를 사용할 경우, 기본생성자를 만들게 되면 충돌이 생긴다. => 전체 생성자도 필요함.
 *
 *
 * 1. ObjectMapper 사용(기본 생성자 필요없음) @Builder
 * 2. ModelMapper 사용. @Builder + @NoArgsConstructor + @AllArgsConstructor(access = AccessLevel.PRIVATE)
 * 3. 생성자로 객체 생성하여 사용.
 */
@Getter
@SuperBuilder
@NoArgsConstructor
@ToString(callSuper = true)
@Document
public class Item extends BaseEntity {
    @Id
    private String id;
    private String itemId;
    private String itemName;
    private String description;
    private ItemType itemType;

    @Transient
    private final String PREFIX_ITEM = "ITEM";

    public void createAndSetItemId(String lastItemId) {
        try {
            this.itemId = String.format(PREFIX_ITEM + "%05d",
                    Integer.parseInt(lastItemId.replace(PREFIX_ITEM, "")) + 1);
        } catch (NumberFormatException e) {
            throw new NumberFormatException("ItemID 생성 중, 유효하지 않은 Item ID=" + lastItemId);
        }
    }
}
