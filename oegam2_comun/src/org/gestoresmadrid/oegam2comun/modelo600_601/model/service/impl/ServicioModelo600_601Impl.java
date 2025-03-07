package org.gestoresmadrid.oegam2comun.modelo600_601.model.service.impl;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import org.apache.commons.lang.StringUtils;
import org.gestoresmadrid.core.bien.model.vo.BienRemesaVO;
import org.gestoresmadrid.core.bien.model.vo.BienRusticoVO;
import org.gestoresmadrid.core.bien.model.vo.BienUrbanoVO;
import org.gestoresmadrid.core.bien.model.vo.BienVO;
import org.gestoresmadrid.core.datosBancariosFavoritos.model.enumerados.EstadoDatosBancarios;
import org.gestoresmadrid.core.datosBancariosFavoritos.model.enumerados.TipoCuentaBancaria;
import org.gestoresmadrid.core.datosBancariosFavoritos.model.vo.DatosBancariosFavoritosVO;
import org.gestoresmadrid.core.general.model.vo.ColegiadoVO;
import org.gestoresmadrid.core.historicocreditos.model.dao.enumerados.ConceptoCreditoFacturado;
import org.gestoresmadrid.core.intervinienteModelos.model.vo.IntervinienteModelosVO;
import org.gestoresmadrid.core.model.enumerados.BancoEnum;
import org.gestoresmadrid.core.model.enumerados.TipoConverterSiNo;
import org.gestoresmadrid.core.model.enumerados.TipoFicheros;
import org.gestoresmadrid.core.model.enumerados.TipoInterviniente;
import org.gestoresmadrid.core.modelo.bien.model.dao.ModeloBienDao;
import org.gestoresmadrid.core.modelo.bien.model.vo.ModeloBienPK;
import org.gestoresmadrid.core.modelo.bien.model.vo.ModeloBienVO;
import org.gestoresmadrid.core.modelo600_601.model.dao.Modelo600_601Dao;
import org.gestoresmadrid.core.modelo600_601.model.vo.Modelo600_601VO;
import org.gestoresmadrid.core.modelo600_601.model.vo.ResultadoModelo600601VO;
import org.gestoresmadrid.core.modelos.model.enumerados.ErroresWSModelo600601;
import org.gestoresmadrid.core.modelos.model.enumerados.EstadoModelos;
import org.gestoresmadrid.core.modelos.model.enumerados.EstadoRemesas;
import org.gestoresmadrid.core.modelos.model.enumerados.GrupoConcepto;
import org.gestoresmadrid.core.modelos.model.enumerados.Modelo;
import org.gestoresmadrid.core.modelos.model.enumerados.TipoBien;
import org.gestoresmadrid.core.modelos.model.enumerados.TipoDocumentoModelo600601;
import org.gestoresmadrid.core.modelos.model.enumerados.TipoTramiteModelos;
import org.gestoresmadrid.core.modelos.model.enumerados.TipoUsoRustico;
import org.gestoresmadrid.core.modelos.model.vo.BonificacionVO;
import org.gestoresmadrid.core.modelos.model.vo.TipoImpuestoVO;
import org.gestoresmadrid.core.participacion.model.vo.ParticipacionVO;
import org.gestoresmadrid.core.personas.model.enumerados.TipoPersona;
import org.gestoresmadrid.core.remesa.model.vo.RemesaVO;
import org.gestoresmadrid.core.tasas.model.enumeration.FormaPago;
import org.gestoresmadrid.oegam2comun.bien.model.service.ServicioBien;
import org.gestoresmadrid.oegam2comun.bien.view.dto.BienDto;
import org.gestoresmadrid.oegam2comun.bien.view.dto.ModeloBienDto;
import org.gestoresmadrid.oegam2comun.bien.view.dto.UnidadMetricaDto;
import org.gestoresmadrid.oegam2comun.bienRustico.model.service.ServicioBienRustico;
import org.gestoresmadrid.oegam2comun.bienUrbano.model.service.ServicioBienUrbano;
import org.gestoresmadrid.oegam2comun.cola.model.service.ServicioCola;
import org.gestoresmadrid.oegam2comun.contrato.model.service.ServicioContrato;
import org.gestoresmadrid.oegam2comun.creditos.model.service.ServicioCredito;
import org.gestoresmadrid.oegam2comun.datosBancariosFavoritos.model.service.ServicioDatosBancariosFavoritos;
import org.gestoresmadrid.oegam2comun.datosBancariosFavoritos.view.dto.DatosBancariosFavoritosDto;
import org.gestoresmadrid.oegam2comun.direccion.model.service.ServicioDireccionCam;
import org.gestoresmadrid.oegam2comun.evolucionModelo600601.model.service.ServicioEvolucionModelo600_601;
import org.gestoresmadrid.oegam2comun.intervinienteModelos.model.service.ServicioIntervinienteModelos;
import org.gestoresmadrid.oegam2comun.intervinienteModelos.view.dto.IntervinienteModelosDto;
import org.gestoresmadrid.oegam2comun.model.service.ServicioNotificacion;
import org.gestoresmadrid.oegam2comun.model.service.ServicioUsuario;
import org.gestoresmadrid.oegam2comun.modelo600.xml.BienRusticoTypeO;
import org.gestoresmadrid.oegam2comun.modelo600.xml.BienUrbanoTypeO;
import org.gestoresmadrid.oegam2comun.modelo600.xml.IntervinienteA2Type;
import org.gestoresmadrid.oegam2comun.modelo600.xml.Remesa;
import org.gestoresmadrid.oegam2comun.modelo600.xml.Remesa.Declaracion.ListaBienesRusticos;
import org.gestoresmadrid.oegam2comun.modelo600.xml.Remesa.Declaracion.ListaBienesUrbanos;
import org.gestoresmadrid.oegam2comun.modelo600.xml.Remesa.Declaracion.ListaIntervinientes;
import org.gestoresmadrid.oegam2comun.modelo600.xml.TipoIntervinienteType;
import org.gestoresmadrid.oegam2comun.modelo600_601.model.service.ServicioModelo600_601;
import org.gestoresmadrid.oegam2comun.modelo600_601.model.service.ServicioResultadoModelo600601;
import org.gestoresmadrid.oegam2comun.modelo600_601.model.service.ServicioWebServiceModelo600601;
import org.gestoresmadrid.oegam2comun.modelo600_601.view.bean.Modelo600_601Bean;
import org.gestoresmadrid.oegam2comun.modelo600_601.view.dto.Modelo600_601Dto;
import org.gestoresmadrid.oegam2comun.modelo600_601.view.dto.ResultadoModelo600601Dto;
import org.gestoresmadrid.oegam2comun.modelo600_601.view.xml.BienRustico;
import org.gestoresmadrid.oegam2comun.modelo600_601.view.xml.BienUrbano;
import org.gestoresmadrid.oegam2comun.modelo600_601.view.xml.Interviniente;
import org.gestoresmadrid.oegam2comun.modelo600_601.view.xml.OtroBien;
import org.gestoresmadrid.oegam2comun.modelo600_601.view.xml.SolicitudPresentacionModelos;
import org.gestoresmadrid.oegam2comun.modelo601.xml.Remesa.Declaracion;
import org.gestoresmadrid.oegam2comun.modelos.model.service.ServicioBonificacion;
import org.gestoresmadrid.oegam2comun.modelos.model.service.ServicioConcepto;
import org.gestoresmadrid.oegam2comun.modelos.model.service.ServicioModelos;
import org.gestoresmadrid.oegam2comun.modelos.model.service.ServicioTipoImpuesto;
import org.gestoresmadrid.oegam2comun.modelos.view.dto.BonificacionDto;
import org.gestoresmadrid.oegam2comun.modelos.view.dto.ConceptoDto;
import org.gestoresmadrid.oegam2comun.modelos.view.dto.FundamentoExencionDto;
import org.gestoresmadrid.oegam2comun.modelos.view.dto.ModeloDto;
import org.gestoresmadrid.oegam2comun.modelos.view.dto.OficinaLiquidadoraDto;
import org.gestoresmadrid.oegam2comun.modelos.view.dto.TipoImpuestoDto;
import org.gestoresmadrid.oegam2comun.notario.view.dto.NotarioDto;
import org.gestoresmadrid.oegam2comun.personas.model.service.ServicioPersona;
import org.gestoresmadrid.oegam2comun.personas.model.service.ServicioPersonaDireccion;
import org.gestoresmadrid.oegam2comun.proceso.enumerados.ProcesosEnum;
import org.gestoresmadrid.oegam2comun.sistemaExplotacion.view.dto.SistemaExplotacionDto;
import org.gestoresmadrid.oegam2comun.situacion.view.dto.SituacionDto;
import org.gestoresmadrid.oegam2comun.tipoInmueble.view.dto.TipoInmuebleDto;
import org.gestoresmadrid.oegam2comun.trafico.model.service.ServicioTramiteTraficoBaja;
import org.gestoresmadrid.oegam2comun.usoRustico.view.dto.UsoRusticoDto;
import org.gestoresmadrid.oegam2comun.utiles.XmlModelo600601Factory;
import org.gestoresmadrid.oegam2comun.view.dto.NotificacionDto;
import org.gestoresmadrid.oegamComun.contrato.view.dto.ContratoDto;
import org.gestoresmadrid.oegamComun.conversor.Conversor;
import org.gestoresmadrid.oegamComun.direccion.view.dto.DireccionDto;
import org.gestoresmadrid.oegamComun.persona.view.dto.PersonaDireccionDto;
import org.gestoresmadrid.oegamComun.persona.view.dto.PersonaDto;
import org.gestoresmadrid.oegamComun.usuario.view.dto.UsuarioDto;
import org.gestoresmadrid.utilidades.components.GestorPropiedades;
import org.gestoresmadrid.utilidades.components.Utiles;
import org.gestoresmadrid.utilidades.components.UtilesFecha;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import com.google.common.io.Files;

import es.globaltms.gestorDocumentos.bean.FileResultBean;
import es.globaltms.gestorDocumentos.constantes.ConstantesGestorFicheros;
import es.globaltms.gestorDocumentos.interfaz.GestorDocumentos;
import es.globaltms.gestorDocumentos.util.Utilidades;
import escrituras.beans.ResultBean;
import utilidades.estructuras.Fecha;
import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;
import utilidades.web.OegamExcepcion;
import utilidades.web.OegamExcepcion.EnumError;
import viafirma.utilidades.UtilesViafirma;

@Service(value="servicioModelo600_601")
public class ServicioModelo600_601Impl implements ServicioModelo600_601{

	private static final long serialVersionUID = -3483676133746336005L;
	
	private static final ILoggerOegam log = LoggerOegam.getLogger(ServicioModelo600_601Impl.class);
	
//	private static final String RUTA_XSD_MODELO_600 = "/org/gestoresmadrid/oegam2/modelo600601/schemas/Modelo_600_Importacion.xsd";
//	private static final String RUTA_XSD_MODELO_601 = "/org/gestoresmadrid/oegam2/modelo600601/schemas/Modelo_601_Importacion.xsd";
	
	public static String ERROR_IMPORTAR_FICHERO = "Error al importar el fichero";
	public static String ERROR_VALIDAR_FICHERO = "Error al validar el fichero";

	private XmlModelo600601Factory xmlModelo600601Factory;
	
	private UtilesViafirma utilesViafirma;

	@Autowired
	private Conversor conversor;
	
	@Autowired
	private Modelo600_601Dao modelo600_601Dao;
	
	@Autowired
	private ServicioModelos servicioModelos;
	
	@Autowired
	private ModeloBienDao modeloBienDao;
	
	@Autowired
	private ServicioIntervinienteModelos servicioIntervinienteModelos;
	
	@Autowired
	private ServicioPersona servicioPersona;
	
	@Autowired
	private ServicioPersonaDireccion servicioPersonaDireccion;
	
	@Autowired
	private ServicioContrato servicioContrato;
	
	@Autowired
	private ServicioBien servicioBien;
	
	@Autowired
	private ServicioConcepto servicioConcepto;
	
	@Autowired
	private ServicioBonificacion servicioBonificacion;
	
	@Autowired
	private ServicioTipoImpuesto servicioTipoImpuesto;
	
	@Autowired
	private ServicioUsuario servicioUsuario;
	
	@Autowired
	private ServicioBienRustico servicioBienRustico;
	
	@Autowired
	private ServicioBienUrbano servicioBienUrbano;
	
	@Autowired
	private ServicioEvolucionModelo600_601 servicioEvolucionModelo600_601;
	
	@Autowired
	private ServicioNotificacion servicioNotificacion;
	
	@Autowired
	private ServicioDatosBancariosFavoritos servicioDatosBancariosFavoritos;
	
	@Autowired
	private ServicioDireccionCam servicioDireccionCam;
	
	@Autowired
	private GestorDocumentos gestorDocumentos;
	
	@Autowired
	private ServicioCredito servicioCredito;
	
	@Autowired
	private ServicioCola servicioCola;
	
	@Autowired
	private ServicioResultadoModelo600601 servicioResultadoModelo600601;
	
	@Autowired
	private GestorPropiedades gestorPropiedades;
	
	@Autowired
	UtilesFecha utilesFecha;

	@Autowired
	Utiles utiles;

	@Override
	@Transactional(readOnly=true)
	public BigDecimal generarNumExpediente(String numColegiado) throws Exception {
		return modelo600_601Dao.generarNumExpediente(numColegiado);
	}
	
	@Override
	@Transactional
	public ResultBean generarModeloRemesa(RemesaVO remesaVO, BigDecimal idUsuario) {
		ResultBean resultado = new ResultBean(false);
		try {
			Boolean existeTransmitente = false;
			IntervinienteModelosVO presentadorVO = null;
			for(IntervinienteModelosVO intervinienteModelosVO : remesaVO.getListaIntervinientes()){
				if(TipoInterviniente.Presentador.getValorEnum().equals(intervinienteModelosVO.getTipoInterviniente())){
					presentadorVO = intervinienteModelosVO;
					break;
				}
			}
			for(IntervinienteModelosVO intervinienteModelosVO : remesaVO.getListaIntervinientes()){
				if(TipoInterviniente.SujetoPasivo.getValorEnum().equals(intervinienteModelosVO.getTipoInterviniente())){
					for(IntervinienteModelosVO transmitenteVO : remesaVO.getListaIntervinientes()){
						if(TipoInterviniente.Transmitente.getValorEnum().equals(transmitenteVO.getTipoInterviniente())){
							existeTransmitente = true;
							ResultBean resultModelo = generarModeloInterviniente(intervinienteModelosVO,transmitenteVO,presentadorVO,remesaVO,idUsuario);
							if(resultModelo.getError()){
								return resultModelo;
							}
						}
					}
					if(!existeTransmitente){
						ResultBean resultModelo = generarModeloInterviniente(intervinienteModelosVO,null,presentadorVO,remesaVO,idUsuario);
						if(resultModelo.getError()){
							return resultModelo;
						}
					}
				}
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de generar los modelos de la remesa,error: ",e);
			resultado.setError(true);
			resultado.addMensajeALista("Ha sucedido un error a la hora de generar los modelos de la remesa");
		}
		return resultado;
	}

	private ResultBean generarModeloInterviniente(IntervinienteModelosVO sujetoPasivoVO,IntervinienteModelosVO transmitenteVO, IntervinienteModelosVO presentadorVO, RemesaVO remesaVO, BigDecimal idUsuario) {
		ResultBean resultado = new ResultBean(false);
		try {
			ResultBean resultNModelo = nuevoModelo600_601Remesa(remesaVO,idUsuario);
			if(!resultNModelo.getError()){
				Modelo600_601VO modelo600_601VO = (Modelo600_601VO) resultNModelo.getAttachment("modelo");
				BigDecimal valorDeclarado = BigDecimal.ZERO;
				if(transmitenteVO != null) {
					if(remesaVO.getListaBienes() != null && !remesaVO.getListaBienes().isEmpty()){
						for(BienRemesaVO bienRemesaVO : remesaVO.getListaBienes()){
							BigDecimal importeTransBien = calcularImportePorcentajes(bienRemesaVO.getValorDeclarado(),bienRemesaVO.getTransmision());
							BigDecimal importeSujPasvPartInterv = calcularImportePartInterviniente(importeTransBien,sujetoPasivoVO.getIdInterviniente(),bienRemesaVO.getId().getIdBien(),remesaVO.getListaCoefParticipacion());
							BigDecimal importeTransPartInter =  calcularImportePartInterviniente(importeSujPasvPartInterv,transmitenteVO.getIdInterviniente(),bienRemesaVO.getId().getIdBien(),remesaVO.getListaCoefParticipacion());
							ResultBean resultModeloBien = guardarModeloBien(bienRemesaVO.getId().getIdBien(),modelo600_601VO.getIdModelo(),importeTransPartInter,new BigDecimal(100));
							if(resultModeloBien != null){
								return resultModeloBien;
							}
							valorDeclarado = valorDeclarado.add(importeTransPartInter);
						}
						modelo600_601VO.setValorDeclarado(valorDeclarado);
						modelo600_601VO.setBaseImponible(valorDeclarado);
					}else{
						servicioConcepto.obtenerBaseImponibleRemesa600(remesaVO,sujetoPasivoVO,transmitenteVO,modelo600_601VO);
					}
					modelo600_601VO.setBaseLiquidable(modelo600_601VO.getBaseImponible());
				}else{
					modelo600_601VO.setValorDeclarado(BigDecimal.ZERO);
					modelo600_601VO.setBaseImponible(BigDecimal.ZERO);
					modelo600_601VO.setBaseLiquidable(BigDecimal.ZERO);
				}
				aplicarBonificacion(modelo600_601VO);
				ResultBean resultSujPasivo = servicioIntervinienteModelos.guardarIntervinientesModelo600_601(conversor.transform(sujetoPasivoVO, IntervinienteModelosVO.class,"intervModelosRemesaVO"),modelo600_601VO.getIdModelo());
				if(resultSujPasivo != null && resultSujPasivo.getError()){
					return resultSujPasivo;
				}
				if(transmitenteVO != null){
					ResultBean resultTransmitente = servicioIntervinienteModelos.guardarIntervinientesModelo600_601(conversor.transform(transmitenteVO, IntervinienteModelosVO.class,"intervModelosRemesaVO"),modelo600_601VO.getIdModelo());
					if(resultTransmitente != null && resultTransmitente.getError()){
						return resultTransmitente;
					}
				}
				ResultBean resultPresentador = servicioIntervinienteModelos.guardarIntervinientesModelo600_601(conversor.transform(presentadorVO, IntervinienteModelosVO.class,"intervModelosRemesaVO"),modelo600_601VO.getIdModelo());
				if(resultPresentador != null && resultPresentador.getError()){
					return resultPresentador;
				}
				ResultBean resultAutoLiquidacion = calcularLiquidacionModelosPorRemesa(modelo600_601VO);
				if(resultAutoLiquidacion != null && resultAutoLiquidacion.getError()){
					return resultAutoLiquidacion;
				}
				
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de generar los modelos de la remesa,error: ",e);
			resultado.setError(true);
			resultado.addMensajeALista("Ha sucedido un error a la hora de generar los modelos de la remesa");
		}
		return resultado;
	}

	private BigDecimal calcularImportePartInterviniente(BigDecimal importeTransBien, Long idInterviniente, Long idBien, Set<ParticipacionVO> listaCoefParticipacion) {
		for(ParticipacionVO participacionVO : listaCoefParticipacion){
			if(participacionVO.getIdBien().equals(idBien) && participacionVO.getIdInterviniente().equals(idInterviniente)){
				return calcularImportePorcentajes(importeTransBien, participacionVO.getPorcentaje());
			}
		}
		return null;
	}

	private ResultBean guardarModeloBien(Long idBien, Long idEscritura, BigDecimal valorDeclarado, BigDecimal transmision) {
		ResultBean resultado = null;
		try {
			ModeloBienVO modeloBienVO = new ModeloBienVO();
			ModeloBienPK id = new ModeloBienPK();
			id.setIdBien(idBien);
			id.setIdModelo(idEscritura);
			modeloBienVO.setId(id);
			if(transmision==null){
				modeloBienVO.setTransmision(new BigDecimal(100));
			}else{
				modeloBienVO.setTransmision(transmision);
			}
			modeloBienVO.setValorDeclarado(valorDeclarado);
			modeloBienDao.guardar(modeloBienVO);
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de guardar el bien del modelo, error: ",e);
			resultado = new ResultBean(true, "Ha sucedido un error a la hora de guardar el bien del modelo");
		}
		return resultado;
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

	private ResultBean nuevoModelo600_601Remesa(RemesaVO remesaVO, BigDecimal idUsuario) {
		ResultBean resultado = new ResultBean(false);
		try {
			Modelo600_601VO modelo600_601VO = conversor.transform(remesaVO, Modelo600_601VO.class);
			modelo600_601VO.setIdContrato(remesaVO.getContrato().getIdContrato());
			modelo600_601VO.setNumExpediente(generarNumExpediente(modelo600_601VO.getNumColegiado()));
			modelo600_601VO.setEstado(new BigDecimal(EstadoModelos.Autoliquidable.getValorEnum()));
			modelo600_601VO.setFechaAlta(new Date());
			modelo600_601Dao.guardar(modelo600_601VO);
			servicioEvolucionModelo600_601.guardarEvolucionModelo(modelo600_601VO.getNumExpediente(), null, modelo600_601VO.getEstado(),idUsuario);
			resultado.addAttachment("modelo", modelo600_601VO);
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de generar las escrituras de la remesa,error: ",e);
			resultado.setError(true);
			resultado.addMensajeALista("Ha sucedido un error a la hora de generar las escrituras de la remesa."); 
		}
		return resultado;
	}
	
	@Override
	@Transactional
	public ResultBean calcularLiquidacionModelosPorRemesa(Modelo600_601VO modelo600601VO) {
		ResultBean resultado = new ResultBean(false);
		try {
			if(!modelo600601VO.getExento().equals(BigDecimal.ONE) || !modelo600601VO.getNoSujeto().equals(BigDecimal.ONE)){
				modelo600601VO.setCuota(modelo600601VO.getBaseImponible().multiply(modelo600601VO.getTipoImpuesto().getMonto()).divide(new BigDecimal(100)));
				if(modelo600601VO.getBonificacion() != null){
					modelo600601VO.setBonificacionCuota(modelo600601VO.getCuota().multiply(modelo600601VO.getBonificacion().getMonto()).divide(new BigDecimal(100)));
				}else{
					modelo600601VO.setBonificacionCuota(BigDecimal.ZERO);
				}
				modelo600601VO.setIngresar(modelo600601VO.getCuota().subtract(modelo600601VO.getBonificacionCuota()));
				modelo600601VO.setTotalIngresar(modelo600601VO.getIngresar());
			}else{
				modelo600601VO.setCuota(BigDecimal.ZERO);
				modelo600601VO.setIngresar(BigDecimal.ZERO);
				modelo600601VO.setTotalIngresar(modelo600601VO.getIngresar());
			}
			modelo600_601Dao.actualizar(modelo600601VO);	
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de generar las liquidaciones de los modelos,error: ",e);
			resultado.setError(true);
			resultado.addMensajeALista("Ha sucedido un error a la hora de generar las liquidaciones de los modelos."); 
		}
		return resultado;
	}
	
	@Override
	@Transactional(readOnly=true)
	public ModeloDto getModelo(Modelo modelo) {
		return servicioModelos.getModeloDtoPorModelo(modelo);
	}
	
	@Override
	@Transactional(readOnly=true)
	public IntervinienteModelosDto getPresentador(BigDecimal idContrato) {
		ResultBean resultado = null;
		IntervinienteModelosDto intervinienteModelosDto = null;
		try {
			ContratoDto contratoDto = servicioContrato.getContratoDto(idContrato);
			if(contratoDto != null && contratoDto.getColegiadoDto() != null){
				resultado = servicioPersona.buscarPersona(contratoDto.getColegiadoDto().getUsuario().getNif(), contratoDto.getColegiadoDto().getNumColegiado());
				if(!resultado.getError()){
					PersonaDto personaDto = (PersonaDto) resultado.getAttachment(ServicioPersona.PERSONA);
					intervinienteModelosDto = new IntervinienteModelosDto();
					if(personaDto != null){
						intervinienteModelosDto.setPersona(personaDto);
						intervinienteModelosDto.setTipoInterviniente(TipoInterviniente.Presentador.getValorEnum());
						resultado = servicioPersonaDireccion.buscarPersonaDireccionDto(contratoDto.getColegiadoDto().getNumColegiado(), contratoDto.getColegiadoDto().getUsuario().getNif());
						if(!resultado.getError()){
							PersonaDireccionDto personaDireccionDto = (PersonaDireccionDto) resultado.getAttachment(ServicioPersonaDireccion.PERSONADIRECCION);
							intervinienteModelosDto.setDireccion(personaDireccionDto.getDireccion());
						}
					}
				}
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de obtener el presentador como el colegiado, error: ",e);
		}
		return intervinienteModelosDto;
	}
	
	public IntervinienteModelosDto getPresentadorContrato(ContratoDto contrato) {
		IntervinienteModelosDto intervinienteModelosDto = null;
		try {
			intervinienteModelosDto = getPresentador(contrato.getIdContrato());
			DireccionDto direccion = new DireccionDto();
			direccion.setCodPostal(contrato.getCodPostal());
			direccion.setEscalera(contrato.getEscalera());
			direccion.setIdMunicipio(contrato.getIdMunicipio());
			direccion.setIdProvincia(contrato.getIdProvincia());
			direccion.setIdTipoVia(contrato.getIdTipoVia());
			//direccion.setNombreMunicipio(contrato.getMunicipioDesc());
			direccion.setNombreProvincia(contrato.getContratoProvincia());
			direccion.setNombreVia(contrato.getVia());
			direccion.setNumero(contrato.getNumero());
			direccion.setPuerta(contrato.getPuerta());
			direccion.setPlanta(contrato.getPiso());
			direccion.setTipoViaDescripcion(contrato.getVia());
			intervinienteModelosDto.setDireccion(direccion);
//			PersonaDto persona = new PersonaDto();
//			persona.setApellido1RazonSocial(contrato.getColegiadoDto().getUsuario().getApellido1());
////			if(contrato.getRazonSocial().trim().isEmpty()){
////				persona.setApellido1RazonSocial(contrato.getApellido1());
////			}
//			persona.setApellido2(contrato.getColegiadoDto().getUsuario().getApellido2());
//			persona.setNif(contrato.getColegiadoDto().getUsuario().getNif());
//			persona.setNombre(contrato.getColegiadoDto().getUsuario().getNombre());
//			persona.setTelefonos(String.valueOf(contrato.getTelefono()));
//			//persona.setFechaNacimiento(contrato.getColegiadoDto().getUsuario().getf);
//			intervinienteModelosDto.setPersona(persona);
			intervinienteModelosDto.setTipoInterviniente("Presentador");
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de obtener el presentador como el colegiado, error: ",e);
		}
		return intervinienteModelosDto;
	}

	@Override
	@Transactional(readOnly=true)
	public ResultBean getModeloDto(BigDecimal numExpediente,Long idModelo, Boolean modeloCompleto) {
		ResultBean resultado = null;
		try {
			if(numExpediente != null || idModelo != null){
				Modelo600_601VO modeloVO = modelo600_601Dao.getModelo(numExpediente,idModelo, modeloCompleto);
				if(modeloVO != null){
					resultado = convertirVoToDto(modeloVO);
				}else{
					resultado = new ResultBean(true, "No existe ning�n modelo para ese numero de expediente");
				}
			}else{
				resultado = new ResultBean(true, "Debe seleccionar un numero de expediente para obtener el modelo.");
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de obtener el modelo por el expediente, error: ",e);
			resultado = new ResultBean(true, "Ha sucedido un error a la hora de obtener el modelo.");
		}
		return resultado;
	}
	
	@Override
	@Transactional(readOnly=true)
	public ResultBean esEstadoAnulado(BigDecimal numExpediente) {
		ResultBean resultado = new ResultBean(false);
		try {
			Boolean esAnulado = false;
			Modelo600_601VO modeloVO = modelo600_601Dao.getModeloPorExpediente(numExpediente,false);
			if(modeloVO != null){
				if(EstadoModelos.Anulado.getValorEnum().equals(modeloVO.getEstado().toString())){
					esAnulado = true;
				}
				resultado.addAttachment("esAnulado", esAnulado);
			}else{
				resultado.setError(true);
				resultado.addMensajeALista("No existen datos del modelo.");
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de comprobar el estado del modelo, error: ",e);
			resultado.setError(true);
			resultado.addMensajeALista("Ha sucedido un error a la hora de comprobar el estado del modelo.");
		}
		return resultado;
	}
	
	@Override
	@Transactional(readOnly=true)
	public Modelo600_601VO getModeloVOPorNumExpediente(BigDecimal numExpediente,Boolean modeloCompleto) {
		Modelo600_601VO modeloVO = null;
		try {
			if(numExpediente != null){
				modeloVO = modelo600_601Dao.getModeloPorExpediente(numExpediente,modeloCompleto);
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de obtener el modelo por el expediente, error: ",e);
		}
		return modeloVO;
	}
	
	@Override
	@Transactional(readOnly=true)
	public ResultBean buscarPersona(String nif, BigDecimal idContrato, Long idModelo, String tipoInterviniente) {
		ResultBean resultado = null;
		try {
			//buscamos si existe el interviniete en la remesa
			if(idModelo != null){
				resultado = servicioIntervinienteModelos.getIntervinientePorNifYIdModeloYTipoInterviniente(nif,idModelo,tipoInterviniente);
			}
			if(resultado == null){
				ContratoDto contratoDto = servicioContrato.getContratoDto(idContrato);
				if(contratoDto != null && contratoDto.getColegiadoDto() != null && contratoDto.getColegiadoDto().getNumColegiado() != null && nif != null && !nif.isEmpty()){
					ResultBean resultBean = servicioPersona.buscarPersona(nif, contratoDto.getColegiadoDto().getNumColegiado());
					if(!resultBean.getError()){
						PersonaDto personaDto = (PersonaDto) resultBean.getAttachment(ServicioPersona.PERSONA);
						IntervinienteModelosDto intervinienteModelosDto = new IntervinienteModelosDto();
						if(personaDto != null){
							intervinienteModelosDto.setPersona(personaDto);
							resultBean = servicioPersonaDireccion.buscarPersonaDireccionDto(contratoDto.getColegiadoDto().getNumColegiado(), nif);
							if(!resultBean.getError()){
								PersonaDireccionDto personaDireccionDto = (PersonaDireccionDto) resultBean.getAttachment(ServicioPersonaDireccion.PERSONADIRECCION);
								if(personaDireccionDto != null){
									intervinienteModelosDto.setDireccion(personaDireccionDto.getDireccion());
								}
							}
						}
						resultado = new ResultBean(false);
						resultado.addAttachment("intervinienteDto", intervinienteModelosDto);
					}else{
						resultado = new ResultBean(true, "Ha sucedido un error a la hora de buscar la persona");
					}
				}else{
					resultado = new ResultBean(true, "No existen datos suficientes para poder realizar la busqueda de los intervinientes.");
				}
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de obtener la persona por el nif: " + nif + ", y para el contrato: " + idContrato + ",error: " ,e);
			resultado = new ResultBean(true, "Ha sucedido un error a la hora de obtener la persona");
		}
		return resultado;
	}
	
	@Override
	public String getGrupoValidacion(String codigo) {
		String grupo = "";
		if(codigo != null && !codigo.isEmpty()){
			ConceptoDto conceptoDto = servicioConcepto.getConceptoPorAbreviatura(codigo);
			if(conceptoDto != null && conceptoDto.getGrupoValidacion() != null && !conceptoDto.getGrupoValidacion().isEmpty()){
				return GrupoConcepto.convertirNombre(conceptoDto.getGrupoValidacion());
			}
		}
		return grupo;
	}
	
	@Override
	public String getFundamentoExencion(String codigo) {
		String fundamento = "";
		if(codigo != null && !codigo.isEmpty()){
			TipoImpuestoDto impuestoDto = servicioTipoImpuesto.buscarTipoImpuestoPorConcepto(codigo);
			if(impuestoDto != null && impuestoDto.getSujetoExento() != null){
				if (impuestoDto.getSujetoExento().equals("E")){
					if(impuestoDto.getFundamentoExencion()!= null && !impuestoDto.getFundamentoExencion().isEmpty()){
						return impuestoDto.getFundamentoExencion();
					}
				} 
				return impuestoDto.getSujetoExento();
			}
		}
		return fundamento;
	}

	@Override
	public String getMontoBonificacion(String codigo) {
		if(codigo != null && !codigo.isEmpty()){
			BonificacionDto bonificacionDto = servicioBonificacion.getBonificacionPorId(codigo);
			if(bonificacionDto!= null && bonificacionDto.getMonto() != null){
				return bonificacionDto.getMonto().toString();
			}
		}
		return null;
	}
	
	private ResultBean convertirVoToDto(Modelo600_601VO modeloVO) {
		ResultBean resultado = null;
		Modelo600_601Dto modeloDto = conversor.transform(modeloVO, Modelo600_601Dto.class);
		if(modeloVO.getDatosBancarios() != null){
			servicioDatosBancariosFavoritos.desencriptarDatosBancariosVoToDto(modeloVO.getDatosBancarios(), modeloDto.getDatosBancarios());
		}else{
			DatosBancariosFavoritosDto datosBancariosFavoritosDto = null;
			if(modeloVO.getNumCuentaPago() != null && !modeloVO.getNumCuentaPago().isEmpty()){
				datosBancariosFavoritosDto = new DatosBancariosFavoritosDto();
				String numCuenta = servicioDatosBancariosFavoritos.descifrarNumCuenta(modeloVO.getNumCuentaPago());
				if(numCuenta != null && !numCuenta.isEmpty() && numCuenta.length() == 20){
					datosBancariosFavoritosDto.setEntidad("****");
					datosBancariosFavoritosDto.setOficina("****");
					datosBancariosFavoritosDto.setDc("**");
					datosBancariosFavoritosDto.setNumeroCuentaPago("******" + numCuenta.substring(16, 20));
				}
			} 
			if(modeloVO.getNifPagador() != null && !modeloVO.getNifPagador().isEmpty()){
				if(datosBancariosFavoritosDto == null){
					datosBancariosFavoritosDto = new DatosBancariosFavoritosDto();
				}
				datosBancariosFavoritosDto.setNifPagador(modeloVO.getNifPagador());
			}
			if(datosBancariosFavoritosDto != null){
				modeloDto.setDatosBancarios(datosBancariosFavoritosDto);
			}
		}
		resultado = servicioBien.convertirListaModeloBienVoToDto(modeloVO,modeloDto);
		if(resultado == null){
			resultado = servicioIntervinienteModelos.convertirIntervinientesModeloVoToDto(modeloVO, modeloDto);
			if(resultado == null || !resultado.getError()){
				if(modeloVO.getListaBienes() != null && !modeloVO.getListaBienes().isEmpty()){
					rellenarNombreBienes(modeloVO,modeloDto);
				}
				if(modeloDto.getListaResultadoModelo() != null && modeloDto.getListaResultadoModelo().isEmpty()){
					ordenarResultadoModelos(modeloDto.getListaResultadoModelo());
				}
			}
			resultado = servicioResultadoModelo600601.convertirListaResultadoVoToDto(modeloVO,modeloDto);
			if(resultado == null || !resultado.getError()){
				if(modeloDto.getListaResultadoModelo() != null && !modeloDto.getListaResultadoModelo().isEmpty()){
					ordenarResultadoModelos(modeloDto.getListaResultadoModelo());
				}
				resultado = new ResultBean(false);
				resultado.addAttachment("modeloDto", modeloDto);
			}
		}
		return resultado;
	}

	private void rellenarNombreBienes(Modelo600_601VO modeloVO,	Modelo600_601Dto modeloDto) {
		if(modeloDto.getListaBienesRusticos() != null && !modeloDto.getListaBienesRusticos().isEmpty()){
			ordenarBienes(modeloDto.getListaBienesRusticos());
			for(int i = 0; i < modeloDto.getListaBienesRusticos().size();i++){
				modeloDto.getListaBienesRusticos().get(i).getBien().setNombreBien(ServicioBien.NOMBRE_BIEN_RUSTICO + i);
			}
		}
		if(modeloDto.getListaBienesUrbanos() != null && !modeloDto.getListaBienesUrbanos().isEmpty()){
			ordenarBienes(modeloDto.getListaBienesUrbanos());
			for(int j = 0;j < modeloDto.getListaBienesUrbanos().size();j++){
				modeloDto.getListaBienesUrbanos().get(j).getBien().setNombreBien(ServicioBien.NOMBRE_BIEN_URBANO + j);
			}
		}
		if(modeloDto.getListaOtrosBienes() != null && !modeloDto.getListaOtrosBienes().isEmpty()){
			ordenarBienes(modeloDto.getListaOtrosBienes());
			for(int j = 0;j < modeloDto.getListaOtrosBienes().size();j++){
				modeloDto.getListaOtrosBienes().get(j).getBien().setNombreBien(ServicioBien.NOMBRE_OTRO_BIEN + j);
			}
		}

	}
	
	private void ordenarResultadoModelos(List<ResultadoModelo600601Dto> lista) {
		Collections.sort(lista,new Comparator<ResultadoModelo600601Dto>(){
			@Override
			public int compare(ResultadoModelo600601Dto rM1, ResultadoModelo600601Dto rM2) {
				return rM1.getFechaEjecucion().compareTo(rM2.getFechaEjecucion());
			}
		});
	}

	private void ordenarBienes(List<ModeloBienDto> lista) {
		Collections.sort(lista,new Comparator<ModeloBienDto>(){
			@Override
			public int compare(ModeloBienDto br1, ModeloBienDto br2) {
				return br1.getBien().getIdBien().compareTo(br2.getBien().getIdBien());
			}
		});
	}
	
	@Override
	@Transactional
	public ResultBean eliminarBien(Long idModelo, String codigo,BigDecimal idUsuario) {
		ResultBean resultado = new ResultBean(Boolean.FALSE);
		try {
			if(codigo != null && !codigo.isEmpty()){
				String idBienes[] = codigo.split("-");
				Modelo600_601VO modeloVO600601BBDD = null;
				for(String idBien : idBienes){
					ResultBean resultBien = servicioBien.eliminarModeloBien(Long.parseLong(idBien),idModelo);
					if(resultBien.getError()){
						resultado = resultBien;
						break;
					} else {
						modeloVO600601BBDD = (Modelo600_601VO) resultBien.getAttachment("modeloVO");
					}
				}
				if(!resultado.getError()){
					resultado = actualizarModelo600601ConEvolucion(modeloVO600601BBDD,idUsuario);
				}
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de eliminar los bienes, error: ",e);
			resultado = new ResultBean(true, "Ha sucedido un error a la hora de eliminar los bienes");
		}
		if(resultado != null && resultado.getError()){
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		}
		return resultado;
	}
	
	@Override
	@Transactional
	public ResultBean eliminarIntervinientesModelos(Long idModelo, String codigos) {
		ResultBean resultado = null;
		try {
			if(codigos != null && !codigos.isEmpty()){
				String idIntervientes[] = codigos.split("-");
				for(String idInterviniente : idIntervientes){
					ResultBean resultInterviniente = servicioIntervinienteModelos.eliminarInterviniente(Long.parseLong(idInterviniente));
					if(resultInterviniente != null && resultInterviniente.getError()){
						resultado = resultInterviniente;
						break;
					}
				}
			}else{
				resultado = new ResultBean(true, "Debe seleccionar alg�n interviniente para eliminar.");
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de eliminar los intervinientes, error: ",e);
			resultado = new ResultBean(true, "Ha sucedido un error a la hora de eliminar los intervinientes");
		}
		if(resultado != null && resultado.getError()){
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		}
		return resultado;
	}
	
	@Override
	@Transactional
	public ResultBean guardarBienesPantalla(Modelo600_601Dto modeloDto, String codigos, BigDecimal idUsuario, String numColegiado, File fichero, String fileName) {
		ResultBean resultado = new ResultBean(false);
		Boolean conErrores = false;
		try {
			ResultBean resultModelo = guardarModelo(modeloDto, numColegiado, idUsuario, fichero, fileName,false);
			if(!resultModelo.getError()){
				Long idModelo = (Long) resultModelo.getAttachment("idModelo");
				BigDecimal numExpediente = (BigDecimal) resultModelo.getAttachment("numExpediente");
				String idBienes[] = codigos.split("-");
				for(String idBien : idBienes){
					ResultBean resultBien = servicioBien.guardarModeloBien(Long.parseLong(idBien), idModelo);
					if(resultBien != null && !resultBien.getError()){
						conErrores = true;
					}else if(resultBien != null && resultBien.getError()){
						resultado = resultBien;
						break;
					}
				}
				if(conErrores){
					resultado.getListaMensajes().add("Algunos bienes seleccionados no se han guardado correctamente.");
				}
				resultado.addAttachment("numExpediente", numExpediente);
			}else{
				resultado = resultModelo;
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de guardar los bienes, error: ",e);
			resultado = new ResultBean(true, "Ha sucedido un error a la hora de guardar los bienes");
		}
		if(resultado != null && resultado.getError()){
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		}
		return resultado;
	}
	
	@Override
	@Transactional
	public ResultBean actualizarModelo600601ConEvolucion(Modelo600_601VO modelo600_601VO, BigDecimal idUsuario) {
		ResultBean resultado = new ResultBean(Boolean.FALSE);
		try {
			if(modelo600_601VO != null && modelo600_601VO.getIdModelo() != null && modelo600_601VO.getNumExpediente() != null){
				BigDecimal estadoAnterior = modelo600_601VO.getEstado();
				modelo600_601VO.setEstado(new BigDecimal(EstadoModelos.Inicial.getValorEnum()));
				actualizarDatosLiquidacion(modelo600_601VO, modelo600_601VO.isLiquidacionManual(),null);
				modelo600_601Dao.guardarOActualizar(modelo600_601VO);
				servicioEvolucionModelo600_601.guardarEvolucionModelo(modelo600_601VO.getNumExpediente(), estadoAnterior, new BigDecimal(EstadoRemesas.Inicial.getValorEnum()), idUsuario);
			} else {
				resultado.setError(Boolean.TRUE);
				resultado.setMensaje("El modelo no se puede actualizar porque se encuentra vacio o no tiene los datos minimos para poder guardarlo.");
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de actualizar el modelo despues de eliminar el bien, error: ",e);
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("Ha sucedido un error a la hora de actualizar el modelo despues de eliminar el bien.");
		}
		return resultado;
	}
	
	private void actualizarDatosLiquidacion(Modelo600_601VO modelo600_601VO, boolean liquidacionManual, ConceptoDto conceptoDto) {
		if(conceptoDto == null){
			conceptoDto = conversor.transform(modelo600_601VO.getConcepto(), ConceptoDto.class);
		}
		Boolean esConceptoBienes = servicioConcepto.esConceptoBienes(conceptoDto);
		if(!modelo600_601VO.isLiquidacionManual() && esConceptoBienes){
			modelo600_601VO.setBaseImponible(BigDecimal.ZERO);
		}
		if(!modelo600_601VO.isLiquidacionManual()){
			modelo600_601VO.setBaseLiquidable(BigDecimal.ZERO);
			modelo600_601VO.setCuota(BigDecimal.ZERO);
			modelo600_601VO.setBonificacionCuota(BigDecimal.ZERO);
			modelo600_601VO.setIngresar(BigDecimal.ZERO);
			modelo600_601VO.setTotalIngresar(BigDecimal.ZERO);
		}
	}

	@Override
	@Transactional
	public ResultBean guardarModelo(Modelo600_601Dto modeloDto,	String numColegiado, BigDecimal idUsuario, File fichero, String fileName, boolean mostrarEscritura) {
		ResultBean resultado = new ResultBean(false);
		String tipoTramiteModelo = null;
		try {
			resultado = validarDatosMinimosModelo(modeloDto);
			if(resultado == null){
				resultado = new ResultBean(false);
				convertirCombos(modeloDto);
				Modelo600_601VO modeloVO = conversor.transform(modeloDto, Modelo600_601VO.class);
				if(modeloDto.getNumExpediente() == null){
					modeloVO.setFechaAlta(new Date());
					modeloVO.setNumExpediente(generarNumExpediente(numColegiado));
				}
				ResultBean resultTpI = servicioTipoImpuesto.buscarTipoImpuestoPorConceptoYModelo(modeloDto.getConcepto(),modeloDto.getModelo());
				if(!resultTpI.getError()){
					modeloVO.setTipoImpuesto(conversor.transform(((TipoImpuestoDto) resultTpI.getAttachment("tipoImpuesto")),TipoImpuestoVO.class));
				}
				
				actualizarDatosLiquidacion(modeloVO, modeloDto.isLiquidacionManual(),modeloDto.getConcepto());
				servicioConcepto.convertirBaseImponibleDependiendoConcepto(modeloVO,modeloDto);
				BigDecimal estadoAnt = modeloVO.getEstado();
				modeloVO.setEstado(new BigDecimal(EstadoRemesas.Inicial.getValorEnum()));
				modelo600_601Dao.guardarOActualizar(modeloVO);
				servicioEvolucionModelo600_601.guardarEvolucionModelo(modeloVO.getNumExpediente(), estadoAnt, new BigDecimal(EstadoRemesas.Inicial.getValorEnum()), idUsuario);
				if(Modelo.Modelo600.getValorEnum().equals(modeloVO.getModelo().getId().getModelo())){
					tipoTramiteModelo = TipoTramiteModelos.Modelo600.getValorEnum();
				}else if(Modelo.Modelo601.getValorEnum().equals(modeloVO.getModelo().getId().getModelo())){
					tipoTramiteModelo = TipoTramiteModelos.Modelo601.getValorEnum();
				}
				UsuarioDto usuario = servicioUsuario.getUsuarioDto(idUsuario);
				boolean esConceptoBienes = servicioConcepto.esConceptoBienes(modeloDto.getConcepto());
				resultado = guardarListasModelos(modeloDto,modeloVO,usuario,tipoTramiteModelo,esConceptoBienes);
				//guardar Autoliquidacion
				if(resultado == null || !resultado.getError()){
					resultado.addAttachment("numExpediente", modeloVO.getNumExpediente());
					resultado.addAttachment("idModelo", modeloVO.getIdModelo());
				}
				// si mostrarEscritura es true no se guarda la escritura solo se muestra
				if(!mostrarEscritura){
					ResultBean resultEscritura = guardarEscritura(modeloVO.getNumExpediente(), fichero, fileName);
					if(fileName != null && !fileName.isEmpty()){
						modeloVO.setNombreEscritura((String)resultEscritura.getAttachment("nombreEscritura"));
						modeloDto.setNombreEscritura((String)resultEscritura.getAttachment("nombreEscritura"));
					}
				}
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de guardar el modelo, error: ",e);
			resultado = new ResultBean(true, "Ha sucedido un error a la hora de guardar el modelo.");
		}
		if(resultado != null && resultado.getError()){
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		}
		return resultado;
	}

	private ResultBean guardarListasModelos(Modelo600_601Dto modeloDto,Modelo600_601VO modeloVO, UsuarioDto usuario, String tipoTramiteModelo, boolean esConceptoBienes) {
		ResultBean resultado = new ResultBean(false);
		ResultBean resultInterv = guardarIntervinientes(modeloDto,modeloVO,usuario,tipoTramiteModelo);
		if(!resultInterv.getError()){
			if(resultInterv.getListaMensajes() != null && !resultInterv.getListaMensajes().isEmpty()){
				for(String error : resultInterv.getListaMensajes()) {
					resultado.getListaMensajes().add(error);
				}
			}
			if(esConceptoBienes){
				ResultBean resultBien = guardarBienes(modeloDto,modeloVO,usuario,tipoTramiteModelo);
				if(resultBien.getError()){
					if(resultBien.getListaMensajes() != null && !resultBien.getListaMensajes().isEmpty()){
						for(String error : resultBien.getListaMensajes()) {
							resultado.getListaMensajes().add(error);
						}
					}
				}
			} else{
				//Mantis 30882
				//Comprobar si tiene algun bien ya en la relacion
				//OBtenemos todos los bienes para modelo y los borramos ya que como no es de bienes no tienen que estar
				List<ModeloBienVO> listaModelosBien = modeloBienDao.getModeloBienPorIdModelo(modeloDto.getIdModelo());
				//y si hay los borra
				if (listaModelosBien != null && !listaModelosBien.isEmpty()) {
					for (Iterator iterator = listaModelosBien.iterator(); iterator.hasNext();) {
						ModeloBienVO modeloBienVO = (ModeloBienVO) iterator.next();
						modeloBienDao.borrar(modeloBienVO);
					}
				}
			}
		}
		return resultado;
	}

	private ResultBean guardarBienes(Modelo600_601Dto modeloDto, Modelo600_601VO modeloVO, UsuarioDto usuario, String tipoTramiteModelo) {
		ResultBean resultado = new ResultBean(false);
		
		if(modeloDto.getTipoBien() != null && !modeloDto.getTipoBien().isEmpty()){
			if(servicioBienRustico.esBienRustico(modeloDto.getBienRustico())){
				modeloDto.getBienRustico().getTipoInmueble().setIdTipoBien(TipoBien.Rustico.getValorEnum());
				resultado = servicioBien.guardarModeloBienPantalla(modeloDto.getBienRustico(),modeloVO.getIdModelo(),modeloVO.getNumExpediente(),usuario,tipoTramiteModelo);
			}else if(servicioBienUrbano.esBienUrbano(modeloDto.getBienUrbano())){
				modeloDto.getBienUrbano().getTipoInmueble().setIdTipoBien(TipoBien.Urbano.getValorEnum());
				if(!resultado.getError()) {
					resultado = servicioBien.guardarModeloBienPantalla(modeloDto.getBienUrbano(),modeloVO.getIdModelo(),modeloVO.getNumExpediente(),usuario,tipoTramiteModelo);	
				}
			}else if(servicioBien.esOtroBien(modeloDto.getOtroBien())){
				modeloDto.getOtroBien().setTipoBien(TipoBien.Otro.getValorEnum());
				modeloDto.getOtroBien().setTipoInmueble(new TipoInmuebleDto());
				modeloDto.getOtroBien().getTipoInmueble().setIdTipoBien(TipoBien.Otro.getValorEnum());
				modeloDto.getOtroBien().getTipoInmueble().setIdTipoInmueble("OT");
				if(StringUtils.isNotBlank(modeloDto.getOtroBien().getRefCatrastal()) || modeloDto.getOtroBien().getValorDeclarado() != null
						 || modeloDto.getOtroBien().getTransmision() != null  || modeloDto.getOtroBien().getValorTasacion() != null){
					resultado = servicioBien.guardarModeloBienPantalla(modeloDto.getOtroBien(),modeloVO.getIdModelo(),modeloVO.getNumExpediente(),usuario,tipoTramiteModelo);
				}
			}
		}
		return resultado;
	}

	private ResultBean guardarIntervinientes(Modelo600_601Dto modeloDto,Modelo600_601VO modeloVO, UsuarioDto usuario, String tipoTramiteModelo) {
		ResultBean resultado = new ResultBean(false);
		List<String> listaMensajes = new ArrayList<String>();
		List<IntervinienteModelosVO> listaIntervinientes = servicioIntervinienteModelos.getListaIntervinientesVOPorModelo(modeloVO.getIdModelo());
		if(modeloDto.getSujetoPasivo() != null && modeloDto.getSujetoPasivo().getPersona() != null 
				&& modeloDto.getSujetoPasivo().getPersona().getNif() != null && !modeloDto.getSujetoPasivo().getPersona().getNif().isEmpty()){
			modeloDto.getSujetoPasivo().setTipoInterviniente(TipoInterviniente.SujetoPasivo.getValorEnum());
			for(IntervinienteModelosVO intervinienteModelosBBDD : listaIntervinientes){
				if(TipoInterviniente.SujetoPasivo.getValorEnum().equals(intervinienteModelosBBDD.getTipoInterviniente())){
					if(!intervinienteModelosBBDD.getPersona().getId().getNif().equalsIgnoreCase(modeloDto.getSujetoPasivo().getPersona().getNif())){
						servicioIntervinienteModelos.eliminarInterviniente(intervinienteModelosBBDD.getIdInterviniente());
						break;
					}else{
						servicioIntervinienteModelos.evict(intervinienteModelosBBDD);
					}
				}
			}
			ResultBean resultSjP = servicioIntervinienteModelos.guardarIntervinientesModelo(conversor.transform(modeloDto.getSujetoPasivo(), IntervinienteModelosVO.class),modeloVO,tipoTramiteModelo,usuario);
			if(resultSjP.getError()){
				return resultSjP;
			}else{
				if(resultSjP.getListaMensajes() != null && !resultSjP.getListaMensajes().isEmpty()){
					for(String mensaje : resultSjP.getListaMensajes()){
						listaMensajes.add(mensaje);
					}
				}
			}
		}else{
			if(modeloDto.getSujetoPasivo().getIdInterviniente() != null){
				resultado = servicioIntervinienteModelos.eliminarInterviniente(modeloDto.getSujetoPasivo().getIdInterviniente());
				if(resultado.getError()){
					return resultado;
				}
				modeloDto.setSujetoPasivo(null);
			}
		}
		if(modeloDto.getTransmitente() != null && modeloDto.getTransmitente().getPersona() != null 
				&& modeloDto.getTransmitente().getPersona().getNif() != null && !modeloDto.getTransmitente().getPersona().getNif().isEmpty()){
			modeloDto.getTransmitente().setTipoInterviniente(TipoInterviniente.Transmitente.getValorEnum());
			for(IntervinienteModelosVO intervinienteModelosBBDD : listaIntervinientes){
				if(TipoInterviniente.Transmitente.getValorEnum().equals(intervinienteModelosBBDD.getTipoInterviniente())){
					if(!intervinienteModelosBBDD.getPersona().getId().getNif().equalsIgnoreCase(modeloDto.getTransmitente().getPersona().getNif())){
						servicioIntervinienteModelos.eliminarInterviniente(intervinienteModelosBBDD.getIdInterviniente());
						break;
					}else{
						servicioIntervinienteModelos.evict(intervinienteModelosBBDD);
					}
				}
			}
			ResultBean resultTrans = servicioIntervinienteModelos.guardarIntervinientesModelo(conversor.transform(modeloDto.getTransmitente(), IntervinienteModelosVO.class),modeloVO,tipoTramiteModelo,usuario);
			if(resultTrans.getError()){
				return resultTrans;
			}else{
				if(resultTrans.getListaMensajes() != null && !resultTrans.getListaMensajes().isEmpty()){
					for(String mensaje : resultTrans.getListaMensajes()){
						listaMensajes.add(mensaje);
					}
				}
			}
		}else{
			if(modeloDto.getTransmitente().getIdInterviniente() != null){
				resultado = servicioIntervinienteModelos.eliminarInterviniente(modeloDto.getTransmitente().getIdInterviniente());
				if(resultado.getError()){
					return resultado;
				}
				modeloDto.setTransmitente(null);
			}
		}
		if(modeloDto.getPresentador() != null && modeloDto.getPresentador().getPersona() != null 
				&& modeloDto.getPresentador().getPersona().getNif() != null && !modeloDto.getPresentador().getPersona().getNif().isEmpty()
				&& modeloDto.getPresentador().getIdInterviniente() == null){
			modeloDto.getPresentador().setTipoInterviniente(TipoInterviniente.Presentador.getValorEnum());
			ResultBean resultP = servicioIntervinienteModelos.guardarIntervinientesModelo(conversor.transform(modeloDto.getPresentador(), IntervinienteModelosVO.class),modeloVO,tipoTramiteModelo,usuario);
			if(resultP.getError()){
				return resultado;
			}else{
				if(resultP.getListaMensajes() != null && !resultP.getListaMensajes().isEmpty()){
					for(String mensaje : resultP.getListaMensajes()){
						listaMensajes.add(mensaje);
					}
				}
			}
		}
		if(listaMensajes != null && !listaMensajes.isEmpty()){
			resultado.setListaMensajes(listaMensajes);
		}
		return resultado;
	}

	private void convertirCombos(Modelo600_601Dto modeloDto) {
		if(StringUtils.isBlank(modeloDto.getIdBonificacion()) || modeloDto.getIdBonificacion().equals("-1")){
			modeloDto.setIdBonificacion(null);
			modeloDto.setBonificacion(null);
		}
		if(StringUtils.isBlank(modeloDto.getIdFundamentoExencion()) || modeloDto.getIdFundamentoExencion().contentEquals("-1")){
			modeloDto.setFundamentoExencion(null);
			modeloDto.setIdFundamentoExencion(null);
		}
		if(modeloDto.getDescDerechoReal() != null){
			modeloDto.setDescDerechoReal(null);
		}
	}

	private ResultBean validarDatosMinimosModelo(Modelo600_601Dto modeloDto) {
		if(modeloDto == null){
			return new ResultBean(true, "Debe rellenar los datos minimos del modelo para poder guardar correctamente.");
		}
		if(modeloDto.getModelo() == null || modeloDto.getModelo().getModelo() == null || modeloDto.getModelo().getModelo().isEmpty()){
			return new ResultBean(true, "Debe seleccionar el tipo de modelo a guardar.");
		}
		if(modeloDto.getConcepto() == null || modeloDto.getConcepto().getConcepto() == null || modeloDto.getConcepto().getConcepto().isEmpty()){
			return new ResultBean(true, "Debe seleccionar el concepto del modelo para poder guardar correctamente.");
		}
		if(modeloDto.getContrato() == null || modeloDto.getContrato().getIdContrato() == null){
			return new ResultBean(true, "No existen datos del contrato, por lo que no se podra guardar el modelo.");
		}
		if(modeloDto.getOficinaLiquidadora() == null || modeloDto.getOficinaLiquidadora().getId() == null || modeloDto.getOficinaLiquidadora().getId().isEmpty()){
			return new ResultBean(true, "Debe seleccionar una oficina liquidadora.");
		}
		if(modeloDto.getOficinaLiquidadora() == null || modeloDto.getOficinaLiquidadora().getIdProvincia() == null || modeloDto.getOficinaLiquidadora().getIdProvincia().isEmpty()){
			return new ResultBean(true, "Debe seleccionar una provincia para la oficina liquidadora.");
		}
		if(modeloDto.getTipoDocumento() == null || modeloDto.getTipoDocumento().isEmpty()){
			return new ResultBean(true, "Debe seleccionar un tipo de documento.");
		}
		if(modeloDto.getFechaDevengo() == null){
			return new ResultBean(true, "Debe rellenar la fecha devengo.");
		}
		if(!modeloDto.getLiquidacionComplementaria()){
			//modeloDto.setNumJustiPrimeraAutolq(null);
			modeloDto.setFechaPrimLiquidacion(null);
			modeloDto.setNumeroPrimPresentacion(null);
		}
		return null;
	}
	
	@Override
	public List<Modelo600_601Bean> convertirListaEnBeanPantalla(List<Modelo600_601VO> lista) {
		try {
			if(lista != null && !lista.isEmpty()){
				List<Modelo600_601Bean> listaBeanPantalla = new ArrayList<Modelo600_601Bean>();
				for(Modelo600_601VO modeloVO : lista){
					Modelo600_601Bean modeloBean = conversor.transform(modeloVO, Modelo600_601Bean.class, "modelo600_601BeanPantalla");
					modeloBean.setDescEstado(EstadoModelos.convertirTexto(modeloBean.getEstado()));
					listaBeanPantalla.add(modeloBean);
				}
				return listaBeanPantalla;
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de convertir el objeto Modelo600_601VO to Modelo600_601Bean, error: ",e);
		}
		return Collections.emptyList();
	}
	
	@Override
	@Transactional
	public ResultBean gestionarConceptoModelo(Long idModelo) {
		ResultBean resultado = new ResultBean(false);
		try {
			Modelo600_601VO modeloBBDD = modelo600_601Dao.getModeloPorId(idModelo,false);
			resultado = servicioConcepto.gestionarModeloPorConcepto(modeloBBDD);
			if(resultado != null && resultado.getError()){
				TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de gestionar los datos del modelo por el concepto, error: ",e);
			resultado.setError(true);
			resultado.addMensajeALista("Ha sucedido un error a la hora de gestionar los datos del modelo por el concepto");
		}
		return resultado;
	}
	
	@Override
	@Transactional
	public ResultBean actualizarModelo600601(Modelo600_601VO modeloVO) {
		ResultBean resultado = new ResultBean(false);
		try {
			if(modeloVO != null){
				modelo600_601Dao.actualizar(modeloVO);
			}else{
				resultado.setError(true);
				resultado.addMensajeALista("El modelo que esta intentando actualizar esta vacio");
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de actualizar el modelo, error: ",e);
			resultado.setError(true);
			resultado.addMensajeALista("Ha sucedido un error a la hora de actualizar el modelo");
		}
		return resultado;
	}
	
	@Override
	@Transactional
	public ResultBean validarModelo(BigDecimal numExpediente, BigDecimal idUsuario) {
		ResultBean resultado = new ResultBean(false);
		try {
			Modelo600_601VO modeloBBDDVO = modelo600_601Dao.getModeloPorExpediente(numExpediente, true);
			resultado = validarModelo(modeloBBDDVO,false);
			if(!resultado.getError()){
				modeloBBDDVO.setEstado(new BigDecimal(EstadoModelos.Validado.getValorEnum()));
				if(!modeloBBDDVO.isLiquidacionManual()){
					servicioBien.guardarValorDeclaradoBien(modeloBBDDVO);
				}
				modelo600_601Dao.actualizar(modeloBBDDVO);
				servicioEvolucionModelo600_601.guardarEvolucionModelo(modeloBBDDVO.getNumExpediente(), new BigDecimal(EstadoModelos.Inicial.getValorEnum()), 
						new BigDecimal(EstadoModelos.Validado.getValorEnum()), idUsuario);
			}
			resultado.addAttachment("tipoModelo", modeloBBDDVO.getModelo().getId().getModelo());
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de validar el modelo,error: ",e);
			resultado.setError(true);
			resultado.addMensajeALista("Ha sucedido un error a la hora de validar el modelo.");
		}
		if(resultado.getError()){
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		}
		return resultado;
	}

	
	private ResultBean validarModeloPresentacion(Modelo600_601VO modelo600601VO, Boolean esEnBloque,DatosBancariosFavoritosDto datosBancariosFavoritosDto, Boolean esCambiarEstadoAccion) {
		ResultBean resultBean = new ResultBean(false);
		if(modelo600601VO == null){
			return new ResultBean(true,"No se encuentran datos del modelo.");
		}
		if(!esCambiarEstadoAccion){
			if(!esEnBloque){
				if(modelo600601VO.getEstado() == null || !EstadoModelos.Autoliquidable.getValorEnum().equals(modelo600601VO.getEstado().toString())){
					return new ResultBean(true,"El modelo debe encontrarse en estado AutoLiquidado para poder presentarlo.");
				}
			}
			
			if(modelo600601VO.getTotalIngresar() != null && modelo600601VO.getTotalIngresar().compareTo(BigDecimal.ZERO) == 1){
				if(datosBancariosFavoritosDto == null){
					return new ResultBean(true,"Debe rellenar los datos bancarios para poder realizar la presentaci�n.");
				}
				if(datosBancariosFavoritosDto.getIdDatosBancarios() == null){
					if(datosBancariosFavoritosDto.getNifPagador() == null || datosBancariosFavoritosDto.getNifPagador().isEmpty()){
						return new ResultBean(true,"Debe rellenar el nif del pagador para poder realizar la presentaci�n.");
					}
					
					if(datosBancariosFavoritosDto.getEntidad() == null || datosBancariosFavoritosDto.getEntidad().isEmpty()){
						return new ResultBean(true,"Debe rellenar el numero de cuenta al completo para poder realizar la presentaci�n.");
					}else if(datosBancariosFavoritosDto.getEntidad().length() != 4){
						return new ResultBean(true,"Debe rellenar los 4 d�gitos de la entidad para poder realizar la presentaci�n.");
					}else if(BancoEnum.convertir(datosBancariosFavoritosDto.getEntidad())==null){
						return new ResultBean(true,"Este banco no esta soportado.");
					}
					if(datosBancariosFavoritosDto.getOficina() == null || datosBancariosFavoritosDto.getOficina().isEmpty()){
						return new ResultBean(true,"Debe rellenar el numero de cuenta al completo para poder realizar la presentaci�n.");
					}else if(datosBancariosFavoritosDto.getOficina().length() != 4){
						return new ResultBean(true,"Debe rellenar los 4 d�gitos de la oficina para poder realizar la presentaci�n.");
					}
					if(datosBancariosFavoritosDto.getDc() == null || datosBancariosFavoritosDto.getDc().isEmpty()){
						return new ResultBean(true,"Debe rellenar el numero de cuenta al completo para poder realizar la presentaci�n.");
					}else if(datosBancariosFavoritosDto.getDc().length() != 2){
						return new ResultBean(true,"Debe rellenar los 2 d�gitos del DC para poder realizar la presentaci�n.");
					}
					if(datosBancariosFavoritosDto.getNumeroCuentaPago() == null || datosBancariosFavoritosDto.getNumeroCuentaPago().isEmpty()){
						return new ResultBean(true,"Debe rellenar el numero de cuenta al completo para poder realizar la presentaci�n.");
					}else if(datosBancariosFavoritosDto.getNumeroCuentaPago().length() != 10){
						return new ResultBean(true,"Debe rellenar los 10 d�gitos de la cuenta para poder realizar la presentaci�n.");
					}
					if(datosBancariosFavoritosDto.getEsFavorita() != null && datosBancariosFavoritosDto.getEsFavorita()){
						if(datosBancariosFavoritosDto.getDescripcion() == null){
							return new ResultBean(true,"Debe rellenarla descripcion de la cuenta para poder guardarla como favorita.");
						}
					}
				}
			}
		}
		return resultBean;
	}
	
	private ResultBean validarModelo(Modelo600_601VO modeloVO, Boolean esAutoLiq) {
		ResultBean resultado = new ResultBean(false);
		if(modeloVO == null){
			resultado.setError(true);
			resultado.addMensajeALista("No se encuentran datos del modelo.");
		}
		if(esAutoLiq){
			if(modeloVO.getEstado() == null || !EstadoModelos.Validado.getValorEnum().equals(modeloVO.getEstado().toString())){
				resultado.setError(true);
				resultado.addMensajeALista("El modelo se debe de encontrar en estado Validado para poder generar la autoliquidaci�n.");
			}
		}else{
			if(modeloVO.getEstado() == null || !EstadoModelos.Inicial.getValorEnum().equals(modeloVO.getEstado().toString())){
				resultado.setError(true);
				resultado.addMensajeALista("El modelo se debe de encontrar en estado Inicial.");
			}
		}
		if(modeloVO.getConcepto() == null || modeloVO.getConcepto().getId().getConcepto() == null 
				|| modeloVO.getConcepto().getId().getConcepto().isEmpty() 
				|| modeloVO.getConcepto().getNinter() == null || modeloVO.getConcepto().getNinter().isEmpty()){
			resultado.setError(true);
			resultado.addMensajeALista("Debe seleccionar un concepto.");
		}
		
		if(modeloVO.getValorDeclarado() == null){
			resultado.setError(true);
			resultado.addMensajeALista("Debe de rellenar el valor declarado.");
		}
		
		if(modeloVO.getExento().intValue() == 1){
			if(null == modeloVO.getFundamentoExencion()){
				resultado.setError(true);
				resultado.addMensajeALista("Al ser exento debe llevar un fundamento.");
			}
		}

		ResultBean resultValBien = servicioBien.validarBienModelos(modeloVO);
		if(resultValBien != null && resultValBien.getError()){
			for(String mensaje : resultValBien.getListaMensajes()){
				resultado.addMensajeALista(mensaje);
			}
		}
		
		if(Modelo.Modelo600.getValorEnum().equals(modeloVO.getModelo().getId().getModelo())){
			//validar por concepto
			ResultBean resulValModConcept = servicioConcepto.validarModelo600PorConcepto(modeloVO);
			if(resulValModConcept != null && resulValModConcept.getError()){
				for(String mensaje : resulValModConcept.getListaMensajes()){
					resultado.addMensajeALista(mensaje);
				}
			}
		}
	
		if(modeloVO.getTipoDocumento() == null || modeloVO.getTipoDocumento().isEmpty()){
			resultado.setError(true);
			resultado.addMensajeALista("Debe seleccionar un tipo de documento.");
		}else{
			if(ServicioModelo600_601.TIPO_DOC_PUBLICO.equals(modeloVO.getTipoDocumento())){
				if(modeloVO.getNotario() == null || modeloVO.getNotario().getCodigoNotario() == null || modeloVO.getNotario().getCodigoNotario().isEmpty()){
					resultado.setError(true);
					resultado.addMensajeALista("Para un documento p�blico debe seleccionar un notario.");
				}else if(modeloVO.getNumProtocolo() == null){
					resultado.setError(true);
					resultado.addMensajeALista("Para un documento p�blico debe rellenar el numero de protocolo.");
				}
			}else{
				if((modeloVO.getNotario() == null || (modeloVO.getNotario().getCodigoNotario() == null && modeloVO.getNotario().getCodigoNotario().isEmpty())) 
						&& modeloVO.getNumProtocolo() == null){
					resultado.setError(true);
					resultado.addMensajeALista("Para un documento no p�blico debe seleccionar un notario o rellenar el n�mero de protocolo.");
				}
			}
		}
	
		if(BigDecimal.ONE.equals(modeloVO.getExento()) &&
				(modeloVO.getFundamentoExencion() == null || modeloVO.getFundamentoExencion().getFundamentoExcencion() == null || modeloVO.getFundamentoExencion().getFundamentoExcencion().isEmpty())){
			resultado.setError(true);
			resultado.addMensajeALista("Debe seleccionar un fundamento de exencion si marca la casilla de exento.");
		}else if(BigDecimal.ONE.equals(modeloVO.getNoSujeto()) &&
				(modeloVO.getFundamentoNoSujeccion() == null || modeloVO.getFundamentoNoSujeccion().isEmpty())){
			resultado.setError(true);
			resultado.addMensajeALista("Debe seleccionar un fundamento de no sujeci�n si marca la casilla de no sujeto.");
		}

		if(BigDecimal.ONE.equals(modeloVO.getLiquidacionComplementaria())){
			if(modeloVO.getNumeroPrimPresentacion() == null ){
				resultado.setError(true);
				resultado.addMensajeALista("Debe de rellenar el numero de la primera autoliquidaci�n.");
			}else if(modeloVO.getNumeroPrimPresentacion().toString().length() != 13){
				resultado.setError(true);
				resultado.addMensajeALista("El numero de la primera autoliquidaci�n tiene que tener una longitud de 13 caracteres.");
			}else if(modeloVO.getFechaPrimLiquidacion() == null){
				resultado.setError(true);
				resultado.addMensajeALista("Debe de rellenar la fecha de la primera liquidaci�n.");
			}
		}

		if(modeloVO.getFechaDevengo() == null){
			resultado.setError(true);
			resultado.addMensajeALista("Debe de rellenar la fecha devengo.");
		}

		if(modeloVO.getOficinaLiquidadora() == null || modeloVO.getOficinaLiquidadora().getId().getIdProvincia() == null 
				|| modeloVO.getOficinaLiquidadora().getId().getIdProvincia().isEmpty() 
				|| modeloVO.getOficinaLiquidadora().getId().getOficinaLiquidadora() == null || modeloVO.getOficinaLiquidadora().getId().getOficinaLiquidadora().isEmpty()){
			resultado.setError(true);
			resultado.addMensajeALista("Debe de seleccionar la oficina liquidadora.");
		}

		if(modeloVO.getTipoImpuesto() == null || modeloVO.getTipoImpuesto().getMonto() == null){
			resultado.setError(true);
			resultado.addMensajeALista("No existe el tipo de impuesto.");
		}

		ResultBean resultValInterv = servicioIntervinienteModelos.validarIntervinientesModelos(modeloVO);
		if((resultValInterv != null && resultValInterv.getError()) || (resultValBien !=null && resultValBien.getError())){
			resultado.setError(true);
			for(String mensaje : resultValInterv.getListaMensajes()){
				resultado.addMensajeALista(mensaje);
			}
		}else{
			if(!resultado.getError()){
				if(resultValInterv.getListaMensajes() != null && !resultValInterv.getListaMensajes().isEmpty()){
					for(String mensaje : resultValInterv.getListaMensajes()){
						resultado.addMensajeALista(mensaje);
					}
				}
			}
		}

		return resultado;
	}

	@Override
	@Transactional
	public ResultBean autoLiquidarModelo(BigDecimal numExpediente, BigDecimal idUsuario, Modelo600_601Dto modeloDto) {
		ResultBean resultado = new ResultBean(false);
		try {
			Modelo600_601VO modeloBBDDVO = modelo600_601Dao.getModeloPorExpediente(numExpediente, true);
			if(modeloDto!=null) {
				comprobarDatosPantalla(modeloDto, modeloBBDDVO);
			}
			resultado = validarModelo(modeloBBDDVO, true);
			if(!resultado.getError()){
				BigDecimal estadoAnterior =  modeloBBDDVO.getEstado();
				if(!modeloBBDDVO.isLiquidacionManual()){
					aplicarBonificacion(modeloBBDDVO);
					resultado = autoLiquidar(modeloBBDDVO);
					if(!resultado.getError()){
						modeloBBDDVO.setEstado(new BigDecimal(EstadoModelos.Autoliquidable.getValorEnum()));
					}
				} else {
					
					rellenarDatosLiquidacion(modeloBBDDVO,modeloDto);
					resultado = validarDatosLiquidacion(modeloBBDDVO);
					if(!resultado.getError()){
						modeloBBDDVO.setEstado(new BigDecimal(EstadoModelos.Autoliquidable.getValorEnum()));
					}
				}
				if(!resultado.getError()){
					modelo600_601Dao.actualizar(modeloBBDDVO);
					servicioEvolucionModelo600_601.guardarEvolucionModelo(modeloBBDDVO.getNumExpediente(), estadoAnterior, new BigDecimal(EstadoModelos.Autoliquidable.getValorEnum()), idUsuario);
				}
			}
			if(modeloBBDDVO != null){
				resultado.addAttachment("tipoModelo", modeloBBDDVO.getModelo().getId().getModelo());
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de autoliquidar el modelo, error: ",e);
			resultado.setError(true);
			resultado.addMensajeALista("Ha sucedido un error a la hora de autoliquidar el modelo.");
		}
		if(resultado != null && resultado.getError()){
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		}
		return resultado;
	}

	@Override
	@Transactional
	public ResultBean calcularLiquidacion(Modelo600_601Dto modeloDto) {
		ResultBean resultado = new ResultBean(false);
		try{	
			if(modeloDto.getBaseImponible() == null || modeloDto.getBaseImponible().equals(BigDecimal.ZERO)){
				BigDecimal importeTotal = BigDecimal.ZERO;
				if(modeloDto.getListaBienesUrbanos() != null && !modeloDto.getListaBienesUrbanos().isEmpty()){
					for(ModeloBienDto modeloBien : modeloDto.getListaBienesUrbanos()){
						importeTotal = importeTotal.add(calcularImportePorcentajes(modeloBien.getValorDeclarado(), modeloBien.getTransmision()));
					}
				}
				if(modeloDto.getListaBienesRusticos() != null && !modeloDto.getListaBienesRusticos().isEmpty()){
					for(ModeloBienDto modeloBien : modeloDto.getListaBienesRusticos()){
						importeTotal = importeTotal.add(calcularImportePorcentajes(modeloBien.getValorDeclarado(), modeloBien.getTransmision()));
					}
				}
				if((modeloDto.getListaBienesUrbanos() == null || modeloDto.getListaBienesUrbanos().isEmpty()) && 
						(modeloDto.getListaBienesRusticos() == null || modeloDto.getListaBienesRusticos().isEmpty())){
					importeTotal = modeloDto.getValorDeclarado();
				}
				modeloDto.setBaseImponible(importeTotal.toString());
			}
			modeloDto.setBaseLiquidable(modeloDto.getBaseImponible());
			if(!modeloDto.getExento().equals(BigDecimal.ONE) || !modeloDto.getNoSujeto().equals(BigDecimal.ONE)){
				BigDecimal cuota = (new BigDecimal(modeloDto.getBaseImponible())).multiply(modeloDto.getTipoImpuesto().getMonto()).divide(new BigDecimal(100));
				modeloDto.setCuota(cuota.toString());
				BigDecimal bonificacionCuota = BigDecimal.ZERO;
				if(modeloDto.getBonificacion() != null){
					bonificacionCuota = cuota.multiply(modeloDto.getBonificacion().getMonto()).divide(new BigDecimal(100));
				}
				modeloDto.setBonificacionCuota(bonificacionCuota.toString());
				modeloDto.setIngresar((cuota.subtract(bonificacionCuota)).toString());
				modeloDto.setTotalIngresar(modeloDto.getIngresar());
			}else{
				modeloDto.setCuota("0,00");
				modeloDto.setIngresar("0,00");
				modeloDto.setTotalIngresar(modeloDto.getIngresar());
			}
			//prueba
		} catch (Exception e) {
			log.error("Ha sucedido un error al calcular la liquidacion del modelo.: ",e);
			resultado = new ResultBean(true, "Ha sucedido un error al calcular la liquidacion del modelo.");
		}
		return resultado;
	}
	
	private ResultBean accionesCambiarEstadoSinValidacion(Modelo600_601VO modelo600_601BBDD, BigDecimal estadoAnt, BigDecimal estadoNuevo, BigDecimal idUsuario) {
		ResultBean resultado = new ResultBean(false);
		try {
			if(EstadoModelos.Pendiente_Presentacion.getValorEnum().equals(estadoAnt.toString())){
				servicioCola.eliminar(modelo600_601BBDD.getNumExpediente(), ProcesosEnum.MODELO_600_601.toString());
			}else if(EstadoModelos.Anulado.getValorEnum().equals(estadoAnt.toString())){
				if(modelo600_601BBDD.getIdRemesa() != null){
					resultado.setError(true);
					resultado.addMensajeALista("El modelo con n�mero de expediente: " + modelo600_601BBDD.getNumExpediente() + ",no se le puede cambiar de estado porque esta asociado a una remesa en Estado Anulada.");
					return resultado;
				}
			}
			if(EstadoModelos.FinalizadoKO.getValorEnum().equals(estadoNuevo.toString())){
				servicioCola.eliminar(modelo600_601BBDD.getNumExpediente(),ProcesosEnum.MODELO_600_601.toString());
				String tipoTramite = null;
				if(Modelo.Modelo600.getValorEnum().equals(modelo600_601BBDD.getModelo().getId().getModelo())){
					tipoTramite = TipoTramiteModelos.Modelo600.getValorEnum();
				}else if(Modelo.Modelo601.getValorEnum().equals(modelo600_601BBDD.getModelo().getId().getModelo())){
					tipoTramite = TipoTramiteModelos.Modelo601.getValorEnum();
				}
				return servicioCredito.devolverCreditos(tipoTramite,new BigDecimal(modelo600_601BBDD.getContrato().getIdContrato()), BigDecimal.ONE.intValue(), idUsuario, 
						ConceptoCreditoFacturado.DPMD,modelo600_601BBDD.getNumExpediente().toString());
			}else if(EstadoModelos.Autoliquidable.getValorEnum().equals(estadoNuevo.toString())){
				return autoLiquidar(modelo600_601BBDD);
			}else if(EstadoModelos.Pendiente_Presentacion.getValorEnum().equals(estadoNuevo.toString())){
				return presentar(modelo600_601BBDD, null, idUsuario,true, null, null);
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de realizar las acciones relacionadas con el cambio de estado, error: ",e);
			resultado.setError(true);
			resultado.addMensajeALista("Ha sucedido un error a la hora de realizar las acciones relacionadas con el cambio de estado.");
		} catch (OegamExcepcion e) {
			log.error("Ha sucedido un error a la hora de realizar las acciones relacionadas con el cambio de estado, error: ",e);
			resultado.setError(true);
			resultado.addMensajeALista("Ha sucedido un error a la hora de realizar las acciones relacionadas con el cambio de estado.");
		}
		return resultado;
	}

	@Override
	public ResultBean cambiarEstadoModelosRemesa(Long idRemesa, BigDecimal estadoNuevo, BigDecimal idUsuario) {
		ResultBean resultado = new ResultBean(false);
		if(idRemesa != null) {
			List<Modelo600_601Dto> listaModelosRemesas = getListaModelosPorIdRemesa(idRemesa,false);
			if(listaModelosRemesas != null && !listaModelosRemesas.isEmpty()){
				for(Modelo600_601Dto modelo600601Dto : listaModelosRemesas){
					ResultBean resultBean = cambiarEstadoConPosibleEvolucion(true, modelo600601Dto.getNumExpediente(), estadoNuevo, idUsuario, false, null, true, true);
					if(resultBean.getError()){
						resultado.setError(true);
						resultado.addMensajeALista(resultBean.getListaMensajes().get(0));
					}
				}
			}
		}
		return resultado;
	}

	@Override
	public List<Modelo600_601Dto> getListaModelosPorIdRemesa(Long idRemesa,Boolean completo) {
		try {
			List<Modelo600_601VO> listaModelosRemesasVO = getListaModelosPorIdRemesaVO(idRemesa,completo);
			if(listaModelosRemesasVO != null && !listaModelosRemesasVO.isEmpty()){
				return conversor.transform(listaModelosRemesasVO, Modelo600_601Dto.class);
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de obtener los modelos 600/601 por el expediente de la remesa, error: ",e);
		}
		return Collections.emptyList();
	}

	@Override
	@Transactional(readOnly=true)
	public List<Modelo600_601VO> getListaModelosPorIdRemesaVO(Long idRemesa, Boolean completo) {
		List<Modelo600_601VO> listaModelosRemesasVO = null;
		if(idRemesa != null){
			listaModelosRemesasVO = modelo600_601Dao.getListaModelosPorRemesa(idRemesa, completo);
		}
		return listaModelosRemesasVO;
	}

	@Override
	@Transactional
	public ResultBean cambiarEstado(BigDecimal numExpediente, BigDecimal nuevoEstado, BigDecimal idUsuario, Boolean acciones) {
		ResultBean resultado = new ResultBean(false);
		try {
			resultado = cambiarEstadoConPosibleEvolucion(true, numExpediente, nuevoEstado, idUsuario, false, null,acciones,false);
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de cambiar el estado del modelo, error: ",e);
			resultado.setError(true);
			resultado.addMensajeALista("Ha sucedido un error a la hora de cambiar el estado del modelo.");
		}
		if(resultado.getError()){
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		}
		return resultado;
	}

	private ResultBean cambiarEstadoConPosibleEvolucion(boolean evolucion, BigDecimal numExpediente, BigDecimal estadoNuevo, BigDecimal idUsuario, boolean notificacion, String textoNotificacion, Boolean acciones, Boolean vieneDeRemesa) {
		ResultBean resultado = new ResultBean(false);
		if (estadoNuevo == null) {
			resultado.setError(true);
			resultado.addMensajeALista("No se ha indicado el estado para el cambio de estado");
		}else{
			if(numExpediente != null){
				Modelo600_601VO modelo600_601BBDD = modelo600_601Dao.getModeloPorExpediente(numExpediente, false);
				resultado = validarModeloCambioEstado(modelo600_601BBDD, estadoNuevo,vieneDeRemesa);
				if(!resultado.getError()){
					BigDecimal estadoAnt = modelo600_601BBDD.getEstado();
					modelo600_601BBDD.setEstado(estadoNuevo);
					modelo600_601Dao.actualizar(modelo600_601BBDD);
					if(evolucion){
						servicioEvolucionModelo600_601.guardarEvolucionModelo(numExpediente, estadoAnt, estadoNuevo, idUsuario);
					}
					if(notificacion){
						NotificacionDto dto = new NotificacionDto();
						dto.setDescripcion(textoNotificacion);
						if (estadoAnt != null) {
							dto.setEstadoAnt(estadoAnt);
						}
						if (estadoNuevo != null) {
							dto.setEstadoNue(estadoNuevo);
						}
						dto.setIdTramite(modelo600_601BBDD.getNumExpediente());
						if (idUsuario != null) {
							dto.setIdUsuario(idUsuario.longValue());
						}
						if(Modelo.Modelo600.getValorEnum().equals(modelo600_601BBDD.getModelo().getId().getModelo())){
							dto.setTipoTramite(TipoTramiteModelos.Modelo600.getValorEnum());
						}else if (Modelo.Modelo601.getValorEnum().equals(modelo600_601BBDD.getModelo().getId().getModelo())){
							dto.setTipoTramite(TipoTramiteModelos.Modelo601.getValorEnum());
						}
						servicioNotificacion.crearNotificacion(dto);
					}
					if(acciones){
						resultado = accionesCambiarEstadoSinValidacion(modelo600_601BBDD,estadoAnt,estadoNuevo,idUsuario);
					}
					if(!resultado.getError()){
						resultado.addAttachment("tipoModelo",modelo600_601BBDD.getModelo().getId().getModelo());
					}
				}
			}else{
				resultado.setError(true);
				resultado.addMensajeALista("No Existen datos del modelo para poder cambiar el estado");
			}
		}
		return resultado;
	}

	private ResultBean validarModeloCambioEstado(Modelo600_601VO modelo600_601bbdd, BigDecimal estadoNuevo, Boolean vieneDeRemesa) {
		ResultBean resultado = new ResultBean();
		if(modelo600_601bbdd == null){
			resultado.setError(true);
			resultado.addMensajeALista("No existen datos del modelo para cambiar su estado.");
			return resultado;
		}
		if(modelo600_601bbdd.getIdRemesa() != null && !vieneDeRemesa){
			if(EstadoModelos.Anulado.getValorEnum().equals(estadoNuevo.toString())){
				resultado.setError(true);
				resultado.addMensajeALista("No se puede anular un modelo con una remesa asociada sino se anula todo en bloque, dirigase al apartado de remesas y anule dicha remesa completa.");
				return resultado;
			}else if (EstadoModelos.Inicial.getValorEnum().equals(estadoNuevo.toString())) {
				resultado.setError(true);
				resultado.addMensajeALista("No se puede poner en Iniciado un modelo con una remesa asociada.");
				return resultado;
			}else if (EstadoModelos.Validado.getValorEnum().equals(estadoNuevo.toString())) {
				resultado.setError(true);
				resultado.addMensajeALista("No se puede poner en Validado un modelo con una remesa asociada.");
				return resultado;
			}
		}
		return resultado;
	}

	private ResultBean autoLiquidar(Modelo600_601VO modeloBBDDVO) {
		ResultBean resultado = new ResultBean(false);
		try{	
			if(modeloBBDDVO.getBaseImponible() == null || modeloBBDDVO.getBaseImponible().equals(BigDecimal.ZERO)){
				modeloBBDDVO.setBaseImponible(calcularBaseImponible(modeloBBDDVO));
			}
			modeloBBDDVO.setBaseLiquidable(modeloBBDDVO.getBaseImponible());
			if(!modeloBBDDVO.getExento().equals(BigDecimal.ONE) && !modeloBBDDVO.getNoSujeto().equals(BigDecimal.ONE)){
				modeloBBDDVO.setCuota(modeloBBDDVO.getBaseImponible().multiply(modeloBBDDVO.getTipoImpuesto().getMonto()).divide(new BigDecimal(100)));
				if(modeloBBDDVO.getBonificacion()!=null) {
					modeloBBDDVO.setBonificacionCuota(modeloBBDDVO.getCuota().multiply(modeloBBDDVO.getBonificacion().getMonto()).divide(new BigDecimal(100)));
				}
				modeloBBDDVO.setIngresar(modeloBBDDVO.getCuota().subtract(modeloBBDDVO.getBonificacionCuota()));
				modeloBBDDVO.setTotalIngresar(modeloBBDDVO.getIngresar());
			}else{
				modeloBBDDVO.setCuota(BigDecimal.ZERO);
				modeloBBDDVO.setIngresar(BigDecimal.ZERO);
				modeloBBDDVO.setTotalIngresar(modeloBBDDVO.getIngresar());
			}
			// Prueba
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de autoliquidar los modelo,error: ",e);
			resultado = new ResultBean(true, "Ha sucedido un error a la hora de autoliquidar los modelos.");
		}
		return resultado;
	}

	@Override
	public void borrarXmlPresentacionSiexiste(String nombreXml, BigDecimal numExpediente) {
		try {
			if(nombreXml != null && !nombreXml.isEmpty() && numExpediente != null) {
				gestorDocumentos.borraFicheroSiExiste(ConstantesGestorFicheros.MODELO_600_601, ConstantesGestorFicheros.FORMULARIOS_CAM, 
						Utilidades.transformExpedienteFecha(numExpediente), nombreXml);
			}
		} catch (OegamExcepcion e) {
			log.error("Ha sucedido un error a la hora de borrar el xml de presentracion, error: ",e);
		}
	}

	private ResultBean generarXmlPresentacion(Modelo600_601VO modelo600601VO) {
		ResultBean resultado = new ResultBean(false);
		try {
			SolicitudPresentacionModelos solicitudPresentacionModelos = rellenarXmlPresentacion(modelo600601VO);
			if(solicitudPresentacionModelos != null){
				String nombreXml = ServicioModelo600_601.NOMBRE_FICHERO + modelo600601VO.getNumExpediente();
				ResultBean resultValidarXml = validarXmlPresentacion(solicitudPresentacionModelos,modelo600601VO,nombreXml);
				if(!resultValidarXml.getError()){
					ResultBean resultFirma = firmarPresentacionModelos(solicitudPresentacionModelos,modelo600601VO.getContrato().getColegiado());
					if(!resultFirma.getError()){
						gestorDocumentos.guardarFichero(ConstantesGestorFicheros.MODELO_600_601, ConstantesGestorFicheros.FORMULARIOS_CAM, Utilidades.transformExpedienteFecha(modelo600601VO.getNumExpediente()), 
								nombreXml, ConstantesGestorFicheros.EXTENSION_XML, (byte[])resultFirma.getAttachment("xmlFirmado"));
						resultado.addAttachment("nombreXml", nombreXml);
					}else{
						return resultFirma;
					}
				}else{
					return resultValidarXml;
				}
			}else{
				resultado.setError(true);
				resultado.addMensajeALista("Ha sucedido un error a la hora de construir el xml de presentaci�n.");
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de generar el xml con los datos de la presentaci�n, error: ",e);
			resultado.setError(true);
			resultado.addMensajeALista("Ha sucedido un error a la hora de generar el xml con los datos de la presentaci�n");
		} catch (OegamExcepcion e) {
			log.error("Ha sucedido un error a la hora de generar el xml con los datos de la presentaci�n, error: ",e);
			resultado.setError(true);
			resultado.addMensajeALista("Ha sucedido un error a la hora de generar el xml con los datos de la presentaci�n");
		}
		return resultado;
	}

	private ResultBean validarXmlPresentacion(SolicitudPresentacionModelos solicitudPresentacionModelos, Modelo600_601VO modelo600601VO, String nombreXml) {
		ResultBean resultado = new ResultBean(false);
		File fichero = new File(nombreXml);
		try {
			Marshaller marshaller = (Marshaller) getXmlModelo600601Factory().getContext().createMarshaller();
			marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
			marshaller.setProperty(Marshaller.JAXB_ENCODING,ServicioModelo600_601.ENCODING_XML_UTF8);
			marshaller.marshal(solicitudPresentacionModelos, new FileOutputStream(nombreXml));
			// Lo validamos contra el XSD
			try {
				if(Modelo.Modelo600.getValorEnum().equals(modelo600601VO.getModelo().getId().getModelo())){
					new XmlModelo600601Factory().validarXMLModelo600(fichero);
				}else if(Modelo.Modelo601.getValorEnum().equals(modelo600601VO.getModelo().getId().getModelo())){
					new XmlModelo600601Factory().validarXMLModelo601(fichero);
				}
			} catch (OegamExcepcion e) {
				resultado = new ResultBean(true, "Error en la validacion del modelo: " + e.getMensajeError1());
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de validar el xml de presentaci�n generado contra el xsd, error: ",e);
			resultado.setError(true);
			resultado.addMensajeALista("Ha sucedido un error a la hora de validar el xml de presentaci�n generado contra el xsd.");
		}
		if(fichero.exists()){
			fichero.delete();
		}
		return resultado;
	}

	private ResultBean firmarPresentacionModelos(SolicitudPresentacionModelos solicitudPresentacionModelos, ColegiadoVO colegiadoVO) {
		ResultBean resultado = new ResultBean(false);
		try {
			String xmlAFirmar = getXmlModelo600601Factory().toXML(solicitudPresentacionModelos);
			if(xmlAFirmar != null && !xmlAFirmar.isEmpty()){
				String pruebaCaduCert = getUtilesViafirma().firmarPruebaCertificadoCaducidad(xmlAFirmar, colegiadoVO.getAlias());
				if (pruebaCaduCert != null && !pruebaCaduCert.isEmpty()) {
					byte[] xmlFirmado = getUtilesViafirma().firmarXmlModeloPresentacion(xmlAFirmar.getBytes(ServicioModelo600_601.ENCODING_XML_ISO),colegiadoVO.getAlias());
					if(xmlFirmado != null){
						resultado.addAttachment("xmlFirmado", xmlFirmado);
					}else{
						resultado.setError(true);
						resultado.addMensajeALista("Ha sucedido un error a la hora de firmar el xml.");
					}
				}else{
					resultado.setError(true);
					resultado.addMensajeALista("El certificado del colegiado se encuentra caducado, con lo cual la presentaci�n telem�tica no se puede realizar.");
				}
			}else{
				resultado.setError(true);
				resultado.addMensajeALista("Ha sucedido un error a la hora de construir el xml de presentaci�n.");
			}
		} catch (JAXBException e) {
			log.error("Ha sucedido un error a la hora de generar el xml de presentaci�n, error: ",e);
			resultado.setError(true);
			resultado.addMensajeALista("Ha sucedido un error a la hora de construir el xml de presentaci�n.");
		} catch (UnsupportedEncodingException e) {
			log.error("Ha sucedido un error a la hora de realizar el encoding del xml de presentaci�n, error: ",e);
			resultado.setError(true);
			resultado.addMensajeALista("Ha sucedido un error a la hora de realizar el encoding del xml de presentaci�n.");
		}
		return resultado;
	}

	@SuppressWarnings("deprecation")
	private SolicitudPresentacionModelos rellenarXmlPresentacion(Modelo600_601VO modelo600601VO) {
		SolicitudPresentacionModelos solicitudPresentacionModelos = null;
		try {
			solicitudPresentacionModelos = conversor.transform(modelo600601VO, SolicitudPresentacionModelos.class,"solicitudPresentacionModelos");
			solicitudPresentacionModelos.setCodigo(ServicioModelo600_601.CODIGO_REMESA);
			solicitudPresentacionModelos.setFechaEmision(utilesFecha.formatoFecha(Calendar.getInstance().getTime()));
			solicitudPresentacionModelos.setTotalDecl("1");
			solicitudPresentacionModelos.setVersionCodigo("1.6");
			solicitudPresentacionModelos.getDeclaracion().getDetalle().setNumSujetos("1");
			solicitudPresentacionModelos.getDeclaracion().getDetalle().setNumTransmitentes("1");
			String codDeclaracion = utiles.changeSize(modelo600601VO.getIdModelo().toString(), ServicioModelo600_601.TAM_CODIGO_DECLARACION, '0', false);
			if(modelo600601VO.getFechaGeneracion() != null){
				String anio = String.valueOf(modelo600601VO.getFechaGeneracion().getYear() + 1900);
				codDeclaracion = codDeclaracion.replaceFirst("0000", anio);
			}
			solicitudPresentacionModelos.getDeclaracion().setCodigo(codDeclaracion);
			solicitudPresentacionModelos.getDeclaracion().setModelo(modelo600601VO.getModelo().getId().getModelo() + modelo600601VO.getModelo().getId().getVersion());
			solicitudPresentacionModelos.getDeclaracion().setVersion_xsd_modelo("1.2");
			//format fecha devengo
			solicitudPresentacionModelos.getDeclaracion().getDetalle().setFechaDevengo(utilesFecha.formatoFecha(modelo600601VO.getFechaDevengo()));
			rellenarTipoDocumento(solicitudPresentacionModelos,modelo600601VO);
			rellenarLiquidacion(solicitudPresentacionModelos,modelo600601VO);
			List<Interviniente> listaIntervinientes = new ArrayList<Interviniente>();
			for(IntervinienteModelosVO intervinienteModelosVO : modelo600601VO.getListaIntervinientes()){
				listaIntervinientes.add(convertirIntervinienteModelosToInterviniente(intervinienteModelosVO));
			}
			solicitudPresentacionModelos.getDeclaracion().setListaIntervinientes(listaIntervinientes);
			if(modelo600601VO.getListaBienes() != null && !modelo600601VO.getListaBienes().isEmpty()){
				List<BienRustico> listaBienesRusticos = new ArrayList<BienRustico>();
				List<BienUrbano> listaBienesUrbanos = new ArrayList<BienUrbano>();
				List<OtroBien> listaOtrosBienes = new ArrayList<OtroBien>();
				for(ModeloBienVO modeloBienVO : modelo600601VO.getListaBienes()){
					if(modeloBienVO.getBien() instanceof BienRusticoVO){
						BienRustico bienRustico = convertirModeloBienRusticoVoToBienRustico(modeloBienVO); 
						listaBienesRusticos.add(bienRustico);
					}else if(modeloBienVO.getBien() instanceof BienUrbanoVO){
						BienUrbano bienUrbano = convertirModeloBienUrbanoVoToBienUrbano(modeloBienVO);
						listaBienesUrbanos.add(bienUrbano);
					}else{
						OtroBien otroBien = convertirModeloBienOtroVoToOtroBien(modeloBienVO);
						listaOtrosBienes.add(otroBien);
					}
				}
				if(listaBienesRusticos != null && !listaBienesRusticos.isEmpty()){
					solicitudPresentacionModelos.getDeclaracion().setListaBienesRusticos(listaBienesRusticos);
				}
				if(listaBienesUrbanos != null && !listaBienesUrbanos.isEmpty()){
					solicitudPresentacionModelos.getDeclaracion().setListaBienesUrbanos(listaBienesUrbanos);
				}
				if(listaOtrosBienes != null && !listaOtrosBienes.isEmpty()){
					solicitudPresentacionModelos.getDeclaracion().setListaOtrosBienes(listaOtrosBienes);
				}
			}
			solicitudPresentacionModelos.getDeclaracion().getLiquidacion().setNumDocumento("");
			if(solicitudPresentacionModelos.getDeclaracion().getLiquidacion().getNumJustificantePrimeraLiquidacion() == null){
				solicitudPresentacionModelos.getDeclaracion().getLiquidacion().setNumJustificantePrimeraLiquidacion("");
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de crear los objetos del xml del modelo para su presentacion, error: ",e);
			solicitudPresentacionModelos = null;
		}
		return solicitudPresentacionModelos;
	}

	@SuppressWarnings("deprecation")
	private void rellenarLiquidacion(SolicitudPresentacionModelos solicitudPresentacionModelos,	Modelo600_601VO modelo600601VO) {
		solicitudPresentacionModelos.getDeclaracion().getLiquidacion().setPrescrita("N");
		if(BigDecimal.ONE.equals(modelo600601VO.getExento())){
			solicitudPresentacionModelos.getDeclaracion().getLiquidacion().setFundamentoLegalDesc(modelo600601VO.getFundamentoExencion().getDescripcion());
		}else if(BigDecimal.ONE.equals(modelo600601VO.getNoSujeto())){
			solicitudPresentacionModelos.getDeclaracion().getLiquidacion().setFundamentoLegalDesc(modelo600601VO.getFundamentoNoSujeccion());
		}else{
			solicitudPresentacionModelos.getDeclaracion().getLiquidacion().setFundamentoLegalDesc("");
		}
		if(modelo600601VO.getFechaPrimLiquidacion() != null){
			solicitudPresentacionModelos.getDeclaracion().getLiquidacion().setFecha(utilesFecha.formatoFecha(modelo600601VO.getFechaPrimLiquidacion()));
			solicitudPresentacionModelos.getDeclaracion().getLiquidacion().setAnoDocumento(String.valueOf(modelo600601VO.getFechaPrimLiquidacion().getYear() + 1900));
		}else{
			solicitudPresentacionModelos.getDeclaracion().getLiquidacion().setFecha("");
			solicitudPresentacionModelos.getDeclaracion().getLiquidacion().setAnoDocumento("");
		}
		solicitudPresentacionModelos.getDeclaracion().getLiquidacion().setTipoImpuesto("T");
		//TODO Esperando repuesta de la CAM para ver si se manda o no
		solicitudPresentacionModelos.getDeclaracion().getLiquidacion().setReduccion("");
		solicitudPresentacionModelos.getDeclaracion().getLiquidacion().setReduccImporte("0,00");
		if(BigDecimal.ZERO.equals(modelo600601VO.getExento()) && BigDecimal.ZERO.equals(modelo600601VO.getNoSujeto())){
			solicitudPresentacionModelos.getDeclaracion().getLiquidacion().setTipo(utiles.formatoFloat(modelo600601VO.getTipoImpuesto().getMonto().floatValue()));
		}else{
			solicitudPresentacionModelos.getDeclaracion().getLiquidacion().setTipo("");
		}
		solicitudPresentacionModelos.getDeclaracion().getLiquidacion().setAuxiliar1("");
		solicitudPresentacionModelos.getDeclaracion().getLiquidacion().setAuxiliar2("");
		if(modelo600601VO.getBonificacion()==null || StringUtils.isBlank(modelo600601VO.getBonificacion().getBonificacion())) {
			solicitudPresentacionModelos.getDeclaracion().getLiquidacion().setBonificacionCodigo("");
			solicitudPresentacionModelos.getDeclaracion().getLiquidacion().setBonificacionDescripcion("");	
		}else {
			solicitudPresentacionModelos.getDeclaracion().getLiquidacion().setBonificacionCodigo(modelo600601VO.getBonificacion().getBonificacion());
			solicitudPresentacionModelos.getDeclaracion().getLiquidacion().setBonificacionDescripcion(modelo600601VO.getBonificacion().getDescripcion());
		}
	}

	@SuppressWarnings("deprecation")
	private BienUrbano convertirModeloBienUrbanoVoToBienUrbano(ModeloBienVO modeloBienVO) {
		BienUrbano bienUrbano = conversor.transform((BienUrbanoVO)modeloBienVO.getBien(), BienUrbano.class,"solicitudPresentacionModelosBienUrbano");
		bienUrbano.setTransmision(utiles.formatoFloat(modeloBienVO.getTransmision().floatValue()));
		bienUrbano.setValorDeclarado(utiles.formatoFloat(modeloBienVO.getValorDeclarado().floatValue()));
		if(bienUrbano.getProvincia() != null && !bienUrbano.getProvincia().isEmpty()){
			bienUrbano.setProvinciaDesc(servicioDireccionCam.obtenerNombreProvincia(bienUrbano.getProvincia()));
			if(bienUrbano.getMunicipio() != null && !bienUrbano.getMunicipio().isEmpty()){
				bienUrbano.setMunicipio(servicioDireccionCam.getIdMunicipioCam(bienUrbano.getProvincia(), bienUrbano.getMunicipio()));
				bienUrbano.setMunicipioDesc(servicioDireccionCam.obtenerNombreMunicipio(bienUrbano.getProvincia(), bienUrbano.getMunicipio()));
			}
			bienUrbano.setViaDesc(servicioDireccionCam.obtenerNombreTipoVia(bienUrbano.getVia()));
		}
		bienUrbano.setCaracter("");
		if(bienUrbano.getSuperfConstruida() != null && !bienUrbano.getSuperfConstruida().isEmpty()){
			bienUrbano.setSuperfConstruida(utiles.formatoFloat(Float.valueOf(bienUrbano.getSuperfConstruida())));
		}
		BienUrbanoVO bienUrbanoVO = (BienUrbanoVO)modeloBienVO.getBien(); 
		if(bienUrbanoVO.getFechaConstruccion() != null){
			bienUrbano.setAnoConstruccion(String.valueOf(bienUrbanoVO.getFechaConstruccion().getYear() + 1900));
		}else{
			bienUrbano.setAnoConstruccion("");
		}
		return bienUrbano;
	}

	private BienRustico convertirModeloBienRusticoVoToBienRustico(ModeloBienVO modeloBienVO) {
		BienRustico bienRustico = conversor.transform((BienRusticoVO)modeloBienVO.getBien(), BienRustico.class,"solicitudPresentacionModelosBienRustico");
		bienRustico.setTransmision(utiles.formatoFloat(modeloBienVO.getTransmision().floatValue()));
		bienRustico.setValorDeclarado(utiles.formatoFloat(modeloBienVO.getValorDeclarado().floatValue()));
		BienRusticoVO bienRusticoVO = (BienRusticoVO) modeloBienVO.getBien();
		if(TipoUsoRustico.Ganaderia.getValorEnum().equals(bienRusticoVO.getUsoRustico().getTipoUso())){
			bienRustico.setTipoGanaderia(bienRusticoVO.getUsoRustico().getIdUsoRustico().substring(0, 2));
			bienRustico.setTipoGanaderiaDesc(bienRusticoVO.getUsoRustico().getDescUsoRustico());
			bienRustico.setTipoCultivo("");
			bienRustico.setTipoCultivoDesc("");
			bienRustico.setTipoOtros("");
			bienRustico.setTipoOtrosDesc("");
		}else if(TipoUsoRustico.Cultivo.getValorEnum().equals(bienRusticoVO.getUsoRustico().getTipoUso())){
			bienRustico.setTipoCultivo(bienRusticoVO.getUsoRustico().getIdUsoRustico().substring(0, 2));
			bienRustico.setTipoCultivoDesc(bienRusticoVO.getUsoRustico().getDescUsoRustico());
			bienRustico.setTipoOtros("");
			bienRustico.setTipoOtrosDesc("");
			bienRustico.setTipoGanaderia("");
			bienRustico.setTipoGanaderiaDesc("");
		}else if(TipoUsoRustico.Otros.getValorEnum().equals(bienRusticoVO.getUsoRustico().getTipoUso())){
			bienRustico.setTipoOtros(bienRusticoVO.getUsoRustico().getIdUsoRustico().substring(0, 2));
			bienRustico.setTipoOtrosDesc(bienRusticoVO.getUsoRustico().getDescUsoRustico());
			bienRustico.setTipoCultivo("");
			bienRustico.setTipoCultivoDesc("");
			bienRustico.setTipoGanaderia("");
			bienRustico.setTipoGanaderiaDesc("");
		}
		if(bienRustico.getProvincia() != null && !bienRustico.getProvincia().isEmpty()){
			bienRustico.setProvinciaDesc(servicioDireccionCam.obtenerNombreProvincia(bienRustico.getProvincia()));
			if(bienRustico.getMunicipio() != null && !bienRustico.getMunicipio().isEmpty()){
				bienRustico.setMunicipio(servicioDireccionCam.getIdMunicipioCam(bienRustico.getProvincia(), bienRustico.getMunicipio()));
				bienRustico.setMunicipioDesc(servicioDireccionCam.obtenerNombreMunicipio(bienRustico.getProvincia(), bienRustico.getMunicipio()));
			}
		}
		if(bienRustico.getExplotacion() == null){
			bienRustico.setExplotacion("");
		}
		if(bienRustico.getExplotacionDesc() == null){
			bienRustico.setExplotacionDesc("");
		}
		bienRustico.setCaracter("");
		return bienRustico;
	}

	private OtroBien convertirModeloBienOtroVoToOtroBien(ModeloBienVO modeloBienVO) {
		OtroBien otroBien = conversor.transform((BienVO)modeloBienVO.getBien(), OtroBien.class,"solicitudPresentacionModelosOtroBien");
		otroBien.setTransmision(utiles.formatoFloat(modeloBienVO.getTransmision().floatValue()));
		otroBien.setValorDeclarado(utiles.formatoFloat(modeloBienVO.getValorDeclarado().floatValue()));
		if(modeloBienVO.getValorTasacion()!=null) {
			otroBien.setValorTasacion(utiles.formatoFloat(modeloBienVO.getValorTasacion().floatValue()));
		}else {
			otroBien.setValorTasacion(null);
		}
		
		return otroBien;
	}


	private Interviniente convertirIntervinienteModelosToInterviniente(IntervinienteModelosVO intervinienteModelosVO) {
		Interviniente interviniente = conversor.transform(intervinienteModelosVO, Interviniente.class,"solicitudPresentacionModelosInterviniente");
		if (TipoPersona.Fisica.getValorEnum().equals(intervinienteModelosVO.getPersona().getTipoPersona())){
			interviniente.setNombre(intervinienteModelosVO.getPersona().getNombre());
	        interviniente.setApellido1(intervinienteModelosVO.getPersona().getApellido1RazonSocial());
	        interviniente.setApellido2(intervinienteModelosVO.getPersona().getApellido2()==null?"":intervinienteModelosVO.getPersona().getApellido2());
	        interviniente.setNombreRazonsocial("");
	    }else{
	    	interviniente.setNombre("");
	        interviniente.setApellido1("");
	        interviniente.setApellido2("");
	        interviniente.setNombreRazonsocial(intervinienteModelosVO.getPersona().getApellido1RazonSocial());
	    }
		if(interviniente.getProvincia() != null && !interviniente.getProvincia().isEmpty()){
			interviniente.setProvinciaDesc(servicioDireccionCam.obtenerNombreProvincia(interviniente.getProvincia()));
			if(interviniente.getMunicipio() != null && !interviniente.getMunicipio().isEmpty()){
				interviniente.setMunicipio(servicioDireccionCam.getIdMunicipioCam(interviniente.getProvincia(), interviniente.getMunicipio()));
				interviniente.setMunicipioDesc(servicioDireccionCam.obtenerNombreMunicipio(interviniente.getProvincia(), interviniente.getMunicipio()));
			}
		}
		if(interviniente.getSiglas() != null && !interviniente.getSiglas().isEmpty()){
			interviniente.setSiglasDesc(servicioDireccionCam.obtenerNombreTipoVia(interviniente.getSiglas()));
		}
		String telefono = null;
		if(intervinienteModelosVO.getPersona().getTelefonos() != null && !intervinienteModelosVO.getPersona().getTelefonos().isEmpty() 
				&& intervinienteModelosVO.getPersona().getTelefonos().contains(";"))
			telefono = intervinienteModelosVO.getPersona().getTelefonos().substring(0, intervinienteModelosVO.getPersona().getTelefonos().indexOf(';'));
		else
			telefono = intervinienteModelosVO.getPersona().getTelefonos();

		if(telefono != null && !telefono.isEmpty()){
			interviniente.setTelf(telefono);
		}else{
			interviniente.setTelf("");
		}
		interviniente.setParticIgual("S");
		if(TipoInterviniente.Presentador.getValorEnum().equals(intervinienteModelosVO.getTipoInterviniente())){
			interviniente.setGrdoPartic("0,00");
		}else{
			interviniente.setGrdoPartic("100,00");
		}
		interviniente.setMinusvalia("N");
		interviniente.setGrdoMinusvalia("");
		interviniente.setParentesco("");
		interviniente.setParentescoDesc("");
		interviniente.setTituloSucesorio("");
		interviniente.setTituloSucesorioDesc("");
		interviniente.setGrupo("");
		interviniente.setPatrimonio("");
		interviniente.setPatrimonioDesc("");
		return interviniente;
	}

	private void rellenarTipoDocumento(SolicitudPresentacionModelos solicitudPresentacionModelos, Modelo600_601VO modelo600601vo) {
		if(TipoDocumentoModelo600601.Publico.getValorEnum().equals(modelo600601vo.getTipoDocumento())){
			solicitudPresentacionModelos.getDeclaracion().getDetalle().setDocPublico("S");
			solicitudPresentacionModelos.getDeclaracion().getDetalle().setDocAdministrativo("N");
			solicitudPresentacionModelos.getDeclaracion().getDetalle().setDocJudicial("N");
			if(Modelo.Modelo600.getValorEnum().equals(modelo600601vo.getModelo().getId().getModelo())){
				solicitudPresentacionModelos.getDeclaracion().getDetalle().setDocPrivado("N");
			}
		}else if(TipoDocumentoModelo600601.Privado.getValorEnum().equals(modelo600601vo.getTipoDocumento())){
			solicitudPresentacionModelos.getDeclaracion().getDetalle().setDocPublico("N");
			solicitudPresentacionModelos.getDeclaracion().getDetalle().setDocAdministrativo("N");
			solicitudPresentacionModelos.getDeclaracion().getDetalle().setDocJudicial("N");
			solicitudPresentacionModelos.getDeclaracion().getDetalle().setDocPrivado("S");
		}else if(TipoDocumentoModelo600601.Judicial.getValorEnum().equals(modelo600601vo.getTipoDocumento())){
			solicitudPresentacionModelos.getDeclaracion().getDetalle().setDocPublico("N");
			solicitudPresentacionModelos.getDeclaracion().getDetalle().setDocAdministrativo("N");
			solicitudPresentacionModelos.getDeclaracion().getDetalle().setDocJudicial("S");
			if(Modelo.Modelo600.getValorEnum().equals(modelo600601vo.getModelo().getId().getModelo())){
				solicitudPresentacionModelos.getDeclaracion().getDetalle().setDocPrivado("N");
			}
		}else if(TipoDocumentoModelo600601.Administrativo.getValorEnum().equals(modelo600601vo.getTipoDocumento())){
			solicitudPresentacionModelos.getDeclaracion().getDetalle().setDocPublico("N");
			solicitudPresentacionModelos.getDeclaracion().getDetalle().setDocAdministrativo("S");
			solicitudPresentacionModelos.getDeclaracion().getDetalle().setDocJudicial("N");
			if(Modelo.Modelo600.getValorEnum().equals(modelo600601vo.getModelo().getId().getModelo())){
				solicitudPresentacionModelos.getDeclaracion().getDetalle().setDocPrivado("N");
			}
		}

		if(modelo600601vo.getNumProtocolo() != null){
			solicitudPresentacionModelos.getDeclaracion().getDetalle().setNumProtocolo(modelo600601vo.getNumProtocolo().toString());
		}else{
			solicitudPresentacionModelos.getDeclaracion().getDetalle().setNumProtocolo("");
		}
		if(modelo600601vo.getProtocoloBis() != null){
			solicitudPresentacionModelos.getDeclaracion().getDetalle().setNumProtocoloBis(TipoConverterSiNo.convertirTexto(modelo600601vo.getProtocoloBis().toString(),BigDecimal.ONE.intValue()));
		} else {
			solicitudPresentacionModelos.getDeclaracion().getDetalle().setNumProtocoloBis("");
		}
		if(modelo600601vo.getFechaDevengo() != null){
			solicitudPresentacionModelos.getDeclaracion().getDetalle().setFechaDocumento(utilesFecha.formatoFecha(modelo600601vo.getFechaDevengo()));
		} else {
			solicitudPresentacionModelos.getDeclaracion().getDetalle().setFechaDocumento("");
		}
	}

	private ResultBean guardarDatosBancariosPresentacion(DatosBancariosFavoritosDto datosBancariosDto, Modelo600_601VO modelo600601VO, Boolean esCambiarEstadoAccion, BigDecimal idUsuario) {
		ResultBean resultGDatosBancFav = new ResultBean(false);
		if(!esCambiarEstadoAccion){
			if(datosBancariosDto != null){
				datosBancariosDto.setFormaPago(String.valueOf(FormaPago.CCC.getCodigo()));
				if(TipoCuentaBancaria.NUEVA.getValorEnum().equals(datosBancariosDto.getTipoDatoBancario())) {
					if(datosBancariosDto.getEsFavorita() != null && datosBancariosDto.getEsFavorita()){
						DatosBancariosFavoritosVO datosBancariosFavoritosVO = conversor.transform(datosBancariosDto, DatosBancariosFavoritosVO.class);
						datosBancariosFavoritosVO.setIdContrato(modelo600601VO.getContrato().getIdContrato());
						datosBancariosFavoritosVO.setEstado(new BigDecimal(EstadoDatosBancarios.HABILITADO.getValorEnum()));
						datosBancariosFavoritosVO.setDatosBancarios(servicioDatosBancariosFavoritos.cifrarNumCuentaPantalla(datosBancariosDto));
						resultGDatosBancFav = servicioDatosBancariosFavoritos.guardarDatosBancariosFavoritos(datosBancariosFavoritosVO,idUsuario);
						if(!resultGDatosBancFav.getError()){
							modelo600601VO.setIdDatosBancarios(datosBancariosFavoritosVO.getIdDatosBancarios());
							modelo600601VO.setNifPagador(null);
							modelo600601VO.setDatosBancarios(null);
						}
					}else{
						modelo600601VO.setNumCuentaPago(servicioDatosBancariosFavoritos.cifrarNumCuentaPantalla(datosBancariosDto));
						modelo600601VO.setNifPagador(datosBancariosDto.getNifPagador());
						modelo600601VO.setIdDatosBancarios(null);
					}
				}else {
					modelo600601VO.setIdDatosBancarios(datosBancariosDto.getIdDatosBancarios());
				}
			}
		}
		return resultGDatosBancFav;
	}

	@Override
	@Transactional(readOnly=true)
	public ResultBean imprimirDocumento(BigDecimal numExpediente, String idResultadosModelo, String tipoFichero) {
		ResultBean resultado = new ResultBean(false);
		String nombreFichero = null;
		ResultadoModelo600601Dto resultadoModelo600601Dto = null;
		try {
			if(idResultadosModelo != null && !idResultadosModelo.isEmpty() && tipoFichero != null && !tipoFichero.isEmpty()){
				String[] idResultados = idResultadosModelo.split("-");
				if(idResultados.length == 1){
					resultadoModelo600601Dto = servicioResultadoModelo600601.getResultadoModelo600601DtoPorId(Integer.parseInt(idResultados[0]));
					if(resultadoModelo600601Dto != null && resultadoModelo600601Dto.getJustificante() != null && !resultadoModelo600601Dto.getJustificante().isEmpty()){
						nombreFichero = getNombreFicheroModelo(resultadoModelo600601Dto.getModelo600601().getIdModelo(),resultadoModelo600601Dto.getJustificante(),tipoFichero);
						FileResultBean fichero = gestorDocumentos.buscarFicheroPorNombreTipo(ConstantesGestorFicheros.MODELO_600_601, ConstantesGestorFicheros.PDF_MODELOS_600_601, 
								Utilidades.transformExpedienteFecha(numExpediente), nombreFichero, ConstantesGestorFicheros.EXTENSION_PDF);
						if(fichero != null && fichero.getFile() != null){
							resultado.addAttachment("fichero", fichero.getFile());
							resultado.addAttachment("nombreFichero", nombreFichero + ConstantesGestorFicheros.EXTENSION_PDF); 
						}else{
							resultado.setError(true);
							resultado.addMensajeALista("El fichero que desea imprimir no existe.");
						}
					}else{
						resultado.setError(true);
						resultado.addMensajeALista("No se tienen datos para imprimir de ese resultado.");
					}
				}else{
					resultado.setError(true);
					resultado.addMensajeALista("Los documentos solo se pueden imprimir individualmente.");
				}

			}else if(tipoFichero.equalsIgnoreCase("3")){
				nombreFichero = ServicioModelo600_601.NOMBRE_ESCRITURA + numExpediente;
				FileResultBean fichero = gestorDocumentos.buscarFicheroPorNombreTipo(ConstantesGestorFicheros.MODELO_600_601, ConstantesGestorFicheros.FORMULARIOS_CAM, 
						Utilidades.transformExpedienteFecha(numExpediente), nombreFichero, ConstantesGestorFicheros.EXTENSION_PDF);
				if(fichero != null && fichero.getFile() != null){
					resultado.addAttachment("fichero", fichero.getFile());
					resultado.addAttachment("nombreFichero", nombreFichero + ConstantesGestorFicheros.EXTENSION_PDF); 
				}else{
					resultado.setError(true);
					resultado.addMensajeALista("El fichero que desea imprimir no existe.");
				}
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de imprimir el documento, error: ",e);
			resultado.setError(true);
			resultado.addMensajeALista("Ha sucedido un error a la hora de imprimir el documento.");
		} catch (OegamExcepcion e) {
			log.error("Ha sucedido un error a la hora de imprimir el documento, error: ",e);
			resultado.setError(true);
			resultado.addMensajeALista("Ha sucedido un error a la hora de imprimir el documento.");
		}
		return resultado;
	}

	@Override
	public String getNombreFicheroModelo(Long idModelo, String justificante, String tipoFichero) {
		String nombre = null;
		if(TipoFicheros.Carta_Pago.getValorEnum().equals(tipoFichero)){
			nombre =  ServicioWebServiceModelo600601.NOMBRE_FICH_CARTA_PAGO + "_" + idModelo + "_" + justificante;
		}else if(TipoFicheros.Diligencia.getValorEnum().equals(tipoFichero)){
			nombre = ServicioWebServiceModelo600601.NOMBRE_FICH_DILIGENCIA + "_" + idModelo + "_" + justificante;
		}else if(TipoFicheros.Escritura.getValorEnum().equals(tipoFichero)){
			nombre = ServicioWebServiceModelo600601.NOMBRE_FICH_ESCRITURA + "_";
		}
		return nombre;
	}

	@Override
	@Transactional(readOnly=true)
	public ResultBean validarContratosPresentacionEnBloque(String codSeleccionados, BigDecimal idContrato) {
		ResultBean resultado = new ResultBean(false);
		try {
			if(codSeleccionados != null && !codSeleccionados.isEmpty()){
				String[] numExpedientes = codSeleccionados.split("-");
				for(String numExp : numExpedientes){
					Modelo600_601VO modelo600_601vo = modelo600_601Dao.getModeloPorExpediente(new BigDecimal(numExp), false);
					if(modelo600_601vo != null){
						if(modelo600_601vo.getContrato() != null && modelo600_601vo.getContrato().getIdContrato() != null){
							if(idContrato.longValue() != modelo600_601vo.getContrato().getIdContrato()){
								resultado.setError(true);
								resultado.addMensajeALista("Para poder presentar en bloque todos los modelos deben de ser del mismo contrato.");
								break;
							}
						}else{
							resultado.setError(true);
							resultado.addMensajeALista("Para el modelo: " + numExp + " no existen datos de su contrato.");
							break;
						}
					}else{
						resultado.setError(true);
						resultado.addMensajeALista("Para el modelo: " + numExp + " no existen datos.");
						break;
					}
				}
				
			}else{
				resultado.setError(true);
				resultado.addMensajeALista("Debe de seleccionar alg�n modelo para presentarlos.");
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de validar los modelos en bloque para presentarlos, error: ",e);
			resultado.setError(true);
			resultado.addMensajeALista("Ha sucedido un error a la hora de validar los modelos en bloque para presentarlos.");
		}
		return resultado;
	}

	@Override
	public ResultBean guardarEscritura(BigDecimal numExp, File fichero, String fileName) {
		ResultBean resultado = new ResultBean(false);
		String nombreEscritura = null;
		if (fichero != null && !fileName.isEmpty()){
			nombreEscritura = ServicioModelo600_601.NOMBRE_ESCRITURA + numExp;
			if (fichero != null){
				try {
					gestorDocumentos.guardarFichero(ConstantesGestorFicheros.MODELO_600_601, ConstantesGestorFicheros.FORMULARIOS_CAM, Utilidades.transformExpedienteFecha(numExp), 
							nombreEscritura, ConstantesGestorFicheros.EXTENSION_PDF, Files.toByteArray(fichero));
					resultado.addAttachment("nombreEscritura", nombreEscritura + ConstantesGestorFicheros.EXTENSION_PDF);
				} catch (IOException | OegamExcepcion e) {
					log.error("Ha sucedido un error a la hora de guardar la escritura: " + numExp + ",error: ",e);
					resultado.setError(true);
					resultado.addMensajeALista("Ha sucedido un error a la hora de guardar la escritura: " + numExp);
				}
			}
		}
		return resultado;
	}

	@Override
	@Transactional
	public ResultBean presentar(BigDecimal numExp, DatosBancariosFavoritosDto datosBancarios, BigDecimal idUsuario, File fichero, String fileName) {
		ResultBean resultado = new ResultBean(false);
		try {
			Modelo600_601VO modelo600_601BBDD = modelo600_601Dao.getModeloPorExpediente(numExp, true);
			resultado = presentar(modelo600_601BBDD,datosBancarios,idUsuario,false,fichero,fileName);
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de presentar el modelo: " + numExp + ",error: ",e);
			resultado.setError(true);
			resultado.addMensajeALista("Ha sucedido un error a la hora de presentar el modelo: " + numExp);
		} catch (OegamExcepcion e) {
			if(EnumError.error_00001.equals(e.getCodigoError())){
				resultado.addMensajeALista("Ha sucedido un error a la hora de presentar el modelo: " + numExp + ", " + e.getMensajeError1());
			}else if(EnumError.error_00002.equals(e.getCodigoError())){
				resultado.addMensajeALista("Ha sucedido un error a la hora de presentar el modelo: " + numExp + ", " + e.getMensajeError1());
			}else{
				log.error("Ha sucedido un error a la hora de presentar el modelo: " + numExp + ",error: ",e);
				resultado.addMensajeALista("Ha sucedido un error a la hora de presentar el modelo: error a la hora de encolar la solicitud");
			}
			resultado.setError(true);
		}
		if(resultado.getError()){
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		}
		return resultado;
	}

	private ResultBean presentar(Modelo600_601VO modelo600_601BBDD, DatosBancariosFavoritosDto datosBancarios, BigDecimal idUsuario, Boolean esCambiarEstadoAccion, File fichero, String fileName) throws OegamExcepcion, IOException {
		ResultBean resultado = new ResultBean(false);
		String nombreXml = null;
//		String nombreEscritura = null;
		ResultBean resultCola = null;
		ResultBean resultEscritura = guardarEscritura(modelo600_601BBDD.getNumExpediente(), fichero, fileName);
		if(!resultEscritura.getError()){
			ResultBean resultValidar = validarModeloPresentacion(modelo600_601BBDD,false,datosBancarios,esCambiarEstadoAccion);
			if(!resultValidar.getError()){
				ResultBean resultGDatosBancFav = guardarDatosBancariosPresentacion(datosBancarios,modelo600_601BBDD,esCambiarEstadoAccion,idUsuario);
				if(!resultGDatosBancFav.getError()){
					if(fileName != null && !fileName.isEmpty()){
						modelo600_601BBDD.setNombreEscritura((String)resultEscritura.getAttachment("nombreEscritura"));
					}
					BigDecimal estadoAnt = modelo600_601BBDD.getEstado();
					modelo600_601BBDD.setFechaPresentacion(Calendar.getInstance().getTime());
					modelo600_601BBDD.setEstado(new BigDecimal(EstadoModelos.Pendiente_Presentacion.getValorEnum()));
					modelo600_601Dao.actualizar(modelo600_601BBDD);
					servicioEvolucionModelo600_601.guardarEvolucionModelo(modelo600_601BBDD.getNumExpediente(), estadoAnt, 
							modelo600_601BBDD.getEstado(), idUsuario);
	
					String tipoTramite = null;
					if(Modelo.Modelo600.getValorEnum().equals(modelo600_601BBDD.getModelo().getId().getModelo())){
						tipoTramite = TipoTramiteModelos.Modelo600.getValorEnum();
					}else if(Modelo.Modelo601.getValorEnum().equals(modelo600_601BBDD.getModelo().getId().getModelo())){
						tipoTramite = TipoTramiteModelos.Modelo601.getValorEnum();
					}

					ResultBean resultCreditos = tratarCobrarCreditos(modelo600_601BBDD, idUsuario);
					if (resultCreditos == null || !resultCreditos.getError()) {
						ResultBean resultGenXmlPresentacion = generarXmlPresentacion(modelo600_601BBDD);
						if(!resultGenXmlPresentacion.getError()){
							nombreXml = (String)resultGenXmlPresentacion.getAttachment("nombreXml");
							resultCola = servicioCola.crearSolicitud(ProcesosEnum.MODELO_600_601.toString(),nombreXml + " " +modelo600_601BBDD.getNombreEscritura(), 
										gestorPropiedades.valorPropertie(ServicioTramiteTraficoBaja.NOMBRE_HOST_SOLICITUD_PROCESOS3),
										tipoTramite, modelo600_601BBDD.getNumExpediente().toString(), idUsuario, null,new BigDecimal(modelo600_601BBDD.getContrato().getIdContrato()));
							if(resultCola.getError()){
								resultado.setError(true);
								resultado.addMensajeALista("Modelo " + modelo600_601BBDD.getNumExpediente() + " fallo al generar la presentaci�n: " + resultCola.getListaMensajes().get(0));
							}
							resultado.addAttachment("nombreXml", nombreXml);
							resultado.addMensajeALista("Modelo " + modelo600_601BBDD.getNumExpediente() + " presentado correctamente.");
						}else{
							resultado.setError(true);
							resultado.addMensajeALista("Modelo " + modelo600_601BBDD.getNumExpediente() + " fallo al generar la presentaci�n: " + resultGenXmlPresentacion.getListaMensajes().get(0));
						}
					}else{
						resultado.setError(true);
						resultado.addMensajeALista("Modelo " + modelo600_601BBDD.getNumExpediente() + " fallo al generar la presentaci�n: " + resultCreditos.getListaMensajes().get(0));
					}
				}else{
					resultado.setError(true);
					resultado.addMensajeALista("Modelo " + modelo600_601BBDD.getNumExpediente() + " fallo al generar la presentaci�n: " + resultGDatosBancFav.getListaMensajes().get(0));

				}
			}else{
				resultado.setError(true);
				resultado.addMensajeALista("Modelo " + modelo600_601BBDD.getNumExpediente() + " fallo al generar la presentaci�n: " + resultValidar.getListaMensajes().get(0));
			}
		} else { 
			resultado = resultEscritura;
		}
		if(modelo600_601BBDD != null && modelo600_601BBDD.getModelo() != null && modelo600_601BBDD.getModelo().getId() != null){
			resultado.addAttachment("tipoModelo", modelo600_601BBDD.getModelo().getId().getModelo());
		}
		if(resultado.getError()){
			if(nombreXml != null && !nombreXml.isEmpty()){
				borrarXmlPresentacionSiexiste(nombreXml, modelo600_601BBDD.getNumExpediente());
			}
//			if(nombreEscritura != null && !nombreEscritura.isEmpty()){
//				borrarXmlPresentacionSiexiste(nombreEscritura, modelo600_601BBDD.getNumExpediente());
//			}
		}
		return resultado;
	}

	private ResultBean tratarCobrarCreditos(Modelo600_601VO modelo600601VO, BigDecimal idUsuario){
		ResultBean resultado = new ResultBean(false);
		try {
			int creditos = 1;
			if("SI".equalsIgnoreCase(gestorPropiedades.valorPropertie(ServicioWebServiceModelo600601.cobrarCreditos))){
				creditos = servicioCredito.cantidadCreditosAnotados(modelo600601VO.getNumExpediente().toString(), ConceptoCreditoFacturado.PMD, ConceptoCreditoFacturado.DPMD);
			}
			if(creditos < 1){
				String tipoTramite = null;
				if(Modelo.Modelo600.getValorEnum().equals(modelo600601VO.getModelo().getId().getModelo())){
					tipoTramite = TipoTramiteModelos.Modelo600.getValorEnum();
				}else if(Modelo.Modelo601.getValorEnum().equals(modelo600601VO.getModelo().getId().getModelo())){
					tipoTramite = TipoTramiteModelos.Modelo601.getValorEnum();
				}
				resultado = servicioCredito.validarCreditos(tipoTramite, new BigDecimal(modelo600601VO.getIdContrato()), BigDecimal.ONE);
				if (!resultado.getError()) {
					// Descontar creditos
					resultado = servicioCredito.descontarCreditos(tipoTramite, new BigDecimal(modelo600601VO.getIdContrato()), BigDecimal.ONE, idUsuario, ConceptoCreditoFacturado.PMD,
							modelo600601VO.getNumExpediente().toString());
				}
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de cobrar los creditos de la presentaci�n de modelos 600/601, error: ", e);
			resultado.setError(true);
			resultado.addMensajeALista("Ha sucedido un error a la hora de cobrar los creditos.");
		}
		return resultado;
	}

	@Override
	@Transactional
	public ResultBean presentarModeloRemesasBloque(Modelo600_601VO modelo600_601vo, DatosBancariosFavoritosDto datosBancarios, BigDecimal idUsuario) {
		ResultBean resultado = new ResultBean(false);
		try {
			resultado = presentar(modelo600_601vo,datosBancarios,idUsuario,false, null, null);
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de presentar el modelo: " + modelo600_601vo.getNumExpediente() + ",error: ",e);
			resultado.setError(true);
			resultado.addMensajeALista("Ha sucedido un error a la hora de presentar el modelo: " + modelo600_601vo.getNumExpediente());
		} catch (OegamExcepcion e) {
			if(EnumError.error_00001.equals(e.getCodigoError())){
				resultado.addMensajeALista("Ha sucedido un error a la hora de presentar el modelo: " + modelo600_601vo.getNumExpediente() + ", " + e.getMensajeError1());
			}else if(EnumError.error_00002.equals(e.getCodigoError())){
				resultado.addMensajeALista("Ha sucedido un error a la hora de presentar el modelo: " + modelo600_601vo.getNumExpediente() + ", " + e.getMensajeError1());
			}else{
				log.error("Ha sucedido un error a la hora de presentar el modelo: " + modelo600_601vo.getNumExpediente() + ",error: ",e);
				resultado.addMensajeALista("Ha sucedido un error a la hora de presentar el modelo: error a la hora de encolar la solicitud");
			}
			resultado.setError(true);
		}
		if(resultado.getError()){
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		}
		return resultado;
	}

	@Override
	@Transactional(readOnly=true)
	public BigDecimal getEstadoModelo(BigDecimal numExpediente) {
		BigDecimal estado = null;
		try {
			if(numExpediente != null){
				Modelo600_601VO modelo600601BBDD = modelo600_601Dao.getModeloPorExpediente(numExpediente, false);
				if(modelo600601BBDD != null){
					return modelo600601BBDD.getEstado();
				}
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de obtener el estado del modelo, error: ",e);
		}
		return estado;
	}
	
	@Override
	@Transactional(readOnly = true)
	public List<Modelo600_601Dto> getListaModelosPorExpedientesYContrato(String codSeleccionados, Long idContrato) {
		try {
			if(codSeleccionados != null && !codSeleccionados.isEmpty()){
				List<Modelo600_601VO> lista = modelo600_601Dao.getListaModelosPorExpedientesYContrato(utiles.convertirStringArrayToBigDecimal(codSeleccionados.split("-")),idContrato);
				if(lista != null && !lista.isEmpty()){
					return conversor.transform(lista, Modelo600_601Dto.class);
				}
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de obtener la lista de modelos 600/601 por sus expedientes, error: ",e);
		}
		return Collections.emptyList();
	}

	@Override
	@Transactional(readOnly=true)
	public ResultBean validarModeloImpresion(BigDecimal numExpediente) {
		ResultBean resultado = new ResultBean(false);
		try {
			if(numExpediente != null){
				Modelo600_601VO modelo600_601vo = modelo600_601Dao.getModeloPorExpediente(numExpediente, true);
				if(modelo600_601vo != null){
					if(!EstadoModelos.FinalizadoOK.getValorEnum().equals(modelo600_601vo.getEstado().toString())){
						resultado.setError(true);
						resultado.addMensajeALista("El modelo: "+ numExpediente + " se debe de encontrar en estado '" + EstadoModelos.FinalizadoOK.getNombreEnum() + "' para poder realizar su impresi�n.");
						return resultado;
					}
					if(modelo600_601vo.getListaResultadoModelo() != null && !modelo600_601vo.getListaResultadoModelo().isEmpty()){
						Boolean esOk = false;
						for(ResultadoModelo600601VO resultadoModelo600601VO : modelo600_601vo.getListaResultadoModelo()){
							if(ErroresWSModelo600601.Error000.getValorEnum().equals(resultadoModelo600601VO.getCodigoResultado())){
								esOk = true;
								break;
							}
						}
						if(!esOk){
							resultado.setError(true);
							resultado.addMensajeALista("El modelo: "+ numExpediente + " no tiene resultados correctos para poder realizar la impresi�n de sus documentos.");
						}
					}else{
						resultado.setError(true);
						resultado.addMensajeALista("El modelo: "+ numExpediente + " no tiene documentos asociados para su impresi�n.");
					}
				}else{
					resultado.setError(true);
					resultado.addMensajeALista("No existen datos para el modelo: "+ numExpediente + ".");
				}
			}else{
				resultado.setError(true);
				resultado.addMensajeALista("Debe indicar un numero de expediente valido para comprobar.");
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de validar el modelo 600/601 para su impresi�n, error: ",e);
			resultado.setError(true);
			resultado.addMensajeALista("Ha sucedido un error a la hora de validar el modelo 600/601 para su impresi�n.");
		}
		return resultado;
	}

	@Override
	@Transactional
	public ResultBean eliminarModelosRemesa(Long idRemesa) {
		ResultBean resultado = new ResultBean(false);
		try {
			if(idRemesa != null){
				List<Modelo600_601VO> listaModelos = getListaModelosPorIdRemesaVO(idRemesa, true);
				if(listaModelos != null && !listaModelos.isEmpty()){
					for(Modelo600_601VO modelo600_601VO : listaModelos){
						if(modelo600_601VO.getListaResultadoModelo() != null && !modelo600_601VO.getListaResultadoModelo().isEmpty()){
							ResultBean resultModeloBean = servicioResultadoModelo600601.eliminarResuladosModelos(modelo600_601VO.getListaResultadoModelo(),null);
							if(resultModeloBean.getError()){
								resultado.setError(true);
								resultado.addMensajeALista(resultModeloBean.getListaMensajes().get(0));
								break;
							}
						}
						ResultBean resultIntervBean = servicioIntervinienteModelos.eliminarIntervientesModelos(modelo600_601VO.getListaIntervinientes());
						if(resultIntervBean.getError()){
							resultado.setError(true);
							resultado.addMensajeALista(resultIntervBean.getListaMensajes().get(0));
							break;
						}
						ResultBean resultModeloBienBean = servicioBien.eliminarListaModeloBien(modelo600_601VO.getListaBienes());
						if(resultModeloBienBean.getError()){
							resultado.setError(true);
							resultado.addMensajeALista(resultModeloBienBean.getListaMensajes().get(0));
							break;
						}

						ResultBean resultEvolMod = servicioEvolucionModelo600_601.eliminarEvolucionModelo(modelo600_601VO.getNumExpediente());
						if(resultEvolMod.getError()){
							resultado.setError(true);
							resultado.addMensajeALista(resultEvolMod.getListaMensajes().get(0));
							break;
						}
						modelo600_601Dao.borrar(modelo600_601VO);
					}
				}
			}else{
				resultado.setError(true);
				resultado.addMensajeALista("No existen datos de la remesa para poder eliminar sus modelos asociados.");
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de eliminar los modelos de la remesa, error: ",e);
			resultado.setError(true);
			resultado.addMensajeALista("Ha sucedido un error a la hora de eliminar los modelos de la remesa.");
		}
		return resultado;
	}

	@Override
	@Transactional
	public ResultBean anularModelo(BigDecimal numExpediente, BigDecimal idUsuario) {
		ResultBean resultado = new ResultBean(false);
		try {
			Modelo600_601VO modelo600_601vo = getModeloVOPorNumExpediente(numExpediente, false);
			if(modelo600_601vo != null){
				
			}else{
				resultado.setError(true);
				resultado.addMensajeALista("Para el expediente: " + numExpediente + " no existen datos para poder eliminarlo.");
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de eliminar el tramite, error:",e);
			resultado.setError(true);
			resultado.addMensajeALista("Ha sucedido un error a la hora de eliminar el tramite.");
		}
		return resultado;
	}

	public XmlModelo600601Factory getXmlModelo600601Factory() {
		if(xmlModelo600601Factory == null){
			xmlModelo600601Factory = new XmlModelo600601Factory();
		}
		return xmlModelo600601Factory;
	}

	public void setXmlModelo600601Factory(XmlModelo600601Factory xmlModelo600601Factory) {
		this.xmlModelo600601Factory = xmlModelo600601Factory;
	}

	public UtilesViafirma getUtilesViafirma() {
		if(utilesViafirma == null){
			utilesViafirma = new UtilesViafirma();
		}
		return utilesViafirma;
	}

	public void setUtilesViafirma(UtilesViafirma utilesViafirma) {
		this.utilesViafirma = utilesViafirma;
	}

	/**
	 * {@inheritDoc}
	 */
	@Transactional
	public ResultBean importarModelo600(File fichero, String idContrato, BigDecimal idUsuario) {
		ResultBean resultado = new ResultBean();
		ContratoDto contrato = servicioContrato.getContratoDto(new BigDecimal(idContrato));
		String numColegiado = contrato.getColegiadoDto().getNumColegiado();
		try {
			//ValidadorXml.validarXML(RUTA_XSD_MODELO_600, fichero);
			JAXBContext jc = JAXBContext.newInstance(Remesa.class);
			Unmarshaller unmarshaller = jc.createUnmarshaller();
			Remesa remesa = (Remesa) unmarshaller.unmarshal(fichero);
			for(org.gestoresmadrid.oegam2comun.modelo600.xml.Remesa.Declaracion modelo : remesa.getDeclaracion()){
				for(IntervinienteA2Type elemento : modelo.getListaIntervinientes().getInterviniente()){
					if(elemento.getTipo()==TipoIntervinienteType.PRESENTADOR){
						if(!contrato.getColegiadoDto().getUsuario().getNif().equalsIgnoreCase(elemento.getNif())){
							resultado.setError(true);
							resultado.getListaMensajes().add("No puede importar un modelo con un presentador distinto del que le corresponde");
						}
					}
				}
				if(!resultado.getError()){
					//ResultBean resultadoGuardar = guardarModelo(conversor.transform(modelo, Modelo600_601Dto.class), numColegiado, idUsuario, null, null);
					ResultBean resultadoGuardar = guardarModelo(pasarModelo600aModeloDto(modelo,contrato), numColegiado, idUsuario, null, null,false);
					if(resultadoGuardar.getError()){
						resultado.setError(true);
						resultado.setListaMensajes(resultadoGuardar.getListaMensajes());
					}
				}
			}
		}
		catch (JAXBException e) {
			log.error("Error validaci�n del fichero, error:" + e.getMessage());
			resultado.setError(true);
			resultado.addMensajeALista(ERROR_VALIDAR_FICHERO);
		}
		return resultado;
	}

	private Modelo600_601Dto pasarModelo600aModeloDto(org.gestoresmadrid.oegam2comun.modelo600.xml.Remesa.Declaracion modelo, ContratoDto contrato) {
		Modelo600_601Dto modeloDto = new Modelo600_601Dto();
		modeloDto.setBaseImponible(modelo.getLiquidacion().getBaseImponible());
		modeloDto.setBaseLiquidable(modelo.getLiquidacion().getBaseLiquidable());
		List<ModeloBienDto> bienesRusticos = new ArrayList<ModeloBienDto>();
		ListaBienesRusticos listaBienesRusticos = modelo.getListaBienesRusticos();
		if(listaBienesRusticos!=null){
			for(BienRusticoTypeO elemento : listaBienesRusticos.getBienRustico()){
				ModeloBienDto modeloBienDto = new ModeloBienDto();
				BienDto bienRustico = new BienDto();
				DireccionDto direccion = new DireccionDto();
				direccion.setCodPostal(elemento.getCodigoPostal());
				direccion.setIdProvincia(elemento.getProvincia());
				direccion.setIdMunicipio(elemento.getMunicipio());
				direccion.setMunicipio(elemento.getMunicipioDesc());
				direccion.setNombreMunicipio(elemento.getMunicipioDesc());
				direccion.setNombreProvincia(elemento.getProvincia());
				bienRustico.setDireccion(direccion);
				bienRustico.setParaje(elemento.getParaje());
				bienRustico.setParcela(elemento.getParcela());
				bienRustico.setPoligono(elemento.getPoligono());
				bienRustico.setRefCatrastal(elemento.getReferenciaCatastral());
				SistemaExplotacionDto sistemaExplotacion = new SistemaExplotacionDto();
				sistemaExplotacion.setIdSistemaExplotacion(elemento.getExplotacion());
				sistemaExplotacion.setDescSistema(elemento.getExplotacionDesc());
				bienRustico.setSistemaExplotacion(sistemaExplotacion);
				SituacionDto situacion = new SituacionDto();
				bienRustico.setSituacion(situacion);
				bienRustico.setSubParcela(elemento.getParcela());
				bienRustico.setSuperficie(new BigDecimal(elemento.getSuperficie().isEmpty()?"0":elemento.getSuperficie().replace(",", ".")));
				bienRustico.setSuperficieConst(new BigDecimal(elemento.getSuperficie().isEmpty()?"0":elemento.getSuperficie().replace(",", ".")));
				bienRustico.setTipoBien(elemento.getTipo());
				TipoInmuebleDto tipoInmueble = new TipoInmuebleDto();
				tipoInmueble.setIdTipoInmueble(elemento.getTipo());
				tipoInmueble.setDescTipoInmueble(elemento.getTipoDesc());
				bienRustico.setTipoInmueble(tipoInmueble);
				bienRustico.setTransmision(new BigDecimal(elemento.getTransmision().isEmpty()?"0":elemento.getTransmision().replace(",", ".")));
				UnidadMetricaDto unidadMetrica = new UnidadMetricaDto();
				unidadMetrica.setUnidadMetrica(elemento.getUnidadSuperficie().value());
				unidadMetrica.setDescunidad(elemento.getUnidadSuperficieDesc().value());
				bienRustico.setUnidadMetrica(unidadMetrica);
				UsoRusticoDto usoRustico = new UsoRusticoDto();
				usoRustico.setDescUsoRustico(elemento.getTipoCultivoDesc());
				usoRustico.setIdUsoRustico(elemento.getTipoCultivo());
				usoRustico.setTipoUso(elemento.getTipoCultivo());
				bienRustico.setUsoRustico(usoRustico);
				bienRustico.setValorDeclarado(new BigDecimal(elemento.getValorDeclarado().isEmpty()?"0":elemento.getValorDeclarado().replace(",", ".")));
				modeloBienDto.setBien(bienRustico);
				modeloBienDto.setTransmision(new BigDecimal(elemento.getTransmision().isEmpty()?"0":elemento.getTransmision().replace(",", ".")));
				modeloBienDto.setValorDeclarado(new BigDecimal(elemento.getValorDeclarado().isEmpty()?"0":elemento.getValorDeclarado().replace(",", ".")));
				bienesRusticos.add(modeloBienDto);
			}
		}
		modeloDto.setListaBienesRusticos(bienesRusticos);
		List<ModeloBienDto> bienesUrbanos = new ArrayList<ModeloBienDto>();
		ListaBienesUrbanos listaBienesUrbanos = modelo.getListaBienesUrbanos();
		if(listaBienesUrbanos!=null){
			for(BienUrbanoTypeO elemento : listaBienesUrbanos.getBienUrbano()){
				ModeloBienDto modeloBienDto = new ModeloBienDto();
				BienDto bienUrbano = new BienDto();
				bienUrbano.setAnioContratacion(new BigDecimal(elemento.getAnoContrato().isEmpty()?"0":elemento.getAnoContrato().replace(",", ".")));
				bienUrbano.setArrendamiento("S".equalsIgnoreCase(elemento.getArrendamiento()));
				bienUrbano.setDescalificado("S".equalsIgnoreCase(elemento.getDescalificado()));
				DireccionDto direccion = new DireccionDto();
				direccion.setCodPostal(elemento.getCodigoPostal());
				direccion.setCodPostalCorreos(elemento.getCodigoPostal());
				direccion.setEscalera(elemento.getEscalera());
				direccion.setIdProvincia(elemento.getProvincia());
				direccion.setIdMunicipio(elemento.getMunicipio());
				direccion.setMunicipio(elemento.getMunicipioDesc());
				direccion.setNombreMunicipio(elemento.getMunicipioDesc());
				direccion.setNombreProvincia(elemento.getProvincia());
				direccion.setNombreVia(elemento.getNombreVia());
				direccion.setNumero(elemento.getNumero());
				direccion.setNumLocal(new BigDecimal(elemento.getNumLocal().isEmpty()?"0":elemento.getNumLocal().replace(",", ".")));
				direccion.setPlanta(elemento.getPlanta());
				direccion.setPuerta(elemento.getPuerta());
				direccion.setTipoViaDescripcion(elemento.getViaDesc());
				direccion.setVia(elemento.getVia());
				direccion.setIdTipoVia(elemento.getVia());
				bienUrbano.setDireccion(direccion);
				bienUrbano.setDupTri(elemento.getDupTrip());
				bienUrbano.setFechaConstruccion(new Fecha());
				bienUrbano.getFechaConstruccion().setAnio(elemento.getAnoConstruccion());
				bienUrbano.setPrecioMaximo(new BigDecimal(elemento.getPrecioMaximoVenta().isEmpty()?"0":elemento.getPrecioMaximoVenta().replace(",", ".")));
				bienUrbano.setRefCatrastal(elemento.getReferenciaCatastral());
				SistemaExplotacionDto sistemaExplotacion = new SistemaExplotacionDto();
				bienUrbano.setSistemaExplotacion(sistemaExplotacion);
				SituacionDto situacion = new SituacionDto();
				situacion.setIdSituacion(elemento.getSituacion());
				situacion.setDescSituacion(elemento.getSituacionDesc());
				bienUrbano.setSituacion(situacion);
				bienUrbano.setSuperficie(new BigDecimal(elemento.getSuperficieConstruida().isEmpty()?"0":elemento.getSuperficieConstruida().replace(",", ".")));
				bienUrbano.setSuperficieConst(new BigDecimal(elemento.getSuperficieConstruida().isEmpty()?"0":elemento.getSuperficieConstruida().replace(",", ".")));
				bienUrbano.setTipoBien(elemento.getTipo());
				TipoInmuebleDto tipoInmueble = new TipoInmuebleDto();
				tipoInmueble.setIdTipoInmueble(elemento.getTipo());
				tipoInmueble.setDescTipoInmueble(elemento.getTipoDesc());
				bienUrbano.setTipoInmueble(tipoInmueble);
				bienUrbano.setTransmision(new BigDecimal(elemento.getTransmision().isEmpty()?"0":elemento.getTransmision().replace(",", ".")));
				UnidadMetricaDto unidadMetrica = new UnidadMetricaDto();
				bienUrbano.setUnidadMetrica(unidadMetrica);
				UsoRusticoDto usoRustico = new UsoRusticoDto();
				bienUrbano.setUsoRustico(usoRustico);
				bienUrbano.setValorDeclarado(new BigDecimal(elemento.getValorDeclarado().isEmpty()?"0":elemento.getValorDeclarado().replace(",", ".")));
				bienUrbano.setViviendaProtOficial("S".equalsIgnoreCase(elemento.getVPO()));
				//TODO Todavia no se contempla descomentar cuando se contemple
				//bienUrbano.setViviendaHabitual("S".equalsIgnoreCase(elemento.getViviendaHabitual()));
				modeloBienDto.setBien(bienUrbano);
				modeloBienDto.setTransmision(new BigDecimal(elemento.getTransmision().isEmpty()?"0":elemento.getTransmision().replace(",", ".")));
				modeloBienDto.setValorDeclarado(new BigDecimal(elemento.getValorDeclarado().isEmpty()?"0":elemento.getValorDeclarado().replace(",", ".")));
				bienesUrbanos.add(modeloBienDto);
			}
		}
		modeloDto.setListaBienesUrbanos(bienesUrbanos);
		if(modeloDto.getListaBienesRusticos().size()>0){
			modeloDto.setTipoBien("RUSTICO");
			modeloDto.setBienRustico(modeloDto.getListaBienesRusticos().get(modeloDto.getListaBienesRusticos().size()-1).getBien());
		}
		if(modeloDto.getListaBienesUrbanos().size()>0){
			modeloDto.setTipoBien("URBANO");
			modeloDto.setBienUrbano(modeloDto.getListaBienesUrbanos().get(modeloDto.getListaBienesUrbanos().size()-1).getBien());
		}
		BonificacionDto bonificacion = new BonificacionDto();
		bonificacion.setBonificacion(modelo.getLiquidacion().getBonificacionCuotaImporte());
		if(utiles.stringToBigDecimal(bonificacion.getBonificacion()).floatValue()!=0){
			modeloDto.setBonificacion(bonificacion);
		}
		modeloDto.setBonificacionCuota(modelo.getLiquidacion().getBonificacionCuota());
		ConceptoDto concepto = new ConceptoDto();
		concepto.setConcepto(modelo.getDetalle().getExpAbreviada().value());
		concepto.setDescConcepto(modelo.getDetalle().getConcepto());
		modeloDto.setConcepto(concepto);
		modeloDto.setContrato(contrato);
		modeloDto.setCuota(modelo.getLiquidacion().getCuota());
		modeloDto.setEstado("1");
		modeloDto.setExento("S".equalsIgnoreCase(modelo.getLiquidacion().getExento()));
		modeloDto.setFechaAlta(new Fecha(modelo.getDetalle().getFechaDocumento()));
		modeloDto.setFechaDevengo(new Fecha(modelo.getDetalle().getFechaDevengo()));
		modeloDto.setFechaPrimLiquidacion(new Fecha(modelo.getLiquidacion().getFecha()));
		FundamentoExencionDto fundamentoExencion = new FundamentoExencionDto();
		fundamentoExencion.setFundamentoExcencion(modelo.getLiquidacion().getFundamentoLegal());
		fundamentoExencion.setDescripcion(modelo.getLiquidacion().getFundamentoLegalDesc());
		modeloDto.setFundamentoExencion(fundamentoExencion);
		modeloDto.setFundamentoNoSujeccion(modelo.getLiquidacion().getNoSujeto());
		modeloDto.setLiquidacionComplementaria("S".equalsIgnoreCase(modelo.getLiquidacion().getLiquidacionComplementaria()));
		ModeloDto model = getModelo(Modelo.convertirTexto("600"));
		modeloDto.setModelo(model);
		modeloDto.setNoSujeto("S".equalsIgnoreCase(modelo.getLiquidacion().getNoSujeto()));
		NotarioDto notario = new NotarioDto();
		notario.setApellidos(modelo.getDetalle().getApellidoNotario());
		notario.setCodigoNotaria(modelo.getDetalle().getCodNotaria());
		notario.setCodigoNotario(modelo.getDetalle().getCodNotario());
		notario.setNombre(modelo.getDetalle().getNombreNotario());
		modeloDto.setNotario(notario);
		modeloDto.setNumColegiado(contrato.getColegiadoDto().getNumColegiado());
		// Si es nulo se genera al guardar
		modeloDto.setNumExpediente(null);
		//modeloDto.setNumJustiPrimeraAutolq(modelo.getLiquidacion().getNumJustificantePrimeraLiquidacion());
		modeloDto.setNumProtocolo(new BigDecimal(modelo.getDetalle().getNumProtocolo().isEmpty()?"0":modelo.getDetalle().getNumProtocolo().replace(",", ".")));
		OficinaLiquidadoraDto oficinaLiquidadora = new OficinaLiquidadoraDto();
		oficinaLiquidadora.setId(modelo.getDetalle().getOficinaLiquidadora());
		//TODO Ver si se puede calcuar de otra forma en vez de meterlo a pelo
		oficinaLiquidadora.setIdProvincia("28");
		oficinaLiquidadora.setNombreOficinaLiq(modelo.getDetalle().getOficinaLiquidadoraDesc().value());
		modeloDto.setOficinaLiquidadora(oficinaLiquidadora);
		modeloDto.setPrescrito("S".equalsIgnoreCase(modelo.getLiquidacion().getPrescrita()));
		ListaIntervinientes listaIntervinientes = modelo.getListaIntervinientes();
		modeloDto.setPresentador(new IntervinienteModelosDto());
		modeloDto.setSujetoPasivo(new IntervinienteModelosDto());
		modeloDto.setTransmitente(new IntervinienteModelosDto());
		if(listaIntervinientes != null){
			for(IntervinienteA2Type elemento : listaIntervinientes.getInterviniente()){
				if(!elemento.getTipo().value().equalsIgnoreCase("Presentador")){
					IntervinienteModelosDto interviniente = new IntervinienteModelosDto();
					DireccionDto direccion = new DireccionDto();
					direccion.setCodPostal(elemento.getCp());
					direccion.setEscalera(elemento.getEscalera());
					direccion.setIdMunicipio(elemento.getMunicipio());
					direccion.setIdProvincia(elemento.getProvincia());
					direccion.setIdTipoVia(elemento.getSiglas());
					direccion.setNombreMunicipio(elemento.getMunicipioDesc());
					direccion.setNombreProvincia(elemento.getProvinciaDesc().value());
					direccion.setNombreVia(elemento.getNombreVia());
					direccion.setNumero(elemento.getNumero());
					direccion.setPuerta(elemento.getPuerta());
					direccion.setPlanta(elemento.getPiso());
					direccion.setTipoViaDescripcion(elemento.getSiglasDesc());
					interviniente.setDireccion(direccion);
					PersonaDto persona = new PersonaDto();
					persona.setApellido1RazonSocial(elemento.getNombreRazonsocial());
					if(elemento.getNombreRazonsocial().trim().isEmpty()){
						persona.setApellido1RazonSocial(elemento.getApellido1());
					}
					persona.setApellido2(elemento.getApellido2());
					persona.setNif(elemento.getNif());
					persona.setNombre(elemento.getNombre());
					persona.setTelefonos(elemento.getTelf());
					persona.setFechaNacimiento(new Fecha(elemento.getFecNacimiento()));
					interviniente.setPersona(persona);
					interviniente.setPorcentaje(new BigDecimal(elemento.getGrdoPartic().isEmpty()?"0":elemento.getGrdoPartic().replace(",", ".")));
					interviniente.setTipoInterviniente(elemento.getTipo().value());
					if(interviniente.getTipoInterviniente().equalsIgnoreCase("Sujeto Pasivo")){
						modeloDto.setSujetoPasivo(interviniente);
					}else if(interviniente.getTipoInterviniente().equalsIgnoreCase("Transmitente")){
						modeloDto.setTransmitente(interviniente);
					}
				}
			}
		}
		modeloDto.setProtocoloBis("S".equalsIgnoreCase(modelo.getDetalle().getNumProtocoloBis()));
		String sTipoDoc = "";
		if("S".equalsIgnoreCase(modelo.getDetalle().getDocPublico())){
			sTipoDoc = "PUB";
		}else if("S".equalsIgnoreCase(modelo.getDetalle().getDocPrivado())){
			sTipoDoc = "PRI";
		}else if("S".equalsIgnoreCase(modelo.getDetalle().getDocJudicial())){
			sTipoDoc = "JUD";
		}else if("S".equalsIgnoreCase(modelo.getDetalle().getDocAdministrativo())){
			sTipoDoc = "ADM";
		}
		modeloDto.setPresentador(getPresentadorContrato(contrato));
		modeloDto.setTipoDocumento(sTipoDoc);
		TipoImpuestoDto tipoImpuesto = new TipoImpuestoDto();
		concepto = new ConceptoDto();
		concepto.setConcepto(modelo.getDetalle().getExpAbreviada().value());
		concepto.setDescConcepto(modelo.getDetalle().getConcepto());
		tipoImpuesto.setConcepto(concepto);
		tipoImpuesto.setFundamentoExencion(modelo.getLiquidacion().getFundamentoLegal());
		tipoImpuesto.setTipoImpuesto(modelo.getLiquidacion().getTipoImpuesto());
		modeloDto.setTipoImpuesto(tipoImpuesto);
		modeloDto.setTotalIngresar(modelo.getLiquidacion().getTotalIngresar().replace(".", "").replace(",", "."));
		modeloDto.setValorDeclarado(utiles.stringToBigDecimal(modelo.getLiquidacion().getValorDeclarado()));
		return modeloDto;
	}

	/**
	 * {@inheritDoc}
	 */
	@Transactional
	public ResultBean importarModelo601(File fichero, String idContrato, BigDecimal idUsuario) {
		ResultBean resultado = new ResultBean();
		ContratoDto contrato = servicioContrato.getContratoDto(new BigDecimal(idContrato));
		String numColegiado = contrato.getColegiadoDto().getNumColegiado();
		try {
			//ValidadorXml.validarXML(RUTA_XSD_MODELO_600, fichero);
			JAXBContext jc = JAXBContext.newInstance(org.gestoresmadrid.oegam2comun.modelo601.xml.Remesa.class);
			Unmarshaller unmarshaller = jc.createUnmarshaller();
			org.gestoresmadrid.oegam2comun.modelo601.xml.Remesa remesa = (org.gestoresmadrid.oegam2comun.modelo601.xml.Remesa) unmarshaller.unmarshal(fichero);
			for(org.gestoresmadrid.oegam2comun.modelo601.xml.Remesa.Declaracion modelo : remesa.getDeclaracion()){
				for(org.gestoresmadrid.oegam2comun.modelo601.xml.IntervinienteA2Type elemento : modelo.getListaIntervinientes().getInterviniente()){
					if(elemento.getTipo()==org.gestoresmadrid.oegam2comun.modelo601.xml.TipoIntervinienteType.PRESENTADOR){
						if(!contrato.getColegiadoDto().getUsuario().getNif().equalsIgnoreCase(elemento.getNif())){
							resultado.setError(true);
							resultado.getListaMensajes().add("No puede importar un modelo con un presentador distinto del que le corresponde");
						}
					}
				}
				if(!resultado.getError()){
					//ResultBean resultadoGuardar = guardarModelo(conversor.transform(modelo, Modelo600_601Dto.class), numColegiado, idUsuario, null, null);
					ResultBean resultadoGuardar = guardarModelo(pasarModelo601aModeloDto(modelo,contrato), numColegiado, idUsuario, null, null,false);
					if(resultadoGuardar.getError()){
						resultado.setError(true);
						resultado.setListaMensajes(resultadoGuardar.getListaMensajes());
					}
				}
			}
		}
		catch (JAXBException e) {
			log.error("Error validaci�n del fichero, error:" + e.getMessage());
			resultado.setError(true);
			resultado.addMensajeALista(ERROR_VALIDAR_FICHERO);
		}
		return resultado;
	}

	private Modelo600_601Dto pasarModelo601aModeloDto(Declaracion modelo, ContratoDto contrato) {
		Modelo600_601Dto modeloDto = new Modelo600_601Dto();
		modeloDto.setBaseImponible(modelo.getLiquidacion().getBaseImponible());
		modeloDto.setBaseLiquidable(modelo.getLiquidacion().getBaseLiquidable());
		List<ModeloBienDto> bienesRusticos = new ArrayList<ModeloBienDto>();
		org.gestoresmadrid.oegam2comun.modelo601.xml.Remesa.Declaracion.ListaBienesRusticos listaBienesRusticos = modelo.getListaBienesRusticos();
		if(listaBienesRusticos!=null){
			for(org.gestoresmadrid.oegam2comun.modelo601.xml.BienRusticoTypeO elemento : listaBienesRusticos.getBienRustico()){
				ModeloBienDto modeloBienDto = new ModeloBienDto();
				BienDto bienRustico = new BienDto();
				DireccionDto direccion = new DireccionDto();
				direccion.setCodPostal(elemento.getCodigoPostal());
				direccion.setIdProvincia(elemento.getProvincia());
				direccion.setIdMunicipio(elemento.getMunicipio());
				direccion.setMunicipio(elemento.getMunicipioDesc());
				direccion.setNombreMunicipio(elemento.getMunicipioDesc());
				direccion.setNombreProvincia(elemento.getProvincia());
				bienRustico.setDireccion(direccion);
				bienRustico.setParaje(elemento.getParaje());
				bienRustico.setParcela(elemento.getParcela());
				bienRustico.setPoligono(elemento.getPoligono());
				bienRustico.setRefCatrastal(elemento.getReferenciaCatastral());
				SistemaExplotacionDto sistemaExplotacion = new SistemaExplotacionDto();
				sistemaExplotacion.setIdSistemaExplotacion(elemento.getExplotacion());
				sistemaExplotacion.setDescSistema(elemento.getExplotacionDesc());
				bienRustico.setSistemaExplotacion(sistemaExplotacion);
				SituacionDto situacion = new SituacionDto();
				bienRustico.setSituacion(situacion);
				bienRustico.setSubParcela(elemento.getParcela());
				bienRustico.setSuperficie(new BigDecimal(elemento.getSuperficie().isEmpty()?"0":elemento.getSuperficie().replace(",", ".")));
				bienRustico.setSuperficieConst(new BigDecimal(elemento.getSuperficie().isEmpty()?"0":elemento.getSuperficie().replace(",", ".")));
				bienRustico.setTipoBien(elemento.getTipo());
				TipoInmuebleDto tipoInmueble = new TipoInmuebleDto();
				tipoInmueble.setIdTipoInmueble(elemento.getTipo());
				tipoInmueble.setDescTipoInmueble(elemento.getTipoDesc());
				bienRustico.setTipoInmueble(tipoInmueble);
				bienRustico.setTransmision(new BigDecimal(elemento.getTransmision().isEmpty()?"0":elemento.getTransmision().replace(",", ".")));
				UnidadMetricaDto unidadMetrica = new UnidadMetricaDto();
				unidadMetrica.setUnidadMetrica(elemento.getUnidadSuperficie().value());
				unidadMetrica.setDescunidad(elemento.getUnidadSuperficieDesc().value());
				bienRustico.setUnidadMetrica(unidadMetrica);
				UsoRusticoDto usoRustico = new UsoRusticoDto();
				usoRustico.setDescUsoRustico(elemento.getTipoCultivoDesc());
				usoRustico.setIdUsoRustico(elemento.getTipoCultivo());
				usoRustico.setTipoUso(elemento.getTipoCultivo());
				bienRustico.setUsoRustico(usoRustico);
				bienRustico.setValorDeclarado(new BigDecimal(elemento.getValorDeclarado().isEmpty()?"0":elemento.getValorDeclarado().replace(",", ".")));
				modeloBienDto.setBien(bienRustico);
				modeloBienDto.setTransmision(new BigDecimal(elemento.getTransmision().isEmpty()?"0":elemento.getTransmision().replace(",", ".")));
				modeloBienDto.setValorDeclarado(new BigDecimal(elemento.getValorDeclarado().isEmpty()?"0":elemento.getValorDeclarado().replace(",", ".")));
				bienesRusticos.add(modeloBienDto);
			}
		}
		modeloDto.setListaBienesRusticos(bienesRusticos);
		List<ModeloBienDto> bienesUrbanos = new ArrayList<ModeloBienDto>();
		org.gestoresmadrid.oegam2comun.modelo601.xml.Remesa.Declaracion.ListaBienesUrbanos listaBienesUrbanos = modelo.getListaBienesUrbanos();
		if(listaBienesUrbanos!=null){
			for(org.gestoresmadrid.oegam2comun.modelo601.xml.BienUrbanoTypeO elemento : listaBienesUrbanos.getBienUrbano()){
				ModeloBienDto modeloBienDto = new ModeloBienDto();
				BienDto bienUrbano = new BienDto();
				bienUrbano.setAnioContratacion(new BigDecimal(elemento.getAnoContrato().isEmpty()?"0":elemento.getAnoContrato().replace(",", ".")));
				bienUrbano.setArrendamiento("S".equalsIgnoreCase(elemento.getArrendamiento()));
				bienUrbano.setDescalificado("S".equalsIgnoreCase(elemento.getDescalificado()));
				DireccionDto direccion = new DireccionDto();
				direccion.setCodPostal(elemento.getCodigoPostal());
				direccion.setCodPostalCorreos(elemento.getCodigoPostal());
				direccion.setEscalera(elemento.getEscalera());
				direccion.setIdProvincia(elemento.getProvincia());
				direccion.setIdMunicipio(elemento.getMunicipio());
				direccion.setMunicipio(elemento.getMunicipioDesc());
				direccion.setNombreMunicipio(elemento.getMunicipioDesc());
				direccion.setNombreProvincia(elemento.getProvincia());
				direccion.setNombreVia(elemento.getNombreVia());
				direccion.setNumero(elemento.getNumero());
				direccion.setNumLocal(new BigDecimal(elemento.getNumLocal().isEmpty()?"0":elemento.getNumLocal().replace(",", ".")));
				direccion.setPlanta(elemento.getPlanta());
				direccion.setPuerta(elemento.getPuerta());
				direccion.setTipoViaDescripcion(elemento.getViaDesc());
				direccion.setVia(elemento.getVia());
				direccion.setIdTipoVia(elemento.getVia());
				bienUrbano.setDireccion(direccion);
				bienUrbano.setDupTri(elemento.getDupTrip());
				bienUrbano.setFechaConstruccion(new Fecha());
				bienUrbano.getFechaConstruccion().setAnio(elemento.getAnoConstruccion());
				bienUrbano.setPrecioMaximo(new BigDecimal(elemento.getPrecioMaximoVenta().isEmpty()?"0":elemento.getPrecioMaximoVenta().replace(",", ".")));
				bienUrbano.setRefCatrastal(elemento.getReferenciaCatastral());
				SistemaExplotacionDto sistemaExplotacion = new SistemaExplotacionDto();
				bienUrbano.setSistemaExplotacion(sistemaExplotacion);
				SituacionDto situacion = new SituacionDto();
				situacion.setIdSituacion(elemento.getSituacion());
				situacion.setDescSituacion(elemento.getSituacionDesc());
				bienUrbano.setSituacion(situacion);
				bienUrbano.setSuperficie(new BigDecimal(elemento.getSuperficieConstruida().isEmpty()?"0":elemento.getSuperficieConstruida().replace(",", ".")));
				bienUrbano.setSuperficieConst(new BigDecimal(elemento.getSuperficieConstruida().isEmpty()?"0":elemento.getSuperficieConstruida().replace(",", ".")));
				bienUrbano.setTipoBien(elemento.getTipo());
				TipoInmuebleDto tipoInmueble = new TipoInmuebleDto();
				tipoInmueble.setIdTipoInmueble(elemento.getTipo());
				tipoInmueble.setDescTipoInmueble(elemento.getTipoDesc());
				bienUrbano.setTipoInmueble(tipoInmueble);
				bienUrbano.setTransmision(new BigDecimal(elemento.getTransmision().isEmpty()?"0":elemento.getTransmision().replace(",", ".")));
				UnidadMetricaDto unidadMetrica = new UnidadMetricaDto();
				bienUrbano.setUnidadMetrica(unidadMetrica);
				UsoRusticoDto usoRustico = new UsoRusticoDto();
				bienUrbano.setUsoRustico(usoRustico);
				bienUrbano.setValorDeclarado(new BigDecimal(elemento.getValorDeclarado().isEmpty()?"0":elemento.getValorDeclarado().replace(",", ".")));
				bienUrbano.setViviendaProtOficial("S".equalsIgnoreCase(elemento.getVPO()));
				//TODO Todavia no se contempla descomentar cuando se contemple
				//bienUrbano.setViviendaHabitual("S".equalsIgnoreCase(elemento.getViviendaHabitual()));
				modeloBienDto.setBien(bienUrbano);
				modeloBienDto.setTransmision(new BigDecimal(elemento.getTransmision().isEmpty()?"0":elemento.getTransmision().replace(",", ".")));
				modeloBienDto.setValorDeclarado(new BigDecimal(elemento.getValorDeclarado().isEmpty()?"0":elemento.getValorDeclarado().replace(",", ".")));
				bienesUrbanos.add(modeloBienDto);
			}
		}
		modeloDto.setListaBienesUrbanos(bienesUrbanos);
		if(modeloDto.getListaBienesRusticos().size()>0){
			modeloDto.setTipoBien("RUSTICO");
			modeloDto.setBienRustico(modeloDto.getListaBienesRusticos().get(modeloDto.getListaBienesRusticos().size()-1).getBien());
		}
		if(modeloDto.getListaBienesUrbanos().size()>0){
			modeloDto.setTipoBien("URBANO");
			modeloDto.setBienUrbano(modeloDto.getListaBienesUrbanos().get(modeloDto.getListaBienesUrbanos().size()-1).getBien());
		}
		BonificacionDto bonificacion = new BonificacionDto();
		bonificacion.setBonificacion(modelo.getLiquidacion().getBonificacionCuotaImporte());
		if(utiles.stringToBigDecimal(bonificacion.getBonificacion()).floatValue()!=0){
			modeloDto.setBonificacion(bonificacion);
		}
		modeloDto.setBonificacionCuota(modelo.getLiquidacion().getBonificacionCuota());
		ConceptoDto concepto = new ConceptoDto();
		concepto.setConcepto(modelo.getDetalle().getExpAbreviada().value());
		concepto.setDescConcepto(modelo.getDetalle().getConcepto());
		modeloDto.setConcepto(concepto);
		modeloDto.setContrato(contrato);
		modeloDto.setCuota(modelo.getLiquidacion().getCuota());
		modeloDto.setEstado("1");
		modeloDto.setExento("S".equalsIgnoreCase(modelo.getLiquidacion().getExento()));
		modeloDto.setFechaAlta(new Fecha(modelo.getDetalle().getFechaDocumento()));
		modeloDto.setFechaDevengo(new Fecha(modelo.getDetalle().getFechaDevengo()));
		modeloDto.setFechaPrimLiquidacion(new Fecha(modelo.getLiquidacion().getFecha()));
		FundamentoExencionDto fundamentoExencion = new FundamentoExencionDto();
		fundamentoExencion.setFundamentoExcencion(modelo.getLiquidacion().getFundamentoLegal());
		fundamentoExencion.setDescripcion(modelo.getLiquidacion().getFundamentoLegalDesc());
		modeloDto.setFundamentoExencion(fundamentoExencion);
		modeloDto.setFundamentoNoSujeccion(modelo.getLiquidacion().getNoSujeto());
		modeloDto.setLiquidacionComplementaria("S".equalsIgnoreCase(modelo.getLiquidacion().getLiquidacionComplementaria()));
		ModeloDto model = getModelo(Modelo.convertirTexto("601"));
		modeloDto.setModelo(model);
		modeloDto.setNoSujeto("S".equalsIgnoreCase(modelo.getLiquidacion().getNoSujeto()));
		NotarioDto notario = new NotarioDto();
		notario.setApellidos(modelo.getDetalle().getApellidoNotario());
		notario.setCodigoNotaria(modelo.getDetalle().getCodNotaria());
		notario.setCodigoNotario(modelo.getDetalle().getCodNotario());
		notario.setNombre(modelo.getDetalle().getNombreNotario());
		modeloDto.setNotario(notario);
		modeloDto.setNumColegiado(contrato.getColegiadoDto().getNumColegiado());
		// Si es nulo se genera al guardar
		modeloDto.setNumExpediente(null);
		//modeloDto.setNumJustiPrimeraAutolq(modelo.getLiquidacion().getNumJustificantePrimeraLiquidacion());
		modeloDto.setNumProtocolo(new BigDecimal(modelo.getDetalle().getNumProtocolo().isEmpty()?"0":modelo.getDetalle().getNumProtocolo().replace(",", ".")));
		OficinaLiquidadoraDto oficinaLiquidadora = new OficinaLiquidadoraDto();
		oficinaLiquidadora.setId(modelo.getDetalle().getOficinaLiquidadora());
		//TODO Ver si se puede calcuar de otra forma en vez de meterlo a pelo
		oficinaLiquidadora.setIdProvincia("28");
		oficinaLiquidadora.setNombreOficinaLiq(modelo.getDetalle().getOficinaLiquidadoraDesc().value());
		modeloDto.setOficinaLiquidadora(oficinaLiquidadora);
		modeloDto.setPrescrito("S".equalsIgnoreCase(modelo.getLiquidacion().getPrescrita()));
		org.gestoresmadrid.oegam2comun.modelo601.xml.Remesa.Declaracion.ListaIntervinientes listaIntervinientes = modelo.getListaIntervinientes();
		modeloDto.setPresentador(new IntervinienteModelosDto());
		modeloDto.setSujetoPasivo(new IntervinienteModelosDto());
		modeloDto.setTransmitente(new IntervinienteModelosDto());
		if(listaIntervinientes != null){
			for(org.gestoresmadrid.oegam2comun.modelo601.xml.IntervinienteA2Type elemento : listaIntervinientes.getInterviniente()){
				if(!elemento.getTipo().value().equalsIgnoreCase("Presentador")){
					IntervinienteModelosDto interviniente = new IntervinienteModelosDto();
					DireccionDto direccion = new DireccionDto();
					direccion.setCodPostal(elemento.getCp());
					direccion.setEscalera(elemento.getEscalera());
					direccion.setIdMunicipio(elemento.getMunicipio());
					direccion.setIdProvincia(elemento.getProvincia());
					direccion.setIdTipoVia(elemento.getSiglas());
					direccion.setNombreMunicipio(elemento.getMunicipioDesc());
					direccion.setNombreProvincia(elemento.getProvinciaDesc().value());
					direccion.setNombreVia(elemento.getNombreVia());
					direccion.setNumero(elemento.getNumero());
					direccion.setPuerta(elemento.getPuerta());
					direccion.setPlanta(elemento.getPiso());
					direccion.setTipoViaDescripcion(elemento.getSiglasDesc());
					interviniente.setDireccion(direccion);
					PersonaDto persona = new PersonaDto();
					persona.setApellido1RazonSocial(elemento.getNombreRazonsocial());
					if(elemento.getNombreRazonsocial().trim().isEmpty()){
						persona.setApellido1RazonSocial(elemento.getApellido1());
					}
					persona.setApellido2(elemento.getApellido2());
					persona.setNif(elemento.getNif());
					persona.setNombre(elemento.getNombre());
					persona.setTelefonos(elemento.getTelf());
					persona.setFechaNacimiento(new Fecha(elemento.getFecNacimiento()));
					interviniente.setPersona(persona);
					interviniente.setPorcentaje(new BigDecimal(elemento.getGrdoPartic().isEmpty()?"0":elemento.getGrdoPartic().replace(",", ".")));
					interviniente.setTipoInterviniente(elemento.getTipo().value());
					if(interviniente.getTipoInterviniente().equalsIgnoreCase("Sujeto Pasivo")){
						modeloDto.setSujetoPasivo(interviniente);
					}else if(interviniente.getTipoInterviniente().equalsIgnoreCase("Transmitente")){
						modeloDto.setTransmitente(interviniente);
					}
				}
			}
		}
		modeloDto.setProtocoloBis("S".equalsIgnoreCase(modelo.getDetalle().getNumProtocoloBis()));
		String sTipoDoc = "";
		if("S".equalsIgnoreCase(modelo.getDetalle().getDocPublico())){
			sTipoDoc = "PUB";
		}else if("S".equalsIgnoreCase(modelo.getDetalle().getDocJudicial())){
			sTipoDoc = "JUD";
		}else if("S".equalsIgnoreCase(modelo.getDetalle().getDocAdministrativo())){
			sTipoDoc = "ADM";
		}
		modeloDto.setPresentador(getPresentadorContrato(contrato));
		modeloDto.setTipoDocumento(sTipoDoc);
		TipoImpuestoDto tipoImpuesto = new TipoImpuestoDto();
		concepto = new ConceptoDto();
		concepto.setConcepto(modelo.getDetalle().getExpAbreviada().value());
		concepto.setDescConcepto(modelo.getDetalle().getConcepto());
		tipoImpuesto.setConcepto(concepto);
		tipoImpuesto.setFundamentoExencion(modelo.getLiquidacion().getFundamentoLegal());
		tipoImpuesto.setTipoImpuesto(modelo.getLiquidacion().getTipoImpuesto());
		modeloDto.setTipoImpuesto(tipoImpuesto);
		modeloDto.setTotalIngresar(modelo.getLiquidacion().getTotalIngresar().replace(".", "").replace(",", "."));
		modeloDto.setValorDeclarado(utiles.stringToBigDecimal(modelo.getLiquidacion().getValorDeclarado()));
		return modeloDto;	}

	private void rellenarDatosLiquidacion(Modelo600_601VO modeloBBDDVO, Modelo600_601Dto modeloDto) {
		if(modeloDto != null){
			if(modeloDto.getBaseImponible() != null && !modeloDto.getBaseImponible().isEmpty()){
				modeloBBDDVO.setBaseImponible(new BigDecimal(modeloDto.getBaseImponible().replace(",",".")));
			} else if(modeloDto.getTotalIngresar() != null && !modeloDto.getTotalIngresar().isEmpty()){
				modeloBBDDVO.setTotalIngresar(new BigDecimal(modeloDto.getTotalIngresar().replace(",",".")));
			} else if(modeloDto.getBaseLiquidable() != null && !modeloDto.getBaseLiquidable().isEmpty()){
				modeloBBDDVO.setBaseLiquidable(new BigDecimal(modeloDto.getBaseLiquidable().replace(",",".")));
			} else if(modeloDto.getCuota() != null && !modeloDto.getCuota().isEmpty()){
				modeloBBDDVO.setCuota(new BigDecimal(modeloDto.getCuota().replace(",",".")));
			} else if(modeloDto.getBonificacionCuota() != null && !modeloDto.getBonificacionCuota().isEmpty()){
				modeloBBDDVO.setBonificacionCuota(new BigDecimal(modeloDto.getBonificacionCuota().replace(",",".")));
			} else if(modeloDto.getIngresar() != null && !modeloDto.getIngresar().isEmpty()){
				modeloBBDDVO.setIngresar(new BigDecimal(modeloDto.getIngresar().replace(",",".")));
			}	
		}
	}

	private ResultBean validarDatosLiquidacion(Modelo600_601VO modeloBBDDVO) {
		ResultBean resultado = new ResultBean(Boolean.FALSE);
		if(!modeloBBDDVO.isLiquidacionManual()){
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("No se puede liquidar el modelo porque debe de indicar que el modelo es modificado por el Gestor Administrativo.");
		} else if(modeloBBDDVO.getValorDeclarado() == null){
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("No se puede liquidar el modelo porque debe de rellenar el valor declarado.");
		} else if(modeloBBDDVO.getBaseImponible() == null){
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("No se puede liquidar el modelo porque debe de rellenar la base imponible.");
		} else if(modeloBBDDVO.getTotalIngresar() == null){
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("No se puede liquidar el modelo porque debe de rellenar el total a ingresar.");
		} else if(modeloBBDDVO.getBaseLiquidable() == null){
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("No se puede liquidar el modelo porque debe de rellenar la base liquidable.");
		} else if(modeloBBDDVO.getCuota() == null){
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("No se puede liquidar el modelo porque debe de rellenar la cuota.");
		} else if(modeloBBDDVO.getBonificacionCuota() == null){
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("No se puede liquidar el modelo porque debe de rellenar la bonificaci�n Cuota.");
		} else if(modeloBBDDVO.getIngresar() == null){
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("No se puede liquidar el modelo porque debe de rellenar la cantidad a ingresar.");
		} else {
			if(modeloBBDDVO.getValorDeclarado().compareTo(BigDecimal.ZERO) == 0){
				if(modeloBBDDVO.getBaseImponible().compareTo(BigDecimal.ZERO) != 0) {
					resultado.setError(Boolean.TRUE);
					resultado.setMensaje("No se puede liquidar el modelo porque al indicar una base imponible el valor declarado no puede ser 0.");
				} else if(modeloBBDDVO.getTotalIngresar().compareTo(BigDecimal.ZERO) != 0){
					resultado.setError(Boolean.TRUE);
					resultado.setMensaje("No se puede liquidar el modelo porque al indicar un importe total a ingresar el valor declarado y la base imponible no pueden ser 0.");
				}
			} else if(modeloBBDDVO.getTotalIngresar().compareTo(BigDecimal.ZERO) == 0){
				if(modeloBBDDVO.getTotalIngresar().compareTo(BigDecimal.ZERO) != 0){
						resultado.setError(Boolean.TRUE);
						resultado.setMensaje("No se puede liquidar el modelo porque al indicar una base imponible igual a 0 el importe total a ingresar no puede ser distinto de 0.");
				}
			}
		}
		return resultado;
	}
	
	/*
	 * Comprobamos los datos que hay en pantalla cuando pulsamos autoliquidar
	 * y sustituimos por datos obtenidos por BBDD
	 */
	private void comprobarDatosPantalla(Modelo600_601Dto dto, Modelo600_601VO vo) {
		if(StringUtils.isBlank(dto.getIdBonificacion())) {
			vo.setIdBonificacion("");
		}else {
			vo.setIdBonificacion(dto.getIdBonificacion());
		}
		vo.setIdBonificacion(dto.getIdBonificacion());
		vo.setExento(dto.getExento()? BigDecimal.ONE: BigDecimal.ZERO);
		vo.setNoSujeto(dto.getNoSujeto()? BigDecimal.ONE: BigDecimal.ZERO);
		vo.setFundamentoNoSujeccion(dto.getFundamentoNoSujeccion());
		vo.setLiquidacionComplementaria(dto.getLiquidacionComplementaria()? BigDecimal.ONE: BigDecimal.ZERO);
		if(StringUtils.isBlank(dto.getIdFundamentoExencion())) {
			vo.setIdFundamentoExencion("");
		}else if(dto.getIdFundamentoExencion().equals("-1")){
			vo.setIdFundamentoExencion("");
		}else {
			vo.setIdFundamentoExencion(dto.getIdFundamentoExencion());
		}
	}

	public ResultBean aplicarBonificacion(Modelo600_601VO modeloVO) {
		Modelo600_601Dto modeloDto = conversor.transform(modeloVO, Modelo600_601Dto.class);
		ResultBean resultado = new ResultBean(Boolean.FALSE);

		if(modeloDto.getConcepto().getConcepto().equals("TS0") || modeloDto.getConcepto().getConcepto().equals("TU1")
				 || modeloDto.getConcepto().getConcepto().equals("VT1") || modeloDto.getConcepto().getConcepto().equals("VT2")
				 || modeloDto.getConcepto().getConcepto().equals("VT3") ||modeloDto.getConcepto().getConcepto().equals("VP1")) {
			bonificacionViviendaHabitual(modeloVO);
		}
		if(modeloDto.getConcepto().getConcepto().equals("TM0")) {
			bonificacionBienesMuebles(modeloVO);
		}
		if(modeloDto.getConcepto().getConcepto().equals("AU0")) {
			bonificacionFincasUrbanas(modeloVO);
		}
		if(modeloDto.getConcepto().getConcepto().equals("VT5") || modeloDto.getConcepto().getConcepto().equals("VT6") || modeloVO.getConcepto().getDescConcepto().equals("VT7") || modeloVO.getConcepto().getDescConcepto().equals("VP2")) {
			if(!resultado.getError()) {
				bonificacionFamiliaNumerosa(modeloVO);
			}
		}
		return resultado;
	}
	public ResultBean mezclarBonificados(Modelo600_601VO modeloVO) {
		ResultBean resultado = new ResultBean(Boolean.FALSE);
			int contadorBonificado = 0;
			int contadorNoBonificado = 0;
			for(ModeloBienVO modeloBienDto : modeloVO.getListaBienes()){
				if(modeloBienDto.getValorDeclarado().compareTo(new BigDecimal(250000)) == -1) {
					contadorNoBonificado++;
				}else {
					contadorBonificado++;
				}
				if(contadorNoBonificado != 0 && contadorBonificado != 0) {
					resultado.addError("No se pueden mezclar bienes Bonificados y no bonificados");
					break;
				}
			}
		return resultado;
	}
	public void bonificacionFamiliaNumerosa(Modelo600_601VO modeloVO) {
		BonificacionVO bonificacionVO = null;
		BigDecimal valorMinimo = new BigDecimal(120000);
		BigDecimal valorMaximo = new BigDecimal(180000);
		for(ModeloBienVO modeloBienVO : modeloVO.getListaBienes()){
			ModeloBienDto modeloBienDto = conversor.transform(modeloBienVO, ModeloBienDto.class);
			if(modeloBienVO.getValorDeclarado().compareTo(valorMinimo)==-1) {
				modeloVO.setIdConcepto("VT5");
			}else if(modeloBienVO.getValorDeclarado().compareTo(valorMaximo)==1) {
				modeloVO.setIdConcepto("VT7");
			}else {
				modeloVO.setIdConcepto("VT6");
			}
			if(modeloBienDto.getBien().getViHabitual()==true) {
				bonificacionVO = servicioBonificacion.activarBonificacionPorId("07");
				modeloVO.setIdBonificacion(bonificacionVO.getBonificacion());
			}
		}
	}

	public void bonificacionBienesMuebles(Modelo600_601VO modeloVO) {
		BonificacionVO bonificacionVO = null;
		if(modeloVO.getListaBienes()!=null) {
			int contadorBono = 0;
			BigDecimal cuota100 = new BigDecimal(100);
			for(ModeloBienVO modeloBienVO : modeloVO.getListaBienes()){
				if(modeloBienVO.getModelo600_601()!= null && modeloBienVO.getModelo600_601().getTipoImpuesto()!=null){
					BigDecimal porcentajeCuota = modeloBienVO.getModelo600_601().getTipoImpuesto().getMonto().divide(cuota100);
					if(modeloBienVO.getValorDeclarado().compareTo(new BigDecimal(500)) <= 0) {
						contadorBono++;
						BigDecimal bonificacionCuota = modeloBienVO.getValorDeclarado().multiply(porcentajeCuota);
						bonificacionCuota = bonificacionCuota.add(modeloVO.getBonificacionCuota());
						modeloVO.setBonificacionCuota(bonificacionCuota);
					}
				}
			}
			if(contadorBono== modeloVO.getListaBienes().size()) {
				bonificacionVO = servicioBonificacion.activarBonificacionPorId("06");
				modeloVO.setIdBonificacion(bonificacionVO.getBonificacion());
			}
		}
	}

	public void bonificacionFincasUrbanas(Modelo600_601VO modeloVO) {
		BonificacionVO bonificacionVO = null;
		BigDecimal periodoMinimo = new BigDecimal(36);
		BigDecimal importeMinimo = new BigDecimal(1250);
		if(!StringUtils.isBlank(modeloVO.getTipoPeriodoRenta())){
			switch (modeloVO.getTipoPeriodoRenta()) {
			case "MENSUAL":
				if(modeloVO.getNumPeriodoRenta().compareTo(periodoMinimo) >= 0) {
					if(modeloVO.getImporteRenta().compareTo(importeMinimo) <=0){
						modeloVO.setExento(BigDecimal.ONE);
						bonificacionVO = servicioBonificacion.activarBonificacionPorId("09");
						modeloVO.setIdBonificacion(bonificacionVO.getBonificacion());
					}
				}
				break;
			case "ANUAL":
				BigDecimal meses = modeloVO.getNumPeriodoRenta().multiply(new BigDecimal(12));
				BigDecimal mensualidad = modeloVO.getImporteRenta().divide(meses);
				if(modeloVO.getNumPeriodoRenta().compareTo(periodoMinimo) >= 0 && mensualidad.compareTo(importeMinimo)<=0){
					modeloVO.setExento(BigDecimal.ONE);
					bonificacionVO = servicioBonificacion.activarBonificacionPorId("09");
					modeloVO.setIdBonificacion(bonificacionVO.getBonificacion());
				}
				break;
			}
		}
	}

	public void bonificacionViviendaHabitual(Modelo600_601VO modeloVO) {
		ResultBean resultado = new ResultBean(Boolean.FALSE);
		BigDecimal sumaBienes = BigDecimal.ZERO;
		BonificacionVO bonificacionVO = null;
		if(modeloVO.getListaBienes() != null) {
			resultado = mezclarBonificados(modeloVO);
			if(!resultado.getError()) {
				for(ModeloBienVO modeloBienVO : modeloVO.getListaBienes()){
					if(modeloBienVO.getValorDeclarado()==null) {
						modeloBienVO.setValorDeclarado(BigDecimal.ZERO);
					}
					switch (modeloBienVO.getBien().getTipoInmueble().getId().getIdTipoInmueble()) {
						case "CT":
							sumaBienes = sumaBienes.add(modeloBienVO.getValorDeclarado());
							break;
						case "PG":
							sumaBienes = sumaBienes.add(modeloBienVO.getValorDeclarado());
							break;
						case "VI":
							sumaBienes = sumaBienes.add(modeloBienVO.getValorDeclarado());
							break;
						case "VU":
							sumaBienes = sumaBienes.add(modeloBienVO.getValorDeclarado());
							break;
						case "VH":
							sumaBienes = sumaBienes.add(modeloBienVO.getValorDeclarado());
							break;
						default:
							//throw new IllegalArgumentException("Error en los Bienes: ");
							break;
					}
					if(sumaBienes.compareTo(new BigDecimal(250000)) == -1) {
						bonificacionVO = servicioBonificacion.activarBonificacionPorId("05");
						modeloVO.setIdBonificacion(bonificacionVO.getBonificacion());
					}else {
						resultado.setError(Boolean.TRUE);
						resultado.setMensaje("La suma de los bienes excede los 250.000€");
					}
				}
			}
		}
	}

	public void bonificacionViviendaHabitualFamiliaNumerosa(Modelo600_601VO modeloVO) {
		ResultBean resultado = new ResultBean(Boolean.FALSE);
		BigDecimal sumaBienes = BigDecimal.ZERO;
		BonificacionVO bonificacionVO = null;
		if(modeloVO.getListaBienes() != null) {
			resultado = mezclarBonificados(modeloVO);
			if(!resultado.getError()) {
				for(ModeloBienVO modeloBienVO : modeloVO.getListaBienes()){
					if(modeloBienVO.getValorDeclarado()==null) {
						modeloBienVO.setValorDeclarado(BigDecimal.ZERO);
					}
					switch (modeloBienVO.getBien().getTipoInmueble().getId().getIdTipoInmueble()) {
						case "CT":
							sumaBienes = sumaBienes.add(modeloBienVO.getValorDeclarado());
							break;
						case "PG":
							sumaBienes = sumaBienes.add(modeloBienVO.getValorDeclarado());
							break;
						case "VI":
							sumaBienes = sumaBienes.add(modeloBienVO.getValorDeclarado());
							break;
						case "VU":
							sumaBienes = sumaBienes.add(modeloBienVO.getValorDeclarado());
							break;
						case "VH":
							sumaBienes = sumaBienes.add(modeloBienVO.getValorDeclarado());
							break;
						default:
							throw new IllegalArgumentException("Error en los Bienes: ");
					}
					if(sumaBienes.compareTo(new BigDecimal(250000)) == -1) {
						bonificacionVO = servicioBonificacion.activarBonificacionPorId("07");
						modeloVO.setIdBonificacion(bonificacionVO.getBonificacion());
					}else {
						resultado.setError(Boolean.TRUE);
						resultado.setMensaje("La suma de los bienes excede los 250.000€");
					}
				}
			}
		}
	}

	private BigDecimal calcularBaseImponible(Modelo600_601VO modeloVO) {
		BigDecimal baseImponible = BigDecimal.ZERO;
		for(ModeloBienVO modeloBienVO : modeloVO.getListaBienes()){
			if(modeloBienVO.getValorDeclarado()==null) {
				modeloBienVO.setValorDeclarado(BigDecimal.ZERO);
			}
			baseImponible = baseImponible.add(modeloBienVO.getTransmision().multiply(modeloBienVO.getValorDeclarado()).divide(new BigDecimal(100)));
		}
		return baseImponible;
	}
}