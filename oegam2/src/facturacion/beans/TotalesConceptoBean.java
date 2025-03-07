package facturacion.beans;

import java.util.ArrayList;
import java.util.List;

/**
 * Bean para almacenar los valores y totales de los conceptos de una factura
 * 
 * @author santiago.cuenca
 *
 */
public class TotalesConceptoBean {

	private List<String> totalesHonorarios;
	private List<String> totalesSuplidos;
	private List<String> totalesGastos;
	private String totalConcepto;

	public List<String> getTotalesHonorarios() {
		if (totalesHonorarios == null) {
			totalesHonorarios = new ArrayList<>();
		}
		return totalesHonorarios;
	}

	public void setTotalesHonorarios(List<String> totalesHonorarios) {
		this.totalesHonorarios = totalesHonorarios;
	}

	public List<String> getTotalesSuplidos() {
		if (totalesSuplidos == null) {
			totalesSuplidos = new ArrayList<>();
		}
		return totalesSuplidos;
	}

	public void setTotalesSuplidos(List<String> totalesSuplidos) {
		this.totalesSuplidos = totalesSuplidos;
	}

	public List<String> getTotalesGastos() {
		if (totalesGastos == null) {
			totalesGastos = new ArrayList<>();
		}
		return totalesGastos;
	}

	public void setTotalesGastos(List<String> totalesGastos) {
		this.totalesGastos = totalesGastos;
	}

	public String getTotalConcepto() {
		return totalConcepto;
	}

	public void setTotalConcepto(String totalConcepto) {
		this.totalConcepto = totalConcepto;
	}

}