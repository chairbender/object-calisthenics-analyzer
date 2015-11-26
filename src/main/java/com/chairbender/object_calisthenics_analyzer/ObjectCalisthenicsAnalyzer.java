package com.chairbender.object_calisthenics_analyzer;

import com.chairbender.object_calisthenics_analyzer.adapter.FirstClassCollectionsVisitorAdapter;
import com.chairbender.object_calisthenics_analyzer.adapter.NoElseKeywordVisitorAdapter;
import com.chairbender.object_calisthenics_analyzer.adapter.SingleLevelOfIndentationVisitorAdapter;
import com.chairbender.object_calisthenics_analyzer.adapter.WrapAllPrimitivesAndStringsVisitorAdapter;
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
 * Main class for the application. Lets one invoke the analyzer from the command line. Also lets one invoke
 * the main logic for this analyzer as a Java API using the "analyze()" method.
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
        ViolationMonitor violations = analyze(new File(args[0]));
        //list the violations
        violations.printViolations(System.out);
    }

    /**
     * @param rootSourcePath path of the root folder holding the Java source code (i.e. the
     *      folder holding the root-level package. For example,
     *      if I have src/main/java/com/example/Example.java, this
     *      argument should be the path to the "java" folder)
     *
     * @return the analysis of the provided java project
     */
    public static ViolationMonitor analyze(File rootSourcePath) throws IOException, ParseException {
        Collection<File> files = FileUtils.listFiles(
                rootSourcePath,
                new RegexFileFilter("^(.*?)"),
                DirectoryFileFilter.DIRECTORY
        );
        ViolationMonitor violationMonitor = new ViolationMonitor();
        for (File toProcess : files) {
            CompilationUnit compilationUnit = JavaParser.parse(toProcess);
            new SingleLevelOfIndentationVisitorAdapter(violationMonitor,toProcess).visit(compilationUnit,null);
            new NoElseKeywordVisitorAdapter(violationMonitor,toProcess).visit(compilationUnit,null);
            new WrapAllPrimitivesAndStringsVisitorAdapter(violationMonitor,toProcess).visit(compilationUnit,null);
            new FirstClassCollectionsVisitorAdapter(violationMonitor,toProcess).visit(compilationUnit,null);
        }

        return violationMonitor;
    }
}
