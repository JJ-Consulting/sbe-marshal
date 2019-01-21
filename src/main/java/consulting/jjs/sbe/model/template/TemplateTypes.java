package consulting.jjs.sbe.model.template;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import lombok.Getter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Getter
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
public class TemplateTypes {

  @JacksonXmlElementWrapper(useWrapping = false)
  @JsonProperty("type")
  private List<TemplateType> types = new ArrayList<>();

  @JacksonXmlElementWrapper(useWrapping = false)
  @JsonProperty("composite")
  private List<TemplateComposite> composites = new ArrayList<>();

  @JacksonXmlElementWrapper(useWrapping = false)
  @JsonProperty("enum")
  private List<TemplateEnum> enums = new ArrayList<>();

  @JacksonXmlElementWrapper(useWrapping = false)
  @JsonProperty("set")
  private List<TemplateSet> sets = new ArrayList<>();

}
