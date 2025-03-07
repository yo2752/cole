package org.gestoresmadrid.oegamPlacasMatricula.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;

import org.gestoresmadrid.core.general.model.vo.UsuarioVO;
import org.gestoresmadrid.oegam2comun.model.service.ServicioUsuario;
import org.gestoresmadrid.oegam2comun.personas.model.service.ServicioPersona;
import org.gestoresmadrid.oegam2comun.trafico.solicitudesplacas.enumerados.EstadoSolicitudPlacasEnum;
import org.gestoresmadrid.oegamComun.acceso.service.impl.UtilesColegiado;
import org.gestoresmadrid.oegamPlacasMatricula.service.ServicioPlacasMatricula;
import org.gestoresmadrid.oegamPlacasMatricula.service.ServicioValidacionPlacasMatricula;
import org.gestoresmadrid.oegamPlacasMatricula.view.bean.ValidacionPlacasBean;
import org.gestoresmadrid.oegamPlacasMatricula.view.dto.SolicitudPlacaDto;
import org.gestoresmadrid.utilidades.components.UtilesFecha;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ServicioValidacionPlacasMatriculaImpl implements ServicioValidacionPlacasMatricula {

	private static final long serialVersionUID = 2540890480627358913L;

	@Autowired
	private ServicioPlacasMatricula servicioPlacasMatricula;

	@Autowired
	private UtilesFecha utilesFecha;

	@Autowired
	private ServicioPersona servicioPersona;

	@Autowired
	private ServicioUsuario servicioUsuario;

	@Autowired
	private UtilesColegiado utilesColegiado;

	private String validarSiGuardada(SolicitudPlacaDto solicitudDto) {
		String sValGuardada = "";
		if (solicitudDto.getIdSolicitud() == null) {
			sValGuardada = "Debe guardar la/s solicitud/es antes de confirmarla/s.";
			log.error("Debe guardar la/s solicitud/es antes de confirmarla/s.");
		}

		return sValGuardada;
	}

	private ValidacionPlacasBean validarSolicitud(SolicitudPlacaDto solicitudDto, EstadoSolicitudPlacasEnum estadoPrevio, int contador) {

		ValidacionPlacasBean validarSolicitud = new ValidacionPlacasBean();

		validarSolicitud.setNumColegiado(solicitudDto.getNumColegiado());
		validarSolicitud.setFechaSolicitud(solicitudDto.getFechaSolicitud() != null ? solicitudDto.getFechaSolicitud().toString() : null);
		validarSolicitud.setMatricula(solicitudDto.getMatricula());
		validarSolicitud.setNoElemento(contador);

		ArrayList<String> mensajes = new ArrayList<String>();

		if (solicitudDto.getEstado() != Integer.parseInt(estadoPrevio.getValorEnum())) {
			if (solicitudDto.getEstado().equals(Integer.parseInt(EstadoSolicitudPlacasEnum.Iniciado.getValorEnum())) && (estadoPrevio.equals(EstadoSolicitudPlacasEnum.Confirmado))) {
				validarSolicitud.setError(true);
				mensajes.add("La solicitud debe encontrarse en estado confirmado para poder pasar a estado tramitado.");
				log.error("La solicitud debe encontrarse en estado confirmado para poder pasar a estado tramitado.");
			} else if (solicitudDto.getEstado().equals(Integer.parseInt(EstadoSolicitudPlacasEnum.Iniciado.getValorEnum())) && (estadoPrevio.equals(EstadoSolicitudPlacasEnum.Tramitado))) {
				validarSolicitud.setError(true);
				mensajes.add("La solicitud debe encontrarse en estado tramitado para poder pasar a estado finalizado.");
				log.error("La solicitud debe encontrarse en estado tramitado para poder pasar a estado finalizado.");
			} else if (solicitudDto.getEstado().equals(Integer.parseInt(EstadoSolicitudPlacasEnum.Confirmado.getValorEnum())) && (estadoPrevio.equals(EstadoSolicitudPlacasEnum.Tramitado))) {
				validarSolicitud.setError(true);
				mensajes.add("La solicitud debe encontrarse en estado tramitado para poder pasar a estado finalizado.");
				log.error("La solicitud debe encontrarse en estado tramitado para poder pasar a estado finalizado.");
			}
		}

		if (solicitudDto.getMatricula() == null || "".equals(solicitudDto.getMatricula())) {
			validarSolicitud.setError(true);
			mensajes.add("no existe Matrícula");
			log.error("no existe Matrícula para la solicitud " + contador);
		} else if (solicitudDto.getIdSolicitud() == null && (null == solicitudDto.getTitular() || null == solicitudDto.getNifTitular() || servicioPersona.getPersonaVO(solicitudDto.getNifTitular(),
				solicitudDto.getNumColegiado()) == null)) {
			validarSolicitud.setError(true);
			mensajes.add("El titular para la solicitud con matrícula " + solicitudDto.getMatricula() + " no existe o no tiene DNI. Revise los datos en personas.");
			log.error("El titular para la solicitud con matrícula " + solicitudDto.getMatricula() + " no existe o no tiene DNI. Revise los datos en personas.");
		}

		validarSolicitud.setMensajes(mensajes);

		return validarSolicitud;
	}

	@Override
	public ArrayList<ValidacionPlacasBean> validarSolicitudes(ArrayList<SolicitudPlacaDto> listaSolicitudes, EstadoSolicitudPlacasEnum estadoPrevio) {

		ArrayList<ValidacionPlacasBean> resultadoValidaciones = new ArrayList<ValidacionPlacasBean>();
		int contador = 0;

		for (SolicitudPlacaDto spBean : listaSolicitudes) {
			if (estadoPrevio != null) {
				String sValSiGuardada = validarSiGuardada(spBean);
				if (!"".equals(sValSiGuardada)) {
					ValidacionPlacasBean valSiGuardada = new ValidacionPlacasBean();
					valSiGuardada.setError(true);
					valSiGuardada.setMatricula(spBean.getMatricula());
					valSiGuardada.setMensajes(new ArrayList<String>(1));
					valSiGuardada.getMensajes().add(sValSiGuardada);
					resultadoValidaciones.add(valSiGuardada);
					break;
				}
			}

			EstadoSolicitudPlacasEnum estadoPrevioValidar = null;
			if (estadoPrevio == null) {
				estadoPrevioValidar = EstadoSolicitudPlacasEnum.Iniciado;
			} else {
				estadoPrevioValidar = estadoPrevio;
			}
			ValidacionPlacasBean resultadoValidacion = validarSolicitud(spBean, estadoPrevioValidar, contador);
			resultadoValidaciones.add(resultadoValidacion);
			contador++;
		}

		return resultadoValidaciones;
	}

	@Override
	public ValidacionPlacasBean validarSolicitudesModificar(String[] estados) {
		ValidacionPlacasBean resultado = new ValidacionPlacasBean();
		resultado.setError(false);
		for (int x = 0; x < estados.length; x++) {
			for (int y = x + 1; y < estados.length; y++) {
				if (!estados[x].equals(estados[y])) {
					resultado.setError(true);
					ArrayList<String> mensaje = new ArrayList<String>();
					mensaje.add("No se pueden editar solicitudes con distinto estado.");
					log.error("No se pueden editar solicitudes con distinto estado.");
					resultado.setMensajes(mensaje);
					break;
				}
			}
		}

		return resultado;
	}

	@Override
	public ValidacionPlacasBean validarSolicitudesBorrar(String[] estados) {
		ValidacionPlacasBean resultado = new ValidacionPlacasBean();
		resultado.setError(false);
		for (int x = 0; x < estados.length; x++) {
			if (!estados[x].equals("1")) {
				resultado.setError(true);
				ArrayList<String> mensaje = new ArrayList<String>();
				mensaje.add("Sólo pueden borrarse solicitudes que se encuentren en estado Iniciado.");
				log.error("Sólo pueden borrarse solicitudes que se encuentren en estado Iniciado.");
				resultado.setMensajes(mensaje);
				break;
			}
		}

		return resultado;
	}

	@Override
	public ValidacionPlacasBean validarRealizacion(ArrayList<SolicitudPlacaDto> listaSolicitudes) {
		ValidacionPlacasBean validar = new ValidacionPlacasBean();
		validar.setError(false);
		validar.setMensajes(new ArrayList<String>(1));
		BigDecimal idUsuario = utilesColegiado.getIdUsuarioSessionBigDecimal() != null ? utilesColegiado.getIdUsuarioSessionBigDecimal() : null;
		UsuarioVO usuario = servicioUsuario.getUsuario(idUsuario);
		for (int i = 0; i < listaSolicitudes.size(); i++) {
			SolicitudPlacaDto spBean = listaSolicitudes.get(i);
			spBean.setUsuario(usuario);
			spBean.setNumColegiado(utilesColegiado.getNumColegiadoSession());
			SolicitudPlacaDto solicitud = servicioPlacasMatricula.getSolicitudPorClaveUnica(spBean);
			if (solicitud != null && solicitud.getEstado() != null) {
				if (solicitud.getEstado().equals(Integer.valueOf(EstadoSolicitudPlacasEnum.Iniciado.getValorEnum()))) {
					spBean.setIdSolicitud(solicitud.getIdSolicitud());
				} else { // if (!solicitud.getEstado().equals(EstadoSolicitudPlacasEnum.Iniciado))
					listaSolicitudes.remove(spBean);
					i--;
					validar.setError(true);
					validar.getMensajes().add("Se eliminó la solicitud para " + spBean.getMatricula() + " debido a que ya existía una solicitud guardada con la misma fecha.");
					log.error("Se eliminó la solicitud para " + spBean.getMatricula() + " debido a que ya existía una solicitud guardada con la misma fecha.");
				}
			} else {
				spBean.setFechaSolicitud(utilesFecha.getFechaActual());
			}
		}

		return validar;
	}

	@Override
	public ValidacionPlacasBean validarConfirmacion(ArrayList<SolicitudPlacaDto> listaSolicitudes) {
		ValidacionPlacasBean validar = new ValidacionPlacasBean();
		validar.setError(false);
		validar.setMensajes(new ArrayList<String>(1));
		BigDecimal idUsuario = utilesColegiado.getIdUsuarioSessionBigDecimal() != null ? utilesColegiado.getIdUsuarioSessionBigDecimal() : null;
		UsuarioVO usuario = servicioUsuario.getUsuario(idUsuario);
		for (int i = 0; i < listaSolicitudes.size(); i++) {
			SolicitudPlacaDto spBean = listaSolicitudes.get(i);
			spBean.setUsuario(usuario);
			SolicitudPlacaDto solicitud = servicioPlacasMatricula.getSolicitudPantalla(spBean.getIdSolicitud());
			if (solicitud != null && solicitud.getEstado() != null) {
				if (solicitud != null && solicitud.getEstado() != null && solicitud.getEstado().equals(Integer.parseInt(EstadoSolicitudPlacasEnum.Iniciado.getValorEnum()))) {
					spBean.setEstado(Integer.parseInt(EstadoSolicitudPlacasEnum.Confirmado.getValorEnum()));
					spBean.setDescEstado(EstadoSolicitudPlacasEnum.Confirmado.getNombreEnum());
					spBean.setFechaSolicitud(solicitud.getFechaSolicitud());
					spBean.setTitular(solicitud.getTitular());
					spBean.setContrato(solicitud.getContrato());
				} else if (!solicitud.getEstado().equals(Integer.valueOf(EstadoSolicitudPlacasEnum.Iniciado.getValorEnum()))) {
					listaSolicitudes.remove(spBean);
					i--;
					validar.setError(true);
					validar.getMensajes().add("Se eliminó la solicitud para " + spBean.getMatricula() + " debido a que ya existía una solicitud confirmada con la misma fecha.");
					log.error("Se eliminó la solicitud para " + spBean.getMatricula() + " debido a que ya existía una solicitud confirmada con la misma fecha.");
				}
			} /*
				 * else { validar.setError(true); validar.getMensajes().add("Debe guardar la/s solicitud/es antes de confirmarla/s."); break; }
				 */
		}

		return validar;
	}

	@Override
	public ValidacionPlacasBean validarFinalizacion(ArrayList<SolicitudPlacaDto> listaSolicitudes) {
		ValidacionPlacasBean validar = new ValidacionPlacasBean();
		validar.setError(false);
		validar.setMensajes(new ArrayList<String>(1));
		BigDecimal idUsuario = utilesColegiado.getIdUsuarioSessionBigDecimal() != null ? utilesColegiado.getIdUsuarioSessionBigDecimal() : null;
		UsuarioVO usuario = servicioUsuario.getUsuario(idUsuario);
		boolean haySolicNula = false;
		for (SolicitudPlacaDto solicitudPlacaDto : listaSolicitudes) {
			SolicitudPlacaDto solicitud = servicioPlacasMatricula.getSolicitudPantalla(solicitudPlacaDto.getIdSolicitud());
			if (solicitud != null && solicitud.getEstado() != null) {
				if (solicitud.getEstado() != null && solicitud.getEstado().equals(Integer.parseInt(EstadoSolicitudPlacasEnum.Tramitado.getValorEnum()))) {
					solicitudPlacaDto.setFechaSolicitud(solicitud.getFechaSolicitud());
					solicitudPlacaDto.setDuplicada(solicitud.getDuplicada());
					solicitudPlacaDto.setTipoDelantera(solicitud.getTipoDelantera());
					solicitudPlacaDto.setTipoTrasera(solicitud.getTipoTrasera());
					solicitudPlacaDto.setTipoAdicional(solicitud.getTipoAdicional());
					solicitudPlacaDto.setRefPropia(solicitud.getRefPropia());
					solicitudPlacaDto.setEstado(Integer.parseInt(EstadoSolicitudPlacasEnum.Finalizado.getValorEnum()));
					solicitudPlacaDto.setDescEstado(EstadoSolicitudPlacasEnum.Finalizado.getNombreEnum());
					solicitudPlacaDto.setUsuario(usuario);
					solicitudPlacaDto.setTitular(solicitud.getTitular());
				} else if (!solicitud.getEstado().equals(Integer.valueOf(EstadoSolicitudPlacasEnum.Tramitado.getValorEnum()))) {
					validar.setError(true);
					validar.getMensajes().add("Se eliminó la solicitud para " + solicitudPlacaDto.getMatricula() + " debido a que ya existía una solicitud finalizada con la misma fecha.");
					log.error("Se eliminó la solicitud para \" + solicitudPlacaDto.getMatricula() + \" debido a que ya existía una solicitud finalizada con la misma fecha.");
					break;
				}
			} else {
				haySolicNula = true;
				break;
			}
		}

		if (haySolicNula) {
			validar.setError(true);
			validar.getMensajes().add("Se ha producido un error al finalizar la/s solicitud/es");
			log.error("Se ha producido un error al finalizar la/s solicitud/es");
		}

		return validar;
	}

}
