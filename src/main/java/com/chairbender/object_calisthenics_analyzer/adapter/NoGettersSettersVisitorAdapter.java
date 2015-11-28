package com.chairbender.object_calisthenics_analyzer.adapter;

import com.chairbender.object_calisthenics_analyzer.violation.NoGettersSettersViolation;
import com.chairbender.object_calisthenics_analyzer.violation.SingleLevelOfIndentationViolation;
import com.chairbender.object_calisthenics_analyzer.violation.ViolationMonitor;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.body.ModifierSet;
import com.github.javaparser.ast.expr.AssignExpr;
import com.github.javaparser.ast.expr.Expression;
import com.github.javaparser.ast.expr.FieldAccessExpr;
import com.github.javaparser.ast.expr.NameExpr;
import com.github.javaparser.ast.stmt.*;
import com.github.javaparser.ast.type.VoidType;

import java.io.File;
import java.util.List;


/**
 * Checks that the class doesn't define any obvious getter or setter methods.
 *
 * It detects these by checking to see if a non-private method simply sets the value of a field
 * or retrieves the value of a member field.
 *
 * Created by chairbender on 11/21/2015.
 */
public class NoGettersSettersVisitorAdapter extends CalisthenicsVisitorAdapter{

    /**
     *
     * @param violationMonitor violation monitor to report violations to
     * @param file file being examined
     */
    public NoGettersSettersVisitorAdapter(ViolationMonitor violationMonitor, File file) {
        super(violationMonitor, file);
    }

    @Override
    public void visit(MethodDeclaration methodDeclaration, Object arg) {
        //criteria for detecting a getter:
        if (methodDeclaration.getBody() != null) {
            checkForGetter(methodDeclaration);
            checkForSetter(methodDeclaration);
        }
        super.visit(methodDeclaration, arg);

    }

    /**
     * Checks that the method isn't an obvious getter, based
     * on the following criteria:
     1. Non-private
     2. One line - return <someMember>;
     3. No arguments
     4. Non-void return type

     If it IS a getter, reports a violation
     * @param methodDeclaration method to check
     */
    private void checkForGetter(MethodDeclaration methodDeclaration) {
        if (!ModifierSet.isPrivate(methodDeclaration.getModifiers()) &&
                (methodDeclaration.getParameters() == null || methodDeclaration.getParameters().size() == 0) &&
                !(methodDeclaration.getType() instanceof VoidType)) {
            if (methodDeclaration.getBody() != null) {
                BlockStmt body = methodDeclaration.getBody();
                if (body.getStmts().size() == 1) {
                    Statement bodyStatement = body.getStmts().get(0);
                    if (bodyStatement instanceof ReturnStmt) {
                        ReturnStmt returnStmt = (ReturnStmt) bodyStatement;
                        Expression returnExpression = returnStmt.getExpr();
                        //check that it only returns a member variable
                        if (returnExpression instanceof NameExpr ||
                                returnExpression instanceof FieldAccessExpr) {
                            reportViolation(new NoGettersSettersViolation(file,methodDeclaration));
                        }
                    }
                }
            }
        }
    }

    /**
     * Checks that the method isn't an obvious getter, based
     * on the following criteria:
     1. Non-private
     2. One argument
     3. One statement - this.member = argument


     If it IS a setter, reports a violation
     * @param methodDeclaration method to check
     */
    private void checkForSetter(MethodDeclaration methodDeclaration) {
        if (!ModifierSet.isPrivate(methodDeclaration.getModifiers()) &&
                (methodDeclaration.getParameters() != null && methodDeclaration.getParameters().size() == 1)) {
            String paramName = methodDeclaration.getParameters().get(0).getId().getName();
            if (methodDeclaration.getBody() != null) {
                BlockStmt body = methodDeclaration.getBody();
                if (body.getStmts().size() == 1) {
                    Statement bodyStatement = body.getStmts().get(0);
                    if (bodyStatement instanceof ExpressionStmt) {
                        ExpressionStmt expressionStmt = (ExpressionStmt) bodyStatement;
                        Expression expression = expressionStmt.getExpression();
                        //check that it only sets the value of a member
                        if ((expression instanceof NameExpr ||
                                expression instanceof FieldAccessExpr ||
                        expression instanceof AssignExpr) &&
                                expression.toStringWithoutComments().contains(paramName)) {
                            reportViolation(new NoGettersSettersViolation(file,methodDeclaration));
                        }
                    }
                }
            }
        }
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
