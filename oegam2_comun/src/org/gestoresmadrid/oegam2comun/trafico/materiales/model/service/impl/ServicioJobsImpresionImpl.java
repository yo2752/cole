package org.gestoresmadrid.oegam2comun.trafico.materiales.model.service.impl;

import java.math.BigDecimal;

import org.gestoresmadrid.core.docPermDistItv.model.vo.DocPermDistItvVO;
import org.gestoresmadrid.core.tipoPermDistItv.model.enumerado.TipoDocumentoImprimirEnum;
import org.gestoresmadrid.core.trafico.materiales.model.dao.JobDao;
import org.gestoresmadrid.core.trafico.materiales.model.vo.JobVO;
import org.gestoresmadrid.oegam2comun.permisoDistintivoItv.model.service.impl.ServicioDocPrmDstvFichaImpl;
import org.gestoresmadrid.oegam2comun.permisoDistintivoItv.view.bean.ResultadoPermisoDistintivoItvBean;
import org.gestoresmadrid.oegam2comun.trafico.materiales.model.service.ServicioJobsImpresion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;

@Service
public class ServicioJobsImpresionImpl implements ServicioJobsImpresion{

	private static final long serialVersionUID = 960090149795363754L;
	
	private static final ILoggerOegam log = LoggerOegam.getLogger(ServicioDocPrmDstvFichaImpl.class);
	
	@Autowired
	JobDao jobDao;
	
	@Override
	@Transactional
	public ResultadoPermisoDistintivoItvBean imprimirDistintivosJefatura(DocPermDistItvVO docPermDistItvVO, BigDecimal idUsuario, String jefaturaImpr) {
		ResultadoPermisoDistintivoItvBean resultado = new ResultadoPermisoDistintivoItvBean(Boolean.FALSE);
		try {
			if(docPermDistItvVO != null){
				if(TipoDocumentoImprimirEnum.DISTINTIVO.getValorEnum().equals(docPermDistItvVO.getTipo())){
					JobVO jobDistintivo = rellenarJobJefatura(docPermDistItvVO,idUsuario, TipoDocumentoImprimirEnum.DISTINTIVO.getValorEnum(),jefaturaImpr);
					JobVO jobJustificante = rellenarJobJustfJefatura(docPermDistItvVO,idUsuario, jefaturaImpr);
					jobDao.guardar(jobDistintivo);
					jobDao.guardar(jobJustificante);
				} else {
					resultado.setError(Boolean.TRUE);
					resultado.setMensaje("El documento que desea imprimir no es del tipo distintivo.");
				}
			} else {
				resultado.setError(Boolean.TRUE);
				resultado.setMensaje("No existen datos del documento con distintivos para imprimir.");
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de imprimir automaticamente los distintivos seleccionados, error: ",e);
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("Ha sucedido un error a la hora de imprimir automaticamente los distintivos seleccionados.");
		}
		return resultado;
	}
	
	@Override
	@Transactional
	public ResultadoPermisoDistintivoItvBean imprimirDistintivos(DocPermDistItvVO docPermDistItvVO, BigDecimal idUsuario, Boolean esColegio) {
		ResultadoPermisoDistintivoItvBean resultado = new ResultadoPermisoDistintivoItvBean(Boolean.FALSE);
		try {
			if(docPermDistItvVO != null){
				if(TipoDocumentoImprimirEnum.DISTINTIVO.getValorEnum().equals(docPermDistItvVO.getTipo())){
					JobVO jobDistintivo = rellenarJob(docPermDistItvVO,idUsuario, TipoDocumentoImprimirEnum.DISTINTIVO.getValorEnum(),esColegio);
					JobVO jobJustificante = rellenarJobJustf(docPermDistItvVO,idUsuario, esColegio);
					jobDao.guardar(jobDistintivo);
					jobDao.guardar(jobJustificante);
				} else {
					resultado.setError(Boolean.TRUE);
					resultado.setMensaje("El documento que desea imprimir no es del tipo distintivo.");
				}
			} else {
				resultado.setError(Boolean.TRUE);
				resultado.setMensaje("No existen datos del documento con distintivos para imprimir.");
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de imprimir automaticamente los distintivos seleccionados, error: ",e);
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("Ha sucedido un error a la hora de imprimir automaticamente los distintivos seleccionados.");
		}
		return resultado;
	}
	
	@Override
	@Transactional
	public ResultadoPermisoDistintivoItvBean imprimirPermisosJefatura(DocPermDistItvVO docPermDistItvVO, BigDecimal idUsuario, String jefaturaImpr) {
		ResultadoPermisoDistintivoItvBean resultado = new ResultadoPermisoDistintivoItvBean(Boolean.FALSE);
		try {
			if(docPermDistItvVO != null){
				if(TipoDocumentoImprimirEnum.PERMISO_CIRCULACION.getValorEnum().equals(docPermDistItvVO.getTipo())){
					JobVO jobPermiso = rellenarJobJefatura(docPermDistItvVO,idUsuario, TipoDocumentoImprimirEnum.PERMISO_CIRCULACION.getValorEnum(),jefaturaImpr);
					JobVO jobJustificante = rellenarJobJustfJefatura(docPermDistItvVO, idUsuario,jefaturaImpr);
					jobDao.guardar(jobPermiso);
					jobDao.guardar(jobJustificante);
				} else {
					resultado.setError(Boolean.TRUE);
					resultado.setMensaje("El documento que desea imprimir no es del tipo permiso de circulación.");
				}
			} else {
				resultado.setError(Boolean.TRUE);
				resultado.setMensaje("No existen datos del documento con permisos de circulacion para imprimir.");
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de imprimir automaticamente los permisos de circulacion seleccionados, error: ",e);
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("Ha sucedido un error a la hora de imprimir automaticamente los permisos de circulacion seleccionados.");
		}
		return resultado;
	}
	
	@Override
	@Transactional
	public ResultadoPermisoDistintivoItvBean imprimirPermisos(DocPermDistItvVO docPermDistItvVO, BigDecimal idUsuario, Boolean esColegio) {
		ResultadoPermisoDistintivoItvBean resultado = new ResultadoPermisoDistintivoItvBean(Boolean.FALSE);
		try {
			if(docPermDistItvVO != null){
				if(TipoDocumentoImprimirEnum.PERMISO_CIRCULACION.getValorEnum().equals(docPermDistItvVO.getTipo())){
					JobVO jobPermiso = rellenarJob(docPermDistItvVO,idUsuario, TipoDocumentoImprimirEnum.PERMISO_CIRCULACION.getValorEnum(),esColegio);
					JobVO jobJustificante = rellenarJobJustf(docPermDistItvVO, idUsuario,esColegio);
					jobDao.guardar(jobPermiso);
					jobDao.guardar(jobJustificante);
				} else {
					resultado.setError(Boolean.TRUE);
					resultado.setMensaje("El documento que desea imprimir no es del tipo permiso de circulación.");
				}
			} else {
				resultado.setError(Boolean.TRUE);
				resultado.setMensaje("No existen datos del documento con permisos de circulacion para imprimir.");
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de imprimir automaticamente los permisos de circulacion seleccionados, error: ",e);
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("Ha sucedido un error a la hora de imprimir automaticamente los permisos de circulacion seleccionados.");
		}
		return resultado;
	}
	
	@Override
	@Transactional
	public ResultadoPermisoDistintivoItvBean imprimirFichas(DocPermDistItvVO docPermDistItvVO, BigDecimal idUsuario, Boolean esColegio) {
		ResultadoPermisoDistintivoItvBean resultado = new ResultadoPermisoDistintivoItvBean(Boolean.FALSE);
		try {
			if(docPermDistItvVO != null){
				if(TipoDocumentoImprimirEnum.FICHA_TECNICA.getValorEnum().equals(docPermDistItvVO.getTipo())){
					JobVO jobFicha = rellenarJob(docPermDistItvVO,idUsuario, TipoDocumentoImprimirEnum.FICHA_TECNICA.getValorEnum(),esColegio);
					JobVO jobJustificante = rellenarJobJustf(docPermDistItvVO, idUsuario,esColegio);
					jobDao.guardar(jobFicha);
					jobDao.guardar(jobJustificante);
				} else {
					resultado.setError(Boolean.TRUE);
					resultado.setMensaje("El documento que desea imprimir no es del tipo ficha técnica.");
				}
			} else {
				resultado.setError(Boolean.TRUE);
				resultado.setMensaje("No existen datos del documento con fichas técnicas para imprimir.");
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de imprimir automaticamente las fichas técnicas seleccionadas, error: ",e);
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("Ha sucedido un error a la hora de imprimir automaticamente las fichas técnicas seleccionadas.");
		}
		return resultado;
	}
	
	@Override
	@Transactional
	public ResultadoPermisoDistintivoItvBean imprimirFichasJefatura(DocPermDistItvVO docPermDistItvVO, BigDecimal idUsuario, String jefaturaImpr) {
		ResultadoPermisoDistintivoItvBean resultado = new ResultadoPermisoDistintivoItvBean(Boolean.FALSE);
		try {
			if(docPermDistItvVO != null){
				if(TipoDocumentoImprimirEnum.FICHA_TECNICA.getValorEnum().equals(docPermDistItvVO.getTipo())){
					JobVO jobFicha = rellenarJobJefatura(docPermDistItvVO,idUsuario, TipoDocumentoImprimirEnum.FICHA_TECNICA.getValorEnum(),jefaturaImpr);
					JobVO jobJustificante = rellenarJobJustfJefatura(docPermDistItvVO, idUsuario,jefaturaImpr);
					jobDao.guardar(jobFicha);
					jobDao.guardar(jobJustificante);
				} else {
					resultado.setError(Boolean.TRUE);
					resultado.setMensaje("El documento que desea imprimir no es del tipo ficha técnica.");
				}
			} else {
				resultado.setError(Boolean.TRUE);
				resultado.setMensaje("No existen datos del documento con fichas técnicas para imprimir.");
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de imprimir automaticamente las fichas técnicas seleccionadas, error: ",e);
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("Ha sucedido un error a la hora de imprimir automaticamente las fichas técnicas seleccionadas.");
		}
		return resultado;
	}
	
	@Override
	@Transactional
	public ResultadoPermisoDistintivoItvBean imprimirPermisosFichas(DocPermDistItvVO docPermDistItvVO, BigDecimal idUsuario, Boolean esColegio) {
		ResultadoPermisoDistintivoItvBean resultado = new ResultadoPermisoDistintivoItvBean(Boolean.FALSE);
		try {
			if(docPermDistItvVO != null){
				if(TipoDocumentoImprimirEnum.FICHA_PERMISO.getValorEnum().equals(docPermDistItvVO.getTipo())){
					JobVO jobPermiso = rellenarJob(docPermDistItvVO,idUsuario, TipoDocumentoImprimirEnum.PERMISO_CIRCULACION.getValorEnum(),esColegio);
					JobVO jobFicha = rellenarJob(docPermDistItvVO,idUsuario, TipoDocumentoImprimirEnum.FICHA_TECNICA.getValorEnum(),esColegio);
					JobVO jobJustificante = rellenarJobJustf(docPermDistItvVO, idUsuario,esColegio);
					jobDao.guardar(jobPermiso);
					jobDao.guardar(jobFicha);
					jobDao.guardar(jobJustificante);
				} else {
					resultado.setError(Boolean.TRUE);
					resultado.setMensaje("El documento que desea imprimir no es del tipo fichas y permisos.");
				}
			} else {
				resultado.setError(Boolean.TRUE);
				resultado.setMensaje("No existen datos del documento con fichas y permisos para imprimir.");
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de imprimir automaticamente las fichas y permisos seleccionados, error: ",e);
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("Ha sucedido un error a la hora de imprimir automaticamente las fichas y permisos seleccionados.");
		}
		return resultado;
	}

	private JobVO rellenarJobJustf(DocPermDistItvVO docPermDistItvVO, BigDecimal idUsuario, Boolean esColegio) {
		JobVO jobVO = new JobVO();
		jobVO.setIdContrato(docPermDistItvVO.getContrato().getIdContrato());
		jobVO.setDocIdPerm(docPermDistItvVO.getDocIdPerm());
		jobVO.setDocDistintivo(docPermDistItvVO.getIdDocPermDistItv());
		jobVO.setEstado(new Long(0));
		jobVO.setFechaDocumento(docPermDistItvVO.getFechaAlta());
		jobVO.setJefatura(docPermDistItvVO.getJefatura());;
		jobVO.setTipoDistintivo(docPermDistItvVO.getTipoDistintivo());
		jobVO.setSubtipo(docPermDistItvVO.getTipo());
		jobVO.setTipoDocumento(TipoDocumentoImprimirEnum.JUSTIFICANTE.getValorEnum());
		jobVO.setIdUsuario(idUsuario.longValue());
		if(esColegio){
			jobVO.setColegio("S");
		} else {
			jobVO.setColegio("N");
		}
		return jobVO;
	}
	
	private JobVO rellenarJobJustfJefatura(DocPermDistItvVO docPermDistItvVO, BigDecimal idUsuario, String jefaturaImpr) {
		JobVO jobVO = new JobVO();
		jobVO.setIdContrato(docPermDistItvVO.getContrato().getIdContrato());
		jobVO.setDocIdPerm(docPermDistItvVO.getDocIdPerm());
		jobVO.setDocDistintivo(docPermDistItvVO.getIdDocPermDistItv());
		jobVO.setEstado(new Long(0));
		jobVO.setFechaDocumento(docPermDistItvVO.getFechaAlta());
		jobVO.setJefatura(jefaturaImpr);;
		jobVO.setTipoDistintivo(docPermDistItvVO.getTipoDistintivo());
		jobVO.setSubtipo(docPermDistItvVO.getTipo());
		jobVO.setTipoDocumento(TipoDocumentoImprimirEnum.JUSTIFICANTE.getValorEnum());
		jobVO.setIdUsuario(idUsuario.longValue());
		if("CO".equals(jefaturaImpr)){
			jobVO.setColegio("S");
		} else {
			jobVO.setColegio("N");
		}
		return jobVO;
	}

	private JobVO rellenarJob(DocPermDistItvVO docPermDistItvVO, BigDecimal idUsuario, String tipo, Boolean esColegio) {
		JobVO jobVO = new JobVO();
		jobVO.setIdContrato(docPermDistItvVO.getContrato().getIdContrato());
		jobVO.setDocIdPerm(docPermDistItvVO.getDocIdPerm());
		jobVO.setDocDistintivo(docPermDistItvVO.getIdDocPermDistItv());
		jobVO.setEstado(new Long(0));
		jobVO.setFechaDocumento(docPermDistItvVO.getFechaAlta());
		if(TipoDocumentoImprimirEnum.DISTINTIVO.getValorEnum().equals(docPermDistItvVO.getTipo())){
			jobVO.setTipoDistintivo(docPermDistItvVO.getTipoDistintivo());
		}
		jobVO.setTipoDocumento(tipo);
		jobVO.setIdUsuario(idUsuario.longValue());
		if(esColegio){
			jobVO.setColegio("S");
			jobVO.setJefatura("CO");
		} else {
			jobVO.setColegio("N");
			jobVO.setJefatura(docPermDistItvVO.getJefatura());
		}
		return jobVO;
	}
	

	private JobVO rellenarJobJefatura(DocPermDistItvVO docPermDistItvVO, BigDecimal idUsuario, String tipo, String jefaturaImpr) {
		JobVO jobVO = new JobVO();
		jobVO.setIdContrato(docPermDistItvVO.getContrato().getIdContrato());
		jobVO.setDocIdPerm(docPermDistItvVO.getDocIdPerm());
		jobVO.setDocDistintivo(docPermDistItvVO.getIdDocPermDistItv());
		jobVO.setEstado(new Long(0));
		jobVO.setFechaDocumento(docPermDistItvVO.getFechaAlta());
		if(TipoDocumentoImprimirEnum.DISTINTIVO.getValorEnum().equals(docPermDistItvVO.getTipo())){
			jobVO.setTipoDistintivo(docPermDistItvVO.getTipoDistintivo());
		}
		jobVO.setTipoDocumento(tipo);
		jobVO.setIdUsuario(idUsuario.longValue());
		jobVO.setJefatura(jefaturaImpr);
		if("CO".equals(jefaturaImpr)){
			jobVO.setColegio("S");
		} else {
			jobVO.setColegio("N");
		}
		return jobVO;
	}

}
