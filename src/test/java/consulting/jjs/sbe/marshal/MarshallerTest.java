package consulting.jjs.sbe.marshal;

import consulting.jjs.sbe.exception.FunctionalException;
import consulting.jjs.sbe.model.template.MessageSchema;
import org.junit.Test;

import java.io.InputStream;

public class MarshallerTest {

  @Test
  public void shouldUnmarshallSchema() throws FunctionalException {
    InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream("sbe-schema.xml");

    MessageSchema messageSchema = new Marshaller().unmarshal(inputStream);

    System.out.println(messageSchema);
  }

}
