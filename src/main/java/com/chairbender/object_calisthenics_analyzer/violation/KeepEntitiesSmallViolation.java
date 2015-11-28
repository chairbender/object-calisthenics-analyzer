package com.chairbender.object_calisthenics_analyzer.violation;

import com.chairbender.object_calisthenics_analyzer.util.MessageUtils;
import com.chairbender.object_calisthenics_analyzer.violation.model.RuleInfo;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.FieldDeclaration;

import java.io.File;

/**
 * Represents a violation of having more than 50 lines in a class
 *
 * Created by chairbender on 11/21/2015.
 */
public class KeepEntitiesSmallViolation extends Violation {
    private static final RuleInfo ruleInfo = new RuleInfo("Limit the responsibilities of large classes by breaking them" +
            " up into smaller classes. As you create" +
            " more classes, keep the packages organized as well. Ensure the classes in each package are cohesive and have a shared purpose. Try not to exceed 10 classes" +
            " per package.","Rule 7 - Keep all entities small (keep classes under 50 lines)");

    /**
     * @param violatingDeclaration the declaration violating the rule
     */
    public KeepEntitiesSmallViolation(ClassOrInterfaceDeclaration violatingDeclaration) {
        super(violatingDeclaration);
    }

    @Override
    public RuleInfo getRuleInfo() {
        return ruleInfo;
    }

}
