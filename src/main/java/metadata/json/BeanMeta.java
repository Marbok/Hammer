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

    private String scope;

    private List<InjectMeta> constructor;
    private List<InjectMeta> setters;
}
