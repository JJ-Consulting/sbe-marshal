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

package consulting.jjs.sbe;

import consulting.jjs.sbe.model.input.ArrayFieldValue;
import consulting.jjs.sbe.model.input.ComposedFieldValue;
import consulting.jjs.sbe.model.input.Data;
import consulting.jjs.sbe.model.input.Group;
import consulting.jjs.sbe.model.input.GroupValue;
import consulting.jjs.sbe.model.input.Message;
import consulting.jjs.sbe.model.input.SimpleFieldValue;
import org.junit.Test;

import java.io.InputStream;
import java.nio.ByteBuffer;
import java.util.Arrays;

public class SbeTest {

  @Test
  public void shouldEncode() {
    InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream("sbe-schema.xml");
    Message     message     = getMockedMessage();

    long       t1         = System.currentTimeMillis();
    Sbe        sbe        = new Sbe(inputStream);
    long       t2         = System.currentTimeMillis();
    ByteBuffer byteBuffer = sbe.encode(message);
    long       t3         = System.currentTimeMillis();

    System.out.println(t1);
    System.out.println(t2);
    System.out.println(t3);

    byteBuffer.flip();
    int i = 0;
    while (byteBuffer.hasRemaining()) {
      int unsignedByte = byteBuffer.get() & 0xFF;
      System.out.print(unsignedByte);
      System.out.print('|');
      ++i;
    }
    System.out.println("\n" + i);
  }

  private Message getMockedMessage() {
    return Message.builder()
            .name("Car")
            .fields(Arrays.asList(
                    new SimpleFieldValue("serialNumber", "1234"),
                    new SimpleFieldValue("modelYear", "2013"),
                    new SimpleFieldValue("available", "T"),
                    new SimpleFieldValue("code", "A"),
                    new ArrayFieldValue("someNumbers").addValue("0").addValue("1").addValue("2").addValue("3").addValue("4"),
                    new ArrayFieldValue("vehicleCode").addValue("a").addValue("b").addValue("c").addValue("d").addValue("e").addValue("f"),
                    new SimpleFieldValue("extras", "110"),
                    new ComposedFieldValue("engine")
                            .addFieldValue(new SimpleFieldValue("capacity", "2000"))
                            .addFieldValue(new SimpleFieldValue("numCylinders", "4"))
                            .addFieldValue(new ArrayFieldValue("manufacturerCode").addValue("1").addValue("2").addValue("3"))
                            .addFieldValue(new SimpleFieldValue("efficiency", "35"))
                            .addFieldValue(new SimpleFieldValue("boosterEnabled", "T"))
                            .addFieldValue(new ComposedFieldValue("booster")
                                    .addFieldValue(new SimpleFieldValue("BoostType", "NITROUS"))
                                    .addFieldValue(new SimpleFieldValue("horsePower", "200")))
                    )
            )
            .groups(Arrays.asList(
                    new Group("fuelFigures")
                            .addGroupValue(new GroupValue()
                                    .addFieldValue(new SimpleFieldValue("speed", "30"))
                                    .addFieldValue(new SimpleFieldValue("mpg", "35.9"))
                                    .addDataValue(new Data("usageDescription", "Urban Cycle")))
                            .addGroupValue(new GroupValue()
                                    .addFieldValue(new SimpleFieldValue("speed", "55"))
                                    .addFieldValue(new SimpleFieldValue("mpg", "49.0"))
                                    .addDataValue(new Data("usageDescription", "Combined Cycle")))
                            .addGroupValue(new GroupValue()
                                    .addFieldValue(new SimpleFieldValue("speed", "75"))
                                    .addFieldValue(new SimpleFieldValue("mpg", "40.0"))
                                    .addDataValue(new Data("usageDescription", "Highway Cycle"))),
                    new Group("performanceFigures")
                            .addGroupValue(new GroupValue()
                                    .addFieldValue(new SimpleFieldValue("octaneRating", "95"))
                                    .addGroup(new Group("acceleration")
                                            .addGroupValue(new GroupValue()
                                                    .addFieldValue(new SimpleFieldValue("mph", ""))
                                                    .addFieldValue(new SimpleFieldValue("seconds", "4.0")))))
                            .addGroupValue(new GroupValue()
                                    .addFieldValue(new SimpleFieldValue("octaneRating", "99")))
            ))
            .datas(Arrays.asList(
                    new Data("manufacturer", "Honda"),
                    new Data("model", "Civic VTi"),
                    new Data("activationCode", "abcdef")
            ))
            .build();
  }

}
