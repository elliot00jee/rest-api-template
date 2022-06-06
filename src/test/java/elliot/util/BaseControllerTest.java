package elliot.util;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.restdocs.mockmvc.MockMvcRestDocumentationConfigurer;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;


@ExtendWith(RestDocumentationExtension.class)
@SpringBootTest
@ActiveProfiles("local")
@AutoConfigureMockMvc
public class BaseControllerTest {

    protected MockMvc mockMvc;

    /**
     * @AutoConfigureRestDocs를 붙이고, @Import(RestDocsConfig.class) 해서, RestDocsMockMvcBuilderCustomizer를 통해서
     * MockMvcRestDocumentationConfigurer를 세팅하게 해봤는데
     * RestDocsMockMvcBuilderCustomizer 생성자가 호출되면서 RestDocsProperties 내부의 값을 가져와서 다시 덮어써버리는 문제가 있다.
     * 왜 그런진 모르겠다.
     */
    @BeforeEach
    public void setUp(WebApplicationContext webApplicationContext, RestDocumentationContextProvider restDocumentation) {
        if(mockMvc == null) {
            MockMvcRestDocumentationConfigurer mockMvcRestDocumentationConfigurer = documentationConfiguration(restDocumentation);

            mockMvcRestDocumentationConfigurer.uris()
                    .withPort(8888)
                    .withHost("localhost")
                    .withScheme("https");

            mockMvcRestDocumentationConfigurer.operationPreprocessors()
                    .withResponseDefaults(prettyPrint())
                    .withRequestDefaults(prettyPrint());

            mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
                    .apply(mockMvcRestDocumentationConfigurer)
                    .build();
        }
    }
}
