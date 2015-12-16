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
     * @param node node to get the fully qualified class name for
     * @return a string with the fully qualified class name of the class the node appears in and the java class
     *      name and line number of the violation in paranthesese (just like you see with a stack trace). Null if
     *      the fully qualified class name cannot be found
     */
    public static String getFullyQualifiedViolationLocation(Node node) {
        String className = ClassUtils.getFullyQualifiedClassName(node);
        if (className != null) {
            return className + ":" + node.getBeginLine();
        } else {
            return null;
        }
    }
}
