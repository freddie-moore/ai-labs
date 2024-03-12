
package com.aim.metaheuristics.population.memetic;

import uk.ac.nott.cs.aim.domains.chesc2014_SAT.SAT;
import uk.ac.nott.cs.aim.satheuristics.genetics.PopulationReplacement;

public class TransGenerationalReplacementWithElitistReplacement extends PopulationReplacement {

	/**
	 * Replaces the current population with the offspring and replaces the worst
	 * offspring with the best solution if the best is not contained in the offspring.
	 *
	 * @return The indices of the solutions to use in the next generation.
	 *
	 * PSEUDOCODE
	 *
	 * INPUT current_pop, offspring_pop
	 * fitnesses <- evaluate( current_pop U offspring_pop );
	 * best <- min( fitnesses );
	 * next_pop <- indicesOf( offspring_pop );
	 * IF best \notin offspring_pop THEN
	 *     next_pop.replace( worst, best );
	 * ENDIF
	 * OUTPUT: next_pop; // return the indices of the next population
	 */
	@Override
	protected int[] getNextGeneration(SAT oProblem, int iPopulationSize) {
		
		// TODO
		return null;
	}

}
