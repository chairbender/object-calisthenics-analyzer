package com.chairbender.object_calisthenics_analyzer.violation.model;

import com.chairbender.object_calisthenics_analyzer.violation.*;

/**
 * Represents a specific type of rule violation
 */
public class ViolationCategory implements Comparable<ViolationCategory> {
    private ViolationCode violationCode;

    @Override
    public int compareTo(ViolationCategory o) {
        return violationCode.getValue() - o.violationCode.getValue();
    }

    public enum ViolationCode {
        RULE_1_INDENTATION(0),
        RULE_2_NO_ELSE(1),
        RULE_3_WRAP_PRIMITIVES(2),
        RULE_4_FIRST_CLASS_COLLECTIONS(3),
        RULE_5_ONE_DOT(4),
        RULE_7_KEEP_ENTITIES_SMALL(5),
        RULE_8_TWO_INSTANCE_VARIABLES(6),
        RULE_9_NO_GETTERS(7);

        private final int value;
        private ViolationCode(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }

    }

    public static ViolationCategory RULE_1_INDENTATION_VIOLATION = new ViolationCategory(ViolationCode.RULE_1_INDENTATION);
    public static ViolationCategory RULE_2_NO_ELSE_VIOLATION = new ViolationCategory(ViolationCode.RULE_2_NO_ELSE);
    public static ViolationCategory RULE_3_WRAP_PRIMITIVES_VIOLATION = new ViolationCategory(ViolationCode.RULE_3_WRAP_PRIMITIVES);
    public static ViolationCategory RULE_4_FIRST_CLASS_COLLECTIONS_VIOLATION = new ViolationCategory(ViolationCode.RULE_4_FIRST_CLASS_COLLECTIONS);
    public static ViolationCategory RULE_5_ONE_DOT_VIOLATION = new ViolationCategory(ViolationCode.RULE_5_ONE_DOT);
    public static ViolationCategory RULE_7_SMALL_ENTITIES_VIOLATION = new ViolationCategory(ViolationCode.RULE_7_KEEP_ENTITIES_SMALL);
    public static ViolationCategory RULE_8_TWO_INTANCE_VARIABLES_VIOLATION = new ViolationCategory(ViolationCode.RULE_8_TWO_INSTANCE_VARIABLES);
    public static ViolationCategory RULE_9_NO_GETTERS_VIOLATION = new ViolationCategory(ViolationCode.RULE_9_NO_GETTERS);


    public ViolationCategory(ViolationCode violationCode) {
        this.violationCode = violationCode;
    }

    /**
     *
     * @return the rule info for the type of violation that this represents.
     */
    public RuleInfo getRuleInfo() {
        switch (violationCode) {
            case RULE_1_INDENTATION:
                return SingleLevelOfIndentationViolation.RULE_INFO;
            case RULE_2_NO_ELSE:
                return NoElseStatementViolation.RULE_INFO;
            case RULE_3_WRAP_PRIMITIVES:
                return WrapAllPrimitivesAndStringsViolation.RULE_INFO;
            case RULE_4_FIRST_CLASS_COLLECTIONS:
                return FirstClassCollectionsViolation.RULE_INFO;
            case RULE_5_ONE_DOT:
                return OneDotPerLineViolation.RULE_INFO;
            case RULE_7_KEEP_ENTITIES_SMALL:
                return KeepEntitiesSmallViolation.RULE_INFO;
            case RULE_8_TWO_INSTANCE_VARIABLES:
                return TwoOrFewerFieldsViolation.RULE_INFO;
            case RULE_9_NO_GETTERS:
                return NoGettersSettersViolation.RULE_INFO;
            default:
                return null;
        }
    }

    /**
     *
     * @param forViolation violation to get the category of
     * @return the violation category for the category forViolation falls in
     */
    public static ViolationCategory forViolation(Violation forViolation) {
        if (forViolation instanceof SingleLevelOfIndentationViolation) {
            return RULE_1_INDENTATION_VIOLATION;
        } else if (forViolation instanceof  NoElseStatementViolation) {
            return RULE_2_NO_ELSE_VIOLATION;
        } else if (forViolation instanceof  WrapAllPrimitivesAndStringsViolation) {
            return RULE_3_WRAP_PRIMITIVES_VIOLATION;
        } else if (forViolation instanceof  FirstClassCollectionsViolation) {
            return RULE_4_FIRST_CLASS_COLLECTIONS_VIOLATION;
        } else if (forViolation instanceof  OneDotPerLineViolation) {
            return RULE_5_ONE_DOT_VIOLATION;
        } else if (forViolation instanceof  KeepEntitiesSmallViolation) {
            return RULE_7_SMALL_ENTITIES_VIOLATION;
        } else if (forViolation instanceof  TwoOrFewerFieldsViolation) {
            return RULE_8_TWO_INTANCE_VARIABLES_VIOLATION;
        } else if (forViolation instanceof  NoGettersSettersViolation) {
            return RULE_9_NO_GETTERS_VIOLATION;
        } else {
            return null;
        }
    }



}
