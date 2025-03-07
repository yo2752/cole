package org.gestoresmadrid.oegam2comun.trafico.eeff.model.service.impl;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.net.MalformedURLException;
import java.net.URL;
import java.rmi.RemoteException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.JAXBException;
import javax.xml.rpc.ServiceException;

import org.gestoresmadrid.core.general.model.vo.ContratoUsuarioVO;
import org.gestoresmadrid.core.historicocreditos.model.dao.enumerados.ConceptoCreditoFacturado;
import org.gestoresmadrid.core.model.beans.ColaBean;
import org.gestoresmadrid.core.model.enumerados.EstadoTramiteTrafico;
import org.gestoresmadrid.core.model.enumerados.TipoInterviniente;
import org.gestoresmadrid.core.trafico.eeff.model.dao.EeffLiberacionDao;
import org.gestoresmadrid.core.trafico.eeff.model.enumerados.ConstantesEEFF;
import org.gestoresmadrid.core.trafico.eeff.model.vo.EeffLiberacionVO;
import org.gestoresmadrid.oegam2comun.cola.model.service.ServicioCola;
import org.gestoresmadrid.oegam2comun.creditos.model.service.ServicioCredito;
import org.gestoresmadrid.oegam2comun.proceso.enumerados.ProcesosEnum;
import org.gestoresmadrid.oegam2comun.trafico.eeff.model.service.ServicioEEFFLiberacion;
import org.gestoresmadrid.oegam2comun.trafico.eeff.utils.TransformToXML;
import org.gestoresmadrid.oegam2comun.trafico.eeff.view.dto.EeffLiberacionDTO;
import org.gestoresmadrid.oegam2comun.trafico.model.service.ServicioTramiteTrafico;
import org.gestoresmadrid.oegamComun.acceso.service.impl.UtilesColegiado;
import org.gestoresmadrid.oegamComun.colegiado.services.ServicioColegiado;
import org.gestoresmadrid.oegamComun.colegiado.view.dto.ColegiadoDto;
import org.gestoresmadrid.oegamComun.conversor.Conversor;
import org.gestoresmadrid.utilidades.components.GestorPropiedades;
import org.gestoresmadrid.utilidades.components.UtilesFecha;
import org.hibernate.HibernateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.viafirma.cliente.exception.InternalException;
import org.xml.sax.SAXException;

import com.thoughtworks.xstream.XStream;

import colas.modelo.ModeloSolicitud;
import colas.utiles.UtilesEEFF;
import es.globaltms.gestorDocumentos.bean.FicheroBean;
import es.globaltms.gestorDocumentos.constantes.ConstantesGestorFicheros;
import es.globaltms.gestorDocumentos.interfaz.GestorDocumentos;
import es.globaltms.gestorDocumentos.util.Utilidades;
import es.trafico.cliente.vehiculos.custodiaitv.webservice.SolicitudOperacionesITVWS;
import es.trafico.cliente.vehiculos.custodiaitv.webservice.SolicitudOperacionesITVWSService;
import es.trafico.cliente.vehiculos.custodiaitv.webservice.SolicitudOperacionesITVWSServiceLocator;
import es.trafico.servicios.vehiculos.custodiaitv.beans.InfoErrorDTO;
import es.trafico.servicios.vehiculos.custodiaitv.beans.RespuestaLiberarDTO;
import escrituras.beans.ResultBean;
import hibernate.entities.personas.Direccion;
import hibernate.entities.personas.Persona;
import hibernate.entities.trafico.IntervinienteTrafico;
import hibernate.entities.trafico.Vehiculo;
import trafico.beans.jaxb.matw.FORMATOGA.MATRICULACION.DATOSLIBERACION;
import trafico.dto.TramiteTraficoDto;
import trafico.modelo.ModeloCreditosTrafico;
import trafico.modelo.ModeloTrafico;
import trafico.modelo.impl.ModeloTramiteTrafImpl;
import trafico.modelo.interfaz.ModeloTramiteTrafInterface;
import trafico.utiles.enumerados.TipoTramiteTrafico;
import utilidades.estructuras.Fecha;
import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;
import utilidades.web.OegamExcepcion;

@Service
public class ServicioEEFFLiberacionImpl implements ServicioEEFFLiberacion {

	public static final ILoggerOegam log = LoggerOegam.getLogger(ServicioEEFFLiberacionImpl.class);

	@Autowired
	private EeffLiberacionDao liberacionDao;

	@Autowired
	private GestorDocumentos gestorDocumentos;

	@Autowired
	private ServicioCredito servicioCredito;

	@Autowired
	private Conversor conversor;
	
	@Autowired
	ServicioCola servicioCola;
	
	@Autowired
	private ServicioColegiado servicioColegiado;

	@Autowired
	private GestorPropiedades gestorPropiedades;
	
	@Autowired
	UtilesFecha utilesFecha;

	@Autowired
	private UtilesColegiado utilesColegiado;
	
	ModeloTramiteTrafInterface modeloTrafInte = null;

	ModeloCreditosTrafico modeloCreditosTrafico = null;
	
	ModeloTrafico modeloTrafico = null;
	
	ModeloSolicitud modeloSolicitud = null;

	SolicitudOperacionesITVWSService soOperacionesITVWSService = null;
	
	@Autowired
	ServicioTramiteTrafico servicioTramiteTrafico;

	@Transactional
	@Override
	public EeffLiberacionDTO recuperarDatos(BigDecimal numExpediente) {
		return convertirEEFFLiberacionToEEFFLiberacionDTO(recuperarDatosVO(numExpediente));
	}

	@Override
	@Transactional
	public EeffLiberacionVO recuperarDatosVO(BigDecimal numExpediente) {
		return liberacionDao.buscarPorExpediente(numExpediente);
	}

	private BigDecimal guardarDatos(EeffLiberacionDTO eeffDTO) {
		BigDecimal numExpediente = null;
		if (eeffDTO == null) {
			return null;
		}
		if (existeLiberacionEEFF(eeffDTO)) {
			EeffLiberacionVO eeffGuardado = recuperarDatosVO(eeffDTO.getNumExpediente());
			if (!eeffGuardado.isRealizado()) {
				liberacionDao.evict(eeffGuardado);
				try {
					if (!actualizarDatos(convertirEEFFLiberacionDTOToEEFFLiberacion(eeffDTO))) {
						return null;
					}
				} catch (ParseException e) {
					log.error("Error al convertir el LiberacionDTO a LiberacionVO para el expediente: " + eeffDTO.getNumExpediente(), e);
				}
			}
			numExpediente = eeffGuardado.getNumExpediente();
		} else {
			try {
				EeffLiberacionVO eeffLiberacion = convertirEEFFLiberacionDTOToEEFFLiberacion(eeffDTO);
				numExpediente = (BigDecimal)liberacionDao.guardar(eeffLiberacion);
			} catch (ParseException e1) {
				log.error("Error guardando los datos");
				return null;
			} catch (ClassCastException e) {
				log.error("Error convirtiendo el número de expediente al guardar una liberación");
				return null;
			}
		}
		return numExpediente;
	}
	
	@Override
	@Transactional
	public boolean guardarDatos(EeffLiberacionDTO eeffLiberacionDTO, BigDecimal numExpediente, String numColegiado) {
		Integer numColegiadoInteger;
		eeffLiberacionDTO.setNumExpediente(numExpediente);
		if (numColegiado == null) {
			numColegiadoInteger = new Integer("0");
		} else {
			try {
				numColegiadoInteger = new Integer(numColegiado);
			} catch (NumberFormatException e) {
				numColegiadoInteger = new Integer("0");
			}
		}
		eeffLiberacionDTO.setNumColegiado(numColegiadoInteger);
		return guardarDatos(eeffLiberacionDTO) != null;
	}

	private boolean existeLiberacionEEFF(EeffLiberacionDTO eeffLiberacionDTO) {
		if (eeffLiberacionDTO == null || eeffLiberacionDTO.getNumExpediente() == null) {
			return false;
		}
		EeffLiberacionDTO recuperado = (EeffLiberacionDTO) recuperarDatos(eeffLiberacionDTO.getNumExpediente());
		return recuperado != null && recuperado.getNumExpediente() != null;
	}

	@Override
	@Transactional
	public boolean actualizarDatos(EeffLiberacionVO eeffVO) {
		if (eeffVO == null) {
			return false;
		}
		return liberacionDao.actualizar(eeffVO) != null;
	}

	@Override
	@Transactional
	public ResultBean guardarDatosImportados(trafico.beans.jaxb.matw.FORMATOGA.MATRICULACION faMa, BigDecimal numExp) {
		ResultBean resultado = new ResultBean();
		try {
			if (!utilesColegiado.tienePermisoLiberarEEFF()) {
				return resultado;
			}
			ResultBean rs = guardarDatosImportados(numExp,faMa.getNUMEROPROFESIONAL(), faMa.getEXENTOLIBERAR(),
					faMa.getDATOSLIBERACION(), faMa.getDATOSVEHICULO().getNIVE(), faMa.getDATOSVEHICULO().getNUMEROBASTIDOR());
			if (rs.getError()) {
				resultado.setError(true);
			}
			for (String mensaje : rs.getListaMensajes()) {
				resultado.addMensajeALista(mensaje);
			}
		} catch (Exception e){
			//TODO Este try-catch es para controlar que hay sesión. Si 
		}
		return resultado;
	}

	private ResultBean guardarDatosImportados(BigDecimal numExp,BigInteger numeroProfesional, String exentoLiberar,
			DATOSLIBERACION datosLiberacion, String nive, String bastidor) {
		EeffLiberacionVO eeffliberacion;
		ResultBean resultado = new ResultBean();
		if (exentoLiberar != null && exentoLiberar.equals("NO")) {
			if (datosLiberacion != null) {
				ResultBean rs = validarEeffLibFORMATOGA(datosLiberacion, nive, bastidor);
				if (!rs.getError()) {
					eeffliberacion = new EeffLiberacionVO();
					eeffliberacion.setNumExpediente(numExp);
					eeffliberacion.setNumColegiado(numeroProfesional.intValue());
					eeffliberacion.setTarjetaNive(nive);
					eeffliberacion.setTarjetaBastidor(bastidor);
					eeffliberacion.setFirCif(datosLiberacion.getCIFFIR());
					eeffliberacion.setFirMarca(datosLiberacion.getMARCAFIR());
					eeffliberacion.setNumFactura(datosLiberacion.getNUMFACTURA());
					eeffliberacion.setNifRepresentado(datosLiberacion.getNIFREPRESENTADO());
					Fecha fechaFactura = new Fecha(datosLiberacion.getFECHAFACTURA());
					try {
						eeffliberacion.setFechaFactura(fechaFactura.getFecha());
					} catch (ParseException e) {
						log.error("Error al introducir la fecha de la factura " + e);
						resultado.addMensajeALista("Problemas con la fecha de la factura. No se ha guardado.");
					}
					if (datosLiberacion.getIMPORTE() != null) {
						eeffliberacion.setImporte(Double.parseDouble(datosLiberacion.getIMPORTE()));
					}
					eeffliberacion.setExento(false);
					liberacionDao.guardar(eeffliberacion);
				} else {
					resultado.setError(true);
					log.debug("Vehículo no exento de liberar pero no estan rellenos los datos");
					resultado.addMensajeALista("Vehículo no exento de liberar, falta datos de liberar");
				}
			}
		} else {
			eeffliberacion = new EeffLiberacionVO();
			eeffliberacion.setNumExpediente(numExp);
			if (numeroProfesional != null && numeroProfesional.intValue() != 0) {
				eeffliberacion.setNumColegiado(numeroProfesional.intValue());
			}
			eeffliberacion.setExento(true);
			liberacionDao.guardar(eeffliberacion);
			resultado.addMensajeALista("Vehículo exento de liberar");
		}
		return resultado;
	}

	@Override
	public ResultBean validarEeffLibMatwFORMATOGA(trafico.beans.jaxb.matw.FORMATOGA.MATRICULACION faMa) {
		ResultBean resultado = new ResultBean();
		if (faMa.getEXENTOLIBERAR() != null && faMa.getEXENTOLIBERAR().equals("NO")) {
			ResultBean rs = validarEeffLibFORMATOGA(faMa.getDATOSLIBERACION(),faMa.getDATOSVEHICULO().getNIVE(), faMa.getDATOSVEHICULO()
							.getNUMEROBASTIDOR());
			resultado = rs;
		} else if (faMa.getEXENTOLIBERAR() != null && faMa.getEXENTOLIBERAR().equals("SI")) {
			ResultBean rs = validarEeffLibFORMATOGAExento(faMa.getDATOSLIBERACION(), faMa.getDATOSVEHICULO().getNIVE(), faMa.getDATOSVEHICULO().getNUMEROBASTIDOR());
			for (String mensaje : rs.getListaMensajes()) {
				resultado.addMensajeALista(mensaje);
			}
		}
		return resultado;
	}

	private ResultBean validarEeffLibFORMATOGAExento(DATOSLIBERACION datosLiberacion, String nive, String numerobastidor) {
		ResultBean resultado = new ResultBean();
		if (datosLiberacion != null && datosLiberacion.getCIFFIR() != null && !datosLiberacion.getCIFFIR().isEmpty()) {
			resultado.addMensajeALista("El trámite está exento de liberar, pero los datos de liberación están rellenos. No se guardarán estos datos.");
		}
		return resultado;
	}

	private ResultBean validarEeffLibFORMATOGA(DATOSLIBERACION datosLiberacion,String nive, String bastidor) {
		ResultBean resultado = new ResultBean();
		if (nive == null || nive.isEmpty()) {
			log.debug("Vehículo no exento de liberar pero no estan rellenos los datos");
			resultado.setError(true);
			resultado.addMensajeALista(ConstantesEEFF.EEFF_TEXTO_ERROR_IMPO_VALI_NIVE);
		}
		if (bastidor == null || bastidor.isEmpty()) {
			log.debug("Vehículo no exento de liberar pero no estan rellenos los datos");
			resultado.setError(true);
			resultado.addMensajeALista(ConstantesEEFF.EEFF_TEXTO_ERROR_IMPO_VALI_BASTIDOR);
		}
		
		if(datosLiberacion==null || datosLiberacion.getNIFREPRESENTADO() == null || datosLiberacion.getNIFREPRESENTADO().isEmpty()){
			log.debug("Vehículo no exento de liberar pero no estan rellenos los datos");
			resultado.setError(true);
			resultado.addMensajeALista(ConstantesEEFF.EEFF_TEXTO_ERROR_CIF_CONCESIONARIO);
		}
		
		return resultado;
	}

	private void cargarDatosParaLiberar(EeffLiberacionDTO eeffLiberacionDTO,Vehiculo vehiculo, IntervinienteTrafico titular) {
		eeffLiberacionDTO.setTarjetaBastidor(vehiculo.getBastidor());
		eeffLiberacionDTO.setTarjetaNive(vehiculo.getNive());

		if (titular.getPersonaDireccion() != null && titular.getPersonaDireccion().getPersona() != null) {
			Persona persona = titular.getPersonaDireccion().getPersona();
			eeffLiberacionDTO.setNif(persona.getId().getNif());
			if (persona.getNombre() != null && persona.getNombre().length() >= ConstantesEEFF.TAMANIO_NOMBRE) {
				eeffLiberacionDTO.setNombre(persona.getNombre().substring(0,ConstantesEEFF.TAMANIO_NOMBRE - 1));
			} else {
				eeffLiberacionDTO.setNombre(persona.getNombre());
			}
			if (persona.getApellido1RazonSocial() != null && persona.getApellido1RazonSocial().length() >= ConstantesEEFF.TAMANIO_APELLIDO1) {
				eeffLiberacionDTO.setPrimerApellido(persona.getApellido1RazonSocial().substring(0,ConstantesEEFF.TAMANIO_APELLIDO1 - 1));
			} else {
				eeffLiberacionDTO.setPrimerApellido(persona.getApellido1RazonSocial());
			}
			if (persona.getApellido2() != null && persona.getApellido2().length() >= ConstantesEEFF.TAMANIO_APELLIDO2) {
				eeffLiberacionDTO.setSegundoApellido(persona.getApellido2().substring(0, ConstantesEEFF.TAMANIO_APELLIDO2 - 1));
			} else {
				eeffLiberacionDTO.setSegundoApellido(persona.getApellido2());
			}

			if (titular.getPersonaDireccion() != null) {
				Direccion direccion = titular.getPersonaDireccion().getDireccion();
				if (direccion != null) {
					eeffLiberacionDTO.setTipoVia(direccion.getIdTipoVia());
					eeffLiberacionDTO.setNombreVia(direccion.getNombreVia());
					eeffLiberacionDTO.setBloque(direccion.getBloque());
					eeffLiberacionDTO.setNumero(direccion.getNumero());
					eeffLiberacionDTO.setEscalera(direccion.getEscalera());
					eeffLiberacionDTO.setPiso(direccion.getPlanta());
					eeffLiberacionDTO.setPuerta(direccion.getPuerta());
					eeffLiberacionDTO.setCodPostal(direccion.getCodPostalCorreos());
					if (direccion.getIdDireccion() != 0) {
						eeffLiberacionDTO.setIdDireccion(String.valueOf(direccion.getIdDireccion()));
					} else {
						eeffLiberacionDTO.setIdDireccion(null);
					}
					if (direccion.getMunicipio() != null && direccion.getMunicipio().getProvincia() != null) {
						eeffLiberacionDTO.setProvincia(getModeloTrafico().obtenerDescripcionProvincia(direccion.getMunicipio().getProvincia().getIdProvincia()));
						eeffLiberacionDTO.setMunicipio(getModeloTrafico().obtenerDescripcionMunicipio(direccion.getMunicipio().getId().getIdMunicipio(),
										direccion.getMunicipio().getProvincia().getIdProvincia()));
					}
				}
			}
		}
		eeffLiberacionDTO.setRespuesta(ConstantesEEFF.EEFF_TEXTO_MENSAJE_ENCOLAR_LIBERAR);
	}

	private boolean solicitarLiberacion(BigDecimal numExpediente, EeffLiberacionDTO eeffLiberacionDTO, Long idContrato, BigDecimal idUsuario) throws OegamExcepcion {
		try {
			String nombreFicheroXML = generarXml(eeffLiberacionDTO);
			if (nombreFicheroXML == null) {
				return false;
			}
			
			ResultBean resultBean = servicioCola.crearSolicitud(ProcesosEnum.PROCESO_EEFF.getNombreEnum(), nombreFicheroXML, gestorPropiedades.valorPropertie(NOMBRE_HOST),
					TipoTramiteTrafico.consultaEEFF.getValorEnum(), numExpediente.toString(), idUsuario, null,new BigDecimal(idContrato));
			if(resultBean == null || !resultBean.getError()){
				return true;
			}
			return false;
		} catch (OegamExcepcion e) {
			log.error("Error solicitando la liberación, no se ha podido encolar");
			throw e;
		}
	}

	/**
	 * Método que convierte el Dto al Entity
	 * 
	 * @param eeffLiberacionDTO
	 * @return
	 * @throws ParseException
	 * @throws HibernateException
	 */

	private EeffLiberacionVO convertirEEFFLiberacionDTOToEEFFLiberacion(EeffLiberacionDTO eeffLiberacionDTO) throws ParseException {
		return conversor.transform(eeffLiberacionDTO,EeffLiberacionVO.class);
	}

	/**
	 * Método que convierte el entity EeffLiberacion al EEFFLiberacionDTO
	 * 
	 * @param eeffLiberacion
	 * @return
	 */
	private EeffLiberacionDTO convertirEEFFLiberacionToEEFFLiberacionDTO(EeffLiberacionVO eeffLiberacion) {
		return comprobarFechasDTO(conversor.transform(eeffLiberacion,EeffLiberacionDTO.class));
	}
	
	protected EeffLiberacionDTO comprobarFechasDTO(EeffLiberacionDTO eeffLiberacionDTO){
		if (eeffLiberacionDTO==null){
			return null;
		}
		Fecha fecha = eeffLiberacionDTO.getFechaFactura(); 
		boolean modi = false;
		if(fecha != null){
			if(fecha.getDia().length() == 1){
				fecha.setDia("0" + fecha.getDia());
				modi = true;
			}
			if(fecha.getMes().length() == 1){
				fecha.setMes("0" + fecha.getMes());
				modi = true;
			}
		}
		if(modi){
			eeffLiberacionDTO.setFechaFactura(fecha);
		}
		
		modi = false;
		fecha = eeffLiberacionDTO.getFechaRealizacion();
		
		if(fecha != null){
			if(fecha.getDia().length() == 1){
				fecha.setDia("0" + fecha.getDia());
				modi = true;
			}
			if(fecha.getMes().length() == 1){
				fecha.setMes("0" + fecha.getMes());
				modi = true;
			}
		}
		if(modi){
			eeffLiberacionDTO.setFechaRealizacion(fecha);
		}
		
		return eeffLiberacionDTO;
	}

	private String generarXml(EeffLiberacionDTO eeffDTO) {
		String xml;
		String nombreFichero= null;
		File file = null;
		try {
			xml = TransformToXML.transformarDtoLibXml(eeffDTO);
			nombreFichero = "EEFFLIBERACION_"+ eeffDTO.getNumExpediente();
			if (xml != null && !xml.isEmpty()) {
				file = gestorDocumentos.guardarFichero(ConstantesEEFF.EEFF, ConstantesEEFF.EEFF_SUBTIPO_LIBERACION, 
						Utilidades.transformExpedienteFecha(eeffDTO.getNumExpediente()), "EEFFLIBERACION_"+ eeffDTO.getNumExpediente(), ".xml", 
						xml.getBytes("UTF-8"));
			}
		} catch (ParseException e) {
			log.error("Se ha producido un error al intentar parsear el documento xml\n" + e.getMessage());
		} catch (JAXBException je) {
			log.error("Se ha producido un error al generar  el documento xml\n" + je.getMessage());
		} catch (SAXException se) {
			log.error("Se ha producido un error al generar el documento xml\n" + se.getMessage());
		} catch (IOException ie) {
			log.error("Se ha producido un error al tratar el archivo xml\n" + ie.getMessage());
		} catch (InternalException ine) {
			log.error("Ha ocurrido un error interno al generar el archivo xml\n" + ine.getMessage());
		} catch (OegamExcepcion oe) {
			log.error("Ha ocurrido un error  al generar el archivo xml\n" + oe.getMessage());
		}
		if (file != null) {
			return nombreFichero;
		}
		return null;
	}

	private List<String> validarDatos(EeffLiberacionDTO eeff) {
		List<String> errores = new ArrayList<String>();
		log.debug("Ha entrado método validar Datos Liberación");
		if (eeff == null) {
			errores.add(ConstantesEEFF.EEFF_TEXTO_ERROR_VALIDACION_LIBERACION);
		} else {
			try {
				if (eeff.getNif() == null || eeff.getNif().isEmpty()) {
					errores.add(ConstantesEEFF.EEFF_TEXTO_ERROR_IMPO_VALI_TITUDNI);
				}
				if (eeff.getTipoVia() == null || eeff.getTipoVia().isEmpty()) {
					errores.add(ConstantesEEFF.EEFF_TEXTO_ERROR_IMPO_VALI_DIRVIA);
				}
				if (eeff.getNombreVia() == null
						|| eeff.getNombreVia().isEmpty()) {
					errores.add(ConstantesEEFF.EEFF_TEXTO_ERROR_IMPO_VALI_NOM_VIA);
				}
				if (eeff.getNumero() == null || eeff.getNumero().isEmpty()) {
					errores.add(ConstantesEEFF.EEFF_TEXTO_ERROR_IMPO_VALI_DIRNUM);
				}
				if (eeff.getProvincia() == null
						|| eeff.getProvincia().isEmpty()) {
					errores.add(ConstantesEEFF.EEFF_TEXTO_ERROR_IMPO_VALI_DIRPROV);
				}
				if (eeff.getMunicipio() == null
						|| eeff.getMunicipio().isEmpty()) {
					errores.add(ConstantesEEFF.EEFF_TEXTO_ERROR_IMPO_VALI_DIRMUN);
				}
				if (eeff.getCodPostal() == null
						|| eeff.getCodPostal().isEmpty()) {
					errores.add(ConstantesEEFF.EEFF_TEXTO_ERROR_IMPO_VALI_DIR_CP);
				}

				if (eeff.getTarjetaNive() != null && !eeff.getTarjetaNive().isEmpty()) {
					if (eeff.getTarjetaNive().length() != ConstantesEEFF.EEFF_LONGITUD_NIVE) {
						errores.add(ConstantesEEFF.EEFF_TEXTO_ERROR_VALI_LONG_NIVE);
					}
				} else {
					errores.add(ConstantesEEFF.EEFF_TEXTO_ERROR_VALI_NIVE);
				}
				if (eeff.getTarjetaBastidor() == null || eeff.getTarjetaBastidor().isEmpty()) {
					errores.add(ConstantesEEFF.EEFF_TEXTO_ERROR_VALI_BASTIDOR);
				}
				if (eeff.getFirCif() == null || eeff.getFirCif().isEmpty()) {
					errores.add(ConstantesEEFF.EEFF_TEXTO_ERROR_VALI_FIRCIF);
				}
				if (eeff.getFirMarca() == null || eeff.getFirMarca().isEmpty()) {
					errores.add(ConstantesEEFF.EEFF_TEXTO_ERROR_VALI_FIRMARCA);
				}
				if (eeff.getNumFactura() == null || eeff.getNumFactura().isEmpty()) {
					errores.add(ConstantesEEFF.EEFF_TEXTO_ERROR_IMPO_VALI_NUM_FACT);
				}
				if (eeff.getFechaFactura() == null || eeff.getFechaFactura().isfechaNula()) {
					errores.add("La fecha de la factura es obligatoria");
				}
				if(eeff.getNifRepresentado() == null || eeff.getNifRepresentado().isEmpty()){
					errores.add(ConstantesEEFF.EEFF_TEXTO_ERROR_CIF_CONCESIONARIO);
				}
			} catch (ClassCastException e) {
				errores.add(ConstantesEEFF.EEFF_TEXTO_ERROR_VALIDACION_LIBERACION);
			}
		}
		return errores;
	}

	@Override
	@Transactional
	public boolean duplicar(BigDecimal numExpedientePrevio, BigDecimal numExpedienteNuevo) {
		if (numExpedientePrevio == null || numExpedienteNuevo == null) {
			return false;
		}
		EeffLiberacionDTO eeff = (EeffLiberacionDTO) this.recuperarDatos(numExpedientePrevio);
		if (eeff != null) {
			eeff.setNumExpediente(numExpedienteNuevo);
			if (this.guardarDatos(eeff) == null) {
				return false;
			}
			return true;
		}
		return false;
	}

	@Override
	@Transactional
	public boolean esRealizado(BigDecimal numExpediente) {
		List<EeffLiberacionVO> lista = liberacionDao.getEeffLiberacionPorNumExpediente(numExpediente);
		if (lista != null && !lista.isEmpty()) {
			return lista.get(0).isRealizado();
		}
		return false;
	}

	@Override
	public ResultBean comprobarCreditos(int numCreditos) {
		// Hay creditos para poder liberar
		if (!utilesColegiado.tienePermisoAdmin()) {
			ContratoUsuarioVO contrato = utilesColegiado.getContratoUsuario(utilesColegiado.getIdUsuarioSessionBigDecimal().toString());
			ResultBean result = servicioCredito.validarCreditos(TipoTramiteTrafico.consultaEEFF.getValorEnum(), BigDecimal.valueOf(contrato.getId().getIdContrato()), new BigDecimal(numCreditos));
			if (result.getError()) {
				return new ResultBean(true,"No tiene suficientes creditos para hacer la liberación");
			}
		}
		return new ResultBean();
	}

	@Override
	public ResultBean comprobarCreditosProceso(String idUsuario, int numCreditos) {
		ContratoUsuarioVO contrato = utilesColegiado.getContratoUsuario(idUsuario);
		ResultBean result = servicioCredito.validarCreditos(TipoTramiteTrafico.consultaEEFF.getValorEnum(), BigDecimal.valueOf(contrato.getId().getIdContrato()), new BigDecimal(numCreditos));
		if (result.getError()) {
			return new ResultBean(true,"No tiene suficientes creditos para hacer la liberación");
		}
		return new ResultBean();
	}

	
	@Override
	public StringBuffer getFicheroXml(ColaBean solicitud) {
		BigDecimal idEnvio = solicitud.getIdTramite();
		File file = null;
		StringBuffer xml = null;

		try {
			file = gestorDocumentos.buscarFicheroPorNombreTipo(ConstantesEEFF.EEFF,ConstantesEEFF.EEFF_SUBTIPO_LIBERACION,
					Utilidades.transformExpedienteFecha(idEnvio),solicitud.getXmlEnviar(), ConstantesGestorFicheros.EXTENSION_XML).getFile();
			if (file != null) {
				xml = new StringBuffer("<![CDATA[");
				// Solo debe de haber un documento para un expediente.
				log.info("Obtenido fichero XML " + file.getName());

				BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file), "UTF-8"));
				int value = 0;
				// reads to the end of the stream
				while ((value = br.read()) != -1) {
					// converts int to character
					xml.append((char) value);
				}
				br.close();
				xml.append("]]>");				
			}
		} catch (OegamExcepcion e) {
			log.error("Error: Fichero no encontrado." + e);
		} catch (IOException e) {
			log.error("Error al leeer el fichero." + e);
		}

		return xml;
	}

	@Override
	public EeffLiberacionVO liberacionProceso(EeffLiberacionVO eeffLiberacion,String estadoBastidor, String numRegistroEntrada, String numRegistroSalida) {
		eeffLiberacion.setRespuesta("Estado de la liberacion " + estadoBastidor + " NumReg Entrada " + numRegistroEntrada + ", NumReg Salida "
				+ numRegistroSalida);
		eeffLiberacion.setRealizado(true);
		// No existe fecha de realización por parte de dgt
		eeffLiberacion.setFechaRealizacion(utilesFecha.convertirFechaEnDate(utilesFecha.getFechaHoraActualLEG()));
		getModeloTrafInte().cambiarEstado(eeffLiberacion.getNumExpediente(),EstadoTramiteTrafico.LiberadoEEFF);

		actualizarDatos(eeffLiberacion);
		log.info("Liberacion actualizada, se guarda la respuesta");
		return eeffLiberacion;
	}
	
	@Override
	@Transactional
	public ResultBean liberar(BigDecimal numExpediente, BigDecimal idUsuario) throws OegamExcepcion {
		ResultBean resultado;
		if (numExpediente == null) {
			return new ResultBean(true,
					"No se ha indicado el número de expediente");
		}
		// Dto que contiene los datos del trámite
		TramiteTraficoDto tramite = getModeloTrafInte().recuperarDtoPorNumExp(numExpediente);
		// Comprobación del estado
		if (tramite.getEstado() == null || !tramite.getEstado().equals(new BigDecimal(EstadoTramiteTrafico.Validado_Telematicamente.getValorEnum()))) {
			log.error("El trámite " + numExpediente + " no está validado telemáticamente por lo que no se puede liberar");
			return new ResultBean(true,"Tramite " + numExpediente + ": " + ConstantesEEFF.EEFF_TEXTO_ERROR_ESTADO_PARA_LIBERACION);
		}
		// Se obtienen los datos de liberación asociados al trámite, se obtiene aparte
		EeffLiberacionDTO eeffLiberacionDTO = recuperarDatos(numExpediente);
		if (eeffLiberacionDTO == null) {
			log.error("El trámite no tiene asociados datos de liberación");
			return new ResultBean(true,"Tramite " + numExpediente + ": " + ConstantesEEFF.EEFF_TEXTO_ERROR_DATOS_LIBERACION);
		}
		if (eeffLiberacionDTO.isRealizado() || eeffLiberacionDTO.isExento()) {
			log.info("El trámite ya se ha liberado, o está exento de liberación");
			return new ResultBean(true, "Tramite " + numExpediente + ": " + "No se puede realizar la liberación, porque ya está realizada, o está exenta");
		}
		// se obtiene el titular
		IntervinienteTrafico titularTramite = null;
		for (int k = 0; k < tramite.getIntervinienteTraficos().size() && titularTramite == null; k++) {
			if (null != tramite.getIntervinienteTraficos().get(k).getTipoIntervinienteBean() && tramite.getIntervinienteTraficos().get(k)
							.getTipoIntervinienteBean().getTipoInterviniente().equals(TipoInterviniente.Titular.getValorEnum())) {
				titularTramite = tramite.getIntervinienteTraficos().get(k);
			}
		}
		if (tramite.getVehiculo() == null || titularTramite == null) {
			log.error("El trámite no tiene asociados  datos de vehículo o de titular ");
			return new ResultBean(true, "Tramite " + numExpediente + ": " + ConstantesEEFF.EEFF_TEXTO_ERROR_VALIDACION_LIBERACION);
		}
		// Se cargan los datos de vehiculo, titular y liberacion para hacer la
		// solicitud
		cargarDatosParaLiberar(eeffLiberacionDTO, tramite.getVehiculo(),titularTramite);
		// Se validan los datos de liberación
		// Validación de datos
		List<String> erroresValidacion = validarDatos(eeffLiberacionDTO);
		if (erroresValidacion != null && erroresValidacion.size() > 0) {
			resultado = new ResultBean();
			resultado.setError(true);
			resultado.setMensaje("Hay fallos en la validación para la liberación. No se puede liberar.");
			for (String error : erroresValidacion) {
				resultado.addMensajeALista("Tramite " + numExpediente + ": " + error);
			}
			return resultado;
		}
		boolean resultadoSolicitud;
		BigDecimal[] nexpediente = new BigDecimal[1];
		nexpediente[0] = numExpediente;

		guardarDatos(eeffLiberacionDTO, numExpediente,tramite.getNumColegiado());

		if (!getModeloTrafInte().cambiarEstado(nexpediente, EstadoTramiteTrafico.Pendiente_Liberar, idUsuario)) {
			log.error("El trámite no ha podido cambiar de estado a pendiente de liberar");
			return new ResultBean(true, "Tramite " + numExpediente + ": " + ConstantesEEFF.EEFF_TEXTO_SOLICITUD_LIBERACION_ERROR);
		}
		log.debug("El trámite " + tramite.getNumExpediente() + " que se quiere liberar ha cambiado de estado a Pendiente de liberar");

		try {
			resultadoSolicitud = solicitarLiberacion(numExpediente, eeffLiberacionDTO, tramite.getContrato().getIdContrato(), idUsuario);
		} catch (OegamExcepcion e) {
			getModeloTrafInte().cambiarEstado(nexpediente, EstadoTramiteTrafico.Validado_Telematicamente, idUsuario);
			// Lanzo una excepción para que la transacción haga el rollback, y
			// deshaga el guardar los datos de liberación.
			throw e;
		}
		if (!resultadoSolicitud) {
			log.error("No se ha podido realizar la solicitud de liberación del trámite " + tramite.getNumExpediente());
			return new ResultBean(true, "Tramite " + numExpediente + ": " + ConstantesEEFF.EEFF_TEXTO_SOLICITUD_LIBERACION_ERROR);
		}
		log.info("Se ha solicitado la ejecución del proceso de liberar del trámite " + tramite.getNumExpediente());
		resultado = new ResultBean(false, "Tramite " + numExpediente + ": " + ConstantesEEFF.EEFF_TEXTO_SOLICITUD_LIBERACION_CORRECTA);
		return resultado;
	}

	@Override
	@Transactional
	public ResultBean liberaProceso(ColaBean solicitud) {
		RespuestaLiberarDTO respuestaLiberarDTO = null;
		EeffLiberacionVO eeffLiberacionVO = null;
		String sRespuesta = null;
		ResultBean resultado = null;
		try {
			boolean creditosOk = descontarCreditos(solicitud,getModeloTrafico().obtenerIdContratoTramite(solicitud.getIdTramite()),
					TipoTramiteTrafico.consultaEEFF.getValorEnum());
			if (creditosOk) {
				StringBuffer xml = getFicheroXml(solicitud);
				if (xml != null) {
					ColegiadoDto colegiadoDto = servicioColegiado.getColegiadoDto("2717");
					UtilesEEFF utilesEEFF = new UtilesEEFF();
					utilesEEFF.getHandlerFirmado(colegiadoDto.getAlias(),getSoOperacionesITVWSService());
					SolicitudOperacionesITVWS solicitudOperacionesITVWS = getSoOperacionesITVWSService().
							getSolicitudOperacionesITVWS(new URL(gestorPropiedades.valorPropertie(ConstantesEEFF.EEFF_URL)));
					log.info("Enviando peticion de liberacion...");

					respuestaLiberarDTO = solicitudOperacionesITVWS.liberarEITV("ES_es", xml.toString());
					log.info("Obtenida respuesta de liberacion");
					eeffLiberacionVO = recuperarDatosVO(solicitud.getIdTramite());
					if (respuestaLiberarDTO != null && respuestaLiberarDTO.getNumRegistroEntrada() != null && !respuestaLiberarDTO.getNumRegistroEntrada().equals("")) {
						eeffLiberacionVO = liberacionProceso(eeffLiberacionVO,respuestaLiberarDTO.getDatosSimpleEITV().getEstadoBastidor(),
								respuestaLiberarDTO.getNumRegistroEntrada(),respuestaLiberarDTO.getNumRegistroSalida());
						guardarRespuesta(respuestaLiberarDTO, eeffLiberacionVO.getNumExpediente().toString());
						sRespuesta = "OK";
					} else if (respuestaLiberarDTO.getInfoErrores() != null && respuestaLiberarDTO.getInfoErrores().length > 0) {
						devolverCreditos(solicitud,getModeloTrafico().obtenerIdContratoTramite(solicitud.getIdTramite()),
								TipoTramiteTrafico.consultaEEFF.getValorEnum());
						sRespuesta = " ";
						for (InfoErrorDTO info : respuestaLiberarDTO.getInfoErrores()) {
							log.info(info.getCodigoError() + ":"+ info.getDescripcionError());
							sRespuesta += info.getCodigoError() + ":" + info.getDescripcionError() + " ";
						}
						resultado = new ResultBean(false,sRespuesta);
						eeffLiberacionVO.setRespuesta(sRespuesta);
						actualizarDatos(eeffLiberacionVO);
						guardarRespuesta(respuestaLiberarDTO, eeffLiberacionVO.getNumExpediente().toString());
					}else {
						devolverCreditos(solicitud,getModeloTrafico().obtenerIdContratoTramite(solicitud.getIdTramite()),
								TipoTramiteTrafico.consultaEEFF.getValorEnum());
						resultado = new ResultBean(true,"No se puede realizar la liberación porque ha sucedio un error en la conexion.");
						eeffLiberacionVO.setRespuesta("No se puede realizar la liberación porque ha sucedio un error en la conexion.");
						actualizarDatos(eeffLiberacionVO);
						sRespuesta = "No se puede realizar la liberación porque ha sucedio un error en la conexion.";
					}
				} else {
					resultado = new ResultBean(false,"No se puede realizar la liberación porque no existe un fichero xml con los datos.");
					guardarRespuesta(devolverRespuestaErrorXml(), solicitud.getIdTramite().toString());
					sRespuesta = "No se puede realizar la liberación porque no existe un fichero xml con los datos.";
				}
			}else{
				resultado = new ResultBean(false,"No se puede realizar la liberación porque no tiene creditos suficientes para ello.");
				guardarRespuesta(devolverRespuestaErrorXml(), solicitud.getIdTramite().toString());
				sRespuesta = "No se puede realizar la liberación porque no tiene creditos suficientes para ello.";
			}
			solicitud.setRespuesta(sRespuesta);
		} catch (RemoteException e) {
			log.error("Error al liberar: " + e);
			resultado = new ResultBean(true,"Error al liberar: " + e);
			devolverCreditos(solicitud,getModeloTrafico().obtenerIdContratoTramite(solicitud.getIdTramite()),
					TipoTramiteTrafico.consultaEEFF.getValorEnum());
		} catch (MalformedURLException e) {
			log.error("Error al liberar: " + e);			
			resultado = new ResultBean(true,"Error al liberar: " + e);
			devolverCreditos(solicitud,getModeloTrafico().obtenerIdContratoTramite(solicitud.getIdTramite()),
					TipoTramiteTrafico.consultaEEFF.getValorEnum());
		} catch (ServiceException e) {
			log.error("Error al liberar: " + e);
			resultado = new ResultBean(true,"Error al liberar: " + e);
			devolverCreditos(solicitud,getModeloTrafico().obtenerIdContratoTramite(solicitud.getIdTramite()),
					TipoTramiteTrafico.consultaEEFF.getValorEnum());
		}

		return resultado;
	}

	protected RespuestaLiberarDTO devolverRespuestaErrorXml() {
		RespuestaLiberarDTO respuestaLiberarDTO = new RespuestaLiberarDTO();

		InfoErrorDTO infoErrorDTO = new InfoErrorDTO();
		infoErrorDTO.setCodigoError("0");
		infoErrorDTO.setDescripcionError("No se puede realizar la consulta porque no existe un fichero xml con los datos.");
		InfoErrorDTO[] listaInfoErrorDTOs = new InfoErrorDTO[1];
		listaInfoErrorDTOs[0] = infoErrorDTO;

		respuestaLiberarDTO.setInfoErrores(listaInfoErrorDTOs);
		return respuestaLiberarDTO;
	}

	protected void guardarRespuesta(Object XmlRespuesta, String numExpediente) {
		String xmlXStream = null;
		// Pasando un objeto a xml.
		XStream xstream = new XStream();
		xstream.processAnnotations(XmlRespuesta.getClass());
		xmlXStream = xstream.toXML(XmlRespuesta);

		FicheroBean fichero = new FicheroBean();
		fichero.setTipoDocumento(ConstantesGestorFicheros.EEFF);
		fichero.setSubTipo(ConstantesGestorFicheros.EEFFLIBERACION);

		fichero.setNombreDocumento(ConstantesGestorFicheros.RESPUESTA_EEFF + numExpediente);
		fichero.setFicheroByte(xmlXStream.getBytes());
		fichero.setExtension(ConstantesGestorFicheros.EXTENSION_XML);
		fichero.setSobreescribir(false);
		if (numExpediente != null && numExpediente.isEmpty()) {
			fichero.setFecha(Utilidades.transformExpedienteFecha(numExpediente));
		} else {
			fichero.setFecha(utilesFecha.getFechaHoraActualLEG());
		}

		try {
			gestorDocumentos.guardarFichero(fichero);
		} catch (OegamExcepcion e) {
			log.error("Error al guardar la respeusta", e, numExpediente);
		}
	}

	private boolean descontarCreditos(ColaBean cola, BigDecimal idcontrato, String tipoTramite) {
		String cobraCreditos = gestorPropiedades.valorPropertie(ConstantesEEFF.EEFF_COBRAR_CREDITOS_LIBERAR);
		if (cobraCreditos==null || !cobraCreditos.equals("true")){
			return true;
		}
		// El tipo de credito es decremental y se descuenta credito en negativo... es una manera distinta de poner credito incremental
		ResultBean res = servicioCredito.descontarCreditos(tipoTramite, idcontrato, new BigDecimal(-1), cola.getIdUsuario(), ConceptoCreditoFacturado.EFL);
		if (res.getError()) {
			log.error("ERROR DESCONTAR CREDITOS");
			log.error("CONTRATO: " + idcontrato.toString());
			log.error("ID_USUARIO: " + cola.getIdUsuario());
		}
		return !res.getError();
	}

	private boolean devolverCreditos(ColaBean cola, BigDecimal idcontrato, String tipoTramite) {
		String cobraCreditos = gestorPropiedades.valorPropertie(ConstantesEEFF.EEFF_COBRAR_CREDITOS_LIBERAR);
		if (cobraCreditos==null || !cobraCreditos.equals("true")){
			return true;
		}
		ResultBean resultBean = servicioCredito.devolverCreditos(tipoTramite, idcontrato, -1, cola.getIdUsuario(), ConceptoCreditoFacturado.DEFL);
		if (resultBean.getError()) {
			log.error("Error al devolver los creditos.");
		}
		return !resultBean.getError();
	}

	@Transactional
	@Override
	public EeffLiberacionDTO actualizarDatosDTO(EeffLiberacionDTO eeffDTO) {
		try {
			actualizarDatos(convertirEEFFLiberacionDTOToEEFFLiberacion(eeffDTO));
		} catch (ParseException e) {
			log.error("Error en la actualizacion", e);
		}
		return eeffDTO;
	}

	public EeffLiberacionDao getLiberacionDao() {
		return liberacionDao;
	}

	public void setLiberacionDao(EeffLiberacionDao liberacionDao) {
		this.liberacionDao = liberacionDao;
	}

	public ModeloTramiteTrafInterface getModeloTrafInte() {
		if (modeloTrafInte == null) {
			modeloTrafInte = new ModeloTramiteTrafImpl();
		}
		return modeloTrafInte;
	}

	public ModeloCreditosTrafico getModeloCreditosTrafico() {
		if (modeloCreditosTrafico == null) {
			modeloCreditosTrafico = new ModeloCreditosTrafico();
		}
		return modeloCreditosTrafico;
	}

	public SolicitudOperacionesITVWSService getSoOperacionesITVWSService() {
		if (soOperacionesITVWSService == null) {
			soOperacionesITVWSService = new SolicitudOperacionesITVWSServiceLocator();
		}
		return soOperacionesITVWSService;
	}
	
	private ModeloTrafico getModeloTrafico() {
		if(modeloTrafico == null){
			modeloTrafico = new ModeloTrafico();
		}
		return modeloTrafico;
	}

	private ModeloSolicitud getModeloSolicitud() {
		if(modeloSolicitud == null){
			modeloSolicitud =  new ModeloSolicitud();
		}
		return modeloSolicitud;
	}


	
}
