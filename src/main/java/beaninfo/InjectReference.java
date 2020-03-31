package beaninfo;

import lombok.EqualsAndHashCode;
import lombok.ToString;

@EqualsAndHashCode
@ToString
public class InjectReference extends AbstractInjectParam {

    private String reference;

    public InjectReference(Class<?> clazz, String reference) {
        this(clazz, null, reference);
    }

    public InjectReference(Class<?> clazz, String name, String reference) {
        super(clazz, name);
        this.reference = reference;
    }

    @Override
    public String getValue() {
        return reference;
    }

    @Override
    public boolean isReference() {
        return true;
    }
}
