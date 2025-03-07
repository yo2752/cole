package org.gestoresmadrid.oegam2comun.arrendatarios.utiles;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.gestoresmadrid.core.arrendatarios.model.enumerados.EstadoCaycEnum;
import org.gestoresmadrid.core.arrendatarios.model.enumerados.TipoOperacionCaycEnum;
import org.gestoresmadrid.core.model.beans.DatoMaestroBean;
import org.gestoresmadrid.core.personas.model.enumerados.TipoPersona;
import org.gestoresmadrid.oegam2comun.arrendatarios.view.dto.ArrendatarioDto;
import org.gestoresmadrid.oegam2comun.contrato.model.service.ServicioContrato;
import org.gestoresmadrid.oegam2comun.direccion.model.service.ServicioMunicipio;
import org.gestoresmadrid.oegam2comun.direccion.model.service.ServicioProvincia;
import org.gestoresmadrid.oegam2comun.direccion.model.service.ServicioPueblo;
import org.gestoresmadrid.oegam2comun.direccion.model.service.ServicioTipoVia;
import org.gestoresmadrid.oegam2comun.direccion.view.dto.PuebloDto;
import org.gestoresmadrid.oegam2comun.direccion.view.dto.TipoViaDto;
import org.gestoresmadrid.oegamComun.direccion.view.dto.MunicipioDto;
import org.gestoresmadrid.oegamComun.direccion.view.dto.ProvinciaDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import escrituras.utiles.enumerados.SexoPersona;

public class UtilesVistaArrendatarios implements Serializable{

	private static final long serialVersionUID = 7806707074596451769L;

	private static UtilesVistaArrendatarios utilesVistaArrendatarios;
	
	@Autowired
	ServicioProvincia servicioProvincia;	
	
	@Autowired
	ServicioMunicipio servicioMunicipio;
	
	@Autowired
	ServicioPueblo servicioPueblo;
	
	@Autowired
	ServicioTipoVia servicioTipoVia;
	
	@Autowired
	ServicioContrato servicioContrato;
	
	public static UtilesVistaArrendatarios getInstance(){
		if (utilesVistaArrendatarios == null) {
			utilesVistaArrendatarios = new UtilesVistaArrendatarios();
			SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(utilesVistaArrendatarios);
		}
		return utilesVistaArrendatarios;	
	}
	
	public TipoPersona[] getComboTipoPersonas() {
		return TipoPersona.values();
	}
	
	public SexoPersona[] getComboSexo() {
		return SexoPersona.values();
	}
	
	public List<ProvinciaDto> getListaProvincias(){
		return servicioProvincia.getListaProvincias();
	}
	
	public List<MunicipioDto> getListaMunicipiosPorProvincia(ArrendatarioDto arrendatarioDto){		
		if(arrendatarioDto != null && arrendatarioDto.getPersona() != null && arrendatarioDto.getPersona().getDireccionDto() != null && 
				arrendatarioDto.getPersona().getDireccionDto().getIdProvincia() != null && !arrendatarioDto.getPersona().getDireccionDto().getIdProvincia().isEmpty()){
			return servicioMunicipio.listaMunicipios(arrendatarioDto.getPersona().getDireccionDto().getIdProvincia());
		}
		return Collections.emptyList();
	}
	
	public List<PuebloDto> getListaPueblos(ArrendatarioDto arrendatarioDto){
		if(arrendatarioDto != null && arrendatarioDto.getPersona() != null && arrendatarioDto.getPersona().getDireccionDto() != null && 
				arrendatarioDto.getPersona().getDireccionDto().getIdMunicipio() != null && !arrendatarioDto.getPersona().getDireccionDto().getIdMunicipio().isEmpty() && 
				arrendatarioDto.getPersona().getDireccionDto().getIdProvincia() != null && !arrendatarioDto.getPersona().getDireccionDto().getIdProvincia().isEmpty()){
			return servicioPueblo.listaPueblos(arrendatarioDto.getPersona().getDireccionDto().getIdProvincia(), arrendatarioDto.getPersona().getDireccionDto().getIdMunicipio());
		}
		return Collections.emptyList();
	}
	
	public List<TipoViaDto> getListaTipoVias(ArrendatarioDto arrendatarioDto){
		if(arrendatarioDto != null && arrendatarioDto.getPersona() != null && arrendatarioDto.getPersona().getDireccionDto() != null && 
				arrendatarioDto.getPersona().getDireccionDto().getIdProvincia() != null && !arrendatarioDto.getPersona().getDireccionDto().getIdProvincia().isEmpty()){
			return servicioTipoVia.listadoTipoVias(arrendatarioDto.getPersona().getDireccionDto().getIdProvincia());
		}
		return Collections.emptyList();
	}
	
	public Boolean esEstadoGuardable(ArrendatarioDto arrendatarioDto) {
		if (arrendatarioDto != null && arrendatarioDto.getEstado() != null && !arrendatarioDto.getEstado().isEmpty()) {
			if (EstadoCaycEnum.Validado.getValorEnum().equals(arrendatarioDto.getEstado()) || EstadoCaycEnum.Finalizado.getValorEnum().equals(arrendatarioDto.getEstado()) 
				|| EstadoCaycEnum.Pdte_Respuesta_DGT.getValorEnum().equals(arrendatarioDto.getEstado())){			
				return Boolean.FALSE;
			}
		}
		return Boolean.TRUE;
	} 
	
	public Boolean esEstadoConsultable(ArrendatarioDto arrendatarioDto) {
		if (arrendatarioDto != null && arrendatarioDto.getEstado() != null && !arrendatarioDto.getEstado().isEmpty()) {
			if (EstadoCaycEnum.Validado.getValorEnum().equals(arrendatarioDto.getEstado())) {
				return Boolean.TRUE;
			}
		}
		return Boolean.FALSE;
	}
	
	public Boolean esEstadoIniciado (ArrendatarioDto arrendatarioDto){
		if (arrendatarioDto != null && arrendatarioDto.getEstado() != null && !arrendatarioDto.getEstado().isEmpty()) {
			if (EstadoCaycEnum.Iniciado.getValorEnum().equals(arrendatarioDto.getEstado())) {
				return Boolean.TRUE;
			}
		}
		return Boolean.FALSE;
	}
	
	public List<DatoMaestroBean> getComboContratosHabilitados() {
		return servicioContrato.getComboContratosHabilitados();
	}
	
	
	public EstadoCaycEnum[] getEstados() {
        return EstadoCaycEnum.values();
    }

	public List<TipoOperacionCaycEnum> getTiposOperacion() {
	    
	    // Autor: DSR
	    // FECHA: 12/2/2018
	    // Descripcion: ÑAPA ÑAPA ÑAPA 
	    List <TipoOperacionCaycEnum>  temp=new ArrayList<TipoOperacionCaycEnum>() ;
	            for(TipoOperacionCaycEnum tipo : TipoOperacionCaycEnum.values()){
                if(tipo.getNombreEnum().contains(("Arrenda"))){
                    temp.add(tipo);
                }
            }
	    
       
        return temp;
    }
	
	
    public String getEstado(int i) {
      
        EstadoCaycEnum[] Lista=getEstados();       
        
        return Lista[i].getNombreEnum();
    }
    
    public Boolean esOperacionAltaArrendatario(ArrendatarioDto arrendatarioDto){
    	if (arrendatarioDto!=null && arrendatarioDto.getOperacion()!=null && !arrendatarioDto.getOperacion().isEmpty()){
    		if (TipoOperacionCaycEnum.Alta_Arrendatario.getValorEnum().equals(arrendatarioDto.getOperacion().toString())){
    			return Boolean.TRUE;
    		}    			
    	}    	
    	return Boolean.FALSE;    	
    }

	
 
}
