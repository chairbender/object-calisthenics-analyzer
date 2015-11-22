package com.chairbender.object_calisthenics_analyzer.violation;

import com.chairbender.object_calisthenics_analyzer.util.MessageUtils;
import com.github.javaparser.ast.stmt.Statement;

import java.io.File;

/**
 * Represents a violation that involves a specific file and a specific line
 *
 * Created by chairbender on 11/22/2015.
 */
public abstract class StatementViolation extends Violation{
    private Statement violatingStatement;

    /**
     * @param inFile file the violation occurred in
     */
    public StatementViolation(File inFile,Statement violatingStatement) {
        super(inFile);
        this.violatingStatement = violatingStatement;
    }

    /**
     *
     * @return a string representing the exact location of the violation
     */
    protected String getViolationStatementLocation() {
        return MessageUtils.getFullyQualifiedViolationLocation(getFile(),violatingStatement);
    }
}
