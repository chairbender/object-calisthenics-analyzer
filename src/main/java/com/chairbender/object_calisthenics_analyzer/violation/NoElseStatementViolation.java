package com.chairbender.object_calisthenics_analyzer.violation;

import com.chairbender.object_calisthenics_analyzer.util.MessageUtils;
import com.chairbender.object_calisthenics_analyzer.violation.model.RuleInfo;
import com.github.javaparser.ast.stmt.Statement;

import java.io.File;

/**
 * Represents a violation of using an else statement.
 *
 * Created by chairbender on 11/21/2015.
 */
public class NoElseStatementViolation extends StatementViolation {
    private static final RuleInfo ruleInfo = new RuleInfo("Use polymorphism to handle variations in logic based on variations in state. Use the Null Object Pattern" +
            " to handle checking for nulls and behaving differently if that is the case.",
            "Rule 2 - Don't use the ELSE keyword. Yes, even ELSE IF");

    /**@param inFile file in which the violation occurred
     * @param violatingStatement the statement violating the rule
     */
    public NoElseStatementViolation(File inFile, Statement violatingStatement) {
        super(inFile, violatingStatement);
    }

    @Override
    public RuleInfo getRuleInfo() {
        return ruleInfo;
    }
}
