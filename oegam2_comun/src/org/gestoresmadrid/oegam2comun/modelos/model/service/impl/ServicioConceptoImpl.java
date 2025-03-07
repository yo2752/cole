package org.gestoresmadrid.oegam2comun.modelos.model.service.impl;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;

import org.gestoresmadrid.core.intervinienteModelos.model.vo.IntervinienteModelosVO;
import org.gestoresmadrid.core.model.enumerados.TipoInterviniente;
import org.gestoresmadrid.core.modelo600_601.model.vo.Modelo600_601VO;
import org.gestoresmadrid.core.modelos.model.dao.ConceptoDao;
import org.gestoresmadrid.core.modelos.model.enumerados.DerechoReal;
import org.gestoresmadrid.core.modelos.model.enumerados.DuracionDerechoReal;
import org.gestoresmadrid.core.modelos.model.enumerados.DuracionRenta;
import org.gestoresmadrid.core.modelos.model.enumerados.GrupoConcepto;
import org.gestoresmadrid.core.modelos.model.enumerados.Modelo;
import org.gestoresmadrid.core.modelos.model.vo.ConceptoVO;
import org.gestoresmadrid.core.modelos.model.vo.ModeloVO;
import org.gestoresmadrid.core.participacion.model.vo.ParticipacionVO;
import org.gestoresmadrid.core.remesa.model.vo.RemesaVO;
import org.gestoresmadrid.oegam2comun.bienRustico.model.service.ServicioBienRustico;
import org.gestoresmadrid.oegam2comun.bienUrbano.model.service.ServicioBienUrbano;
import org.gestoresmadrid.oegam2comun.modelo600_601.model.service.ServicioModelo600_601;
import org.gestoresmadrid.oegam2comun.modelo600_601.view.dto.Modelo600_601Dto;
import org.gestoresmadrid.oegam2comun.modelos.model.service.ServicioConcepto;
import org.gestoresmadrid.oegam2comun.modelos.view.dto.ConceptoDto;
import org.gestoresmadrid.oegam2comun.modelos.view.dto.ModeloDto;
import org.gestoresmadrid.oegam2comun.remesa.model.service.ServicioRemesas;
import org.gestoresmadrid.oegamComun.conversor.Conversor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import escrituras.beans.ResultBean;
import escrituras.utiles.enumerados.DescripcionDerechoReal;
import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;

@Service
public class ServicioConceptoImpl implements ServicioConcepto{

	private static final long serialVersionUID = -1375199152326049004L;
	
	private static final ILoggerOegam log = LoggerOegam.getLogger(ServicioConceptoImpl.class);
	
	@Autowired
	private ConceptoDao conceptoDao;
	
	@Autowired
	private Conversor conversor;
	
	@Autowired
	private ServicioBienUrbano servicioBienUrbano;
	
	@Autowired
	private ServicioBienRustico servicioBienRustico;
	
	@Autowired
	private ServicioModelo600_601 servicioModelo600_601;
	
	@Autowired
	private ServicioRemesas servicioRemesas;
	
	@Override
	@Transactional
	public List<ConceptoDto> getlistaConceptosPorModelo(ModeloDto modelo) {
		try{
			if(modelo != null){
				List<ConceptoVO> lista = conceptoDao.getListaConceptosPorModelo(conversor.transform(modelo, ModeloVO.class));
				if(lista != null && !lista.isEmpty()){
					return conversor.transform(lista, ConceptoDto.class);
				}
			}else{
				log.error("Error al obtener la lista de conceptos por modelo, el modelo es nulo");
			}
		}catch (Exception e) {
			log.error("Error al obtener la lista de conceptos por modelo, error: ",e);
		}
		return null;
	}

	@Override
	@Transactional(readOnly=true)
	public ConceptoDto getConceptoPorAbreviatura(String abreviatura) {
		ConceptoVO conceptoVo = conceptoDao.getConceptoPorAbreviatura(abreviatura);
		if(conceptoVo != null){
			return conversor.transform(conceptoVo, ConceptoDto.class);
		}
		return null;
	}
	
	@Override
	@Transactional(readOnly=true)
	public List<ConceptoDto> getlistaConceptosPorModelo(String modelo,String version) {
		try {
			List<ConceptoVO> listaConcepto = conceptoDao.getListaTodosConceptos(modelo,version);
			if(listaConcepto != null && !listaConcepto.isEmpty()){
				return conversor.transform(listaConcepto, ConceptoDto.class);
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de listar todos los conceptos, error: ",e);
		}
		return Collections.emptyList();
	}
	
	@Override
	public boolean esConceptoBienes(ConceptoDto concepto) {
		String grupoValidacion = getGrupoValidacionConcepto(concepto);
		
		switch (Integer.parseInt(grupoValidacion)) {
		case 1:
			/*La base imponible y el valor declarado deben de estar rellenos con los cual los habilitamos*/
			return false;
		case 2:
			/*Habilitamos la renta*/
			return false;
		case 3:
			/* Debe de haber bienes rústicos. Habilitamos la pestaña de bienes rusticos*/
			return true;
		case 4:
			/* Debe de haber bienes urbanos. Habilitamos la pestaña de bienes urbanos*/
			return true;
		case 5:
			/* Habilitamos los derechos reales*/
			return false;
		case 6:
			/* Debe de haber bienes rústicos y derechos reales*/
			return true;
		case 7:
			/* Debe de haber bienes urbanos y derechos reales*/
			return true;
		case 8:
			/* Debe de haber bienes urbanos y la base imponible debe de estar habilitada por si quieren introducir un valor*/
			return true;
		case 9:
			/* Debe de haber bienes (cualquiera de los dos tipos) y la base imponible y valor declarado debe estar habilitado para introducir un valor*/
			return true;
		case 10:
			/* Debe de haber bienes (cualquiera de los dos tipos)*/
			return true;
		case 11:
			/* Debe de haber bienes (cualquiera de los dos tipos)*/
			return true;
		default:
			log.error("El grupo de validacion del concepto no corresponde con ninguno conocido.");
			break;
		}
		return false;
	}
	
	@Override
	@Transactional(readOnly=true)
	public ResultBean validarConceptoModelo(Modelo600_601VO modeloVO) {
		return new ResultBean(false);
		
	}
	
	
	@Override
	@Transactional(readOnly=true)
	public void convertirBaseImponibleDependiendoConcepto(Modelo600_601VO modeloVO, Modelo600_601Dto modeloDto) {
		String grupoValidacion = getGrupoValidacionConcepto(modeloDto.getConcepto());
		String baseImponible = null;
		switch (Integer.parseInt(grupoValidacion)) {
		case 1:
			/*La base imponible y el valor declarado deben de estar rellenos con los cual los habilitamos*/
			if(modeloDto.getBaseImponible() != null && !modeloDto.getBaseImponible().isEmpty()){
				baseImponible = modeloDto.getBaseImponible().replace(",",".");
				modeloVO.setBaseImponible(new BigDecimal(baseImponible));
			}
			break;
		case 3:
			/* Debe de haber bienes rústicos. Habilitamos la pestaña de bienes rusticos*/
			break;
		case 4:
			/* Debe de haber bienes urbanos. Habilitamos la pestaña de bienes urbanos*/
			break;
		case 5:
			/* Habilitamos los derechos reales*/
			break;
		case 6:
			/* Debe de haber bienes rústicos y derechos reales*/
			break;
		case 7:
			/* Debe de haber bienes urbanos y derechos reales*/
			break;
		case 8:
			/* Debe de haber bienes urbanos y la base imponible debe de estar habilitada por si quieren introducir un valor*/
			break;
		case 9:
			/* Debe de haber bienes (cualquiera de los dos tipos) y la base imponible y valor declarado debe estar habilitado para introducir un valor*/
			if(modeloDto.getBaseImponible() != null && !modeloDto.getBaseImponible().isEmpty()){
				baseImponible = modeloDto.getBaseImponible().replace(",",".");
				modeloVO.setBaseImponible(new BigDecimal(baseImponible));
			}
			break;
		case 10:
			/* Debe de haber bienes (cualquiera de los dos tipos)*/
			break;
		case 11:
			/* Debe de haber bienes (cualquiera de los dos tipos)*/
			break;
		default:
			log.error("El grupo de validacion del concepto no corresponde con ninguno conocido.");
			break;
		}
	}
	
	private String getGrupoValidacionConcepto(ConceptoDto concepto) {
		String grupoValidacion = null;
		if(concepto == null){
			log.error("No tiene grupo de validacion para poder comprobar el concepto");
		}else if(concepto.getGrupoValidacion() != null && !concepto.getGrupoValidacion().isEmpty()){
			grupoValidacion = GrupoConcepto.convertirNombre(concepto.getGrupoValidacion());
		}else{
			ConceptoDto concptDto = getConceptoPorAbreviatura(concepto.getConcepto());
			if(concepto != null){
				grupoValidacion = GrupoConcepto.convertirNombre(concptDto.getGrupoValidacion());
			}
		}
		return grupoValidacion;
	}

	@Override
	@Transactional
	public ResultBean gestionarRemesaPorConcepto(RemesaVO remesaVO) {
		ResultBean resultado = new ResultBean(false);
		try {
			String grupoValidacion = getGrupoValidacionConcepto(conversor.transform(remesaVO.getConcepto(),ConceptoDto.class));
			switch (Integer.parseInt(grupoValidacion)) {
			case 1:
				/*La base imponible y el valor declarado deben de estar rellenos con los cual los habilitamos*/
				resultado = servicioBienUrbano.eliminarBienesUrbanoRemesa(remesaVO.getIdRemesa());
				if(resultado != null && resultado.getError()){
					return resultado;
				}
				resultado = servicioBienRustico.eliminarBienesRusticosRemesa(remesaVO.getIdRemesa());
				if(resultado != null && resultado.getError()){
					return resultado;
				}
				if(Modelo.Modelo600.getValorEnum().equals(remesaVO.getModelo().getId().getModelo())){
					//no lleva renta
					remesaVO.setDuracionRenta(null);
					remesaVO.setTipoPeriodoRenta(null);
					remesaVO.setNumPeriodoRenta(null);
					remesaVO.setImporteRenta(null);
					//no lleva derechos reales
					remesaVO.setDescDerechoReal(null);
					remesaVO.setDuracionDerechoReal(null);
					remesaVO.setNumAnio(null);
					remesaVO.setNacimientoUsuFructuario(null);
					return servicioRemesas.actualizarRemesa(remesaVO);
				}
				return resultado;
			case 2:
				/*Habilitamos la renta, no debe de llevar bienes*/
				resultado = servicioBienUrbano.eliminarBienesUrbanoRemesa(remesaVO.getIdRemesa());
				if(resultado != null && resultado.getError()){
					return resultado;
				}
				resultado = servicioBienRustico.eliminarBienesRusticosRemesa(remesaVO.getIdRemesa());
				if(resultado != null && resultado.getError()){
					return resultado;
				}
				if(Modelo.Modelo600.getValorEnum().equals(remesaVO.getModelo().getId().getModelo())){
					//no lleva derechos reales
					remesaVO.setDescDerechoReal(null);
					remesaVO.setDuracionDerechoReal(null);
					remesaVO.setNumAnio(null);
					remesaVO.setNacimientoUsuFructuario(null);
					return servicioRemesas.actualizarRemesa(remesaVO);
				}
				return resultado;
			case 3:
				/* Debe de haber bienes rústicos. Habilitamos la pestaña de bienes rusticos*/
				resultado = servicioBienUrbano.eliminarBienesUrbanoRemesa(remesaVO.getIdRemesa());
				if(Modelo.Modelo600.getValorEnum().equals(remesaVO.getModelo().getId().getModelo())){
					//no lleva renta
					remesaVO.setDuracionRenta(null);
					remesaVO.setTipoPeriodoRenta(null);
					remesaVO.setNumPeriodoRenta(null);
					remesaVO.setImporteRenta(null);
					//no lleva derechos reales
					remesaVO.setDescDerechoReal(null);
					remesaVO.setDuracionDerechoReal(null);
					remesaVO.setNumAnio(null);
					remesaVO.setNacimientoUsuFructuario(null);
					return servicioRemesas.actualizarRemesa(remesaVO);
				}
				return resultado;
			case 4:
				/* Debe de haber bienes urbanos. Habilitamos la pestaña de bienes urbanos*/
				resultado = servicioBienRustico.eliminarBienesRusticosRemesa(remesaVO.getIdRemesa());
				if(resultado != null && resultado.getError()){
					return resultado;
				}
				if(Modelo.Modelo600.getValorEnum().equals(remesaVO.getModelo().getId().getModelo())){
					//no lleva renta
					remesaVO.setDuracionRenta(null);
					remesaVO.setTipoPeriodoRenta(null);
					remesaVO.setNumPeriodoRenta(null);
					remesaVO.setImporteRenta(null);
					//no lleva derechos reales
					remesaVO.setDescDerechoReal(null);
					remesaVO.setDuracionDerechoReal(null);
					remesaVO.setNumAnio(null);
					remesaVO.setNacimientoUsuFructuario(null);
					return servicioRemesas.actualizarRemesa(remesaVO);
				}
				return resultado;
			case 5:
				/* Habilitamos los derechos reales*/
				resultado = servicioBienUrbano.eliminarBienesUrbanoRemesa(remesaVO.getIdRemesa());
				if(resultado != null && resultado.getError()){
					return resultado;
				}
				resultado = servicioBienRustico.eliminarBienesRusticosRemesa(remesaVO.getIdRemesa());
				if(resultado != null && resultado.getError()){
					return resultado;
				}
				if(Modelo.Modelo600.getValorEnum().equals(remesaVO.getModelo().getId().getModelo())){
					//no lleva renta
					remesaVO.setDuracionRenta(null);
					remesaVO.setTipoPeriodoRenta(null);
					remesaVO.setNumPeriodoRenta(null);
					remesaVO.setImporteRenta(null);
					return servicioRemesas.actualizarRemesa(remesaVO);
				}
				return resultado;
			case 6:
				/* Debe de haber bienes rústicos y derechos reales*/
				resultado = servicioBienUrbano.eliminarBienesUrbanoRemesa(remesaVO.getIdRemesa());
				if(resultado != null && resultado.getError()){
					return resultado;
				}
				if(Modelo.Modelo600.getValorEnum().equals(remesaVO.getModelo().getId().getModelo())){
					//no lleva renta
					remesaVO.setDuracionRenta(null);
					remesaVO.setTipoPeriodoRenta(null);
					remesaVO.setNumPeriodoRenta(null);
					remesaVO.setImporteRenta(null);
					return servicioRemesas.actualizarRemesa(remesaVO);
				}
				return resultado;
			case 7:
				/* Debe de haber bienes urbanos y derechos reales*/
				resultado = servicioBienRustico.eliminarBienesRusticosRemesa(remesaVO.getIdRemesa());
				if(resultado != null && resultado.getError()){
					return resultado;
				}
				if(Modelo.Modelo600.getValorEnum().equals(remesaVO.getModelo().getId().getModelo())){
					//no lleva renta
					remesaVO.setDuracionRenta(null);
					remesaVO.setTipoPeriodoRenta(null);
					remesaVO.setNumPeriodoRenta(null);
					remesaVO.setImporteRenta(null);
					return servicioRemesas.actualizarRemesa(remesaVO);
				}
				return resultado;
			case 8:
				/* Debe de haber bienes urbanos y la base imponible debe de estar habilitada por si quieren introducir un valor*/
				resultado = servicioBienRustico.eliminarBienesRusticosRemesa(remesaVO.getIdRemesa());
				if(resultado != null && resultado.getError()){
					return resultado;
				}
				if(Modelo.Modelo600.getValorEnum().equals(remesaVO.getModelo().getId().getModelo())){
					//no lleva renta
					remesaVO.setDuracionRenta(null);
					remesaVO.setTipoPeriodoRenta(null);
					remesaVO.setNumPeriodoRenta(null);
					remesaVO.setImporteRenta(null);
					//no lleva derechos reales
					remesaVO.setDescDerechoReal(null);
					remesaVO.setDuracionDerechoReal(null);
					remesaVO.setNumAnio(null);
					remesaVO.setNacimientoUsuFructuario(null);
					return servicioRemesas.actualizarRemesa(remesaVO);
				}
				return resultado;
			case 9:
				/* Debe de haber bienes (cualquiera de los dos tipos) y la base imponible y valor declarado debe estar habilitado para introducir un valor*/
				if(Modelo.Modelo600.getValorEnum().equals(remesaVO.getModelo().getId().getModelo())){
					//no lleva renta
					remesaVO.setDuracionRenta(null);
					remesaVO.setTipoPeriodoRenta(null);
					remesaVO.setNumPeriodoRenta(null);
					remesaVO.setImporteRenta(null);
					//no lleva derechos reales
					remesaVO.setDescDerechoReal(null);
					remesaVO.setDuracionDerechoReal(null);
					remesaVO.setNumAnio(null);
					remesaVO.setNacimientoUsuFructuario(null);
					return servicioRemesas.actualizarRemesa(remesaVO);
				}
				return resultado;
			case 10:
				/* Debe de haber bienes (cualquiera de los dos tipos)*/
				if(Modelo.Modelo600.getValorEnum().equals(remesaVO.getModelo().getId().getModelo())){
					//no lleva renta
					remesaVO.setDuracionRenta(null);
					remesaVO.setTipoPeriodoRenta(null);
					remesaVO.setNumPeriodoRenta(null);
					remesaVO.setImporteRenta(null);
					//no lleva derechos reales
					remesaVO.setDescDerechoReal(null);
					remesaVO.setDuracionDerechoReal(null);
					remesaVO.setNumAnio(null);
					remesaVO.setNacimientoUsuFructuario(null);
					return servicioRemesas.actualizarRemesa(remesaVO);
				}
				return resultado;
			default:
				log.error("El grupo de validacion del concepto no corresponde con ninguno conocido.");
				break;
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de gestionar el contenido de la remesa por su concepto, error: ",e);
			resultado.setError(true);
			resultado.addMensajeALista("Ha sucedido un error a la hora de gestionar el contenido de la remesa por su concepto.");
		}
		return resultado;
	}
	
	@Override
	@Transactional
	public ResultBean gestionarModeloPorConcepto(Modelo600_601VO modeloVO) {
		ResultBean resultado = new ResultBean(false);
		try {
			String grupoValidacion = getGrupoValidacionConcepto(conversor.transform(modeloVO.getConcepto(),ConceptoDto.class));
			switch (Integer.parseInt(grupoValidacion)) {
			case 1:
				/*La base imponible y el valor declarado deben de estar rellenos con los cual los habilitamos*/
				resultado = servicioBienUrbano.eliminarBienUrbanoModelo(modeloVO.getIdModelo());
				if(resultado != null && resultado.getError()){
					return resultado;
				}
				resultado = servicioBienRustico.eliminarBienesRusticosModelo(modeloVO.getIdRemesa());
				if(resultado != null && resultado.getError()){
					return resultado;
				}
				if(Modelo.Modelo600.getValorEnum().equals(modeloVO.getModelo().getId().getModelo())){
					//no lleva renta
					modeloVO.setDuracionRenta(null);
					modeloVO.setTipoPeriodoRenta(null);
					modeloVO.setNumPeriodoRenta(null);
					modeloVO.setImporteRenta(null);
					//no lleva derechos reales
					modeloVO.setDescDerechoReal(null);
					modeloVO.setDuracionDerechoReal(null);
					modeloVO.setNumAnio(null);
					modeloVO.setNacimientoUsufructuario(null);
					return servicioModelo600_601.actualizarModelo600601(modeloVO);
				}
				return resultado;
			case 2:
				/*Habilitamos la renta, no debe de llevar bienes*/
				resultado = servicioBienUrbano.eliminarBienUrbanoModelo(modeloVO.getIdModelo());
				if(resultado != null && resultado.getError()){
					return resultado;
				}
				resultado = servicioBienRustico.eliminarBienesRusticosModelo(modeloVO.getIdRemesa());
				if(resultado != null && resultado.getError()){
					return resultado;
				}
				if(Modelo.Modelo600.getValorEnum().equals(modeloVO.getModelo().getId().getModelo())){
					//no lleva derechos reales
					modeloVO.setDescDerechoReal(null);
					modeloVO.setDuracionDerechoReal(null);
					modeloVO.setNumAnio(null);
					modeloVO.setNacimientoUsufructuario(null);
					return servicioModelo600_601.actualizarModelo600601(modeloVO);
				}
				return resultado;
			case 3:
				/* Debe de haber bienes rústicos. Habilitamos la pestaña de bienes rusticos*/
				resultado = servicioBienUrbano.eliminarBienUrbanoModelo(modeloVO.getIdModelo());
				if(resultado != null && resultado.getError()){
					return resultado;
				}
				if(Modelo.Modelo600.getValorEnum().equals(modeloVO.getModelo().getId().getModelo())){
					//no lleva renta
					modeloVO.setDuracionRenta(null);
					modeloVO.setTipoPeriodoRenta(null);
					modeloVO.setNumPeriodoRenta(null);
					modeloVO.setImporteRenta(null);
					//no lleva derechos reales
					modeloVO.setDescDerechoReal(null);
					modeloVO.setDuracionDerechoReal(null);
					modeloVO.setNumAnio(null);
					modeloVO.setNacimientoUsufructuario(null);
					return servicioModelo600_601.actualizarModelo600601(modeloVO);
				}
				return resultado;
			case 4:
				/* Debe de haber bienes urbanos. Habilitamos la pestaña de bienes urbanos*/
				resultado = servicioBienRustico.eliminarBienesRusticosModelo(modeloVO.getIdRemesa());
				if(resultado != null && resultado.getError()){
					return resultado;
				}
				if(Modelo.Modelo600.getValorEnum().equals(modeloVO.getModelo().getId().getModelo())){
					//no lleva renta
					modeloVO.setDuracionRenta(null);
					modeloVO.setTipoPeriodoRenta(null);
					modeloVO.setNumPeriodoRenta(null);
					modeloVO.setImporteRenta(null);
					//no lleva derechos reales
					modeloVO.setDescDerechoReal(null);
					modeloVO.setDuracionDerechoReal(null);
					modeloVO.setNumAnio(null);
					modeloVO.setNacimientoUsufructuario(null);
					return servicioModelo600_601.actualizarModelo600601(modeloVO);
				}
				return resultado;
			case 5:
				/* Habilitamos los derechos reales*/
				resultado = servicioBienUrbano.eliminarBienUrbanoModelo(modeloVO.getIdModelo());
				if(resultado != null && resultado.getError()){
					return resultado;
				}
				resultado = servicioBienRustico.eliminarBienesRusticosModelo(modeloVO.getIdRemesa());
				if(resultado != null && resultado.getError()){
					return resultado;
				}
				if(Modelo.Modelo600.getValorEnum().equals(modeloVO.getModelo().getId().getModelo())){
					//no lleva renta
					modeloVO.setDuracionRenta(null);
					modeloVO.setTipoPeriodoRenta(null);
					modeloVO.setNumPeriodoRenta(null);
					modeloVO.setImporteRenta(null);
					return servicioModelo600_601.actualizarModelo600601(modeloVO);
				}
				return resultado;
			case 6:
				/* Debe de haber bienes rústicos y derechos reales*/
				resultado = servicioBienUrbano.eliminarBienUrbanoModelo(modeloVO.getIdModelo());				
				if(resultado != null && resultado.getError()){
					return resultado;
				}
				if(Modelo.Modelo600.getValorEnum().equals(modeloVO.getModelo().getId().getModelo())){
					//no lleva renta
					modeloVO.setDuracionRenta(null);
					modeloVO.setTipoPeriodoRenta(null);
					modeloVO.setNumPeriodoRenta(null);
					modeloVO.setImporteRenta(null);
					return servicioModelo600_601.actualizarModelo600601(modeloVO);
				}
				return resultado;
			case 7:
				/* Debe de haber bienes urbanos y derechos reales*/
				resultado = servicioBienRustico.eliminarBienesRusticosModelo(modeloVO.getIdRemesa());
				if(resultado != null && resultado.getError()){
					return resultado;
				}
				if(Modelo.Modelo600.getValorEnum().equals(modeloVO.getModelo().getId().getModelo())){
					//no lleva renta
					modeloVO.setDuracionRenta(null);
					modeloVO.setTipoPeriodoRenta(null);
					modeloVO.setNumPeriodoRenta(null);
					modeloVO.setImporteRenta(null);
					return servicioModelo600_601.actualizarModelo600601(modeloVO);
				}
				return resultado;
			case 8:
				/* Debe de haber bienes urbanos y la base imponible debe de estar habilitada por si quieren introducir un valor*/
				resultado = servicioBienRustico.eliminarBienesRusticosModelo(modeloVO.getIdRemesa());
				if(resultado != null && resultado.getError()){
					return resultado;
				}
				if(Modelo.Modelo600.getValorEnum().equals(modeloVO.getModelo().getId().getModelo())){
					//no lleva renta
					modeloVO.setDuracionRenta(null);
					modeloVO.setTipoPeriodoRenta(null);
					modeloVO.setNumPeriodoRenta(null);
					modeloVO.setImporteRenta(null);
					//no lleva derechos reales
					modeloVO.setDescDerechoReal(null);
					modeloVO.setDuracionDerechoReal(null);
					modeloVO.setNumAnio(null);
					modeloVO.setNacimientoUsufructuario(null);
					return servicioModelo600_601.actualizarModelo600601(modeloVO);
				}
				return resultado;
			case 9:
				/* Debe de haber bienes (cualquiera de los dos tipos) y la base imponible y valor declarado debe estar habilitado para introducir un valor*/
				if(Modelo.Modelo600.getValorEnum().equals(modeloVO.getModelo().getId().getModelo())){
					//no lleva renta
					modeloVO.setDuracionRenta(null);
					modeloVO.setTipoPeriodoRenta(null);
					modeloVO.setNumPeriodoRenta(null);
					modeloVO.setImporteRenta(null);
					//no lleva derechos reales
					modeloVO.setDescDerechoReal(null);
					modeloVO.setDuracionDerechoReal(null);
					modeloVO.setNumAnio(null);
					modeloVO.setNacimientoUsufructuario(null);
					return servicioModelo600_601.actualizarModelo600601(modeloVO);
				}
				return resultado;
			case 10:
				/* Debe de haber bienes (cualquiera de los dos tipos)*/
				if(Modelo.Modelo600.getValorEnum().equals(modeloVO.getModelo().getId().getModelo())){
					//no lleva renta
					modeloVO.setDuracionRenta(null);
					modeloVO.setTipoPeriodoRenta(null);
					modeloVO.setNumPeriodoRenta(null);
					modeloVO.setImporteRenta(null);
					//no lleva derechos reales
					modeloVO.setDescDerechoReal(null);
					modeloVO.setDuracionDerechoReal(null);
					modeloVO.setNumAnio(null);
					modeloVO.setNacimientoUsufructuario(null);
					return servicioModelo600_601.actualizarModelo600601(modeloVO);
				}
				return resultado;
			default:
				log.error("El grupo de validacion del concepto no corresponde con ninguno conocido.");
				break;
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de gestionar el contenido del modelo por su concepto, error: ",e);
			resultado.setError(true);
			resultado.addMensajeALista("Ha sucedido un error a la hora de gestionar el contenido del modelo por su concepto.");
		}
		return resultado;
	}
	
	@Override
	@Transactional
	public ResultBean validarModelo600PorConcepto(Modelo600_601VO modeloVO) {
		ResultBean resultado = new ResultBean(false);
		try {
			String grupoValidacion = getGrupoValidacionConcepto(conversor.transform(modeloVO.getConcepto(),ConceptoDto.class));
			switch (Integer.parseInt(grupoValidacion)) {
			case 1:
				/*La base imponible y el valor declarado deben de estar rellenos con los cual los habilitamos*/
				return resultado;
			case 2:
				/*Habilitamos la renta, no debe de llevar bienes*/
				if(modeloVO.getImporteRenta() == null){
					resultado.setError(true);
					resultado.addMensajeALista("El importe de la renta debe de estar relleno.");
					return resultado;
				}
				if(modeloVO.getValorDeclarado() == null){
					resultado.setError(true);
					resultado.addMensajeALista("El valor declarado debe de estar relleno.");
					return resultado;
				}
				if(modeloVO.getDuracionRenta() == null || modeloVO.getDuracionRenta().isEmpty() || modeloVO.getDuracionRenta().equals("-1")){
					resultado.setError(true);
					resultado.addMensajeALista("El modelo 600 para el concepto seleccionado la duración de la renta es obligatoria.");
					return resultado;
				}
				if(DuracionRenta.Global.getValorEnum().equals(modeloVO.getDuracionRenta())){
					if(modeloVO.getTipoPeriodoRenta() != null || !modeloVO.getTipoPeriodoRenta().isEmpty()){
						resultado.setError(true);
						resultado.addMensajeALista("Para la duración de renta global no hace falta seleccionar el tipo de periodo de renta.");
						return resultado;
					}else if(modeloVO.getNumPeriodoRenta() != null){
						resultado.setError(true);
						resultado.addMensajeALista("Para la duración de renta global no hace falta rellenar el numero de periodo de renta.");
						return resultado;
					}
				}else if(DuracionRenta.Periodica.getValorEnum().equals(modeloVO.getDuracionRenta())){
					if(modeloVO.getTipoPeriodoRenta() == null || modeloVO.getTipoPeriodoRenta().isEmpty()){
						resultado.setError(true);
						resultado.addMensajeALista("Para la duración de renta periodica debe seleccionar el tipo de periodo de renta.");
						return resultado;
					}else{
						if(modeloVO.getNumPeriodoRenta() == null){
							resultado.setError(true);
							resultado.addMensajeALista("Para la duración de renta periodica hace falta rellenar el numero de periodo de renta.");
							return resultado;
						}
					}
				}
				return resultado;
			case 3:
				/* Debe de haber bienes rústicos. Habilitamos la pestaña de bienes rusticos*/
				return resultado;
			case 4:
				/* Debe de haber bienes urbanos. Habilitamos la pestaña de bienes urbanos*/
				return resultado;
			case 5:
				/* Habilitamos los derechos reales*/
				if(modeloVO.getDescDerechoReal() == null || modeloVO.getDescDerechoReal().isEmpty() || modeloVO.getDescDerechoReal().equals("-1")){
					resultado.setError(true);
					resultado.addMensajeALista("El modelo 600 para el concepto seleccionado el derecho real es obligatorio.");
					return resultado;
				}
				if(modeloVO.getDuracionDerechoReal() == null || modeloVO.getDuracionDerechoReal().isEmpty()){
					resultado.setError(true);
					resultado.addMensajeALista("El modelo 600 para el concepto seleccionado el la duración del derecho real es obligatorio.");
					return resultado;
				}
				if(DescripcionDerechoReal.Otros.getValorEnum().equals(modeloVO.getDescDerechoReal())){
					if(modeloVO.getBaseImponible() == null){
						resultado.setError(true);
						resultado.addMensajeALista("La base imponible debe ir rellena.");
						return resultado;
					}else if(modeloVO.getValorDeclarado() == null){
						resultado.setError(true);
						resultado.addMensajeALista("El valor declarado debe ir relleno.");
						return resultado;
					}else{
						if(modeloVO.getBaseImponible().compareTo(modeloVO.getValorDeclarado()) == -1){
							resultado.setError(true);
							resultado.addMensajeALista("La base Imponible debe ser mayor o igual al valor declarado.");
							return resultado;
						}
					}
				}else {
					if(DuracionDerechoReal.Temporal.getValorEnum().equals(modeloVO.getDuracionDerechoReal())){
						if(modeloVO.getNumAnio() == null){
							resultado.setError(true);
							resultado.addMensajeALista("El modelo 600 para el concepto seleccionado y para la duracion del derecho real seleccionada los numeros de años son obligatorios.");
							return resultado;
						}
					}else if(DuracionDerechoReal.Vitalicio.getValorEnum().equals(modeloVO.getDuracionDerechoReal())){
						if(modeloVO.getNacimientoUsufructuario() == null){
							resultado.setError(true);
							resultado.addMensajeALista("El modelo 600 para el concepto seleccionado y para la duracion del derecho real seleccionada la fecha de nacimiento del usufructuario es obligatoria.");
							return resultado;
						}
					}
				}
				return resultado;
			case 6:
				/* Debe de haber bienes rústicos y derechos reales*/
				if(modeloVO.getDescDerechoReal() == null || modeloVO.getDescDerechoReal().isEmpty() || modeloVO.getDescDerechoReal().equals("-1")){
					resultado.setError(true);
					resultado.addMensajeALista("El modelo 600 para el concepto seleccionado el derecho real es obligatorio.");
					return resultado;
				}
				if(modeloVO.getDuracionDerechoReal() == null || modeloVO.getDuracionDerechoReal().isEmpty()){
					resultado.setError(true);
					resultado.addMensajeALista("El modelo 600 para el concepto seleccionado el la duración del derecho real es obligatorio.");
					return resultado;
				}
				if(DescripcionDerechoReal.Otros.getValorEnum().equals(modeloVO.getDescDerechoReal())){
					if(modeloVO.getBaseImponible() == null){
						resultado.setError(true);
						resultado.addMensajeALista("La base imponible debe ir rellena.");
						return resultado;
					}else if(modeloVO.getValorDeclarado() == null){
						resultado.setError(true);
						resultado.addMensajeALista("El valor declarado debe ir relleno.");
						return resultado;
					}else{
						if(modeloVO.getBaseImponible().compareTo(modeloVO.getValorDeclarado()) == -1){
							resultado.setError(true);
							resultado.addMensajeALista("La base Imponible debe ser mayor o igual al valor declarado.");
							return resultado;
						}
					}
				}else {
					if(DuracionDerechoReal.Temporal.getValorEnum().equals(modeloVO.getDuracionDerechoReal())){
						if(modeloVO.getNumAnio() == null){
							resultado.setError(true);
							resultado.addMensajeALista("El modelo 600 para el concepto seleccionado y para la duracion del derecho real seleccionada los numeros de años son obligatorios.");
							return resultado;
						}
					}else if(DuracionDerechoReal.Vitalicio.getValorEnum().equals(modeloVO.getDuracionDerechoReal())){
						if(modeloVO.getNacimientoUsufructuario() == null){
							resultado.setError(true);
							resultado.addMensajeALista("El modelo 600 para el concepto seleccionado y para la duracion del derecho real seleccionada la fecha de nacimiento del usufructuario es obligatoria.");
							return resultado;
						}
					}
				}
				return resultado;
			case 7:
				/* Debe de haber bienes urbanos y derechos reales*/
				if(modeloVO.getDescDerechoReal() == null || modeloVO.getDescDerechoReal().isEmpty() || modeloVO.getDescDerechoReal().equals("-1")){
					resultado.setError(true);
					resultado.addMensajeALista("El modelo 600 para el concepto seleccionado el derecho real es obligatorio.");
					return resultado;
				}
				if(modeloVO.getDuracionDerechoReal() == null || modeloVO.getDuracionDerechoReal().isEmpty()){
					resultado.setError(true);
					resultado.addMensajeALista("El modelo 600 para el concepto seleccionado el la duración del derecho real es obligatorio.");
					return resultado;
				}
				if(DescripcionDerechoReal.Otros.getValorEnum().equals(modeloVO.getDescDerechoReal())){
					if(modeloVO.getBaseImponible() == null){
						resultado.setError(true);
						resultado.addMensajeALista("La base imponible debe ir rellena.");
						return resultado;
					}else if(modeloVO.getValorDeclarado() == null){
						resultado.setError(true);
						resultado.addMensajeALista("El valor declarado debe ir relleno.");
						return resultado;
					}else{
						if(modeloVO.getBaseImponible().compareTo(modeloVO.getValorDeclarado()) == -1){
							resultado.setError(true);
							resultado.addMensajeALista("La base Imponible debe ser mayor o igual al valor declarado.");
							return resultado;
						}
					}
				}else {
					if(DuracionDerechoReal.Temporal.getValorEnum().equals(modeloVO.getDuracionDerechoReal())){
						if(modeloVO.getNumAnio() == null){
							resultado.setError(true);
							resultado.addMensajeALista("El modelo 600 para el concepto seleccionado y para la duracion del derecho real seleccionada los numeros de años son obligatorios.");
							return resultado;
						}
					}else if(DuracionDerechoReal.Vitalicio.getValorEnum().equals(modeloVO.getDuracionDerechoReal())){
						if(modeloVO.getNacimientoUsufructuario() == null){
							resultado.setError(true);
							resultado.addMensajeALista("El modelo 600 para el concepto seleccionado y para la duracion del derecho real seleccionada la fecha de nacimiento del usufructuario es obligatoria.");
							return resultado;
						}
					}
				}
				return resultado;
			case 8:
				/* Debe de haber bienes urbanos y la base imponible debe de estar habilitada por si quieren introducir un valor*/
				return resultado;
			case 9:
				/* Debe de haber bienes (cualquiera de los dos tipos) y la base imponible y valor declarado debe estar habilitado para introducir un valor*/
				return resultado;
			case 10:
				/* Debe de haber bienes (cualquiera de los dos tipos)*/
				return resultado;
			default:
				log.error("El grupo de validacion del concepto no corresponde con ninguno conocido.");
				break;
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de validar el contenido del modelo por su concepto, error: ",e);
			resultado.setError(true);
			resultado.addMensajeALista("Ha sucedido un error a la hora de validar el contenido del modelo por su concepto.");
		}
		return resultado;
	}
	
	@Override
	@Transactional
	public ResultBean validarRemesa600PorConcepto(RemesaVO remesaVO) {
		ResultBean resultado = new ResultBean(false);
		try {
			String grupoValidacion = getGrupoValidacionConcepto(conversor.transform(remesaVO.getConcepto(),ConceptoDto.class));
			switch (Integer.parseInt(grupoValidacion)) {
			case 1:
				/*La base imponible y el valor declarado deben de estar rellenos con los cual los habilitamos*/
				return resultado;
			case 2:
				/*Habilitamos la renta, no debe de llevar bienes*/
				if(remesaVO.getDuracionRenta() == null || remesaVO.getDuracionRenta().isEmpty()){
					resultado.setError(true);
					resultado.addMensajeALista("Para la remesa 600 y el concepto seleccionado la duracion de la renta es obligatoria.");
					return resultado;
				}
				if(DuracionRenta.Periodica.getValorEnum().equals(remesaVO.getDuracionRenta())){
					if(remesaVO.getTipoPeriodoRenta() == null || remesaVO.getTipoPeriodoRenta().isEmpty()){
						resultado.setError(true);
						resultado.addMensajeALista("Para la remesa 600 y el concepto seleccionado el tipo de periodo de la renta es obligatoria.");
						return resultado;
					}
					if(remesaVO.getNumPeriodoRenta() == null){
						resultado.setError(true);
						resultado.addMensajeALista("Para la remesa 600 y el concepto seleccionado el numero de periodo de la renta es obligatoria.");
						return resultado;
					}
				}
				if(remesaVO.getImporteRenta() == null){
					resultado.setError(true);
					resultado.addMensajeALista("Para la remesa 600 y el concepto seleccionado el importe de la renta es obligatoria.");
					return resultado;
				}
				return resultado;
			case 3:
				/* Debe de haber bienes rústicos. Habilitamos la pestaña de bienes rusticos*/
				return resultado;
			case 4:
				/* Debe de haber bienes urbanos. Habilitamos la pestaña de bienes urbanos*/
				return resultado;
			case 5:
				/* Habilitamos los derechos reales*/
				if(remesaVO.getDescDerechoReal() == null || remesaVO.getDescDerechoReal().isEmpty() || remesaVO.getDescDerechoReal().equals("-1")){
					resultado.setError(true);
					resultado.addMensajeALista("Para la remesa 600 y el concepto seleccionado el derecho real es obligatorio.");
					return resultado;
				}
				if(remesaVO.getDuracionDerechoReal() == null || remesaVO.getDuracionDerechoReal().isEmpty()){
					resultado.setError(true);
					resultado.addMensajeALista("Para la remesa 600 y el concepto seleccionado el la duración del derecho real es obligatorio.");
					return resultado;
				}
				if(DuracionDerechoReal.Temporal.getValorEnum().equals(remesaVO.getDuracionDerechoReal())){
					if(remesaVO.getNumAnio() == null){
						resultado.setError(true);
						resultado.addMensajeALista("Para la remesa 600 y el concepto seleccionado y para la duracion del derecho real seleccionada los numeros de años son obligatorios.");
						return resultado;
					}
				}else if(DuracionDerechoReal.Vitalicio.getValorEnum().equals(remesaVO.getDuracionDerechoReal())){
					if(remesaVO.getNacimientoUsuFructuario() == null){
						resultado.setError(true);
						resultado.addMensajeALista("Para la remesa 600 y el concepto seleccionado y para la duracion del derecho real seleccionada la fecha de nacimiento del usufructuario es obligatoria.");
						return resultado;
					}
				}
				return resultado;
			case 6:
				/* Debe de haber bienes rústicos y derechos reales*/
				if(remesaVO.getDescDerechoReal() == null || remesaVO.getDescDerechoReal().isEmpty() || remesaVO.getDescDerechoReal().equals("-1")){
					resultado.setError(true);
					resultado.addMensajeALista("Para la remesa 600 y el concepto seleccionado el derecho real es obligatorio.");
					return resultado;
				}
				if(remesaVO.getDuracionDerechoReal() == null || remesaVO.getDuracionDerechoReal().isEmpty()){
					resultado.setError(true);
					resultado.addMensajeALista("Para la remesa 600 y el concepto seleccionado el la duración del derecho real es obligatorio.");
					return resultado;
				}
				if(DuracionDerechoReal.Temporal.getValorEnum().equals(remesaVO.getDuracionDerechoReal())){
					if(remesaVO.getNumAnio() == null){
						resultado.setError(true);
						resultado.addMensajeALista("Para la remesa 600 y el concepto seleccionado y para la duracion del derecho real seleccionada los numeros de años son obligatorios.");
						return resultado;
					}
				}else if(DuracionDerechoReal.Vitalicio.getValorEnum().equals(remesaVO.getDuracionDerechoReal())){
					if(remesaVO.getNacimientoUsuFructuario() == null){
						resultado.setError(true);
						resultado.addMensajeALista("Para la remesa 600 y el concepto seleccionado y para la duracion del derecho real seleccionada la fecha de nacimiento del usufructuario es obligatoria.");
						return resultado;
					}
				}
				return resultado;
			case 7:
				/* Debe de haber bienes urbanos y derechos reales*/
				if(remesaVO.getDescDerechoReal() == null || remesaVO.getDescDerechoReal().isEmpty() || remesaVO.getDescDerechoReal().equals("-1")){
					resultado.setError(true);
					resultado.addMensajeALista("Para la remesa 600 y el concepto seleccionado el derecho real es obligatorio.");
					return resultado;
				}
				if(remesaVO.getDuracionDerechoReal() == null || remesaVO.getDuracionDerechoReal().isEmpty()){
					resultado.setError(true);
					resultado.addMensajeALista("Para la remesa 600 y el concepto seleccionado el la duración del derecho real es obligatorio.");
					return resultado;
				}
				if(DuracionDerechoReal.Temporal.getValorEnum().equals(remesaVO.getDuracionDerechoReal())){
					if(remesaVO.getNumAnio() == null){
						resultado.setError(true);
						resultado.addMensajeALista("Para la remesa 600 y el concepto seleccionado y para la duracion del derecho real seleccionada los numeros de años son obligatorios.");
						return resultado;
					}
				}else if(DuracionDerechoReal.Vitalicio.getValorEnum().equals(remesaVO.getDuracionDerechoReal())){
					if(remesaVO.getNacimientoUsuFructuario() == null){
						resultado.setError(true);
						resultado.addMensajeALista("Para la remesa 600 y el concepto seleccionado y para la duracion del derecho real seleccionada la fecha de nacimiento del usufructuario es obligatoria.");
						return resultado;
					}
				}
				return resultado;
			case 8:
				/* Debe de haber bienes urbanos y la base imponible debe de estar habilitada por si quieren introducir un valor*/
				return resultado;
			case 9:
				/* Debe de haber bienes (cualquiera de los dos tipos) y la base imponible y valor declarado debe estar habilitado para introducir un valor*/
				return resultado;
			case 10:
				/* Debe de haber bienes (cualquiera de los dos tipos)*/
				return resultado;
			default:
				log.error("El grupo de validacion del concepto no corresponde con ninguno conocido.");
				break;
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de validar el contenido del modelo por su concepto, error: ",e);
			resultado.setError(true);
			resultado.addMensajeALista("Ha sucedido un error a la hora de validar el contenido del modelo por su concepto.");
		}
		return resultado;
	}
	
	@Override
	@Transactional(readOnly=true)
	public void obtenerBaseImponibleRemesa600(RemesaVO remesaVO, IntervinienteModelosVO sujetoPasivoVO,IntervinienteModelosVO transmitenteVO, Modelo600_601VO modelo600_601VO) {
		try {
			String grupoValidacion = getGrupoValidacionConcepto(conversor.transform(remesaVO.getConcepto(),ConceptoDto.class));
			switch (Integer.parseInt(grupoValidacion)) {
			case 1:
				/*La base imponible y el valor declarado deben de estar rellenos con los cual los habilitamos*/
				calcularValorDeclarado(remesaVO, modelo600_601VO, sujetoPasivoVO,transmitenteVO);
				modelo600_601VO.setBaseImponible(modelo600_601VO.getValorDeclarado());
				break;
			case 2:
				/*Habilitamos la renta, no debe de llevar bienes*/
				if(Modelo.Modelo600.getValorEnum().equals(remesaVO.getModelo().getId().getModelo())){
					calcularBaseImponibleRenta(remesaVO,modelo600_601VO);
					calcularValorDeclarado(remesaVO, modelo600_601VO, sujetoPasivoVO,transmitenteVO);
				}else{
					calcularValorDeclarado(remesaVO, modelo600_601VO, sujetoPasivoVO,transmitenteVO);
					modelo600_601VO.setBaseImponible(modelo600_601VO.getValorDeclarado());
				}
				break;
			case 3:
				/* Debe de haber bienes rústicos. Habilitamos la pestaña de bienes rusticos*/
				calcularValorDeclarado(remesaVO, modelo600_601VO, sujetoPasivoVO,transmitenteVO);
				modelo600_601VO.setBaseImponible(modelo600_601VO.getValorDeclarado());
				break;
			case 4:
				/* Debe de haber bienes urbanos. Habilitamos la pestaña de bienes urbanos*/
				calcularValorDeclarado(remesaVO, modelo600_601VO, sujetoPasivoVO,transmitenteVO);
				modelo600_601VO.setBaseImponible(modelo600_601VO.getValorDeclarado());
				break;
			case 5:
				/* Habilitamos los derechos reales*/
				if(Modelo.Modelo600.getValorEnum().equals(remesaVO.getModelo().getId().getModelo())){
					if (!DerechoReal.Otros.getValorEnum().equals(remesaVO.getDescDerechoReal())){
						calcularBaseImponibleDerechosReales(remesaVO,modelo600_601VO);
						calcularValorDeclarado(remesaVO, modelo600_601VO, sujetoPasivoVO,transmitenteVO);
					}else{
						calcularValorDeclarado(remesaVO, modelo600_601VO, sujetoPasivoVO,transmitenteVO);
						modelo600_601VO.setBaseImponible(modelo600_601VO.getValorDeclarado());
					}
				}else{
					calcularValorDeclarado(remesaVO, modelo600_601VO, sujetoPasivoVO,transmitenteVO);
					modelo600_601VO.setBaseImponible(modelo600_601VO.getValorDeclarado());
				}
				break;
			case 6:
				/* Debe de haber bienes rústicos y derechos reales*/
				if(Modelo.Modelo600.getValorEnum().equals(remesaVO.getModelo().getId().getModelo())){
					if (!DerechoReal.Otros.getValorEnum().equals(remesaVO.getDescDerechoReal())){
						calcularBaseImponibleDerechosReales(remesaVO,modelo600_601VO);
						calcularValorDeclarado(remesaVO, modelo600_601VO, sujetoPasivoVO,transmitenteVO);
					}else{
						calcularValorDeclarado(remesaVO, modelo600_601VO, sujetoPasivoVO,transmitenteVO);
						modelo600_601VO.setBaseImponible(modelo600_601VO.getValorDeclarado());
					}
				}else{
					calcularValorDeclarado(remesaVO, modelo600_601VO, sujetoPasivoVO,transmitenteVO);
					modelo600_601VO.setBaseImponible(modelo600_601VO.getValorDeclarado());
				}
				break;
			case 7:
				/* Debe de haber bienes urbanos y derechos reales*/
				if(Modelo.Modelo600.getValorEnum().equals(remesaVO.getModelo().getId().getModelo())){
					if (!DerechoReal.Otros.getValorEnum().equals(remesaVO.getDescDerechoReal())){
						calcularBaseImponibleDerechosReales(remesaVO,modelo600_601VO);
						calcularValorDeclarado(remesaVO, modelo600_601VO, sujetoPasivoVO,transmitenteVO);
					}else{
						calcularValorDeclarado(remesaVO, modelo600_601VO, sujetoPasivoVO,transmitenteVO);
						modelo600_601VO.setBaseImponible(modelo600_601VO.getValorDeclarado());
					}
				}else{
					calcularValorDeclarado(remesaVO, modelo600_601VO, sujetoPasivoVO,transmitenteVO);
					modelo600_601VO.setBaseImponible(modelo600_601VO.getValorDeclarado());
				}
				break;
			case 8:
				/* Debe de haber bienes urbanos y la base imponible debe de estar habilitada por si quieren introducir un valor*/
				calcularValorDeclarado(remesaVO, modelo600_601VO, sujetoPasivoVO,transmitenteVO);
				modelo600_601VO.setBaseImponible(modelo600_601VO.getValorDeclarado());
				break;
			case 9:
				/* Debe de haber bienes (cualquiera de los dos tipos) y la base imponible y valor declarado debe estar habilitado para introducir un valor*/
				calcularValorDeclarado(remesaVO, modelo600_601VO, sujetoPasivoVO,transmitenteVO);
				modelo600_601VO.setBaseImponible(modelo600_601VO.getValorDeclarado());
				break;
			case 10:
				/* Debe de haber bienes (cualquiera de los dos tipos)*/
				calcularValorDeclarado(remesaVO, modelo600_601VO, sujetoPasivoVO,transmitenteVO);
				modelo600_601VO.setBaseImponible(modelo600_601VO.getValorDeclarado());
				break;
			default:
				log.error("El grupo de validacion del concepto no corresponde con ninguno conocido.");
				break;
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de validar el contenido del modelo por su concepto, error: ",e);
		}
	}
	
	private void calcularBaseImponibleRenta(RemesaVO remesaVO,Modelo600_601VO modelo600_601VO) {
		BigDecimal importe = BigDecimal.ZERO;
		if(DuracionRenta.Periodica.getValorEnum().equals(remesaVO.getDuracionRenta())){
			importe = remesaVO.getImporteRenta().multiply(remesaVO.getNumPeriodoRenta());
		}else{
			importe = remesaVO.getImporteRenta();
		}
		modelo600_601VO.setBaseImponible(importe);
	}
	
	private void calcularValorDeclarado(RemesaVO remesaVO,Modelo600_601VO modelo600_601VO, IntervinienteModelosVO sujetoPasivoVO, IntervinienteModelosVO transmitenteVO){
		BigDecimal porcentajeSujPasivo = null;
		BigDecimal porcentajeTransmitente = null;
		for(ParticipacionVO participacionVO : remesaVO.getListaCoefParticipacion()){
			if(participacionVO.getIdInterviniente().equals(sujetoPasivoVO.getIdInterviniente()) && TipoInterviniente.SujetoPasivo.getValorEnum().equals(sujetoPasivoVO.getTipoInterviniente())){
				porcentajeSujPasivo = participacionVO.getPorcentaje();
			}else if(participacionVO.getIdInterviniente().equals(transmitenteVO.getIdInterviniente()) && TipoInterviniente.Transmitente.getValorEnum().equals(transmitenteVO.getTipoInterviniente())){
				porcentajeTransmitente = participacionVO.getPorcentaje();
			}
		}
		if(porcentajeSujPasivo != null && porcentajeTransmitente != null){
			BigDecimal importeTransmitir = calcularImportePorcentajes(remesaVO.getImporteTotal(), porcentajeSujPasivo);
			modelo600_601VO.setValorDeclarado(calcularImportePorcentajes(importeTransmitir, porcentajeTransmitente));
		}else{
			modelo600_601VO.setValorDeclarado(BigDecimal.ZERO);
		}
	}

	private void calcularBaseImponibleDerechosReales(RemesaVO remesaVO,	Modelo600_601VO modelo600_601VO) {
		BigDecimal baseImponible = BigDecimal.ZERO;
		BigDecimal usufructo = calcularUsufructo(remesaVO);
		if (DescripcionDerechoReal.Usufructo.getValorEnum().equals(remesaVO.getDescDerechoReal())){
			baseImponible = remesaVO.getImporteTotal().multiply(usufructo.divide(new BigDecimal(100)));
		}else if (DescripcionDerechoReal.Nuda_Propiedad.getValorEnum().equals(remesaVO.getDescDerechoReal())){
			baseImponible = remesaVO.getImporteTotal().multiply(BigDecimal.ONE.subtract(usufructo.divide(new BigDecimal(100))));
		}else if (DescripcionDerechoReal.Uso_Habitacion.getValorEnum().equals(remesaVO.getDescDerechoReal())){
			baseImponible = remesaVO.getImporteTotal().multiply(new BigDecimal(0.75)).multiply(usufructo.divide(new BigDecimal(100)));
		}
		modelo600_601VO.setBaseImponible(baseImponible.multiply(new BigDecimal(100)).divide(new BigDecimal(100)));
		modelo600_601VO.setValorDeclarado(remesaVO.getImporteTotal());
	}

	private BigDecimal calcularUsufructo(RemesaVO remesaVO) {
		BigDecimal usuFructo = BigDecimal.ZERO;
		if(DuracionDerechoReal.Temporal.getValorEnum().equals(remesaVO.getDuracionDerechoReal())) {
			usuFructo = remesaVO.getNumAnio().multiply(new BigDecimal(2));
		}else{
			BigDecimal anioNacUsu = new BigDecimal(remesaVO.getNacimientoUsuFructuario().getYear());
			BigDecimal anioDevengo = new BigDecimal(remesaVO.getFechaDevengo().getYear());
			BigDecimal edad = anioNacUsu.subtract(anioDevengo);
			usuFructo = new BigDecimal(89).subtract(edad);
			if (usuFructo.compareTo(new BigDecimal(70)) == 1){
				usuFructo = new BigDecimal(70);
			}
		}
		
		return usuFructo;
	}

	private BigDecimal calcularImportePorcentajes(BigDecimal importe, BigDecimal porcentaje) {
		try {
			BigDecimal importeFinal = (porcentaje.multiply(importe)).divide(new BigDecimal(100));
			return importeFinal;
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de calcular el importe de transmision del bien,error: ",e);
		}
		return null;
	}
}
