package testHelpers;

import context.BeforeInitHandler;
import exceptions.CreateBeanException;

public class CalcScoreBeforeInitHandler implements BeforeInitHandler {

    @Override
    public Object handle(Object bean, String beanName) throws CreateBeanException {
        if (!bean.getClass().isAnnotationPresent(CalcScore.class)) return bean;

        return ((InitMethodTest) bean).setBeforeScore(50);
    }
}
