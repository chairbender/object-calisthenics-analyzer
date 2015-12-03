package com.chairbender.object_calisthenics_analyzer.violation;

import com.chairbender.object_calisthenics_analyzer.violation.model.RuleInfo;
import com.github.javaparser.ast.stmt.Statement;

/**
 * Represents a violation of using an else statement.
 *
 * Created by chairbender on 11/21/2015.
 */
public class NoElseStatementViolation extends Violation {
    public static final RuleInfo RULE_INFO = new RuleInfo("Use polymorphism to handle variations in logic based on variations in state. Use the Null Object Pattern" +
            " to handle checking for nulls and behaving differently if that is the case.",
            "Rule 2 - Don't use the ELSE keyword. Yes, even ELSE IF");

    /**
     * @param violatingStatement the statement violating the rule
     */
    public NoElseStatementViolation(Statement violatingStatement) {
        super(violatingStatement);
    }

    @Override
    public RuleInfo getRuleInfo() {
        return RULE_INFO;
    }
}
