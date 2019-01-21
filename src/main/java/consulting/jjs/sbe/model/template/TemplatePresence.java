package consulting.jjs.sbe.model.template;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum TemplatePresence {

  @JsonProperty("required")
  REQUIRED,
  @JsonProperty(value = "optional")
  OPTIONAL,
  @JsonProperty(value = "constant")
  CONSTANT

}
