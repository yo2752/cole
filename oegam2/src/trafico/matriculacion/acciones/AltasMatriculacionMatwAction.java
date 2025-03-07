package trafico.matriculacion.acciones;


import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.interceptor.SessionAware;
import org.displaytag.pagination.PaginatedList;
import org.gestoresmadrid.core.consultasTGate.model.enumerados.OrigenTGate;
import org.gestoresmadrid.core.historicocreditos.model.dao.enumerados.ConceptoCreditoFacturado;
import org.gestoresmadrid.core.model.enumerados.EstadoTramiteTrafico;
import org.gestoresmadrid.core.model.enumerados.TipoInterviniente;
import org.gestoresmadrid.core.model.enumerados.TipoTramiteTrafico;
import org.gestoresmadrid.core.trafico.eeff.model.enumerados.ConstantesEEFF;
import org.gestoresmadrid.core.vehiculo.model.enumerados.Color;
import org.gestoresmadrid.oegam2comun.consultaEitv.model.service.ServicioConsultaEitv;
import org.gestoresmadrid.oegam2comun.creditos.model.service.ServicioCreditoFacturado;
import org.gestoresmadrid.oegam2comun.datosSensibles.model.service.ServicioDatosSensibles;
import org.gestoresmadrid.oegam2comun.model.service.ServicioUsuario;
import org.gestoresmadrid.oegam2comun.trafico.dto.ivtm.IvtmMatriculacionDto;
import org.gestoresmadrid.oegam2comun.trafico.eeff.model.service.ServicioEEFF;
import org.gestoresmadrid.oegam2comun.trafico.eeff.view.dto.EeffLiberacionDTO;
import org.gestoresmadrid.oegam2comun.trafico.model.service.ServicioTramiteTrafico;
import org.gestoresmadrid.oegam2comun.trafico.model.service.ServicioTramiteTraficoMatriculacion;
import org.gestoresmadrid.oegam2comun.trafico.view.beans.ResultadoMatriculacionBean;
import org.gestoresmadrid.oegam2comun.trafico.view.dto.TramiteTrafMatrDto;
import org.gestoresmadrid.oegam2comun.vehiculo.model.service.ServicioVehiculo;
import org.gestoresmadrid.oegamComun.acceso.service.impl.UtilesColegiado;
import org.gestoresmadrid.oegamDocBase.service.ServicioGestionDocBase;
import org.gestoresmadrid.oegamDocBase.view.bean.DocBaseCarpetaTramiteBean;
import org.gestoresmadrid.oegamDocBase.view.bean.ResultadoDocBaseBean;
import org.gestoresmadrid.utilidades.components.GestorPropiedades;
import org.gestoresmadrid.utilidades.components.Utiles;
import org.springframework.beans.factory.annotation.Autowired;

import com.opensymphony.xwork2.Action;

import colas.modelo.ModeloSolicitud;
import es.globaltms.gestorDocumentos.constantes.ConstantesGestorFicheros;
import escrituras.beans.Persona;
import escrituras.beans.ResultBean;
import escrituras.modelo.ModeloColegiado;
import escrituras.modelo.ModeloPersona;
import general.acciones.ActionBase;
import oegam.constantes.ConstantesFirma;
import oegam.constantes.ConstantesPQ;
import oegam.constantes.ConstantesSession;
import procesos.daos.BeanPQTomarITV;
import trafico.avpogestbasti.TN3270.Bsti;
import trafico.beans.DatosITV;
import trafico.beans.IntervinienteTrafico;
import trafico.beans.MarcaBean;
import trafico.beans.TipoVehiculoBean;
import trafico.beans.TramiteTraficoBean;
import trafico.beans.TramiteTraficoMatriculacionBean;
import trafico.beans.avpobastigest.BstiBean;
import trafico.beans.daos.BeanPQAltaMatriculacion;
import trafico.beans.daos.BeanPQCambiarEstadoTramite;
import trafico.beans.daos.BeanPQMatricular;
import trafico.beans.utiles.MatriculacionTramiteTraficoBeanMatwPQConversion;
import trafico.ivtm.servicio.ServicioIVTMMatriculacionImpl;
import trafico.ivtm.servicio.ServicioIVTMMatriculacionIntf;
import trafico.modelo.ModeloCreditosTrafico;
import trafico.modelo.ModeloEITV;
import trafico.modelo.ModeloMatriculacion;
import trafico.modelo.ModeloPresentacionAEAT;
import trafico.modelo.ModeloTrafico;
import trafico.modelo.impl.ModeloTramiteTrafImpl;
import trafico.modelo.interfaz.ModeloTramiteTrafInterface;
import trafico.utiles.ComprobadorDatosSensibles;
import trafico.utiles.ConstantesPDF;
import trafico.utiles.UtilesVistaTrafico;
import trafico.utiles.UtilidadesAccionTrafico;
import trafico.utiles.constantes.ConstantesAEAT;
import trafico.utiles.constantes.ConstantesIVTM;
import trafico.utiles.constantes.ConstantesMensajes;
import trafico.utiles.constantes.ConstantesTrafico;
import trafico.utiles.enumerados.Combustible;
import trafico.utiles.enumerados.EstadoIVTM;
import trafico.utiles.enumerados.TipoCreacion;
import utilidades.estructuras.Fecha;
import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;
import utilidades.mensajes.Claves;
import utilidades.web.OegamExcepcion;


@SuppressWarnings("serial")
public class AltasMatriculacionMatwAction extends ActionBase implements SessionAware
{

	private static final String NO_SE_HA_PODIDO_ANULAR_EL_TRAMITE = "no se ha podido anular el tramite";
	private static final String TRAMITE_ANULADO = "tramite anulado";
	private static final String ALTAS_MATRICULACION_MATW = "altasMatriculacionMatw";
	private static final String DESCARGAR_DOCUMENTO_BASE = "descargarDocumentoBase";
	private static final String DESCARGAR_PRESENTACION_576 = "descargarFichero";
	private static final String CLONAR_TRAMITE = "aClonarTramite";
	private static final String PAGO_IVTM = "pagoIVTM";
	public static final String CODIGO_CERTIFICADO_ALTA = "ALTAPROYECTOIVTM";
	private boolean readOnly = false; 
	private boolean habilitarBastidor;
	private static final ILoggerOegam log = LoggerOegam.getLogger(AltasMatriculacionMatwAction.class);	
	private File fileModeloAEAT;
	private File fileJustifIvtm;
	private String nombreFicheroJustifIvtm;
	private String fileModeloAEATFileName; 
	private String fileModeloAEATContentType; 
	private boolean permisoPago;
	private TramiteTraficoMatriculacionBean traficoTramiteMatriculacionBean;
	
	/* INICIO MANTIS 0013245: añadimos estos Beans para no perder los datos */
	private IntervinienteTrafico presentadorSoloAdmin;
	/* FIN MANTIS 0013245 */
	
	//Valor del atributo ID del nodo a firmar en la petición XML a MATEGE
	public static final String MATEGE_NODO_FIRMAR_ID = "D0";
	public static final String MATEGE_XPATH_NODO_ESCRIBIR_FIRMA_CLIENTE = "/AFIRMA";
	public static final String MATEGE_XPATH_NODO_ESCRIBIR_FIRMA_SERVIDOR = "AFIRMA";
	private File fichero; 
	private String provinciaIVTM;
	private String municipioIVTM;
	private String puebloIVTM;
	private Boolean ficheroDeclaracion576;
	private Boolean pdf576;

	private InputStream inputStream; 	//Flujo de bytes del fichero a imprimir en PDF del action
	private String fileName;			// Nombre del fichero a imprimir
	private String fileSize;			// Tamaño del fichero a imprimir
	
	private String propTexto;
	private boolean marcadoConductorHabitual;


	private String tipoIntervinienteBuscar;//para el botón de buscar interviniente del dni
	private PaginatedList listaAcciones;

	private ModeloTrafico modeloTrafico;
	private ModeloEITV modeloEITV;
	private ModeloSolicitud modeloSolicitud;
	private ModeloCreditosTrafico modeloCreditosTrafico;
	private ModeloMatriculacion modeloMatriculacion;
	private ModeloPresentacionAEAT modeloPresentacionAEAT;
	
	private IvtmMatriculacionDto ivtmMatriculacionDto;
	private String ibanTitular= null;
	private ServicioIVTMMatriculacionIntf servicioIVTM = null;
	private String url;
	
	private String numsExpediente;
	private String tipoTramiteSeleccionado;
	private String estadoTramiteSeleccionado;
	private String bastidorSeleccionado;
	private boolean esAccionClonado = false;

	private DocBaseCarpetaTramiteBean docBaseCarpetaTramiteBean;
	
	private static final String antiguoDatosSensibles = "datosSensibles.antiguo";
	
	//Eeff 
	@Autowired
	ServicioEEFF servicioEEFF;
	
	@Autowired
	ServicioVehiculo servicioVehiculo;
	
	@Autowired
	ServicioUsuario servicioUsuario;

	@Autowired
	ServicioCreditoFacturado servicioCreditoFacturado;
	
	@Autowired
	ServicioDatosSensibles servicioDatosSensibles;
	
	@Autowired
	ServicioConsultaEitv servicioConsultaEitv;
	
	@Autowired
	ServicioTramiteTrafico servicioTramiteTrafico;
	
	@Autowired
	ServicioTramiteTraficoMatriculacion servicioTramiteTraficoMatriculacion;
	
	ModeloTramiteTrafInterface modeloTramite = new ModeloTramiteTrafImpl();
	
	@Autowired
	MatriculacionTramiteTraficoBeanMatwPQConversion matriculacionTramiteTraficoBeanMatwPQConversion;

	@Autowired
	GestorPropiedades gestorPropiedades;
	
	@Autowired
	Utiles utiles;
	
	@Autowired
	ServicioGestionDocBase servicioGestionDocBase;

	@Autowired
	private UtilesColegiado utilesColegiado;
	
	public String inicio() throws Throwable{

		log.debug("inicio AltasAction"); 
		propTexto = ConstantesMensajes.MENSAJE_TRAMITESRESPONSABILIDAD;
		traficoTramiteMatriculacionBean=new TramiteTraficoMatriculacionBean(true); 
		traficoTramiteMatriculacionBean.getTramiteTraficoBean().getVehiculo().setColorBean(Color.Indefinido.getValorEnum());
		traficoTramiteMatriculacionBean.getTramiteTraficoBean().getJefaturaTrafico().setJefaturaProvincial(utilesColegiado.getIdJefaturaSesion());
		/**
		 * Incidencia: 11763. Se pide que el campo Exento o cuota 0 de 576 no este marcado por defecto en tramites nuevos 
		 * @author David Sierra
		 * @date 17-10-2014
		 */
		traficoTramiteMatriculacionBean.setExento576("false");
		setFicheroDeclaracion576(false);
		setPdf576(false);
//		try {
//			borramePruebaExcepcionOegam();
//		} catch (OegamExcepcion e) {
//			log.error(e); 
//			UtilesExcepciones.logarException(e, "excepcion forzada",log);
//			log.error("e.getmensajeerror1" + e.getMensajeError1()); 
//			e.printStackTrace();
//		}
		return completarFormulario(true);		
	}

	/**
	 * 
	 * @return
	 * @throws Throwable
       SE HA MIGRADO A ALTASMATRICULACIONACTION 
	 */
	private ResultBean guardarTramite() throws Throwable { 

		log.info("guardar tramite"); 
		ResultBean resultBean = new ResultBean();
		
		if (traficoTramiteMatriculacionBean.getTramiteTraficoBean().getVehiculo() != null) {
			// 0007933: MATW - CO2 000001 :Modificacion del formato de co2 999.999 (Se eliminan ceros por la izquiera de la parte entera)
			if (traficoTramiteMatriculacionBean.getTramiteTraficoBean().getVehiculo() != null && traficoTramiteMatriculacionBean.getTramiteTraficoBean().getVehiculo().getCo2() != null
					&& !traficoTramiteMatriculacionBean.getTramiteTraficoBean().getVehiculo().getCo2().trim().isEmpty()) {
				BigDecimal co2 = new BigDecimal(traficoTramiteMatriculacionBean.getTramiteTraficoBean().getVehiculo().getCo2()).setScale(3, BigDecimal.ROUND_DOWN);
				traficoTramiteMatriculacionBean.getTramiteTraficoBean().getVehiculo().setCo2(co2.toString());
			}
			// 9250: MATW - Validar "92" vehículo cilindrada superior a su cilindrada :Modificacion del formato de la cilindrada 999.99 (Se eliminan ceros por la izquiera de la parte entera)
			if (traficoTramiteMatriculacionBean.getTramiteTraficoBean().getVehiculo().getCilindrada() != null
					&& !traficoTramiteMatriculacionBean.getTramiteTraficoBean().getVehiculo().getCilindrada().trim().isEmpty()
					&& traficoTramiteMatriculacionBean.getTramiteTraficoBean().getVehiculo().getTipoVehiculoBean() != null
					&& traficoTramiteMatriculacionBean.getTramiteTraficoBean().getVehiculo().getTipoVehiculoBean().getTipoVehiculo() != null
					&& !traficoTramiteMatriculacionBean.getTramiteTraficoBean().getVehiculo().getTipoVehiculoBean().getTipoVehiculo().isEmpty()
					&& traficoTramiteMatriculacionBean.getTramiteTraficoBean().getVehiculo().getTipoVehiculoBean().getTipoVehiculo().substring(0, 1).equals("9")
					&& traficoTramiteMatriculacionBean.getTramiteTraficoBean().getVehiculo().getCilindrada().length() < 4) {
				BigDecimal cilind = new BigDecimal(traficoTramiteMatriculacionBean.getTramiteTraficoBean().getVehiculo().getCilindrada()).setScale(2, BigDecimal.ROUND_DOWN);

				traficoTramiteMatriculacionBean.getTramiteTraficoBean().getVehiculo().setCilindrada(cilind.toString());

			}
		}
		
		if (!utilesColegiado.tienePermiso(utilesColegiado.PERMISO_MANTENIMIENTO_MATRICULACION_MATW))
		{
			log.error("no hay permiso de mantenimiento de matriculacion");
			resultBean.setError(true); 
			resultBean.addMensajeALista("Error permisos mantenimiento matriculacion");
		}
		
		// INICIO - 0006802: ERROR VAALIDACION 3041
		//if (!validarCodITV(beanPantalla.getTramiteTraficoBean().getVehiculo().getCodItv())) {
		if (!validarCodITV(traficoTramiteMatriculacionBean.getTramiteTraficoBean().getVehiculo().getCodItv())) {
			resultBean.setError(new Boolean(true));
					
			List<String> listaMensajes = new ArrayList<String>();
			listaMensajes.add("El código ITV introducido es erróneo.");
					
			resultBean.addListaMensajes(listaMensajes);
			return resultBean; 
		}
		// FIN - 0006802: ERROR VAALIDACION 3041
		
		//DRC@15-02-2013 Incidencia Mantis: 2708
		traficoTramiteMatriculacionBean.setIdTipoCreacion(new BigDecimal(TipoCreacion.OEGAM.getValorEnum()));
			
		// Cogemos los datos de la liberación EEFF para que no se pierdan
		EeffLiberacionDTO eeffLiberacionDTO = traficoTramiteMatriculacionBean.getEeffLiberacionDTO();
		
		//Si un tramite esta liberado no se puede modificar los datos de vehiculo ni de persona. 
		if(!utilesColegiado.tienePermiso(utilesColegiado.PERMISO_ADMINISTRACION) && eeffLiberacionDTO!=null && servicioEEFF.esRealizadoLiberacion(traficoTramiteMatriculacionBean.getTramiteTraficoBean().getNumExpediente())){
			//comprobamos si se ha modificado los datos
			HashMap<String, Boolean> modificado = modeloTramite.tramiteModificado(traficoTramiteMatriculacionBean);
			if(!modificado.get(ConstantesTrafico.CONSTANTE_TITULAR) ||
					!modificado.get(ConstantesTrafico.CONSTANTE_VEHICULO)){
				List<String> listaMensaje = new ArrayList<String>();
				listaMensaje.add("No se puede modificar ni titular ni vehiculo");
				listaMensaje.add("Si necesita realizar los cambios contacte con el Colegio.");
				resultBean.addListaMensajes(listaMensaje);
				resultBean.setError(true);
				return resultBean;
			}
			
		}
		
		//WorkArround
		Boolean revisado = traficoTramiteMatriculacionBean.getTramiteTraficoBean().getVehiculo().getRevisado();
		
		// Mantis 15845. David Sierra: Valida el Tipo Inspeccion ITV
		String tipoInspeccionItvValidacion = getModeloMatriculacion().validarTipoInspeccionItv(traficoTramiteMatriculacionBean);
		if ("error".equals(tipoInspeccionItvValidacion)) {
			String mensajeTipoInspeccionItvError = ConstantesMensajes.MENSAJE_TIPO_INSPECCION_ITV_ERROR;
			List<String> listaMensaje = new ArrayList<String>();
			listaMensaje.add(mensajeTipoInspeccionItvError);
			resultBean.addListaMensajes(listaMensaje);
			resultBean.setError(true);
			return resultBean;
		} else if ("aviso".equals(tipoInspeccionItvValidacion)) {
			String mensajeTipoInspeccionItvAviso = ConstantesMensajes.MENSAJE_TIPO_INSPECCION_ITV;
			addActionMessage(mensajeTipoInspeccionItvAviso);
		}
		// Fin Mantis
		
		// Mantis 19262. David Sierra. Validacion formato fechas
		String validacionFecha = getModeloMatriculacion().validarFechasMatriculacion(traficoTramiteMatriculacionBean);
		if(!"OK".equals(validacionFecha)) {						
			List<String> listaMensaje = new ArrayList<String>();
			listaMensaje.add(validacionFecha);
			resultBean.addListaMensajes(listaMensaje);
			resultBean.setError(true);
			return resultBean;
		}
		
		// Validación de la matricula origen extranjero, solo se  valida  si el propertie esta habilitado.
		
		String validacionMatriculaOrigen = getModeloMatriculacion().validarMatriculaOrigen(traficoTramiteMatriculacionBean);
		if (!"OK".equals(validacionMatriculaOrigen)) {						
			List<String> listaMensaje = new ArrayList<String>();
			listaMensaje.add(validacionMatriculaOrigen);
			resultBean.addListaMensajes(listaMensaje);
			resultBean.setError(true);
			return resultBean;
		}
		
		

		
		BeanPQAltaMatriculacion beanPq = matriculacionTramiteTraficoBeanMatwPQConversion.convertirTramiteMatriculacionToPQ(traficoTramiteMatriculacionBean,
				utilesColegiado.getNumColegiadoSession(), utilesColegiado.getIdContratoSessionBigDecimal(), utilesColegiado.getIdUsuarioSessionBigDecimal(), utilesColegiado.getIdContratoSessionBigDecimal()); 
		
		
		HashMap <String,Object> resultadoModelo = getModeloMatriculacion().guardarAltaTramiteMatriculacionMatw(beanPq);
		TramiteTraficoMatriculacionBean beanPantalla = (TramiteTraficoMatriculacionBean) resultadoModelo.get(ConstantesPQ.BEANPANTALLA);
		
		if( utilesColegiado.tienePermisoLiberarEEFF()){
			if(beanPantalla!=null && beanPantalla.getTramiteTraficoBean()!=null && beanPantalla.getTramiteTraficoBean().getNumExpediente()!=null){
			
				if(beanPantalla.getTramiteTraficoBean().getVehiculo()==null || beanPantalla.getTramiteTraficoBean().getVehiculo().getNive()==null
						|| beanPantalla.getTramiteTraficoBean().getVehiculo().getBastidor()==null){
					resultBean.setError(true);
					resultBean.addMensajeALista("Error recuperando el tramite, no se han guardado los datos de liberación");
				}
				else{
					eeffLiberacionDTO.setTarjetaNive(beanPantalla.getTramiteTraficoBean().getVehiculo().getNive());
					eeffLiberacionDTO.setTarjetaBastidor(beanPantalla.getTramiteTraficoBean().getVehiculo().getBastidor());
					beanPantalla.setEeffLiberacionDTO(eeffLiberacionDTO);
					if(!servicioEEFF.esRealizadoLiberacion(beanPantalla.getTramiteTraficoBean().getNumExpediente())){
						if(beanPantalla.getTramiteTraficoBean().getNumColegiado()!=null){
							servicioEEFF.guardarDatosLiberacion(beanPantalla.getEeffLiberacionDTO(), beanPantalla.getTramiteTraficoBean().getNumExpediente(), beanPantalla.getTramiteTraficoBean().getNumColegiado());
							if(traficoTramiteMatriculacionBean.getTramiteTraficoBean().getEstado() != null && traficoTramiteMatriculacionBean.getTramiteTraficoBean().getEstado().equals(beanPantalla.getTramiteTraficoBean().getEstado()) && 
									!EstadoTramiteTrafico.Iniciado.equals(beanPantalla.getTramiteTraficoBean().getEstado())){
								ResultBean resultCambioEstado = servicioTramiteTrafico.cambiarEstadoSinEvolucion(beanPantalla.getTramiteTraficoBean().getNumExpediente(), EstadoTramiteTrafico.Iniciado.getValorEnum());
								if(resultCambioEstado == null){
									beanPantalla.getTramiteTraficoBean().setEstado(EstadoTramiteTrafico.Iniciado.getValorEnum());
								}else if(resultCambioEstado.getError()){
									resultBean.setError(true);
									resultBean.addMensajeALista(resultCambioEstado.getListaMensajes().get(0));
								}
							}
						}
						else{
							resultBean.setError(true);
							resultBean.addMensajeALista("Error recuperando el tramite, no se han guardado los datos de liberación");
						}
					}
					else{
						resultBean.setError(false);
						resultBean.addMensajeALista("El vehículo esta liberado no se puede modificar ningun dato de la liberacion del vehículo");
					}
				}
				
			}else{
				resultBean.setError(true);
				resultBean.addMensajeALista("Error recuperando el tramite, no se han guardado los datos de liberación");
			}
			
		}
		
		//WorkArround revisado
		beanPantalla.getTramiteTraficoBean().getVehiculo().setRevisado(revisado);
		
		resultBean = (ResultBean)resultadoModelo.get(ConstantesPQ.RESULTBEAN);
		if (resultBean.getError()==false){
			setTraficoTramiteMatriculacionBean(beanPantalla);
		}
		
		// Mantis 19172. David Sierra. Comprobacion caducidad nif
		ResultBean resultValCadNif = getModeloMatriculacion().comprobarCaducidadNifs(traficoTramiteMatriculacionBean, false);
		if(resultValCadNif.getListaMensajes() != null && !resultValCadNif.getListaMensajes().isEmpty()) {
			for(String mensaje : resultValCadNif.getListaMensajes()){
				if(resultValCadNif.getError()){
					addActionError(mensaje);
				}else{
					addActionMessage(mensaje);
				}
			}
		}

		
		
		// Mantis 14937. David Sierra: Se muestra un mensaje informativo cuando un colegiado introduce un bastidor
		// que ya ha dado de alta ese colegiado (duplicado) tanto al guardar como al validar.
		String bastidor = null;
		String colegiado = null;
		
		if(beanPantalla.getTramiteTraficoBean() != null && beanPantalla.getTramiteTraficoBean().getVehiculo() != null) {
			if (beanPantalla.getTramiteTraficoBean().getVehiculo().getBastidor() != null) {
				bastidor = beanPantalla.getTramiteTraficoBean().getVehiculo().getBastidor();
			}
			if (beanPantalla.getTramiteTraficoBean().getVehiculo().getNumColegiado() != null) {
				colegiado = beanPantalla.getTramiteTraficoBean().getVehiculo().getNumColegiado();
			}
		}		
		if(bastidor != null && colegiado != null) {
			if(servicioVehiculo.isBastidorDuplicado(bastidor, colegiado) && !esAccionClonado) {
				addActionMessage("El bastidor " + bastidor +  " está duplicado para su número de colegiado.");
				log.error("El " + colegiado + " ha dado de alta el siguiente bastidor duplicado " + bastidor);
			}			
		}
		// Fin Mantis
		//TODO MPC. Cambio IVTM.
		//Guardamos la parte de ivtm si existe, y está en un estado que se puede modificar.
		if(ivtmMatriculacionDto!=null && !getServicioIVTM().puedeGenerarAutoliquidacion(traficoTramiteMatriculacionBean.getTramiteTraficoBean().getNumExpediente()).getError()){
			ivtmMatriculacionDto.setNumExpediente(traficoTramiteMatriculacionBean.getTramiteTraficoBean().getNumExpediente());
			ivtmMatriculacionDto = getServicioIVTM().guardarIVTM(ivtmMatriculacionDto);
		}
		
		if(beanPantalla.getCheckJustifIvtm()!=null &&beanPantalla.getCheckJustifIvtm().equals("S")){
			ResultadoMatriculacionBean resultado = servicioTramiteTraficoMatriculacion.obtenerJustificanteIVTM(traficoTramiteMatriculacionBean.getTramiteTraficoBean().getNumExpediente().toString());
			if (!resultado.getError()) {
				setNombreFicheroJustifIvtm(ConstantesGestorFicheros.NOMBRE_JUSTIFICANTE_IVTM+beanPantalla.getTramiteTraficoBean().getNumExpediente().toString()+ConstantesGestorFicheros.EXTENSION_PDF);
			}
		}
		
		return resultBean; 
	}
	
	// INICIO - 0006802: ERROR VAALIDACION 3041
	/**
	 * Validamos que el codigo ITV no contenga espacios ni caracteres especiales.
	 * @param codITV
	 * @return false si contiene al caracter especial o espacio; true si no lo encuentra.
	 */
	private boolean validarCodITV(String codITV) {
		
		if (codITV != null) {
			String[] caracteresInvalidos = {"á","à", "ä", "â", "ª", "Á", "À", "Â", "Ä",
					"é", "è", "ë", "ê", "É", "È", "Ê", "Ë",
					"í", "ì", "ï", "î", "Í", "Ì", "Ï", "Î",
					"ó", "ò", "ö", "ô", "Ó", "Ò", "Ö", "Ô",
					"ú", "ù", "ü", "û", "Ú", "Ù", "Û", "Ü",
					"ñ", "Ñ", "ç", "Ç"};
			
		    for(int i = 0; i < codITV.length(); ++i) {
		        char caracter = codITV.charAt(i);
		        
		        if(!Character.isLetterOrDigit(caracter)) {
		            return false;	            
		        } else {
		        	if (Character.isLetter(caracter)) {
		        		for (int j= 0; j < caracteresInvalidos.length; j++) {
		        			if (codITV.contains(caracteresInvalidos[j])) {
		        				 return false;	 
		        			}
		        		}
		        	}
		        }
		    }
		}
			   
	    return true;
	}
	
	
	// FIN - 0006802: ERROR VAALIDACION 3041
	
	/**
	 * 
	 * @return
	 * @throws Throwable
	 
	 */
	
	private ResultBean tomarTramite() throws Throwable { 

		log.info("tomar tramite"); 
		BeanPQTomarITV beanPQTomarItv=new  BeanPQTomarITV();
		beanPQTomarItv.setP_CODIGO_ITV(traficoTramiteMatriculacionBean.getTramiteTraficoBean().getVehiculo().getCodItv());
		beanPQTomarItv.setP_ID_VEHICULO(traficoTramiteMatriculacionBean.getTramiteTraficoBean().getVehiculo().getIdVehiculo());
		HashMap <String,Object> resultadoModelo = getModeloMatriculacion().tomarITV(beanPQTomarItv);
		//setTraficoTramiteMatriculacionBean((DatosITV) resultadoModelo.get(ConstantesPQ.BEANPANTALLA));
		DatosITV datosITV=(DatosITV) resultadoModelo.get(ConstantesPQ.BEANPANTALLA);
		traficoTramiteMatriculacionBean.getTramiteTraficoBean().getVehiculo().setNive(datosITV.getNive());
		/*if (traficoTramiteMatriculacionBean.getTramiteTraficoBean().getVehiculo().getBastidor()==null ||
				"".equals(traficoTramiteMatriculacionBean.getTramiteTraficoBean().getVehiculo().getBastidor()))
			traficoTramiteMatriculacionBean.getTramiteTraficoBean().getVehiculo().setBastidor(datosITV.getBastidor());*/
		traficoTramiteMatriculacionBean.getTramiteTraficoBean().getVehiculo().setCo2(datosITV.getCo2());
		traficoTramiteMatriculacionBean.getTramiteTraficoBean().getVehiculo().setCarburanteBean(Combustible.convertirCarburanteBean(datosITV.getCarburante()));
		try {
			traficoTramiteMatriculacionBean.getTramiteTraficoBean().getVehiculo().setCilindrada((new BigDecimal(datosITV.getCilindrada())).toString());
		}catch(Exception e){
			traficoTramiteMatriculacionBean.getTramiteTraficoBean().getVehiculo().setCilindrada(null);
		}
		traficoTramiteMatriculacionBean.getTramiteTraficoBean().getVehiculo().setMarcaBean(new MarcaBean());
		traficoTramiteMatriculacionBean.getTramiteTraficoBean().getVehiculo().getMarcaBean().setMarca(datosITV.getMarca());
		traficoTramiteMatriculacionBean.getTramiteTraficoBean().getVehiculo().getMarcaBean().setCodigoMarca(datosITV.getCodigoMarca());
		try {
			traficoTramiteMatriculacionBean.getTramiteTraficoBean().getVehiculo().setPesoMma((new BigDecimal(datosITV.getMma())).toString());
		}catch(Exception e){
			traficoTramiteMatriculacionBean.getTramiteTraficoBean().getVehiculo().setPesoMma(null);
		}
		traficoTramiteMatriculacionBean.getTramiteTraficoBean().getVehiculo().setModelo(datosITV.getModelo());
		if(null!=datosITV.getPlazas() && ""!=datosITV.getPlazas()){
			traficoTramiteMatriculacionBean.getTramiteTraficoBean().getVehiculo().setPlazas(new BigDecimal(datosITV.getPlazas()));
		}
		traficoTramiteMatriculacionBean.getTramiteTraficoBean().getVehiculo().setPotenciaFiscal(datosITV.getPotenciaFiscal());
		traficoTramiteMatriculacionBean.getTramiteTraficoBean().getVehiculo().setPotenciaNeta(datosITV.getPotenciaReal());
		try{
			traficoTramiteMatriculacionBean.getTramiteTraficoBean().getVehiculo().setTara((new BigDecimal(datosITV.getTara())).toString());
		}catch(Exception e){
			traficoTramiteMatriculacionBean.getTramiteTraficoBean().getVehiculo().setTara(datosITV.getTara());
		}

		if(null!=datosITV.getTipoVehiculoTrafico()){
			//traficoTramiteMatriculacionBean.getTramiteTraficoBean().getVehiculo().setTipoIndustria(datosITV.getTipoVehiculoIndustria());
			traficoTramiteMatriculacionBean.getTramiteTraficoBean().getVehiculo().setTipoVehiculoBean(new TipoVehiculoBean(true));
			traficoTramiteMatriculacionBean.getTramiteTraficoBean().getVehiculo().getTipoVehiculoBean().setTipoVehiculo(datosITV.getTipoVehiculoTrafico().trim());
			
		}
		
		//Nuevos campos MATE 2.5
		traficoTramiteMatriculacionBean.getTramiteTraficoBean().getVehiculo().setTipoIndustria(datosITV.getTipoVehiculoIndustria()!=null?datosITV.getTipoVehiculoIndustria():null);
		traficoTramiteMatriculacionBean.getTramiteTraficoBean().getVehiculo().getCriterioConstruccionBean().setIdCriterioConstruccion(datosITV.getTipoVehiculoIndustria()!=null?datosITV.getTipoVehiculoIndustria().substring(0,2):null);
		traficoTramiteMatriculacionBean.getTramiteTraficoBean().getVehiculo().getCriterioUtilizacionBean().setIdCriterioUtilizacion(datosITV.getTipoVehiculoIndustria()!=null?datosITV.getTipoVehiculoIndustria().substring(2,datosITV.getTipoVehiculoIndustria().length()):null);
		traficoTramiteMatriculacionBean.getTramiteTraficoBean().getVehiculo().setMtmaItv(datosITV.getMma());
		traficoTramiteMatriculacionBean.getTramiteTraficoBean().getVehiculo().setFabricante(datosITV.getFabricante());
		traficoTramiteMatriculacionBean.getTramiteTraficoBean().getVehiculo().setTipoItv(datosITV.getTipo());
		traficoTramiteMatriculacionBean.getTramiteTraficoBean().getVehiculo().setVariante(datosITV.getVariante());
		traficoTramiteMatriculacionBean.getTramiteTraficoBean().getVehiculo().setVersion(datosITV.getVersion());
		traficoTramiteMatriculacionBean.getTramiteTraficoBean().getVehiculo().getHomologacionBean().setIdHomologacion(datosITV.getCatHomologacion());
		traficoTramiteMatriculacionBean.getTramiteTraficoBean().getVehiculo().setConsumo(datosITV.getConsumo());
		traficoTramiteMatriculacionBean.getTramiteTraficoBean().getVehiculo().setMom(datosITV.getMasaMom());
		traficoTramiteMatriculacionBean.getTramiteTraficoBean().getVehiculo().getFabricacionBean().setIdFabricacion(datosITV.getProcedencia());
		traficoTramiteMatriculacionBean.getTramiteTraficoBean().getVehiculo().setDistanciaEntreEjes(datosITV.getDistanciaEjes());
		traficoTramiteMatriculacionBean.getTramiteTraficoBean().getVehiculo().setViaAnterior(datosITV.getViaAnterior());
		traficoTramiteMatriculacionBean.getTramiteTraficoBean().getVehiculo().setViaPosterior(datosITV.getViaPosterior());
		traficoTramiteMatriculacionBean.getTramiteTraficoBean().getVehiculo().getAlimentacionBean().setIdAlimentacion(datosITV.getTipoAlimentacion());
		traficoTramiteMatriculacionBean.getTramiteTraficoBean().getVehiculo().setNumHomologacion(datosITV.getContraseniaHomologacion());
		traficoTramiteMatriculacionBean.getTramiteTraficoBean().getVehiculo().setNivelEmisiones(datosITV.getNivelEmision());
		traficoTramiteMatriculacionBean.getTramiteTraficoBean().getVehiculo().setEcoInnovacion(datosITV.getEcoInnovacion());
		traficoTramiteMatriculacionBean.getTramiteTraficoBean().getVehiculo().setReduccionEco(datosITV.getReduccionEco());
		traficoTramiteMatriculacionBean.getTramiteTraficoBean().getVehiculo().setCodigoEco(datosITV.getCodigoEco());
		if(datosITV.getRelPotenciaPeso()!=null){
			BigDecimal potPeso = new BigDecimal(datosITV.getRelPotenciaPeso());
			traficoTramiteMatriculacionBean.getTramiteTraficoBean().getVehiculo().setPotenciaPeso(potPeso);
		}
		if(datosITV.getNumPlazasPie()!=null){
			BigDecimal numPlazasPie = new BigDecimal(datosITV.getNumPlazasPie());
			traficoTramiteMatriculacionBean.getTramiteTraficoBean().getVehiculo().setNumPlazasPie(numPlazasPie);
		}
		traficoTramiteMatriculacionBean.getTramiteTraficoBean().getVehiculo().getCarroceriaBean().setIdCarroceria(datosITV.getCarroceria());
		
		//TODO MPC. Cambio IVTM.
		ivtmMatriculacionDto = getServicioIVTM().recuperarPorNumExp(traficoTramiteMatriculacionBean.getTramiteTraficoBean().getNumExpediente());

		return  (ResultBean)resultadoModelo.get(ConstantesPQ.RESULTBEAN);
	}

   public String clonar(){
	   log.info("clonar tramite");
	   
	   ResultBean resultBean;
	   try {
		   esAccionClonado = true;
		   resultBean = guardarTramite();
		   if(resultBean.getError()){
			   addActionError("Trámite no guardado.");
			   for (int i=0;i<resultBean.getListaMensajes().size();i++) {
				   addActionError(resultBean.getListaMensajes().get(i)); 
			   }
			   mantenerAcciones();
			   return ALTAS_MATRICULACION_MATW;
		   }
		   setNumsExpediente(traficoTramiteMatriculacionBean.getTramiteTraficoBean().getNumExpediente().toString());
		   setTipoTramiteSeleccionado(TipoTramiteTrafico.Matriculacion.getValorEnum());
		   setEstadoTramiteSeleccionado(traficoTramiteMatriculacionBean.getTramiteTraficoBean().getEstado().getValorEnum());
		   if(traficoTramiteMatriculacionBean.getTramiteTraficoBean() != null && traficoTramiteMatriculacionBean.getTramiteTraficoBean().getVehiculo() != null && 
				   traficoTramiteMatriculacionBean.getTramiteTraficoBean().getVehiculo().getBastidor() != null){
			   setBastidorSeleccionado(traficoTramiteMatriculacionBean.getTramiteTraficoBean().getVehiculo().getBastidor());
		   }
	   } catch (Throwable e) {
		   addActionError("Trámite no guardado, porque ha sucedido un error interno.");
		   log.error("Trámite no guardado. error: " + e.getMessage());
		   try {
			mantenerAcciones();
		   } catch (Throwable e1) {
			   log.error("Trámite no guardado. error: " + e.getMessage());
		   }
		   return ALTAS_MATRICULACION_MATW;
	   }
	   return CLONAR_TRAMITE;
   }

	/**
	 * 
	 * @return
	 * @throws Throwable
	    
	*/
	public String guardar() throws Throwable {
		log.info("guardar"); 
		
		ResultBean resultBean = guardarTramite();
		
		if (resultBean.getError()==false)
		{					
			addActionMessage("Trámite guardado.");
			for (int i=0;i<resultBean.getListaMensajes().size();i++)
				addActionError(resultBean.getListaMensajes().get(i)); 
		} 
		else
		{			
			addActionError("Trámite no guardado.");
			for (int i=0;i<resultBean.getListaMensajes().size();i++) {
				addActionError(resultBean.getListaMensajes().get(i)); 
			}
		}
		mantenerAcciones();
		return ALTAS_MATRICULACION_MATW;		

	}
	
	
	/**
	 * 
	 * @return
	 * @throws Throwable
	    */
	 
	public String tomar()throws Throwable {

		ResultBean resultBean = tomarTramite();  		

		if (resultBean.getError()==true)
		{				
			addActionError(resultBean.getMensaje()); 
			//traficoTramiteMatriculacionBean.getTramiteTraficoBean().setVehiculo(null);
		}
		
		//habilitarBastidor = true;

		mantenerAcciones();
		return ALTAS_MATRICULACION_MATW;
	}
    
     
	
	/**
	 * 
	 * @return
	 * @throws Throwable
	   
	 */
	public String autoliquidarIVTM(){
		//El contenido de esto debería ir en el servicio de matriculación.
		//Control de permisos
		if(!utilesColegiado.tienePermisoAutoliquidarIvtm()){
			addActionError(ConstantesIVTM.TEXTO_IVTM_NO_PERMISO_AUTOLIQUIDACION);
			return ERROR;
		}
		if (!UtilesVistaTrafico.getInstance().esConsultableOGuardableMATW(traficoTramiteMatriculacionBean.getTramiteTraficoBean())){
			addActionError(ConstantesIVTM.TEXTO_IVTM_NO_PERMISO_AUTOLIQUIDACION);
			return ERROR;
		}
		ResultBean rs = null;
		//Comprobamos si ya se ha validado.
		if(traficoTramiteMatriculacionBean.getTramiteTraficoBean()!=null && traficoTramiteMatriculacionBean.getTramiteTraficoBean().getNumExpediente()!=null){
			BigDecimal numExpediente = traficoTramiteMatriculacionBean.getTramiteTraficoBean().getNumExpediente();
			rs= getServicioIVTM().puedeGenerarAutoliquidacion(numExpediente);
			if (rs.getError()){
				for (String error : rs.getListaMensajes()){
					addActionError(error);
				}
				return ALTAS_MATRICULACION_MATW;
			}
		}
		//Ajustamos la tara
		getModeloMatriculacion().ajustarTara(traficoTramiteMatriculacionBean);
		//Guardamos el trámite de tráfico
		try {
			rs = guardarTramite();
		} catch (Throwable e1) {
			rs = new ResultBean();
			rs.setError(true);
			rs.addMensajeALista("Existen errores al guardar");
		}
		if (rs.getError()){
			addActionError(ConstantesIVTM.TEXTO_IVTM_TRAMITE_NO_GUARDADO);
			for (String error : rs.getListaMensajes()){
				addActionError(error);
			}
			return ALTAS_MATRICULACION_MATW;
		} else if (traficoTramiteMatriculacionBean == null || traficoTramiteMatriculacionBean.getTramiteTraficoBean()==null || traficoTramiteMatriculacionBean.getTramiteTraficoBean().getNumExpediente()==null){
			addActionError(ConstantesIVTM.TEXTO_IVTM_TRAMITE_NO_GUARDADO);
			return ALTAS_MATRICULACION_MATW;
		}
		if (ivtmMatriculacionDto==null){
			addActionError(ConstantesIVTM.TEXTO_IVTM_ERROR_NO_LUQIDACION);
		} else {
			rs = getServicioIVTM().validarTramite(traficoTramiteMatriculacionBean.getTramiteTraficoBean().getNumExpediente());
			if (rs.getError()){
				ivtmMatriculacionDto.setEstadoIvtm(new BigDecimal(EstadoIVTM.Iniciado.getValorEnum()));
				ivtmMatriculacionDto.setNumExpediente(traficoTramiteMatriculacionBean.getTramiteTraficoBean().getNumExpediente());
				try{
					ivtmMatriculacionDto = getServicioIVTM().guardarIVTM(ivtmMatriculacionDto);
					if(ivtmMatriculacionDto==null){
						addActionError(ConstantesIVTM.TEXTO_IVTM_IVTM_NO_GUARDADO);
					}
				}catch(Exception e){
					addActionError(ConstantesIVTM.TEXTO_IVTM_ERROR_GUARDAR_IVTM);
				}
				for(String error : rs.getListaMensajes()){
					addActionError(error);
				}
				addActionError(ConstantesIVTM.TEXTO_IVTM_ERROR_NO_LUQIDACION);
			} else {	
				//meter el bastidor y el gestor		
				ivtmMatriculacionDto.setFechaReq(new Fecha(new SimpleDateFormat(ConstantesIVTM.FORMATO_FECHA_GUARDAR_IVTM).format(new Date())));
				ivtmMatriculacionDto.setEstadoIvtm(new BigDecimal(EstadoIVTM.Pendiente_Respuesta_Ayto.getValorEnum()));
				ivtmMatriculacionDto.setNumColegiado(traficoTramiteMatriculacionBean.getTramiteTraficoBean().getNumColegiado());
				ivtmMatriculacionDto.setBastidor(traficoTramiteMatriculacionBean.getTramiteTraficoBean().getVehiculo().getBastidor());
				if(traficoTramiteMatriculacionBean.getTramiteTraficoBean().getNumColegiado()!=null && !traficoTramiteMatriculacionBean.getTramiteTraficoBean().getNumColegiado().equals("")){
					ivtmMatriculacionDto.setIdPeticion(getServicioIVTM().generarIdPeticion(traficoTramiteMatriculacionBean.getTramiteTraficoBean().getNumColegiado()));
				}else{
					ivtmMatriculacionDto.setIdPeticion(getServicioIVTM().generarIdPeticion(utilesColegiado.getNumColegiadoSession()));
				}
				ivtmMatriculacionDto.setNumExpediente(traficoTramiteMatriculacionBean.getTramiteTraficoBean().getNumExpediente());
				ivtmMatriculacionDto = getServicioIVTM().guardarIVTM(ivtmMatriculacionDto);
				try{
					if(ivtmMatriculacionDto==null){
						addActionError(ConstantesIVTM.TEXTO_IVTM_IVTM_NO_GUARDADO);
					} else {
						addActionMessage(ConstantesIVTM.TEXTO_IVTM_VALIDACION_CORRECTA);
						if (modeloTramite.cambiarEstado(traficoTramiteMatriculacionBean.getTramiteTraficoBean().getNumExpediente(),EstadoTramiteTrafico.Pendiente_Respuesta_IVTM)){
							traficoTramiteMatriculacionBean.getTramiteTraficoBean().setEstado(EstadoTramiteTrafico.Pendiente_Respuesta_IVTM.getValorEnum());
							try{
								//Se encola la peticion de IVTM
								if(getServicioIVTM().solicitarIVTM(traficoTramiteMatriculacionBean.getTramiteTraficoBean().getNumExpediente())){
									addActionMessage(ConstantesIVTM.TEXTO_IVTM_ENCOLADO_ALTA_CORRECTO);
								} else {
									addActionError(ConstantesIVTM.TEXTO_IVTM_ENCOLADO_ALTA_ERROR);
									modeloTramite.cambiarEstado (traficoTramiteMatriculacionBean.getTramiteTraficoBean().getNumExpediente(),EstadoTramiteTrafico.Iniciado);
									traficoTramiteMatriculacionBean.getTramiteTraficoBean().setEstado(EstadoTramiteTrafico.Iniciado.getValorEnum());
									ivtmMatriculacionDto.setEstadoIvtm(new BigDecimal(EstadoIVTM.Iniciado.getValorEnum()));
									ivtmMatriculacionDto = getServicioIVTM().guardarIVTM(ivtmMatriculacionDto);
								}	 
							} catch (Exception e) {
								addActionError(ConstantesIVTM.TEXTO_IVTM_ENCOLADO_ALTA_ERROR);
								modeloTramite.cambiarEstado (traficoTramiteMatriculacionBean.getTramiteTraficoBean().getNumExpediente(),EstadoTramiteTrafico.Iniciado);
								traficoTramiteMatriculacionBean.getTramiteTraficoBean().setEstado(EstadoTramiteTrafico.Iniciado.getValorEnum());
								ivtmMatriculacionDto.setEstadoIvtm(new BigDecimal(EstadoIVTM.Iniciado.getValorEnum()));
								ivtmMatriculacionDto = getServicioIVTM().guardarIVTM(ivtmMatriculacionDto);
							}
						}
					}
				}catch(Exception e){
					addActionError(ConstantesIVTM.TEXTO_IVTM_ERROR_GUARDAR_IVTM);
					addActionError(ConstantesIVTM.TEXTO_IVTM_ERROR_GUARDADO_NO_ALTA_IVTM);
				}
			}
		}		
		
		return ALTAS_MATRICULACION_MATW;
	}
	
	//TODO MPC. Cambio IVTM. Método nuevo
	public String pagoIVTM() throws ParseException {
		if (!utilesColegiado.tienePermisoAutoliquidarIvtm()) {
			addActionError(ConstantesIVTM.TEXTO_IVTM_NO_PERMISO_AUTOLIQUIDACION);
			return ERROR;
		}
		if (!UtilesVistaTrafico.getInstance().esConsultableOGuardableMATW(traficoTramiteMatriculacionBean.getTramiteTraficoBean())) {
			addActionError(ConstantesIVTM.TEXTO_IVTM_NO_PERMISO_AUTOLIQUIDACION);
			return ERROR;
		}
		ResultBean rs = null;
		try {
			rs = guardarTramite();
		} catch (Throwable e1) {
			rs = new ResultBean();
			rs.setError(true);
			rs.addMensajeALista("Existen errores al guardar");
		}
		if (rs.getError()) {
			addActionError(ConstantesIVTM.TEXTO_IVTM_TRAMITE_NO_GUARDADO);
			for (String error : rs.getListaMensajes()) {
				addActionError(error);
			}
			return ALTAS_MATRICULACION_MATW;
		} else if (traficoTramiteMatriculacionBean == null || traficoTramiteMatriculacionBean.getTramiteTraficoBean() == null
				|| traficoTramiteMatriculacionBean.getTramiteTraficoBean().getNumExpediente() == null) {
			addActionError(ConstantesIVTM.TEXTO_IVTM_TRAMITE_NO_GUARDADO);
			return ALTAS_MATRICULACION_MATW;
		}

		if (ivtmMatriculacionDto == null) {
			addActionError(gestorPropiedades.valorPropertie(ConstantesIVTM.TEXTO_IVTM_ERROR_NO_PAGO));
		} else {
			rs = getServicioIVTM().validarTramitePago(traficoTramiteMatriculacionBean.getTramiteTraficoBean().getNumExpediente());
		}
		if (rs.getError()) {
			for (String error : rs.getListaMensajes()) {
				addActionError(error);
			}
			addActionError(ConstantesIVTM.TEXTO_IVTM_ERROR_NO_PAGO);
			return ALTAS_MATRICULACION_MATW;
		} else {
			url = getServicioIVTM().crearUrl(false, ivtmMatriculacionDto, traficoTramiteMatriculacionBean);
		}

		return PAGO_IVTM;
	}

 
	/**
	 * 
	 * @return
	 * @throws Throwable
	 */
	public String validarMatriculacion() throws Throwable{
		log.info("Matriculacion Tramite Trafico: inicio--validarMatriculacion:");		 
		ResultBean resultBean = guardarTramite();
		

		if (resultBean.getError()==false) {			
			ResultBean resultadoValidacion = getModeloMatriculacion().validar(traficoTramiteMatriculacionBean);
			//Si hay errores pero ha sido validado pdf

			if (resultadoValidacion.getError() && EstadoTramiteTrafico.Validado_PDF.getValorEnum().equals(resultadoValidacion.getMensaje())) {
				addActionMessage("Resultado en la validación: " + EstadoTramiteTrafico.convertirTexto(resultadoValidacion.getMensaje()));
				for (int i=0;i<resultadoValidacion.getListaMensajes().size();i++) {
					String mensaje = resultadoValidacion.getListaMensajes().get(i);
					mensaje = mensaje.replace("--", " - ");
					// 15-10-2012. Ricardo Rodriguez. Incidencia : 0002589
					if(mensaje.contains("AVISO") || mensaje.contains("Aviso")){
						addActionMessage(mensaje);
					}else{
						addActionError(mensaje);
					}
					// addActionError(mensaje);
					// FIN 15-10-2012. Ricardo Rodriguez. Incidencia : 0002589
				}
			}
			//Si hay errores y no ha sido validado
			else if (resultadoValidacion.getError() &&
					!(EstadoTramiteTrafico.Validado_Telematicamente.getValorEnum().equals(resultadoValidacion.getMensaje()) 
							|| EstadoTramiteTrafico.Validado_PDF.getValorEnum().equals(resultadoValidacion.getMensaje()))) {
				addActionError("Error en la validación:");
				for (int i=0;i<resultadoValidacion.getListaMensajes().size();i++) {
					String mensaje = resultadoValidacion.getListaMensajes().get(i);
					mensaje = mensaje.replace("--", " - ");
					// 15-10-2012. Ricardo Rodriguez. Incidencia : 0002589
					if(mensaje.contains("AVISO") || mensaje.contains("Aviso")){
						addActionMessage(mensaje);
					}else{
						addActionError(mensaje);
					}
					// addActionError(mensaje);
					// FIN 15-10-2012. Ricardo Rodriguez. Incidencia : 0002589
				}
			} else {
				addActionMessage("Validación correcta");
				//MANTIS.-  PARA AGREGAR EL MENSAJE INFORMATIVO DE LOS 26 CARACTERES
				if(resultadoValidacion.getListaMensajes() != null){
					for (String mensaje : resultadoValidacion.getListaMensajes()) {
						//
						 // SCL. Mantis 0004189. No mostraba mensajes de código ITV inválido.
						 // Por alguna razón había un condicional concreto para ciertos mensajes controlados, y se estaban descartando el resto.
						 // Elimino la restricción para que se muestren todos. No deberían mostrarse ORA's u otros mensajes puesto que getError es false en este punto
						 // y la validación correcta. También elimino código basura comentado.
						 //
						mensaje = mensaje.replace("--", " - ");
						addActionMessage(mensaje);
					}
				}
			}
			
			if(EstadoTramiteTrafico.Validado_PDF.getValorEnum().equals(resultadoValidacion.getMensaje())){
				//Generamos XML de Matriculacion
				ResultBean resultadoJaxb = getModeloMatriculacion().generaXMLMatriculacionNuevo(traficoTramiteMatriculacionBean);
				//Mensajes de error en la validacion JAXB
				if(resultadoJaxb.getError()){
					if(resultadoJaxb.getListaMensajes()!=null){
						for(String mensaje : resultadoJaxb.getListaMensajes()){
							addActionError(mensaje);
						}
					}
				}
			}
		} else {
			if (resultBean.getError()){
				if(resultBean.getListaMensajes()!= null && !resultBean.getListaMensajes().isEmpty()) {
					for(String s: resultBean.getListaMensajes()) {
						addActionError(s);
					}
				} else if ( resultBean.getMensaje()!= null && !resultBean.getMensaje().trim().isEmpty()) {
					addActionError(resultBean.getMensaje());
				}
			}
		}

		mantenerAcciones();
		return ALTAS_MATRICULACION_MATW;
	}
	
	
	
	/**
	 * Realiza la llamada a BSTI para obtener la matrícula a partir del bastidor
	 * @return
	 */
	public String obtenerMatricula(){
		Bsti bsti = new Bsti();
		BstiBean bean = null; 
		
		try {
			mantenerAcciones();
		} catch (Throwable e1) {
			addActionError(e1.toString());
		}
		if(traficoTramiteMatriculacionBean.getTramiteTraficoBean().getVehiculo().getBastidor()!=null &&
				!"".equals(traficoTramiteMatriculacionBean.getTramiteTraficoBean().getVehiculo().getBastidor())){
			bean = bsti.obtenerMatricula(traficoTramiteMatriculacionBean.getTramiteTraficoBean().getVehiculo().getBastidor(), traficoTramiteMatriculacionBean.getTramiteTraficoBean().getNumExpediente(), utilesColegiado.getIdUsuarioSessionBigDecimal(), utilesColegiado.getNumColegiadoSession(), traficoTramiteMatriculacionBean.getTramiteTraficoBean().getVehiculo().getIdVehiculo().longValue(),
					OrigenTGate.ObtenerMatricula.getValorEnum());
			
			if(!bean.getError()){
				String matricula = bean.getMatricula();
				
				//Realizamos la llamada al paquete matricular para añadir la matrícula al detalle del vehículo
				BeanPQMatricular beanPQMatricular = new BeanPQMatricular(); 
				beanPQMatricular.setP_NUM_EXPEDIENTE(traficoTramiteMatriculacionBean.getTramiteTraficoBean().getNumExpediente());	
				beanPQMatricular.setP_MATRICULA(matricula);
				try{
					beanPQMatricular.setP_FECHA_MATRICULACION(traficoTramiteMatriculacionBean.getTramiteTraficoBean().getVehiculo().getFechaMatriculacion()!=null && 
						!"".equals(traficoTramiteMatriculacionBean.getTramiteTraficoBean().getVehiculo().getFechaMatriculacion())?
								traficoTramiteMatriculacionBean.getTramiteTraficoBean().getVehiculo().getFechaMatriculacion().getTimestamp():
									traficoTramiteMatriculacionBean.getTramiteTraficoBean().getFechaPresentacion().getTimestamp()); 
					beanPQMatricular.setP_FECHA_PRESENTACION(traficoTramiteMatriculacionBean.getTramiteTraficoBean().getFechaPresentacion().getTimestamp());
				} catch (ParseException e){
					addActionError("Error al añadir la matrícula al vehículo. No se le ha descontado ningún credito.");
					return ALTAS_MATRICULACION_MATW;
				}
				beanPQMatricular.setP_ESTADO(new BigDecimal(traficoTramiteMatriculacionBean.getTramiteTraficoBean().getEstado().getValorEnum())); 
				
				ResultBean resultBean = getModeloMatriculacion().matricular(beanPQMatricular);
				
				if(!resultBean.getError()){
					traficoTramiteMatriculacionBean.getTramiteTraficoBean().getVehiculo().setMatricula(matricula);
					traficoTramiteMatriculacionBean.getTramiteTraficoBean().getVehiculo().setFechaMatriculacion(traficoTramiteMatriculacionBean.getTramiteTraficoBean().getVehiculo().getFechaMatriculacion()!=null && 
							!"".equals(traficoTramiteMatriculacionBean.getTramiteTraficoBean().getVehiculo().getFechaMatriculacion())?
									traficoTramiteMatriculacionBean.getTramiteTraficoBean().getVehiculo().getFechaMatriculacion():
										traficoTramiteMatriculacionBean.getTramiteTraficoBean().getFechaPresentacion());
					addActionMessage("Matrícula obtenida con éxito y actualizada en el detalle del vehículo.");
				} else {
					addActionError(resultBean.getMensaje());
				}
			} else {
				addActionError(bean.getMensaje());
			}
			
			if(bean!=null && bean.getError().booleanValue() &&
					!(bean.getMensaje().equals("Error en la invocación")
							|| bean.getMensaje().equals("Se ha producido un error en la invocación al servicio de la D.G.T.")
							|| bean.getMensaje().equals("Error en los parámetros.")
							|| bean.isDesactivadaConsulta())){
				
				//Hacer la actualizacion de créditos incrementales
				HashMap<String, Object> resultado = getModeloCreditosTrafico().descontarCreditos(
						utilesColegiado.getIdContratoSessionBigDecimal().toString(),
						utiles.convertirIntegerABigDecimal(1),
						"T5",utilesColegiado.getIdUsuarioSessionBigDecimal());
				
				ResultBean resultadoProcedimiento =(ResultBean) resultado.get(ConstantesPQ.RESULTBEAN);
				
				if(resultadoProcedimiento.getError()){
					addActionError("Error al descontar créditos de la operación");
				} else {
					try {
						servicioCreditoFacturado.anotarGasto(1, ConceptoCreditoFacturado.GOM, utilesColegiado.getIdContratoSession(), "T5", traficoTramiteMatriculacionBean.getTramiteTraficoBean().getNumExpediente().toString());
					} catch(Exception e) {
						log.error("No se pudo trazar el gasto de creditos", e);
					}
				}
			}
		} else {
				addActionError("Debe rellenar el bastidor del vehículo para realizar la consulta BSTI. No se le ha descontado ningún credito.");
				}
		
		return ALTAS_MATRICULACION_MATW;
	}
	
	
	/**
	 * Presentación del modelo 576 desde el alta de trámites de matriculación
	 * @return cadena para el dispatcher de struts
	 */
	 
	public String presentarAEAT(){

		try{
			ResultBean resultBeanGuardar = guardarTramite();  		
			if (resultBeanGuardar.getError()==false)
			{					
				addActionMessage("Trámite guardado.");
				for (int i=0;i<resultBeanGuardar.getListaMensajes().size();i++)
					addActionError(resultBeanGuardar.getListaMensajes().get(i)); 
			} 
			else
			{			
				addActionError("Trámite no guardado.");
				for (int i=0;i<resultBeanGuardar.getListaMensajes().size();i++) {
					addActionError(resultBeanGuardar.getListaMensajes().get(i)); 
				}
			} 

			ResultBean resultadoPresentacion = new ResultBean();
			byte[] respuestaHtml = null;
			//TramiteTraficoMatriculacionBean tramite = new TramiteTraficoMatriculacionBean();

			HashMap<String, Object> resultado = getModeloMatriculacion().obtenerDetalle(traficoTramiteMatriculacionBean.getTramiteTraficoBean().getNumExpediente(),
					utilesColegiado.getNumColegiadoSession(),utilesColegiado.getIdContratoSessionBigDecimal());
			ResultBean resultBean = (ResultBean)resultado.get("ResultBean");
			if(resultBean.getError()){
				if(resultBean.getMensaje().contains("No existe el trámite")){
					addActionError("Guarde el trámite antes de presentar el impuesto 576 en la AEAT");
				}else{
					addActionError(resultBean.getMensaje());
				}
				return ALTAS_MATRICULACION_MATW;
			}
			//tramite = (TramiteTraficoMatriculacionBean) resultado.get(ConstantesPQ.BEANPANTALLA);
			traficoTramiteMatriculacionBean = (TramiteTraficoMatriculacionBean) resultado.get(ConstantesPQ.BEANPANTALLA);

			//resultsTramitacion = getModeloPresentacionAEAT().tramitar576(tramite);
			
			// Mantis 19403
			String alias;
			if (traficoTramiteMatriculacionBean.getTramiteTraficoBean() != null && traficoTramiteMatriculacionBean.getTramiteTraficoBean().getNumColegiado() != null
				&& !traficoTramiteMatriculacionBean.getTramiteTraficoBean().getNumColegiado().isEmpty()) {
				alias = utilesColegiado.getAlias(traficoTramiteMatriculacionBean.getTramiteTraficoBean().getNumColegiado());
			} else {
				alias = utilesColegiado.getAlias();
			}
			Map<String,Object> resultsTramitacion = getModeloPresentacionAEAT().tramitar576(traficoTramiteMatriculacionBean, alias);
			boolean errores = false;
			@SuppressWarnings("unchecked")
			ArrayList<String> datosRequeridos = (ArrayList<String>)resultsTramitacion.get("datosRequeridos");
			if(datosRequeridos != null && datosRequeridos.size() > 0){
				addActionError("Faltan los siguientes datos requeridos para la presentación del 576:");
				String faltanDatos = "";
				for(int i = 0; i < datosRequeridos.size(); i ++){
					if(i == 0){
						faltanDatos += datosRequeridos.get(i);
					}else{
						faltanDatos += ", " + datosRequeridos.get(i);
					}
				}
				addActionError(faltanDatos);
				errores = true;
			}
			@SuppressWarnings("unchecked")
			ArrayList<String> erroresFormato = (ArrayList<String>)resultsTramitacion.get("erroresFormato");
			if(erroresFormato != null && erroresFormato.size() > 0){
				addActionError("Errores de formato que bloquean la presentación del 576:");
				for(int i = 0; i < erroresFormato.size(); i ++){
					addActionError(erroresFormato.get(i));
				}
				errores = true;
			}
			// Detiene en caso de que haya error por falta de datos requeridos y/o errores de formato:
			if(errores){
				return ALTAS_MATRICULACION_MATW;
			}
			resultadoPresentacion = (ResultBean) resultsTramitacion.get("resultadoPresentacion");
			if(resultadoPresentacion == null || resultadoPresentacion.getError()){
				addActionError("Errores en el proceso de presentación en la AEAT");
				if(resultadoPresentacion.getMensaje() != null && !resultadoPresentacion.getMensaje().equals("")){
					String erroresPresentacion = resultadoPresentacion.getMensaje();
					String[] arrayErroresPresentacion = erroresPresentacion.split(ConstantesAEAT.DELIMITADOR_ERRORES);
					for(String actionError : arrayErroresPresentacion){
						if(!actionError.equals("")){
							addActionError(actionError);
						}
					}
				}else if(resultadoPresentacion.getListaMensajes() != null && resultadoPresentacion.getListaMensajes().size() > 0){
					for(String mensajeError : resultadoPresentacion.getListaMensajes()){
						if(mensajeError != null && !mensajeError.equals("")){
							String[] arrayErroresPresentacion = mensajeError.split(ConstantesAEAT.DELIMITADOR_ERRORES);
							for(String actionError : arrayErroresPresentacion){
								if(actionError != null && !actionError.equals("")){
									addActionError(actionError);
								}
							}
						}
					}
				}
				return ALTAS_MATRICULACION_MATW;
			}
			else{
				addActionMessage("La presentación en la AEAT se ha realizado correctamente.");
				if(null!=resultadoPresentacion.getAttachment(ConstantesSession.RESPUESTA576)){
					respuestaHtml = (byte[]) resultadoPresentacion.getAttachment(ConstantesSession.RESPUESTA576);
					// Extrae de la respuesta el CEM:
					String respuestaCorrecta = resultadoPresentacion.getMensaje();
					if(respuestaCorrecta.indexOf(ConstantesAEAT.VARIABLE_CEM_EN_HTML) != -1){
						String subCadenaVarCem = respuestaCorrecta.substring(respuestaCorrecta.indexOf(ConstantesAEAT.VARIABLE_CEM_EN_HTML));
						subCadenaVarCem = subCadenaVarCem.substring(ConstantesAEAT.VARIABLE_CEM_EN_HTML.length(),
								subCadenaVarCem.indexOf(";"));
						subCadenaVarCem = subCadenaVarCem.replaceAll("\"", "");
						subCadenaVarCem = subCadenaVarCem.trim();
						if(!subCadenaVarCem.equals("")){
							traficoTramiteMatriculacionBean.getTramiteTraficoBean().setCemIedtm(subCadenaVarCem);
							guardarTramite();  
							addActionMessage("Se ha actualizado el trámite con el CEM recibido");
						}
					}
					
				}
			}

			if(null!=respuestaHtml){
				pdf576 = true;
				ficheroDeclaracion576 = false;
				getSession().put(ConstantesSession.FICHERO_RESPUESTA, respuestaHtml);
			}
			
			return ALTAS_MATRICULACION_MATW;

		}catch(Throwable ex){
			log.error(ex);
			addActionError(ex.toString());
			return Action.ERROR;
		}
	}
	
	
//	public static void main(String[] args){
//		byte[] array;
//		try {
//			array = utiles.getBytesFromFile(new File("C:/datos/respuesta_sin_errores.html"));
//			String respuestaHtml = new String(array);
//			// Extrae de la respuesta el CEM:
//			String respuestaCorrecta = new String(respuestaHtml);
//			if(respuestaCorrecta.indexOf(ConstantesAEAT.VARIABLE_CEM_EN_HTML) != -1){
//				String subCadenaVarCem = respuestaCorrecta.substring(respuestaCorrecta.indexOf(ConstantesAEAT.VARIABLE_CEM_EN_HTML));
//				subCadenaVarCem = subCadenaVarCem.substring(ConstantesAEAT.VARIABLE_CEM_EN_HTML.length(),
//						subCadenaVarCem.indexOf(";"));
//				subCadenaVarCem = subCadenaVarCem.replaceAll("\"", "");
//				subCadenaVarCem = subCadenaVarCem.trim();
//				if(!subCadenaVarCem.equals("")){
//					/*traficoTramiteMatriculacionBean.getTramiteTraficoBean().setCemIedtm(subCadenaVarCem);
//					guardarTramite();  
//					addActionMessage("Se ha actualizado el trámite con el CEM recibido");*/
//				}
//			}
//		} catch (IOException e) {
//			log.error("Error inesperado", e);
//		}
//		
//	}
	
	
	/**
	 * Método que exportará el fichero del impuesto 576
	 * @return

	 */
	public String exportarFichero576(){
		
		log.debug("Inicio: Exportando el fichero de 576");
		String donde= "descargarFichero";
		ResultBean resultadoImprimirAction = (ResultBean)
			getSession().get(ConstantesSession.FICHERO576);	
		
		try {
			mantenerAcciones();
		} catch (Throwable e) {
			addActionError(e.toString());
		}
		if(null==resultadoImprimirAction ||
				resultadoImprimirAction.getError()){
			addActionError("Se ha producido un error al obtener el fichero generado.");
			return ALTAS_MATRICULACION_MATW;
		}
		//Si viene de imprimir
		byte[] paraImprimir = (byte[]) resultadoImprimirAction.getAttachment(ConstantesSession.FICHERO576);
		
		if(null==paraImprimir){
			addActionError("Se ha producido un error al obtener el fichero generado.");
			return ALTAS_MATRICULACION_MATW;
		}
		
		ByteArrayInputStream stream = new ByteArrayInputStream(paraImprimir);
		
		setInputStream(stream);
	
		String nombreFichero = (String) resultadoImprimirAction.getAttachment(ConstantesSession.NOMBRE_FICH);
	
		setFicheroDeclaracion576(false);
	
		setFileName(nombreFichero + ConstantesPDF.EXTENSION_TXT);
	
		log.debug("Exportando el fichero de 576");
		getSession().remove(ConstantesSession.FICHERO576);
		return donde;
	}

	/**
	 * Método que exportará la respuesta de la AEAT para el 576
	 * @return
	 */
	public String exportarRespuesta576(){
		
		log.debug("Inicio: Obtiene Respuesta de 576");
		String donde= "descargarRespuesta";
		byte[] paraImprimir = (byte[]) getSession().get(ConstantesSession.FICHERO_RESPUESTA);	

		if(null==paraImprimir){
			addActionError("Se ha producido un error al obtener la Respuesta Generada.");
			return ALTAS_MATRICULACION_MATW;
		}
		
		ByteArrayInputStream stream = new ByteArrayInputStream(paraImprimir);
		
		setInputStream(stream);
	
		String nombreFichero = ConstantesAEAT.NOMBRE_FICHERO_RESPUESTA;
		
		setPdf576(false);
	

		setFileName(nombreFichero + ConstantesPDF.EXTENSION_PDF);
	
		log.debug("Exportando el fichero de 576");
		getSession().remove(ConstantesSession.FICHERO_RESPUESTA);
		return donde;
	}

	private String getTipoFirma() {
		return gestorPropiedades.valorPropertie(ConstantesFirma.TIPO_FIRMA_MATRICULACION);
	}
	
	public String getDataEitv(){
		try {
			ResultBean resultado = servicioConsultaEitv.consultaEitv(getTraficoTramiteMatriculacionBean().getTramiteTraficoBean().getNumExpediente(),
					utilesColegiado.getIdUsuarioSessionBigDecimal(), utilesColegiado.getIdContratoSessionBigDecimal());
			if(resultado != null && resultado.getError()){
				for(String mensaje : resultado.getListaMensajes()){
					addActionError(mensaje);
				}
			}else{
				Map<String, Object> resultadoMetodo = getModeloMatriculacion().obtenerDetalle(getTraficoTramiteMatriculacionBean().getTramiteTraficoBean().getNumExpediente(), 
						utilesColegiado.getNumColegiadoSession(), utilesColegiado.getIdContratoSessionBigDecimal());
				traficoTramiteMatriculacionBean = (TramiteTraficoMatriculacionBean) resultadoMetodo.get(ConstantesPQ.BEANPANTALLA);
				addActionMessage("Solicitud de Consulta de Tarjeta EITV realizada con éxito."); 
			}
			mantenerAcciones();
		} catch (Exception e) {
			log.error("Ha surgido un error a la hora de crear la solicutd de consulta de tarjeta EITV, error: ",e);
			addActionError("Ha ocurrido un error interno tramitando la solicitud"); 
		} catch (Throwable e) {
			log.error("Ha surgido un error a la hora de crear la solicutd de consulta de tarjeta EITV, error: ",e);
			addActionError("Ha ocurrido un error interno tramitando la solicitud"); 
		}
		return ALTAS_MATRICULACION_MATW;	
	}
	
	

	public String matricularTelematicamente() throws Throwable{		

		if (traficoTramiteMatriculacionBean.getTramiteTraficoBean().getNumExpediente()==null)
		{
			addActionError("No puede estar vacio el numero de expediente"); 
			mantenerAcciones();
			return ALTAS_MATRICULACION_MATW;					
		}

		/* Comprobaciones y actualizaciones de la Liberacion EEFF */
		if (utilesColegiado.tienePermisoLiberarEEFF()){
			if (traficoTramiteMatriculacionBean != null && traficoTramiteMatriculacionBean.getTramiteTraficoBean()!= null && traficoTramiteMatriculacionBean.getTramiteTraficoBean().getVehiculo()!= null && traficoTramiteMatriculacionBean.getTramiteTraficoBean().getVehiculo().getNive()!=null && !"".equals(traficoTramiteMatriculacionBean.getTramiteTraficoBean().getVehiculo().getNive())){
				EeffLiberacionDTO eeffLiberacionDTO = (EeffLiberacionDTO) servicioEEFF.recuperarDatosLiberacion(traficoTramiteMatriculacionBean.getTramiteTraficoBean().getNumExpediente());
				if (eeffLiberacionDTO== null) {
					if (traficoTramiteMatriculacionBean.getEeffLiberacionDTO().isExento()) {
						servicioEEFF.guardarDatosLiberacion(traficoTramiteMatriculacionBean.getEeffLiberacionDTO(), traficoTramiteMatriculacionBean.getTramiteTraficoBean().getNumExpediente(), traficoTramiteMatriculacionBean.getTramiteTraficoBean().getNumColegiado());
					} else {
						addActionError(ConstantesEEFF.EEFF_TEXTO_NO_LIBERADO_MATRICULAR);
						mantenerAcciones();
						return ALTAS_MATRICULACION_MATW;
					}
				} else if (! eeffLiberacionDTO.isRealizado()) {
					if (traficoTramiteMatriculacionBean.getEeffLiberacionDTO().isExento()) {
						eeffLiberacionDTO.setExento(true);
						eeffLiberacionDTO = servicioEEFF.actualizarDatosLiberacion(eeffLiberacionDTO);
					} else {
						addActionError(ConstantesEEFF.EEFF_TEXTO_NO_LIBERADO_MATRICULAR);
						mantenerAcciones();
						return ALTAS_MATRICULACION_MATW;
					}
				}
			}
					
		}
		/* Fin de comprobaciones y actualizaciones de la liberación EEFF */
		
		// -- Inicio comprobación de datos sensibles
		try {
			String[] numExpedientes = new String[]{traficoTramiteMatriculacionBean.getTramiteTraficoBean().getNumExpediente().toString()};
			List<String> expedientesSensibles = null;
			String datosSensiblesAnt =  gestorPropiedades.valorPropertie(antiguoDatosSensibles);
			if("true".equals(datosSensiblesAnt)){
				expedientesSensibles = new ComprobadorDatosSensibles().isSensibleData(numExpedientes);
			}else{
				expedientesSensibles = servicioDatosSensibles.existenDatosSensiblesPorExpedientes(utiles.convertirStringArrayToBigDecimal(numExpedientes), utilesColegiado.getIdUsuarioSessionBigDecimal());
			}
			if(expedientesSensibles != null && !expedientesSensibles.isEmpty()){
				for (int j = 0 ;j< expedientesSensibles.size(); j++){ 
						addActionError("Se ha recibido un error técnico. No intenten presentar el tramite. Les rogamos comuniquen con el Colegio.");
						log.error(Claves.CLAVE_LOG_TRAFICO_TRAMITE_CONSULTA+"Datos sensibles en el trámite " + expedientesSensibles.get(j));		 
				}
				mantenerAcciones();
				return ALTAS_MATRICULACION_MATW;	
			}
		} catch (Exception e) {}
		// -- Fin de comprobación de datos sensibles
		ResultBean resultBean = new ResultBean(); 

		HashMap<String,Object> tramiteDetalle = getModeloMatriculacion().obtenerDetalleConDescripciones(traficoTramiteMatriculacionBean.getTramiteTraficoBean().getNumExpediente(),utilesColegiado.getNumColegiadoSession(),utilesColegiado.getIdContratoSessionBigDecimal());
		//Recogemos los valores del modelo
		ResultBean resultadoDetalle = (ResultBean) tramiteDetalle.get(ConstantesPQ.RESULTBEAN);		
		TramiteTraficoMatriculacionBean tramite = (TramiteTraficoMatriculacionBean)tramiteDetalle.get(ConstantesPQ.BEANPANTALLA);

		if(resultadoDetalle.getError()){
			addActionError("Error al obtener el trámite " + traficoTramiteMatriculacionBean.getTramiteTraficoBean().getNumExpediente() + ": " + resultadoDetalle.getMensaje()); 
			log.error(Claves.CLAVE_LOG_TRAFICO_TRAMITE_CONSULTA+"Error al obtener el trámite " + traficoTramiteMatriculacionBean.getTramiteTraficoBean().getNumExpediente() + ": " + resultadoDetalle.getMensaje());
		}
		
		if (getTipoFirma().equals(ConstantesFirma.TIPO_FIRMA_HSM))
			{
			resultBean = getModeloMatriculacion().matriculacionTelematicaHsm(tramite,utilesColegiado.getIdUsuarioSessionBigDecimal(),utilesColegiado.getIdContratoSessionBigDecimal());
			}
		
			//tratamos el resultado de la tramitación.
			
			if (resultBean.getError()==false)
				{
				addActionMessage(resultBean.getMensaje());
				/* INICIO MANTIS 0013245: actualizamos el Bean de pantalla con los datos del trámite */
				actualizarDatosTramiteMatriculacionBean(tramite);
				/* FIN MANTIS 0013245 */
				}		
			else
				{			
				addActionError("Error en la tramitación telemática:" + resultBean.getMensaje());
				}		
		
		mantenerAcciones();
		return ALTAS_MATRICULACION_MATW; 


	}


	/**
	 * Método que actualiza el Bean de pantalla con los datos del trámite
	 * 
	 * @param tramite
	 */
	private void actualizarDatosTramiteMatriculacionBean(TramiteTraficoMatriculacionBean tramite) {
		
		traficoTramiteMatriculacionBean.getTramiteTraficoBean().setEstado(tramite.getTramiteTraficoBean().getEstado().getValorEnum());
		traficoTramiteMatriculacionBean.getTramiteTraficoBean().setFechaUltModif(tramite.getTramiteTraficoBean().getFechaUltModif());
		traficoTramiteMatriculacionBean.getTramiteTraficoBean().getVehiculo().setFechaMatriculacion(tramite.getTramiteTraficoBean().getVehiculo().getFechaMatriculacion());
		
		// Datos del presentador (no varían, pero si no se informan se pierden)
		presentadorSoloAdmin = new IntervinienteTrafico();
		ModeloColegiado modeloColegiado = new ModeloColegiado();
		presentadorSoloAdmin.setPersona(modeloColegiado.obtenerColegiadoCompleto(traficoTramiteMatriculacionBean.getTramiteTraficoBean().getIdContrato() != null
				? traficoTramiteMatriculacionBean.getTramiteTraficoBean().getIdContrato() : utilesColegiado.getIdContratoSessionBigDecimal()));
		presentadorSoloAdmin.setTipoInterviniente(TipoInterviniente.PresentadorTrafico.getValorEnum());

		
		if (docBaseCarpetaTramiteBean == null) {
			docBaseCarpetaTramiteBean = servicioGestionDocBase.getDocBaseParaTramite(traficoTramiteMatriculacionBean.getTramiteTraficoBean().getNumExpediente());
		}
	}
	
	/**
	 * 
	 * @return
	 * @throws OegamExcepcion
	 */
	public String duplicar() throws OegamExcepcion{

		log.info("duplicar tramite"); 

		try {
			mantenerAcciones();
		} catch (Throwable e) {
			addActionError(e.toString());
		}
		//obtenemos el numero de expediente del duplicado
		HashMap <String,Object> resultadoModelo = getModeloTrafico().duplicarTramite(getTraficoTramiteMatriculacionBean().getTramiteTraficoBean());
		ResultBean resultBeanDuplicar = (ResultBean) resultadoModelo.get(ConstantesPQ.RESULTBEAN); 
		TramiteTraficoBean tramiteTraficoBean = (TramiteTraficoBean) resultadoModelo.get(ConstantesPQ.BEANPANTALLA); 

		//obtenemos el detalle para ese numero de expediente
		HashMap <String,Object> resultadoDetalle = getModeloMatriculacion().obtenerDetalle(tramiteTraficoBean.getNumExpediente(),utilesColegiado.getNumColegiadoSession(),utilesColegiado.getIdContratoSessionBigDecimal());		
		traficoTramiteMatriculacionBean = (TramiteTraficoMatriculacionBean) resultadoDetalle.get(ConstantesPQ.BEANPANTALLA);


		log.debug("resultado = " + resultBeanDuplicar.getMensaje());
		if (resultBeanDuplicar.getError()==false)
		{				
			addActionMessage("tramite duplicado"); 
			return ALTAS_MATRICULACION_MATW;			
		}
		else{			
			addActionError("no se ha podido duplicar el tramite" + resultBeanDuplicar.getMensaje());
			return ALTAS_MATRICULACION_MATW;
		}


	}

	public String anularTramite(){

		log.info("duplicar tramite"); 

		try {
			mantenerAcciones();
		} catch (Throwable e) {
			addActionError(e.toString());
		}
		if (!utilesColegiado.tienePermiso(utilesColegiado.PERMISO_MANTENIMIENTO_MATRICULACION_MATW)) {
			log.error("no hay permiso de mantenimiento de matriculacion");
			addActionError("Error permisos mantenimiento matriculacion");
			return ALTAS_MATRICULACION_MATW;  
		}

		//obtenemos el numero de expediente del duplicado
		BeanPQCambiarEstadoTramite beanPQCambiarEstadoTramite = new BeanPQCambiarEstadoTramite();


		beanPQCambiarEstadoTramite.setP_ESTADO(new BigDecimal(EstadoTramiteTrafico.Anulado.getValorEnum())); 
		beanPQCambiarEstadoTramite.setP_NUM_EXPEDIENTE(traficoTramiteMatriculacionBean.getTramiteTraficoBean().getNumExpediente()); 
		HashMap <String,Object> resultadoModelo = getModeloTrafico().cambiarEstadoTramite(beanPQCambiarEstadoTramite);
		ResultBean resultBeanAnular = (ResultBean) resultadoModelo.get(ConstantesPQ.RESULTBEAN); 

		if (resultBeanAnular.getError()==false)
		{				
			addActionMessage(TRAMITE_ANULADO);
			//obtenemos el detalle para ese numero de expediente
			HashMap <String,Object> resultadoDetalle = getModeloMatriculacion().obtenerDetalle(getTraficoTramiteMatriculacionBean().getTramiteTraficoBean().getNumExpediente(),utilesColegiado.getNumColegiadoSession(),utilesColegiado.getIdContratoSessionBigDecimal());		
			setTraficoTramiteMatriculacionBean((TramiteTraficoMatriculacionBean) resultadoDetalle.get(ConstantesPQ.BEANPANTALLA));

		}
		else{			
			addActionError(NO_SE_HA_PODIDO_ANULAR_EL_TRAMITE + resultBeanAnular.getMensaje());
		}

		return ALTAS_MATRICULACION_MATW;		


	}

	/**
	 * Método para mantener las acciones de un trámite según se vayan realizando acciones con el mismo.
	 * @throws Throwable
	 */
	public void mantenerAcciones() throws Throwable{
		propTexto = ConstantesMensajes.MENSAJE_TRAMITESRESPONSABILIDAD;
		//Incorporamos la obtención de acciones por trámite
		if(null!=traficoTramiteMatriculacionBean.getTramiteTraficoBean().getNumExpediente()){
		
			String numExpediente = traficoTramiteMatriculacionBean.getTramiteTraficoBean().getNumExpediente().toString();
			listaAcciones = UtilidadesAccionTrafico.getInstance().generarListaAcciones(numExpediente);
		}
		//Fin Acciones
	}

	public File getFileModeloAEAT() {
		return fileModeloAEAT;
	}


	public void setFileModeloAEAT(File fileModeloAEAT) {
		this.fileModeloAEAT = fileModeloAEAT;
	}


	public String getFileModeloAEATFileName() {
		return fileModeloAEATFileName;
	}


	public void setFileModeloAEATFileName(String fileModeloAEATFileName) {
		this.fileModeloAEATFileName = fileModeloAEATFileName;
	}


	public String getFileModeloAEATContentType() {
		return fileModeloAEATContentType;
	}


	public void setFileModeloAEATContentType(String fileModeloAEATContentType) {
		this.fileModeloAEATContentType = fileModeloAEATContentType;
	}


	public TramiteTraficoMatriculacionBean getTraficoTramiteMatriculacionBean() {
		return traficoTramiteMatriculacionBean;
	}


	public void setTraficoTramiteMatriculacionBean(
			TramiteTraficoMatriculacionBean traficoTramiteMatriculacionBean) {
		this.traficoTramiteMatriculacionBean = traficoTramiteMatriculacionBean;
	}

	/* INICIO MANTIS 0013245 */
	public IntervinienteTrafico getPresentadorSoloAdmin() {
		return presentadorSoloAdmin;
	}

	public void setPresentadorSoloAdmin(IntervinienteTrafico presentadorSoloAdmin) {
		this.presentadorSoloAdmin = presentadorSoloAdmin;
	}	
	/* FIN MANTIS 0013245 */


	private String completarFormulario(boolean formularioInicial) throws Throwable {


		if (formularioInicial)
		{			
			limpiarCamposSession(); 			
		}

		log.info("completar formulario");	
		
		String habilitarMatriculacion = gestorPropiedades.valorPropertie("habilitar.matriculacion.nueva");
		
		if ("SI".equals(habilitarMatriculacion)) {
			return "altaTramiteMatriculacion";
		}

		return ALTAS_MATRICULACION_MATW;
	}

	/**
	 * Método que busca un interviniente en bbdd por el DNI y devuelve todos los datos del interviniente.
	 * @return
	 */
	public String buscarInterviniente(){

		Persona persona = new Persona(true);

		// Si el interviniente que hay que buscar es un arrendatario
		if (tipoIntervinienteBuscar!= null && tipoIntervinienteBuscar.equals(TipoInterviniente.Arrendatario.getNombreEnum())){
			String numColegiado = (null!= traficoTramiteMatriculacionBean.getTramiteTraficoBean().getNumColegiado() &&
					!"".equals(traficoTramiteMatriculacionBean.getTramiteTraficoBean().getNumColegiado()))?
					traficoTramiteMatriculacionBean.getTramiteTraficoBean().getNumColegiado():utilesColegiado.getNumColegiadoSession();
			persona = ModeloPersona.obtenerDetallePersonaCompleto(traficoTramiteMatriculacionBean.getArrendatarioBean().getPersona().getNif(), numColegiado);
			IntervinienteTrafico interviniente = new IntervinienteTrafico(true);
			interviniente.setPersona(persona);
			interviniente.setIdContrato(traficoTramiteMatriculacionBean.getTramiteTraficoBean().getIdContrato());
			interviniente.setNumColegiado(traficoTramiteMatriculacionBean.getTramiteTraficoBean().getNumColegiado());
			traficoTramiteMatriculacionBean.setArrendatarioBean(interviniente);

		}else if // Si el interviniente que hay que buscar es un RepresentanteTitular
		(tipoIntervinienteBuscar!= null && tipoIntervinienteBuscar.equals(TipoInterviniente.RepresentanteTitular.getNombreEnum())){
			String numColegiado = (null!= traficoTramiteMatriculacionBean.getTramiteTraficoBean().getNumColegiado() &&
					!"".equals(traficoTramiteMatriculacionBean.getTramiteTraficoBean().getNumColegiado()))?
					traficoTramiteMatriculacionBean.getTramiteTraficoBean().getNumColegiado():utilesColegiado.getNumColegiadoSession();
			persona = ModeloPersona.obtenerDetallePersonaCompleto(traficoTramiteMatriculacionBean.getRepresentanteTitularBean().getPersona().getNif(), numColegiado);
			IntervinienteTrafico interviniente = new IntervinienteTrafico(true);
			interviniente.setPersona(persona);
			interviniente.setIdContrato(traficoTramiteMatriculacionBean.getTramiteTraficoBean().getIdContrato());
			interviniente.setNumColegiado(traficoTramiteMatriculacionBean.getTramiteTraficoBean().getNumColegiado());
			traficoTramiteMatriculacionBean.setRepresentanteTitularBean(interviniente);

		}else if // Si el interviniente que hay que buscar es un Titular
		(tipoIntervinienteBuscar!= null && tipoIntervinienteBuscar.equals(TipoInterviniente.Titular.getNombreEnum())){
			String numColegiado = (null!= traficoTramiteMatriculacionBean.getTramiteTraficoBean().getNumColegiado() &&
					!"".equals(traficoTramiteMatriculacionBean.getTramiteTraficoBean().getNumColegiado()))?
					traficoTramiteMatriculacionBean.getTramiteTraficoBean().getNumColegiado():utilesColegiado.getNumColegiadoSession();
			//TODO MPC. Cambio IVTM.
			ibanTitular = getServicioIVTM().getIbanTitular(traficoTramiteMatriculacionBean.getTitularBean().getPersona().getNif(), numColegiado);
			persona = ModeloPersona.obtenerDetallePersonaCompleto(traficoTramiteMatriculacionBean.getTitularBean().getPersona().getNif(), numColegiado);
			IntervinienteTrafico interviniente = new IntervinienteTrafico(true);
			interviniente.setPersona(persona);
			interviniente.setIdContrato(traficoTramiteMatriculacionBean.getTramiteTraficoBean().getIdContrato());
			interviniente.setNumColegiado(traficoTramiteMatriculacionBean.getTramiteTraficoBean().getNumColegiado());
			traficoTramiteMatriculacionBean.setTitularBean(interviniente);
		}else if // Si el interviniente que hay que buscar es un Conductor Habitual
		(tipoIntervinienteBuscar!= null && tipoIntervinienteBuscar.equals(TipoInterviniente.ConductorHabitual.getNombreEnum())){
			String numColegiado = (null!= traficoTramiteMatriculacionBean.getTramiteTraficoBean().getNumColegiado() &&
					!"".equals(traficoTramiteMatriculacionBean.getTramiteTraficoBean().getNumColegiado()))?
					traficoTramiteMatriculacionBean.getTramiteTraficoBean().getNumColegiado():utilesColegiado.getNumColegiadoSession();
			persona = ModeloPersona.obtenerDetallePersonaCompleto(traficoTramiteMatriculacionBean.getConductorHabitualBean().getPersona().getNif(), numColegiado);
			IntervinienteTrafico interviniente = new IntervinienteTrafico(true);
			interviniente.setPersona(persona);
			interviniente.setIdContrato(traficoTramiteMatriculacionBean.getTramiteTraficoBean().getIdContrato());
			interviniente.setNumColegiado(traficoTramiteMatriculacionBean.getTramiteTraficoBean().getNumColegiado());
			traficoTramiteMatriculacionBean.setConductorHabitualBean(interviniente);
		}

		try {
			mantenerAcciones();
		} catch (Throwable e) {
			addActionError(e.toString());
		}
		return ALTAS_MATRICULACION_MATW;
	}
	
	public String descargarDocBase() {
		try {
			if (docBaseCarpetaTramiteBean == null) {
				docBaseCarpetaTramiteBean = servicioGestionDocBase.getDocBaseParaTramite(traficoTramiteMatriculacionBean.getTramiteTraficoBean().getNumExpediente());
			}
			if (docBaseCarpetaTramiteBean != null) {
				ResultadoDocBaseBean resultado = servicioGestionDocBase.descargarDocBase(docBaseCarpetaTramiteBean.getIdDocBase());
				inputStream = new FileInputStream((File) resultado.getFichero());
				if (inputStream == null) {
					addActionError("No se puede recuperar el PDF del documento base para el trámite: '" + traficoTramiteMatriculacionBean.getTramiteTraficoBean().getNumExpediente() + "'");
					return ALTAS_MATRICULACION_MATW;
				}
				fileName = (String) resultado.getNombreFichero();
			} else {
				addActionError("No se puede recuperar el documento base para el trámite: '" + traficoTramiteMatriculacionBean.getTramiteTraficoBean().getNumExpediente() + "'");
				return ALTAS_MATRICULACION_MATW;
			}
			return DESCARGAR_DOCUMENTO_BASE;
		} catch (Exception e) {
			addActionError("No se puede recuperar el documento base para el trámite: '" + traficoTramiteMatriculacionBean.getTramiteTraficoBean().getNumExpediente() + "'");
			log.error(e.getMessage());
			return ALTAS_MATRICULACION_MATW;
		}
	}
	
	public String liberarTramite (){
		log.debug("Ha entrado al método liberarTramite ");
		if (! utilesColegiado.tienePermisoLiberarEEFF()){
			addActionError(ConstantesEEFF.EEFF_TEXTO_NO_PERMISO);
			log.debug("El colegiado "+ utilesColegiado.getNumColegiadoSession() + " ha intentado liberar un trámite y no tiene permisos");
			return ERROR;
		}
		log.debug("El colegiado "+ utilesColegiado.getNumColegiadoSession() + " ha intentado liberar un trámite");
		if(traficoTramiteMatriculacionBean == null || traficoTramiteMatriculacionBean.getTramiteTraficoBean()==null || traficoTramiteMatriculacionBean.getTramiteTraficoBean().getNumExpediente()==null){
			addActionError("El trámite no tiene numéro de expediente");
			log.debug("El colegiado "+ utilesColegiado.getNumColegiadoSession() + " ha intentado liberar un trámite que no tiene número de expediente");
			return ERROR;
		}
		BigDecimal numExpediente = traficoTramiteMatriculacionBean.getTramiteTraficoBean().getNumExpediente();
		ResultBean resultBean = servicioEEFF.liberar(numExpediente, utilesColegiado.getIdUsuarioSessionBigDecimal());
		if(resultBean.getError()){
			for (String error : resultBean.getListaMensajes()){
				addActionError(error);
			}
		}else{
			for (String error : resultBean.getListaMensajes()){
				addActionMessage(error);
			}
			recuperarTramite(numExpediente);
		}
		try {
			mantenerAcciones();
		} catch (Throwable e) {
		}
		return ALTAS_MATRICULACION_MATW;
	}

	private void recuperarTramite(BigDecimal numExpediente){
		HashMap<String, Object> resultadoMetodo =  getModeloMatriculacion().obtenerDetalle(numExpediente,utilesColegiado.getNumColegiadoSession(),utilesColegiado.getIdContratoSessionBigDecimal());
		traficoTramiteMatriculacionBean = (TramiteTraficoMatriculacionBean) resultadoMetodo.get(ConstantesPQ.BEANPANTALLA);
	}
	
	@Override
	public String execute() throws Exception {
		log.debug("execute");
		try {
			mantenerAcciones();
		} catch (Throwable e) {
			addActionError(e.toString());
		}
		try {
			mantenerAcciones();
		} catch (Throwable e) {
			addActionError(e.toString());
		}
		return ALTAS_MATRICULACION_MATW;
	}

	public String actualizarMatManual(){
		ResultBean resultBean = new ResultBean();
		HttpServletRequest request = ServletActionContext.getRequest();

		String numExpediente = request.getParameter("numExpediente");
		String matricula = request.getParameter("matricula");
		String fechaMatriculacion = request.getParameter("fechaMatriculacion");

		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
		Date dateMatriculacion = null;
		try {
			dateMatriculacion = formatter.parse(fechaMatriculacion);
			resultBean = servicioVehiculo.actualizarMatricula(new BigDecimal(numExpediente), matricula, dateMatriculacion, servicioUsuario.getUsuarioDto(utilesColegiado.getIdUsuarioSessionBigDecimal()));
		} catch (ParseException e) {
			resultBean.addError("Error en el formato de los datos de entrada");
			resultBean.setError(true);
		}

		if (resultBean != null && resultBean.getError()) {
			addActionError(resultBean.getMensaje());
		} else {
			addActionMessage("Se ha actualizado la matrícula.");
		}
		return ALTAS_MATRICULACION_MATW;
	}
	
	public String recuperar576(){
		String pagina = "";
		Boolean error = Boolean.FALSE;
		try {
			if(traficoTramiteMatriculacionBean != null && traficoTramiteMatriculacionBean.getTramiteTraficoBean() != null &&
					traficoTramiteMatriculacionBean.getTramiteTraficoBean().getNumExpediente() != null){
				ResultadoMatriculacionBean resultado = servicioTramiteTraficoMatriculacion.obtenerFicheroPresentacion576(traficoTramiteMatriculacionBean.getTramiteTraficoBean().getNumExpediente().toString());
				if(!resultado.getError()){
					inputStream = new FileInputStream((File) resultado.getFichero());
					fileName = resultado.getNombreFichero();
					pagina = DESCARGAR_PRESENTACION_576;
				} else {
					addActionError(resultado.getMensaje());
					error = Boolean.TRUE;
				}
			} else  {
				addActionError("No se puede descargar el fichero de presentacion del 576 al estar el numero de expediente vacio.");
				error = Boolean.TRUE;
			}
			
		} catch (Throwable e) {
			log.error("Ha sucedido un error a la hora de recuperar el fichero para la presentación del 576, error: ",e);
			addActionError("Ha sucedido un error a la hora de recuperar el fichero para la presentación del 576.");
			
		}
		if(error){
			try {
				mantenerAcciones();
			} catch (Throwable e) {
				log.error("Ha sucedido un error a la hora de recuperar el fichero para la presentación del 576, error: ",e);
			}
			pagina = ALTAS_MATRICULACION_MATW;
		}
		return pagina;
	}
	
	
	public String verJustificanteIVTM(){
		
		String pagina = "";
		Boolean error = Boolean.FALSE;
		try {
			if(traficoTramiteMatriculacionBean != null && traficoTramiteMatriculacionBean.getTramiteTraficoBean() != null &&
					traficoTramiteMatriculacionBean.getTramiteTraficoBean().getNumExpediente() != null){
				TramiteTrafMatrDto trafMatrDto = servicioTramiteTraficoMatriculacion.getTramiteMatriculacion(traficoTramiteMatriculacionBean.getTramiteTraficoBean().getNumExpediente(),
						Boolean.FALSE, Boolean.FALSE);
				traficoTramiteMatriculacionBean.getTramiteTraficoBean().setEstado(trafMatrDto.getEstado());
				traficoTramiteMatriculacionBean.setCheckJustifIvtm(trafMatrDto.getCheckJustifFicheroIvtm() ? "S" : "N");
				ResultadoMatriculacionBean resultado = servicioTramiteTraficoMatriculacion.obtenerJustificanteIVTM(traficoTramiteMatriculacionBean.getTramiteTraficoBean().getNumExpediente().toString());
				if(!resultado.getError()){
					inputStream = new FileInputStream((File) resultado.getFichero());
					fileName = ConstantesGestorFicheros.NOMBRE_JUSTIFICANTE_IVTM+traficoTramiteMatriculacionBean.getTramiteTraficoBean().getNumExpediente()+ConstantesGestorFicheros.EXTENSION_PDF;
					pagina = "descargarJustificanteIVTM";
				} else {
					addActionError(resultado.getMensaje());
					error = Boolean.TRUE;
					pagina = ALTAS_MATRICULACION_MATW;
				}
			} else  {
				addActionError("Ha sucedido un error a la hora de recuperar el fichero justificante del pago IVTM.");
				error = Boolean.TRUE;
				pagina = ALTAS_MATRICULACION_MATW;
			}
		} catch (Throwable e) {
			log.error("Ha sucedido un error a la hora de recuperar el fichero justificante del pago IVTM, error: ",e);
			addActionError("Ha sucedido un error a la hora de recuperar el fichero justificante del pago IVTM.");
			
		}
		if(error){
			try {
				mantenerAcciones();
			} catch (Throwable e) {
				log.error("Ha sucedido un error a la hora de recuperar el fichero justificante del pago IVTM, error: ",e);
			}
			if(traficoTramiteMatriculacionBean.getTramiteTraficoBean().getEstado() != null
					 && EstadoTramiteTrafico.Finalizado_Telematicamente_Impreso.getValorEnum().equals(traficoTramiteMatriculacionBean.getTramiteTraficoBean().getEstado().getValorEnum())){
				addActionError("Ha sucedido un error a la hora de recuperar el fichero justificante del pago IVTM.");
				error = Boolean.TRUE;
				pagina = ALTAS_MATRICULACION_MATW;
			}else{
				addActionError("No se puede consultar el justificante del pago IVTM sin tener guardado un trámite con un número de expediente asociado.");
				error = Boolean.TRUE;
				pagina = ALTAS_MATRICULACION_MATW;
			}
			
		}
		return pagina;
	}
	
	public String subirJustificanteIVTM(){
		
		String pagina = "";
		Boolean error = Boolean.FALSE;
		ResultadoMatriculacionBean resultado = new ResultadoMatriculacionBean(error);
		try {
			
			if(traficoTramiteMatriculacionBean != null && traficoTramiteMatriculacionBean.getTramiteTraficoBean() != null &&
					traficoTramiteMatriculacionBean.getTramiteTraficoBean().getNumExpediente() != null){
				if(getFileJustifIvtm()!=null){
						resultado = servicioTramiteTraficoMatriculacion.subirJustificanteIVTM(getFileJustifIvtm(), traficoTramiteMatriculacionBean.getTramiteTraficoBean().getNumExpediente().toString());	
				}else{
					addActionError("Introduzca un fichero para subir");
					return ALTAS_MATRICULACION_MATW;
				}				
				if(!resultado.getError()){
					traficoTramiteMatriculacionBean.setCheckJustifIvtm("S");
					setNombreFicheroJustifIvtm(resultado.getFichero().getName());
					addActionMessage("Se ha subido correctamente el justificante de pago IVTM");
					pagina = ALTAS_MATRICULACION_MATW;
				} else {
					addActionError(resultado.getMensaje());
					error = Boolean.TRUE;
				}
			} else  {
				addActionError("No se puede subir el fichero de justificante de pago IVTM, al estar el numero de expediente vacio.");
				error = Boolean.TRUE;
			}
			
		} catch (Throwable e) {
			log.error("Ha sucedido un error a la hora de recuperar el fichero justificante del pago IVTM, error: ",e);
			addActionError("Ha sucedido un error a la hora de recuperar el fichero justificante del pago IVTM.");
			
		}
		if(error){
			try {
				mantenerAcciones();
			} catch (Throwable e) {
				log.error("Ha sucedido un error a la hora de recuperar el fichero para la presentación del 576, error: ",e);
			}
			pagina = ALTAS_MATRICULACION_MATW;
		}
		return pagina;
	}
	
	public String abrirPopMatriculacion(){
		return "popUpMatriculaManual";
	}

	public File getFichero() {
		return fichero;
	}

	public void setFichero(File fichero) {
		this.fichero = fichero;
	}

	public boolean isReadOnly() {
		return readOnly;
	}

	public void setReadOnly(boolean readOnly) {
		this.readOnly = readOnly;
	}

	public String getTipoIntervinienteBuscar() {
		return tipoIntervinienteBuscar;
	}

	public void setTipoIntervinienteBuscar(String tipoIntervinienteBuscar) {
		this.tipoIntervinienteBuscar = tipoIntervinienteBuscar;
	}

	public String getProvinciaIVTM() {
		return provinciaIVTM;
	}

	public void setProvinciaIVTM(String provinciaIVTM) {
		this.provinciaIVTM = provinciaIVTM;
	}

	public String getMunicipioIVTM() {
		return municipioIVTM;
	}

	public void setMunicipioIVTM(String municipioIVTM) {
		this.municipioIVTM = municipioIVTM;
	}

	public String getPuebloIVTM() {
		return puebloIVTM;
	}

	public void setPuebloIVTM(String puebloIVTM) {
		this.puebloIVTM = puebloIVTM;
	}

	public PaginatedList getListaAcciones() {
		return listaAcciones;
	}

	public void setListaAcciones(PaginatedList listaAcciones) {
		this.listaAcciones = listaAcciones;
	}

	public String getPropTexto() {
		return propTexto;
	}

	public void setPropTexto(String propTexto) {
		this.propTexto = propTexto;
	}

	public boolean isMarcadoConductorHabitual() {
		return marcadoConductorHabitual;
	}

	public void setMarcadoConductorHabitual(boolean marcadoConductorHabitual) {
		this.marcadoConductorHabitual = marcadoConductorHabitual;
	}

	/* *********************************************************************** */
	/* MODELOS *************************************************************** */
	/* *********************************************************************** */

	public ModeloTrafico getModeloTrafico() {
		if (modeloTrafico == null) {
			modeloTrafico = new ModeloTrafico();
		}
		return modeloTrafico;
	}

	public void setModeloTrafico(ModeloTrafico modeloTrafico) {
		this.modeloTrafico = modeloTrafico;
	}

	public ModeloEITV getModeloEITV() {
		if (modeloEITV == null) {
			modeloEITV = new ModeloEITV();
		}
		return modeloEITV;
	}

	public void setModeloEITV(ModeloEITV modeloEITV) {
		this.modeloEITV = modeloEITV;
	}

	public ModeloSolicitud getModeloSolicitud() {
		if (modeloSolicitud == null) {
			modeloSolicitud = new ModeloSolicitud();
		}
		return modeloSolicitud;
	}

	public void setModeloSolicitud(ModeloSolicitud modeloSolicitud) {
		this.modeloSolicitud = modeloSolicitud;
	}

	public ModeloCreditosTrafico getModeloCreditosTrafico() {
		if (modeloCreditosTrafico == null) {
			modeloCreditosTrafico = new ModeloCreditosTrafico();
		}
		return modeloCreditosTrafico;
	}

	public void setModeloCreditosTrafico(ModeloCreditosTrafico modeloCreditosTrafico) {
		this.modeloCreditosTrafico = modeloCreditosTrafico;
	}
	
	public ModeloMatriculacion getModeloMatriculacion() {
		if (modeloMatriculacion == null) {
			modeloMatriculacion = new ModeloMatriculacion();
		}
		return modeloMatriculacion;
	}

	public void setModeloMatriculacion(ModeloMatriculacion modeloMatriculacion) {
		this.modeloMatriculacion = modeloMatriculacion;
	}

	public ModeloPresentacionAEAT getModeloPresentacionAEAT() {
		if (modeloPresentacionAEAT == null) {
			modeloPresentacionAEAT = new ModeloPresentacionAEAT();
		}
		return modeloPresentacionAEAT;
	}

	public void setModeloPresentacionAEAT(
			ModeloPresentacionAEAT modeloPresentacionAEAT) {
		this.modeloPresentacionAEAT = modeloPresentacionAEAT;
	}

	public IvtmMatriculacionDto getIvtmMatriculacionDto() {
		return ivtmMatriculacionDto;
	}

	public void setIvtmMatriculacionDto(IvtmMatriculacionDto ivtmMatriculacionDto) {
		this.ivtmMatriculacionDto = ivtmMatriculacionDto;
	}

	public ServicioIVTMMatriculacionIntf getServicioIVTM() {
		if (servicioIVTM==null){
			servicioIVTM = new ServicioIVTMMatriculacionImpl();
		}
		return servicioIVTM;
	}

	public void setServicioIVTM(ServicioIVTMMatriculacionIntf servicioIVTM) {
		this.servicioIVTM = servicioIVTM;
	}

	public String getIbanTitular() {
		return ibanTitular;
	}

	public void setIbanTitular(String ibanTitular) {
		this.ibanTitular = ibanTitular;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getNumsExpediente() {
		return numsExpediente;
	}

	public void setNumsExpediente(String numsExpediente) {
		this.numsExpediente = numsExpediente;
	}

	public String getTipoTramiteSeleccionado() {
		return tipoTramiteSeleccionado;
	}

	public void setTipoTramiteSeleccionado(String tipoTramiteSeleccionado) {
		this.tipoTramiteSeleccionado = tipoTramiteSeleccionado;
	}

	public String getEstadoTramiteSeleccionado() {
		return estadoTramiteSeleccionado;
	}

	public void setEstadoTramiteSeleccionado(String estadoTramiteSeleccionado) {
		this.estadoTramiteSeleccionado = estadoTramiteSeleccionado;
	}

	public ModeloTramiteTrafInterface getModeloTramite() {
		return modeloTramite;
	}

	public void setModeloTramite(ModeloTramiteTrafInterface modeloTramite) {
		this.modeloTramite = modeloTramite;
	}

	public String getBastidorSeleccionado() {
		return bastidorSeleccionado;
	}

	public void setBastidorSeleccionado(String bastidorSeleccionado) {
		this.bastidorSeleccionado = bastidorSeleccionado;
	}

	public Boolean getPdf576() {
		return pdf576;
	}

	public void setPdf576(Boolean pdf576) {
		this.pdf576 = pdf576;
	}

	public InputStream getInputStream() {
		return inputStream;
	}

	public void setInputStream(InputStream inputStream) {
		this.inputStream = inputStream;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getFileSize() {
		return fileSize;
	}

	public void setFileSize(String fileSize) {
		this.fileSize = fileSize;
	}

	public Boolean getFicheroDeclaracion576() {
		return ficheroDeclaracion576;
	}

	public void setFicheroDeclaracion576(Boolean ficheroDeclaracion576) {
		this.ficheroDeclaracion576 = ficheroDeclaracion576;
	}
	
	public boolean isHabilitarBastidor() {
		return habilitarBastidor;
	}

	public void setHabilitarBastidor(boolean habilitarBastidor) {
		this.habilitarBastidor = habilitarBastidor;
	}

	public boolean isPermisoPago() {
		return permisoPago;
	}

	public void setPermisoPago(boolean permisoPago) {
		this.permisoPago = permisoPago;
	}

	public DocBaseCarpetaTramiteBean getDocBaseCarpetaTramiteBean() {
		return docBaseCarpetaTramiteBean;
	}

	public void setDocBaseCarpetaTramiteBean(DocBaseCarpetaTramiteBean docBaseCarpetaTramiteBean) {
		this.docBaseCarpetaTramiteBean = docBaseCarpetaTramiteBean;
	}

	public File getFileJustifIvtm() {
		return fileJustifIvtm;
	}

	public void setFileJustifIvtm(File fileJustifIvtm) {
		this.fileJustifIvtm = fileJustifIvtm;
	}

	public String getNombreFicheroJustifIvtm() {
		return nombreFicheroJustifIvtm;
	}

	public void setNombreFicheroJustifIvtm(String nombreFicheroJustifIvtm) {
		this.nombreFicheroJustifIvtm = nombreFicheroJustifIvtm;
	}

	public ServicioTramiteTraficoMatriculacion getServicioTramiteTraficoMatriculacion() {
		return servicioTramiteTraficoMatriculacion;
	}

	public void setServicioTramiteTraficoMatriculacion(ServicioTramiteTraficoMatriculacion servicioTramiteTraficoMatriculacion) {
		this.servicioTramiteTraficoMatriculacion = servicioTramiteTraficoMatriculacion;
	}

}
