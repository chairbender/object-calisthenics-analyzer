package com.chairbender.object_calisthenics_analyzer.util;

import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.PackageDeclaration;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.stmt.Statement;

/**
 * Methods for finding out information about node's ancestors
 *
 * Created by chairbender on 11/21/2015.
 */
public abstract class AncestorUtils {

    /**
     *
     * @param statement statement to get the fully qualified class name for
     * @return the fully qualified class name of the class the statement appears in
     */
    public static String getFullyQualifiedClassName(Statement statement) {

        //explore ancestors until we get to the class
        Node currentNode = statement;
        while (!(currentNode instanceof ClassOrInterfaceDeclaration)) {
            currentNode = currentNode.getParentNode();
        }
        String className = ((ClassOrInterfaceDeclaration)currentNode).getName();

        while (!(currentNode instanceof CompilationUnit)) {
            currentNode = currentNode.getParentNode();
        }

        PackageDeclaration currentPackageDeclaration = ((CompilationUnit)currentNode).getPackage();

        return currentPackageDeclaration.getName().toString() + "." + className;
    }
}
