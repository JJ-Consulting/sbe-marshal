package consulting.jjs.sbe;

import consulting.jjs.sbe.marshal.Marshaller;
import consulting.jjs.sbe.model.input.FieldValue;
import consulting.jjs.sbe.model.input.Message;
import consulting.jjs.sbe.model.template.DeserializedTemplate;
import consulting.jjs.sbe.model.template.MessageSchema;
import consulting.jjs.sbe.model.template.TemplateMessage;
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
    initStores(new Marshaller().unmarshal(xmlMessageSchema));
  }

  public Sbe(InputStream xmlMessageSchema) {
    initStores(new Marshaller().unmarshal(xmlMessageSchema));
  }

  private void initStores(DeserializedTemplate template) {
    messageSchema = template.getMessageSchema();
    messagesStore = messageSchema.getMessages().stream()
            .collect(Collectors.toMap(TemplateMessage::getName, message -> new TemplateStore(message, template.getDeclaredTypes(), template.getDeclaredComposedTypes())));
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
    for (FieldValue field : message.getFields()) {
      field.consumeValue((fieldName, value) ->
        store.getTemplateFieldByName(fieldName)
                .getType()
                .encode(value, byteBuffer)
      );
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
