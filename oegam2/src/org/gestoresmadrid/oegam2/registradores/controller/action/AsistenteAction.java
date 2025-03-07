package org.gestoresmadrid.oegam2.registradores.controller.action;

import java.math.BigDecimal;
import java.util.ArrayList;

import javax.annotation.Resource;

import org.gestoresmadrid.core.pagination.model.component.ModelPagination;
import org.gestoresmadrid.core.registradores.model.enumerados.EstadoTramiteRegistro;
import org.gestoresmadrid.core.registradores.model.enumerados.TipoTramiteRegistro;
import org.gestoresmadrid.oegam2.pagination.controller.action.AbstractPaginatedListAction;
import org.gestoresmadrid.oegam2comun.registradores.model.service.ServicioAsistente;
import org.gestoresmadrid.oegam2comun.registradores.model.service.ServicioReunion;
import org.gestoresmadrid.oegam2comun.registradores.model.service.ServicioSociedadCargo;
import org.gestoresmadrid.oegam2comun.registradores.model.service.ServicioTramiteRegistro;
import org.gestoresmadrid.oegam2comun.registradores.utiles.UtilesRegistradores;
import org.gestoresmadrid.oegam2comun.registradores.view.beans.ConsultaSociedadCargoBean;
import org.gestoresmadrid.oegam2comun.registradores.view.dto.AsistenteDto;
import org.gestoresmadrid.oegam2comun.registradores.view.dto.ReunionDto;
import org.gestoresmadrid.oegam2comun.registradores.view.dto.TramiteRegistroDto;
import org.gestoresmadrid.oegamComun.acceso.service.impl.UtilesColegiado;
import org.gestoresmadrid.utilidades.components.UtilesFecha;
import org.springframework.beans.factory.annotation.Autowired;

import escrituras.beans.ResultBean;
import utilidades.estructuras.Fecha;
import utilidades.estructuras.FechaFraccionada;
import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;
import utilidades.mensajes.Claves;

public class AsistenteAction extends AbstractPaginatedListAction {

	private static final long serialVersionUID = 8690480880689639003L;

	private static final ILoggerOegam log = LoggerOegam.getLogger("Registradores");

	@Resource
	private ModelPagination modeloSociedadCargoPaginated;

	private ConsultaSociedadCargoBean consultaSociedadCargoBean;

	@Autowired
	private ServicioSociedadCargo servicioSociedadCargo;

	@Autowired
	private ServicioTramiteRegistro servicioTramiteRegistro;

	@Autowired
	private ServicioAsistente servicioAsistente;

	@Autowired
	private ServicioReunion servicioReunion;

	@Autowired
	UtilesFecha utilesFecha;

	@Autowired
	UtilesColegiado utilesColegiado;

	private TramiteRegistroDto tramiteRegistro;

	private ArrayList<String> asistentesMarcados;

	private ArrayList<String> asistentesEliminados;

	private String rowidCargo;

	private String nifCargo;
	private String codigoCargo;

	public String inicio() {
		consultaSociedadCargoBean = new ConsultaSociedadCargoBean();
		consultaSociedadCargoBean.setCifSociedad(getTramiteRegistro().getSociedad().getNif());
		if (null != getTramiteRegistro().getAsistente().getSociedadCargo() && null != getTramiteRegistro().getAsistente().getSociedadCargo().getPersonaCargo()) {
			consultaSociedadCargoBean.setNifCargo(getTramiteRegistro().getAsistente().getSociedadCargo().getPersonaCargo().getNif());
			consultaSociedadCargoBean.setCodigoCargo(getTramiteRegistro().getAsistente().getSociedadCargo().getCodigoCargo());
			consultaSociedadCargoBean.setNombre(getTramiteRegistro().getAsistente().getSociedadCargo().getPersonaCargo().getNombre());
			consultaSociedadCargoBean.setApellido1(getTramiteRegistro().getAsistente().getSociedadCargo().getPersonaCargo().getApellido1RazonSocial());
			consultaSociedadCargoBean.setApellido2(getTramiteRegistro().getAsistente().getSociedadCargo().getPersonaCargo().getApellido2());
			consultaSociedadCargoBean.setCorreoElectronico(getTramiteRegistro().getAsistente().getSociedadCargo().getPersonaCargo().getCorreoElectronico());
		}

		FechaFraccionada fechaInicio = new FechaFraccionada();
		Fecha fechaActual = utilesFecha.getFechaActual();
		fechaInicio.setDiaInicio(fechaActual.getDia());
		fechaInicio.setMesInicio(fechaActual.getMes());
		fechaInicio.setAnioInicio(fechaActual.getAnio());

		FechaFraccionada fechaFin = new FechaFraccionada();
		fechaFin.setDiaFin(fechaActual.getDia());
		fechaFin.setMesFin(fechaActual.getMes());
		fechaFin.setAnioFin(fechaActual.getAnio());

		consultaSociedadCargoBean.setFechaInicio(fechaInicio);
		consultaSociedadCargoBean.setFechaFin(fechaFin);
		return actualizarPaginatedList();
	}

	public String alta() {
		String returnStruts = devolverRespuesta();
		log.info(Claves.CLAVE_LOG_REGISTRADORES_COMUN_INICIO + "alta.");
		tramiteRegistro.setEstado(new BigDecimal(EstadoTramiteRegistro.Iniciado.getValorEnum()));
		ResultBean result = servicioSociedadCargo.guardarSociedadCargo(getTramiteRegistro().getAsistente().getSociedadCargo(), getTramiteRegistro().getIdTramiteRegistro(), getTramiteRegistro()
				.getNumColegiado(), getTramiteRegistro().getSociedad().getNif(), getTramiteRegistro().getTipoTramite(), utilesColegiado.getIdUsuarioSessionBigDecimal());

		if (result.getError()) {
			addActionError("Error al dar de alta un cargo");
			if (result.getListaMensajes() != null && !result.getListaMensajes().isEmpty()) {
				for (String mensaje : result.getListaMensajes())
					addActionError(mensaje);
			}
			return returnStruts;
		}

		if (tramiteRegistro.getReunion() == null || tramiteRegistro.getReunion().getIdReunion() == null) {

			ResultBean resultReunion = servicioReunion.guardarReunion(tramiteRegistro.getReunion(), tramiteRegistro.getIdTramiteRegistro(), tramiteRegistro.getTipoTramite());
			if (resultReunion.getError()) {
				tramiteRegistro = servicioTramiteRegistro.getTramite(tramiteRegistro.getIdTramiteRegistro());
				if (resultReunion.getListaMensajes() != null && !resultReunion.getListaMensajes().isEmpty()) {
					for (String mensaje : resultReunion.getListaMensajes()) {
						addActionError(mensaje);
					}
				}
				addActionError("No se ha podido crear la reunión.");
				return returnStruts;
			}
			tramiteRegistro.getReunion().setIdReunion((Long) resultReunion.getAttachment(ServicioReunion.ID_REUNION));
		}
		AsistenteDto asistenteNuevo = new AsistenteDto();
		asistenteNuevo.setCifSociedad(tramiteRegistro.getSociedad().getNif());
		asistenteNuevo.setCodigoCargo(tramiteRegistro.getAsistente().getSociedadCargo().getCodigoCargo());
		asistenteNuevo.setIdTramiteRegistro(tramiteRegistro.getIdTramiteRegistro());
		asistenteNuevo.setNifCargo(tramiteRegistro.getAsistente().getSociedadCargo().getPersonaCargo().getNif());
		asistenteNuevo.setNumColegiado(tramiteRegistro.getNumColegiado());
		asistenteNuevo.setIdReunion(tramiteRegistro.getReunion().getIdReunion());
		servicioAsistente.guardarAsistente(asistenteNuevo);
		addActionMessage("Asistente dado de alta");

		tramiteRegistro = servicioTramiteRegistro.getTramite(tramiteRegistro.getIdTramiteRegistro());
		tramiteRegistro.setEstado(new BigDecimal(EstadoTramiteRegistro.Iniciado.getValorEnum()));
		log.info(Claves.CLAVE_LOG_REGISTRADORES_COMUN_FIN + "alta.");
		return returnStruts;
	}

	public String editar() {
		log.info(Claves.CLAVE_LOG_REGISTRADORES_COMUN_INICIO + "editar.");
		nifCargo = rowidCargo.split("-")[0];
		codigoCargo = rowidCargo.split("-")[1];

		tramiteRegistro = servicioTramiteRegistro.getTramite(tramiteRegistro.getIdTramiteRegistro());
		if (null == getTramiteRegistro().getAsistente()) {
			getTramiteRegistro().setAsistente(new AsistenteDto());
		}

		getTramiteRegistro().getAsistente().setSociedadCargo(servicioSociedadCargo.getSociedadCargo(tramiteRegistro.getSociedad().getNif(), tramiteRegistro.getNumColegiado(), nifCargo, codigoCargo));
		log.info(Claves.CLAVE_LOG_REGISTRADORES_COMUN_FIN + "editar.");
		return devolverRespuesta();
	}

	public String guardar() {
		log.info(Claves.CLAVE_LOG_REGISTRADORES_COMUN_INICIO + "seleccionar asistentes.");
		String returnStruts = "";
		try {
			returnStruts = devolverRespuesta();
			if (new BigDecimal(EstadoTramiteRegistro.Finalizado_error.getValorEnum()).equals(tramiteRegistro.getEstado())) {
				UtilesRegistradores.aEstadoIniciado(tramiteRegistro, utilesColegiado.getIdUsuarioSessionBigDecimal());
			}

			if (!UtilesRegistradores.permitidaModificacion(tramiteRegistro.getEstado())) {
				tramiteRegistro = servicioTramiteRegistro.getTramite(tramiteRegistro.getIdTramiteRegistro());
				addActionError("El estado actual del trámite no permite la modificación de los datos guardados del mismo");
				return returnStruts;
			}

			tramiteRegistro.setAsistentes(servicioAsistente.getAsistentes(tramiteRegistro.getIdTramiteRegistro()));
			if (tramiteRegistro.getAsistentes() == null || tramiteRegistro.getAsistentes().isEmpty()) {
				tramiteRegistro = servicioTramiteRegistro.getTramite(tramiteRegistro.getIdTramiteRegistro());
				addActionError("Debe seleccionar algún asistente.");
				return returnStruts;
			}

			ResultBean result = servicioReunion.guardarReunion(tramiteRegistro.getReunion(), tramiteRegistro.getIdTramiteRegistro(), tramiteRegistro.getTipoTramite());

			if (result != null && !result.getError()) {
				addActionMessage("Selección realizada");
				tramiteRegistro = servicioTramiteRegistro.getTramite(tramiteRegistro.getIdTramiteRegistro());
				tramiteRegistro.setEstado(new BigDecimal(EstadoTramiteRegistro.Iniciado.getValorEnum()));
			} else {
				addActionError("Error al seleccionar los asistentes");
				if (result != null && result.getListaMensajes() != null && !result.getListaMensajes().isEmpty()) {
					for (String mensaje : result.getListaMensajes())
						addActionError(mensaje);
				}
			}

			log.info(Claves.CLAVE_LOG_REGISTRADORES_COMUN_FIN + "seleccionar asistentes.");
		} catch (Exception ex) {
			log.error(ex);
			addActionError(ex.toString());
		}
		return returnStruts;
	}

	public String seleccionar() {
		log.info(Claves.CLAVE_LOG_REGISTRADORES_COMUN_INICIO + "seleccionar asistentes.");
		String returnStruts = "";
		String lugar = "";
		Fecha fecha = null;
		try {
			returnStruts = devolverRespuesta();
			if (tramiteRegistro.getReunion() != null) {
				lugar = tramiteRegistro.getReunion().getLugar();
				fecha = tramiteRegistro.getReunion().getFecha();
			}

			tramiteRegistro = servicioTramiteRegistro.getTramite(tramiteRegistro.getIdTramiteRegistro());
			if (asistentesMarcados != null && !asistentesMarcados.isEmpty()) {
				for (String asist : asistentesMarcados) {
					nifCargo = asist.split("-")[0];
					codigoCargo = asist.split("-")[1];
					boolean aniadirLista = true;
					if (tramiteRegistro.getAsistentes() != null && !tramiteRegistro.getAsistentes().isEmpty()) {
						for (AsistenteDto asistente : tramiteRegistro.getAsistentes()) {
							aniadirLista = true;
							if (asistente.getNifCargo().equals(nifCargo) && asistente.getCodigoCargo().equals(codigoCargo)) {
								aniadirLista = false;
								break;
							}
						}
					}
					if (aniadirLista) {
						if (tramiteRegistro.getReunion() == null || tramiteRegistro.getReunion().getIdReunion() == null) {
							if (tramiteRegistro.getReunion() == null) {
								tramiteRegistro.setReunion(new ReunionDto());
								tramiteRegistro.getReunion().setLugar(lugar);
								tramiteRegistro.getReunion().setFecha(fecha);
							}
							ResultBean result = servicioReunion.guardarReunion(tramiteRegistro.getReunion(), tramiteRegistro.getIdTramiteRegistro(), tramiteRegistro.getTipoTramite());
							if (result.getError()) {
								tramiteRegistro = servicioTramiteRegistro.getTramite(tramiteRegistro.getIdTramiteRegistro());
								for (String mensaje : result.getListaMensajes()) {
									addActionError(mensaje);
								}
								addActionError("No se ha podido crear la reunión.");
								return returnStruts;
							}
							tramiteRegistro.getReunion().setIdReunion((Long) result.getAttachment(ServicioReunion.ID_REUNION));
						}
						AsistenteDto asistenteNuevo = new AsistenteDto();
						asistenteNuevo.setCifSociedad(tramiteRegistro.getSociedad().getNif());
						asistenteNuevo.setCodigoCargo(codigoCargo);
						asistenteNuevo.setIdTramiteRegistro(tramiteRegistro.getIdTramiteRegistro());
						asistenteNuevo.setNifCargo(nifCargo);
						asistenteNuevo.setNumColegiado(tramiteRegistro.getNumColegiado());
						asistenteNuevo.setIdReunion(tramiteRegistro.getReunion().getIdReunion());
						servicioAsistente.guardarAsistente(asistenteNuevo);
					}
				}
			}
			tramiteRegistro = servicioTramiteRegistro.getTramite(tramiteRegistro.getIdTramiteRegistro());
			tramiteRegistro.setEstado(new BigDecimal(EstadoTramiteRegistro.Iniciado.getValorEnum()));
			if (tramiteRegistro.getReunion() != null) {
				tramiteRegistro.getReunion().setLugar(lugar);
				tramiteRegistro.getReunion().setFecha(fecha);
			}
			log.info(Claves.CLAVE_LOG_REGISTRADORES_COMUN_FIN + "seleccionar asistentes.");
		} catch (Exception ex) {
			log.error(ex);
			addActionError(ex.toString());
		}
		return returnStruts;
	}

	public String eliminar() {
		log.info(Claves.CLAVE_LOG_REGISTRADORES_COMUN_INICIO + "eliminar eliminar.");
		String returnStruts = "";
		try {
			returnStruts = devolverRespuesta();
			servicioAsistente.eliminarAsistente(tramiteRegistro.getIdTramiteRegistro(), tramiteRegistro.getSociedad().getNif(), nifCargo, codigoCargo);
			tramiteRegistro = servicioTramiteRegistro.getTramite(tramiteRegistro.getIdTramiteRegistro());
			tramiteRegistro.setEstado(new BigDecimal(EstadoTramiteRegistro.Iniciado.getValorEnum()));
			log.info(Claves.CLAVE_LOG_REGISTRADORES_COMUN_FIN + "eliminar asistentes.");
		} catch (Exception ex) {
			log.error(ex);
			addActionError(ex.toString());
		}
		return returnStruts;
	}

	private String devolverRespuesta() {
		if (TipoTramiteRegistro.MODELO_1.getValorEnum().equals(tramiteRegistro.getTipoTramite())) {
			return "tramiteRegistroModelo1";
		} else if (TipoTramiteRegistro.MODELO_2.getValorEnum().equals(tramiteRegistro.getTipoTramite())) {
			return "tramiteRegistroModelo2";
		} else if (TipoTramiteRegistro.MODELO_3.getValorEnum().equals(tramiteRegistro.getTipoTramite())) {
			return "tramiteRegistroModelo3";
		} else if (TipoTramiteRegistro.MODELO_4.getValorEnum().equals(tramiteRegistro.getTipoTramite())) {
			return "tramiteRegistroModelo4";
		} else if (TipoTramiteRegistro.MODELO_5.getValorEnum().equals(tramiteRegistro.getTipoTramite())) {
			return "tramiteRegistroModelo5";
		}
		return "";
	}

	@Override
	protected String getResultadoPorDefecto() {
		return SUCCESS;
	}

	@Override
	protected void cargarFiltroInicial() {

	}

	@Override
	protected void cargaRestricciones() {
		if (consultaSociedadCargoBean == null) {
			consultaSociedadCargoBean = new ConsultaSociedadCargoBean();
		}
		consultaSociedadCargoBean.setNumColegiado(utilesColegiado.getNumColegiadoSession());
		if (null != getTramiteRegistro() && null != getTramiteRegistro().getSociedad()) {
			consultaSociedadCargoBean.setCifSociedad(getTramiteRegistro().getSociedad().getNif());
		}

		FechaFraccionada fechaInicio = new FechaFraccionada();
		Fecha fechaActual = utilesFecha.getFechaActual();
		fechaInicio.setDiaInicio(fechaActual.getDia());
		fechaInicio.setMesInicio(fechaActual.getMes());
		fechaInicio.setAnioInicio(fechaActual.getAnio());

		FechaFraccionada fechaFin = new FechaFraccionada();
		fechaFin.setDiaFin(fechaActual.getDia());
		fechaFin.setMesFin(fechaActual.getMes());
		fechaFin.setAnioFin(fechaActual.getAnio());

		consultaSociedadCargoBean.setFechaInicio(fechaInicio);
		consultaSociedadCargoBean.setFechaFin(fechaFin);
	}

	@Override
	protected ILoggerOegam getLog() {
		return log;
	}

	@Override
	protected ModelPagination getModelo() {
		return modeloSociedadCargoPaginated;
	}

	@Override
	protected Object getBeanCriterios() {
		return consultaSociedadCargoBean;
	}

	@Override
	protected void setBeanCriterios(Object object) {
		this.consultaSociedadCargoBean = (ConsultaSociedadCargoBean) object;
	}

	public String getRowidCargo() {
		return rowidCargo;
	}

	public void setRowidCargo(String rowidCargo) {
		this.rowidCargo = rowidCargo;
	}

	public ConsultaSociedadCargoBean getConsultaSociedadCargoBean() {
		return consultaSociedadCargoBean;
	}

	public void setConsultaSociedadCargoBean(ConsultaSociedadCargoBean consultaSociedadCargoBean) {
		this.consultaSociedadCargoBean = consultaSociedadCargoBean;
	}

	public TramiteRegistroDto getTramiteRegistro() {
		return tramiteRegistro;
	}

	public void setTramiteRegistro(TramiteRegistroDto tramiteRegistro) {
		this.tramiteRegistro = tramiteRegistro;
	}

	public ArrayList<String> getAsistentesMarcados() {
		return asistentesMarcados;
	}

	public void setAsistentesMarcados(ArrayList<String> asistentesMarcados) {
		this.asistentesMarcados = asistentesMarcados;
	}

	public ArrayList<String> getAsistentesEliminados() {
		return asistentesEliminados;
	}

	public void setAsistentesEliminados(ArrayList<String> asistentesEliminados) {
		this.asistentesEliminados = asistentesEliminados;
	}

	public String getNifCargo() {
		return nifCargo;
	}

	public void setNifCargo(String nifCargo) {
		this.nifCargo = nifCargo;
	}

	public String getCodigoCargo() {
		return codigoCargo;
	}

	public void setCodigoCargo(String codigoCargo) {
		this.codigoCargo = codigoCargo;
	}
}