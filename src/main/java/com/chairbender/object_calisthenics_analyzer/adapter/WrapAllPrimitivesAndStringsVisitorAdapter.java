package com.chairbender.object_calisthenics_analyzer.adapter;

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
import java.sql.Ref;

/**
 * Tries to detect that all primitives and Strings are wrapped, meaning that any class that declares primitive fields
 * must have only ONE such field. Because at some level, you have to use a primitive.
 *
 * Created by chairbender on 11/22/2015.
 */
public class WrapAllPrimitivesAndStringsVisitorAdapter extends CalisthenicsVisitorAdapter {
    /**
     * @param violationMonitor violation monitor that violations will be reported to
     * @param file             file being visited
     */
    public WrapAllPrimitivesAndStringsVisitorAdapter(ViolationMonitor violationMonitor, File file) {
        super(violationMonitor, file);
    }

    @Override
    public void visit(ClassOrInterfaceDeclaration classOrInterfaceDeclaration, Object arg) {
        //examine the field declarations. If there are more than one, but one is a primitive, report
        //that field declaration as a violation.
        //tally the number of non-static fields
        int numFields = 0;
        for (Node childDeclaration : classOrInterfaceDeclaration.getChildrenNodes()) {
            if (childDeclaration instanceof FieldDeclaration) {
                if (!ModifierSet.isStatic(((FieldDeclaration) childDeclaration).getModifiers())) {
                    numFields++;
                }
            }
        }
        //report each primitive if there was more than one field
        if (numFields > 1) {
            for (Node childDeclaration : classOrInterfaceDeclaration.getChildrenNodes()) {
                if (childDeclaration instanceof FieldDeclaration) {
                    //determine if it is a primitive. Consider string to be a primitive.
                    Type type = ((FieldDeclaration) childDeclaration).getType();
                    if (type instanceof PrimitiveType) {
                        reportViolation(new WrapAllPrimitivesAndStringsViolation(getFile(), (FieldDeclaration) childDeclaration));
                    } else if (type instanceof ReferenceType) {
                        if (((ReferenceType)type).getType() instanceof ClassOrInterfaceType) {
                            ClassOrInterfaceType classOrInterfaceType = (ClassOrInterfaceType) ((ReferenceType)type).getType();
                            if (classOrInterfaceType.getName().equals("String")) {
                                reportViolation(new WrapAllPrimitivesAndStringsViolation(getFile(), (FieldDeclaration) childDeclaration));
                            }
                        }
                    }
                }
            }
        }


    }
}
