package consulting.jjs.sbe.model.input;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiConsumer;

public class ArrayFieldValue extends AbstractField {

  private List<String> values = new ArrayList<>();

  public ArrayFieldValue(String name) {
    super(name);
  }

  public ArrayFieldValue addValue(String value) {
    values.add(value);
    return this;
  }

  @Override
  public void consumeValue(BiConsumer<String, String> nameValueConsumer) {
    values.forEach(v -> nameValueConsumer.accept(name, v));
  }
}
