package com.chairbender.object_calisthenics_analyzer.violation;

import com.chairbender.object_calisthenics_analyzer.util.MessageUtils;
import com.chairbender.object_calisthenics_analyzer.violation.model.RuleInfo;
import com.github.javaparser.ast.body.FieldDeclaration;
import com.github.javaparser.ast.stmt.Statement;

import java.io.File;

/**
 * Represents a violation of not wrapping a primitive or a string.
 *
 * Created by chairbender on 11/21/2015.
 */
public class WrapAllPrimitivesAndStringsViolation extends Violation {
    private static final RuleInfo ruleInfo = new RuleInfo("Create a class with a descriptive name to wrap this usage of the primitive or String class.",
            "Rule 3 - Wrap all primitives and Strings.");

    /**
     * @param violatingDeclaration the declaration violating the rule
     */
    public WrapAllPrimitivesAndStringsViolation(FieldDeclaration violatingDeclaration) {
        super(violatingDeclaration);
    }

    @Override
    public RuleInfo getRuleInfo() {
        return ruleInfo;
    }

}