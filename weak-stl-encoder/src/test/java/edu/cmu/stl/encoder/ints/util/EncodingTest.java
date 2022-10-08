// Mon Feb 14 15:18:57 EST 2022
// Simon Chu

// future TODO: connect Encoding module with JMiniZinc module
// https://github.com/siemens/JMiniZinc
package edu.cmu.stl.encoder.ints.util;

import org.junit.Test;

public class EncodingTest {
    @Test
    public void run() {
        // append string
        Encoding e = new Encoding();
        e.append("constraint a > b");
        System.out.println("the content of e is: ");
        System.out.println(e);
        assert ("constraint a > b".equals(e.toString()));

        // append encoding to another encoding
        Encoding e2 = new Encoding();
        e2.append("int signal_length = 2;");
        e2.append("\n");
        e2.append(e);
        System.out.println("the content of e2 is: ");
        System.out.println(e2);
        assert ("int signal_length = 2;\nconstraint a > b".equals(e2.toString()));

        // test Encoding constructor with string
        Encoding e3 = new Encoding("int signal_length = 2;\n");
        e3.append(e);
        System.out.println("the content of e3 is: ");
        System.out.println(e3);
        assert ("int signal_length = 2;\nconstraint a > b".equals(e2.toString()));
    }
}