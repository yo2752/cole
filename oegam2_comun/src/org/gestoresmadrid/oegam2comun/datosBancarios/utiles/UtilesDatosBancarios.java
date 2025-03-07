package org.gestoresmadrid.oegam2comun.datosBancarios.utiles;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.gestoresmadrid.core.datosBancariosFavoritos.model.enumerados.EstadoDatosBancarios;
import org.gestoresmadrid.core.model.beans.DatoMaestroBean;
import org.gestoresmadrid.core.tasas.model.enumeration.FormaPago;
import org.gestoresmadrid.oegam2comun.contrato.model.service.ServicioContrato;
import org.gestoresmadrid.oegam2comun.datosBancariosFavoritos.view.dto.DatosBancariosFavoritosDto;
import org.gestoresmadrid.oegam2comun.model.service.ServicioPersistenciaEntidadBancaria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

public class UtilesDatosBancarios implements Serializable{

	private static final long serialVersionUID = 4619050401550464005L;
	
	private static UtilesDatosBancarios utilesDatosBancarios;
	
	@Autowired
	private ServicioContrato servicioContrato;
	
	@Autowired
	private ServicioPersistenciaEntidadBancaria servicioPersistenciaEntidadBancaria;
	
	public static UtilesDatosBancarios getInstance(){
		if(utilesDatosBancarios == null){
			utilesDatosBancarios = new UtilesDatosBancarios();
			SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(utilesDatosBancarios);
		}
		return utilesDatosBancarios;
	}
	
	public List<DatoMaestroBean> getListaEntidadesEmisoras(){
		return servicioPersistenciaEntidadBancaria.listAll();
	}
	
	public List<String> listaAnios(){
		List<String> anios = new ArrayList<String>();
		int year = Calendar.getInstance().get(Calendar.YEAR);
		for (int i = 0; i < 21; i++) {
			anios.add(Integer.toString(year + i));
		}
		return anios;
	}
	
	public FormaPago[] getListaFormaPago(){
		return FormaPago.values();
	}
	
	public List<DatoMaestroBean> getComboContratosHabilitados() {
		return servicioContrato.getComboContratosHabilitados();
	}
	
	public List<DatoMaestroBean> getComboContratosHabilitadosColegio(BigDecimal idContrato) {
		return servicioContrato.getComboContratosHabilitadosColegio(idContrato);
	}
	
	public EstadoDatosBancarios[] getComboEstados(){
		return EstadoDatosBancarios.values();
	}
	
	
	public Boolean noEsEstadoHabilitado(DatosBancariosFavoritosDto datosBancarios){
		if(datosBancarios != null && datosBancarios.getEstado() != null && !datosBancarios.getEstado().isEmpty()){
			if(!EstadoDatosBancarios.HABILITADO.getValorEnum().equals(datosBancarios.getEstado())){
				return true;
			}
		}
		return false;
	}
}
