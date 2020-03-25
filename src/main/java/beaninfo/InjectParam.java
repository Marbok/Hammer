package beaninfo;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@EqualsAndHashCode
@ToString
public class InjectParam {
    private String ref;
    private Object val;

    public InjectParam(String ref) {
        this.ref = ref;
    }

    public InjectParam(Object val) {
        this.val = val;
    }

    public boolean isValue() {
        return val != null;
    }

    public boolean isRef() {
        return ref != null;
    }
}
