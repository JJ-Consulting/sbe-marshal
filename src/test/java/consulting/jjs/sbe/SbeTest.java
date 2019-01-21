package consulting.jjs.sbe;

import consulting.jjs.sbe.exception.FunctionalException;
import consulting.jjs.sbe.model.input.Field;
import consulting.jjs.sbe.model.input.Message;
import org.junit.Test;

import java.io.InputStream;
import java.nio.ByteBuffer;
import java.util.Arrays;

public class SbeTest {

  @Test
  public void shouldEncode() throws FunctionalException {
    InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream("sbe-schema.xml");
    Message message = getMockedMessage();

    long        timeBefore  = System.currentTimeMillis();
    Sbe         sbe         = new Sbe(inputStream);
    ByteBuffer  byteBuffer  = sbe.encode(message);

    System.out.println((System.currentTimeMillis() - timeBefore));

    byteBuffer.rewind();
    int i = 0;
    while (byteBuffer.hasRemaining()) {
      int unsignedByte = byteBuffer.get() & 0xFF;
      System.out.print(unsignedByte);
      System.out.print('|');
      ++i;
    }
    System.out.println("\n" + i);
  }

  private Message getMockedMessage() {
    return Message.builder()
            .name("Car")
            .fields(Arrays.asList(
                    Field.builder().name("serialNumber").value("1234").build(),
                    Field.builder().name("modelYear").value("2013").build()
            ))
            .build();
  }

}
