package trafico.acciones;

import java.math.BigDecimal;
import java.util.List;

import org.gestoresmadrid.oegamComun.acceso.service.impl.UtilesColegiado;
import org.springframework.beans.factory.annotation.Autowired;

import com.opensymphony.xwork2.Action;

import escrituras.beans.Persona;
import escrituras.modelo.ModeloPersona;
import general.acciones.ActionBase;
import general.beans.daos.pq_personas2.BeanPQGUARDAR;
import general.beans.utiles.PersonasBeanPQConversion;
import trafico.beans.SolicitudDatosBean;
import trafico.beans.SolicitudVehiculoBean;
import trafico.beans.TramiteTraficoDuplicadoBean;
import trafico.beans.TramiteTraficoMatriculacionBean;
import trafico.beans.TramiteTraficoTransmisionBean;
import trafico.beans.daos.pq_facturacion.BeanFACTURACION;
import trafico.utiles.enumerados.TipoTramiteTrafico;
import trafico.utiles.enumerados.TipoTransferencia;
import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;
import utilidades.mensajes.Claves;

/**
 * @author GLOBALTMS ESPAÑA
 *
 */
public class FacturacionAction extends ActionBase {

	private static final long serialVersionUID = 1L;
	private static final ILoggerOegam log = LoggerOegam.getLogger(FacturacionAction.class);

	private static final String ERROR_FACTURACION_DATOS_TITULAR_DIRECCION = "Faltan datos obligatorios en la Dirección del Titular";
	private static final String ERROR_FACTURACION_DATOS_DIRECCION_FACTURACION = "Faltan datos obligatorios en la Dirección de facturación";

	// T1-Trámite Tráfico Matriculación
	private TramiteTraficoMatriculacionBean traficoTramiteMatriculacionBean;
	// T2-Trámite Tráfico Transmisión
	private TramiteTraficoTransmisionBean tramiteTraficoTransmisionBean;
	// T8-Trámite Duplicado
	private TramiteTraficoDuplicadoBean tramiteTraficoDuplicado;
	// T4-Trámite Solicitud datos
	private SolicitudDatosBean solicitud;

	private Persona personaFac;
	private boolean utilizarTitular;
	private String tipoPersona;

	private String numColegiadoFac;

	@Autowired
	private UtilesColegiado utilesColegiado;

	public String guardarTitular() {
		try {
			TipoTramiteTrafico tipoTramiteTrafico = identificarTramite();

			String matriculaTramite = null;
			String bastidorTramite = null;
			BigDecimal numeroExpediente = null;

			// Recupera el bastidor y la matrícula del trámite realizado
			if (tipoTramiteTrafico == TipoTramiteTrafico.Duplicado) {
				matriculaTramite = tramiteTraficoDuplicado.getTramiteTrafico().getVehiculo().getMatricula();
				bastidorTramite = tramiteTraficoDuplicado.getTramiteTrafico().getVehiculo().getBastidor();
				numeroExpediente = tramiteTraficoDuplicado.getTramiteTrafico().getNumExpediente();
			} else if (tipoTramiteTrafico == TipoTramiteTrafico.Transmision || tipoTramiteTrafico == TipoTramiteTrafico.TransmisionElectronica) {
				matriculaTramite = tramiteTraficoTransmisionBean.getTramiteTraficoBean().getVehiculo().getMatricula();
				bastidorTramite = tramiteTraficoTransmisionBean.getTramiteTraficoBean().getVehiculo().getBastidor();
				numeroExpediente = tramiteTraficoTransmisionBean.getTramiteTraficoBean().getNumExpediente();
			} else if (tipoTramiteTrafico == TipoTramiteTrafico.Matriculacion) {
				matriculaTramite = traficoTramiteMatriculacionBean.getTramiteTraficoBean().getVehiculo().getMatricula();
				bastidorTramite = traficoTramiteMatriculacionBean.getTramiteTraficoBean().getVehiculo().getBastidor();
				numeroExpediente = traficoTramiteMatriculacionBean.getTramiteTraficoBean().getNumExpediente();
			} else if (tipoTramiteTrafico == TipoTramiteTrafico.Solicitud) {
				solicitud.setSolicitudesVehiculos((List<SolicitudVehiculoBean>) getSession().get("listaSolicitud"));
				guardarTitularFacturacionMultiple();
				return tipoTramiteTrafico.getNombreEnum();
			}

			// Comprueba que se ha recuperado el número de expediente y la matrícula y/o el bastidor:
			if (numeroExpediente == null) {
				// No se ha recuperado el número de expediente del trámite:
				addActionError("No se ha recuperado el número de expediente del trámite");
				return tipoTramiteTrafico.getNombreEnum();
			}
			boolean sinMatricula = false;
			boolean sinBastidor = false;
			if (matriculaTramite == null || (matriculaTramite != null && matriculaTramite.equals(""))) {
				sinMatricula = true;
			}
			if (bastidorTramite == null || (bastidorTramite != null && bastidorTramite.equals(""))) {
				sinBastidor = true;
			}
			if (sinMatricula && sinBastidor) {
				addActionError("No se ha recuperado ni la matrícula ni el bastidor");
				addActionError("No se puede guardar el titular de la facturación");
				return tipoTramiteTrafico.getNombreEnum();
			}

			if (utilizarTitular) {
				// Marcado check de utilizar el titular
				if (tipoTramiteTrafico == TipoTramiteTrafico.Duplicado) {
					personaFac = tramiteTraficoDuplicado.getTitular().getPersona();
				} else if (tipoTramiteTrafico == TipoTramiteTrafico.Transmision || tipoTramiteTrafico == TipoTramiteTrafico.TransmisionElectronica){
					if (tramiteTraficoTransmisionBean != null
							&& tramiteTraficoTransmisionBean.getTipoTransferencia() != null
							&& !tramiteTraficoTransmisionBean.getTipoTransferencia().equals(TipoTransferencia.tipo5)) {
						personaFac = tramiteTraficoTransmisionBean.getAdquirienteBean().getPersona();
					} else {
						personaFac = tramiteTraficoTransmisionBean.getPoseedorBean().getPersona();
					}
				} else if (tipoTramiteTrafico == TipoTramiteTrafico.Matriculacion) {
					personaFac = traficoTramiteMatriculacionBean.getTitularBean().getPersona();
				}

				// Mantis 14439. David Sierra
				if (null == personaFac.getDireccion().getMunicipio().getIdMunicipio() ||"-1".equals(personaFac.getDireccion().getMunicipio().getIdMunicipio())) {
					addActionError(ERROR_FACTURACION_DATOS_TITULAR_DIRECCION);
					return tipoTramiteTrafico.getNombreEnum();
				}
				// Fin Mantis
			}

			// Mantis 22856 NullPointerException por idProvincia. Controlamos todos los campos para evitar futuros NullPointer
			if (null == personaFac.getDireccion().getMunicipio().getIdMunicipio() ||"-1".equals(personaFac.getDireccion().getMunicipio().getIdMunicipio())
					|| personaFac.getDireccion().getMunicipio() == null
					|| personaFac.getDireccion().getMunicipio().getProvincia() == null) {
				addActionError(ERROR_FACTURACION_DATOS_DIRECCION_FACTURACION);
				return tipoTramiteTrafico.getNombreEnum();
			}
			// Fin Mantis

			BeanPQGUARDAR beanPQGUARDAR = PersonasBeanPQConversion.convertirPersonaToPQModificar(personaFac);
			beanPQGUARDAR.setP_ID_USUARIO(utilesColegiado.getIdUsuarioSessionBigDecimal());

			beanPQGUARDAR.setP_NUM_COLEGIADO(numColegiadoFac != null && !numColegiadoFac.isEmpty() ? numColegiadoFac : utilesColegiado.getNumColegiadoSession());

			beanPQGUARDAR.execute();
			if (!beanPQGUARDAR.getP_SQLERRM().equalsIgnoreCase(Claves.CLAVE_EJECUCION_CORRECTA_PROCESO)) {
				addActionError(beanPQGUARDAR.getP_SQLERRM());
				log.error(beanPQGUARDAR.getP_SQLERRM());
				return tipoTramiteTrafico.getNombreEnum();
			}

			BeanFACTURACION beanFacturacion = new BeanFACTURACION();
			beanFacturacion.setP_MATRICULA(matriculaTramite);
			beanFacturacion.setP_BASTIDOR(bastidorTramite);
			beanFacturacion.setP_NIF(personaFac.getNif());
			beanFacturacion.setP_TIPO_TRAMITE(tipoTramiteTrafico.getValorEnum());
			beanFacturacion.setP_NUM_EXPEDIENTE(numeroExpediente);

			String tasa = null;
			if (tipoTramiteTrafico == TipoTramiteTrafico.Duplicado) {
				tasa = tramiteTraficoDuplicado.getTramiteTrafico().getTasa().getCodigoTasa();
			} else if (tipoTramiteTrafico == TipoTramiteTrafico.Transmision || tipoTramiteTrafico == TipoTramiteTrafico.TransmisionElectronica) {
				tasa = tramiteTraficoTransmisionBean.getTramiteTraficoBean().getTasa().getCodigoTasa();
			} else if (tipoTramiteTrafico == TipoTramiteTrafico.Matriculacion){
				tasa = traficoTramiteMatriculacionBean.getTramiteTraficoBean().getTasa().getCodigoTasa();
			}
			beanFacturacion.setP_CODIGO_TASA(tasa);

			beanFacturacion.execute();
			if (!beanFacturacion.getP_SQLERRM().equalsIgnoreCase(Claves.CLAVE_EJECUCION_CORRECTA_PROCESO)) {
				String error = beanFacturacion.getP_SQLERRM().substring(0,9);
				if (error.equalsIgnoreCase("ORA-00001")) {
					// Se ha guardado correctamente.
				} else {
					addActionError(beanFacturacion.getP_SQLERRM());
					log.error(beanFacturacion.getP_SQLERRM());
					return tipoTramiteTrafico.getNombreEnum();
				}
			}

			addActionMessage("Datos de facturación guardados.");
			addActionMessage("Genere el certificado desde la consulta de tasas.");

			return tipoTramiteTrafico.getNombreEnum();

		} catch(Exception ex) {
			log.error(ex);
			addActionError(ex.toString());
			return Action.ERROR;
		}
	}

	private TipoTramiteTrafico identificarTramite() throws Exception {
		if (tramiteTraficoDuplicado != null) {
			return TipoTramiteTrafico.Duplicado;
		} else if(traficoTramiteMatriculacionBean != null) {
			return TipoTramiteTrafico.Matriculacion;
		} else if (tramiteTraficoTransmisionBean != null) {
			if (tramiteTraficoTransmisionBean.getTramiteTraficoBean() != null
				&& tramiteTraficoTransmisionBean.getTramiteTraficoBean().getTipoTramite() != null
				&& tramiteTraficoTransmisionBean.getTramiteTraficoBean().getTipoTramite().equals(TipoTramiteTrafico.Transmision)) {
				return TipoTramiteTrafico.Transmision;
			} else {
				return TipoTramiteTrafico.TransmisionElectronica;
			}
		} else if (solicitud != null) {
			return TipoTramiteTrafico.Solicitud;
		} else {
			throw new Exception("Error al obtener el tipo de tramite");
		}
	}

	/**
	 * Método que busca una persona en bbdd por el DNI y devuelve todos los datos del interviniente.
	 * @return cadena identificativa para la vista struts 2
	 */
	public String buscarPersona(){
		try {
			// Establece el tipo de trámite para hacer el return correcto a la vista:
			TipoTramiteTrafico tipoTramiteTrafico = identificarTramite();
			personaFac = ModeloPersona.obtenerDetallePersonaCompleto(personaFac.getNif(),utilesColegiado.getNumColegiadoSession());
			// El XML está mapeado con el nombre del tipo en la enumeración.
			return tipoTramiteTrafico.getNombreEnum();
		} catch(Exception ex) {
			log.error(ex);
			addActionError(ex.toString());
			return Action.ERROR;
		}
	}

	/**
	 * Privado para grabar el titular facturacion de los vehiculos comprendidos en un trámite de solicitud
	 * de información de vehiculos
	 * @return
	 */
	private void guardarTitularFacturacionMultiple() {

		// Bandera para guardar una posible modificación de la persona solo en la primera vuelta del bucle.
		boolean personaGuardada = false;
		// Bandera para marcar que al menos para uno de los códigos de tasa se ha guardado correctamente el titular de la facturación
		boolean titularGuardado = false;

		List<SolicitudVehiculoBean> solicitudes = solicitud.getSolicitudesVehiculos();

		for (SolicitudVehiculoBean iteracion : solicitudes) {
			try {
				// Comprueba que se ha recuperado el número de expediente y la matrícula y/o el bastidor:
				if (iteracion.getNumExpediente() == null) {
					// No se ha recuperado el número de expediente del trámite:
					addActionError("No se ha recuperado el número de expediente del trámite");
					continue;
				}
				boolean sinMatricula = false;
				boolean sinBastidor = false;
				if (iteracion.getVehiculo().getMatricula() == null || (iteracion.getVehiculo().getMatricula() != null
						&& iteracion.getVehiculo().getMatricula().equals(""))) {
					sinMatricula = true;
				}
				if (iteracion.getVehiculo().getBastidor() == null || (iteracion.getVehiculo().getBastidor() != null
						&& iteracion.getVehiculo().getBastidor().equals(""))) {
					sinBastidor = true;
				}
				if (sinMatricula && sinBastidor) {
					addActionError("No se ha recuperado ni la matrícula ni el bastidor");
					addActionError("No se puede guardar el titular de la facturación");
					continue;
				}

				if (!personaGuardada) {
					BeanPQGUARDAR beanPQGUARDAR = PersonasBeanPQConversion.convertirPersonaToPQModificar(personaFac);
					beanPQGUARDAR.setP_ID_USUARIO(utilesColegiado.getIdUsuarioSessionBigDecimal());
					beanPQGUARDAR.setP_NUM_COLEGIADO(utilesColegiado.getNumColegiadoSession());
					beanPQGUARDAR.execute();
					if(!beanPQGUARDAR.getP_SQLERRM().equalsIgnoreCase(Claves.CLAVE_EJECUCION_CORRECTA_PROCESO)){
						addActionError(beanPQGUARDAR.getP_SQLERRM());
						log.error(beanPQGUARDAR.getP_SQLERRM());
						personaGuardada = false;
						continue;
					}
					personaGuardada = true;
				}

				BeanFACTURACION beanFacturacion = new BeanFACTURACION();
				beanFacturacion.setP_MATRICULA(iteracion.getVehiculo().getMatricula());
				beanFacturacion.setP_BASTIDOR(iteracion.getVehiculo().getBastidor());
				beanFacturacion.setP_NIF(personaFac.getNif());
				beanFacturacion.setP_TIPO_TRAMITE(TipoTramiteTrafico.Solicitud.getValorEnum());
				beanFacturacion.setP_NUM_EXPEDIENTE(iteracion.getNumExpediente());

				beanFacturacion.setP_CODIGO_TASA(iteracion.getTasa().getCodigoTasa());

				beanFacturacion.execute();
				if (!beanFacturacion.getP_SQLERRM().equalsIgnoreCase(Claves.CLAVE_EJECUCION_CORRECTA_PROCESO)) {
					String error = beanFacturacion.getP_SQLERRM().substring(0,9);
					if (error.equalsIgnoreCase("ORA-00001")) {
						// Se ha guardado correctamente.
					} else {
						addActionError(beanFacturacion.getP_SQLERRM());
						log.error(beanFacturacion.getP_SQLERRM());
						continue;
					}
				}

				addActionMessage("Datos de facturación guardados para la tasa " + iteracion.getTasa().getCodigoTasa());
				titularGuardado = true;

			} catch (Exception ex) {
				log.error(ex);
				addActionError(ex.toString());
			}
		}
		if (titularGuardado) {
			addActionMessage("Genere el certificado desde la consulta de tasas.");
		}
	}

	public TramiteTraficoMatriculacionBean getTraficoTramiteMatriculacionBean() {
		return traficoTramiteMatriculacionBean;
	}

	public void setTraficoTramiteMatriculacionBean(
			TramiteTraficoMatriculacionBean traficoTramiteMatriculacionBean) {
		this.traficoTramiteMatriculacionBean = traficoTramiteMatriculacionBean;
	}

	public TramiteTraficoTransmisionBean getTramiteTraficoTransmisionBean() {
		return tramiteTraficoTransmisionBean;
	}

	public void setTramiteTraficoTransmisionBean(
			TramiteTraficoTransmisionBean tramiteTraficoTransmisionBean) {
		this.tramiteTraficoTransmisionBean = tramiteTraficoTransmisionBean;
	}

	public TramiteTraficoDuplicadoBean getTramiteTraficoDuplicado() {
		return tramiteTraficoDuplicado;
	}

	public void setTramiteTraficoDuplicado(
			TramiteTraficoDuplicadoBean tramiteTraficoDuplicado) {
		this.tramiteTraficoDuplicado = tramiteTraficoDuplicado;
	}

	public Persona getPersonaFac() {
		return personaFac;
	}

	public void setPersonaFac(Persona personaFac) {
		this.personaFac = personaFac;
	}

	public boolean isUtilizarTitular() {
		return utilizarTitular;
	}

	public void setUtilizarTitular(boolean utilizarTitular) {
		this.utilizarTitular = utilizarTitular;
	}

	public String getTipoPersona() {
		return tipoPersona;
	}

	public void setTipoPersona(String tipoPersona) {
		this.tipoPersona = tipoPersona;
	}

	public SolicitudDatosBean getSolicitud() {
		return solicitud;
	}

	public void setSolicitud(SolicitudDatosBean solicitud) {
		this.solicitud = solicitud;
	}

	public String getNumColegiadoFac() {
		return numColegiadoFac;
	}

	public void setNumColegiadoFac(String numColegiadoFac) {
		this.numColegiadoFac = numColegiadoFac;
	}
}