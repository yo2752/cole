package org.gestoresmadrid.core.presentacion.jpt.model.beans;

import java.util.ArrayList;
import java.util.List;

import org.gestoresmadrid.utilidades.components.UtilesFecha;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

public class ResumenJPTBean {

	private String idDocumento;
	
	private List<BeanPresentadoJpt> expedientes = new ArrayList<BeanPresentadoJpt>();
		
	private Integer numeroDeExpedientesPresentados;

	private String resultadoPresentacion;
	
	@Autowired
	UtilesFecha utilesFecha;

	public ResumenJPTBean() {
		super();
		SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
	}

	public String getIdDocumento() {
		return idDocumento;
	}

	public void setIdDocumento(String idDocumento) {
		this.idDocumento = idDocumento;
	}

	public List<BeanPresentadoJpt> getExpedientes() {
		return expedientes;
	}

	public void setExpedientes(List<BeanPresentadoJpt> expedientes) {
		this.expedientes = expedientes;
	}

	public String getTipoTramiteTrafico() {
		return expedientes.get(0).getTipoTramiteTrafico().getNombreEnum();
	}


	public String getNumeroColegiado() {
		return expedientes.get(0).getNumColegiado();
	}


	public String getFechaPresentacion() {
		return  utilesFecha.formatoFecha(expedientes.get(0).getFechaPresentacion());
	}


	public Integer getNumeroDeExpedientesTotal() {
		return expedientes.size();
	}


	public Integer getNumeroDeExpedientesPresentados() {
		if (numeroDeExpedientesPresentados == null){
			int contador = 0;
			for(BeanPresentadoJpt aux : expedientes){
				if(aux.getPresentado().getValorEnum() == 1){
					contador++;
				}
			}
			numeroDeExpedientesPresentados = contador;
		}
			
		return numeroDeExpedientesPresentados;
	}


	public String getMotivoNoHayExpedientes(){
		
		if ("".equals(idDocumento) || (idDocumento.length() != 36 && idDocumento.length() != 10)) {
			return "El código introducido no tiene un formato correcto.";
		} else {
			return "Para el código de documento introducido no se han encontrado expedientes relacionados en OEgAM";
		}
		
	}

	public String getResultadoPresentacion() {		
		if ("".equals(resultadoPresentacion) || resultadoPresentacion.contains("0") && expedientes.size() == 0) {
			return getMotivoNoHayExpedientes();
		}else{
			if (numeroDeExpedientesPresentados == expedientes.size()) {
				return resultadoPresentacion + " de "+getNumeroDeExpedientesTotal()+". Todos los tramites estaban previamente presentados.";
			} else {
				return resultadoPresentacion + " de "+getNumeroDeExpedientesTotal()+". Previamente había presentados "+getNumeroDeExpedientesPresentados()+" expedientes.";
			}
			
		}
	}
	
	public String getBalancePresentados(){
		String resultado = "Atención: ";
		if(numeroDeExpedientesPresentados == expedientes.size()){
			return resultado += "todos los expedientes asociados al documento ya estan presentados." ;
		}else if(numeroDeExpedientesPresentados < expedientes.size() && numeroDeExpedientesPresentados != 0){
			return resultado += "algunos de los expedientes asociados al documento ya estan presentados." ;
		}else{
			return "";
		}
	}

	public void setResultadoPresentacion(String resultadoPresentacion) {
		this.resultadoPresentacion = resultadoPresentacion;
	}
	
	
	
	
	
	
}
