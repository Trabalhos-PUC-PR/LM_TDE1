package application;

import java.util.Scanner;

import entities.TabelaVerdade;
import util.OperacoesTabela;

public class Principal {

	public static void main(String[] args) {

		Scanner sc = new Scanner(System.in);

		System.out.println("Digite uma expressão para ser calculada: ");
		System.out.println("obs: coloque espaços entre os operadores");
		System.out.println("[p, q] [operador] [p, q] ...");
		System.out.println("Operadores disponiveis: ^, v, ->, <->, ~");
		//System.out.println("(p^~(qv~q))<->p");
		//System.out.println("p<->~q");
		String string = sc.nextLine();

		// separa a string a cada espaço e joga num vetor parametro
		// dessa função
		//OperacoesTabela.leituraAntiga(string.split(" "));
		OperacoesTabela.ordemPrioridade(string.toCharArray());
		System.out.println("--- --- ---");

		// só descomentar isso se precisar fazer os tamanhos da tabela
		// gerada serem digitados (espero q nn precise pq eu considero que ela
		// é maior que 6 o tempo todo)
		
		// System.out.println("Digite os tamanhos da matriz: ");
		// int h = sc.nextInt();
		// int w = sc.nextInt();

		// altura e largura da matriz que a prof pediu
		
		
		int h = 4;
		int w = 6;
		boolean[][] a = new boolean[h][w];
		
		TabelaVerdade tabela = new TabelaVerdade(a);
		
		tabela.atribuicaoValoresTabela();
		tabela.getTable();
		tabela.condicional();
		tabela.operadorE();
		tabela.operadorOu();
		tabela.biCondicional();
		//*/
		sc.close();

	}

}
