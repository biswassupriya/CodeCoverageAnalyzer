package com.coverage.analyzer;

import japa.parser.ASTHelper;
import japa.parser.JavaParser;
import japa.parser.ParseException;
import japa.parser.ast.CompilationUnit;
import japa.parser.ast.Node;
import japa.parser.ast.expr.IntegerLiteralExpr;
import japa.parser.ast.expr.MethodCallExpr;
import japa.parser.ast.expr.NameExpr;
import japa.parser.ast.expr.StringLiteralExpr;
import japa.parser.ast.stmt.BlockStmt;
import japa.parser.ast.stmt.ExpressionStmt;
import japa.parser.ast.stmt.Statement;
import japa.parser.ast.visitor.ModifierVisitorAdapter;

import java.io.StringReader;

public class CoverageVisitor extends ModifierVisitorAdapter<Object> {
  // AST nodes don't know which file they come from, so we'll pass the information in
  private String filename;

  public CoverageVisitor(String filename) {
    this.filename = filename;
  }

  @Override
  public Node visit(ExpressionStmt node, Object arg) {
    BlockStmt block = new BlockStmt();
    ASTHelper.addStmt(block, node);
    ASTHelper.addStmt(block, makeCoverageTrackingCall(filename, node.getBeginLine()));
    return block;
  }

  private Statement makeCoverageTrackingCall(String filename, int line) {
    NameExpr coverageTracker = ASTHelper.createNameExpr("com.coverage.analyzer.runtime.CoverageTracker");
    MethodCallExpr call = new MethodCallExpr(coverageTracker, "markExecuted");
    ASTHelper.addArgument(call, new StringLiteralExpr(filename));
    ASTHelper.addArgument(call, new IntegerLiteralExpr(String.valueOf(line)));
    return new ExpressionStmt(call);
  }

  public static void main(String[] args) throws ParseException {
    CompilationUnit unit = JavaParser.parse(new StringReader(new StringBuilder()
            .append("package com.coverage.analyzer;\n\n")
            .append("public class Hello {\n")
            .append("  public static void main(String[] args) {\n")
            .append("    System.out.println(\"hello, world\");\n")
            .append("    System.out.println(\"Japan, world\");\n")
            .append("  }\n")
            .append("}\n").toString()));

    unit.accept(new CoverageVisitor("/path/to/Hello.java"), null);

    System.out.println(unit.toString());
  }


}