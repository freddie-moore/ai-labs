package com.aim.heuristics;


import uk.ac.nott.cs.aim.domains.chesc2014_SAT.SAT;
import uk.ac.nott.cs.aim.satheuristics.SATHeuristic;

import java.util.Random;


public class ShallowestDescentHC extends SATHeuristic {

	public ShallowestDescentHC(Random random) {
		
		super(random);
	}

	/**
	  * This heuristic is similar to Steepest Descent Hill Climbing
	  * but the difference here is that we want to flip the bit that
	  * results in the least improvement (note this does not include
	  * no improvement).
	  */
	public void applyHeuristic(SAT problem) {
		double firstEval = problem.getObjectiveFunctionValue(CURRENT_SOLUTION_INDEX);
		double bestEval = Double.MIN_VALUE;
		boolean improved = false;
		int bestIndex = 0;

		for(int j = 0; j<problem.getNumberOfVariables(); j++) {
			problem.bitFlip(j);
			double tmpEval = problem.getObjectiveFunctionValue(CURRENT_SOLUTION_INDEX);

			if(tmpEval < firstEval && tmpEval > bestEval) {
				bestIndex = j;
				bestEval = tmpEval;
				improved = true;
			}

			problem.bitFlip(j);

		}

		if(improved) { problem.bitFlip(bestIndex); }
	}

	public String getHeuristicName() {
		
		return "Shallowest Descent HC";
	}

}
