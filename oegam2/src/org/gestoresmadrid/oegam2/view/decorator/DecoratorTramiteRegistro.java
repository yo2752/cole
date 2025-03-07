package org.gestoresmadrid.oegam2.view.decorator;

import org.displaytag.decorator.TableDecorator;
import org.gestoresmadrid.core.registradores.model.enumerados.EstadoTramiteRegistro;
import org.gestoresmadrid.core.registradores.model.enumerados.TipoTramiteRegistro;
import org.gestoresmadrid.core.registradores.model.vo.TramiteRegistroVO;
import org.gestoresmadrid.utilidades.components.UtilesFecha;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

public class DecoratorTramiteRegistro extends TableDecorator {

	@Autowired
	UtilesFecha utilesFecha;

	public DecoratorTramiteRegistro() {
		super();
		SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
	}

	public String getFechaEnvio() {
		TramiteRegistroVO tramite = (TramiteRegistroVO) getCurrentRowObject();
		if (tramite.getFechaEnvio() != null) {
			return utilesFecha.formatoFecha("dd/MM/yyyy", tramite.getFechaEnvio());
		} else {
			return "";
		}
	}

	public String getTipoTramite() {
		TramiteRegistroVO tramite = (TramiteRegistroVO) getCurrentRowObject();
		if (tramite.getTipoTramite() != null) {
			return TipoTramiteRegistro.recuperarDescripcionCorta(tramite.getTipoTramite());
		} else {
			return "";
		}
	}

	public String getEstado() {
		TramiteRegistroVO tramite = (TramiteRegistroVO) getCurrentRowObject();
		if (tramite.getEstado() != null) {
			return EstadoTramiteRegistro.convertirTexto(tramite.getEstado().toString());
		} else {
			return "";
		}
	}

	@Override
	public String addRowClass() {
		TramiteRegistroVO tramite = (TramiteRegistroVO) getCurrentRowObject();
		if (EstadoTramiteRegistro.Anulado.getValorEnum().equals(tramite.getEstado().toString())) {
			return "enlaceAnulado anulado";
		}
		if (EstadoTramiteRegistro.Finalizado.getValorEnum().equals(tramite.getEstado().toString())
				|| EstadoTramiteRegistro.Calificado_Defectos.getValorEnum().equals(tramite.getEstado().toString())
				|| EstadoTramiteRegistro.Inscrito_Parcialmente.getValorEnum().equals(tramite.getEstado().toString())
				|| EstadoTramiteRegistro.Confirmada_Denegacion.getValorEnum().equals(tramite.getEstado().toString())) {
			return "enlaceImpreso impreso";
		}
		return null;
	}

}