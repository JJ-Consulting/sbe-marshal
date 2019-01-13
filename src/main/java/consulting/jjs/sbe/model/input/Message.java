package consulting.jjs.sbe.model.input;

import java.util.ArrayList;
import java.util.List;

public class Message {

  private Integer messageSchemaIndex;
  private String messageSchemaName;
  private String messageSchemaId;

  public Message(Integer messageSchemaIndex) {
    this.messageSchemaIndex = messageSchemaIndex;
  }

  public Message(String messageSchemaName, String messageSchemaId) {
    this.messageSchemaName = messageSchemaName;
    this.messageSchemaId = messageSchemaId;
  }

  private final List<Field> fields = new ArrayList<>();

  public Message addField(Field field) {
    this.fields.add(field);
    return this;
  }

}
