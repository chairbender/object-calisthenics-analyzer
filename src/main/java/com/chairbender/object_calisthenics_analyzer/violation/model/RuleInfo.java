package com.chairbender.object_calisthenics_analyzer.violation.model;

/**
 * Encapsulates information about a specific rule.
 * Created by chairbender on 11/22/2015.
 */
public class RuleInfo {
    private final String ruleDescription;
    private final String fixDescription;

    /**
     *
     * @param fixDescription recommended fix for a violation of the rule
     * @param ruleDescription the description of the object calisthenics violation, including the rule number.
     */
    public RuleInfo(String fixDescription, String ruleDescription) {
        this.fixDescription = fixDescription;
        this.ruleDescription = ruleDescription;
    }

    /**
     *
     * @return a string describing the rule and how to fix it.
     */
    public String describe() {
        return ruleDescription + "\n" + "Suggestion: " + fixDescription;
    }
}
