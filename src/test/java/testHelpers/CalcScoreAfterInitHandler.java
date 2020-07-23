package testHelpers;

import context.AfterInitHandler;
import exceptions.CreateBeanException;

public class CalcScoreAfterInitHandler implements AfterInitHandler {
    @Override
    public Object handle(Object bean, String beanName) throws CreateBeanException {
        if (!bean.getClass().isAnnotationPresent(CalcScore.class)) return bean;

        InitMethodTest initMethodTest = (InitMethodTest) bean;
        initMethodTest.setAfterScore(initMethodTest.getBeforeScore() + 25);
        return bean;
    }
}
