package org.gestoresmadrid.oegam2comun.trafico.materiales.model.service.impl;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.imageio.ImageIO;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFPalette;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.ClientAnchor;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.Drawing;
import org.apache.poi.ss.usermodel.Picture;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.gestoresmadrid.core.tipoPermDistItv.model.enumerado.TipoDocumentoImprimirEnum;
import org.gestoresmadrid.oegam2comun.trafico.materiales.model.service.ServicioFacturacionSemanal;
import org.gestoresmadrid.oegam2comun.trafico.materiales.model.service.ServicioGenerarExcelStockSemanal;
import org.gestoresmadrid.oegam2comun.trafico.materiales.view.dto.FacturacionSemanalBean;
import org.gestoresmadrid.oegam2comun.trafico.materiales.view.dto.ValueFechaBean;
import org.springframework.stereotype.Service;

import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;

@Service("ServicioGenerarExcelStockSemanalImpl")
public class ServicioGenerarExcelStockSemanalImpl implements ServicioGenerarExcelStockSemanal {

	private static final String LOGO_PROPERTY     = "logo3.png";
	private static final String CABECERA_PROPERTY = "CuadroTextICOGAM.png";

	private static final ILoggerOegam log = LoggerOegam.getLogger(ServicioGenerarExcelImpl.class);

	private static final String FONT_BLACK_BOLD   = "negritaBold";
	private static final String FONT_BLACK_NORMAL = "negritaNormal";
	
	
	Map<String, HSSFFont> fonts = new HashMap<String, HSSFFont>();
	
	@Override
	public ByteArrayInputStream generarExcelWithData(FacturacionSemanalBean facturacionSemanalBean,
			String fileName, Date fecInforme) {
		HSSFWorkbook wb = new HSSFWorkbook();
		
		initFonts(wb);
		
		String tituloSheetStock = "Stock";
		HSSFSheet sheetStock = wb.createSheet(tituloSheetStock);
		
		distintivosStock(wb, sheetStock, 2, 9, fecInforme, facturacionSemanalBean);
		
		distintivosInpresion(wb, 2, 9, fecInforme, facturacionSemanalBean);
		
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
	
	private void distintivosInpresion(HSSFWorkbook wb, int column, int row,
			Date fecInforme, FacturacionSemanalBean facturacionSemanalBean) {


		HashMap<String, HashMap<String, HashMap<String, Long>>> impresiones = 
											facturacionSemanalBean.getLineasFacturacionSemanalImpresion();
		
		Set<Entry<String, HashMap<String, HashMap<String, Long>>>> setMap = impresiones.entrySet();
		Iterator<Entry<String, HashMap<String, HashMap<String, Long>>>> iteratorMap = setMap.iterator();

		int rowJefatura = row;
		int indexJefatura = 0;

		while(iteratorMap.hasNext()) {
			
			Map.Entry<String, HashMap<String, HashMap<String, Long>>> entry =
                    (Map.Entry<String, HashMap<String, HashMap<String, Long>>>) iteratorMap.next();

			// Sheet, cabecera por jefatura
			String tituloSheetJefatura = "Volumen impresión -- " + entry.getKey();
			HSSFSheet sheetImpresion = wb.createSheet(tituloSheetJefatura);
			
			insertarImage(wb, sheetImpresion, LOGO_PROPERTY, 1 ,1);
			insertarImage(wb, sheetImpresion, CABECERA_PROPERTY, 3, 1);
			
			insertarTitulo(wb, sheetImpresion, construirTextoFecha(fecInforme), 2, 7);

			String titulo = "VOLUMEN DE IMPRESIÓN";
			insertarTitulo(wb, sheetImpresion, titulo, column, rowJefatura);

			insertarTituloJefatura(wb, sheetImpresion, "JEFATURA: " + entry.getKey(), column, rowJefatura + 2);
			
			if (impresiones.size() == 0) {
				insertarNoHayDatos(wb, sheetImpresion, column, rowJefatura + 2);
			}

			int columnTabla = column;

			HashMap<String, HashMap<String, Long>> valueFecha = entry.getValue();
			ValueFechaBean valueFechaBean = particionarValuesFecha(valueFecha);
			
			if (facturacionSemanalBean.getDocumentos()[0]) {
				insertarTituloDocumento(wb, sheetImpresion, "DISTINTIVOS: ", columnTabla, rowJefatura + 4);
				String[] tiposDist = createArrayDistintivos(facturacionSemanalBean.getTipos(), false);
				insertarCabeceraTablaImpresionDistintivos(wb, sheetImpresion, columnTabla, rowJefatura + 6, tiposDist);
				indexJefatura = insertarBodyFacturacionMaterial(wb, sheetImpresion, columnTabla, rowJefatura + 6, tiposDist, valueFechaBean.getValueFechaDistintivos());
				
				columnTabla += 15;
			}
			
			
			if (facturacionSemanalBean.getDocumentos()[1]) {
				insertarTituloDocumento(wb, sheetImpresion, "PERMISOS: ", columnTabla, rowJefatura + 4);
				String[] tiposDist = {TipoDocumentoImprimirEnum.PERMISO_CIRCULACION.getValorEnum(),ServicioFacturacionSemanal.TIPO_INCIDENCIA_PERMISO}; 
				insertarCabeceraTablaImpresionDistintivos(wb, sheetImpresion, columnTabla, rowJefatura + 6, tiposDist);
				int indexJefaturaPermisos = insertarBodyFacturacionMaterial(wb, sheetImpresion, columnTabla, rowJefatura + 6, tiposDist,  valueFechaBean.getValueFechaPermisos());
				indexJefatura = (indexJefaturaPermisos > indexJefatura)? indexJefaturaPermisos: indexJefatura;
				
				columnTabla += 5;
			}
			
			if (facturacionSemanalBean.getDocumentos()[2]) {
				insertarTituloDocumento(wb, sheetImpresion, "FICHAS TÉCNICAS: ", columnTabla, rowJefatura + 4);
				String[] tiposDist = {TipoDocumentoImprimirEnum.FICHA_TECNICA.getValorEnum(), ServicioFacturacionSemanal.TIPO_INCIDENCIA_FICHA}; 
				insertarCabeceraTablaImpresionDistintivos(wb, sheetImpresion, columnTabla, rowJefatura + 6, tiposDist);
				int indexJefaturaPermisos = insertarBodyFacturacionMaterial(wb, sheetImpresion, columnTabla, rowJefatura + 6, tiposDist,  valueFechaBean.getValueFechaFichasTecnicas());
				indexJefatura = (indexJefaturaPermisos > indexJefatura)? indexJefaturaPermisos: indexJefatura;
			}
			
		}

	}


	private void insertarNoHayDatos(HSSFWorkbook wb, HSSFSheet sheet, int column, int row) {

		String titulo = "No se ha encontrado información de impresión para estos datos";

		HSSFRow rowExcel = sheet.getRow(row);
		if (rowExcel == null) {
			rowExcel = sheet.createRow(row);
		}
		
		HSSFCell cell = null;
		
		CellRangeAddress regionToMerge = new CellRangeAddress(row, row, column, column + 8);
		cell = rowExcel.createCell(column);
		cell.setCellValue(titulo);
		sheet.addMergedRegion(regionToMerge);
		
		for( int j = regionToMerge.getFirstColumn(); j <= regionToMerge.getLastColumn(); j++ ) {
			 Cell cellItem = rowExcel.getCell(j);
			 if (cellItem == null) {
				 cellItem = rowExcel.createCell(j);
			 }
			 cellItem.setCellStyle(styleBlueBoxWithoutBorder(wb));
		}
	}

	private void insertarTituloDocumento(HSSFWorkbook wb, HSSFSheet sheet, String titulo, int column, int row) {

		HSSFCellStyle style = styleWhiteBoxWithoutBorder(wb);

		
		HSSFRow rowExcel = sheet.getRow(row);
		if (rowExcel == null) {
			rowExcel = sheet.createRow(row);
		}
		
		HSSFCell cell = null;
		
		CellRangeAddress regionToMerge = new CellRangeAddress(row, row, column, column + 2);
		cell = rowExcel.createCell(column);
		cell.setCellValue(titulo);
		sheet.addMergedRegion(regionToMerge);
		
		for( int j = regionToMerge.getFirstColumn(); j <= regionToMerge.getLastColumn(); j++ ) {
			 Cell cellItem = rowExcel.getCell(j);
			 if (cellItem == null) {
				 cellItem = rowExcel.createCell(j);
			 }
			 cellItem.setCellStyle(style);
		}

	}

	private void insertarTituloJefatura(HSSFWorkbook wb, HSSFSheet sheet, String titulo, int column, int row) {
		HSSFCellStyle style = styleWhiteBoxWithoutBorder(wb);

		HSSFRow rowExcel = sheet.getRow(row);
		if (rowExcel == null) {
			rowExcel = sheet.createRow(row);
		}
		
		HSSFCell cell = null;
		
		CellRangeAddress regionToMerge = new CellRangeAddress(row, row, column, column + 4);
		cell = rowExcel.createCell(column);
		cell.setCellValue(titulo);
		sheet.addMergedRegion(regionToMerge);
		
		for( int j = regionToMerge.getFirstColumn(); j <= regionToMerge.getLastColumn(); j++ ) {
			 Cell cellItem = rowExcel.getCell(j);
			 if (cellItem == null) {
				 cellItem = rowExcel.createCell(j);
			 }
			 cellItem.setCellStyle(style);
		}
	
		
	}

	private void distintivosStock(HSSFWorkbook wb, HSSFSheet sheet, int column, int row
			, Date fecInforme, FacturacionSemanalBean facturacionSemanalBean) {

		insertarImage(wb, sheet, LOGO_PROPERTY, 1 ,1);
		insertarImage(wb, sheet, CABECERA_PROPERTY, 3, 1);
		
		insertarTitulo(wb, sheet, construirTextoFecha(fecInforme), 2, 7);

		int columnTabla = column;
		int rowTable = row;
		
		String titulo = "DISTINTIVOS -STOCK-";
		insertarTitulo(wb, sheet, titulo, columnTabla, rowTable);
		String[] tiposDist = createArrayDistintivos(facturacionSemanalBean.getTipos(), true);
		insertarCabeceraTablaStockDistintivos(wb, sheet, columnTabla, rowTable + 2, tiposDist);
		insertarBodyTableStockDistintivos(wb, sheet, columnTabla, rowTable + 2, tiposDist, facturacionSemanalBean);
		
		columnTabla += 12;

		titulo = "PERMISOS -STOCK-";
		insertarTitulo(wb, sheet, titulo, columnTabla, rowTable);
		String[] tiposPerm = {TipoDocumentoImprimirEnum.PERMISO_CIRCULACION.getValorEnum()};
		insertarCabeceraTablaStockPermisos(wb, sheet, columnTabla, rowTable + 2, tiposPerm);
		insertarBodyTableStockPermisos(wb, sheet, columnTabla, rowTable + 2, tiposPerm, facturacionSemanalBean);


	}

	private ValueFechaBean particionarValuesFecha(HashMap<String, HashMap<String, Long>> valueFecha) {
		HashMap<String, HashMap<String, Long>> valueFechaWork = clonarValueFecha(valueFecha); 
		
		HashMap<String, HashMap<String, Long>> valueFechaDistintivos    = new HashMap<String, HashMap<String, Long>>();
		HashMap<String, HashMap<String, Long>> valueFechaPermisos       = new HashMap<String, HashMap<String, Long>>();
		HashMap<String, HashMap<String, Long>> valueFechaFichasTecnicas = new HashMap<String, HashMap<String, Long>>();
		
		Set<Entry<String, HashMap<String, Long>>> setMap = valueFechaWork.entrySet();
		Iterator<Entry<String, HashMap<String, Long>>> iteratorMap = setMap.iterator();
		
		while(iteratorMap.hasNext()) {
			Map.Entry<String, HashMap<String, Long>> entry =
                    (Map.Entry<String, HashMap<String, Long>>) iteratorMap.next();
		
			String fecha = entry.getKey();
			HashMap<String, Long> valueMateriales = entry.getValue();

			Set<Entry<String, Long>> setMapMaterial = valueMateriales.entrySet();
			Iterator<Entry<String, Long>> iteratorMapMaterial = setMapMaterial.iterator();

			HashMap<String, Long> valueMaterialDist     = new HashMap<String, Long>();
			HashMap<String, Long> valueMaterialPermisos = new HashMap<String, Long>();
			HashMap<String, Long> valueMaterialFichas   = new HashMap<String, Long>();

			while(iteratorMapMaterial.hasNext()) {
				Map.Entry<String, Long> entryMaterial = (Map.Entry<String, Long>) iteratorMapMaterial.next();
				String material = entryMaterial.getKey();
				Long   unidades = entryMaterial.getValue();
				
				if (TipoDocumentoImprimirEnum.PERMISO_CIRCULACION.getValorEnum().equals(material) ||
						ServicioFacturacionSemanal.TIPO_INCIDENCIA_PERMISO.equals(material)	) {
					valueMaterialPermisos.put(material, unidades);
				} else if (TipoDocumentoImprimirEnum.FICHA_TECNICA.getValorEnum().equals(material) ||
						ServicioFacturacionSemanal.TIPO_INCIDENCIA_FICHA.equals(material)) {
					valueMaterialFichas.put(material, unidades);
				} else {
					if ( material != null ) {
						valueMaterialDist.put(material, unidades);
					}
				}
			}
			
			if (valueMaterialDist.size() != 0) {
				valueFechaDistintivos.put(fecha, valueMaterialDist);
			} 
			
			if (valueMaterialPermisos.size() != 0) {
				valueFechaPermisos.put(fecha, valueMaterialPermisos);
			}
			
			if (valueMaterialFichas.size() != 0) {
				valueFechaFichasTecnicas.put(fecha, valueMaterialFichas);
			}
			
		}
		
		ValueFechaBean valueFechaBean = new ValueFechaBean(valueFechaDistintivos, valueFechaPermisos, valueFechaFichasTecnicas);
		
		return valueFechaBean;
	}


	private int insertarBodyFacturacionMaterial(HSSFWorkbook wb, HSSFSheet sheet, int column, int row,
			String[] tiposDist, HashMap<String, HashMap<String, Long>> valueFecha) {
		
		Set<Entry<String, HashMap<String, Long>>> setMap = valueFecha.entrySet();
		Iterator<Entry<String, HashMap<String, Long>>> iteratorMap = setMap.iterator();
		
		Long totalFechas = 0L;
		
		int rowIndex = row + 2;
		while(iteratorMap.hasNext()) {
			
			Map.Entry<String, HashMap<String, Long>> entry =
                    (Map.Entry<String, HashMap<String, Long>>) iteratorMap.next();
		
			String fecha = entry.getKey();
			HashMap<String, Long> valueMateriales = entry.getValue();

			if (valueMateriales.size() != 0) {
				
				HSSFRow rowFecha = sheet.getRow(rowIndex);
				if (rowFecha == null) {
					rowFecha = sheet.createRow(rowIndex);
				}

				rowIndex++;
				
				HSSFCell cell = rowFecha.createCell(column);
				cell.setCellValue(fecha);
				cell.setCellStyle(styleWhiteBoldBox(wb));
				sheet.autoSizeColumn(column);

				Set<Entry<String, Long>> setMapMaterial = valueMateriales.entrySet();
				Iterator<Entry<String, Long>> iteratorMapMaterial = setMapMaterial.iterator();
				int columnIndex = column + 1;
				
				Long totalFecha  = 0L;
				
				while(iteratorMapMaterial.hasNext()) {
					Map.Entry<String, Long> entryMaterial = (Map.Entry<String, Long>) iteratorMapMaterial.next();
					String material = entryMaterial.getKey();
					Long   unidades = entryMaterial.getValue();
					
					String strUnidades = formatearUnidades(unidades);
					
					int celda = obtenerCelda(material, tiposDist);
					if (celda > -1) {
						Cell cellMaterial = rowFecha.getCell(columnIndex + celda);
						if (cellMaterial == null) {
							cellMaterial = rowFecha.createCell(columnIndex + celda);
						}
							
						cellMaterial.setCellValue( strUnidades );
						totalFecha += unidades;
									
						cellMaterial.setCellStyle(styleWhiteBox(wb));
						sheet.autoSizeColumn(columnIndex + celda);
					}
				}
				
				for( int index = columnIndex; index < columnIndex + tiposDist.length; index++) {
					Cell cellMaterial = rowFecha.getCell(index);
					if (cellMaterial == null) {
						cellMaterial = rowFecha.createCell(index);
						cellMaterial.setCellValue(0L);
						cellMaterial.setCellStyle(styleWhiteBox(wb));
						sheet.autoSizeColumn(index);
					}
				}
				
				HSSFCell cellMaterial = rowFecha.createCell(columnIndex + tiposDist.length);
				cellMaterial.setCellValue( totalFecha );
				cellMaterial.setCellStyle(styleWhiteBox(wb));
				sheet.autoSizeColumn(columnIndex + tiposDist.length);

				totalFechas += totalFecha;
			}
		}
		
		HSSFRow rowTotalFechas = sheet.getRow(rowIndex);
		if (rowTotalFechas == null) {
			rowTotalFechas = sheet.createRow(rowIndex);
		}

		CellRangeAddress regionToMergeTotales = new CellRangeAddress(rowIndex, rowIndex, column, column + tiposDist.length);
		HSSFCell cellTotalLiteral = rowTotalFechas.createCell(column);
		cellTotalLiteral.setCellValue("TOTAL");
		
		for( int j = regionToMergeTotales.getFirstColumn(); j <= regionToMergeTotales.getLastColumn(); j++ ) {
			 Cell cellItem = rowTotalFechas.getCell(j);
			 if (cellItem == null) {
				 cellItem = rowTotalFechas.createCell(j);
			 }
			 cellItem.setCellStyle(styleCreamBoldBox(wb));
		}
		
		sheet.addMergedRegion(regionToMergeTotales);
		
		HSSFCell cellTotalValor = rowTotalFechas.createCell(regionToMergeTotales.getLastColumn() + 1);
		cellTotalValor.setCellValue(totalFechas);
		cellTotalValor.setCellStyle(styleCreamBoldBox(wb));
		sheet.autoSizeColumn(regionToMergeTotales.getLastColumn() + 1);

		return rowIndex;
		
	}

	private String formatearUnidades(Long unidades) {
		
		DecimalFormatSymbols unusualSymbols = new DecimalFormatSymbols(Locale.ENGLISH);
		unusualSymbols.setDecimalSeparator(',');
		unusualSymbols.setGroupingSeparator('.');
		DecimalFormat df = new DecimalFormat();
		df.setGroupingSize(3);		

		return df.format(unidades);
	}

	private HashMap<String, HashMap<String, Long>> clonarValueFecha(HashMap<String, HashMap<String, Long>> origenFecha) {
		
		HashMap<String, HashMap<String, Long>> destinoFecha = new HashMap<String, HashMap<String, Long>>(); 
		
		Set<Entry<String, HashMap<String, Long>>> setMap = origenFecha.entrySet();
		Iterator<Entry<String, HashMap<String, Long>>> iteratorMap = setMap.iterator();
		
		while(iteratorMap.hasNext()) {
			Map.Entry<String, HashMap<String, Long>> entry =
                    (Map.Entry<String, HashMap<String, Long>>) iteratorMap.next();		

			String fecha = entry.getKey();
			
			HashMap<String, Long> origenMateriales = entry.getValue();
			HashMap<String, Long> destinoMateriales = new HashMap<String, Long>(); 

			Set<Entry<String, Long>> setMapMaterial = origenMateriales.entrySet();
			Iterator<Entry<String, Long>> iteratorMapMaterial = setMapMaterial.iterator();

			while(iteratorMapMaterial.hasNext()) {
				Map.Entry<String, Long> entryMaterial = (Map.Entry<String, Long>) iteratorMapMaterial.next();
				String material = entryMaterial.getKey();
				Long   unidades = entryMaterial.getValue();
				
				destinoMateriales.put(material, unidades);
			}
			
			destinoFecha.put(fecha, origenMateriales);
		}
		
		return destinoFecha;
	}

	private void insertarBodyTableStockDistintivos(HSSFWorkbook wb, HSSFSheet sheet, int column, int row,
			String[] tiposDist, FacturacionSemanalBean facturacionSemanalBean) {

		HashMap<String, HashMap<String, Long>> lineasFacturacionSemanalStock =
				facturacionSemanalBean.getLineasFacturacionSemanalStock();
		Set<Entry<String, HashMap<String, Long>>> setMap = lineasFacturacionSemanalStock.entrySet();
		
		Iterator<Entry<String, HashMap<String, Long>>> iteratorMap = setMap.iterator();
		int rowIndex = row + 2;
		while(iteratorMap.hasNext()) {
			Map.Entry<String, HashMap<String, Long>> entry =
                    (Map.Entry<String, HashMap<String, Long>>) iteratorMap.next();
			
			HSSFRow rowJP = sheet.getRow(rowIndex);
			if (rowJP == null) {
				rowJP = sheet.createRow(rowIndex);
			}
			
			rowIndex++;

			String jefatura = entry.getKey();
			HashMap<String, Long> value = entry.getValue();

			HSSFCell cell = rowJP.createCell(column);
			cell.setCellValue(jefatura);
			cell.setCellStyle(styleWhiteBoldBox(wb));
			sheet.autoSizeColumn(column);

			Set<Entry<String, Long>> setMapMaterial = value.entrySet();
			Iterator<Entry<String, Long>> iteratorMapMaterial = setMapMaterial.iterator();
			int columnIndex = column + 1;
			List<String> tiposRellenos = new ArrayList<String>();
			while(iteratorMapMaterial.hasNext()) {
				Map.Entry<String, Long> entryMaterial = (Map.Entry<String, Long>) iteratorMapMaterial.next();
				String material = entryMaterial.getKey();
				Long   unidades = entryMaterial.getValue();

				tiposRellenos.add(material);
				String strUnidades = formatearUnidades(unidades);
				
				if (!TipoDocumentoImprimirEnum.PERMISO_CIRCULACION.getValorEnum().equals(material)) {
					int celda = obtenerCelda(material, tiposDist);
					
					HSSFCell cellMaterial = rowJP.createCell(columnIndex + celda);
					cellMaterial.setCellValue(strUnidades);
					cellMaterial.setCellStyle(styleWhiteBox(wb));
					sheet.autoSizeColumn(columnIndex + celda);
				}
				
			}
			
			//Prueba poner 0 en los stock que no tengan valor
			for (String tipoDistintivo: tiposDist) {
				if (!tiposRellenos.contains(tipoDistintivo)) {
					int celda = obtenerCelda(tipoDistintivo, tiposDist);
					
					HSSFCell cellMaterial = rowJP.createCell(columnIndex + celda);
					cellMaterial.setCellValue(0L);
					cellMaterial.setCellStyle(styleWhiteBox(wb));
					sheet.autoSizeColumn(columnIndex + celda);
					
				}
			}

		}
	}

	private void insertarBodyTableStockPermisos(HSSFWorkbook wb, HSSFSheet sheet, int column, int row,
			String[] tiposPerm, FacturacionSemanalBean facturacionSemanalBean) {
		
		HashMap<String, HashMap<String, Long>> lineasFacturacionSemanalStock =
				facturacionSemanalBean.getLineasFacturacionSemanalStock();
		Set<Entry<String, HashMap<String, Long>>> setMap = lineasFacturacionSemanalStock.entrySet();
		
		Iterator<Entry<String, HashMap<String, Long>>> iteratorMap = setMap.iterator();
		int rowIndex = row + 2;
		while(iteratorMap.hasNext()) {
			Map.Entry<String, HashMap<String, Long>> entry =
                    (Map.Entry<String, HashMap<String, Long>>) iteratorMap.next();
			
			HSSFRow rowJP = sheet.getRow(rowIndex);
			if (rowJP == null) {
				rowJP = sheet.createRow(rowIndex);
			}
			
			rowIndex++;
			
			String jefatura = entry.getKey();
			HashMap<String, Long> value = entry.getValue();

			HSSFCell cell = rowJP.createCell(column);
			cell.setCellValue(jefatura);
			cell.setCellStyle(styleWhiteBoldBox(wb));
			sheet.autoSizeColumn(column);

			Set<Entry<String, Long>> setMapMaterial = value.entrySet();
			Iterator<Entry<String, Long>> iteratorMapMaterial = setMapMaterial.iterator();
			int columnIndex = column + 1;
			while(iteratorMapMaterial.hasNext()) {
				Map.Entry<String, Long> entryMaterial = (Map.Entry<String, Long>) iteratorMapMaterial.next();
				String material = entryMaterial.getKey();
				Long   unidades = entryMaterial.getValue();
				
				String strUnidades = formatearUnidades(unidades);

				if (TipoDocumentoImprimirEnum.PERMISO_CIRCULACION.getValorEnum().equals(material)) {
					int celda = obtenerCelda(material, tiposPerm);
					
					HSSFCell cellMaterial = rowJP.createCell(columnIndex + celda);
					cellMaterial.setCellValue(strUnidades);
					cellMaterial.setCellStyle(styleWhiteBox(wb));
					sheet.autoSizeColumn(columnIndex + celda);
				}

			}
		}
	}

	private String[] createArrayDistintivos(String[] tipos, boolean stock) {
		
		String[] workTipo = null;
		if (stock) {
			workTipo = new String[tipos.length - 1];
			
			int indexWorkTipo = 0;
			for (int indexTipos = 0; indexTipos < tipos.length; indexTipos++) {
				if ( !TipoDocumentoImprimirEnum.PERMISO_CIRCULACION.getValorEnum().equals(tipos[indexTipos]) ) {
					workTipo[indexWorkTipo++] = tipos[indexTipos];
				}
			}
			
		} else {
			workTipo = new String[tipos.length];
			
			int indexWorkTipo = 0;
			for (int indexTipos = 0; indexTipos < tipos.length; indexTipos++) {
				if ( !TipoDocumentoImprimirEnum.PERMISO_CIRCULACION.getValorEnum().equals(tipos[indexTipos]) ) {
					workTipo[indexWorkTipo++] = tipos[indexTipos];
				}
			}
			
			workTipo[indexWorkTipo] = ServicioFacturacionSemanal.TIPO_INCIDENCIA_DISTINTIVO;
			
		}

		return workTipo;
	}

	private int obtenerCelda(String material, String[] tipos) {
		int index = 0;
		for( ; index < tipos.length; index++) {
			if (tipos[index].equals(material)) {
				return index;
			}
		}
		
		return -1;
	}

	private void insertarCabeceraTablaStockDistintivos(HSSFWorkbook wb, HSSFSheet sheet, int column, int row, String[] tipos) {

		// poner los títulos
		HSSFRow rowTipos = sheet.getRow(row);
		if (rowTipos == null) {
			rowTipos = sheet.createRow(row);
		}
		
		
		CellRangeAddress regionToMerge = new CellRangeAddress(row, row, column + 1, column + tipos.length);

		HSSFCell cellTipo = rowTipos.createCell(column + 1);
		cellTipo.setCellValue("TIPO");
		cellTipo.setCellStyle(styleCreamBoldBox(wb));
		sheet.addMergedRegion(regionToMerge);
		
		for( int j = regionToMerge.getFirstColumn(); j <= regionToMerge.getLastColumn(); j++ ) {
			 Cell cellItem = rowTipos.getCell(j);
			 if (cellItem == null) {
				 cellItem = rowTipos.createCell(j);
			 }
			 cellItem.setCellStyle(styleCreamBoldBox(wb));
		}
		
		HSSFRow rowJP = sheet.getRow(row + 1);
		if (rowJP == null) {
			rowJP = sheet.createRow(row + 1);
		}
		
		HSSFCell cellJPTit = rowJP.createCell(column);
		cellJPTit.setCellValue("JEFATURA");
		cellJPTit.setCellStyle(styleCreamBoldBox(wb));
		sheet.autoSizeColumn(column);
		
		for(int index = 0; index < tipos.length; index++) {
			HSSFCell cellTipoTitulo = rowJP.createCell(column + 1 + index);
			cellTipoTitulo.setCellValue(tipos[index]);
			cellTipoTitulo.setCellStyle(styleWhiteBoldBox(wb));
		}
	}

	private void insertarCabeceraTablaImpresionDistintivos(HSSFWorkbook wb, HSSFSheet sheet, int column, int row, String[] tipos) {

		HSSFRow rowTipos = sheet.getRow(row);
		if (rowTipos == null) {
			rowTipos = sheet.createRow(row);
		}
		
		CellRangeAddress regionToMerge = new CellRangeAddress(row, row, column + 1, column + tipos.length);

		HSSFCell cellTipo = rowTipos.createCell(column + 1);
		cellTipo.setCellValue("TIPO");
		cellTipo.setCellStyle(styleCreamBoldBox(wb));
		sheet.addMergedRegion(regionToMerge);
		
		for( int j = regionToMerge.getFirstColumn(); j <= regionToMerge.getLastColumn(); j++ ) {
			 Cell cellItem = rowTipos.getCell(j);
			 if (cellItem == null) {
				 cellItem = rowTipos.createCell(j);
			 }
			 cellItem.setCellStyle(styleCreamBoldBox(wb));
		}
		
		
		HSSFRow rowJP = sheet.getRow(row + 1);
		if (rowJP == null) {
			rowJP = sheet.createRow(row + 1);
		}
		
		HSSFCell cellJPTit = rowJP.createCell(column);
		cellJPTit.setCellValue("FECHA");
		cellJPTit.setCellStyle(styleCreamBoldBox(wb));
		sheet.autoSizeColumn(column);
		
		int index = 0;
		for(; index < tipos.length; index++) {
			HSSFCell cellTipoTitulo = rowJP.createCell(column + 1 + index);
			sheet.autoSizeColumn(column + 1 + index);
			if (tipos[index].startsWith("IN")) {
				cellTipoTitulo.setCellValue("INCD");
			} else {
				cellTipoTitulo.setCellValue(tipos[index]);
			}
			
			cellTipoTitulo.setCellStyle(styleWhiteBoldBox(wb));
		}
		
		HSSFCell cellTotalLiteral = rowJP.createCell(column + index + 1);
		cellTotalLiteral.setCellValue("TOTAL DÍA");
		cellTotalLiteral.setCellStyle(styleWhiteBoldBox(wb));
		sheet.autoSizeColumn(column + index + 1);

	}

	private void insertarCabeceraTablaStockPermisos(HSSFWorkbook wb, HSSFSheet sheet, int column, int row, String[] tipos) {

		// poner los títulos
		HSSFRow rowTipos = sheet.getRow(row);
		if (rowTipos == null) {
			rowTipos = sheet.createRow(row);
		}
		
		CellRangeAddress regionToMerge = new CellRangeAddress(row, row, column + 1, column + tipos.length);

		HSSFCell cellTipo = rowTipos.createCell(column + 1);
		cellTipo.setCellValue("TIPO");
		cellTipo.setCellStyle(styleCreamBoldBox(wb));
		sheet.addMergedRegion(regionToMerge);
		
		for( int j = regionToMerge.getFirstColumn(); j <= regionToMerge.getLastColumn(); j++ ) {
			 Cell cellItem = rowTipos.getCell(j);
			 if (cellItem == null) {
				 cellItem = rowTipos.createCell(j);
			 }
			 cellItem.setCellStyle(styleCreamBoldBox(wb));
		}
		
		HSSFRow rowJP = sheet.getRow(row + 1);
		if (rowJP == null) {
			rowJP = sheet.createRow(row + 1);
		}
		
		HSSFCell cellJPTit = rowJP.createCell(column);
		cellJPTit.setCellValue("JEFATURA");
		cellJPTit.setCellStyle(styleCreamBoldBox(wb));
		sheet.autoSizeColumn(column);
		
		int index = 0;
		for(; index < tipos.length; index++) {
			HSSFCell cellTipoTitulo = rowJP.createCell(column + 1 + index);
			cellTipoTitulo.setCellValue(tipos[index]);
			
			cellTipoTitulo.setCellStyle(styleWhiteBoldBox(wb));
		}
		
		
	}

	private void insertarTitulo(HSSFWorkbook wb, HSSFSheet sheet, String titulo, int column, int row) {
		HSSFCellStyle style = styleWhiteBoldBox(wb);

		HSSFRow rowExcel = sheet.getRow(row);
		if (rowExcel == null) {
			rowExcel = sheet.createRow(row);
		}
		
		HSSFCell cell = null;
		
		CellRangeAddress regionToMerge = new CellRangeAddress(row, row, column, column + 8);
		cell = rowExcel.createCell(column);
		cell.setCellValue(titulo);
		sheet.addMergedRegion(regionToMerge);
		
		for( int j = regionToMerge.getFirstColumn(); j <= regionToMerge.getLastColumn(); j++ ) {
			 Cell cellItem = rowExcel.getCell(j);
			 if (cellItem == null) {
				 cellItem = rowExcel.createCell(j);
			 }
			 cellItem.setCellStyle(style);
		}
	
	}

	private String construirTextoFecha(Date fecInforme) {
		Locale locale = Locale.getDefault();
		Calendar today = Calendar.getInstance();
		today.setTime(fecInforme);
		
		StringBuilder texto = new StringBuilder("FECHA DE ENVÍO: ");
		texto.append(today.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.LONG, locale).toUpperCase());
		texto.append(", ");
		texto.append(today.get(Calendar.DAY_OF_MONTH));
		texto.append(" DE ");
		texto.append(today.getDisplayName(Calendar.MONTH, Calendar.LONG, locale).toUpperCase());
		texto.append(". HORA: ");
		texto.append(today.get(Calendar.HOUR_OF_DAY));
		texto.append(":");
		texto.append(today.get(Calendar.MINUTE));

		return texto.toString();
	}

	private void insertarImage(HSSFWorkbook wb, HSSFSheet sheet, String logo, int column, int row) {
		String urlLogo = "/images/" + logo;

		try {
			BufferedImage icono = ImageIO.read( ServicioGenerarExcelStockSemanalXlsxImpl.class.getResource(urlLogo) );
			byte[] byteArray = toByteArrayAutoClosable(icono, "png");
			
			int pictureIdx = wb.addPicture(byteArray, Workbook.PICTURE_TYPE_PNG);
			CreationHelper helper = wb.getCreationHelper();
			Drawing drawing = sheet.createDrawingPatriarch();
			ClientAnchor anchor = helper.createClientAnchor();
			anchor.setCol1(column);
			anchor.setRow1(row);
			Picture pict = drawing.createPicture(anchor, pictureIdx);
			pict.resize();
			
			
		} catch (FileNotFoundException e1) {
			log.error("Fichero logo no encontrado " + e1.getMessage());
			e1.printStackTrace();
		} catch (IOException e) {
			log.error("Error de entrada/salida " + e.getMessage());
			e.printStackTrace();
		}
		
	}
	
	// Styles cells
	private HSSFCellStyle styleWhiteBoldBox(HSSFWorkbook wb) {
		
		HSSFCellStyle style = wb.createCellStyle();
		style.setFont(createFontNegritaBold(wb));
		style.setBorderBottom(HSSFCellStyle.BORDER_MEDIUM);
		style.setBorderTop(HSSFCellStyle.BORDER_MEDIUM);
		style.setBorderRight(HSSFCellStyle.BORDER_MEDIUM);
		style.setBorderLeft(HSSFCellStyle.BORDER_MEDIUM);
		
		style.setAlignment(CellStyle.ALIGN_CENTER);
		
		return style;
		
	}
	
	private HSSFCellStyle styleWhiteBox(HSSFWorkbook wb) {
		HSSFCellStyle style = wb.createCellStyle();
		style.setFont(createFontNegritaNormal(wb));
		style.setBorderBottom(HSSFCellStyle.BORDER_MEDIUM);
		style.setBorderTop(HSSFCellStyle.BORDER_MEDIUM);
		style.setBorderRight(HSSFCellStyle.BORDER_MEDIUM);
		style.setBorderLeft(HSSFCellStyle.BORDER_MEDIUM);
		
		style.setAlignment(CellStyle. ALIGN_CENTER);
		
		return style;
		
	}

	private HSSFCellStyle styleWhiteBoxWithoutBorder(HSSFWorkbook wb) {
		HSSFCellStyle style = wb.createCellStyle();
		style.setFont(createFontNegritaBold(wb));
		style.setAlignment(CellStyle. ALIGN_CENTER);
		
		return style;
		
	}

	private HSSFCellStyle styleBlueBoxWithoutBorder(HSSFWorkbook wb) {
		HSSFCellStyle style = wb.createCellStyle();
		style.setFont(createFontNegritaBold(wb));
		style.setAlignment(CellStyle. ALIGN_CENTER);
		
		//HSSFColor blue =  setColor(wb, (byte) 51, (byte) 204, (byte) 255);
		HSSFColor cream =  setColor(wb, (byte) 255, (byte) 242, (byte) 204);
		style.setFillForegroundColor(cream.getIndex());
		style.setFillPattern(CellStyle.SOLID_FOREGROUND);

		return style;
		
	}

	private HSSFCellStyle styleCreamBoldBox(HSSFWorkbook wb) {
		
		HSSFCellStyle style = wb.createCellStyle();
		style.setFont(createFontNegritaBold(wb));
		
		style.setBorderBottom(HSSFCellStyle.BORDER_MEDIUM);
		style.setBorderTop(HSSFCellStyle.BORDER_MEDIUM);
		style.setBorderRight(HSSFCellStyle.BORDER_MEDIUM);
		style.setBorderLeft(HSSFCellStyle.BORDER_MEDIUM);
		
		style.setAlignment(CellStyle.ALIGN_CENTER);
		
		HSSFColor cream =  setColor(wb, (byte) 255, (byte) 242, (byte) 204);
		style.setFillForegroundColor(cream.getIndex());
		style.setFillPattern(CellStyle.SOLID_FOREGROUND);
		
		return style;
		
	}
	
	private HSSFFont createFontNegritaBold(HSSFWorkbook wb) {
		return fonts.get(FONT_BLACK_BOLD);
	}
	
	private HSSFFont createFontNegritaNormal(HSSFWorkbook wb) {
		return fonts.get(FONT_BLACK_NORMAL);
	}
	
	private void initFonts(HSSFWorkbook wb) {
		HSSFFont blackBold = wb.createFont();
		blackBold.setFontName(FONT_BLACK_BOLD);
		blackBold.setColor(HSSFColor.BLACK.index);
		blackBold.setFontHeightInPoints((short) 12);
		blackBold.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		fonts.put(FONT_BLACK_BOLD, blackBold);
		
		HSSFFont blackNormal = wb.createFont();
		blackNormal.setFontName(FONT_BLACK_NORMAL);
		blackNormal.setColor(HSSFColor.BLACK.index);
		blackNormal.setFontHeightInPoints((short) 12);
		blackNormal.setBoldweight(HSSFFont.COLOR_NORMAL);
		fonts.put(FONT_BLACK_NORMAL, blackNormal);
		
	}
	
	private HSSFColor setColor(HSSFWorkbook workbook, byte r,byte g, byte b){
	    HSSFPalette palette = workbook.getCustomPalette();
	    HSSFColor hssfColor = null;
	    try {
	        hssfColor= palette.findColor(r, g, b); 
	        if (hssfColor == null ){
	            palette.setColorAtIndex(HSSFColor.LAVENDER.index, r, g,b);
	            hssfColor = palette.getColor(HSSFColor.LAVENDER.index);
	        }
	    } catch (Exception e) {
	        log.error(e);
	    }

	    return hssfColor;
	}	

    private static byte[] toByteArrayAutoClosable(BufferedImage image, String type) throws IOException {
        try (ByteArrayOutputStream out = new ByteArrayOutputStream()){
            ImageIO.write(image, type, out);
            return out.toByteArray();
        }
    }

}
