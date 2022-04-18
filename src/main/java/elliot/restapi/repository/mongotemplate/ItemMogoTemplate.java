package elliot.restapi.repository.mongotemplate;

import elliot.restapi.entity.Item;

import java.util.Optional;

/**
 * MongoRepository 기능 외에 MongoTemplate을 직접 사용하여 데이터를 조회하고 싶을 때 interface를 만든 뒤 구현한다.
 * 생성한 interface는 XXXRepository에서 MongoRepository와 함께 상속받는다.
 */
public interface ItemMogoTemplate {

    Optional<Item> findByItemId(String itemId);

}
