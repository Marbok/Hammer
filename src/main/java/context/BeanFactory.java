package context;

import beaninfo.AbstractInjectParam;
import beaninfo.BeanInfo;
import exceptions.CreateBeanException;
import org.apache.commons.lang3.StringUtils;
import util.CollectionsUtil;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static beaninfo.Scope.SINGLETON;

public class BeanFactory {

    public static final String PREFIX_SETTER = "set";

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

        infectParamBySetters(o, beanInfo);

        if (SINGLETON.equals(beanInfo.getScope())) {
            container.put(beanInfo.getName(), o);
        }

        return o;
    }

    private void infectParamBySetters(Object bean, BeanInfo beanInfo) {
        List<AbstractInjectParam> setterParams = beanInfo.getSetterParams();

        if (CollectionsUtil.isEmpty(setterParams)) {
            return;
        }

        for (var setterParam : setterParams) {
            try {
                String setterName = PREFIX_SETTER + StringUtils.capitalize(setterParam.getName());
                Method setter = bean.getClass().getDeclaredMethod(setterName, setterParam.getClazz());
                if (setterParam.isReference()) {
                    String ref = (String) setterParam.getValue();
                    setter.invoke(bean, initBean(context.getBeanInfo(ref)));
                } else {
                    setter.invoke(bean, setterParam.getValue());
                }
            } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
                e.printStackTrace();
            }
        }

    }

    private Object createBean(BeanInfo beanInfo) {
        try {
            Constructor<?> actualCons = getActualConstructor(beanInfo);
            List<AbstractInjectParam> constructorParam = beanInfo.getConstructorParams();
            if (CollectionsUtil.isEmpty(constructorParam)) {
                return actualCons.newInstance();
            }
            Object[] constructorParametersValues = new Object[constructorParam.size()];
            for (int i = 0, n = constructorParam.size(); i < n; i++) {
                AbstractInjectParam param = constructorParam.get(i);
                if (param.isReference()) {
                    String ref = (String) param.getValue();
                    constructorParametersValues[i] = initBean(context.getBeanInfo(ref));
                } else {
                    constructorParametersValues[i] = param.getValue();
                }
            }
            return actualCons.newInstance(constructorParametersValues);
        } catch (IllegalArgumentException | IllegalAccessException | InstantiationException | InvocationTargetException e) {
            throw new CreateBeanException("Can't create bean: " + beanInfo.getName(), e);
        }
    }

    private Constructor<?> getActualConstructor(BeanInfo beanInfo) {
        if (CollectionsUtil.isEmpty(beanInfo.getConstructorParams())) {
            return getDefaultConstructor(beanInfo);
        }

        List<AbstractInjectParam> constructorParams = beanInfo.getConstructorParams();
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
