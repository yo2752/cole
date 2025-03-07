package org.gestoresmadrid.oegam2comun.modeloCAYC.model.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

import org.gestoresmadrid.core.modelo600_601.model.dao.ResultadoModelo600601Dao;
import org.gestoresmadrid.core.modelo600_601.model.vo.Modelo600_601VO;
import org.gestoresmadrid.core.modelo600_601.model.vo.ResultadoModelo600601VO;
import org.gestoresmadrid.core.modelos.model.enumerados.ErroresWSModelo600601;
import org.gestoresmadrid.oegam2comun.modelo600_601.view.dto.Modelo600_601Dto;
import org.gestoresmadrid.oegam2comun.modelo600_601.view.dto.ResultadoModelo600601Dto;
import org.gestoresmadrid.oegam2comun.modelo600_601.view.dto.ResultadoModelos;
import org.gestoresmadrid.oegam2comun.modeloCAYC.model.service.ServicioResultadoModeloCAYC;
import org.gestoresmadrid.oegamComun.conversor.Conversor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import es.globaltms.gestorDocumentos.constantes.ConstantesGestorFicheros;
import es.globaltms.gestorDocumentos.interfaz.GestorDocumentos;
import es.globaltms.gestorDocumentos.util.Utilidades;
import escrituras.beans.ResultBean;
import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;
import utilidades.web.OegamExcepcion;

@Service
public class ServicioResultadoModeloCAYCImpl implements ServicioResultadoModeloCAYC{

	private static final long serialVersionUID = -3017057734371398650L;
	
	private static final ILoggerOegam log = LoggerOegam.getLogger(ServicioResultadoModeloCAYCImpl.class);
	
	@Autowired
	private ResultadoModelo600601Dao resultadoModelo600601Dao;
	
	@Autowired
	private GestorDocumentos gestorDocumentos;
	
	@Autowired
	private Conversor conversor;
	
	@Override
	@Transactional
	public ResultBean guardarResultadoSubSanable(Modelo600_601VO modelo600601VO, Date fechaEjecucion,String respuesta) {
		ResultBean resultado = new ResultBean(false);
		try {
			if(modelo600601VO != null && fechaEjecucion != null && respuesta != null && !respuesta.isEmpty()){
				ResultadoModelo600601VO resultadoModelo600601VO = new ResultadoModelo600601VO();
				resultadoModelo600601VO.setModelo600601(modelo600601VO);
				resultadoModelo600601VO.setFechaEjecucion(fechaEjecucion);
				resultadoModelo600601VO.setCodigoResultado(respuesta);
				resultadoModelo600601Dao.guardar(resultadoModelo600601VO);
			}else{
				resultado.setError(true);
				resultado.addMensajeALista("No se puede guardar la respuesta del WS porque faltan datos obligatorios.");
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de guardar el resultado de la llamada al WS, error: ",e);
			resultado.setError(true);
			resultado.addMensajeALista("Ha sucedido un error a la hora de guardar el resultado de la llamada al WS.");
		}
		return resultado;
	}
	
	@Override
	@Transactional
	public ResultBean guardarResultadoOk(Modelo600_601VO modelo600601VO, ResultadoModelos resultLlamadaWS) {
		ResultBean resultado = new ResultBean(false);
		String identificadorDoc = null; 
		try {
			if(modelo600601VO != null && resultLlamadaWS != null){
				ResultadoModelo600601VO resultadoModelo600601VO = rellenarDatosResultadoModelos(modelo600601VO,resultLlamadaWS);
				if(resultadoModelo600601VO.getJustificante() != null && !resultadoModelo600601VO.getJustificante().isEmpty()){
					identificadorDoc = modelo600601VO.getIdModelo() + "_"+ resultadoModelo600601VO.getJustificante();
					resultadoModelo600601VO.setCartaPago(identificadorDoc);
					resultadoModelo600601VO.setDiligencia(identificadorDoc);
				}
				resultadoModelo600601Dao.guardar(resultadoModelo600601VO);
				if(identificadorDoc != null && !identificadorDoc.isEmpty()){
					resultado.addAttachment("identificadorDoc", identificadorDoc);
				}
			}else{
				resultado.setError(true);
				resultado.addMensajeALista("No se puede guardar la respuesta del WS porque faltan datos obligatorios.");
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de guardar el resultado de la llamada al WS, error: ",e);
			resultado.setError(true);
			resultado.addMensajeALista("Ha sucedido un error a la hora de guardar el resultado de la llamada al WS.");
		}
		return resultado;
	}

	private ResultadoModelo600601VO rellenarDatosResultadoModelos(Modelo600_601VO modelo600601VO,ResultadoModelos resultLlamadaWS) {
		ResultadoModelo600601VO resultadoModelo600601VO = new ResultadoModelo600601VO();
		resultadoModelo600601VO.setModelo600601(modelo600601VO);
		resultadoModelo600601VO.setFechaEjecucion(resultLlamadaWS.getFechaEjecucion());
		resultadoModelo600601VO.setCodigoResultado(resultLlamadaWS.getErrorWS().getValorEnum());
		if(!resultLlamadaWS.isError()){
			resultadoModelo600601VO.setNccm((String) resultLlamadaWS.getAttachment("nccm"));
			resultadoModelo600601VO.setCso((String)resultLlamadaWS.getAttachment("cso"));
			resultadoModelo600601VO.setFechaPresentacion((Date) resultLlamadaWS.getAttachment("fechaPresentacion"));
			resultadoModelo600601VO.setExpedienteCAM((String)resultLlamadaWS.getAttachment("expedienteCam"));
			String justificante = (String) resultLlamadaWS.getAttachment("justificante");
			if(justificante != null && !justificante.isEmpty()){
				resultadoModelo600601VO.setJustificante(justificante);
			}
		}
		return resultadoModelo600601VO;
	}
	
	@Override
	public String getTextoCodigoResultado(String codigo) {
		try {
			if(codigo != null && !codigo.isEmpty()){
				return ErroresWSModelo600601.getNombrePorValor(codigo);
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de obtener la descripcion del codigo de resultado, e: ",e);
		}
		return "";
	}

	@Override
	public ResultBean convertirListaResultadoVoToDto(Modelo600_601VO modeloVO, Modelo600_601Dto modeloDto) {
		ResultBean resultado = null;
		try {
			if(modeloVO != null && modeloVO.getListaResultadoModelo() != null && !modeloVO.getListaResultadoModelo().isEmpty()){
				List<ResultadoModelo600601Dto> lista = new ArrayList<ResultadoModelo600601Dto>();
				for(ResultadoModelo600601VO resultadoModelo600601VO : modeloVO.getListaResultadoModelo()){
					lista.add(conversor.transform(resultadoModelo600601VO, ResultadoModelo600601Dto.class));
				}
				modeloDto.setListaResultadoModelo(lista);
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de convertir la lista resultado VO to Dto, error: ",e);
			resultado = new ResultBean(true);
			resultado.addMensajeALista("Ha sucedido un error a la hora de convertir la lista con el resumen de resultados.");
		}
		return resultado;
	}
	
	@Override
	@Transactional(readOnly=true)
	public ResultadoModelo600601Dto getResultadoModelo600601DtoPorId(Integer idResultadoModelo600601) {
		if(idResultadoModelo600601 != null){
			ResultadoModelo600601VO resultadoModelo600601VO = resultadoModelo600601Dao.getResultadoModeloPorId(idResultadoModelo600601);
			if(resultadoModelo600601VO != null){
				return conversor.transform(resultadoModelo600601VO, ResultadoModelo600601Dto.class);
			}
		}
		return null;
	}
	
	@Override
	@Transactional
	public ResultBean eliminarResuladosModelos(Set<ResultadoModelo600601VO> listaResultadoModelo, Long idModelo) {
		ResultBean resultado = new ResultBean(false);
		if(listaResultadoModelo != null && !listaResultadoModelo.isEmpty()){
			for(ResultadoModelo600601VO resultadoModelo600601VO : listaResultadoModelo){
				resultado = eliminarResultadoModelo600601VO(resultadoModelo600601VO);
			}
		}else if(idModelo != null){
			List<ResultadoModelo600601VO> lista = resultadoModelo600601Dao.getResultadoModeloByIdModelo600601(idModelo);
			if(lista != null && !lista.isEmpty()){
				for(ResultadoModelo600601VO resultadoModelo600601VO : lista){
					resultado = eliminarResultadoModelo600601VO(resultadoModelo600601VO);
				}
			}
		}
		return resultado;
	}

	private ResultBean eliminarResultadoModelo600601VO(ResultadoModelo600601VO resultadoModelo600601VO) {
		ResultBean resultado = new ResultBean(false);
		try {
			if(resultadoModelo600601VO.getCartaPago() != null && !resultadoModelo600601VO.getCartaPago().isEmpty()){
				gestorDocumentos.borraFicheroSiExiste(ConstantesGestorFicheros.MODELO_600_601, ConstantesGestorFicheros.PDF_MODELOS_600_601, 
						Utilidades.transformExpedienteFecha(resultadoModelo600601VO.getModelo600601().getNumExpediente()),resultadoModelo600601VO.getCartaPago());
			}
			if(resultadoModelo600601VO.getDiligencia() != null && !resultadoModelo600601VO.getDiligencia().isEmpty()){
				gestorDocumentos.borraFicheroSiExiste(ConstantesGestorFicheros.MODELO_600_601, ConstantesGestorFicheros.PDF_MODELOS_600_601, 
					Utilidades.transformExpedienteFecha(resultadoModelo600601VO.getModelo600601().getNumExpediente()),resultadoModelo600601VO.getDiligencia());
			}
			resultadoModelo600601Dao.borrar(resultadoModelo600601VO);
		} catch (OegamExcepcion e) {
			log.error("Ha sucedido un error a la hora de borrar la carta de pago y la diligencia");
			resultado.setError(true);
			resultado.addMensajeALista("Ha sucedido un error a la hora de borrar la carta de pago y la diligencia");
		}
		return resultado;
	}
}
