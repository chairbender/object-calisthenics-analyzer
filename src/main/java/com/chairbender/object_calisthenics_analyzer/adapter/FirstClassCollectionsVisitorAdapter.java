package com.chairbender.object_calisthenics_analyzer.adapter;

import com.chairbender.object_calisthenics_analyzer.util.ClassUtils;
import com.chairbender.object_calisthenics_analyzer.violation.FirstClassCollectionsViolation;
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
 * Tries to detect that all classes that has any Collection member has NO other members, so that all collections
 * get wrapped.
 *
 * Created by chairbender on 11/22/2015.
 */
public class FirstClassCollectionsVisitorAdapter extends CalisthenicsVisitorAdapter {
    /**
     * @param violationMonitor violation monitor that violations will be reported to
     * @param file             file being visited
     */
    public FirstClassCollectionsVisitorAdapter(ViolationMonitor violationMonitor, File file) {
        super(violationMonitor, file);
    }

    @Override
    public void visit(ClassOrInterfaceDeclaration classOrInterfaceDeclaration, Object arg) {
        //examine the field declarations. If there are more than one, but one is a collection, report
        //that extra field declaration as a violation.
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
                    //determine if it is a collection.
                    Type type = ((FieldDeclaration) childDeclaration).getType();
                    if (type instanceof ReferenceType) {
                        if (((ReferenceType)type).getType() instanceof ClassOrInterfaceType) {
                            ClassOrInterfaceType classOrInterfaceType = (ClassOrInterfaceType) ((ReferenceType)type).getType();
                            //determine if the type is a collection or any collection subinterface or any
                            //class that implements any collection subinterface
                            if (ClassUtils.isCollection(classOrInterfaceType.getName())) {
                                reportViolation(new FirstClassCollectionsViolation(getFile(), (FieldDeclaration) childDeclaration));
                            }
                        }
                    }
                }
            }
        }

        super.visit(classOrInterfaceDeclaration,arg);
    }
}
