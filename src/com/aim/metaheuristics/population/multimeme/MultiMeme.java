package com.aim.metaheuristics.population.multimeme;

import java.nio.file.attribute.BasicFileAttributes;
import java.util.Random;

import com.aim.metaheuristics.population.MemeplexInheritanceMethod;
import com.aim.metaheuristics.population.ParentSelection;
import com.aim.metaheuristics.population.heuristics.*;

import com.aim.metaheuristics.population.memetic.BasicReplacement;
import com.aim.metaheuristics.population.memetic.FittestSelection;
import com.aim.metaheuristics.population.memetic.RandomSelection;
import com.aim.metaheuristics.population.memetic.UniformXO;
import uk.ac.nott.cs.aim.domains.chesc2014_SAT.Meme;
import uk.ac.nott.cs.aim.domains.chesc2014_SAT.SAT;
import uk.ac.nott.cs.aim.satheuristics.genetics.CrossoverHeuristic;
import uk.ac.nott.cs.aim.satheuristics.genetics.PopulationHeuristic;
import uk.ac.nott.cs.aim.satheuristics.genetics.PopulationReplacement;
import uk.ac.nott.cs.aim.searchmethods.PopulationBasedSearchMethod;

public class MultiMeme extends PopulationBasedSearchMethod {

	/**
	 * The innovation rate setting
	 */
	private final double innovationRate;
	
	private final CrossoverHeuristic crossover;
	private final BitMutation mutation;
	private final PopulationReplacement replacement;
	private final ParentSelection p1selection;
	private final ParentSelection p2selection;

	private final MemeplexInheritanceMethod inheritance;
	
	/**
	 * The possible local search operators to use.
	 */
	private final PopulationHeuristic[] lss; 
	
	// Constructor used for testing. Please do not remove!
	/**
	 *
	 * @param problem
	 * @param rng
	 * @param populationSize
	 * @param innovationRate
	 * @param crossover
	 * @param mutation
	 * @param replacement
	 * @param p1selection
	 * @param p2selection
	 * @param inheritance
	 * @param lss
	 */
	public MultiMeme(SAT problem, Random rng, int populationSize, double innovationRate, CrossoverHeuristic crossover,
							 BitMutation mutation, PopulationReplacement replacement, ParentSelection p1selection, ParentSelection p2selection, MemeplexInheritanceMethod inheritance,
							 PopulationHeuristic[] lss) {

		super(problem, rng, populationSize);

		this.innovationRate = innovationRate;
		this.crossover = crossover;
		this.mutation = mutation;
		this.replacement = replacement;
		this.p1selection = p1selection;
		this.p2selection = p2selection;
		this.inheritance = inheritance;
		this.lss = lss;
	}

	/**
	 * Constructor called when using the CUSTOM operator mode.
	 * You need to create each of the components by yourself!
	 * @param problem
	 * @param rng
	 * @param populationSize
	 * @param innovationRate
	 */
	public MultiMeme(SAT problem, Random rng, int populationSize, double innovationRate) {

		this(
				problem,
				rng,
				populationSize,
				innovationRate,
				new PTX1(problem, rng),
				new BitMutation(problem, rng),
				new BasicReplacement(),
				new RandomSelection(problem, rng, populationSize),
				null,
				new SimpleInheritanceMethod(problem, rng),
				new PopulationHeuristic[]{
						new DBHC_OI(problem, rng),
						new DBHC_IE(problem, rng),
						new SDHC_OI(problem, rng),
						new SDHC_IE(problem, rng)
				}
		);
	}

	/**
	 * MMA PSEUDOCODE:
	 * 
	 * INPUT: PopulationSize, MaxGenerations, InnovationRate
	 * 
	 * generateInitialPopulation();
	 * FOR 0 -> MaxGenerations
	 * 
	 ####### BEGIN IMPLEMENTING HERE #######
	 *     FOR 0 -> PopulationSize / 2
	 *         select parents using tournament selection with tournament size = 3
	 *         apply crossover to generate offspring
	 *         inherit memeplex using simple inheritance method
	 *         mutate the memes within each memeplex of each child with  probability dependent on the innovation rate
	 *         apply mutation to offspring with intensity of mutation set for each solution dependent on its meme option
	 *         apply local search to offspring with choice of operator dependent on each solution�s meme option
	 *     ENDFOR
	 *     do population replacement
	 ####### STOP IMPLEMENTING HERE #######
	 * ENDFOR
	 * return s_best;
	 */
	public void runMainLoop() {
		for(int i = 0; i < POPULATION_SIZE; i += 2) {
			int p1 = p1selection.parentSelection();
			int p2 = p2selection.parentSelection();

			int c1 = POPULATION_SIZE + i;
			int c2 = POPULATION_SIZE + i + 1;

			crossover.applyHeuristic(p1, p2, c1, c2);
			inheritance.performMemeticInheritance(p1, p2, c1, c2);

			performMutationOfMemeplex(c1);
			performMutationOfMemeplex(c2);

			applyLocalSearchForChildDependentOnMeme(c1, 1);
			applyMutationForChildDependentOnMeme(c2, 1);
		}
		replacement.doReplacement(problem, POPULATION_SIZE);
	}
	
	/**
	 * Applies mutation to the child dependent on its current meme option for mutation.
	 * Mapping of meme option to IOM: IntensityOfMutation <- memeOption;
	 * 
	 * @param childIndex The solution memory index of the child to mutate.
	 * @param memeIndex The meme index used for storing the meme relating to the intensity of mutation setting.
	 */
	public void applyMutationForChildDependentOnMeme(int childIndex, int memeIndex) {
		int intensityOfMutation =  problem.getMeme(childIndex, memeIndex).getMemeOption();

		mutation.setMutationRate(intensityOfMutation);
		mutation.applyHeuristic(childIndex);
	}
	
	/**
	 * Applies the local search operator to the child as specified by its current meme option.
	 * 
	 * @param childIndex The solution memory index of the child to mutate.
	 * @param memeIndex The meme index used for storing the meme relating to the local search operator setting.
	 */
	public void applyLocalSearchForChildDependentOnMeme(int childIndex, int memeIndex) {
		PopulationHeuristic lssHeuristic = lss[memeIndex];
		lssHeuristic.applyHeuristic(childIndex);
	}
	
	/**
	 * Applies mutation to each meme within the memeplex of the specified solution with probability
	 * dependent on the innovation rate.
	 * 
	 * HINT: mutation does not mean bit flip; it only means in this case 
	 * 		that you should MODIFY the current value of the meme option
	 * 		subject to the above definition.
	 * 
	 * @param solutionIndex The solution memory index of the solution to mutate the memeplex of.
	 */
	public void performMutationOfMemeplex(int solutionIndex) {
		for(int i = 0; i < problem.getNumberOfMemes(); i++) {
			applyMutationForChildDependentOnMeme(solutionIndex, i);
		}
	}
	
	public String toString() {
		
		return "Multimeme Memetic Algorithm";
	}
	
}
