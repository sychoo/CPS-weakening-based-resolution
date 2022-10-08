// 2022-03-16 13:21:02
package edu.cmu.stl.encoder.ints.util;
import edu.cmu.stl.encoder.ints.encoder.AST;
import edu.cmu.stl.encoder.ints.encoder.ast.*;

import org.junit.Test;

public class ASTTest {
    @Test
    public void run() {
      AST intAST = new Int("1");
      AST idAST = new Id("var");
      AST arithAST = new ArithExpr("-", intAST, idAST);
    }
}
