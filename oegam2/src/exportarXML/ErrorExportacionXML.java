package exportarXML;

public class ErrorExportacionXML {

	private int numFallidos = 0;

	public int getNumFallidos() {
		return numFallidos;
	}

	public void setNumFallidos(int numFallidos) {
		this.numFallidos = numFallidos;
	}

	public int addFallido() {
		this.numFallidos++;
		return this.numFallidos;
	}
}