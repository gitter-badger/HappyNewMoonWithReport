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
 * An unsigned integer of 16 bits. .
 */
public class UInt16 extends UInt<Integer> {

    public UInt16(BytesFile bytesFile) {
        assert (bytesFile.longEnough(minBytes()));
        value = convert(bytesFile);
    }

    public UInt16(Integer value) {
        this.value = value;
    }

    public UInt16(DataTypeNumber number) {
        this.value = number.integerValue();
    }

    public Integer convert(BytesFile bytesFile) {
        Integer result = 0;
        // little Endian!
        for (Integer i = 0; i < maxBits(); i = i + 8) {
            result += Byte.toUnsignedInt(bytesFile.readByte()) << i;
        }
        return result;
    }


	/* private functions **/

	/* Override DataTypeNumber */

    @Override
    public Integer maxBits() {
        return 16;
    }

    @Override
    public Integer minValue() {
        return 0;
    }

    @Override
    public Integer maxValue() {
        return 1 << maxBits();
    }

	/* override of Object **/

    @Override
    public String toString() {
        return "UInt16{" +
                "value=" + value +
                "} ";
    }

}
