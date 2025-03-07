package trafico.matriculacion.acciones;

import trafico.beans.ConsultaTramiteMatriculacionBean;
import general.acciones.ActionBase;

public class ConsultaTramiteMatriculacionAction extends ActionBase {

	private static final long serialVersionUID = 1L;
	private ConsultaTramiteMatriculacionBean beanConsultaMatriculacion;

	public ConsultaTramiteMatriculacionBean getBeanConsultaMatriculacion() {
		return beanConsultaMatriculacion;
	}

	public void setBeanConsultaMatriculacion(ConsultaTramiteMatriculacionBean beanConsultaMatriculacion) {
		this.beanConsultaMatriculacion = beanConsultaMatriculacion;
	}

	@Override
	public String execute() throws Exception {
		return super.execute();
	}

	public String inicio() {
		if (beanConsultaMatriculacion == null)
			beanConsultaMatriculacion = new ConsultaTramiteMatriculacionBean(true);
		return "consultaTramiteMatriculacion";
	}

	public String buscar() {
		return "consultaTramiteMatriculacion";
	}

}