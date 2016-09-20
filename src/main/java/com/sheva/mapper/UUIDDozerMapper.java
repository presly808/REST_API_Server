package com.sheva.mapper;

import java.util.UUID;

import org.dozer.CustomConverter;

public class UUIDDozerMapper implements CustomConverter {

    @Override
    public Object convert(Object arg0, Object arg1, Class<?> arg2, Class<?> arg3) {
        if(arg1 == null || !(arg1 instanceof UUID)) {
            return null;
        }
        UUID sourceValue = (UUID) arg1;
        return UUID.fromString(sourceValue.toString());
    }

}
