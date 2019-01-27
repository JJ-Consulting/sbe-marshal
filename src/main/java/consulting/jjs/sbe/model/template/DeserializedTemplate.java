package consulting.jjs.sbe.model.template;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Map;

@Getter
@RequiredArgsConstructor
public class DeserializedTemplate {

  private final MessageSchema                     messageSchema;
  private final Map<String, AbstractTemplateType> declaredTypes;
  private final Map<String, AbstractTemplateType> declaredComposedTypes;

}
