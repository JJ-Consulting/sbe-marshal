package consulting.jjs.sbe.store;

import consulting.jjs.sbe.model.template.AbstractTemplateType;
import consulting.jjs.sbe.model.template.TemplateField;
import consulting.jjs.sbe.model.template.TemplateMessage;

import java.util.Map;
import java.util.stream.Collectors;

public class TemplateStore {

  private final Map<String, TemplateField> fields;

  public TemplateStore(TemplateMessage message, Map<String, AbstractTemplateType> declaredTypes,
                       Map<String, AbstractTemplateType> declaredComposedTypes) {
    fields = message.get().collect(Collectors.toMap(TemplateField::getName, f -> {
      f.setType(declaredTypes.get(f.getTypeName()));
      return f;
    }));

    declaredComposedTypes.forEach((k, v) -> {
      TemplateField field = new TemplateField();
      field.setType(v);
      fields.put(k, field);
    });

  }

  public TemplateField getTemplateFieldByName(String fieldName) {
    return fields.get(fieldName);
  }


}
