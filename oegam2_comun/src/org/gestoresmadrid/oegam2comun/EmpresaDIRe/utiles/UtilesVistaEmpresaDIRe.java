package org.gestoresmadrid.oegam2comun.EmpresaDIRe.utiles;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.gestoresmadrid.core.arrendatarios.model.enumerados.EstadoCaycEnum;
import org.gestoresmadrid.core.arrendatarios.model.enumerados.TipoOperacionCaycEnum;
import org.gestoresmadrid.core.model.beans.DatoMaestroBean;
import org.gestoresmadrid.core.personas.model.enumerados.TipoPersona;
import org.gestoresmadrid.oegam2comun.EmpresaDIRe.view.dto.EmpresaDIReDto;
import org.gestoresmadrid.oegam2comun.contrato.model.service.ServicioContrato;
import org.gestoresmadrid.oegam2comun.direccion.model.service.ServicioMunicipio;
import org.gestoresmadrid.oegam2comun.direccion.model.service.ServicioProvincia;
import org.gestoresmadrid.oegam2comun.direccion.model.service.ServicioPueblo;
import org.gestoresmadrid.oegam2comun.direccion.model.service.ServicioTipoVia;
import org.gestoresmadrid.oegamComun.direccion.view.dto.ProvinciaDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import escrituras.utiles.enumerados.SexoPersona;

public class UtilesVistaEmpresaDIRe implements Serializable{

	private static final long serialVersionUID = 7806707074596451769L;

	private static UtilesVistaEmpresaDIRe utilesVistaEmpresaDIRe;
	
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
	
	public static UtilesVistaEmpresaDIRe getInstance(){
		if (utilesVistaEmpresaDIRe == null) {
			utilesVistaEmpresaDIRe = new UtilesVistaEmpresaDIRe();
			SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(utilesVistaEmpresaDIRe);
		}
		return utilesVistaEmpresaDIRe;	
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
	
	
	
	
	
	
	
	public Boolean esEstadoGuardable(EmpresaDIReDto empresaDIReDto) {
		if (empresaDIReDto != null && empresaDIReDto.getEstado() != null && !empresaDIReDto.getEstado().isEmpty()) {
			if (EstadoCaycEnum.Validado.getValorEnum().equals(empresaDIReDto.getEstado()) || EstadoCaycEnum.Finalizado.getValorEnum().equals(empresaDIReDto.getEstado()) 
				|| EstadoCaycEnum.Pdte_Respuesta_DGT.getValorEnum().equals(empresaDIReDto.getEstado())){			
				return Boolean.FALSE;
			}
		}
		return Boolean.TRUE;
	} 
	
	public Boolean esEstadoConsultable(EmpresaDIReDto empresaDIReDto) {
		if (empresaDIReDto != null && empresaDIReDto.getEstado() != null && !empresaDIReDto.getEstado().isEmpty()) {
			if (EstadoCaycEnum.Validado.getValorEnum().equals(empresaDIReDto.getEstado())) {
				return Boolean.TRUE;
			}
		}
		return Boolean.FALSE;
	}
	
	public Boolean esEstadoIniciado (EmpresaDIReDto empresaDIReDto){
		if (empresaDIReDto != null && empresaDIReDto.getEstado() != null && !empresaDIReDto.getEstado().isEmpty()) {
			if (EstadoCaycEnum.Iniciado.getValorEnum().equals(empresaDIReDto.getEstado())) {
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
                if(tipo.getNombreEnum().contains(("DIRe"))){
                    temp.add(tipo);
                }
            }
	    
       
        return temp;
    }
	
	
    public String getEstado(int i) {
      
        EstadoCaycEnum[] Lista=getEstados();       
        
        return Lista[i].getNombreEnum();
    }
    
    public Boolean esOperacionAltaEmpresaDIRe(EmpresaDIReDto empresaDIReDto){
    	if (empresaDIReDto!=null && empresaDIReDto.getOperacion()!=null && !empresaDIReDto.getOperacion().isEmpty()){
    		if (TipoOperacionCaycEnum.Alta_EmpresaDIRe.getValorEnum().equals(empresaDIReDto.getOperacion().toString())){
    			return Boolean.TRUE;
    		}    			
    	}    	
    	return Boolean.FALSE;    	
    }

	
 
}
