/**
 * 
 */
package trafico.modelo.ivtm.impl;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.gestoresmadrid.core.model.enumerados.TipoInterviniente;
import org.gestoresmadrid.oegam2comun.proceso.enumerados.ProcesosEnum;
import org.gestoresmadrid.oegam2comun.trafico.dto.ivtm.IvtmDatosEntradaMatriculasWS;
import org.gestoresmadrid.oegam2comun.trafico.dto.ivtm.IvtmDatosSalidaMatriculasWS;
import org.gestoresmadrid.oegam2comun.trafico.dto.ivtm.IvtmMatriculacionDto;
import org.gestoresmadrid.oegam2comun.trafico.dto.ivtm.IvtmResultadoWSMatriculasWS;
import org.gestoresmadrid.oegamComun.acceso.service.impl.UtilesColegiado;
import org.gestoresmadrid.utilidades.components.UtilesFecha;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import colas.modelo.ModeloSolicitud;
import escrituras.beans.ResultBean;
//TODO MPC. Cambio IVTM. Clase nueva.
import hibernate.entities.personas.Persona;
import hibernate.entities.personas.PersonaDireccion;
import hibernate.entities.trafico.IntervinienteTrafico;
import hibernate.entities.trafico.IvtmMatriculacion;
import trafico.beans.jaxb.matriculacion.DatosIvtm;
import trafico.beans.jaxb.matriculacion.DatosIvtmMate;
import trafico.beans.jaxb.matw.DatosIvtmMatw;
import trafico.beans.jaxb.matw.FORMATOGA;
import trafico.beans.jaxb.matw.FORMATOGA.MATRICULACION;
import trafico.beans.schemas.generated.transTelematica.TipoSINO;
import trafico.dao.GenericDao;
import trafico.dao.implHibernate.AliasQueryBean;
import trafico.dao.implHibernate.GenericDaoImplHibernate;
import trafico.dao.implHibernate.ivtm.IvtmMatriculacionDaoImplHibernate;
import trafico.dao.ivtm.IvtmMatriculacionDaoInterface;
import trafico.dto.TramiteTraficoDto;
import trafico.generarPeticiones.ivtm.GenerarPeticionIVTM;
import trafico.modelo.impl.ModeloTramiteTrafImpl;
import trafico.modelo.ivtm.IVTMModeloMatriculacionInterface;
import trafico.utiles.constantes.ConstantesIVTM;
import trafico.utiles.enumerados.EstadoIVTM;
import trafico.utiles.enumerados.RespuestaPagoIVTM;
import trafico.utiles.enumerados.TipoTramiteTrafico;
import trafico.utiles.enumerados.TipoVehiculoIvtm;
import trafico.validaciones.ivtm.ValidacionIvtm;
import utilidades.estructuras.Fecha;
import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;
import utilidades.web.CrearSolicitudExcepcion;
import utilidades.web.OegamExcepcion;

public class IVTMModeloMatriculacionImpl extends IVTMModeloImpl implements IVTMModeloMatriculacionInterface {

	private IvtmMatriculacionDaoInterface ivtmDao;
	private static final String CADENA_VACIA = "";

	private static final ILoggerOegam log = LoggerOegam.getLogger(IVTMModeloMatriculacionImpl.class);

	@Autowired
	UtilesFecha utilesFecha;

	@Autowired
	private UtilesColegiado utilesColegiado;

	public IVTMModeloMatriculacionImpl() {
		log.info("Antes de contructor Dao ImplHibernate");
		SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
		ivtmDao = new IvtmMatriculacionDaoImplHibernate(new IvtmMatriculacion());
		log.info("Después de contructor Dao ImplHibernate");
	}

	@Override
	public IvtmMatriculacionDto guardar(IvtmMatriculacionDto ivtmDto) throws OegamExcepcion {
		IvtmMatriculacion ivtm;
		try {
			IvtmMatriculacion existe =ivtmDao.buscarPorExpediente(ivtmDto.getNumExpediente());
			if (existe == null) {
				try {
					ivtm = convertDtoToEntity(ivtmDto);
				} catch (ParseException e) {
					log.error(e.getMessage());
					throw new OegamExcepcion("Error: al parsear de fecha a date");
				}
				BigDecimal numExpediente = (BigDecimal) ivtmDao.guardar(ivtm);

				ivtm = ivtmDao.buscarPorExpediente(numExpediente);
			} else{
				return this.actualizar(ivtmDto);
			}
		} catch (Exception e) {
			log.error(e);
			throw new OegamExcepcion("Error: al guardar IVTM");
		}
		return convertEntityToDto(ivtm);
	}

	@Override
	public ResultBean guardarDatosImportados(MATRICULACION matriculacionGA, BigDecimal numExp, boolean tienePermisoIVTM) {
		ResultBean resultado = new ResultBean();
		if (matriculacionGA != null && matriculacionGA.getDATOSIMPUESTOS() != null
				&& matriculacionGA.getDATOSIMPUESTOS().getDATOSIMVTM() != null) {
			guardarDatosImportados(new DatosIvtmMatw(matriculacionGA.getDATOSIMPUESTOS().getDATOSIMVTM()), numExp, resultado, tienePermisoIVTM,matriculacionGA);
			if (resultado.getError()){
				log.debug("No están rellenos los datos correspondientes a IVTM ");
				resultado.setMensaje("Faltan datos correspondientes a IVTM ");
			}
		}
		return resultado;
	}

	public DatosIvtm obtenerDatosParaImportacionMatw(BigDecimal numExpediente, boolean tienePermisoIVTM){
		return obtenerDatosParaImportacion(new DatosIvtmMatw(), numExpediente, tienePermisoIVTM);
	}

	@Override
	public DatosIvtm obtenerDatosParaImportacion(BigDecimal numExpediente, boolean tienePermisoIVTM) {
		return obtenerDatosParaImportacion(new DatosIvtmMate(), numExpediente, tienePermisoIVTM);
	}

	private DatosIvtm obtenerDatosParaImportacion(DatosIvtm ivtm, BigDecimal numExpediente, boolean tienePermisoIVTM) {
		IVTMModeloMatriculacionInterface modeloIVTM = new IVTMModeloMatriculacionImpl();
		IvtmMatriculacionDto dto = modeloIVTM.buscarIvtmNumExp(numExpediente);
		if (dto != null) {
			if (null != dto.getFechaPago()) {
				ivtm.setFECHAALTAAUTOLIQUIDACIONIMVTM(dto.getFechaPago().toString());
			}
			ivtm.setCUOTAAPAGARIMVTM(dto.getImporte());
			if (tienePermisoIVTM) {
				ivtm.setBONOMEDIOAMBIENTE(
						dto.isBonmedam() != null && dto.isBonmedam() ? TipoSINO.SI.value() : TipoSINO.NO.value());
				ivtm.setCODIGOGESTOR(dto.getCodGestor());
				if (dto.getCodigoRespuestaPago() != null) {
					ivtm.setCODIGORESPUESTAPAGO(dto.getCodigoRespuestaPago().getValorEnum());
				}
				ivtm.setDIGITOCONTROL(dto.getDigitoControl());
				ivtm.setEMISOR(dto.getEmisor());
				ivtm.setEXENTOPAGO(dto.isExento()!=null && dto.isExento()?TipoSINO.SI.value():TipoSINO.NO.value());
				ivtm.setIBAN(dto.getIban());
				ivtm.setIMPORTEANUAL(dto.getImporteAnual());
				ivtm.setPORCBONOMEDIOAMBIENTE(dto.getPorcentajebonmedam());
				ivtm.setNRC(dto.getNrc());
				if(dto.getCodigoRespuestaPago() != null)
					ivtm.setCODIGORESPUESTAPAGO(dto.getCodigoRespuestaPago().getValorEnum());
				ivtm.setIDPETICIONIVTM(dto.getIdPeticion()!= null ? dto.getIdPeticion().toString() : CADENA_VACIA);
			}
			return ivtm;
		}
		return null;
	}

	private void guardarDatosImportados(DatosIvtm datosIvtm, BigDecimal numExp, ResultBean resultado, boolean tienePermisoIVTM, MATRICULACION matriculacionGA) {
		IvtmMatriculacion ivtm = new IvtmMatriculacion();
		ivtm.setNumExpediente(numExp);
		//log.error("Mantis 17184: Se va a guardar el siguiente tramite en la tabla IVTM:" +numExp.toString(),numExp.toString());
		ivtm.setEstadoIvtm(new BigDecimal(EstadoIVTM.Ivtm_Importado.getValorEnum()));
		if (datosIvtm.getFECHAALTAAUTOLIQUIDACIONIMVTM() != null
				&& !"".equals(datosIvtm.getFECHAALTAAUTOLIQUIDACIONIMVTM())) {
			try {
				Fecha fechaPago = new Fecha(datosIvtm.getFECHAALTAAUTOLIQUIDACIONIMVTM());
				ivtm.setFechaPago(fechaPago.getFecha());
			} catch (ParseException e) {
				log.error("Error al introducir la fecha de pago "+e);
				resultado.addMensajeALista("Problemas con la fecha de pago");
				resultado.setError(true);
			}
		}
		ivtm.setImporte(datosIvtm.getCUOTAAPAGARIMVTM());
		//log.error("Mantis 17184: Tiene permiso IVTM: "+tienePermisoIVTM,numExp.toString());
		if (tienePermisoIVTM){
			boolean bonMed = false;
			if (datosIvtm.getBONOMEDIOAMBIENTE()!=null){
				bonMed= datosIvtm.getBONOMEDIOAMBIENTE().equals(TipoSINO.SI.value())?true:false;
			}
			ivtm.setBastidor(matriculacionGA.getDATOSVEHICULO().getNUMEROBASTIDOR());
			ivtm.setNumColegiado(matriculacionGA.getNUMEROPROFESIONAL().toString());
			ivtm.setBonmedam(bonMed);
			ivtm.setCodGestor(datosIvtm.getCODIGOGESTOR());
			ivtm.setDigitoControl(datosIvtm.getDIGITOCONTROL());
			ivtm.setEmisor(datosIvtm.getEMISOR());
			boolean exento = false;
			if (datosIvtm.getEXENTOPAGO()!=null){
				exento = datosIvtm.getEXENTOPAGO().equals(TipoSINO.SI.value())?true:false;
			}
			ivtm.setExento(exento);	
			ivtm.setFechaReq(new Date());
			ivtm.setIban(datosIvtm.getIBAN());
			if (datosIvtm.getEXENTOPAGO()!=null && datosIvtm.getEXENTOPAGO().equals(TipoSINO.SI)){
				ivtm.setImporte(new BigDecimal(0));
				ivtm.setImporteAnual(new BigDecimal(0));
			} else {
				ivtm.setImporte(datosIvtm.getCUOTAAPAGARIMVTM());
				ivtm.setImporteAnual(datosIvtm.getIMPORTEANUAL());
			}
			ivtm.setNrc(datosIvtm.getNRC());
			ivtm.setPorcentajebonmedam(datosIvtm.getPORCBONOMEDIOAMBIENTE());
		}
		ivtmDao.guardar(ivtm);
	}

	private boolean esMadrid(String municipio){
		if (municipio ==null){
			return false;
		}
		return municipio.equalsIgnoreCase(ConstantesIVTM.MUNICIPIO_MADRID_IMPO);
	}

	private void validarFORMATOIVTMGA(DatosIvtm datosIvtm, String municipio, ResultBean resultado, boolean tienePermisoIVTM) {
		if (tienePermisoIVTM){
			if (esMadrid(municipio)){
				if (datosIvtm.getIBAN()!=null && (datosIvtm.getIBAN().length()!=24 || !ValidacionIvtm.validarIbanIVTM(datosIvtm.getIBAN()))) {
					resultado.addMensajeALista(ConstantesIVTM.TEXTO_IBAN_OBLIGATORIO);
					resultado.setError(true);
				}
			} else { //El titular no es de Madrid
				if (datosIvtm.getEXENTOPAGO()!=null && !datosIvtm.getEXENTOPAGO().equals(TipoSINO.SI.value())
						&& datosIvtm.getCUOTAAPAGARIMVTM() == null) {
					resultado.addMensajeALista(ConstantesIVTM.TEXTO_IVTM_OBLIGATORIO_IMPORTE);
					resultado.setError(true);
				}
			}
		}
		// LA FECHA DEL PAGOIVTM NO ES OBLIGATORIA para que se pueda importar. Mantis 21164
		// La fecha se valida siempre
		//if (datosIvtm.getFECHAALTAAUTOLIQUIDACIONIMVTM()==null || datosIvtm.getFECHAALTAAUTOLIQUIDACIONIMVTM().isEmpty()){
		//	resultado.addMensajeALista(PropertiesReader.get(ConstantesIVTM.TEXTO_FECHA_OBLIGATORIA) + "pago");
		//	resultado.setError(true);
		//}
	}

	/**
	 * @see IVTMModeloMatriculacionInterface
	 */
	public ResultBean validarFORMATOIVTMGA(FORMATOGA ga, boolean tienePermisoIVTM) {
		ResultBean resultado = new ResultBean();
		if (ga != null && ga.getMATRICULACION() != null) {
			for (MATRICULACION matriculacionAux: ga.getMATRICULACION()) {
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

	@Override
	public IvtmMatriculacionDto actualizar(IvtmMatriculacionDto ivtmDto) throws OegamExcepcion {
		IvtmMatriculacion ivtm;

		try {
			ivtm = convertDtoToEntity(ivtmDto);
		} catch (ParseException e) {
			log.error(e.getMessage());
			throw new OegamExcepcion("Error: al parsear de fecha a date");
		}
		try {
			IvtmMatriculacion ivtmRecuperado = ivtmDao.buscarPorExpediente(ivtm.getNumExpediente());
			Class c = ivtmRecuperado.getClass();
			for (Method metodo: c.getDeclaredMethods() ){
				if (metodo.getName().startsWith("get")
						&& null != metodo.invoke(ivtm, new Class[]{})) {
					String atributo = metodo.getName().replaceFirst("get", "");
					Class claseParametro = metodo.getReturnType();
					Method metodoSet = c.getDeclaredMethod("set" + atributo, new Class[] { claseParametro });
					metodoSet.invoke(ivtmRecuperado, metodo.invoke(ivtm, new Class[] {}));
				}
				if (metodo.getName().startsWith("is") && null != metodo.invoke(ivtm, new Class[] {})) {
					String atributo = metodo.getName().replaceFirst("is", "");
					Class claseParametro = metodo.getReturnType();
					Method metodoSet = c.getDeclaredMethod("set" + atributo, new Class[] { claseParametro });
					metodoSet.invoke(ivtmRecuperado, metodo.invoke(ivtm, new Class[] {}));
				}
			}
			ivtm = (IvtmMatriculacion) ivtmDao.actualizar(ivtmRecuperado);
		} catch (Exception e) {
			log.error(e);
			throw new OegamExcepcion("Error: al guardar IVTM");
		}
		return convertEntityToDto(ivtm);
	}

	public IvtmMatriculacionDto recuperarPorNumExp(BigDecimal numExpediente) {
		if (numExpediente == null) {
			return null;
		}
		IvtmMatriculacion ivtm = ivtmDao.buscarPorExpediente(numExpediente);
		return convertEntityToDto(ivtm);
	}

	@Override
	public IvtmMatriculacionDto buscarIvtmNumExp(BigDecimal numExpediente) {
		IvtmMatriculacion ivtmMa = ivtmDao.buscarPorExpediente(numExpediente);
		return convertEntityToDto(ivtmMa);
	}

	@Override
	public boolean solicitarIVTM(BigDecimal numExpediente, IvtmMatriculacionDto ivtmMatriculacionDto) {
		BigDecimal usuario = utilesColegiado.getIdUsuarioSessionBigDecimal();
		try {
			new ModeloSolicitud().crearSolicitud(numExpediente, usuario, TipoTramiteTrafico.Matriculacion, ProcesosEnum.IVTM_ALTA.getNombreEnum(),
					ConstantesIVTM.TIPO_ALTA_IVTM_WS);
			return true;
		} catch (CrearSolicitudExcepcion e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public IvtmMatriculacionDto actualizarEstado(IvtmMatriculacionDto ivtmDto) {
		IvtmMatriculacion ivtm = new IvtmMatriculacion();
		ivtm.setNumExpediente(ivtmDto.getNumExpediente());
		ivtm.setEstadoIvtm(ivtmDto.getEstadoIvtm());

		ivtm = ivtmDao.actualizar(ivtm);

		return convertEntityToDto(ivtm);
	}

	@Override
	public IvtmMatriculacionDto convertEntityToDto(IvtmMatriculacion ivtm) {
		IvtmMatriculacionDto ivtmDto = new IvtmMatriculacionDto();
		if (ivtm == null) {
			return null;
		}
		if (ivtm.isBonmedam() != null)
			ivtmDto.setBonmedam(ivtm.isBonmedam());
		if (ivtm.getCodGestor() != null && !ivtm.getCodGestor().equals(""))
			ivtmDto.setCodGestor(ivtm.getCodGestor());
		if (ivtm.getCodigoRespuestaPago() != null && !ivtm.getCodigoRespuestaPago().equals(""))
			ivtmDto.setCodigoRespuestaPago(RespuestaPagoIVTM.convertir(ivtm.getCodigoRespuestaPago()));
		if (ivtm.getDigitoControl() != null && !ivtm.getDigitoControl().equals(""))
			ivtmDto.setDigitoControl(ivtm.getDigitoControl());
		if (ivtm.getEmisor() != null && !ivtm.getEmisor().equals(""))
			ivtmDto.setEmisor(ivtm.getEmisor());
		if (ivtm.getEstadoIvtm() != null)
			ivtmDto.setEstadoIvtm(ivtm.getEstadoIvtm());
		if (ivtm.isExento() != null)
			ivtmDto.setExento(ivtm.isExento());
		if (ivtm.getFechaPago() != null)
			ivtmDto.setFechaPago(utilesFecha.getFechaConDate(ivtm.getFechaPago()));
		if (ivtm.getFechaReq() != null)
			ivtmDto.setFechaReq(utilesFecha.getFechaConDate(ivtm.getFechaReq()));
		if (ivtm.getIban() != null && !ivtm.getIban().equals(""))
			ivtmDto.setIban(ivtm.getIban());
		if (ivtm.getIdPeticion() != null)
			ivtmDto.setIdPeticion(ivtm.getIdPeticion());
		if (ivtm.getImporte() != null)
			ivtmDto.setImporte(ivtm.getImporte());
		if (ivtm.getImporteAnual() != null)
			ivtmDto.setImporteAnual(ivtm.getImporteAnual());
		if (ivtm.getMensaje() != null && !ivtm.getMensaje().equals(""))
			ivtmDto.setMensaje(ivtm.getMensaje());
		if (ivtm.getNrc() != null && !ivtm.getNrc().equals(""))
			ivtmDto.setNrc(ivtm.getNrc());
		if (ivtm.getNumExpediente() != null)
			ivtmDto.setNumExpediente(ivtm.getNumExpediente());
		if (ivtm.getPorcentajebonmedam() != null)
			ivtmDto.setPorcentajebonmedam(ivtm.getPorcentajebonmedam());
		if (ivtm.getBastidor() != null && !ivtm.getBastidor().equals(""))
			ivtmDto.setBastidor(ivtm.getBastidor());
		if (ivtm.getNumColegiado() != null && !ivtm.getNumColegiado().equals(""))
			ivtmDto.setNumColegiado(ivtm.getNumColegiado());

		return ivtmDto;
	}
	@Override
	public IvtmMatriculacion convertDtoToEntity(IvtmMatriculacionDto ivtmDto) throws ParseException {
		IvtmMatriculacion ivtm = new IvtmMatriculacion();

		if (ivtmDto.isBonmedam() != null)
			ivtm.setBonmedam(ivtmDto.isBonmedam());
		if (ivtmDto.getCodGestor() != null && !ivtmDto.getCodGestor().equals(""))
			ivtm.setCodGestor(ivtmDto.getCodGestor());
		if (ivtmDto.getCodigoRespuestaPago() != null)
			ivtm.setCodigoRespuestaPago(ivtmDto.getCodigoRespuestaPago().getValorEnum());
		if (ivtmDto.getDigitoControl() != null && !ivtmDto.getDigitoControl().equals(""))
			ivtm.setDigitoControl(ivtmDto.getDigitoControl());
		if (ivtmDto.getEmisor() != null && !ivtmDto.getEmisor().equals(""))
			ivtm.setEmisor(ivtmDto.getEmisor());
		if (ivtmDto.getEstadoIvtm() != null)
			ivtm.setEstadoIvtm(ivtmDto.getEstadoIvtm());
		if (ivtmDto.isExento() != null)
			ivtm.setExento(ivtmDto.isExento());
		if (ivtmDto.getFechaPago() != null && !ivtmDto.getFechaPago().isfechaNula())
			ivtm.setFechaPago(ivtmDto.getFechaPago().getDate());
		if (ivtmDto.getFechaReq() != null && !ivtmDto.getFechaReq().isfechaHoraNula())
			ivtm.setFechaReq(ivtmDto.getFechaReq().getDate());
		if (ivtmDto.getIban() != null && !ivtmDto.getIban().equals(""))
			ivtm.setIban(ivtmDto.getIban());
		if (ivtmDto.getIdPeticion() != null)
			ivtm.setIdPeticion(ivtmDto.getIdPeticion());
		if (ivtmDto.getImporte() != null)
			ivtm.setImporte(ivtmDto.getImporte());
		if (ivtmDto.getImporteAnual() != null)
			ivtm.setImporteAnual(ivtmDto.getImporteAnual());
		if (ivtmDto.getMensaje() != null && !ivtmDto.getMensaje().equals(""))
			ivtm.setMensaje(ivtmDto.getMensaje());
		if (ivtmDto.getNrc() != null && !ivtmDto.getNrc().equals(""))
			ivtm.setNrc(ivtmDto.getNrc());
		if (ivtmDto.getNumExpediente() != null)
			ivtm.setNumExpediente(ivtmDto.getNumExpediente());
		if (ivtmDto.getPorcentajebonmedam() != null)
			ivtm.setPorcentajebonmedam(ivtmDto.getPorcentajebonmedam());
		if (ivtmDto.getBastidor() != null && !ivtmDto.getBastidor().equals(""))
			ivtm.setBastidor(ivtmDto.getBastidor());
		if (ivtmDto.getNumColegiado() != null && !ivtmDto.getNumColegiado().equals(""))
			ivtm.setNumColegiado(ivtmDto.getNumColegiado());

		return ivtm;
	}

	public TipoVehiculoIvtm obtenerTipoVehiculo(String tipo) {
		// CAMIONES/AUTOCARAVANAS
		if (tipo == null) {
			return TipoVehiculoIvtm.Otro;
		}
		if ((tipo.startsWith("0") || tipo.startsWith("1")) || tipo.equals("20") || tipo.equals("21")
				|| tipo.equals("24") || tipo.startsWith("7")) {
			return TipoVehiculoIvtm.Camiones;
		}
		// TURISMOS/AUTOMOVILES 3 RUEDAS
		if (tipo.equals("22") || tipo.equals("23") || tipo.equals("25") || tipo.equals("40")) {
			return TipoVehiculoIvtm.Turismo;
		}
		// AUTOBUSES/AUTOCARES
		if (tipo.startsWith("30")) {
			return TipoVehiculoIvtm.Autobuses;
		}
		// MOTOCICLETAS
		if (tipo.startsWith("5")) {
			return TipoVehiculoIvtm.Motocicletas;
		}
		// TRACTOR Y ESPECIALES
		if (tipo.startsWith("8")) {
			return TipoVehiculoIvtm.Tractores;
		}
		// CICLOMOTORES/MOTOCICLETAS/MOTOCARRO/QUADS
		if (tipo.startsWith("9")) {
			return TipoVehiculoIvtm.Ciclomotores;
		}
		// REMOLQUES Y SEMIRREMOLQUES
		if (tipo.startsWith("R") || tipo.startsWith("S")) {
			return TipoVehiculoIvtm.Remolques;
		}
		return TipoVehiculoIvtm.Otro;
	}

	@Override
	public List<String> validarAlta(TramiteTraficoDto tramiteDTO, IvtmMatriculacionDto ivtmMatriculacionDto) {
		return ValidacionIvtm.validarAlta(tramiteDTO, recuperarPorNumExp(new BigDecimal(tramiteDTO.getNumExpediente())));
	}

	@Override
	public List<String> validarModificacion(TramiteTraficoDto tramiteDTO, IvtmMatriculacionDto ivtmMatriculacionDto) {
		return ValidacionIvtm.validarModificacion(tramiteDTO, ivtmMatriculacionDto);
	}

	@Override
	public List<String> validarPago(TramiteTraficoDto tramiteDTO, IvtmMatriculacionDto ivtmMatriculacionDto) {
		return ValidacionIvtm.validarPago(tramiteDTO, recuperarPorNumExp(new BigDecimal(tramiteDTO.getNumExpediente())));
	}

	@Override
	public es.map.scsp.esquemas.v2.peticion.altaivtm.Peticion damePeticionAlta(TramiteTraficoDto tramiteTrafico) {
		return new GenerarPeticionIVTM().damePeticionAlta(tramiteTrafico);
	}

	@Override
	public es.map.scsp.esquemas.v2.peticion.modificacionivtm.Peticion damePeticionModificacion(TramiteTraficoDto tramiteTrafico) {
		return new GenerarPeticionIVTM().damePeticionModificacion(tramiteTrafico);
	}

	@Override
	public List<String> validarMatriculacion(BigDecimal numExpediente) {
		List<String> errores = new ArrayList<>();
		TramiteTraficoDto tramiteTrafico = new ModeloTramiteTrafImpl().recuperarDtoPorNumExp(numExpediente);
		IvtmMatriculacionDto ivtm = buscarIvtmNumExp(numExpediente);
		if (tramiteTrafico==null || ivtm == null){
			errores.add(ConstantesIVTM.TEXTO_IVTM_ERROR_RECUPERANDO);
			return errores;
		}
		PersonaDireccion personaDireccion = ValidacionIvtm.obtenerPersonaDireccion(tramiteTrafico,
				org.gestoresmadrid.core.model.enumerados.TipoInterviniente.Titular.getValorEnum());
		if (personaDireccion == null || personaDireccion.getDireccion() == null
				|| personaDireccion.getDireccion().getMunicipio() == null
				|| personaDireccion.getDireccion().getMunicipio().getProvincia() == null
				|| personaDireccion.getDireccion().getMunicipio().getProvincia().getIdProvincia() == null
				|| personaDireccion.getDireccion().getMunicipio().getId().getIdMunicipio() == null
				|| personaDireccion.getDireccion().getMunicipio().getId() == null) {
			errores.add(ConstantesIVTM.TEXTO_FALTA_TITULAR);
			return errores;
		}
		boolean esMadrid = false;
		if ( personaDireccion.getDireccion().getMunicipio().getProvincia().getIdProvincia().equals(ConstantesIVTM.PROVINCIA_MADRID) && personaDireccion.getDireccion().getMunicipio().getId().getIdMunicipio().equals(ConstantesIVTM.MUNICIPIO_MADRID)){
			esMadrid = true;
		}
		if (ivtm.getFechaPago()==null){
			errores.add(ConstantesIVTM.TEXTO_FECHA_OBLIGATORIA+ ConstantesIVTM.FECHA_IVTM);
		}
		if (!esMadrid && ivtm.getImporte()==null){
			errores.add(ConstantesIVTM.TEXTO_IMPORTE_OBLIGATORIO);
		}
		return errores;
	}

	public IvtmDatosSalidaMatriculasWS recuperarMatriculasWS (IvtmDatosEntradaMatriculasWS datosEntrada){
		IvtmDatosSalidaMatriculasWS datosSalida = new IvtmDatosSalidaMatriculasWS();
		log.info("Llamado a consulta de Busqueda.");
		// Realiza la consulta
		IvtmResultadoWSMatriculasWS[] listaResultados = null;

		listaResultados = ivtmDao.recuperarMatriculas(datosEntrada);
		// Procesamos la respuesta
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
	public void duplicar(BigDecimal numExpedientePrevio, BigDecimal numExpedienteNuevo) {
		if (numExpedientePrevio== null || numExpedienteNuevo == null) {
			return;
		}
		IvtmMatriculacionDto ivtm = this.buscarIvtmNumExp(numExpedientePrevio);
		ivtm.setNumExpediente(numExpedienteNuevo);
		try {
			this.guardar(ivtm);
		} catch (OegamExcepcion e) {
			log.error("No se ha podido duplicar los datos del IVTM del expediente "+numExpedientePrevio+" al expediente "+numExpedienteNuevo);
			log.error(e.getMessage());
		}
	}

	public boolean estaEnEstadoValidoParaAutoliquidar(EstadoIVTM estado) {
		if (estado == null) {
			return true; // Si no tiene estado, es porque nunca se ha ejecutado antes, y por tanto si se puede
		}
		if (estado == EstadoIVTM.Pendiente_Respuesta_Ayto || estado == EstadoIVTM.Pendiente_Respuesta_Modificacion_Ayto) {
			return false;
		}
		if (estado == EstadoIVTM.Ivtm_Error_Modificacion || estado ==EstadoIVTM.Ivtm_Ok_Modificacion) { // Si está en modificación es porque ya se ha realizado la autoliquidación, y por tanto, no se puede volver a lanzar
			return false;
		}
		if (estado == EstadoIVTM.Ivtm_Error) { // Si hay error, es porque no se ha autoliquidado, y se puede lanzar de nuevo
			return true;
		}
		if (estado == EstadoIVTM.Iniciado) {
			return true;
		}
		if (estado == EstadoIVTM.Ivtm_Importado) { // Un importado, aunque tenga datos del IVTM, se puede volver a solicitar.
			return true;
		}
		if (utilesColegiado.tienePermisoAdmin() && estado == EstadoIVTM.Ivtm_Ok) { // Si está en estado OK, es porque ya se ha ejecutado. Solo el administrador podrá volver a solicitar la autoliquidación
			return true;
		}
		return false;
	}

	@Override
	public String getIbanTitular(BigDecimal numExpediente) {
		GenericDao<Persona> dao = new GenericDaoImplHibernate<Persona>(new Persona());
		List<Criterion> criterion = new ArrayList<>();
		criterion.add(Restrictions.eq("interviniente.id.numExpediente", numExpediente.longValue()));
		criterion.add(Restrictions.eq("interviniente.tipoIntervinienteBean.tipoInterviniente", TipoInterviniente.Titular.getValorEnum()));
		ProjectionList listaProyecciones = Projections.projectionList();
		listaProyecciones.add(Projections.property("iban"));
		List<AliasQueryBean> entitiesJoin = new ArrayList<>();
		entitiesJoin.add(new AliasQueryBean(PersonaDireccion.class, "personaDireccions", "personaDireccion"));
		entitiesJoin.add(new AliasQueryBean(IntervinienteTrafico.class, "personaDireccion.intervinienteTraficos", "interviniente"));
		List<?> lista = dao.buscarPorCriteria(criterion, entitiesJoin, listaProyecciones);
		if (lista!= null && !lista.isEmpty()) {
			return (String)lista.get(0);
		}
		return null;
	}

	@Override
	public String getIbanTitular(String nifTitular, String numColegiado) {
		GenericDao<Persona> dao = new GenericDaoImplHibernate<Persona>(new Persona());
		List<Criterion> criterion = new ArrayList<>();
		criterion.add(Restrictions.eq("id.nif", nifTitular));
		criterion.add(Restrictions.eq("id.numColegiado", numColegiado));
		ProjectionList listaProyecciones = Projections.projectionList();
		listaProyecciones.add(Projections.property("iban"));
		List<?> lista = dao.buscarPorCriteria(criterion, null, listaProyecciones);
		if (lista!= null && !lista.isEmpty()) {
			return (String)lista.get(0);
		}
		return null;
	}

}