package com.example.util;

import lombok.Value;

@Value
public class Tag {

    private String value;

    public String getLink() {
        return ControllerUrlBuilder.searchTag(value);
    }

}
