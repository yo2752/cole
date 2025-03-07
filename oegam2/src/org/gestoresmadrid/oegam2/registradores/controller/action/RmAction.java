package org.gestoresmadrid.oegam2.registradores.controller.action;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;
import org.gestoresmadrid.core.model.enumerados.TextoNotificacion;
import org.gestoresmadrid.core.registradores.model.enumerados.EstadoTramiteRegistro;
import org.gestoresmadrid.core.registradores.model.enumerados.TipoTramiteRegistro;
import org.gestoresmadrid.oegam2comun.registradores.clases.ResultRegistro;
import org.gestoresmadrid.oegam2comun.registradores.model.service.ServicioTramiteRegistro;
import org.gestoresmadrid.oegam2comun.registradores.view.dto.TramiteRegistroDto;
import org.gestoresmadrid.oegam2comun.registradores.xml.estilos.StaxStreamSource;
import org.gestoresmadrid.oegamComun.acceso.service.impl.UtilesColegiado;
import org.springframework.beans.factory.annotation.Autowired;

import escrituras.beans.ResultBean;
import general.acciones.ActionBase;
import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;
import utilidades.mensajes.Claves;
import utilidades.web.OegamExcepcion;

public class RmAction extends ActionBase implements ServletRequestAware, ServletResponseAware {

	private static final long serialVersionUID = 142458496580957419L;

	private static final ILoggerOegam log = LoggerOegam.getLogger("Registradores");

	private HttpServletRequest request;

	private String tipoContratoRegistro;

	@Autowired
	private ServicioTramiteRegistro servicioTramiteRegistro;

	@Autowired
	private UtilesColegiado utilesColegiado;

	private TramiteRegistroDto tramiteRegistro;

	public String solicitar() throws Throwable {
		ResultRegistro resultado;
		log.info(Claves.CLAVE_LOG_REGISTRADORES_COMUN_INICIO + "solicitar.");
		String returnStruts = devolverRespuesta();

		resultado = servicioTramiteRegistro.enviarCorreo(tramiteRegistro);

		if ((int)resultado.getObj() == tramiteRegistro.getCertificantes().size()) {
			log.info(Claves.CLAVE_LOG_REGISTRADORES_COMUN_INFO + " Correos enviados. Inicio cambio estado a Pendiente_Firmas.");
			if (!new BigDecimal(EstadoTramiteRegistro.Pendiente.getValorEnum()).equals(tramiteRegistro.getEstado())) {
				ResultBean respuesta = servicioTramiteRegistro.cambiarEstado(true, tramiteRegistro.getIdTramiteRegistro(), tramiteRegistro.getEstado(), new BigDecimal(EstadoTramiteRegistro.Pendiente
						.getValorEnum()), true, TextoNotificacion.Cambio_Estado.getNombreEnum(), utilesColegiado.getIdUsuarioSessionBigDecimal());
				if (respuesta != null && respuesta.getError()) {
					log.error(Claves.CLAVE_LOG_REGISTRADORES_COMUN_ERROR + "Ha ocurrido el siguiente error actualizando a 'Pendiente_Firmas' el tramite con identificador: "
							+ tramiteRegistro.getIdTramiteRegistro() + " : " + respuesta.getMensaje());
				} else {
					log.info(Claves.CLAVE_LOG_PROCESO_WREG + "Se ha actualizado el estado del tramite con identificador: " + tramiteRegistro.getIdTramiteRegistro() + " a 'Pendiente firmas'");
					addActionMessage("Cuando los certificantes firmen el trámite recibirá una notificación");
					tramiteRegistro.setEstado(new BigDecimal(EstadoTramiteRegistro.Pendiente.getValorEnum()));
				}
			}
		}
		tramiteRegistro = servicioTramiteRegistro.getTramite(tramiteRegistro.getIdTramiteRegistro());
		log.info(Claves.CLAVE_LOG_REGISTRADORES_COMUN_FIN + "solicitar.");
		return returnStruts;
	}

	public String cerrarVistaDocumento() {
		log.info(Claves.CLAVE_LOG_REGISTRADORES_MODELO1_INICIO + "cerrarVistaDocumento.");
		try {
			tramiteRegistro = servicioTramiteRegistro.getTramite(tramiteRegistro.getIdTramiteRegistro());
			String respuesta = devolverRespuesta();
			setTipoContratoRegistro(devolverTipoContrato());
			log.info(Claves.CLAVE_LOG_REGISTRADORES_COMUN_FIN + "cerrarVistaDocumento.");
			return respuesta;
		} catch (Exception e) {
			log.error(Claves.CLAVE_LOG_REGISTRADORES_COMUN_ERROR + e);
			return "error";
		}
	}

	public String mostrar() {
		BufferedReader br = null;
		try {
			String ficheroHtml = StaxStreamSource.crearFicheroHtml(tramiteRegistro.getIdTramiteRegistro().toString());
			br = new BufferedReader(new FileReader(ficheroHtml));
			StringBuffer sb = new StringBuffer();
			String line = null;
			while ((line = br.readLine()) != null) {
				String UTF8Str = new String(line.getBytes(), "UTF-8");
				sb.append(UTF8Str);
			}
			request.setAttribute("contenido", sb);
		} catch (OegamExcepcion e) {
			log.error(e);
		} catch (Exception ex) {
			log.error(ex);
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					log.error("No se pudo cerrar el bufferedReader", e);
				}
			}
		}
		return "verRM";
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

	private String devolverTipoContrato() {
		if (TipoTramiteRegistro.MODELO_1.getValorEnum().equals(tramiteRegistro.getTipoTramite())) {
			return "TramiteRegistroMd1";
		} else if (TipoTramiteRegistro.MODELO_2.getValorEnum().equals(tramiteRegistro.getTipoTramite())) {
			return "TramiteRegistroMd2";
		} else if (TipoTramiteRegistro.MODELO_3.getValorEnum().equals(tramiteRegistro.getTipoTramite())) {
			return "TramiteRegistroMd3";
		} else if (TipoTramiteRegistro.MODELO_4.getValorEnum().equals(tramiteRegistro.getTipoTramite())) {
			return "TramiteRegistroMd4";
		} else if (TipoTramiteRegistro.MODELO_5.getValorEnum().equals(tramiteRegistro.getTipoTramite())) {
			return "TramiteRegistroMd5";
		}
		return "";
	}

	@Override
	public void setServletRequest(HttpServletRequest arg0) {
		this.request = arg0;
	}

	@Override
	public void setServletResponse(HttpServletResponse arg0) {}

	public TramiteRegistroDto getTramiteRegistro() {
		return tramiteRegistro;
	}

	public void setTramiteRegistro(TramiteRegistroDto tramiteRegistro) {
		this.tramiteRegistro = tramiteRegistro;
	}

	public String getTipoContratoRegistro() {
		return tipoContratoRegistro;
	}

	public void setTipoContratoRegistro(String tipoContratoRegistro) {
		this.tipoContratoRegistro = tipoContratoRegistro;
	}
}