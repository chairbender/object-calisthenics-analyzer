package com.chairbender.object_calisthenics_analyzer.util;

import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.PackageDeclaration;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;

import java.io.File;

/**
 * Methods for formatting messages
 * Created by chairbender on 11/21/2015.
 */
public abstract class MessageUtils {

    /**
     *
     * @param fileName file the violation occurred in
     * @param node node to get the fully qualified class name for
     * @return a string with the fully qualified class name of the class the node appears in and the file
     *      name and line number of the violation in paranthesese (just like you see with a stack trace)
     */
    public static String getFullyQualifiedViolationLocation(File fileName, Node node) {

        //explore ancestors until we get to the class
        Node currentNode = node;
        while (!(currentNode instanceof ClassOrInterfaceDeclaration)) {
            currentNode = currentNode.getParentNode();
        }
        String className = ((ClassOrInterfaceDeclaration)currentNode).getName();

        while (!(currentNode instanceof CompilationUnit)) {
            currentNode = currentNode.getParentNode();
        }

        PackageDeclaration currentPackageDeclaration = ((CompilationUnit)currentNode).getPackage();
        String fullyQualifiedClassName = currentPackageDeclaration.getName().toString() + "." + className;


        return fullyQualifiedClassName + "(" + fileName.getName() + ":" + node.getBeginLine() + ")";
    }
}
