package elliot.restapi.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import elliot.restapi.controller.dto.ItemDto;
import elliot.restapi.entity.Item;
import elliot.restapi.entity.ItemType;
import elliot.util.BaseControllerTest;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoClientDatabaseFactory;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.http.MediaType;
import org.springframework.restdocs.constraints.ConstraintDescriptions;
import org.springframework.restdocs.payload.FieldDescriptor;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.util.StringUtils;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static elliot.util.ApiDocumentUtils.getDocumentResponse;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.restdocs.snippet.Attributes.key;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


class ItemControllerTest extends BaseControllerTest {
    private static final String MONGODB_CONN_STR = "mongodb://item:item@localhost:27017/item";
    static MongoTemplate mongoTemplate;

    @Autowired
    ObjectMapper objectMapper;

    FieldDescriptor[] items = new FieldDescriptor[] {
            fieldWithPath("itemId").type(JsonFieldType.STRING).attributes(key("format").value("ITEM00000001")).description("Item ID"),
            fieldWithPath("itemName").type(JsonFieldType.STRING).description("Item Name"),
            fieldWithPath("description").type(JsonFieldType.STRING).description("Item Description"),
            fieldWithPath("itemType").type(JsonFieldType.STRING).description("Item Type")
    };

    @BeforeAll
    static void beforeAll() {
        mongoTemplate = new MongoTemplate(new SimpleMongoClientDatabaseFactory(MONGODB_CONN_STR));
    }

    @AfterEach
    void afterEach() {
        mongoTemplate.remove(new Query(new Criteria("createdBy").is("TEST-USER-01")), "item");
    }

    @Nested
    @DisplayName("item 생성 테스트")
    class createItem {
        @Test
        @DisplayName("성공")
        void createItem_success() throws Exception {
            ItemDto itemDto = new ItemDto().setItemName("A-item1").setItemType(ItemType.A.name()).setDescription("item desc");

            ConstrainedFields fields = new ConstrainedFields(ItemDto.class);

            mockMvc.perform(post("/v1/items")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(itemDto))
                            .header("userId", "TEST-USER-01"))
                    .andDo(print())
                    .andExpect(status().isOk())
                    .andExpect(handler().methodName("createItem"))
                    .andExpect(handler().handlerType(ItemController.class))
                    .andExpect(jsonPath("$.success", is(true)))
                    .andExpect(jsonPath("$.data.itemId", notNullValue()))
                    .andDo(document("create-item",
                            requestFields(
                                    fieldWithPath("itemId").ignored(),
                                    fields.withPath("itemName").description("Item Name"),
                                    fieldWithPath("itemType").description("Item Type"),
                                    fieldWithPath("description").description("Item Description").optional()
                            )));
        }
    }
    private static class ConstrainedFields {

        private final ConstraintDescriptions constraintDescriptions;

        ConstrainedFields(Class<?> input) {
            this.constraintDescriptions = new ConstraintDescriptions(input);
        }

        private FieldDescriptor withPath(String path) {
            return fieldWithPath(path).attributes(key("constraints").value(StringUtils
                    .collectionToDelimitedString(this.constraintDescriptions
                            .descriptionsForProperty(path), ". ")));
        }
    }

    @Nested
    @DisplayName("item 단건 조회 테스트")
    class getItem {

        @Test
        @DisplayName("성공")
        void getItem_success() throws Exception {
            mongoTemplate.insert(
                    Item.builder()
                            .itemId("TEST00001")
                            .itemName("TEST-ITEM-01")
                            .itemType(ItemType.A)
                            .description("TEST-DESC")
                            .createdBy("TEST-USER-01")
                            .build());

            mockMvc.perform(get("/v1/items/{itemId}", "TEST00001"))
                    .andDo(print())
                    .andExpect(status().isOk())
                    .andDo(document("get-item",
                            getDocumentResponse(),
                            pathParameters(parameterWithName("itemId").description("Item ID")),
                            responseFields(
                                    beneathPath("data").withSubsectionId("data"),
                                    items
                            )
                    ));


        }
    }

    @Nested
    @DisplayName("모든 item 조회 테스트")
    class getAllItem {

        @Test
        @DisplayName("성공")
        void getAllItem_success() throws Exception {
            mongoTemplate.insert(
                    Item.builder()
                            .itemId("TEST00001")
                            .itemName("TEST-ITEM-01")
                            .itemType(ItemType.A)
                            .description("TEST-DESC")
                            .createdBy("TEST-USER-01")
                            .build());

            mockMvc.perform(get("/v1/items"))
                    .andDo(print())
                    .andExpect(status().isOk())
                    .andDo(document("get-all-item",
                            getDocumentResponse(),
                            responseFields(
                                    /**
                                     * 이상한 점: data가 array인데도, 서브섹션으로 정의하면 array 가 아니라 그냥 map으로 가져온다.
                                     */
                                    beneathPath("data").withSubsectionId("data"),
                                    items
                            )
                    ));


        }
    }
}