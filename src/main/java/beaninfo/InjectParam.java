package beaninfo;

import lombok.Getter;
import metadata.json.ConstructorMeta;
import metadata.json.SetterMeta;

@Getter
public class InjectParam {
    private String ref;
    private Object val;

    public InjectParam(String ref) {
        this.ref = ref;
    }

    public InjectParam(Object val) {
        this.val = val;
    }

    public static InjectParam map(ConstructorMeta constructorMeta) {
        return constructorMeta.getRef() == null ?
                new InjectParam(constructorMeta.getRef()) :
                new InjectParam(constructorMeta.getValue());
    }

    public static InjectParam map(SetterMeta setterMeta) {
        return setterMeta.getRef() == null ?
                new InjectParam(setterMeta.getRef()) :
                new InjectParam(setterMeta.getValue());
    }
}
