package consulting.jjs.sbe.model.input;

public abstract class AbstractField implements FieldValue {

  protected Integer id;
  protected String  name;

  public AbstractField(String name) {
    this.name = name;
  }

}
