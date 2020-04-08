package beaninfo.inject_param;

import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.lang.reflect.Array;
import java.util.function.Function;

@EqualsAndHashCode
@ToString
public class InjectReferences extends AbstractInjectParam {
    private String[] references;

    public InjectReferences(Class<?> clazz, String name, String[] references) {
        super(clazz, name);
        this.references = references;
    }

    @Override
    public Object createObjectForInject(Function<String, Object> initBeanByRef) {
        Object injectBeans = Array.newInstance(clazz.getComponentType(), references.length);
        for (int i = 0, n = references.length; i < n; i++) {
            Array.set(injectBeans, i, initBeanByRef.apply(references[i]));
        }
        return injectBeans;
    }

}
