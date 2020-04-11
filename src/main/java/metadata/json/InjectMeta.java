package metadata.json;

import beaninfo.inject_param.*;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.ClassUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.List;
import java.util.Map;

import static java.util.Arrays.asList;
import static lombok.AccessLevel.NONE;

@Data
// TODO kill setters, if jackson let
public class InjectMeta {
    private String value;
    private String[] values;
    private String ref;
    private String[] refs;
    private String type;
    private String name;
    @JsonProperty("value-type")
    private String valueType;
    @JsonProperty("key-type")
    private String keyType;
    private Map<String, String> map;

    @JsonIgnore
    @Setter(NONE)
    @Getter(NONE)
    private Class<?> injectClass;

    public AbstractInjectParam createInjectParam() throws ClassNotFoundException {
        injectClass = ClassUtils.getClass(type);
        if (injectClass.equals(List.class) || asList(injectClass.getInterfaces()).contains(List.class)) {
            return getInjectList();
        }
        return injectClass.isArray() ? getInjectArray() : getInjectParam();
    }

    private AbstractInjectParam getInjectList() throws ClassNotFoundException {
        if (valueType == null || !ObjectUtils.anyNotNull(refs, values))
            throw new IllegalStateException("Not list: " + name);
        Class<?> subClass = ClassUtils.getClass(valueType);
        return refs != null ? new InjectListReferences(injectClass, name, subClass, refs) : new InjectListValues(injectClass, name, subClass, values);

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
