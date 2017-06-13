package com.smpp.platform.dal;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class Property {
    private String name;
    private Method getter;
    private Method setter;
    private Class propertyType;
    private Class baseType;
    private boolean id;
    private boolean unique;
    private boolean generatedValue;

    //    Constructor num 1
    public Property(Field field) {
        init(field.getName(), field.getType(), field.getDeclaringClass());
        // Field Name  	  //Field Type		//Class type which calls This Field
    }

    //    Constructor num 2
    public Property(String name, Class propertyType, Class baseType) {
        init(name, propertyType, baseType);
    }

    private void init(String name, Class propertyType, Class baseType) {
        try {
            this.name = name;
            this.propertyType = propertyType;
            this.baseType = baseType;
            if (baseType.getDeclaredField(name).getAnnotation(Id.class) != null) {
                id = true;
            }
            if (baseType.getDeclaredField(name).getAnnotation(Unique.class) != null) {
                unique = true;
            }
            if (baseType.getDeclaredField(name).getAnnotation(GeneratedValue.class) != null) {
                generatedValue = true;
            }
            if (propertyType.equals(Boolean.class) || propertyType.equals(Boolean.TYPE)) {
                getter = baseType.getMethod("is" + Character.toUpperCase(name.charAt(0)) + name.substring(1));
            } else {
                getter = baseType.getMethod("get" + Character.toUpperCase(name.charAt(0)) + name.substring(1));
            }
            setter = baseType.getMethod("set" + Character.toUpperCase(name.charAt(0)) + name.substring(1), propertyType);
        } catch (Exception e) {
            throw new IllegalArgumentException("Never happens", e);
        }
    }

    public Object get(Object instance) {
        try {
            return getter.invoke(instance);
        } catch (Exception ex) {
            throw new IllegalArgumentException("Never happens", ex);
        }
    }

    public void set(Object instance, Object value) {
        try {
            setter.invoke(instance, value);  // setter.invoke(instance,value);
        } catch (Exception ex) {
            throw new IllegalArgumentException("Never happens", ex);
        }
    }

    public boolean isUnique() {
        return unique;
    }

    public boolean isId() {
        return id;
    }

    public Class getPropertyType() {
        return propertyType;
    }

    public String getName() {
        return name;
    }

    public Class getBaseType() {
        return baseType;
    }

    public boolean isGeneratedValue() {
        return generatedValue;
    }
}
