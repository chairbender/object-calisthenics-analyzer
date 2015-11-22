package com.chairbender.object_calisthenics_analyzer.violation;

import com.chairbender.object_calisthenics_analyzer.util.AncestorUtils;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.stmt.Statement;

import java.io.File;

/**
 * Represents a violation of having more than one level of indentation inside
 * a method.
 *
 * Created by chairbender on 11/21/2015.
 */
public class SingleLevelOfIndentationViolation extends Violation {
    private Statement violatingStatement;


    /**@param inFile file in which the violation occurred
     * @param violatingStatement the statement violating the rule
     */
    public SingleLevelOfIndentationViolation(File inFile, Statement violatingStatement) {
        super(inFile);
        this.violatingStatement = violatingStatement;
    }

    @Override
    public String print() {
        //determine the package and class in which the violation occurred by going up from the
        //violating statement node until we reach the class node and others like it
        String fullyQualifiedClassName = AncestorUtils.getFullyQualifiedClassName(violatingStatement);


        return "More than one level of indentation per method in " + fullyQualifiedClassName + "(" +
                getFile().getName() + ":" + violatingStatement.getBeginLine() + ")";
    }


}
