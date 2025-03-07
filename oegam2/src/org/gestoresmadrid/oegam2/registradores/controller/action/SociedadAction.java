package org.gestoresmadrid.oegam2.registradores.controller.action;

import java.math.BigDecimal;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.gestoresmadrid.core.pagination.model.component.ModelPagination;
import org.gestoresmadrid.core.personas.model.enumerados.TipoPersona;
import org.gestoresmadrid.core.registradores.model.enumerados.EstadoTramiteRegistro;
import org.gestoresmadrid.core.registradores.model.enumerados.TipoTramiteRegistro;
import org.gestoresmadrid.oegam2.pagination.controller.action.AbstractPaginatedListAction;
import org.gestoresmadrid.oegam2comun.personas.model.service.ServicioPersona;
import org.gestoresmadrid.oegam2comun.registradores.model.service.ServicioTramiteRegistro;
import org.gestoresmadrid.oegam2comun.registradores.view.beans.ConsultaSociedadesBean;
import org.gestoresmadrid.oegam2comun.registradores.view.dto.TramiteRegistroDto;
import org.gestoresmadrid.oegamComun.acceso.service.impl.UtilesColegiado;
import org.gestoresmadrid.oegamComun.persona.view.dto.PersonaDto;
import org.springframework.beans.factory.annotation.Autowired;

import escrituras.beans.ResultBean;
import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;
import utilidades.mensajes.Claves;

public class SociedadAction extends AbstractPaginatedListAction {

	private static final long serialVersionUID = -5358499584684349581L;

	private static final ILoggerOegam log = LoggerOegam.getLogger("SociedadAction");

	@Resource
	private ModelPagination modeloSociedadesPaginated;

	private ConsultaSociedadesBean consultaSociedadesBean;

	@Autowired
	private ServicioPersona servicioPersona;

	@Autowired
	private ServicioTramiteRegistro servicioTramiteRegistro;

	@Autowired
	private UtilesColegiado utilesColegiado;

	private TramiteRegistroDto tramiteRegistro;

	private PersonaDto sociedad;

	private String cifSeleccion;

	public String inicio() {
		consultaSociedadesBean = new ConsultaSociedadesBean();
		consultaSociedadesBean.setCifSociedad(tramiteRegistro.getSociedad().getNif());
		consultaSociedadesBean.setDenominacionSocial(tramiteRegistro.getSociedad().getApellido1RazonSocial());
		consultaSociedadesBean.setIus(tramiteRegistro.getSociedad().getIus());
		consultaSociedadesBean.setSeccion(tramiteRegistro.getSociedad().getSeccion());
		consultaSociedadesBean.setHoja(tramiteRegistro.getSociedad().getHoja());
		consultaSociedadesBean.setHojaBis(tramiteRegistro.getSociedad().getHojaBis());
		consultaSociedadesBean.setSubtipo(tramiteRegistro.getSociedad().getSubtipo());
		consultaSociedadesBean.setCorreoElectronico(tramiteRegistro.getSociedad().getCorreoElectronico());
		return actualizarPaginatedList();
	}

	public String buscar() {
		return actualizarPaginatedList();
	}

	public String guardar() {
		log.info(Claves.CLAVE_LOG_REGISTRADORES_COMUN_INICIO + "seleccionar.");
		String returnStruts = "";
		try {
			returnStruts = devolverRespuesta();
			if (tramiteRegistro != null) {
				tramiteRegistro.setEstado(new BigDecimal(EstadoTramiteRegistro.Iniciado.getValorEnum()));
				ResultBean respuesta = servicioTramiteRegistro.guardarTramiteRegistro(tramiteRegistro, null, utilesColegiado.getIdUsuarioSessionBigDecimal());
				if (respuesta.getError()) {
					addActionError("No se ha modificado el estado del trámite: " + respuesta.getMensaje());
				}

				if (StringUtils.isBlank(tramiteRegistro.getSociedad().getNumColegiado())) {
					tramiteRegistro.getSociedad().setNumColegiado(utilesColegiado.getNumColegiadoSession());
				}

				ResultBean result = servicioPersona.guardarSociedad(tramiteRegistro, utilesColegiado.getIdUsuarioSessionBigDecimal());

				if (result != null && !result.getError()) {
					addActionMessage("Trámite guardado correctamente");
					tramiteRegistro.setCif(tramiteRegistro.getSociedad().getNif());
					servicioTramiteRegistro.guardarTramiteRegistro(tramiteRegistro, null, utilesColegiado.getIdUsuarioSessionBigDecimal());
				} else {
					addActionError("Error al seleccionar la sociedad");
					if (result != null && result.getListaMensajes() != null && !result.getListaMensajes().isEmpty()) {
						for (String mensaje : result.getListaMensajes())
							addActionError(mensaje);
					}
				}
			}
			log.info(Claves.CLAVE_LOG_REGISTRADORES_COMUN_FIN + "seleccionar.");
		} catch (Exception ex) {
			log.error(ex);
			addActionError(ex.toString());
		}
		if (tramiteRegistro != null) {
			PersonaDto sociedadPantalla = tramiteRegistro.getSociedad();
			tramiteRegistro = servicioTramiteRegistro.getTramite(tramiteRegistro.getIdTramiteRegistro());
			tramiteRegistro.setSociedad(sociedadPantalla);
		}
		return returnStruts;
	}

	public String seleccionar() {
		String returnStruts = devolverRespuesta();

		if (null != tramiteRegistro.getIdTramiteRegistro()) {
			ResultBean respuesta = servicioTramiteRegistro.guardarTramiteRegistro(tramiteRegistro, null, utilesColegiado.getIdUsuarioSessionBigDecimal());
			if (respuesta == null || respuesta.getError()) {
				addActionError("Error al seleccionar la sociedad");
				if (respuesta != null && respuesta.getListaMensajes() != null && !respuesta.getListaMensajes().isEmpty()) {
					for (String mensaje : respuesta.getListaMensajes())
						addActionError(mensaje);
				}
				log.info(Claves.CLAVE_LOG_REGISTRADORES_COMUN_FIN + "detalle sociedad.");
				return returnStruts;
			} 

			tramiteRegistro = servicioTramiteRegistro.getTramite(tramiteRegistro.getIdTramiteRegistro());
		}

		sociedad = servicioPersona.getPersona(cifSeleccion, utilesColegiado.getNumColegiadoSession());
		if (null == tramiteRegistro)
			tramiteRegistro = new TramiteRegistroDto();
		tramiteRegistro.setSociedad(sociedad);

		log.info(Claves.CLAVE_LOG_REGISTRADORES_COMUN_FIN + "detalle sociedad.");
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
		} else if (TipoTramiteRegistro.MODELO_11.getValorEnum().equals(tramiteRegistro.getTipoTramite())) {
			return "tramiteRegistroCuenta";
		} else if (TipoTramiteRegistro.MODELO_12.getValorEnum().equals(tramiteRegistro.getTipoTramite())) {
			return "tramiteRegistroLibro";
		}
		return "";
	}

	@Override
	protected ILoggerOegam getLog() {
		return log;
	}

	@Override
	protected ModelPagination getModelo() {
		return modeloSociedadesPaginated;
	}

	@Override
	protected Object getBeanCriterios() {
		return consultaSociedadesBean;
	}

	@Override
	protected void setBeanCriterios(Object object) {
		this.consultaSociedadesBean = (ConsultaSociedadesBean) object;
	}

	@Override
	protected String getResultadoPorDefecto() {
		return SUCCESS;
	}

	@Override
	protected void cargaRestricciones() {
		if (consultaSociedadesBean == null) {
			consultaSociedadesBean = new ConsultaSociedadesBean();
		}
		consultaSociedadesBean.setNumColegiado(utilesColegiado.getNumColegiadoSession());
		consultaSociedadesBean.setTipoPersona(TipoPersona.Juridica.getValorEnum());
	}

	@Override
	protected void cargarFiltroInicial() {
		if (consultaSociedadesBean == null) {
			consultaSociedadesBean = new ConsultaSociedadesBean();
		}
		if (StringUtils.isBlank(consultaSociedadesBean.getNumColegiado())) {
			consultaSociedadesBean.setNumColegiado(utilesColegiado.getNumColegiadoSession());
		}
		if (StringUtils.isBlank(consultaSociedadesBean.getTipoPersona())) {
			consultaSociedadesBean.setTipoPersona(TipoPersona.Juridica.getValorEnum());
		}
	}

	public ConsultaSociedadesBean getConsultaSociedadesBean() {
		return consultaSociedadesBean;
	}

	public void setConsultaSociedadesBean(ConsultaSociedadesBean consultaSociedadesBean) {
		this.consultaSociedadesBean = consultaSociedadesBean;
	}

	public PersonaDto getSociedad() {
		return sociedad;
	}

	public void setSociedad(PersonaDto sociedad) {
		this.sociedad = sociedad;
	}

	public String getCifSeleccion() {
		return cifSeleccion;
	}

	public void setCifSeleccion(String cifSeleccion) {
		this.cifSeleccion = cifSeleccion;
	}

	public TramiteRegistroDto getTramiteRegistro() {
		return tramiteRegistro;
	}

	public void setTramiteRegistro(TramiteRegistroDto tramiteRegistro) {
		this.tramiteRegistro = tramiteRegistro;
	}
}