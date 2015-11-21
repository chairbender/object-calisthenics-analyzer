package com.chairbender.object_calisthenics_analyzer.processor;

import org.apache.log4j.Level;
import spoon.processing.AbstractProcessor;
import spoon.reflect.code.*;
import spoon.reflect.declaration.CtMethod;

import java.util.List;

/**
 * Checks that there is a single level of indentation per method
 *
 * Created by chairbender on 11/21/2015.
 */
public class SingleLevelOfIndentationProcessor extends AbstractProcessor<CtMethod> {
    @Override
    public void process(CtMethod element) {
        CtBlock body = element.getBody();

        //check each statement to see if it contains a new
        //level of indentation. If we find one, check the body for
        //any statements that contain new levels of indentation and
        //report it if so.
        //TODO: Handle lamdas?
        for (CtStatement statement : body.getStatements()) {
            if (statement instanceof CtSwitch) {
                for (CtCase ctCase : (List<CtCase>)((CtSwitch)statement).getCases()) {
                    checkBody(ctCase.getStatements());
                }
            } else if (statement instanceof CtIf) {
                //TODO: How does this work for else if?
                if (((CtIf)statement).getElseStatement() != null) {
                    checkBody(((CtIf) statement).getElseStatement());
                }
                checkBody(((CtIf)statement).getThenStatement());
            } else if (statement instanceof CtLoop) {
                checkStatement(((CtLoop) statement).getBody());
            } else if (statement instanceof CtTry) {
                checkStatement(((CtTry) statement).getBody());
            }

        }
    }

    /**
     * Checks if the list of statements has any blocks inside of it. If so,
     * reports the violation.
     * @param statements statements to check
     */
    private void checkBody(List<CtStatement> statements) {
        for (CtStatement statement : statements) {
            checkStatement(statement);
        }

    }

    /**
     * If the statement is an instance of CtBlock, checks for blocks inside of it and reports them if found.
     * Otherwise, check if the statement itself opens any blocks and report it if true
     * @param statement statement to check
     */
    private void checkStatement(CtStatement statement) {
        if (statement instanceof  CtBlock) {
            checkBody(((CtBlock)statement).getStatements());
        } else {
            if (statement instanceof CtSwitch ||
                statement instanceof CtIf ||
                statement instanceof CtLoop ||
                statement instanceof CtTry) {

                //report the violation
                getFactory().getEnvironment().report(this, Level.WARN, statement,
                        "This line increases the level of indentation for this method beyond 1.");
            }
        }


    }
}
