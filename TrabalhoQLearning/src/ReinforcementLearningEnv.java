import br.furb.furbot.Alien;
import br.furb.furbot.Direcao;
import br.furb.furbot.Furbot;
import br.furb.furbot.ObjetoDoMundo;
import br.furb.furbot.Tesouro;
import br.furb.furbot.exceptions.MundoException;

public class ReinforcementLearningEnv {
	
	private Furbot agente;
	
	private Tesouro tesouro;
	
	//           x y
	private State[][] states;

	public ReinforcementLearningEnv(Furbot furbot) {
		this.agente = furbot;
		initStates();
	}

	private void initStates() {
		states = new State[agente.getQtdeColunas()][agente.getQtdeLinhas()];
		for (int x = 0; x < states.length; x++) {
			for (int y = 0; y < states[x].length; y++) {
				states[x][y] = new State(x, y);
			}
		}
	}

	public State reset() {
		initStates();
		agente.mudarPosicao(0, agente.getQtdeLinhas() - 1);
		if (temObjeto()) {
			tesouro.mudarPosicao(3, 2);
			tesouro = null;
		}
		return getState();
	}

	private State getState() {
		var x = agente.getX();
		var y = agente.getY();
		return states[x][y];
	}

	public Action step(Action action) {
		Direcao direction = action.getDirection();
		
		// Pega o objeto (Tesouro)
		capturarObjeto();
		
		if (podeAndar(direction)) {
			andar(agente, direction);
			andar(tesouro, direction);
		}
		
		action.setNewState(getState());
		
		boolean objetoEstaNaBase = temObjeto() && tesouro.getY() == 0 && tesouro.getX() >= 2 && tesouro.getX() < 5;
		action.getOwnerState().setDone(objetoEstaNaBase);
		
		return action;
	}

	private void capturarObjeto() throws MundoException {
		if (tesouro == null)  {
			ObjetoDoMundo objeto = agente.getObjeto(Direcao.DIREITA);
			if (objeto instanceof Tesouro)
				tesouro = (Tesouro) objeto;
			
			if (tesouro == null) {
				objeto = agente.getObjeto(Direcao.ESQUERDA);
				if (objeto instanceof Tesouro)
					tesouro = (Tesouro) objeto;
			}
		}
	}
	
	private boolean podeAndar(Direcao direction) {
		boolean agentePodeAndar = podeAndarAgente(direction);
		boolean objetoPodeAndar = podeAndarObjeto(direction) || !temObjeto();
		
		return agentePodeAndar && objetoPodeAndar;
	}

	private boolean podeAndarObjeto(Direcao direction) {
		if (!temObjeto())
			return false;
		
		if (tesouro.ehFim(direction))
			return false;
		
		if (tesouro.ehVazio(direction))
			return true;
		
		return tesouro.getObjeto(direction) instanceof Alien /*Base*/ || 
				tesouro.getObjeto(direction) == agente;
	}

	private boolean podeAndarAgente(Direcao direction) {
		if (agente.ehFim(direction))
			return false;
		
		if (agente.ehVazio(direction))
			return true;
		
		return agente.getObjeto(direction) instanceof Tesouro && 
				(direction.equals(Direcao.ESQUERDA) || direction.equals(Direcao.DIREITA));
	}

	private boolean temObjeto() {
		return tesouro != null;
	}
	
	private void andar(ObjetoDoMundo objeto, Direcao direction) {
		if (objeto == null) {
			return;
		}
		
		switch (direction) {
		case ACIMA:
			objeto.andarAcima();
			return;
			
		case DIREITA:
			objeto.andarDireita();
			return;
			
		case ABAIXO:
			objeto.andarAbaixo();
			return;
			
		case ESQUERDA:
			objeto.andarEsquerda();
			return;

		default:
			break;
		}
		
	}

}
