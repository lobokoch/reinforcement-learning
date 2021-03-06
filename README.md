# Trabalho Prático I - Problema de Transporte de Objeto

**FUNDAÇÃO UNIVERSIDADE REGIONAL DE BLUMENAU**  
**CENTRO DE CIÊNCIAS EXATAS E NATURAIS**  
**DEPARTAMENTO DE SISTEMAS E COMPUTAÇÃO**  
**Professor: Ricardo Grunitzki**  
**Disciplina: Reinforcement Learning**  
**Aluno: Márcio Koch**  

# Objetivo do trabalho

Um agente deve percorrer a grade 7 x 6, encontrar um objeto e transporta-lo até a base. Essa tarefa deve ser executada na menor quantidade de passos de tempo possível. O agente não possui nenhum conhecimento prévio sobre o ambiente, o qual possui paredes, as quais ele não pode transpor. O agente também não possui conhecimento prévio sobre a localização do objeto.

**NOTA**: Para a implementação do trabalho foi utilizado o **Furbot**, projeto em Java criado pelo time do curso de ciências da computação da FURB que objetiva auxiliar no aprendizado de algoritmos dos alunos de programação nas fazes iniciais do curso de ciências da computação. O Furbot é um robozinho que anda por um mundo no formato de grade, com paredes, aliens e tesouros, o que o torna aderente para implementação do trabalho proposto na disciplina de aprendizado por reforço.

### Mundo do exercício
![](https://github.com/lobokoch/reinforcement-learning/blob/main/mundo_proposto.png?raw=true)
### Mundo do exercício modelo no Furbot
![Mundo do Furbot](https://github.com/lobokoch/reinforcement-learning/blob/main/mundo_furbot.png?raw=true)

# Relatório da solução

Basicamente a minha solução para o problema foi baseada na junção da solução do Jupiter notebook que o professor apresentou em aula (Q-learning for Cliff Walking problem.ipynb) usando o algoritmo Q-learning com gym, mais o site [q-learning-simulator](https://www.mladdict.com/q-learning-simulator). Só implementei em Java e a parte do Environment e da execução do Steps do gym eu implementei usando o Furbot que já tem uma implementação de mundo em grid que favoreceu a implementação.

1. **Modelagem do MDP:**  
	*a) Apresente a modelagem de estados considerada, bem como a quantidade de estados presentes no MDP. Inclua na contagem os estados não válidos;*
	R: **Os estados são as coordenadas x (colunas) e y (linhas) das células do mundo sendo 7 x 6 respectivamente. Os estados inválidos (agente não consegue transpor) são as coordenadas das células onde encontram-se as paredes (de tijolos no mundo Furbot). Todas as demais células possuem estados válidos e o agente recebe recompensa de -0.1, com exceção das células da base (Aliens verde), onde ai o agente recebe uma recompensa de 1.0 e o estado recebe DONE = true (objetivo atingido).**
	
	b) *Apresente a modelagem das ações que o agente pode executar;*
	R: **O agente pode mover-se para CIMA, DIREITA, ABAIXO, ESQUERDA ou ainda permanecer na mesma posição (não se move), caso a posição que ele deveria andar confronta com uma parede ou borda do mundo. Caso o agente esteja posicionado a direita ou esquerda do objeto de transporte (baú de tesouro no mundo do Furbot) ele agarra o objeto de transporte e o move junto com sigo, inclusive não movimentando nem a sim, como ao objeto de transporte se algum intersecciona com uma parede ou borda do mundo.**
	
	c) *Apresente a modelagem da função de recompensa, com as situações em que o agente é recompensado bem como a magnitude da recompensa. Justifique as suas escolhas.*
	R: **Inicialmente todos os estados possuem recompensa 0.1 (me baseei no q-learning-simulator). Cada estado possui 4 ações possíveis (CIMA, DIREITA, ABAIXO, ESQUERDA ) e cada ação possui um atributo qValue que inicialmente possui valor zero. O agente recebe recompensa de -0.1 a cada mudança de estado, com exceção das células da base (Aliens verde), onde ai o agente recebe uma recompensa de +1. O cálculo de atualização do Q value usa a fórmula do algoritmo Q-Learning: Q(s,a)=Q(s,a)+α(r+γ maxQ(s​′​​,a​′​​)−Q(s,a))**

3. **Configuração dos Experimentos**  
	a) *Apresente os valores de taxa de aprendizagem (alfa) e fator de desconto (gamma) do algoritmo de aprendizagem Q-Learning;*
	R: **Utilizei os seguintes valores como padrão (podem ser configurados)**
	
	| alpha | gamma |
	|--|--|
	| 0.5 | 0.9 |  
	
	b) *Apresente as configurações do horizonte de aprendizagem, que é representado pela quantidade máxima de passos de tempo por episódios, quantidade máxima de episódios, e política de exploração ao longo do tempo;*
	R: **Utilizei os seguintes valores como padrão (podem ser configurados)**
	| num_episodes | max_steps | epsilon | decay_epsilon | max_epsilon | min_epsilon |
	|--|--|--|--|--| --|
	| 1000 | 100 | 1.0 | 0.05 | 1.0 |  0.001 |

4. **Resultados Experimentais**  
	a) Apresente a curva de convergência, representada pela quantidade de passos (timesteps) necessários para resolver a tarefa ao longo do tempo (episódios).
	R:** Curva de convergência com os seguintes parâmetros (os defaults)**
	- num_episodes.: 1000
	- max_steps....: 100
	- alpha........: 0.5
	- gamma........: 0.9
	- epsilon......: 1.0
	- decay_epsilon: 0.05
	- max_epsilon..: 1.0
	- min_epsilon..: 0.001
	- Average reward (all episodes)....: -5.272999999999997
	- Average reward (last 10 episodes): -4.989999999999998
	![](https://github.com/lobokoch/reinforcement-learning/blob/main/curva_convergencia_1.png?raw=true)
	
	b) *Apresente o tempo de processamento necessário para resolver o problema.*  
	R: **0,443 segundos.**

### Mais alguns testes exploratórios  

	** Com 10.000 episódios:**  
	- Processing time .................: 2,979 segundos  
	- Average reward (all episodes)....: -5.140559999999998  
	- Average reward (last 10 episodes): -5.099999999999998  

![](https://github.com/lobokoch/reinforcement-learning/blob/main/curva_convergencia_10_000.png?raw=true)

**Com 150 episódios:**  
	- Processing time .................: 0,148 segundos  
	- Average reward (all episodes)....: -5.742666666666662  
	- Average reward (last 10 episodes): -5.099999999999998  

![](https://github.com/lobokoch/reinforcement-learning/blob/main/curva_convergencia_150.png?raw=true)

# Funcionamento para executar a aplicação
- O projeto é feito em Java 11 com Eclipse 2021-06 (última versão no momento da realização do trabalho) , portanto precisa de:
-  Eclipse IDE - Pode ser baixado [daqui](https://www.eclipse.org/downloads/packages/release/2021-06/r/eclipse-ide-enterprise-java-and-web-developers)
-  Baixa a JDK 11, descompactar em um diretório e configurar na IDE Eclipse - A JDK 11 pode ser baixada para o SO desejado a parti de: [https://adoptopenjdk.net](https://adoptopenjdk.net)
- Para configurar a JDK 11 na IDE do Eclipse, acesse o menu **Window** > **Preferences** e siga o desenho da tela abaixo:![](https://github.com/lobokoch/reinforcement-learning/blob/main/setup_jdk_11.png?raw=true)
- Baixar o projeto [TrabalhoQLearning](https://github.com/lobokoch/reinforcement-learning/tree/main/TrabalhoQLearning) do GitHub e importar no Eclipse.
- Verifique na IDE Eclipse se o Java Compiler também está configurado para a versão 11 do Java, caso não esteja (pode estar configurado por padrão para a versão 16 do Java por exemplo), ajuste para a versão 11.
- Acesse o menu **File** > **Properties** (exibe as propriedades do projeto em questão):![](https://github.com/lobokoch/reinforcement-learning/blob/main/java_compiler_1.png?raw=true)

- No Eclipse, configurar o build path para os jars externos do Furbot contidos no diretório: [dependencias_furbot](https://github.com/lobokoch/reinforcement-learning/tree/main/TrabalhoQLearning/dependencias_furbot) dentro do próprio projeto.
- Clicar com o botão direito sobre o projeto e seguir os passos da imagem abaixo:   ![](https://github.com/lobokoch/reinforcement-learning/blob/main/build_path.png?raw=true)

Adicionar os jars externos, seguindo a imagem abaixo:
![](https://github.com/lobokoch/reinforcement-learning/blob/main/configrure_external_jars.png?raw=true)

- A classe principal pra executar (tem o método **main**) é `ReinforcementLearningExercio1.java`  
![](https://github.com/lobokoch/reinforcement-learning/blob/main/main.png?raw=true) 

- É possível modificar os parâmetros nas variáveis dentro do retângulo em verde na imagem acima.
- Para executar a aplicação na IDE Eclipse, basta pressionar a tecla **F11**, ou clicar com o botão direito no mouse numa área do editor de código próximo ao método **main** da classe **ReinforcementLearningExercio1.java** e no menu de contexto, clicar em **Run As** > **Java Application**.
- ![](https://github.com/lobokoch/reinforcement-learning/blob/main/run.png?raw=true)

- Vai abrir a tela do Furbot. Pode-se aumentar a velocidade do Furbot até 10 (velocidade máxima, foi usado 10 nos experimentos) e depois clicar no botão de execução (os elementos estão circundados com retângulo vermelho no print abaixo).
![](https://github.com/lobokoch/reinforcement-learning/blob/main/furbot_1.png?raw=true)

- Ao final da execução é exibido um relatório conforme abaixo no console do Eclipse:
```
**************** FURB - Pos Datascience ****************
 Disciplina: Aprendizado por Reforco 
 Prof. Ricardo Grunitzki 
 Aluno: Marcio Koch 
 Trabalho Pratico I - Problema de Transporte de Objeto 
 *******************************************************
*********** PARAMETROS DE EXECUCAO ***********
debug........: false
num_episodes.: 1000
max_steps....: 100
alpha........: 0.5
gamma........: 0.9
epsilon......: 1.0
decay_epsilon: 0.05
max_epsilon..: 1.0
min_epsilon..: 0.01
***********************************************
 
-----------------------------------------------------------------------------------------------------------
All 1000 episodes has been finished with 961 DONES and 39 NOT DONE.
-----------------------------------------------------------------------------------------------------------
 
*************** REPORT ***************
Processing time .................: 0.464 seconds.
Average reward (all episodes)....: -5.464199999999996
Average reward (last 10 episodes): -5.639999999999996
```

# Exemplo de uma execução com o objetivo atingido
Objeto (tesouro) transportado pelo agente (Furbot) até a base (área dos Aliens).

![](https://github.com/lobokoch/reinforcement-learning/blob/main/done.png?raw=true)


**NOTA 1:** Se ativar a variável `debug = true (false é o padrão)`, exibe mais informações no console do Eclipse, como os estados da execução.

**NOTA 2:** Caso ao final da execução exibir um stack trace de erro no console do Eclipse, não se preocupe que é um problema interno do próprio Furbot, nem sempre acontece e não interfere nos resultados nem na execução.