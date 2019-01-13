package consulting.jjs.sbe.model.template;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlText;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class TemplateEnumValue {

  @JacksonXmlProperty(isAttribute = true)
  private String name;
  @JacksonXmlText
  private String value;
}
