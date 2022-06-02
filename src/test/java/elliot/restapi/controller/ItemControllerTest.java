package elliot.restapi.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import elliot.restapi.controller.dto.ItemDto;
import elliot.restapi.entity.ItemType;
import org.hamcrest.Matcher;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.MockMvcPrint;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoClientDatabaseFactory;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ActiveProfiles("local")
@SpringBootTest
@AutoConfigureMockMvc
class ItemControllerTest {
    private static final String MONGODB_CONN_STR = "mongodb://item:item@localhost:27017/item";
    static MongoTemplate mongoTemplate;

    @Autowired
    MockMvc mockMvc;
    @Autowired
    ObjectMapper objectMapper;

    @AfterAll
    static void afterAll() {
        mongoTemplate = new MongoTemplate(new SimpleMongoClientDatabaseFactory(MONGODB_CONN_STR));
        mongoTemplate.remove(new Query(new Criteria("createdBy").is("test-user-01")), "item");
    }

    @Nested
    @DisplayName("item 생성 테스트")
    class createItem {
        @Test
        @DisplayName("성공")
        void createItem_success() throws Exception {
            ItemDto itemDto = new ItemDto().setItemName("A-item1").setItemType(ItemType.A).setDescription("item desc");
            mockMvc.perform(post("/v1/item")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(itemDto))
                            .header("userId", "test-user-01"))
                    .andDo(print())
                    .andExpect(status().isOk())
                    .andExpect(handler().methodName("createItem"))
                    .andExpect(handler().handlerType(ItemController.class))
                    .andExpect(jsonPath("$.success", is(true)))
                    .andExpect(jsonPath("$.data.itemId", notNullValue()));
        }
    }
}