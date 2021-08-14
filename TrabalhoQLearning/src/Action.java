import br.furb.furbot.Direcao;

public class Action {

	private final State ownerState;
	
	private State newState;

	private Direcao direction;

	private Double qValue = 0.0;
	
	private Action(State ownerState) {
		this.ownerState = ownerState;
	}
	
	public static Action of(State ownerState, Direcao direction) {
		Action action = new Action(ownerState);
		action.setDirection(direction);
		return action;
	}
	
	public State getNewState() {
		return newState;
	}

	public void setNewState(State newState) {
		this.newState = newState;
	}

	public Direcao getDirection() {
		return direction;
	}

	public void setDirection(Direcao direction) {
		this.direction = direction;
	}

	public Double getQValue() {
		return qValue;
	}

	public void setQValue(Double qValue) {
		this.qValue = qValue;
	}

	public State getOwnerState() {
		return ownerState;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Action [ownerState=");
		builder.append(ownerState!= null);
		builder.append(", newState=");
		builder.append(newState!= null);
		builder.append(", direction=");
		builder.append(direction);
		builder.append(", qValue=");
		builder.append(qValue);
		builder.append("]");
		return builder.toString();
	}
	
	

}
