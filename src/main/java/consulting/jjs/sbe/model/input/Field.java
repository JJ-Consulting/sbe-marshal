package consulting.jjs.sbe.model.input;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Field {

  private Integer id;
  private String  name;
  private String  value;

  public Field(int id, String value) {
    this.id = id;
    this.name = null;
    this.value = value;
  }

  public Field(String name, String value) {
    this.id = null;
    this.name = name;
    this.value = value;
  }

}
