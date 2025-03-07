package org.gestoresmadrid.oegam2comun.estadisticas.impresion.service.impl;

import java.awt.Color;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import org.apache.commons.io.IOUtils;
import org.apache.poi.hssf.usermodel.HSSFShape;
import org.apache.poi.hssf.usermodel.HSSFSimpleShape;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.ClientAnchor;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.Drawing;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.TextHorizontalOverflow;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFClientAnchor;
import org.apache.poi.xssf.usermodel.XSSFColor;
import org.apache.poi.xssf.usermodel.XSSFDrawing;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFSimpleShape;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.gestoresmadrid.core.estadisticas.impresion.model.beans.ResultadoEstadisticaImpresion;
import org.gestoresmadrid.core.estadisticas.impresion.model.dao.EstadisticaImpresionDao;
import org.gestoresmadrid.core.trafico.model.dao.TramiteTraficoMatrDao;
import org.gestoresmadrid.oegam2comun.estadisticas.impresion.service.ServicioEstadisticaImpresion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import escrituras.beans.ResultBean;


@Service
public class ServicioEstadisticaImpresionImpl implements ServicioEstadisticaImpresion{
	
	@Autowired
	TramiteTraficoMatrDao tramiteTraficoMatrDao;
	
	@Autowired
	EstadisticaImpresionDao estadisticaDao;

	@Override
	@Transactional
	public ResultBean generarExcel(Date fechaInicio, Date fechaFin, String jefatura, String tipoDocumento) {
		ResultBean salida = new ResultBean(Boolean.FALSE);
		List<ResultadoEstadisticaImpresion> lista = new ArrayList<ResultadoEstadisticaImpresion>();
		try{
			//salida = crearExcel(lista);
			//lista = estadisticaDao.getStockDistintivosPorJefatura(fechaInicio, fechaFin, jefatura);
			//lista = estadisticaDao.getNumDistintivosPorJefatura(fechaInicio, fechaFin, jefatura);
			salida = crearExcel(lista);
		}catch(Exception e){
 			salida.setError(Boolean.TRUE);
			salida.setMensaje("Ha sucedido un error a la hora de generar la estadistica de impresión");			
		}
		
		return salida;
	}
	
	
	private ResultBean crearExcel(List<ResultadoEstadisticaImpresion> lista){
		ResultBean salida = new ResultBean(Boolean.FALSE);
		
		int fila = 0;
		int col = 0;
		XSSFWorkbook myWorkBook = new XSSFWorkbook();
		XSSFSheet sheet = myWorkBook.createSheet("INFORME" );
		XSSFCellStyle estiloNegrita  = myWorkBook.createCellStyle();
		XSSFFont negrita = myWorkBook.createFont();
		negrita.setBold(true);
		negrita.setFontHeight(9);
		estiloNegrita.setFont(negrita);
		sheet.autoSizeColumn(col);	
		
		introducirCabecera(myWorkBook,sheet);
		try{
			
			
			Cell cell = sheet.createRow(7).createCell(1);
			cell.setCellStyle(estiloNegrita);
			cell.setCellValue("DISTINTIVOS - STOCK -");
			
			Cell cell3 = sheet.createRow(7).createCell(5);
			cell.setCellStyle(estiloNegrita);
			cell3.setCellValue("PERMISOS -STOCK-");
			
//			Cell cell2 = sheet.createRow(9).createCell(3);
//			cell2.setCellValue("JEFATURA");
//			XSSFCellStyle estiloCaja  = myWorkBook.createCellStyle();
//			estiloCaja.setFillBackgroundColor(new XSSFColor(new Color(255,242,204)));
//			cell2.setCellStyle(estiloCaja);
			
			


			
			
			salida.addAttachment("excel", myWorkBook);
			
			
		}catch(Exception e){
			salida.setError(Boolean.TRUE);
			salida.setMensaje("Ha sucedido un error a la hora de generar la estadistica de impresión");
		}
		return salida;
	}


	private void introducirCabecera(XSSFWorkbook myWorkBook,XSSFSheet sheet){
		final CreationHelper helper = myWorkBook.getCreationHelper();
		final Drawing drawing = sheet.createDrawingPatriarch();
		final ClientAnchor anchor = helper.createClientAnchor();
		anchor.setAnchorType( ClientAnchor.MOVE_AND_RESIZE );
		anchor.setCol1(1); //Column B
	    anchor.setRow1(2); //Row 3
	    anchor.setCol2(2); //Column C
	    anchor.setRow2(3); //Row 4
		try {
		   final FileInputStream stream =  new FileInputStream("C:\\workspace\\oegam2\\WebContent\\img\\pngFavIcon2.png");
		   final int pictureIndex =       myWorkBook.addPicture(IOUtils.toByteArray(stream), Workbook.PICTURE_TYPE_PNG);
		   final org.apache.poi.ss.usermodel.Picture pict = drawing.createPicture( anchor, pictureIndex );
		   Cell cell = sheet.createRow(2).createCell(1);
		   int widthUnits = 20*256;
		   sheet.setColumnWidth(1, widthUnits);

		   //set height to n points in twips = n * 20
		   short heightUnits = 60*20;
		   cell.getRow().setHeight(heightUnits);
		   
		   
		   XSSFDrawing patriarch = sheet.createDrawingPatriarch();

		   /* Here is the thing: the line will go from top left in cell (0,0) to down left 
		   of cell (0,1) */
		   XSSFClientAnchor anchor2 = new XSSFClientAnchor(0, 0, 0, 255, (short) 0, 0,(short) 1, 0);

		   XSSFSimpleShape shape = patriarch.createSimpleShape(anchor2);
		   shape.setShapeType(HSSFSimpleShape.OBJECT_TYPE_RECTANGLE);
		   shape.setLineStyleColor(141, 28, 40);//granate ICOGAM
		   shape.setFillColor(141, 28, 40);
		   shape.setLineWidth(50);
		   shape.setText("ICOGAM Informe Estadístico y de stock para DGT");
		   shape.setLineStyle(HSSFShape.LINESTYLE_SOLID);
		   
		   Cell celdaTitulo = sheet.createRow(5).createCell(1);
		   Date fecha = new Date();
		   DateFormat format = new SimpleDateFormat("EEEE d 'de' MMMM 'de' yyyy",new Locale("es", "ES"));
		   XSSFCellStyle estiloNegrita  = myWorkBook.createCellStyle();
		   XSSFFont negrita = myWorkBook.createFont();
		   negrita.setBold(true);
		   negrita.setFontHeight(15);
		   estiloNegrita.setFont(negrita);
		   celdaTitulo.setCellStyle(estiloNegrita);
		   celdaTitulo.setCellValue("FECHA DE ENVÍO: " + format.format(fecha));
		   
		   
		   
		   
		   
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

}
