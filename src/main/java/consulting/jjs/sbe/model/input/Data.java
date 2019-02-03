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

import java.util.Arrays;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.stream.Stream;

public class Data extends AbstractField {

  private final String       strValue;   // char array
  private final List<String> arrayValue; // generic array
  private       Integer      length;

  public Data(String name, String strValue) {
    super(name);
    this.strValue = strValue;

    length = strValue.length();
    arrayValue = null;
  }

  public Data(String name, List<String> arrayValue) {
    super(name);
    this.arrayValue = arrayValue;

    length = arrayValue.size();
    strValue = null;
  }

  @Override
  public void consumeValue(BiConsumer<String, String> nameValueConsumer) {
    nameValueConsumer.accept(name + ".length", length.toString());

    Stream<String> values = strValue != null ? Arrays.stream(strValue.split("")) : arrayValue.stream();
    values.forEach(value -> nameValueConsumer.accept(name + ".varData", value));
  }
}
