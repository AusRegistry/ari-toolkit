package com.ausregistry.jtoolkit2.test.infrastructure;

import java.lang.reflect.Field;

public final class ReflectionUtils {

    private ReflectionUtils() {
    }

    public static Field getFieldFromInstance(Object instance, String propertyName) throws
            NoSuchFieldException, IllegalAccessException {
        final Field property = instance.getClass().getDeclaredField(propertyName);
        property.setAccessible(true);
        return property;
    }

    public static void setPropertyOnInstance(Object instance, String propertyName, Object propertyInstance) throws
            NoSuchFieldException, IllegalAccessException {
        getFieldFromInstance(instance, propertyName).set(instance, propertyInstance);
    }
}
