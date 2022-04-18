package elliot.restapi.repository.mongotemplate;

import elliot.restapi.entity.Item;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import java.util.Optional;


@Slf4j
@RequiredArgsConstructor
public class ItemMogoTemplateImpl implements ItemMogoTemplate {

    private final MongoTemplate mongoTemplate;

    public Optional<Item> findByItemId(String itemId) {
        System.out.println("ItemMogoTemplateImpl.findByItemId");

        Query query = new Query(new Criteria("itemId").is(itemId));
        return Optional.ofNullable(
                mongoTemplate.findOne(query, Item.class));
    }
}
