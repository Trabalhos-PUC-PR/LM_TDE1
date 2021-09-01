package application;

import java.util.Scanner;
import entities.TabelaVerdade;

public class Principal {

	public static void main(String[] args) {

		Scanner sc = new Scanner(System.in);
		
		// s� descomentar isso se precisar fazer os tamanhos da tabela
		// gerada serem digitados (espero q nn precise pq eu considero que ela
		// � maior que 6 o tempo todo)
		
		// System.out.println("Digite os tamanhos da matriz: ");
		// int h = sc.nextInt();
		// int w = sc.nextInt();

		// altura e largura da matriz que a prof pediu
		int h = 4;
		int w = 6;
		boolean[][] a = new boolean[h][w];

		TabelaVerdade tabela = new TabelaVerdade(a);

		System.out.println("Digite uma express�o para ser calculada: ");
		System.out.println("obs: coloque espa�os entre os operadores");
		System.out.println("[p, q] [operador] [p, q] ...");
		System.out.println("Operadores disponiveis: ^, v, ->, <->, ~");
		String string = sc.nextLine();

		// separa a string a cada espa�o e joga num vetor parametro
		// dessa fun��o
		operacao(string.split(" "));
		System.out.println("--- --- ---");

		tabela.atribuicaoValoresTabela();
		tabela.getTable();
		tabela.condicional();
		tabela.operadorE();
		tabela.operadorOu();
		tabela.biCondicional();

		sc.close();

	}

	private static void operacao(String[] op) {

		if(op.length == 1) {
			return;
		}
		
		System.out.printf(" p | q | V \n");

		// esse (for) serve pra passar por todos os valores da tabela verdade,
		// eu usei os bits dos numeros pra ir um por um, no caso, 
		// 0 � 00, 1 � 01, 2 � 10 e 3 � 11
		for (int i = 0; i < 4; i++) {
			// no caso o que esses fazem � verificar se tem um bit 1 na
			// 1� ou 2� posi��o, e atribuem true ou false pras variaveis
			// de acordo com o que � achado
			boolean a = ((0b10 & i) != 0) ? true : false;
			boolean b = ((0b01 & i) != 0) ? true : false;

			// esse vetor vai servir de referencia pra fazer o
			// calculo da opera��o digitada
			Boolean[] bool = new Boolean[op.length];

			for (int x = 0; x < op.length; x++) {
				// ele v� o que o usuario digitou, se foi p, ele recebe a,
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
			// talvez d� pra juntar esses 2 for, TALVEZ
			for (int x = 0; x < op.length; x++) {

				if (bool[x] == null) {
					// o for vai passando pelas posi��es do bool que a gnt viu
					// ali em cima, e s� para na hora de fazer um calculo
					// (quando ele achar um null)
					if (bool[x + 1] == null) {
						// s� passa por aqui se tiver um simbolo seguido do outro
						// (isso acontece com nega��o), ele inverte o bit que 
						// recebe a nega��o e puxa tudo (incluindo x) um pra
						// frente (ele puxa a string[] op tamb�m pq ela � usada
						// pra ver qual opera��o � pra fazer)
						bool[x + 2] = !(bool[x + 2]) ? true : false;
						bool[x] = bool[x - 1];
						op[x + 1] = op[x];
						x++;
					}

					// como todos os valores do bool s�o (true, false, null),
					// s� temos como saber se TEM OU N�O um operador ali,
					// pra ver QUAL pegamos da pr�pria string
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
						System.out.println("Operador inv�lido");
					}
				}

			}
			// no final de cada calculo, printa a, b, e o ultimo valor do vetor
			// bool (no calculo o valor vai sendo carregado at� a ultima posi��o)
			System.out.printf(" %s | %s | %d\n", 
					a ? 1 : 0, 
					b ? 1 : 0, 
					(bool[op.length - 1]) ? 1 : 0);
		}
	}

}
