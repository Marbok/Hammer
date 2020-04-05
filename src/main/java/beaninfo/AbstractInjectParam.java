package beaninfo;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.util.function.Function;

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

    public abstract Object createObjectForInject(Function<String, Object> initBeanByRef);
}
