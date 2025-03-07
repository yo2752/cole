package trafico.beans.ivtm;
//TODO MPC. Cambio IVTM. Clase nueva.
public class ResumenAutoliquidacionIVTM {
	private String tipoTramite = "";
	private int numAutoLiquidados = 0;
	private int numAutoliquidadosFallidos = 0;

	public ResumenAutoliquidacionIVTM() {
	}

	public ResumenAutoliquidacionIVTM(String tipoTramite) {
		setTipoTramite(tipoTramite);
	}

	public String getTipoTramite() {
		return tipoTramite;
	}

	public void setTipoTramite(String tipoTramite) {
		this.tipoTramite = tipoTramite;
	}

	public int getNumAutoLiquidados() {
		return numAutoLiquidados;
	}

	public void setNumAutoLiquidados(int numAutoLiquidados) {
		this.numAutoLiquidados = numAutoLiquidados;
	}

	public int getNumAutoliquidadosFallidos() {
		return numAutoliquidadosFallidos;
	}

	public void setNumAutoliquidadosFallidos(int numAutoliquidadosFallidos) {
		this.numAutoliquidadosFallidos = numAutoliquidadosFallidos;
	}

	public int addLiquidadosIVTM() {
		this.numAutoLiquidados++;
		return this.numAutoLiquidados;
	}

	public int addFallido() {
		this.numAutoliquidadosFallidos++;
		return this.numAutoliquidadosFallidos;
	}
}