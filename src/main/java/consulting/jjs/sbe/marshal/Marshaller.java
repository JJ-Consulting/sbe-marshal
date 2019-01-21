package consulting.jjs.sbe.marshal;

import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import consulting.jjs.sbe.model.template.MessageSchema;
import consulting.jjs.sbe.model.template.TemplatePrimitive;
import consulting.jjs.sbe.model.template.TemplateType;
import lombok.Getter;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Map;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Marshaller {

  private static final String    DESERIALIZATION_ERROR = "Unable to unmarshal input schema";
  private static final XmlMapper XML_MAPPER            = new XmlMapper();

  private static final TemplateType[] PRIMITIVE_TYPES = {
          TemplateType.builder().name("char").primitiveType(TemplatePrimitive.CHAR).build(),
          TemplateType.builder().name("uint8").primitiveType(TemplatePrimitive.UINT_8).build(),
          TemplateType.builder().name("uint16").primitiveType(TemplatePrimitive.UINT_16).build(),
          TemplateType.builder().name("uint32").primitiveType(TemplatePrimitive.UINT_32).build(),
          TemplateType.builder().name("uint64").primitiveType(TemplatePrimitive.UINT_64).build(),
          TemplateType.builder().name("int8").primitiveType(TemplatePrimitive.INT_8).build(),
          TemplateType.builder().name("int16").primitiveType(TemplatePrimitive.INT_16).build(),
          TemplateType.builder().name("int32").primitiveType(TemplatePrimitive.INT_32).build(),
          TemplateType.builder().name("int64").primitiveType(TemplatePrimitive.INT_64).build(),
          TemplateType.builder().name("float").primitiveType(TemplatePrimitive.FLOAT).build(),
          TemplateType.builder().name("double").primitiveType(TemplatePrimitive.DOUBLE).build()
  };

  @Getter
  private MessageSchema             messageSchema;
  @Getter
  private Map<String, TemplateType> declaredTypes;

  public void unmarshal(String input) {
    unmarshal(() -> {
      try {
        return XML_MAPPER.readValue(input, MessageSchema.class);
      } catch (IOException e) {
        throw new RuntimeException(DESERIALIZATION_ERROR, e);
      }
    });

  }

  public void unmarshal(InputStream inputStream) {
    unmarshal(() -> {
      try {
        return XML_MAPPER.readValue(inputStream, MessageSchema.class);
      } catch (IOException e) {
        throw new RuntimeException(DESERIALIZATION_ERROR, e);
      }
    });
  }

  private void unmarshal(Supplier<MessageSchema> messageSchemaSupplier) {
    messageSchema = messageSchemaSupplier.get();
    declaredTypes = Stream.concat(Arrays.stream(PRIMITIVE_TYPES), messageSchema.getTypes().stream()
            .flatMap(types -> types.getTypes().stream()))
            .collect(Collectors.toMap(TemplateType::getName, templateType -> templateType));
  }

}
