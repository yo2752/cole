package org.gestoresmadrid.oegam2.controller.action;

import java.io.File;
import java.io.InputStream;
import java.math.BigDecimal;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.annotation.Resource;

import org.apache.struts2.ServletActionContext;
import org.gestoresmadrid.core.model.beans.DatoMaestroBean;
import org.gestoresmadrid.core.pagination.model.component.ModelPagination;
import org.gestoresmadrid.core.tasas.model.enumeration.EstadoCompra;
import org.gestoresmadrid.core.tasas.model.enumeration.FormaPago;
import org.gestoresmadrid.core.tasas.model.enumeration.FormatoTasa;
import org.gestoresmadrid.oegam2.pagination.controller.action.AbstractPaginatedListAction;
import org.gestoresmadrid.oegam2comun.contrato.model.service.ServicioContrato;
import org.gestoresmadrid.oegam2comun.model.service.ServicioPersistenciaEntidadBancaria;
import org.gestoresmadrid.oegam2comun.tasas.model.dto.RespuestaTasas;
import org.gestoresmadrid.oegam2comun.tasas.model.service.ServicioCompraTasas;
import org.gestoresmadrid.oegam2comun.tasas.view.beans.CompraTasasBean;
import org.gestoresmadrid.oegam2comun.tasas.view.beans.ResumenCompraBean;
import org.gestoresmadrid.oegam2comun.tasas.view.beans.ResumenTasas;
import org.gestoresmadrid.oegam2comun.tasas.view.beans.TasasSolicitadasBean;
import org.gestoresmadrid.oegamComun.acceso.service.impl.UtilesColegiado;
import org.gestoresmadrid.utilidades.components.GestorPropiedades;
import org.gestoresmadrid.utilidades.components.UtilesFecha;
import org.springframework.beans.factory.annotation.Autowired;

import es.globaltms.gestorDocumentos.bean.ByteArrayInputStreamBean;
import es.globaltms.gestorDocumentos.enums.FileResultStatus;
import escrituras.beans.ResultBean;
import utilidades.estructuras.FechaFraccionada;
import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;
import utilidades.validaciones.UtilesCuentaBancaria;

/**
 * Controlador para la gestión de compra de tasas de la DGT
 * @author erds
 */
public class GestionCompraTasasAction extends AbstractPaginatedListAction {

	private static final long serialVersionUID = -2101680028479266242L;

	private static final ILoggerOegam LOG = LoggerOegam.getLogger(GestionCompraTasasAction.class);

	// Listado de tasas a comprar seleccionado desde pantalla.
	private TasasSolicitadasBean tasasSolicitadasBean;
	private static final String COLUMDEFECT = "idCompra";
	private CompraTasasBean compraTasasBean;
	private List<CompraTasasBean> listaCompraTasas;

	private List<ResumenCompraBean> listaResumenCompraBean;

	// Constantes retornos
	private static final String CARGA_LISTA_COMPRA_TASAS = "cargaListaCompraTasas";
	private static final String RESUMEN_COMPRA_TASAS = "resumenCompraTasas";
	private static final String POPUP_CAMBIO_ESTADO = "popupCambioEstado";
	private static final String POPUP_IMPORTACION = "popupImportacionTasas";
	private static final String DESCARGA_DOCUMENTO = "descargaDocumento";
	private static final String IMPORTAR_COMPRA = "importarCompra";

	private static final String NOMBRE_ACTION = "CompraTasas";
	private static final String TOKEN_SEPARATOR = ",";

	private List<String> anios;
	private List<DatoMaestroBean> entidadesBancarias;

	private String idCompras;
	private String estado;
	private BigDecimal contratoImportacion;
	private String formatoImportacion;
	private String idContrato;
	private String formato;

	private List<ResumenTasas> listaResumen;

	private ResumenCompraBean resumenCompraBean;

	// Descarga ficheros
	private InputStream inputStream;
	private String fileName;
	private String fileSize;

	// Upload para importación de compras
	private File ficheroTasas;
	private File ficheroJustificante;

	@Resource(name = "modeloCompraTasasPaginated")
	private ModelPagination modeloCompraTasasPaginated;

	@Autowired
	private ServicioCompraTasas servicioCompraTasas;

	@Autowired
	private ServicioPersistenciaEntidadBancaria servicioPersistenciaEntidadBancaria;

	@Autowired
	private ServicioContrato servicioContrato;

	@Autowired
	private GestorPropiedades gestorPropiedades;

	@Autowired
	UtilesFecha utilesFecha;

	@Autowired
	UtilesColegiado utilesColegiado;

	/* ******************************************************* */
	/* INICIO MÉTODOS DE NAVEGACIÓN */
	/* ******************************************************* */

	/**
	 * Cuando el usuario pinche en el botón realizar compra, se pasarán los datos introducidos por pantalla, se tratarán y después se enviarán a la pantalla de resumen para confirmar la compra.
	 * @return
	 */
	public String realizarCompra() {
		// Actualizar y calcular el importe
		resumenCompraBean = servicioCompraTasas.resumenCompra(resumenCompraBean);

		// Comprobar si los valores son correctos
		ResultBean resultBean = comprobarDatosCompra();
		if (resultBean.getError()) {
			for (String mensaje : resultBean.getListaMensajes()) {
				addActionError(mensaje);
			}
			return SUCCESS;
		}
		return RESUMEN_COMPRA_TASAS;
	}

	/**
	 * Recibe confirmación por parte del usuario para llevar a cabo la compra. El primer paso es guardar el registro de la compra. El segundo es invocar el método del web service correspondiente.
	 * @return
	 */
	public String confirmarCompra() {
		// Primer paso. Guardar el pedido
		guardarPedido();

		// Segundo paso. Crear una solicitud en la cola.
		if (resumenCompraBean.getIdCompra() != null) {
			return crearSolicitud(resumenCompraBean);
		} else {
			addActionError("Error al guardar, no se realizará compra");
			return RESUMEN_COMPRA_TASAS;
		}
	}

	/**
	 * Guardar el registro de la compra, sin tratar de tramitarla.
	 * @return
	 */
	public String guardarCompraSinComprar() {
		if (guardarPedido()) {
			addActionMessage("El pedido se ha guardado correctamente, sin tramitar la compra");
		} else {
			addActionError("Error al guardar, no se realizará compra");
		}
		return RESUMEN_COMPRA_TASAS;
	}

	/**
	 * Crear una nueva compra desde la pantalla de listado de compras
	 */
	public String nueva() {
		resumenCompraBean = new ResumenCompraBean();
		resumenCompraBean.setFormaPago(Integer.toString(FormaPago.CCC.getCodigo()));
		cargarListadoDeTasas();
		return SUCCESS;
	}

	/**
	 * Crear una nueva compra desde la pantalla de listado de compras
	 */
	public String modificar() {
		cargarListadoDeTasas();
		return SUCCESS;
	}

	/**
	 * Carga el popup para realizar el cambio de estado
	 * @return
	 */
	public String cargarPopUp() {
		return POPUP_CAMBIO_ESTADO;
	}

	/**
	 * Carga el popup para realizar la importación de tasas
	 * @return
	 */
	public String cargarPopUpImportacion() {
		if (formatoImportacion == null || formatoImportacion.isEmpty()) {
			formatoImportacion = Integer.toString(FormatoTasa.ELECTRONICO.getCodigo());
		}
		return POPUP_IMPORTACION;
	}

	/**
	 * Realiza el cambio de estado de las compras
	 * @return
	 * @throws Throwable
	 */
	public String cambiarEstados() throws Throwable {
		if (!utilesColegiado.tienePermisoAdmin() && !utilesColegiado.tienePermisoColegio()) {
			addActionMessage("No tiene permisos para ejecutar esta acción");
		} else {
			EstadoCompra nuevoEstado = EstadoCompra.convertir(estado);
			if (nuevoEstado == null) {
				addActionError("Estado no reconocido");
			} else {
				for (String idCompra : idCompras.split(TOKEN_SEPARATOR)) {
					if (servicioCompraTasas.actualizarEstado(nuevoEstado, new Long(idCompra), null)) {
						addActionMessage("Estado de la compra " + idCompra + " correctamente actualizado.");
					} else {
						addActionError("No se pudo cambiar el estado de la compra " + idCompra + ".");
					}
				}
			}
		}
		return navegar();
	}

	/**
	 * Descarga el fichero de tasas guardado en TDOC
	 * @return
	 */
	public String descargarFichero() {
		ByteArrayInputStreamBean byteArrayInputStreamBean = null;
		if (idCompras != null && !idCompras.isEmpty()) {
			String[] idsCompras = idCompras.split(TOKEN_SEPARATOR);
			byteArrayInputStreamBean = servicioCompraTasas.descargarFicheroTasas(idsCompras, getIdContratoSiNoAdministrador());
		}
		return descargaDocumento(byteArrayInputStreamBean, false);
	}

	/**
	 * Descarga el fichero de tasas guardado en TDOC
	 * @return
	 */
	public String descargarFicheroDetalle() {
		ByteArrayInputStreamBean byteArrayInputStreamBean = null;
		if (resumenCompraBean!= null && resumenCompraBean.getIdCompra()!=null) {
			String[] idsCompras = resumenCompraBean.getIdCompra().toString().split(TOKEN_SEPARATOR);
			byteArrayInputStreamBean = servicioCompraTasas.descargarFicheroTasas(idsCompras, getIdContratoSiNoAdministrador());
		}
		return descargaDocumento(byteArrayInputStreamBean, true);
	}

	/**
	 * Descarga el PDF del justificante de pago guardado en TDOC
	 * @return
	 */
	public String justificantePago() {
		ByteArrayInputStreamBean byteArrayInputStreamBean = null;
		if (idCompras != null && !idCompras.isEmpty()) {
			String[] idsCompras = idCompras.split(TOKEN_SEPARATOR);
			byteArrayInputStreamBean = servicioCompraTasas.descargarJustificanteTasas(idsCompras, getIdContratoSiNoAdministrador());
		}
		return descargaDocumento(byteArrayInputStreamBean, false);
	}

	/**
	 * Descarga el PDF del justificante de pago guardado en TDOC
	 * @return
	 */
	public String justificantePagoDetalle() {
		ByteArrayInputStreamBean byteArrayInputStreamBean = null;
		if (resumenCompraBean != null && resumenCompraBean.getIdCompra() != null) {
			String[] idsCompras = resumenCompraBean.getIdCompra().toString().split(TOKEN_SEPARATOR);
			byteArrayInputStreamBean = servicioCompraTasas.descargarJustificanteTasas(idsCompras, getIdContratoSiNoAdministrador());
		}
		return descargaDocumento(byteArrayInputStreamBean, true);
	}

	/**
	 * Se llamará desde la pantalla de consulta para mostrar el detalle de una compra seleccionada.
	 * @return
	 */
	public String detalle() {
		if (idCompras != null && idCompras.split(TOKEN_SEPARATOR).length == 1) {
			resumenCompraBean = servicioCompraTasas.detalleCompra(new Long(idCompras), getIdContratoSiNoAdministrador());
			if (resumenCompraBean != null && EstadoCompra.INICIADO.equals(EstadoCompra.convertir(resumenCompraBean.getEstado()))) {
				return modificar();
			} else if (resumenCompraBean != null){
				return RESUMEN_COMPRA_TASAS;
			}
		}
		addActionError("Debe seleccionar una compra");
		return navegar();
	}

	/**
	 * Se llama desde el detalle de una compra para volver al listado
	 * @return
	 */
	public String volver() {
		return super.inicio();
	}

	/**
	 * Método eliminar los trámites que se hayan seleccionado de la pantalla de consultar.
	 * @return
	 * @throws ParseException
	 */
	public String eliminar() throws ParseException {
		if (!utilesColegiado.tienePermisoAdmin() && !utilesColegiado.tienePermisoColegio()) {
			addActionError("No tiene permisos para ejecutar esta acción");
		} else {
			for (String idCompra : idCompras.split(TOKEN_SEPARATOR)) {
				if (servicioCompraTasas.anular(new Long(idCompra), getIdContratoSiNoAdministrador())) {
					addActionMessage("Se ha anulado la compra " + idCompra);
				} else {
					addActionError("No se ha podido anular la compra " + idCompra + ". Compruebe que no se ha realizado el pago");
				}
			}
		}
		return navegar();
	}

	/**
	 * Realiza la importacion automatica del fichero de tasas a un contrato
	 * @return
	 */
	public String importarTasas() {
		doImportacionTasas();
		return navegar();
	}

	public String inicioImportarCompra() {
		return IMPORTAR_COMPRA;
	}

	public String realizarImportarCompra() {
		if (!utilesColegiado.tienePermisoAdmin()) {
			resumenCompraBean.setIdContrato(utilesColegiado.getIdContratoSession());
		}
		if (resumenCompraBean.getIdContrato() == null) {
			addActionError("Debe seleccionar un contrato para la importación");
		} else {
			ResultBean resultBean = servicioCompraTasas.importarCompraTasas(resumenCompraBean, ficheroTasas, ficheroJustificante);
			if (resultBean.getError()) {
				for (String mensaje : resultBean.getListaMensajes()) {
					addActionError(mensaje);
				}
			} else {
				resumenCompraBean = servicioCompraTasas.detalleCompra(resumenCompraBean.getIdCompra(), getIdContratoSiNoAdministrador());
				if (resumenCompraBean != null) {
					addActionMessage("Compra importada correctamente");
					return RESUMEN_COMPRA_TASAS;
				} else {
					addActionError("Ocurrio un error en la importacion de la compra. No se ha podido mostrar el detalle de la misma.");
				}
			}
		}
		return IMPORTAR_COMPRA;
	}

	/* ********************************************************** */
	/* FIN METODOS DE NAVEGACIÓN */
	/* ********************************************************** */

	/* ********************************************************** */
	/* INICIO METODOS AUXILIARES PRIVADOS */
	/* ********************************************************** */

	/**
	 * Guarda un nuevo pedido de compra de tasas o actualiza el existente
	 * @return
	 */
	private boolean guardarPedido() {
		boolean guardado = false;

		// Si está pendiente de envío, no se puedo actualizar
		if (!EstadoCompra.PENDIENTE_WS.equals(EstadoCompra.convertir(resumenCompraBean.getEstado()))) {
			// Comprobar si el tramite esta finalizado OK
			if (!EstadoCompra.FINALIZADO_OK.equals(EstadoCompra.convertir(resumenCompraBean.getEstado()))) {
				// El trámite no está pendiente de envío ni finalizado Ok, se puede actualizar
				FormaPago formaPago = FormaPago.convertir(resumenCompraBean.getFormaPago());
				if (FormaPago.CCC.equals(formaPago)) {
					resumenCompraBean.setDatosBancarios(resumenCompraBean.getEntidad() + resumenCompraBean.getOficina() + resumenCompraBean.getDc() + resumenCompraBean.getNumeroCuentaPago());
				} else if (FormaPago.TARJETA.equals(formaPago)) {
					resumenCompraBean.setDatosBancarios(resumenCompraBean.getTarjetaNum1() + resumenCompraBean.getTarjetaNum2() + resumenCompraBean.getTarjetaNum3()
							+ resumenCompraBean.getTarjetaNum4() + resumenCompraBean.getTarjetaCcv() + resumenCompraBean.getTarjetaMes() + resumenCompraBean.getTarjetaAnio()
							+ resumenCompraBean.getTarjetaEntidad());
				} else if (FormaPago.IBAN.equals(formaPago)) {
					resumenCompraBean.setDatosBancarios(resumenCompraBean.getIban());
				}

				// Es una nueva compra
				if (resumenCompraBean.getIdCompra() == null) {
					// Estado inicial de compra
					resumenCompraBean.setEstado(BigDecimal.ZERO);
					resumenCompraBean.setFechaAlta(Calendar.getInstance().getTime());
					resumenCompraBean.setIdContrato(utilesColegiado.getIdContratoSession());
				}
				Long idCompra = servicioCompraTasas.guardaCompra(resumenCompraBean);
				if (idCompra != null) {
					resumenCompraBean.setIdCompra(idCompra);
					guardado = true;
				}
			} else {
				// Si está finalizado OK, solo se permite actualizar la referencia
				guardado = servicioCompraTasas.actualizarReferenciaPropia(resumenCompraBean.getRefPropia(), resumenCompraBean.getIdCompra());
			}
		}
		return guardado;
	}

	/**
	 * Por motivos de seguridad, cuando no sea usuario admin, se empleara siempre el id de contrato de sesion
	 * @return
	 */
	private Long getIdContratoSiNoAdministrador() {
		Long idContrato = null;
		if (!utilesColegiado.tienePermisoAdmin() && !utilesColegiado.tienePermisoColegio()) {
			idContrato = utilesColegiado.getIdContratoSession();
		}
		return idContrato;
	}

	/**
	 * Comprobar que los datos introducidos para realizar la compra se ajustan a los requeridos.
	 * @param compraTasasBean
	 * @return
	 */
	private ResultBean comprobarDatosCompra() {
		ResultBean resultBean = new ResultBean(false);

		FormaPago formaPago = FormaPago.convertir(resumenCompraBean.getFormaPago());
		if (formaPago == null) {
			resultBean.setError(true);
			resultBean.addMensajeALista("Es obligatorio seleccionar una forma de pago.");
		} else if (FormaPago.CCC.equals(formaPago)) {
			// Forma de pago cuenta bancaria
			if ((resumenCompraBean.getEntidad().length() != 4 || resumenCompraBean.getOficina().length() != 4
					|| resumenCompraBean.getDc().length() != 2
					|| resumenCompraBean.getNumeroCuentaPago().length() != 10)) {
				resultBean.setError(true);
				resultBean.addMensajeALista("El formato de la cuenta bancaria no es correcto. Tienen que ser 20 dígitos.");
			} else if (!UtilesCuentaBancaria.esValidoCCC(resumenCompraBean.getEntidad(), resumenCompraBean.getOficina(), resumenCompraBean.getDc(), resumenCompraBean.getNumeroCuentaPago())) {
				resultBean.setError(true);
				resultBean.addMensajeALista("El formato de la cuenta bancaria no es correcto. Revise el DC.");
			}
		} else if (FormaPago.IBAN.equals(formaPago)) {
			// Forma de pago cuenta IBAN
			if (resumenCompraBean.getIban() == null || resumenCompraBean.getIban().length() != 24) {
				resultBean.setError(true);
				resultBean.addMensajeALista("El formato de la cuenta bancaria no es correcto. Tienen que ser 24 digitos.");
			} else if (!UtilesCuentaBancaria.esValidoIBAN(resumenCompraBean.getDatosBancarios().substring(0, 2), resumenCompraBean.getDatosBancarios().substring(2, 4), resumenCompraBean
					.getDatosBancarios().substring(4, 8), resumenCompraBean.getDatosBancarios().substring(8, 12), resumenCompraBean.getDatosBancarios().substring(12, 14), resumenCompraBean
					.getDatosBancarios().substring(14))) {
				resultBean.setError(true);
				resultBean.addMensajeALista("No es una cuenta IBAN correcta, revisela.");
			}
		} else if (FormaPago.TARJETA.equals(formaPago)) {
			// Forma de pago Tarjeta
			if (resumenCompraBean.getTarjetaEntidad() == null || resumenCompraBean.getTarjetaEntidad().isEmpty()) {
				resultBean.setError(true);
				resultBean.addMensajeALista("Falta informar la entidad emisora de la tarjeta.");
			}
			int length = resumenCompraBean.getTarjetaNum1() != null ? resumenCompraBean.getTarjetaNum1().length() : 0;
			length += resumenCompraBean.getTarjetaNum2() != null ? resumenCompraBean.getTarjetaNum2().length() : 0;
			length += resumenCompraBean.getTarjetaNum3() != null ? resumenCompraBean.getTarjetaNum3().length() : 0;
			length += resumenCompraBean.getTarjetaNum4() != null ? resumenCompraBean.getTarjetaNum4().length() : 0;
			if (length != 16) {
				resultBean.setError(true);
				resultBean.addMensajeALista("El número de la tarjeta no es correcto, reviselo.");
			}
			if (resumenCompraBean.getTarjetaCcv() == null || resumenCompraBean.getTarjetaCcv().length() != 3) {
				resultBean.setError(true);
				resultBean.addMensajeALista("El CCV de la tarjeta no es correcto.");
			}
			if (resumenCompraBean.getTarjetaMes() == null || resumenCompraBean.getTarjetaMes().isEmpty() || resumenCompraBean.getTarjetaAnio() == null || resumenCompraBean.getTarjetaAnio().isEmpty()) {
				resultBean.setError(true);
				resultBean.addMensajeALista("Falta la fecha de vencimiento de la tarjeta.");
			} else {
				Calendar c = Calendar.getInstance();
				if (Integer.toString(c.get(Calendar.YEAR)).equals(resumenCompraBean.getTarjetaAnio()) && c.get(Calendar.MONTH) + 1 > Integer.parseInt(resumenCompraBean.getTarjetaMes())) {
					resultBean.setError(true);
					resultBean.addMensajeALista("La fecha de vencimiento de la tarjeta no es válida.");
				}
			}
		}

		if (resumenCompraBean.getImporteTotalTasas() == null || BigDecimal.ZERO.compareTo(resumenCompraBean.getImporteTotalTasas()) >= 0) {
			resultBean.setError(true);
			resultBean.addMensajeALista("No ha seleccionado ninguna cantidad de tasa para comprar.");
		}

		String maxTasasCompradas = gestorPropiedades.valorPropertie("maximo.numero.tasas.comprar");
		if (maxTasasCompradas != null) {
			if (resumenCompraBean.getListaResumenCompraBean() != null && !resumenCompraBean.getListaResumenCompraBean().isEmpty()) {
				long numTasasCompradas = 0;
				for (TasasSolicitadasBean tasasSolicitadasBean : resumenCompraBean.getListaResumenCompraBean()) {
					numTasasCompradas += tasasSolicitadasBean.getCantidad().longValue();
				}
				if (Long.parseLong(maxTasasCompradas) < numTasasCompradas) {
					resultBean.setError(true);
					resultBean.addMensajeALista("No se pueden comprar mas de " + maxTasasCompradas + " tasas de una vez.");
				}
			}
		} else {
			resultBean.setError(true);
			resultBean.addMensajeALista("No se ha podido comprobar el numero máximo de tasas permitidas por compra.");
		}

		return resultBean;
	}

	/**
	 * Actualiza el listado de tasas disponibles del bean de compra
	 */
	private void cargarListadoDeTasas() {
		ResumenCompraBean resumenCompraNuevo = servicioCompraTasas.iniciarNuevaCompra();
		if (resumenCompraBean == null) {
			resumenCompraBean = resumenCompraNuevo;
		} else {
			resumenCompraBean.setListaCompraTasasCirculacion(resumenCompraNuevo.getListaCompraTasasCirculacion());
			resumenCompraBean.setListaCompraTasasConduccion(resumenCompraNuevo.getListaCompraTasasConduccion());
			resumenCompraBean.setListaCompraTasasFormacionReconocimiento(resumenCompraNuevo.getListaCompraTasasFormacionReconocimiento());
			resumenCompraBean.setListaCompraTasasOtrasTarifas(resumenCompraNuevo.getListaCompraTasasOtrasTarifas());

			if (resumenCompraBean.getListaResumenCompraBean() != null) {
				for (TasasSolicitadasBean tasaSolicitada : resumenCompraBean.getListaResumenCompraBean()) {
					if (tasaSolicitada.getGrupo() != null && tasaSolicitada.getCodigoTasa() != null) {
						switch (tasaSolicitada.getGrupo()) {
							case 1:
								for (TasasSolicitadasBean t : resumenCompraBean.getListaCompraTasasCirculacion()) {
									if (tasaSolicitada.getCodigoTasa().equals(t.getCodigoTasa())) {
										t.setCantidad(tasaSolicitada.getCantidad());
										break;
									}
								}
								break;
							case 2:
								for (TasasSolicitadasBean t : resumenCompraBean.getListaCompraTasasConduccion()) {
									if (tasaSolicitada.getCodigoTasa().equals(t.getCodigoTasa())) {
										t.setCantidad(tasaSolicitada.getCantidad());
										break;
									}
								}
								break;
							case 3:
								for (TasasSolicitadasBean t : resumenCompraBean.getListaCompraTasasFormacionReconocimiento()) {
									if (tasaSolicitada.getCodigoTasa().equals(t.getCodigoTasa())) {
										t.setCantidad(tasaSolicitada.getCantidad());
										break;
									}
								}
								break;
							case 4:
								for (TasasSolicitadasBean t : resumenCompraBean.getListaCompraTasasOtrasTarifas()) {
									if (tasaSolicitada.getCodigoTasa().equals(t.getCodigoTasa())) {
										t.setCantidad(tasaSolicitada.getCantidad());
										break;
									}
								}
								break;
							default:
								break;
						}
					}
				}
			}

		}
	}

	/**
	 * Evalua el retorno despues de una accion de descarga de documentos
	 * @param byteArrayInputStreamBean
	 * @return
	 */
	private String descargaDocumento(ByteArrayInputStreamBean byteArrayInputStreamBean, boolean detalle) {
		if (byteArrayInputStreamBean == null || FileResultStatus.FILE_NOT_FOUND.equals(byteArrayInputStreamBean.getStatus()) || byteArrayInputStreamBean.getByteArrayInputStream() == null) {
			addActionError("No se han encontrado los ficheros solicitados.");
			if (detalle) {
				idCompras = resumenCompraBean.getIdCompra().toString();
				return detalle();
			} else {
				return navegar();
			}
		} else {
			setInputStream(byteArrayInputStreamBean.getByteArrayInputStream());
			setFileName(byteArrayInputStreamBean.getFileName());
			return DESCARGA_DOCUMENTO;
		}
	}

	/**
	 * Crea la solicitud en cola, si dispone de creditos
	 * 
	 * @param resumenCompraBean
	 * @return
	 */
	private String crearSolicitud(ResumenCompraBean resumenCompraBean) {
		ResultBean resultBean = servicioCompraTasas.crearSolicitud(resumenCompraBean, utilesColegiado.getIdUsuarioSessionBigDecimal());

		if (!resultBean.getError()) {
			addActionMessage("Solicitud creada correctamente");
			return super.inicio();
		} else {
			addActionError("Error al crear la solicitud");
			for (String mensaje : resultBean.getListaMensajes()) {
				addActionError(mensaje);
			}
			resumenCompraBean = servicioCompraTasas.detalleCompra(resumenCompraBean.getIdCompra(), null);
			return RESUMEN_COMPRA_TASAS;
		}
	}

	/**
	 * inicia el combo de anios de caducidad de tarjeta de credito/debito
	 */
	private void iniciarAnios() {
		if (anios == null) {
			anios = new ArrayList<String>();
			int year = Calendar.getInstance().get(Calendar.YEAR);
			for (int i = 0; i < 21; i++) {
				anios.add(Integer.toString(year + i));
			}
		}
	}

	/**
	 * Inicia el combo de entidades bancarias emisoras de tarjetas de credito/debito
	 */
	private void iniciarEntidadesBancarias() {
		if (entidadesBancarias == null) {
			entidadesBancarias = servicioPersistenciaEntidadBancaria.listAll();
		}
	}

	/**
	 * Realiza las comprobaciones y transformaciones necesarias para llamar al servicio de importacion de tasas
	 */
	private void doImportacionTasas() {
		// Comprobar identificador de la compra
		Long idCompra = null;
		if (idCompras != null && idCompras.split(TOKEN_SEPARATOR).length == 1) {
			idCompra = new Long(idCompras);
		} else {
			addActionError("Debe seleccionar una compra");
		}

		// Comprobar contrato al que importar
		BigDecimal contrato = null;
		String numColegiado = null;
		if (!utilesColegiado.tienePermisoAdmin() && !utilesColegiado.tienePermisoColegio()) {
			contrato = utilesColegiado.getIdContratoSessionBigDecimal();
			numColegiado = utilesColegiado.getNumColegiadoSession();
		} else if (idContrato != null && !idContrato.isEmpty()) {
			contrato = new BigDecimal(idContrato);
			numColegiado = servicioContrato.getContratoDto(contrato).getColegiadoDto().getNumColegiado();
		} else {
			addActionError("Debe seleccionar un contrato para la importación");
		}

		// Formato de tasas
		FormatoTasa formatoTasa = FormatoTasa.convertir(formato);
		if (formatoTasa == null) {
			addActionError("Debe seleccionar el formato de las tasas");
		}

		if (idCompra != null && contrato != null && formatoTasa != null) {
			String idSession = ServletActionContext.getRequest().getSession().getId();

			RespuestaTasas respuesta = servicioCompraTasas.importarTasas(idCompra, utilesColegiado.getIdUsuarioSession(), contrato, idSession, formatoTasa, numColegiado, utilesColegiado.tienePermisoAdmin(),utilesColegiado.tienePermisoColegio());
			if (respuesta.isError()) {
				addActionError(respuesta.getMensajeError());
				if (respuesta.getMensajesError() != null) {
					for (String s : respuesta.getMensajesError()) {
						addActionError(s);
					}
				}
			} else {
				addActionMessage("Fichero importado correctamente");
			}
			listaResumen = respuesta.getListaResumenTasas();
		}
	}

	/* ************************************************** */
	/* FIN MÉTODOS AUXILIARES PRIVADOS */
	/* ************************************************** */

	/* ************************************************** */
	/* INICIO IMPLEMENTACIÓN BUSCADOR */
	/* ************************************************** */
	@Override
	public ModelPagination getModelo() {
		return modeloCompraTasasPaginated;
	}

	@Override
	protected void cargaRestricciones() {
		if (!utilesColegiado.tienePermisoAdmin()) {
			compraTasasBean.setIdContrato(utilesColegiado.getIdContratoSession());
		}
	}

	@Override
	public String getColumnaPorDefecto() {
		return COLUMDEFECT;
	}

	@Override
	protected String getResultadoPorDefecto() {
		return CARGA_LISTA_COMPRA_TASAS;
	}

	@Override
	protected Object getBeanCriterios() {
		return compraTasasBean;
	}

	@Override
	protected void setBeanCriterios(Object object) {
		this.compraTasasBean = (CompraTasasBean) object;
	}

	@Override
	protected ILoggerOegam getLog() {
		return LOG;
	}

	@Override
	protected void cargarFiltroInicial() {
		compraTasasBean = new CompraTasasBean();
		compraTasasBean.setEstado(null);
		FechaFraccionada fechaAlta = utilesFecha.getFechaFracionadaActual();
		compraTasasBean.setFechaAlta(fechaAlta);
	}

	@Override
	public String getAction() {
		return NOMBRE_ACTION;
	}

	/* ***************************************** */
	/* FIN IMPLEMENTACIÓN BUSCADOR */
	/* ***************************************** */

	/* ***************************************** */
	/* INICIO GETTERS-SETTERS */
	/* ***************************************** */
	public CompraTasasBean getCompraTasasBean() {
		return compraTasasBean;
	}

	public void setCompraTasasBean(CompraTasasBean compraTasasBean) {
		this.compraTasasBean = compraTasasBean;
	}

	public List<CompraTasasBean> getListaCompraTasas() {
		return listaCompraTasas;
	}

	public void setListaCompraTasas(List<CompraTasasBean> listaCompraTasas) {
		this.listaCompraTasas = listaCompraTasas;
	}

	public String getIdCompras() {
		return idCompras;
	}

	public void setIdCompras(String idCompras) {
		this.idCompras = idCompras;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public List<ResumenCompraBean> getListaResumenCompraBean() {
		return listaResumenCompraBean;
	}

	public void setListaResumenCompraBean(List<ResumenCompraBean> listaResumenCompraBean) {
		this.listaResumenCompraBean = listaResumenCompraBean;
	}

	public ResumenCompraBean getResumenCompraBean() {
		return resumenCompraBean;
	}

	public void setResumenCompraBean(ResumenCompraBean resumenCompraBean) {
		this.resumenCompraBean = resumenCompraBean;
	}

	public List<String> getAnios() {
		iniciarAnios();
		return anios;
	}

	public List<DatoMaestroBean> getEntidadesBancarias() {
		iniciarEntidadesBancarias();
		return entidadesBancarias;
	}

	public TasasSolicitadasBean getTasasSolicitadasBean() {
		return tasasSolicitadasBean;
	}

	public void setTasasSolicitadasBean(TasasSolicitadasBean tasasSolicitadasBean) {
		this.tasasSolicitadasBean = tasasSolicitadasBean;
	}

	public BigDecimal getContratoImportacion() {
		return contratoImportacion;
	}

	public void setContratoImportacion(BigDecimal contratoImportacion) {
		this.contratoImportacion = contratoImportacion;
	}

	public String getFormatoImportacion() {
		return formatoImportacion;
	}

	public void setFormatoImportacion(String formatoImportacion) {
		this.formatoImportacion = formatoImportacion;
	}

	public String getIdContrato() {
		return idContrato;
	}

	public void setIdContrato(String idContrato) {
		this.idContrato = idContrato;
	}

	public String getFormato() {
		return formato;
	}

	public void setFormato(String formato) {
		this.formato = formato;
	}

	public List<ResumenTasas> getListaResumen() {
		return listaResumen;
	}

	public void setListaResumen(List<ResumenTasas> listaResumen) {
		this.listaResumen = listaResumen;
	}

	public InputStream getInputStream() {
		return inputStream;
	}

	public void setInputStream(InputStream inputStream) {
		this.inputStream = inputStream;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getFileSize() {
		return fileSize;
	}

	public void setFileSize(String fileSize) {
		this.fileSize = fileSize;
	}

	public File getFicheroTasas() {
		return ficheroTasas;
	}

	public void setFicheroTasas(File ficheroTasas) {
		this.ficheroTasas = ficheroTasas;
	}

	public File getFicheroJustificante() {
		return ficheroJustificante;
	}

	public void setFicheroJustificante(File ficheroJustificante) {
		this.ficheroJustificante = ficheroJustificante;
	}

	/* **************************** */
	/* FIN GETTERS-SETTERS */
	/* **************************** */

}