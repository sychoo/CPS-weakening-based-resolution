// Mon Feb 14 15:14:21 EST 2022
// Simon Chu

package edu.cmu.stl.encoder.ints.util;

public class Encoding {
    StringBuilder sb = null;

    public Encoding () {
        this.sb = new StringBuilder();
    }

    public Encoding (String str)  {
        this.sb = new StringBuilder(str);
    }
     
    public void append(String str) {
        this.sb.append(str);
    }

    public String toString() {
        return this.sb.toString();
    }

    public void append(Encoding e) {
        this.sb.append(e.toString());
    }
}
