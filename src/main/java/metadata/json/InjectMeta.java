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

import java.util.Collection;
import java.util.Map;

import static lombok.AccessLevel.NONE;
import static util.ClassUtil.haveInterface;

@Data
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
        if (!ObjectUtils.allNotNull(valueType, keyType, map))
            throw new IllegalStateException("Not map: " + name);
        Class<?> keyClass = ClassUtils.getClass(keyType);
        Class<?> valueClass = ClassUtils.getClass(valueType);
        return new InjectMapValues(injectClass, name, keyClass, valueClass, map);
    }

    private AbstractInjectParam getInjectCollection() throws ClassNotFoundException {
        if (valueType == null || !ObjectUtils.anyNotNull(refs, values))
            throw new IllegalStateException("Not list: " + name);
        Class<?> subClass = ClassUtils.getClass(valueType);
        return refs != null ? new InjectCollectionReferences(injectClass, name, subClass, refs) : new InjectCollectionValues(injectClass, name, subClass, values);
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
