package org.gestoresmadrid.oegam2comun.licenciasCam.model.service.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.gestoresmadrid.core.licencias.model.dao.LcDocumentoLicenciaDao;
import org.gestoresmadrid.core.licencias.model.vo.LcDocumentoLicenciaVO;
import org.gestoresmadrid.oegam2comun.licenciasCam.gestor.GestorDatosMaestrosLic;
import org.gestoresmadrid.oegam2comun.licenciasCam.model.service.ServicioLcDocumentoLicencia;
import org.gestoresmadrid.oegam2comun.licenciasCam.view.bean.DatoMaestroLicBean;
import org.gestoresmadrid.oegam2comun.licenciasCam.view.bean.ResultadoLicenciasBean;
import org.gestoresmadrid.oegam2comun.licenciasCam.view.dto.FicheroInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import es.globaltms.gestorDocumentos.bean.FicheroBean;
import es.globaltms.gestorDocumentos.bean.FileResultBean;
import es.globaltms.gestorDocumentos.constantes.ConstantesGestorFicheros;
import es.globaltms.gestorDocumentos.interfaz.GestorDocumentos;
import es.globaltms.gestorDocumentos.util.Utilidades;
import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;
import utilidades.mensajes.Claves;
import utilidades.web.OegamExcepcion;

@Service
public class ServicioLcDocumentoLicenciaImpl implements ServicioLcDocumentoLicencia {

	private static final long serialVersionUID = 8345174133295434542L;

	private static final ILoggerOegam log = LoggerOegam.getLogger(ServicioLcDocumentoLicenciaImpl.class);

	@Autowired
	LcDocumentoLicenciaDao lcDocumentoLicenciaDao;

	@Autowired
	GestorDocumentos gestorDocumentos;

	@Autowired
	GestorDatosMaestrosLic gestorDatosMaestrosLic;

	@Transactional
	@Override
	public LcDocumentoLicenciaVO getLcDocumentoLicencia(Long idDocumentoLicencia) {
		try {
			return lcDocumentoLicenciaDao.getLcDocumentoLicencia(idDocumentoLicencia);
		} catch (Exception e) {
			log.error("Error al obtener los datos del documento licencia:" + idDocumentoLicencia, e);
		}
		return null;
	}

	@Transactional
	@Override
	public List<LcDocumentoLicenciaVO> getLcDocumentoLicenciaPorNumExp(BigDecimal numExpediente) {
		try {
			return lcDocumentoLicenciaDao.getLcDocumentoLicenciaPorNumExp(numExpediente);
		} catch (Exception e) {
			log.error("Error al obtener la lista de los documentos con numExpediente:" + numExpediente, e);
		}
		return null;
	}

	@Transactional
	@Override
	public ResultadoLicenciasBean guardar(BigDecimal numExpediente, Long idContrato, String tipoDocumento) {
		ResultadoLicenciasBean resultado = new ResultadoLicenciasBean(Boolean.FALSE);
		try {
			LcDocumentoLicenciaVO lcDocumentoLicenciaVO = new LcDocumentoLicenciaVO();
			lcDocumentoLicenciaVO.setNumExpediente(numExpediente);
			lcDocumentoLicenciaVO.setTipoDocumento(tipoDocumento);
			lcDocumentoLicenciaVO.setFechaAlta(new Date());
			lcDocumentoLicenciaVO.setIdContrato(idContrato);

			Long id = (Long) lcDocumentoLicenciaDao.guardar(lcDocumentoLicenciaVO);
			resultado.addAttachment(ID_DOCUMENTO_LICENCIA, id);
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de guardar los datos del documento de licencia, error: ", e);
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("Ha sucedido un error a la hora de guardar los datos del documento de licencia.");
		}
		return resultado;
	}

	@Transactional
	@Override
	public ResultadoLicenciasBean eliminar(String nombreFichero) {
		ResultadoLicenciasBean resultado = new ResultadoLicenciasBean(Boolean.FALSE);
		try {
			Long idDocumentoLicencia = obtenerIdDocumentoLicencia(nombreFichero);
			if (idDocumentoLicencia != null) {
				LcDocumentoLicenciaVO lcDocumentoLicenciaVO = new LcDocumentoLicenciaVO();
				lcDocumentoLicenciaVO.setIdDocumentoLicencia(idDocumentoLicencia);
				lcDocumentoLicenciaDao.borrar(lcDocumentoLicenciaVO);
			} else {
				resultado.setError(Boolean.TRUE);
				resultado.setMensaje("No se ha encontrado el identificador del documento");
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de eliminar el documento de licencia, error: ", e);
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("Ha sucedido un error a la hora de eliminar el documento de licencia.");
		}
		return resultado;
	}

	@Override
	public ArrayList<FicheroInfo> recuperarDocumentos(BigDecimal numExpediente) {
		ArrayList<FicheroInfo> ficherosSubidos = new ArrayList<FicheroInfo>();
		if (numExpediente != null) {
			try {
				String carpeta = ConstantesGestorFicheros.LICENCIAS_CAM;
				String subcarpeta = ConstantesGestorFicheros.DOCUMENTOS_LICENCIAS;
				FileResultBean ficheros = gestorDocumentos.buscarFicheroPorNumExpTipo(carpeta, subcarpeta, Utilidades.transformExpedienteFecha(numExpediente), numExpediente.toString());
				if (null != ficheros.getFiles() && !ficheros.getFiles().isEmpty()) {
					for (File temporal : ficheros.getFiles()) {
						FicheroInfo ficheroInfo = new FicheroInfo(temporal, 0);
						ficheroInfo.setTipo(obtenerTipo(ficheroInfo.getNombre()));
						ficheroInfo.setTipoDocumento(obtenerDescripcionTipoDocumento(ficheroInfo.getNombre()));
						ficherosSubidos.add(ficheroInfo);
					}
				}
			} catch (OegamExcepcion e) {
				log.error(Claves.CLAVE_LOG_LICENCIAS_COMUN_ERROR + " Error al recuperar los documentos " + e.getMessage(), e, numExpediente.toString());
			}
		}
		return ficherosSubidos;
	}

	@Override
	public File guardarFichero(File fileUpload, BigDecimal numExpediente, String extension, String tipoDocumento, Long idFichero) throws OegamExcepcion, IOException {
		FileInputStream fis = new FileInputStream(fileUpload);
		byte[] array = IOUtils.toByteArray(fis);
		FicheroBean ficheroBean = new FicheroBean();
		ficheroBean.setTipoDocumento(ConstantesGestorFicheros.LICENCIAS_CAM);
		ficheroBean.setSubTipo(ConstantesGestorFicheros.DOCUMENTOS_LICENCIAS);
		ficheroBean.setExtension("." + extension);
		ficheroBean.setFicheroByte(array);
		ficheroBean.setFecha(Utilidades.transformExpedienteFecha(numExpediente));
		ficheroBean.setSobreescribir(true);
		ficheroBean.setNombreDocumento(tipoDocumento + "_" + idFichero + "_" + numExpediente);
		File fichero = gestorDocumentos.guardarByte(ficheroBean);

		FileOutputStream fos = new FileOutputStream(fichero);
		fos.write(array);
		fos.close();

		return fichero;
	}

	@Override
	public int obtenerTipoDocumento(String nombreFichero) {
		String tipoDocumento = obtenerTipo(nombreFichero);
		if (!"SIN_TIPO".equals(tipoDocumento)) {
			return Integer.parseInt(tipoDocumento);
		}
		return 0;
	}

	private String obtenerTipo(String nombreFichero) {
		String[] partes = nombreFichero.split("_");
		if (partes != null && partes.length >= 2) {
			if (partes[0] != null) {
				return partes[0];
			}
		}
		return "SIN_TIPO";
	}

	@Override
	public String obtenerDescripcionTipoDocumento(String nombreFichero) {
		List<DatoMaestroLicBean> listaDocumentos = gestorDatosMaestrosLic.obtenerTiposDocumentos();
		if (listaDocumentos != null && !listaDocumentos.isEmpty()) {
			String idTipoDocumento = obtenerTipo(nombreFichero);
			for (DatoMaestroLicBean documento : listaDocumentos) {
				if (documento.getCodigo().equals(idTipoDocumento)) {
					return documento.getDescripcion();
				}
			}
		}
		return "Sin Tipo";
	}

	private Long obtenerIdDocumentoLicencia(String nombreFichero) {
		String[] partes = nombreFichero.split("_");
		if (partes != null && partes.length >= 2) {
			if (partes[1] != null) {
				return new Long(partes[1]);
			}
		}
		return null;
	}
}
