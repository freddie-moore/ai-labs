package com.aim.metaheuristics.population.memetic;

import com.aim.metaheuristics.population.ParentSelection;
import uk.ac.nott.cs.aim.domains.chesc2014_SAT.SAT;

import java.util.Random;

/**
 * @author Warren G. Jackson
 */
public class FittestSelection extends ParentSelection {

	public FittestSelection(SAT problem, Random rng, int POPULATION_SIZE) {
		
		super(problem, rng, POPULATION_SIZE);
	}

	/**
	  * @return The index of the chosen parent solution.
	  *
	  * PSEUDOCODE
	  *
	  * INPUT: parent_pop
	  * bestSolution = getBestSolution(solutions);
	  * index = indexOf(bestSolution);
	  * return index;
	  */
	public int parentSelection() {
		int bestIndex = 0;
		double bestEval = problem.getObjectiveFunctionValue(0);
		
		for(int i = 1; i < POPULATION_SIZE; i++) {
			double tmpEval = problem.getObjectiveFunctionValue(i);

			if(tmpEval < bestEval) {
				bestIndex = i;
				bestEval = tmpEval;
			}
		}
		
		return bestIndex;
	}
}
