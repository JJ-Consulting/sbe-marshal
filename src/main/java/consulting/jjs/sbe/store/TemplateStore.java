package consulting.jjs.sbe.store;

import consulting.jjs.sbe.model.template.TemplateField;
import consulting.jjs.sbe.model.template.TemplateMessage;
import consulting.jjs.sbe.model.template.TemplateType;

import java.util.Map;
import java.util.stream.Collectors;

public class TemplateStore {

  private final Map<String, TemplateField> fields;

  public TemplateStore(TemplateMessage message, Map<String, TemplateType> declaredTypes) {
    fields = message.get().collect(Collectors.toMap(TemplateField::getName, f -> {
      f.setType(declaredTypes.get(f.getTypeName()));
      return f;
    }));
  }

  public TemplateField getTemplateFieldByName(String fieldName) {
    return fields.get(fieldName);
  }


}
