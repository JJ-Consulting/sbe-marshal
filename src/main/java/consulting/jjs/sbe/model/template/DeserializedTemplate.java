package consulting.jjs.sbe.model.template;

import consulting.jjs.sbe.marshal.DeclaredTypes;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Map;

@Getter
@RequiredArgsConstructor
public class DeserializedTemplate {

  private final MessageSchema              messageSchema;
  private final DeclaredTypes              declaredTypes;
  private final Map<String, DeclaredTypes> declaredComposedTypes;

}
