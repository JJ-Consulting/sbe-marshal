package consulting.jjs.sbe.marshal;

import consulting.jjs.sbe.model.template.DeserializedTemplate;
import consulting.jjs.sbe.model.template.MessageSchema;
import org.junit.Test;

import java.io.InputStream;

public class MarshallerTest {

  @Test
  public void shouldUnmarshallSchema() {
    InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream("sbe-schema.xml");
    Marshaller marshaller = new Marshaller();
    DeserializedTemplate template = marshaller.unmarshal(inputStream);
    MessageSchema messageSchema = template.getMessageSchema();

    System.out.println(messageSchema);
  }

}
