/*
package com.coverage.analyzer;

import japa.parser.ASTHelper;
import japa.parser.JavaParser;
import japa.parser.ParseException;
import japa.parser.ast.CompilationUnit;
import japa.parser.ast.body.MethodDeclaration;
import japa.parser.ast.body.TypeDeclaration;
import japa.parser.ast.expr.MethodCallExpr;
import japa.parser.ast.expr.NameExpr;
import japa.parser.ast.expr.StringLiteralExpr;
import japa.parser.ast.visitor.VoidVisitorAdapter;

import java.io.StringReader;

public class HelloJavaparser {
    public static void main(String[] args) throws ParseException {
        CompilationUnit unit = JavaParser.parse(new StringReader(new StringBuilder()
                .append("package io.badawi.hello;\n\n")
                .append("public class Hello {\n")
                .append("  public static void main(String[] args) {\n")
                .append("    System.out.println(\"hello, world\");\n")
                .append("  }\n")
                .append("}\n").toString()));

        // Create an AST fragment representing System.out.println("hello, javaparser")
        NameExpr systemOut = ASTHelper.createNameExpr("System.out");
        MethodCallExpr call = new MethodCallExpr(systemOut, "println");
        ASTHelper.addArgument(call, new StringLiteralExpr("hello, javaparser"));

        // Add this statement to the main method
        TypeDeclaration helloClass = unit.getTypes().get(0);
        MethodDeclaration mainMethod = (MethodDeclaration) helloClass.getMembers().get(0);
        ASTHelper.addStmt(mainMethod.getBody(), call);

       // System.out.println(unit.toString());

        unit.accept(new VoidVisitorAdapter<Object>() {
            @Override
            public void visit(MethodCallExpr node, Object arg) {
                System.out.println("found method call: " + node.toString());
            }
        }, null);

    }
}*/
