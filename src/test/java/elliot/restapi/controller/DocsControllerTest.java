package elliot.restapi.controller;

import elliot.restapi.util.ResponseUtils;
import elliot.util.BaseControllerTest;
import elliot.util.CustomResponseFieldsSnippet;
import elliot.util.RestDocsConfig;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.FieldDescriptor;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static elliot.util.CustomPayloadDocumentation.customResponseFields;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.snippet.Attributes.key;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@AutoConfigureMockMvc
@AutoConfigureRestDocs(uriHost = "test")
@ActiveProfiles("local")
@SpringBootTest
@Import(RestDocsConfig.class)
class DocsControllerTest  {

    @Autowired
    MockMvc mockMvc;

    @Test
    public void getCommonResponse() throws Exception {
        mockMvc.perform(get("/docs/common-response").contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andDo(document("common",
                        customResponseFields(
                                "common-response",
                                fieldWithPath("success").type(JsonFieldType.BOOLEAN).description("성공 여부"),
                                subsectionWithPath("data").type(JsonFieldType.OBJECT).description("결과 데이터").optional(),
                                fieldWithPath("message").type(JsonFieldType.STRING).description("결과 메시지").optional()
                        )
                    )
                );

    }

}