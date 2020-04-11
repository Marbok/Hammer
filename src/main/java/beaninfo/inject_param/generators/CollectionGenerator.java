package beaninfo.inject_param.generators;

import exceptions.CollectionCreateException;

import java.util.*;

public interface CollectionGenerator {
    default Collection<Object> generateCollection(Class<?> clazz) throws CollectionCreateException {
        try {
            if (clazz.equals(List.class) || clazz.equals(Collection.class)) {
                return new ArrayList<>();
            } else if (clazz.equals(Set.class)) {
                return new HashSet<>();
            } else {
                return (Collection<Object>) clazz.getConstructor().newInstance();
            }
        } catch (Exception e) {
            throw new CollectionCreateException("Not a collection: " + clazz.getName(), e);
        }
    }
}
