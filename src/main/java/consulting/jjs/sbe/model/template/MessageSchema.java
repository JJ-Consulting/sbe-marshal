package consulting.jjs.sbe.model.template;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import consulting.jjs.sbe.marshal.ByteOrderDeserializer;
import consulting.jjs.sbe.marshal.PostDeserialize;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.nio.ByteOrder;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
public class MessageSchema implements PostDeserialize {

  private String    id;
  private String    version;
  @JsonDeserialize(using = ByteOrderDeserializer.class)
  private ByteOrder byteOrder;

  @JacksonXmlElementWrapper(useWrapping = false)
  @JsonProperty("message")
  private List<TemplateMessage> messages;

  @JacksonXmlElementWrapper(useWrapping = false)
  private List<TemplateTypes> types = new ArrayList<>();

  @Override
  public void afterUnmarshalling() {
    types.forEach(TemplateTypes::afterUnmarshalling);
  }
}


