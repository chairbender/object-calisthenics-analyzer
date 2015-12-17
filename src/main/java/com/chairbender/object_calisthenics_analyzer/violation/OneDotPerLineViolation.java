package com.chairbender.object_calisthenics_analyzer.violation;

import com.chairbender.object_calisthenics_analyzer.violation.model.RuleInfo;
import com.github.javaparser.ast.stmt.Statement;

import java.io.File;

/**
 * Represents a violation of using more than one "dot" in a line
 *
 * Created by chairbender on 11/21/2015.
 */
public class OneDotPerLineViolation extends Violation {
    public static final RuleInfo RULE_INFO = new RuleInfo("If your object is dealing with two other objects at once, it knows about too many" +
            " other objects. Try moving the activity it is doing into one of those other objects.\n" +
            "If all the dots are connected, your object is digging deeply into another object, violating encapsulation." +
            " Try telling an object to do something for you rather than poking around its insides.\n" +
            "Use the Law of Demeter (Only talk to your friends).",
            "Rule 5 - One dot per line.");

    /**
     * @param violatingStatement the statement violating the rule
     *@param sourceFile file where the violation occurred
     */
    public OneDotPerLineViolation(Statement violatingStatement,File sourceFile) {
        super(violatingStatement,sourceFile);
    }

    @Override
    public RuleInfo getRuleInfo() {
        return RULE_INFO;
    }
}
