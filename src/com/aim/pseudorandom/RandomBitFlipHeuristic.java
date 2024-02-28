package com.aim.pseudorandom;

import java.util.Random;

import uk.ac.nott.cs.aim.domains.chesc2014_SAT.SAT;
import uk.ac.nott.cs.aim.satheuristics.SATHeuristic;


/**
 * A heuristic to flip a random bit.
 * @author Warren G. Jackson
 */
public class RandomBitFlipHeuristic extends SATHeuristic {

	public RandomBitFlipHeuristic(Random random) {
		
		super(random);
	}

	@Override
	public void applyHeuristic(SAT problem) {
		
		// select a random bit in the solution
		int numVars = problem.getNumberOfVariables();
		int randomIndex = random.nextInt(numVars);

		// flip the bit
		problem.bitFlip(randomIndex);
		
	}

	@Override
	public String getHeuristicName() {
		
		return "Random Bit Flip";
	}
}
