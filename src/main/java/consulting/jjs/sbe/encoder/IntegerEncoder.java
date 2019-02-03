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
 * Corresponds to the {@code int32} and {@code uint32} type of SBE
 */
public abstract class IntegerEncoder implements TypeEncoder {

  /**
   * {@inheritDoc}
   */
  @Override
  public Integer getSize() {
    return 4;
  }

  /**
   * Corresponds to the {@code int32} type of SBE
   */
  public static class Signed extends IntegerEncoder {

    /**
     * {@inheritDoc}
     */
    @Override
    public void encode(String value, ByteBuffer buffer) {
      buffer.putInt(Integer.valueOf(value));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void encodeNull(ByteBuffer buffer) {
      buffer.putInt(Integer.MIN_VALUE);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void encodeMin(ByteBuffer buffer) {
      buffer.putInt(Integer.MIN_VALUE + 1); // precomputed at compile time
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void encodeMax(ByteBuffer buffer) {
      buffer.putInt(Integer.MAX_VALUE);
    }
  }

  /**
   * Corresponds to the {@code uint32} type of SBE
   */
  public static class Unsigned extends IntegerEncoder {
    /**
     * {@inheritDoc}
     */
    @Override
    public void encode(String value, ByteBuffer buffer) {
      buffer.putInt(Integer.parseUnsignedInt(value));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void encodeNull(ByteBuffer buffer) {
      buffer.putInt(-1);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void encodeMin(ByteBuffer buffer) {
      buffer.putInt(0);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void encodeMax(ByteBuffer buffer) {
      buffer.putInt(-2);
    }
  }

}
