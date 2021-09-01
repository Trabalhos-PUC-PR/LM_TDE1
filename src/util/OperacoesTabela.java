package util;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import Exceptions.OperationSizeException;

public class OperacoesTabela {

	public static void ordemPrioridade(char[] op) {

		if (op.length == 1) {
			throw new OperationSizeException("Operation too small to be calculated");
		}
		//só pra fazer commit
		List<Character> opLista = new ArrayList<>();
		List<Integer> priorList = new ArrayList<>();
		
		
		for (char a : op) {
			opLista.add(a);
		}
// () ¬ ^ v -> <-> | ttff | (p^~(qv~q))<->p
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
				priorList.add(1); // a
				break;
			case ('q'):
				priorList.add(0); // b
				break;
			default:
				break;
			}
		}
		opLista = opLista.stream().filter(x -> x != ' ').collect(Collectors.toList());
		System.out.println(opLista);
		System.out.println(priorList);

		while (priorList.size() > 1) {

			int topPriority = 0;
			int posTopPri = 0;
			int posTopEnd = 0;
			int posTopPriTemp = 0;
			int posTopEndTemp = 0;
			int pos = 0;

			for (int a : priorList) {
				if (a >= topPriority) {
					// muda a prioridade de acordo com o valor achado
					// se maior que o atual, mudar
					topPriority = a;
					posTopPri = pos;
					//System.out.printf("topPriority: %d, topPos: %d, stopPos%d\n", topPriority, posTopPriority, posFinalPriority);
				}
				if (topPriority == 8 & a == 7 & posTopEnd == 0) {
					// tenta achar o próximo fecha parenteses
					// depois do abre parenteses
					posTopEnd = posTopEndTemp;
				} else {
					posTopEndTemp++;
				}
				pos++;
			}
			
			System.out.printf("pos ini: [%d, %d] pos end: [%d, %d]", posTopPri, priorList.get(posTopPri), posTopEnd, priorList.get(posTopEnd));
			
			System.out.println(priorList);
			//System.out.printf("topPriority: %d, topPos: %d, stopPos%d\n", topPriority, posTopPriority, posFinalPriority);
			
			
			
			priorList.remove(priorList.size()-1);
		}

		System.out.println(priorList);

	}

	public static void leituraAntiga(String[] op) {

		System.out.printf(" p | q | v \n");

		// esse (for) serve pra passar por todos os valores da tabela verdade,
		// eu usei os bits dos numeros pra ir um por um, no caso,
		// 0 é 00, 1 é 01, 2 é 10 e 3 é 11
		for (int i = 0; i < 4; i++) {
			// no caso o que esses fazem é verificar se tem um bit 1 na
			// 1° ou 2° posição, e atribuem true ou false pras variaveis
			// de acordo com o que é achado
			boolean a = ((0b10 & i) != 0) ? true : false;
			boolean b = ((0b01 & i) != 0) ? true : false;

			// esse vetor vai servir de referencia pra fazer o
			// calculo da operação digitada
			Boolean[] bool = new Boolean[op.length];

			for (int x = 0; x < op.length; x++) {
				// ele vê o que o usuario digitou, se foi p, ele recebe a,
				// se foi q, ele recebe b, e se foi um simbolo ou operador,
				// ele recebe null

				if (op[x].equals("p")) {
					bool[x] = a;
				} else {

					if (op[x].equals("q")) {
						bool[x] = b;
					} else {

						bool[x] = null;
					}
				}
			}
			// talvez dê pra juntar esses 2 for, TALVEZ
			for (int x = 0; x < op.length; x++) {

				if (bool[x] == null) {
					// o for vai passando pelas posições do bool que a gnt viu
					// ali em cima, e só para na hora de fazer um calculo
					// (quando ele achar um null)
					if (bool[x + 1] == null) {
						// só passa por aqui se tiver um simbolo seguido do outro
						// (isso acontece com negação), ele inverte o bit que
						// recebe a negação e puxa tudo (incluindo x) um pra
						// frente (ele puxa a string[] op também pq ela é usada
						// pra ver qual operação é pra fazer)
						bool[x + 2] = !(bool[x + 2]) ? true : false;
						bool[x] = bool[x - 1];
						op[x + 1] = op[x];
						x++;
					}

					// como todos os valores do bool são (true, false, null),
					// só temos como saber se TEM OU NÃO um operador ali,
					// pra ver QUAL pegamos da própria string
					switch (op[x]) {

					case ("^"):
						bool[x + 1] = (bool[x - 1] & bool[x + 1]) ? true : false;
						break;
					case ("v"):
						bool[x + 1] = (bool[x - 1] | bool[x + 1]) ? true : false;
						break;
					case ("->"):
						bool[x + 1] = (!bool[x - 1] | bool[x + 1]) ? true : false;
						break;
					case ("<->"):
						bool[x + 1] = ((bool[x - 1] & bool[x + 1]) | (!bool[x - 1] & !bool[x + 1])) ? true : false;
						break;
					case ("~"):
						bool[x + 1] = !(bool[x + 1]) ? true : false;
						break;

					// se digitaram qualquer coisa menos o que era pra digitar,
					// printa que ta errado
					default:
						System.out.println("Operador inválido");
					}
				}

			}
			// no final de cada calculo, printa a, b, e o ultimo valor do vetor
			// bool (no calculo o valor vai sendo carregado até a ultima posição)
			System.out.printf(" %s | %s | %d\n", a ? 1 : 0, b ? 1 : 0, (bool[op.length - 1]) ? 1 : 0);
		}
	}

}
