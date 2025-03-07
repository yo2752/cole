package org.gestoresmadrid.oegamImportacion.estadisticasImportacion.service.impl;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.apache.commons.lang3.StringUtils;
import org.gestoresmadrid.core.contrato.model.vo.ContratoVO;
import org.gestoresmadrid.core.importacionFichero.model.dao.EstadisticaImportacionFicherosDao;
import org.gestoresmadrid.core.importacionFichero.model.enumerados.EstadosImportacionEstadistica;
import org.gestoresmadrid.core.importacionFichero.model.enumerados.OrigenImportacion;
import org.gestoresmadrid.core.importacionFichero.model.enumerados.ResultadoImportacionEnum;
import org.gestoresmadrid.core.importacionFichero.model.vo.EstadisticaImportacionFicherosVO;
import org.gestoresmadrid.oegamImportacion.bean.ResultadoBean;
import org.gestoresmadrid.oegamImportacion.bean.ResultadoImportacionBean;
import org.gestoresmadrid.oegamImportacion.contrato.service.ServicioContratoImportacion;
import org.gestoresmadrid.oegamImportacion.control.service.ServicioControlContratoImp;
import org.gestoresmadrid.oegamImportacion.control.service.ServicioControlPeticionesImp;
import org.gestoresmadrid.oegamImportacion.estadisticasImportacion.service.ServicioEstadisticaImportacion;
import org.gestoresmadrid.oegamImportacion.estadisticasImportacion.view.bean.ConsultaEstadisticasImportBean;
import org.gestoresmadrid.oegamImportacion.estadisticasImportacion.view.bean.EstadisticaImportacionBean;
import org.gestoresmadrid.oegamImportacion.estadisticasImportacion.view.bean.EstadisticaImportacionFilterBean;
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
public class ServicioEstadisticaImportacionImpl implements ServicioEstadisticaImportacion {

	private static final long serialVersionUID = 1702962792145932698L;

	private static final ILoggerOegam log = LoggerOegam.getLogger(ServicioEstadisticaImportacionImpl.class);

	@Autowired
	EstadisticaImportacionFicherosDao estadisticaImportacionFicherosDao;

	@Autowired
	ServicioContratoImportacion servicioContrato;

	@Autowired
	ServicioControlPeticionesImp servicioControlPeticionesImp;

	@Autowired
	ServicioControlContratoImp servicioControlContratoImp;

	@Autowired
	GestorDocumentos gestorDocumentos;

	@Autowired
	UtilesFecha utilesFecha;

	@Override
	@Transactional(readOnly = true)
	public EstadisticaImportacionFicherosVO getEstadisticaImportacion(Long idImportacionFich) {
		try {
			return estadisticaImportacionFicherosDao.getEstadisticaImportacion(idImportacionFich);
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de obtener la estadisticas, error: ", e);
		}
		return null;
	}

	@Override
	@Transactional(readOnly = true)
	public List<EstadisticaImportacionFicherosVO> listaImportacionesEjecutandose(Long idContrato, String tipo) {
		ResultadoImportacionBean resultado = new ResultadoImportacionBean(Boolean.FALSE);
		try {
			return estadisticaImportacionFicherosDao.listaEstadisticasEjecutandose(idContrato, tipo);
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de obtener la lista de estadisticas, error: ", e);
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("Ha sucedido un error a la hora de obtener la lista de estadísticas en BBDD.");
		}
		return null;
	}

	@Override
	public List<EstadisticaImportacionBean> convertirListaEnBeanPantallaConsulta(List<EstadisticaImportacionFicherosVO> listaBBDD) {
		List<EstadisticaImportacionBean> listaImpor = new ArrayList<EstadisticaImportacionBean>();
		for (EstadisticaImportacionFicherosVO importacionFicherosVO : listaBBDD) {
			EstadisticaImportacionBean estadisticaImportacionBean = new EstadisticaImportacionBean();
			estadisticaImportacionBean.setTipo(importacionFicherosVO.getTipo());
			if (ResultadoImportacionEnum.OK.getCodigo().equals(importacionFicherosVO.getTipoError())) {
				estadisticaImportacionBean.setTipoError(ResultadoImportacionEnum.OK.getResultado());
			} else if (ResultadoImportacionEnum.OK_PARCIAL.getCodigo().equals(importacionFicherosVO.getTipoError())) {
				estadisticaImportacionBean.setTipoError(ResultadoImportacionEnum.OK_PARCIAL.getResultado());
			} else if (ResultadoImportacionEnum.Error_Catastrofico.getCodigo().equals(importacionFicherosVO.getTipoError())) {
				estadisticaImportacionBean.setTipoError(ResultadoImportacionEnum.Error_Catastrofico.getResultado());
			}
			if (importacionFicherosVO.getNumOk() != null) {
				estadisticaImportacionBean.setNumOk(importacionFicherosVO.getNumOk().toString());
			}
			if (importacionFicherosVO.getNumError() != null) {
				estadisticaImportacionBean.setNumError(importacionFicherosVO.getNumError().toString());
			}
			ContratoVO contrato = servicioContrato.getContrato(new BigDecimal(importacionFicherosVO.getIdContrato()));
			estadisticaImportacionBean.setDescContrato(contrato.getColegiado().getNumColegiado() + " - " + contrato.getVia());
			estadisticaImportacionBean.setFecha(utilesFecha.getFechaFracionada(importacionFicherosVO.getFecha()));
			listaImpor.add(estadisticaImportacionBean);
		}
		return listaImpor;
	}

	@Override
	public List<ConsultaEstadisticasImportBean> convertirListaEnBeanPantallaConsultaEst(List<EstadisticaImportacionFicherosVO> listaBBDD) {
		List<ConsultaEstadisticasImportBean> listaImpor = new ArrayList<ConsultaEstadisticasImportBean>();
		for (EstadisticaImportacionFicherosVO importacionFicherosVO : listaBBDD) {
			ConsultaEstadisticasImportBean estadisticaImportacionBean = new ConsultaEstadisticasImportBean();

			estadisticaImportacionBean.setIdImportacionFich(importacionFicherosVO.getIdImportacionFich());
			estadisticaImportacionBean.setFecha(utilesFecha.getFechaFracionada(importacionFicherosVO.getFecha()));
			estadisticaImportacionBean.setNumColegiado(importacionFicherosVO.getContrato().getColegiado().getNumColegiado());
			estadisticaImportacionBean.setOrigen(OrigenImportacion.convertirTexto(importacionFicherosVO.getOrigen()));

			if (importacionFicherosVO.getNumOk() != null) {
				estadisticaImportacionBean.setNumOk(importacionFicherosVO.getNumOk().toString());
			}

			if (importacionFicherosVO.getNumError() != null) {
				estadisticaImportacionBean.setNumError(importacionFicherosVO.getNumError().toString());
			}

			estadisticaImportacionBean.setNombre(importacionFicherosVO.getNombre());
			estadisticaImportacionBean.setTipo(importacionFicherosVO.getTipo());

			if (StringUtils.isNotBlank(importacionFicherosVO.getTipoError())) {
				estadisticaImportacionBean.setEstado(ResultadoImportacionEnum.convertirResultadoCorto(importacionFicherosVO.getTipoError()));
			} else {
				estadisticaImportacionBean.setEstado("EJECUTANDO");
			}

			listaImpor.add(estadisticaImportacionBean);
		}
		return listaImpor;
	}

	@Override
	@Transactional
	public ResultadoImportacionBean guardarEstadistica(EstadisticaImportacionFicherosVO estadisticaImporFich) {
		ResultadoImportacionBean resultado = new ResultadoImportacionBean(Boolean.FALSE);
		try {
			if (estadisticaImporFich != null) {
				Long id = (Long) estadisticaImportacionFicherosDao.guardar(estadisticaImporFich);
				resultado.setIdImportacionFich(id);
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de guardar la estadistica en BBDD, error: ", e);
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("Ha sucedido un error a la hora de guardar la estadística en BBDD.");
		}
		return resultado;
	}

	@Override
	@Transactional
	public ResultadoImportacionBean actualizarEstadistica(EstadisticaImportacionFicherosVO estadisticaImporFich) {
		ResultadoImportacionBean resultado = new ResultadoImportacionBean(Boolean.FALSE);
		try {
			if (estadisticaImporFich != null) {
				estadisticaImportacionFicherosDao.actualizar(estadisticaImporFich);
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de guardar la estadistica en BBDD, error: ", e);
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("Ha sucedido un error a la hora de guardar la estadística en BBDD.");
		}
		return resultado;
	}

	@Override
	@Transactional
	public ResultadoBean terminarEjecucion(String idSeleccioneados) {
		ResultadoBean resultado = new ResultadoBean(Boolean.FALSE);
		try {
			if (idSeleccioneados != null && !idSeleccioneados.isEmpty()) {
				String[] listaIds = idSeleccioneados.split("_");
				for (String idImportacionFich : listaIds) {
					EstadisticaImportacionFicherosVO estadistica = getEstadisticaImportacion(Long.valueOf(idImportacionFich));
					if (estadistica != null) {
						if (EstadosImportacionEstadistica.Ejecutandose.getValorEnum().equals(estadistica.getEstado())) {
							estadistica.setEstado(EstadosImportacionEstadistica.Parado.getValorEnum());
							estadistica.setTipoError(ResultadoImportacionEnum.Parado.getCodigo());
							estadisticaImportacionFicherosDao.actualizar(estadistica);
						} else {
							if (StringUtils.isNotBlank(estadistica.getNombre())) {
								resultado.addListaMensaje("Sólo pueden terminar la ejecución importaciones en 'Ejecución'. Nombre documento: " + estadistica.getNombre());
							} else {
								resultado.addListaMensaje("Sólo pueden terminar la ejecución importaciones en 'Ejecución'. Id: " + idImportacionFich);
							}

						}
					} else {
						resultado.addListaMensaje("No se ha encontrado la estadística para parar la ejecución de la importación. Id: " + idImportacionFich);
					}
				}
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de para la ejecución de una importación, error: ", e);
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("Ha sucedido un error a la hora de para la ejecución de una importación.");
		}
		return resultado;
	}

	@Override
	@Transactional
	public ResultadoImportacionBean generarExcel(EstadisticaImportacionFilterBean estadisticaImportacionFilterBean) {
		ResultadoImportacionBean resultado = new ResultadoImportacionBean(Boolean.FALSE);
		try {
			resultado = validacionesGenerarExcel(estadisticaImportacionFilterBean);
			if (!resultado.getError()) {
				if (estadisticaImportacionFilterBean.getIdContrato() == null) {
					List<EstadisticaImportacionFicherosVO> listaImportacion = estadisticaImportacionFicherosDao.getListaImportacionExcel(estadisticaImportacionFilterBean.getFecha().getFechaInicio(),
							estadisticaImportacionFilterBean.getFecha().getFechaFin(), estadisticaImportacionFilterBean.getTipo(), estadisticaImportacionFilterBean.getIdContrato());
					if (listaImportacion != null && !listaImportacion.isEmpty()) {
						resultado = generarExcelImportacion(listaImportacion, estadisticaImportacionFilterBean.getTipo());
						if (!resultado.getError()) {
							resultado.setNombreFichero(resultado.getNombreFichero());
							resultado.setEsDescargable(Boolean.TRUE);
							resultado.setFechaGenExcel(new Date());
						}
					} else {
						resultado.setError(Boolean.TRUE);
						resultado.setMensaje("No se han encontrado datos para poder generar el excel.");
					}
				} else {
					List<EstadisticaImportacionFicherosVO> listaImportacion = estadisticaImportacionFicherosDao.getListaImportacionExcel(estadisticaImportacionFilterBean.getFecha().getFechaInicio(),
							estadisticaImportacionFilterBean.getFecha().getFechaFin(), estadisticaImportacionFilterBean.getTipo(), estadisticaImportacionFilterBean.getIdContrato());
					if (listaImportacion != null && !listaImportacion.isEmpty()) {
						resultado = generarExcelImportacion(listaImportacion, estadisticaImportacionFilterBean.getTipo());
						if (!resultado.getError()) {
							resultado.setNombreFichero(resultado.getNombreFichero());
							resultado.setEsDescargable(Boolean.TRUE);
							resultado.setFechaGenExcel(new Date());
						}
					} else {
						resultado.setError(Boolean.TRUE);
						resultado.setMensaje("No se han encontrado datos para poder generar el excel.");
					}
				}
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de generar el excel con la importación, error: ", e);
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("Ha sucedido un error a la hora de generar el excel con la importación.");
		}
		return resultado;
	}

	private ResultadoImportacionBean generarExcelImportacion(List<EstadisticaImportacionFicherosVO> listaImportacion, String tipo) {
		ResultadoImportacionBean resultado = new ResultadoImportacionBean(Boolean.FALSE);
		WritableWorkbook copyWorkbook = null;
		WritableSheet sheet;
		try {
			File archivo = null;
			resultado.setNombreFichero("Resumen_Importacion_" + new SimpleDateFormat("ddMMYYHHmmss").format(new Date()));
			FicheroBean fichero = new FicheroBean();
			fichero.setExtension(ConstantesGestorFicheros.EXTENSION_XLS);
			fichero.setNombreDocumento(resultado.getNombreFichero());
			fichero.setTipoDocumento(ConstantesGestorFicheros.EXCELS);
			fichero.setSubTipo(ConstantesGestorFicheros.ESTADISTICA_IMPORTACION_FICH);
			fichero.setFecha(utilesFecha.getFechaActual());
			fichero.setSobreescribir(true);
			fichero.setFichero(new File(resultado.getNombreFichero()));
			archivo = gestorDocumentos.guardarFichero(fichero);
			copyWorkbook = Workbook.createWorkbook(archivo);
			sheet = copyWorkbook.createSheet("Resumen_Importacion", 0);
			CellView vistaCeldas = new CellView();
			vistaCeldas.setAutosize(true);
			int numColumnas = 6;

			for (int i = 0; i <= numColumnas; i++) {
				sheet.setColumnView(i, vistaCeldas);
			}
			// Generamos las cabeceras de la hoja Excel con el formato indicado
			// Formato de las celdas de cabecera
			WritableFont fuenteCabecera = new WritableFont(WritableFont.createFont("MS Sans Serif"), 10, WritableFont.BOLD, false);
			fuenteCabecera.setColour(Colour.WHITE);
			fuenteCabecera.setBoldStyle(WritableFont.BOLD);
			WritableCellFormat formatoCabecera = new WritableCellFormat(fuenteCabecera);

			formatoCabecera.setBackground(Colour.DARK_RED);
			formatoCabecera.setAlignment(Alignment.LEFT);
			formatoCabecera.setBorder(Border.ALL, BorderLineStyle.THIN);

			// Formato de las celdas de datos
			WritableFont fuenteDatos = new WritableFont(WritableFont.createFont("MS Sans Serif"), 10, WritableFont.NO_BOLD, false);
			fuenteDatos.setColour(Colour.BLACK);
			WritableCellFormat formatoDatos = new WritableCellFormat(fuenteDatos);

			formatoDatos.setAlignment(Alignment.LEFT);
			sheet.addCell(new Label(0, 0, "Colegiado", formatoCabecera));
			sheet.addCell(new Label(1, 0, "Fecha", formatoCabecera));
			sheet.addCell(new Label(2, 0, "Tipo", formatoCabecera));
			sheet.addCell(new Label(3, 0, "Numero OK", formatoCabecera));
			sheet.addCell(new Label(4, 0, "Numero Error", formatoCabecera));
			sheet.addCell(new Label(5, 0, "Resultado Importación", formatoCabecera));

			int conFila = 1;
			int cont = 0;
			for (EstadisticaImportacionFicherosVO resultImpor : listaImportacion) {
				cont = 0;
				ContratoVO contrato = servicioContrato.getContrato(new BigDecimal(resultImpor.getIdContrato()));
				sheet.addCell(new Label(cont++, conFila, contrato.getColegiado().getNumColegiado(), formatoDatos));
				sheet.addCell(new Label(cont++, conFila, resultImpor.getFecha().toString(), formatoDatos));
				sheet.addCell(new Label(cont++, conFila, resultImpor.getTipo(), formatoDatos));
				sheet.addCell(new Label(cont++, conFila, resultImpor.getNumOk() == null ? "0" : resultImpor.getNumOk().toString(), formatoDatos));
				sheet.addCell(new Label(cont++, conFila, resultImpor.getNumError() == null ? "0" : resultImpor.getNumError().toString(), formatoDatos));
				if (ResultadoImportacionEnum.OK.getCodigo().equals(resultImpor.getTipoError())) {
					sheet.addCell(new Label(cont++, conFila, ResultadoImportacionEnum.OK.getResultado(), formatoDatos));
				} else if (ResultadoImportacionEnum.OK_PARCIAL.getCodigo().equals(resultImpor.getTipoError())) {
					sheet.addCell(new Label(cont++, conFila, ResultadoImportacionEnum.OK_PARCIAL.getResultado(), formatoDatos));
				} else if (ResultadoImportacionEnum.Error_Catastrofico.getCodigo().equals(resultImpor.getTipoError())) {
					sheet.addCell(new Label(cont++, conFila, ResultadoImportacionEnum.Error_Catastrofico.getResultado(), formatoDatos));
				}

				conFila++;
			}
			copyWorkbook.write();
		} catch (Throwable e) {
			log.error("Ha sucedido un error a la hora de generar el excel con la importación, error: ", e);
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("Ha sucedido un error a la hora de generar el excel con la importación.");
		} finally {
			if (copyWorkbook != null) {
				try {
					copyWorkbook.close();
				} catch (WriteException e) {
					log.error("Error ", e);
				} catch (IOException e) {
					log.error("Error ", e);
				}
			}
		}
		return resultado;
	}

	private ResultadoImportacionBean validacionesGenerarExcel(EstadisticaImportacionFilterBean estadisticaImportacionFilterBean) {
		ResultadoImportacionBean resultado = new ResultadoImportacionBean(Boolean.FALSE);
		if (estadisticaImportacionFilterBean == null) {
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("Debe de seleccionar algún filtro para poder generar el excel.");
		} else if (estadisticaImportacionFilterBean.getFecha() == null || estadisticaImportacionFilterBean.getFecha().getFechaInicio() == null && estadisticaImportacionFilterBean.getFecha()
				.getFechaFin() == null) {
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("Debe de indicar una fecha inicial y una fecha fin para poder generar el excel.");
		}
		return resultado;
	}

	@Override
	@Transactional
	public ResultadoImportacionBean descargarExcel(String nombreFichero, Date fechaGener) {
		ResultadoImportacionBean resultado = new ResultadoImportacionBean(Boolean.FALSE);
		try {
			if (nombreFichero != null && !nombreFichero.isEmpty() && fechaGener != null) {
				FileResultBean fichero = gestorDocumentos.buscarFicheroPorNombreTipo(ConstantesGestorFicheros.EXCELS, ConstantesGestorFicheros.ESTADISTICA_IMPORTACION_FICH, utilesFecha
						.getFechaConDate(fechaGener), nombreFichero, ConstantesGestorFicheros.EXTENSION_XLS);
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
			log.error("Ha sucedido un error a la hora de descargar el excel con la importación, error: ", e);
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("Ha sucedido un error a la hora de descargar el excel con la importación.");
		}
		return resultado;
	}

	@Override
	@Transactional
	public ResultadoImportacionBean generarEstadisticaDinamica(EstadisticaImportacionFilterBean estadisticaImportacionFilterBean) {
		ResultadoImportacionBean resultado = new ResultadoImportacionBean(Boolean.FALSE);
		try {
			if (estadisticaImportacionFilterBean != null) {
				if (estadisticaImportacionFilterBean.getFecha() != null && estadisticaImportacionFilterBean.getFecha().getDiaInicio() != null && !estadisticaImportacionFilterBean.getFecha()
						.getDiaInicio().isEmpty() && estadisticaImportacionFilterBean.getFecha().getDiaFin() != null && !estadisticaImportacionFilterBean.getFecha().getDiaFin().isEmpty()) {
					List<EstadisticaImportacionFicherosVO> listaImportacion = estadisticaImportacionFicherosDao.getListaImportacion(estadisticaImportacionFilterBean.getFecha().getFechaInicio(),
							estadisticaImportacionFilterBean.getFecha().getFechaFin(), estadisticaImportacionFilterBean.getOrigen());
					if (listaImportacion != null && !listaImportacion.isEmpty()) {
						HashMap<String, Long> listaEstadisticas = new HashMap<String, Long>();
						HashMap<String, Long> listaEstadisticasOk = new HashMap<String, Long>();
						HashMap<String, Long> listaEstadisticasError = new HashMap<String, Long>();
						HashMap<String, Long> listaEstadisticasTipoError0 = new HashMap<String, Long>();
						HashMap<String, Long> listaEstadisticasTipoError1 = new HashMap<String, Long>();
						HashMap<String, Long> listaEstadisticasTipoError2 = new HashMap<String, Long>();
						Long totalExpedientesOk = null;
						Long totalExpedientesError = null;
						Long totalTipoError0 = null;
						Long totalTipoError1 = null;
						Long totalTipoError2 = null;
						for (EstadisticaImportacionFicherosVO esImportacionFicherosVO : listaImportacion) {
							totalExpedientesOk = new Long(0);
							if (esImportacionFicherosVO.getNumOk() != null) {
								if (listaEstadisticasOk != null && !listaEstadisticasOk.isEmpty() && listaEstadisticasOk.containsKey(esImportacionFicherosVO.getContrato().getColegiado()
										.getNumColegiado())) {
									totalExpedientesOk = listaEstadisticasOk.get(esImportacionFicherosVO.getContrato().getColegiado().getNumColegiado());
								}
								totalExpedientesOk += esImportacionFicherosVO.getNumOk();
							}
							if (totalExpedientesOk != 0) {
								listaEstadisticasOk.put(esImportacionFicherosVO.getContrato().getColegiado().getNumColegiado(), totalExpedientesOk);
							} else if (totalExpedientesOk == 0 && !listaEstadisticasOk.containsKey(esImportacionFicherosVO.getContrato().getColegiado().getNumColegiado())) {
								listaEstadisticasOk.put(esImportacionFicherosVO.getContrato().getColegiado().getNumColegiado(), totalExpedientesOk);
							}
							totalExpedientesError = new Long(0);
							if (esImportacionFicherosVO.getNumError() != null) {
								if (listaEstadisticasError != null && !listaEstadisticasError.isEmpty() && listaEstadisticasError.containsKey(esImportacionFicherosVO.getContrato().getColegiado()
										.getNumColegiado())) {
									totalExpedientesError = listaEstadisticasError.get(esImportacionFicherosVO.getContrato().getColegiado().getNumColegiado());
								}
								totalExpedientesError += esImportacionFicherosVO.getNumError();
							}
							if (totalExpedientesError != 0) {
								listaEstadisticasError.put(esImportacionFicherosVO.getContrato().getColegiado().getNumColegiado(), totalExpedientesError);
							} else if (totalExpedientesError == 0 && !listaEstadisticasError.containsKey(esImportacionFicherosVO.getContrato().getColegiado().getNumColegiado())) {
								listaEstadisticasError.put(esImportacionFicherosVO.getContrato().getColegiado().getNumColegiado(), totalExpedientesError);
							}
							if (esImportacionFicherosVO.getContrato() != null && esImportacionFicherosVO.getContrato().getColegiado().getNumColegiado() != null) {
								Long totalColegiado = new Long(0);
								if (listaEstadisticas != null && !listaEstadisticas.isEmpty() && listaEstadisticas.containsKey(esImportacionFicherosVO.getContrato().getColegiado()
										.getNumColegiado())) {
									totalColegiado = listaEstadisticas.get(esImportacionFicherosVO.getContrato().getColegiado().getNumColegiado());
								}
								listaEstadisticas.put(esImportacionFicherosVO.getContrato().getColegiado().getNumColegiado(), totalColegiado + new Long(esImportacionFicherosVO.getContrato()
										.getColegiado().getNumColegiado()));
							}
							totalTipoError0 = new Long(0);
							if (esImportacionFicherosVO.getTipoError() != null && ResultadoImportacionEnum.OK.getCodigo().equals(esImportacionFicherosVO.getTipoError())) {
								if (listaEstadisticasTipoError0 != null && !listaEstadisticasTipoError0.isEmpty() && listaEstadisticasTipoError0.containsKey(esImportacionFicherosVO.getContrato()
										.getColegiado().getNumColegiado())) {
									totalTipoError0 = listaEstadisticasTipoError0.get(esImportacionFicherosVO.getContrato().getColegiado().getNumColegiado());
								}
								totalTipoError0 += esImportacionFicherosVO.getTipoError().length();
							}
							if (totalTipoError0 != 0) {
								listaEstadisticasTipoError0.put(esImportacionFicherosVO.getContrato().getColegiado().getNumColegiado(), totalTipoError0);
							} else if (totalTipoError0 == 0 && !listaEstadisticasTipoError0.containsKey(esImportacionFicherosVO.getContrato().getColegiado().getNumColegiado())) {
								listaEstadisticasTipoError0.put(esImportacionFicherosVO.getContrato().getColegiado().getNumColegiado(), totalTipoError0);
							}
							totalTipoError1 = new Long(0);
							if (esImportacionFicherosVO.getTipoError() != null && ResultadoImportacionEnum.OK_PARCIAL.getCodigo().equals(esImportacionFicherosVO.getTipoError())) {
								if (listaEstadisticasTipoError1 != null && !listaEstadisticasTipoError1.isEmpty() && listaEstadisticasTipoError1.containsKey(esImportacionFicherosVO.getContrato()
										.getColegiado().getNumColegiado())) {
									totalTipoError1 = listaEstadisticasTipoError1.get(esImportacionFicherosVO.getContrato().getColegiado().getNumColegiado());
								}
								totalTipoError1 += esImportacionFicherosVO.getTipoError().length();
							}
							if (totalTipoError1 != 0) {
								listaEstadisticasTipoError1.put(esImportacionFicherosVO.getContrato().getColegiado().getNumColegiado(), totalTipoError1);
							} else if (totalTipoError1 == 0 && !listaEstadisticasTipoError1.containsKey(esImportacionFicherosVO.getContrato().getColegiado().getNumColegiado())) {
								listaEstadisticasTipoError1.put(esImportacionFicherosVO.getContrato().getColegiado().getNumColegiado(), totalTipoError1);
							}
							totalTipoError2 = new Long(0);
							if (esImportacionFicherosVO.getTipoError() != null && ResultadoImportacionEnum.Error_Catastrofico.getCodigo().equals(esImportacionFicherosVO.getTipoError())) {
								if (listaEstadisticasTipoError2 != null && !listaEstadisticasTipoError2.isEmpty() && listaEstadisticasTipoError2.containsKey(esImportacionFicherosVO.getContrato()
										.getColegiado().getNumColegiado())) {
									totalTipoError2 = listaEstadisticasTipoError2.get(esImportacionFicherosVO.getContrato().getColegiado().getNumColegiado());
								}
								totalTipoError2 += esImportacionFicherosVO.getTipoError().length();
							}
							if (totalTipoError2 != 0) {
								listaEstadisticasTipoError2.put(esImportacionFicherosVO.getContrato().getColegiado().getNumColegiado(), totalTipoError2);
							} else if (totalTipoError2 == 0 && !listaEstadisticasTipoError2.containsKey(esImportacionFicherosVO.getContrato().getColegiado().getNumColegiado())) {
								listaEstadisticasTipoError2.put(esImportacionFicherosVO.getContrato().getColegiado().getNumColegiado(), totalTipoError2);
							}
						}
						@SuppressWarnings({ "rawtypes", "unchecked" })
						Map<String, String> mapOrdenadoOk = new TreeMap(listaEstadisticasOk);
						String data1 = "[";
						for (String numColegiado : mapOrdenadoOk.keySet()) {
							if (data1.equals("[")) {
								data1 += listaEstadisticasOk.get(numColegiado);
							} else {
								data1 += "," + listaEstadisticasOk.get(numColegiado);
							}
						}
						data1 += "]";
						resultado.setDataGraficos1(data1);

						@SuppressWarnings({ "rawtypes", "unchecked" })
						Map<String, String> mapOrdenadoError = new TreeMap(listaEstadisticasError);
						String data2 = "[";
						for (String numColegiado : mapOrdenadoError.keySet()) {
							if (data2.equals("[")) {
								data2 += listaEstadisticasError.get(numColegiado);
							} else {
								data2 += "," + listaEstadisticasError.get(numColegiado);
							}
						}
						data2 += "]";
						resultado.setDataGraficos2(data2);

						@SuppressWarnings({ "rawtypes", "unchecked" })
						Map<String, String> mapOrdenadoTipoError0 = new TreeMap(listaEstadisticasTipoError0);
						String dataError0 = "[";
						for (String numColegiado : mapOrdenadoTipoError0.keySet()) {
							if (dataError0.equals("[")) {
								dataError0 += listaEstadisticasTipoError0.get(numColegiado);
							} else {
								dataError0 += "," + listaEstadisticasTipoError0.get(numColegiado);
							}
						}
						dataError0 += "]";
						resultado.setDataGraficosError0(dataError0);

						@SuppressWarnings({ "rawtypes", "unchecked" })
						Map<String, String> mapOrdenadoTipoError1 = new TreeMap(listaEstadisticasTipoError1);
						String dataError1 = "[";
						for (String numColegiado : mapOrdenadoTipoError1.keySet()) {
							if (dataError1.equals("[")) {
								dataError1 += listaEstadisticasTipoError1.get(numColegiado);
							} else {
								dataError1 += "," + listaEstadisticasTipoError1.get(numColegiado);
							}
						}
						dataError1 += "]";
						resultado.setDataGraficosError1(dataError1);

						@SuppressWarnings({ "rawtypes", "unchecked" })
						Map<String, String> mapOrdenadoTipoError2 = new TreeMap(listaEstadisticasTipoError2);
						String dataError2 = "[";
						for (String numColegiado : mapOrdenadoTipoError2.keySet()) {
							if (dataError2.equals("[")) {
								dataError2 += listaEstadisticasTipoError2.get(numColegiado);
							} else {
								dataError2 += "," + listaEstadisticasTipoError2.get(numColegiado);
							}
						}
						dataError2 += "]";
						resultado.setDataGraficosError2(dataError2);

						@SuppressWarnings({ "rawtypes", "unchecked" })
						Map<String, String> mapOrdenadoListaEstadistica = new TreeMap(listaEstadisticas);
						String categor = "[";
						for (String numColegiado : mapOrdenadoListaEstadistica.keySet()) {
							if (categor.equals("[")) {
								categor += "'" + numColegiado + "'";
							} else {
								categor += ", '" + numColegiado + "'";
							}
						}
						categor += "]";
						resultado.setDataCategoria(categor);
					} else {
						resultado.setError(Boolean.TRUE);
						resultado.setMensaje("Sin información.");
					}
				} else {
					resultado.setError(Boolean.TRUE);
					resultado.setMensaje("La fecha para realizar la búsqueda está vacía. Debe indicarse para la Fecha de Alta los campos Desde y Hasta");
				}
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de generar los datos del grafico, error: ", e);
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("Ha sucedido un error a la hora de generar los datos del gráfico.");
		}
		return resultado;
	}

	@Override
	@Transactional(readOnly = true)
	public boolean hayImportacionesEjecutandose(Long idContrato, String tipo) {
		List<EstadisticaImportacionFicherosVO> lista = listaImportacionesEjecutandose(idContrato, tipo);
		if (lista != null && !lista.isEmpty()) {
			return true;
		}
		return false;
	}

	@Override
	@Transactional(readOnly = true)
	public int numeroPeticionesEjecutandosePorTipo(String tipo) {
		Integer numeroPeticiones = estadisticaImportacionFicherosDao.numeroPeticionesEjecutandose(tipo);
		if (numeroPeticiones != null) {
			return numeroPeticiones.intValue();
		}
		return 0;
	}

	@Override
	@Transactional(readOnly = true)
	public boolean superaLimiteImportaciones(String tipo) {
		int numeroPeticionesEjec = numeroPeticionesEjecutandosePorTipo(tipo);
		if (numeroPeticionesEjec > 0) {
			int numPeticionesPermitidas = servicioControlPeticionesImp.numeroPeticiones(tipo);
			if (numeroPeticionesEjec >= numPeticionesPermitidas) {
				return true;
			}
		}
		return false;
	}

	@Override
	@Transactional(readOnly = true)
	public boolean contratoConPermiso(Long idContrato, String tipo) {
		return servicioControlContratoImp.esPermitido(idContrato, tipo);
	}
}
