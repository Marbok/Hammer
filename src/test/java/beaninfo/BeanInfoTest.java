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
        BeanMeta beanMeta = new BeanMeta()
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

        BeanInfo actual = new BeanInfo(beanMeta);

        BeanInfo expected = new BeanInfo()
                .setName("string")
                .setClazz(String.class)
                .setConstructorParams(Arrays.asList(
                        new InjectValue(int.class, null, "5"),
                        new InjectReference(String.class, null, "builder")
                ))
                .setSetterParams(Arrays.asList(
                        new InjectReference(BigDecimal.class, null, "buffer"),
                        new InjectValue(double.class, null, "6.5")
                ));

        assertEquals(expected, actual);
    }
}