package consulting.jjs.sbe;

import consulting.jjs.sbe.model.input.FieldValue;
import consulting.jjs.sbe.model.input.Message;
import consulting.jjs.sbe.store.MessageStore;
import consulting.jjs.sbe.store.TemplateStore;

import java.io.InputStream;
import java.nio.ByteBuffer;

public class Sbe {

  private static final int DEFAULT_MEMORY_SIZE = 1024;

  private TemplateStore templateStore;

  public Sbe(String xmlMessageSchema) {
    templateStore = new TemplateStore(xmlMessageSchema);
  }

  public Sbe(InputStream xmlMessageSchema) {
    templateStore = new TemplateStore(xmlMessageSchema);
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
    byteBuffer.order(templateStore.getByteOrder());
    MessageStore store = templateStore.getMessage(message.getName());
    encodeHeader(message, byteBuffer);
    for (FieldValue field : message.getFields()) {
      field.consumeValue((fieldName, value) ->
        store.getTemplateFieldByName(fieldName)
                .getType()
                .encode(value, byteBuffer)
      );
    }
  }

  private int getMessageSize(Message message) {
    // TODO: get exact size from message
    return 1024;
  }

  private void encodeHeader(Message message, ByteBuffer buffer) {
    // TODO
  }

}
