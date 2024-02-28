package com.aim.heuristics;

import uk.ac.nott.cs.aim.domains.chesc2014_SAT.SAT;
import uk.ac.nott.cs.aim.helperfunctions.ArrayMethods;
import uk.ac.nott.cs.aim.satheuristics.SATHeuristic;

import java.util.Random;
import java.util.stream.IntStream;

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
        problem.createRandomSolution(CURRENT_SOLUTION_INDEX);

        for(int j = 0; j < problem.getNumberOfVariables(); j++) {
            problem.bitFlip(j);
            double tmpEval = problem.getObjectiveFunctionValue(CURRENT_SOLUTION_INDEX);

            if(tmpEval < bestEval) {
                bestEval = tmpEval;
            } else {
                problem.bitFlip(j);
            }
        }


    }

    public int[] createRandomPermutation(SAT problem) {
        int[] perm = new int[problem.getNumberOfVariables()];

        for(int i = 0; i < problem.getNumberOfVariables(); i++) {
            perm[i] = i;
        }

        for(int i = 0; i < problem.getNumberOfVariables()-1; i++)
        {
            int loc = random.nextInt(perm.length);
            int tmp = perm[i];
            perm[i] = perm[loc];


        }
        return perm;
    }

    @Override
    public String getHeuristicName() {
        return "DBHC";
    }
}
