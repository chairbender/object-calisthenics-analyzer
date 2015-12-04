package com.chairbender.object_calisthenics_analyzer.violation;

import com.chairbender.object_calisthenics_analyzer.violation.model.ViolationCategory;

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
    private Map<ViolationCategory,List<Violation>> violations = new HashMap<ViolationCategory,List<Violation>>();

    /**
     * Logs a violation of the object calisthenics rules
     *
     * @param toReport violation to report
     */
    public void reportViolation(Violation toReport) {
        ViolationCategory category = ViolationCategory.forViolation(toReport);
        if (!violations.containsKey(category)) {
            violations.put(category, new LinkedList<Violation>());
        }
        violations.get(category).add(toReport);

    }

    /**
     * lists all the violations in a human-readable format
     *
     * @param out printstream to pretty print a list of violations
     */
    public void printViolations(PrintStream out) {
        for (ViolationCategory violationCategory : violations.keySet()) {
            out.println(violationCategory.getRuleInfo().describe() + "\n");
            for (Violation violation : violations.get(violationCategory)) {
                out.println("\t" + violation.toString() + "\n");
            }
        }
    }

    /**
     *
     * @return all of the violations of each category in the order they were reported. Do not
     * modify this map.
     */
    public Map<ViolationCategory,List<Violation>> getAllViolations() {
        return violations;
    }
}
