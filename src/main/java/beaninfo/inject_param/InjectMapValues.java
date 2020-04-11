package beaninfo.inject_param;

import beaninfo.inject_param.generators.ValueGenerator;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

public class InjectMapValues extends AbstractInjectParam implements ValueGenerator {

    final Map<Object, Object> map;

    public InjectMapValues(Class<?> clazz, String name, Class<?> keyClass, Class<?> valueClass, Map<String, String> map) {
        super(clazz, name);

        try {
            this.map = clazz.equals(Map.class) ? new HashMap<>() : (Map<Object, Object>) clazz.getConstructor().newInstance();
        } catch (Exception e) {
            throw new IllegalStateException("Bean isn't a map: " + name);
        }

        for (var entry : map.entrySet()) {
            Object key = generateValue(keyClass, entry.getKey());
            Object value = generateValue(valueClass, entry.getValue());
            this.map.put(key, value);
        }
    }

    @Override
    public Object createObjectForInject(Function<String, Object> initBeanByRef) {
        return map;
    }
}
