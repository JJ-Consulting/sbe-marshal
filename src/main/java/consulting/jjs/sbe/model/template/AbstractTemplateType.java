package consulting.jjs.sbe.model.template;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import consulting.jjs.sbe.encoder.TypeEncoder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public abstract class AbstractTemplateType implements TypeEncoder {

  @JacksonXmlProperty(isAttribute = true)
  private String            name;

}
