package consulting.jjs.sbe.model.template;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

public class TemplateData {

  @JacksonXmlProperty(isAttribute = true)
  private Integer id;
  @JacksonXmlProperty(isAttribute = true)
  private String  name;
  @JsonProperty("type")
  private String  typeName;

}
