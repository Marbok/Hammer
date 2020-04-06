package context;

import beaninfo.AbstractInjectParam;
import beaninfo.BeanInfo;
import exceptions.CreateBeanException;
import org.apache.commons.lang3.StringUtils;
import util.CollectionsUtil;

import java.lang.reflect.Constructor;
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

        setterParams.forEach(param -> {
            try {
                String setterName = PREFIX_SETTER + StringUtils.capitalize(param.getName());
                bean.getClass()
                        .getDeclaredMethod(setterName, param.getClazz())
                        .invoke(bean, param.createObjectForInject(s -> initBean(context.getBeanInfo(s))));
            } catch (Exception e) {
                throw new CreateBeanException("Can't inject in setter: " + param.getName() + "in bean: " + beanInfo.getName(), e);
            }
        });

    }

    private Object createBean(BeanInfo beanInfo) {
        try {
            Constructor<?> actualCons = getActualConstructor(beanInfo);
            List<AbstractInjectParam> constructorParams = beanInfo.getConstructorParams();
            if (CollectionsUtil.isEmpty(constructorParams)) {
                return actualCons.newInstance();
            }
            return actualCons.newInstance(constructorParams.stream()
                    .map(param -> param.createObjectForInject(s -> initBean(context.getBeanInfo(s))))
                    .toArray());
        } catch (Exception e) {
            throw new CreateBeanException("Can't create bean: " + beanInfo.getName(), e);
        }
    }

    private Constructor<?> getActualConstructor(BeanInfo beanInfo) {
        if (CollectionsUtil.isEmpty(beanInfo.getConstructorParams())) {
            return getDefaultConstructor(beanInfo);
        }
        try {
            return beanInfo.getClazz().getConstructor(beanInfo
                    .getConstructorParams()
                    .stream()
                    .map(AbstractInjectParam::getClazz)
                    .toArray(Class[]::new));
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
        return initBean(context.getBeanInfo(name));
    }

}
