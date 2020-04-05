package beaninfo;

import metadata.json.InjectMeta;
import org.apache.commons.lang3.ClassUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;

public class InjectParamFactory {

    public AbstractInjectParam createInjectParam(InjectMeta injectMeta) throws ClassNotFoundException {
        Class<?> injectClass = ClassUtils.getClass(injectMeta.getType());
        return injectClass.isArray()
                ? getInjectArray(injectClass, injectMeta)
                : getInjectParam(injectClass, injectMeta);
    }

    private AbstractInjectParam getInjectParam(Class<?> injectClass, InjectMeta injectMeta) {
        if (!ObjectUtils.anyNotNull(injectMeta.getValue(), injectMeta.getRef())) {
            throw new IllegalStateException("Not value: " + injectMeta.getName());
        }

        return StringUtils.isEmpty(injectMeta.getRef())
                ? new InjectValue(injectClass, injectMeta.getName(), injectMeta.getValue())
                : new InjectReference(injectClass, injectMeta.getName(), injectMeta.getRef());
    }

    private AbstractInjectParam getInjectArray(Class<?> injectClass, InjectMeta injectMeta) {
        if (!ObjectUtils.anyNotNull(injectMeta.getValues(), injectMeta.getRefs())) {
            throw new IllegalStateException("Not array: " + injectMeta.getName());
        }

        return injectMeta.getRefs() != null
                ? new InjectReferences(injectClass, injectMeta.getName(), injectMeta.getRefs())
                : new InjectValues(injectClass, injectMeta.getName(), injectMeta.getValues());
    }

}
