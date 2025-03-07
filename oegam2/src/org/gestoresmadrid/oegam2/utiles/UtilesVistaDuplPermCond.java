package org.gestoresmadrid.oegam2.utiles;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.gestoresmadrid.core.trafico.duplicado.permiso.conducir.model.enumerados.TiposDocDuplicadosPermisos;
import org.gestoresmadrid.core.trafico.duplicado.permiso.conducir.model.enumerados.TiposDuplicadosPermisos;
import org.gestoresmadrid.core.trafico.interga.model.enumerados.EstadoTramitesInterga;
import org.gestoresmadrid.oegam2comun.paises.model.service.ServicioPais;
import org.gestoresmadrid.oegam2comun.paises.view.dto.PaisDto;
import org.gestoresmadrid.oegamDuplicadoPermisoConducir.view.dto.DuplicadoPermisoConducirDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

public class UtilesVistaDuplPermCond {

	private static UtilesVistaDuplPermCond utilesVistaPermisoInter;

	@Autowired
	ServicioPais servicioPais;

	public static UtilesVistaDuplPermCond getInstance() {
		if (utilesVistaPermisoInter == null) {
			utilesVistaPermisoInter = new UtilesVistaDuplPermCond();
			SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(utilesVistaPermisoInter);
		}
		return utilesVistaPermisoInter;
	}

	public boolean esGuardable(DuplicadoPermisoConducirDto duplicadoPermisoConducirDto) {
		return (duplicadoPermisoConducirDto.getEstado() == null || duplicadoPermisoConducirDto.getEstado().isEmpty() || duplicadoPermisoConducirDto.getEstado().equals(EstadoTramitesInterga.Iniciado
				.getValorEnum()) || duplicadoPermisoConducirDto.getEstado().equals(EstadoTramitesInterga.Validado.getValorEnum()) || duplicadoPermisoConducirDto.getEstado().equals(
						EstadoTramitesInterga.Documentacion_Firmada.getValorEnum()) || duplicadoPermisoConducirDto.getEstado().equals(EstadoTramitesInterga.Finalizado_Error.getValorEnum())
				|| duplicadoPermisoConducirDto.getEstado().equals(EstadoTramitesInterga.Error_Firma_Documentos.getValorEnum()));
	}

	public boolean puedeFirmarDeclaracionYSolicitud(DuplicadoPermisoConducirDto duplicadoPermisoConducirDto) {
		return (StringUtils.isNotBlank(duplicadoPermisoConducirDto.getEstado()) && (duplicadoPermisoConducirDto.getEstado().equals(EstadoTramitesInterga.Validado.getValorEnum())
				|| duplicadoPermisoConducirDto.getEstado().equals(EstadoTramitesInterga.Error_Firma_Documentos.getValorEnum())));
	}

	public boolean esValidable(DuplicadoPermisoConducirDto duplicadoPermisoConducirDto) {
		return (StringUtils.isNotBlank(duplicadoPermisoConducirDto.getEstado()) && duplicadoPermisoConducirDto.getEstado().equals(EstadoTramitesInterga.Iniciado.getValorEnum()));
	}

	public boolean esTramitable(DuplicadoPermisoConducirDto duplicadoPermisoConducirDto) {
		return (StringUtils.isNotBlank(duplicadoPermisoConducirDto.getEstado()) && duplicadoPermisoConducirDto.getEstado().equals(EstadoTramitesInterga.Documentacion_Firmada.getValorEnum()));
	}

	public boolean puedeGenerarMandato(DuplicadoPermisoConducirDto duplicadoPermisoConducirDto) {
		return (duplicadoPermisoConducirDto.getTitular() != null && StringUtils.isNotBlank(duplicadoPermisoConducirDto.getTitular().getNif()) && StringUtils.isNotBlank(duplicadoPermisoConducirDto
				.getEstadoImpresion()) && !duplicadoPermisoConducirDto.getEstadoImpresion().equals(EstadoTramitesInterga.Impresion_OK.getValorEnum()) && !duplicadoPermisoConducirDto
						.getEstadoImpresion().equals(EstadoTramitesInterga.Doc_Recibida.getValorEnum()));
	}

	public boolean esAccionDoc(DuplicadoPermisoConducirDto duplicadoPermisoConducirDto) {
		return (StringUtils.isNotBlank(duplicadoPermisoConducirDto.getEstadoImpresion()) && !duplicadoPermisoConducirDto.getEstadoImpresion().equals(EstadoTramitesInterga.Impresion_OK.getValorEnum())
				&& !duplicadoPermisoConducirDto.getEstadoImpresion().equals(EstadoTramitesInterga.Doc_Recibida.getValorEnum()));
	}

	public TiposDuplicadosPermisos[] getTiposDuplicado() {
		return TiposDuplicadosPermisos.values();
	}

	public Boolean existeTitular(DuplicadoPermisoConducirDto duplicadoPermisoConducirDto) {
		if (duplicadoPermisoConducirDto.getTitular() != null && duplicadoPermisoConducirDto.getTitular().getNif() != null && !duplicadoPermisoConducirDto.getTitular().getNif().isEmpty()
				&& duplicadoPermisoConducirDto.getTitular().getNumColegiado() != null && !duplicadoPermisoConducirDto.getTitular().getNumColegiado().isEmpty()) {
			return Boolean.TRUE;
		}
		return Boolean.FALSE;
	}

	public boolean esImprimible(DuplicadoPermisoConducirDto duplicadoPermisoConducirDto) {
		return (StringUtils.isNotBlank(duplicadoPermisoConducirDto.getEstado()) && duplicadoPermisoConducirDto.getEstado().equals(EstadoTramitesInterga.Finalizado.getValorEnum()) && StringUtils
				.isNotBlank(duplicadoPermisoConducirDto.getEstadoImpresion()) && duplicadoPermisoConducirDto.getEstadoImpresion().equals(EstadoTramitesInterga.Doc_Recibida.getValorEnum()));
	}

	public boolean esEnviableDocumentacion(DuplicadoPermisoConducirDto duplicadoPermisoConducirDto) {
		return (StringUtils.isNotBlank(duplicadoPermisoConducirDto.getEstado()) && duplicadoPermisoConducirDto.getEstado().equals(EstadoTramitesInterga.Pendiente_Envio_Documentos.getValorEnum())
				&& StringUtils.isNotBlank(duplicadoPermisoConducirDto.getEstadoImpresion()) && duplicadoPermisoConducirDto.getEstadoImpresion().equals(EstadoTramitesInterga.Doc_Parcialmente_Subida
						.getValorEnum()));
	}

	public List<PaisDto> getListaPaises() {
		return servicioPais.paises();
	}

	public List<TiposDocDuplicadosPermisos> getTipoDocumentos() {
		return TiposDocDuplicadosPermisos.listaTipoDocumentos();
	}
}