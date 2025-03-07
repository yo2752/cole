package org.gestoresmadrid.oegam2comun.trafico.model.service.impl;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.List;

import org.gestoresmadrid.core.model.enumerados.TipoImpreso;
import org.gestoresmadrid.oegam2comun.correo.model.service.ServicioCorreo;
import org.gestoresmadrid.oegam2comun.presentacion.jpt.enumerados.JefaturasJPTEnum;
import org.gestoresmadrid.oegam2comun.proceso.enumerados.ProcesosEnum;
import org.gestoresmadrid.oegam2comun.proceso.model.service.ServicioProcesos;
import org.gestoresmadrid.oegam2comun.trafico.model.service.ServicioIncidenciasBajas;
import org.gestoresmadrid.oegam2comun.trafico.model.service.ServicioTramiteTraficoBaja;
import org.gestoresmadrid.oegam2comun.trafico.view.beans.ResultadoIncidenciasBajasBean;
import org.gestoresmadrid.oegam2comun.trafico.view.dto.TramiteTrafBajaDto;
import org.gestoresmadrid.utilidades.components.GestorPropiedades;
import org.gestoresmadrid.utilidades.components.UtilesFecha;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import colas.constantes.ConstantesProcesos;
import es.globaltms.gestorDocumentos.bean.FicheroBean;
import es.globaltms.gestorDocumentos.constantes.ConstantesGestorFicheros;
import es.globaltms.gestorDocumentos.interfaz.GestorDocumentos;
import escrituras.beans.ResultBean;
import jxl.CellView;
import jxl.Workbook;
import jxl.format.Alignment;
import jxl.format.Border;
import jxl.format.BorderLineStyle;
import jxl.format.Colour;
import jxl.write.Label;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;
import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;
import utilidades.web.OegamExcepcion;

@Service
public class ServicioIncidenciasBajasImpl implements ServicioIncidenciasBajas{

	private static final long serialVersionUID = -5617986011055255321L;

	private static ILoggerOegam log = LoggerOegam.getLogger(ServicioIncidenciasBajasImpl.class);

	@Autowired
	ServicioTramiteTraficoBaja servicioTramiteTraficoBaja;

	@Autowired
	GestorDocumentos gestorDocumentos;

	@Autowired
	ServicioCorreo servicioCorreo;

	@Autowired
	ServicioProcesos servicioProcesos;

	@Autowired
	UtilesFecha utilesFecha;

	@Autowired
	private GestorPropiedades gestorPropiedades;

	@Override
	public String generarExcelIncidenciasPorJefatura(){
		String mensaje = "";
		if (!servicioProcesos.hayEnvio(ProcesosEnum.INCD_BTV_MADRID.getNombreEnum())) {
			log.info("Inicio Envio: " + ProcesosEnum.INCD_BTV_MADRID.toString());
			ResultadoIncidenciasBajasBean resultado = generarExcelIncidencias(JefaturasJPTEnum.MADRID, ProcesosEnum.INCD_BTV_MADRID.getNombreEnum());
			if (resultado.getError()) {
				mensaje = getMensaje(mensaje, resultado.getMensaje());
			}
		}

		if (!servicioProcesos.hayEnvio(ProcesosEnum.INCD_BTV_ALCORCON.getNombreEnum())) {
			log.info("Inicio Envio: " + ProcesosEnum.INCD_BTV_ALCORCON.toString());
			ResultadoIncidenciasBajasBean resultado = generarExcelIncidencias(JefaturasJPTEnum.ALCORCON, ProcesosEnum.INCD_BTV_ALCORCON.getNombreEnum());
			if (resultado.getError()) {
				mensaje = getMensaje(mensaje, resultado.getMensaje());
			}
		}

		if (!servicioProcesos.hayEnvio(ProcesosEnum.INCD_BTV_ALCALA.getNombreEnum())) {
			log.info("Inicio Envio: " + ProcesosEnum.INCD_BTV_ALCALA.toString());

			ResultadoIncidenciasBajasBean resultado = generarExcelIncidencias(JefaturasJPTEnum.ALCALADEHENARES, ProcesosEnum.INCD_BTV_ALCALA.getNombreEnum());
			if (resultado.getError()) {
				mensaje = getMensaje(mensaje, resultado.getMensaje());
			}
		}

		if (!servicioProcesos.hayEnvio(ProcesosEnum.INCD_BTV_AVILA.getNombreEnum())) {
			log.info("Inicio Envio: " + ProcesosEnum.INCD_BTV_AVILA.toString());

			ResultadoIncidenciasBajasBean resultado = generarExcelIncidencias(JefaturasJPTEnum.AVILA, ProcesosEnum.INCD_BTV_AVILA.getNombreEnum());
			if (resultado.getError()) {
				mensaje = getMensaje(mensaje, resultado.getMensaje());
			}
		}

		if (!servicioProcesos.hayEnvio(ProcesosEnum.INCD_BTV_CIUDAD_REAL.getNombreEnum())) {
			log.info("Inicio Envio: " + ProcesosEnum.INCD_BTV_CIUDAD_REAL.toString());

			ResultadoIncidenciasBajasBean resultado = generarExcelIncidencias(JefaturasJPTEnum.CIUDADREAL, ProcesosEnum.INCD_BTV_CIUDAD_REAL.getNombreEnum());
			if (resultado.getError()) {
				mensaje = getMensaje(mensaje, resultado.getMensaje());
			}
		}

		if (!servicioProcesos.hayEnvio(ProcesosEnum.INCD_BTV_CUENCA.getNombreEnum())) {
			log.info("Inicio Envio: " + ProcesosEnum.INCD_BTV_CUENCA.toString());

			ResultadoIncidenciasBajasBean resultado = generarExcelIncidencias(JefaturasJPTEnum.CUENCA, ProcesosEnum.INCD_BTV_CUENCA.getNombreEnum());
			if (resultado.getError()) {
				mensaje = getMensaje(mensaje, resultado.getMensaje());
			}
		}

		if (!servicioProcesos.hayEnvio(ProcesosEnum.INCD_BTV_SEGOVIA.getNombreEnum())) {
			log.info("Inicio Envio: " + ProcesosEnum.INCD_BTV_SEGOVIA.toString());

			ResultadoIncidenciasBajasBean resultado = generarExcelIncidencias(JefaturasJPTEnum.SEGOVIA, ProcesosEnum.INCD_BTV_SEGOVIA.getNombreEnum());
			if (resultado.getError()) {
				mensaje = getMensaje(mensaje, resultado.getMensaje());
			}
		}

		if (!servicioProcesos.hayEnvio(ProcesosEnum.INCD_BTV_GUADALAJARA.getNombreEnum())) {
			log.info("Inicio Envio: " + ProcesosEnum.INCD_BTV_GUADALAJARA.toString());

			ResultadoIncidenciasBajasBean resultado = generarExcelIncidencias(JefaturasJPTEnum.GUADALAJARA, ProcesosEnum.INCD_BTV_GUADALAJARA.getNombreEnum());
			if (resultado.getError()) {
				mensaje = getMensaje(mensaje, resultado.getMensaje());
			}
		}
		return mensaje;
	}

	private String getMensaje(String mensaje, String mensajeResultado) {
		if (!mensaje.isEmpty()) {
			mensaje += ", " + mensajeResultado;
		} else {
			mensaje = mensajeResultado;
		}
		return mensaje;
	}

	private ResultadoIncidenciasBajasBean generarExcelIncidencias(JefaturasJPTEnum jefatura, String proceso) {
		String resultadoEjecucion = null;
		String mensajeResultado = null;
		ResultadoIncidenciasBajasBean resultado;

		resultado = generarExcelIncidencias(jefatura);
		if (resultado.getError()) {
			resultadoEjecucion = ConstantesProcesos.EJECUCION_NO_CORRECTA;
			mensajeResultado = resultado.getMensaje();
		} else {
			resultadoEjecucion = ConstantesProcesos.EJECUCION_CORRECTA;
			mensajeResultado = "Solicitud procesada correctamente";
		}
		servicioProcesos.actualizarEjecucion(proceso, mensajeResultado, resultadoEjecucion, "0");

		return resultado;
	}

	@Override
	public ResultadoIncidenciasBajasBean generarExcelIncidencias(JefaturasJPTEnum jefatura) {
		ResultadoIncidenciasBajasBean resultado = new ResultadoIncidenciasBajasBean(Boolean.FALSE);
		try {
			Date fechaLaboralAnt = utilesFecha.getPrimerLaborableAnterior().getFecha();
			List<TramiteTrafBajaDto> listaTramites = servicioTramiteTraficoBaja.getListaTramitesFinalizadosIncidenciaBtv(jefatura.getJefatura(),fechaLaboralAnt);
			if(listaTramites != null && !listaTramites.isEmpty()){
				resultado = generarExcel(listaTramites, jefatura.getDescripcion(), fechaLaboralAnt);
				if(!resultado.getError()){
					if(resultado.getListaMensajes() != null && !resultado.getListaMensajes().isEmpty()){
						enviarMailErrores(resultado.getListaMensajes(), jefatura.getDescripcion());
					}
					if(resultado.getFichero() != null){
						resultado = enviarMailFicheroExcelIncidencias(resultado.getFichero(),resultado.getNombreFichero(), jefatura);
					}
				}
			} else {
				resultado.setMensaje("No existen tramites para la jefatura: " + jefatura.getDescripcion());
			}
		} catch (Throwable e) {
			log.error("Ha sucedido un error a la hora de generar el excel para la jefatura: " + jefatura.getDescripcion() + ", error: ", e);
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("Ha sucedido un error a la hora de generar el excel para la jefatura: " + jefatura.getDescripcion());
		}
		return resultado;
	}

	private ResultadoIncidenciasBajasBean enviarMailFicheroExcelIncidencias(File fichero, String nombreFichero, JefaturasJPTEnum jefatura) {
		ResultadoIncidenciasBajasBean resultado = new ResultadoIncidenciasBajasBean(Boolean.FALSE);
		try {
			String to = getToCorreo(jefatura);
			String bcc = gestorPropiedades.valorPropertie("direccion.excel.bajas.mail.destinatario2");
			String from = gestorPropiedades.valorPropertie("direccion.excel.bajas.mail.usuario");
			String subject = "Fichero Excel con Incidencias BTV";

			String texto = "<br><span style=\"font-size:15pt;font-family:Tunga;margin-left:20px;\"><br>Se solicita desde la Oficina Electrónica de Gestión Administrativa (OEGAM), "
					+ "el envío del siguiente fichero Excel con las incidencias de las bajas definitivas del tipo:<br>Tránsito Comunitario,Exportación.<br><br></span>";

			FicheroBean ficheroBean = new FicheroBean();
			ficheroBean.setFichero(fichero);
			ficheroBean.setNombreDocumento(nombreFichero);
			ResultBean resultEnvio = servicioCorreo.enviarCorreoBajas(texto, Boolean.FALSE, from, subject, to, null, bcc, "bajas", ficheroBean);
			if (resultEnvio.getError()) {
				resultado.setError(Boolean.TRUE);
				resultado.setMensaje(resultEnvio.getMensaje());
			}
		} catch (Throwable e) {
			log.error("Ha sucedido un error a la hora de enviar el correo con las incidencias de las bajas a la jefatura: " + jefatura + ", error: ",e);
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("Ha sucedido un error a la hora de enviar el correo con las incidencias de las bajas a la jefatura: " + jefatura);
		}
		return resultado;
	}

	private String getToCorreo(JefaturasJPTEnum jefatura) {
		String to = null;
		if (JefaturasJPTEnum.MADRID.getJefatura().equals(jefatura.getJefatura())) {
			to = gestorPropiedades.valorPropertie("direccion.excel.bajas.incidencias.mail.destinatario.madrid");
		} else if (JefaturasJPTEnum.ALCORCON.getJefatura().equals(jefatura.getJefatura())) {
			to = gestorPropiedades.valorPropertie("direccion.excel.bajas.incidencias.mail.destinatario.alcorcon");
		} else if (JefaturasJPTEnum.ALCALADEHENARES.getJefatura().equals(jefatura.getJefatura())) {
			to = gestorPropiedades.valorPropertie("direccion.excel.bajas.incidencias.mail.destinatario.alcala");
		} else if (JefaturasJPTEnum.AVILA.getJefatura().equals(jefatura.getJefatura())) {
			to = gestorPropiedades.valorPropertie("direccion.excel.bajas.incidencias.mail.destinatario.avila");
		} else if (JefaturasJPTEnum.CIUDADREAL.getJefatura().equals(jefatura.getJefatura())) {
			to = gestorPropiedades.valorPropertie("direccion.excel.bajas.incidencias.mail.destinatario.ciudadReal");
		} else if (JefaturasJPTEnum.CUENCA.getJefatura().equals(jefatura.getJefatura())) {
			to = gestorPropiedades.valorPropertie("direccion.excel.bajas.incidencias.mail.destinatario.cuenca");
		} else if (JefaturasJPTEnum.SEGOVIA.getJefatura().equals(jefatura.getJefatura())) {
			to = gestorPropiedades.valorPropertie("direccion.excel.bajas.incidencias.mail.destinatario.segovia");
		} else if (JefaturasJPTEnum.GUADALAJARA.getJefatura().equals(jefatura.getJefatura())) {
			to = gestorPropiedades.valorPropertie("direccion.excel.bajas.incidencias.mail.destinatario.guadalajara");
		}
		return to;
	}

	private void enviarMailErrores(List<String> listaMensajes, String jefatura) {
		try {
			String to = gestorPropiedades.valorPropertie("direccion.excel.bajas.incidencias.mail.destinatario2");
			String from = gestorPropiedades.valorPropertie("direccion.excel.bajas.mail.usuario");
			String subject = "Errores Fichero Excel con Incidencias BTV";
			String texto = "<br><span style=\"font-size:15pt;font-family:Tunga;margin-left:20px;\"><br>Estos son los errores registrados en el envio de las incidencias de bajas a la jefatura de " + jefatura + ":";
			for(String mensaje : listaMensajes){
				texto += "<br>- " + mensaje;
			}
			texto += "</span>";
			ResultBean resultEnvio = servicioCorreo.enviarCorreoBajas(texto, Boolean.FALSE, from, subject, to, null, null, "bajas", null);
			if (resultEnvio.getError()) {
				log.error(resultEnvio.getMensaje());
			}
		} catch (Throwable e) {
			log.error("Ha sucedido un error a la hora de enviar el correo con los errores de las incidencias de las bajas a la jefatura: " + jefatura + ", error: ",e);
		}
	}

	private ResultadoIncidenciasBajasBean generarExcel(List<TramiteTrafBajaDto> listaBajas, String jefatura, Date fecha) throws OegamExcepcion {
		ResultadoIncidenciasBajasBean resultado = new ResultadoIncidenciasBajasBean(Boolean.FALSE);
		String nombreFichero = "";
		File archivo = null;
		FicheroBean fichero = new FicheroBean();
		// --------------------------------------------------------------------------------

		nombreFichero = TipoImpreso.BajasExcel_INCD.getNombreEnum() + "_ICOGAM_" + jefatura + "_" + utilesFecha.formatoFecha("ddMMyyyy", fecha);

		fichero.setExtension(".xls");
		fichero.setNombreDocumento(nombreFichero);
		fichero.setTipoDocumento(ConstantesGestorFicheros.EXCELS);
		fichero.setSubTipo(ConstantesGestorFicheros.BAJASENVIO);
		fichero.setFecha(utilesFecha.getFechaFracionada(fecha));
		fichero.setSobreescribir(true);
		fichero.setFichero(new File(nombreFichero));

		archivo = gestorDocumentos.guardarFichero(fichero);

		// Obtenemos los objetos de la hoja de excel donde insertaremos los trámites.
		WritableWorkbook copyWorkbook = null;
		WritableSheet sheet;
		try {
			// Creamos la hoja y el fichero Excel
			copyWorkbook = Workbook.createWorkbook(archivo);

			sheet = copyWorkbook.createSheet(INCD_BTV, 0);

			// Formato para las columnas que ajusten el tamaño al del texto
			CellView vistaCeldas = new CellView();
			vistaCeldas.setAutosize(true);

			for (int i = 0; i <= COL_DESCRIPCION; i++) {
				sheet.setColumnView(i, vistaCeldas);
			}

			// Generamos las cabeceras de la hoja Excel con el formato indicado
			// Formato de las celdas de cabecera
			WritableFont fuenteCabecera = new WritableFont(WritableFont.createFont("MS Sans Serif"), 10, WritableFont.BOLD, false);
			fuenteCabecera.setColour(Colour.BLACK);
			WritableCellFormat formatoCabecera = new WritableCellFormat(fuenteCabecera);

			formatoCabecera.setBackground(Colour.PALE_BLUE);
			formatoCabecera.setAlignment(Alignment.LEFT);
			formatoCabecera.setBorder(Border.ALL, BorderLineStyle.THIN);

			// Formato de las celdas de datos
			WritableFont fuenteDatos = new WritableFont(WritableFont.createFont("MS Sans Serif"), 10, WritableFont.NO_BOLD, false);
			fuenteDatos.setColour(Colour.BLACK);
			WritableCellFormat formatoDatos = new WritableCellFormat(fuenteDatos);

			formatoDatos.setAlignment(Alignment.LEFT);

			Label label = null;
			try {
				label = new Label(COL_NGESTORIA, 0, NGESTORIA, formatoCabecera);
				sheet.addCell(label);
				label = new Label(COL_NMATRICULA, 0, NMATRICULA, formatoCabecera);
				sheet.addCell(label);

				Integer i = 1;
				for (TramiteTrafBajaDto tramite : listaBajas) {
					try{
						label = new Label(COL_NGESTORIA, i, tramite.getNumColegiado(), formatoDatos);
						sheet.addCell(label);

						label = new Label(COL_NMATRICULA, i, tramite.getVehiculoDto().getMatricula(), formatoDatos);
						sheet.addCell(label);
						i++;
					}catch(Throwable e){
						log.error("Ha sucedido un error a la hora de adjuntar la baja " + tramite.getNumExpediente() + " al excel de incidencias de BTV para la jefatura: " + jefatura + " , error: ",e);
						resultado.addListaMensaje("Ha sucedido un error a la hora de adjuntar la baja " + tramite.getNumExpediente() + " al excel.");
					}
				}
			} catch (RowsExceededException e) {
				log.error("Error al imprimir tramites de baja para la jefatura: " + jefatura + ", error: ", e);
				resultado.addListaMensaje("Error al imprimir tramites de baja para la jefatura: " + jefatura);
			} catch (WriteException e) {
				log.error("Error al imprimir tramites de baja para la jefatura: " + jefatura + ", error: ", e);
				resultado.addListaMensaje("Error al imprimir tramites de baja para la jefatura: " + jefatura);
			}
			copyWorkbook.write();
		} catch (IOException e) {
			log.error("Error al imprimir tramites de baja para la jefatura: " + jefatura + ", error: ", e);
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("Error al imprimir tramites de baja para la jefatura: " + jefatura);
		} catch (Exception e) {
			log.error("Error al imprimir tramites de baja para la jefatura: " + jefatura + ", error: ", e);
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("Error al imprimir tramites de baja para la jefatura: " + jefatura);
		} finally {
			if (copyWorkbook != null) {
				try {
					copyWorkbook.close();
				} catch (WriteException e) {
					log.error("Error al imprimir tramites de baja para la jefatura: " + jefatura + ", error: ", e);
					resultado.setError(Boolean.TRUE);
					resultado.setMensaje("Error al imprimir tramites de baja para la jefatura: " + jefatura);
				} catch (IOException e) {
					log.error("Error al imprimir tramites de baja para la jefatura: " + jefatura + ", error: ", e);
					resultado.setError(Boolean.TRUE);
					resultado.setMensaje("Error al imprimir tramites de baja para la jefatura: " + jefatura);
				}
			}
		}
		if(!resultado.getError()){
			resultado.setFichero(archivo);
			resultado.setNombreFichero(nombreFichero + ".xls");
		}
		return resultado;
	}

}