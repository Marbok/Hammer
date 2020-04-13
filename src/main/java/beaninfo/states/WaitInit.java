package beaninfo.states;

import beaninfo.BeanInfo;
import exceptions.CreateBeanException;

public class WaitInit implements BeanInfoState {

    private final BeanInfo beanInfo;

    public WaitInit(BeanInfo beanInfo) {
        this.beanInfo = beanInfo;
    }

    @Override
    public void startInitialization() {
        beanInfo.setState(new InProcessInit(beanInfo));
    }

    @Override
    public void stopInitialization() {
        throw new CreateBeanException("Didn't start bean initialization: " + beanInfo.getName());
    }
}
