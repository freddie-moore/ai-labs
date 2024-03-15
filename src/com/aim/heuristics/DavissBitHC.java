package com.aim.heuristics;

import uk.ac.nott.cs.aim.domains.chesc2014_SAT.SAT;
import uk.ac.nott.cs.aim.helperfunctions.ArrayMethods;
import uk.ac.nott.cs.aim.satheuristics.SATHeuristic;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.stream.IntStream;

import static java.util.Collections.shuffle;

public class DavissBitHC extends SATHeuristic {
    
    public DavissBitHC(Random random) {
        super(random);
    }

    /**
     * DAVIS's BIT HILL CLIMBING LECTURE SLIDE PSEUDO-CODE
     *
     * bestEval = evaluate(currentSolution);
     * perm = createRandomPermutation();
     * for(j = 0; j < length[currentSolution]; j++) {
     *
     *     bitFlip(currentSolution, perm[j]); 		//flips j^th bit from permutation of solution producing s' from s
     *     tmpEval = evaluate(currentSolution);
     *
     *     if(tmpEval < bestEval) { 				// if there is improvement (strict improvement)
     *
     *         bestEval = tmpEval; 				// accept the best flip
     *
     *     } else { 								// if there is no improvement, reject the current bit flip
     *
     *          bitFlip(solution, perm[j]); 		//go back to s from s'
     *     }
     * }
     *
     * @param problem The problem to be solved.
     */
    public void applyHeuristic(SAT problem) {
        double bestEval = problem.getObjectiveFunctionValue(CURRENT_SOLUTION_INDEX);
        ArrayList<Integer> perm = createRandomPermutation(problem);

        for(int j = 0; j < problem.getNumberOfVariables(); j++) {
            problem.bitFlip(perm.get(j));
            double tmpEval = problem.getObjectiveFunctionValue(CURRENT_SOLUTION_INDEX);

            if(tmpEval < bestEval) {
                bestEval = tmpEval;
            } else {
                problem.bitFlip(perm.get(j));
            }
        }


    }

    public ArrayList<Integer> createRandomPermutation(SAT problem) {
        ArrayList<Integer> perm = new ArrayList<>();

        for(int i = 0; i < problem.getNumberOfVariables(); i++) {
            perm.add(i);
        }

        shuffle(perm, random);

        return perm;
    }

    @Override
    public String getHeuristicName() {
        return "DBHC";
    }
}
