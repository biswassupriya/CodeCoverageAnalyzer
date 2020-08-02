package com.coverage.analyzer;

import japa.parser.ASTHelper;
import japa.parser.JavaParser;
import japa.parser.ParseException;
import japa.parser.ast.CompilationUnit;
import japa.parser.ast.expr.IntegerLiteralExpr;
import japa.parser.ast.expr.MethodCallExpr;
import japa.parser.ast.expr.NameExpr;
import japa.parser.ast.expr.StringLiteralExpr;
import japa.parser.ast.stmt.ExpressionStmt;
import japa.parser.ast.stmt.Statement;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class CoverageTracker {
    // Maps filenames and line numbers to true (executed) or false (not executed).
    private static Map<String, Map<Integer, Boolean>> coverage =
            new HashMap<String, Map<Integer, Boolean>>();


    public static void markExecuted(String filename, int line) {
        if (!coverage.containsKey(filename)) {
            coverage.put(filename, new HashMap<Integer, Boolean>());
        }
        coverage.get(filename).put(line, true);
    }

    static {
        Runtime.getRuntime().addShutdownHook(new Thread() {
            @Override
            public void run() {
                writeCoverageToFile();
            }
        });
    }

    public static void main(String[] args) throws IOException, ParseException {
        File file = new File(args[0]);
        CompilationUnit unit = JavaParser.parse(new FileReader(file));
        unit.accept(new CoverageVisitor(file.getAbsolutePath()), null);
        System.out.println(unit.toString());
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


    private static void writeCoverageToFile() {
        String lcovCoverage = generateLcov();
        String coverageReportPath = System.getProperty("coverage.report.path", "coverage_report.lcov");
        FileWriter writer = null;
        try {
            writer = new FileWriter(coverageReportPath);
            writer.write(lcovCoverage);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                writer.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private static String generateLcov() {
        StringBuilder sb = new StringBuilder();
        for (String filename : coverage.keySet()) {
            sb.append("SF:" + filename + "\n");
            for (Map.Entry<Integer, Boolean> line : coverage.get(filename).entrySet()) {
                sb.append(String.format("DA:%d,%d\n", line.getKey(), line.getValue() ? 1 : 0));
            }
            sb.append("end_of_record\n");
        }
        return sb.toString();
    }
}
