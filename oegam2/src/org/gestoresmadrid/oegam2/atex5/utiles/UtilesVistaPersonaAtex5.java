package org.gestoresmadrid.oegam2.atex5.utiles;

import java.io.Serializable;

import org.gestoresmadrid.core.atex5.model.enumerados.EstadoAtex5;
import org.gestoresmadrid.oegam2comun.atex5.view.dto.ConsultaPersonaAtex5Dto;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

public class UtilesVistaPersonaAtex5 implements Serializable {

	private static final long serialVersionUID = 3929747786511388859L;

	private static UtilesVistaPersonaAtex5 utilesVistaPersonaAtex5;

	public static UtilesVistaPersonaAtex5 getInstance() {
		if (utilesVistaPersonaAtex5 == null) {
			utilesVistaPersonaAtex5 = new UtilesVistaPersonaAtex5();
			SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(utilesVistaPersonaAtex5);
		}
		return utilesVistaPersonaAtex5;
	}

	public Boolean esEstadoGuardable(ConsultaPersonaAtex5Dto consultaPersonaAtex5Dto) {
		if (consultaPersonaAtex5Dto != null && consultaPersonaAtex5Dto.getEstado() != null && !consultaPersonaAtex5Dto.getEstado().isEmpty()) {
			if (EstadoAtex5.Finalizado_PDF.getValorEnum().equals(consultaPersonaAtex5Dto.getEstado()) 
					|| EstadoAtex5.Pdte_Respuesta_DGT.getValorEnum().equals(consultaPersonaAtex5Dto.getEstado())
					|| EstadoAtex5.Anulado.getValorEnum().equals(consultaPersonaAtex5Dto.getEstado())
					|| EstadoAtex5.Finalizado_Sin_Antecedentes.getValorEnum().equals(consultaPersonaAtex5Dto.getEstado())) {
					return Boolean.FALSE;
				}
		}
		return Boolean.TRUE;
	}

	public Boolean esEstadoConsultable(ConsultaPersonaAtex5Dto consultaPersonaAtex5Dto) {
		if (consultaPersonaAtex5Dto != null && consultaPersonaAtex5Dto.getEstado() != null && !consultaPersonaAtex5Dto.getEstado().isEmpty()) {
			if (EstadoAtex5.Iniciado.getValorEnum().equals(consultaPersonaAtex5Dto.getEstado())) {
				return Boolean.TRUE;
			}
		}
		return Boolean.FALSE;
	}

	public Boolean esEstadoFinalizadoOK(ConsultaPersonaAtex5Dto consultaPersonaAtex5Dto) {
		if (consultaPersonaAtex5Dto != null && consultaPersonaAtex5Dto.getEstado() != null && !consultaPersonaAtex5Dto.getEstado().isEmpty()) {
			if (EstadoAtex5.Finalizado_PDF.getValorEnum().equals(consultaPersonaAtex5Dto.getEstado())
					|| EstadoAtex5.Finalizado_Sin_Antecedentes.getValorEnum().equals(consultaPersonaAtex5Dto.getEstado())) {
					return Boolean.TRUE;
				}
		}
		return Boolean.FALSE;
	}
}
