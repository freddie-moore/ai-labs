package com.aim.heuristics;


import java.util.Random;

import uk.ac.nott.cs.aim.domains.chesc2014_SAT.SAT;
import uk.ac.nott.cs.aim.satheuristics.SATHeuristic;


public class SteepestDescentHC extends SATHeuristic {

	public SteepestDescentHC(Random random) {
		
		super(random);
	}

	/**
	  * STEEPEST DESCENT HILL CLIMBING LECTURE SLIDE PSEUDO-CODE
	  *
	  * bestEval = evaluate(currentSolution);
	  * improved = false;
	  * 
	  * for(j = 0; j < length[currentSolution]; j++) {
	  * 
	  *     bitFlip(currentSolution, j); 				//flips j^th bit of current solution
	  *     tmpEval = evaluate(currentSolution);
	  *
	  *     if(tmpEval < bestEval) { 					// remember the bit which yields the best value after evaluation
	  *
	  *         bestIndex = j;
	  *         bestEval = tmpEval; 					//record best achievable solution objective value
	  *         improved = true;
	  *     }
	  *
	  *     bitFlip(currentSolution, j); 				//go back to the initial current solution
	  * }
	  *
	  * if(improved) { bitFlip(currentSolution, bestIndex); }
	  *
	  * @param problem The problem to be solved.
	  */
	public void applyHeuristic(SAT problem) {
		double bestEval = problem.getObjectiveFunctionValue(CURRENT_SOLUTION_INDEX);
		boolean improved = false;
		int bestIndex = 0;

		for(int j = 0; j<problem.getNumberOfVariables(); j++) {
			problem.bitFlip(j);
			double tmpEval = problem.getObjectiveFunctionValue(CURRENT_SOLUTION_INDEX);

			if(tmpEval < bestEval) {
				bestIndex = j;
				bestEval = tmpEval;
				improved = true;
			}

			problem.bitFlip(j);

		}

		if(improved) { problem.bitFlip(bestIndex); }
		
	}

	public String getHeuristicName() {
		
		return "Steepest Descent HC";
	}

}
