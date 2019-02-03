/*
 * Copyright (c) 2019 J&J's Consulting.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package consulting.jjs.sbe.marshal;

import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import consulting.jjs.sbe.model.template.AbstractTemplateType;
import consulting.jjs.sbe.model.template.DeserializedTemplate;
import consulting.jjs.sbe.model.template.MessagesSchema;
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
        return XML_MAPPER.readValue(input, MessagesSchema.class);
      } catch (IOException e) {
        throw new RuntimeException(DESERIALIZATION_ERROR, e);
      }
    });

  }

  public DeserializedTemplate unmarshal(InputStream inputStream) {
    return unmarshal(() -> {
      try {
        return XML_MAPPER.readValue(inputStream, MessagesSchema.class);
      } catch (IOException e) {
        throw new RuntimeException(DESERIALIZATION_ERROR, e);
      }
    });
  }

  private void templateTypeConsumer(AbstractTemplateType type) {
    declaredTypes.declareType(type.getName(), type);
  }

  private DeserializedTemplate unmarshal(Supplier<MessagesSchema> messageSchemaSupplier) {
    declaredTypes = new DeclaredTypes();
    declaredComposedTypes = new HashMap<>();

    Arrays.stream(PRIMITIVE_TYPES).forEach(this::templateTypeConsumer);

    MessagesSchema messagesSchema = messageSchemaSupplier.get();
    messagesSchema.getTypes().forEach(types -> {
      types.getTypes().forEach(this::templateTypeConsumer);
      types.getEnums().forEach(this::templateTypeConsumer);
      types.getSets().forEach(this::templateTypeConsumer);
      types.getComposites().forEach(this::templateCompositeTypeConsumer);
    });
    linkCompositesReferences(messagesSchema);
    return new DeserializedTemplate(messagesSchema, declaredTypes, declaredComposedTypes);
  }

  private void linkCompositesReferences(MessagesSchema messagesSchema) {
    messagesSchema.getTypes().stream()
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
