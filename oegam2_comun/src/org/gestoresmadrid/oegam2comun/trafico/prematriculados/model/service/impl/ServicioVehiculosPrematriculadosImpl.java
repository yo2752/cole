package org.gestoresmadrid.oegam2comun.trafico.prematriculados.model.service.impl;

import java.io.File;
import java.io.FileNotFoundException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.gestoresmadrid.core.trafico.prematriculados.model.dao.VehiculoPrematriculadoDaoInt;
import org.gestoresmadrid.core.trafico.prematriculados.model.enumerados.EstadoConsultaVehiculoPrematiculado;
import org.gestoresmadrid.core.trafico.prematriculados.model.vo.VehiculoPrematriculadoVO;
import org.gestoresmadrid.oegam2comun.consultaEitv.model.service.ServicioConsultaEitv;
import org.gestoresmadrid.oegam2comun.peticionesurl.BeanVehiculoPreMatriculado;
import org.gestoresmadrid.oegam2comun.peticionesurl.EjecutadorPeticionesAUrl;
import org.gestoresmadrid.oegam2comun.trafico.prematriculados.model.service.ServicioVehiculosPrematriculados;
import org.gestoresmadrid.oegam2comun.trafico.prematriculados.view.dto.ResumenImportacion;
import org.gestoresmadrid.oegam2comun.trafico.prematriculados.view.dto.VehiculoPrematriculadoDto;
import org.gestoresmadrid.oegamComun.acceso.service.impl.UtilesColegiado;
import org.gestoresmadrid.oegamComun.conversor.Conversor;
import org.gestoresmadrid.utilidades.components.GestorPropiedades;
import org.gestoresmadrid.utilidades.components.Utiles;
import org.gestoresmadrid.utilidades.components.UtilesFecha;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import colas.constantes.ConstantesProcesos;
import colas.modelo.ModeloSolicitud;
import es.globaltms.gestorDocumentos.bean.FicheroBean;
import es.globaltms.gestorDocumentos.constantes.ConstantesGestorFicheros;
import es.globaltms.gestorDocumentos.interfaz.GestorDocumentos;
import escrituras.beans.ResultBean;
import trafico.beans.schemas.generated.eitv.generated.ConsultaTarjeta;
import trafico.utiles.XMLConsultaTarjetaEITVFactory;
import trafico.utiles.enumerados.TipoTramiteTrafico;
import utilidades.estructuras.Fecha;
import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;
import utilidades.propiedades.PropertiesConstantes;
import utilidades.web.CrearSolicitudExcepcion;
import utilidades.web.OegamExcepcion;

@Service
public class ServicioVehiculosPrematriculadosImpl implements ServicioVehiculosPrematriculados {

	private static final long serialVersionUID = -3671322591331353258L;

	private static ILoggerOegam log = LoggerOegam.getLogger(ServicioVehiculosPrematriculadosImpl.class);

	private static final String UTF_8 = "UTF-8";
	private static final String RUTA_CONSULTA_FICHA_TECNICA_XML = "consultaPrematriculados.xml.ruta";
	private static final String RUTA_PETICIONESURL_XSD = "peticionesAUrl.xsd.ruta";

	@Autowired
	private VehiculoPrematriculadoDaoInt vehiculoPrematriculadoDao;

	@Autowired
	private GestorDocumentos gestorDocumentos;

	@Autowired
	private Conversor conversor;

	@Autowired
	EjecutadorPeticionesAUrl ejecutadorPeticionesAUrl;

	@Autowired
	private ServicioConsultaEitv servicioConsultaEitv;

	@Autowired
	private GestorPropiedades gestorPropiedades;

	@Autowired
	Utiles utiles;

	@Autowired
	UtilesFecha utilesFecha;

	@Autowired
	private UtilesColegiado utilesColegiado;

	private ModeloSolicitud modeloSolicitud;
	private ComunesServicioVehiculoPrematriculados serviciosComunes;

	/************************/
	/** Getters and Setters */
	/************************/

	public VehiculoPrematriculadoDaoInt getVehiculoPrematriculadoDao() {
		return vehiculoPrematriculadoDao;
	}

	public void setVehiculoPrematriculadoDao(VehiculoPrematriculadoDaoInt vehiculoPrematriculadoDao) {
		this.vehiculoPrematriculadoDao = vehiculoPrematriculadoDao;
	}

	public ModeloSolicitud getModeloSolicitud() {
		if (modeloSolicitud==null){
			modeloSolicitud = new ModeloSolicitud();
		}
		return modeloSolicitud;
	}

	public void setModeloSolicitud(ModeloSolicitud modeloSolicitud) {
		this.modeloSolicitud = modeloSolicitud;
	}

	public ComunesServicioVehiculoPrematriculados getServiciosComunes() {
		if (serviciosComunes==null){
			serviciosComunes = new ComunesServicioVehiculoPrematriculados();
		}
		return serviciosComunes;
	}

	public void setServiciosComunes(ComunesServicioVehiculoPrematriculados serviciosComunes) {
		this.serviciosComunes = serviciosComunes;
	}

	/*************************/
	/** Servicios			 */
	/*************************/

	@Override
	public ResultBean validarConsulta(VehiculoPrematriculadoDto vehiculo) {
		ResultBean rs = new ResultBean();
		if (vehiculo==null) {
			rs.setError(true);
			rs.addMensajeALista("No hay vehículo para validar");
		} else {
			if(vehiculo.isFichaTecnica()){
				if(vehiculo.getNive() == null || vehiculo.getNive().length() != 32){
					rs.setError(true);
					rs.addMensajeALista("El código NIVE debe tener una longitud de 32 caracteres");
				}
			}
			if(vehiculo.getBastidor() == null || vehiculo.getBastidor().length() != 17){
				rs.setError(true);
				rs.addMensajeALista("El número de bastidor debe tener una longitud de 17 caracteres");
			}
			if (!vehiculo.isDatosTecnicos() && ! vehiculo.isFichaTecnica()){
				rs.setError(true);
				rs.addMensajeALista("Debe seleccionar si quiere los datos técnicos, la ficha técnica, o ambos");
			}
		}
		return rs;
	}

	@Override
	@Transactional(propagation=Propagation.REQUIRED)
	public ResultBean consultarEitv(VehiculoPrematriculadoDto vehiculo) {
		ResultBean rs = new ResultBean();
		vehiculo.setFechaAlta(new Date());
		if (vehiculo.isDatosTecnicos()){
			vehiculo.setEstadoCaracteristicas(EstadoConsultaVehiculoPrematiculado.INICIADO.getValorEnum());
		} else {
			vehiculo.setEstadoCaracteristicas(EstadoConsultaVehiculoPrematiculado.NO_SOLICITADO.getValorEnum());
		}
		if (vehiculo.isFichaTecnica()){
			vehiculo.setEstadoFichaTecnica(EstadoConsultaVehiculoPrematiculado.INICIADO.getValorEnum());
		} else {
			vehiculo.setEstadoFichaTecnica(EstadoConsultaVehiculoPrematiculado.NO_SOLICITADO.getValorEnum());
		}
		if (!this.guardarConsultaEitv(vehiculo)){
			rs.setError(true);
			rs.addMensajeALista("No se ha podido guardar");
		} else {
			if (vehiculo.isDatosTecnicos()){
				rs = servicioConsultaEitv.consultaEitvPreMatriculados(vehiculo,
						utilesColegiado.getIdUsuarioSessionBigDecimal(), utilesColegiado.getIdContratoSessionBigDecimal());
				if(rs != null && rs.getError()){
					TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
				}
			}else if (vehiculo.isFichaTecnica()){
				try {
					getModeloSolicitud().crearSolicitud(new BigDecimal(vehiculo.getId()),  utilesColegiado.getIdUsuarioSessionBigDecimal(), TipoTramiteTrafico.ConsultaEITV, ConstantesProcesos.PROCESO_VEHICULO_PREMATRICULADO_FICHA_TECNICA_EITV, null);
				} catch (CrearSolicitudExcepcion e) {
					log.error("No se puede crear la solictud para la consulta de la ficha técnica");
					rs.setError(true);
					rs.addMensajeALista("No se ha podido crear la solicitud para consultar la ficha técnica");
				}
			}
		}
		return rs;
	}
	@Override
	@Transactional
	public ResultBean guardarSolicitudes(List<VehiculoPrematriculadoDto> listaVehiculos) {
		ResumenImportacion resumenImportacionTotal = new ResumenImportacion("TOTAL");
		List<String> listaErroneos = new ArrayList<String>();
		List<String> listaOK = new ArrayList<String>();
		ResultBean result = null;
		for (int i=0; i<listaVehiculos.size();i++){
			VehiculoPrematriculadoDto vehiculo = listaVehiculos.get(i);
			ResultBean rs = validarConsulta(vehiculo);
			if (rs.getError()){
				for (String error : rs.getListaMensajes()){
					listaErroneos.add("Línea "+(i+1)+": "+ error);
				}
				resumenImportacionTotal.addIncorrecto();
			} else {
				rs = consultarEitv(vehiculo);
				if (rs.getError()){
					for (String error: rs.getListaMensajes()){
						listaErroneos.add("Línea "+(i+1)+": "+ error);
					}
					resumenImportacionTotal.addIncorrecto();
				} else {
					String mensaje = "Línea " + (i + 1) + " correcta. Generada petición: " + vehiculo.getId() + 
							" Bastidor: " + vehiculo.getBastidor();
					if(vehiculo.getNive() != null && !vehiculo.getNive().isEmpty()){
						mensaje += " Nive: " + vehiculo.getNive();
					}
					listaOK.add(mensaje);
					resumenImportacionTotal.addCorrecto();
				}
			}

		}
		result = new ResultBean(false);
		result.addAttachment("listaOk", listaOK);
		result.addAttachment("listaErroneos", listaErroneos);
		result.addAttachment("resumenImportacionTotal", resumenImportacionTotal);
		return result;
	}
	
	private boolean guardarConsultaEitv(VehiculoPrematriculadoDto vehiculo) {
		Long id = (Long) getVehiculoPrematriculadoDao().guardar(conversor.transform(vehiculo, VehiculoPrematriculadoVO.class));
		if (id!=null){
			vehiculo.setId(id);
			return true;
		}
		return false;
	}

	public File buscarCaracteristicasDocumento(Long id, Date fecha) {
		try {
			return gestorDocumentos.buscarFicheroPorNombreTipo(ConstantesGestorFicheros.MATE, ConstantesGestorFicheros.EITVCARACTERISTICAS, 
					new Fecha(fecha), ConstantesGestorFicheros.CONSULTA_CARACTERISTICASEITV+id, ConstantesGestorFicheros.EXTENSION_XML).getFile();
		} catch (OegamExcepcion e) {
			log.error("No se ha encontrado el documento");
			return null;
		}
	}

	@Override
	@Transactional
	public ResultBean guardarXmlRespuestaDatosTecnicos(String data, Long id, Date fecha) {
		ResultBean result = null;
		FicheroBean fichero = new FicheroBean();
		fichero.setTipoDocumento(ConstantesGestorFicheros.MATE);
		fichero.setSubTipo(ConstantesGestorFicheros.EITVCARACTERISTICAS);
		fichero.setNombreDocumento(ConstantesGestorFicheros.CONSULTA_CARACTERISTICASEITV+id);
		fichero.setExtension(ConstantesGestorFicheros.EXTENSION_XML);
		fichero.setFecha(new Fecha(fecha));
		try {
			byte[] xmlData = utiles.doBase64Decode(data, UTF_8);
			fichero.setFicheroByte(xmlData);
			File file = gestorDocumentos.guardarFichero(fichero);
			if(file == null){
				result = new ResultBean(true, "Ha surgido un error a la hora de guardar el xml con los datos técnicos del vehículo.");
			}
		} catch (OegamExcepcion e) {
			log.error("Error al guardar el documento",e);
			result = new ResultBean(true, "Ha surgido un error a la hora de guardar el xml con los datos tecnicos del vehiculo.");
		} catch (Exception e) {
			log.error("Error al guardar el documento,error al decodificar",e);
			result = new ResultBean(true, "Ha surgido un error a la hora de guardar el xml con los datos técnicos del vehículo.");
		}
		return result;
	}

	@Override
	@Transactional
	public ResultBean guardarPdfRespuestaDatosTecnicos(String data, VehiculoPrematriculadoVO vehiculoPreVO) {
		ResultBean result = null;
		FicheroBean fichero = new FicheroBean();
		fichero.setTipoDocumento(ConstantesGestorFicheros.MATE);
		fichero.setSubTipo(ConstantesGestorFicheros.EITVFICHATECNICA_NUEVO);
		fichero.setNombreDocumento(ConstantesGestorFicheros.CONSULTA_FICHATECNICAEITV+vehiculoPreVO.getId());
		fichero.setExtension(ConstantesGestorFicheros.EXTENSION_PDF);
		fichero.setFecha(new Fecha(vehiculoPreVO.getFechaAlta()));
		try {
			byte[] pdfData = utiles.doBase64Decode(data, UTF_8);
			fichero.setFicheroByte(pdfData);
			File file = gestorDocumentos.guardarFichero(fichero);
			if(file == null){
				result = new ResultBean(true, "Ha surgido un error a la hora de guardar el xml con los datos técnicos del vehículo.");
			}
		} catch (OegamExcepcion e) {
			log.error("Error al guardar el documento",e);
			result = new ResultBean(true, "Ha surgido un error a la hora de guardar el xml con los datos técnicos del vehículo.");
		} catch (Exception e) {
			log.error("Error al guardar el documento,error al decodificar",e);
			result = new ResultBean(true, "Ha surgido un error a la hora de guardar el xml con los datos técnicos del vehículo.");
		}	
		return result;
	}

	@Override
	public File buscarFichaTecnicaDocumento(Long id, Date fecha) {
		try {
			return gestorDocumentos.buscarFicheroPorNombreTipo(ConstantesGestorFicheros.MATE, ConstantesGestorFicheros.EITVFICHATECNICA,
					utilesFecha.getFechaFracionada(fecha), ConstantesGestorFicheros.CONSULTA_FICHATECNICAEITV+id, ConstantesGestorFicheros.EXTENSION_PDF).getFile();
		} catch (OegamExcepcion e) {
			log.error("No se ha encontrado el documento");
			return null;
		}
	}

	@Override
	@Transactional
	public List<VehiculoPrematriculadoDto> buscar(Long[] ids, String numColegiado) {
		return conversor.transform(vehiculoPrematriculadoDao.buscar(ids, numColegiado),VehiculoPrematriculadoDto.class);
	}

	private VehiculoPrematriculadoVO buscarVO(Long id, String numColegiado) {
		return vehiculoPrematriculadoDao.buscar(id, numColegiado);
	}

	@Override
	@Transactional
	public String recogerXmlEitv(Long id) {
		List<VehiculoPrematriculadoDto> vehiculosPrematriculados = buscar(new Long[]{id}, null);
		if (vehiculosPrematriculados==null || vehiculosPrematriculados.size()!=1 || vehiculosPrematriculados.get(0)==null){
			return null;
		}
		File ficheroAenviar=null;
		try {
			ficheroAenviar = gestorDocumentos.buscarFicheroPorNombreTipo(ConstantesGestorFicheros.MATE, ConstantesGestorFicheros.EITV_PREMATRICULADO,
					new Fecha(vehiculosPrematriculados.get(0).getFechaAlta()),ConstantesProcesos.VEHICULO_PREMATICULADO_DATOS_EITV+id, ConstantesGestorFicheros.EXTENSION_XML).getFile();
		} catch (OegamExcepcion e) {
			log.error("Error al recuperar el documento.");
		}

		try {
			return new XMLConsultaTarjetaEITVFactory().xmlFileToString(ficheroAenviar);
		} catch (FileNotFoundException e) {
			log.error("No se ha encontrado el fichero");
			return null;
		}
	}

	@Override
	@Transactional
	public void generarSolicitudXml(Long id) {
		List<VehiculoPrematriculadoDto> vehiculos = buscar(new Long[]{id}, null);
		if (vehiculos==null || vehiculos.size()!=1){
			return;
		}
		VehiculoPrematriculadoDto vehiculo = vehiculos.get(0);
		if(vehiculo != null && vehiculo.getNumColegiado()!=null && vehiculo.getNive()!=null && vehiculo.getBastidor()!=null && vehiculo.getFechaAlta()!=null){
			try {
				ConsultaTarjeta consultaTarjetaEitv = getServiciosComunes().getObjetoRaizXml(vehiculo.getNumColegiado(), vehiculo.getNive(), vehiculo.getBastidor());
				consultaTarjetaEitv = getServiciosComunes().anhadirFirmasHsm(consultaTarjetaEitv, vehiculo.getNumColegiado());
				guardarFicheroEitvXML(consultaTarjetaEitv, vehiculo.getId(), vehiculo.getFechaAlta());
			} catch (Throwable e) {
				log.error("Error al guardar el XML");
			}
		}
	}

	private void guardarFicheroEitvXML(ConsultaTarjeta consultaTarjetaEitv, Long id, Date fecha) throws Throwable {
		FicheroBean ficheroBean = new FicheroBean();
		ficheroBean.setExtension(ConstantesGestorFicheros.EXTENSION_XML);
		ficheroBean.setTipoDocumento(ConstantesGestorFicheros.MATE);
		ficheroBean.setSubTipo(ConstantesGestorFicheros.EITV_PREMATRICULADO);
		ficheroBean.setNombreDocumento(ConstantesProcesos.VEHICULO_PREMATICULADO_DATOS_EITV+id);
		ficheroBean.setFecha(new Fecha(fecha));
		ficheroBean.setSobreescribir(true);
		try {
			gestorDocumentos.crearFicheroXml(ficheroBean, XMLConsultaTarjetaEITVFactory.NAME_CONTEXT, consultaTarjetaEitv, null, null);
		} catch (OegamExcepcion e) {
			log.error("Error al guardar el documento XML");
		}
	}

	@Override
	@Transactional
	public boolean guardarRespuestaDatosTecnicos(boolean correcto, String respuesta, Long id) {
		VehiculoPrematriculadoVO vehiculo = buscarVO(id, null);
		if (vehiculo==null){
			return false;
		}
		vehiculo.setRespuestaCaracteristicas(respuesta);
		if (correcto){
			vehiculo.setEstadoCaracteristicas(EstadoConsultaVehiculoPrematiculado.FINALIZADO.getValorEnum());
		} else {
			vehiculo.setEstadoCaracteristicas(EstadoConsultaVehiculoPrematiculado.ERROR.getValorEnum());
		}
		vehiculoPrematriculadoDao.actualizar(vehiculo);
		return true;
	}

	@Override
	@Transactional
	public boolean guardarRespuestaFichaTecnica(boolean correcto, String respuesta, Long id) {
		VehiculoPrematriculadoVO vehiculo = buscarVO(id, null);
		if (vehiculo==null){
			return false;
		}
		vehiculo.setRespuestaFichaTecnica(respuesta);
		if (correcto){
			vehiculo.setEstadoFichaTecnica(EstadoConsultaVehiculoPrematiculado.FINALIZADO.getValorEnum());
		} else {
			vehiculo.setEstadoFichaTecnica(EstadoConsultaVehiculoPrematiculado.ERROR.getValorEnum());
		}
		vehiculoPrematriculadoDao.actualizar(vehiculo);
		return true;
	}

	@Override
	@Transactional
	public ResultBean consultaFichaTecnica(Long id) {
		VehiculoPrematriculadoVO vehiculo = buscarVO(id, null);
		BeanVehiculoPreMatriculado bean = new BeanVehiculoPreMatriculado();
		bean.setBastidor(vehiculo.getBastidor());
		bean.setNive(vehiculo.getNive());
		bean.setId(vehiculo.getId());
		bean.setFecha(new Fecha(vehiculo.getFechaAlta()));
		String rutaXML = gestorPropiedades.valorPropertie(PropertiesConstantes.RUTA_DIRECTORIO_DATOS)+gestorPropiedades.valorPropertie(RUTA_CONSULTA_FICHA_TECNICA_XML);
		String rutaXSD = gestorPropiedades.valorPropertie(PropertiesConstantes.RUTA_DIRECTORIO_DATOS)+gestorPropiedades.valorPropertie(RUTA_PETICIONESURL_XSD);
		return ejecutadorPeticionesAUrl.ejecutarPeticionAUrlSegura(rutaXML, rutaXSD, bean);
	}

	@Override
	@Transactional
	public VehiculoPrematriculadoVO buscarVehiculoPrematriculado(Long idVehiculo) {
		VehiculoPrematriculadoVO vehiculoPrematriculadoVO = new VehiculoPrematriculadoVO();
		vehiculoPrematriculadoVO.setId(idVehiculo);

		List<VehiculoPrematriculadoVO> lista = vehiculoPrematriculadoDao.buscar(vehiculoPrematriculadoVO);
		if(lista != null && !lista.isEmpty()){
			return lista.get(0);
		}
		return null;
	}

	public EjecutadorPeticionesAUrl getEjecutadorPeticionesAUrl() {
		return ejecutadorPeticionesAUrl;
	}

	public void setEjecutadorPeticionesAUrl(
			EjecutadorPeticionesAUrl ejecutadorPeticionesAUrl) {
		this.ejecutadorPeticionesAUrl = ejecutadorPeticionesAUrl;
	}

	public Conversor getConversor() {
		return conversor;
	}

	public void setConversor(Conversor conversor) {
		this.conversor = conversor;
	}

	public ServicioConsultaEitv getServicioConsultaEitv() {
		return servicioConsultaEitv;
	}

	public void setServicioConsultaEitv(ServicioConsultaEitv servicioConsultaEitv) {
		this.servicioConsultaEitv = servicioConsultaEitv;
	}

}