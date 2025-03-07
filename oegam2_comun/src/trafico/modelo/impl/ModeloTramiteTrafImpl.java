package trafico.modelo.impl;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.gestoresmadrid.core.model.enumerados.EstadoTramiteTrafico;
import org.gestoresmadrid.core.springContext.ContextoSpring;
import org.gestoresmadrid.oegam2comun.model.service.ServicioNotificacion;
import org.gestoresmadrid.oegam2comun.trafico.model.service.ServicioTramiteTraficoMatriculacion;
import org.gestoresmadrid.utilidades.components.GestorPropiedades;
import org.gestoresmadrid.utilidades.components.Utiles;
import org.gestoresmadrid.utilidades.components.UtilesFecha;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import hibernate.dao.general.UsuarioDAOImpl;
import hibernate.dao.trafico.TramiteTraficoDAO;
import hibernate.entities.general.Usuario;
import hibernate.entities.trafico.EvolucionTramiteTrafico;
import hibernate.entities.trafico.EvolucionTramiteTraficoPK;
import hibernate.entities.trafico.TramiteTrafTran;
import hibernate.entities.trafico.TramiteTrafico;
import hibernate.entities.trafico.Vehiculo;
import trafico.beans.IntervinienteTrafico;
import trafico.beans.TramiteTraficoMatriculacionBean;
import trafico.beans.VehiculoBean;
import trafico.beans.resultTransformer.ConsultaTramiteTraficoNumExpEstadoResultadoBean;
import trafico.dao.GenericDao;
import trafico.dao.implHibernate.GenericDaoImplHibernate;
import trafico.dao.implHibernate.TramiteTraficoDaoImplHibernate;
import trafico.dao.interfaz.TramiteTraficoDaoIntr;
import trafico.dto.TramiteTraficoDto;
import trafico.modelo.interfaz.ModeloMatrInterface;
import trafico.modelo.interfaz.ModeloTramiteTrafInterface;
import trafico.utiles.constantes.ConstantesTrafico;
import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;
import utilidades.web.OegamExcepcion;

public class ModeloTramiteTrafImpl implements ModeloTramiteTrafInterface {

	private ModeloMatrInterface modeloMatr = new ModeloMatrImpl();
	private TramiteTraficoDaoIntr tramiteDao;

	private static final ILoggerOegam log = LoggerOegam.getLogger(ModeloTramiteTrafImpl.class);

	@Autowired
	private GestorPropiedades gestorPropiedades;

	@Autowired
	UtilesFecha utilesFecha;

	@Autowired
	Utiles utiles;

	public ModeloTramiteTrafImpl() {
		SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
		tramiteDao = new TramiteTraficoDAO();
	}

	@Override
	public TramiteTraficoDto actualizar(TramiteTraficoDto tramiteDto) throws OegamExcepcion {
		TramiteTrafico tramite = null;
		try {
			tramite = convertDtoToTramiteTraf(tramiteDto);
		} catch (ParseException e) {
			log.error(e);
			throw new OegamExcepcion("Error: al parsear de fecha a date");
		}
		try {
			if (tramite != null) {
				tramite = (TramiteTrafico) tramiteDao.actualizar(tramite);
				if (tramiteDto.getTramiteTrafMatr() != null) {
					modeloMatr.actualizar(tramiteDto.getTramiteTrafMatr());
				}
			}
		} catch (Exception e) {
			log.error(e.getMessage());
			throw new OegamExcepcion("Error: al guardar el tramite");
		}
		return convertTramiteTrafToDto(tramite);
	}

	@Override
	public TramiteTraficoDto recuperarDtoPorNumExp(BigDecimal numExpediente) {
		TramiteTrafico tramite = tramiteDao.buscarPorExpediente(numExpediente);

		TramiteTraficoDto tramiteDto = convertTramiteTrafToDto(tramite);

		tramiteDto.setTramiteTrafMatr(modeloMatr.recuperarDtoMatrPorNumExpediente(numExpediente));

		return tramiteDto;
	}

	public TramiteTraficoDto convertTramiteTrafToDto(TramiteTrafico tramite) {
		if (tramite == null) {
			return null;
		}
		TramiteTraficoDto resultado = new TramiteTraficoDto();

		if (tramite.getAnotaciones() != null && !tramite.getAnotaciones().equals(""))
			resultado.setAnotaciones(tramite.getAnotaciones());
		if (tramite.getCambioDomicilio() != null && !tramite.getCambioDomicilio().equals(""))
			resultado.setCambioDomicilio(tramite.getCambioDomicilio());
		if (tramite.getCem() != null && !tramite.getCem().equals(""))
			resultado.setCem(tramite.getCem());
		if (tramite.getCema() != null && !tramite.getCema().equals(""))
			resultado.setCema(tramite.getCema());
		if (tramite.getContrato() != null)
			resultado.setContrato(tramite.getContrato());
		if (tramite.getEstado() != null)
			resultado.setEstado(tramite.getEstado());
		if (tramite.getEvolucionTramiteTraficos() != null)
			resultado.setEvolucionTramiteTraficos(tramite.getEvolucionTramiteTraficos());
		if (tramite.getExentoCem() != null && !tramite.getExentoCem().equals(""))
			resultado.setExentoCem(tramite.getExentoCem());
		if (tramite.getExentoIedtm() != null && !tramite.getExentoIedtm().equals(""))
			resultado.setExentoIedtm(tramite.getExentoIedtm());
		if (tramite.getFechaAlta() != null)
			resultado.setFechaAlta(utilesFecha.getFechaConDate(tramite.getFechaAlta()));
		if (tramite.getFechaDigitalizacion() != null)
			resultado.setFechaDigitalizacion(utilesFecha.getFechaConDate(tramite.getFechaDigitalizacion()));
		if (tramite.getFechaIedtm() != null)
			resultado.setFechaIedtm(utilesFecha.getFechaConDate(tramite.getFechaIedtm()));
		if (tramite.getFechaImpresion() != null)
			resultado.setFechaImpresion(utilesFecha.getFechaConDate(tramite.getFechaImpresion()));
		if (tramite.getFechaPresentacion() != null)
			resultado.setFechaPresentacion(utilesFecha.getFechaConDate(tramite.getFechaPresentacion()));
		if (tramite.getFechaUltModif() != null)
			resultado.setFechaUltModif(utilesFecha.getFechaConDate(tramite.getFechaUltModif()));
		if (tramite.getFinancieraIedtm() != null && !tramite.getFinancieraIedtm().equals(""))
			resultado.setFinancieraIedtm(tramite.getFinancieraIedtm());
		if (tramite.getUsuario() != null)
			resultado.setUsuario(tramite.getUsuario());
		if (tramite.getIedtm() != null && !tramite.getIedtm().equals(""))
			resultado.setIedtm(tramite.getIedtm());
		if (tramite.getIntervinienteTraficos() != null)
			resultado.setIntervinienteTraficos(tramite.getIntervinienteTraficos());
		if (tramite.getJefaturaTrafico() != null)
			resultado.setJefaturaTrafico(tramite.getJefaturaTrafico());
		if (tramite.getJustificanteProfs() != null)
			resultado.setJustificanteProfs(tramite.getJustificanteProfs());
		if (tramite.getModeloIedtm() != null && !tramite.getModeloIedtm().equals(""))
			resultado.setModeloIedtm(tramite.getModeloIedtm());
		if (tramite.getNoSujecionIedtm() != null && !tramite.getNoSujecionIedtm().equals(""))
			resultado.setNoSujecionIedtm(tramite.getNoSujecionIedtm());
		if (tramite.getNpasos() != null && !tramite.getNpasos().equals(""))
			resultado.setNpasos(tramite.getNpasos());
		if (tramite.getNRegIedtm() != null && !tramite.getNRegIedtm().equals(""))
			resultado.setnRegIedtm(tramite.getNRegIedtm());
		if (tramite.getNumColegiado() != null && !tramite.getNumColegiado().equals(""))
			resultado.setNumColegiado(tramite.getNumColegiado());
		if (tramite.getNumExpediente() != null)
			resultado.setNumExpediente(tramite.getNumExpediente());
		if (tramite.getRefPropia() != null && !tramite.getRefPropia().equals(""))
			resultado.setRefPropia(tramite.getRefPropia());
		if (tramite.getRenting() != null && !tramite.getRenting().equals(""))
			resultado.setRenting(tramite.getRenting());
		if (tramite.getRespuesta() != null && !tramite.getRespuesta().equals(""))
			resultado.setRespuesta(tramite.getRespuesta());
		if (tramite.getRespuestaDigitalizacionGdoc() != null && !tramite.getRespuestaDigitalizacionGdoc().equals(""))
			resultado.setRespuestaDigitalizacionGdoc(tramite.getRespuestaDigitalizacionGdoc());
		if (tramite.getRespuestaGest() != null && !tramite.getRespuestaGest().equals(""))
			resultado.setRespuestaGest(tramite.getRespuestaGest());
		if (tramite.getSimultanea() != null)
			resultado.setSimultanea(tramite.getSimultanea());
		if (tramite.getTasa() != null)
			resultado.setTasa(tramite.getTasa());
		if (tramite.getTipoCreacion() != null)
			resultado.setTipoCreacion(tramite.getTipoCreacion());
		if (tramite.getTipoTramite() != null && !tramite.getTipoTramite().equals(""))
			resultado.setTipoTramite(tramite.getTipoTramite());
		if (tramite.getTramiteTrafBaja() != null)
			resultado.setTramiteTrafBaja(tramite.getTramiteTrafBaja());

		// Modificación para añadir el tramite tráfico matriculación DTO
		if (tramite.getTramiteTrafMatr() != null) {
			resultado.setTramiteTrafMatr(modeloMatr.convertTraTrafMatToDto(tramite.getTramiteTrafMatr()));
		}
		if (tramite.getTramiteTrafSolInfo() != null)
			resultado.setTramiteTrafSolInfo(tramite.getTramiteTrafSolInfo());
		if (tramite.getTramiteTrafTran() != null)
			resultado.setTramiteTrafTran(tramite.getTramiteTrafTran());
		if (tramite.getVehiculo() != null)
			resultado.setVehiculo(tramite.getVehiculo());
		if (tramite.getYbestado() != null)
			resultado.setYbestado(tramite.getYbestado());
		if (tramite.getYbpdf() != null)
			resultado.setYbpdf(tramite.getYbpdf());

		return resultado;
	}

	@Override
	public TramiteTrafico convertDtoToTramiteTraf(TramiteTraficoDto tramiteDto) throws ParseException {
		if (tramiteDto == null) {
			return null;
		}
		TramiteTrafico tramite = new TramiteTrafico();
		TramiteTrafTran tramiteTransmision = new TramiteTrafTran();

		if (tramiteDto.getAnotaciones() != null && !tramiteDto.getAnotaciones().equals(""))
			tramite.setAnotaciones(tramiteDto.getAnotaciones());
		if (tramiteDto.getCambioDomicilio() != null && !tramiteDto.getCambioDomicilio().equals(""))
			tramite.setCambioDomicilio(tramiteDto.getCambioDomicilio());
		if (tramiteDto.getCem() != null && !tramiteDto.getCem().equals(""))
			tramite.setCem(tramiteDto.getCem());
		if (tramiteDto.getCema() != null && !tramiteDto.getCema().equals(""))
			tramite.setCema(tramiteDto.getCema());
		if (tramiteDto.getContrato() != null)
			tramite.setContrato(tramiteDto.getContrato());
		if (tramiteDto.getEstado() != null)
			tramite.setEstado(tramiteDto.getEstado());
		if (tramiteDto.getEvolucionTramiteTraficos() != null)
			tramite.setEvolucionTramiteTraficos(tramiteDto.getEvolucionTramiteTraficos());
		if (tramiteDto.getExentoCem() != null && !tramiteDto.getExentoCem().equals(""))
			tramite.setExentoCem(tramiteDto.getExentoCem());
		if (tramiteDto.getExentoIedtm() != null && !tramiteDto.getExentoIedtm().equals(""))
			tramite.setExentoIedtm(tramiteDto.getExentoIedtm());
		if (tramiteDto.getFechaAlta() != null)
			tramite.setFechaAlta(tramiteDto.getFechaAlta().getDate());
		if (tramiteDto.getFechaDigitalizacion() != null)
			tramite.setFechaDigitalizacion(tramiteDto.getFechaDigitalizacion().getDate());
		if (tramiteDto.getFechaIedtm() != null)
			tramite.setFechaIedtm(tramiteDto.getFechaIedtm().getDate());
		if (tramiteDto.getFechaImpresion() != null)
			tramite.setFechaImpresion(tramiteDto.getFechaImpresion().getDate());
		if (tramiteDto.getFechaPresentacion() != null)
			tramite.setFechaPresentacion(tramiteDto.getFechaPresentacion().getDate());
		if (tramiteDto.getFechaUltModif() != null)
			tramite.setFechaUltModif(tramiteDto.getFechaUltModif().getDate());
		if (tramiteDto.getFinancieraIedtm() != null && !tramiteDto.getFinancieraIedtm().equals(""))
			tramite.setFinancieraIedtm(tramiteDto.getFinancieraIedtm());
		if (tramiteDto.getUsuario() != null)
			tramite.setUsuario(tramiteDto.getUsuario());
		if (tramiteDto.getIedtm() != null && !tramiteDto.getIedtm().equals(""))
			tramite.setIedtm(tramiteDto.getIedtm());
		if (tramiteDto.getIntervinienteTraficos() != null)
			tramite.setIntervinienteTraficos(tramiteDto.getIntervinienteTraficos());
		if (tramiteDto.getJefaturaTrafico() != null)
			tramite.setJefaturaTrafico(tramiteDto.getJefaturaTrafico());
		if (tramiteDto.getJustificanteProfs() != null)
			tramite.setJustificanteProfs(tramiteDto.getJustificanteProfs());
		if (tramiteDto.getModeloIedtm() != null && !tramiteDto.getModeloIedtm().equals(""))
			tramite.setModeloIedtm(tramiteDto.getModeloIedtm());
		if (tramiteDto.getNoSujecionIedtm() != null && !tramiteDto.getNoSujecionIedtm().equals(""))
			tramite.setNoSujecionIedtm(tramiteDto.getNoSujecionIedtm());
		if (tramiteDto.getNpasos() != null && !tramiteDto.getNpasos().equals(""))
			tramite.setNpasos(tramiteDto.getNpasos());
		if (tramiteDto.getnRegIedtm() != null && !tramiteDto.getnRegIedtm().equals(""))
			tramite.setNRegIedtm(tramiteDto.getnRegIedtm());
		if (tramiteDto.getNumColegiado() != null && !tramiteDto.getNumColegiado().equals(""))
			tramite.setNumColegiado(tramiteDto.getNumColegiado());
		if (tramiteDto.getNumExpediente() != null)
			tramite.setNumExpediente(tramiteDto.getNumExpediente());
		if (tramiteDto.getRefPropia() != null && !tramiteDto.getRefPropia().equals(""))
			tramite.setRefPropia(tramiteDto.getRefPropia());
		if (tramiteDto.getRenting() != null && !tramiteDto.getRenting().equals(""))
			tramite.setRenting(tramiteDto.getRenting());

		// Hibernate.initialize(tramiteDto.getTramiteTrafTran());

		// if(tramiteDto.getTramiteTrafTran().getResCheckCtit()!=null && !tramiteDto.getTramiteTrafTran().getResCheckCtit().equals(""))
		// tramite.getTramiteTrafTran().setResCheckCtit(tramiteDto.getTramiteTrafTran().getResCheckCtit());
		if (tramiteDto.getRespuesta() != null && !tramiteDto.getRespuesta().equals(""))
			tramite.setRespuesta(tramiteDto.getRespuesta());
		if (tramiteDto.getRespuestaDigitalizacionGdoc() != null && !tramiteDto.getRespuestaDigitalizacionGdoc().equals(""))
			tramite.setRespuestaDigitalizacionGdoc(tramiteDto.getRespuestaDigitalizacionGdoc());
		if (tramiteDto.getRespuestaGest() != null && !tramiteDto.getRespuestaGest().equals(""))
			tramite.setRespuestaGest(tramiteDto.getRespuestaGest());
		if (tramiteDto.getSimultanea() != null)
			tramite.setSimultanea(tramiteDto.getSimultanea());
		if (tramiteDto.getTasa() != null)
			tramite.setTasa(tramiteDto.getTasa());
		if (tramiteDto.getTipoCreacion() != null)
			tramite.setTipoCreacion(tramiteDto.getTipoCreacion());
		if (tramiteDto.getTipoTramite() != null && !tramiteDto.getTipoTramite().equals(""))
			tramite.setTipoTramite(tramiteDto.getTipoTramite());
		if (tramiteDto.getTramiteTrafBaja() != null)
			tramite.setTramiteTrafBaja(tramiteDto.getTramiteTrafBaja());

		// Modificación para añadir el trámite tráfico matriculación DTO
		if (tramiteDto.getTramiteTrafMatr() != null) {
			tramite.setTramiteTrafMatr(modeloMatr.convertDtoToTraTrafMat(tramiteDto.getTramiteTrafMatr()));
		}
		if (tramiteDto.getTramiteTrafSolInfo() != null)
			tramite.setTramiteTrafSolInfo(tramiteDto.getTramiteTrafSolInfo());
		if (tramiteDto.getTramiteTrafTran() != null)
			tramite.setTramiteTrafTran(tramiteDto.getTramiteTrafTran());
		if (tramiteDto.getVehiculo() != null)
			tramite.setVehiculo(tramiteDto.getVehiculo());
		if (tramiteDto.getYbestado() != null)
			tramite.setYbestado(tramiteDto.getYbestado());
		if (tramiteDto.getYbpdf() != null)
			tramite.setYbpdf(tramiteDto.getYbpdf());

		return tramite;
	}

	@Override
	public boolean cambiarEstado(BigDecimal numExpediente, EstadoTramiteTrafico estadoNuevo) {
		try {
			TramiteTraficoDto tramiteTraficoDto = recuperarDtoPorNumExp(numExpediente);
			tramiteTraficoDto.setEstado(new BigDecimal(estadoNuevo.getValorEnum()));
			try {
				actualizar(tramiteTraficoDto);
			} catch (OegamExcepcion e) {
				log.error("Error al cambiar al estado " + estadoNuevo.getNombreEnum() + " del trámite " + numExpediente);
				return false;
			}
		} catch (Exception e) {
			return false;
		}
		return true;
	}

	@Override
	public boolean cambiarEstado(BigDecimal[] numExpedientes, EstadoTramiteTrafico estadoNuevo, BigDecimal idUsuario) {
		try {
			ServicioNotificacion servicioNotificacion = ContextoSpring.getInstance().getBean(ServicioNotificacion.class);

			List<TramiteTrafico> expedientesANotificar = tramiteDao.obtenerTramitesNoEstado(utiles.convertirBigDecimalArrayToLongArray(numExpedientes), estadoNuevo);
			if (expedientesANotificar != null && !expedientesANotificar.isEmpty()) {
				servicioNotificacion.notificarCambioEstado(expedientesANotificar, estadoNuevo, idUsuario);
				List<Long> numExpedientesANotificar = new ArrayList<>();
				for (TramiteTrafico t : expedientesANotificar) {
					numExpedientesANotificar.add(t.getNumExpediente());
				}
				tramiteDao.actualizarFechaUltimaModificacion(numExpedientesANotificar);
				if (EstadoTramiteTrafico.Finalizado_PDF.equals(estadoNuevo) || EstadoTramiteTrafico.Finalizado_Telematicamente_Impreso.equals(estadoNuevo)) {
					tramiteDao.actualizarFechaImpresion(numExpedientesANotificar);
				}
			}
			tramiteDao.actualizarEstados(numExpedientes, estadoNuevo);
			anadirEvolucion(expedientesANotificar, estadoNuevo, idUsuario);
		} catch (Exception e) {
			return false;
		}
		return true;
	}

	protected void anadirEvolucion(List<TramiteTrafico> expedientes, EstadoTramiteTrafico estadoNuevo, BigDecimal usuario) {
		anadirEvolucion(expedientes, estadoNuevo, new UsuarioDAOImpl().getUsuario(usuario.longValue()));
	}

	protected void anadirEvolucion(List<TramiteTrafico> expedientes, EstadoTramiteTrafico estadoNuevo, Usuario usuario) {
		GenericDao<EvolucionTramiteTrafico> dao = new GenericDaoImplHibernate<EvolucionTramiteTrafico>(new EvolucionTramiteTrafico());
		EvolucionTramiteTrafico evolucion = new EvolucionTramiteTrafico();
		evolucion.setUsuario(usuario);
		EvolucionTramiteTraficoPK pk = new EvolucionTramiteTraficoPK();
		evolucion.setId(pk);
		pk.setEstadoNuevo(utiles.stringToLong(estadoNuevo.getValorEnum()));
		pk.setFechaCambio(new Date());
		for (TramiteTrafico tramite : expedientes) {
			pk.setEstadoAnterior(utiles.convertirBigDecimalALong(tramite.getEstado()));
			pk.setNumExpediente(tramite.getNumExpediente());
			dao.guardar(evolucion);
		}
	}

	@Override
	public HashMap<String, Boolean> tramiteModificado(TramiteTraficoMatriculacionBean tramiteBeanPantalla) {
		HashMap<String, Boolean> resultado = new HashMap<>();
		TramiteTraficoDto tramiteDto = recuperarDtoPorNumExp(tramiteBeanPantalla.getTramiteTraficoBean().getNumExpediente());
		if (tramiteDto != null) {
			resultado.putAll(titularModificado(tramiteBeanPantalla.getTitularBean(), tramiteDto));
			resultado.putAll(vehiculoModificado(tramiteBeanPantalla.getTramiteTraficoBean().getVehiculo(), tramiteDto));
		}
		return resultado;
	}


	@Override
	public String validarRelacionMatriculas(String[] codSeleccionados) throws Exception, OegamExcepcion {
		List<BigDecimal> estados = new ArrayList<>();
		String mensajeError = "";

		// Se validan número de trámites
		int maxNum = Integer.valueOf(gestorPropiedades.valorPropertie("matw.relacionMatriculas.maximo"));
		if (codSeleccionados.length > maxNum) {
			mensajeError = "El número de trámites seleccionados para la Relación de Matrículas no debe ser superior a " + maxNum;
		} else {
			Long estado = Long.valueOf(EstadoTramiteTrafico.Finalizado_Telematicamente.getValorEnum());
			estados.add(BigDecimal.valueOf(estado));
			estado = Long.valueOf(EstadoTramiteTrafico.Finalizado_Telematicamente_Impreso.getValorEnum());
			estados.add(BigDecimal.valueOf(estado));

			// Validamos estados
			List<ConsultaTramiteTraficoNumExpEstadoResultadoBean> lista = new TramiteTraficoDaoImplHibernate().existeNumExpedienteEstados(utiles.convertirStringArrayToLongArray(codSeleccionados),
					estados);

			if (lista.size() == codSeleccionados.length) {
				if (!comprobarFechaPresentacion(lista)) {
					return "No todos los trámites tienen la misma fecha de presentación";
				} else {
					ServicioTramiteTraficoMatriculacion servicioTramiteTraficoMatriculacion = ContextoSpring.getInstance().getBean(ServicioTramiteTraficoMatriculacion.class);
					for (String numExpediente : codSeleccionados) {
						if (servicioTramiteTraficoMatriculacion.esSupertelematico(new BigDecimal(numExpediente), null, Boolean.TRUE)) {
							return "Existen trámites Supertelemáticos (" + numExpediente + "), no puede crearse el Listado de Bastidores";
						}
					}
				}
			} else {
				return "No todos los trámites tienen los estados correctos, deben estar en el estado Finalizado Telemáticamente o Finalizado Telemáticamente Impreso";
			}
		}

		return mensajeError;
	}

	private boolean comprobarFechaPresentacion(List<ConsultaTramiteTraficoNumExpEstadoResultadoBean> lista) {
		String fechaPresentacion = null;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		for (ConsultaTramiteTraficoNumExpEstadoResultadoBean conBean : lista) {
			if (fechaPresentacion == null) {
				fechaPresentacion = sdf.format(conBean.getFechaPresentacion());
			} else if (!fechaPresentacion.equals(sdf.format(conBean.getFechaPresentacion()))) {
				return false;
			}
		}
		return true;
	}

	private HashMap<String, Boolean> titularModificado(IntervinienteTrafico intervinienteTrafico, TramiteTraficoDto tramiteDto) {
		HashMap<String, Boolean> resultado = new HashMap<>();
		Boolean modificado = false;

		if (null != intervinienteTrafico.getCambioDomicilio() && !intervinienteTrafico.getCambioDomicilio().equals(tramiteDto.getCambioDomicilio())) {
			modificado = true;
		}
		if (intervinienteTrafico.getIdContrato() != null && !intervinienteTrafico.getIdContrato().equals(tramiteDto.getContrato().getIdContrato())) {
			modificado = true;
		}
		if (intervinienteTrafico.getIdUsuario() != null && !intervinienteTrafico.getIdUsuario().equals(tramiteDto.getUsuario().getIdUsuario())) {
			modificado = true;
		}
		if (intervinienteTrafico.getNumColegiado() != null && !intervinienteTrafico.getNumColegiado().equals(tramiteDto.getNumColegiado())) {
			modificado = true;
		}
		if (intervinienteTrafico.getNumExpediente() != null && !intervinienteTrafico.getNumExpediente().equals(tramiteDto.getNumExpediente())) {
			modificado = true;
		}

		resultado.put(ConstantesTrafico.CONSTANTE_TITULAR, modificado);
		return resultado;
	}

	private HashMap<String, Boolean> vehiculoModificado(VehiculoBean vehiculoBean, TramiteTraficoDto tramiteDto) {
		HashMap<String, Boolean> resultado = new HashMap<>();
		Boolean modificado = false;

		Vehiculo vehiculo = tramiteDto.getVehiculo();

		// Ya que el nive y bastidor son prioritarios saltaran el mensaje inmediatamente.
		if (vehiculoBean.getNive() != null && !vehiculoBean.getNive().equals(vehiculo.getNive())) {
			resultado.put(ConstantesTrafico.CONSTANTE_VEHICULO, true);
			return resultado;
		}
		if (vehiculoBean.getBastidor() != null && !vehiculoBean.getBastidor().equals(vehiculo.getBastidor())) {
			resultado.put(ConstantesTrafico.CONSTANTE_VEHICULO, true);
			return resultado;
		}

		if (vehiculoBean.getImportado() != null && !vehiculoBean.getImportado().equals(vehiculo.getImportado())) {
			modificado = true;
		}
		if (vehiculoBean.getSubasta() != null && vehiculo.getSubastado() != null && !vehiculoBean.getSubasta().equals(vehiculo.getSubastado())) {
			modificado = true;
		}
		if (vehiculoBean.getVehiculoAgricola() != null && !vehiculoBean.getVehiculoAgricola().equals(vehiculo.getVehiculoAgricola())) {
			modificado = true;
		}
		if (vehiculoBean.getVehiculoTransporte() != null && !vehiculoBean.getVehiculoTransporte().equals(vehiculo.getVehiculoTransporte())) {
			modificado = true;
		}
		if (vehiculoBean.getAnioFabrica() != null && !vehiculoBean.getAnioFabrica().equals(vehiculo.getAnioFabrica())) {
			modificado = true;
		}
		if (vehiculoBean.getCaracteristicas() != null && !vehiculoBean.getCaracteristicas().equals(vehiculo.getCaracteristicas())) {
			modificado = true;
		}
		if (vehiculoBean.getCdMarca() != null && !vehiculoBean.getCdMarca().equals(vehiculo.getCdmarca())) {
			modificado = true;
		}
		if (vehiculoBean.getCdModVeh() != null && !vehiculoBean.getCdModVeh().equals(vehiculo.getCdmodveh())) {
			modificado = true;
		}
		if (vehiculoBean.getCilindrada() != null && !vehiculoBean.getCilindrada().equals(vehiculo.getCilindrada())) {
			modificado = true;
		}
		if (vehiculoBean.getClasificacionItv() != null && !vehiculoBean.getClasificacionItv().equals(vehiculo.getClasificacionItv())) {
			modificado = true;
		}
		if (vehiculoBean.getCo2() != null && !vehiculoBean.getCo2().equals(vehiculo.getCo2())) {
			modificado = true;
		}
		if (vehiculoBean.getCodigoEco() != null && !vehiculoBean.getCodigoEco().equals(vehiculo.getCodigoEco())) {
			modificado = true;
		}
		if (vehiculoBean.getCodItv() != null && !vehiculoBean.getCodItv().equals(vehiculo.getCoditv())) {
			modificado = true;
		}
		if (vehiculoBean.getConceptoItv() != null && !vehiculoBean.getConceptoItv().equals(vehiculo.getConceptoItv())) {
			modificado = true;
		}
		if (vehiculoBean.getConsumo() != null && !vehiculoBean.getConsumo().equals(vehiculo.getConsumo())) {
			modificado = true;
		}
		if (vehiculoBean.getDiplomatico() != null && vehiculo.getDiplomatico() != null && !vehiculoBean.getDiplomatico().equals(vehiculo.getDiplomatico())) {
			modificado = true;
		}
		if (vehiculoBean.getDistanciaEntreEjes() != null && !vehiculoBean.getDistanciaEntreEjes().equals(vehiculo.getDistanciaEjes())) {
			modificado = true;
		}
		if (vehiculoBean.getEcoInnovacion() != null && !vehiculoBean.getEcoInnovacion().equals(vehiculo.getEcoInnovacion())) {
			modificado = true;
		}
		if (vehiculoBean.getEstacionItv() != null && !vehiculoBean.getEstacionItv().equals(vehiculo.getEstacionItv())) {
			modificado = true;
		}
		if (vehiculoBean.getExcesoPeso() != null && vehiculo.getExcesoPeso() != null && !vehiculoBean.getExcesoPeso().equals(vehiculo.getExcesoPeso())) {
			modificado = true;
		}
		if (vehiculoBean.getFabricante() != null && !vehiculoBean.getFabricante().equals(vehiculo.getFabricante())) {
			modificado = true;
		}
		if (vehiculoBean.getModelo() != null && !vehiculoBean.getModelo().equals(vehiculo.getModelo())) {
			modificado = true;
		}
		if ((vehiculoBean.getMatricula() != null && vehiculo.getMatricula() != null
				&& !vehiculoBean.getMatricula().equals(vehiculo.getMatricula()))
				|| (vehiculo.getMatricula() == null && vehiculoBean.getMatricula() != null
						&& !vehiculoBean.getMatricula().equals(""))) {
			modificado = true;
		}
		if (vehiculoBean.getNivelEmisiones() != null && !vehiculoBean.getNivelEmisiones().equals(vehiculo.getNivelEmisiones())) {
			modificado = true;
		}
		if (vehiculoBean.getMtmaItv() != null && !vehiculoBean.getMtmaItv().equals(vehiculo.getMtmaItv())) {
			modificado = true;
		}
		if (vehiculoBean.getNumHomologacion() != null && !vehiculoBean.getNumHomologacion().equals(vehiculo.getNHomologacion())) {
			modificado = true;
		}
		if (vehiculoBean.getPesoMma() != null && !vehiculoBean.getPesoMma().equals(vehiculo.getPesoMma())) {
			modificado = true;
		}
		if (vehiculoBean.getKmUso() != null && !vehiculoBean.getKmUso().equals(vehiculo.getKmUso())) {
			modificado = true;
		}
		if (vehiculoBean.getIdLugarMatriculacion() != null && !vehiculoBean.getIdLugarMatriculacion().equals(vehiculo.getIdLugarMatriculacion())) {
			modificado = true;
		}
		if (vehiculoBean.getMatriAyuntamiento() != null && !vehiculoBean.getMatriAyuntamiento().equals(vehiculo.getMatriAyuntamiento())) {
			modificado = true;
		}
		if (vehiculoBean.getMom() != null && !vehiculoBean.getMom().equals(vehiculo.getMom())) {
			modificado = true;
		}
		if (vehiculoBean.getNumPlazasPie() != null && !vehiculoBean.getNumPlazasPie().equals(vehiculo.getNPlazasPie())) {
			modificado = true;
		}
		if (vehiculoBean.getNumRuedas() != null && !vehiculoBean.getNumRuedas().equals(vehiculo.getNRuedas())) {
			modificado = true;
		}
		if (vehiculoBean.getPlazas() != null && !vehiculoBean.getPlazas().equals(vehiculo.getPlazas())) {
			modificado = true;
		}
		if (vehiculoBean.getPotenciaFiscal() != null && !vehiculoBean.getPotenciaFiscal().equals(vehiculo.getPotenciaFiscal())) {
			modificado = true;
		}
		if (vehiculoBean.getPotenciaNeta() != null && !vehiculoBean.getPotenciaNeta().equals(vehiculo.getPotenciaNeta())) {
			modificado = true;
		}
		if (vehiculoBean.getPotenciaPeso() != null && !vehiculoBean.getPotenciaPeso().equals(vehiculo.getPotenciaPeso())) {
			modificado = true;
		}
		if (vehiculoBean.getProcedencia() != null && !vehiculoBean.getProcedencia().equals(vehiculo.getProcedencia())) {
			modificado = true;
		}
		if (vehiculoBean.getReduccionEco() != null && !vehiculoBean.getReduccionEco().equals(vehiculo.getReduccionEco())) {
			modificado = true;
		}
		if (vehiculoBean.getTara() != null && !vehiculoBean.getTara().equals(vehiculo.getTara())) {
			modificado = true;
		}
		if (vehiculoBean.getTipoIndustria() != null && !vehiculoBean.getTipoIndustria().equals(vehiculo.getTipoIndustria())) {
			modificado = true;
		}
		if (vehiculoBean.getTipoItv() != null && !vehiculoBean.getTipoItv().equals(vehiculo.getTipoItv())) {
			modificado = true;
		}
		if (vehiculoBean.getTipoVehiculoBean() != null && vehiculoBean.getTipoVehiculoBean().getTipoVehiculo() != null
				&& null != vehiculo.getTipoVehiculoBean() && vehiculo.getTipoVehiculoBean().getTipoVehiculo() != null
				&& !vehiculoBean.getTipoVehiculoBean().getTipoVehiculo()
						.equals(vehiculo.getTipoVehiculoBean().getTipoVehiculo())) {
			modificado = true;
		}
		if (vehiculoBean.getVariante() != null && !vehiculoBean.getVariante().equals(vehiculo.getVariante())) {
			modificado = true;
		}
		if (vehiculoBean.getVehiUsado() != null && !vehiculoBean.getVehiUsado().equals(vehiculo.getVehiUsado())) {
			modificado = true;
		}
		if (vehiculoBean.getVersion() != null && !vehiculoBean.getVersion().equals(vehiculo.getVersion())) {
			modificado = true;
		}
		if (vehiculoBean.getViaAnterior() != null && !vehiculoBean.getViaAnterior().equals(vehiculo.getViaAnterior())) {
			modificado = true;
		}
		if (vehiculoBean.getViaPosterior() != null && !vehiculoBean.getViaPosterior().equals(vehiculo.getViaPosterior())) {
			modificado = true;
		}

		resultado.put(ConstantesTrafico.CONSTANTE_VEHICULO, modificado);
		return resultado;
	}

}