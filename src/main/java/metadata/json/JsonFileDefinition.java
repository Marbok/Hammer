package metadata.json;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class JsonFileDefinition {
    @JsonProperty("imports")
    private List<String> imports;

    @JsonProperty("beans")
    private List<BeanMeta> beans;
}
