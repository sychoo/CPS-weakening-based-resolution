// 2022-03-06 15:14:18
// Simon Chu

// Custom class for return results

package edu.cmu.stl.encoder.floats.util;

import edu.cmu.stl.encoder.floats.util.Encoding;

public class EmitEncodingResult {
    private Encoding encoding;
    private Encoding debugEncoding;

    public EmitEncodingResult(Encoding encoding) {
        this.encoding = encoding;
        this.debugEncoding = null;        
    }

    public EmitEncodingResult(Encoding encoding, Encoding debugEncoding) {
        this.encoding = encoding;
        this.debugEncoding = debugEncoding;
    }

    public Encoding getEncoding() {
        return this.encoding;
    }


    public Encoding getDebugEncoding() {
        return this.debugEncoding;
    }
}