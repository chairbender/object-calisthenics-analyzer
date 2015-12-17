package com.chairbender.object_calisthenics_analyzer.violation;

import com.chairbender.object_calisthenics_analyzer.violation.model.RuleInfo;
import com.github.javaparser.ast.body.FieldDeclaration;

import java.io.File;

/**
 * Represents a violation of not wrapping a primitive or a string.
 *
 * Created by chairbender on 11/21/2015.
 */
public class WrapAllPrimitivesAndStringsViolation extends Violation {
    public static final RuleInfo RULE_INFO = new RuleInfo("Create a class with a descriptive name to wrap this usage of the primitive or String class.",
            "Rule 3 - Wrap all primitives and Strings.");

    /**
     * @param violatingDeclaration the declaration violating the rule
     * @param sourceFile file where the violation occurred
     */
    public WrapAllPrimitivesAndStringsViolation(FieldDeclaration violatingDeclaration,File sourceFile) {
        super(violatingDeclaration,sourceFile);
    }

    @Override
    public RuleInfo getRuleInfo() {
        return RULE_INFO;
    }

}
