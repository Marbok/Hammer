package util;

import static java.util.Arrays.asList;

/**
 * Common methods for work with Class
 */
public final class ClassUtil {

    private ClassUtil() {
    }

    /**
     * @param clazz           the class being checked
     * @param targetInterface
     * @return true, if clazz is targetInterface or it implements this interface
     */
    public static boolean haveInterface(Class<?> clazz, Class<?> targetInterface) {
        return clazz.equals(targetInterface) || asList(clazz.getInterfaces()).contains(targetInterface);
    }
}
