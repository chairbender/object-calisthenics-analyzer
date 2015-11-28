package com.chairbender.object_calisthenics_analyzer.violation;

import com.chairbender.object_calisthenics_analyzer.util.MessageUtils;
import com.chairbender.object_calisthenics_analyzer.violation.model.RuleInfo;
import com.github.javaparser.ast.body.FieldDeclaration;

import java.io.File;

/**
 * Represents a violation of having a class that has a collections member and some other members
 *
 * Created by chairbender on 11/21/2015.
 */
public class FirstClassCollectionsViolation extends Violation {
    private static final RuleInfo ruleInfo = new RuleInfo("Create a class with a descriptive name to wrap this collection.",
            "Rule 4 - First class collections. Any class containing a collection should have no other members.");

    /**
     * @param violatingDeclaration the declaration violating the rule
     */
    public FirstClassCollectionsViolation(FieldDeclaration violatingDeclaration) {
        super(violatingDeclaration);
    }

    @Override
    public RuleInfo getRuleInfo() {
        return ruleInfo;
    }
}
