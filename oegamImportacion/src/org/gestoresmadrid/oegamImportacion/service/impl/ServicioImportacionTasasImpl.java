package org.gestoresmadrid.oegamImportacion.service.impl;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.apache.commons.io.FilenameUtils;
import org.gestoresmadrid.core.contrato.model.vo.ContratoVO;
import org.gestoresmadrid.core.general.model.vo.UsuarioVO;
import org.gestoresmadrid.core.modelos.model.enumerados.Decision;
import org.gestoresmadrid.core.tasas.model.dao.TasaDao;
import org.gestoresmadrid.core.tasas.model.dao.TasaPegatinaDao;
import org.gestoresmadrid.core.tasas.model.vo.TasaPegatinaVO;
import org.gestoresmadrid.core.tasas.model.vo.TasaVO;
import org.gestoresmadrid.oegamConversiones.conversion.Conversion;
import org.gestoresmadrid.oegamImportacion.bean.ImportarTramiteBean;
import org.gestoresmadrid.oegamImportacion.bean.ResultadoImportacionBean;
import org.gestoresmadrid.oegamImportacion.service.ServicioImportacionTasas;
import org.gestoresmadrid.oegamImportacion.tasa.bean.TasaImportacionBean;
import org.gestoresmadrid.oegamImportacion.tasa.service.ServicioEvolucionTasaImportacion;
import org.gestoresmadrid.oegamImportacion.tasa.service.ServicioTasaImportacion;
import org.gestoresmadrid.utilidades.components.GestorPropiedades;
import org.gestoresmadrid.utilidades.components.UtilesFecha;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import es.globaltms.gestorDocumentos.interfaz.GestorDocumentos;
import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;

@Service
public class ServicioImportacionTasasImpl implements ServicioImportacionTasas {

	private static final long serialVersionUID = -4762261346149731940L;

	private static final ILoggerOegam log = LoggerOegam.getLogger(ServicioImportacionTasasImpl.class);

	@Autowired
	ServicioEvolucionTasaImportacion evolucionTasaImportacion;

	@Autowired
	ServicioTasaImportacion servicioTasa;

	@Autowired
	UtilesFecha utilesFecha;

	@Autowired
	GestorDocumentos gestorDocumentos;

	@Autowired
	Conversion conversion;

	@Autowired
	GestorPropiedades gestorPropiedades;

	@Autowired
	TasaDao tasaDao;

	@Autowired
	TasaPegatinaDao tasaPegatinaDao;

	@Override
	public ResultadoImportacionBean obtenerDatosFichero(File fichero, String nombreFichero, ImportarTramiteBean importarTramiteBean, ContratoVO contrato, Long usuario, Boolean esAdmin) {
		ResultadoImportacionBean resultado = new ResultadoImportacionBean(Boolean.FALSE);
		int posicion = 0;
		try {
			resultado = validarFichero(fichero, nombreFichero);
			if (!resultado.getError()) {
				TasaImportacionBean tasaBean = null;
				@SuppressWarnings("resource")
				BufferedReader input = new BufferedReader(new InputStreamReader(new FileInputStream(fichero), "ISO-8859-1"));
				String line = null;
				int indice = 0;
				int numLineasCabecera = 0;
				String fechaVenta = "";
				while (( line = input.readLine()) != null) {
					tasaBean = new TasaImportacionBean();
					if (++indice == 1) {
						if (line.length() == 42) {
							fechaVenta = line.substring(12, 20);
							numLineasCabecera = Integer.parseInt(line.substring(39, 42).trim());
							log.error("- Línea " + line);
						} else {
							resultado.setError(Boolean.TRUE);
							resultado.addListaMensaje("- Línea " + indice + ": La línea no tiene el formato correcto");
						}
					} else {
						if (line.length() == 29) {
							tasaBean.setCodigoTasa(line.substring(0, 12));
							int codigo1 = line.substring(12, 14) != null && !line.substring(12, 14).trim().equals("") ? Integer.parseInt(line.substring(12, 14).trim()) : -1;
							int codigo2 = line.substring(14, 16) != null && !line.substring(14, 16).trim().equals("") ? Integer.parseInt(line.substring(14, 16).trim()) : -1;
							tasaBean.setTipoTasa(codigo1 + "." + codigo2);
							DecimalFormat formateador = new DecimalFormat("######.##");
							Number precio = null;
							try {
								precio = formateador.parse(line.substring(20, 26).trim() + "," + line.substring(27, 29).trim());
							} catch (ParseException pe) {
								log.error("- Línea " + line + ":Error al recuperar el precio de la tasa, error: ", pe);
								resultado.setError(Boolean.TRUE);
								resultado.addListaMensaje("- Línea " + indice + ": Error al recuperar el precio de la tasa");
							}
							tasaBean.setPrecio(BigDecimal.valueOf(precio.doubleValue()));
							SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
							Date fechaParseada = null;
							try {
								fechaParseada = format.parse(fechaVenta);
							} catch (ParseException pe) {
								log.error("- Línea " + line + ":Error al recuperar la fecha de venta, error: ", pe);
								resultado.setError(Boolean.TRUE);
								resultado.addListaMensaje("- Línea " + indice + ": Error al recuperar la fecha de venta");
							}

							tasaBean.setFechaAlta(fechaParseada);
							tasaBean.setFechaAsignacion(null);
							tasaBean.setFechaFinVigencia(null);
							tasaBean.setIdContrato(contrato.getIdContrato().intValue());
							tasaBean.setIdUsuario(usuario.intValue());
							if (esAdmin) {
								tasaBean.setImportadoIcogam(Decision.Si.getValorEnum());
							} else {
								tasaBean.setImportadoIcogam(Decision.No.getValorEnum());
							}
							tasaBean.setFormatoTasa(importarTramiteBean.getFormatoTasa());
							ResultadoImportacionBean resultValid = validacionDatosImportacion(tasaBean);
							if (!resultValid.getError()) {
								resultado.addListaTasasBean(tasaBean, posicion);
								posicion++;
							} else {
								resultado.setError(Boolean.TRUE);
								resultado.addListaMensaje(resultValid.getMensaje());
							}
						} else {
							resultado.addListaMensaje("- Línea " + indice + ": La línea no tiene el formato correcto");
						}
					}
				}
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de obtener los datos del fichero, error: ", e);
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("Ha sucedido un error a la hora de obtener los datos del fichero.");
		}
		return resultado;
	}

	private ResultadoImportacionBean validacionDatosImportacion(TasaImportacionBean tasaBean) {
		ResultadoImportacionBean resultado = new ResultadoImportacionBean(Boolean.FALSE);
		if (tasaBean == null) {
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("No hay datos para importar.");
		} else {
			if (tasaBean.getCodigoTasa() == null || tasaBean.getCodigoTasa().isEmpty()) {
				resultado.setError(Boolean.TRUE);
				resultado.setMensaje("No se informa el codigo de la tasa");
			} else if (tasaBean.getIdContrato() == null) {
				resultado.setError(Boolean.TRUE);
				resultado.setMensaje("Falta el contrato al que se asigna la tasa");
			} else if (tasaBean.getPrecio() == null) {
				resultado.setError(Boolean.TRUE);
				resultado.setMensaje("Falta el precio de la tasa");
			} else if (tasaBean.getIdUsuario() == null) {
				resultado.setError(Boolean.TRUE);
				resultado.setMensaje("No consta el usuario que está dando de alta la tasa");
			} else if (tasaBean.getTipoTasa() == null || tasaBean.getTipoTasa().isEmpty()) {
				resultado.setError(Boolean.TRUE);
				resultado.setMensaje("Falta el tipo de la tasa");
			} else {
				TasaVO tasa = servicioTasa.getTasa(tasaBean.getCodigoTasa(), tasaBean.getTipoTasa());
				if (tasa == null) {
					TasaPegatinaVO tasaPegatinaVO = servicioTasa.getTasaPegatina(tasaBean.getCodigoTasa(), tasaBean.getTipoTasa());
					if (tasaPegatinaVO != null) {
						resultado.setError(Boolean.TRUE);
						resultado.setMensaje("La tasa " +tasaBean.getCodigoTasa()+ " ya existe en el sistema como tasa pegatina y no se puede volver a importar");
					}
				} else {
					resultado.setError(Boolean.TRUE);
					resultado.setMensaje("La tasa " +tasaBean.getCodigoTasa()+ " ya existe en el sistema como tasa electrónica y no se puede volver a importar");
				}
			}
		}
		return resultado;
	}

	private ResultadoImportacionBean validarFichero(File fichero, String nombreFichero) {
		ResultadoImportacionBean resultado = new ResultadoImportacionBean(Boolean.FALSE);
		if (fichero == null || nombreFichero == null || nombreFichero.isEmpty()) {
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("Debe de seleccionar un fichero para importar.");
		} else {
			String ext = FilenameUtils.getExtension(nombreFichero);
			if (!"txt".equals(ext)){
				resultado.setError(Boolean.TRUE);
				resultado.setMensaje("Solo se pueden importar fichero con extensión .txt");
			}
		}
		return resultado;
	}

	@Override
	@Transactional
	public ResultadoImportacionBean importarTasasElectronica(TasaImportacionBean tasaBean, ContratoVO contratoVO, Long idUsuario, Boolean esGestoria) {
		ResultadoImportacionBean resultado = new ResultadoImportacionBean(Boolean.FALSE);
		try {
			TasaVO tasaVO = new TasaVO();
			tasaVO = conversion.transform(tasaBean, TasaVO.class);
			tasaVO.setCodigoTasa(tasaBean.getCodigoTasa());
			tasaVO.setTipoTasa(tasaBean.getTipoTasa());
			tasaVO.setContrato(contratoVO);
			tasaVO.getContrato().setIdContrato(tasaBean.getIdContrato().longValue());
			tasaVO.setUsuario(new UsuarioVO());
			tasaVO.getUsuario().setIdUsuario(idUsuario);
			tasaVO.setFechaAlta(tasaBean.getFechaAlta());
			tasaVO.setPrecio(tasaBean.getPrecio());
			tasaVO.setImportadoIcogam(new BigDecimal(tasaBean.getImportadoIcogam()));
			Calendar fechaFinVigencia = Calendar.getInstance();
			fechaFinVigencia.set(Calendar.MONTH, Calendar.DECEMBER);
			fechaFinVigencia.set(Calendar.DAY_OF_MONTH, 31);
			tasaVO.setFechaFinVigencia(fechaFinVigencia.getTime());
			tasaDao.guardar(tasaVO);
			guardarEvolucionTasa(tasaVO.getCodigoTasa(), null, idUsuario);
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de obtener los datos del fichero, error: ", e);
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("Ha sucedido un error a la hora de obtener los datos del fichero.");
		}
		if (resultado.getError()) {
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		}
		return resultado;
	}

	@Override
	@Transactional
	public ResultadoImportacionBean importarTasasPegatinaTasa(TasaImportacionBean tasaBean, ContratoVO contratoVO, Long idUsuario, Boolean esGestoria) {
		ResultadoImportacionBean resultado = new ResultadoImportacionBean(Boolean.FALSE);
		try {
			TasaPegatinaVO tasaPegatinaVO = new TasaPegatinaVO();
			tasaPegatinaVO = conversion.transform(tasaBean, TasaPegatinaVO.class);
			tasaPegatinaVO.setCodigoTasa(tasaBean.getCodigoTasa());
			tasaPegatinaVO.setTipoTasa(tasaBean.getTipoTasa());
			tasaPegatinaVO.setContrato(contratoVO);
			tasaPegatinaVO.getContrato().setIdContrato(tasaBean.getIdContrato().longValue());
			tasaPegatinaVO.setUsuario(new UsuarioVO());
			tasaPegatinaVO.getUsuario().setIdUsuario(idUsuario);
			tasaPegatinaVO.setFechaAlta(tasaBean.getFechaAlta());
			tasaPegatinaVO.setPrecio(tasaBean.getPrecio());
			tasaPegatinaVO.setImportadoIcogam(new BigDecimal(tasaBean.getImportadoIcogam()));
			Calendar fechaFinVigencia = Calendar.getInstance();
			fechaFinVigencia.set(Calendar.MONTH, Calendar.DECEMBER);
			fechaFinVigencia.set(Calendar.DAY_OF_MONTH, 31);
			tasaPegatinaVO.setFechaFinVigencia(fechaFinVigencia.getTime());
			tasaPegatinaDao.guardar(tasaPegatinaVO);
			guardarEvolucionTasa(tasaPegatinaVO.getCodigoTasa(), null, idUsuario);
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de obtener los datos del fichero, error: ", e);
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("Ha sucedido un error a la hora de obtener los datos del fichero.");
		}
		if (resultado.getError()) {
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		}
		return resultado;
	}

	private void guardarEvolucionTasa(String codigoTasa, BigDecimal numExpediente, Long idUsuario) {
		try {
			evolucionTasaImportacion.insertarEvolucionTasa(codigoTasa, numExpediente, ServicioEvolucionTasaImportacion.CREAR, idUsuario);
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de guardar la evolucion de la tasa, error: ", e);
		}
	}
}