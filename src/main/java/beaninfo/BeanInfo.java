package beaninfo;

import beaninfo.states.BeanInfoState;
import beaninfo.states.WaitInit;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.List;

import static beaninfo.Scope.SINGLETON;

@Data
@NoArgsConstructor
public class BeanInfo {
    private String name;
    private Class<?> clazz;
    private Scope scope = SINGLETON;
    private Constructor<?> constructor;
    private List<Method> setters;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private BeanInfoState state = new WaitInit(this);

    public void startInitialized() {
        state.startInitialization();
    }

    public void stopInitialized() {
        state.stopInitialization();
    }
}
