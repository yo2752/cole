package utiles.hibernate;
//TODO MPC. Cambio IVTM. Clase nueva.
import java.util.Date;
import java.util.List;

import org.gestoresmadrid.utilidades.components.UtilesFecha;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import utilidades.estructuras.FechaFraccionada;

public class CriterionUtiles {
	
	private static final String HORA_FIN_DIA = "23:59";
	private static final int N_SEGUNDOS = 59;

	@Autowired
	UtilesFecha utilesFecha;

	public CriterionUtiles() {
		super();
		SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
	}

	public void anadirCriterioFecha(List<Criterion> criterionList, FechaFraccionada fecha, String tipo){
		if (fecha!=null){
			Date fechaFin= null;
			if (!fecha.isfechaFinNula()){
			    fechaFin=fecha.getFechaFin();
				try {
					utilesFecha.setHoraEnDate(fechaFin, HORA_FIN_DIA);
					utilesFecha.setSegundosEnDate(fechaFin, N_SEGUNDOS);
				} catch (Throwable e) {
				}
			}
			if (!fecha.isfechaInicioNula() && !fecha.isfechaFinNula()) {
				Criterion rest2 = Restrictions.between(tipo, fecha.getFechaInicio(), fechaFin);
				criterionList.add(rest2);
			} else if (!fecha.isfechaInicioNula()){
				Criterion rest2 = Restrictions.ge(tipo, fecha.getFechaInicio());
				criterionList.add(rest2);
			} else if (!fecha.isfechaFinNula()){
				Criterion rest2 = Restrictions.le(tipo, fechaFin);
				criterionList.add(rest2);
			}
		}
	}
}
