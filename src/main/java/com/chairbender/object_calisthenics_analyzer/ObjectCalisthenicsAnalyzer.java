package com.chairbender.object_calisthenics_analyzer;

import com.chairbender.object_calisthenics_analyzer.adapter.*;
import com.chairbender.object_calisthenics_analyzer.violation.ViolationMonitor;
import com.github.javaparser.JavaParser;
import com.github.javaparser.ParseException;
import com.github.javaparser.ast.CompilationUnit;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.DirectoryFileFilter;
import org.apache.commons.io.filefilter.RegexFileFilter;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;

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
     *  0 - path of a folder holding Java source code to be analyzed, which will be recursively
     *      explored and each Java file will be analyzed. Or, a path to a single Java file to analyze.
     *
     */
    public static void main(String[] args) throws IOException, ParseException {
        ViolationMonitor violations = analyze(new File(args[0]), "UTF-8");
        //list the violations
        violations.printViolations(System.out);
    }

    /**
     * @param file path of a folder holding Java source code to be analyzed, which will be recursively
     *                       explored and each Java file will be analyzed. Or, a path to a single Java file to analyze.
     * @param encoding encoding to use for reading the file
     * @throws IOException if error occurs reading the file
     * @throws ParseException if error occurs parsing the source code
     * @return the analysis of the provided java project
     */
    public static ViolationMonitor analyze(File file, String encoding) throws IOException, ParseException {
        Collection<File> files;
        if (file.isDirectory()) {
            files = FileUtils.listFiles(
                    file,
                    new RegexFileFilter("^(.*java?)"),
                    DirectoryFileFilter.DIRECTORY
            );
        } else {
            files = new HashSet<File>();
            files.add(file);
        }
        ViolationMonitor violationMonitor = new ViolationMonitor();
        for (File toProcess : files) {
            CompilationUnit compilationUnit = JavaParser.parse(toProcess,encoding,false);
            visitAll(compilationUnit,violationMonitor,toProcess);
        }

        return violationMonitor;
    }

    /**
     * Runs all analyses on the compilation unit and adds any reports to the specified violation monitor, toAdd.
     * THIS WILL BE MODIFIED!
     * @param compilationUnit unit to analyze
     * @param toAdd violation monitor to add any reports to. Will be modified
     * @return the violation monitor, now with any reports added to it.
     *
     */
    private static ViolationMonitor visitAll(CompilationUnit compilationUnit,ViolationMonitor toAdd,File sourceFile) {
        new SingleLevelOfIndentationVisitorAdapter(toAdd,sourceFile).visit(compilationUnit,null);
        new NoElseKeywordVisitorAdapter(toAdd,sourceFile).visit(compilationUnit,null);
        new WrapAllPrimitivesAndStringsVisitorAdapter(toAdd,sourceFile).visit(compilationUnit,null);
        new FirstClassCollectionsVisitorAdapter(toAdd,sourceFile).visit(compilationUnit,null);
        new OneDotPerLineVisitorAdapter(toAdd,sourceFile).visit(compilationUnit, null);
        new SmallEntitiesVisitorAdapter(toAdd,sourceFile).visit(compilationUnit, null);
        new TwoOrFewerFieldsVisitorAdapter(toAdd,sourceFile).visit(compilationUnit, null);
        new NoGettersSettersVisitorAdapter(toAdd,sourceFile).visit(compilationUnit, null);

        return toAdd;
    }
}
