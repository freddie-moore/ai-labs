package com.aim.metaheuristics.singlepoint.simulatedannealing;


import java.util.Random;

import uk.ac.nott.cs.aim.domains.chesc2014_SAT.SAT;
import uk.ac.nott.cs.aim.searchmethods.SinglePointSearchMethod;


public class SimulatedAnnealing extends SinglePointSearchMethod {
	
	private final CoolingSchedule oCoolingSchedule;
	
	public SimulatedAnnealing(CoolingSchedule schedule, SAT problem, Random random) {
		
		super(problem, random);
		
		this.oCoolingSchedule = schedule;
	}

	/**
	 * ================================================================
	 * NOTE: In the same way as last week's exercise, you only need
	 *       to implement the code WITHIN the loop for runMainLoop().
	 *       Everything else is handled by the framework/Lab_03_Runner.
	 * ================================================================
	 * 
	 * PSEUDOCODE for Simulated Annealing:
	 *
	 * INPUT : T_0 and any other parameters of the cooling schedule
	 * s_0 = generateInitialSolution();
	 * Temp <- T_0;
	 * s_{best} <- s_0;
	 * s' <- s_0;
	 *
	 * REPEAT
	 *     s' <- randomBitFlip(s);
	 *     delta <- f(s') - f(s);
	 *     r <- random \in [0,1];
	 *     IF delta < 0 OR r < P(delta, Temp) THEN
	 *         s <- s';
	 *     ENDIF
	 *     s_{best} <- updateBest(); // NOTE: this step is already handled by the framework!
	 *     Temp <- advanceTemperature(); // DO NOT FORGET THIS STEP!!!
	 *     
	 * UNTIL termination conditions are satisfied;
	 *
	 * RETURN s_{best};
	 * 
	 * 
	 * REMEMBER That the solutions in the CURRENT_SOLUTION_INDEX and BACKUP_SOLUTION_INDEX
	 * 	should be the same before returning from 'runMainLoop()'!
	 * 
	 * Here, P is the probability function e^(-delta/T)
	 */
	protected void runMainLoop() {
		// random bit flip
		int numVars = problem.getNumberOfVariables();
		int randomIndex = random.nextInt(numVars);
		problem.bitFlip(randomIndex);

		double delta = problem.getObjectiveFunctionValue(CURRENT_SOLUTION_INDEX) - problem.getObjectiveFunctionValue(BACKUP_SOLUTION_INDEX);
		double r = random.nextDouble(1);

		if((delta < 0) || (r < (Math.exp((0-delta) / oCoolingSchedule.getCurrentTemperature())))) {
			problem.bitFlip(randomIndex, BACKUP_SOLUTION_INDEX);
		} else {
			problem.bitFlip(randomIndex, CURRENT_SOLUTION_INDEX);
		}

		oCoolingSchedule.advanceTemperature();
		
		
	}
		
	public String toString() {
		return "Simulated Annealing with " + oCoolingSchedule.toString();
	}
}
