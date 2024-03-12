package com.aim.metaheuristics.population.memetic;

import java.util.Random;

import com.aim.metaheuristics.population.ParentSelection;
import uk.ac.nott.cs.aim.domains.chesc2014_SAT.SAT;

/**
 * @author Warren G. Jackson
 */
public class TournamentSelection extends ParentSelection {

	private final int tournamentSize;

	public TournamentSelection(SAT problem, Random rng, int POPULATION_SIZE, int tournamentSize) {
		
		super(problem, rng, POPULATION_SIZE);
		this.tournamentSize = tournamentSize;
	}

	/**
	  * @return The index of the chosen parent solution.
	  *
	  * PSEUDOCODE
	  *
	  * INPUT: parent_pop, tournament_size
	  * solutions = getUniqueRandomSolutions(tournament_size); 
	  * bestSolution = getBestSolution(solutions);
	  * index = indexOf(bestSolution);
	  * return index;
	  */
	public int parentSelection() {
		
		int bestIndex = -1;
		int randIndex;
		double tmpEval;
		double bestEval = Double.MAX_VALUE;

		for(int i = 0; i < tournamentSize; i++) {
			randIndex = rng.nextInt(POPULATION_SIZE);
			tmpEval = problem.getObjectiveFunctionValue(randIndex);

			if(tmpEval < bestEval) {
				bestIndex = randIndex;
				bestEval = tmpEval;
			}
		}
		
		return bestIndex;
	}
}
