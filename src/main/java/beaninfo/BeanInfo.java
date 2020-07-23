package beaninfo;

import beaninfo.inject_param.AbstractInjectParam;
import beaninfo.states.BeanInfoState;
import beaninfo.states.WaitInit;
import exceptions.BeanInfoException;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import metadata.json.BeanMeta;
import metadata.json.InjectMeta;
import org.apache.commons.lang3.ClassUtils;
import util.CollectionsUtil;

import java.util.ArrayList;
import java.util.List;

import static beaninfo.Scope.SINGLETON;

@Data
@NoArgsConstructor
public class BeanInfo {
    private String name;
    private Class<?> clazz;
    private Scope scope = SINGLETON;
    private List<AbstractInjectParam> constructorParams;
    private List<AbstractInjectParam> setterParams;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private BeanInfoState state = new WaitInit(this);

    public BeanInfo(BeanMeta beanMeta) {
        try {
            name = beanMeta.getBeanName() != null ? beanMeta.getBeanName() : beanMeta.getClassName();
            clazz = ClassUtils.getClass(beanMeta.getClassName());
            scope = beanMeta.getScope() == null ? SINGLETON : Scope.valueOf(beanMeta.getScope().toUpperCase());
            constructorParams = injectMetasToInjectParams(beanMeta.getConstructor());
            setterParams = injectMetasToInjectParams(beanMeta.getSetters());
        } catch (BeanInfoException e) {
            throw new IllegalStateException("Not correct type in bean: " + beanMeta.getBeanName(), e);
        } catch (ClassNotFoundException e) {
            throw new IllegalStateException("Not correct class: " + beanMeta.getClassName() + ", in bean: " + beanMeta.getBeanName(), e);
        }
    }

    private List<AbstractInjectParam> injectMetasToInjectParams(List<InjectMeta> meta) throws BeanInfoException {
        if (CollectionsUtil.isEmpty(meta)) {
            return new ArrayList<>();
        }

        List<AbstractInjectParam> injectParams = new ArrayList<>();
        for (var injectMeta : meta) {
            try {
                injectParams.add(injectMeta.createInjectParam());
            } catch (ClassNotFoundException e) {
                throw new BeanInfoException("Not correct type injection", e);
            }
        }
        return injectParams;
    }

    public void startInitialized() {
        state.startInitialization();
    }

    public void stopInitialized() {
        state.stopInitialization();
    }
}
