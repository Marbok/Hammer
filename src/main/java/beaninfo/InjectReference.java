package beaninfo;

import lombok.EqualsAndHashCode;
import lombok.ToString;

@EqualsAndHashCode(callSuper = true)
@ToString
public class InjectReference extends AbstractInjectParam {

    private String reference;

    public InjectReference(Class<?> clazz, String reference) {
        super(clazz);
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
