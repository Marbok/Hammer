package beaninfo;

import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.function.Function;

@EqualsAndHashCode
@ToString
public class InjectReference extends AbstractInjectParam {

    private String reference;

    public InjectReference(Class<?> clazz, String name, String reference) {
        super(clazz, name);
        this.reference = reference;
    }

    @Override
    public String getValue() {
        return reference;
    }

    @Override
    public Object createObjectForInject(Function<String, Object> initBeanByRef) {
        return initBeanByRef.apply(reference);
    }
}
