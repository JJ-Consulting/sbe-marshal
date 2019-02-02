package consulting.jjs.sbe.marshal;

import consulting.jjs.sbe.model.template.AbstractTemplateType;

import java.util.HashMap;
import java.util.Map;
import java.util.function.BiConsumer;

public class DeclaredTypes {

  Map<String, AbstractTemplateType> types = new HashMap<>();

  public void declareType(String typeName, AbstractTemplateType type) {
    types.put(typeName, type);
  }

  public AbstractTemplateType getDeclaredType(String typeName) {
    return types.get(typeName);
  }

  public void forEach(BiConsumer<String, AbstractTemplateType> biConsumer) {
    types.forEach(biConsumer);
  }

}
