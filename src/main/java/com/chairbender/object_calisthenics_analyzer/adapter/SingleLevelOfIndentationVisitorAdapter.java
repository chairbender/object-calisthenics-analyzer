package com.chairbender.object_calisthenics_analyzer.adapter;

import com.chairbender.object_calisthenics_analyzer.adapter.CalisthenicsVisitorAdapter;
import com.chairbender.object_calisthenics_analyzer.violation.SingleLevelOfIndentationViolation;
import com.chairbender.object_calisthenics_analyzer.violation.ViolationMonitor;
import com.github.javaparser.ast.PackageDeclaration;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.stmt.*;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;

import java.io.File;
import java.util.List;


/**
 * Checks that there is a single level of indentation per method.
 * We're not counting synchronized blocks or try statements.
 *
 * Created by chairbender on 11/21/2015.
 */
public class SingleLevelOfIndentationVisitorAdapter extends CalisthenicsVisitorAdapter{

    /**
     *
     * @param violationMonitor violation monitor to report violations to
     * @param file file being examined
     */
    public SingleLevelOfIndentationVisitorAdapter(ViolationMonitor violationMonitor, File file) {
        super(violationMonitor,file);
    }

    @Override
    public void visit(MethodDeclaration methodDeclaration, Object arg) {
        if (methodDeclaration.getBody() != null) {
            for (Statement statement : methodDeclaration.getBody().getStmts()) {
                //check for starting a block statement
                if (statement instanceof SwitchStmt) {
                    SwitchStmt switchStmt = (SwitchStmt) statement;
                    for (SwitchEntryStmt switchEntryStmt : switchStmt.getEntries()) {
                        reportExcessiveIndentation(switchEntryStmt.getStmts());
                    }
                } else if (statement instanceof IfStmt) {
                    Statement currentIf = statement;
                    do {
                        reportExcessiveIndentation(((IfStmt)currentIf).getThenStmt());
                        currentIf = ((IfStmt) currentIf).getElseStmt();
                    } while (currentIf instanceof IfStmt);
                } else if (statement instanceof WhileStmt) {
                    reportExcessiveIndentation(((WhileStmt)statement).getBody());
                } else if (statement instanceof ForeachStmt) {
                    reportExcessiveIndentation(((ForeachStmt)statement).getBody());
                } else if (statement instanceof ForStmt) {
                    reportExcessiveIndentation(((ForStmt)statement).getBody());
                }  else if (statement instanceof DoStmt) {
                    reportExcessiveIndentation(((DoStmt)statement).getBody());
                }
            }
        }
        super.visit(methodDeclaration,arg);

    }

    /**
     * Checks whether any statement opens a block. If so, reports it.
     *
     * @param stmts statements to check
     */
    private void reportExcessiveIndentation(List<Statement> stmts) {
        for (Statement statement : stmts) {
            reportExcessiveIndentation(statement);
        }
    }


    /**
     * Checks whether this statement opens a block. If so, reports it.
     * If statement IS a block, checks the statements inside the block.
     *
     * @param statement statement to check
     */
    private void reportExcessiveIndentation(Statement statement) {
        if (statement instanceof BlockStmt) {
            reportExcessiveIndentation(((BlockStmt)statement).getStmts());
        }
        if (statement instanceof SwitchStmt ||
                statement instanceof IfStmt ||
                statement instanceof WhileStmt ||
                statement instanceof ForStmt ||
                statement instanceof DoStmt ||
                statement instanceof ForeachStmt) {
            reportViolation(new SingleLevelOfIndentationViolation(getFile(),statement));
        }
    }


}
