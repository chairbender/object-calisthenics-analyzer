package com.chairbender.object_calisthenics_analyzer.violation;

import java.io.PrintStream;
import java.util.*;

/**
 * Listens for and stores all of the violation of a given
 * colletion of source code. Provides functionality for interacting with that
 * data.
 *
 * Created by chairbender on 11/21/2015.
 */
public class ViolationMonitor {
    Map<Class<? extends Violation>,List<Violation>> violationList = new HashMap<>();


    /**
     * Logs a violation of the object calisthenics rules
     *
     * @param toReport violation to report
     */
    public void reportViolation(Violation toReport) {
        if (!violationList.containsKey(toReport.getClass())) {
            violationList.put(toReport.getClass(),new LinkedList<>());
        }
        violationList.get(toReport.getClass()).add(toReport);
    }

    /**
     * lists all the violations in a human-readable format
     *
     * @param out printstream to pretty print a list of violations
     */
    public void printViolations(PrintStream out) {
        for (Class<? extends Violation> violationType : violationList.keySet()) {
            Violation aViolation = violationList.get(violationType).get(0);
            out.println(aViolation.getRuleInfo().describe());
            for (Violation violation : violationList.get(violationType)) {
                out.println("\t" + violation.getViolationLocation());
            }
        }
    }
}
