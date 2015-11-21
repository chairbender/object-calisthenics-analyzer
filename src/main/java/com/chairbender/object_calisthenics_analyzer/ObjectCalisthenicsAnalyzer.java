package com.chairbender.object_calisthenics_analyzer;

import com.chairbender.object_calisthenics_analyzer.processor.SingleLevelOfIndentationProcessor;
import spoon.Launcher;

/**
 * Main class for the application. Lets one invoke the analyzer programatically.
 *
 *
 * Created by chairbender on 11/21/2015.
 */
public class ObjectCalisthenicsAnalyzer {

    /**
     * Analyzes a Java application. Returns the line numbers of specific
     * violations of object calisthenics.
     *
     * @param args
     *  0 - path of the root folder holding the Java source code (i.e. the
     *      folder holding the root-level package. For example,
     *      if I have src/main/java/com/example/Example.java, this
     *      argument should be the path to the "java" folder)
     *
     */
    public static void main(String[] args) {
        Launcher spoon = new Launcher();
        spoon.addInputResource(args[0]);
        spoon.addProcessor(new SingleLevelOfIndentationProcessor());
        spoon.run();
    }
}
