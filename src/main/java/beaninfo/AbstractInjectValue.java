package beaninfo;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

public abstract class AbstractInjectValue extends AbstractInjectParam {

    private static Map<Class<?>, Function<String, Object>> VALUES_GENERATOR = new HashMap<>();

    static {
        VALUES_GENERATOR.put(byte.class, Byte::valueOf);
        VALUES_GENERATOR.put(int.class, Integer::valueOf);
        VALUES_GENERATOR.put(long.class, Long::valueOf);
        VALUES_GENERATOR.put(float.class, Float::valueOf);
        VALUES_GENERATOR.put(double.class, Double::valueOf);
        VALUES_GENERATOR.put(boolean.class, Boolean::valueOf);
        VALUES_GENERATOR.put(char.class, s -> s.charAt(0));
        VALUES_GENERATOR.put(String.class, s -> s);
    }

    public AbstractInjectValue(Class<?> clazz, String name) {
        super(clazz, name);
    }

    @Override
    public boolean isReference() {
        return false;
    }

    protected Object generateValue(Class<?> clazz, String value) {
        Function<String, Object> generateFunction = VALUES_GENERATOR.get(clazz);
        if (generateFunction == null) {
            throw new UnsupportedOperationException("Can't inject type: " + clazz.getName());
        }
        return generateFunction.apply(value);
    }
}
