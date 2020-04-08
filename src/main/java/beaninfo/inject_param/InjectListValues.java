package beaninfo.inject_param;

import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

@EqualsAndHashCode
@ToString
public class InjectListValues extends AbstractInjectParam implements ValueGenerator {

    private List<Object> values = new ArrayList<>();

    public InjectListValues(Class<?> clazz, String name, Class<?> componentType, String[] values) {
        super(clazz, name);
        for (String value : values) {
            this.values.add(generateValue(componentType, value));
        }
    }

    @Override
    public Object createObjectForInject(Function<String, Object> initBeanByRef) {
        return values;
    }
}
