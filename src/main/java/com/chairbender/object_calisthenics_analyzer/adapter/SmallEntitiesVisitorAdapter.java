package com.chairbender.object_calisthenics_analyzer.adapter;

import com.chairbender.object_calisthenics_analyzer.violation.KeepEntitiesSmallViolation;
import com.chairbender.object_calisthenics_analyzer.violation.ViolationMonitor;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;

import java.io.File;

/**
 * Ensures that all classes are no more than 50 lines.
 *
 * Created by chairbender on 11/27/2015.
 */
public class SmallEntitiesVisitorAdapter extends CalisthenicsVisitorAdapter {

    /**
     * @param violationMonitor violation monitor that violations will be reported to
     */
    public SmallEntitiesVisitorAdapter(ViolationMonitor violationMonitor) {
        super(violationMonitor);
    }

    @Override
    public void visit(ClassOrInterfaceDeclaration classOrInterfaceDeclaration, Object arg) {
        //determine the start and end lines and check that it doesn't exceed 50.
        if (classOrInterfaceDeclaration.getEndLine() - classOrInterfaceDeclaration.getBeginLine() > 50) {
            reportViolation(new KeepEntitiesSmallViolation(classOrInterfaceDeclaration));
        }

        super.visit(classOrInterfaceDeclaration, arg);
    }
}
