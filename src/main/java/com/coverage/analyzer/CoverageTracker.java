/*
package com.coverage.analyzer;

import japa.parser.ASTHelper;
import japa.parser.ast.expr.IntegerLiteralExpr;
import japa.parser.ast.expr.MethodCallExpr;
import japa.parser.ast.expr.NameExpr;
import japa.parser.ast.expr.StringLiteralExpr;
import japa.parser.ast.stmt.ExpressionStmt;
import japa.parser.ast.stmt.Statement;

import java.util.HashMap;
import java.util.Map;

public class CoverageTracker {
    // Maps filenames and line numbers to true (executed) or false (not executed).
    private static Map<String, Map<Integer, Boolean>> coverage =
            new HashMap<String, Map<Integer, Boolean>>();

    // Serializes coverage in some format; we'll revisit this.
    public static void writeCoverageToFile() { }

    public static void markExecuted(String filename, int line) {
        if (!coverage.containsKey(filename)) {
            coverage.put(filename, new HashMap<Integer, Boolean>());
        }
        coverage.get(filename).put(line, true);
    }

    static {
        Runtime.getRuntime().addShutdownHook(new Thread() {
            @Override public void run() {
                writeCoverageToFile();
            }
        });
    }

    private Statement makeCoverageTrackingCall(String filename, int line) {
        CoverageTracker.markExecutable(filename, line);
        NameExpr coverageTracker = ASTHelper.createNameExpr("com.coverage.analyzer.runtime.CoverageTracker");
        MethodCallExpr call = new MethodCallExpr(coverageTracker, "markExecuted");
        ASTHelper.addArgument(call, new StringLiteralExpr(filename));
        ASTHelper.addArgument(call, new IntegerLiteralExpr(String.valueOf(line)));
        return new ExpressionStmt(call);
    }

    public static void markExecutable(String filename, int line) {
        if (!coverage.containsKey(filename)) {
            coverage.put(filename, new HashMap<Integer, Boolean>());
        }
        coverage.get(filename).put(line, false);
    }
}
*/
