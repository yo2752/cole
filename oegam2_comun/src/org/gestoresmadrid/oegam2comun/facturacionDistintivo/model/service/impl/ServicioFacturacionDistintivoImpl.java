package org.gestoresmadrid.oegam2comun.facturacionDistintivo.model.service.impl;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.gestoresmadrid.core.docPermDistItv.model.dao.VehNoMatOegamDao;
import org.gestoresmadrid.core.docPermDistItv.model.enumerados.EstadoPermisoDistintivoItv;
import org.gestoresmadrid.core.docPermDistItv.model.enumerados.TipoDistintivo;
import org.gestoresmadrid.core.docPermDistItv.model.vo.DocPermDistItvVO;
import org.gestoresmadrid.core.docPermDistItv.model.vo.VehNoMatOegamVO;
import org.gestoresmadrid.core.facturacionDistintivo.model.dao.FacturacionDistintivoDao;
import org.gestoresmadrid.core.facturacionDistintivo.model.dao.FacturacionDstvIncDao;
import org.gestoresmadrid.core.facturacionDistintivo.model.enumerados.EstadoFacturacionDstv;
import org.gestoresmadrid.core.facturacionDistintivo.model.vo.FacturacionDistintivoVO;
import org.gestoresmadrid.core.facturacionDistintivo.model.vo.FacturacionDstvIncidenciaVO;
import org.gestoresmadrid.core.facturacionDistintivo.model.vo.ResultFacturacionDstv;
import org.gestoresmadrid.core.trafico.model.dao.TramiteTraficoMatrDao;
import org.gestoresmadrid.oegam2comun.facturacionDistintivo.model.service.ServicioFacturacionDistintivo;
import org.gestoresmadrid.oegam2comun.facturacionDistintivo.model.view.bean.FacturacionDstvBean;
import org.gestoresmadrid.oegam2comun.facturacionDistintivo.model.view.bean.FacturacionDstvFilterBean;
import org.gestoresmadrid.oegam2comun.facturacionDistintivo.model.view.bean.ResultadoFactDstvBean;
import org.gestoresmadrid.oegam2comun.facturacionDistintivo.model.view.dto.FacturacionDstvIncDto;
import org.gestoresmadrid.oegam2comun.permisoDistintivoItv.view.bean.ResultadoPermisoDistintivoItvBean;
import org.gestoresmadrid.oegamComun.conversor.Conversor;
import org.gestoresmadrid.utilidades.components.UtilesFecha;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

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
public class ServicioFacturacionDistintivoImpl implements ServicioFacturacionDistintivo {

	private static final long serialVersionUID = 5337340574491946406L;

	private static final ILoggerOegam log = LoggerOegam.getLogger(ServicioFacturacionDistintivoImpl.class);

	private static final String ERROR_AL_IMPRIMIR_TRAMITES_DE_BAJA = "Error al imprimir tramites de baja ";
	private static final String TOTAL = "Total";
	private static final String MS_SANS_SERIF = "MS Sans Serif";

	@Autowired
	private Conversor conversor;

	@Autowired
	FacturacionDistintivoDao facturacionDistintivoDao;

	@Autowired
	FacturacionDstvIncDao facturacionDstvIncDao;

	@Autowired
	TramiteTraficoMatrDao tramiteTraficoMatrDao;

	@Autowired
	VehNoMatOegamDao vehNoMatOegamDao;

	@Autowired
	GestorDocumentos gestorDocumentos;

	@Autowired
	UtilesFecha utilesFecha;

	@Override
	@Transactional
	public ResultadoFactDstvBean descargarExcel(String nombreFichero, Date fechaGen) {
		ResultadoFactDstvBean resultado = new ResultadoFactDstvBean(Boolean.FALSE);
		try {
			if (nombreFichero != null && !nombreFichero.isEmpty() && fechaGen != null) {
				FileResultBean fichero = gestorDocumentos.buscarFicheroPorNombreTipo(ConstantesGestorFicheros.EXCELS, ConstantesGestorFicheros.FACTURACION_DOC_DGT,
						utilesFecha.getFechaConDate(fechaGen),nombreFichero , ConstantesGestorFicheros.EXTENSION_XLS);
				if (fichero != null && fichero.getFile() != null) {
					resultado.setFichero(fichero.getFile());
				} else {
					resultado.setError(Boolean.TRUE);
					resultado.setMensaje("No se ha encontrado ningún excel para poder realizar su descarga.");
				}
			} else {
				resultado.setError(Boolean.TRUE);
				resultado.setMensaje("No se puede descargar el excel generado porque la fecha o el nombre de generación estan vacíos.");
			}
		} catch (Throwable e) {
			log.error("Ha sucedido un error a la hora de descargar el excel con la facturacion, error: ",e);
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("Ha sucedido un error a la hora de descargar el excel con la facturacion.");
		}
		return resultado;
	}

	@Override
	@Transactional
	public ResultadoFactDstvBean generarExcel(FacturacionDstvFilterBean facturacionDstv) {
		ResultadoFactDstvBean resultado = new ResultadoFactDstvBean(Boolean.FALSE);
		try {
			resultado = validacionesGenerarExcel(facturacionDstv);
			if(!resultado.getError()){
				List<ResultFacturacionDstv> listaFacturacion = facturacionDistintivoDao.getListaFacturacionExcel(facturacionDstv.getFecha(), facturacionDstv.getIdContrato(), 
						facturacionDstv.getDocDistintivo(), facturacionDstv.getTipoDistintivo(),EstadoPermisoDistintivoItv.Impresion_OK.getValorEnum());
				if(listaFacturacion != null && !listaFacturacion.isEmpty()){
					resultado = generarExcelFacturacion(listaFacturacion, facturacionDstv.getTipoDistintivo());
					if(!resultado.getError()){
						resultado.setNombreFichero(resultado.getNombreFichero());
						resultado.setEsDescargable(Boolean.TRUE);
						resultado.setFechaGenExcel(new Date());
					}
				} else {
					resultado.setError(Boolean.TRUE);
					resultado.setMensaje("No se han encontrado datos para poder generar el excel.");
				}
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de generar el excel con la facturacion, error: ", e);
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("Ha sucedido un error a la hora de generar el excel con la facturacion.");
		}
		return resultado;
	}

	@Override
	@Transactional
	public ResultadoFactDstvBean generarExcelDetallado(FacturacionDstvFilterBean facturacionDstv) {
		ResultadoFactDstvBean resultado = new ResultadoFactDstvBean(Boolean.FALSE);
		try {
			resultado = validacionesGenerarExcelDetallado(facturacionDstv);
			if(!resultado.getError()){
				List<FacturacionDistintivoVO> listaFacturacion = facturacionDistintivoDao.getListaFacturacionExcelDetallado(facturacionDstv.getFecha(), facturacionDstv.getIdContrato(), 
						facturacionDstv.getDocDistintivo(), facturacionDstv.getTipoDistintivo());
				if(listaFacturacion != null && !listaFacturacion.isEmpty()){
					resultado = generarExcelFacturacionDetallado(listaFacturacion, facturacionDstv.getTipoDistintivo());
					if(!resultado.getError()){
						resultado.setNombreFichero(resultado.getNombreFichero());
						resultado.setEsDescargable(Boolean.TRUE);
						resultado.setFechaGenExcel(new Date());
					}
				} else {
					resultado.setError(Boolean.TRUE);
					resultado.setMensaje("No se han encontrado datos para poder generar el excel.");
				}
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de generar el excel con la facturacion, error: ", e);
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("Ha sucedido un error a la hora de generar el excel con la facturacion.");
		}
		return resultado;
	}

	private ResultadoFactDstvBean generarExcelFacturacion(List<ResultFacturacionDstv> listaFacturacion, String tipoDistintivo) {
		ResultadoFactDstvBean resultado = new ResultadoFactDstvBean(Boolean.FALSE);
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
			copyWorkbook = Workbook.createWorkbook(archivo);
			sheet = copyWorkbook.createSheet("Resumen_Facturacion", 0);
			CellView vistaCeldas = new CellView();
			vistaCeldas.setAutosize(true);
			int numColumnas = 0;
			if (tipoDistintivo != null && !tipoDistintivo.isEmpty()) {
				numColumnas = 7;
			} else {
				numColumnas = 6;
			}
			for (int i = 0; i <= numColumnas; i++) {
				sheet.setColumnView(i, vistaCeldas);
			}
			// Generamos las cabeceras de la hoja Excel con el formato indicado
			// Formato de las celdas de cabecera
			WritableFont fuenteCabecera = new WritableFont(WritableFont.createFont(MS_SANS_SERIF), 10, WritableFont.BOLD, false);
			fuenteCabecera.setColour(Colour.WHITE);
			fuenteCabecera.setBoldStyle(WritableFont.BOLD);
			WritableCellFormat formatoCabecera = new WritableCellFormat(fuenteCabecera);

			formatoCabecera.setBackground(Colour.LAVENDER);
			formatoCabecera.setAlignment(Alignment.LEFT);
			formatoCabecera.setBorder(Border.ALL, BorderLineStyle.THIN);

			// Formato de las celdas de datos
			WritableFont fuenteDatos = new WritableFont(WritableFont.createFont(MS_SANS_SERIF), 10, WritableFont.NO_BOLD, false);
			fuenteDatos.setColour(Colour.BLACK);
			WritableCellFormat formatoDatos = new WritableCellFormat(fuenteDatos);

			formatoDatos.setAlignment(Alignment.LEFT);
			sheet.addCell(new Label(0, 0, "Colegiado", formatoCabecera));
			sheet.addCell(new Label(1, 0, "Via", formatoCabecera));
			sheet.addCell(new Label(2, 0, "Fecha", formatoCabecera));
			if(tipoDistintivo != null && !tipoDistintivo.isEmpty()){
				sheet.addCell(new Label(3, 0, "Tipo", formatoCabecera));
				sheet.addCell(new Label(4, 0, "Distintivos", formatoCabecera));
				sheet.addCell(new Label(5, 0, "Duplicados", formatoCabecera));
				sheet.addCell(new Label(6, 0, TOTAL, formatoCabecera));
			} else {
				sheet.addCell(new Label(3, 0, "Distintivos", formatoCabecera));
				sheet.addCell(new Label(4, 0, "Duplicados", formatoCabecera));
				sheet.addCell(new Label(5, 0, TOTAL, formatoCabecera));
			}

			int conFila = 1;
			int cont = 0;
			for(ResultFacturacionDstv facturacionDstv : listaFacturacion){
				cont = 0;
				sheet.addCell(new Label(cont++, conFila, facturacionDstv.getNumColegiado(), formatoDatos));
				sheet.addCell(new Label(cont++, conFila, facturacionDstv.getVia(), formatoDatos));
				sheet.addCell(new Label(cont++, conFila, facturacionDstv.getFecha(),formatoDatos));
				if(tipoDistintivo != null && !tipoDistintivo.isEmpty()){
					sheet.addCell(new Label(cont++, conFila, TipoDistintivo.convertirValor(facturacionDstv.getTipo()),formatoDatos));
				}
				sheet.addCell(new Label(cont++, conFila, facturacionDstv.getCantidadDstv() == null ? "0" : facturacionDstv.getCantidadDstv().toString(),formatoDatos));
				sheet.addCell(new Label(cont++, conFila, facturacionDstv.getCantidadDup() == null ? "0" : facturacionDstv.getCantidadDup().toString(),formatoDatos));
				Long total = (facturacionDstv.getCantidadDstv() == null ? 0 : facturacionDstv.getCantidadDstv()) + 
						(facturacionDstv.getCantidadDup() == null ? 0 : facturacionDstv.getCantidadDup());
				sheet.addCell(new Label(cont++, conFila, total.toString(), formatoDatos));
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
				} catch (WriteException | IOException e) {
					log.error(ERROR_AL_IMPRIMIR_TRAMITES_DE_BAJA, e);
				}
			}
		}
		return resultado;
	}

	private ResultadoFactDstvBean generarExcelFacturacionDetallado(List<FacturacionDistintivoVO> listaFacturacion, String tipoDistintivo) {
		ResultadoFactDstvBean resultado = new ResultadoFactDstvBean(Boolean.FALSE);
		WritableWorkbook copyWorkbook = null;
		WritableSheet sheet;
		try {
			File archivo = null;
			resultado.setNombreFichero("Resumen_Facturacion_Detallado_" + new SimpleDateFormat("ddMMYYHHmmss").format(new Date()));
			FicheroBean fichero = new FicheroBean();
			fichero.setExtension(ConstantesGestorFicheros.EXTENSION_XLS);
			fichero.setNombreDocumento(resultado.getNombreFichero());
			fichero.setTipoDocumento(ConstantesGestorFicheros.EXCELS);
			fichero.setSubTipo(ConstantesGestorFicheros.FACTURACION_DOC_DGT);
			fichero.setFecha(utilesFecha.getFechaActual());
			fichero.setSobreescribir(true);
			fichero.setFichero(new File(resultado.getNombreFichero()));
			archivo = gestorDocumentos.guardarFichero(fichero);
			copyWorkbook = Workbook.createWorkbook(archivo);
			sheet = copyWorkbook.createSheet("Resumen_Facturacion_Detallado", 0);
			CellView vistaCeldas = new CellView();
			vistaCeldas.setAutosize(true);
			int numColumnas = 0;
			if(tipoDistintivo != null && !tipoDistintivo.isEmpty()){
				numColumnas = 6;
			} else {
				numColumnas = 5;
			}
			for (int i = 0; i <= numColumnas; i++) {
				sheet.setColumnView(i, vistaCeldas);
			}
			// Generamos las cabeceras de la hoja Excel con el formato indicado
			// Formato de las celdas de cabecera
			WritableFont fuenteCabecera = new WritableFont(WritableFont.createFont(MS_SANS_SERIF), 10, WritableFont.BOLD, false);
			fuenteCabecera.setColour(Colour.WHITE);
			fuenteCabecera.setBoldStyle(WritableFont.BOLD);
			WritableCellFormat formatoCabecera = new WritableCellFormat(fuenteCabecera);

			formatoCabecera.setBackground(Colour.LAVENDER);
			formatoCabecera.setAlignment(Alignment.LEFT);
			formatoCabecera.setBorder(Border.ALL, BorderLineStyle.THIN);

			// Formato de las celdas de datos
			WritableFont fuenteDatos = new WritableFont(WritableFont.createFont(MS_SANS_SERIF), 10, WritableFont.NO_BOLD, false);
			fuenteDatos.setColour(Colour.BLACK);
			WritableCellFormat formatoDatos = new WritableCellFormat(fuenteDatos);
			
			// Formato de las celdas de datos
			WritableFont fuenteDatosMtr = new WritableFont(WritableFont.createFont(MS_SANS_SERIF), 10, WritableFont.BOLD, false);
			fuenteDatosMtr.setColour(Colour.BLACK);
			fuenteDatosMtr.setBoldStyle(WritableFont.BOLD);
			WritableCellFormat formatoDatosMtr = new WritableCellFormat(fuenteDatosMtr);

			formatoDatos.setAlignment(Alignment.LEFT);
			sheet.addCell(new Label(0, 0, "Colegiado", formatoCabecera));
			sheet.addCell(new Label(1, 0, "Via", formatoCabecera));
			sheet.addCell(new Label(2, 0, "Fecha", formatoCabecera));
			if(tipoDistintivo != null && !tipoDistintivo.isEmpty()){
				sheet.addCell(new Label(3, 0, TOTAL, formatoCabecera));
				sheet.addCell(new Label(4, 0, "Matrícula", formatoCabecera));
				sheet.addCell(new Label(5, 0, "1º Impresión o Duplicado", formatoCabecera));
			} else {
				sheet.addCell(new Label(3, 0, "Matrícula", formatoCabecera));
				sheet.addCell(new Label(4, 0, "1º Impresión o Duplicado", formatoCabecera));
			}

			int conFila = 1;
			int cont = 0;
			for(FacturacionDistintivoVO facturacionDstv : listaFacturacion){
				cont = 0;
				String numColegiado = "";
				String via = "";
				if (facturacionDstv.getContrato() != null && facturacionDstv.getContrato().getColegiado() != null) {
					numColegiado = facturacionDstv.getContrato().getColegiado().getNumColegiado();
					via = facturacionDstv.getContrato().getVia();
				}
				sheet.addCell(new Label(cont++, conFila, numColegiado, formatoDatos));
				sheet.addCell(new Label(cont++, conFila, via, formatoDatos));

				sheet.addCell(new Label(cont++, conFila, utilesFecha.formatoFecha(facturacionDstv.getFecha()),formatoDatos));
				if(tipoDistintivo != null && !tipoDistintivo.isEmpty()){
					sheet.addCell(new Label(cont++, conFila, TipoDistintivo.convertirValor(facturacionDstv.getTipoDistintivo()),formatoDatos));
				}

				if (facturacionDstv.getIdDocPermDistItv() != null) {
					boolean primeraFila = true;

					// Buscar en Tramite_Trafico Primera Impresión
					List<String> listaMatriculasPI = tramiteTraficoMatrDao.listaMatriculasPorDocDisintintivo(facturacionDstv.getIdDocPermDistItv());
					// Buscar en VehNoMatOegam Duplicados
					List<String> listaMatriculasD = vehNoMatOegamDao.listaMatriculasPorDocDisintintivo(facturacionDstv.getIdDocPermDistItv());

					if (listaMatriculasPI != null && !listaMatriculasPI.isEmpty()) {
						for (String matricula : listaMatriculasPI) {
							if (!primeraFila) {
								cont = 0;
								sheet.addCell(new Label(cont++, conFila, numColegiado, formatoDatos));
								sheet.addCell(new Label(cont++, conFila, via, formatoDatos));
								sheet.addCell(new Label(cont++, conFila, utilesFecha.formatoFecha(facturacionDstv.getFecha()), formatoDatos));
								if(tipoDistintivo != null && !tipoDistintivo.isEmpty()){
									sheet.addCell(new Label(cont++, conFila, TipoDistintivo.convertirValor(facturacionDstv.getTipoDistintivo()), formatoDatos));
								}
							} else {
								primeraFila = false;
							}
							sheet.addCell(new Label(cont++, conFila, matricula, formatoDatosMtr));
							sheet.addCell(new Label(cont++, conFila, "Primera impresión", formatoDatosMtr));
							conFila++;
						}
					}

					if (listaMatriculasD != null && !listaMatriculasD.isEmpty()) {
						for (String matricula : listaMatriculasD) {
							if (!primeraFila) {
								cont = 0;
								sheet.addCell(new Label(cont++, conFila, numColegiado, formatoDatos));
								sheet.addCell(new Label(cont++, conFila, via, formatoDatos));
								sheet.addCell(new Label(cont++, conFila, utilesFecha.formatoFecha(facturacionDstv.getFecha()), formatoDatos));
								if(tipoDistintivo != null && !tipoDistintivo.isEmpty()){
									sheet.addCell(new Label(cont++, conFila, TipoDistintivo.convertirValor(facturacionDstv.getTipoDistintivo()), formatoDatos));
								}
								primeraFila = false;
							} else {
								primeraFila = false;
							}
							sheet.addCell(new Label(cont++, conFila, matricula, formatoDatosMtr));
							sheet.addCell(new Label(cont++, conFila, "Duplicado", formatoDatosMtr));
							conFila++;
						}
					}
					if (primeraFila) {
						conFila++;
					}
				}
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
				} catch (WriteException | IOException e) {
					log.error(ERROR_AL_IMPRIMIR_TRAMITES_DE_BAJA, e);
				}
			}
		}
		return resultado;
	}

	private ResultadoFactDstvBean validacionesGenerarExcel(FacturacionDstvFilterBean facturacionDstv) {
		ResultadoFactDstvBean resultado = new ResultadoFactDstvBean(Boolean.FALSE);
		if(facturacionDstv == null){
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("Debe de seleccionar algún filtro para poder generar el excel.");
		} else if(facturacionDstv.getFecha() == null || facturacionDstv.getFecha().getFechaInicio() == null){
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("Debe de indicar una fecha inicial para poder generar el excel.");
		}
		return resultado;
	}

	private ResultadoFactDstvBean validacionesGenerarExcelDetallado(FacturacionDstvFilterBean facturacionDstv) {
		ResultadoFactDstvBean resultado = new ResultadoFactDstvBean(Boolean.FALSE);
		if(facturacionDstv == null){
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("Debe de seleccionar algún filtro para poder generar el excel.");
		} else if(facturacionDstv.getFecha() == null || facturacionDstv.getFecha().getFechaInicio() == null){
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("Debe de indicar una fecha inicial para poder generar el excel.");
		} else if(facturacionDstv.getIdContrato() == null){
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("Debe de indicar un contrato para poder generar el excel.");
		}
		return resultado;
	}

	@Override
	public List<FacturacionDstvBean> convertirListaEnBeanPantallaConsulta(List<FacturacionDistintivoVO> list) {
		List<FacturacionDstvBean> listaFactDstv = new ArrayList<>();
		for (FacturacionDistintivoVO facturacionDistintivoVO : list) {
			FacturacionDstvBean facturacionDstvBean = new FacturacionDstvBean();
			facturacionDstvBean.setIdFact(facturacionDistintivoVO.getIdDistintivoFacturado());
			facturacionDstvBean.setDocId(facturacionDistintivoVO.getDocDistintivo().getDocIdPerm());
			facturacionDstvBean.setContrato(facturacionDistintivoVO.getContrato().getColegiado().getNumColegiado()
					+ " - " + facturacionDistintivoVO.getContrato().getVia());
			facturacionDstvBean.setEstado(facturacionDistintivoVO.getEstado());
			facturacionDstvBean.setFecha(facturacionDistintivoVO.getFecha().toString());
			facturacionDstvBean.setTipo(facturacionDistintivoVO.getTipoDistintivo());
			facturacionDstvBean.setTipo(facturacionDistintivoVO.getTipoDistintivo());
			if (facturacionDistintivoVO.getTotal() != null) {
				facturacionDstvBean.setTotalFacturado(facturacionDistintivoVO.getTotal().toString());
			} else {
				facturacionDstvBean.setTotalFacturado("0");
			}
			if(facturacionDistintivoVO.getTotalDup() != null){
				facturacionDstvBean.setTotalFacturadoDup(facturacionDistintivoVO.getTotalDup().toString());
			} else {
				facturacionDstvBean.setTotalFacturadoDup("0");
			}
			if (facturacionDistintivoVO.getListadoIncidencias() != null
					&& !facturacionDistintivoVO.getListadoIncidencias().isEmpty()) {
				Long totalInciDstv = Long.valueOf(0);
				Long totalInciDup = Long.valueOf(0);
				for (FacturacionDstvIncidenciaVO incidenciaVO : facturacionDistintivoVO.getListadoIncidencias()) {
					if ("N".equals(incidenciaVO.getDuplicado())) {
						totalInciDstv += incidenciaVO.getCantidad().longValue();
					} else if ("S".equals(incidenciaVO.getDuplicado())) {
						totalInciDup += incidenciaVO.getCantidad().longValue();
					}
				}
				facturacionDstvBean.setTotalIncidencia(totalInciDstv.toString());
				facturacionDstvBean.setTotalInciDuplicado(totalInciDup.toString());
			}
			listaFactDstv.add(facturacionDstvBean);
		}
		return listaFactDstv;
	}

	@Override
	@Transactional
	public ResultadoPermisoDistintivoItvBean guardarFacturacionDocumento(DocPermDistItvVO docPermDistItvVO) {
		ResultadoPermisoDistintivoItvBean resultado = new ResultadoPermisoDistintivoItvBean(Boolean.FALSE);
		try {
			if (docPermDistItvVO != null) {
				Date fecha = new Date();
				FacturacionDistintivoVO facturacionDistintivoVO = new FacturacionDistintivoVO();
				facturacionDistintivoVO.setIdContrato(docPermDistItvVO.getIdContrato());
				facturacionDistintivoVO.setEstado(EstadoFacturacionDstv.Facturado.getValorEnum());
				facturacionDistintivoVO.setFecha(fecha);
				facturacionDistintivoVO.setIdDocPermDistItv(docPermDistItvVO.getIdDocPermDistItv());
				facturacionDistintivoVO.setTipoDistintivo(docPermDistItvVO.getTipoDistintivo());
				if (docPermDistItvVO.getListaTramitesDistintivo() != null
						&& !docPermDistItvVO.getListaTramitesDistintivo().isEmpty()) {
					facturacionDistintivoVO.setTotal(Long.valueOf(docPermDistItvVO.getListaTramitesDistintivo().size()));
				} else {
					facturacionDistintivoVO.setTotal(Long.valueOf(0));
				}
				if (docPermDistItvVO.getListaDuplicadoDistintivos() != null
						&& !docPermDistItvVO.getListaDuplicadoDistintivos().isEmpty()) {
					Long cantidadDupPrImpresion = Long.valueOf(0);
					Long cantidadDup = Long.valueOf(0);
					for (VehNoMatOegamVO vehNoMatOegamVO : docPermDistItvVO.getListaDuplicadoDistintivos()) {
						if ("S".equals(vehNoMatOegamVO.getPrimeraImpresion())) {
							cantidadDupPrImpresion++;
						} else {
							cantidadDup++;
						}
					}
					facturacionDistintivoVO.setTotal(facturacionDistintivoVO.getTotal() + cantidadDupPrImpresion);
					facturacionDistintivoVO.setTotalDup(cantidadDup);
				} else {
					facturacionDistintivoVO.setTotalDup(Long.valueOf(0));
				}
				facturacionDistintivoDao.guardar(facturacionDistintivoVO);
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de guardar la facturacion de los distintivos, error: ", e);
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("Ha sucedido un error a la hora de guardar la facturacion de los distintivos.");
		}
		return resultado;
	}

	@Override
	@Transactional
	public ResultadoPermisoDistintivoItvBean borrarFacturacion(Long idDoc) {
		ResultadoPermisoDistintivoItvBean resultado = new ResultadoPermisoDistintivoItvBean(Boolean.FALSE);
		try {
			if (idDoc != null) {
				FacturacionDistintivoVO facturacionDistintivo = facturacionDistintivoDao.getFacturacionPorDocId(idDoc);
				if (facturacionDistintivo != null) {
					List<FacturacionDstvIncidenciaVO> listaIncidencias = facturacionDstvIncDao.getListaIncidenciasFact(facturacionDistintivo.getIdDistintivoFacturado());
					if(listaIncidencias != null && !listaIncidencias.isEmpty()){
						for(FacturacionDstvIncidenciaVO incidencia : listaIncidencias){
							facturacionDstvIncDao.borrar(incidencia);
						}
					}
					facturacionDistintivoDao.borrar(facturacionDistintivo);
				}
			} else {
				resultado.setError(Boolean.TRUE);
				resultado.setMensaje("Debe de indicar un docId para poder eliminar la facturacion asociada.");
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de eliminar la facturación de los distintivos para el docId: "
					+ idDoc + ", error: ", e);
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje(
					"Ha sucedido un error a la hora de eliminar la facturación de los distintivos para el docId: "
							+ idDoc);
		}
		return resultado;
	}

	@Override
	@Transactional
	public ResultadoFactDstvBean guardarIncidenciaDstv(String codSeleccionados, FacturacionDstvIncDto facturacionDstvInc,BigDecimal idUsuario) {
		ResultadoFactDstvBean resultado = new ResultadoFactDstvBean(Boolean.FALSE);
		try {
			resultado = validarDatosMinimosGuardado(facturacionDstvInc);
			if (!resultado.getError()) {
				FacturacionDistintivoVO facturacionDistintivo = facturacionDistintivoDao.getFacturacionPorId(new Long(codSeleccionados));
				resultado = validarCantidadIncidencias(facturacionDstvInc, facturacionDistintivo);
				if (!resultado.getError()) {
					FacturacionDstvIncidenciaVO dstvIncidenciaVO = conversor.transform(facturacionDstvInc,FacturacionDstvIncidenciaVO.class);
					dstvIncidenciaVO.setIdDistintivoFacturado(facturacionDistintivo.getIdDistintivoFacturado());
					dstvIncidenciaVO.setCantidad(new Long(facturacionDstvInc.getCantidad()));
					dstvIncidenciaVO.setIdUsuarioIncidencia(idUsuario.longValue());
					dstvIncidenciaVO.setFechaIncidencia(new Date());
					dstvIncidenciaVO.setMotivo(facturacionDstvInc.getMotivoIncidencia());
					dstvIncidenciaVO.setDuplicado(facturacionDstvInc.getDuplicado());
					facturacionDstvIncDao.guardarOActualizar(dstvIncidenciaVO);
					if ("S".equals(dstvIncidenciaVO.getDuplicado())) {
						facturacionDistintivo.setTotalDup(facturacionDistintivo.getTotalDup() - dstvIncidenciaVO.getCantidad());
					} else {
						facturacionDistintivo.setTotal(facturacionDistintivo.getTotal() - dstvIncidenciaVO.getCantidad());
					}
					if ((facturacionDistintivo.getTotal() == null || facturacionDistintivo.getTotal() == 0) 
							&& (facturacionDistintivo.getTotalDup() == null || facturacionDistintivo.getTotalDup() == 0)) {
						facturacionDistintivo.setEstado(EstadoFacturacionDstv.Incidencia.getValorEnum());
					}
					facturacionDistintivoDao.guardarOActualizar(facturacionDistintivo);
				} else {
					resultado.setError(Boolean.TRUE);
					resultado.setMensaje("Ha sucedico un error con los datos de la facturación.");
				}
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de guardar los datos de la incidencia de facturacion", e);
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("Ha sucedido un error a la hora de guardar los datos de la incidencia de facturacion");
		}
		if(resultado.getError()){
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		}
		return resultado;
	}

	private ResultadoFactDstvBean validarCantidadIncidencias(FacturacionDstvIncDto facturacionDstvInc,FacturacionDistintivoVO facturacionDistintivo) {
		ResultadoFactDstvBean resultado = new ResultadoFactDstvBean(Boolean.FALSE);
		int totalInc = Integer.parseInt(facturacionDstvInc.getCantidad());
		if ("N".equals(facturacionDstvInc.getDuplicado())) {
			if (facturacionDistintivo.getTotal() != null) {
				int totalFact = facturacionDistintivo.getTotal().intValue();
				if (totalInc > totalFact) {
					resultado.setError(Boolean.TRUE);
					resultado.setMensaje("El número de incidencias no puede ser mayor al número total facturado.");
				}
			} else {
				resultado.setError(Boolean.TRUE);
				resultado.setMensaje("No se puede descontar las incidencias porque no se encuentra cantidad total en BBDD");
			}
		} else if ("S".equals(facturacionDstvInc.getDuplicado())) {
			if (facturacionDistintivo.getTotalDup() != null) {
				int totalFactDup = facturacionDistintivo.getTotalDup().intValue();
				if (totalInc > totalFactDup) {
					resultado.setError(Boolean.TRUE);
					resultado.setMensaje("El número de incidencias no puede ser mayor al número total facturado de duplicado.");
				}
			} else {
				resultado.setError(Boolean.TRUE);
				resultado.setMensaje("No se puede descontar las incidencias porque no se encuentra cantidad total en BBDD");
			}
		}
		return resultado;
	}

	private ResultadoFactDstvBean validarDatosMinimosGuardado(FacturacionDstvIncDto facturacionDstvInc) {
		ResultadoFactDstvBean resultado = new ResultadoFactDstvBean(Boolean.FALSE);
		if (facturacionDstvInc == null) {
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("Debe de rellenarlos datos obligatorios para poder realizar su guardado.");
		} else if (facturacionDstvInc.getDuplicado() == null || facturacionDstvInc.getDuplicado().isEmpty()) {
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("Tiene que indicar si la facturación es de duplicado o no.");
		}
		return resultado;
	}

}