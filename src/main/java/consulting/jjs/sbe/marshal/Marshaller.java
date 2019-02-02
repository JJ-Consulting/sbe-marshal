package consulting.jjs.sbe.marshal;

import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import consulting.jjs.sbe.model.template.AbstractTemplateType;
import consulting.jjs.sbe.model.template.DeserializedTemplate;
import consulting.jjs.sbe.model.template.MessageSchema;
import consulting.jjs.sbe.model.template.TemplateComposite;
import consulting.jjs.sbe.model.template.TemplateType;
import consulting.jjs.sbe.model.template.TemplateTypeReference;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
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

  private DeclaredTypes              declaredTypes;
  private Map<String, DeclaredTypes> declaredComposedTypes;

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

  private void templateTypeConsumer(AbstractTemplateType type) {
    declaredTypes.declareType(type.getName(), type);
  }

  private DeserializedTemplate unmarshal(Supplier<MessageSchema> messageSchemaSupplier) {
    declaredTypes = new DeclaredTypes();
    declaredComposedTypes = new HashMap<>();

    Arrays.stream(PRIMITIVE_TYPES).forEach(this::templateTypeConsumer);

    MessageSchema messageSchema = messageSchemaSupplier.get();
    messageSchema.getTypes().forEach(types -> {
      types.getTypes().forEach(this::templateTypeConsumer);
      types.getEnums().forEach(this::templateTypeConsumer);
      types.getSets().forEach(this::templateTypeConsumer);
      types.getComposites().forEach(this::templateCompositeTypeConsumer);
    });
    linkCompositesReferences(messageSchema);
    return new DeserializedTemplate(messageSchema, declaredTypes, declaredComposedTypes);
  }

  private void linkCompositesReferences(MessageSchema messageSchema) {
    messageSchema.getTypes().stream()
            .flatMap(types -> types.getComposites().stream())
            .forEach(parentComposite ->
                    parentComposite.getReferences().stream().filter(TemplateTypeReference::isReferenceComposite)
                            .forEach(ref -> {
                              linkCompositeReferences(declaredComposedTypes.get(parentComposite.getName()),
                                      declaredComposedTypes.get(ref.getType()), ref.getName());
                            })
            );
  }

  private void linkCompositeReferences(DeclaredTypes parentComposite,
                                       DeclaredTypes childComposite, String referenceName) {
    childComposite.forEach((name, type) -> {
      AbstractTemplateType duplicatedType = type.duplicate();
      duplicatedType.setName(referenceName + "." + type.getName());
      parentComposite.declareType(duplicatedType.getName(), duplicatedType);
    });
  }

  private void templateCompositeTypeConsumer(TemplateComposite composite) {
    DeclaredTypes compositeTypes = new DeclaredTypes();

    composite.getTypes().forEach(type -> compositeTypes.declareType(type.getName(), type));
    composite.getEnums().forEach(type -> compositeTypes.declareType(type.getName(), type));
    composite.getReferences().forEach(ref -> { // handling references to simple value
      AbstractTemplateType type = declaredTypes.getDeclaredType(ref.getType());
      if (type != null) { // reference of composite -> NPE
        AbstractTemplateType duplicated = type.duplicate();
        duplicated.setName(ref.getName());
        compositeTypes.declareType(duplicated.getName(), duplicated);
      } else {
        ref.setReferenceComposite(true);
      }
    });

    declaredComposedTypes.put(composite.getName(), compositeTypes);
  }

}
