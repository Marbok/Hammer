package metadata.json;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class BeanMeta {
    @JsonProperty("class")
    private String className;

    @JsonProperty("name")
    private String beanName;

    @JsonProperty("constructor")
    private List<ConstructorMeta> constructor;

    @JsonProperty("setters")
    private List<SetterMeta> setters;
}
