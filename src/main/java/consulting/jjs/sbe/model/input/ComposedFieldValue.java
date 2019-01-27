package consulting.jjs.sbe.model.input;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiConsumer;

public class ComposedFieldValue extends AbstractField {

  private List<FieldValue> fieldValues = new ArrayList<>();

  public ComposedFieldValue(String name) {
    super(name);
  }

  public ComposedFieldValue addFieldValue(FieldValue fieldValue) {
    this.fieldValues.add(fieldValue);
    return this;
  }

  @Override
  public void consumeValue(BiConsumer<String, String> nameValueConsumer) {
    fieldValues.forEach(fieldValue -> fieldValue.consumeValue(nameValueConsumer));
  }
}
