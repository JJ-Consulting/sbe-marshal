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

package consulting.jjs.sbe.store;

import consulting.jjs.sbe.encoder.TypeEncoder;
import consulting.jjs.sbe.marshal.DeclaredTypes;
import consulting.jjs.sbe.model.template.AbstractTemplateType;
import consulting.jjs.sbe.model.template.TemplateField;
import consulting.jjs.sbe.model.template.TemplateGroup;
import consulting.jjs.sbe.model.template.TemplateMessage;
import lombok.Getter;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

/**
 * Stores consolidated data about a message from a template by setting relationship between message
 * {@link TemplateField}s and their {@link TypeEncoder}.
 */
public class MessageStore {

  private static final String VAR_STRING = "varStringEncoding";
  private static final String VAR_NUM    = "varDataEncoding";

  private static final Stream<TemplateField> SBE_FIELDS = Stream.of(
          new TemplateField("messageHeader", "messageHeader"),
          new TemplateField("groupSizeEncoding", "groupSizeEncoding")
  );

  private final Map<String, TemplateField> fields = new HashMap<>();
  @Getter
  private final Integer                    messageId;
  @Getter
  private       Integer                    messageSize;
  private       int                        messageCompositesSize;
  // group size in byte per group name
  private       Map<String, Integer>       groupsSize = new HashMap<>();

  public MessageStore(TemplateMessage message, DeclaredTypes declaredTypes,
                      Map<String, DeclaredTypes> declaredComposedTypes) {
    messageId = message.getId();
    messageCompositesSize = 0;
    message.get().forEach(field -> {
      AbstractTemplateType simpleType = declaredTypes.getDeclaredType(field.getTypeName());
      if (simpleType != null) { // simple type
        field.setType(simpleType);
        fields.put(field.getName(), field);
      } else { // composite type
        mangleComposite(declaredComposedTypes, field, false);
      }
    });
    SBE_FIELDS.forEach(field -> mangleComposite(declaredComposedTypes, field, true));

    computeSizes(message);

  }

  private void computeSizes(TemplateMessage message) {
    // body message size
    messageSize = messageCompositesSize + message.getSize();
    // groups sizes
    message.getGroups().stream().forEach(this::computeGroupSize);
  }

  private void computeGroupSize(TemplateGroup group) {
    groupsSize.put(group.getName(), group.getSize());
    group.getGroups().forEach(this::computeGroupSize);
  }

  private void mangleComposite(Map<String, DeclaredTypes> declaredComposedTypes, TemplateField field, boolean header) {
    String        typeName           = field.getTypeName();
    DeclaredTypes compositeTypes     = declaredComposedTypes.get(typeName);
    boolean       variableDataLength = VAR_STRING.equals(typeName) || VAR_NUM.equals(typeName);
    compositeTypes.forEach((compositeName, composedType) -> {
      TemplateField compositeField = new TemplateField();
      compositeField.setName(field.getName() + "." + compositeName);
      // header fields and variable length data are not taken into account for body size
      if (!header && !variableDataLength) {
        messageCompositesSize += composedType.getSize();
      }
      compositeField.setType(composedType);
      fields.put(compositeField.getName(), compositeField);
    });
  }

  public TemplateField getTemplateFieldByName(String fieldName) {
    return fields.get(fieldName);
  }

  public Integer getGroupSize(String groupName) {
    return groupsSize.get(groupName);
  }

}
