package beaninfo.inject_param;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public class InjectListReferences extends AbstractInjectParam {

    private String[] references;
    private Class<?> subClass;

    public InjectListReferences(Class<?> injectClass, String name, Class<?> subClass, String[] references) {
        super(injectClass, name);
        this.references = references;
        this.subClass = subClass;
    }


    @Override
    public Object createObjectForInject(Function<String, Object> initBeanByRef) {
        List<Object> injectBeans = new ArrayList<>();
        for (var reference : references) {
            Object bean = initBeanByRef.apply(reference);
            if (!subClass.equals(bean.getClass())) throw new IllegalStateException("Not consistent types in list");
            injectBeans.add(bean);
        }
        return injectBeans;
    }
}
