package com.lg.fresher.lgcommerce.utils;

import lombok.experimental.UtilityClass;

import java.util.UUID;

@UtilityClass
public class UUIDUtil {
    public String generateId(){
        return UUID.randomUUID().toString();
    }

    public boolean isValidUUID(String id) {
        try {
            UUID.fromString(id);
            return true;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }
}
