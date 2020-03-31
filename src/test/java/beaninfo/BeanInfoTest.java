package beaninfo;

import metadata.json.BeanMeta;
import metadata.json.InjectMeta;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;

class BeanInfoTest {

    @Test
    void map() {
        var beanMeta = new BeanMeta()
                .setBeanName("string")
                .setClassName("java.lang.String")
                .setConstructor(Arrays.asList(
                        new InjectMeta().setType("int").setValue("5"),
                        new InjectMeta().setType("java.lang.String").setRef("builder")
                ))
                .setSetters(Arrays.asList(
                        new InjectMeta().setType("java.math.BigDecimal").setRef("buffer"),
                        new InjectMeta().setType("double").setValue("6.5")
                ));

        var actual = BeanInfo.map(beanMeta);

        var constructorExp = Arrays.asList(
                new InjectValue(int.class, "5"),
                new InjectReference(String.class, "builder")
        );

        var settersExp = Arrays.asList(
                new InjectReference(BigDecimal.class, "buffer"),
                new InjectValue(double.class, "6.5")
        );

        assertEquals("string", actual.getName());
        assertEquals(String.class, actual.getClazz());
        assertEquals(constructorExp, actual.getConstructorParams());
        assertEquals(settersExp, actual.getSetterParams());
    }
}