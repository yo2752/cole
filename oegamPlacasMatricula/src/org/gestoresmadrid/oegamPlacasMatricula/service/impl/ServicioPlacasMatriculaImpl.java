package org.gestoresmadrid.oegamPlacasMatricula.service.impl;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Array;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.WordUtils;
import org.gestoresmadrid.core.contrato.model.vo.ContratoVO;
import org.gestoresmadrid.core.general.model.vo.ColegiadoVO;
import org.gestoresmadrid.core.general.model.vo.UsuarioVO;
import org.gestoresmadrid.core.historicocreditos.model.dao.enumerados.ConceptoCreditoFacturado;
import org.gestoresmadrid.core.intervinienteTrafico.model.vo.IntervinienteTraficoVO;
import org.gestoresmadrid.core.model.enumerados.TipoInterviniente;
import org.gestoresmadrid.core.personas.model.vo.PersonaPK;
import org.gestoresmadrid.core.personas.model.vo.PersonaVO;
import org.gestoresmadrid.core.springContext.ContextoSpring;
import org.gestoresmadrid.core.trafico.model.vo.JefaturaTraficoVO;
import org.gestoresmadrid.core.trafico.model.vo.TramiteTraficoVO;
import org.gestoresmadrid.core.trafico.solicitudesplacas.model.vo.SolicitudPlacaVO;
import org.gestoresmadrid.core.vehiculo.model.vo.VehiculoVO;
import org.gestoresmadrid.oegam2comun.contrato.model.service.ServicioContrato;
import org.gestoresmadrid.oegam2comun.correo.model.service.ServicioCorreo;
import org.gestoresmadrid.oegam2comun.creditos.model.service.ServicioCredito;
import org.gestoresmadrid.oegam2comun.creditos.model.service.ServicioCreditoFacturado;
import org.gestoresmadrid.oegam2comun.model.service.ServicioNotificacion;
import org.gestoresmadrid.oegam2comun.personas.model.service.ServicioPersona;
import org.gestoresmadrid.oegam2comun.trafico.model.service.ServicioJefaturaTrafico;
import org.gestoresmadrid.oegam2comun.trafico.model.service.ServicioTramiteTrafico;
import org.gestoresmadrid.oegam2comun.trafico.solicitudesplacas.constantes.ConstantesPlacasMatriculacion;
import org.gestoresmadrid.oegam2comun.trafico.solicitudesplacas.enumerados.EstadoSolicitudPlacasEnum;
import org.gestoresmadrid.oegam2comun.vehiculo.model.service.ServicioVehiculo;
import org.gestoresmadrid.oegam2comun.view.dto.NotificacionDto;
import org.gestoresmadrid.oegamComun.acceso.service.impl.UtilesColegiado;
import org.gestoresmadrid.oegamComun.conversor.Conversor;
import org.gestoresmadrid.oegamPlacasMatricula.service.ServicioPersistenciaPlacasMatricula;
import org.gestoresmadrid.oegamPlacasMatricula.service.ServicioPlacasMatricula;
import org.gestoresmadrid.oegamPlacasMatricula.service.ServicioValidacionPlacasMatricula;
import org.gestoresmadrid.oegamPlacasMatricula.view.bean.ValidacionPlacasBean;
import org.gestoresmadrid.oegamPlacasMatricula.view.dto.SolicitudPlacaDto;
import org.gestoresmadrid.placas.utilities.enumerados.TipoPlacasMatriculasEnum;
import org.gestoresmadrid.placas.utilities.enumerados.TipoSolicitudPlacasEnum;
import org.gestoresmadrid.utilidades.components.GestorPropiedades;
import org.gestoresmadrid.utilidades.components.UtilesFecha;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.xhtmlrenderer.pdf.ITextRenderer;

import escrituras.beans.ResultBean;
import hibernate.utiles.constantes.ConstantesHibernate;
import trafico.beans.EstadisticasPlacasBean;
import trafico.utiles.UtilesConversiones;
import trafico.utiles.constantes.Constantes;
import trafico.utiles.enumerados.TipoTramiteTrafico;
import utiles.correo.PropiedadesEmail;
import utilidades.estructuras.Fecha;
import utilidades.web.OegamExcepcion;

@Service
public class ServicioPlacasMatriculaImpl implements ServicioPlacasMatricula {

	private static final long serialVersionUID = 1L;

	private Integer[] arrayTipoPlacas = { 1, 2, 3, 4, 5, 6 };

	final String sFormatoFechaIntervalo = "dd/MM/yyyy";

	List<List<HashMap<Integer, String>>> listaSolicitudAgruVehiculo = null;
	StringBuffer resultadoHtmlAgruVehiculos;
	HashMap<Integer, Integer> mapaSolicitudAgruCreditos = null;
	HashMap<String, Integer> mapaSolicitudAgruNumColegiado = null;
	HashMap<String, HashMap<Integer, Integer>> mapaAgrupaCreditoColegiado;

	@Autowired
	UtilesFecha utilesFecha;

	@Autowired
	private ServicioContrato servicioContrato;

	@Autowired
	private ServicioVehiculo servicioVehiculo;

	@Autowired
	private ServicioJefaturaTrafico servicioJefaturaTrafico;

	@Autowired
	private Conversor conversor;

	@Autowired
	private GestorPropiedades gestorPropiedades;

	@Autowired
	private ServicioCorreo servicioCorreo;

	@Autowired
	private ServicioTramiteTrafico servicioTramiteTrafico;

	@Autowired
	private ServicioCredito servicioCredito;

	@Autowired
	private ServicioPersona servicioPersona;

	@Autowired
	private ServicioPersistenciaPlacasMatricula servicioPersistenciaPlacasMatricula;

	@Autowired
	private ServicioValidacionPlacasMatricula servicioValidacionPlacasMatricula;

	@Autowired
	private UtilesColegiado utilesColegiado;

	public ServicioCorreo getServicioCorreo() {
		if (servicioCorreo == null) {
			servicioCorreo = ContextoSpring.getInstance().getBean(ServicioCorreo.class);
		}
		return servicioCorreo;
	}

	private SolicitudPlacaVO guardarSolicitud(SolicitudPlacaVO solicitud, boolean descontarCreditos) {

		solicitud.setIdUsuario(solicitud.getUsuario().getIdUsuario());

		solicitud = servicioPersistenciaPlacasMatricula.guardarOActualizar(solicitud);

		if (solicitud != null && descontarCreditos) {
			boolean descontar = modificarCreditosSolicitudPlacas(solicitud, true);

			if (!descontar) {
				solicitud.setEstado(ConstantesPlacasMatriculacion.UNO);
				servicioPersistenciaPlacasMatricula.guardarOActualizar(solicitud);
			}

		}

		return solicitud;

	}

	@Override
	public ArrayList<ValidacionPlacasBean> realizarSolicitud(ArrayList<SolicitudPlacaDto> listaSolicitudes) {

		ArrayList<ValidacionPlacasBean> validar;

		// Validamos las distintas solicitudes de placas
		validar = servicioValidacionPlacasMatricula.validarSolicitudes(listaSolicitudes, null);

		for (ValidacionPlacasBean resultadoValidar : validar) {
			if (resultadoValidar.isError()) {
				return validar;
			}
		}

		// Preparamos los datos para ser guardados por el DAO
		ArrayList<SolicitudPlacaVO> solicitudes = prepararSolicitudes(listaSolicitudes);

		// Realizamos el guardado de las solicitudes
		ArrayList<ValidacionPlacasBean> guardar = new ArrayList<>();
		try {
			guardar = guardarSolicitudes(solicitudes, false);
			return guardar;
		} catch (Throwable e) {
			log.error("Error guardando las solicitudes de placas de matriculación.");
			ValidacionPlacasBean resultadoGuardar = new ValidacionPlacasBean();
			ArrayList<String> mensajes = new ArrayList<>();
			resultadoGuardar.setError(true);
			mensajes.add("Error guardando las solicitudes de placas de matriculación.");
			resultadoGuardar.setMensajes(mensajes);
			guardar.add(resultadoGuardar);
			return guardar;
		}

	}

	private ArrayList<SolicitudPlacaVO> prepararSolicitudes(ArrayList<SolicitudPlacaDto> listaSolicitudes) {
		ArrayList<SolicitudPlacaVO> solicitudes = new ArrayList<>();

		for (SolicitudPlacaDto spBean : listaSolicitudes) {
			SolicitudPlacaVO solicitud = prepararSolicitud(spBean);
			solicitudes.add(solicitud);
		}

		return solicitudes;
	}

	@Override
	public ArrayList<ValidacionPlacasBean> confirmarSolicitud(ArrayList<SolicitudPlacaDto> listaSolicitudes) {
		// Preparamos los datos para ser guardados por el DAO
		ArrayList<SolicitudPlacaVO> solicitudes = prepararSolicitudes(listaSolicitudes);

		// Realizamos el guardado de las solicitudes
		ArrayList<ValidacionPlacasBean> guardar = new ArrayList<>();
		try {
			guardar = guardarSolicitudes(solicitudes, true);
			return guardar;
		} catch (Throwable e) {
			log.error("Error confirmando las solicitudes de placas de matriculación.");
			ValidacionPlacasBean resultadoGuardar = new ValidacionPlacasBean();
			ArrayList<String> mensajes = new ArrayList<>();
			resultadoGuardar.setError(true);
			mensajes.add("Error confirmando las solicitudes de placas de matriculación.");
			resultadoGuardar.setMensajes(mensajes);
			guardar.add(resultadoGuardar);
			return guardar;
		}

	}

	@Override
	public ArrayList<ValidacionPlacasBean> finalizarSolicitud(ArrayList<SolicitudPlacaDto> listaSolicitudes) {

		// Preparamos los datos para ser guardados por el DAO
		ArrayList<SolicitudPlacaVO> solicitudes = prepararSolicitudes(listaSolicitudes);

		// Realizamos el guardado de las solicitudes
		ArrayList<ValidacionPlacasBean> guardar = new ArrayList<>();
		try {
			guardar = guardarSolicitudes(solicitudes, false);
			return guardar;
		} catch (Throwable e) {
			log.error("Error finalizando las solicitudes de placas de matriculación.");
			ValidacionPlacasBean resultadoGuardar = new ValidacionPlacasBean();
			ArrayList<String> mensajes = new ArrayList<>();
			resultadoGuardar.setError(true);
			mensajes.add("Error finalizando las solicitudes de placas de matriculación.");
			resultadoGuardar.setMensajes(mensajes);
			guardar.add(resultadoGuardar);
			return guardar;
		}

	}

	@Override
	public boolean cambiarEstadoSolicitud(ArrayList<SolicitudPlacaDto> listaSolicitudes) {

		boolean cambioEstado = false;

		// Preparamos los datos para ser guardados por el DAO
		ArrayList<SolicitudPlacaVO> solicitudes = prepararSolicitudes(listaSolicitudes);

		// Realizamos el guardado de las solicitudes
		try {
			guardarSolicitudes(solicitudes, false);
			cambioEstado = true;
		} catch (Throwable e) {
			log.error("Error guardando las solicitudes de placas de matriculación.");
			cambioEstado = false;
		}

		return cambioEstado;
	}

	private ArrayList<ValidacionPlacasBean> guardarSolicitudes(ArrayList<SolicitudPlacaVO> solicitudes, boolean descontarCreditos) {

		ArrayList<ValidacionPlacasBean> guardar = new ArrayList<>();

		int noElemento = 0;
		for (SolicitudPlacaVO solicitudPlaca : solicitudes) {

			SolicitudPlacaVO resultadoSolicitud = guardarSolicitud(solicitudPlaca, descontarCreditos);

			ValidacionPlacasBean resultadoGuardar = new ValidacionPlacasBean();
			ArrayList<String> mensajes = new ArrayList<>();
			String numColegiado = solicitudPlaca.getNumColegiado();
			String fechaSolicitud = solicitudPlaca.getFechaSolicitud() != null ? solicitudPlaca.getFechaSolicitud().toString() : null;
			String matricula = solicitudPlaca.getMatricula();

			resultadoGuardar.setNumColegiado(numColegiado);
			resultadoGuardar.setFechaSolicitud(fechaSolicitud);
			resultadoGuardar.setMatricula(matricula);
			resultadoGuardar.setNoElemento(noElemento);

			if (resultadoSolicitud == null) {

				if (descontarCreditos) {
					mensajes.add("Compruebe que dispone de créditos suficientes para efectuar la operación.");
				}

				resultadoGuardar.setError(true);
				resultadoGuardar.setMensajes(mensajes);
			} else {
				resultadoGuardar.setIdSolicitud(resultadoSolicitud.getIdSolicitud());
			}

			guardar.add(resultadoGuardar);

			noElemento++;

		}

		return guardar;
	}

	@Override
	public List<SolicitudPlacaDto> convertirListaEnBeanPantallaConsulta(List<SolicitudPlacaVO> lista) {
		List<SolicitudPlacaDto> listaSolicitudes = new ArrayList<>();

		if (lista != null && !lista.isEmpty()) {
			listaSolicitudes = conversor.transform(lista, SolicitudPlacaDto.class);
			for (SolicitudPlacaDto solPlacaDto : listaSolicitudes) {
				solPlacaDto.setDescEstado(EstadoSolicitudPlacasEnum.convertirTexto(String.valueOf(solPlacaDto.getEstado())));
				solPlacaDto.setDescContrato(this.obtenerDescContrato(solPlacaDto));
			}
		}

		return listaSolicitudes;
	}

	private String obtenerDescContrato(SolicitudPlacaDto solicitudPlacaDto) {
		if (solicitudPlacaDto.getContrato() != null) {
			StringBuffer descContrato = new StringBuffer();
			descContrato.append(solicitudPlacaDto.getNumColegiado());
			descContrato.append(" - ");
			descContrato.append(solicitudPlacaDto.getContrato().getVia());
			return descContrato.toString();
		}
		return "";
	}

	private SolicitudPlacaDto convertirEntidadEnBeanPantallaEdicion(SolicitudPlacaVO solicitudPlacaVo) {
		SolicitudPlacaDto solicitudPlacaDto = conversor.transform(solicitudPlacaVo, SolicitudPlacaDto.class);
		solicitudPlacaDto.setDescEstado(EstadoSolicitudPlacasEnum.convertirTexto(String.valueOf(solicitudPlacaDto.getEstado())));

		return solicitudPlacaDto;
	}

	private String analizarMatricula(String matricula) {
		UtilesConversiones utiles = new UtilesConversiones();
		String trozo1;
		String trozo2;
		String trozo3;
		String tipoMatricula = ConstantesPlacasMatriculacion.TIPO_MATRICULA_ORDINARIA;

		// Análisis matrícula ordinaria
		if (matricula.length() == ConstantesPlacasMatriculacion.SIETE.intValue()) {
			trozo1 = matricula.substring(0, 4);
			trozo2 = matricula.substring(4, 7);

			if (utiles.comprobarNumero(trozo1) && !utiles.comprobarNumero(trozo2)) {
				return tipoMatricula;
			}
		}

		if (matricula.length() == ConstantesPlacasMatriculacion.OCHO.intValue()) {
			// Análisis tipo matrícula antigua

			// Análisis matrícula tractor y agrícolas
			trozo1 = matricula.substring(0, 1);
			trozo2 = matricula.substring(1, 5);
			trozo3 = matricula.substring(5, 8);

			if (!utiles.comprobarNumero(trozo1) && trozo1.equals("E") && utiles.comprobarNumero(trozo2) && !utiles.comprobarNumero(trozo3)) {
				tipoMatricula = ConstantesPlacasMatriculacion.TIPO_MATRICULA_TRACTOR;
				return tipoMatricula;
			}

			// Análisis matrícula ciclomotores
			trozo1 = matricula.substring(0, 2);
			trozo2 = matricula.substring(2, 5);
			trozo3 = matricula.substring(5, 8);

			if (!utiles.comprobarNumero(trozo1) && trozo1.substring(0, 1).equals("C") && utiles.comprobarNumero(trozo2) && !utiles.comprobarNumero(trozo3)) {
				tipoMatricula = ConstantesPlacasMatriculacion.TIPO_MATRICULA_CICLOMOTOR;
				return tipoMatricula;
			}
		}

		return tipoMatricula;
	}

	private TipoPlacasMatriculasEnum getTipoPlacaPorDefecto(String matricula) {
		TipoPlacasMatriculasEnum tipoPlaca = null;

		String tipoMatricula = analizarMatricula(matricula);

		if (tipoMatricula.equals(ConstantesPlacasMatriculacion.TIPO_MATRICULA_ORDINARIA)) {
			tipoPlaca = TipoPlacasMatriculasEnum.Turismo_Ordinaria_Larga;
		} else if (tipoMatricula.equals(ConstantesPlacasMatriculacion.TIPO_MATRICULA_TRACTOR)) {
			tipoPlaca = TipoPlacasMatriculasEnum.Tractor;
		} else if (tipoMatricula.equals(ConstantesPlacasMatriculacion.TIPO_MATRICULA_CICLOMOTOR)) {
			tipoPlaca = TipoPlacasMatriculasEnum.Ciclomotor;
		} else {
			tipoPlaca = TipoPlacasMatriculasEnum.Turismo_Ordinaria_Larga;
		}
		return tipoPlaca;
	}

	private SolicitudPlacaVO prepararSolicitud(SolicitudPlacaDto solicitudPlacaDto) {
		// Rellenamos el contrato a través de su id
		ContratoVO contrato = null;
		if (solicitudPlacaDto.getContrato() != null && solicitudPlacaDto.getContrato().getIdContrato() != null) {
			contrato = servicioContrato.getContrato(new BigDecimal(solicitudPlacaDto.getContrato().getIdContrato()));
			solicitudPlacaDto.setContrato(contrato);
		}

		// Rellenamos el vehículo
		VehiculoVO vehiculo = null;
		if (solicitudPlacaDto.getVehiculo() != null) {
			String matricula = solicitudPlacaDto.getMatricula();
			String numColegiado = solicitudPlacaDto.getNumColegiado();
			vehiculo = servicioVehiculo.getVehiculoVO(solicitudPlacaDto.getVehiculo().getIdVehiculo(), numColegiado, matricula, null, null, null);
			if (vehiculo != null) {
				solicitudPlacaDto.setVehiculo(vehiculo);
			}
		}

		// Obtenemos el titular a través del NIF
		PersonaVO titular = new PersonaVO();
		if ((solicitudPlacaDto.getTitular() != null && solicitudPlacaDto.getTitular().getId() != null && solicitudPlacaDto.getTitular().getId().getNif() != null)) {
			titular = servicioPersona.getPersonaVO(solicitudPlacaDto.getTitular().getId().getNif(), solicitudPlacaDto.getNumColegiado());
		} else if (solicitudPlacaDto.getNifTitular() != null) {
			titular = servicioPersona.getPersonaVO(solicitudPlacaDto.getNifTitular(), solicitudPlacaDto.getNumColegiado());
		}
		if (titular != null) {
			solicitudPlacaDto.setTitular(titular);
		}

		// Trámite de tráfico
		if (solicitudPlacaDto.getTramiteTrafico() != null && solicitudPlacaDto.getTramiteTrafico().getNumExpediente() != null) {
			solicitudPlacaDto.setTramiteTrafico(servicioTramiteTrafico.getTramite(solicitudPlacaDto.getTramiteTrafico().getNumExpediente(), false));
		}

		return conversor.transform(solicitudPlacaDto, SolicitudPlacaVO.class);
	}

	@Override
	public SolicitudPlacaVO getSolicitud(Integer idSolicitud, String... initialized) {
		if (idSolicitud == null) {
			return null;
		}
		return servicioPersistenciaPlacasMatricula.getSolicitud(idSolicitud, initialized);
	}

	@Override
	public SolicitudPlacaDto getSolicitudPorClaveUnica(SolicitudPlacaDto spBean) {
		SolicitudPlacaVO solicitud = prepararSolicitud(spBean);
		SolicitudPlacaVO solicitudGuardada = servicioPersistenciaPlacasMatricula.getSolicitudPorClaveUnica(solicitud);

		SolicitudPlacaDto solicitudRetorno = null;
		if (solicitudGuardada != null) {
			solicitudRetorno = this.convertirEntidadEnBeanPantallaEdicion(solicitudGuardada);
			return solicitudRetorno;
		} else {
			return solicitudRetorno;
		}
	}

	@Override
	public SolicitudPlacaDto getSolicitudPantalla(Integer idSolicitud) {
		SolicitudPlacaDto spBean = null;

		// Preparo el array de entidades que necesitaré inicializar
		Integer numEntidades = Integer.valueOf(2);
		String[] entidades = (String[]) Array.newInstance(String.class, numEntidades);
		entidades[0] = ConstantesHibernate.USUARIO_PROPERTY;
		entidades[1] = "titular";

		// Obtengo la solicitud guardada en BBDD
		SolicitudPlacaVO solicitud = getSolicitud(idSolicitud, entidades);

		// Convierto la solicitud obtenida de BBDD a un bean de pantalla
		if (solicitud != null) {
			spBean = this.convertirEntidadEnBeanPantallaEdicion(solicitud);
		}

		return spBean;
	}

	private TipoSolicitudPlacasEnum getTipoSolicitudPlaca(TipoPlacasMatriculasEnum tipoPlaca) {
		TipoSolicitudPlacasEnum tipoSolicitud;

		switch (tipoPlaca) {
			case Turismo_Ordinaria_Larga:
				tipoSolicitud = TipoSolicitudPlacasEnum.Solicitud_Placa_Ordinaria_Larga;
				break;
			case Turismo_Ordinaria_Corta:
				tipoSolicitud = TipoSolicitudPlacasEnum.Solicitud_Placa_Ordinaria_Corta_Alfa_Romeo;
				break;
			case Motocicleta:
				tipoSolicitud = TipoSolicitudPlacasEnum.Solicitud_Placa_Motocicleta;
				break;
			case Moto_Corta:
				tipoSolicitud = TipoSolicitudPlacasEnum.Solicitud_Placa_Motocicleta_Trial;
				break;
			case Tractor:
				tipoSolicitud = TipoSolicitudPlacasEnum.Solicitud_Placa_Vehiculo_Especial;
				break;
			case Ciclomotor:
				tipoSolicitud = TipoSolicitudPlacasEnum.Solicitud_Placa_Ciclomotor;
				break;
			case TaxiVTC:
				tipoSolicitud = TipoSolicitudPlacasEnum.Solicitud_Placa_TaxiVTC;
				break;
			default:
				tipoSolicitud = null;
				break;
		}

		return tipoSolicitud;
	}

	private boolean modificarCreditosSolicitudPlacas(SolicitudPlacaVO solicitud, boolean incremental) {
		boolean descontar = false;
		ArrayList<TipoSolicitudPlacasEnum> listaCobros = new ArrayList<>();
		ArrayList<TipoSolicitudPlacasEnum> listaDevoluciones = new ArrayList<>();
		Integer numeroCreditos;
		/*
		 * TODO: Esto se tiene que probar bien. De momento se coloca 1 crédito fijo ya que el PQ se encargará de sumarlo o descontarlo en función de que el tipo de crédito sea incremental o decremental en base de datos
		 */
//		if (incremental) {
//			numeroCreditos = Integer.valueOf(1);
//		} else {
		numeroCreditos = Integer.valueOf(1);
//		}

		TipoSolicitudPlacasEnum tipoSolicitudPlaca;

		if (solicitud.getTipoDelantera() != null) {
			tipoSolicitudPlaca = getTipoSolicitudPlaca(TipoPlacasMatriculasEnum.convertir(String.valueOf(solicitud.getTipoDelantera())));
			listaCobros.add(tipoSolicitudPlaca);
		}

		if (solicitud.getTipoTrasera() != null) {
			tipoSolicitudPlaca = getTipoSolicitudPlaca(TipoPlacasMatriculasEnum.convertir(String.valueOf(solicitud.getTipoTrasera())));
			listaCobros.add(tipoSolicitudPlaca);
		}

		if (solicitud.getTipoAdicional() != null) {
			tipoSolicitudPlaca = getTipoSolicitudPlaca(TipoPlacasMatriculasEnum.convertir(String.valueOf(solicitud.getTipoAdicional())));
			listaCobros.add(tipoSolicitudPlaca);
		}

		for (TipoSolicitudPlacasEnum tipoSolicitud : listaCobros) {
			descontar = descuentoCreditos(solicitud, numeroCreditos, tipoSolicitud);
			if (descontar) {
				listaDevoluciones.add(tipoSolicitud);
			} else {
				if (!listaDevoluciones.isEmpty()) {
					devolverCreditos(solicitud, new BigDecimal(numeroCreditos).negate().intValue(), listaDevoluciones);
					log.error("SOLICITUD_PLACAS: Error al realizar el cargo de créditos para alguna de las placas de la solicitud: " + solicitud.getIdSolicitud());
					log.error("SOLICITUD_PLACAS: Se ha procedido a la devolución de los créditos cargados en la solicitud: " + solicitud.getIdSolicitud()
							+ " debido a errores en el cargo de créditos para alguna de las placas.");

					try {
						ServicioCreditoFacturado servicioCreditoFacturado = (ServicioCreditoFacturado) ContextoSpring.getInstance().getBean(Constantes.SERVICIO_HISTORICO_CREDITO);

						if (servicioCreditoFacturado != null) {
							servicioCreditoFacturado.eliminarGasto(solicitud);
						}
					} catch (Exception e) {
						log.error("Se ha producido un error al guardar historico de creditos para el tramite: " + solicitud.getIdSolicitud(), e);
					}
				}
				return descontar;
			}
		}

		return descontar;

	}

	private void devolverCreditos(SolicitudPlacaVO solicitud, Integer numeroCreditos, List<TipoSolicitudPlacasEnum> listaDevoluciones) {
		for (TipoSolicitudPlacasEnum tipoSolicitudPlaca : listaDevoluciones) {
			descuentoCreditos(solicitud, numeroCreditos, tipoSolicitudPlaca);
		}
	}

	private boolean descuentoCreditos(SolicitudPlacaVO solicitud, Integer numeroCreditos, TipoSolicitudPlacasEnum tipoSolicitudPlaca) {

		boolean descontar = false;

		String idContrato = solicitud != null && solicitud.getContrato() != null ? String.valueOf(solicitud.getContrato().getIdContrato()) : null;
		BigDecimal idUsuario = solicitud != null && solicitud.getUsuario() != null ? new BigDecimal(solicitud.getUsuario().getIdUsuario()) : null;
		if (idContrato != null && idUsuario != null) {
			String[] tramites = null;
			if (solicitud.getNumExpediente() != null) {
				tramites = new String[1];
				tramites[0] = solicitud.getNumExpediente().toString();
			}
			ResultBean res = servicioCredito.descontarCreditos(TipoTramiteTrafico.Solicitud_Placas.getValorEnum(), BigDecimal.valueOf(solicitud.getContrato().getIdContrato()), new BigDecimal(
					numeroCreditos), idUsuario, ConceptoCreditoFacturado.SPL, tramites);
			if (res.getError()) {
				log.error("ERROR DESCONTAR CREDITOS");
				log.error("CONTRATO: " + idContrato);
				log.error("ID_USUARIO: " + String.valueOf(idUsuario));
			} else {
				descontar = true;
			}
		}

		return descontar;
	}

	private SolicitudPlacaDto getSolicitudPorMatricula(SolicitudPlacaDto solicitudPlacaDto) {
		SolicitudPlacaDto solicitudPlacaBeanRetorno = null;
		solicitudPlacaDto.setFechaSolicitud(new Fecha(utilesFecha.getFechaHoy()));
		SolicitudPlacaVO solicitud = servicioPersistenciaPlacasMatricula.getSolicitudPorClaveUnica(prepararSolicitud(solicitudPlacaDto));

		if (solicitud != null) {
			solicitudPlacaDto = this.convertirEntidadEnBeanPantallaEdicion(solicitud);
			solicitudPlacaBeanRetorno = solicitudPlacaDto;
		}

		return solicitudPlacaBeanRetorno;
	}

	@Override
	public ArrayList<SolicitudPlacaDto> crearNuevasSolicitudes(List<SolicitudPlacaDto> listaSolicitudes, String[] expedientes, UsuarioVO usuario, Integer idContrato) throws OegamExcepcion {
		ArrayList<SolicitudPlacaDto> listaSolicitudesExistente = new ArrayList<>();
		// Si vienen vehículos partimos de consulta de trámites
		SolicitudPlacaDto solicitudPlacaBean = null;

		ArrayList<TramiteTraficoVO> tramites = new ArrayList<>();

		// Este sería el caso en el que se han mandado expedientes
		// desde consulta de trámites de tráfico
		if (expedientes != null) {
			for (String expediente : expedientes) {
				TramiteTraficoVO tramite = servicioTramiteTrafico.getTramite(new BigDecimal(expediente), true);
				tramites.add(tramite);
			}
		}

		for (TramiteTraficoVO tramite : tramites) {
			solicitudPlacaBean = new SolicitudPlacaDto();

			List<IntervinienteTraficoVO> intervinientes = new ArrayList<>();
			intervinientes.addAll(tramite.getIntervinienteTraficos());
			for (IntervinienteTraficoVO interviniente : intervinientes) {
				if (interviniente.getTipoIntervinienteVO().getTipoInterviniente().equals(TipoInterviniente.Titular.getValorEnum())) {
					PersonaVO personaBean = new PersonaVO();
					personaBean.setId(new PersonaPK());
					personaBean.getId().setNif(interviniente.getId().getNif());
					solicitudPlacaBean.setTitular(personaBean);
				}
			}

			if (tramite.getVehiculo() != null) {
				solicitudPlacaBean.setBastidor(tramite.getVehiculo().getBastidor());
				solicitudPlacaBean.setMatricula(tramite.getVehiculo().getMatricula());
			}
			solicitudPlacaBean.setNumExpediente(tramite.getNumExpediente());
			if (solicitudPlacaBean.getTitular() != null) {
				solicitudPlacaBean.setNifTitular(solicitudPlacaBean.getTitular().getId().getNif());
			}
			listaSolicitudes.add(solicitudPlacaBean);
		}

		for (SolicitudPlacaDto spBean : listaSolicitudes) {

			SolicitudPlacaDto spBeanAux;

			// Comprobar si la matricula existe o es nueva solicitud
			spBean.setUsuario(usuario);
			spBeanAux = getSolicitudPorMatricula(spBean);

			// Si es nueva, se le añaden los datos correspondientes
			if (spBeanAux == null) {
				TipoPlacasMatriculasEnum tipoPlaca = getTipoPlacaPorDefecto(spBean.getMatricula());
				spBeanAux = new SolicitudPlacaDto();
				spBeanAux.setBastidor(spBean.getBastidor());
				spBeanAux.setMatricula(spBean.getMatricula());
				spBeanAux.setNifTitular(spBean.getNifTitular());
				spBeanAux.setEstado(Integer.parseInt(EstadoSolicitudPlacasEnum.Iniciado.getValorEnum()));
				spBeanAux.setDescEstado(EstadoSolicitudPlacasEnum.Iniciado.getNombreEnum());
				spBeanAux.setFechaSolicitud(new Fecha(utilesFecha.getFechaHoy()));
				spBeanAux.setContrato(servicioContrato.getContrato(new BigDecimal(idContrato)));
				spBeanAux.setIdContrato(idContrato);
				spBeanAux.setUsuario(usuario);
				spBeanAux.setTipoDelantera(Integer.parseInt(tipoPlaca.getValorEnum()));
				// spBeanAux.setVehiculo(spBean.getVehiculo());
				spBeanAux.setTitular(spBean.getTitular());
				spBeanAux.setTramiteTrafico(spBean.getTramiteTrafico());
				spBeanAux.setNumExpediente(spBean.getNumExpediente());
				spBeanAux.setTipoVehiculo(spBean.getTipoVehiculo());
			}

			listaSolicitudesExistente.add(spBeanAux);
		}

		return listaSolicitudesExistente;
	}

	private void crearNotificacion(SolicitudPlacaDto spBean) {
		ServicioNotificacion servicioNotificacion = ContextoSpring.getInstance().getBean(ServicioNotificacion.class);
		NotificacionDto notifDto = new NotificacionDto();
		notifDto.setTipoTramite(TipoTramiteTrafico.Solicitud_Placas.getValorEnum());
		notifDto.setIdTramite(BigDecimal.valueOf(spBean.getIdSolicitud()));
		notifDto.setEstadoAnt(new BigDecimal(EstadoSolicitudPlacasEnum.Tramitado.getValorEnum()));
		notifDto.setEstadoNue(new BigDecimal(EstadoSolicitudPlacasEnum.Finalizado.getValorEnum()));
		notifDto.setDescripcion(ConstantesPlacasMatriculacion.NOTIFICACION_FINALIZADO);
		notifDto.setIdUsuario(spBean.getUsuario().getIdUsuario());
		servicioNotificacion.crearNotificacion(notifDto);
	}

	@Override
	public boolean notificar(SolicitudPlacaDto spBean, boolean notificarOegam) {

		boolean enviado;
		// Asumimos que si se crea notificación en Oegam se trata de un correo al usuario, y si no, al colegio
		if (notificarOegam) {
			enviado = sendMailUsuario(spBean);
		} else {
			enviado = sendMailColegio(spBean);
		}

		if (enviado && notificarOegam) {
			crearNotificacion(spBean);
		}

		return enviado;

	}

	@Override
	public HashMap<Integer, Boolean> notificarVarias(ArrayList<SolicitudPlacaDto> listaSolicitudes, boolean notificarOegam) {

		HashMap<Integer, Boolean> notificaciones = new HashMap<>();

		for (SolicitudPlacaDto spBean : listaSolicitudes) {

			boolean enviado = notificar(spBean, notificarOegam);

			if (enviado) {
				notificaciones.put(spBean.getIdSolicitud(), Boolean.valueOf(enviado));
			}

		}

		return notificaciones;
	}

	private PropiedadesEmail configurarCorreo(String propiedadRecipient, String propiedadBcc, String propiedadSubject, String propiedadBody) {
		PropiedadesEmail propiedades = new PropiedadesEmail();

		propiedades.setPara(propiedadRecipient);
		propiedades.setCopia(propiedadBcc);
		propiedades.setAsunto(propiedadSubject);
		propiedades.setCuerpo(propiedadBody);

		return propiedades;
	}

	private boolean sendMailUsuario(SolicitudPlacaDto spBean) {
		boolean correcto = false;

		String subject;
		String recipient;
		String bcc;

		String correoHabilitado = gestorPropiedades.valorPropertie(ConstantesPlacasMatriculacion.NOTIFICATION_MAIL_HABILITADO) != null ? gestorPropiedades.valorPropertie(
				ConstantesPlacasMatriculacion.NOTIFICATION_MAIL_HABILITADO) : null;

		// El properties debe habilitar el envio de correo directo al colegiado, en caso contrario, debe asumirse que no estamos en producción y no se envia correo al colegiado
		String userMail;
		if (correoHabilitado != null && !"".equals(correoHabilitado) && correoHabilitado.equals("SI")) {
			userMail = spBean.getUsuario().getCorreoElectronico();
		} else {
			userMail = gestorPropiedades.valorPropertie(ConstantesPlacasMatriculacion.NOTIFICATION_MAIL_RECIPIENT);
		}

		String matricula = spBean.getMatricula() != null ? spBean.getMatricula() : "";
		String delantera = spBean.getTipoDelantera() != null ? TipoPlacasMatriculasEnum.convertir(spBean.getTipoDelantera()) : "No Solicitada";
		String trasera = spBean.getTipoTrasera() != null ? TipoPlacasMatriculasEnum.convertir(spBean.getTipoTrasera()) : "No Solicitada";
		String adicional = spBean.getTipoAdicional() != null ? TipoPlacasMatriculasEnum.convertir(spBean.getTipoAdicional()) : "No solicitada";
		String duplicada = spBean.getDuplicada() != null && spBean.getDuplicada() > 0 ? "Si" : "No";

		String textoAniadir = "<p>Su solicitud de placas para la matrícula " + matricula + " ha finalizado satisfactoriamente.</p>" + "<p><u>Detalle de su solicitud:</u></p>" + "<ul>"
				+ "<li><strong>Matrícula</strong>: " + matricula + "</li>" + "<li><strong>Placa delantera</strong>: " + delantera + "</li>" + "<li><strong>Placa trasera</strong>: " + trasera + "</li>"
				+ "<li><strong>Placa adicional</strong>: " + adicional + "</li>" + "<li><strong>Placa duplicada</strong>: " + duplicada + "</li>" + "</ul>";
		subject = gestorPropiedades.valorPropertie(ConstantesPlacasMatriculacion.NOTIFICATION_MAIL_SUBJECT);
		recipient = userMail;
		bcc = gestorPropiedades.valorPropertie(ConstantesPlacasMatriculacion.NOTIFICATION_MAIL_BCC);

		PropiedadesEmail propiedadesEmail = configurarCorreo(recipient, bcc, subject, textoAniadir);

		correcto = sendMail(propiedadesEmail);
		if (correcto) {
			StringBuffer sb = new StringBuffer();
			sb.append("Se ha mandado un correo referente a solicitud de placas.");
			sb.append(" El colegiado que solicitó la placa es: ");
			sb.append(spBean.getNumColegiado());
			sb.append(" La matricula es: ");
			sb.append(spBean.getMatricula());
			sb.append(" Los destinatarios del correo han sido: ");
			sb.append(recipient);
			sb.append(", ");
			sb.append(bcc);
			log.error(sb);
		} else {
			StringBuffer sb = new StringBuffer();
			sb.append("No se ha podido enviar el correo de placas. ");
			sb.append(" La matricula es: ");
			sb.append(spBean.getMatricula());
			sb.append(" Los destinatarios del correo eran: ");
			sb.append(recipient);
			sb.append(", ");
			sb.append(bcc);
			log.error(sb);
		}
		return correcto;
	}

	private boolean sendMailColegio(SolicitudPlacaDto spBean) {
		boolean correcto = false;

		String subject;
		String recipient;
		String Bcc;

		String jefaturaProv = utilesColegiado.getIdJefaturaSesion();
		JefaturaTraficoVO jefaturaTramite = servicioJefaturaTrafico.getJefaturaTrafico(jefaturaProv);
		String correoJefatura = null;

		if (jefaturaProv != null) {
			if (jefaturaProv.equals("M")) {
				correoJefatura = gestorPropiedades.valorPropertie(ConstantesPlacasMatriculacion.ORDER_MAIL_JEFATURA_MADRID);
			} else if (jefaturaProv.equals("M1")) {
				correoJefatura = gestorPropiedades.valorPropertie(ConstantesPlacasMatriculacion.ORDER_MAIL_JEFATURA_ALCORCON);
			} else {
				correoJefatura = gestorPropiedades.valorPropertie(ConstantesPlacasMatriculacion.ORDER_MAIL_JEFATURA_ESTANDAR);
			}
		} else {
			correoJefatura = gestorPropiedades.valorPropertie(ConstantesPlacasMatriculacion.ORDER_MAIL_JEFATURA_ESTANDAR);
		}

		String userMail = gestorPropiedades.valorPropertie(ConstantesPlacasMatriculacion.ORDER_MAIL_COLEGIO_RECIPIENT);

		subject = ConstantesPlacasMatriculacion.ORDER_MAIL_SUBJECT;
		recipient = userMail;
		Bcc = gestorPropiedades.valorPropertie(ConstantesPlacasMatriculacion.ORDER_MAIL_COLEGIO_BCC);

		if (correoJefatura != null) {
			if (Bcc != null && !"".equals(Bcc)) {
				Bcc = Bcc.concat("," + correoJefatura);
			} else {
				Bcc = correoJefatura;
			}
		}

		String numColegiado = String.valueOf(spBean.getTitular().getId().getNumColegiado());

		ContratoVO contrato = servicioContrato.getContrato(new BigDecimal(spBean.getContrato().getIdContrato()));

		String municipio = contrato.getMunicipio().getNombre();

		String bastidor = spBean.getBastidor();

		ColegiadoVO colegiado = contrato.getColegiado();
		String apellidosNombre = colegiado.getUsuario().getApellidosNombre();
		String dniColegiado = colegiado.getUsuario().getNif();

		String domicilio = contrato.getIdTipoVia() + " " + contrato.getVia() + ", " + contrato.getNumero() + "<br />" + contrato.getCodPostal() + " " + municipio + " ( " + contrato.getProvincia()
				.getNombre() + " )";

		String titular = null;
		PersonaVO persona = servicioPersona.getPersonaVO(spBean.getTitular().getId().getNif(), spBean.getTitular().getId().getNumColegiado());
		titular = persona.getApellido1RazonSocial() + " " + (persona.getApellido2() != null ? persona.getApellido2() : " ") + " " + (persona.getNombre() != null ? persona.getNombre() : " ");

		String fechaSolicitud = spBean.getFechaSolicitud() != null ? spBean.getFechaSolicitud().toString() : "";

		String matricula = spBean.getMatricula() != null ? spBean.getMatricula() : "";
		String delantera = spBean.getTipoDelantera() != null ? TipoPlacasMatriculasEnum.convertir(spBean.getTipoDelantera()) : "No Solicitada";
		String trasera = spBean.getTipoTrasera() != null ? TipoPlacasMatriculasEnum.convertir(spBean.getTipoTrasera()) : "No Solicitada";
		String adicional = spBean.getTipoAdicional() != null ? TipoPlacasMatriculasEnum.convertir(spBean.getTipoAdicional()) : "No solicitada";
		String jefatura = jefaturaTramite != null && jefaturaTramite.getDescripcion() != null ? jefaturaTramite.getDescripcion() : "Sin jefatura asociada";
		String duplicada = spBean.getDuplicada() != null && spBean.getDuplicada() > 0 ? "Si" : "No";
		String textoAniadir = "<p>Se ha confirmado una nueva solicitud de placa para la matrícula: <strong>" + matricula + "</strong></p>" + "<h2><u>Detalle de la solicitud:</u></h2>"
				+ "<h3>Datos de la solicitud</h3>" + "<ul>" + "<li><strong>Fecha de la solicitud</strong>: " + fechaSolicitud + "</li>" + "<li><strong>Placa delantera</strong>: " + delantera + "</li>"
				+ "<li><strong>Placa trasera</strong>: " + trasera + "</li>" + "<li><strong>Placa adicional</strong>: " + adicional + "</li>" + "<li><strong>Placa duplicada</strong>: " + duplicada
				+ "</li>" + "</ul>" + "<h3>Datos del vehículo</h3>" + "<ul>" + "<li><strong>Titular</strong>: " + WordUtils.capitalizeFully(StringUtils.lowerCase(titular)) + "</li>"
				+ "<li><strong>Matrícula</strong>: " + matricula + "</li>" + "<li><strong>Bastidor</strong>: " + bastidor + "</li>" + "</ul>" + "<h3>Datos del colegiado</h3>" + "<ul>"
				+ "<li><strong>Jefatura Provincial</strong>: " + WordUtils.capitalizeFully(StringUtils.lowerCase(jefatura)) + "</li>" + "<li><strong>Número de Colegiado</strong>: " + numColegiado
				+ "</li>" + "<li><strong>Presentador</strong>: " + WordUtils.capitalizeFully(StringUtils.lowerCase(apellidosNombre)) + " (" + dniColegiado + ")</li>"
				+ "<li><strong>Domicilio</strong>: " + WordUtils.capitalizeFully(StringUtils.lowerCase(domicilio)) + "</li>" + "</ul>";

		PropiedadesEmail propiedadesEmail = configurarCorreo(recipient, Bcc, subject, textoAniadir);

		correcto = sendMail(propiedadesEmail);

		if (correcto) {
			StringBuffer sb = new StringBuffer();
			sb.append("Se ha mandado un correo referente a solicitud de placas.");
			sb.append(" El colegiado que solicitó la placa es: ");
			sb.append(spBean.getNumColegiado());
			sb.append(" La matricula es: ");
			sb.append(spBean.getMatricula());
			sb.append(" Los destinatarios del correo han sido: ");
			sb.append(recipient);
			sb.append(", ");
			sb.append(Bcc);
			log.error(sb);
		} else {
			StringBuffer sb = new StringBuffer();
			sb.append("No se ha podido enviar el correo de placas. ");
			sb.append(" La matricula es: ");
			sb.append(spBean.getMatricula());
			sb.append(" Los destinatarios del correo eran: ");
			sb.append(recipient);
			sb.append(", ");
			sb.append(Bcc);
			log.error(sb);
		}
		return correcto;
	}

	private boolean sendMail(PropiedadesEmail propiedadesEmail) {
		boolean correcto;

		ResultBean resultBean = null;
		try {
			resultBean = getServicioCorreo().enviarCorreo(propiedadesEmail.getCuerpo(), propiedadesEmail.getTextoPlano(), propiedadesEmail.getRemitente(), propiedadesEmail.getAsunto(),
					propiedadesEmail.getPara(), propiedadesEmail.getCopia(), null, null);
			if (resultBean == null || resultBean.getError())
				throw new OegamExcepcion("Error en la notificacion de error servicio");

		} catch (OegamExcepcion | IOException e) {
			e.printStackTrace();
		}

		if (resultBean != null) {
			if (resultBean.getError()) {
				correcto = false;
			} else {
				correcto = true;
			}
		} else {
			correcto = false;
		}

		return correcto;
	}

	private void generaTablaSinAgrupacion(EstadisticasPlacasBean estadisticasPlacasBean) {
		List<SolicitudPlacaVO> listaSolicitud = servicioPersistenciaPlacasMatricula.generarEstadisticasSinAgrupacion(estadisticasPlacasBean.getFecha().getFechaInicio(), estadisticasPlacasBean
				.getFecha().getFechaFin());
		resultadoHtmlAgruVehiculos = new StringBuffer();
		resultadoHtmlAgruVehiculos.append("<HTML>");
		resultadoHtmlAgruVehiculos.append("<HEAD> <TITLE> <span style=\"font-size:14pt;font-family:Tunga;margin-left:20px;\"> " + "<th> ESTADÍSTICAS DE PLACAS DE MATRICULACIÓN  <br/>" + "Período del "
				+ utilesFecha.formatoFecha(sFormatoFechaIntervalo, estadisticasPlacasBean.getFecha().getFechaInicio()) + " hasta " + utilesFecha.formatoFecha(sFormatoFechaIntervalo,
						estadisticasPlacasBean.getFecha().getFechaFin()) + "</th></span></TITLE></HEAD>");
		resultadoHtmlAgruVehiculos.append("<BODY>");
		if (listaSolicitud != null) {
			resultadoHtmlAgruVehiculos.append("<br/> <h3>GENERAL PLACAS DE MATRÍCULA:</h3>");
			resultadoHtmlAgruVehiculos.append("<table border='1' width='100%'> <tr> " + "<th>Matrícula</th> " + "<th>Col.</th> " + "<th>Placa delantera</th> " + "<th>Placa trasera</th> "
					+ "<th>Placa adicional</th> " + "<th>Fecha</th></tr>");
			for (SolicitudPlacaVO solicitudPlaca : listaSolicitud) {
				resultadoHtmlAgruVehiculos.append("<tr>");
				resultadoHtmlAgruVehiculos.append("<td>");
				resultadoHtmlAgruVehiculos.append(solicitudPlaca.getMatricula() != null ? solicitudPlaca.getMatricula() : "");
				resultadoHtmlAgruVehiculos.append("</td>");
				resultadoHtmlAgruVehiculos.append("<td>");
				resultadoHtmlAgruVehiculos.append(solicitudPlaca.getNumColegiado());
				resultadoHtmlAgruVehiculos.append("</td>");
				resultadoHtmlAgruVehiculos.append("<td>");
				resultadoHtmlAgruVehiculos.append(solicitudPlaca.getTipoDelantera() != null ? TipoPlacasMatriculasEnum.convertir(solicitudPlaca.getTipoDelantera()) : "");
				resultadoHtmlAgruVehiculos.append("</td>");
				resultadoHtmlAgruVehiculos.append("<td>");
				resultadoHtmlAgruVehiculos.append(solicitudPlaca.getTipoTrasera() != null ? TipoPlacasMatriculasEnum.convertir(solicitudPlaca.getTipoTrasera()) : "");
				resultadoHtmlAgruVehiculos.append("</td>");
				resultadoHtmlAgruVehiculos.append("<td>");
				resultadoHtmlAgruVehiculos.append(solicitudPlaca.getTipoAdicional() != null ? TipoPlacasMatriculasEnum.convertir(solicitudPlaca.getTipoAdicional()) : "");
				resultadoHtmlAgruVehiculos.append("</td>");
				resultadoHtmlAgruVehiculos.append("<td>");
				resultadoHtmlAgruVehiculos.append(solicitudPlaca.getFechaSolicitud());
				resultadoHtmlAgruVehiculos.append("</td>");
				resultadoHtmlAgruVehiculos.append("</tr>");
			}
			resultadoHtmlAgruVehiculos.append("</table>");
		}
	}

	private void generaTablaAgrupadaPorVehiculo(EstadisticasPlacasBean estadisticasPlacasBean) {
		if (estadisticasPlacasBean.isAgrTipoVehiculo()) {
			listaSolicitudAgruVehiculo = servicioPersistenciaPlacasMatricula.generarEstadisticasAgrupadoPorVehiculo(estadisticasPlacasBean.getFecha().getFechaInicio(), estadisticasPlacasBean
					.getFecha().getFechaFin());

			if (!listaSolicitudAgruVehiculo.isEmpty()) {
				resultadoHtmlAgruVehiculos.append("<br/> <th>SOLICITUDES DE PLACAS SEGÚN EL TIPO DE VEHÍCULO:</th>");
				resultadoHtmlAgruVehiculos.append("<table border='1' width='100%'> <tr> " + "<th>Tipo de vehículo</th>");
				resultadoHtmlAgruVehiculos.append("<th>Total de solicitudes de placas</th></tr>");
				for (List<HashMap<Integer, String>> listaVehiculos : listaSolicitudAgruVehiculo) {
					if (listaVehiculos.get(0).get(1) != null && listaVehiculos.get(0).get(0) != null) {
						resultadoHtmlAgruVehiculos.append("<tr>");
						resultadoHtmlAgruVehiculos.append("<td>");
						resultadoHtmlAgruVehiculos.append(listaVehiculos.get(0).get(0));
						resultadoHtmlAgruVehiculos.append("</td>");
						resultadoHtmlAgruVehiculos.append("<td>");
						resultadoHtmlAgruVehiculos.append(listaVehiculos.get(0).get(1));
						resultadoHtmlAgruVehiculos.append("</td>");
						resultadoHtmlAgruVehiculos.append("</tr>");
					}
				}
				resultadoHtmlAgruVehiculos.append("</table>");
			}
		}
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	private void generaTablaAgrupadaCreditosColegiado(EstadisticasPlacasBean estadisticasPlacasBean) {
		if (estadisticasPlacasBean.isAgrTipoCredito()) {
			mapaAgrupaCreditoColegiado = servicioPersistenciaPlacasMatricula.generarEstadisticasAgrupadoPorCreditosColegiado(estadisticasPlacasBean.getFecha().getFechaInicio(), estadisticasPlacasBean
					.getFecha().getFechaFin());
			mapaSolicitudAgruNumColegiado = servicioPersistenciaPlacasMatricula.generarEstadisticasAgrupadoPorNumColegiado(estadisticasPlacasBean.getFecha().getFechaInicio(), estadisticasPlacasBean
					.getFecha().getFechaFin());
			if (!mapaAgrupaCreditoColegiado.isEmpty()) {
				resultadoHtmlAgruVehiculos.append("<br/> <th>AGRUPACIÓN POR TIPO DE CRÉDITO Y NÚMERO DE COLEGIADO:</th>");
				resultadoHtmlAgruVehiculos.append("<table border='1' width='100%'> <tr> " + "<th>Número de colegiado</th> " + "<th>Turismo Ordinaria Larga<br/> CTP1</th>"
						+ "<th>Turismo Ordinaria Corta<br/> CTP2</th>" + "<th>Motocicleta<br/> CTP3</th>" + "<th>Moto Corta<br/> CTP4</th>" + "<th>Tractor<br/> CTP5</th>"
						+ "<th>Ciclomotor<br/> CTP6</th>" + "<th>Total de placas</th></tr>");

				Iterator entries = mapaAgrupaCreditoColegiado.entrySet().iterator();
				while (entries.hasNext()) {
					Entry thisEntry = (Entry) entries.next();
					String colegiado = (String) thisEntry.getKey();
					HashMap<Integer, Integer> mapaTipoPlacaDeColegiado = (HashMap<Integer, Integer>) thisEntry.getValue();
					resultadoHtmlAgruVehiculos.append("<tr>");
					resultadoHtmlAgruVehiculos.append("<td>");
					resultadoHtmlAgruVehiculos.append(colegiado);
					resultadoHtmlAgruVehiculos.append("</td>");
					resultadoHtmlAgruVehiculos.append("<td>");
					resultadoHtmlAgruVehiculos.append(mapaTipoPlacaDeColegiado.get(arrayTipoPlacas[0]) != null ? mapaTipoPlacaDeColegiado.get(arrayTipoPlacas[0]) : "0");
					resultadoHtmlAgruVehiculos.append("</td>");
					resultadoHtmlAgruVehiculos.append("<td>");
					resultadoHtmlAgruVehiculos.append(mapaTipoPlacaDeColegiado.get(arrayTipoPlacas[1]) != null ? mapaTipoPlacaDeColegiado.get(arrayTipoPlacas[1]) : "0");
					resultadoHtmlAgruVehiculos.append("</td>");
					resultadoHtmlAgruVehiculos.append("<td>");
					resultadoHtmlAgruVehiculos.append(mapaTipoPlacaDeColegiado.get(arrayTipoPlacas[2]) != null ? mapaTipoPlacaDeColegiado.get(arrayTipoPlacas[2]) : "0");
					resultadoHtmlAgruVehiculos.append("</td>");
					resultadoHtmlAgruVehiculos.append("<td>");
					resultadoHtmlAgruVehiculos.append(mapaTipoPlacaDeColegiado.get(arrayTipoPlacas[3]) != null ? mapaTipoPlacaDeColegiado.get(arrayTipoPlacas[3]) : "0");
					resultadoHtmlAgruVehiculos.append("</td>");
					resultadoHtmlAgruVehiculos.append("<td>");
					resultadoHtmlAgruVehiculos.append(mapaTipoPlacaDeColegiado.get(arrayTipoPlacas[4]) != null ? mapaTipoPlacaDeColegiado.get(arrayTipoPlacas[4]) : "0");
					resultadoHtmlAgruVehiculos.append("</td>");
					resultadoHtmlAgruVehiculos.append("<td>");
					resultadoHtmlAgruVehiculos.append(mapaTipoPlacaDeColegiado.get(arrayTipoPlacas[5]) != null ? mapaTipoPlacaDeColegiado.get(arrayTipoPlacas[5]) : "0");
					resultadoHtmlAgruVehiculos.append("</td>");
					resultadoHtmlAgruVehiculos.append("<td>");
					resultadoHtmlAgruVehiculos.append(mapaSolicitudAgruNumColegiado.get(colegiado));
					resultadoHtmlAgruVehiculos.append("</td>");
					resultadoHtmlAgruVehiculos.append("</tr>");
				}
				resultadoHtmlAgruVehiculos.append("</table>");
			}
		}
	}

	private void generaTablaAgrupadaCreditos(EstadisticasPlacasBean estadisticasPlacasBean) {
		if (estadisticasPlacasBean.isAgrTipoCredito()) {
			mapaSolicitudAgruCreditos = servicioPersistenciaPlacasMatricula.generarEstadisticasAgrupadoPorCreditos(estadisticasPlacasBean.getFecha().getFechaInicio(), estadisticasPlacasBean.getFecha()
					.getFechaFin());
			if (!mapaSolicitudAgruCreditos.isEmpty()) {
				resultadoHtmlAgruVehiculos.append("<br/> <th>AGRUPACIÓN POR TIPO DE CRÉDITO:</th>");
				resultadoHtmlAgruVehiculos.append("<table border='1' width='100%'> <tr> " + "<th>Tipo de matrícula</th> ");
				resultadoHtmlAgruVehiculos.append("<th>Total de créditos consumidos</th></tr>");
				for (Integer tipoPlaca : arrayTipoPlacas) {
					if (mapaSolicitudAgruCreditos.containsKey(tipoPlaca)) {
						resultadoHtmlAgruVehiculos.append("<tr>");
						resultadoHtmlAgruVehiculos.append("<td>");
						resultadoHtmlAgruVehiculos.append(TipoPlacasMatriculasEnum.convertir(tipoPlaca));
						resultadoHtmlAgruVehiculos.append("</td>");
						resultadoHtmlAgruVehiculos.append("<td>");
						resultadoHtmlAgruVehiculos.append(mapaSolicitudAgruCreditos.get(tipoPlaca));
						resultadoHtmlAgruVehiculos.append("</td>");
						resultadoHtmlAgruVehiculos.append("</tr>");
					}
				}
				resultadoHtmlAgruVehiculos.append("</table>");
			}
		}
	}

	@SuppressWarnings("rawtypes")
	private void generaTablaAgrupadaPorColegiado(EstadisticasPlacasBean estadisticasPlacasBean) {
		if (estadisticasPlacasBean.isNumColegiado()) {
			mapaSolicitudAgruNumColegiado = servicioPersistenciaPlacasMatricula.generarEstadisticasAgrupadoPorNumColegiado(estadisticasPlacasBean.getFecha().getFechaInicio(), estadisticasPlacasBean
					.getFecha().getFechaFin());
			if (mapaSolicitudAgruNumColegiado != null) {
				mapaSolicitudAgruNumColegiado.keySet();
				resultadoHtmlAgruVehiculos.append("<br/> <th>AGRUPACIÓN POR NÚMERO DE COLEGIADO:</th>");
				resultadoHtmlAgruVehiculos.append("<table border='1' width='100%'> <tr> " + "<th>Número de colegiado</th> ");
				resultadoHtmlAgruVehiculos.append("<th>Total de placas solicitadas</th></tr>");
				Iterator entries = mapaSolicitudAgruNumColegiado.entrySet().iterator();
				while (entries.hasNext()) {
					Entry thisEntry = (Entry) entries.next();
					String colegiado = (String) thisEntry.getKey();
					Integer total = (Integer) thisEntry.getValue();

					resultadoHtmlAgruVehiculos.append("<tr>");
					resultadoHtmlAgruVehiculos.append("<td>");
					resultadoHtmlAgruVehiculos.append(colegiado);
					resultadoHtmlAgruVehiculos.append("</td>");
					resultadoHtmlAgruVehiculos.append("<td>");
					resultadoHtmlAgruVehiculos.append(total);
					resultadoHtmlAgruVehiculos.append("</td>");
					resultadoHtmlAgruVehiculos.append("</tr>");
				}

			}
			resultadoHtmlAgruVehiculos.append("</table>");
		}
	}

	/*
	 * Método que guardará con formato html en un StringBuffer una serie de tablas en función de los checks que haya marcado el usuario. La tabla sin agrupación es una tabla con información general que siempre se creará. El resto de tablas se evaluará dentro de los métodos generaX si tiene que
	 * crearse o no. Despúes convertirá el html a pdf para que sea descargado.
	 */
	@Override
	public byte[] generarEstadisticas(EstadisticasPlacasBean estadisticasPlacasBean) {

		generaTablaSinAgrupacion(estadisticasPlacasBean);
		generaTablaAgrupadaPorVehiculo(estadisticasPlacasBean);
		generaTablaAgrupadaCreditosColegiado(estadisticasPlacasBean);
		generaTablaAgrupadaCreditos(estadisticasPlacasBean);
		generaTablaAgrupadaPorColegiado(estadisticasPlacasBean);

		resultadoHtmlAgruVehiculos.append("</BODY></HTML>");
		return generarPDFTemporal(resultadoHtmlAgruVehiculos);

	}

	private byte[] generarPDFTemporal(StringBuffer resultadoHtmlAgruVehiculos) {
		OutputStream outputStream = null;
		byte[] result = null;
		try {

			ITextRenderer iTextRenderer = new ITextRenderer();

			/**
			 * Setting the document as the url value passed. This means that the iText renderer has to parse this html document to generate the pdf.
			 */
			iTextRenderer.setDocumentFromString(resultadoHtmlAgruVehiculos.toString());
			iTextRenderer.layout();

			/**
			 * The generated pdf will be written to the invoice.pdf file.
			 */
			outputStream = new ByteArrayOutputStream();
			/**
			 * Creating the pdf and writing it in invoice.pdf file.
			 */
			iTextRenderer.createPDF(outputStream);

			result = ((ByteArrayOutputStream) outputStream).toByteArray();
		} catch (Exception e) {
			log.error(e.getMessage());
		} finally {
			if (outputStream != null) {
				try {
					outputStream.close();
				} catch (IOException e) {
					log.error("No se pudo cerrar el outputstream", e);
				}
			}
		}
		return result;
	}

	@Override
	public boolean borrarSolicitud(SolicitudPlacaVO solicitud) {
		solicitud.setEstado(0);
		return servicioPersistenciaPlacasMatricula.borrarSolicitud(solicitud);
	}

}