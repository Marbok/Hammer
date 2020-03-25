package context;

import beaninfo.BeanInfo;
import beaninfo.InjectParam;
import exceptions.CreateBeanException;
import util.MapUtil;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
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
            Map<String, InjectParam> constructorParam = beanInfo.getConstructorParam();
            if (MapUtil.isEmpty(constructorParam)) {
                return actualCons.newInstance();
            } else {
                Parameter[] parameters = actualCons.getParameters();
                List<Object> injectObject = new ArrayList<>(parameters.length);
                for (var parameter : parameters) {
                    InjectParam paramInjectInfo = constructorParam.get(parameter.getName());
                    if (paramInjectInfo.isValue()) {
                        injectObject.add(paramInjectInfo.getVal());
                    } else {
                        String ref = paramInjectInfo.getRef();
                        Object bean = container.get(ref);
                        if (bean == null) {
                            bean = initBean(context.getBeanInfo(ref));
                        }
                        injectObject.add(bean);
                    }
                }
                return actualCons.newInstance(injectObject);
            }
        } catch (IllegalAccessException | InstantiationException | InvocationTargetException e) {
            throw new CreateBeanException("Can't create bean: " + beanInfo.getName(), e);
        }
    }

    private Constructor<?> getActualConstructor(BeanInfo beanInfo) {
        if (MapUtil.isEmpty(beanInfo.getConstructorParam())) {
            return getDefaultConstructor(beanInfo);
        }

        Map<String, InjectParam> constructorParam = beanInfo.getConstructorParam();
        Constructor<?>[] constructors = beanInfo.getClazz().getConstructors();

        for (var cons : constructors) {
            Parameter[] parameters = cons.getParameters();
            if (constructorParam.size() == parameters.length) {
                for (var parameter : parameters) {
                    if (!constructorParam.containsKey(parameter.getName())) {
                        break;
                    }
                }
                return cons;
            }
        }
        throw new CreateBeanException("Wrong arguments for constructor in bean: " + beanInfo.getName());
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
