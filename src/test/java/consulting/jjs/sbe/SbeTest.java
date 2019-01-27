package consulting.jjs.sbe;

import consulting.jjs.sbe.model.input.ArrayFieldValue;
import consulting.jjs.sbe.model.input.ComposedFieldValue;
import consulting.jjs.sbe.model.input.Message;
import consulting.jjs.sbe.model.input.SimpleFieldValue;
import org.junit.Test;

import java.io.InputStream;
import java.nio.ByteBuffer;
import java.util.Arrays;

public class SbeTest {

  @Test
  public void shouldEncode() {
    InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream("sbe-schema.xml");
    Message     message     = getMockedMessage();

    Sbe        sbe        = new Sbe(inputStream);
    ByteBuffer byteBuffer = sbe.encode(message);

    byteBuffer.flip();
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
                    new SimpleFieldValue("serialNumber", "1234"),
                    new SimpleFieldValue("modelYear", "2013"),
                    new SimpleFieldValue("available", "T"),
                    new SimpleFieldValue("code", "A"),
                    new ArrayFieldValue("someNumbers").addValue("0").addValue("1").addValue("2").addValue("3").addValue("4"),
                    new ArrayFieldValue("vehicleCode").addValue("a").addValue("b").addValue("c").addValue("d").addValue("e").addValue("f"),
                    new SimpleFieldValue("extras", "110"),
                    new ComposedFieldValue("engine")
                            .addFieldValue(new SimpleFieldValue("capacity", "2000"))
                            .addFieldValue(new SimpleFieldValue("numCylinders", "4"))
                            .addFieldValue(new ArrayFieldValue("manufacturerCode").addValue("1").addValue("2").addValue("3"))
            ))
            .build();
  }

}
