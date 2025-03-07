package org.gestoresmadrid.oegam2.registradores.controller.action;

import java.math.BigDecimal;
import java.util.ArrayList;

import javax.annotation.Resource;

import org.gestoresmadrid.core.pagination.model.component.ModelPagination;
import org.gestoresmadrid.core.registradores.model.enumerados.EstadoTramiteRegistro;
import org.gestoresmadrid.core.registradores.model.enumerados.TipoTramiteRegistro;
import org.gestoresmadrid.oegam2.pagination.controller.action.AbstractPaginatedListAction;
import org.gestoresmadrid.oegam2comun.registradores.model.service.ServicioCertifCargo;
import org.gestoresmadrid.oegam2comun.registradores.model.service.ServicioSociedadCargo;
import org.gestoresmadrid.oegam2comun.registradores.model.service.ServicioTramiteRegistro;
import org.gestoresmadrid.oegam2comun.registradores.utiles.UtilesRegistradores;
import org.gestoresmadrid.oegam2comun.registradores.view.beans.ConsultaSociedadCargoBean;
import org.gestoresmadrid.oegam2comun.registradores.view.dto.CertifCargoDto;
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

public class CertificanteAction extends AbstractPaginatedListAction {

	private static final long serialVersionUID = -4565911403673644823L;

	private static final ILoggerOegam log = LoggerOegam.getLogger("Registradores");

	@Resource
	private ModelPagination modeloSociedadCargoPaginated;

	private ConsultaSociedadCargoBean consultaSociedadCargoBean;

	@Autowired
	private ServicioSociedadCargo servicioSociedadCargo;

	@Autowired
	private ServicioTramiteRegistro servicioTramiteRegistro;

	@Autowired
	private ServicioCertifCargo servicioCertifCargo;

	@Autowired
	UtilesFecha utilesFecha;

	@Autowired
	UtilesColegiado utilesColegiado;

	private TramiteRegistroDto tramiteRegistro;

	private ArrayList<String> certificantesMarcados;

	private ArrayList<String> certificantesEliminados;

	private String rowidCargo;

	private String nifCargo;
	private String codigoCargo;

	public String inicio() {
		consultaSociedadCargoBean = new ConsultaSociedadCargoBean();
		consultaSociedadCargoBean.setCifSociedad(getTramiteRegistro().getSociedad().getNif());
		if (null != getTramiteRegistro().getCertificante().getSociedadCargo() && null != getTramiteRegistro().getCertificante().getSociedadCargo().getPersonaCargo()) {
			consultaSociedadCargoBean.setNifCargo(getTramiteRegistro().getCertificante().getSociedadCargo().getPersonaCargo().getNif());
			consultaSociedadCargoBean.setCodigoCargo(getTramiteRegistro().getCertificante().getSociedadCargo().getCodigoCargo());
			consultaSociedadCargoBean.setNombre(getTramiteRegistro().getCertificante().getSociedadCargo().getPersonaCargo().getNombre());
			consultaSociedadCargoBean.setApellido1(getTramiteRegistro().getCertificante().getSociedadCargo().getPersonaCargo().getApellido1RazonSocial());
			consultaSociedadCargoBean.setApellido2(getTramiteRegistro().getCertificante().getSociedadCargo().getPersonaCargo().getApellido2());
			consultaSociedadCargoBean.setCorreoElectronico(getTramiteRegistro().getCertificante().getSociedadCargo().getPersonaCargo().getCorreoElectronico());
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
		ResultBean result = servicioSociedadCargo.guardarSociedadCargo(getTramiteRegistro().getCertificante().getSociedadCargo(), getTramiteRegistro().getIdTramiteRegistro(), getTramiteRegistro()
				.getNumColegiado(), tramiteRegistro.getSociedad().getNif(), tramiteRegistro.getTipoTramite(), utilesColegiado.getIdUsuarioSessionBigDecimal());

		if (result.getError()) {
			addActionError("Error al dar de alta un cargo");
			if (result.getListaMensajes() != null && !result.getListaMensajes().isEmpty()) {
				for (String mensaje : result.getListaMensajes())
					addActionError(mensaje);
			}
			return returnStruts;
		}

		CertifCargoDto cerCargoNuevo = new CertifCargoDto();
		cerCargoNuevo.setCifSociedad(tramiteRegistro.getSociedad().getNif());
		cerCargoNuevo.setCodigoCargo(tramiteRegistro.getCertificante().getSociedadCargo().getCodigoCargo());
		cerCargoNuevo.setIdTramiteRegistro(tramiteRegistro.getIdTramiteRegistro());
		cerCargoNuevo.setNifCargo(tramiteRegistro.getCertificante().getSociedadCargo().getPersonaCargo().getNif());
		cerCargoNuevo.setNumColegiado(tramiteRegistro.getNumColegiado());
		servicioCertifCargo.guardarCertifCargo(cerCargoNuevo);
		addActionMessage("Certificante dado de alta");

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
		if (null == getTramiteRegistro().getCertificante()) {
			getTramiteRegistro().setCertificante(new CertifCargoDto());
		}

		getTramiteRegistro().getCertificante().setSociedadCargo(servicioSociedadCargo.getSociedadCargo(tramiteRegistro.getSociedad().getNif(), tramiteRegistro.getNumColegiado(), nifCargo,
				codigoCargo));
		log.info(Claves.CLAVE_LOG_REGISTRADORES_COMUN_FIN + "editar.");

		return devolverRespuesta();
	}

	public String guardar() {
		log.info(Claves.CLAVE_LOG_REGISTRADORES_COMUN_INICIO + "seleccionar certificantes.");
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

			if (!TipoTramiteRegistro.MODELO_2.getValorEnum().equals(tramiteRegistro.getTipoTramite())) {
				tramiteRegistro.setCertificantes(servicioCertifCargo.getCertificantes(tramiteRegistro.getIdTramiteRegistro()));
				if (tramiteRegistro.getCertificantes() == null || tramiteRegistro.getCertificantes().isEmpty()) {
					tramiteRegistro = servicioTramiteRegistro.getTramite(tramiteRegistro.getIdTramiteRegistro());
					addActionError("Debe seleccionar algún certificante.");
					return returnStruts;
				}
			}
			tramiteRegistro.setEstado(new BigDecimal(EstadoTramiteRegistro.Iniciado.getValorEnum()));
			ResultBean result = servicioTramiteRegistro.guardarTramiteRegistro(tramiteRegistro, null, utilesColegiado.getIdUsuarioSessionBigDecimal());

			if (result != null && !result.getError()) {
				addActionMessage("Selección realizada");
				tramiteRegistro = servicioTramiteRegistro.getTramite(tramiteRegistro.getIdTramiteRegistro());
			} else {
				addActionError("Error al seleccionar los certificantes");
				if (result != null && result.getListaMensajes() != null && !result.getListaMensajes().isEmpty()) {
					for (String mensaje : result.getListaMensajes())
						addActionError(mensaje);
				}
			}
			log.info(Claves.CLAVE_LOG_REGISTRADORES_COMUN_FIN + "seleccionar certificantes.");
		} catch (Exception ex) {
			log.error(ex);
			addActionError(ex.toString());
		}
		return returnStruts;
	}

	public String seleccionar() {
		log.info(Claves.CLAVE_LOG_REGISTRADORES_COMUN_INICIO + "seleccionar certificantes.");
		String returnStruts = "";
		try {
			returnStruts = devolverRespuesta();
			String lugar = tramiteRegistro.getLugar();
			Fecha fechaCertif = tramiteRegistro.getFechaCertif();
			String refPropia = tramiteRegistro.getRefPropia();
			tramiteRegistro = servicioTramiteRegistro.getTramite(tramiteRegistro.getIdTramiteRegistro());
			if (null != certificantesMarcados && !certificantesMarcados.isEmpty()) {
				for (String certif : certificantesMarcados) {
					nifCargo = certif.split("-")[0];
					codigoCargo = certif.split("-")[1];
					boolean aniadirLista = true;
					if (tramiteRegistro.getCertificantes() != null && !tramiteRegistro.getCertificantes().isEmpty()) {
						for (CertifCargoDto certifCargo : tramiteRegistro.getCertificantes()) {
							aniadirLista = true;
							if (certifCargo.getNifCargo().equals(nifCargo) && certifCargo.getCodigoCargo().equals(codigoCargo)) {
								aniadirLista = false;
								break;
							}
						}
					}
					if (aniadirLista) {
						CertifCargoDto cerCargoNuevo = new CertifCargoDto();
						cerCargoNuevo.setCifSociedad(tramiteRegistro.getSociedad().getNif());
						cerCargoNuevo.setCodigoCargo(codigoCargo);
						cerCargoNuevo.setIdTramiteRegistro(tramiteRegistro.getIdTramiteRegistro());
						cerCargoNuevo.setNifCargo(nifCargo);
						cerCargoNuevo.setNumColegiado(tramiteRegistro.getNumColegiado());
						servicioCertifCargo.guardarCertifCargo(cerCargoNuevo);
					}
				}
			}
			tramiteRegistro = servicioTramiteRegistro.getTramite(tramiteRegistro.getIdTramiteRegistro());
			tramiteRegistro.setEstado(new BigDecimal(EstadoTramiteRegistro.Iniciado.getValorEnum()));
			tramiteRegistro.setLugar(lugar);
			tramiteRegistro.setFechaCertif(fechaCertif);
			tramiteRegistro.setRefPropia(refPropia);
			log.info(Claves.CLAVE_LOG_REGISTRADORES_COMUN_FIN + "seleccionar certificantes.");
		} catch (Exception ex) {
			log.error(ex);
			addActionError(ex.toString());
		}
		return returnStruts;
	}

	public String eliminar() {
		log.info(Claves.CLAVE_LOG_REGISTRADORES_COMUN_INICIO + "eliminar certificantes.");
		String returnStruts = "";
		try {
			returnStruts = devolverRespuesta();
			servicioCertifCargo.eliminarCertifCargo(tramiteRegistro.getIdTramiteRegistro(), tramiteRegistro.getSociedad().getNif(), nifCargo, codigoCargo);
			tramiteRegistro = servicioTramiteRegistro.getTramite(tramiteRegistro.getIdTramiteRegistro());
			tramiteRegistro.setEstado(new BigDecimal(EstadoTramiteRegistro.Iniciado.getValorEnum()));
			log.info(Claves.CLAVE_LOG_REGISTRADORES_COMUN_FIN + "eliminar certificantes.");
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

	public ArrayList<String> getCertificantesMarcados() {
		return certificantesMarcados;
	}

	public void setCertificantesMarcados(ArrayList<String> certificantesMarcados) {
		this.certificantesMarcados = certificantesMarcados;
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

	public ArrayList<String> getCertificantesEliminados() {
		return certificantesEliminados;
	}

	public void setCertificantesEliminados(ArrayList<String> certificantesEliminados) {
		this.certificantesEliminados = certificantesEliminados;
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