package org.gestoresmadrid.oegam2comun.permisoDistintivoItv.model.service.impl;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.gestoresmadrid.core.tipoPermDistItv.model.enumerado.TipoDocumentoImprimirEnum;
import org.gestoresmadrid.oegam2comun.correo.model.service.ServicioCorreo;
import org.gestoresmadrid.oegam2comun.distintivo.model.service.ServicioDistintivoDgt;
import org.gestoresmadrid.oegam2comun.distintivo.view.bean.ResultadoDistintivoDgtBean;
import org.gestoresmadrid.oegam2comun.permisoDistintivoItv.model.service.ServicioImpNochePrmDstvFicha;
import org.gestoresmadrid.oegam2comun.trafico.model.service.ServicioTramiteTraficoMatriculacion;
import org.gestoresmadrid.oegamComun.contrato.view.dto.ContratoDto;
import org.gestoresmadrid.utilidades.components.GestorPropiedades;
import org.gestoresmadrid.utilidades.components.UtilesFecha;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
import oegam.constantes.ConstantesSession;
import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;
import utilidades.web.OegamExcepcion;

@Service
public class ServicioImpNochePrmDstvFichaImpl implements ServicioImpNochePrmDstvFicha{

	private static final long serialVersionUID = -8750907112840977845L;

	private static final ILoggerOegam log = LoggerOegam.getLogger(ServicioImpNochePrmDstvFichaImpl.class);
	
	@Autowired
	ServicioDistintivoDgt servicioDistintivoDgt;
	
	@Autowired
	ServicioTramiteTraficoMatriculacion servicioTramiteTraficoMatriculacion;
	
	@Autowired
	ServicioCorreo servicioCorreo;
	
	@Autowired
	GestorDocumentos gestorDocumentos;
	
	@Autowired
	private GestorPropiedades gestorPropiedades;
	
	@Autowired
	UtilesFecha utilesFecha;

	@Override
	public ResultadoDistintivoDgtBean imprimirDocumentos() {
		ResultadoDistintivoDgtBean resultado = new ResultadoDistintivoDgtBean(Boolean.FALSE);
		try {
			List<ContratoDto> listaContratos = servicioTramiteTraficoMatriculacion.getListaIdsContratosConDistintivos();
			if(listaContratos != null && !listaContratos.isEmpty()){
				for(ContratoDto contrato : listaContratos){
					ResultadoDistintivoDgtBean resultGen = new ResultadoDistintivoDgtBean(Boolean.FALSE);
					generarDistintivosContrato(resultGen, contrato);
					/*if(resultGen.getListaFicherosColegiado() != null && !resultGen.getListaFicherosColegiado().isEmpty()){
						enviarMailGeneracionDistintivoColegiado(resultado, contrato, resultGen.getListaFicherosColegiado());
					}*/
					rellenarResultadoGenDoc(resultado, resultGen, Boolean.FALSE);
				}
			}
			//generar excel resumen
			resultadosResumenDistintivos(resultado);
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de generar los documentos, error: ",e);
			resultado.setError(Boolean.TRUE);
			resultado.setExcepcion(e);
		} catch (OegamExcepcion e) {
			log.error("Ha sucedido un error a la hora de generar los documentos, error: ",e);
			resultado.setError(Boolean.TRUE);
			resultado.setExcepcion(new Exception(e));
		}
		return resultado;
	}
	

	private void resultadosResumenDistintivos(ResultadoDistintivoDgtBean resultado) throws OegamExcepcion {
		ResultBean resultadoMetodo = new ResultBean();
		String nombreFichero = "";
		File archivo = null;
		FicheroBean fichero = new FicheroBean();
		// --------------------------------------------------------------------------------

		nombreFichero = TipoDocumentoImprimirEnum.DISTINTIVO.getNombreEnum();

		fichero.setExtension(".xls");
		fichero.setNombreDocumento(nombreFichero);
		fichero.setTipoDocumento(ConstantesGestorFicheros.EXCELS);
		fichero.setSubTipo(ConstantesGestorFicheros.DISTINTIVOS);
		fichero.setFecha(utilesFecha.getFechaActual());
		fichero.setSobreescribir(true);
		fichero.setFichero(new File(nombreFichero));

		archivo = gestorDocumentos.guardarFichero(fichero);

		// Obtenemos los objetos de la hoja de excel donde insertaremos los trámites.
		WritableWorkbook copyWorkbook = null;
		WritableSheet sheet;
		try {
			// Creamos la hoja y el fichero Excel
			copyWorkbook = Workbook.createWorkbook(archivo);

			sheet = copyWorkbook.createSheet("Resumen generacion documentos distintivos, ficha tecnica y permisos", 0);

			// Formato para las columnas que ajusten el tamaño al del texto
			CellView vistaCeldas = new CellView();
			vistaCeldas.setAutosize(true);

			WritableFont fuente = new WritableFont(WritableFont.createFont("MS Sans Serif"), 10, WritableFont.BOLD, false);
			fuente.setColour(Colour.BLACK);
			WritableCellFormat formato = new WritableCellFormat(fuente);

			formato.setBackground(Colour.GRAY_25);
			formato.setBorder(Border.LEFT, BorderLineStyle.THIN);
			formato.setBorder(Border.RIGHT, BorderLineStyle.THIN);

			vistaCeldas.setFormat(formato);
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
				label = new Label(0, 0, "Resultado OK distintivos", formatoCabecera);
				sheet.addCell(label);
				label = new Label(1, 0, "Resultado ERRORES distintivos", formatoCabecera);
				sheet.addCell(label);
				
				Integer i = 1;
				
				if(resultado.getResumen().getListaOk() != null){
					String ok = resultado.getResumen().getListaOk().toString();
					String[] oks = ok.split(",");
					for (String resultadoOk : oks) {
						label = new Label(0, i, resultadoOk, formatoDatos);
						sheet.addCell(label);
						i++;
					}
					
				}
				if(resultado.getResumen().getListaErrores() != null){
					String ko = resultado.getResumen().getListaErrores().toString();
					String[] koS = ko.split(",");
					for (String resultadoKo : koS) {
						label = new Label(1, i, resultadoKo, formatoDatos);
						sheet.addCell(label);
						i++;
					}
				}
				copyWorkbook.write();
		} catch (IOException e) {
			log.error("Error al generar resumen distintivos ", e);
		} catch (Exception e) {
			log.error("Error al generar resumen distintivos ", e);
		} finally {
			if (copyWorkbook != null) {
				try {
					copyWorkbook.close();
				} catch (WriteException e) {
					log.error("Error al generar resumen distintivos ", e);
				} catch (IOException e) {
					log.error("Error al generar resumen distintivos ", e);
				}
			}

		}

		resultadoMetodo.addAttachment(ConstantesSession.FICHERO_EXCEL, archivo);
		resultadoMetodo.addAttachment(ConstantesSession.HAY_FICHERO_EXCEL, true);

		try {
			enviarMailFicheroExcel(resultadoMetodo);
		} catch (Exception e) {
			log.info(e);
			resultadoMetodo.setError(true);
			resultadoMetodo.setMensaje(e.toString());
		}

		return;
		
	}

	private ResultBean enviarMailFicheroExcel(ResultBean resultadoMetodo) throws Exception, OegamExcepcion {
		ResultBean resultadoEnviarFichero = resultadoMetodo;
		if (null != resultadoEnviarFichero && null != resultadoEnviarFichero.getAttachment(ConstantesSession.HAY_FICHERO_EXCEL)) {
			Boolean hayFicheroExcel = (Boolean) resultadoEnviarFichero.getAttachment(ConstantesSession.HAY_FICHERO_EXCEL);

			if (hayFicheroExcel != null && hayFicheroExcel) {
				
				String to = gestorPropiedades.valorPropertie("direcciones.excel.errores.distintivos");
				String from = "oegam@gestoresmadrid.org";
				String subject = "Resumen diario de la generación de distintivos";

				String texto = "<br><span style=\"font-size:15pt;font-family:Tunga;margin-left:20px;\"><br>Resultado resumen distintivos recibidos.<br><br></span>";

				File file = (File) resultadoEnviarFichero.getAttachment(ConstantesSession.FICHERO_EXCEL);

				FicheroBean fichero = new FicheroBean();
				fichero.setFichero(file);
				fichero.setNombreDocumento(file.getName());

				ResultBean resultEnvio;
				
				resultEnvio = servicioCorreo.enviarCorreo(texto, null, from, subject, to, null, null, "distintivos", fichero);
								
				if (resultEnvio.getError()) {
					resultadoEnviarFichero.setError(true);
					resultadoEnviarFichero.setMensaje(resultEnvio.getMensaje());
				} else {
					resultadoEnviarFichero.setError(false);
				}
			}
		}
		return resultadoEnviarFichero;
	}
		private void generarDistintivosContrato(ResultadoDistintivoDgtBean resultado, ContratoDto contrato) {
		try {
			ResultadoDistintivoDgtBean resultGenDstv = servicioDistintivoDgt.generarDistintivoNoche(contrato);
			rellenarResultadoGenDoc(resultado, resultGenDstv, Boolean.FALSE);
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de generar los distintivos para el contrato: " + contrato.getIdContrato() + ", error: ",e);
			resultado.getResumen().addNumError();
			resultado.getResumen().addListaMensajeError("Ha sucedido un error a la hora de generar los distintivos para el contrato: " + contrato.getIdContrato());
		}
	}

	private void rellenarResultadoGenDoc(ResultadoDistintivoDgtBean resultado,ResultadoDistintivoDgtBean resultGenDstv, Boolean rellenarListasFicheros) {
		if(resultGenDstv.getError()){
			resultado.getResumen().addNumError();
			resultado.getResumen().addListaMensajeError(resultGenDstv.getMensaje());
		} 
			
		if(resultGenDstv.getResumen().getListaErrores() != null && !resultGenDstv.getResumen().getListaErrores().isEmpty()){
			resultado.getResumen().addListaErrores(resultGenDstv.getResumen().getListaErrores());
			resultado.getResumen().addListaNumErrores(resultGenDstv.getResumen().getNumError());
		}
		if(resultGenDstv.getResumen().getListaOk() != null && !resultGenDstv.getResumen().getListaOk().isEmpty()){
			resultado.getResumen().addListaOk(resultGenDstv.getResumen().getListaOk());
			resultado.getResumen().addListaNumOk(resultGenDstv.getResumen().getNumOk());
		} else if(!resultGenDstv.getError()){
			if(resultGenDstv.getMensaje() != null && !resultGenDstv.getMensaje().isEmpty()){
				resultado.getResumen().addNumOk();
				resultado.getResumen().addListaMensajeOk(resultGenDstv.getMensaje());
			}
		}
		if(rellenarListasFicheros){
			if(resultGenDstv.getListaFicherosColegiado() != null && !resultGenDstv.getListaFicherosColegiado().isEmpty()){
				resultado.addListaFicherosColegiado(resultGenDstv.getListaFicherosColegiado());
			}
		}
	}
	
}
