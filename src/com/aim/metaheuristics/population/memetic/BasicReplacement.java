
package com.aim.metaheuristics.population.memetic;

import uk.ac.nott.cs.aim.domains.chesc2014_SAT.SAT;
import uk.ac.nott.cs.aim.satheuristics.genetics.PopulationReplacement;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

public class BasicReplacement extends PopulationReplacement {

	/**
	 * Replaces the current population with the offspring population.
	 *
	 * @return The indices of the solutions to use in the next generation.
	 *
	 * PSEUDOCODE
	 *
	 * INPUT current_pop, offspring_pop
	 * OUTPUT: offspring_pop; // return the indices of the next population
	 */
	@Override
	protected int[] getNextGeneration(SAT oProblem, int iPopulationSize) {
		int[] ret = IntStream.range(iPopulationSize, (iPopulationSize*2)).toArray();

		return ret;
	}

}
