package elliot.util;

import org.springframework.restdocs.payload.FieldDescriptor;
import org.springframework.restdocs.payload.ResponseFieldsSnippet;

import java.util.Arrays;
import java.util.List;

public class CustomPayloadDocumentation {
    public static CustomResponseFieldsSnippet customResponseFields(String type, FieldDescriptor... descriptors) {
        return new CustomResponseFieldsSnippet(type, Arrays.asList(descriptors), null, false);
    }
}
