package consulting.jjs.sbe.model.input;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Message {

  private String           name;
  private Integer          id;
  private List<FieldValue> fields = new ArrayList<>();

  public Message(Integer id) {
    this.id = id;
  }

  public Message(String name) {
    this.name = name;
  }

}
