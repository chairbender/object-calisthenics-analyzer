package com.chairbender.object_calisthenics_analyzer.violation;

import com.chairbender.object_calisthenics_analyzer.violation.model.RuleInfo;

import java.io.File;

/**
 * Represents a violation of one of the object calisthenics rules
 *
 * Created by chairbender on 11/21/2015.
 */
public abstract class Violation{
    private File inFile;

    /**
     *
     * @param inFile file the violation occurred in
     */
    public Violation(File inFile) {
        this.inFile = inFile;
    }

    /**
     * @return the information on the rule being violated.
     */
    public abstract RuleInfo getRuleInfo();

    /**
     *
     * @return the file in which the violation occurred
     */
    public File getFile() {
        return inFile;
    }

    /**
     * Prints the violation in a human readable format
     */
    public abstract String getViolationLocation();

}
