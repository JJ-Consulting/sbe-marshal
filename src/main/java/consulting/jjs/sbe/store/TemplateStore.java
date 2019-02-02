package consulting.jjs.sbe.store;

import consulting.jjs.sbe.marshal.Marshaller;
import consulting.jjs.sbe.model.template.DeserializedTemplate;
import consulting.jjs.sbe.model.template.MessageSchema;
import consulting.jjs.sbe.model.template.TemplateMessage;
import lombok.Getter;

import java.io.InputStream;
import java.nio.ByteOrder;
import java.util.Map;
import java.util.stream.Collectors;

public class TemplateStore {

  private Map<String, MessageStore> messagesStore;
  @Getter
  private ByteOrder                 byteOrder;

  public TemplateStore(String xmlMessageSchema) {
    initStores(new Marshaller().unmarshal(xmlMessageSchema));
  }

  public TemplateStore(InputStream xmlMessageSchema) {
    initStores(new Marshaller().unmarshal(xmlMessageSchema));
  }

  private void initStores(DeserializedTemplate template) {
    MessageSchema messageSchema = template.getMessageSchema();
    byteOrder = messageSchema.getByteOrder();
    messagesStore = messageSchema.getMessages().stream()
            .collect(Collectors.toMap(TemplateMessage::getName,
                    message -> new MessageStore(message, template.getDeclaredTypes(), template.getDeclaredComposedTypes())));
  }

  public MessageStore getMessage(String name) {
    return messagesStore.get(name);
  }

}
