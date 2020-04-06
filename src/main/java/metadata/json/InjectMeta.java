package metadata.json;

import beaninfo.*;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.ClassUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;

import static lombok.AccessLevel.NONE;

@Data
public class InjectMeta {
    private String value;
    private String[] values;
    private String ref;
    private String[] refs;
    private String type;
    private String name;

    @JsonIgnore
    @Setter(NONE)
    @Getter(NONE)
    private Class<?> injectClass;

    public AbstractInjectParam createInjectParam() throws ClassNotFoundException {
        injectClass = ClassUtils.getClass(type);
        return injectClass.isArray() ? getInjectArray() : getInjectParam();
    }

    private AbstractInjectParam getInjectParam() {
        if (!ObjectUtils.anyNotNull(value, ref)) throw new IllegalStateException("Not value: " + name);
        return StringUtils.isEmpty(ref) ? new InjectValue(injectClass, name, value) : new InjectReference(injectClass, name, ref);
    }

    private AbstractInjectParam getInjectArray() {
        if (!ObjectUtils.anyNotNull(values, refs)) throw new IllegalStateException("Not array: " + name);
        return refs != null ? new InjectReferences(injectClass, name, refs) : new InjectValues(injectClass, name, values);
    }

}
