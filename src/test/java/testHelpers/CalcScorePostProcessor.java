package testHelpers;

import context.BeanPostProcessor;
import exceptions.CreateBeanException;

public class CalcScorePostProcessor implements BeanPostProcessor {

    @Override
    public Object beforeInitialization(Object bean, String beanName) throws CreateBeanException {
        if (!bean.getClass().isAnnotationPresent(CalcScore.class)) return bean;

        return ((InitMethodTest) bean).setBeforeScore(50);
    }

    @Override
    public Object afterInitialization(Object bean, String beanName) throws CreateBeanException {
        if (!bean.getClass().isAnnotationPresent(CalcScore.class)) return bean;

        InitMethodTest initMethodTest = (InitMethodTest) bean;
        initMethodTest.setAfterScore(initMethodTest.getBeforeScore() + 25);
        return bean;
    }
}
