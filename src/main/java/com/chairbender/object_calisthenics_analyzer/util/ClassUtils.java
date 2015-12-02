package com.chairbender.object_calisthenics_analyzer.util;

import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.PackageDeclaration;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import org.reflections.Reflections;

import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Encapsulates functionality for working with classes
 *
 * Created by chairbender on 11/26/2015.
 */
public abstract class ClassUtils {
    //holds the list of all class and interface names for classes that, at some point
    //implement the collection interface, only in the java util package
    private static Set<String> collectionClassNames;

    /**
     * populates the collection class names
     */
    private static void loadCollectionClassNames() {
        collectionClassNames = new HashSet<>();

        Reflections reflections = new Reflections("java.util");

        collectionClassNames.add("Collection");
        for (Class<?> collectionClass : reflections.getSubTypesOf(Collection.class)) {
            collectionClassNames.add(collectionClass.getSimpleName());
        }
        collectionClassNames.add("Map");
        for (Class<?> collectionClass : reflections.getSubTypesOf(Map.class)) {
            collectionClassNames.add(collectionClass.getSimpleName());
        }
    }


    public static boolean isCollection(String name) {
        if (collectionClassNames == null) {
            loadCollectionClassNames();
        }
        return collectionClassNames.contains(name);
    }

    /**
     *
     * @param forNode node whose class should be determined
     * @return the fully qualified class name for the class that forNode
     *      appears in.
     */
    public static String getFullyQualifiedClassName(Node forNode) {
        //explore ancestors until we get to the class
        Node currentNode = forNode;
        while (!(currentNode instanceof ClassOrInterfaceDeclaration)) {
            currentNode = currentNode.getParentNode();
        }
        String className = ((ClassOrInterfaceDeclaration)currentNode).getName();

        while (!(currentNode instanceof CompilationUnit)) {
            currentNode = currentNode.getParentNode();
        }

        PackageDeclaration currentPackageDeclaration = ((CompilationUnit)currentNode).getPackage();
        String fullyQualifiedClassName = currentPackageDeclaration.getName().toString() + "." + className;


        return fullyQualifiedClassName;

    }
}
