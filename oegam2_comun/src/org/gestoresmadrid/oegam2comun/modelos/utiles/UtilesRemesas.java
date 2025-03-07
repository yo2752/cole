package org.gestoresmadrid.oegam2comun.modelos.utiles;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;

import org.gestoresmadrid.core.model.enumerados.Estado;
import org.gestoresmadrid.core.model.enumerados.TipoInterviniente;
import org.gestoresmadrid.core.modelos.model.enumerados.Decision;
import org.gestoresmadrid.core.modelos.model.enumerados.DerechoReal;
import org.gestoresmadrid.core.modelos.model.enumerados.DupleTriple;
import org.gestoresmadrid.core.modelos.model.enumerados.DuracionDerechoReal;
import org.gestoresmadrid.core.modelos.model.enumerados.DuracionRenta;
import org.gestoresmadrid.core.modelos.model.enumerados.EstadoRemesas;
import org.gestoresmadrid.core.modelos.model.enumerados.Modelo;
import org.gestoresmadrid.core.modelos.model.enumerados.PeriodoRenta;
import org.gestoresmadrid.core.modelos.model.enumerados.TipoBien;
import org.gestoresmadrid.core.personas.model.enumerados.TipoPersona;
import org.gestoresmadrid.oegam2comun.direccion.model.service.ServicioMunicipioCam;
import org.gestoresmadrid.oegam2comun.direccion.model.service.ServicioProvinciaCam;
import org.gestoresmadrid.oegam2comun.direccion.model.service.ServicioPueblo;
import org.gestoresmadrid.oegam2comun.direccion.model.service.ServicioTipoViaCam;
import org.gestoresmadrid.oegam2comun.direccion.view.dto.MunicipioCamDto;
import org.gestoresmadrid.oegam2comun.direccion.view.dto.ProvinciaCamDto;
import org.gestoresmadrid.oegam2comun.direccion.view.dto.PuebloDto;
import org.gestoresmadrid.oegam2comun.direccion.view.dto.TipoViaCamDto;
import org.gestoresmadrid.oegam2comun.modelos.model.service.ServicioConcepto;
import org.gestoresmadrid.oegam2comun.modelos.model.service.ServicioOficinaLiquidadora;
import org.gestoresmadrid.oegam2comun.modelos.model.service.ServicioTipoDocumentoEscr;
import org.gestoresmadrid.oegam2comun.modelos.view.dto.ConceptoDto;
import org.gestoresmadrid.oegam2comun.modelos.view.dto.ModeloDto;
import org.gestoresmadrid.oegam2comun.modelos.view.dto.OficinaLiquidadoraDto;
import org.gestoresmadrid.oegam2comun.modelos.view.dto.TipoDocumentoEscrDto;
import org.gestoresmadrid.oegam2comun.remesa.view.dto.RemesaDto;
import org.gestoresmadrid.oegamComun.direccion.view.dto.DireccionDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import escrituras.utiles.enumerados.SexoPersona;

public class UtilesRemesas implements Serializable{

	private static final long serialVersionUID = -5276547833815152101L;

	private static UtilesRemesas utilesRemesas;
	
	@Autowired
	private ServicioOficinaLiquidadora servicioOficinaLiquidadora;
	
	@Autowired
	private ServicioTipoDocumentoEscr servicioTipoDocumentoEscr;
	
	@Autowired
	private ServicioConcepto servicioConcepto;
	
	@Autowired
	private ServicioProvinciaCam servicioProvinciaCam;
	
	@Autowired
	private ServicioTipoViaCam servicioTipoViaCam;
	
	@Autowired
	private ServicioMunicipioCam servicioMunicipioCam;
	
	@Autowired
	private ServicioPueblo servicioPueblo;
	
	public UtilesRemesas() {
		super();
	}
	
	public static UtilesRemesas getInstance(){
		if(utilesRemesas == null){
			utilesRemesas = new UtilesRemesas();
			SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(utilesRemesas);
		}
		return utilesRemesas;
	}
	
	public List<OficinaLiquidadoraDto> getListaOficinasLiquidadoras(RemesaDto remesa){
		if(remesa == null || remesa.getOficinaLiquidadora() == null || 
				remesa.getOficinaLiquidadora().getIdProvincia() == null || remesa.getOficinaLiquidadora().getIdProvincia().isEmpty()){
			return Collections.emptyList();
		}
		return servicioOficinaLiquidadora.getListaOficinasLiquidadoras(remesa.getOficinaLiquidadora().getIdProvincia());
	}
	
	public List<TipoDocumentoEscrDto> getTiposDocumentos(ModeloDto modelo){
		return servicioTipoDocumentoEscr.getListaDocumentosPorModelo(modelo);
	}
	
	public List<ConceptoDto> getListaConceptosPorModeloDto(ModeloDto modelo){
		return servicioConcepto.getlistaConceptosPorModelo(modelo);
	}
	
	public List<ProvinciaCamDto> getListaProvincias(){
		return servicioProvinciaCam.getListaProvinciasCam();
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
	
	public TipoPersona[] getComboTipoPersonas() {
		return TipoPersona.values();
	}
	
	public SexoPersona[] getComboSexo() {
		return SexoPersona.values();
	}
	
	public EstadoRemesas[] getListaEstadosRemesas(){
		return EstadoRemesas.values();
	}
	
	public Modelo[] getModelos(){
		return Modelo.values();
	}
	
	public List<ConceptoDto> getListaConceptosPorModelo(String modelo, String version){
		return servicioConcepto.getlistaConceptosPorModelo(modelo,version);
	}
	
	public Boolean esConceptoBienes(RemesaDto remesa){
		if(remesa != null && remesa.getConcepto() != null){
			return servicioConcepto.esConceptoBienes(remesa.getConcepto());
		}
		return null;
	}
	
	public Boolean esRemesaGenerarModelos(RemesaDto remesa){
		if(remesa != null && remesa.getEstado() != null){
			if(EstadoRemesas.Validada.getValorEnum().equals(remesa.getEstado())){
				return true;
			}
		}
		return false;
	}
	
	public Boolean esGuardableRemesa(RemesaDto remesa){
		if(remesa != null){
			if(remesa.getEstado() == null){
				return true;
			}else if(EstadoRemesas.Inicial.getValorEnum().equals(remesa.getEstado()) 
					|| EstadoRemesas.Validada.getValorEnum().equals(remesa.getEstado())){
				return true;
			}
		}
		return false;
	}
	
	public Boolean esValidableModelos(RemesaDto remesa){
		if(remesa != null && remesa.getEstado() != null){
			if(EstadoRemesas.Inicial.getValorEnum().equals(remesa.getEstado())){
				return true;
			}
		}
		return false;
	}
	
	public Boolean esGenerable(RemesaDto remesa){
		if(remesa != null && remesa.getEstado() != null){
			if(EstadoRemesas.Validada.getValorEnum().equals(remesa.getEstado())){
				return true;
			}
		}
		return false;
	}
	
	public Boolean esGenerada(RemesaDto remesa){
		if(remesa != null && remesa.getEstado() != null){
			if(EstadoRemesas.Generada.getValorEnum().equals(remesa.getEstado())){
				return true;
			}
		}
		return false;
	}
	
	public List<MunicipioCamDto> getListaMunicipiosPorProvincia(RemesaDto remesa, String tipo){
		DireccionDto direccion = null;
		if(TipoBien.Urbano.getValorEnum().equals(tipo)){
			if(remesa.getBienUrbanoDto() != null){
				direccion = remesa.getBienUrbanoDto().getDireccion();
			}
		}else if(TipoBien.Rustico.getValorEnum().equals(tipo)){
			if(remesa.getBienRusticoDto() != null){
				direccion = remesa.getBienRusticoDto().getDireccion();
			}
		}else if(TipoBien.Otro.getValorEnum().equals(tipo)){
			if(remesa.getOtroBienDto() != null){
				direccion = remesa.getOtroBienDto().getDireccion();
			}
		}else if(TipoInterviniente.SujetoPasivo.getValorEnum().equals(tipo)){
			if(remesa.getSujetoPasivo() != null){
				direccion = remesa.getSujetoPasivo().getDireccion();
			}
		}else if(TipoInterviniente.Transmitente.getValorEnum().equals(tipo)){
			if(remesa.getTransmitente() != null){
				direccion = remesa.getTransmitente().getDireccion();
			}
		}else if(TipoInterviniente.Presentador.getValorEnum().equals(tipo)){
			if(remesa.getPresentador() != null){
				direccion = remesa.getPresentador().getDireccion();
			}
		}
		if(direccion != null && direccion.getIdProvincia() != null && !direccion.getIdProvincia().isEmpty()){
			return servicioMunicipioCam.getListaMunicipiosPorProvincia(direccion.getIdProvincia());
		}
		return Collections.emptyList();
	}
	
	public List<PuebloDto> getListaPueblosPorMunicipioYProvincia(RemesaDto remesa, String tipo){
		DireccionDto direccion = null;
		if(TipoBien.Urbano.getValorEnum().equals(tipo)){
			if(remesa.getBienUrbanoDto() != null){
				direccion = remesa.getBienUrbanoDto().getDireccion();
			}
		}else if(TipoBien.Rustico.getValorEnum().equals(tipo)){
			if(remesa.getBienRusticoDto() != null){
				direccion = remesa.getBienRusticoDto().getDireccion();
			}
		}else if(TipoInterviniente.SujetoPasivo.getValorEnum().equals(tipo)){
			if(remesa.getSujetoPasivo() != null){
				direccion = remesa.getSujetoPasivo().getDireccion();
			}
		}else if(TipoInterviniente.Transmitente.getValorEnum().equals(tipo)){
			if(remesa.getTransmitente() != null){
				direccion = remesa.getTransmitente().getDireccion();
			}
		}else if(TipoInterviniente.Presentador.getValorEnum().equals(tipo)){
			if(remesa.getPresentador() != null){
				direccion = remesa.getPresentador().getDireccion();
			}
		}
		if(direccion != null && direccion.getIdProvincia() != null && !direccion.getIdProvincia().isEmpty()
				&& direccion.getIdMunicipio() != null && !direccion.getIdMunicipio().isEmpty()){
			return servicioPueblo.listaPueblos(direccion.getIdProvincia(), direccion.getIdMunicipio());
		}
		return Collections.emptyList();
	}
	
	public List<TipoViaCamDto> getListaTiposVias(RemesaDto remesa, String tipo){
		DireccionDto direccion = null;
		if(TipoBien.Urbano.getValorEnum().equals(tipo)){
			if(remesa.getBienUrbanoDto() != null){
				direccion = remesa.getBienUrbanoDto().getDireccion();
			}
		}else if(TipoBien.Rustico.getValorEnum().equals(tipo)){
			if(remesa.getBienRusticoDto() != null){
				direccion = remesa.getBienRusticoDto().getDireccion();
			}
		}else if(TipoInterviniente.SujetoPasivo.getValorEnum().equals(tipo)){
			if(remesa.getSujetoPasivo() != null){
				direccion = remesa.getSujetoPasivo().getDireccion();
			}
		}else if(TipoInterviniente.Transmitente.getValorEnum().equals(tipo)){
			if(remesa.getTransmitente() != null){
				direccion = remesa.getTransmitente().getDireccion();
			}
		}else if(TipoInterviniente.Presentador.getValorEnum().equals(tipo)){
			if(remesa.getPresentador() != null){
				direccion = remesa.getPresentador().getDireccion();
			}
		}
		if(direccion != null && direccion.getIdProvincia() != null && !direccion.getIdProvincia().isEmpty()
				&& direccion.getIdMunicipio() != null && !direccion.getIdMunicipio().isEmpty()){
			return servicioTipoViaCam.getListaTipoVias();
		}
		return Collections.emptyList();
	}
	
	public Boolean esTipoImpuestoExento(RemesaDto remesaDto){
		if(remesaDto != null && remesaDto.getTipoImpuesto() != null){
			if(remesaDto.getTipoImpuesto().getSujetoExento() != null && !remesaDto.getTipoImpuesto().getSujetoExento().isEmpty()){
				if("E".equals(remesaDto.getTipoImpuesto().getSujetoExento())){
					return true;
				}
			}
		}
		return false;
	}
	
	public Boolean esTipoImpuestoNoSujeto(RemesaDto remesaDto){
		if(remesaDto != null && remesaDto.getTipoImpuesto() != null){
			if(remesaDto.getTipoImpuesto().getSujetoExento() != null && !remesaDto.getTipoImpuesto().getSujetoExento().isEmpty()){
				if("S".equals(remesaDto.getTipoImpuesto().getSujetoExento())){
					return true;
				}
			}
		}
		return false;
	}
	
	public DerechoReal[] getListaDerechosReales(){
		return DerechoReal.values();
	}
	
	
	public DuracionDerechoReal[] getDuracionDerechoReal(){
		return DuracionDerechoReal.values();
	}
	
	public DuracionRenta[] getDuracionRenta(){
		return DuracionRenta.values();
	}
	
	public PeriodoRenta[] getPeriodosRenta(){
		return PeriodoRenta.values();
	}
	
	public EstadoRemesas[] getComboEstadosRemesas(){
		return EstadoRemesas.values();
	}
}
