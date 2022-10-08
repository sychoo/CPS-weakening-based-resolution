package edu.cmu.stl.encoder.floats.parsing.ast;

import java.util.ArrayList;

import edu.cmu.stl.encoder.floats.util.EmitEncodingResult;
import edu.cmu.stl.encoder.floats.util.Encoding;

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

    // get all the existing signal variables in the AST
    abstract public ArrayList<String> getSignalVariables();

    // get the signal length
    abstract public int getSignalLength();
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
