package util;

import java.util.Collection;

/**
 * Common methods for work with Collections
 */
public final class CollectionsUtil {

    private CollectionsUtil() {
    }

    /**
     * @param collection any collection
     * @return true, if the collection is null or empty
     */
    public static boolean isEmpty(Collection<?> collection) {
        return collection == null || collection.isEmpty();
    }

    /**
     * @param collection any collection
     * @return true, if the collection isn't null or empty
     */
    public static boolean isNonEmpty(Collection<?> collection) {
        return !isEmpty(collection);
    }
}
