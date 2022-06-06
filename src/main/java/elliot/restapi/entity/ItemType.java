package elliot.restapi.entity;

import lombok.Getter;

@Getter
public enum ItemType {
    A("A등급"), B("B등급"),C("C등급"),D("D등급");

    private final String description;

    ItemType(String description) {
        this.description = description;
    }
}
