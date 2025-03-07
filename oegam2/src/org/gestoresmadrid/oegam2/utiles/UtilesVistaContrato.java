package org.gestoresmadrid.oegam2.utiles;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.gestoresmadrid.core.contrato.model.enumerados.EstadoContrato;
import org.gestoresmadrid.core.contrato.model.enumerados.EstadoMobileGest;
import org.gestoresmadrid.core.model.beans.DatoMaestroBean;
import org.gestoresmadrid.oegam2comun.contrato.model.service.ServicioContrato;
import org.gestoresmadrid.oegam2comun.contrato.model.service.ServicioContratoColegiado;
import org.gestoresmadrid.oegam2comun.direccion.model.service.ServicioProvincia;
import org.gestoresmadrid.oegam2comun.model.service.ServicioAplicacion;
import org.gestoresmadrid.oegam2comun.model.service.ServicioTipoTramite;
import org.gestoresmadrid.oegamComun.acceso.service.impl.UtilesColegiado;
import org.gestoresmadrid.oegamComun.accesos.view.dto.AplicacionDto;
import org.gestoresmadrid.oegamComun.direccion.view.dto.ProvinciaDto;
import org.gestoresmadrid.oegamComun.trafico.view.dto.TipoTramiteDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

public class UtilesVistaContrato {

	private static UtilesVistaContrato utilesVistaContrato;

	@Autowired
	ServicioContrato servicioContrato;

	@Autowired
	ServicioProvincia servicioProvincia;

	@Autowired
	ServicioContratoColegiado servicioContratoColegiado;

	@Autowired
	ServicioAplicacion servicioAplicacion;

	@Autowired
	ServicioTipoTramite servicioTipoTramite;

	@Autowired
	private UtilesColegiado utilesColegiado;

	public synchronized static UtilesVistaContrato getInstance() {
		if (utilesVistaContrato == null) {
			utilesVistaContrato = new UtilesVistaContrato();
			SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(utilesVistaContrato);
		}
		return utilesVistaContrato;
	}

	public List<DatoMaestroBean> getComboContratosHabilitados() {
		return servicioContrato.getComboContratosHabilitados();
	}

	public List<DatoMaestroBean> getComboContratosHabilitadosColegio(BigDecimal idContrato) {
		return servicioContrato.getComboContratosHabilitadosColegio(idContrato);
	}

	public List<DatoMaestroBean> getComboContratosHabilitadosColegiado(String numColegiado) {
		return servicioContrato.getComboContratosHabilitadosColegiado(numColegiado);
	}

	public EstadoContrato[] getEstadosContratos() {
		return EstadoContrato.values();
	}

	public EstadoMobileGest[] getEsMobileGest() {
		return EstadoMobileGest.values();
	}

	public List<ProvinciaDto> getListaProvincias() {
		return servicioProvincia.getListaProvincias();
	}

	public String getNombreContrato(BigDecimal idContrato) {
		return idContrato != null
				? (servicioContrato.getContratoDto(idContrato).getColegiadoDto().getNumColegiado() + "-"
						+ servicioContrato.getContratoDto(idContrato).getVia())
				: "";
	}

	public boolean esUsuarioDelContrato(BigDecimal idContrato) {
		if (idContrato != null) {
			return servicioContratoColegiado.esUsuarioDelContrato(idContrato, utilesColegiado.getIdUsuarioSessionBigDecimal());
		}
		return false;
	}

	public List<AplicacionDto> getListAplicacionesPorContrato(BigDecimal idContrato) {
		if(idContrato != null) {
			return servicioContrato.getAplicacionesPorContrato(idContrato);
		}
		return new ArrayList<AplicacionDto>();
	}

	public List<TipoTramiteDto> getListTipoTramiteConsulta(String codigoAplicacion) {
		if (StringUtils.isBlank(codigoAplicacion)) {
			return new ArrayList<>();
		}
		List<TipoTramiteDto> tipoTramites = servicioTipoTramite.getTipoTramitePorAplicacion(codigoAplicacion);
		if (tipoTramites != null) {
			return tipoTramites;
		} else {
			return new ArrayList<>();
		}
	}

//	public List<AplicacionDto> getListAplicaciones() {
//		List <Object[]> listTramites = servicioTipoTramite.getTipoTramiteConAplicacion();
//		List<AplicacionDto> listAplicaciones = new ArrayList<>();
//		for (Object[] tramite : listTramites) {
//			AplicacionDto aplicacion = new AplicacionDto();
//			aplicacion.setCodigoAplicacion((String)tramite[0]);
//			aplicacion.setDescAplicacion((String)tramite[1]);
//			listAplicaciones.add(aplicacion);
//		}
//		return listAplicaciones;
//	}

}