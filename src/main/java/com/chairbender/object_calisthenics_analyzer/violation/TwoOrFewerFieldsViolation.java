package com.chairbender.object_calisthenics_analyzer.violation;

import com.chairbender.object_calisthenics_analyzer.util.MessageUtils;
import com.chairbender.object_calisthenics_analyzer.violation.model.RuleInfo;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;

import java.io.File;

/**
 * Represents a violation of having more than two fields in a class
 *
 * Created by chairbender on 11/21/2015.
 */
public class TwoOrFewerFieldsViolation extends Violation {
    private static final RuleInfo ruleInfo = new RuleInfo("Encapsulate multiple related fields into their own classes, then use that class as the member/field instead of the related fields. " +
            " For example, put two string fields called firstName and lastName into a class called FullName to contain them.\n" +
            "Use the" +
            " 'has-a' idea of object oriented programming (i.e. Employee 'has-an' Address). Prefer containment (has-a) to inheritance (is-a), but don't rule inheritance out completely.",
            "Rule 8 - No classes with more than 2 instance variables/fields/members.");
    private final ClassOrInterfaceDeclaration violatingDeclaration;

    /**@param inFile file in which the violation occurred
     * @param violatingDeclaration the declaration violating the rule
     */
    public TwoOrFewerFieldsViolation(File inFile, ClassOrInterfaceDeclaration violatingDeclaration) {
        super(inFile);
        this.violatingDeclaration = violatingDeclaration;
    }

    @Override
    public RuleInfo getRuleInfo() {
        return ruleInfo;
    }

    @Override
    public String getViolationLocation() {
        return MessageUtils.getFullyQualifiedViolationLocation(getFile(),violatingDeclaration);
    }
}
