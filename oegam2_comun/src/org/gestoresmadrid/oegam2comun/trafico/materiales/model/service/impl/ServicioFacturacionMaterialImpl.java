package org.gestoresmadrid.oegam2comun.trafico.materiales.model.service.impl;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.gestoresmadrid.core.contrato.model.dao.ContratoDao;
import org.gestoresmadrid.core.contrato.model.vo.ContratoVO;
import org.gestoresmadrid.core.docPermDistItv.model.beans.FacturacionDocBean;
import org.gestoresmadrid.core.docPermDistItv.model.dao.DocPermDistItvDao;
import org.gestoresmadrid.core.docPermDistItv.model.enumerados.TipoDistintivo;
import org.gestoresmadrid.core.tipoPermDistItv.model.enumerado.TipoDocumentoImprimirEnum;
import org.gestoresmadrid.oegam2comun.permisoDistintivoItv.model.service.ServicioDocPrmDstvFicha;
import org.gestoresmadrid.oegam2comun.trafico.materiales.model.service.ServicioConsultaMateriales;
import org.gestoresmadrid.oegam2comun.trafico.materiales.model.service.ServicioFacturacionMaterial;
import org.gestoresmadrid.oegam2comun.trafico.materiales.view.beans.FacturacionStockBean;
import org.gestoresmadrid.oegam2comun.trafico.materiales.view.beans.ResultadoFacturacionStockBean;
import org.gestoresmadrid.oegam2comun.trafico.materiales.view.dto.FacturacionStockDto;
import org.gestoresmadrid.utilidades.components.UtilesFecha;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import es.globaltms.gestorDocumentos.bean.FicheroBean;
import es.globaltms.gestorDocumentos.bean.FileResultBean;
import es.globaltms.gestorDocumentos.constantes.ConstantesGestorFicheros;
import es.globaltms.gestorDocumentos.interfaz.GestorDocumentos;
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
import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;


@Service
public class ServicioFacturacionMaterialImpl implements ServicioFacturacionMaterial {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6561077929544138721L;
	
	private static final ILoggerOegam log = LoggerOegam.getLogger(ServicioFacturacionMaterialImpl.class);

	@Resource DocPermDistItvDao docPermDistItvDao;
	@Resource ContratoDao       contratoDao;
	
	@Autowired ServicioConsultaMateriales servicioConsultaMateriales;
	
	@Autowired
	ServicioDocPrmDstvFicha servicioDocPrmDstvFicha;
	
	@Autowired
	GestorDocumentos gestorDocumentos;
	
	@Autowired
	UtilesFecha utilesFecha;

	@Override
	public ResultadoFacturacionStockBean generarExcelFacturacion(FacturacionStockBean facturacionStock) {
		ResultadoFacturacionStockBean resultado = new ResultadoFacturacionStockBean(Boolean.FALSE);
		try {
			resultado = validarCamposBusquedaFctStock(facturacionStock);
			if(!resultado.getError()){
				resultado = servicioDocPrmDstvFicha.obtenerFacturacionDocumentos(facturacionStock);
				if(!resultado.getError()){
					resultado = generarExcelFacturacion(resultado.getListaFacturacion(), facturacionStock);
					if(!resultado.getError()){
						facturacionStock.setNombreFichero(resultado.getNombreFichero());
						facturacionStock.setEsDescargable(Boolean.TRUE);
						facturacionStock.setFechaGenExcel(new Date());
						resultado.setFacturacionStock(facturacionStock);
					}
				}
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de generar el excel de facturación, error: ",e);
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("Ha sucedido un error a la hora de generar el excel de facturación.");
		}
		return resultado;
	}
	

	@Override
	@Transactional(readOnly=true)
	public ResultadoFacturacionStockBean descargarExcelFacturacion(FacturacionStockBean facturacionStock) {
		ResultadoFacturacionStockBean resultado = new ResultadoFacturacionStockBean(Boolean.FALSE);
		try {
			resultado = validarCamposDescarga(facturacionStock);
			if(!resultado.getError()){
				FileResultBean fichero = gestorDocumentos.buscarFicheroPorNombreTipo(ConstantesGestorFicheros.EXCELS, ConstantesGestorFicheros.FACTURACION_DOC_DGT,
						utilesFecha.getFechaConDate(facturacionStock.getFechaGenExcel()), facturacionStock.getNombreFichero(), ConstantesGestorFicheros.EXTENSION_XLS);
				if(fichero != null && fichero.getFile() != null){
					resultado.setExcel(fichero.getFile());
				} else {
					resultado.setError(Boolean.TRUE);
					resultado.setMensaje("No se ha encontrado ningún excel para poder realizar su descarga.");
				}
			}
		} catch (Throwable e) {
			log.error("Ha sucedido un error a la hora de descargar el excel de facturación, error: ",e);
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("Ha sucedido un error a la hora de descargar el excel de facturación.");
		}
		return resultado;
	}
	
	private ResultadoFacturacionStockBean validarCamposDescarga(FacturacionStockBean facturacionStock) {
		ResultadoFacturacionStockBean resultado = new ResultadoFacturacionStockBean(Boolean.FALSE);
		if(facturacionStock == null){
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("No existen datos para poder descargar su facturación.");
		} else if(facturacionStock.getFechaGenExcel() == null){
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("No se puede descargar el excel generado porque la fecha de generacion esta vacia.");
		} else if(facturacionStock.getNombreFichero() == null || facturacionStock.getNombreFichero().isEmpty()){
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("No se puede descargar el excel generado porque el nombre del fichero esta vacio.");
		}
		return resultado;
	}


	private ResultadoFacturacionStockBean generarExcelFacturacion(List<FacturacionDocBean> listaFacturacionDocBBDD,	FacturacionStockBean facturacionStock) {
		ResultadoFacturacionStockBean resultado = new ResultadoFacturacionStockBean(Boolean.FALSE);
		WritableWorkbook copyWorkbook = null;
		WritableSheet sheet;
		try {
			File archivo = null;
			resultado.setNombreFichero("Resumen_Facturacion_" + new SimpleDateFormat("ddMMYYHHmmss").format(new Date()));
			FicheroBean fichero = new FicheroBean();
			fichero.setExtension(ConstantesGestorFicheros.EXTENSION_XLS);
			fichero.setNombreDocumento(resultado.getNombreFichero());
			fichero.setTipoDocumento(ConstantesGestorFicheros.EXCELS);
			fichero.setSubTipo(ConstantesGestorFicheros.FACTURACION_DOC_DGT);
			fichero.setFecha(utilesFecha.getFechaActual());
			fichero.setSobreescribir(true);
			fichero.setFichero(new File(resultado.getNombreFichero()));
			
			archivo = gestorDocumentos.guardarFichero(fichero);
			// Creamos la hoja y el fichero Excel
			copyWorkbook = Workbook.createWorkbook(archivo);
			sheet = copyWorkbook.createSheet("Resumen_Facturacion", 0);

			// Formato para las columnas que ajusten el tamaño al del texto
			CellView vistaCeldas = new CellView();
			vistaCeldas.setAutosize(true);
			int numColumnas = 0;
			if(TipoDocumentoImprimirEnum.DISTINTIVO.getValorEnum().equals(facturacionStock.getTipo())){
				if(facturacionStock.getTipoDistintivo() != null && !facturacionStock.getTipoDistintivo().isEmpty()){
					numColumnas = 7;
				} else {
					numColumnas = 14;
				}
			} else {
				numColumnas = 7;
			}
			for (int i = 0; i <= numColumnas; i++) {
				sheet.setColumnView(i, vistaCeldas);
			}
			
			// Generamos las cabeceras de la hoja Excel con el formato indicado
			// Formato de las celdas de cabecera
			WritableFont fuenteCabecera = new WritableFont(WritableFont.createFont("MS Sans Serif"), 10, WritableFont.BOLD, false);
			fuenteCabecera.setColour(Colour.WHITE);
			fuenteCabecera.setBoldStyle(WritableFont.BOLD);
			WritableCellFormat formatoCabecera = new WritableCellFormat(fuenteCabecera);

			formatoCabecera.setBackground(Colour.LAVENDER);
			formatoCabecera.setAlignment(Alignment.LEFT);
			formatoCabecera.setBorder(Border.ALL, BorderLineStyle.THIN);

			// Formato de las celdas de datos
			WritableFont fuenteDatos = new WritableFont(WritableFont.createFont("MS Sans Serif"), 10, WritableFont.NO_BOLD, false);
			fuenteDatos.setColour(Colour.BLACK);
			WritableCellFormat formatoDatos = new WritableCellFormat(fuenteDatos);

			formatoDatos.setAlignment(Alignment.LEFT);
			
			sheet.addCell(new Label(0, 0, "Fecha Impresión", formatoCabecera));
			sheet.addCell(new Label(1, 0, "Nro. Colegiado", formatoCabecera));
			sheet.addCell(new Label(2, 0, "Nombre Colegiado", formatoCabecera));
			sheet.addCell(new Label(3, 0, "Via", formatoCabecera));
			sheet.addCell(new Label(4, 0, "Provincia", formatoCabecera));
			int cont = 5;
			if(TipoDocumentoImprimirEnum.DISTINTIVO.getValorEnum().equals(facturacionStock.getTipo())){
				if(facturacionStock.getTipoDistintivo() != null && !facturacionStock.getTipoDistintivo().isEmpty()){
					TipoDistintivo tipoDistintivo = TipoDistintivo.convertir(facturacionStock.getTipoDistintivo());
					sheet.addCell(new Label(cont++, 0, tipoDistintivo.getDescripcion(), formatoCabecera));
				} else {
					sheet.addCell(new Label(cont++, 0, TipoDistintivo.CERO.getDescripcion(), formatoCabecera));
					sheet.addCell(new Label(cont++, 0, TipoDistintivo.ECO.getDescripcion(), formatoCabecera));
					sheet.addCell(new Label(cont++, 0, TipoDistintivo.C.getDescripcion(), formatoCabecera));
					sheet.addCell(new Label(cont++, 0, TipoDistintivo.B.getDescripcion(), formatoCabecera));
					sheet.addCell(new Label(cont++, 0, TipoDistintivo.CEROMT.getDescripcion(), formatoCabecera));
					sheet.addCell(new Label(cont++, 0, TipoDistintivo.ECOMT.getDescripcion(), formatoCabecera));
					sheet.addCell(new Label(cont++, 0, TipoDistintivo.CMT.getDescripcion(), formatoCabecera));
					sheet.addCell(new Label(cont++, 0, TipoDistintivo.BMT.getDescripcion(), formatoCabecera));
				}
			} else if(TipoDocumentoImprimirEnum.PERMISO_CIRCULACION.getValorEnum().equals(facturacionStock.getTipo())){
				sheet.addCell(new Label(cont++, 0, "Permisos Circulacion", formatoCabecera));
			} else if(TipoDocumentoImprimirEnum.FICHA_TECNICA.getValorEnum().equals(facturacionStock.getTipo())){
				sheet.addCell(new Label(cont++, 0, "Fichas Tecnicas", formatoCabecera));
			}
			sheet.addCell(new Label(cont++, 0, "Total", formatoCabecera));
			int conFila = 1;
			for(FacturacionDocBean facturacionDocBean : listaFacturacionDocBBDD){
				cont = 0;
				sheet.addCell(new Label(cont++, conFila, facturacionDocBean.getFechaImpresion(), formatoDatos));
				sheet.addCell(new Label(cont++, conFila, facturacionDocBean.getNum_colegiado(), formatoDatos));
				sheet.addCell(new Label(cont++, conFila, facturacionDocBean.getNombreColegiado(), formatoDatos));
				sheet.addCell(new Label(cont++, conFila, facturacionDocBean.getVia(), formatoDatos));
				sheet.addCell(new Label(cont++, conFila, facturacionDocBean.getProvincia(), formatoDatos));
				if(TipoDocumentoImprimirEnum.DISTINTIVO.getValorEnum().equals(facturacionStock.getTipo())){
					if(facturacionStock.getTipoDistintivo() != null && !facturacionStock.getTipoDistintivo().isEmpty()){
						String valor = obtenerValorTipoDstv(facturacionStock.getTipoDistintivo(), facturacionDocBean).toString();
						sheet.addCell(new Label(cont++, conFila, valor, formatoDatos));
						sheet.addCell(new Label(cont++, conFila, valor, formatoDatos));
					} else {
						Long valorTotal = new Long(0);
						Long valor = obtenerValorTipoDstv(TipoDistintivo.CERO.getValorEnum(), facturacionDocBean);
						sheet.addCell(new Label(cont++, conFila, valor.toString(), formatoDatos));
						valorTotal += valor;
						valor = obtenerValorTipoDstv(TipoDistintivo.ECO.getValorEnum(), facturacionDocBean);
						sheet.addCell(new Label(cont++, conFila, valor.toString(), formatoDatos));
						valorTotal += valor;
						valor = obtenerValorTipoDstv(TipoDistintivo.C.getValorEnum(), facturacionDocBean);
						sheet.addCell(new Label(cont++, conFila, valor.toString(), formatoDatos));
						valorTotal += valor;
						valor = obtenerValorTipoDstv(TipoDistintivo.B.getValorEnum(), facturacionDocBean);
						sheet.addCell(new Label(cont++, conFila, valor.toString(), formatoDatos));
						valorTotal += valor;
						valor = obtenerValorTipoDstv(TipoDistintivo.CEROMT.getValorEnum(), facturacionDocBean);
						sheet.addCell(new Label(cont++, conFila, valor.toString(), formatoDatos));
						valorTotal += valor;
						valor = obtenerValorTipoDstv(TipoDistintivo.ECOMT.getValorEnum(), facturacionDocBean);
						sheet.addCell(new Label(cont++, conFila, valor.toString(), formatoDatos));
						valorTotal += valor;
						valor = obtenerValorTipoDstv(TipoDistintivo.CMT.getValorEnum(), facturacionDocBean);
						sheet.addCell(new Label(cont++, conFila, valor.toString(), formatoDatos));
						valorTotal += valor;
						valor = obtenerValorTipoDstv(TipoDistintivo.BMT.getValorEnum(), facturacionDocBean);
						sheet.addCell(new Label(cont++, conFila, valor.toString(), formatoDatos));
						valorTotal += valor;
						sheet.addCell(new Label(cont++, conFila, valorTotal.toString(), formatoDatos));
						
					}
				} else if(TipoDocumentoImprimirEnum.PERMISO_CIRCULACION.getValorEnum().equals(facturacionStock.getTipo())){
					//sheet.addCell(new Label(cont++, conFila, facturacionDocBean.getTotalPermisos().toString(), formatoDatos));
					//sheet.addCell(new Label(cont++, conFila, facturacionDocBean.getTotalPermisos().toString(), formatoDatos));
				} else if(TipoDocumentoImprimirEnum.FICHA_TECNICA.getValorEnum().equals(facturacionStock.getTipo())){
					//sheet.addCell(new Label(cont++, conFila, facturacionDocBean.getTotalFichas().toString(), formatoDatos));
					//sheet.addCell(new Label(cont++, conFila, facturacionDocBean.getTotalFichas().toString(), formatoDatos));
				}
				conFila++;
			}
			copyWorkbook.write();
		} catch (Throwable e) {
			log.error("Ha sucedido un error a la hora de generar el excel con la facturación, error: ",e);
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("Ha sucedido un error a la hora de generar el excel con la facturación.");
		} finally {
			if (copyWorkbook != null) {
				try {
					copyWorkbook.close();
				} catch (WriteException e) {
					log.error("Error al imprimir tramites de baja ", e);
				} catch (IOException e) {
					log.error("Error al imprimir tramites de baja ", e);
				}
			}
		}
		return resultado;
	}

	private Long obtenerValorTipoDstv(String tipoDistintivo, FacturacionDocBean facturacionDocBean) {
		Long valor = new Long(0);
		if(TipoDistintivo.C.getValorEnum().equals(tipoDistintivo)){
			if(facturacionDocBean.getTotalC() != null){
				valor += facturacionDocBean.getTotalC();
			}
			if(facturacionDocBean.getTotalCVh() != null){
				valor += facturacionDocBean.getTotalCVh();
			}
		}else if(TipoDistintivo.CERO.getValorEnum().equals(tipoDistintivo)){
			if(facturacionDocBean.getTotalCero() != null){
				valor += facturacionDocBean.getTotalCero();
			}
			if(facturacionDocBean.getTotalCeroVh() != null){
				valor += facturacionDocBean.getTotalCeroVh();
			}
		}else if(TipoDistintivo.B.getValorEnum().equals(tipoDistintivo)){
			if(facturacionDocBean.getTotalB() != null){
				valor += facturacionDocBean.getTotalB();
			}
			if(facturacionDocBean.getTotalBVh() != null){
				valor += facturacionDocBean.getTotalBVh();
			}
		}else if(TipoDistintivo.ECO.getValorEnum().equals(tipoDistintivo)){
			if(facturacionDocBean.getTotalEco() != null){
				valor += facturacionDocBean.getTotalEco();
			}
			if(facturacionDocBean.getTotalEcoVh() != null){
				valor += facturacionDocBean.getTotalEcoVh();
			}
		}else if(TipoDistintivo.CMT.getValorEnum().equals(tipoDistintivo)){
			if(facturacionDocBean.getTotalCMts() != null){
				valor += facturacionDocBean.getTotalCMts();
			}
			if(facturacionDocBean.getTotalCMtsVh() != null){
				valor += facturacionDocBean.getTotalCMtsVh();
			}
		}else if(TipoDistintivo.BMT.getValorEnum().equals(tipoDistintivo)){
			if(facturacionDocBean.getTotalBMts() != null){
				valor += facturacionDocBean.getTotalBMts();
			}
			if(facturacionDocBean.getTotalBMtsVh() != null){
				valor += facturacionDocBean.getTotalBMtsVh();
			}
		}else if(TipoDistintivo.CEROMT.getValorEnum().equals(tipoDistintivo)){
			if(facturacionDocBean.getTotalCeroMts() != null){
				valor += facturacionDocBean.getTotalCeroMts();
			}
			if(facturacionDocBean.getTotalCeroMtsVh() != null){
				valor += facturacionDocBean.getTotalCeroMtsVh();
			}
		}else if(TipoDistintivo.ECOMT.getValorEnum().equals(tipoDistintivo)){
			if(facturacionDocBean.getTotalEcoMts() != null){
				valor += facturacionDocBean.getTotalEcoMts();
			}
			if(facturacionDocBean.getTotalEcoMtsVh() != null){
				valor += facturacionDocBean.getTotalEcoMtsVh();
			}
		}
		return valor;
	}
	
	private ResultadoFacturacionStockBean validarCamposBusquedaFctStock(FacturacionStockBean facturacionStock) {
		ResultadoFacturacionStockBean resultado = new ResultadoFacturacionStockBean(Boolean.FALSE);
		if(facturacionStock == null){
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("Debe de indicar algun dato para poder obtener la facturación.");
		} else if(facturacionStock.getFecha() == null || facturacionStock.getFecha().isfechaInicioNula()){
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("Debe de indicar la fecha de inicio para poder obtener la facturación.");
		}
		return resultado;
	}

	@Override
	public FacturacionStockDto obtenerInformacionColegiado(Long contrato, FacturacionStockDto facturacionStockDto) {
		 ContratoVO contratoVO = contratoDao.obtenerContratoPorId(contrato);
		 if (contratoVO != null) {
			 facturacionStockDto.setNumColegiado(contratoVO.getColegiado().getNumColegiado());
			 facturacionStockDto.setNombreColegiado(contratoVO.getRazonSocial());
			 facturacionStockDto.setProvincia(contratoVO.getProvincia().getNombre());
			 facturacionStockDto.setVia(contratoVO.getVia());
		 }
		 return facturacionStockDto;
	}

}
