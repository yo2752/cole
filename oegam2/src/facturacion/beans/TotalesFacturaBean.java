package facturacion.beans;

import java.util.ArrayList;
import java.util.List;

/**
 * Bean para almacenar los totales de factura, usado normalmente para los
 * cálculos de totales
 * 
 * @author santiago.cuenca
 *
 */
public class TotalesFacturaBean {

	private List<TotalesConceptoBean> totalesConceptos;
	private String totalFactura;

	public List<TotalesConceptoBean> getTotalesConceptos() {
		if (totalesConceptos == null) {
			totalesConceptos = new ArrayList<>();
		}
		return totalesConceptos;
	}

	public void setTotalesConceptos(List<TotalesConceptoBean> totalesConceptos) {
		this.totalesConceptos = totalesConceptos;
	}

	public String getTotalFactura() {
		return totalFactura;
	}

	public void setTotalFactura(String totalFactura) {
		this.totalFactura = totalFactura;
	}

}