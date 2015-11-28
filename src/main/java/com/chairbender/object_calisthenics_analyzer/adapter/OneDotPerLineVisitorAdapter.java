package com.chairbender.object_calisthenics_analyzer.adapter;

import com.chairbender.object_calisthenics_analyzer.violation.NoElseStatementViolation;
import com.chairbender.object_calisthenics_analyzer.violation.OneDotPerLineViolation;
import com.chairbender.object_calisthenics_analyzer.violation.ViolationMonitor;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.expr.Expression;
import com.github.javaparser.ast.stmt.*;

import java.io.File;


/**
 * Checks that there is only one dot per line
 *
 * Created by chairbender on 11/21/2015.
 */
public class OneDotPerLineVisitorAdapter extends CalisthenicsVisitorAdapter{

    /**
     *
     * @param violationMonitor violation monitor to report violations to
     */
    public OneDotPerLineVisitorAdapter(ViolationMonitor violationMonitor) {
        super(violationMonitor);
    }

    @Override
    public void visit(BlockStmt blockStmt, Object arg) {
        for (Statement statement : blockStmt.getStmts()) {
            checkStatement(statement);
        }
        super.visit(blockStmt,arg);
    }

    /**
     * Looks at the statement for violations and reports any that are found.
     * @param toCheck statement to inspect
     */
    private void checkStatement(Statement toCheck) {
        if (toCheck == null) {
            return;
        }
        //Check that there is only one occurrence of the "." in the statement,
        //but ignore literals. Each type of statement needs to be handled differently
        if (toCheck instanceof AssertStmt ||
                toCheck instanceof ExplicitConstructorInvocationStmt ||
                toCheck instanceof ExpressionStmt ||
                toCheck instanceof ReturnStmt ||
                toCheck instanceof ThrowStmt) {
            reportDotCountViolation(toCheck.toString(),toCheck);
        } else if (toCheck instanceof DoStmt) {
            reportDotCountViolation(((DoStmt)toCheck).getCondition(),toCheck);
        } else if (toCheck instanceof ForeachStmt) {
            reportDotCountViolation(((ForeachStmt) toCheck).getIterable(), toCheck);
        }  else if (toCheck instanceof ForStmt) {
            for (Expression initExpression : ((ForStmt)toCheck).getInit()) {
                reportDotCountViolation(initExpression,toCheck);
            }
            for (Expression updateExpression : ((ForStmt)toCheck).getUpdate()) {
                reportDotCountViolation(updateExpression,toCheck);
            }
            reportDotCountViolation(((ForStmt) toCheck).getCompare(), toCheck);
        } else if (toCheck instanceof IfStmt) {
            reportDotCountViolation(((IfStmt)toCheck).getCondition(),toCheck);
            checkStatement(((IfStmt) toCheck).getElseStmt());
            checkStatement(((IfStmt)toCheck).getThenStmt());
        } else if (toCheck instanceof LabeledStmt) {
            checkStatement(((LabeledStmt) toCheck).getStmt());
        } else if (toCheck instanceof SwitchEntryStmt) {
            for (Statement statement : ((SwitchEntryStmt)toCheck).getStmts()) {
                checkStatement(statement);
            }
            reportDotCountViolation(((SwitchEntryStmt) toCheck).getLabel(), toCheck);
        } else if (toCheck instanceof SwitchStmt) {
            for (SwitchEntryStmt statement : ((SwitchStmt)toCheck).getEntries()) {
                checkStatement(statement);
            }
            reportDotCountViolation(((SwitchStmt)toCheck).getSelector(),toCheck);
        } else if (toCheck instanceof SynchronizedStmt) {
            reportDotCountViolation(((SynchronizedStmt)toCheck).getExpr(),toCheck);
        } else if (toCheck instanceof WhileStmt) {
            reportDotCountViolation(((WhileStmt) toCheck).getCondition(), toCheck);
        }
    }

    /**
     * Reports a violation if toCheck has more than one dot. Reports the violation as
     * having occurred on statement
     * @param toCheck object which will be turned into a string and checked for a dot count violation.
     *                if null, will just be ignored
     * @param statement statement to report if a violation occurs
     */
    private void reportDotCountViolation(Object toCheck,Statement statement) {
        if (toCheck != null) {
        if (dotCount(toCheck.toString()) > 1) {
            reportViolation(new OneDotPerLineViolation(statement));

        }   }
    }

    /**
     *
     * @param s string to check
     * @return the number of dots in the string, excluding dots within quotes
     */
    private static int dotCount(String s) {

        int dotCount = 0;
        boolean inQuotes = false;
        char prevChar = '\0';
        for (char currentChar : s.toCharArray()) {
            //ignore anything inside an unescaped pair of quotes
            if (currentChar == '\"' &&
                    prevChar != '\\') {
                inQuotes = !inQuotes;
            }
            if (!inQuotes) {
                if (currentChar == '.') {
                    dotCount++;
                }
            }

            prevChar = currentChar;
        }

        return dotCount;
    }


}
