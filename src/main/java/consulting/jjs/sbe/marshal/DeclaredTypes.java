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

package consulting.jjs.sbe.marshal;

import consulting.jjs.sbe.model.template.AbstractTemplateType;

import java.util.HashMap;
import java.util.Map;
import java.util.function.BiConsumer;

public class DeclaredTypes {

  Map<String, AbstractTemplateType> types = new HashMap<>();

  public void declareType(String typeName, AbstractTemplateType type) {
    types.put(typeName, type);
  }

  public AbstractTemplateType getDeclaredType(String typeName) {
    return types.get(typeName);
  }

  public void forEach(BiConsumer<String, AbstractTemplateType> biConsumer) {
    types.forEach(biConsumer);
  }

}
