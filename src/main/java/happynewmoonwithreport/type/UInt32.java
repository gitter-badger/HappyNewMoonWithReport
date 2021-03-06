/*
 *  Copyright 2017 Whole Bean Software, LTD.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 *
 */
package happynewmoonwithreport.type;

import happynewmoonwithreport.BytesFile;

/**
 * An unsigned integer of 32 bits.
 */
public class UInt32 extends UInt<Long> {

    public UInt32() {
        super();
    }

    public UInt32(BytesFile bytesFile) {
        assert (bytesFile.longEnough(minBytes()));
        value = convert(bytesFile);
    }

    public UInt32(byte in1, byte in2, byte in3, byte in4) {
        byte[] byteAll = new byte[]{in1, in2, in3, in4};
        BytesFile bytesFile = new BytesFile(byteAll);
        value = convert(bytesFile);
    }

    public UInt32(byte[] in) {
        if (minBytes() < in.length) {
            throw new IllegalArgumentException("Must be length of 4");
        }
        BytesFile bytesFile = new BytesFile(in);
        value = convert(bytesFile);
    }

    public UInt32(Long value) {
        this.value = value;
    }

    public UInt32(Integer value) {
        this.value = value.longValue();
    }

    public Long convert(BytesFile bytesFile) {
        Long result = 0L;
        // little Endian!
        for (Integer i = 0; i < maxBits(); i = i + 8) {
            result += Byte.toUnsignedLong(bytesFile.readByte()) << i;
        }
        return result;
    }

    @Override
    public Integer integerValue() {
        checkIfTooLarge();
        return value.intValue();
    }

    @Override
    public Boolean isBoundByInteger() {
        return (Integer.MIN_VALUE <= value && value <= Integer.MAX_VALUE);
    }

    public void checkIfTooLarge() {
        if (isBoundByInteger() == false) {
            throw new RuntimeException("Value is too large!");
        }
    }


	/* Override DataTypeNumber */

    @Override
    public Integer maxBits() {
        return 32;
    }

    @Override
    public Long minValue() {
        return 0L;
    }

    @Override
    public Long maxValue() {
        return 1L << maxBits();
    }

	/* override of Object **/

    @Override
    public String toString() {
        return "UInt32{" +
                "value=" + value +
                "} ";
    }
}
