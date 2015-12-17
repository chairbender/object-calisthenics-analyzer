package com.chairbender.object_calisthenics_analyzer.violation;

import com.chairbender.object_calisthenics_analyzer.violation.model.RuleInfo;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;

import java.io.File;

/**
 * Represents a violation of having more than two fields in a class
 *
 * Created by chairbender on 11/21/2015.
 */
public class TwoOrFewerFieldsViolation extends Violation {
    public static final RuleInfo RULE_INFO = new RuleInfo("Encapsulate multiple related fields into their own classes, then use that class as the member/field instead of the related fields. " +
            " For example, put two string fields called firstName and lastName into a class called FullName to contain them.\n" +
            "Use the" +
            " 'has-a' idea of object oriented programming (i.e. Employee 'has-an' Address). Prefer containment (has-a) to inheritance (is-a), but don't rule inheritance out completely.",
            "Rule 8 - No classes with more than 2 instance variables/fields/members.");

    /**
     * @param violatingDeclaration the declaration violating the rule
     * @param sourceFile file where the violation occurred
     */
    public TwoOrFewerFieldsViolation(ClassOrInterfaceDeclaration violatingDeclaration,File sourceFile) {
        super(violatingDeclaration,sourceFile);
    }

    @Override
    public RuleInfo getRuleInfo() {
        return RULE_INFO;
    }
}
