package org.gestoresmadrid.oegam2comun.trafico.eeff.model.service.impl;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.gestoresmadrid.core.historicocreditos.model.dao.enumerados.ConceptoCreditoFacturado;
import org.gestoresmadrid.core.model.enumerados.TipoInterviniente;
import org.gestoresmadrid.core.model.enumerados.TipoTramiteTrafico;
import org.gestoresmadrid.core.trafico.eeff.model.dao.ConsultaEEFFDao;
import org.gestoresmadrid.core.trafico.eeff.model.dao.LiberacionEEFFDao;
import org.gestoresmadrid.core.trafico.eeff.model.enumerados.ConstantesEEFF;
import org.gestoresmadrid.core.trafico.eeff.model.enumerados.EstadoConsultaEEFF;
import org.gestoresmadrid.core.trafico.eeff.model.vo.ConsultaEEFFVO;
import org.gestoresmadrid.core.trafico.eeff.model.vo.LiberacionEEFFVO;
import org.gestoresmadrid.oegam2comun.cola.model.service.ServicioCola;
import org.gestoresmadrid.oegam2comun.contrato.model.service.ServicioContrato;
import org.gestoresmadrid.oegam2comun.creditos.model.service.ServicioCredito;
import org.gestoresmadrid.oegam2comun.direccion.model.service.ServicioDireccion;
import org.gestoresmadrid.oegam2comun.intervinienteTrafico.model.service.ServicioIntervinienteTrafico;
import org.gestoresmadrid.oegam2comun.proceso.enumerados.ProcesosEnum;
import org.gestoresmadrid.oegam2comun.trafico.eeff.model.service.ServicioEEFFNuevo;
import org.gestoresmadrid.oegam2comun.trafico.eeff.view.dto.ConsultaEEFFDto;
import org.gestoresmadrid.oegam2comun.trafico.eeff.view.dto.LiberacionEEFFDto;
import org.gestoresmadrid.oegam2comun.trafico.eeff.view.dto.ResultadoConsultaEEFFDto;
import org.gestoresmadrid.oegam2comun.trafico.eeff.view.xml.SolicitudRegistroEntrada;
import org.gestoresmadrid.oegam2comun.trafico.model.service.ServicioTramiteTraficoMatriculacion;
import org.gestoresmadrid.oegam2comun.trafico.tramitar.build.BuildEEFF;
import org.gestoresmadrid.oegam2comun.trafico.view.dto.TramiteTrafMatrDto;
import org.gestoresmadrid.oegamComun.contrato.view.dto.ContratoDto;
import org.gestoresmadrid.oegamComun.conversor.Conversor;
import org.gestoresmadrid.oegamComun.interviniente.view.dto.IntervinienteTraficoDto;
import org.gestoresmadrid.oegamComun.vehiculo.view.dto.VehiculoDto;
import org.gestoresmadrid.utilidades.components.GestorPropiedades;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import escrituras.beans.ResultBean;
import trafico.beans.jaxb.matw.FORMATOGA.MATRICULACION.DATOSLIBERACION;
import trafico.beans.jaxb.pruebaCertificado.SolicitudPruebaCertificado;
import trafico.utiles.XMLPruebaCertificado;
import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;
import utilidades.web.OegamExcepcion;
import viafirma.utilidades.UtilesViafirma;

@Service
public class ServicioEEFFNuevoImpl implements ServicioEEFFNuevo{

	private static final long serialVersionUID = -5400762086917405678L;

	public static final ILoggerOegam log = LoggerOegam.getLogger(ServicioEEFFNuevoImpl.class);
	
	@Autowired
	ServicioIntervinienteTrafico servicioIntervinienteTrafico;
	
	@Autowired
	ServicioCredito servicioCredito;
	
	@Autowired
	ServicioContrato servicioContrato;
	
	@Autowired
	BuildEEFF buildEEFF;
	
	@Autowired
	ServicioCola servicioCola;
	
	@Autowired
	ServicioDireccion servicioDireccion;
	
	@Autowired
	Conversor conversor;
	
	@Autowired
	LiberacionEEFFDao liberacionEEFFDao;
	
	@Autowired
	ConsultaEEFFDao consultaEEFFDao;
	
	@Autowired
	ServicioTramiteTraficoMatriculacion servicioTramiteTraficoMatriculacion;
	
	@Autowired
	private GestorPropiedades gestorPropiedades;
	
	@Override
	@Transactional
	public ResultBean liberar(LiberacionEEFFDto liberacionEEFFDTO, BigDecimal idContrato, BigDecimal idUsuario) {
		ResultBean resultado = new ResultBean(false);
		String nombreXml = null;
		try {
			if(liberacionEEFFDTO != null){
				IntervinienteTraficoDto titularDto = servicioIntervinienteTrafico.getIntervinienteTraficoDto(liberacionEEFFDTO.getNumExpediente(), 
					TipoInterviniente.Titular.getValorEnum(), liberacionEEFFDTO.getNif(), liberacionEEFFDTO.getNumColegiado().toString());
				resultado = validarDatosLiberacion(liberacionEEFFDTO, titularDto);
				if(!resultado.getError()){
					LiberacionEEFFVO liberacionEEFFBBDD = liberacionEEFFDao.getLiberacionEEFFPorExpediente(liberacionEEFFDTO.getNumExpediente());
					resultado = comprobarCreditosLiberacion(liberacionEEFFBBDD.getNumExpediente(), idContrato, idUsuario);
					if(!resultado.getError()){
						conversor.transform(liberacionEEFFDTO, liberacionEEFFBBDD);
						liberacionEEFFDao.actualizar(liberacionEEFFBBDD);
						ContratoDto contratoDto = servicioContrato.getContratoDto(idContrato);
						Boolean esCertColegiadoCorrecto = comprobarCaducidadCertificado(contratoDto.getColegiadoDto().getAlias());
						if(esCertColegiadoCorrecto){
							SolicitudRegistroEntrada solicitudRegistroEntrada = buildEEFF.obtenerSolicitudLiberacionEEFF(liberacionEEFFBBDD,titularDto,contratoDto);
							resultado = buildEEFF.realizarFirmaLiberacion(solicitudRegistroEntrada,contratoDto.getColegiadoDto().getAlias(),liberacionEEFFBBDD.getNumExpediente());
							if(!resultado.getError()){
								nombreXml = (String) resultado.getAttachment(NOMBRE_XML);
								ResultBean result = servicioCola.crearSolicitud(ProcesosEnum.PROCESO_EEFF.getNombreEnum(), nombreXml, gestorPropiedades.valorPropertie(NOMBRE_HOST_SOLICITUD), 
										TipoTramiteTrafico.consultaEEFF.getValorEnum(), liberacionEEFFBBDD.getNumExpediente().toString(), idUsuario, null, idContrato);
								if(result.getError()){
									resultado.setListaMensajes(result.getListaMensajes());
									resultado.setError(Boolean.TRUE);
								}
							}
						} else {
							resultado.setError(Boolean.TRUE);
							resultado.addMensajeALista("El certificado del colegiado se encuentra caducado.");
						}
					}
				}
			} else {
				resultado.setError(Boolean.TRUE);
				resultado.setMensaje("Para poder liberar un vehículo, el trámite debe tener asociados datos de liberación.");
			}
		} catch (Throwable e) {
			log.error("Ha sucedido un error a la hora de realizar la liberación del expediente: " + liberacionEEFFDTO.getNumExpediente() + ", error: ",e);
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("Ha sucedido un error a la hora de realizar la liberación del expediente: " + liberacionEEFFDTO.getNumExpediente());
		}
		return resultado;
	}

	private ResultBean comprobarCreditosLiberacion(BigDecimal numExpediente, BigDecimal idContrato, BigDecimal idUsuario) {
		ResultBean resultado = new ResultBean(false);
		try {
			String cobraCreditos = gestorPropiedades.valorPropertie(COBRAR_CREDITOS_EEFF);
			if (cobraCreditos == null){
				resultado.setError(Boolean.TRUE);
				resultado.setMensaje("No se puede comprobar si tiene créditos suficientes");
			} else if (cobraCreditos.equalsIgnoreCase("true")){
				resultado = servicioCredito.validarCreditos(TipoTramiteTrafico.consultaEEFF.getValorEnum(), idContrato, BigDecimal.ONE);
				if (!resultado.getError()) {
					// Descontar creditos
					resultado = servicioCredito.descontarCreditos(TipoTramiteTrafico.consultaEEFF.getValorEnum(), idContrato, BigDecimal.ONE,
							idUsuario, ConceptoCreditoFacturado.EFC, numExpediente.toString());
				}
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de comprobar los creditos para liberar una matricula, error: ",e, numExpediente.toString());
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("Ha sucedido un error a la hora de comprobar los creditos para liberar.");
		}
		return resultado;
	}

	private ResultBean validarDatosLiberacion(LiberacionEEFFDto liberacionEEFFDTO, IntervinienteTraficoDto titularDto) {
		ResultBean resultado = new ResultBean(false);
		resultado.setMensaje("Debe rellenar los siguientes campos para poder realizar la liberación:");
		if((liberacionEEFFDTO.getExento() != null && liberacionEEFFDTO.getExento()) || (liberacionEEFFDTO.getRealizado() != null && liberacionEEFFDTO.getRealizado())){
			resultado.setError(Boolean.TRUE);
			resultado.addMensajeALista("No se puede realizar la liberación, porque ya está realizada o está exenta.");
		} else if(titularDto == null || titularDto.getPersona() == null || titularDto.getPersona().getNif() == null || titularDto.getPersona().getNif().isEmpty()){
			resultado.setError(Boolean.TRUE);
			resultado.addMensajeALista("No se puede realizar la liberación, porque no se encuentran datos del titular.");
		} else if(titularDto.getDireccion() == null || titularDto.getDireccion().getIdDireccion() == null){
			resultado.setError(Boolean.TRUE);
			resultado.addMensajeALista("No se puede realizar la liberación, porque no se encuentran datos de la dirección del titular.");
		} else{
			if(liberacionEEFFDTO.getTarjetaBastidor() == null || liberacionEEFFDTO.getTarjetaBastidor().isEmpty()){
				resultado.setError(Boolean.TRUE);
				resultado.addMensajeALista("- Debe de indicar el Bastidor del vehículo.");
			}
			if(liberacionEEFFDTO.getTarjetaNive() == null || liberacionEEFFDTO.getTarjetaNive().isEmpty()){
				resultado.setError(Boolean.TRUE);
				resultado.addMensajeALista("- Debe de indicar el NIVE del vehículo.");
			} else{
				if (liberacionEEFFDTO.getTarjetaNive().length() != ConstantesEEFF.EEFF_LONGITUD_NIVE) {
					resultado.setError(Boolean.TRUE);
					resultado.addMensajeALista("- El código NIVE debe tener una longitud de 32 caracteres.");
				}
			}
			if(liberacionEEFFDTO.getFirCif() == null || liberacionEEFFDTO.getFirCif().isEmpty()){
				resultado.setError(Boolean.TRUE);
				resultado.addMensajeALista("- El CIF del FIR es obligatorio.");
			}
			if(liberacionEEFFDTO.getFirMarca() == null || liberacionEEFFDTO.getFirMarca().isEmpty()){
				resultado.setError(Boolean.TRUE);
				resultado.addMensajeALista("- La marca del FIR es obligatoria.");
			}
			if(liberacionEEFFDTO.getFechaFactura() == null){
				resultado.setError(Boolean.TRUE);
				resultado.addMensajeALista("- La fecha de la factura es obligatoria.");
			}
			if(liberacionEEFFDTO.getNumFactura() == null || liberacionEEFFDTO.getNumFactura().isEmpty()){
				resultado.setError(Boolean.TRUE);
				resultado.addMensajeALista("- El número de factura es obligatorio.");
			}
			if(liberacionEEFFDTO.getImporte() == null){
				resultado.setError(Boolean.TRUE);
				resultado.addMensajeALista("- El Importe de la factura es obligatorio.");
			}
			if(liberacionEEFFDTO.getNifRepresentado() == null || liberacionEEFFDTO.getNifRepresentado().isEmpty()){
				resultado.setError(Boolean.TRUE);
				resultado.addMensajeALista("- Debe de rellenar el CIF del Concesionario Representado.");
			}
		}
		if(!resultado.getError()){
			resultado.setMensaje(null);
		} else {
			if(liberacionEEFFDTO.getExento() == null){
				liberacionEEFFDTO.setExento(Boolean.FALSE);
			}
			if(liberacionEEFFDTO.getRealizado() == null){
				liberacionEEFFDTO.setRealizado(Boolean.FALSE);
			}
		}
		return resultado;
	}
	
	@Override
	@Transactional
	public ResultBean guardarDatosLiberacion(TramiteTrafMatrDto tramiteTrafMatrDto, BigDecimal numExpediente) {
		ResultBean resultado = new ResultBean(Boolean.FALSE);
		try {
			LiberacionEEFFVO liberacionEEFFBBDDVO = getLiberacionEEFFVO(numExpediente);
			if(tramiteTrafMatrDto != null && tramiteTrafMatrDto.getVehiculoDto() != null 
				&& tramiteTrafMatrDto.getVehiculoDto().getNive() != null && !tramiteTrafMatrDto.getVehiculoDto().getNive().isEmpty()){
				if(liberacionEEFFBBDDVO != null){
					if(liberacionEEFFBBDDVO.getRealizado() != null && liberacionEEFFBBDDVO.getRealizado()){
						return resultado;
					}
				} else {
					liberacionEEFFBBDDVO = new LiberacionEEFFVO();
				}
				conversor.transform(tramiteTrafMatrDto.getLiberacionEEFF(), liberacionEEFFBBDDVO);
				tratarDatosLiberacion(liberacionEEFFBBDDVO,tramiteTrafMatrDto, numExpediente);
				liberacionEEFFDao.guardarOActualizar(liberacionEEFFBBDDVO);
			} else {
				if(liberacionEEFFBBDDVO != null){
					liberacionEEFFDao.borrar(liberacionEEFFBBDDVO);
				}
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de guardar los datos de liberación, error: ",e, numExpediente.toString());
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("Ha sucedido un error a la hora de guardar los datos de liberación.");
		}
		return resultado;
	}

	public void tratarDatosLiberacion(LiberacionEEFFVO liberacionEEFFBBDDVO, TramiteTrafMatrDto tramiteTrafMatrDto, BigDecimal numExpediente) {
		if(tramiteTrafMatrDto.getVehiculoDto() != null){
			if(tramiteTrafMatrDto.getVehiculoDto().getBastidor() != null && !tramiteTrafMatrDto.getVehiculoDto().getBastidor().isEmpty()){
				liberacionEEFFBBDDVO.setTarjetaBastidor(tramiteTrafMatrDto.getVehiculoDto().getBastidor());
			}
			if(tramiteTrafMatrDto.getVehiculoDto().getNive() != null && !tramiteTrafMatrDto.getVehiculoDto().getNive().isEmpty()){
				liberacionEEFFBBDDVO.setTarjetaNive(tramiteTrafMatrDto.getVehiculoDto().getNive());
			}
		}
		if(tramiteTrafMatrDto.getTitular() != null && tramiteTrafMatrDto.getTitular().getPersona() != null 
			&& tramiteTrafMatrDto.getTitular().getPersona().getNif() != null && !tramiteTrafMatrDto.getTitular().getPersona().getNif().isEmpty()){
			liberacionEEFFBBDDVO.setNif(tramiteTrafMatrDto.getTitular().getPersona().getNif());
		}
		if(liberacionEEFFBBDDVO.getNumExpediente() == null){
			liberacionEEFFBBDDVO.setNumExpediente(numExpediente);
		}
		if(liberacionEEFFBBDDVO.getNumColegiado() == null || liberacionEEFFBBDDVO.getNumColegiado().isEmpty()){
			liberacionEEFFBBDDVO.setNumColegiado(tramiteTrafMatrDto.getNumColegiado());
		}
		if(liberacionEEFFBBDDVO.getExento() == null){
			liberacionEEFFBBDDVO.setExento(Boolean.FALSE);
		}
		if(liberacionEEFFBBDDVO.getRealizado() == null){
			liberacionEEFFBBDDVO.setRealizado(Boolean.FALSE);
		}
	}
	
	@Override
	@Transactional(readOnly=true)
	public LiberacionEEFFDto getLiberacionEEFFDto(BigDecimal numExpediente) {
		LiberacionEEFFDto liberacionEEFFDTO = null;
		try {
			if(numExpediente != null){
				LiberacionEEFFVO liberacionEEFFVO = getLiberacionEEFFVO(numExpediente);
				if(liberacionEEFFVO != null){
					liberacionEEFFDTO = conversor.transform(liberacionEEFFVO, LiberacionEEFFDto.class);
					
					liberacionEEFFDTO.setTitular(servicioIntervinienteTrafico.getIntervinienteTraficoDto(liberacionEEFFVO.getNumExpediente(), 
							TipoInterviniente.Titular.getValorEnum(), liberacionEEFFVO.getNif(), liberacionEEFFVO.getNumColegiado().toString()));
					if(liberacionEEFFDTO.getTitular() != null && liberacionEEFFDTO.getTitular().getDireccion() != null
						&& liberacionEEFFDTO.getTitular().getDireccion().getIdProvincia() != null && !liberacionEEFFDTO.getTitular().getDireccion().getIdProvincia().isEmpty()){
						liberacionEEFFDTO.getTitular().getDireccion().setNombreProvincia(servicioDireccion.obtenerNombreProvincia(liberacionEEFFDTO.getTitular().getDireccion().getIdProvincia()));
						
						if(liberacionEEFFDTO.getTitular().getDireccion().getIdMunicipio() != null && !liberacionEEFFDTO.getTitular().getDireccion().getIdMunicipio().isEmpty()){
								liberacionEEFFDTO.getTitular().getDireccion().setNombreMunicipio(
										servicioDireccion.obtenerNombreMunicipio(liberacionEEFFDTO.getTitular().getDireccion().getIdMunicipio(), 
																					liberacionEEFFDTO.getTitular().getDireccion().getIdProvincia()));
						}
					}
				}
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error al obtener los datos de liberacion ,error: ",e, numExpediente.toString());
		}
		return liberacionEEFFDTO;
	}

	@Override
	@Transactional(readOnly=true)
	public LiberacionEEFFVO getLiberacionEEFFVO(BigDecimal numExpediente) {
		LiberacionEEFFVO liberacionEEFFVO = null;
		try {
			liberacionEEFFVO = liberacionEEFFDao.getLiberacionEEFFPorExpediente(numExpediente);
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de obtener los datos de liberación, error: ",e, numExpediente.toString());
		}
		return liberacionEEFFVO;
	}

	@Override
	@Transactional(readOnly=true)
	public ResultBean comprobarDatosModificadosLiberacion(TramiteTrafMatrDto tramiteTrafMatrDto) {
		ResultBean resultado = new ResultBean(Boolean.FALSE);
		try {
			if(tramiteTrafMatrDto != null && tramiteTrafMatrDto.getNumExpediente() != null){
				LiberacionEEFFVO liberacionEEFFVO = liberacionEEFFDao.getLiberacionEEFFPorExpediente(tramiteTrafMatrDto.getNumExpediente());
				if(liberacionEEFFVO != null && liberacionEEFFVO.getRealizado() != null && liberacionEEFFVO.getRealizado()){
					IntervinienteTraficoDto titularLiberacionDto = servicioIntervinienteTrafico.getIntervinienteTraficoDto(liberacionEEFFVO.getNumExpediente(), 
							TipoInterviniente.Titular.getValorEnum(), liberacionEEFFVO.getNif(), liberacionEEFFVO.getNumColegiado().toString());
					Boolean esModificado = comprobarDatosModificadosTitular(tramiteTrafMatrDto.getTitular(), titularLiberacionDto);
					if(esModificado){
						resultado.setError(Boolean.TRUE);
						resultado.setMensaje("Una vez realizada la liberación del vehículo no se pueden modificar los datos del titular.");
					} else {
						esModificado = comprobarDatosModificadosVehiculo(tramiteTrafMatrDto.getVehiculoDto(),liberacionEEFFVO);
						if(esModificado){
							resultado.setError(Boolean.TRUE);
							resultado.setMensaje("Una vez realizada la liberación del vehículo no se pueden modificar los datos del mismo.");
						}
					}
					liberacionEEFFDao.evict(liberacionEEFFVO);
				}
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de comprobar los datos modificados para la liberación, error: ",e, tramiteTrafMatrDto.getNumExpediente().toString());
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("Ha sucedido un error a la hora de comprobar los datos modificados para la liberación.");
		}
		return resultado;
	}

	private Boolean comprobarDatosModificadosVehiculo(VehiculoDto vehiculoDto, LiberacionEEFFVO liberacionEEFFVO) {
		Boolean esModificado = Boolean.FALSE;
		if(vehiculoDto == null){
			esModificado = Boolean.TRUE;
		} else if(!liberacionEEFFVO.getTarjetaBastidor().toUpperCase().equals(vehiculoDto.getBastidor())) {
			esModificado = Boolean.TRUE;
		} else if(!liberacionEEFFVO.getTarjetaNive().toUpperCase().equals(vehiculoDto.getNive())){
			esModificado = Boolean.TRUE;
		}
		return esModificado;
	}

	private Boolean comprobarDatosModificadosTitular(IntervinienteTraficoDto titularTramiteDto, IntervinienteTraficoDto titularLiberacionDto) {
		Boolean esModificado = Boolean.FALSE;
		if(titularTramiteDto == null || titularTramiteDto.getPersona() == null || titularTramiteDto.getDireccion() == null){
			esModificado = Boolean.TRUE;
		} else if(!titularLiberacionDto.getPersona().getNif().equals(titularTramiteDto.getPersona().getNif())) {
			esModificado = Boolean.TRUE;
		} else if(!titularLiberacionDto.getPersona().getNombre().toUpperCase().equals(titularTramiteDto.getPersona().getNombre().toUpperCase())){
			esModificado = Boolean.TRUE;
		} else if(!titularLiberacionDto.getPersona().getApellido1RazonSocial().toUpperCase().equals(titularTramiteDto.getPersona().getApellido1RazonSocial().toUpperCase())){
			esModificado = Boolean.TRUE;
		} else if(!titularLiberacionDto.getPersona().getApellido2().toUpperCase().equals(titularTramiteDto.getPersona().getApellido2().toUpperCase())){
			esModificado = Boolean.TRUE;
		} else if(titularLiberacionDto.getDireccion().getIdDireccion() != titularTramiteDto.getDireccion().getIdDireccion()){
			esModificado = Boolean.TRUE;
		} else if(!titularLiberacionDto.getDireccion().getIdProvincia().equals(titularTramiteDto.getDireccion().getIdProvincia())){
			esModificado = Boolean.TRUE;
		} else if(!titularLiberacionDto.getDireccion().getIdMunicipio().equals(titularTramiteDto.getDireccion().getIdMunicipio())){
			esModificado = Boolean.TRUE;
		} else if(!titularLiberacionDto.getDireccion().getIdTipoVia().equals(titularTramiteDto.getDireccion().getIdTipoVia())){
			esModificado = Boolean.TRUE;
		} else if(!titularLiberacionDto.getDireccion().getNombreVia().toUpperCase().equals(titularTramiteDto.getDireccion().getNombreVia().toUpperCase())){
			esModificado = Boolean.TRUE;
		} else if(!titularLiberacionDto.getDireccion().getNumero().equals(titularTramiteDto.getDireccion().getNumero())){
			esModificado = Boolean.TRUE;
		} else if(!titularLiberacionDto.getDireccion().getPlanta().equals(titularTramiteDto.getDireccion().getPlanta())){
			esModificado = Boolean.TRUE;
		} else if(!titularLiberacionDto.getDireccion().getPuerta().equals(titularTramiteDto.getDireccion().getPuerta())){
			esModificado = Boolean.TRUE;
		} else if(!titularLiberacionDto.getDireccion().getCodPostal().equals(titularTramiteDto.getDireccion().getCodPostal())){
			esModificado = Boolean.TRUE;
		}
		return esModificado;
	}
	
	@Override
	@Transactional
	public ResultBean guardarLiberacionProceso(LiberacionEEFFDto liberacionEEFFDto) {
		ResultBean resultBean = new ResultBean(Boolean.FALSE);
		try {
			LiberacionEEFFVO liberacionEEFFVO = conversor.transform(liberacionEEFFDto, LiberacionEEFFVO.class);
			if(liberacionEEFFVO != null){
				liberacionEEFFDao.actualizar(liberacionEEFFVO);
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de actualizar la liberacion mediante el proceso, error: ",e);
			resultBean.setError(Boolean.TRUE);
			resultBean.setMensaje("Ha sucedido un error a la hora de actualizar los datos de la liberación.");
		}
		return resultBean;
	}
	
	@Override
	@Transactional(readOnly=true)
	public ResultBean obtenerDetalleConsultaEEFF(BigDecimal numExpediente) {
		ResultBean resultado = new ResultBean(Boolean.FALSE);
		try {
			if(numExpediente != null){
				ConsultaEEFFDto consultaEEFFDto = getConsultaEEFFDto(numExpediente, Boolean.TRUE);
				if(consultaEEFFDto != null){
					if(EstadoConsultaEEFF.Finalizado.getValorEnum().equals(consultaEEFFDto.getEstado())){
						ResultadoConsultaEEFFDto resultadoConsultaEEFFDto = buildEEFF.getResultadoConsultaEEFF(consultaEEFFDto.getNumExpediente());
						if(resultadoConsultaEEFFDto != null){
							consultaEEFFDto.setResultadoConsultaEEFF(resultadoConsultaEEFFDto);
						}
					}
					resultado.addAttachment(CONSULTA_EEFF_DTO, consultaEEFFDto);
				} else { 
					resultado.setError(Boolean.TRUE);
					resultado.setMensaje("No existen datos para el numero de expediente: " + numExpediente);
				}
			} else{
				resultado.setError(Boolean.TRUE);
				resultado.setMensaje("Debe de indicar el numero de expediente para obtener su detalle.");
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de obtener el detalle de la consulta EEFF del expediente: " + numExpediente,e, numExpediente.toString());
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("Ha sucedido un error a la hora de obtener el detalle de la consulta EEFF.");
		}
		return resultado;
	}
	
	@Override
	@Transactional
	public ResultBean guardarConsultaEEFF(ConsultaEEFFDto consultaEEFF) {
		ResultBean resultado = new ResultBean(Boolean.FALSE);
		try {
			resultado  = validarDatosMinimosGuardadoConsultaEEFF(consultaEEFF);
			if(!resultado.getError()){
				ConsultaEEFFVO consultaEEFFVO = conversor.transform(consultaEEFF, ConsultaEEFFVO.class);
				if(consultaEEFFVO.getNumExpediente() == null){
					consultaEEFFVO.setNumExpediente(new BigDecimal(
							consultaEEFFDao.generarNumExpediente(new BigDecimal(consultaEEFFVO.getNumColegiado()))));
					consultaEEFFVO.setFechaAlta(new Date());
				}
				consultaEEFFVO.setEstado(new BigDecimal(EstadoConsultaEEFF.Iniciado.getValorEnum()));
				consultaEEFFDao.guardarOActualizar(consultaEEFFVO);
				resultado.addAttachment(NUM_EXPEDIENTE_CONSULTA_EEFF_DTO, consultaEEFFVO.getNumExpediente());
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de guardar la consulta EEFF, error: ",e);
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("Ha sucedido un error a la hora de guardar la consulta EEFF.");
		}
		if(resultado.getError()){
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		}
		return resultado;
	}

	public ResultBean validarDatosMinimosGuardadoConsultaEEFF(ConsultaEEFFDto consultaEEFF) {
		ResultBean resultBean = new ResultBean(Boolean.FALSE);
		if(consultaEEFF == null){
			resultBean.setError(Boolean.TRUE);
			resultBean.setMensaje("No existen datos de la consulta para guardar.");
		} else if(consultaEEFF.getContrato() == null || consultaEEFF.getContrato().getIdContrato() == null){
			resultBean.setError(Boolean.TRUE);
			resultBean.setMensaje("No existen datos del contrato de la consulta para guardar.");
		} else if(!EstadoConsultaEEFF.Iniciado.getValorEnum().equals(consultaEEFF.getEstado())
				&& !EstadoConsultaEEFF.Finalizado_Con_Error.getValorEnum().equals(consultaEEFF.getEstado())){
			resultBean.setError(Boolean.TRUE);
			resultBean.setMensaje("Solo se pueden modificar consultas EEFF en estado Iniciado o Finalizado Con Error .");
		} else {
			if(consultaEEFF.getNumColegiado() == null || consultaEEFF.getNumColegiado().isEmpty()){
				ContratoDto contratoDto = servicioContrato.getContratoDto(consultaEEFF.getContrato().getIdContrato());
				if(contratoDto != null && contratoDto.getColegiadoDto() != null 
					&& contratoDto.getColegiadoDto().getNumColegiado() != null && !contratoDto.getColegiadoDto().getNumColegiado().isEmpty()){
					consultaEEFF.setNumColegiado(contratoDto.getColegiadoDto().getNumColegiado());
				} else {
					resultBean.setError(Boolean.TRUE);
					resultBean.setMensaje("No existen datos del contrato seleccionado.");
				}
			}
		}
		return resultBean;
	}
	
	@Override
	@Transactional
	public ResultBean consultarEEFF(BigDecimal numExpediente, BigDecimal idUsuario, Boolean esConsulta) {
		ResultBean resultado = new ResultBean(Boolean.FALSE);
		try {
			ConsultaEEFFVO consultaEEFFVO = getConsultaEEFFVO(numExpediente,Boolean.TRUE);
			resultado  = validarDatosConsultaEEFF(consultaEEFFVO,esConsulta);
			if(!resultado.getError()){
				resultado = comprobarCreditosConsultaEEFF(numExpediente, new BigDecimal(consultaEEFFVO.getIdContrato()), idUsuario);
				if(!resultado.getError()){
					consultaEEFFVO.setFechaRealizacion(new Date());
					consultaEEFFVO.setRealizado(Boolean.FALSE);
					consultaEEFFVO.setEstado(new BigDecimal(EstadoConsultaEEFF.Pdte_Respuesta.getValorEnum()));
					consultaEEFFDao.actualizar(consultaEEFFVO);
					resultado = solicitarConsulta(consultaEEFFVO,idUsuario);
				}
			}
		} catch (Throwable e) {
			log.error("Ha sucedido un error a la hora de realizar la consulta EEFF, error: ",e, numExpediente.toString());
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("Ha sucedido un error a la hora de realizar la consulta EEFF.");
		}
		if(resultado.getError()){
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		} else {
			resultado.addAttachment(NUM_EXPEDIENTE_CONSULTA_EEFF_DTO, numExpediente);
		}
		return resultado;
	}

	@Override
	@Transactional(readOnly=true)
	public ConsultaEEFFVO getConsultaEEFFVO(BigDecimal numExpediente, Boolean consultaCompleta) {
		try {
			if(numExpediente != null){
				return consultaEEFFDao.getConsultaEEFFPorNumExpediente(numExpediente,consultaCompleta);
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de obtener la consulta EEFF por el numExpediente: " + numExpediente + ", error: ",e, numExpediente.toString());
		}
		return null;
	}
	
	@Override
	@Transactional(readOnly=true)
	public ConsultaEEFFDto getConsultaEEFFDto(BigDecimal numExpediente, Boolean consultaCompleta) {
		try {
			if(numExpediente != null){
				ConsultaEEFFVO consultaEEFFVO = getConsultaEEFFVO(numExpediente, consultaCompleta);
				if(consultaEEFFVO != null){
					return conversor.transform(consultaEEFFVO, ConsultaEEFFDto.class);
				}
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de obtener la consulta EEFF por el numExpediente: " + numExpediente + ", error: ",e, numExpediente.toString());
		}
		return null;
	}

	private ResultBean solicitarConsulta(ConsultaEEFFVO consultaEEFFVO, BigDecimal idUsuario) throws OegamExcepcion {
		ResultBean resultado = new ResultBean(Boolean.FALSE);
		Boolean esCertColegiadoCorrecto = comprobarCaducidadCertificado(consultaEEFFVO.getContrato().getColegiado().getAlias());
		if(esCertColegiadoCorrecto){
			SolicitudRegistroEntrada solicitudRegistroEntrada = buildEEFF.obtenerSolicitudConsultaEEFF(consultaEEFFVO);
			resultado = buildEEFF.realizarFirmaConsulta(solicitudRegistroEntrada,consultaEEFFVO.getContrato().getColegiado().getAlias(),consultaEEFFVO.getNumExpediente());
			if(!resultado.getError()){
				String nombreXml = (String) resultado.getAttachment(NOMBRE_XML);
				ResultBean result = servicioCola.crearSolicitud(ProcesosEnum.PROCESO_EEFF.getNombreEnum(), nombreXml, gestorPropiedades.valorPropertie(NOMBRE_HOST_SOLICITUD), 
						TipoTramiteTrafico.consultaEEFF.getValorEnum(), consultaEEFFVO.getNumExpediente().toString(), idUsuario, null, new BigDecimal(consultaEEFFVO.getIdContrato()));
				if(result.getError()){
					resultado.setListaMensajes(result.getListaMensajes());
					resultado.setError(Boolean.TRUE);
				}
			}
		} else {
			resultado.setError(Boolean.TRUE);
			resultado.addMensajeALista("El certificado del colegiado se encuentra caducado.");
		}
		return resultado;
	}

	private Boolean comprobarCaducidadCertificado(String aliasColegiado) {
		Boolean esOk = Boolean.FALSE;
		SolicitudPruebaCertificado solicitudPruebaCertificado = buildEEFF.obtenerSolicitudPruebaCertificado(aliasColegiado);
		XMLPruebaCertificado xmlPruebaCertificado = new XMLPruebaCertificado();
		String xml = xmlPruebaCertificado.toXMLSolicitudPruebaCert(solicitudPruebaCertificado);
		UtilesViafirma utilesViafirma = new UtilesViafirma();
		String idFirma = utilesViafirma.firmarPruebaCertificadoCaducidad(xml, aliasColegiado);
		if (idFirma != null) {
			esOk = Boolean.TRUE;
		}
		return esOk;
	}
	
	private ResultBean validarDatosConsultaEEFF(ConsultaEEFFVO consultaEEFFVO, Boolean esConsulta) {
		ResultBean resultBean = new ResultBean(Boolean.FALSE);
		if(consultaEEFFVO == null){
			resultBean.setError(Boolean.TRUE);
			resultBean.addMensajeALista("No existen datos de la consulta EEFF.");
		} else {
			List<String> listaErrores = new ArrayList<>();
			if(!esConsulta){
				listaErrores.add("Se han encontrado los siguientes errores a la hora de validar la consulta EEFF:");
			}
			if(consultaEEFFVO.getTarjetaBastidor() == null || consultaEEFFVO.getTarjetaBastidor().isEmpty()){
				listaErrores.add("- No existen datos del bastidor.");
				resultBean.setError(Boolean.TRUE);
			}
			if(consultaEEFFVO.getTarjetaNive() == null || consultaEEFFVO.getTarjetaNive().isEmpty()){
				listaErrores.add("- No existen datos del nive.");
				resultBean.setError(Boolean.TRUE);
			} else {
				if(consultaEEFFVO.getTarjetaNive().length() != EEFF_LONGITUD_NIVE){
					listaErrores.add("- La longitud del nive no es correcta.");
					resultBean.setError(Boolean.TRUE);
				}
			}
			if(consultaEEFFVO.getFirCif() == null || consultaEEFFVO.getFirCif().isEmpty()){
				listaErrores.add("- El CIF del FIR es obligatorio.");
				resultBean.setError(Boolean.TRUE);
			}
			if(consultaEEFFVO.getFirMarca() == null || consultaEEFFVO.getFirMarca().isEmpty()){
				listaErrores.add("- La marca del FIR es obligatoria.");
				resultBean.setError(Boolean.TRUE);
			}
			if(consultaEEFFVO.getNifRepresentado() == null || consultaEEFFVO.getNifRepresentado().isEmpty()){
				listaErrores.add("- El CIF del concesionario es obligatorio.");
				resultBean.setError(Boolean.TRUE);
			}
			if(resultBean.getError()){
				resultBean.setListaMensajes(listaErrores);
			}
		}
		return resultBean;
	}
	
	private ResultBean comprobarCreditosConsultaEEFF(BigDecimal numExpediente, BigDecimal idContrato, BigDecimal idUsuario) {
		ResultBean resultado = new ResultBean(false);
		try {
			String cobraCreditos = gestorPropiedades.valorPropertie(EEFF_COBRAR_CREDITOS_CONSULTA);
			if (cobraCreditos == null){
				resultado.setError(Boolean.TRUE);
				resultado.setMensaje("No se puede comprobar si tiene créditos suficientes");
			} else if (cobraCreditos.equalsIgnoreCase("true")){
				resultado = servicioCredito.validarCreditos(TipoTramiteTrafico.consultaEEFF.getValorEnum(), idContrato, new BigDecimal(-1));
				if (!resultado.getError()) {
					// Descontar creditos
					resultado = servicioCredito.descontarCreditos(TipoTramiteTrafico.consultaEEFF.getValorEnum(), idContrato, new BigDecimal(-1),
							idUsuario, ConceptoCreditoFacturado.EFC, numExpediente.toString());
				}
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de comprobar los creditos para realizar la consulta EEFF, error: ",e, numExpediente.toString());
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("Ha sucedido un error a la hora de comprobar los creditos para realizar la consulta EEFF.");
		}
		return resultado;
	}
	
	@Override
	@Transactional
	public ResultBean guardarConsultaEEFFProceso(ConsultaEEFFDto consultaEEFFDto) {
		ResultBean resultado = new ResultBean(Boolean.TRUE);
		try {
			if(consultaEEFFDto != null){
				ConsultaEEFFVO consultaEEFFVO = conversor.transform(consultaEEFFDto, ConsultaEEFFVO.class);
				if(consultaEEFFVO != null) {
					consultaEEFFDao.actualizar(consultaEEFFVO);
				}
			} else {
				resultado.setError(Boolean.TRUE);
				resultado.setMensaje("No existen datos de la consulta EEFF para actualizar.");
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de actualizar la consulta EEFF, error: ",e);
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("Ha sucedido un error a la hora de actualizar la consulta EEFF.");
		}
		return resultado;
	}
	
	@Override
	@Transactional
	public ResultBean cambiarEstadoConsultaEEFF(BigDecimal numExpediente, EstadoConsultaEEFF estadoNuevo, BigDecimal idUsuario, Boolean accionesAsociadas) {
		ResultBean resultado =  new ResultBean(Boolean.FALSE);
		try {
			ConsultaEEFFVO consultaEEFFVO = getConsultaEEFFVO(numExpediente, Boolean.FALSE);
			if(consultaEEFFVO != null){
				if(EstadoConsultaEEFF.Anulada.getValorEnum().equals(consultaEEFFVO.getEstado().toString())){
					resultado.setError(Boolean.TRUE);
					resultado.setMensaje("La consulta EEFF: " + consultaEEFFVO.getNumExpediente() + " se encuentra en estado anulada y no se podran realizar acciones sobre ella.");
					return resultado;
				}
				BigDecimal estadoAnt = consultaEEFFVO.getEstado();
				consultaEEFFVO.setEstado(new BigDecimal(estadoNuevo.getValorEnum()));
				consultaEEFFDao.actualizar(consultaEEFFVO);
				if(accionesAsociadas){
					resultado = accionesAsociadasCambioEstadoConsultaEEFF(consultaEEFFVO, idUsuario, estadoAnt.toString(), estadoNuevo.getValorEnum());
				}
			} else {
				resultado.setError(Boolean.TRUE);
				resultado.setMensaje("No existen datos para cambiar el estado de la consulta EEFF: " + numExpediente);
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de cambiar el estado de la consulta EEFF: " + numExpediente + ",error: ",e, numExpediente.toString());
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("Ha sucedido un error a la hora de cambiar el estado de la consulta EEFF: " + numExpediente);
		}
		return resultado;
	}

	private ResultBean accionesAsociadasCambioEstadoConsultaEEFF(ConsultaEEFFVO consultaEEFFVO, BigDecimal idUsuario, String estadoAnt, String estadoNuevo) {
		ResultBean resultado = new ResultBean(Boolean.FALSE);
		try {
			if(EstadoConsultaEEFF.Pdte_Respuesta.getValorEnum().equals(estadoAnt)){
				servicioCola.eliminar(consultaEEFFVO.getNumExpediente(), ProcesosEnum.EEFF.getNombreEnum());
			}else if(EstadoConsultaEEFF.Anulada.getValorEnum().equals(estadoAnt.toString())){
				resultado.setError(Boolean.TRUE);
				resultado.setMensaje("La consulta EEFF: " + consultaEEFFVO.getNumExpediente() + ", se encuentra en estado anulado y no se podran realizar acciones sobre ella.");
			} else if(EstadoConsultaEEFF.Pdte_Respuesta.getValorEnum().equals(estadoNuevo)){
				resultado = solicitarConsulta(consultaEEFFVO,idUsuario); 
			}
		} catch (Throwable e) {
			log.error("Ha sucedido un error a la hora de realizar las acciones asociadas al cambio de estado de la consulta EEFF: " + consultaEEFFVO.getNumExpediente() + ", error: ",e);
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("Ha sucedido un error a la hora de realizar las acciones asociadas al cambio de estado de la consulta EEFF: " + consultaEEFFVO.getNumExpediente());
		}
		return resultado;
	}
	
	@Override
	public ResultBean validarEeffLibMatwFORMATOGA(trafico.beans.jaxb.matw.FORMATOGA.MATRICULACION faMa) {
		ResultBean resultado = new ResultBean();
		if (faMa.getEXENTOLIBERAR() != null && faMa.getEXENTOLIBERAR().equals("NO")) {
			ResultBean rs = validarEeffLibFORMATOGA(faMa.getDATOSLIBERACION(),faMa.getDATOSVEHICULO().getNIVE(), faMa.getDATOSVEHICULO()
							.getNUMEROBASTIDOR());
			resultado = rs;
		} else if (faMa.getEXENTOLIBERAR() != null && faMa.getEXENTOLIBERAR().equals("SI")) {
			ResultBean rs = validarEeffLibFORMATOGAExento(faMa.getDATOSLIBERACION(), faMa.getDATOSVEHICULO().getNIVE(), faMa.getDATOSVEHICULO().getNUMEROBASTIDOR());
			for (String mensaje : rs.getListaMensajes()) {
				resultado.addMensajeALista(mensaje);
			}
		}
		return resultado;
	}
	
	private ResultBean validarEeffLibFORMATOGAExento(DATOSLIBERACION datosLiberacion, String nive, String numerobastidor) {
		ResultBean resultado = new ResultBean();
		if (datosLiberacion != null && datosLiberacion.getCIFFIR() != null && !datosLiberacion.getCIFFIR().isEmpty()) {
			resultado.addMensajeALista("El trámite está exento de liberar, pero los datos de liberación están rellenos. No se guardarán estos datos.");
		}
		return resultado;
	}
	
	private ResultBean validarEeffLibFORMATOGA(DATOSLIBERACION datosLiberacion,String nive, String bastidor) {
		ResultBean resultado = new ResultBean(Boolean.FALSE);
		if (nive == null || nive.isEmpty()) {
			log.debug("Vehículo no exento de liberar pero no estan rellenos los datos");
			resultado.setError(true);
			resultado.addMensajeALista(ConstantesEEFF.EEFF_TEXTO_ERROR_IMPO_VALI_NIVE);
		}
		if (bastidor == null || bastidor.isEmpty()) {
			log.debug("Vehículo no exento de liberar pero no estan rellenos los datos");
			resultado.setError(true);
			resultado.addMensajeALista(ConstantesEEFF.EEFF_TEXTO_ERROR_IMPO_VALI_BASTIDOR);
		}
		
		if(datosLiberacion==null || datosLiberacion.getNIFREPRESENTADO() == null || datosLiberacion.getNIFREPRESENTADO().isEmpty()){
			log.debug("Vehículo no exento de liberar pero no estan rellenos los datos");
			resultado.setError(true);
			resultado.addMensajeALista(ConstantesEEFF.EEFF_TEXTO_ERROR_CIF_CONCESIONARIO);
		}
		return resultado;
	}
	
	
	@Override
	@Transactional
	public ResultBean guardarDatosImportacion(trafico.beans.jaxb.matw.FORMATOGA.MATRICULACION formatoMatriculacion, BigDecimal numExpediente) {
		ResultBean resultado = new ResultBean();
		try {
			resultado = validarEeffLibFORMATOGA(formatoMatriculacion.getDATOSLIBERACION(), 
					formatoMatriculacion.getDATOSVEHICULO().getNIVE(), formatoMatriculacion.getDATOSVEHICULO().getNUMEROBASTIDOR());
			if(!resultado.getError()){
				LiberacionEEFFVO liberacionEEFFVO = new LiberacionEEFFVO(); 
				conversor.transform(formatoMatriculacion, liberacionEEFFVO);
				liberacionEEFFVO.setNumExpediente(numExpediente);
				if(formatoMatriculacion.getDATOSLIBERACION().getFECHAFACTURA() != null && !formatoMatriculacion.getDATOSLIBERACION().getFECHAFACTURA().isEmpty()){
					liberacionEEFFVO.setFechaFactura( new SimpleDateFormat("dd/MM/yyyy").parse(formatoMatriculacion.getDATOSLIBERACION().getFECHAFACTURA()));
				}
				if (formatoMatriculacion.getDATOSLIBERACION().getIMPORTE() != null) {
					liberacionEEFFVO.setImporte(new BigDecimal(formatoMatriculacion.getDATOSLIBERACION().getIMPORTE()));
				}
				if(formatoMatriculacion.getEXENTOLIBERAR() != null && formatoMatriculacion.getEXENTOLIBERAR().equals("NO")){
					liberacionEEFFVO.setExento(Boolean.FALSE);
				} else {
					liberacionEEFFVO.setExento(Boolean.TRUE);
					resultado.addMensajeALista("Vehículo exento de liberar");
				}
				liberacionEEFFVO.setRealizado(Boolean.FALSE);
				liberacionEEFFDao.guardar(liberacionEEFFVO);
			}
		} catch (Exception e){
			log.error("Ha sucedido un error a la hora de importar los datos de liberacion para el expediente: " + formatoMatriculacion.getNUMEROEXPEDIENTE(), e);
			resultado.setError(Boolean.TRUE);
			resultado.addMensajeALista("Ha sucedido un error a la hora de importar los datos de liberacion para el expediente: " + formatoMatriculacion.getNUMEROEXPEDIENTE());
		}
		if(resultado.getError()){
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		}
		return resultado;
	}

	@Override
	@Transactional
	public ResultBean realizarConsultaEEFFTramiteTrafico(BigDecimal numExpedienteTramite, BigDecimal idUsuario) {
		ResultBean resultado = new ResultBean(Boolean.FALSE);
		try {
			if(numExpedienteTramite != null){
				TramiteTrafMatrDto tramiteTrafMatrDto = servicioTramiteTraficoMatriculacion.getTramiteMatriculacion(numExpedienteTramite, Boolean.FALSE, Boolean.TRUE);
				if(tramiteTrafMatrDto != null){
					resultado = validarDatosLiberacionTramiteTraf(tramiteTrafMatrDto);
					if(!resultado.getError()){
						ConsultaEEFFVO consultaEEFFVO = conversor.transform(tramiteTrafMatrDto, ConsultaEEFFVO.class, "tramiteTrafMatrToConsultaEEFF");
						Date fechaAlta =  new Date();
						consultaEEFFVO.setFechaAlta(fechaAlta);
						consultaEEFFVO.setEstado(new BigDecimal(EstadoConsultaEEFF.Iniciado.getValorEnum()));
						consultaEEFFDao.guardar(consultaEEFFVO);
						resultado = solicitarConsulta(consultaEEFFVO, idUsuario);
					}
				} else {
					resultado.setError(Boolean.TRUE);
					resultado.setMensaje("Para el tramite: " + numExpedienteTramite + ", no se puede realizar la consulta EEFF porque no es un tramite de matriculación.");
				}
			} else {
				resultado.setError(Boolean.TRUE);
				resultado.setMensaje("Debe de indicar el numero de tramite para el que quiere realizar la consulta EEFF");
			}
		} catch (Throwable e) {
			log.error("Ha sucedido un error al realizar la consulta EEFF para el tramite: " + numExpedienteTramite + ", error: ",e);
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("Ha sucedido un error al realizar la consulta EEFF para el tramite: " + numExpedienteTramite);
		}
		if(resultado.getError()){
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		}
		return resultado;
	}

	private ResultBean validarDatosLiberacionTramiteTraf(TramiteTrafMatrDto tramiteTrafMatrDto) {
		ResultBean resultBean = new ResultBean(Boolean.FALSE);
		if(tramiteTrafMatrDto.getVehiculoDto() == null){
			resultBean.setMensaje("La consulta EEFF para el tramite: " + tramiteTrafMatrDto.getNumExpediente() + " no se puede realizar porque no existen datos del vehículo.");
			resultBean.setError(Boolean.TRUE);
		} else if(tramiteTrafMatrDto.getLiberacionEEFF() == null){
			resultBean.setMensaje("La consulta EEFF para el tramite: " + tramiteTrafMatrDto.getNumExpediente() + " no se puede realizar porque no existen datos de la liberación.");
			resultBean.setError(Boolean.TRUE);
		} else if(tramiteTrafMatrDto.getVehiculoDto().getBastidor() == null || tramiteTrafMatrDto.getVehiculoDto().getBastidor().isEmpty()){
			resultBean.setMensaje("La consulta EEFF para el tramite: " + tramiteTrafMatrDto.getNumExpediente() + " no se puede realizar porque no existen datos del bastidor.");
			resultBean.setError(Boolean.TRUE);
		} else if(tramiteTrafMatrDto.getVehiculoDto().getNive() == null || tramiteTrafMatrDto.getVehiculoDto().getNive().isEmpty()){
			resultBean.setMensaje("La consulta EEFF para el tramite: " + tramiteTrafMatrDto.getNumExpediente() + " no se puede realizar porque no existen datos del nive.");
			resultBean.setError(Boolean.TRUE);
		} else if(tramiteTrafMatrDto.getVehiculoDto().getNive().length() != EEFF_LONGITUD_NIVE){
			resultBean.setMensaje("La consulta EEFF para el tramite: " + tramiteTrafMatrDto.getNumExpediente() + " no se puede realizar porque la longitud del nive no es correcta.");
			resultBean.setError(Boolean.TRUE);
		} else if(tramiteTrafMatrDto.getLiberacionEEFF().getFirCif() == null || tramiteTrafMatrDto.getLiberacionEEFF().getFirCif().isEmpty()){
			resultBean.setMensaje("La consulta EEFF para el tramite: " + tramiteTrafMatrDto.getNumExpediente() + " no se puede realizar porque el CIF del FIR es obligatorio.");
			resultBean.setError(Boolean.TRUE);
		} else if(tramiteTrafMatrDto.getLiberacionEEFF().getFirMarca() == null || tramiteTrafMatrDto.getLiberacionEEFF().getFirMarca().isEmpty()){
			resultBean.setMensaje("La consulta EEFF para el tramite: " + tramiteTrafMatrDto.getNumExpediente() + " no se puede realizar porque la marca del FIR es obligatoria.");
			resultBean.setError(Boolean.TRUE);
		} else if(tramiteTrafMatrDto.getLiberacionEEFF().getNifRepresentado() == null || tramiteTrafMatrDto.getLiberacionEEFF().getNifRepresentado().isEmpty()){
			resultBean.setMensaje("La consulta EEFF para el tramite: " + tramiteTrafMatrDto.getNumExpediente() + " no se puede realizar porque l CIF del concesionario es obligatorio.");
			resultBean.setError(Boolean.TRUE);
		}
		return resultBean;
	}
}
