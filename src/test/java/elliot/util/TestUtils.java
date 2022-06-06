package elliot.util;

import elliot.restapi.entity.ItemType;
import lombok.Getter;
import lombok.ToString;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class TestUtils {

    public static List<EnumMap> getEnumMaps(Class<? extends Enum<?>> enumClass) throws NoSuchMethodException {
        ItemType[] itemTypes = ItemType.values();
        return Arrays.stream(itemTypes).map(itemType -> new EnumMap(itemType.name(), itemType.getDescription())).collect(Collectors.toList());
    }

    @ToString
    @Getter
    static class EnumMap {
        private final String name;
        private final String description;

        EnumMap(String name, String description) {
            this.name = name;
            this.description = description;
        }
    }
}
