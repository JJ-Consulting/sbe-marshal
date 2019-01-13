package consulting.jjs.sbe.model.input;

import lombok.Getter;

@Getter
public class Field {

  private Integer typeIndex;
  private String typeName;
  private final String id;
  private final String name;
  private final String value;

  public Field(int typeIndex, String id, String name, String value) {
    this.typeIndex = typeIndex;
    this.id = id;
    this.name = name;
    this.value = value;
  }

  public Field(String typeName, String id, String name, String value) {
    this.typeName = typeName;
    this.id = id;
    this.name = name;
    this.value = value;
  }

}
