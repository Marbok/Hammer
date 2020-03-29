package context;

import beaninfo.AbstractInjectParam;
import beaninfo.BeanInfo;
import exceptions.CreateBeanException;
import util.CollectionsUtil;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BeanFactory {

    private Context context;
    private Map<String, Object> container = new HashMap<>();

    public BeanFactory(Context context) {
        this.context = context;
        context.getAllBeanInfo().forEach(this::initBean);
    }

    private Object initBean(BeanInfo beanInfo) {
        Object o = container.get(beanInfo.getName());
        if (o != null) {
            return o;
        }

        o = createBean(beanInfo);
        container.put(beanInfo.getName(), o);
        return o;
    }

    private Object createBean(BeanInfo beanInfo) {
        try {
            Constructor<?> actualCons = getActualConstructor(beanInfo);
            List<AbstractInjectParam> constructorParam = beanInfo.getConstructorParam();
            if (CollectionsUtil.isEmpty(constructorParam)) {
                return actualCons.newInstance();
            } else {
                Object[] constructorParametersValues = beanInfo.getConstructorParam().stream()
                        .map(param -> {
                            if (param.isReference()) {
                                String ref = (String) param.getValue();
                                Object o = container.get(ref);
                                if (o == null) {
                                    o = createBean(context.getBeanInfo(ref));
                                }
                                return o;
                            } else {
                                return param.getValue();
                            }
                        }).toArray();
                return actualCons.newInstance(constructorParametersValues);
            }
        } catch (IllegalArgumentException | IllegalAccessException | InstantiationException | InvocationTargetException e) {
            throw new CreateBeanException("Can't create bean: " + beanInfo.getName(), e);
        }
    }

    private Constructor<?> getActualConstructor(BeanInfo beanInfo) {
        if (CollectionsUtil.isEmpty(beanInfo.getConstructorParam())) {
            return getDefaultConstructor(beanInfo);
        }

        List<AbstractInjectParam> constructorParams = beanInfo.getConstructorParam();
        Class<?>[] array = new Class[constructorParams.size()];
        for (int i = 0, n = array.length; i < n; i++) {
            array[i] = constructorParams.get(i).getClazz();
        }

        try {
            return beanInfo.getClazz().getConstructor(array);
        } catch (NoSuchMethodException e) {
            throw new CreateBeanException("Wrong arguments for constructor in bean: " + beanInfo.getName());
        }
    }


    private Constructor<?> getDefaultConstructor(BeanInfo beanInfo) {
        try {
            return beanInfo.getClazz().getConstructor();
        } catch (NoSuchMethodException e) {
            throw new CreateBeanException("Need parameters or constructor without arguments for bean: " + beanInfo.getName(), e);
        }
    }

    public Object getBean(String name) {
        return container.get(name);
    }

}
