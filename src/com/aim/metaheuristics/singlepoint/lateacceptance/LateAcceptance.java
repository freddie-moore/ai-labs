package com.aim.metaheuristics.singlepoint.lateacceptance;

import com.aim.heuristics.DavissBitHC;
import uk.ac.nott.cs.aim.domains.chesc2014_SAT.SAT;
import uk.ac.nott.cs.aim.satheuristics.SATHeuristic;
import uk.ac.nott.cs.aim.searchmethods.SinglePointSearchMethod;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class LateAcceptance extends SinglePointSearchMethod {

    private static int length;
    private static Double[] memory;

    private int iterations = 0;
    private int idleIterations = 0;

    double bestEval = Double.MAX_VALUE;
    private SATHeuristic oMutationHeuristic;

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

        // Initialise memory
        memory = new Double[length];
        this.length = length;

        // Set mutation heuristic
        this.oMutationHeuristic = new DavissBitHC(random);

        // Initialise memory
        for(int j = 0; j < length; j++) {
            memory[j] = problem.getObjectiveFunctionValue(0);
        }
        // Initialise bestEval
        bestEval = problem.getObjectiveFunctionValue(0);
        problem.copySolution(0, 1);

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
        iterations += 1;

        // Construct candidate solution
        oMutationHeuristic.applyHeuristic(problem);
        // Evaluate candidate solution
        double eval = problem.getObjectiveFunctionValue(0);

        if(eval >= bestEval) {
            idleIterations += 1;
        } else {
            idleIterations = 0;
        }

        int virtualBeginning = iterations % length;

        if(eval < memory[virtualBeginning] || eval <= bestEval) {
            bestEval = eval;
            problem.copySolution(0, 1);
        }
        if(bestEval < memory[virtualBeginning]) {
            memory[virtualBeginning] = bestEval;
        }
    }

    public String toString() {
    
        return "Late Acceptance";
	}
}
