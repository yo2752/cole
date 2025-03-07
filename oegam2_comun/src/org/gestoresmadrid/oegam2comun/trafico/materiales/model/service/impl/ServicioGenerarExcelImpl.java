package org.gestoresmadrid.oegam2comun.trafico.materiales.model.service.impl;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Hashtable;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFPalette;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.CellStyle;
import org.gestoresmadrid.oegam2comun.trafico.materiales.model.service.ServicioGenerarExcel;
import org.gestoresmadrid.oegam2comun.trafico.materiales.view.dto.FacturacionStockDto;
import org.springframework.stereotype.Service;

import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;

@Service
public class ServicioGenerarExcelImpl implements ServicioGenerarExcel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 707564565128944700L;

	@SuppressWarnings("unused")
	private static final ILoggerOegam log = LoggerOegam.getLogger(ServicioGenerarExcelImpl.class);

	private static final int ROW_HEADERS = 0;
	private static final int ROW_DATA    = 1;

	private static final int COL_NUMCOLEGIADO   = 0;
	private static final int COL_NOMCOLEGIADO   = 1;
	private static final int COL_FECHAIMPRESION = 2;
	private static final int COL_PROVINCIA      = 3;
	private static final int COL_VIA            = 4;
	
	private static final int COL_PERMISOS       = 5;
	private static final int COL_EITV           = 6;
	
	private static final int COL_0AZUL          = 7;
	private static final int COL_ECO            = 8;
	private static final int COL_CVERDE         = 9;
	private static final int COL_BAMARILLA      = 10;
	private static final int COL_TOTAL          = 11;
	
	private static final String NO_DATA = "No se han encontrado impresiones para esos datos de búsqueda";

	@SuppressWarnings("resource")
	@Override
	public ByteArrayInputStream generarExcelFacturacionStock(List<FacturacionStockDto> datosFact, String fileName, Long tipoDocumento) {
		HSSFWorkbook wb = null;
		
		if (datosFact.size() == 0) {
			wb = new HSSFWorkbook();
			HSSFSheet sheet = wb.createSheet(fileName);
			
			HSSFRow row = null;
			row = sheet.createRow(ROW_HEADERS);
			HSSFCell cell = null;
			
			HSSFFont negrita = wb.createFont();
			negrita.setColor(HSSFColor.WHITE.index);
			negrita.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
			
			HSSFCellStyle letraNegrita = wb.createCellStyle();
			letraNegrita.setFont(negrita);
			letraNegrita.setFillPattern(CellStyle.SOLID_FOREGROUND);
			
			cell = row.createCell(1);
			cell.setCellValue(NO_DATA);
			cell.setCellStyle(letraNegrita);
			sheet.autoSizeColumn(1);
			
		} else {
			@SuppressWarnings("serial")
			Hashtable<Integer, String> headers = 
					new Hashtable<Integer, String>() {{
						put(COL_NUMCOLEGIADO, "Nro. Colegiado"); 
						put(COL_NOMCOLEGIADO, "Nombre Colegiado");
						put(COL_FECHAIMPRESION, "Fecha Impresión");
						put(COL_PROVINCIA, "Provincia");
						put(COL_VIA, "Vía");
						put(COL_PERMISOS, "Nro. Permisos");
						put(COL_EITV, "Nro. EITV");
						put(COL_0AZUL, "Ambiental 0 Azul");
						put(COL_ECO, "Ambiental Eco");
						put(COL_CVERDE, "Ambiental C Verde");
						put(COL_BAMARILLA, "Ambiental B Amarilla");
						put(COL_TOTAL, "Total");
					}};
			int blancosEitv = 1;
			int blancosDist = 2;
					
			// Se crea el archivo
			wb = new HSSFWorkbook();
			// Se crea la hoja con nombre
			HSSFSheet sheet = wb.createSheet(fileName);
			
			HSSFRow row = null;
			
			//color fondo rojo y letra blanca
			HSSFPalette palette = wb.getCustomPalette();
			palette.setColorAtIndex(HSSFColor.LAVENDER.index, (byte) 165, (byte)  38, (byte)  66);
			HSSFColor hssfColorRed = palette.getColor(HSSFColor.LAVENDER.index); 
			
			HSSFFont negrita = wb.createFont();
			negrita.setColor(HSSFColor.WHITE.index);
			negrita.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
			
			HSSFCellStyle fondoRed = wb.createCellStyle();
			fondoRed.setFillForegroundColor(hssfColorRed.getIndex());
			fondoRed.setFont(negrita);
			fondoRed.setFillPattern(CellStyle.SOLID_FOREGROUND);
			
			row = sheet.createRow(ROW_HEADERS);
			HSSFCell cell = null;
			
			cell = row.createCell(COL_NUMCOLEGIADO);
			cell.setCellValue(headers.get(COL_NUMCOLEGIADO));
			cell.setCellStyle(fondoRed);
			sheet.autoSizeColumn(COL_NUMCOLEGIADO);
			
			cell = row.createCell(COL_NOMCOLEGIADO);
			cell.setCellValue(headers.get(COL_NOMCOLEGIADO));
			cell.setCellStyle(fondoRed);
			sheet.autoSizeColumn(COL_NOMCOLEGIADO);
			
			cell = row.createCell(COL_FECHAIMPRESION);
			cell.setCellValue(headers.get(COL_FECHAIMPRESION));
			cell.setCellStyle(fondoRed);
			sheet.autoSizeColumn(COL_FECHAIMPRESION);
			
			cell = row.createCell(COL_PROVINCIA);
			cell.setCellValue(headers.get(COL_PROVINCIA));
			cell.setCellStyle(fondoRed);
			sheet.autoSizeColumn(COL_PROVINCIA);
			
			cell = row.createCell(COL_VIA);
			cell.setCellValue(headers.get(COL_VIA));
			cell.setCellStyle(fondoRed);
			sheet.autoSizeColumn(COL_VIA);
			
			if (tipoDocumento == 2) {
				cell = row.createCell(COL_PERMISOS);
				cell.setCellValue(headers.get(COL_PERMISOS));
				cell.setCellStyle(fondoRed);
				sheet.autoSizeColumn(COL_PERMISOS);
			}
			
			if (tipoDocumento == 3) {
				cell = row.createCell(COL_EITV - blancosEitv);
				cell.setCellValue(headers.get(COL_EITV));
				cell.setCellStyle(fondoRed);
				sheet.autoSizeColumn(COL_EITV - blancosEitv);
			}
			
			if (tipoDocumento == 1) {
				cell = row.createCell(COL_0AZUL - blancosDist);
				cell.setCellValue(headers.get(COL_0AZUL));
				cell.setCellStyle(fondoRed);
				sheet.autoSizeColumn(COL_0AZUL - blancosDist);
	
				cell = row.createCell(COL_ECO - blancosDist);
				cell.setCellValue(headers.get(COL_ECO));
				cell.setCellStyle(fondoRed);
				sheet.autoSizeColumn(COL_ECO - blancosDist);
	
				cell = row.createCell(COL_CVERDE - blancosDist);
				cell.setCellValue(headers.get(COL_CVERDE));
				cell.setCellStyle(fondoRed);
				sheet.autoSizeColumn(COL_CVERDE - blancosDist);
	
				cell = row.createCell(COL_BAMARILLA - blancosDist);
				cell.setCellValue(headers.get(COL_BAMARILLA));
				cell.setCellStyle(fondoRed);
				sheet.autoSizeColumn(COL_BAMARILLA - blancosDist);
	
				cell = row.createCell(COL_TOTAL - blancosDist);
				cell.setCellValue(headers.get(COL_TOTAL));
				cell.setCellStyle(fondoRed);
				sheet.autoSizeColumn(COL_TOTAL - blancosDist);
			}
			
			
			int fila = ROW_DATA;
			for( FacturacionStockDto itemStok : datosFact) {
			row = sheet.createRow(fila);
			
			cell = row.createCell(COL_NUMCOLEGIADO);
			if ( itemStok.getNumColegiado() != null ) {
				cell.setCellValue(itemStok.getNumColegiado());
			}
			sheet.autoSizeColumn(COL_NUMCOLEGIADO);
			
			cell = row.createCell(COL_NOMCOLEGIADO);
			if ( itemStok.getNombreColegiado() != null ) {
				cell.setCellValue(itemStok.getNombreColegiado());
			}
			sheet.autoSizeColumn(COL_NOMCOLEGIADO);
			
			cell = row.createCell(COL_FECHAIMPRESION);
			if ( itemStok.getNombreColegiado() != null ) {
				cell.setCellValue(itemStok.getFechaImpresion());
			}
			sheet.autoSizeColumn(COL_FECHAIMPRESION);
			
			cell = row.createCell(COL_PROVINCIA);
			if ( itemStok.getProvincia() != null ) {
				cell.setCellValue(itemStok.getProvincia());
			}
			sheet.autoSizeColumn(COL_PROVINCIA);
			
			cell = row.createCell(COL_VIA);
			if ( itemStok.getVia() != null ) {
				cell.setCellValue(itemStok.getVia());
			}
			sheet.autoSizeColumn(COL_VIA);
			
			if (tipoDocumento == 2) {
				cell = row.createCell(COL_PERMISOS);
				cell.setCellValue(itemStok.getPermisos());
				sheet.autoSizeColumn(COL_PERMISOS);
			}
			
			if (tipoDocumento == 3) {
				cell = row.createCell(COL_EITV - blancosEitv);
				cell.setCellValue(itemStok.getEitv());
				sheet.autoSizeColumn(COL_EITV - blancosEitv);
			}
	
			if (tipoDocumento == 1) {
				cell = row.createCell(COL_0AZUL - blancosDist);
				cell.setCellValue(itemStok.getNro0Azul());
				sheet.autoSizeColumn(COL_0AZUL - blancosDist);
				
				cell = row.createCell(COL_ECO - blancosDist);
				cell.setCellValue(itemStok.getMroEco());
				sheet.autoSizeColumn(COL_ECO - blancosDist);
				
				cell = row.createCell(COL_CVERDE - blancosDist);
				cell.setCellValue(itemStok.getNroCVerde());
				sheet.autoSizeColumn(COL_CVERDE - blancosDist);
				
				cell = row.createCell(COL_BAMARILLA - blancosDist);
				cell.setCellValue(itemStok.getNroBAmarilla());
				sheet.autoSizeColumn(COL_BAMARILLA - blancosDist);
				
				cell = row.createCell(COL_TOTAL - blancosDist);
				cell.setCellValue(itemStok.getTotal());
				sheet.autoSizeColumn(COL_TOTAL - blancosDist);
			}
			
			fila++;
			}
		}
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		try {
			wb.write(baos);
			ByteArrayInputStream bis = new ByteArrayInputStream(baos.toByteArray());
			return bis;
		} catch (IOException e) {
			e.printStackTrace();
		}		

		return null;
	}


}
