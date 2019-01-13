package consulting.jjs.sbe.marshal;

import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import consulting.jjs.sbe.exception.FunctionalException;
import consulting.jjs.sbe.model.template.MessageSchema;

import java.io.IOException;
import java.io.InputStream;

public class Marshaller {

  private static final XmlMapper XML_MAPPER = new XmlMapper();

  public MessageSchema unmarshal(String input) throws FunctionalException {
    try {
      MessageSchema messageSchema = XML_MAPPER.readValue(input, MessageSchema.class);
      messageSchema.afterUnmarshalling();
      return messageSchema;
    } catch (IOException e) {
      throw new FunctionalException("Unable to unmarshal input schema", e);
    }
  }

  public MessageSchema unmarshal(InputStream inputStream) throws FunctionalException {
    try {
      XmlMapper xmlMapper = new XmlMapper();
      MessageSchema messageSchema = xmlMapper.readValue(inputStream, MessageSchema.class);
      messageSchema.afterUnmarshalling();
      return messageSchema;
    } catch (IOException e) {
      throw new FunctionalException("Unable to unmarshal input schema", e);
    }
  }

}
