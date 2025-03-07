package org.gestoresmadrid.oegam2.trafico.empresa.telematica.utiles;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;

import org.gestoresmadrid.core.model.beans.DatoMaestroBean;
import org.gestoresmadrid.oegam2comun.contrato.model.service.ServicioContrato;
import org.gestoresmadrid.oegam2comun.direccion.model.service.ServicioMunicipio;
import org.gestoresmadrid.oegam2comun.direccion.model.service.ServicioProvincia;
import org.gestoresmadrid.oegam2comun.trafico.empresa.telematica.view.beans.EstadoEmpresaTelematica;
import org.gestoresmadrid.oegam2comun.trafico.empresa.telematica.view.dto.EmpresaTelematicaDto;
import org.gestoresmadrid.oegamComun.acceso.service.impl.UtilesColegiado;
import org.gestoresmadrid.oegamComun.direccion.view.dto.MunicipioDto;
import org.gestoresmadrid.oegamComun.direccion.view.dto.ProvinciaDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

public class UtilesVistaEmpresaTelematica implements Serializable{
	
	private static final long serialVersionUID = 8733965188112679324L;
	private static UtilesVistaEmpresaTelematica utilesVistaEmpresaTelematica;
	
	@Autowired
	ServicioContrato servicioContrato;
	
	@Autowired
	ServicioMunicipio servicioMunicipio;
	
	@Autowired
	ServicioProvincia servicioProvincia;
	
	@Autowired
	private UtilesColegiado utilesColegiado;
	
	public static UtilesVistaEmpresaTelematica getInstance(){
		if(utilesVistaEmpresaTelematica == null){
			utilesVistaEmpresaTelematica = new UtilesVistaEmpresaTelematica();
			SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(utilesVistaEmpresaTelematica);
		}
		return utilesVistaEmpresaTelematica;
	}

	public EstadoEmpresaTelematica[] getEstados(){
		return EstadoEmpresaTelematica.values();
	}
	
	public List<MunicipioDto> getMunicipios(String idProvincia){
		if (idProvincia != null && !idProvincia.isEmpty())
			return servicioMunicipio.listaMunicipios(idProvincia);
		return Collections.emptyList();
	}
	
	public List<ProvinciaDto> getProvincias(){
		return servicioProvincia.getListaProvincias();
	}
	
	public List<DatoMaestroBean> getComboContratosHabilitados(){
		return servicioContrato.getComboContratosHabilitados();
	}
	
	public Boolean esAdmin(){
		return utilesColegiado.tienePermisoAdmin();
	}
	
	public Boolean esEstadoGuardable(EmpresaTelematicaDto empresaTelematicaDto){
		if(empresaTelematicaDto == null || empresaTelematicaDto.getEstado() == null || empresaTelematicaDto.getEstado().isEmpty()
			|| EstadoEmpresaTelematica.Activo.getValorEnum().equals(empresaTelematicaDto.getEstado())){
			return Boolean.TRUE;
		}
		return Boolean.FALSE;
	}

}
