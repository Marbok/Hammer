package util;

import java.util.stream.Stream;

import static java.util.Arrays.asList;

/**
 * Common methods for work with Class
 */
public final class ClassUtil {

    private ClassUtil() {
    }

    /**
     * @param clazz           the class being checked
     * @param targetInterface interface, which we want check
     * @return true, if clazz is targetInterface or it implements this interface
     */
    public static boolean haveInterface(Class<?> clazz, Class<?> targetInterface) {
        return clazz.equals(targetInterface) || asList(clazz.getInterfaces()).contains(targetInterface);
    }

    /**
     * @param clazz checked class
     * @return true - if class have default constructor
     */
    public static boolean haveDefaultConstructor(Class<?> clazz) {
        return Stream.of(clazz.getConstructors())
                .filter(constructor -> constructor.getParameterTypes().length == 0)
                .findFirst()
                .isPresent();
    }
}
