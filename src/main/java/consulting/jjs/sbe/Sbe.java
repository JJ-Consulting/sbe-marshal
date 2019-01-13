package consulting.jjs.sbe;

import consulting.jjs.sbe.exception.FunctionalException;
import consulting.jjs.sbe.marshal.Marshaller;
import consulting.jjs.sbe.model.input.Message;
import consulting.jjs.sbe.model.template.MessageSchema;

import java.io.InputStream;
import java.nio.ByteBuffer;

public class Sbe {

  private static final int DEFAULT_MEMORY_SIZE = 1024;

  private final MessageSchema messageSchema;

  public Sbe(String xmlMessageSchema) throws FunctionalException {
    messageSchema = new Marshaller().unmarshal(xmlMessageSchema);
  }

  public Sbe(InputStream xmlMessageSchema) throws FunctionalException {
    messageSchema = new Marshaller().unmarshal(xmlMessageSchema);
  }

  public ByteBuffer encode(Message message) {
    ByteBuffer buffer = ByteBuffer.allocate(DEFAULT_MEMORY_SIZE);
    encode(message, buffer);
    return buffer;
  }

  public ByteBuffer encode(Message message, int memorySizeToAllocate) {
    ByteBuffer buffer = ByteBuffer.allocate(memorySizeToAllocate);
    encode(message, buffer);
    return buffer;
  }

  public ByteBuffer encodeMinMemoryAllocated(Message message) {
    ByteBuffer buffer = ByteBuffer.allocate(getMessageSize(message));
    encode(message, buffer);
    return buffer;
  }

  public void encode(Message message, ByteBuffer byteBuffer) {

  }

  private int getMessageSize(Message message) {
    // TODO
    return 1024;
  }

}
