package happynewmoonwithreport.type;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;

import static org.junit.Assert.assertEquals;

public class VarInt32LoopTest {

    Map<Integer, byte[]> problemChildren;
    Random random;

    @Before
    public void setUp() throws Exception {
        problemChildren = new HashMap<>();
        setupProblemChildren();
        random = new Random(System.currentTimeMillis());

    }

    public void setupProblemChildren() {
        problemChildren.put(-624485, new byte[]{(byte) 0x9B, (byte) 0xF1, (byte) 0x59});
        problemChildren.put(0, new byte[]{0x00});
        problemChildren.put(1, new byte[]{0x01});
        problemChildren.put(2, new byte[]{0x02});
        problemChildren.put(-1, new byte[]{0x7F});
        problemChildren.put(134217728, new byte[]{(byte) 0x80, (byte) 0x80, (byte) 0x80, (byte) 0xC0});

    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void testProblemChildren() throws Exception {
        for (Entry<Integer, byte[]> child : problemChildren.entrySet()) {

            VarInt32 varInt32 = new VarInt32(child.getValue(), 0);
            Integer result = varInt32.value();

            assertEqualHex( child.getKey(), result);
        }
    }

    @Test
    public void testProblemChildrenWrite() throws Exception {
        for (Entry<Integer, byte[]> child : problemChildren.entrySet()) {

            VarInt32 varInt32 = new VarInt32(child.getKey());
            ByteOutput result = varInt32.convert();

            assertArrayEqualsJDH(child.getValue(), result.bytes());
        }
    }

    public void assertArrayEqualsJDH(byte[] expected, byte[] actual) {
        Integer length = Math.min(expected.length, actual.length);
        Boolean equal = true;
        for (int i = 0; i < length; i++) {
            if (expected[i] == 0 || actual[i] == 0) {
                break;
            }
            if (expected[i] != actual[i]) {
                equal = false;
                throw new AssertionError(
                        "Array not equals" + "expected " + expected.toString() + " actual = " + actual.toString());
            }
        }
    }

    private void assertEqualHex(Integer expected, Integer result) {
        assertEquals("expected = " + expected.toString() + " hex = " + Integer.toHexString(expected), expected, result);
    }

    Integer maxCount = 1_000_000;

    @Test
    public void testReadUnsignedConstrutor2() throws Exception {
        for (Integer j = 0; j < maxCount; j++) {
            Integer i = random.nextInt();

            VarInt32 expected = new VarInt32(i);
            ByteArrayByteOutput out = (ByteArrayByteOutput) expected.convert();

            ByteInput in = new ByteArrayByteInput(out.bytes());
            VarInt32 varInt32_b = new VarInt32(in);
            Integer result_b = varInt32_b.value();

            assertEqualHex (i, result_b);
        }
    }

}