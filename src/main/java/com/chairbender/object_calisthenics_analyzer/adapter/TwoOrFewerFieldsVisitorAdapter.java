package com.chairbender.object_calisthenics_analyzer.adapter;

import com.chairbender.object_calisthenics_analyzer.violation.TwoOrFewerFieldsViolation;
import com.chairbender.object_calisthenics_analyzer.violation.ViolationMonitor;
import com.chairbender.object_calisthenics_analyzer.violation.WrapAllPrimitivesAndStringsViolation;
import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.FieldDeclaration;
import com.github.javaparser.ast.body.ModifierSet;
import com.github.javaparser.ast.type.ClassOrInterfaceType;
import com.github.javaparser.ast.type.PrimitiveType;
import com.github.javaparser.ast.type.ReferenceType;
import com.github.javaparser.ast.type.Type;

import java.io.File;

/**
 * Checks that each class has only two or less fields/members. Ignores static fields.
 *
 * Created by chairbender on 11/22/2015.
 */
public class TwoOrFewerFieldsVisitorAdapter extends CalisthenicsVisitorAdapter {
    /**
     * @param violationMonitor violation monitor that violations will be reported to
     * @param file             file being visited
     */
    public TwoOrFewerFieldsVisitorAdapter(ViolationMonitor violationMonitor, File file) {
        super(violationMonitor, file);
    }

    @Override
    public void visit(ClassOrInterfaceDeclaration classOrInterfaceDeclaration, Object arg) {
        int numFields = 0;
        for (Node childDeclaration : classOrInterfaceDeclaration.getChildrenNodes()) {
            if (childDeclaration instanceof FieldDeclaration) {
                if (!ModifierSet.isStatic(((FieldDeclaration) childDeclaration).getModifiers())) {
                    numFields++;
                }
            }
        }
        if (numFields > 2) {
            reportViolation(new TwoOrFewerFieldsViolation(file,classOrInterfaceDeclaration));
        }
        super.visit(classOrInterfaceDeclaration,arg);

    }
}
