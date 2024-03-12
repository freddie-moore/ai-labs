package com.aim.metaheuristics.singlepoint.lateacceptance;

import uk.ac.nott.cs.aim.domains.chesc2014_SAT.SAT;
import uk.ac.nott.cs.aim.searchmethods.SinglePointSearchMethod;

import java.util.Random;

public class LateAcceptance extends SinglePointSearchMethod {

    /**
     * Creates a search method with a population size of 2; one for the current solution,
     * and one for the backup solution. Creating a copy of the current the solution in the
     * backup solution index.
     *
     * @param problem The problem to be solved by the single point-based search method.
     * @param random  The random number generator.
     */
    public LateAcceptance(SAT problem, Random random, int length) {

        super(problem, random);

        // and anything else you need...!

    }

    /**
     * In Late Acceptance, an incumbent solution, s', is accepted iff 
     * f(s_{i}^{'}) <= f(s_{i-L}) where L is the length of a list containing
     * the memory of L previously accepted solution costs.
     * 
     * More details are/will be available in lecture 4 on "Move Acceptance". 
     */
    @Override
    protected void runMainLoop() {

        // TODO - implement a single pass of Late Acceptance

    }

    public String toString() {
    
        return "Late Acceptance";
	}
}
