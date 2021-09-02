package util;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import Exceptions.OperationException;
import entities.Posicao;

public class OperacoesTabela {
	/*
	 * Vou fazer um geralzao aqui, minha ideia de como fazer isso funcionar foi o
	 * seguinte: eu precisava receber a string (a formula p ser calculada) e de
	 * alguma forma saber aonde devo come�ar os calculos (parenteses, ordem de
	 * prioridade)
	 * 
	 * minha ideia foi: pra cada operador, um peso e atribuido ("()" sao os valores
	 * mais altos, negacao e o segundo mais alto, e por ai vai), pra ficar mais
	 * facil de analizar a operacao
	 * 
	 * depois de gerar uma lista com representacoes numericas da operacao, eu
	 * analiso se tem parenteses, e caso algum seja achado, a posicao do parenteses
	 * que abre e do que fecha e guardada, dentro do parenteses eu pego o maior
	 * valor e calculo ele, ate sobrar o resultado final. ele e inserido no lugar
	 * que o parenteses ficava e o ciclo se repete ate chegar no resultado final
	 * 
	 */

	public static void ordemPrioridade(char[] op) {

		if (op.length == 1) {
			// s� pra galera n�o mandar calcular tipo... p
			// p � p po
			throw new OperationException("Operation too small to be calculated");
		}

		System.out.printf("\nTABELA VERDADE \nCALCULADA: " + "\n| p | q | v |\n");
		for (int h = 0; h < 4; h++) {

			/*
			 * aqui come�a a parte interessante, essas condicionais ternarias basicamente
			 * verificam se tal bit � 1 ou se � 0 pra gerar a tabela verdade. por exemplo:
			 * se h � igual a 2, em bin�rio ele � 10. ao detectar que tem um bit 1 no 2�
			 * digito, ele deixa a variavel valor1 como 1, se h = 1, o valor seria 01, e
			 * valor1 seria igual a 0
			 * 
			 */
			int valor1 = ((0b10 & h) != 0) ? 1 : 0;
			int valor2 = ((0b01 & h) != 0) ? 1 : 0;

			List<Integer> priorList = new ArrayList<>();
			List<Character> opLista = new ArrayList<>();

			for (char a : op) {
				opLista.add(a);
			}

			// converte os valores em caractere para um valor numerico predefinido
			for (char a : opLista) {
				switch (a) {
				case ('('):
					priorList.add(8);
					break;
				case (')'):
					priorList.add(7);
					break;
				case ('~'):
					priorList.add(6);
					break;
				case ('^'):
					priorList.add(5);
					break;
				case ('v'):
					priorList.add(4);
					break;
				case ('-'):
					priorList.add(3);
					int pos1 = opLista.indexOf('-');
					opLista.set(pos1 + 1, ' ');
					break;
				case ('<'):
					priorList.add(2);
					int pos = opLista.indexOf('<');
					opLista.set(pos + 2, ' ');
					opLista.set(pos + 1, ' ');
					break;
				case ('p'):
					priorList.add(valor1); // a
					break;
				case ('q'):
					priorList.add(valor2); // b
					break;
				default:
					break;
				}
			}

			// isso filtra todos os espa�os vazios na string pra ficar "uniforme"
			opLista = opLista.stream().filter(x -> x != ' ').collect(Collectors.toList());

			// depois da convers�o num�rica, ele come�a um loop que s� acaba quando
			// o que sobrar da convers�o num�rica � o resultado final
			while (priorList.size() > 1) {

				// pra come�ar, eu pego em qual posi��o um parenteses come�a, e em
				// qual ele fecha. IMPORTANTISSIMO
				Posicao pos = posicaoPrioridade(priorList);

				/*
				 * se ele n�o registrou um final pro parenteses (se voltou 0), quer dizer que
				 * n�o tem parenteses, e que ele vai at� o final da formula calculando, ent�o o
				 * fim deve ser definido como o final da formula
				 */
				if (pos.getFim() == 0) {
					pos.setFim(priorList.size() - 1);
				}

				/*
				//descomentar pra ver oq ta acontecendo mais ou menos
				System.out.printf("pos ini: [%d, %d] pos end: [%d, %d]", pos.getInicio(),
				priorList.get(pos.getInicio()), pos.getFim(), priorList.get(pos.getFim()));
				System.out.println(priorList); //
				*/
				
				/* essa lista � especificamente o que ta dentro dos parenteses OU o resto
				 * das prioridades
				 */
				List<Integer> prioridade = prioridade(priorList, pos);

				/* se ele come�a com parenteses, ele remove o fecha-parenteses e
				 * o abre-parenteses, s� pra nao dar trabalho depois
				 */
				if (prioridade.get(0) == 8) {
					prioridade.remove(prioridade.size() - 1);
					prioridade.remove(0);
				}

				// agora o programa vai resolver o que ta dentro do parenteses (ou
				// a prioridade) e s� vai parar quando tiver a resposta final
				while (prioridade.size() > 1) {
					
					// pega qual � a prioridade DENTRO do parenteses, serve pra fazer
					// na ordem certa a nega��o, E, OU, cond e BICOND
					Posicao posInterna = posicaoPrioridade(prioridade);
					
					// a fun��o operacao serve pra calcular coisa. 
					prioridade = operacao(prioridade, posInterna.getInicio());
				}
				
				// se a lista de prioridade (aquela com todos os valores numericos)
				// for maior que um, ent�o o programa substitui tudo que tinha no
				// parenteses pro resultado final da opera��o 
				if (priorList.size() > 1) {
					for (int i = pos.getFim() - 1; i >= pos.getInicio(); i--) {
						priorList.remove(i);
					}
					priorList.set(pos.getInicio(), prioridade.get(0));
				} else {
					// se a lista de prioridade for igual a 1, quer dizer que o calculo
					// ta pronto
					priorList.set(0, prioridade.get(0));
				}
			}
			
			// saindo do while que s� para quando a lista de valores num�ricos � igual 1,
			// o resultado final E os valores de P e Q s�o impressos na tela
			System.out.printf("| %s | %s | %s |\n", (valor1 == 1) ? "V" : "F", (valor2 == 1) ? "V" : "F",
					(priorList.get(0) == 1) ? "V" : "F");
		}
	}

	public static List<Integer> operacao(List<Integer> lista, int a) {
		/*
		 * basicamente essa funcao recebe uma lista de opera��es e uma posi��o
		 * ele S� calcula o operador da posi��o inserida
		 */
		switch (lista.get(a)) {
		// nega��o
		case (6):
			lista.set(a, (lista.get(a + 1) == 1 ? 0 : 1));
			lista.remove(a + 1);
			break;
		// operador E
		case (5):
			boolean p1 = paraBoolean(lista.get(a - 1));
			boolean q1 = paraBoolean(lista.get(a + 1));
			lista.set(a - 1, (p1 & q1) ? 1 : 0);
			lista.remove(a + 1);
			lista.remove(a);
			break;
		// operador OU
		case (4):
			boolean p2 = paraBoolean(lista.get(a - 1));
			boolean q2 = paraBoolean(lista.get(a + 1));
			lista.set(a - 1, (p2 | q2) ? 1 : 0);
			lista.remove(a + 1);
			lista.remove(a);
			break;
		// operador Condicional
		case (3):
			boolean p3 = paraBoolean(lista.get(a - 1));
			boolean q3 = paraBoolean(lista.get(a + 1));
			lista.set(a - 1, (!p3 | q3) ? 1 : 0);
			lista.remove(a + 1);
			lista.remove(a);
			break;
		// operador Bicondicional
		case (2):
			boolean p4 = paraBoolean(lista.get(a - 1));
			boolean q4 = paraBoolean(lista.get(a + 1));
			lista.set(a - 1, ((p4 & q4) | (!p4 & !q4)) ? 1 : 0);
			lista.remove(a + 1);
			lista.remove(a);
			break;
		// se n�o foi nenhum dos operadores a unica op��o � que o programa vai
		// ficar em um loop infinito
		case (0):
		case (1):
			throw new OperationException("Loop infinito :/");
		}
		return lista;
	}
	
	// converte 1 para true e 0 para false (facilita o calculo na operacao)
	public static boolean paraBoolean(int a) {
		return ((0b1 & a) != 0) ? true : false;
	}

	public static Posicao posicaoPrioridade(List<Integer> lista) {
		Integer topPriority = 0;
		Integer posTopPri = 0;
		int posTopEnd = 0;
		int pos = 0;

		/*
		 * ele vai passando na lista, valor por valor, e fica salvando onde o maior //
		 * valor num�rico (a prioridade na ordem) fica na lista. quando for um 8 ( '(' )
		 * // ele reseta o valor de onde fecha parenteses (por que existe a
		 * possibilidade de // ter um abre-fecha parenteses antes) // o programa sempre
		 * vai come�ar pelo parenteses, e s� depois que todos forem // calculados que
		 * ele come�a a pegar a ordem normal (E ou OU, nega��o etc)
		 */
		
		// para cada elemento a na lista
		for (int a : lista) {

			if (a >= topPriority) {
				// muda a prioridade de acordo com o valor achado
				// se maior que o atual, mudar
				topPriority = a;
				posTopEnd = 0;
				posTopPri = pos;
			}
			if (a == 7 && posTopEnd == 0) {
				// tenta achar o pr�ximo fecha parenteses
				// depois do abre parenteses
				posTopEnd = pos;
			}
			pos++;
		}

		// retorna onde abre e onde fecha parenteses
		return new Posicao(posTopPri, posTopEnd);
	}

	public static List<Integer> prioridade(List<Integer> lista, Posicao p) {
		// destaca uma lista menor, que ta dentro do parenteses (ele ignora tamb�m 
		// quando a lista come�a com ^ ou v (come�aria com operador quando eles s�o
		// os maiores valores num�ricos que sobraram) se n�o ignorar operador no inicio,
		// o programa buga)
		List<Integer> listaMenor = new ArrayList<>();
		if (lista.size() > 4 && lista.get(p.getInicio()) != 5 && lista.get(p.getInicio()) != 4) {
			for (int i = p.getInicio(); i <= p.getFim(); i++) {
				listaMenor.add(lista.get(i));
			}
			return listaMenor;
		} else {
			return lista;
		}
	}
}
