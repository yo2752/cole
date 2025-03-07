package org.gestoresmadrid.oegam2comun.ivtmMatriculacion.model.service.impl;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.gestoresmadrid.core.ivtmMatriculacion.model.beans.IvtmDatosEntradaMatriculasWS;
import org.gestoresmadrid.core.ivtmMatriculacion.model.dao.ConsultasIvtmDao;
import org.gestoresmadrid.core.ivtmMatriculacion.model.dao.IvtmMatriculacionDao;
import org.gestoresmadrid.core.ivtmMatriculacion.model.enumerados.EstadoIVTM;
import org.gestoresmadrid.core.ivtmMatriculacion.model.vo.ConsultasIvtmVO;
import org.gestoresmadrid.core.ivtmMatriculacion.model.vo.IvtmMatriculacionVO;
import org.gestoresmadrid.core.model.enumerados.TipoVehiculoEnum;
import org.gestoresmadrid.oegam2comun.cola.model.service.ServicioCola;
import org.gestoresmadrid.oegam2comun.ivtmMatriculacion.model.service.ServicioIvtmMatriculacion;
import org.gestoresmadrid.oegam2comun.ivtmMatriculacion.model.view.dto.IvtmMatriculacionDto;
import org.gestoresmadrid.oegam2comun.ivtmMatriculacion.validacion.ValidacionIvtm;
import org.gestoresmadrid.oegam2comun.proceso.enumerados.ProcesosEnum;
import org.gestoresmadrid.oegam2comun.trafico.dto.ivtm.IvtmConsultaMatriculacionDto;
import org.gestoresmadrid.oegam2comun.trafico.dto.ivtm.IvtmDatosSalidaMatriculasWS;
import org.gestoresmadrid.oegam2comun.trafico.dto.ivtm.IvtmResultadoWSMatriculasWS;
import org.gestoresmadrid.oegam2comun.trafico.model.service.ServicioTramiteTraficoMatriculacion;
import org.gestoresmadrid.oegam2comun.trafico.view.dto.TramiteTrafMatrDto;
import org.gestoresmadrid.oegamComun.acceso.service.impl.UtilesColegiado;
import org.gestoresmadrid.oegamComun.conversor.Conversor;
import org.gestoresmadrid.oegamComun.direccion.view.dto.DireccionDto;
import org.gestoresmadrid.utilidades.components.GestorPropiedades;
import org.gestoresmadrid.utilidades.components.UtilesFecha;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import escrituras.beans.ResultBean;
import general.utiles.enumerados.ServicioTraficoEnum;
import trafico.beans.jaxb.matriculacion.DatosIvtm;
import trafico.beans.jaxb.matw.DatosIvtmMatw;
import trafico.beans.jaxb.matw.FORMATOGA;
import trafico.beans.jaxb.matw.FORMATOGA.MATRICULACION;
import trafico.beans.schemas.generated.transTelematica.TipoSINO;
import trafico.utiles.constantes.ConstantesIVTM;
import trafico.utiles.enumerados.TipoTramiteTrafico;
import utilidades.estructuras.Fecha;
import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;
import utilidades.web.OegamExcepcion;

@Service
public class ServicioIvtmMatriculacionImpl implements ServicioIvtmMatriculacion {

	private static final long serialVersionUID = 8929711248392665560L;

	private static final ILoggerOegam log = LoggerOegam.getLogger(ServicioIvtmMatriculacionImpl.class);

	@Autowired
	private IvtmMatriculacionDao ivtmMatriculacionDao;

	@Autowired
	private ConsultasIvtmDao consultasIvtmDao;

	@Autowired
	private ServicioTramiteTraficoMatriculacion servicioTramiteTraficoMatriculacion;

	@Autowired
	private Conversor conversor;

	@Autowired
	private ServicioCola servicioCola;

	@Autowired
	private GestorPropiedades gestorPropiedades;

	@Autowired
	UtilesFecha utilesFecha;

	@Autowired
	private UtilesColegiado utilesColegiado;

	@Override
	@Transactional
	public ResultBean guardarIvtm(IvtmMatriculacionDto ivtmMatriculacionDto) {
		if (ivtmMatriculacionDto == null) {
			return new ResultBean(true, "Debe rellenar los datos del IVTM para poder guardarlo");
		}

		IvtmMatriculacionVO ivtmMatriculacionVO = conversor.transform(ivtmMatriculacionDto, IvtmMatriculacionVO.class);
		ivtmMatriculacionVO = ivtmMatriculacionDao.guardarOActualizar(ivtmMatriculacionVO);
		if (ivtmMatriculacionVO != null) {
			return new ResultBean();
		} else {
			return new ResultBean(true, "Debe rellenar los datos del IVTM para poder guardarlo");
		}
	}

	@Override
	@Transactional
	public IvtmDatosSalidaMatriculasWS recuperarMatriculasWS(IvtmDatosEntradaMatriculasWS datosEntrada) {
		IvtmDatosSalidaMatriculasWS datosSalida = new IvtmDatosSalidaMatriculasWS();
		log.info("Llamado a consulta de Busqueda.");
		//Realiza la consulta
		IvtmResultadoWSMatriculasWS[] listaResultados = null;
		org.gestoresmadrid.core.ivtmMatriculacion.model.beans.IvtmResultadoWSMatriculasWS[]
				matriculas = ivtmMatriculacionDao.recuperarMatriculas(datosEntrada);

		if (matriculas != null) {
			listaResultados = new IvtmResultadoWSMatriculasWS[matriculas.length];
			for(int i=0; i < matriculas.length; i++){
				listaResultados[i] = conversor.transform(matriculas[i],
						org.gestoresmadrid.oegam2comun.trafico.dto.ivtm.IvtmResultadoWSMatriculasWS.class);
			}
		}

		//Procesamos la respuesta
		if (listaResultados==null){
			datosSalida.setCodigoResultado(ConstantesIVTM.RESULTADO_ERROR_INTERNO);
			log.info("Resultado: " + ConstantesIVTM.RESULTADO_ERROR_INTERNO);
		} else if (listaResultados.length==0){
			datosSalida.setCodigoResultado(ConstantesIVTM.RESULTADO_NO_HAY_RESULTADOS);
			log.info("Resultado: " + ConstantesIVTM.RESULTADO_NO_HAY_RESULTADOS);
		} else {
			datosSalida.setListaResultados(listaResultados);
			datosSalida.setCodigoResultado(ConstantesIVTM.RESULTADO_OK);
		}
		return datosSalida;
	}

	@Override
	@Transactional
	public ResultBean puedeGenerarAutoliquidacion(BigDecimal numExpediente) {
		ResultBean rs = new ResultBean();
		IvtmMatriculacionVO ivtmGuardado = null;
		try {
			ivtmGuardado = getIvtmPorExpediente(numExpediente);
			if ((ivtmGuardado != null) && (ivtmGuardado.getEstadoIvtm() != null)) {
				EstadoIVTM estadoGuardado = EstadoIVTM.convertir(ivtmGuardado.getEstadoIvtm().toString());
				if (!estaEnEstadoValidoParaAutoliquidar(estadoGuardado)) {
					rs.setError(Boolean.valueOf(true));
					rs.addMensajeALista(ConstantesIVTM.TEXTO_IVTM_NO_PERMISO_USO_AUTOLIQUIDACION);
				}
			}
		} catch (Exception e) {
			log.error("Error en el método puedeGenerarAutoliquidacion", e, numExpediente.toString());
		} finally {
			this.ivtmMatriculacionDao.evict(ivtmGuardado);
		}
		return rs;
	}

	@Override
	@Transactional
	public List<String> validarMatriculacion(TramiteTrafMatrDto tramiteDto) {
		List<String> errores = new ArrayList<>();
		IvtmMatriculacionDto ivtm = getIvtmPorExpedienteDto(tramiteDto.getNumExpediente());
		if (!validarVelocidadMaximaObligatoria(tramiteDto)) {
			errores.add("La velocidad máxima es obligatoria");
		}

		if (tramiteDto.getTitular() != null) {
			DireccionDto direccion = tramiteDto.getTitular().getDireccion();
			if (direccion == null || direccion.getIdMunicipio() == null || direccion.getIdProvincia() == null) {
				errores.add("Falta la dirección del titular");
				return errores;
			}
			boolean esMadrid = false;
			if (direccion.getIdProvincia().equals(ConstantesIVTM.PROVINCIA_MADRID) && direccion.getIdMunicipio().equals(ConstantesIVTM.MUNICIPIO_MADRID)) {
				esMadrid = true;
			}
			if (ivtm != null) {
				if (ivtm.getFechaPago() == null) {
					errores.add("Es obligatoria la fecha de pago");
				}
				if (!esMadrid && ivtm.getImporte() == null) {
					errores.add("El importe del IVTM es obligatorio");
				}
			}
		} else {
			errores.add("Falta el titular");
			return errores;
		}
		return errores;
	}

	private boolean validarVelocidadMaximaObligatoria(TramiteTrafMatrDto tramiteDto) {
		boolean validacion = true;
		List<String> listTiposVehiculos = new ArrayList<>();
		List<String> listTiposVehiculos7Exentos = new ArrayList<>();

		if (tramiteDto != null && tramiteDto.getVehiculoDto() != null
				&& tramiteDto.getVehiculoDto().getServicioTrafico().getIdServicio() != null) {
			listTiposVehiculos.add(TipoVehiculoEnum.TRACTOR.getValorEnum());
			listTiposVehiculos.add(TipoVehiculoEnum.TRACTOCAMION.getValorEnum());
			listTiposVehiculos.add(TipoVehiculoEnum.TRACTOCARRO.getValorEnum());
			listTiposVehiculos.add(TipoVehiculoEnum.REMOLQUE.getValorEnum());
			listTiposVehiculos.add(TipoVehiculoEnum.MAQUINARIA_AGRICOLA_ARRASTRADA_DE_2_EJES.getValorEnum());
			listTiposVehiculos.add(TipoVehiculoEnum.SEMIRREMOLQUE.getValorEnum());
			listTiposVehiculos.add(TipoVehiculoEnum.MAQUINARIA_AGRICOLA_ARRASTRADA_DE_1_EJE.getValorEnum());

			listTiposVehiculos7Exentos.add(TipoVehiculoEnum.QUITANIEVES.getValorEnum());
			listTiposVehiculos7Exentos.add(TipoVehiculoEnum.VIVIENDA.getValorEnum());
			listTiposVehiculos7Exentos.add(TipoVehiculoEnum.BARREDORA.getValorEnum());
			listTiposVehiculos7Exentos.add(TipoVehiculoEnum.HORMIGONERA.getValorEnum());
			listTiposVehiculos7Exentos.add(TipoVehiculoEnum.GRUA.getValorEnum());
			listTiposVehiculos7Exentos.add(TipoVehiculoEnum.SERVICIO_CONTRA_INCENDIOS.getValorEnum());
			listTiposVehiculos7Exentos.add(TipoVehiculoEnum.ASPIRADORA_DE_FANGOS.getValorEnum());
			listTiposVehiculos7Exentos.add(TipoVehiculoEnum.TREN_HASTA_160_PLAZAS.getValorEnum());

			if (tramiteDto.getVehiculoDto().getServicioTrafico().getIdServicio().equals(ServicioTraficoEnum.PART_AGRICOLA.getValorEnum())
					&& (listTiposVehiculos.contains(tramiteDto.getVehiculoDto().getTipoVehiculo()) || ((tramiteDto.getVehiculoDto().getTipoVehiculo() != null
					&& tramiteDto.getVehiculoDto().getTipoVehiculo().charAt(0) == '7') && !listTiposVehiculos7Exentos.contains(tramiteDto.getVehiculoDto().getTipoVehiculo())))
				|| (tramiteDto.getVehiculoDto().getIdDirectivaCee() != null && tramiteDto.getVehiculoDto().getIdDirectivaCee().charAt(0) == 'T')) {
				validacion = tramiteDto.getVehiculoDto().getVelocidadMaxima() != null;
			}
		}
		return validacion;
	}

	private boolean estaEnEstadoValidoParaAutoliquidar(EstadoIVTM estado) {
		if (estado == null || estado == EstadoIVTM.Ivtm_Error || estado == EstadoIVTM.Iniciado || estado == EstadoIVTM.Ivtm_Importado) {
			return true;
		} else if (estado == EstadoIVTM.Pendiente_Respuesta_Ayto
				|| estado == EstadoIVTM.Pendiente_Respuesta_Modificacion_Ayto
				|| estado == EstadoIVTM.Ivtm_Error_Modificacion
				|| estado == EstadoIVTM.Ivtm_Ok_Modificacion) {
			return false;
		}
		return (utilesColegiado.tienePermisoAdmin().booleanValue() && estado == EstadoIVTM.Ivtm_Ok);
	}

	@Override
	@Transactional(readOnly = true)
	public IvtmMatriculacionVO getIvtmPorExpediente(BigDecimal numExpediente) {
		return this.ivtmMatriculacionDao.getIvtmPorExpediente(numExpediente);
	}

	@Override
	@Transactional(readOnly = true)
	public IvtmMatriculacionDto getIvtmPorExpedienteDto(BigDecimal numExpediente) {
		IvtmMatriculacionVO ivtmMa = this.ivtmMatriculacionDao.getIvtmPorExpediente(numExpediente);
		return this.conversor.transform(ivtmMa, IvtmMatriculacionDto.class);
	}

	@Override
	@Transactional
	public ResultBean validarTramite(BigDecimal numExpediente) throws OegamExcepcion {
		ResultBean rs = new ResultBean();
		TramiteTrafMatrDto tramiteTrafMatrDto = servicioTramiteTraficoMatriculacion.getTramiteMatriculacion(numExpediente, false, true);
		if (tramiteTrafMatrDto != null) {
			IvtmMatriculacionDto ivtmDto = getIvtmPorExpedienteDto(numExpediente);
			rs.setListaMensajes(ValidacionIvtm.validarAlta(tramiteTrafMatrDto, ivtmDto));
		} else {
			rs.addMensajeALista(ConstantesIVTM.TEXTO_IVTM_ERROR_RECUPERANDO);
		}
		if (rs.getListaMensajes() != null && !rs.getListaMensajes().isEmpty()) {
			rs.setError(true);
		}
		return rs;
	}

	@Override
	@Transactional
	public ResultBean validarTramitePago(BigDecimal numExpediente) throws OegamExcepcion {
		ResultBean rs = new ResultBean();
		TramiteTrafMatrDto tramiteTrafMatrDto = servicioTramiteTraficoMatriculacion.getTramiteMatriculacion(numExpediente, false, true);
		if (tramiteTrafMatrDto != null) {
			IvtmMatriculacionDto ivtmDto = getIvtmPorExpedienteDto(numExpediente);
			rs.setListaMensajes(ValidacionIvtm.validarPago(tramiteTrafMatrDto, ivtmDto));
		} else {
			rs.addMensajeALista(ConstantesIVTM.TEXTO_IVTM_ERROR_RECUPERANDO);
		}
		if (rs.getListaMensajes() != null && !rs.getListaMensajes().isEmpty()) {
			rs.setError(true);
		}
		return rs;
	}

	@Override
	@Transactional
	public BigDecimal generarIdPeticion(String numColegiado) {
		BigDecimal idPeticion = null;
		BigDecimal maxExistenteIvtm = ivtmMatriculacionDao.idPeticionMax(numColegiado);
		BigDecimal maxExistenteConsultas = consultasIvtmDao.idPeticionMax(numColegiado);

		if (maxExistenteIvtm == null && maxExistenteConsultas == null) {
			String peticion = numColegiado;
			Fecha fecha = new Fecha(new SimpleDateFormat("dd/MM/yyyy").format(new Date()));
			peticion += fecha.getDia() + fecha.getMes() + fecha.getAnio().substring(2);
			peticion += ConstantesIVTM.TIPO_ID_PETICION_IVTM;
			peticion += "00001";
			idPeticion = new BigDecimal(peticion);
		} else {
			if (maxExistenteIvtm == null) {
				idPeticion = new BigDecimal(maxExistenteConsultas.longValue() + 1);
			} else if (maxExistenteConsultas == null) {
				idPeticion = new BigDecimal(maxExistenteIvtm.longValue() + 1);
			} else {
				idPeticion = maxExistenteIvtm.max(maxExistenteConsultas);
				idPeticion = new BigDecimal(idPeticion.longValue() + 1);
			}
		}
		return idPeticion;
	}

	@Override
	@Transactional
	public ResultBean guardarDatosImportados(MATRICULACION matriculacionGA, BigDecimal numExp) {
		ResultBean resultado = new ResultBean();
		if (matriculacionGA != null && matriculacionGA.getDATOSIMPUESTOS() != null && matriculacionGA.getDATOSIMPUESTOS().getDATOSIMVTM() != null) {
			guardarDatosImportados(new DatosIvtmMatw(matriculacionGA.getDATOSIMPUESTOS().getDATOSIMVTM()), numExp, resultado);
			if (resultado.getError()) {
				log.debug("No están rellenos los datos correspondientes a IVTM ");
				resultado.setMensaje("Faltan datos correspondientes a IVTM ");
			}
		}
		return resultado;
	}

	private void guardarDatosImportados(DatosIvtm datosIvtm, BigDecimal numExp, ResultBean resultado) {
		IvtmMatriculacionVO ivtm = new IvtmMatriculacionVO();
		ivtm.setNumExpediente(numExp);
		ivtm.setEstadoIvtm(new BigDecimal(EstadoIVTM.Ivtm_Importado.getValorEnum()));
		if (datosIvtm.getFECHAALTAAUTOLIQUIDACIONIMVTM() != null && !"".equals(datosIvtm.getFECHAALTAAUTOLIQUIDACIONIMVTM())) {
			try {
				Fecha fechaPago = new Fecha(datosIvtm.getFECHAALTAAUTOLIQUIDACIONIMVTM());
				ivtm.setFechaPago(fechaPago.getFecha());
			} catch (ParseException e) {
				log.error("Error al introducir la fecha de pago " + e);
				resultado.addMensajeALista("Problemas con la fecha de pago");
				resultado.setError(true);
			}
		}
		ivtm.setImporte(datosIvtm.getCUOTAAPAGARIMVTM());
		boolean bonMed = false;
		if (datosIvtm.getBONOMEDIOAMBIENTE() != null) {
			bonMed = datosIvtm.getBONOMEDIOAMBIENTE().equals(TipoSINO.SI.value());
		}
		ivtm.setBonmedam(bonMed);
		ivtm.setCodGestor(datosIvtm.getCODIGOGESTOR());
		ivtm.setDigitoControl(datosIvtm.getDIGITOCONTROL());
		ivtm.setEmisor(datosIvtm.getEMISOR());
		boolean exento = false;
		if (datosIvtm.getEXENTOPAGO() != null) {
			exento = datosIvtm.getEXENTOPAGO().equals(TipoSINO.SI.value());
		}
		ivtm.setExento(exento);
		ivtm.setFechaRequerida(new Date());
		ivtm.setIban(datosIvtm.getIBAN());
		if (datosIvtm.getEXENTOPAGO() != null && datosIvtm.getEXENTOPAGO().equals(TipoSINO.SI)) {
			ivtm.setImporte(new BigDecimal(0));
			ivtm.setImporteAnual(new BigDecimal(0));
		} else {
			ivtm.setImporte(datosIvtm.getCUOTAAPAGARIMVTM());
			ivtm.setImporteAnual(datosIvtm.getIMPORTEANUAL());
		}
		ivtm.setNrc(datosIvtm.getNRC());
		ivtm.setPorcentajebonmedam(datosIvtm.getPORCBONOMEDIOAMBIENTE());
		ivtmMatriculacionDao.guardarOActualizar(ivtm);
	}

	@Override
	public ResultBean validarFORMATOIVTMGA(FORMATOGA ga, boolean tienePermisoIVTM) {
		ResultBean resultado = new ResultBean();
		if (ga != null && ga.getMATRICULACION() != null) {
			for (MATRICULACION matriculacionAux : ga.getMATRICULACION()) {
				if (matriculacionAux.getDATOSIMPUESTOS() != null && matriculacionAux.getDATOSIMPUESTOS().getDATOSIMVTM() != null) {
					DatosIvtm datosIvtm = new DatosIvtmMatw(matriculacionAux.getDATOSIMPUESTOS().getDATOSIMVTM());
					if (matriculacionAux.getDATOSTITULAR() != null) {
						validarFORMATOIVTMGA(datosIvtm, matriculacionAux.getDATOSTITULAR().getMUNICIPIOTITULAR(), resultado, tienePermisoIVTM);
					} else {
						validarFORMATOIVTMGA(datosIvtm, null, resultado, tienePermisoIVTM);
					}
				}
			}
		}
		return resultado;
	}

	private void validarFORMATOIVTMGA(DatosIvtm datosIvtm, String municipio, ResultBean resultado, boolean tienePermisoIVTM) {
		if (tienePermisoIVTM) {
			if (esMadrid(municipio)) {
				if (datosIvtm.getIBAN() != null && (datosIvtm.getIBAN().length() != 24 || !ValidacionIvtm.validarIbanIVTM(datosIvtm.getIBAN()))) {
					resultado.addMensajeALista(ConstantesIVTM.TEXTO_IBAN_OBLIGATORIO);
					resultado.setError(true);
				}
			} else if (datosIvtm.getEXENTOPAGO() != null && !datosIvtm.getEXENTOPAGO().equals(TipoSINO.SI.value())
					&& datosIvtm.getCUOTAAPAGARIMVTM() == null) {
				resultado.addMensajeALista(ConstantesIVTM.TEXTO_IVTM_OBLIGATORIO_IMPORTE);
				resultado.setError(true);
			}
		}
	}

	private boolean esMadrid(String municipio) {
		if (municipio == null) {
			return false;
		}
		return municipio.equalsIgnoreCase(ConstantesIVTM.MUNICIPIO_MADRID_IMPO);
	}

	@Override
	@Transactional
	public String crearUrl(boolean desdeConsulta, IvtmMatriculacionDto ivtmMatriculacionDto, TramiteTrafMatrDto tramiteTrafMatrDto) {
		// Procesar URL añadiendo campos
		String url = gestorPropiedades.valorPropertie("url.pagoIVTM");
		String aplicacion = gestorPropiedades.valorPropertie("ivtm.pago.aplicacion");
		String entorno = gestorPropiedades.valorPropertie("ivtm.pago.entorno");
		String tipoServicio = gestorPropiedades.valorPropertie("ivtm.pago.tipoServicio");

		if (desdeConsulta) {
			url = url.replace(URL_APLICACION, gestorPropiedades.valorPropertie("ivtm.pago.direccionRetornoConsulta") + "?numExpediente=" + tramiteTrafMatrDto.getNumExpediente());
		} else {
			url = url.replace(URL_APLICACION, gestorPropiedades.valorPropertie("ivtm.pago.direccionRetornoTramite") + "?numExpediente=" + tramiteTrafMatrDto.getNumExpediente());
		}

		url = url.replace(ENTORNO, entorno);
		url = url.replace(TIPO_SERVICIO, tipoServicio);
		url = url.replace(APLICACION, aplicacion);

		// TODO: valores por defecto de momento (puede ser que el emisor y gestor sean siempre los mismos datos)
		url = url.replace(ID_EMISOR, "1");
		url = url.replace(IDENTIFICADOR, "41330000040001059");
		url = url.replace(GESTOR, "44410");
		url = url.replace(DC, "39");
		// url = url.replace(ID_EMISOR, ivtmMatriculacionDto.getEmisor());
		// url = url.replace(IDENTIFICADOR, ivtmMatriculacionDto.getNrc());
		// url = url.replace(GESTOR, ivtmMatriculacionDto.getCodGestor());
		// url = url.replace(DC, ivtmMatriculacionDto.getDigitoControl());
		try {
			url = url.replace(FECHA_CONTROL, utilesFecha.formatoFecha("dd-MM-yyyy", ivtmMatriculacionDto.getFechaPago().getDate()));
		} catch (ParseException e) {
			log.error("Error al convertir la fecha de pago: " + e.getMessage());
		}

		int importe = ivtmMatriculacionDto.getImporte().intValue();
		url = url.replace(IMPORTE, String.valueOf(importe * 100));
		url = url.replace(NIF, tramiteTrafMatrDto.getTitular().getPersona().getNif());
		// TODO: 01 = CENTRO
		url = url.replace(DISTRITO_MUNICIPAL, "01");
		// url = url.replace(DISTRITO_MUNICIPAL, traficoTramiteMatriculacionBean.getTitularBean().getPersona().getDireccion().getMunicipio().getIdMunicipio());

		url = url.replace(" ", "%20");

		// URL de prueba
		/*
		 * if (desdeConsulta) { url =
		 * "https://test.munimadrid.es:444/SBAE_058_SFSeleccionTipoPago_Internet/tipoPagoTelematico.do?URLRETORNO=http://localhost:8080/oegam2/vueltaPagoConsultaIVTMRespuestaPagoIVTM.action?numExpediente=205628111400007&codigo=0&mensaje=&numOperacion=1&resultadoIntegracion=0&cadena=&firmaRetorno=&IDEMISOR=1&Identificador=41330000040001059&Gestor=44410&DC=39&Fecha_de_Control=20-10-2014&Importe=00000000001230&NIF%2FCIF=11111111H&Distrito_Municipal=01&ENTORNO=2&APLICACION=Movilidad&TIPOSERVICIO=SERVICIO_BASICO"
		 * ; } else { url =
		 * "https://test.munimadrid.es:444/SBAE_058_SFSeleccionTipoPago_Internet/tipoPagoTelematico.do?URLRETORNO=http://localhost:8080/oegam2/vueltaPagoTramiteIVTMRespuestaPagoIVTM.action?numExpediente=205628111400007&codigo=0&mensaje=&numOperacion=1&resultadoIntegracion=0&cadena=&firmaRetorno=&IDEMISOR=1&Identificador=41330000040001059&Gestor=44410&DC=39&Fecha_de_Control=20-10-2014&Importe=00000000001230&NIF%2FCIF=11111111H&Distrito_Municipal=01&ENTORNO=2&APLICACION=Movilidad&TIPOSERVICIO=SERVICIO_BASICO"
		 * ; }
		 */
		return url;
	}

	@Override
	@Transactional
	public DatosIvtm obtenerDatosParaImportacion(BigDecimal numExpediente, boolean tienePermisoIVTM) {
		IvtmMatriculacionDto dto = getIvtmPorExpedienteDto(numExpediente);
		DatosIvtm ivtm = new DatosIvtmMatw();
		if (dto != null) {
			if (null != dto.getFechaPago()) {
				ivtm.setFECHAALTAAUTOLIQUIDACIONIMVTM(dto.getFechaPago().toString());
			}
			ivtm.setCUOTAAPAGARIMVTM(dto.getImporte());
			if (tienePermisoIVTM) {
				ivtm.setBONOMEDIOAMBIENTE(dto.isBonmedam() != null && dto.isBonmedam() ? TipoSINO.SI.value() : TipoSINO.NO.value());
				ivtm.setCODIGOGESTOR(dto.getCodGestor());
				if (dto.getCodigoRespuestaPago() != null) {
					ivtm.setCODIGORESPUESTAPAGO(dto.getCodigoRespuestaPago().getValorEnum());
				}
				ivtm.setDIGITOCONTROL(dto.getDigitoControl());
				ivtm.setEMISOR(dto.getEmisor());
				ivtm.setEXENTOPAGO(dto.isExento() != null && dto.isExento() ? TipoSINO.SI.value() : TipoSINO.NO.value());
				ivtm.setIBAN(dto.getIban());
				ivtm.setIMPORTEANUAL(dto.getImporteAnual());
				ivtm.setPORCBONOMEDIOAMBIENTE(dto.getPorcentajebonmedam());
				ivtm.setNRC(dto.getNrc());
				if (dto.getCodigoRespuestaPago() != null)
					ivtm.setCODIGORESPUESTAPAGO(dto.getCodigoRespuestaPago().getValorEnum());
				ivtm.setIDPETICIONIVTM(dto.getIdPeticion() != null ? dto.getIdPeticion().toString() : "");
			}
			return ivtm;
		}
		return null;
	}
	/*
	 *  listaResultados = conversor.transform(ivtmMatriculacionDao.recuperarMatriculas(datosEntrada),
			org.gestoresmadrid.oegam2comun.trafico.dto.ivtm.IvtmResultadoWSMatriculasWS[].class);
			IvtmResultadoWSMatriculasWS[]
	 */

	@Override
	public ResultBean encolarConsultar(IvtmConsultaMatriculacionDto ivtmConsultaMatriculacionDto, BigDecimal idUsuario) {
		log.debug("Ha entrado método solicitar servicio consulta");
		ResultBean resultCola = null;
		try{
			//IvtmConsultaMatriculacion ivtmConsulta = conversor.transform(ivtmMatriculacionDto, IvtmConsultaMatriculacion.class);
			resultCola = servicioCola.crearSolicitud(ProcesosEnum.IVTM_CONSULTA.getNombreEnum(), ConstantesIVTM.TIPO_CONSULTA_IVTM_WS, gestorPropiedades.valorPropertie(ServicioIvtmMatriculacion.NOMBRE_HOST_SOLICITUD), TipoTramiteTrafico.Consulta_IVTM.getValorEnum(),ivtmConsultaMatriculacionDto.getIdPeticion().toString() , idUsuario, null, utilesColegiado.getIdContratoSessionBigDecimal());
		} catch(OegamExcepcion e){
			log.error("Ha sucedido un error a la hora de generar la consulta de IVTM , error: ", e);
		}
		return resultCola;
	}

	@Override
	public BigDecimal guardarDatosConsulta(IvtmConsultaMatriculacionDto ivtmConsultaMatriculacionDto) {
		ConsultasIvtmVO consultasIvtmVO = conversor.transform(ivtmConsultaMatriculacionDto, ConsultasIvtmVO.class);
		return null;
	}

}