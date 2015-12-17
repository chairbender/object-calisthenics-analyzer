package com.chairbender.object_calisthenics_analyzer.adapter;

import com.chairbender.object_calisthenics_analyzer.violation.Violation;
import com.chairbender.object_calisthenics_analyzer.violation.ViolationMonitor;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;

import java.io.File;

/**
 * Abstract class representing any class which looks for some problem with the code and reports
 * it as a CalisthenicsViolation.
 *
 * Created by chairbender on 11/21/2015.
 */
public abstract class CalisthenicsVisitorAdapter extends VoidVisitorAdapter{
    protected ViolationMonitor violationMonitor;
    protected File sourceFile;

    /**
     *
     * @param violationMonitor violation monitor that violations will be reported to
     */
    public CalisthenicsVisitorAdapter(ViolationMonitor violationMonitor,File sourceFile) {
        this.violationMonitor = violationMonitor;
        this.sourceFile = sourceFile;
    }

    /**
     * reports the violation to the violation monitor
     *
     * @param toReport violation to report to the violation monitor
     */
    protected void reportViolation(Violation toReport) {
        violationMonitor.reportViolation(toReport);
    }
}
