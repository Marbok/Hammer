package beaninfo.inject_param;

import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

@EqualsAndHashCode
@ToString
public class InjectListReferences extends AbstractInjectParam {

    private final String[] references;
    private final Class<?> subClass;

    public InjectListReferences(Class<?> injectClass, String name, Class<?> subClass, String[] references) {
        super(injectClass, name);
        this.references = references;
        this.subClass = subClass;
    }


    @Override
    public Object createObjectForInject(Function<String, Object> initBeanByRef) {

        List<Object> injectBeans;
        try {
            injectBeans = clazz.equals(List.class) ? new ArrayList<>() : (List<Object>) clazz.getConstructor().newInstance();
        } catch (Exception e) {
            throw new IllegalStateException("Bean isn't a list: " + name);
        }

        for (var reference : references) {
            Object bean = initBeanByRef.apply(reference);
            if (!subClass.equals(bean.getClass())) throw new IllegalStateException("Not consistent types in list");
            injectBeans.add(bean);
        }
        return injectBeans;
    }
}
