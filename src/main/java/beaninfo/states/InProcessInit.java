package beaninfo.states;

import beaninfo.BeanInfo;
import exceptions.CreateBeanException;

public class InProcessInit implements BeanInfoState {

    private final BeanInfo beanInfo;

    public InProcessInit(BeanInfo beanInfo) {
        this.beanInfo = beanInfo;
    }

    @Override
    public void startInitialization() {
        throw new CreateBeanException("Cycle dependency: " + beanInfo.getName());
    }

    @Override
    public void stopInitialization() {
        beanInfo.setState(new WaitInit(beanInfo));
    }
}
