package beaninfo;

import exceptions.BeanInfoException;
import lombok.Data;
import metadata.json.BeanMeta;
import metadata.json.InjectMeta;
import org.apache.commons.lang3.ClassUtils;
import org.apache.commons.lang3.StringUtils;
import util.CollectionsUtil;

import java.util.ArrayList;
import java.util.List;

import static beaninfo.Scope.SINGLETON;

@Data
public class BeanInfo {
    private String name;
    private Class<?> clazz;
    private Scope scope = SINGLETON;
    private List<AbstractInjectParam> constructorParam;
    private List<AbstractInjectParam> setterParam;

    public BeanInfo setConstructorParam(List<InjectMeta> meta) throws BeanInfoException {
        if (CollectionsUtil.isNonEmpty(meta)) {
            this.constructorParam = injectMetasToInjectParams(meta);
        }
        return this;
    }

    public BeanInfo setSetterParam(List<InjectMeta> meta) throws BeanInfoException {
        if (CollectionsUtil.isNonEmpty(meta)) {
            this.setterParam = injectMetasToInjectParams(meta);
        }
        return this;
    }

    private List<AbstractInjectParam> injectMetasToInjectParams(List<InjectMeta> meta) throws BeanInfoException {
        List<AbstractInjectParam> injectParams = new ArrayList<>();
        for (var injectMeta : meta) {
            try {
                Class<?> injectClass = ClassUtils.getClass(injectMeta.getType());
                if (StringUtils.isEmpty(injectMeta.getRef())) {
                    injectParams.add(new InjectPrimitive(injectClass, injectMeta.getValue()));
                } else {
                    injectParams.add(new InjectReference(injectClass, injectMeta.getRef()));
                }
            } catch (ClassNotFoundException e) {
                throw new BeanInfoException("Not correct type: " + injectMeta.getType(), e);
            }
        }
        return injectParams;
    }

    public static BeanInfo map(BeanMeta beanMeta) {
        try {
            return new BeanInfo()
                    .setName(beanMeta.getBeanName())
                    .setClazz(ClassUtils.getClass(beanMeta.getClassName()))
                    .setConstructorParam(beanMeta.getConstructor())
                    .setSetterParam(beanMeta.getSetters());
        } catch (BeanInfoException e) {
            throw new IllegalStateException("Not correct type in bean: " + beanMeta.getBeanName(), e);
        } catch (ClassNotFoundException e) {
            throw new IllegalStateException("Not correct class: " + beanMeta.getClassName() + ", in bean: " + beanMeta.getBeanName(), e);
        }
    }
}
