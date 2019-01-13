package consulting.jjs.sbe.model.template;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@ToString
public class TemplateSet {

  @JacksonXmlProperty(isAttribute = true)
  private String               name;
  @JacksonXmlProperty(isAttribute = true)
  private String               encodingType;
  @JacksonXmlElementWrapper(useWrapping = false)
  @JsonProperty("choice")
  private List<TemplateChoice> choices = new ArrayList<>();

}
