package beaninfo;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@EqualsAndHashCode
@ToString
public abstract class AbstractInjectParam {

    private Class<?> clazz;
    private String name;

    public AbstractInjectParam(Class<?> clazz, String name) {
        this.clazz = clazz;
        this.name = name;
    }

    public abstract Object getValue();

    public abstract boolean isReference();
}
