package application;

import java.util.Scanner;
import entities.TabelaVerdade;

public class Principal {

	public static void main(String[] args) {

		Scanner sc = new Scanner(System.in);

		// System.out.println("Digite os tamanhos da matriz: ");
		// int h = sc.nextInt();
		// int w = sc.nextInt();
		int h = 4;
		int w = 6;
		boolean[][] a = new boolean[h][w];

		TabelaVerdade tabela = new TabelaVerdade(a);

		tabela.atribuicaoValoresTabela();

		System.out.println("Digite uma expressão para ser calculada: ");
		System.out.println("obs: coloque espaços entre os operadores");
		System.out.println("[p, q] [operador] [p, q] ...");
		System.out.println("Operadores disponiveis: ^, v, ->, <->, ~");
		String string = sc.nextLine();

		operacao(string.split(" "));
		System.out.println("--- --- --- --- ---");

		tabela.getTable();
		tabela.condicional();
		tabela.operadorE();
		tabela.operadorOu();
		tabela.biCondicional();

		sc.close();

	}

	private static void operacao(String[] op) {

		System.out.printf(" p | q | v \n");

		for (int i = 0; i < 4; i++) {
			boolean a = ((0b10 & i) != 0) ? true : false;
			boolean b = ((0b01 & i) != 0) ? true : false;
			Boolean[] bool = new Boolean[op.length];
			
			for (int x = 0; x < op.length; x++) {
				
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
			for (int x = 0; x < op.length; x++) {

				if (bool[x] == null) {
					if (bool[x + 1] == null) {
						bool[x + 2] = !(bool[x + 2]) ? true : false;
						//bool[x + 1] = bool[x];
						bool[x] = bool[x - 1];
						op[x + 1] = op[x];
						x++;
					}

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

					case ("1"):
						break;
					case ("0"):
						break;

					default:
						System.out.println("Operador inválido");
					}
				}

			}
			System.out.printf(" %s | %s | %d\n", a ? 1 : 0, b ? 1 : 0, (bool[op.length - 1]) ? 1 : 0);
		}
	}

}
