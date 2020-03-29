package beaninfo;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@EqualsAndHashCode
@ToString
public abstract class AbstractInjectParam {
    private Class<?> clazz;

    public AbstractInjectParam(Class<?> clazz) {
        this.clazz = clazz;
    }

    public abstract Object getValue();

    public abstract boolean isReference();
}
