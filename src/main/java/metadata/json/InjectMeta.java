package metadata.json;

import beaninfo.inject_param.*;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.ClassUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.Collection;
import java.util.Map;

import static lombok.AccessLevel.NONE;
import static org.apache.commons.lang3.ObjectUtils.allNotNull;
import static org.apache.commons.lang3.ObjectUtils.anyNotNull;
import static util.ClassUtil.haveInterface;

/**
 * Parameter's description for injection
 */
@Data
public class InjectMeta {

    /**
     * Full name of parameter's class.
     * Example: "java.lang.String"
     */
    private String type;

    /**
     * Parameter's name is required for setter,
     * because factory finds method using template "set + Name".
     * Skip it for constructor.
     */
    private String name;

    /**
     *
     */
    private String value;
    private String[] values;

    private String ref;
    private String[] refs;

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
        if (haveInterface(injectClass, Collection.class)) {
            return getInjectCollection();
        } else if (haveInterface(injectClass, Map.class)) {
            return getInjectMap();
        } else if (injectClass.isArray()) {
            return getInjectArray();
        }
        return getInjectParam();
    }

    private AbstractInjectParam getInjectMap() throws ClassNotFoundException {
        if (!allNotNull(valueType, keyType, map))
            throw new IllegalStateException("Not map: " + name);
        Class<?> keyClass = ClassUtils.getClass(keyType);
        Class<?> valueClass = ClassUtils.getClass(valueType);
        return new InjectMapValues(injectClass, name, keyClass, valueClass, map);
    }

    private AbstractInjectParam getInjectCollection() throws ClassNotFoundException {
        if (valueType == null || !anyNotNull(refs, values))
            throw new IllegalStateException("Not list: " + name);
        Class<?> subClass = ClassUtils.getClass(valueType);
        return refs != null ? new InjectCollectionReferences(injectClass, name, subClass, refs) : new InjectCollectionValues(injectClass, name, subClass, values);
    }

    private AbstractInjectParam getInjectParam() {
        if (!anyNotNull(value, ref)) throw new IllegalStateException("Not value: " + name);
        return StringUtils.isEmpty(ref) ? new InjectValue(injectClass, name, value) : new InjectReference(injectClass, name, ref);
    }

    private AbstractInjectParam getInjectArray() {
        if (!anyNotNull(values, refs)) throw new IllegalStateException("Not array: " + name);
        return refs != null ? new InjectReferences(injectClass, name, refs) : new InjectValues(injectClass, name, values);
    }

}
