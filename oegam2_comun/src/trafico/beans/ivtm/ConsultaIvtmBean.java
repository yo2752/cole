package trafico.beans.ivtm;
import org.gestoresmadrid.utilidades.components.UtilesFecha;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import general.beans.BeanCriteriosSkeletonPaginatedList;
//TODO MPC. Cambio IVTM. Clase nueva.
import utilidades.estructuras.FechaFraccionada;

public class ConsultaIvtmBean implements BeanCriteriosSkeletonPaginatedList {

	private String numColegiado;
	private String matricula;
	private FechaFraccionada fechaBusqueda;

	@Autowired
	UtilesFecha utilesFecha;

	public ConsultaIvtmBean(){
		SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
	}

	public ConsultaIvtmBean(boolean inicio) {
		SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
		fechaBusqueda = new FechaFraccionada();
	}

	public String getNumColegiado() {
		return numColegiado;
	}

	public void setNumColegiado(String numColegiado) {
		this.numColegiado = numColegiado;
	}

	public String getMatricula() {
		return matricula;
	}

	public void setMatricula(String matricula) {
		this.matricula = matricula;
	}

	public FechaFraccionada getFechaBusqueda() {
		return fechaBusqueda;
	}

	public void setFechaBusqueda(FechaFraccionada fechaBusqueda) {
		this.fechaBusqueda = fechaBusqueda;
	}

	@Override
	public BeanCriteriosSkeletonPaginatedList iniciarBean() {
		fechaBusqueda = new FechaFraccionada();
		setFechaBusqueda(utilesFecha.getFechaFracionadaActual());
		return this;
	}

}