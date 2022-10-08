package edu.cmu.stl.encoder.ints.encoder;

import java.util.ArrayList;

import edu.cmu.stl.encoder.ints.util.EmitEncodingResult;
import edu.cmu.stl.encoder.ints.util.Encoding;

// 2022-03-16 11:52:37
public abstract class AST {
    String obj = null; // store extended object name
    public AST(String obj) {
        this.obj = obj;
    }

    public String toString() {
        return "(" + this.obj + ")";
    }

    // isWeak flag decide if the AST encode the weakened formula
    abstract public EmitEncodingResult emitEncoding(int time, String rhoVar, boolean isWeak); 

    // negation (Id, Int are exceptions, throw runtime exception)
    abstract public AST negate();
}


//=====================================
//============= Int, Id ===============
//=====================================


//=====================================
//======== Arithmetic Expr ============
//=====================================


//=====================================
//======== Comparison Expr ============
//=====================================



//=====================================
//=========== Logic Expr ==============
//=====================================



//=====================================
//======== Temporal Expr ==============
//=====================================
