package com.chairbender.object_calisthenics_analyzer;

import com.chairbender.object_calisthenics_analyzer.adapter.SingleLevelOfIndentationVisitorAdapter;
import com.chairbender.object_calisthenics_analyzer.violation.ViolationMonitor;
import com.github.javaparser.JavaParser;
import com.github.javaparser.ParseException;
import com.github.javaparser.ast.CompilationUnit;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.DirectoryFileFilter;
import org.apache.commons.io.filefilter.RegexFileFilter;

import java.io.File;
import java.io.IOException;
import java.util.Collection;

/**
 * Main class for the application. Lets one invoke the analyzer programatically.
 *
 *
 * Created by chairbender on 11/21/2015.
 */
public class ObjectCalisthenicsAnalyzer {

    /**
     * Analyzes a Java application. Returns the line numbers of specific
     * violation of object calisthenics.
     *
     * @param args
     *  0 - path of the root folder holding the Java source code (i.e. the
     *      folder holding the root-level package. For example,
     *      if I have src/main/java/com/example/Example.java, this
     *      argument should be the path to the "java" folder)
     *
     */
    public static void main(String[] args) throws IOException, ParseException {
        Collection<File> files = FileUtils.listFiles(
                new File(args[0]),
                new RegexFileFilter("^(.*?)"),
                DirectoryFileFilter.DIRECTORY
        );
        ViolationMonitor violationMonitor = new ViolationMonitor();
        for (File toProcess : files) {
            CompilationUnit compilationUnit = JavaParser.parse(toProcess);
            new SingleLevelOfIndentationVisitorAdapter(violationMonitor,toProcess).visit(compilationUnit,null);
        }
        //list the violations
        violationMonitor.printViolations(System.out);
    }
}
