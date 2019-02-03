/*
 * Copyright (c) 2019 J&J's Consulting.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

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

  public ArrayFieldValue addValue(Object value) {
    values.add(value.toString());
    return this;
  }

  @Override
  public void consumeValue(BiConsumer<String, String> nameValueConsumer) {
    values.forEach(v -> nameValueConsumer.accept(name, v));
  }
}
