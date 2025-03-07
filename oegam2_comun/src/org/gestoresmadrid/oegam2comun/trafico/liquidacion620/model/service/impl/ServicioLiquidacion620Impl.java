package org.gestoresmadrid.oegam2comun.trafico.liquidacion620.model.service.impl;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.gestoresmadrid.core.model.enumerados.EstadoTramiteTrafico;
import org.gestoresmadrid.core.trafico.liquidacion620.beans.ResultadoLiquidacion620;
import org.gestoresmadrid.core.trafico.liquidacion620.model.dao.Liquidacion620Dao;
import org.gestoresmadrid.oegam2comun.correo.model.service.ServicioCorreo;
import org.gestoresmadrid.oegam2comun.trafico.liquidacion620.model.service.ServicioLiquidacion620;
import org.gestoresmadrid.utilidades.components.GestorPropiedades;
import org.gestoresmadrid.utilidades.components.UtilesFecha;
import org.jfree.util.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import es.globaltms.gestorDocumentos.bean.FicheroBean;
import es.globaltms.gestorDocumentos.constantes.ConstantesGestorFicheros;
import es.globaltms.gestorDocumentos.interfaz.GestorDocumentos;
import escrituras.beans.ResultBean;
import utilidades.estructuras.Fecha;
import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;
import utilidades.web.OegamExcepcion;

@Service
public class ServicioLiquidacion620Impl implements ServicioLiquidacion620{

	private static final long serialVersionUID = 1078293993169810210L;

	private static ILoggerOegam log = LoggerOegam.getLogger(ServicioLiquidacion620Impl.class);

	@Autowired
	Liquidacion620Dao liquidacion620Dao;

	@Autowired
	GestorDocumentos gestorDocumentos;

	@Autowired
	ServicioCorreo servicioCorreo;

	@Autowired
	private GestorPropiedades gestorPropiedades;

	@Autowired
	UtilesFecha utilesFecha;

	@Override
	@Transactional
	public ResultBean getExcelLiquidacion620Hoy() {
		ResultBean salida = new ResultBean(Boolean.FALSE);
		ArrayList<ResultadoLiquidacion620> listado = null;
		
		listado = (ArrayList<ResultadoLiquidacion620>) liquidacion620Dao.getListaLiquidaciones620(new Date(),new Date(),null);
		if(listado!=null && listado.size()>0){
			salida.addAttachment("excel", crearExcel(listado));
		}else{
			salida.setError(Boolean.TRUE);
			salida.setMensaje("No hay resultados de liquidaciones para el día de hoy");
		}
		return salida;
	}

	private XSSFWorkbook crearExcel(ArrayList<ResultadoLiquidacion620> listado){
		XSSFWorkbook myWorkBook =null;

		if(listado!=null && listado.size()>0){
			try {
				boolean cabecera=true;
				int fila = 0;
				int col = 0;
				myWorkBook = new XSSFWorkbook();
				XSSFSheet sheet = myWorkBook.createSheet("Liquidaciones" );
				XSSFCellStyle estiloNegrita  = myWorkBook.createCellStyle();
				XSSFFont negrita = myWorkBook.createFont();
				negrita.setBold(true);
				negrita.setFontHeight(9);
				estiloNegrita.setFont(negrita);
				sheet.autoSizeColumn(col);
				XSSFRow row = sheet.createRow(fila++);
				for(ResultadoLiquidacion620 element : listado){
					if(cabecera){
						cabecera = false;
						row.createCell(col).setCellValue("NÚMERO EXPEDIENTE");
						row.getCell(col).setCellStyle(estiloNegrita);
						col++;
						row.createCell(col).setCellValue("ESTADO");
						row.getCell(col).setCellStyle(estiloNegrita);
						col++;
						row.createCell(col).setCellValue("MATRICULA");
						row.getCell(col).setCellStyle(estiloNegrita);
						col++;
						row.createCell(col).setCellValue("MARCA");
						row.getCell(col).setCellStyle(estiloNegrita);
						col++;
						row.createCell(col).setCellValue("MODELO");
						row.getCell(col).setCellStyle(estiloNegrita);
						col++;
						row.createCell(col).setCellValue("VALOR DECLARADO");
						row.getCell(col).setCellStyle(estiloNegrita);
						col++;
						row.createCell(col).setCellValue("BASE IMPONIBLE");
						row.getCell(col).setCellStyle(estiloNegrita);
						col++;
						row.createCell(col).setCellValue("CUOTA TRIBUTARIA");
						row.getCell(col).setCellStyle(estiloNegrita);
						col++;
						row.createCell(col).setCellValue("VALOR CAM");
						row.getCell(col).setCellStyle(estiloNegrita);
						col++;
						row.createCell(col).setCellValue("BASE IMP. CAM");
						row.getCell(col).setCellStyle(estiloNegrita);
						col++;
						row.createCell(col).setCellValue("CUOTA CAM");
						row.getCell(col).setCellStyle(estiloNegrita);
						col++;
						row.createCell(col).setCellValue("DIFERENCIA CUOTA NO PAGADA");
						row.getCell(col).setCellStyle(estiloNegrita);
						col++;
					}
					if(comprobarValorCam(element.getValorCam(),element.getValorDeclarado())){
						col = 0;
						row = sheet.createRow(fila++);
						row.createCell(col++).setCellValue(element.getNumExpediente().toString());
						row.createCell(col++).setCellValue(EstadoTramiteTrafico.convertirTexto(element.getEstado()));
						row.createCell(col++).setCellValue(element.getMatricula());
						row.createCell(col++).setCellValue(element.getMarca());
						row.createCell(col++).setCellValue(element.getModelo());
						row.createCell(col++).setCellValue(element.getValorDeclarado().toString());
						row.createCell(col++).setCellValue(element.getBaseImponible().toString());
						row.createCell(col++).setCellValue(element.getCuotaTributaria().toString());
						row.createCell(col++).setCellValue(element.getValorCam().toString());
						row.createCell(col++).setCellValue(calcularBaseImpCam(element.getFechaDevengo(),element.getFechaPrimMat(),element.getValorCam()).toString());
						row.createCell(col++).setCellValue(calcularCuotaCam(element.getFechaDevengo(),element.getFechaPrimMat(),element.getValorCam()).toString());
						row.createCell(col++).setCellValue(calcularCuotaCam(element.getFechaDevengo(),element.getFechaPrimMat(),element.getValorCam()).subtract(element.getCuotaTributaria()).toString());
					}

				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		return myWorkBook;
	}

	private boolean comprobarValorCam(BigDecimal valorCam, BigDecimal valorDeclarado){
		try{
			if(valorDeclarado.compareTo(valorCam)==1){
				return false;
			}
		}catch(Exception e){
			Log.error("No se ha podido calcular la base imponible cam " +e.getMessage());
		}
		return true;

	}

	private BigDecimal calcularBaseImpCam(Date fechaDevengo, Date fechaPrimMat, BigDecimal valorCam ){
		try{
			BigDecimal cuotaReduc = calcularCuotaReduccion(fechaDevengo, fechaPrimMat);

			return valorCam.multiply(cuotaReduc.divide(new BigDecimal(100)));

		}catch(Exception e){
			Log.error("No se ha podido calcular la base imponible cam " +e.getMessage());
		}
		return valorCam;
	}

	private BigDecimal calcularCuotaReduccion(Date fechaDevengo, Date fechaPrimMat){
		BigDecimal cuotaSalida = null;
		try{
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy");
			String anioDevengo = dateFormat.format(fechaDevengo);
			String anioPrimMat = dateFormat.format(fechaPrimMat);
			Integer intAnioDev = new Integer(anioDevengo);
			Integer intAnioPrimMat = new Integer(anioPrimMat);

			int diferencia = intAnioDev.intValue() - intAnioPrimMat.intValue();

			if(diferencia>12){
				diferencia = 12;
			}

			switch (diferencia) {
				case 0: cuotaSalida  = new BigDecimal(100);
				break;
				case 1: cuotaSalida = new BigDecimal(84);
				break;
				case 2: cuotaSalida = new BigDecimal(67);
				break;
				case 3: cuotaSalida = new BigDecimal(56);
				break;
				case 4: cuotaSalida = new BigDecimal(47);
				break;
				case 5: cuotaSalida = new BigDecimal(39);
				break;
				case 6: cuotaSalida = new BigDecimal(34);
				break;
				case 7: cuotaSalida = new BigDecimal(28);
				break;
				case 8: cuotaSalida = new BigDecimal(24);
				break;
				case 9: cuotaSalida =new BigDecimal(19);
				break;
				case 10: cuotaSalida = new BigDecimal(17);
				break;
				case 11: cuotaSalida =new BigDecimal(13);
				break;
				case 12: cuotaSalida = new BigDecimal(10);
				break;
				default:
					break;
			}
		}catch(Exception e){
			cuotaSalida = new BigDecimal(0);
		}
		return cuotaSalida;
	}

	private BigDecimal calcularCuotaCam(Date fechaDevengo, Date fechaPrimMat, BigDecimal valorCam){
		BigDecimal gravamen = new BigDecimal(4);
		BigDecimal baseImpCam =null;
		try{
			baseImpCam = calcularBaseImpCam(fechaDevengo, fechaPrimMat, valorCam );
		}catch(Exception e){
			Log.error("No se ha podido calcular la cuota CAM " + e.getMessage());
		}
		return gravamen.divide(new BigDecimal(100)).multiply(baseImpCam);
	}

	@Override
	@Transactional
	public ResultBean getExcelLiquidacion620PorFecha(Date fechaIni, Date fechaFin, String numColegiado) {
		ResultBean salida = new ResultBean(Boolean.FALSE);
		ArrayList<ResultadoLiquidacion620> listado = null;

		listado = 	(ArrayList<ResultadoLiquidacion620>) liquidacion620Dao.getListaLiquidaciones620(fechaIni,fechaFin, numColegiado);
		if(listado != null && listado.size() > 0){
			salida.addAttachment("excel", crearExcel(listado));
		}else{
			salida.setError(Boolean.TRUE);
			salida.setMensaje("No existe listado para el rango de fecha marcado o el colegiado no ha liquidado 620 en la fecha indicada");
		}
		return salida;
	}

	@Override
	@Transactional
	public ResultBean getExcelLiquidacion620DiaAnterior() {
		ResultBean salida = new ResultBean(Boolean.FALSE);
		ArrayList<ResultadoLiquidacion620> listado = null;

		try {
			listado = (ArrayList<ResultadoLiquidacion620>) liquidacion620Dao.getListaLiquidaciones620(utilesFecha.getPrimerLaborableAnterior().getDate(), utilesFecha.getPrimerLaborableAnterior()
					.getDate(), null);
		} catch (ParseException e) {
			log.error("Ha sucedido un error a la hora de obtener el listado para crear el excel, error: ", e);
			salida.setError(Boolean.TRUE);
			salida.setMensaje("Ha sucedido un error a la hora de obtener el listado para crear el excel.");
		} catch (OegamExcepcion e) {
			log.error("Ha sucedido un error a la hora de obtener el listado para crear el excel, error: ", e);
			salida.setError(Boolean.TRUE);
			salida.setMensaje("Ha sucedido un error a la hora de obtener el listado para crear el excel.");
		}

		if (listado != null && listado.size() > 0) {
			guardarFichero((XSSFWorkbook) crearExcel(listado));
		} else {
			salida.setError(Boolean.TRUE);
			salida.setMensaje("No hay resultados de liquidaciones para el día de hoy");
		}
		return salida;
	}

	private boolean guardarFichero(XSSFWorkbook workbook) {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		try {
			workbook.write(baos);
			Fecha fecha = utilesFecha.getFechaActual();
			String nombreFichero = "Liquidacion620_" + utilesFecha.formatoFecha("yyyyMMdd", new Date());
			File fichero = gestorDocumentos.guardarFichero(ConstantesGestorFicheros.TIPO_EXCEL620, null, fecha, nombreFichero, ConstantesGestorFicheros.EXTENSION_XLSX, baos.toByteArray());

			if (fichero != null) {
				StringBuilder texto = new StringBuilder();
				texto.append("Se ha generado la excel con las liquidaciones 620 del día " + utilesFecha.restarDias(new Date(), -1, null, null, null).toString() + " ");
				FicheroBean adjunto = new FicheroBean();
				adjunto.setNombreYExtension("Liquidaciones620" + utilesFecha.formatoFecha("yyyyMMdd", new Date()) + ".xlsx");
				adjunto.setFichero(fichero);
				servicioCorreo.enviarCorreo(texto.toString(), null, null, PROPERTIE_CORREO_ASUNTO, gestorPropiedades.valorPropertie(PROPERTIE_CORREO_PARA), gestorPropiedades.valorPropertie(PROPERTIE_CORREO_COPIA), null,
						null, adjunto);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} catch (OegamExcepcion e) {
			e.printStackTrace();
		}
		return true;

	}
}