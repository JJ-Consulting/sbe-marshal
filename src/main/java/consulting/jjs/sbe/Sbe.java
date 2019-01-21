package consulting.jjs.sbe;

import consulting.jjs.sbe.marshal.Marshaller;
import consulting.jjs.sbe.model.input.Field;
import consulting.jjs.sbe.model.input.Message;
import consulting.jjs.sbe.model.template.MessageSchema;
import consulting.jjs.sbe.model.template.TemplateMessage;
import consulting.jjs.sbe.model.template.TemplateType;
import consulting.jjs.sbe.store.TemplateStore;

import java.io.InputStream;
import java.nio.ByteBuffer;
import java.util.Map;
import java.util.stream.Collectors;

public class Sbe {

  private static final int DEFAULT_MEMORY_SIZE = 1024;

  private MessageSchema              messageSchema;
  // Fields store by message name
  private Map<String, TemplateStore> messagesStore;

  public Sbe(String xmlMessageSchema) {
    Marshaller marshaller = new Marshaller();
    marshaller.unmarshal(xmlMessageSchema);
    initStores(marshaller);
  }

  public Sbe(InputStream xmlMessageSchema) {
    Marshaller marshaller = new Marshaller();
    marshaller.unmarshal(xmlMessageSchema);
    initStores(marshaller);
  }

  private void initStores(Marshaller marshaller) {
    messageSchema = marshaller.getMessageSchema();
    messagesStore = messageSchema.getMessages().stream()
            .collect(Collectors.toMap(TemplateMessage::getName, message -> new TemplateStore(message, marshaller.getDeclaredTypes())));
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
    byteBuffer.order(messageSchema.getByteOrder());
    TemplateStore store = messagesStore.get(message.getName());
    encodeHeader(message, byteBuffer);
    for (Field field : message.getFields()) {
      TemplateType type = store.getTemplateFieldByName(field.getName()).getType();
      type.encode(field.getValue(), byteBuffer);
    }
  }

  private int getMessageSize(Message message) {
    // TODO
    return 1024;
  }

  private void encodeHeader(Message message, ByteBuffer buffer) {
    // TODO
  }

}
