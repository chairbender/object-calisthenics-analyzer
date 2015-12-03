package com.chairbender.object_calisthenics_analyzer.violation;

import com.chairbender.object_calisthenics_analyzer.violation.model.RuleInfo;
import com.github.javaparser.ast.stmt.Statement;

/**
 * Represents a violation of having more than one level of indentation inside
 * a method.
 *
 * Created by chairbender on 11/21/2015.
 */
public class SingleLevelOfIndentationViolation extends Violation {
    public static final RuleInfo RULE_INFO = new RuleInfo("Turn deep levels of indentation into precisely-named methods. Use the Extract Method feature of your IDE.",
            "Rule 1 - One level of indentation per method (e.g. no If statement or loop inside an If statement or loop).");


    /**
     * @param violatingStatement the statement violating the rule
     */
    public SingleLevelOfIndentationViolation(Statement violatingStatement) {
        super(violatingStatement);
    }

    @Override
    public RuleInfo getRuleInfo() {
        return RULE_INFO;
    }

}
