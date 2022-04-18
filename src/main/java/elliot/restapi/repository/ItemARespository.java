package elliot.restapi.repository;

import elliot.restapi.entity.Item;
import elliot.restapi.entity.ItemType;
import elliot.restapi.repository.mongotemplate.ItemMogoTemplate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

/**
 * MongoRepository 상위 인터페이스에서 지원되는 메소드
 * - save(S): 존재할 경우 update, 없을 경우 생성(ById)
 * - findById(ID): 조회
 * - findAll(): 데이터 전부 조회. 파라미터로 정렬 또는 페이징 조건을 넘길 수 있다.
 *
 * Spring Data가 구현체를 만들어서 인젝션해주기 때문에, 개발자는 인터페이스만 만들면 된다.
 * @Repository X => org.springframework.data.repository.MongoRepository를 상속받은 인터페이스는 컴포넌트 스캔 대상
 *
 * 메소드 이름으로 쿼리 생성
 * ex)
 * findByItemId => select * from ItemA where itemId = ?
 * findTopByOrderByItemIdDesc => select * from ItemA order by itemId limit 1
 * deleteByItemId => delete from ItemA where itemId = ?
 * countByItemId => select count(*) from ItemA where itemId = ?
 *
 * 참고) https://docs.spring.io/spring-data/mongodb/docs/current/reference/html/#repositories.query-methods.query-creation
 */
public interface ItemARespository extends MongoRepository<Item, String>, ItemMogoTemplate {

    Optional<Item> findTopByOrderByItemIdDesc();
    Page<Item> findByItemType(ItemType itemType, Pageable pageable);
}
