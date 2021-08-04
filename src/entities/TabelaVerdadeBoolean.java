package entities;

public class TabelaVerdadeBoolean {

	private boolean[][] matriz;

	public TabelaVerdadeBoolean() {
	}

	public TabelaVerdadeBoolean(boolean[][] matriz) {
		this.matriz = matriz;
	}

	public void atribuicaoValoresTabela() {
		for (int i = 0; i < matriz.length; i++) {
			for (int j = 0; j < matriz[0].length-1; j++) {
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
			for (int j = 0; j < matriz[0].length; j++) {
				System.out.print((matriz[i][j]) + ", ");
			}
			System.out.println();
		}
	}

	public void condicional() {
		for (int i = 0; i < matriz.length; i++) {
			matriz[i][2] = (matriz[i][1]) ? true : false;
		}
	}

	/*
	 * public void setTable(int a, int x, int y) { TabelaVerdade temp = new
	 * TabelaVerdade(); }
	 */
}
