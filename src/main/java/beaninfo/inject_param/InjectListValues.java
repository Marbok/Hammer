package beaninfo.inject_param;

import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

@EqualsAndHashCode
@ToString
public class InjectListValues extends AbstractInjectParam implements ValueGenerator {

    private final List<Object> values;

    public InjectListValues(Class<?> clazz, String name, Class<?> componentType, String[] values) {
        super(clazz, name);

        try {
            this.values = clazz.equals(List.class) ? new ArrayList<>() : (List<Object>) clazz.getConstructor().newInstance();
        } catch (Exception e) {
            throw new IllegalStateException("Bean isn't a list: " + name);
        }

        for (String value : values) {
            this.values.add(generateValue(componentType, value));
        }
    }

    @Override
    public Object createObjectForInject(Function<String, Object> initBeanByRef) {
        return values;
    }
}
