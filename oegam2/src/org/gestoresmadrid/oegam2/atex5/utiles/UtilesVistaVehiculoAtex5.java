package org.gestoresmadrid.oegam2.atex5.utiles;

import java.io.Serializable;

import org.gestoresmadrid.core.atex5.model.enumerados.EstadoAtex5;
import org.gestoresmadrid.oegam2comun.atex5.view.dto.ConsultaVehiculoAtex5Dto;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

public class UtilesVistaVehiculoAtex5 implements Serializable {

	private static final long serialVersionUID = -6014941843263568397L;

	private static UtilesVistaVehiculoAtex5 utilesVistaVehiculoAtex5;

	public static UtilesVistaVehiculoAtex5 getInstance() {
		if (utilesVistaVehiculoAtex5 == null) {
			utilesVistaVehiculoAtex5 = new UtilesVistaVehiculoAtex5();
			SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(utilesVistaVehiculoAtex5);
		}
		return utilesVistaVehiculoAtex5;
	}

	public Boolean esEstadoGuardable(ConsultaVehiculoAtex5Dto consultaVehiculoAtex5Dto) {
		if (consultaVehiculoAtex5Dto != null && consultaVehiculoAtex5Dto.getEstado() != null && !consultaVehiculoAtex5Dto.getEstado().isEmpty()) {
			if (EstadoAtex5.Finalizado_PDF.getValorEnum().equals(consultaVehiculoAtex5Dto.getEstado()) 
				|| EstadoAtex5.Pdte_Respuesta_DGT.getValorEnum().equals(consultaVehiculoAtex5Dto.getEstado())
				|| EstadoAtex5.Anulado.getValorEnum().equals(consultaVehiculoAtex5Dto.getEstado())
				|| EstadoAtex5.Finalizado_Sin_Antecedentes.getValorEnum().equals(consultaVehiculoAtex5Dto.getEstado())) {
				return Boolean.FALSE;
			}
		}
		return Boolean.TRUE;
	}

	public Boolean esEstadoConsultable(ConsultaVehiculoAtex5Dto consultaVehiculoAtex5Dto) {
		if (consultaVehiculoAtex5Dto != null && consultaVehiculoAtex5Dto.getEstado() != null && !consultaVehiculoAtex5Dto.getEstado().isEmpty()) {
			if (EstadoAtex5.Iniciado.getValorEnum().equals(consultaVehiculoAtex5Dto.getEstado())) {
				return Boolean.TRUE;
			}
		}
		return Boolean.FALSE;
	}

	public Boolean esEstadoFinalizadoOK(ConsultaVehiculoAtex5Dto consultaVehiculoAtex5Dto) {
		if (consultaVehiculoAtex5Dto != null && consultaVehiculoAtex5Dto.getEstado() != null && !consultaVehiculoAtex5Dto.getEstado().isEmpty()) {
			if (EstadoAtex5.Finalizado_PDF.getValorEnum().equals(consultaVehiculoAtex5Dto.getEstado())
				|| EstadoAtex5.Finalizado_Sin_Antecedentes.getValorEnum().equals(consultaVehiculoAtex5Dto.getEstado())) {
				return Boolean.TRUE;
			}
		}
		return Boolean.FALSE;
	}

}