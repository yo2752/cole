package org.gestoresmadrid.oegam2.utiles;

import org.apache.commons.lang3.StringUtils;
import org.gestoresmadrid.core.trafico.interga.model.enumerados.EstadoDeclaracion;
import org.gestoresmadrid.core.trafico.interga.model.enumerados.EstadoTramitesInterga;
import org.gestoresmadrid.oegamComun.acceso.service.impl.UtilesColegiado;
import org.gestoresmadrid.oegamPermisoInternacional.view.dto.PermisoInternacionalDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

public class UtilesVistaPermisoInter {

	private static UtilesVistaPermisoInter utilesVistaPermisoInter;

	@Autowired
	private UtilesColegiado utilesColegiado;

	public static UtilesVistaPermisoInter getInstance() {
		if (utilesVistaPermisoInter == null) {
			utilesVistaPermisoInter = new UtilesVistaPermisoInter();
			SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(utilesVistaPermisoInter);
		}
		return utilesVistaPermisoInter;
	}

	public boolean esGuardable(PermisoInternacionalDto permisoInternacionalDto) {
		return (permisoInternacionalDto.getEstado() == null || permisoInternacionalDto.getEstado().isEmpty() || permisoInternacionalDto.getEstado().equals(EstadoTramitesInterga.Iniciado.getValorEnum())
				|| permisoInternacionalDto.getEstado().equals(EstadoTramitesInterga.Validado.getValorEnum()) || permisoInternacionalDto.getEstado().equals(EstadoTramitesInterga.Declaracion_Firmada
						.getValorEnum()) || permisoInternacionalDto.getEstado().equals(EstadoTramitesInterga.Finalizado_Error.getValorEnum()));
	}

	public boolean puedeEnviarDeclaracion(PermisoInternacionalDto permisoInternacionalDto) {
		return (StringUtils.isNotBlank(permisoInternacionalDto.getEstado()) && permisoInternacionalDto.getEstado().equals(EstadoTramitesInterga.Iniciado.getValorEnum()));
	}

	public boolean esValidable(PermisoInternacionalDto permisoInternacionalDto) {
		return (StringUtils.isNotBlank(permisoInternacionalDto.getEstado()) && permisoInternacionalDto.getEstado().equals(EstadoTramitesInterga.Declaracion_Firmada.getValorEnum()));
	}

	public boolean esTramitable(PermisoInternacionalDto permisoInternacionalDto) {
		return (StringUtils.isNotBlank(permisoInternacionalDto.getEstado()) && permisoInternacionalDto.getEstado().equals(EstadoTramitesInterga.Validado.getValorEnum()));
	}

	public boolean esImprimible(PermisoInternacionalDto permisoInternacionalDto) {
		return (utilesColegiado.tienePermisoAdmin() && StringUtils.isNotBlank(permisoInternacionalDto.getEstado())
				&& permisoInternacionalDto.getEstado().equals(EstadoTramitesInterga.Finalizado.getValorEnum())
				&& StringUtils.isNotBlank(permisoInternacionalDto.getEstadoImpresion())
				&& permisoInternacionalDto.getEstadoImpresion()
						.equals(EstadoTramitesInterga.Doc_Recibida.getValorEnum())
				&& StringUtils.isNotBlank(permisoInternacionalDto.getEstadoDeclaracion())
				&& permisoInternacionalDto.getEstadoDeclaracion().equals(EstadoDeclaracion.Enviados.getValorEnum()));
	}

	public boolean esEnviableDeclaracionColegiado(PermisoInternacionalDto permisoInternacionalDto) {
		return (StringUtils.isNotBlank(permisoInternacionalDto.getEstado()) && permisoInternacionalDto.getEstado().equals(EstadoTramitesInterga.Finalizado_Pdt_Declaracion.getValorEnum()));
	}

	public boolean esEnviableDeclaracionColegio(PermisoInternacionalDto permisoInternacionalDto) {
		return (utilesColegiado.tienePermisoAdmin() && StringUtils.isNotBlank(permisoInternacionalDto.getEstado())
				&& permisoInternacionalDto.getEstado().equals(EstadoTramitesInterga.Finalizado.getValorEnum())
				&& StringUtils.isNotBlank(permisoInternacionalDto.getEstadoDeclaracion()) && permisoInternacionalDto
						.getEstadoDeclaracion().equals(EstadoDeclaracion.No_Enviado_Colegio.getValorEnum()));
	}

	public boolean esEliminableMandato(PermisoInternacionalDto permisoInternacionalDto) {
		return (StringUtils.isNotBlank(permisoInternacionalDto.getEstadoImpresion()) && !permisoInternacionalDto.getEstadoImpresion().equals(EstadoTramitesInterga.Doc_Subida.getValorEnum()));
	}

	public boolean hayDocumentacionAportada(PermisoInternacionalDto permisoInternacionalDto) {
		return (permisoInternacionalDto.getDocumentacionAportada() != null && "S".equals(permisoInternacionalDto.getDocumentacionAportada()));
	}

	public boolean puedeGenerarMandato(PermisoInternacionalDto permisoInternacionalDto) {
		return (permisoInternacionalDto.getTitular() != null && StringUtils.isNotBlank(permisoInternacionalDto.getTitular().getNif()));
	}

	public Boolean esEstadoTramiteAportacionDoc(PermisoInternacionalDto permisoInternacionalDto) {
		if (utilesColegiado.tienePermisoAdmin()
				&& (StringUtils.isNotBlank(permisoInternacionalDto.getEstado()) && StringUtils.isNotBlank(permisoInternacionalDto.getEstadoImpresion())) && EstadoTramitesInterga.Finalizado.getValorEnum()
					.equals(permisoInternacionalDto.getEstado()) && (EstadoTramitesInterga.Impresion_OK.getValorEnum().equals(permisoInternacionalDto.getEstadoImpresion())
						|| EstadoTramitesInterga.Doc_Subida.getValorEnum().equals(permisoInternacionalDto.getEstadoImpresion()))) {
			return Boolean.TRUE;
		}
		return Boolean.FALSE;
	}

	public Boolean existeTitular(PermisoInternacionalDto permisoInternacionalDto) {
		if (permisoInternacionalDto.getTitular() != null && permisoInternacionalDto.getTitular().getNif() != null && !permisoInternacionalDto.getTitular().getNif().isEmpty() && permisoInternacionalDto
				.getTitular().getNumColegiado() != null && !permisoInternacionalDto.getTitular().getNumColegiado().isEmpty()) {
			return Boolean.TRUE;
		}
		return Boolean.FALSE;
	}
}