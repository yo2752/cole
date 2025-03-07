package org.gestoresmadrid.oegam2comun.atex5.model.service.impl;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import org.gestoresmadrid.core.atex5.model.vo.ConsultaPersonaAtex5VO;
import org.gestoresmadrid.oegam2comun.atex5.model.service.ServicioImpresionAtex5;
import org.gestoresmadrid.oegam2comun.atex5.view.bean.ResultadoAtex5Bean;
import org.gestoresmadrid.oegam2comun.atex5.view.dto.ConsultaVehiculoAtex5Dto;
import org.gestoresmadrid.utilidades.components.GestorPropiedades;
import org.gestoresmadrid.utilidades.components.UtilesFecha;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.globaltms.gestorDocumentos.bean.FicheroBean;
import es.globaltms.gestorDocumentos.constantes.ConstantesGestorFicheros;
import es.globaltms.gestorDocumentos.interfaz.GestorDocumentos;
import es.globaltms.gestorDocumentos.util.Utilidades;
import utilidades.informes.ReportExporter;
import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;
import utilidades.web.OegamExcepcion;

@Service
public class ServicioImpresionAtex5Impl implements ServicioImpresionAtex5{

	private static final long serialVersionUID = 3475879919930867566L;
	
	private static final ILoggerOegam log = LoggerOegam.getLogger(ServicioImpresionAtex5Impl.class);
	
	@Autowired
	GestorDocumentos gestorDocumentos;
	
	@Autowired
	private GestorPropiedades gestorPropiedades;
	
	@Autowired
	UtilesFecha utilesFecha;

	@Override
	public ResultadoAtex5Bean imprimirPdfVehiculoAtex5(ConsultaVehiculoAtex5Dto consultaVehiculoAtex5, String ficheroXml) {
		ResultadoAtex5Bean resultado = new ResultadoAtex5Bean(Boolean.FALSE);
		//BufferedReader br = null;
		try {
			String ruta = gestorPropiedades.valorPropertie(RUTA_PDF_VEHICULO_ATEX5);
			ReportExporter re = new ReportExporter();
		/*	br = new BufferedReader(new FileReader(ficheroXml));
			String line;
			StringBuilder sb = new StringBuilder();

			while ((line = br.readLine()) != null) {
				sb.append(line.trim());
			}
*/
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("IMG_DIR", ruta);
			params.put("COLEGIADO", consultaVehiculoAtex5.getNumColegiado());
			params.put("GESTOR", consultaVehiculoAtex5.getContrato().getColegiadoDto().getUsuario().getApellidosNombre());

			Calendar calendar = Calendar.getInstance();
			params.put("HORAINFORME", calendar.get(Calendar.HOUR_OF_DAY) + ":" + calendar.get(Calendar.MINUTE) + ":" + calendar.get(Calendar.SECOND));
			params.put("FECHAINFORME", utilesFecha.getFechaHoy());

			byte[] byteFinal = re.generarInforme(ruta, "vehiculoAtex5", "pdf", ficheroXml, "cabecera", params, null);
			if(byteFinal != null){
				resultado.setNombreFichero(consultaVehiculoAtex5.getNumExpediente() + ConstantesGestorFicheros.EXTENSION_PDF);
				guardarFicheroVehiculoAtex5(byteFinal,resultado.getNombreFichero(),consultaVehiculoAtex5.getNumExpediente());
			} else {
				resultado.setError(Boolean.TRUE);
				resultado.setMensaje("No se ha podido generar el pdf correctamente.");
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de imprimir el pdf, error: ", e);
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("Ha sucedido un error a la hora de imprimir el pdf.");
		}
		return resultado;
	}

	private void guardarFicheroVehiculoAtex5(byte[] byteFinal, String nombreFichero, BigDecimal numExpediente) {
		FicheroBean ficheroBean = new FicheroBean();
		ficheroBean.setFicheroByte(byteFinal);
		ficheroBean.setTipoDocumento(ConstantesGestorFicheros.ATEX5);
		ficheroBean.setSubTipo(ConstantesGestorFicheros.ATEX5_VEHICULO_PDF);
		ficheroBean.setFecha(Utilidades.transformExpedienteFecha(numExpediente));
		ficheroBean.setNombreYExtension(nombreFichero);
		ficheroBean.setSobreescribir(Boolean.TRUE);
		try {
			gestorDocumentos.guardarFichero(ficheroBean);
		} catch (OegamExcepcion e) {
			log.error("Ha sucedido un error a la hora de custodiar el pdf del vehiculoAtex5: " + numExpediente + ",error: ",e);
		}
	}
	
	@Override
	public ResultadoAtex5Bean imprimirPdfPersonaAtex5(ConsultaPersonaAtex5VO consultaPersonaAtex5VO, File ficheroXml) {
		ResultadoAtex5Bean resultado = new ResultadoAtex5Bean(Boolean.FALSE);
		BufferedReader br = null;
		try {
			String ruta = gestorPropiedades.valorPropertie(RUTA_PDF_PERSONA_ATEX5);
			ReportExporter re = new ReportExporter();
			br = new BufferedReader(new FileReader(ficheroXml));
			String line;
			StringBuilder sb = new StringBuilder();

			while ((line = br.readLine()) != null) {
				sb.append(line.trim());
			}

			Map<String, Object> params = new HashMap<String, Object>();
			params.put("IMG_DIR", ruta);
			params.put("COLEGIADO", consultaPersonaAtex5VO.getNumColegiado());
			params.put("GESTOR", consultaPersonaAtex5VO.getContrato().getColegiado().getUsuario().getApellidosNombre());

			Calendar calendar = Calendar.getInstance();
			params.put("HORAINFORME", calendar.get(Calendar.HOUR_OF_DAY) + ":" + calendar.get(Calendar.MINUTE) + ":" + calendar.get(Calendar.SECOND));
			params.put("FECHAINFORME", utilesFecha.getFechaHoy());

			byte[] byteFinal = re.generarInforme(ruta, "personaAtex5", "pdf", sb.toString(), "cabecera", params, null);
			if(byteFinal != null){
				resultado.setNombreFichero(consultaPersonaAtex5VO.getNumExpediente() +  ConstantesGestorFicheros.EXTENSION_PDF);
				guardarFicheroPersonaAtex5(byteFinal,resultado.getNombreFichero(),consultaPersonaAtex5VO.getNumExpediente());
			} else {
				resultado.setError(Boolean.TRUE);
				resultado.setMensaje("No se ha podido generar el pdf correctamente.");
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de imprimir el pdf, error: ", e);
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("Ha sucedido un error a la hora de imprimir el pdf.");
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					log.error("No se pudo cerrar el bufferedReader", e);
					resultado.setError(Boolean.TRUE);
					resultado.setMensaje("No se ha podido generar el pdf correctamente.");
				}
			}
		}
		return resultado;
	}

	private void guardarFicheroPersonaAtex5(byte[] byteFinal, String nombreFichero, BigDecimal numExpediente) {
		FicheroBean ficheroBean = new FicheroBean();
		ficheroBean.setFicheroByte(byteFinal);
		ficheroBean.setTipoDocumento(ConstantesGestorFicheros.ATEX5);
		ficheroBean.setSubTipo(ConstantesGestorFicheros.ATEX5_PERSONA_PDF);
		ficheroBean.setFecha(Utilidades.transformExpedienteFecha(numExpediente));
		ficheroBean.setNombreYExtension(nombreFichero);
		ficheroBean.setSobreescribir(Boolean.TRUE);
		try {
			gestorDocumentos.guardarFichero(ficheroBean);
		} catch (OegamExcepcion e) {
			log.error("Ha sucedido un error a la hora de custodiar el pdf de la personaAtex5: " + numExpediente + ",error: ",e);
		}
	}

}
