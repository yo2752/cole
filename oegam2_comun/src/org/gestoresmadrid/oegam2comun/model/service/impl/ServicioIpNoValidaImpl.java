package org.gestoresmadrid.oegam2comun.model.service.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.gestoresmadrid.core.general.model.dao.impl.IpNoPermitidasDao;
import org.gestoresmadrid.core.general.model.vo.IpNoPermitidasVO;
import org.gestoresmadrid.oegam2comun.correo.model.service.ServicioCorreo;
import org.gestoresmadrid.oegam2comun.model.service.ServicioIpNoValida;
import org.gestoresmadrid.utilidades.components.GestorPropiedades;
import org.gestoresmadrid.utilidades.components.UtilesFecha;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import escrituras.beans.ResultBean;
import utilidades.estructuras.Fecha;
import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;
import utilidades.web.OegamExcepcion;

@Service
@Transactional
public class ServicioIpNoValidaImpl implements ServicioIpNoValida {

	@Autowired
	private IpNoPermitidasDao ipNoPermitidasDao;

	@Autowired
	private ServicioCorreo servicioCorreo;

	@Autowired
	private GestorPropiedades gestorPropiedades;

	@Autowired
	UtilesFecha utilesFecha;

	private static final ILoggerOegam log = LoggerOegam.getLogger(ServicioIpNoValidaImpl.class);

	private static final String SUBJECT_INFORMACION = "Información Acceso IP no Valida.";
	private static final String SUBJECT_ACESSO_DENEGADO = "Acceso Denegado por IP no Valida.";

	@Override
	public List<IpNoPermitidasVO> getListaIPNoPermitidas() {
		List<IpNoPermitidasVO> listaIpNoPermitidas = new ArrayList<>();
		listaIpNoPermitidas = ipNoPermitidasDao.getListadoIpNoPermitidas();

		return listaIpNoPermitidas;
	}

	@Override
	public boolean validarIP(String ipAccesso, List<IpNoPermitidasVO> listaIPNoPermitidas) {
		boolean valido = true;
		for (IpNoPermitidasVO ipNoPermitidas : listaIPNoPermitidas) {
			if (ipNoPermitidas.getIp().equals(ipAccesso)) {
				if (ipNoPermitidas.getPermitirAcceso() == 1) {
					if (ipNoPermitidas.getEnviarCorreo() == 1) {
						enviarCorreoIPAccesso(ipAccesso, ipNoPermitidas, valido);
						break;
					}
				} else {
					valido = false;
					enviarCorreoIPAccesso(ipAccesso, ipNoPermitidas, valido);
					break;
				}
			}
		}
		return valido;
	}

	private void enviarCorreoIPAccesso(String ip, IpNoPermitidasVO ipNoPermitidas, boolean esPermitido) {
		log.info("inicio envioCorreo acceso por IP no Permitida");

		StringBuffer sb = new StringBuffer();
		Fecha fecha = utilesFecha.getFechaHoraActualLEG();
		sb.append("<br>El Usuario " + ipNoPermitidas.getUsuario().getApellidosNombre() + " con ID: "
				+ ipNoPermitidas.getUsuario().getIdUsuario() + " y perteneciente al Colegiado "
				+ ipNoPermitidas.getColegiado().getNumColegiado() + ".<br>Se ha conectado a la aplicación desde la IP "
				+ ip + "  el dia " + fecha.getDia() + "/" + fecha.getMes() + "/" + fecha.getAnio() + ", a la hora "
				+ fecha.getHora() + ":" + fecha.getMinutos() + ":" + fecha.getSegundos() + "<br>");
		String subject = "";
		if (!esPermitido) {
			sb.append("Al usuario se le ha denegado el acceso");
			subject = SUBJECT_ACESSO_DENEGADO;
		} else {
			subject = SUBJECT_INFORMACION;
		}

		String entorno = gestorPropiedades.valorPropertie("Entorno");
		if (!"PRODUCCION".equals(entorno)) {
			subject = entorno + ": " + subject;
		}

		try {
			ResultBean resultEnvio;
			StringBuffer sb2 = new StringBuffer(
					"<span style=\"font-size:15pt;font-family:Tunga;margin-left:20px;\"><br>Notificación desde la Oficina Electrónica de Gestión Administrativa (OEGAM) <br>")
							.append(sb.toString()).append("<br><br></span>");
			resultEnvio = servicioCorreo.enviarCorreo(sb2.toString(), null, null, subject, ipNoPermitidas.getMail(),
					null, null, null, null);
			if (resultEnvio == null || resultEnvio.getError()) {
				throw new OegamExcepcion("Error en la notificacion de error servicio");
			}

			log.error("Se va a enviar un correo electronico informando de un accesso con la siguiente IP no valida: "
					+ ip);
		} catch (OegamExcepcion | IOException e) {
			log.error("Error al enviar correo de accesso a la aplicación con una IP no valida ", e);
		}
		log.info("fin envioCorreo");
	}

	/**
	 * Realiza la recarga de las ip permitidas en la caché
	 * 
	 * @return
	 */
	@Override
	public Map<String, String> obtenerIP() {
		log.debug("Recargando informacion de las IP Permitidas cacheados");

		List<IpNoPermitidasVO> tempIpPerm = ipNoPermitidasDao.getListadoIpNoPermitidas();
		if (tempIpPerm != null) {
			Map<String, String> map = new HashMap<>();

			for (int i = 0; i < tempIpPerm.size(); i++) {
				map.put(tempIpPerm.get(i).getIp(), tempIpPerm.get(i).getMail());
			}

			if (map != null) {
				return map;
			}
		}
		return null;
	}

}