package com.chairbender.object_calisthenics_analyzer.violation;

import com.chairbender.object_calisthenics_analyzer.util.ClassUtils;
import com.chairbender.object_calisthenics_analyzer.util.MessageUtils;
import com.chairbender.object_calisthenics_analyzer.violation.model.RuleInfo;
import com.github.javaparser.ast.Node;

import java.io.File;

/**
 * Represents a violation of one of the object calisthenics rules
 *
 * Created by chairbender on 11/21/2015.
 */
public abstract class Violation{
    private Node violatingNode;

    /**
     *
     * @param violatingNode node where the violation occurred
     */
    public Violation(Node violatingNode) {
        this.violatingNode = violatingNode;
    }

    /**
     * @return the information on the rule being violated.
     */
    public abstract RuleInfo getRuleInfo();

    /**
     * @return the exact node where the violation occurred
     */
    public Node getViolationLocation() {
        return violatingNode;
    }

    /**
     *
     * @return a string with the fully qualified class name and line number of the violation. Under some circumstances, this
     * class name cannot be determined, in which case this will return null
     */
    @Override
    public String toString() {
        return MessageUtils.getFullyQualifiedViolationLocation(violatingNode);
    }

    /**
     *
     * @return the fully qualified class name where the violation occurred, null if not found
     */
    public String getFullyQualifiedViolationClass() {
        return ClassUtils.getFullyQualifiedClassName(violatingNode);
    }
}
