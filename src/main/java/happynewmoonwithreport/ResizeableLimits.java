package happynewmoonwithreport;

import happynewmoonwithreport.type.UInt32;
import happynewmoonwithreport.type.VarUInt1;
import happynewmoonwithreport.type.VarUInt32;

/**
 * <h3 id="resizable_limits"><code class="highlighter-rouge">resizable_limits</code></h3> <p>A packed tuple that
 * describes the limits of a <a href="../semantics/#table">table</a> or <a href="../semantics/#resizing">memory</a>:</p>
 * <p> <table> <thead> <tr> <th>Field</th> <th>Type</th> <th>Description</th> </tr> </thead> <tbody> <tr> <td>flags</td>
 * <td><code class="highlighter-rouge">varuint1</code></td> <td> <code class="highlighter-rouge">1</code> if the maximum
 * field is present, <code class="highlighter-rouge">0</code> otherwise</td> </tr> <tr> <td>initial</td> <td><code
 * class="highlighter-rouge">varuint32</code></td> <td>initial length (in units of table elements or wasm pages)</td>
 * </tr> <tr> <td>maximum</td> <td> <code class="highlighter-rouge">varuint32</code>?</td> <td>only present if specified
 * by <code class="highlighter-rouge">flags</code> </td> </tr> </tbody> </table> <p> <p>Note: In the <a
 * href="../future-features/#threads">future <img class="emoji" title=":unicorn:" alt=":unicorn:"
 * src="https://assets-cdn.github.com/images/icons/emoji/unicode/1f984.png" height="20" width="20"
 * align="absmiddle"></a>, the "flags" field may be changed to <code class="highlighter-rouge">varuint32</code>, e.g.,
 * to include a flag for sharing between threads.</p>
 * <p>
 * <p>
 * Source : <a href= "http://webassembly.org/docs/binary-encoding/#resizable_limits">http://webassembly.org/docs/binary-encoding/#resizable_limits</a>
 * <p>
 */

@Deprecated
public class ResizeableLimits {

    /**
     * does the limit have max?
     */
    private VarUInt1 flags;
    /**
     * length in wasm pages (64k)
     */
    private UInt32 initialLength;
    /**
     * length in wasm pages (64k)
     * <p>
     * usage:
     * <code>
     * if (flag == true) {
     * max = getMaximumLength(); {
     * else {
     * // There is no Maximum  (infinity)
     * <p>
     * }
     * }</code>
     */
    private UInt32 maximumLength;

    public ResizeableLimits() {

    }

    public ResizeableLimits(VarUInt1 flags, UInt32 initialLength, UInt32 maximumLength) {
        this.flags = flags;
        this.initialLength = initialLength;
        this.maximumLength = maximumLength;
    }

    public ResizeableLimits(BytesFile payload) {
        flags = new VarUInt1(payload);
        initialLength = new VarUInt32(payload);
        if (flags.isTrue()) {
            maximumLength = new VarUInt32(payload);
        }
    }

    public VarUInt1 getFlags() {
        return flags;
    }

    public UInt32 getInitialLength() {
        return initialLength;
    }

    /**
     * May be null!!
     *
     * @return
     */
    public UInt32 getMaximumLength() {
        return maximumLength;
    }
}
