package consulting.jjs.sbe.model.input;

import java.util.function.BiConsumer;

public class SimpleFieldValue extends AbstractField {

  private String value;

  public SimpleFieldValue(String name, String value) {
    super(name);
    this.value = value;
  }

  @Override
  public void consumeValue(BiConsumer<String, String> nameValueConsumer) {
    nameValueConsumer.accept(name, value);
  }
}
