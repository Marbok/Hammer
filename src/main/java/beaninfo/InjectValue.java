package beaninfo;

import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.function.Function;

@EqualsAndHashCode
@ToString
public class InjectValue extends AbstractInjectParam implements ValueGenerator {

    private Object value;

    public InjectValue(Class<?> clazz, String name, String value) {
        super(clazz, name);
        this.value = generateValue(clazz, value);
    }

    @Override
    public Object getValue() {
        return value;
    }

    @Override
    public Object createObjectForInject(Function<String, Object> initBeanByRef) {
        return getValue();
    }

}
