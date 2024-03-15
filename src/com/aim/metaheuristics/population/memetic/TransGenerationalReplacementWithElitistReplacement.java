
package com.aim.metaheuristics.population.memetic;

import uk.ac.nott.cs.aim.domains.chesc2014_SAT.SAT;
import uk.ac.nott.cs.aim.satheuristics.genetics.PopulationReplacement;

import java.util.Arrays;
import java.util.stream.IntStream;

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
		double eval;
		double bestEval = Double.MAX_VALUE;
		double worstEval = Double.MIN_VALUE;
		int bestIndex = -1;
		int worstIndex = -1;

		for(int i = 0; i<iPopulationSize*2; i++) {
			eval = oProblem.getObjectiveFunctionValue(i);
			if(eval < bestEval) {
				bestEval = eval;
				bestIndex = i;
			}
			if(eval > worstEval) {
				worstEval = eval;
				worstIndex = i;
			}
		}

		int[] next_pop = IntStream.range(iPopulationSize, (iPopulationSize*2)).toArray();
		if(bestIndex < iPopulationSize) {
			if(0 < (worstIndex - iPopulationSize) && (worstIndex - iPopulationSize) < iPopulationSize ) {
				next_pop[worstIndex - iPopulationSize] = bestIndex;
			}
		}

		return next_pop;
	}

}
