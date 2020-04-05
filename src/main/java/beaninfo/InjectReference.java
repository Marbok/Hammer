package beaninfo;

import lombok.EqualsAndHashCode;
import lombok.ToString;

@EqualsAndHashCode
@ToString
public class InjectReference extends AbstractInjectSingleReference {

    private String reference;

    public InjectReference(Class<?> clazz, String name, String reference) {
        super(clazz, name);
        this.reference = reference;
    }

    @Override
    public String getValue() {
        return reference;
    }
}
