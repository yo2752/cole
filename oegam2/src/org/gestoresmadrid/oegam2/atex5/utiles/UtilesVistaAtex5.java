package org.gestoresmadrid.oegam2.atex5.utiles;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.gestoresmadrid.core.atex5.model.enumerados.EstadoAtex5;
import org.gestoresmadrid.core.model.beans.DatoMaestroBean;
import org.gestoresmadrid.core.model.enumerados.TipoTasa;
import org.gestoresmadrid.oegam2comun.atex5.view.dto.ConsultaPersonaAtex5Dto;
import org.gestoresmadrid.oegam2comun.atex5.view.dto.ConsultaVehiculoAtex5Dto;
import org.gestoresmadrid.oegam2comun.contrato.model.service.ServicioContrato;
import org.gestoresmadrid.oegam2comun.tasas.model.service.ServicioTasa;
import org.gestoresmadrid.oegamComun.tasa.view.dto.TasaDto;
import org.gestoresmadrid.utilidades.components.GestorPropiedades;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

public class UtilesVistaAtex5 implements Serializable {

	private static final long serialVersionUID = -6650737998523692208L;

	private static UtilesVistaAtex5 utilesVistaAtex5;

	@Autowired
	private ServicioContrato servicioContrato;

	@Autowired
	private ServicioTasa servicioTasa;

	@Autowired
	private GestorPropiedades gestorPropiedades;

	public static UtilesVistaAtex5 getInstance() {
		if (utilesVistaAtex5 == null) {
			utilesVistaAtex5 = new UtilesVistaAtex5();
			SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(utilesVistaAtex5);
		}
		return utilesVistaAtex5;
	}

	public EstadoAtex5[] getEstados() {
		return EstadoAtex5.values();
	}

	public List<DatoMaestroBean> getComboContratosHabilitados() {
		return servicioContrato.getComboContratosHabilitados();
	}

	public List<TasaDto> getTasasContratoInformatica() {
		String numContrato = gestorPropiedades.valorPropertie("contrato.informatica");
		if (numContrato != null && !numContrato.isEmpty()) {
			List<TasaDto> codigosTasaAtex = servicioTasa.obtenerTasasContrato(new Long(numContrato), TipoTasa.CuatroUno.getValorEnum());
			if (codigosTasaAtex != null && !codigosTasaAtex.isEmpty()) {
				return codigosTasaAtex;
			}
		}
		return Collections.emptyList();
	}

	public Boolean utilizarTasasAtex5() {
		String utilizarTasas = gestorPropiedades.valorPropertie("nuevo.atex5.tasas");
		if ("SI".equalsIgnoreCase(utilizarTasas)) {
			return Boolean.TRUE;
		}
		return Boolean.FALSE;
	}

	public Boolean esOkVisualizarDatosVehiculo(ConsultaVehiculoAtex5Dto consultaVehiculoAtex5Dto) {
		if (consultaVehiculoAtex5Dto != null && EstadoAtex5.Finalizado_PDF.getValorEnum().equals(consultaVehiculoAtex5Dto.getEstado())
			&& consultaVehiculoAtex5Dto.getVehiculoAtex5() != null){
			return Boolean.TRUE;
		}
		return Boolean.FALSE;
	}

	public Boolean esOkVisualizarDatosVehiculoTitular(ConsultaVehiculoAtex5Dto consultaVehiculoAtex5Dto){
		if(consultaVehiculoAtex5Dto != null && EstadoAtex5.Finalizado_PDF.getValorEnum().equals(consultaVehiculoAtex5Dto.getEstado())
			&& consultaVehiculoAtex5Dto.getVehiculoAtex5() != null) {
			if (consultaVehiculoAtex5Dto.getVehiculoAtex5().getPersonaFisicaDto() != null || consultaVehiculoAtex5Dto.getVehiculoAtex5().getPersonaJuridicaDto() != null) {
				return Boolean.TRUE;
			}
		}
		return Boolean.FALSE;
	}

	public Boolean esOkVisualizarDatosPersona(ConsultaPersonaAtex5Dto consultaPersonaAtex5Dto) {
		if (consultaPersonaAtex5Dto != null && EstadoAtex5.Finalizado_PDF.getValorEnum().equals(consultaPersonaAtex5Dto.getEstado())
			&& consultaPersonaAtex5Dto.getPersonaAtex5() != null) {
			return Boolean.TRUE;
		}
		return Boolean.FALSE;
	}

	public String convertirFecha(Date fecha) {
		if (fecha != null) {
			return new SimpleDateFormat("dd/MM/yyyy").format(fecha);
		}
		return "";
	}
}