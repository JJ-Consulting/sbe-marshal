package consulting.jjs.sbe.store;

import consulting.jjs.sbe.marshal.DeclaredTypes;
import consulting.jjs.sbe.model.template.AbstractTemplateType;
import consulting.jjs.sbe.model.template.TemplateField;
import consulting.jjs.sbe.model.template.TemplateMessage;

import java.util.HashMap;
import java.util.Map;

public class MessageStore {

  private final Map<String, TemplateField> fields = new HashMap<>();

  public MessageStore(TemplateMessage message, DeclaredTypes declaredTypes,
                      Map<String, DeclaredTypes> declaredComposedTypes) {

    message.get().forEach(field -> {
      AbstractTemplateType type = declaredTypes.getDeclaredType(field.getTypeName());
      if (type != null) { // simple type
        field.setType(type);
        fields.put(field.getName(), field);
      } else { // composite type
        DeclaredTypes compositeTypes = declaredComposedTypes.get(field.getTypeName());
        compositeTypes.forEach((k, v) -> {
          TemplateField compositeField = new TemplateField();
          compositeField.setName(field.getName() + "." + k);
          compositeField.setType(v);
          fields.put(compositeField.getName(), compositeField);
        });
      }
    });

  }

  public TemplateField getTemplateFieldByName(String fieldName) {
    return fields.get(fieldName);
  }

}
