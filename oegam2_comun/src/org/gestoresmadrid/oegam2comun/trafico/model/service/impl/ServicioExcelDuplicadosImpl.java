package org.gestoresmadrid.oegam2comun.trafico.model.service.impl;

import java.util.Date;
import java.util.List;

import org.gestoresmadrid.oegam2comun.correo.model.service.ServicioCorreo;
import org.gestoresmadrid.oegam2comun.impresion.excel.duplicados.ImpresionExcelDuplicados;
import org.gestoresmadrid.oegam2comun.presentacion.jpt.enumerados.JefaturasJPTEnum;
import org.gestoresmadrid.oegam2comun.proceso.enumerados.ProcesosEnum;
import org.gestoresmadrid.oegam2comun.proceso.model.service.ServicioProcesos;
import org.gestoresmadrid.oegam2comun.trafico.model.service.ServicioExcelDuplicados;
import org.gestoresmadrid.oegam2comun.trafico.model.service.ServicioTramiteTraficoDuplicado;
import org.gestoresmadrid.oegam2comun.trafico.view.beans.ResultadoImpresionExcel;
import org.gestoresmadrid.oegam2comun.trafico.view.dto.TramiteTrafDuplicadoDto;
import org.gestoresmadrid.utilidades.components.GestorPropiedades;
import org.gestoresmadrid.utilidades.components.UtilesFecha;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import colas.constantes.ConstantesProcesos;
import escrituras.beans.ResultBean;
import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;

@Service
public class ServicioExcelDuplicadosImpl implements ServicioExcelDuplicados{

	private static final long serialVersionUID = -6418446206683335699L;

	private static ILoggerOegam log = LoggerOegam.getLogger(ServicioExcelDuplicadosImpl.class);

	@Autowired
	ServicioProcesos servicioProcesos;

	@Autowired
	ServicioTramiteTraficoDuplicado servicioTramiteTraficoDuplicado;

	@Autowired
	ImpresionExcelDuplicados impresionExcelDuplicados;

	@Autowired
	ServicioCorreo servicioCorreo;

	@Autowired
	UtilesFecha utilesFecha;

	@Autowired
	private GestorPropiedades gestorPropiedades;

	@Override
	public void generarExcelDuplicados() {
		ResultadoImpresionExcel resultado = new ResultadoImpresionExcel(Boolean.FALSE);
		if (!servicioProcesos.hayEnvio(ProcesosEnum.Excel_Duplicados_Madrid.getNombreEnum())) {
			// JEFATURA MADRID
			generarExcel(JefaturasJPTEnum.MADRID,ProcesosEnum.Excel_Duplicados_Madrid.getNombreEnum(), resultado);
		}
		if (!servicioProcesos.hayEnvio(ProcesosEnum.Excel_Duplicados_Alcorcon.getNombreEnum())) {
			// JEFATURA ALCORCON
			generarExcel(JefaturasJPTEnum.ALCORCON,ProcesosEnum.Excel_Duplicados_Alcorcon.getNombreEnum(), resultado);
		}
		if (!servicioProcesos.hayEnvio(ProcesosEnum.Excel_Duplicados_Alcala.getNombreEnum())) {
			// JEFATURA ALCALA
			generarExcel(JefaturasJPTEnum.ALCALADEHENARES,ProcesosEnum.Excel_Duplicados_Alcala.getNombreEnum(), resultado);
		}
		if ("SI".equalsIgnoreCase(gestorPropiedades.valorPropertie("envio.mail.jefaturaAvila"))
				&& !servicioProcesos.hayEnvio(ProcesosEnum.Excel_Duplicados_Avila.getNombreEnum())) {
			// JEFATURA AVILA
			generarExcel(JefaturasJPTEnum.AVILA,ProcesosEnum.Excel_Duplicados_Avila.getNombreEnum(), resultado);
		}

		if ("SI".equalsIgnoreCase(gestorPropiedades.valorPropertie("envio.mail.jefaturaCiudadReal"))
				&& !servicioProcesos.hayEnvio(ProcesosEnum.Excel_Duplicados_Ciudad_Real.getNombreEnum())) {
			// JEFATURA CIUDAD REAL
			generarExcel(JefaturasJPTEnum.CIUDADREAL,ProcesosEnum.Excel_Duplicados_Ciudad_Real.getNombreEnum(), resultado);
		}

		if ("SI".equalsIgnoreCase(gestorPropiedades.valorPropertie("envio.mail.jefaturaCuenca"))
				&& !servicioProcesos.hayEnvio(ProcesosEnum.Excel_Duplicados_Cuenca.getNombreEnum())) {
			// JEFATURA CUENCA
			generarExcel(JefaturasJPTEnum.CUENCA,ProcesosEnum.Excel_Duplicados_Cuenca.getNombreEnum(), resultado);
		}

		if ("SI".equalsIgnoreCase(gestorPropiedades.valorPropertie("envio.mail.jefaturaSegovia"))
				&& !servicioProcesos.hayEnvio(ProcesosEnum.Excel_Duplicados_Segovia.getNombreEnum())) {
			// JEFATURA SEGOVIA
			generarExcel(JefaturasJPTEnum.SEGOVIA,ProcesosEnum.Excel_Duplicados_Segovia.getNombreEnum(), resultado);
		}

		if ("SI".equalsIgnoreCase(gestorPropiedades.valorPropertie("envio.mail.jefaturaGuadalajara"))
				&& !servicioProcesos.hayEnvio(ProcesosEnum.Excel_Duplicados_Guadalajara.getNombreEnum())) {
			// JEFATURA GUADALAJARA
			generarExcel(JefaturasJPTEnum.GUADALAJARA,ProcesosEnum.Excel_Duplicados_Guadalajara.getNombreEnum(), resultado);
		}
		enviarMailResumenExcels(resultado);
	}

	private void enviarMailResumenExcels(ResultadoImpresionExcel resultado) {
		try {
			if(existeEnvioExcel(resultado)){
				String to = gestorPropiedades.valorPropertie("direccion.excel.resumen.mail.destinatario");
				String from = gestorPropiedades.valorPropertie("direccion.excel.duplicados.mail.usuario");
				String subject = gestorPropiedades.valorPropertie("direccion.excel.resumen.duplicados.subject");

				StringBuffer sb = getResumenEnvio(resultado);

				ResultBean resultEnvio;
				resultEnvio = servicioCorreo.enviarCorreoDuplicados(sb.toString(), Boolean.FALSE, from, subject, to, null, null, "duplicados", null);

				if(resultEnvio.getError()){
					for(String mensaje : resultEnvio.getListaMensajes()){
						log.error(mensaje);
					}
				}
			}
		} catch (Throwable e) {
			log.error("Ha sucedido un error a la hora de enviar el correo de resumen de los duplicados, error: ",e);
		}

	}

	private boolean existeEnvioExcel(ResultadoImpresionExcel resultado) {
		Boolean existeEnvio = Boolean.FALSE;
		for(JefaturasJPTEnum jefatura : JefaturasJPTEnum.values()){
			if(JefaturasJPTEnum.MADRID.getJefatura().equals(jefatura.getJefatura())){
				if(resultado.getMensajeErrorMadrid() != null && !resultado.getMensajeErrorMadrid().isEmpty()){
				existeEnvio = Boolean.TRUE;
				} else if(resultado.getNumExcelErrorMadrid() > 0 || resultado.getNumExcelMadrid() > 0){
					existeEnvio = Boolean.TRUE;
				}
			} else if(JefaturasJPTEnum.ALCORCON.getJefatura().equals(jefatura.getJefatura())){
				if(resultado.getMensajeErrorAlcorcon() != null && !resultado.getMensajeErrorAlcorcon().isEmpty()){
					existeEnvio = Boolean.TRUE;
				} else if(resultado.getNumExcelErrorAlcorcon() > 0 || resultado.getNumExcelAlcorcon() > 0){
					existeEnvio = Boolean.TRUE;
				}
			}else if(JefaturasJPTEnum.ALCALADEHENARES.getJefatura().equals(jefatura.getJefatura())){
				if(resultado.getMensajeErrorAlcala() != null && !resultado.getMensajeErrorAlcala().isEmpty()){
					existeEnvio = Boolean.TRUE;
				} else if(resultado.getNumExcelErrorAlcala() > 0 || resultado.getNumExcelAlcala() > 0){
					existeEnvio = Boolean.TRUE;
				}
			}else if(JefaturasJPTEnum.AVILA.getJefatura().equals(jefatura.getJefatura())){
				if(resultado.getMensajeErrorAvila() != null && !resultado.getMensajeErrorAvila().isEmpty()){
					existeEnvio = Boolean.TRUE;
				} else if(resultado.getNumExcelErrorAvila() > 0 || resultado.getNumExcelAvila() > 0){
					existeEnvio = Boolean.TRUE;
				}
			}else if(JefaturasJPTEnum.CIUDADREAL.getJefatura().equals(jefatura.getJefatura())){
				if(resultado.getMensajeErrorCiudadReal() != null && !resultado.getMensajeErrorCiudadReal().isEmpty()){
					existeEnvio = Boolean.TRUE;
				} else if(resultado.getNumExcelErrorCiudadReal() > 0 || resultado.getNumExcelCiudadReal() > 0){
					existeEnvio = Boolean.TRUE;
				}
			}else if(JefaturasJPTEnum.CUENCA.getJefatura().equals(jefatura.getJefatura())){
				if(resultado.getMensajeErrorCuenca() != null && !resultado.getMensajeErrorCuenca().isEmpty()){
					existeEnvio = Boolean.TRUE;
				} else if(resultado.getNumExcelErrorCuenca() > 0 || resultado.getNumExcelCuenca() > 0){
					existeEnvio = Boolean.TRUE;
				}
			}else if(JefaturasJPTEnum.GUADALAJARA.getJefatura().equals(jefatura.getJefatura())){
				if(resultado.getMensajeErrorGuadalajara() != null && !resultado.getMensajeErrorGuadalajara().isEmpty()){
					existeEnvio = Boolean.TRUE;
				} else if(resultado.getNumExcelErrorGuadalajara() > 0 || resultado.getNumExcelGuadalajara() > 0){
					existeEnvio = Boolean.TRUE;
				}
			}else if(JefaturasJPTEnum.SEGOVIA.getJefatura().equals(jefatura.getJefatura())){
				if(resultado.getMensajeErrorSegovia() != null && !resultado.getMensajeErrorSegovia().isEmpty()){
					existeEnvio = Boolean.TRUE;
				} else if(resultado.getNumExcelErrorSegovia() > 0 || resultado.getNumExcelSegovia() > 0){
					existeEnvio = Boolean.TRUE;
				}
			}
			if(existeEnvio){
				break;
			}
		}
		return existeEnvio;
	}

	private StringBuffer getResumenEnvio(ResultadoImpresionExcel resultado) {
		StringBuffer resultadoHtml = new StringBuffer();
		final String sFormatoFecha = "dd/MM/yyyy";

		resultadoHtml.append("<br>Resumen de la generación del excel de duplicados con fecha de presentación : ")
		.append(utilesFecha.formatoFecha(sFormatoFecha,new Date()))
		.append(".<br></br>");

		resultadoHtml.append(cadenaTextoPlanoResultado(JefaturasJPTEnum.MADRID,resultado));
		resultadoHtml.append("<br>");
		resultadoHtml.append(cadenaTextoPlanoResultado(JefaturasJPTEnum.ALCORCON,resultado));
		resultadoHtml.append("<br>");
		resultadoHtml.append(cadenaTextoPlanoResultado(JefaturasJPTEnum.ALCALADEHENARES,resultado));
		resultadoHtml.append("<br>");
		if ("SI".equalsIgnoreCase(gestorPropiedades.valorPropertie("envio.mail.jefaturaAvila"))) {
			resultadoHtml.append(cadenaTextoPlanoResultado(JefaturasJPTEnum.AVILA,resultado));
			resultadoHtml.append("<br>");
		}

		if ("SI".equalsIgnoreCase(gestorPropiedades.valorPropertie("envio.mail.jefaturaCiudadReal"))) {
			resultadoHtml.append(cadenaTextoPlanoResultado(JefaturasJPTEnum.CIUDADREAL,resultado));
			resultadoHtml.append("<br>");
		}

		if ("SI".equalsIgnoreCase(gestorPropiedades.valorPropertie("envio.mail.jefaturaCuenca"))) {
			resultadoHtml.append(cadenaTextoPlanoResultado(JefaturasJPTEnum.CUENCA,resultado));
			resultadoHtml.append("<br>");
		}

		if ("SI".equalsIgnoreCase(gestorPropiedades.valorPropertie("envio.mail.jefaturaSegovia"))) {
			resultadoHtml.append(cadenaTextoPlanoResultado(JefaturasJPTEnum.SEGOVIA,resultado));
			resultadoHtml.append("<br>");
		}

		if ("SI".equalsIgnoreCase(gestorPropiedades.valorPropertie("envio.mail.jefaturaGuadalajara"))) {
			resultadoHtml.append(cadenaTextoPlanoResultado(JefaturasJPTEnum.GUADALAJARA,resultado));
			resultadoHtml.append("<br>");
		}

		return resultadoHtml;
	}

	private Object cadenaTextoPlanoResultado(JefaturasJPTEnum jefatura, ResultadoImpresionExcel resultado) {
		StringBuffer cadenaResultado = new StringBuffer("<br><u><b>");
		StringBuffer listado = new StringBuffer();

		cadenaResultado.append("Jefatura de " + jefatura.getDescripcion() +":");
		cadenaResultado.append("</u></b><br><br>");

		String mensajeError = getMensajeErrorPorJefatura(resultado,jefatura);
		if(mensajeError != null && !mensajeError.isEmpty()){
			cadenaResultado.append("ha sucedido el siguiente error al generar el excel de duplicados para la jefatura: " + jefatura.getDescripcion() +", error: ");
			cadenaResultado.append(mensajeError);
			cadenaResultado.append("</u></b><br><br>");
		} else {
 
			listado.append("<span style=\"font-size:10pt;font-family:Tunga;\"><tr> <td>")
			.append(getNumOkPorJefatura(resultado,jefatura))
			.append("</td><td>Enviadas</td> </tr><tr> <td>")
			.append(getNumErrorPorJefatura(resultado,jefatura))
			.append("</td><td>No Enviadas</td> </tr></span>");

			cadenaResultado.append("<table border='1'> <span style=\"font-size:12pt;font-family:Tunga;margin-left:3px;\"><tr> <th>Número de Duplicados</th> <th>Total </th> </tr> </span> ");
			// fin cabecera

			listado.append("</span>");
			cadenaResultado.append(listado).append("</table>");// Fin tabla

			getListaMensajesPorJefatura(resultado,jefatura, listado, cadenaResultado);
		}

		return cadenaResultado.toString();
	}

	private String getMensajeErrorPorJefatura(ResultadoImpresionExcel resultado, JefaturasJPTEnum jefatura) {
		String mensajeError = null;
		if(JefaturasJPTEnum.MADRID.getJefatura().equals(jefatura.getJefatura()) && resultado.getErrorMadrid()){
			mensajeError = resultado.getMensajeErrorMadrid();
		} else if(JefaturasJPTEnum.ALCORCON.getJefatura().equals(jefatura.getJefatura())){
			mensajeError = resultado.getMensajeErrorAlcorcon();
		}else if(JefaturasJPTEnum.ALCALADEHENARES.getJefatura().equals(jefatura.getJefatura())){
			mensajeError = resultado.getMensajeErrorAlcala();
		}else if(JefaturasJPTEnum.AVILA.getJefatura().equals(jefatura.getJefatura())){
			mensajeError = resultado.getMensajeErrorAvila();
		}else if(JefaturasJPTEnum.CIUDADREAL.getJefatura().equals(jefatura.getJefatura())){
			mensajeError = resultado.getMensajeErrorCiudadReal();
		}else if(JefaturasJPTEnum.CUENCA.getJefatura().equals(jefatura.getJefatura())){
			mensajeError = resultado.getMensajeErrorCuenca();
		}else if(JefaturasJPTEnum.GUADALAJARA.getJefatura().equals(jefatura.getJefatura())){
			mensajeError = resultado.getMensajeErrorGuadalajara();
		}else if(JefaturasJPTEnum.SEGOVIA.getJefatura().equals(jefatura.getJefatura())){
			mensajeError = resultado.getMensajeErrorSegovia();
		}
		return mensajeError;
	}

	private Object getNumErrorPorJefatura(ResultadoImpresionExcel resultado, JefaturasJPTEnum jefatura) {
		Long numError = new Long(0);
		if(JefaturasJPTEnum.MADRID.getJefatura().equals(jefatura.getJefatura())){
			numError = resultado.getNumExcelErrorMadrid();
		} else if(JefaturasJPTEnum.ALCORCON.getJefatura().equals(jefatura.getJefatura())){
			numError = resultado.getNumExcelErrorAlcorcon();
		}else if(JefaturasJPTEnum.ALCALADEHENARES.getJefatura().equals(jefatura.getJefatura())){
			numError = resultado.getNumExcelErrorAlcala();
		}else if(JefaturasJPTEnum.AVILA.getJefatura().equals(jefatura.getJefatura())){
			numError = resultado.getNumExcelErrorAvila();
		}else if(JefaturasJPTEnum.CIUDADREAL.getJefatura().equals(jefatura.getJefatura())){
			numError = resultado.getNumExcelErrorCiudadReal();
		}else if(JefaturasJPTEnum.CUENCA.getJefatura().equals(jefatura.getJefatura())){
			numError = resultado.getNumExcelErrorCuenca();
		}else if(JefaturasJPTEnum.GUADALAJARA.getJefatura().equals(jefatura.getJefatura())){
			numError = resultado.getNumExcelErrorGuadalajara();
		}else if(JefaturasJPTEnum.SEGOVIA.getJefatura().equals(jefatura.getJefatura())){
			numError = resultado.getNumExcelErrorSegovia();
		}
		return numError;
	}

	private void getListaMensajesPorJefatura(ResultadoImpresionExcel resultado, JefaturasJPTEnum jefatura,StringBuffer listado, StringBuffer cadenaResultado) {
		List<String> listaMensajes = null;
		if(JefaturasJPTEnum.MADRID.getJefatura().equals(jefatura.getJefatura())){
			listaMensajes = resultado.getListaResumenMadrid();
		} else if(JefaturasJPTEnum.ALCORCON.getJefatura().equals(jefatura.getJefatura())){
			listaMensajes = resultado.getListaResumenAlcorcon();
		}else if(JefaturasJPTEnum.ALCALADEHENARES.getJefatura().equals(jefatura.getJefatura())){
			listaMensajes = resultado.getListaResumenAlcala();
		}else if(JefaturasJPTEnum.AVILA.getJefatura().equals(jefatura.getJefatura())){
			listaMensajes = resultado.getListaResumenAvila();
		}else if(JefaturasJPTEnum.CIUDADREAL.getJefatura().equals(jefatura.getJefatura())){
			listaMensajes = resultado.getListaResumenCiudadReal();
		}else if(JefaturasJPTEnum.CUENCA.getJefatura().equals(jefatura.getJefatura())){
			listaMensajes = resultado.getListaResumenCuenca();
		}else if(JefaturasJPTEnum.GUADALAJARA.getJefatura().equals(jefatura.getJefatura())){
			listaMensajes = resultado.getListaResumenGuadalajara();
		}else if(JefaturasJPTEnum.SEGOVIA.getJefatura().equals(jefatura.getJefatura())){
			listaMensajes = resultado.getListaResumenSegovia();
		}
		if(listaMensajes != null && !listaMensajes.isEmpty()){
			listado = new StringBuffer();
			listado.append("<span style=\"font-size:10pt;font-family:Tunga;\"><tr> <td>");
			for(String mensaje : listaMensajes){
				listado.append(mensaje);
			}
			cadenaResultado.append("<table border='1'> <span style=\"font-size:12pt;font-family:Tunga;margin-left:3px;\"><tr> <th>Mensajes</th></tr> </span> ");
			listado.append("</span>");
			cadenaResultado.append(listado).append("</table>");// Fin tabla
		}

	}

	private Object getNumOkPorJefatura(ResultadoImpresionExcel resultado, JefaturasJPTEnum jefatura) {
		Long numOk = new Long(0);
		if(JefaturasJPTEnum.MADRID.getJefatura().equals(jefatura.getJefatura())){
			numOk = resultado.getNumExcelMadrid();
		} else if(JefaturasJPTEnum.ALCORCON.getJefatura().equals(jefatura.getJefatura())){
			numOk = resultado.getNumExcelAlcorcon();
		}else if(JefaturasJPTEnum.ALCALADEHENARES.getJefatura().equals(jefatura.getJefatura())){
			numOk = resultado.getNumExcelAlcala();
		}else if(JefaturasJPTEnum.AVILA.getJefatura().equals(jefatura.getJefatura())){
			numOk = resultado.getNumExcelAvila();
		}else if(JefaturasJPTEnum.CIUDADREAL.getJefatura().equals(jefatura.getJefatura())){
			numOk = resultado.getNumExcelCiudadReal();
		}else if(JefaturasJPTEnum.CUENCA.getJefatura().equals(jefatura.getJefatura())){
			numOk = resultado.getNumExcelCuenca();
		}else if(JefaturasJPTEnum.GUADALAJARA.getJefatura().equals(jefatura.getJefatura())){
			numOk = resultado.getNumExcelGuadalajara();
		}else if(JefaturasJPTEnum.SEGOVIA.getJefatura().equals(jefatura.getJefatura())){
			numOk = resultado.getNumExcelSegovia();
		}
		return numOk;
	}

	private void generarExcel(JefaturasJPTEnum jefatura, String proceso, ResultadoImpresionExcel resultado) {
		List<TramiteTrafDuplicadoDto> listaDuplicados = servicioTramiteTraficoDuplicado.duplicadosExcel(jefatura.getJefatura());
		if (listaDuplicados != null && !listaDuplicados.isEmpty()) {
			String descJefatura = null;
			if(JefaturasJPTEnum.ALCALADEHENARES.getJefatura().equals(jefatura.getJefatura())){
				descJefatura = "ALCALA";
			} else if(JefaturasJPTEnum.CIUDADREAL.getJefatura().equals(jefatura.getJefatura())){
				descJefatura =  "CIUDADREAL";
			} else {
				descJefatura = jefatura.getDescripcion();
			}
			servicioProcesos.actualizarEjecucion(proceso, ConstantesProcesos.EJECUCION_CORRECTA_MENSAJE_X_DEFECTO,ConstantesProcesos.EJECUCION_CORRECTA, "0");
			impresionExcelDuplicados.impresionExcelDuplicados(listaDuplicados, descJefatura, resultado);
		} else {
			log.info("La lista de Duplicados para " + jefatura + " esta vacía");
		}
	}

}