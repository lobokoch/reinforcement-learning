
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import br.furb.furbot.Furbot;
import br.furb.furbot.MundoVisual;

public class ReinforcementLearningExercio1 extends Furbot {
	
	public static boolean debug = false;

	public static void main(String[] args) {
		MundoVisual.iniciar("ReinforcementLearningExercio1.xml");
	}
	
	private static double getPropDouble(String name, double defVal) {
		String valueStr = System.getProperty(name);
		
		if (valueStr == null)
			return defVal;
		
		try {
			return Double.parseDouble(valueStr);
		} catch (Exception e) {
			System.err.println("Erro ao converter o valor: " + valueStr + ", da propriedade: " + name + ", para Double. O valor padrao: " + defVal  + " sera assumido.");
			return defVal;
		}
	}
	
	private static int getPropInt(String name, int defVal) {
		String valueStr = System.getProperty(name);
		
		if (valueStr == null)
			return defVal;
		
		try {
			return Integer.parseInt(valueStr);
		} catch (Exception e) {
			System.err.println("Erro ao converter o valor: " + valueStr + ", da propriedade: " + name + ", para Integer. O valor padrao: " + defVal  + " sera assumido.");
			return defVal;
		}
	}
	
	private static boolean getPropBool(String name, boolean defVal) {
		String valueStr = System.getProperty(name);
		
		if (valueStr == null)
			return defVal;
		
		try {
			return Boolean.parseBoolean(valueStr);
		} catch (Exception e) {
			System.err.println("Erro ao converter o valor: " + valueStr + ", da propriedade: " + name + ", para Boolean. O valor padrao: " + defVal  + " sera assumido.");
			return defVal;
		}
	}

	
	@Override
	public void inteligencia() throws Exception {
		debug = getPropBool("debug", false);
		int num_episodes = getPropInt("num_episodes", 1000);
		double alpha = getPropDouble("alpha", 0.5);
		double gamma = getPropDouble("gamma", 0.9);
		double epsilon = getPropDouble("epsilon", 1.0);
		double decay_epsilon = getPropDouble("decay_epsilon", 0.05);
		double max_epsilon = getPropDouble("max_epsilon", 1.0);
		double min_epsilon = getPropDouble("min_epsilon", 0.001);
		
		
		System.out.println("**************** FURB - Pos Datascience ****************");
		System.out.println(" Disciplina: Aprendizado por Reforco ");
		System.out.println(" Prof. Ricardo Grunitzki ");
		System.out.println(" Aluno: Marcio Koch ");
		System.out.println(" Trabalho Pratico I - Problema de Transporte de Objeto ");
		System.out.println(" *******************************************************");
		
		System.out.println("*********** PARAMETROS DE EXECUCAO ***********");
		System.out.println("debug........: " + debug);
		System.out.println("num_episodes.: " + num_episodes);
		System.out.println("alpha........: " + alpha);
		System.out.println("gamma........: " + gamma);
		System.out.println("epsilon......: " + epsilon);
		System.out.println("decay_epsilon: " + decay_epsilon);
		System.out.println("max_epsilon..: " + max_epsilon);
		System.out.println("min_epsilon..: " + min_epsilon);
		System.out.println("***********************************************");
		
				
		ReinforcementLearningEnv environment = new ReinforcementLearningEnv(this);
		long startTime = System.currentTimeMillis();
		List<Double> rewards = qLearning(environment, num_episodes, alpha, gamma, epsilon, decay_epsilon, min_epsilon,
				max_epsilon);
		
		long ellapsedTime = System.currentTimeMillis() - startTime;
		
		System.out.println(" " );
		System.out.println("*************** REPORT ***************" );
		System.out.println("Processing time .................: " + (ellapsedTime / (double)1000) + " seconds.");
		System.out.println("rewards..........................: " + rewards);
		
		System.out.println("Average reward (all episodes)....: " + rewards.stream().mapToDouble(Double::valueOf).sum() / num_episodes);
		System.out.println("Average reward (last 10 episodes): " + rewards.stream().skip(rewards.size() - 10).mapToDouble(Double::valueOf).sum() / 10);

	}

	private List<Double> qLearning(ReinforcementLearningEnv environment, int num_episodes, double alpha, double gamma,
			double epsilon, double decay_epsilon, double min_epsilon, double max_epsilon) {

		List<Double> rewards = new ArrayList<>();
		List<Double> epsilons = new ArrayList<>();

		Random ran = new Random();
		
		int donesCount = 0;

		// episodes
		for (int episode = 0; episode < num_episodes; episode++) {
			
			if (debug)
				System.out.println("episode:" + episode);
			
			// reset the environment to start a new episode
			State state = environment.reset();

			// reward accumulated along episode
			double accumulated_reward = 0;

			for (int step_i = 0; step_i < 100; step_i++) {
				if (debug)
					System.out.println("step_i:" + step_i);

				// epsilon-greedy action selection
				// exploit with probability 1-epsilon
				Action action;
				if (ran.nextDouble() > epsilon)
					action = state.argmaxAction();
				// explore with probability epsilon
				else
					action = state.randomAction();
				
				// perform the action and observe the new state and corresponding reward
				// new_state, reward, done, info = environment.step(action)
				if (debug)
					System.out.println("Action before step:" + action);
				
				action = environment.step(action);

				State new_state = action.getNewState();
				if (debug)
					System.out.println("Action at new_state:" + new_state);
				
				double reward = new_state.getReward();
				boolean done = new_state.isDone();

				// update the Q-table
				// Q[state, action] = Q[state, action] + alpha *
				// (reward + gamma * np.max(Q[new_state, :]) -
				// Q[state, action])
				double qValue = action.getQValue() + alpha * (reward + gamma * new_state.getMaxValue())
						- action.getQValue();

				action.setQValue(qValue);
				
				if (debug)
					System.out.println("Action after step updated:" + action);

				// update the accumulated reward
				accumulated_reward += reward;

				// update the current state
				state = new_state;

				// end the episode when it is done
				if (done) {
					if (debug)
						System.out.println("DONE on episode:" + episode + ", step: " + step_i);
					donesCount++;
					break;
				}

			} // for j

			// decay exploration rate to ensure that the agent exploits more as it becomes
			// experienced
			
			if (debug)
				System.out.println("epsilon: " + epsilon);
			
			epsilon = min_epsilon + (max_epsilon - min_epsilon) * Math.exp(-1 * decay_epsilon * episode);

			// update the lists of rewards and epsilons
			rewards.add(accumulated_reward);
			epsilons.add(epsilon);
		} // for i
		
		System.out.println(" ");
		System.out.println("-----------------------------------------------------------------------------------------------------------");
		System.out.println("All " + num_episodes + " episodes has been finished with " + donesCount + " DONES and " + (num_episodes - donesCount) + " NOT DONE.");
		System.out.println("-----------------------------------------------------------------------------------------------------------");

		return rewards;
	}

}
