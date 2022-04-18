package elliot.restapi.entity;

import lombok.*;
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
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString(callSuper = true)
@Document(collection = "ItemA")
public class Item extends BaseEntity {
    @Id
    private String id;
    private String itemId;
    private String description;
    private ItemType itemType;

    @Transient
    private final String PREFIX_ITEM = "ITEM";

    /**
     * <도메인 주도 설계>
     * - 서비스는 로직을 직접 수행하기보다는 도메인 모델에 로직 수행을 위임하며, 도메인 계층을 조합해서 기능을 실행한다.
     *   핵심 규칙을 구현한 코드는 도메인 모델에만 위치하기 때문에 규칙이 바뀌거나 규칙을 확장해야할 때
     *   다른 코드에 영향을 덜 주고 변경 내역을 모델에 반영할 수 있게 된다.
     * - set 메서드는 도메인의 핵심 개념이나 의도를 코드에서 사라지게 한다.
     */
    public void createAndSetItemId(String lastItemId) {
        try {
            this.itemId = String.format(PREFIX_ITEM + "%05d",
                    Integer.parseInt(lastItemId.replace(PREFIX_ITEM, "")) + 1);
        } catch (NumberFormatException e) {
            throw new NumberFormatException("ItemID 생성 중, 유효하지 않은 Item ID=" + lastItemId);
        }
    }
}
