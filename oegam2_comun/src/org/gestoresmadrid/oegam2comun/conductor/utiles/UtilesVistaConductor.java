package org.gestoresmadrid.oegam2comun.conductor.utiles;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.gestoresmadrid.core.arrendatarios.model.enumerados.EstadoCaycEnum;
import org.gestoresmadrid.core.arrendatarios.model.enumerados.TipoOperacionCaycEnum;
import org.gestoresmadrid.oegam2comun.conductor.view.dto.ConductorDto;
import org.gestoresmadrid.oegam2comun.direccion.model.service.ServicioMunicipio;
import org.gestoresmadrid.oegam2comun.direccion.model.service.ServicioProvincia;
import org.gestoresmadrid.oegam2comun.direccion.model.service.ServicioPueblo;
import org.gestoresmadrid.oegam2comun.direccion.model.service.ServicioTipoVia;
import org.gestoresmadrid.oegam2comun.direccion.view.dto.PuebloDto;
import org.gestoresmadrid.oegam2comun.direccion.view.dto.TipoViaDto;
import org.gestoresmadrid.oegamComun.direccion.view.dto.MunicipioDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

public class UtilesVistaConductor implements Serializable {

	private static final long serialVersionUID = -794665168951643721L;

	private static UtilesVistaConductor utilesVistaConductor;

	@Autowired
	ServicioProvincia servicioProvincia;

	@Autowired
	ServicioMunicipio servicioMunicipio;

	@Autowired
	ServicioPueblo servicioPueblo;

	@Autowired
	ServicioTipoVia servicioTipoVia;

	public static UtilesVistaConductor getInstance() {
		if (utilesVistaConductor == null) {
			utilesVistaConductor = new UtilesVistaConductor();
			SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(utilesVistaConductor);
		}
		return utilesVistaConductor;
	}

	public List<MunicipioDto> getListaMunicipiosPorProvincia(ConductorDto conductorDto) {
		if (conductorDto != null && conductorDto.getPersona() != null
				&& conductorDto.getPersona().getDireccionDto() != null
				&& conductorDto.getPersona().getDireccionDto().getIdProvincia() != null
				&& !conductorDto.getPersona().getDireccionDto().getIdProvincia().isEmpty()) {
			return servicioMunicipio.listaMunicipios(conductorDto.getPersona().getDireccionDto().getIdProvincia());
		}
		return Collections.emptyList();
	}

	public List<PuebloDto> getListaPueblos(ConductorDto conductorDto) {
		if (conductorDto != null && conductorDto.getPersona() != null
				&& conductorDto.getPersona().getDireccionDto() != null
				&& conductorDto.getPersona().getDireccionDto().getIdMunicipio() != null
				&& !conductorDto.getPersona().getDireccionDto().getIdMunicipio().isEmpty()
				&& conductorDto.getPersona().getDireccionDto().getIdProvincia() != null
				&& !conductorDto.getPersona().getDireccionDto().getIdProvincia().isEmpty()) {
			return servicioPueblo.listaPueblos(conductorDto.getPersona().getDireccionDto().getIdProvincia(),
					conductorDto.getPersona().getDireccionDto().getIdMunicipio());
		}
		return Collections.emptyList();
	}

	public List<TipoViaDto> getListaTipoVias(ConductorDto conductorDto) {
		if (conductorDto != null && conductorDto.getPersona() != null
				&& conductorDto.getPersona().getDireccionDto() != null
				&& conductorDto.getPersona().getDireccionDto().getIdProvincia() != null
				&& !conductorDto.getPersona().getDireccionDto().getIdProvincia().isEmpty()) {
			return servicioTipoVia.listadoTipoVias(conductorDto.getPersona().getDireccionDto().getIdProvincia());
		}
		return Collections.emptyList();
	}

	public Boolean esEstadoGuardable(ConductorDto conductorDto) {
		if (conductorDto != null && conductorDto.getEstado() != null && !conductorDto.getEstado().isEmpty()) {
			if (EstadoCaycEnum.Validado.getValorEnum().equals(conductorDto.getEstado())
					|| EstadoCaycEnum.Finalizado.getValorEnum().equals(conductorDto.getEstado())
					|| EstadoCaycEnum.Pdte_Respuesta_DGT.getValorEnum().equals(conductorDto.getEstado())) {
				return Boolean.FALSE;
			}
		}
		return Boolean.TRUE;
	}

	public Boolean esEstadoConsultable(ConductorDto conductorDto) {
		if (conductorDto != null && conductorDto.getEstado() != null && !conductorDto.getEstado().isEmpty()
				&& EstadoCaycEnum.Validado.getValorEnum().equals(conductorDto.getEstado())) {
			return Boolean.TRUE;
		}
		return Boolean.FALSE;
	}

	public Boolean esEstadoIniciado(ConductorDto conductorDto) {
		if (conductorDto != null && conductorDto.getEstado() != null && !conductorDto.getEstado().isEmpty()
				&& EstadoCaycEnum.Iniciado.getValorEnum().equals(conductorDto.getEstado())) {
			return Boolean.TRUE;
		}
		return Boolean.FALSE;
	}

	public EstadoCaycEnum[] getEstados() {
		return EstadoCaycEnum.values();
	}

	public String getEstado(int i) {
		EstadoCaycEnum[] lista = getEstados();
		return lista[i].getNombreEnum();
	}

	public List<TipoOperacionCaycEnum> getTiposOperacion() {
		// Autor: DSR
		// Fecha: 12/2/2018
		// Descripcion: ÑAPA ÑAPA
		List<TipoOperacionCaycEnum> temp = new ArrayList<>();
		for (TipoOperacionCaycEnum tipo : TipoOperacionCaycEnum.values()) {
			if (tipo.getNombreEnum().contains(("Condu"))) {
				temp.add(tipo);
			}
		}
		return temp;
	}

	public Boolean esOperacionAltaConductor(ConductorDto conductorDto) {
		if (conductorDto != null && conductorDto.getOperacion() != null && !conductorDto.getOperacion().isEmpty()
				&& TipoOperacionCaycEnum.Alta_Conductor.getValorEnum().equals(conductorDto.getOperacion())) {
			return Boolean.TRUE;
		}
		return Boolean.FALSE;
	}

}