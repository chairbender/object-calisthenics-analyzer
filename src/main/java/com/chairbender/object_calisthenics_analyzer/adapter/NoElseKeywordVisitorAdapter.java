package com.chairbender.object_calisthenics_analyzer.adapter;

import com.chairbender.object_calisthenics_analyzer.violation.NoElseStatementViolation;
import com.chairbender.object_calisthenics_analyzer.violation.SingleLevelOfIndentationViolation;
import com.chairbender.object_calisthenics_analyzer.violation.ViolationMonitor;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.stmt.*;

import java.io.File;
import java.util.List;


/**
 * Checks that there are no usages of "else"
 *
 * Created by chairbender on 11/21/2015.
 */
public class NoElseKeywordVisitorAdapter extends CalisthenicsVisitorAdapter{

    /**
     *
     * @param violationMonitor violation monitor to report violations to
     * @param file file being examined
     */
    public NoElseKeywordVisitorAdapter(ViolationMonitor violationMonitor, File file) {
        super(violationMonitor,file);
    }

    @Override
    public void visit(BlockStmt blockStmt, Object arg) {
        for (Statement statement : blockStmt.getStmts()) {
            checkStatement(statement);
        }
        super.visit(blockStmt,arg);
    }

    /**
     * Reports a violation if the statement violates the no else keyword rule.
     * @param toCheck statement to check for violations
     */
    private void checkStatement(Statement toCheck) {
        if (toCheck instanceof IfStmt) {
            //report all else statements
            Statement currentIf = toCheck;
            while (currentIf != null && currentIf instanceof IfStmt) {
                if (((IfStmt)currentIf).getElseStmt() != null) {
                    reportViolation(new NoElseStatementViolation(getFile(),((IfStmt)currentIf).getElseStmt()));
                }
                currentIf = ((IfStmt) currentIf).getElseStmt();
            }
        }
    }
}
