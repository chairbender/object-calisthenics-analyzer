package com.chairbender.object_calisthenics_analyzer.violation;

import java.io.PrintStream;
import java.util.*;

/**
 * Listens for and stores all of the violation of a given
 * collection of source code. Provides functionality for interacting with that
 * data.
 *
 * Created by chairbender on 11/21/2015.
 */
public class ViolationMonitor {
    private List<Violation> violationList = new LinkedList<>();

    /**
     * Logs a violation of the object calisthenics rules
     *
     * @param toReport violation to report
     */
    public void reportViolation(Violation toReport) {
        violationList.add(toReport);
    }

    /**
     * lists all the violations in a human-readable format
     *
     * @param out printstream to pretty print a list of violations
     */
    public void printViolations(PrintStream out) {
        for (Violation violation : violationList) {
            out.println(violation.getRuleInfo().describe() + "\n\t" + violation.toString());
        }
    }

    /**
     *
     * @return the list of violations in the order they were reported. Do not
     * modify this list.
     */
    public List<Violation> getAllViolations() {
        return Collections.unmodifiableList(violationList);
    }
}
