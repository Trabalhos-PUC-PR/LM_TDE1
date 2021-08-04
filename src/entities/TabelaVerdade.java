package entities;

public class TabelaVerdade {

	private boolean[][] matriz;

	public TabelaVerdade() {
	}

	public TabelaVerdade(boolean[][] matriz) {
		this.matriz = matriz;
	}

	public void atribuicaoValoresTabela() {
		for (int i = 0; i < matriz.length; i++) {
			for (int j = 0; j < matriz[0].length - 4; j++) {
				double valor = Math.random();
				if (valor > 0.5) {
					matriz[i][j] = false;
				} else {
					matriz[i][j] = true;
				}
			}
		}
	}

	public void getTable() {
		for (int i = 0; i < matriz.length; i++) {
			for (int j = 0; j < matriz[0].length - 4; j++) {
				System.out.print(((matriz[i][j]) ? 1 : 0) + ", ");
			}
			System.out.println();
		}
	}

	public void getTable(int x) {
		for (int i = 0; i < matriz.length; i++) {
			for (int j = 0; j < matriz[0].length - 4; j++) {
				System.out.print(((matriz[i][j]) ? 1 : 0) + ", ");
			}
			System.out.println(((matriz[i][x]) ? 1 : 0));
		}
	}

	public void condicional() {
		for (int i = 0; i < matriz.length; i++) {
			matriz[i][2] = (!matriz[i][0] | matriz[i][1]);
		}
		System.out.println("CONDICIONAL: ");
		getTable(2);
	}

	public void operadorE() {
		for (int i = 0; i < matriz.length; i++) {
			matriz[i][3] = (matriz[i][0] & matriz[i][1]);
		}
		System.out.println("E: ");
		getTable(3);
	}

	public void operadorOu() {
		for (int i = 0; i < matriz.length; i++) {
			matriz[i][4] = (matriz[i][0] | matriz[i][1]);
		}
		System.out.println("OU: ");
		getTable(4);
	}

	public void biCondicional() {
		for (int i = 0; i < matriz.length; i++) {
			matriz[i][5] = (matriz[i][0] & matriz[i][1]) | (!matriz[i][0] & !matriz[i][1]);
		}
		System.out.println("BICONDICIONAL: ");
		getTable(5);
	}
	
	/*
	 * public void setTable(int a, int x, int y) { TabelaVerdade temp = new
	 * TabelaVerdade(); }
	 */
}
