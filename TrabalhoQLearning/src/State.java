import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import br.furb.furbot.Direcao;

public class State {
	
	private final int x;
	
	private final int y;
	
	private Double reward = -0.1;
	
	private Action currentAction;
	
	private boolean done;
	
	private List<Action> actions = new ArrayList<>();
	
	private Random ran = new Random();
	
	public State(int x, int y) {
		this.x = x;
		this.y = y;
		if (x >= 2 && x < 5 && y == 0) // Células da base, recompensa maior.
			reward = 1.0;
		
		initActions();
	}
	
	private void initActions() {
		actions.add(Action.of(this, Direcao.ACIMA));
		actions.add(Action.of(this, Direcao.DIREITA));
		actions.add(Action.of(this, Direcao.ABAIXO));
		actions.add(Action.of(this, Direcao.ESQUERDA));
	}

	public Action argmaxAction() {
		int i = 0;
		Action maxAction = actions.get(i);
		i++;
		while(i < actions.size()) {
			Action action = actions.get(i);
			if (action.getQValue() > maxAction.getQValue())
				maxAction = action;
			i++;
		}
		return maxAction;
	}

	public Action randomAction() {
		int nextInt = ran.nextInt(actions.size());
		Action action = actions.get(nextInt);
		return action;
	}

	public double getMaxValue() {
		Action argmaxAction = argmaxAction();
		return argmaxAction.getQValue();
	}

	public Double getReward() {
		return reward;
	}

	public void setReward(Double reward) {
		this.reward = reward;
	}

	public Action getCurrentAction() {
		return currentAction;
	}

	public void setCurrentAction(Action currentAction) {
		this.currentAction = currentAction;
	}

	public boolean isDone() {
		return done;
	}

	public void setDone(boolean done) {
		this.done = done;
	}

	public List<Action> getActions() {
		return actions;
	}

	public void setActions(List<Action> actions) {
		this.actions = actions;
	}

	public Random getRan() {
		return ran;
	}

	public void setRan(Random ran) {
		this.ran = ran;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("State [x=");
		builder.append(x);
		builder.append(", y=");
		builder.append(y);
		builder.append(", reward=");
		builder.append(reward);
		builder.append(", currentAction=");
		builder.append(currentAction);
		builder.append(", done=");
		builder.append(done);
		builder.append(", actions=");
		builder.append(actions);
		builder.append("]");
		return builder.toString();
	}

	

}
