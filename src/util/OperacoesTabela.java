package util;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import Exceptions.OperationSizeException;
import entities.Posicao;

public class OperacoesTabela {

	public static void ordemPrioridade(char[] op) {

		if (op.length == 1) {
			throw new OperationSizeException("Operation too small to be calculated");
		}

		System.out.printf("\nTABELA VERDADE \nCALCULADA: " + "\n| p | q | v |\n");
		for (int h = 0; h < 4; h++) {

			int valor1 = ((0b10 & h) != 0) ? 1 : 0;
			int valor2 = ((0b01 & h) != 0) ? 1 : 0;

			List<Integer> priorList = new ArrayList<>();
			List<Character> opLista = new ArrayList<>();

			for (char a : op) {
				opLista.add(a);
			}

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

			opLista = opLista.stream().filter(x -> x != ' ').collect(Collectors.toList());

			while (priorList.size() > 1) {

				Posicao pos = posicaoPrioridade(priorList);

				if (pos.getFim() == 0) {
					pos.setFim(priorList.size() - 1);
				}

				List<Integer> prioridade = prioridade(priorList, pos);

				if (prioridade.get(0) == 8) {
					prioridade.remove(prioridade.size() - 1);
					prioridade.remove(0);
				}

				while (prioridade.size() > 1) {
					Posicao posInterna = posicaoPrioridade(prioridade);
					prioridade = operacao(prioridade, posInterna.getInicio());
				}
				if (priorList.size() > 1) {
					for (int i = pos.getFim() - 1; i >= pos.getInicio(); i--) {
						priorList.remove(i);
					}
					priorList.set(pos.getInicio(), prioridade.get(0));
				} else {
					priorList.set(0, prioridade.get(0));
				}
			}

			System.out.printf("| %s | %s | %s |\n", 
					(valor1==1)? "V" : "F", 
					(valor2==1)? "V" : "F", 
					(priorList.get(0) == 1)? "V" : "F");
		}
	}

	public static List<Integer> operacao(List<Integer> lista, int a) {
		switch (lista.get(a)) {
		case (6):
			lista.set(a, (lista.get(a + 1) == 1 ? 0 : 1));
			lista.remove(a + 1);
			break;
		case (5):
			boolean p1 = paraBoolean(lista.get(a - 1));
			boolean q1 = paraBoolean(lista.get(a + 1));
			lista.set(a - 1, (p1 & q1) ? 1 : 0);
			lista.remove(a + 1);
			lista.remove(a);
			break;

		case (4):
			boolean p2 = paraBoolean(lista.get(a - 1));
			boolean q2 = paraBoolean(lista.get(a + 1));
			lista.set(a - 1, (p2 | q2) ? 1 : 0);
			lista.remove(a + 1);
			lista.remove(a);
			break;

		case (3):
			boolean p3 = paraBoolean(lista.get(a - 1));
			boolean q3 = paraBoolean(lista.get(a + 1));
			lista.set(a - 1, (!p3 | q3) ? 1 : 0);
			lista.remove(a + 1);
			lista.remove(a);
			break;

		case (2):
			boolean p4 = paraBoolean(lista.get(a - 1));
			boolean q4 = paraBoolean(lista.get(a + 1));
			lista.set(a - 1, ((p4 & q4) | (!p4 & !q4)) ? 1 : 0);
			lista.remove(a + 1);
			lista.remove(a);
			break;
		}
		return lista;
	}

	public static boolean paraBoolean(int a) {
		return ((0b1 & a) != 0) ? true : false;
	}

	public static Posicao posicaoPrioridade(List<Integer> lista) {
		Integer topPriority = 0;
		Integer posTopPri = 0;
		int posTopEnd = 0;
		int posTopEndTemp = 0;
		int pos = 0;

		for (int a : lista) {
			if (a >= topPriority) {
				// muda a prioridade de acordo com o valor achado
				// se maior que o atual, mudar
				topPriority = a;
				posTopPri = pos;
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

		// retorna onde abre e onde fecha parenteses
		return new Posicao(posTopPri, posTopEnd);
	}

	public static List<Integer> prioridade(List<Integer> lista, Posicao p) {
		List<Integer> listaMenor = new ArrayList<>();
		if (lista.size() >= 4) {
			for (int i = p.getInicio(); i <= p.getFim(); i++) {
				listaMenor.add(lista.get(i));
			}
			return listaMenor;
		} else {
			return lista;
		}
	}
}
