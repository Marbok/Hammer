package util;

import java.util.Collection;

public final class CollectionsUtil {

    private CollectionsUtil() {
    }

    public static <T> boolean isEmpty(Collection<T> collection) {
        return collection == null || collection.isEmpty();
    }

    public static <T> boolean isNonEmpty(Collection<T> collection) {
        return !isEmpty(collection);
    }
}
