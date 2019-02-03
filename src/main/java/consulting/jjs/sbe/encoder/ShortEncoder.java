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

package consulting.jjs.sbe.encoder;

import java.nio.ByteBuffer;

/**
 * Corresponds to the {@code int16} and {@code uint16} type of SBE
 */
public abstract class ShortEncoder implements TypeEncoder {

  /**
   * {@inheritDoc}
   */
  public Integer getSize() {return 2;}

  /**
   * Corresponds to the {@code int16} type of SBE
   */
  public static class Signed extends ShortEncoder {
    /**
     * {@inheritDoc}
     */
    @Override
    public void encode(String value, ByteBuffer buffer) {
      buffer.putShort(Short.valueOf(value));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void encodeNull(ByteBuffer buffer) {
      buffer.putShort(Short.MIN_VALUE);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void encodeMin(ByteBuffer buffer) {
      buffer.putShort((short)(Short.MIN_VALUE + 1));  // precomputed at compile time
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void encodeMax(ByteBuffer buffer) {
      buffer.putShort(Short.MAX_VALUE);
    }

  }

  /**
   * Corresponds to the {@code uint16} type of SBE
   */
  public static class Unsigned extends ShortEncoder {
    /**
     * {@inheritDoc}
     */
    @Override
    public void encode(String value, ByteBuffer buffer) {
      int intValue = Integer.valueOf(value);
      buffer.putShort((short) intValue);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void encodeNull(ByteBuffer buffer) {
      buffer.putShort((short)-1);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void encodeMin(ByteBuffer buffer) {
      buffer.putShort((short)0);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void encodeMax(ByteBuffer buffer) {
      buffer.putShort((short)-2);
    }

  }

}
