package consulting.jjs.sbe.marshal;

import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import consulting.jjs.sbe.model.template.AbstractTemplateType;
import consulting.jjs.sbe.model.template.DeserializedTemplate;
import consulting.jjs.sbe.model.template.MessageSchema;
import consulting.jjs.sbe.model.template.TemplateType;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Supplier;
import java.util.stream.Stream;

public class Marshaller {

  private static final String    DESERIALIZATION_ERROR = "Unable to unmarshal input schema";
  private static final XmlMapper XML_MAPPER            = new XmlMapper();

  private static final TemplateType[] PRIMITIVE_TYPES = Stream.of(
          "char", "uint8", "uint16", "uint32", "uint64", "int8", "int16", "int32", "int64", "float", "double")
          .map(typeStr -> TemplateType.builder().name(typeStr)
                  .primitiveType(TypeEncoderDeserializer.typeEncoderFactory(typeStr)).build())
          .toArray(TemplateType[]::new);

  public DeserializedTemplate unmarshal(String input) {
    return unmarshal(() -> {
      try {
        return XML_MAPPER.readValue(input, MessageSchema.class);
      } catch (IOException e) {
        throw new RuntimeException(DESERIALIZATION_ERROR, e);
      }
    });

  }

  public DeserializedTemplate unmarshal(InputStream inputStream) {
    return unmarshal(() -> {
      try {
        return XML_MAPPER.readValue(inputStream, MessageSchema.class);
      } catch (IOException e) {
        throw new RuntimeException(DESERIALIZATION_ERROR, e);
      }
    });
  }

  private DeserializedTemplate unmarshal(Supplier<MessageSchema> messageSchemaSupplier) {
    MessageSchema messageSchema = messageSchemaSupplier.get();

    Map<String, AbstractTemplateType> declaredTypes         = new HashMap<>();
    Map<String, AbstractTemplateType> declaredComposedTypes = new HashMap<>();

    Consumer<AbstractTemplateType> templateTypeConsumer = (type) -> declaredTypes.put(type.getName(), type);
    Arrays.stream(PRIMITIVE_TYPES).forEach(templateTypeConsumer);
    messageSchema.getTypes().stream().flatMap(types -> types.getTypes().stream()).forEach(templateTypeConsumer);
    messageSchema.getTypes().stream().flatMap(types -> types.getEnums().stream()).forEach(templateTypeConsumer);

    messageSchema.getTypes().stream().forEach(types -> {
      types.getTypes().forEach(templateTypeConsumer);
      types.getEnums().forEach(templateTypeConsumer);
      types.getSets().forEach(templateTypeConsumer);
      types.getComposites().forEach(composite -> composite.getTypes().forEach(type ->
              declaredComposedTypes.put(type.getName(), type)));
    });
    return new DeserializedTemplate(messageSchema, declaredTypes, declaredComposedTypes);
  }

}
