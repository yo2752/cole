package org.gestoresmadrid.oegam2comun.tasas.model.service.impl;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.Serializable;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.gestoresmadrid.core.model.exceptions.TransactionalException;
import org.gestoresmadrid.core.modelos.model.enumerados.Decision;
import org.gestoresmadrid.core.tasas.model.dao.TasasDgtDao;
import org.gestoresmadrid.core.tasas.model.enumeration.FormatoTasa;
import org.gestoresmadrid.core.tasas.model.vo.TasaDgtVO;
import org.gestoresmadrid.oegam2comun.tasas.model.dto.RespuestaTasas;
import org.gestoresmadrid.oegam2comun.tasas.model.service.ServicioGeneracionEtiquetasTasas;
import org.gestoresmadrid.oegam2comun.tasas.model.service.ServicioImportacionTasa;
import org.gestoresmadrid.oegam2comun.tasas.model.service.ServicioPersistenciaTasa;
import org.gestoresmadrid.oegam2comun.tasas.model.service.ServicioPersistenciaTasaPegatina;
import org.gestoresmadrid.oegam2comun.tasas.model.service.ServicioTasa;
import org.gestoresmadrid.oegam2comun.tasas.view.beans.GeneracionEtiquetasBean;
import org.gestoresmadrid.oegam2comun.tasas.view.beans.ResumenCompraBean;
import org.gestoresmadrid.oegam2comun.tasas.view.beans.ResumenTasas;
import org.gestoresmadrid.oegam2comun.tasas.view.beans.Tasa;
import org.gestoresmadrid.oegam2comun.tasas.view.beans.TasasSolicitadasBean;
import org.gestoresmadrid.oegamComun.tasa.view.dto.TasaDto;
import org.gestoresmadrid.utilidades.components.GestorPropiedades;
import org.gestoresmadrid.utilidades.components.UtilesFecha;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import es.globaltms.gestorDocumentos.bean.FicheroBean;
import es.globaltms.gestorDocumentos.constantes.ConstantesGestorFicheros;
import es.globaltms.gestorDocumentos.interfaz.GestorDocumentos;
import escrituras.beans.ResultBean;
import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;
import utilidades.web.OegamExcepcion;

@Repository
public class ServicioImportacionTasaImpl implements ServicioImportacionTasa, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2814052699391620499L;

	private ILoggerOegam log = LoggerOegam.getLogger(ServicioImportacionTasaImpl.class);
	

	@Autowired
	private ServicioPersistenciaTasaPegatina servicioPersistenciaTasaPegina;

	@Autowired
	private ServicioPersistenciaTasa servicioPersistencia;

	@Autowired
	private GestorDocumentos gestorDocumentos;
	
	@Autowired
	private ServicioGeneracionEtiquetasTasas servicioGeneracionEtiquetasTasas;

	@Autowired
	private ServicioTasa servicioTasa;

	@Autowired
	private TasasDgtDao tasasDtgDao;
	
	@Autowired
	private GestorPropiedades gestorPropiedades;
	
	@Autowired
	UtilesFecha utilesFecha;

	@Override
	public ResultBean importarTasas(File fichero, Long idUsuario, BigDecimal contrato, String idSession, FormatoTasa formato, String numColegiado, Boolean tienePermisosAdmin, Boolean tienePermisoColegio) {
		ResultBean resultImportTasas = new ResultBean();
		boolean tasaDePegatina = FormatoTasa.PEGATINA.equals(formato);

		List<ResumenTasas> resumen = new ArrayList<ResumenTasas>();
		resumen.add(0, new ResumenTasas("1.1"));
		resumen.add(1, new ResumenTasas("1.2"));
		resumen.add(2, new ResumenTasas("1.3"));
		resumen.add(3, new ResumenTasas("1.4"));
		resumen.add(4, new ResumenTasas("1.5"));
		resumen.add(5, new ResumenTasas("1.6"));
		resumen.add(6, new ResumenTasas("2.3"));
		resumen.add(7, new ResumenTasas("4.1"));
		resumen.add(8, new ResumenTasas("4.2"));
		resumen.add(9, new ResumenTasas("4.3"));
		resumen.add(10, new ResumenTasas("4.31"));
		resumen.add(11, new ResumenTasas("4.32"));
		resumen.add(12, new ResumenTasas("4.33"));
		resumen.add(13, new ResumenTasas("4.34"));
		resumen.add(14, new ResumenTasas("4.4"));
		resumen.add(15, new ResumenTasas("4.5"));
		resumen.add(16, new ResumenTasas("TOTAL"));

		// Creamos el fichero
		File ficheroErrores = null;

		FicheroBean ficheroBean = new FicheroBean();
		ficheroBean.setTipoDocumento(ConstantesGestorFicheros.IMPORTACION);
		ficheroBean.setSubTipo(ConstantesGestorFicheros.TASAS);
		ficheroBean.setExtension(ConstantesGestorFicheros.EXTENSION_TXT);
		ficheroBean.setFecha(utilesFecha.getFechaActual());
		ficheroBean.setSobreescribir(false);
		ficheroBean.setNombreDocumento(ConstantesGestorFicheros.NOMBRE_ERRORES_TASAS + idSession);
		ficheroBean.setFichero(new File(""));

		try {
			ficheroErrores = gestorDocumentos.guardarFichero(ficheroBean);
			// A partir del objeto File creamos el fichero físicamente
			ficheroErrores.createNewFile();
			ficheroErrores.setReadable(true);
			ficheroErrores.setWritable(true);
		} catch (OegamExcepcion e1) {
			log.error("Error al guardar el fichero");
		} catch (IOException e) {
			log.error("Error al crear el fichero del resumen de la importacion de tasas", e);
		}

		List<String> fallos = new ArrayList<String>();

		try {

			Tasa t = null;

			BufferedReader input = new BufferedReader(new InputStreamReader(new FileInputStream(fichero)));
			BufferedWriter escribirErrores = new BufferedWriter(new FileWriter(ficheroErrores));

			String line = null;
			int indice = 0;
			int numLineasCabecera = 0;
			String fechaVenta = "";
			log.trace("Leyendo líneas del fichero");
			
			List<GeneracionEtiquetasBean> listaGeneEtiquetasBean = tasaDePegatina ? new ArrayList<GeneracionEtiquetasBean>() : null;
			while ((line = input.readLine()) != null) {
				t = new Tasa();
				if (++indice == 1) {
					// Estamos en la linea de cabecera
					if (line.length() == 42) {
						fechaVenta = line.substring(12, 20);
						numLineasCabecera = Integer.parseInt(line.substring(39, 42).trim());
						escribirErrores.write(line + "\r\n"); // Escribimos la linea de cabecera en el fichero de errores, por si hay algún error.
					} else {
						escribirErrores.write(line + "\r\n");
						fallos.add("- Línea " + indice + ": La línea no tiene el formato correcto");
					}
				} else {
					// Resto de lineas
					if (line.length() == 29) {
						t.setCodigoTasa(line.substring(0, 12));
						int codigo1 = line.substring(12, 14) != null && !line.substring(12, 14).trim().equals("") ? Integer.parseInt(line.substring(12, 14).trim()) : -1;
						int codigo2 = line.substring(14, 16) != null && !line.substring(14, 16).trim().equals("") ? Integer.parseInt(line.substring(14, 16).trim()) : -1;
						t.setTipoTasa(codigo1 + "." + codigo2);
						DecimalFormat formateador = new DecimalFormat("######.##");
						Number precio = null;
						try {
							precio = formateador.parse(line.substring(20, 26).trim() + "," + line.substring(27, 29).trim());
						} catch (ParseException pe) {
							escribirErrores.write(line + "\r\n");
							fallos.add("- Línea " + indice + ": Error al recuperar el precio de la tasa");
						}
						t.setPrecio(BigDecimal.valueOf(precio.doubleValue()));
						SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
						Date fechaParseada = null;
						try {
							fechaParseada = format.parse(fechaVenta);
						} catch (ParseException pe) {
							escribirErrores.write(line + "\r\n");
							fallos.add("- Línea " + indice + ": Error al recuperar la fecha de venta");
						}

						t.setFechaAlta(fechaParseada);
						t.setFechaAsignacion(null);
						t.setFechaFinVigencia(null);
						t.setIdContrato(contrato.intValue());
						t.setIdUsuario(idUsuario.intValue());
						if(tienePermisoColegio && tienePermisosAdmin){
							t.setImportadoIcogam(Decision.Si.getValorEnum());
						}else{
							t.setImportadoIcogam(Decision.No.getValorEnum());
						}
						RespuestaTasas respuesta = null;

						if (t.getCodigoTasa() == null || t.getCodigoTasa().isEmpty()) {
							respuesta = new RespuestaTasas(true, "No se informa el codigo de la tasa");
						} else if (t.getIdContrato() == null) {
							respuesta = new RespuestaTasas(true, "Falta el contrato al que se asigna la tasa");
						} else if (t.getPrecio() == null) {
							respuesta = new RespuestaTasas(true, "Falta el precio de la tasa");
						} else if (t.getIdUsuario() == null) {
							respuesta = new RespuestaTasas(true, "No consta el usuario que está dando de alta la tasa");
						} else if (t.getTipoTasa() == null || t.getTipoTasa().isEmpty()) {
							respuesta = new RespuestaTasas(true, "Falta el tipo de la tasa");
						} else {
							TasaDto detalle = servicioTasa.getDetalleTasaCargada(t.getCodigoTasa(), null);
							if (detalle != null) {
								// Ya existe la tasa
								respuesta = new RespuestaTasas(true, "La tasa ya existe no se puede volver a cargar");
								// Meter mas errores
								if (t.getIdContrato().intValue() != detalle.getContrato().getIdContrato().intValue()) {
									respuesta.setMensajeError("La tasa ya esta dada de alta para otro contrato");
								}
								if (!t.getTipoTasa().equals(detalle.getTipoTasa())) {
									respuesta.setMensajeError("La tasa ya esta dada de alta y es de otro tipo");
								}
							} else if (tasaDePegatina) {
								// Tasa de pegatina
								try {
									respuesta = servicioPersistenciaTasaPegina.guardarTasa(t);
									if (!respuesta.isError()) {
										listaGeneEtiquetasBean.add(servicioGeneracionEtiquetasTasas.generarEtiquetasBean(t));
									}
								} catch (TransactionalException e) {
									log.error("Error al guardar la tasa de pegatina", e);
									respuesta = new RespuestaTasas();
									respuesta.setError(true);
									respuesta.setMensajeError(e.getMessage());
								}
							} else {
								// Tasa electronica
								try {
									respuesta = servicioPersistencia.guardarTasa(t);
								} catch (TransactionalException e) {
									log.error("Error al guardar la tasa", e);
									respuesta = new RespuestaTasas();
									respuesta.setError(true);
									respuesta.setMensajeError(e.getMessage());
								}
							}
						}

						if (respuesta.isError()) {
							escribirErrores.write(line + "\r\n");
							fallos.add("- Línea " + indice + ": " + respuesta.getMensajeError());
							actualizaResumen(resumen, t, false);
						} else {
							actualizaResumen(resumen, t, true);
						}
					} else {
						escribirErrores.write(line + "\r\n");
						fallos.add("- Línea " + indice + ": La línea no tiene el formato correcto");
					}
				}
			}

			if (numLineasCabecera != indice - 1) {
				fallos.add("- No coinciden el número de líneas especificadas en la cabecera del fichero (" + numLineasCabecera + ")" + " con el número de líneas de tasas del fichero (" + (indice - 1)
						+ ")");
			}

			// Cerramos reader y writer
			input.close();
			escribirErrores.close();
			String imprimirTasasPegatinas = gestorPropiedades.valorPropertie("nueva.impresion.Tasas.pegatinas");
			if(tasaDePegatina && (listaGeneEtiquetasBean != null && !listaGeneEtiquetasBean.isEmpty()) && tienePermisosAdmin && (imprimirTasasPegatinas != null && imprimirTasasPegatinas.equals("SI"))){
				ResultBean resultado = servicioGeneracionEtiquetasTasas.crearDocumentoSolicitudColaPegatinas(listaGeneEtiquetasBean, contrato, new BigDecimal(idUsuario));
				if(resultado != null && resultado.getError()){
					resultImportTasas.setError(true);
				}else{
					resultImportTasas.addAttachment("fallos", fallos);
					resultImportTasas.addAttachment("resumen", resumen);	
					resultImportTasas.addAttachment("mensajeEncoladoOk", "La solicitud para generar las tasas de pegatinas se ha encolado correctamente.");
				}
			}else{
				resultImportTasas.addAttachment("fallos", fallos);
				resultImportTasas.addAttachment("resumen", resumen);
			}

		} catch (IOException ioe) {
			log.error("Error al crear el fichero de errores");
			resultImportTasas.setError(true);
			fallos.add("- Error desconocido: No se pudo crear el fichero de errores. No se importó ninguna tasa.");
			resultImportTasas.addListaMensajes(fallos);
			log.error(ioe);
		} catch (Throwable e) {
			log.error("Error en la lectura de las lineas del fichero");
			ficheroErrores = fichero;
			fallos.add("- No se pudo importar el fichero. Error en la lectura de las lineas del fichero.");
			log.error(e);
			resultImportTasas.setError(true);
			resultImportTasas.addListaMensajes(fallos);
		}
		return resultImportTasas;
	}

	@Override
	@Transactional(readOnly = true)
	public ResultBean recuperarTasasDesdeFichero(ResumenCompraBean resumenCompraBean, File ficheroTasas) {
		ResultBean resultBean = new ResultBean();
		int numTasas = 0;
		InputStream is = null;
		Reader reader = null;
		BufferedReader input = null;
		try {

			// Recuperar los tipos de tasa existentes
			List<TasaDgtVO> listaTasasDGT = tasasDtgDao.buscar(null);

			is = new FileInputStream(ficheroTasas);
			reader = new InputStreamReader(is);
			input = new BufferedReader(reader);
			String line = null;
			boolean cabecera = true;
			int indice = 0;

			while ((line = input.readLine()) != null) {
				indice++;
				if (cabecera) {
					// Estamos en la linea de cabecera
					if (line.length() == 42) {
						// Autoliquidacion
						resumenCompraBean.setNAutoliquidacion( line.substring(0, 12));
						// Fecha de compra
						resumenCompraBean.setFechaCompra(new SimpleDateFormat("yyyyMMdd").parse(line.substring(12, 20)));
						// Importe total
						resumenCompraBean.setImporteTotalTasas(new BigDecimal(line.substring(29, 39).replace(",", ".")));
						// Numero de tasas compradas
						numTasas = Integer.parseInt(line.substring(39, 42).trim());
						// Cabecera procesada
						cabecera = false;
					} else {
						resultBean.setError(Boolean.TRUE);
						resultBean.addMensajeALista("No se ha podido procesar la cabecera del fichero");
						break;
					}
				} else {
					// Resto de lineas
					if (line.length() == 29) {
						//tipo de tasa
						String tipoTasa = line.substring(12, 14).trim() + "." + line.substring(14, 16).trim();

						if (tipoTasa == null || tipoTasa.isEmpty()) {
							resultBean.addMensajeALista("- Línea " + indice + ": Falta el tipo de la tasa");	
						} else if (incrementarPeticionTasa(listaTasasDGT, tipoTasa, resumenCompraBean)) {
							// Recuperar el resumen de tasas correspondiente a ese codigo
							numTasas--;
						} else {
							resultBean.addMensajeALista("- Línea " + indice + ": Tipo de tasa no reconocido");							
						}
					} else {
						resultBean.addMensajeALista("- Línea " + indice + ": La línea no tiene el formato correcto");
					}
				}
			}
		} catch (FileNotFoundException e) {
			log.error("No se encuentra el fichero de tasas con la compra a importar", e);
		} catch (IOException e) {
			log.error("Error al leer el fichero de tasas con la compra a importar", e);
		} catch (ParseException e) {
			log.error("Error al leer el fichero de tasas con la compra a importar, el formato de la fecha no es el esperado", e);
		} catch (RuntimeException e) {
			log.error("Error inesperado al tratar el fichero de tasas para importar una nueva compra", e);
		} finally {
			try {
				input.close();
			} catch (IOException e) {
				log.error("Error cerrando recurso BufferedReader", e);
			}
			try {
				reader.close();
			} catch (IOException e) {
				log.error("Error cerrando recurso InputStreamReader", e);
			}
			try {
				is.close();
			} catch (IOException e) {
				log.error("Error cerrando recurso FileInputStream", e);
			}
		}

		if (numTasas != 0) {
			resultBean.addMensajeALista("El numero de tasas encontradas no coincide con las indicadas en la cabecera");
		}

		return resultBean;
	}

	private boolean incrementarPeticionTasa(List<TasaDgtVO> listaTasasDGT, String tipoTasa, ResumenCompraBean resumenCompraBean) {
		TasasSolicitadasBean tasaBean = null;
		if (resumenCompraBean.getListaResumenCompraBean() == null) {
			resumenCompraBean.setListaResumenCompraBean(new ArrayList<TasasSolicitadasBean>());
		}
		for (TasasSolicitadasBean t : resumenCompraBean.getListaResumenCompraBean()) {
			if (t.getCodigoTasa().equals(tipoTasa)) {
				tasaBean = t;
				break;
			}
		}
		if (tasaBean == null) {
			for (TasaDgtVO tasaDgtVO : listaTasasDGT) {
				if (tasaDgtVO.getCodigoTasa().equals(tipoTasa)) {
					tasaBean = new TasasSolicitadasBean();
					tasaBean.setCodigoTasa(tasaDgtVO.getCodigoTasa());
					tasaBean.setCantidad(BigDecimal.ONE);
					resumenCompraBean.getListaResumenCompraBean().add(tasaBean);
					break;
				}
			}
		} else {
			tasaBean.setCantidad(tasaBean.getCantidad().add(BigDecimal.ONE));
		}
		
		return tasaBean != null;
	}

	private List<ResumenTasas> actualizaResumen(List<ResumenTasas> resumen, Tasa t, Boolean correcto) {
		if ("1.1".equals(t.getTipoTasa())) {
			if (correcto) {
				resumen.get(0).addCorrecta();
				resumen.get(16).addCorrecta();
			} else {
				resumen.get(0).addIncorrecta();
				resumen.get(16).addIncorrecta();
			}
		} else if ("1.2".equals(t.getTipoTasa())) {
			if (correcto) {
				resumen.get(1).addCorrecta();
				resumen.get(16).addCorrecta();
			} else {
				resumen.get(1).addIncorrecta();
				resumen.get(16).addIncorrecta();
			}
		} else if ("1.3".equals(t.getTipoTasa())) {
			if (correcto) {
				resumen.get(2).addCorrecta();
				resumen.get(16).addCorrecta();
			} else {
				resumen.get(2).addIncorrecta();
				resumen.get(16).addIncorrecta();
			}
		} else if ("1.4".equals(t.getTipoTasa())) {
			if (correcto) {
				resumen.get(3).addCorrecta();
				resumen.get(16).addCorrecta();
			} else {
				resumen.get(3).addIncorrecta();
				resumen.get(16).addIncorrecta();
			}
		} else if ("1.5".equals(t.getTipoTasa())) {
			if (correcto) {
				resumen.get(4).addCorrecta();
				resumen.get(16).addCorrecta();
			} else {
				resumen.get(4).addIncorrecta();
				resumen.get(16).addIncorrecta();
			}
		} else if ("1.6".equals(t.getTipoTasa())) {
			if (correcto) {
				resumen.get(5).addCorrecta();
				resumen.get(16).addCorrecta();
			} else {
				resumen.get(5).addIncorrecta();
				resumen.get(16).addIncorrecta();
			}
		} else if ("2.3".equals(t.getTipoTasa())) {
			if (correcto) {
				resumen.get(6).addCorrecta();
				resumen.get(16).addCorrecta();
			} else {
				resumen.get(6).addIncorrecta();
				resumen.get(16).addIncorrecta();
			}
		} else if ("4.1".equals(t.getTipoTasa())) {
			if (correcto) {
				resumen.get(7).addCorrecta();
				resumen.get(16).addCorrecta();
			} else {
				resumen.get(7).addIncorrecta();
				resumen.get(16).addIncorrecta();
			}
		} else if ("4.2".equals(t.getTipoTasa())) {
			if (correcto) {
				resumen.get(8).addCorrecta();
				resumen.get(16).addCorrecta();
			} else {
				resumen.get(8).addIncorrecta();
				resumen.get(16).addIncorrecta();
			}
		} else if ("4.3".equals(t.getTipoTasa())) {
			if (correcto) {
				resumen.get(9).addCorrecta();
				resumen.get(16).addCorrecta();
			} else {
				resumen.get(9).addIncorrecta();
				resumen.get(16).addIncorrecta();
			}
		} else if ("4.31".equals(t.getTipoTasa())) {
			if (correcto) {
				resumen.get(10).addCorrecta();
				resumen.get(16).addCorrecta();
			} else {
				resumen.get(10).addIncorrecta();
				resumen.get(16).addIncorrecta();
			}
		} else if ("4.32".equals(t.getTipoTasa())) {
			if (correcto) {
				resumen.get(11).addCorrecta();
				resumen.get(16).addCorrecta();
			} else {
				resumen.get(11).addIncorrecta();
				resumen.get(16).addIncorrecta();
			}
		} else if ("4.33".equals(t.getTipoTasa())) {
			if (correcto) {
				resumen.get(12).addCorrecta();
				resumen.get(16).addCorrecta();
			} else {
				resumen.get(12).addIncorrecta();
				resumen.get(16).addIncorrecta();
			}
		} else if ("4.34".equals(t.getTipoTasa())) {
			if (correcto) {
				resumen.get(13).addCorrecta();
				resumen.get(16).addCorrecta();
			} else {
				resumen.get(13).addIncorrecta();
				resumen.get(16).addIncorrecta();
			}
		} else if ("4.4".equals(t.getTipoTasa())) {
			if (correcto) {
				resumen.get(14).addCorrecta();
				resumen.get(16).addCorrecta();
			} else {
				resumen.get(14).addIncorrecta();
				resumen.get(16).addIncorrecta();
			}
		} else if ("4.5".equals(t.getTipoTasa())) {
			if (correcto) {
				resumen.get(15).addCorrecta();
				resumen.get(16).addCorrecta();
			} else {
				resumen.get(15).addIncorrecta();
				resumen.get(16).addIncorrecta();
			}
		}
		return resumen;
	}

	public ServicioPersistenciaTasaPegatina getServicioPersistenciaTasaPegina() {
		return servicioPersistenciaTasaPegina;
	}

	public void setServicioPersistenciaTasaPegina(ServicioPersistenciaTasaPegatina servicioPersistenciaTasaPegina) {
		this.servicioPersistenciaTasaPegina = servicioPersistenciaTasaPegina;
	}

	public ServicioPersistenciaTasa getServicioPersistencia() {
		return servicioPersistencia;
	}

	public void setServicioPersistencia(ServicioPersistenciaTasa servicioPersistencia) {
		this.servicioPersistencia = servicioPersistencia;
	}

	public ServicioGeneracionEtiquetasTasas getServicioGeneracionEtiquetasTasas() {
		return servicioGeneracionEtiquetasTasas;
	}

	public void setServicioGeneracionEtiquetasTasas(
			ServicioGeneracionEtiquetasTasas servicioGeneracionEtiquetasTasas) {
		this.servicioGeneracionEtiquetasTasas = servicioGeneracionEtiquetasTasas;
	}
	
}
