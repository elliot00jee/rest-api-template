package elliot.restapi.repository;

import elliot.restapi.entity.ItemType;
import lombok.Getter;
import lombok.Setter;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.annotation.Id;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@ActiveProfiles("local")
@SpringBootTest
class ItemRespositoryTest {

    @Autowired
    ItemARespository itemARespository;

    @Setter
    @Getter
    @Document
    static class Item {
        @Id
        private String id;
        private String itemId;
        private ItemType itemType;
        private String description;
    }

    @Test
    public void MongoRepository_간단_CRUD_테스트() {
        System.out.println("1. 저장 : save");
        elliot.restapi.entity.Item itemA1 = elliot.restapi.entity.Item.builder().itemId("Item1").itemType(ItemType.A).description("desc1").build();
        elliot.restapi.entity.Item itemA2 = elliot.restapi.entity.Item.builder().itemId("Item2").itemType(ItemType.A).description("desc2").build();
        elliot.restapi.entity.Item itemA3 = elliot.restapi.entity.Item.builder().itemId("Item3").itemType(ItemType.B).description("desc3").build();

        elliot.restapi.entity.Item savedItemA1 = itemARespository.save(itemA1);
        elliot.restapi.entity.Item savedItemA2 = itemARespository.save(itemA2);
        elliot.restapi.entity.Item savedItemA3 = itemARespository.save(itemA3);
        System.out.println(savedItemA1);
        System.out.println(savedItemA2);
        System.out.println(savedItemA3);

        System.out.println("2. 전부 조회 : findAll");
        List<elliot.restapi.entity.Item> items = itemARespository.findAll();
        items.stream().forEach(System.out::println);

        System.out.println("3. 전부 조회 - 정렬 : findAll(Sort.by(...))");
        List<elliot.restapi.entity.Item> itemsSort = itemARespository.findAll(Sort.by(Sort.Direction.DESC));
        itemsSort.stream().forEach(System.out::println);

        System.out.println("4. 수정 : save");
        elliot.restapi.entity.Item itemA4 = elliot.restapi.entity.Item.builder().itemId("Item4").itemType(ItemType.B).description("desc4").build();
        elliot.restapi.entity.Item savedItemA4 = itemARespository.save(itemA4);
        System.out.println("생성된 _id: " + savedItemA4.getId());

        elliot.restapi.entity.Item changedItemA4 = elliot.restapi.entity.Item.builder().id(savedItemA4.getId()).itemId("Item4").itemType(ItemType.B).description("XXXXXXXX").build();
        System.out.println(itemARespository.save(changedItemA4));

    }

    @Test
    public void 페이징_테스트() {
        itemARespository.save(elliot.restapi.entity.Item.builder().itemId("item1").itemType(ItemType.B).build());
        itemARespository.save(elliot.restapi.entity.Item.builder().itemId("item2").itemType(ItemType.B).build());
        itemARespository.save(elliot.restapi.entity.Item.builder().itemId("item3").itemType(ItemType.B).build());
        itemARespository.save(elliot.restapi.entity.Item.builder().itemId("item4").itemType(ItemType.B).build());
        itemARespository.save(elliot.restapi.entity.Item.builder().itemId("item5").itemType(ItemType.B).build());

        /**
         * 1. 페이징 계산
         *
         * 전달되는 파라미터 => pageNum = 0(페이지 인덱스), pageSize = 3(한 페이지에 노출되는 건수)
         * 응답해야 되는 데이터 => 전체 페이지 수, 해당 페이지의 데이터
         * 1) int count = 5(전체 데이터 수 조회)
         *
         *    int count = 5; int pageSize = 3;
         *    int totalPages = count / pageSize;
         *    if(count % pageSize > 0) {
         *      totalPages ++;
         *    }
         *    => totalPage = 2 (전체 페이지 수)
         * 2) offset = pageSize * pageNum
         *    select * from ItemA where itemType = "B" limit 3 offset 0
         *
         * 2. Spring Data
         *    find..By..(.., PageRequest.of(1, 3, Sort.by(Sort.Direction.DESC, "username")))
         *
         */
        PageRequest pageRequest = PageRequest.of(0, 3, Sort.by(Sort.Direction.DESC, "username"));
        Page<elliot.restapi.entity.Item> page = itemARespository.findByItemType(ItemType.B, pageRequest);

        List<elliot.restapi.entity.Item> items = page.getContent(); // 해당 페이지 데이터
        assertThat(items.size()).isEqualTo(3);
        assertThat(page.getTotalElements()).isEqualTo(5); // 전체 데이터 수
        assertThat(page.getTotalPages()).isEqualTo(2); // 전체 페이지 수
        assertThat(page.isFirst()).isEqualTo(true); // 첫 번째 페이지 인지 여부
        assertThat(page.isLast()).isEqualTo(false); // 마지막 페이지 인지 여부
        assertThat(page.hasNext()).isEqualTo(true); // 다음 페이지가 있는지 여부
        assertThat(page.hasPrevious()).isEqualTo(false); // 이전 페이지가 있는지 여부

    }

    @Test
    public void mongoTemplate_테스트() {
        /**
         * MongoRepository와 MongoTemplate이 겹칠 경우, MongoTemplate이 우선순위 가짐
         */
        itemARespository.insert(elliot.restapi.entity.Item.builder().itemId("item1").itemType(ItemType.A).build());
        assertThat(itemARespository.findByItemId("item1").get().getItemType()).isEqualTo(ItemType.A);
    }

    @AfterEach
    public void deleteAll() {
        itemARespository.deleteAll();
    }
}