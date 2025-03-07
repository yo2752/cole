package org.gestoresmadrid.oegam2comun.modelos.utiles;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;

import org.gestoresmadrid.core.model.enumerados.Estado;
import org.gestoresmadrid.core.modelos.model.enumerados.Decision;
import org.gestoresmadrid.core.modelos.model.enumerados.DupleTriple;
import org.gestoresmadrid.core.modelos.model.enumerados.TipoBien;
import org.gestoresmadrid.core.modelos.model.enumerados.TipoBien600;
import org.gestoresmadrid.core.modelos.model.enumerados.TipoUsoRustico;
import org.gestoresmadrid.oegam2comun.bien.model.service.ServicioUnidadMetrica;
import org.gestoresmadrid.oegam2comun.bien.view.bean.BienFilterBean;
import org.gestoresmadrid.oegam2comun.bien.view.dto.BienDto;
import org.gestoresmadrid.oegam2comun.bien.view.dto.UnidadMetricaDto;
import org.gestoresmadrid.oegam2comun.direccion.model.service.ServicioMunicipioCam;
import org.gestoresmadrid.oegam2comun.direccion.model.service.ServicioProvinciaCam;
import org.gestoresmadrid.oegam2comun.direccion.model.service.ServicioTipoViaCam;
import org.gestoresmadrid.oegam2comun.direccion.view.dto.MunicipioCamDto;
import org.gestoresmadrid.oegam2comun.direccion.view.dto.ProvinciaCamDto;
import org.gestoresmadrid.oegam2comun.direccion.view.dto.TipoViaCamDto;
import org.gestoresmadrid.oegam2comun.sistemaExplotacion.model.service.ServicioSistemaExplotacion;
import org.gestoresmadrid.oegam2comun.sistemaExplotacion.view.dto.SistemaExplotacionDto;
import org.gestoresmadrid.oegam2comun.situacion.model.service.ServicioSituacion;
import org.gestoresmadrid.oegam2comun.situacion.view.dto.SituacionDto;
import org.gestoresmadrid.oegam2comun.tipoInmueble.model.service.ServicioTipoInmueble;
import org.gestoresmadrid.oegam2comun.tipoInmueble.view.dto.TipoInmuebleDto;
import org.gestoresmadrid.oegam2comun.usoRustico.model.service.ServicioUsoRustico;
import org.gestoresmadrid.oegam2comun.usoRustico.view.dto.UsoRusticoDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

public class UtilesBienes implements Serializable{

	private static final long serialVersionUID = -2112717903047861422L;

	private static UtilesBienes utilesBienes;
	
	@Autowired
	private ServicioTipoInmueble servicioTipoInmueble;
	
	@Autowired
	private ServicioUsoRustico servicioUsoRustico;
	
	@Autowired
	private ServicioSistemaExplotacion servicioSistemaExplotacion;
	
	@Autowired
	private ServicioUnidadMetrica servicioUnidadMetrica;
	
	@Autowired
	private ServicioSituacion servicioSituacion;
	
	@Autowired
	private ServicioProvinciaCam servicioProvinciaCam;
	
	@Autowired
	private ServicioTipoViaCam servicioTipoViaCam;
	
	@Autowired
	private ServicioMunicipioCam servicioMunicipioCam;
	
	public static UtilesBienes getInstance(){
		if(utilesBienes == null){
			utilesBienes = new UtilesBienes();
			SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(utilesBienes);
		}
		return utilesBienes;
	}
	
	public List<TipoInmuebleDto> getListaTiposInmuebles(TipoBien tipoBien) {
		return servicioTipoInmueble.getListaTiposInmueblesPorTipo(tipoBien);
	}
	
	public List<TipoInmuebleDto> getListaTiposInmueblesConsulta(BienFilterBean bien){
		if(bien.getIdTipoBien()!= null && !bien.getIdTipoBien().isEmpty()){
			return servicioTipoInmueble.getListaTiposInmueblesPorTipo(TipoBien.convertirValor(bien.getIdTipoBien()));
		}
		return Collections.emptyList();
	}
	
	public List<UsoRusticoDto> getListaUsoRusticoPorTipo(TipoUsoRustico tipo){
		return servicioUsoRustico.getListaUsoRusticoPorTipo(tipo);
	}
	
	public TipoBien[] getTipoBien(){
		return TipoBien.values();
	}
	
	public TipoBien600[] getTipoBien600() {
		return TipoBien600.values();
	}
	
	public List<SistemaExplotacionDto> getListaSistemaExplotacion(){
		return servicioSistemaExplotacion.getListaSistemasExplotacion();
	}
	
	public List<UnidadMetricaDto> getListaUnidadesMetricas(){
		return servicioUnidadMetrica.getListaUnidadesMetricas(); 	
	}
	
	public List<SituacionDto> getListaSituacion(){
		return servicioSituacion.getListaSituacion();
	}
	
	public List<ProvinciaCamDto> getListaProvincias(){
		return servicioProvinciaCam.getListaProvinciasCam();
	}
	
	public List<MunicipioCamDto> getListaMunicipiosPorProvincia(BienDto bien){
		if(bien.getDireccion() != null && bien.getDireccion().getIdProvincia() != null && !bien.getDireccion().getIdProvincia().isEmpty()){
			return servicioMunicipioCam.getListaMunicipiosPorProvincia(bien.getDireccion().getIdProvincia());
		}
		return Collections.emptyList();
	}
	
	public List<MunicipioCamDto> getListaMunicipiosPorProvinciaConsulta(String idProvincia){
		if(idProvincia != null && !idProvincia.isEmpty()){
			return servicioMunicipioCam.getListaMunicipiosPorProvincia(idProvincia);
		}
		return Collections.emptyList();
	}
	
	public List<TipoViaCamDto> getListaTiposVias(){
		return servicioTipoViaCam.getListaTipoVias();
	}
	
	
	public DupleTriple[] getDupleTri(){
		return DupleTriple.values();
	}
	
	public Decision[] getDecision() {
		return Decision.values();
	}
	
	public Estado[] getComboEstados() {
		return Estado.values();
	}
}
