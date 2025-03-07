package org.gestoresmadrid.oegam2comun.tasas.model.service.impl;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.math.BigDecimal;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.xml.rpc.ServiceException;

import org.gestoresmadrid.core.historicocreditos.model.dao.enumerados.ConceptoCreditoFacturado;
import org.gestoresmadrid.core.model.enumerados.TipoTramiteTrafico;
import org.gestoresmadrid.core.model.exceptions.TransactionalException;
import org.gestoresmadrid.core.tasas.model.enumeration.EstadoCompra;
import org.gestoresmadrid.core.tasas.model.enumeration.FormatoTasa;
import org.gestoresmadrid.core.tasas.model.enumeration.TipoErrorCompraTasas;
import org.gestoresmadrid.oegam2comun.cola.model.service.ServicioCola;
import org.gestoresmadrid.oegam2comun.creditos.model.service.ServicioCredito;
import org.gestoresmadrid.oegam2comun.model.service.ServicioZipeador;
import org.gestoresmadrid.oegam2comun.proceso.enumerados.ProcesosEnum;
import org.gestoresmadrid.oegam2comun.tasas.model.dto.RespuestaTasas;
import org.gestoresmadrid.oegam2comun.tasas.model.service.ServicioCompraTasas;
import org.gestoresmadrid.oegam2comun.tasas.model.service.ServicioFicherosCompraTasas;
import org.gestoresmadrid.oegam2comun.tasas.model.service.ServicioImportacionTasa;
import org.gestoresmadrid.oegam2comun.tasas.model.service.ServicioPersistenciaCompraTasas;
import org.gestoresmadrid.oegam2comun.tasas.model.service.ServicioWebserviceCompraTasas;
import org.gestoresmadrid.oegam2comun.tasas.view.beans.ResumenCompraBean;
import org.gestoresmadrid.oegam2comun.tasas.view.beans.ResumenTasas;
import org.gestoresmadrid.oegam2comun.tasas.view.beans.TasasSolicitadasBean;
import org.gestoresmadrid.oegamComun.acceso.service.impl.UtilesColegiado;
import org.gestoresmadrid.utilidades.components.GestorPropiedades;
import org.gestoresmadrid.utilidades.components.UtilesFecha;
import org.jfree.util.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import es.globaltms.gestorDocumentos.bean.ByteArrayInputStreamBean;
import es.globaltms.gestorDocumentos.bean.FileResultBean;
import es.globaltms.gestorDocumentos.constantes.ConstantesGestorFicheros;
import es.globaltms.gestorDocumentos.enums.FileResultStatus;
import es.globaltms.gestorDocumentos.interfaz.GestorDocumentos;
import es.trafico.servicios.sic.ingresos.productos.webservices.itv.DatosPedidoCompleto;
import es.trafico.servicios.sic.ingresos.productos.webservices.itv.DocumentoJustificantePago;
import es.trafico.servicios.sic.ingresos.productos.webservices.itv.ResultadoOperacionCompraTasas;
import es.trafico.servicios.sic.ingresos.productos.webservices.itv.Tasa;
import escrituras.beans.ResultBean;
import utilidades.ficheros.BorrarFicherosThread;
import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;
import utilidades.web.OegamExcepcion;

/**
 * Implementacion del Servicio de compra de Tasas de DGT
 * @see ServicioPagoTasas
 */
@Service
public class ServicioCompraTasasImpl implements ServicioCompraTasas {

	// Configuracion en properties
	private static final String NOMBRE_HOST_SOLICITUD = "nombreHostSolicitud";
	private static final String SERVICIO_COMPRA_TASAS_DESCARGA_JUSTIFICANTE = "servicio.compra.tasas.descarga.justificante";
	private static final String COMPRA_TASAS_COBRA_CREDITOS = "servicio.compra.tasas.cobra.creditos";
	private static final String MENSAJE_RECIBIDO_DGT = "Recibido de la DGT: ";

	@Autowired
	private ServicioWebserviceCompraTasas servicioWebserviceCompraTasas;

	@Autowired
	private ServicioFicherosCompraTasas servicioFicherosCompraTasas;

	@Autowired
	private ServicioPersistenciaCompraTasas servicioPersistenciaCompraTasas;

	@Autowired
	private ServicioZipeador servicioZipeador;

	@Autowired
	private ServicioImportacionTasa servicioImportacionTasa;

	@Autowired
	private GestorDocumentos gestorDocumentos;

	@Autowired
	private ServicioCredito servicioCredito;

	@Autowired
	private ServicioCola servicioCola;

	@Autowired
	private GestorPropiedades gestorPropiedades;
	
	private static final ILoggerOegam LOG = LoggerOegam.getLogger(ServicioCompraTasasImpl.class);

	@Autowired
	UtilesFecha utilesFecha;

	@Autowired
	private UtilesColegiado utilesColegiado;
	
	@Override
	public ResumenCompraBean resumenCompra(ResumenCompraBean resumenCompraBean) {
		return servicioPersistenciaCompraTasas.resumenCompra(resumenCompraBean);
	}

	@Override
	public Long guardaCompra(ResumenCompraBean resumenCompraBean) {
		Long id = null;
		try {
			id = servicioPersistenciaCompraTasas.guardaCompra(resumenCompraBean);
		} catch (TransactionalException e) {
			LOG.error("Ocurrio un error al guardar en base de datos, por lo que se hizo rollback", e);
		}
		return id;
	}

	@Override
	public boolean actualizarEstado(EstadoCompra estado, Long idCompra, String respuesta) {
		boolean result = false;
		try {
			result = servicioPersistenciaCompraTasas.actualizarEstado(estado, idCompra, respuesta);
		} catch (TransactionalException e) {
			LOG.error("Ocurrio un error al cambiar el estado, por lo que se hizo rollback", e);
		}
		return result;
	}

	@Override
	public boolean actualizarReferenciaPropia(String refPropia, Long idCompra) {
		boolean result = false;
		try {
			result = servicioPersistenciaCompraTasas.actualizarReferenciaPropia(refPropia, idCompra);
		} catch (TransactionalException e) {
			LOG.error("Ocurrio un error al actualizar la referencia propia, por lo que se hizo rollback", e);
		}
		return result;
	}

	@Override
	public boolean anular(Long idCompra, Long idContrato) {
		boolean result = false;
		try {
			result = servicioPersistenciaCompraTasas.anular(idCompra, idContrato);
		} catch (TransactionalException e) {
			LOG.error("Ocurrio un error al anular la compra, por lo que se hizo rollback", e);
		}
		return result;
	}

	@Override
	public RespuestaTasas realizarCompraTasas(Long idCompra) {
		RespuestaTasas respuesta = new RespuestaTasas();
		ResumenCompraBean compra = servicioPersistenciaCompraTasas.getCompra(idCompra, null, true);

		// Comprobar si existe compra
		if (compra == null) {
			respuesta.setError(true);
			respuesta.setMensajeError(TipoErrorCompraTasas.ERROR_COMPRA_INEXISTENTE.getDescripcion());

		} else {

			// Inicializacion del cliente WS
			try {

				// Gestionar la peticion del presupuesto
				gestionarPeticionPresupuesto(respuesta, compra);

				// Gestionar el pago o recuperacion de las tasas
				gestionarOperacionCompra(respuesta, compra);

				// Gestionar la obtencion del justificante de pago
				gestionarObtencionJustificante(respuesta, compra);

			} catch (ServiceException e) {
				Log.error("Error en la conexion al host del WS de compra de tasas", e);
				respuesta.setError(true);
				respuesta.setException(e);
			} catch (RemoteException e) {
				Log.error("Error en la operacion del WS de compra de tasas", e);
				respuesta.setError(true);
				respuesta.setException(e);
			} finally {
				try {
					// Se actualiza la compra
					servicioPersistenciaCompraTasas.guardaCompra(compra);
				} catch (TransactionalException e) {
					Log.error("Error al actualizar el estado de la compra, se descartan los cambios", e);
					respuesta.setError(true);
					respuesta.setException(e);
				}
			}

		}

		return respuesta;
	}

	@Override
	public ResultBean importarCompraTasas(ResumenCompraBean resumenCompraBean, File ficheroTasas, File ficheroJustificante) {
		ResultBean result;
		try {
			result = servicioImportacionTasa.recuperarTasasDesdeFichero(resumenCompraBean, ficheroTasas);
			if (!result.getError()) {
				// Persistir en BBDD
				resumenCompraBean.setEstado(BigDecimal.valueOf(EstadoCompra.FINALIZADO_OK.getCodigo()));
				servicioPersistenciaCompraTasas.guardaCompra(resumenCompraBean);
				if (resumenCompraBean.getIdCompra() != null) {
					// Guardar ficheros
					servicioFicherosCompraTasas.guardarFicheroTasas(resumenCompraBean, ficheroTasas);
					servicioFicherosCompraTasas.guardarJustificantePago(resumenCompraBean, ficheroJustificante);
				} else {
					result.setError(Boolean.TRUE);
					result.addMensajeALista("Ocurrio un error al guardar la compra de tasas");
				}
			}
		} catch (RuntimeException e) {
			LOG.error("Ocurrio un error no controlado en la importacion de compra de tasas", e);
			if (resumenCompraBean.getIdCompra() != null) {
				// La excepcion ha ocurrido despues de guardar la compra
				result = new ResultBean(Boolean.FALSE);
			} else {
				result = new ResultBean(Boolean.TRUE);
			}
			result.addMensajeALista(e.getMessage());
		}
		return result;
	}

	/**
	 * Comprueba si se generion justificante de compra, y en caso contrario llama al servicio de generar presupuesto y actualiza la respuesta
	 * @param respuesta
	 * @param compra
	 * @throws RemoteException
	 * @throws ServiceException
	 */
	private void gestionarPeticionPresupuesto(RespuestaTasas respuesta, ResumenCompraBean compra) throws RemoteException, ServiceException {
		if (compra.getNumJustificante791() == null || compra.getNumJustificante791().isEmpty()) {

			// Llamada al ws de generar presupuesto
			String numeroJustificante = servicioWebserviceCompraTasas.obtenerNumeroJustificante(compra.getIdCompra());
			if (numeroJustificante == null || numeroJustificante.isEmpty()) {
				respuesta.setError(true);
				respuesta.setMensajeError(TipoErrorCompraTasas.ERROR_GENERICO_PRESUPUESTO.getDescripcion());

			} else {
				compra.setNumJustificante791(numeroJustificante);
			}
		}
	}

	/**
	 * Comprueba si se realiza compra o recuperar relacion de tasas
	 * @param respuesta
	 * @param pagoItv
	 * @param compra
	 * @param identificador
	 * @param tasasSolicitadas
	 * @throws RemoteException
	 * @throws ServiceException
	 */
	private void gestionarOperacionCompra(RespuestaTasas respuesta, ResumenCompraBean compra) throws RemoteException, ServiceException {
		if (compra.getNumJustificante791() != null && !compra.getNumJustificante791().isEmpty()) {
			if ((compra.getNAutoliquidacion() == null || compra.getNAutoliquidacion().isEmpty()) && (compra.getNrc() == null || compra.getNrc().isEmpty())) {
				// Si no se tiene numero de autoliquidacion ni NRC es que no se realizo el pago compra
				realizarPagoTasas(respuesta, compra);
			} else {
				// Se tiene num. de autoliquidacion o NRC, por lo que el pago se realizo con anterioridad
				obtenerRelacionTasas(respuesta, compra);
			}
		}
	}

	/**
	 * Llama al servicio de realizar pago, actualizando la respuesta y el bean de compra. Devuelve el array de tasas comprado
	 * @param respuesta - Bean de respuesta del proceso de compra de Tasas de la DGT según el pedido guardado en BBDD
	 * @param compra - Bean que guarda los datos de la compra
	 * @return Listado de tasas recuperado de DGT
	 * @throws RemoteException
	 * @throws ServiceException
	 */
	private void realizarPagoTasas(RespuestaTasas respuesta, ResumenCompraBean compra) throws RemoteException, ServiceException {
		// Se realiza la llamada al pago
		ResultadoOperacionCompraTasas resultadoServicio = servicioWebserviceCompraTasas.realizarPagoTasas(compra);
		if (resultadoServicio != null) {
			if (LOG.isInfoEnabled() && resultadoServicio.getMensajeRespuesta() != null && !resultadoServicio.getMensajeRespuesta().isEmpty()) {
				LOG.info("Resultado obtenido en WS para el pago de la petición de compra con id[" + compra.getIdCompra() + "]: " + resultadoServicio.getMensajeRespuesta());
			}
			if ((resultadoServicio.getNrc()==null || resultadoServicio.getNrc().isEmpty()) &&
					(resultadoServicio.getNumeroAutoliquidacion()==null || resultadoServicio.getNumeroAutoliquidacion().isEmpty())) {
				LOG.error("Ocurrio un error al realizar el pago de tasas. " + resultadoServicio.getMensajeRespuesta());
				respuesta.setError(true);
				respuesta.setMensajeError(MENSAJE_RECIBIDO_DGT + resultadoServicio.getMensajeRespuesta());
			}
			if (resultadoServicio.getFechaDeCompra() != null) {
				compra.setFechaCompra(resultadoServicio.getFechaDeCompra().getTime());
			}
			compra.setNrc(resultadoServicio.getNrc());
			compra.setNAutoliquidacion(resultadoServicio.getNumeroAutoliquidacion());
			compra.setCsv(resultadoServicio.getCsv());
			

			if (!respuesta.isError() && !comprobarTasasCompradas(compra, resultadoServicio.getTasasCompradas())) {
				respuesta.setError(true);
				respuesta.setMensajeError(TipoErrorCompraTasas.ERROR_CANTIDADES_OBTENIDAS.getDescripcion());
			}

			// Guardar el documento si es que viene
			if (resultadoServicio.getTasasCompradas() != null && resultadoServicio.getTasasCompradas().length > 0) {
				servicioFicherosCompraTasas.guardarRelacionTasas(compra, ordenarResultadoCompra(resultadoServicio.getTasasCompradas()), resultadoServicio.getTasasCompradas().length);
			}

		} else {
			LOG.error("Error realizando pago de tasas, no se aprecia excepcion pero no se obtuvo respuesta");
			respuesta.setError(true);
			respuesta.setMensajeError(TipoErrorCompraTasas.ERROR_GENERICO_PAGO.getDescripcion());
		}
	}

	private Map<String, TreeMap<String,Tasa>> ordenarResultadoCompra(Tasa[] tasasCompradas) {
		Map<String, TreeMap<String,Tasa>> mapaTasas = new TreeMap<String, TreeMap<String,Tasa>>();
		for(Tasa tasa : tasasCompradas){
			if(mapaTasas.containsKey(tasa.getTipoTasa())){
				TreeMap<String, Tasa> tasas = (TreeMap<String, Tasa>) mapaTasas.get(tasa.getTipoTasa());
				tasas.put(tasa.getNumeroTasa(), tasa);
			}else{
				TreeMap<String, Tasa> mapTasaAux = new TreeMap<String, Tasa>(); 
				mapTasaAux.put(tasa.getNumeroTasa(), tasa);
				mapaTasas.put(tasa.getTipoTasa(), mapTasaAux);
			}
			
		}
		return mapaTasas;
	}

	/**
	 * Llama al servicio de obtener la relacion de las tasas, actualizando la respuesta y el bean de compra. Devuelve el array de tasas comprado
	 * @param respuesta - Bean de respuesta del proceso de compra de Tasas de la DGT según el pedido guardado en BBDD
	 * @param compra - Bean que guarda los datos de la compra
	 * @return Listado de tasas recuperado de DGT
	 * @throws RemoteException
	 * @throws ServiceException
	 */
	private void obtenerRelacionTasas(RespuestaTasas respuesta, ResumenCompraBean compra) throws RemoteException, ServiceException {
		// Se realiza la llamada al servicio
		DatosPedidoCompleto datosPedido = servicioWebserviceCompraTasas.obtenerDatosPedido(compra.getIdCompra(), compra.getCifContrato(), compra.getNAutoliquidacion(), compra.getNrc(), compra.getImporteTotalTasas()
				.doubleValue());
		if (datosPedido != null) {
			if (compra.getNAutoliquidacion() == null || compra.getNAutoliquidacion().isEmpty()) {
				compra.setNAutoliquidacion(datosPedido.getNumeroAutoliquidacion());
			}
			if (compra.getNrc() == null || compra.getNrc().isEmpty()) {
				compra.setNrc(datosPedido.getNrc());
			}
			compra.setCsv(datosPedido.getCsv());
			
			// Guardar el documento si es que viene
			if (datosPedido.getTasasSolicitadas() != null && datosPedido.getTasasSolicitadas().length > 0) {
				servicioFicherosCompraTasas.guardarRelacionTasas(compra, ordenarResultadoCompra(datosPedido.getTasasSolicitadas()), datosPedido.getTasasSolicitadas().length);
			} else {
				servicioFicherosCompraTasas.guardarFicheroTasas(compra, datosPedido.getDatosJustificante());
			}

			if (!respuesta.isError() && !comprobarTasasCompradas(compra, datosPedido.getTasasSolicitadas())) {
				respuesta.setError(true);
				respuesta.setMensajeError(TipoErrorCompraTasas.ERROR_CANTIDADES_OBTENIDAS.getDescripcion());
			}

		} else {
			LOG.error("Error realizando pago de tasas, no se aprecia excepcion pero no se obtuvo respuesta");
			respuesta.setError(true);
			respuesta.setMensajeError(TipoErrorCompraTasas.ERROR_GENERICO_RELACIONTASAS.getDescripcion());
		}
	}

	/**
	 * Si esta configurado en properties el obtener justificante de pago y tiene numero de autoliquidacion, llama al servicio de obtener el justificante de pago
	 * @param respuesta
	 * @param pagoItv
	 * @param compra
	 * @throws RemoteException
	 * @throws ServiceException
	 */
	private void gestionarObtencionJustificante(RespuestaTasas respuesta, ResumenCompraBean compra) {
		boolean descargaActiva = "SI".equalsIgnoreCase(gestorPropiedades.valorPropertie(SERVICIO_COMPRA_TASAS_DESCARGA_JUSTIFICANTE));
		if (!descargaActiva) {
			LOG.info("No esta configurada la descarga automatica del justificante de pago");
		} else if (compra.getNAutoliquidacion() != null && !compra.getNAutoliquidacion().isEmpty()) {
			try {
				DocumentoJustificantePago documento = servicioWebserviceCompraTasas.obtenerJustificantePago(compra.getIdCompra(), compra.getCifContrato(), compra.getNAutoliquidacion(), compra.getCsv());
				if (documento != null) {
					if (LOG.isInfoEnabled() && documento.getMensajeRespuesta() != null && !documento.getMensajeRespuesta().isEmpty()) {
						LOG.info("Resultado obtenido en WS para la obtencion del justificante de compra de la petición con id[" + compra.getIdCompra() + "]: " + documento.getMensajeRespuesta());
					}
					if (documento.getDocumento() != null) {
						servicioFicherosCompraTasas.guardarJustificantePago(compra.getIdCompra(), documento, utilesFecha.getFechaConDate(compra.getFechaAlta()), compra.getNAutoliquidacion());
					} else {
						LOG.error("Se ha intentado sin exito obtener el justificante de pago de la peticion de compra con id " + compra.getIdCompra());
					}
				} else {
					LOG.error("Se ha intentado sin exito obtener el justificante de pago de la peticion de compra con id " + compra.getIdCompra());
				}
			} catch (RemoteException e) {
				LOG.error("Error obteniendo justificante de pago", e);
			} catch (ServiceException e) {
				LOG.error("Error obteniendo justificante de pago", e);
			}
		}
	}

	/**
	 * @see ServicioCompraTasas#iniciarNuevaCompra()()
	 */
	@Override
	public ResumenCompraBean iniciarNuevaCompra() {
		return servicioPersistenciaCompraTasas.iniciarNuevaCompra();
	}

	@Override
	public ResumenCompraBean detalleCompra(Long idCompra, Long idContrato) {
		return servicioPersistenciaCompraTasas.getCompra(idCompra, idContrato, true);
	}

	/**
	 * Metodo que comprueba si las tasas compradas son las que fueron solicitadas
	 * @param compra
	 * @param tasas
	 * @return
	 */
	private boolean comprobarTasasCompradas(ResumenCompraBean compra, Tasa[] tasas) {
		boolean iguales = true;
		// Se monta un mapa con las cantidades totales compradas
		Map<String, BigDecimal> tasasMap = new HashMap<String, BigDecimal>();
		if (tasas != null) {
			for (Tasa t : tasas) {
				BigDecimal cantidad = tasasMap.get(t.getTipoTasa());
				if (cantidad == null) {
					cantidad = BigDecimal.ONE;
				} else {
					cantidad = cantidad.add(BigDecimal.ONE);
				}
				tasasMap.put(t.getTipoTasa(), cantidad);
			}
		}
		// Se recorre la lista de tasas solicitadas para comprar, y se verifica que se ha obtenido la misma cantidad.
		if (compra != null && compra.getListaResumenCompraBean() != null) {
			for (TasasSolicitadasBean pedido : compra.getListaResumenCompraBean()) {
				// Se quita la tasa comprada del mapa, para verificar al final, que no se han recibido tasas de distinto tipo del solicitado
				BigDecimal cantidadObtenida = tasasMap.remove(pedido.getCodigoTasa());
				cantidadObtenida = cantidadObtenida != null ? cantidadObtenida : BigDecimal.ZERO;
				BigDecimal cantidadPedida = pedido.getCantidad() != null ? pedido.getCantidad() : BigDecimal.ZERO;
				if (!cantidadObtenida.equals(cantidadPedida)) {
					iguales = false;
				}
			}
		}

		// Si el mapa de tasas no esta vacio, es porque se han recibido tasas de tipos distintos al solicitado
		if (!tasasMap.isEmpty()) {
			iguales = false;
		} else {
			// Se borra el mapa para ayudar al GC
			tasasMap.clear();
		}
		return iguales;
	}

	@Override
	public ByteArrayInputStreamBean descargarJustificanteTasas(String[] idsCompras, Long idContrato) {
		return descargarDocumentos(idsCompras, idContrato, ConstantesGestorFicheros.JUSTIFICANTE);
	}

	@Override
	public ByteArrayInputStreamBean descargarFicheroTasas(String[] idsCompras, Long idContrato) {
		return descargarDocumentos(idsCompras, idContrato, ConstantesGestorFicheros.TASAS);
	}

	private ByteArrayInputStreamBean descargarDocumentos(String[] idsCompras, Long idContrato, String tipoDocumento) {
		ByteArrayInputStreamBean result = new ByteArrayInputStreamBean();
		List<File> files = new ArrayList<File>();
		if (idsCompras != null) {
			for (String idCompra : idsCompras) {
				ResumenCompraBean compraBean = servicioPersistenciaCompraTasas.getCompra(Long.valueOf(idCompra), idContrato, false);
				if (compraBean != null) {
					FileResultBean fileResult = null;
					if (ConstantesGestorFicheros.JUSTIFICANTE.equals(tipoDocumento)) {
						fileResult = servicioFicherosCompraTasas.getJustificantePago(compraBean.getIdCompra(), utilesFecha.getFechaConDate(compraBean.getFechaAlta()));
					} else if (ConstantesGestorFicheros.TASAS.equals(tipoDocumento)) {
						fileResult = servicioFicherosCompraTasas.getRelacionTasas(compraBean.getIdCompra(), utilesFecha.getFechaConDate(compraBean.getFechaAlta()));
					}
					if (fileResult != null && fileResult.getFile() != null && fileResult.getFile().exists()) {
						files.add(fileResult.getFile());
					}
				}
			}
		}
		if (files.isEmpty()) {
			result.setStatus(FileResultStatus.FILE_NOT_FOUND);
		} else if (files.size() == 1) {
			try {
				result.setByteArrayInputStream(new FileInputStream(files.get(0)));
				result.setFileName(files.get(0).getName());
				result.setStatus(FileResultStatus.UNIQUE_FILE_FOUND);
			} catch (FileNotFoundException e) {
				LOG.error("Se ha perdido el fichero", e);
				result.setStatus(FileResultStatus.FILE_NOT_FOUND);
			}
		} else {
			byte[] zip = servicioZipeador.zipear(files);
			if (zip != null) {
				result.setFileName(tipoDocumento + ConstantesGestorFicheros.EXTENSION_ZIP);
				result.setByteArrayInputStream(new ByteArrayInputStream(zip));
				result.setStatus(FileResultStatus.UNIQUE_FILE_FOUND);
			} else {
				result.setStatus(FileResultStatus.ERROR);
			}

		}
		return result;
	}

	@SuppressWarnings("unchecked")
	@Override
	public RespuestaTasas importarTasas(Long idCompra, Long idUsuario, BigDecimal contrato, String idSession, FormatoTasa formato, String numColegiado, Boolean isAdmin, Boolean tienePermisoColegio) {
		RespuestaTasas respuesta = new RespuestaTasas();

		// Recuperar compra y fichero de tasas
		ResumenCompraBean compraBean = servicioPersistenciaCompraTasas.getCompra(idCompra, null, false);
		FileResultBean fileResult = null;
		if (compraBean != null) {
			// Mantis 18649. David Sierra. Se controla que el contrato de la tasa sea el mismo que el del usuario
			BigDecimal contratoUsuario = utilesColegiado.getIdContratoSessionBigDecimal();
			if (!contratoUsuario.toString().equals(compraBean.getIdContrato().toString())) {
				respuesta.setError(true);
				respuesta.setMensajeError("El contrato de la tasa comprada no se corresponde con el contrato del usuario");
				return respuesta;
			} else {
				fileResult = servicioFicherosCompraTasas.getRelacionTasas(compraBean.getIdCompra(), utilesFecha.getFechaConDate(compraBean.getFechaAlta()));
			}
			// Fin Mantis
		}

		if (fileResult != null && fileResult.getFile() != null && fileResult.getFile().exists()) {
			// Si existe fichero de tasas, se llama al servicio de importacion
			ResultBean resultadoImportacion = servicioImportacionTasa.importarTasas(fileResult.getFile(), idUsuario, contrato, idSession, formato, numColegiado, isAdmin,tienePermisoColegio);

			// Se procesa la respuesta del servicio
			List<String> mensajesError = (List<String>) resultadoImportacion.getAttachment("fallos");
			respuesta.setListaResumenTasas((List<ResumenTasas>) resultadoImportacion.getAttachment("resumen"));

			if (mensajesError.size() > 0) {
				respuesta.setError(true);
				respuesta.setMensajeError("Fichero importado parcialmente");
				respuesta.setListaMensajes(mensajesError);
			} else {
				try {
					File file = gestorDocumentos.buscarFicheroPorNombreTipo(ConstantesGestorFicheros.IMPORTACION, ConstantesGestorFicheros.TASAS, utilesFecha.getFechaActual(),
							ConstantesGestorFicheros.NOMBRE_ERRORES_TASAS + idSession, ConstantesGestorFicheros.EXTENSION_TXT).getFile();
					if (file != null) {
						BorrarFicherosThread hiloBorrar = new BorrarFicherosThread(file.getAbsolutePath(), 10000);
						hiloBorrar.start();
						LOG.debug("Se lanza el hilo para borrar el fichero creado, pasado 1 segundo.");
					}
				} catch (OegamExcepcion e) {
					LOG.error("Error al obtener la ruta del fichero de errores", e);
				}
			}
		} else {
			// No existe fichero de tasas
			respuesta.setError(true);
			respuesta.setMensajeError("No existe el fichero de tasas seleccionado");
		}
		return respuesta;
	}

	@Override
	@Transactional
	public ResultBean crearSolicitud(ResumenCompraBean resumenCompraBean, BigDecimal idUsuario) {
		ResultBean result = tratarCobrarCredito(resumenCompraBean.getIdCompra().toString(), BigDecimal.valueOf(resumenCompraBean.getIdContrato()), idUsuario);
		if (result == null || !result.getError()) {
			try {
				result = servicioCola.crearSolicitud(ProcesosEnum.COMPRA_TASAS_DGT.getNombreEnum(), null, gestorPropiedades.valorPropertie(NOMBRE_HOST_SOLICITUD), TipoTramiteTrafico.CompraTasas
							.getValorEnum(), resumenCompraBean.getIdCompra().toString(), utilesColegiado.getIdUsuarioSessionBigDecimal(), null, utilesColegiado.getIdContratoSessionBigDecimal());

				actualizarEstado(EstadoCompra.PENDIENTE_WS, resumenCompraBean.getIdCompra(), null);
			} catch (OegamExcepcion e) {
				TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
				LOG.error("No se pudo encolar la solicitud", e);
				result = new ResultBean(true);
				result.addMensajeALista(e.getMensajeError1());
			}
		}
		return result;
	}

	private ResultBean tratarCobrarCredito(String idCompra, BigDecimal idContrato, BigDecimal idUsuario) {
		ResultBean result;
		int creditos = 1;
		if ("SI".equalsIgnoreCase(gestorPropiedades.valorPropertie(COMPRA_TASAS_COBRA_CREDITOS))) {
			// Comprobar si ya se han descontado creditos para esta compra
			creditos = servicioCredito.cantidadCreditosAnotados(idCompra, ConceptoCreditoFacturado.TCT, ConceptoCreditoFacturado.DTCT);
		}
		if (creditos < 1) {
			// No se han descontado creditos, comprobar si dispone de saldo suficiente
			result = servicioCredito.validarCreditos(TipoTramiteTrafico.CompraTasas.getValorEnum(), idContrato, BigDecimal.ONE);
			if (!result.getError()) {
				// Descontar creditos
				result = servicioCredito.descontarCreditos(TipoTramiteTrafico.CompraTasas.getValorEnum(), idContrato, BigDecimal.ONE, idUsuario, ConceptoCreditoFacturado.TCT,
						idCompra);
			}
		} else {
			// Ya se ha cobrado el credito o creditos desactivados
			result = new ResultBean(false);
		}
		return result;
	}

	@Override
	@Transactional
	public void tratarDevolverCredito(Long idCompra, BigDecimal idUsuario) {
		// Comprobar si ya se han descontado creditos para esta compra
		int creditos = 0;
		if ("SI".equalsIgnoreCase(gestorPropiedades.valorPropertie(COMPRA_TASAS_COBRA_CREDITOS))) {
			creditos = servicioCredito.cantidadCreditosAnotados(idCompra.toString(), ConceptoCreditoFacturado.TCT, ConceptoCreditoFacturado.DTCT);
		}
		if (creditos > 0) {
			ResumenCompraBean compra = servicioPersistenciaCompraTasas.getCompra(idCompra, null, false);
			// Si se obtiene NRC o numJustificante, es que la compra se ha realizado
			if ((compra.getNrc() == null || compra.getNrc().isEmpty()) && (compra.getNAutoliquidacion() == null || compra.getNAutoliquidacion().isEmpty())) {
				servicioCredito.devolverCreditos(TipoTramiteTrafico.CompraTasas.getValorEnum(), BigDecimal.valueOf(compra.getIdContrato()), 1, idUsuario, ConceptoCreditoFacturado.DTCT,
						idCompra.toString());
			}
		}
	}

}
