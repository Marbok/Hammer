package beaninfo;

import lombok.Data;
import metadata.json.BeanMeta;
import metadata.json.ConstructorMeta;
import metadata.json.SetterMeta;
import util.CollectionsUtil;

import java.util.List;
import java.util.Map;

import static beaninfo.Scope.SINGLETON;
import static java.util.stream.Collectors.toMap;

@Data
public class BeanInfo {
    private String name;
    private Class clazz;
    private Scope scope = SINGLETON;
    private Map<String, InjectParam> constructorParam;
    private Map<String, InjectParam> setterParam;

    public BeanInfo setConstructorParam(List<ConstructorMeta> meta) {
        if (CollectionsUtil.isNonEmpty(meta)) {
            this.setConstructorParam(meta.stream().collect(toMap(ConstructorMeta::getNameAttr, InjectParam::new)));
        }
        return this;
    }

    public BeanInfo setConstructorParam(Map<String, InjectParam> constructorParam) {
        this.constructorParam = constructorParam;
        return this;
    }

    public BeanInfo setSetterParam(List<SetterMeta> meta) {
        if (CollectionsUtil.isNonEmpty(meta)) {
            this.setSetterParam(meta.stream().collect(toMap(SetterMeta::getName, InjectParam::new)));
        }
        return this;
    }

    public BeanInfo setSetterParam(Map<String, InjectParam> setterParam) {
        this.setterParam = setterParam;
        return this;
    }

    public static BeanInfo map(BeanMeta beanMeta) {
        try {
            return new BeanInfo()
                    .setName(beanMeta.getBeanName())
                    .setClazz(Class.forName(beanMeta.getClassName()))
                    .setConstructorParam(beanMeta.getConstructor())
                    .setSetterParam(beanMeta.getSetters());
        } catch (ClassNotFoundException e) {
            throw new IllegalStateException("Not correct class: " + beanMeta.getClassName() + ", in bean: " + beanMeta.getBeanName(), e);
        }
    }
}
