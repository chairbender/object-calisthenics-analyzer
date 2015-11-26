package com.chairbender.object_calisthenics_analyzer.util;

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
}
