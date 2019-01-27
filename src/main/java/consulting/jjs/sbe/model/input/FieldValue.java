package consulting.jjs.sbe.model.input;

import java.util.function.BiConsumer;

public interface FieldValue {

  void consumeValue(BiConsumer<String, String> nameValueConsumer);

}
