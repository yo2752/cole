package trafico.beans.utiles;

import java.io.File;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.ParseException;
import java.util.List;

import javax.xml.bind.Marshaller;

import org.gestoresmadrid.core.springContext.ContextoSpring;
import org.gestoresmadrid.core.trafico.eeff.model.enumerados.ConstantesEEFF;
import org.gestoresmadrid.core.vehiculo.model.enumerados.Combustible;
import org.gestoresmadrid.oegam2comun.contrato.model.service.ServicioContrato;
import org.gestoresmadrid.oegam2comun.conversion.Conversiones;
import org.gestoresmadrid.oegam2comun.direccion.model.service.ServicioDireccion;
import org.gestoresmadrid.oegam2comun.ivtmMatriculacion.model.service.ServicioIvtmMatriculacion;
import org.gestoresmadrid.oegam2comun.model.service.ServicioPermisos;
import org.gestoresmadrid.oegam2comun.trafico.eeff.model.service.ServicioEEFFNuevo;
import org.gestoresmadrid.oegam2comun.trafico.eeff.view.dto.LiberacionEEFFDto;
import org.gestoresmadrid.oegam2comun.trafico.view.dto.TramiteTrafMatrDto;
import org.gestoresmadrid.oegam2comun.vehiculo.model.service.ServicioVehiculo;
import org.gestoresmadrid.oegamComun.acceso.service.impl.UtilesColegiado;
import org.gestoresmadrid.oegamComun.contrato.view.dto.ContratoDto;
import org.gestoresmadrid.oegamComun.direccion.view.dto.DireccionDto;
import org.gestoresmadrid.oegamComun.interviniente.view.dto.IntervinienteTraficoDto;
import org.gestoresmadrid.oegamComun.persona.view.dto.PersonaDto;
import org.gestoresmadrid.oegamComun.vehiculo.view.dto.VehiculoDto;
import org.gestoresmadrid.utilidades.components.GestorPropiedades;
import org.gestoresmadrid.utilidades.components.Utiles;
import org.gestoresmadrid.utilidades.components.UtilesFecha;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import com.itextpdf.text.pdf.codec.Base64;

import es.globaltms.gestorDocumentos.constantes.ConstantesGestorFicheros;
import escrituras.beans.Direccion;
import escrituras.beans.Persona;
import escrituras.beans.ResultBean;
import escrituras.utiles.enumerados.EstadoCivilMatw;
import escrituras.utiles.enumerados.SexoPersonaMatw;
import general.utiles.UtilesXML;
import trafico.beans.IntervinienteTrafico;
import trafico.beans.TramiteTraficoMatriculacionBean;
import trafico.beans.VehiculoBean;
import trafico.beans.jaxb.matw.FORMATOGA;
import trafico.beans.jaxb.matw.FORMATOGA.MATRICULACION;
import trafico.beans.jaxb.matw.FORMATOGA.MATRICULACION.DATOSARRENDATARIO;
import trafico.beans.jaxb.matw.FORMATOGA.MATRICULACION.DATOSCONDUCTORHABITUAL;
import trafico.beans.jaxb.matw.FORMATOGA.MATRICULACION.DATOSIMPORTADOR;
import trafico.beans.jaxb.matw.FORMATOGA.MATRICULACION.DATOSIMPUESTOS;
import trafico.beans.jaxb.matw.FORMATOGA.MATRICULACION.DATOSIMPUESTOS.DATOS0506;
import trafico.beans.jaxb.matw.FORMATOGA.MATRICULACION.DATOSIMPUESTOS.DATOSIMVTM;
import trafico.beans.jaxb.matw.FORMATOGA.MATRICULACION.DATOSIMPUESTOS.DATOSNRC;
import trafico.beans.jaxb.matw.FORMATOGA.MATRICULACION.DATOSLIMITACION;
import trafico.beans.jaxb.matw.FORMATOGA.MATRICULACION.DATOSREPRESENTANTEARRENDATARIO;
import trafico.beans.jaxb.matw.FORMATOGA.MATRICULACION.DATOSREPRESENTANTETITULAR;
import trafico.beans.jaxb.matw.FORMATOGA.MATRICULACION.DATOSTITULAR;
import trafico.beans.jaxb.matw.FORMATOGA.MATRICULACION.DATOSVEHICULO;
import trafico.beans.jaxb.matw.ObjectFactory;
import trafico.beans.jaxb.matw.Tipo05;
import trafico.beans.jaxb.matw.Tipo06;
import trafico.beans.jaxb.matw.TipoAlimentacion;
import trafico.beans.jaxb.matw.TipoCarroceria;
import trafico.beans.jaxb.matw.TipoCategoriaElectrica;
import trafico.beans.jaxb.matw.TipoColor;
import trafico.beans.jaxb.matw.TipoCombustible;
import trafico.beans.jaxb.matw.TipoInspeccion;
import trafico.beans.jaxb.matw.TipoMotivoTutela;
import trafico.beans.jaxb.matw.TipoProvincia;
import trafico.beans.jaxb.matw.TipoServicio;
import trafico.beans.jaxb.matw.TipoTarjetaItv;
import trafico.beans.jaxb.matw.dgt.tipos.TipoSN;
import trafico.beans.schemas.generated.transTelematica.TipoSINO;
import trafico.modelo.ivtm.IVTMModeloMatriculacionInterface;
import trafico.modelo.ivtm.impl.IVTMModeloMatriculacionImpl;
import trafico.utiles.UtilesConversiones;
import trafico.utiles.UtilesConversionesMatw;
import trafico.utiles.XMLGAFactory;
import trafico.utiles.constantes.Constantes;
import trafico.utiles.constantes.ConstantesTrafico;
import trafico.utiles.enumerados.ConceptoTutela;
import trafico.utiles.enumerados.TipoTarjetaITV;
import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;
import utilidades.web.excepciones.XmlNoValidoExcepcion;

public class XMLMatw {

	private static ILoggerOegam log = LoggerOegam.getLogger(XMLMatw.class);

	@Autowired
	private ServicioContrato servicioContrato;

	@Autowired
	private ServicioDireccion servicioDireccion;

	@Autowired
	private ServicioVehiculo servicioVehiculo;

	@Autowired
	private ServicioEEFFNuevo servicioEEFF;

	@Autowired
	private ServicioIvtmMatriculacion servicioIvtmMatriculacion;

	@Autowired
	private Conversiones conversiones;

	@Autowired
	private ServicioPermisos servicioPermisos;

	@Autowired
	private GestorPropiedades gestorPropiedades;
	
	@Autowired
	UtilesFecha utilesFecha;

	@Autowired
	private Utiles utiles;

	@Autowired
	UtilesConversiones utilesConversiones;
	
	@Autowired
	private UtilesColegiado utilesColegiado;
	
	public XMLMatw() {
		SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
	}

	/**
	 * Convierte el FORMATOGA a XML
	 * @param ga
	 */
	public ResultBean FORMATOGAtoXML(FORMATOGA ga, String idSession) throws Exception {
		ResultBean resultado = new ResultBean();
		// Clasificar objeto en archivo.
		String nodo = gestorPropiedades.valorPropertie(ConstantesGestorFicheros.RUTA_ARCHIVOS_TEMP);
		File salida = new File(nodo + "/temp/matr" + idSession + ".xml");
		try {
			// Crear clasificador
			Marshaller m = new XMLGAFactory().getMatriculacionMatWContext().createMarshaller();
			m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
			m.setProperty(Marshaller.JAXB_ENCODING, "ISO-8859-1");
			m.marshal(ga, salida);
			byte[] bytes = utiles.getBytesFromFile(salida);
			resultado.addAttachment(ConstantesTrafico.BYTESXML, bytes);
		} catch (Exception e) {
			log.error(e);
			resultado.setError(true);
			resultado.setMensaje("Error al generar el fichero XML");
			return resultado;
		} finally {
			try {
				// Validación interna contra el esquema antes de borrar el temporal:
				UtilesXML.validarXML(salida, gestorPropiedades.valorPropertie(Constantes.PROPERTIES_RUTA_ESQUEMA_MATRICULACION_MATW));
			} catch (XmlNoValidoExcepcion e) {
				log.error(e);
			} catch (Exception e) {
				log.error(e);
			}
			salida.delete();
		}

		return resultado;
	}

	/**
	 * Metodo para la exportacion de tramites
	 */

	public FORMATOGA convertirAFORMATOGA(List<TramiteTraficoMatriculacionBean> listaMatriculacion, boolean tienePermisoIVTM, boolean xmlSesion) throws Exception {

		trafico.beans.jaxb.matw.ObjectFactory factory = new trafico.beans.jaxb.matw.ObjectFactory();
		FORMATOGA ga = factory.createFORMATOGA();

		// CABECERA
		ga.setCABECERA(factory.createFORMATOGACABECERA());
		ga.getCABECERA().setDATOSGESTORIA(factory.createFORMATOGACABECERADATOSGESTORIA());
		ga.setPlataforma("OEGAM");

		UtilesConversionesMatw utils = new UtilesConversionesMatw();

		// Si se importa el xml de la sesion del usuario
		if (xmlSesion) {
			// Añado Cabecera
			ga.getCABECERA().getDATOSGESTORIA().setNIF(utilesColegiado.getNifUsuario());
			ga.getCABECERA().getDATOSGESTORIA().setNOMBRE(utilesColegiado.getApellidosNombreUsuario());
			ga.getCABECERA().getDATOSGESTORIA().setPROFESIONAL(new BigInteger(utilesColegiado.getNumColegiadoSession()));
			ga.getCABECERA().getDATOSGESTORIA().setPROVINCIA(TipoProvincia.fromValue(utils.getSiglaProvinciaFromNombre(utilesColegiado.getProvinciaDelContrato())));

		}
		// Si se importa el xml del contrato de la gestoria del tramite a exportar
		else {

			ServicioContrato servicioContrato = ContextoSpring.getInstance().getBean(ServicioContrato.class);
			ContratoDto contrato = servicioContrato.getContratoDto(listaMatriculacion.get(0).getTramiteTraficoBean().getIdContrato());

			// Añado Cabecera
			ga.getCABECERA().getDATOSGESTORIA().setNIF(contrato.getCif());
			ga.getCABECERA().getDATOSGESTORIA().setNOMBRE(contrato.getRazonSocial());
			ga.getCABECERA().getDATOSGESTORIA().setPROFESIONAL(new BigInteger(contrato.getColegiadoDto().getNumColegiado()));
			ga.getCABECERA().getDATOSGESTORIA().setPROVINCIA(TipoProvincia.fromValue(utils.getSiglasFromIdProvincia(contrato.getIdProvincia())));

			// Para evitar que se importen trámites exportados desde OEGAM:
			ga.getCABECERA().getDATOSGESTORIA().setOEGAMDGT(Base64.encodeBytes(Constantes.ORIGEN_OEGAM.getBytes()));

		}

		// Añado Fecha de creación
		ga.setFechaCreacion(utilesFecha.getFechaHoy());

		// Añado los trámites
		for (int i = 0; i < listaMatriculacion.size(); i++) {
			ga.getMATRICULACION().add(convertirBeanToGa(listaMatriculacion.get(i), tienePermisoIVTM, xmlSesion));
		}

		return ga;
	}

	public FORMATOGA convertirAFORMATOGANuevo(List<TramiteTrafMatrDto> listaMatriculacion, boolean xmlSesion) throws Exception {
		FORMATOGA ga = instanciarCompleto();
		boolean tienePermisoLiberacion = Boolean.FALSE;
		boolean tienePermisoIVTM = Boolean.FALSE;
		if (xmlSesion) {
			ga.getCABECERA().getDATOSGESTORIA().setNIF(utilesColegiado.getNifUsuario());
			ga.getCABECERA().getDATOSGESTORIA().setNOMBRE(utilesColegiado.getApellidosNombreUsuario());
			ga.getCABECERA().getDATOSGESTORIA().setPROFESIONAL(new BigInteger(utilesColegiado.getNumColegiadoSession()));
			ga.getCABECERA().getDATOSGESTORIA().setPROVINCIA(TipoProvincia.fromValue(conversiones.getSiglaProvinciaFromNombre(utilesColegiado.getProvinciaDelContrato())));

		} else {
			ContratoDto contrato = servicioContrato.getContratoDto(listaMatriculacion.get(0).getIdContrato());
			// Añado Cabecera
			ga.getCABECERA().getDATOSGESTORIA().setNIF(contrato.getCif());
			ga.getCABECERA().getDATOSGESTORIA().setNOMBRE(contrato.getRazonSocial());
			ga.getCABECERA().getDATOSGESTORIA().setPROFESIONAL(new BigInteger(contrato.getColegiadoDto().getNumColegiado()));
			ga.getCABECERA().getDATOSGESTORIA().setPROVINCIA(TipoProvincia.fromValue(conversiones.getSiglasFromIdProvincia(contrato.getIdProvincia())));
			ga.getCABECERA().getDATOSGESTORIA().setOEGAMDGT(Base64.encodeBytes(Constantes.ORIGEN_OEGAM.getBytes()));
		}

		// Añado Fecha de creación
		ga.setFechaCreacion(utilesFecha.getFechaHoy());

		// Añado los trámites
		for (int i = 0; i < listaMatriculacion.size(); i++) {
			if (xmlSesion) {
				tienePermisoLiberacion = utilesColegiado.tienePermisoLiberarEEFF();
				tienePermisoIVTM = utilesColegiado.tienePermisoAutoliquidarIvtm();
			} else {
				tienePermisoLiberacion = servicioPermisos.tienePermisoElContrato(listaMatriculacion.get(i).getIdContrato().longValue(), ConstantesEEFF.CODIGO_PERMISO_BBDD_LIBERAR_EEFF,
						UtilesColegiado.APLICACION_OEGAM_TRAF);
				tienePermisoIVTM = servicioPermisos.tienePermisoElContrato(listaMatriculacion.get(i).getIdContrato().longValue(), UtilesColegiado.PERMISO_AUTOLIQUIDAR_IVTM,
						UtilesColegiado.APLICACION_OEGAM_TRAF);
			}
			ga.getMATRICULACION().add(convertirBeanToGaNuevo(listaMatriculacion.get(i), tienePermisoIVTM, tienePermisoLiberacion, xmlSesion));
		}
		return ga;

	}

	private FORMATOGA.MATRICULACION convertirBeanToGa(TramiteTraficoMatriculacionBean bean, boolean tienePermisoIVTM, boolean xmlSesion) throws Exception {

		// FORMATOGA.MATRICULACION mat = instanciarMATRICULACION();
		UtilesConversionesMatw utils = new UtilesConversionesMatw();

		trafico.beans.jaxb.matw.ObjectFactory factory = new trafico.beans.jaxb.matw.ObjectFactory();
		FORMATOGA.MATRICULACION mat = factory.createFORMATOGAMATRICULACION();

		/****************************************************************************************************************
		 * RELLENAMOS EL FORMATOGA CON LOS DATOS QUE TENGAMOS EN EL BEAN DE PANTALLA ***********************************
		 ***************************************************************************************************************/

		// NÚMERO DE EXPEDIENTE
		if (bean.getTramiteTraficoBean().getNumExpediente() != null && !bean.getTramiteTraficoBean().getNumExpediente().equals("")) {
			mat.setNUMEROEXPEDIENTE(bean.getTramiteTraficoBean().getNumExpediente().toString());
		}

		// REFERENCIA PROPIA
		if (bean.getTramiteTraficoBean().getReferenciaPropia() != null && !bean.getTramiteTraficoBean().getReferenciaPropia().equals("")) {
			mat.setREFERENCIAPROPIA(bean.getTramiteTraficoBean().getReferenciaPropia());
		}

		// NUMERO PROFESIONAL
		// Si exporta xml de sesion se enviará como profesional el de la sesion , caso contrario el del tramite
		if (bean.getTramiteTraficoBean().getNumColegiado() != null && !bean.getTramiteTraficoBean().getNumColegiado().equals("") && !xmlSesion) {
			mat.setNUMEROPROFESIONAL(new BigInteger("0" + bean.getTramiteTraficoBean().getNumColegiado()));
		} else {
			mat.setNUMEROPROFESIONAL(new BigInteger("0" + utilesColegiado.getNumColegiadoSession()));
		}
		// FECHA CREACION
		if (bean.getTramiteTraficoBean().getFechaCreacion() != null && !bean.getTramiteTraficoBean().getFechaCreacion().isfechaNula()) {
			mat.setFECHACREACION(bean.getTramiteTraficoBean().getFechaCreacion().toString());
		}

		// FECHA PRESENTACIÓN
		if (bean.getTramiteTraficoBean().getFechaPresentacion() != null && !bean.getTramiteTraficoBean().getFechaPresentacion().isfechaNula()) {
			mat.setFECHAPRESENTACION(bean.getTramiteTraficoBean().getFechaPresentacion().toString());
		}

		// JEFATURA
		if (bean.getTramiteTraficoBean().getJefaturaTrafico() != null && bean.getTramiteTraficoBean().getJefaturaTrafico().getJefaturaProvincial() != null && !bean.getTramiteTraficoBean()
				.getJefaturaTrafico().getJefaturaProvincial().equals("")) {
			mat.setJEFATURA(TipoProvincia.fromValue(bean.getTramiteTraficoBean().getJefaturaTrafico().getJefaturaProvincial()));

		}

		// SUCURSAL
		if (bean.getTramiteTraficoBean().getJefaturaTrafico() != null && bean.getTramiteTraficoBean().getJefaturaTrafico().getSucursal() != null && !bean.getTramiteTraficoBean().getJefaturaTrafico()
				.getSucursal().equals("")) {
			mat.setSUCURSAL(bean.getTramiteTraficoBean().getJefaturaTrafico().getSucursal());
		}

		// TIPO_TASA -Si el xml de sesion no se informara de este campo
		if (bean.getTramiteTraficoBean().getTasa() != null && bean.getTramiteTraficoBean().getTasa().getTipoTasa() != null && !bean.getTramiteTraficoBean().getTasa().getTipoTasa().equals("")
				&& !xmlSesion) {
			mat.setTIPOTASA(bean.getTramiteTraficoBean().getTasa().getTipoTasa());
		}

		// CODIGO TASA -Si el xml de sesion no se informara de este campo
		if (bean.getTramiteTraficoBean().getTasa() != null && bean.getTramiteTraficoBean().getTasa().getCodigoTasa() != null && !bean.getTramiteTraficoBean().getTasa().getCodigoTasa().equals("")
				&& !xmlSesion) {
			mat.setTASA(bean.getTramiteTraficoBean().getTasa().getCodigoTasa());
		}

		// OBSERVACIONES
		if (bean.getTramiteTraficoBean().getAnotaciones() != null && !bean.getTramiteTraficoBean().getAnotaciones().equals("")) {
			mat.setOBSERVACIONES(bean.getTramiteTraficoBean().getAnotaciones());
		}

		// ENTREGA_FACT_MATRICULACION
		if (bean.getEntregaFactMatriculacion() != null && !bean.getEntregaFactMatriculacion().equals("")) {

			mat.setENTREGAFACTMATRICULACION(bean.getEntregaFactMatriculacion().equalsIgnoreCase("true") ? TipoSINO.SI.name() : TipoSINO.NO.name());
		}

		// VERSION MATE (para evitar que se importen tramites con el xsd antiguo)
		mat.setVERSIONMATE(Constantes.VERSION_ACTUAL_MATE);

		// jbc Datos de exportación de Entidades financieras

		if (utilesColegiado.tienePermisoLiberarEEFF().booleanValue()) {
			if (bean.getEeffLiberacionDTO() != null && !bean.getEeffLiberacionDTO().isExento()) {
				LiberacionEEFFDto liberacionEEFFDTO = servicioEEFF.getLiberacionEEFFDto(bean.getTramiteTraficoBean().getNumExpediente());
				mat.setEXENTOLIBERAR("NO");
				FORMATOGA.MATRICULACION.DATOSLIBERACION fmEEFF = factory.createFORMATOGAMATRICULACIONDATOSLIBERACION();
				fmEEFF.setCIFFIR(liberacionEEFFDTO.getFirCif());
				fmEEFF.setMARCAFIR(liberacionEEFFDTO.getFirMarca());
				fmEEFF.setNUMFACTURA(liberacionEEFFDTO.getNumFactura());
				if (liberacionEEFFDTO.getImporte() != null) {
					fmEEFF.setIMPORTE(liberacionEEFFDTO.getImporte().toString());
				}
				if (liberacionEEFFDTO.getNifRepresentado() != null) {
					fmEEFF.setNIFREPRESENTADO(liberacionEEFFDTO.getNifRepresentado().trim());
				}
				if (liberacionEEFFDTO.getFechaFactura() != null) {
					fmEEFF.setFECHAFACTURA(liberacionEEFFDTO.getFechaFactura().toString());
				}
				if (null != liberacionEEFFDTO.getFechaRealizacion()) {
					fmEEFF.setFECHAREALIZACION(liberacionEEFFDTO.getFechaRealizacion().toString());
				}
				mat.setDATOSLIBERACION(fmEEFF);
			} else {
				mat.setEXENTOLIBERAR("SI");
			}
		}

		// Flag. Indica si meter titular en la matriculación.
		boolean hayTitular = false;

		// DATOS TITULAR
		FORMATOGA.MATRICULACION.DATOSTITULAR datostitular = factory.createFORMATOGAMATRICULACIONDATOSTITULAR();

		// INTERVINIENTE DE TRAFICO : TITULAR
		IntervinienteTrafico titular = bean.getTitularBean();
		if (titular != null) {

			// AUTONOMO
			if (titular.getAutonomo() != null && !titular.getAutonomo().equals("false")) {
				datostitular.setAUTONOMO("SI");
				hayTitular = true;
			}

			// CODIGO IAE
			if (titular.getCodigoIAE() != null && !titular.getCodigoIAE().equals("false")) {
				datostitular.setCODIGOIAE(titular.getCodigoIAE());
				hayTitular = true;
			}

			// CAMBIO DE DOMICILIO
			if (titular.getCambioDomicilio() != null && !titular.getCambioDomicilio().equals("false")) {
				datostitular.setCAMBIODOMICILIOTITULAR("SI");
				hayTitular = true;
			}

			// PERSONA DEL INTERVINIENTE : TITULAR
			Persona personaTitular = titular.getPersona();
			if (personaTitular != null) {

				// RAZÓN SOCIAL
				if (personaTitular.getNif() != null && !utils.isNifNie(personaTitular.getNif()) && personaTitular.getApellido1RazonSocial() != null && !personaTitular.getApellido1RazonSocial().equals(
						"")) {
					datostitular.setRAZONSOCIALTITULAR(personaTitular.getApellido1RazonSocial());
					hayTitular = true;
				}

				// NIF
				if (personaTitular.getNif() != null && !personaTitular.getNif().equals("")) {
					datostitular.setDNITITULAR(personaTitular.getNif());
					hayTitular = true;
				}

				// ANAGRAMA
				if (personaTitular.getAnagrama() != null && !personaTitular.getAnagrama().equals("")) {
					datostitular.setANAGRAMATITULAR(personaTitular.getAnagrama());
					hayTitular = true;
				}

				// APELLIDO 1
				if (personaTitular.getNif() != null && utils.isNifNie(personaTitular.getNif()) && personaTitular.getApellido1RazonSocial() != null && !personaTitular.getApellido1RazonSocial().equals(
						"")) {
					datostitular.setAPELLIDO1TITULAR(personaTitular.getApellido1RazonSocial());
					hayTitular = true;
				}

				// APELLIDO 2
				if (personaTitular.getApellido2() != null && !personaTitular.getApellido2().equals("")) {
					datostitular.setAPELLIDO2TITULAR(personaTitular.getApellido2());
					hayTitular = true;
				}

				// NOMBRE
				if (personaTitular.getNombre() != null && !personaTitular.getNombre().equals("")) {
					datostitular.setNOMBRETITULAR(personaTitular.getNombre());
					hayTitular = true;
				}

				// FECHA NACIMIENTO
				if (personaTitular.getFechaNacimiento() != null && !personaTitular.getFechaNacimiento().toString().equals("")) {
					datostitular.setFECHANACIMIENTOTITULAR(utilesFecha.getFechaFracionada(personaTitular.getFechaNacimiento()).toString());
					hayTitular = true;
				}

				// SEXO
				if (personaTitular.getSexo() != null && !personaTitular.getSexo().equals("")) {
					datostitular.setSEXOTITULAR(SexoPersonaMatw.convertirAformatoGA(personaTitular.getSexo()));
					hayTitular = true;
				}

				// ESTADO CIVIL
				if (personaTitular.getEstadoCivil() != null && personaTitular.getEstadoCivil().getValorEnum() != null && !personaTitular.getEstadoCivil().getValorEnum().equals("")) {
					datostitular.setESTADOCIVILTITULAR(EstadoCivilMatw.convertirAFormatoGA(personaTitular.getEstadoCivil().getValorEnum()));
					hayTitular = true;
				}

				// TELEFONO TITULAR
				if (personaTitular.getTelefonos() != null && personaTitular.getTelefonos().equals("")) {
					datostitular.setTELEFONOTITULAR(personaTitular.getTelefonos());
					hayTitular = true;
				}

				// CORREO ELECTRONICO
				if (personaTitular.getCorreoElectronico() != null && !personaTitular.getCorreoElectronico().equals("")) {
					datostitular.setCORREOELECTRONICOTITULAR(personaTitular.getCorreoElectronico());
					hayTitular = true;
				}
				// FECHA CADUCIDAD NIF
				if (personaTitular.getFechaCaducidadNif() != null && !personaTitular.getFechaCaducidadNif().equals("")) {
					datostitular.setFECHACADUCIDADNIFTITULAR(personaTitular.getFechaCaducidadNif().toString());
					hayTitular = true;
				}
				// FECHA CADUCIDAD ALTERNATIVO
				if (personaTitular.getFechaCaducidadAlternativo() != null && !personaTitular.getFechaCaducidadAlternativo().equals("")) {
					datostitular.setFECHACADUCIDADDOCUMENTOSUSTITUTIVOTITULAR(personaTitular.getFechaCaducidadAlternativo().toString());
					hayTitular = true;
				}
				// TIPO DOCUMENTO SUSTITUTIVO
				if (personaTitular.getTipoDocumentoAlternativo() != null && !personaTitular.getTipoDocumentoAlternativo().equals("")) {
					datostitular.setTIPODOCUMENTOSUSTITUTIVOTITULAR(personaTitular.getTipoDocumentoAlternativo().toString());
					hayTitular = true;
				}

				// DIRECCION DEL INTERVINIENTE : TITULAR
				Direccion direccion = personaTitular.getDireccion();
				if (direccion != null) {

					// TIPO VIA
					if (direccion.getTipoVia() != null && direccion.getTipoVia().getIdTipoVia() != null) {
						if (utils.getIdTipoViaDGTFromIdTipoVia(direccion.getTipoVia().getIdTipoVia()) != null && !utils.getIdTipoViaDGTFromIdTipoVia(direccion.getTipoVia().getIdTipoVia()).equals(
								"")) {
							datostitular.setSIGLASDIRECCIONTITULAR(utils.getIdTipoViaDGTFromIdTipoVia(direccion.getTipoVia().getIdTipoVia()));
							hayTitular = true;
						}
					}

					// NOMBRE VIA
					if (direccion.getNombreVia() != null && !direccion.getNombreVia().equals("")) {
						datostitular.setNOMBREVIADIRECCIONTITULAR(direccion.getNombreVia());
						hayTitular = true;
					}

					// NUMERO
					if (direccion.getNumero() != null && !direccion.getNumero().equals("")) {
						datostitular.setNUMERODIRECCIONTITULAR(direccion.getNumero());
						hayTitular = true;
					}

					// LETRA
					if (direccion.getLetra() != null && !direccion.getLetra().equals("")) {
						datostitular.setLETRADIRECCIONTITULAR(direccion.getLetra());
						hayTitular = true;
					}

					// ESCALERA
					if (direccion.getEscalera() != null && !direccion.getEscalera().equals("")) {
						datostitular.setESCALERADIRECCIONTITULAR(direccion.getEscalera());
						hayTitular = true;
					}

					// PISO
					if (direccion.getPlanta() != null && !direccion.getPlanta().equals("")) {
						datostitular.setPISODIRECCIONTITULAR(direccion.getPlanta());
						hayTitular = true;
					}

					// PUERTA
					if (direccion.getPuerta() != null && !direccion.getPuerta().equals("")) {
						datostitular.setPUERTADIRECCIONTITULAR(direccion.getPuerta());
						hayTitular = true;
					}

					// KILOMETRO
					if (direccion.getPuntoKilometrico() != null && !direccion.getPuntoKilometrico().equals("")) {
						datostitular.setKMDIRECCIONTITULAR(direccion.getPuntoKilometrico());
						hayTitular = true;
					}

					// HECTOMETRO
					if (direccion.getHm() != null && !direccion.getHm().equals("")) {
						datostitular.setHECTOMETRODIRECCIONTITULAR(direccion.getHm());
						hayTitular = true;
					}

					// BLOQUE
					if (direccion.getBloque() != null && !direccion.getBloque().equals("")) {
						datostitular.setBLOQUEDIRECCIONTITULAR(direccion.getBloque());
						hayTitular = true;
					}

					// MUNICIPIO
					if (direccion.getMunicipio() != null && direccion.getMunicipio().getNombre() != null && !direccion.getMunicipio().getNombre().equals("")) {
						datostitular.setMUNICIPIOTITULAR(direccion.getMunicipio().getNombre());
						hayTitular = true;
					}

					// PUEBLO
					if (direccion.getPuebloCorreos() != null && !direccion.getPuebloCorreos().equals("")) {
						datostitular.setPUEBLOTITULAR(direccion.getPuebloCorreos());
						hayTitular = true;
					}

					// PROVINCIA
					if (direccion.getMunicipio() != null && direccion.getMunicipio().getProvincia() != null && direccion.getMunicipio().getProvincia().getIdProvincia() != null && !direccion
							.getMunicipio().getProvincia().getIdProvincia().equals("")) {
						if (utils.getSiglasFromIdProvinciaMatw(direccion.getMunicipio().getProvincia().getIdProvincia()) != null && !utils.getSiglasFromIdProvinciaMatw(direccion.getMunicipio()
								.getProvincia().getIdProvincia()).equals("")) {
							datostitular.setPROVINCIATITULAR(TipoProvincia.fromValue(utils.getSiglasFromIdProvinciaMatw(direccion.getMunicipio().getProvincia().getIdProvincia())));
							hayTitular = true;
						}

						// CODIGO POSTAL
						if (direccion.getCodPostalCorreos() != null && !direccion.getCodPostalCorreos().equals("")) {
							datostitular.setCPTITULAR(direccion.getCodPostalCorreos());
							hayTitular = true;
						}

					}
				}
			}
		}

		// Comprueba el flag para setear titular o no en el elemento raiz mat:
		if (hayTitular) {
			mat.setDATOSTITULAR(datostitular);
		}

		// DATOS REPRESENTANTE TITULAR
		FORMATOGA.MATRICULACION.DATOSREPRESENTANTETITULAR datosrepresentante = factory.createFORMATOGAMATRICULACIONDATOSREPRESENTANTETITULAR();

		// Flag. Indica si meter representante del titular en la matriculación.
		boolean hayRepresentante = false;

		// INTERVINIENTE DE TRAFICO : REPRESENTANTE
		IntervinienteTrafico representante = bean.getRepresentanteTitularBean();
		if (representante != null) {

			// CONCEPTO REPRESENTACION
			if (representante.getConceptoRepre() != null && representante.getConceptoRepre().getValorEnum() != null && !representante.getConceptoRepre().getValorEnum().equals("")) {
				datosrepresentante.setCONCEPTOREPTITULAR(representante.getConceptoRepre().getValorEnum());
				// Si el concepto es 'tutela o patria potestad' puede llevar 'MOTIVO'
				if (representante.getConceptoRepre() == ConceptoTutela.Tutela) {
					if (representante.getIdMotivoTutela() != null) {
						datosrepresentante.setMOTIVOTUTELA(TipoMotivoTutela.valueOf(representante.getIdMotivoTutela().getValorEnum()));
					}
				}
				hayRepresentante = true;
			}

			// MOTIVO REPRESENTACION

			// ACREDITACION
			if (representante.getDatosDocumento() != null && representante.getDatosDocumento().equals("")) {
				datosrepresentante.setACREDITACIONREPTITULAR(representante.getDatosDocumento());
				hayRepresentante = true;
			}

			// FECHA FIN TUTELA
			if (representante.getFechaFin() != null && !representante.getFechaFin().toString().equals("")) {
				datosrepresentante.setFECHAFINTUTELA(representante.getFechaFin().toString());
				hayRepresentante = true;
			}

			// PERSONA DEL INTERVINIENTE : REPRESENTANTE
			Persona personaRepresentante = representante.getPersona();
			if (personaRepresentante != null) {

				// RAZÓN SOCIAL
				if (personaRepresentante.getNif() != null && !utils.isNifNie(personaRepresentante.getNif()) && personaRepresentante.getApellido1RazonSocial() != null && !personaRepresentante
						.getApellido1RazonSocial().equals("")) {
					datosrepresentante.setRAZONSOCIALREP(personaRepresentante.getApellido1RazonSocial());
					hayRepresentante = true;
				}

				// NIF
				if (personaRepresentante.getNif() != null && !personaRepresentante.getNif().equals("")) {
					datosrepresentante.setDNIREP(personaRepresentante.getNif());
					hayRepresentante = true;
				}

				// APELLIDO 1
				if (personaRepresentante.getNif() != null && utils.isNifNie(personaRepresentante.getNif()) && personaRepresentante.getApellido1RazonSocial() != null && !personaRepresentante
						.getApellido1RazonSocial().equals("")) {
					datosrepresentante.setAPELLIDO1REP(personaRepresentante.getApellido1RazonSocial());
					hayRepresentante = true;
				}

				// APELLIDO 2
				if (personaRepresentante.getApellido2() != null && !personaRepresentante.getApellido2().equals("")) {
					datosrepresentante.setAPELLIDO2REP(personaRepresentante.getApellido2());
					hayRepresentante = true;
				}

				// NOMBRE
				if (personaRepresentante.getNombre() != null && !personaRepresentante.getNombre().equals("")) {
					datosrepresentante.setNOMBREREP(personaRepresentante.getNombre());
					hayRepresentante = true;
				}

				// FECHA NACIMIENTO
				if (personaRepresentante.getFechaNacimiento() != null && !personaRepresentante.getFechaNacimiento().toString().equals("")) {
					datosrepresentante.setFECHANACIMIENTOREP(utilesFecha.getFechaFracionada(personaRepresentante.getFechaNacimiento()).toString());
					hayRepresentante = true;
				}

				// SEXO
				if (personaRepresentante.getSexo() != null && !personaRepresentante.getSexo().equals("")) {
					datosrepresentante.setSEXOREP(SexoPersonaMatw.convertirAformatoGA(personaRepresentante.getSexo()));
					hayRepresentante = true;
				}

				// ESTADO CIVIL
				if (personaRepresentante.getEstadoCivil() != null && personaRepresentante.getEstadoCivil().getValorEnum() != null && !personaRepresentante.getEstadoCivil().getValorEnum().equals("")) {
					datosrepresentante.setESTADOCIVILREP(EstadoCivilMatw.convertirAFormatoGA(personaRepresentante.getEstadoCivil().getValorEnum()));
					hayRepresentante = true;
				}
				// FECHA CADUCIDAD NIF
				if (personaRepresentante.getFechaCaducidadNif() != null && !personaRepresentante.getFechaCaducidadNif().equals("")) {
					datosrepresentante.setFECHACADUCIDADNIFREPRESENTANTETITULAR(personaRepresentante.getFechaCaducidadNif().toString());
					hayRepresentante = true;
				}
				// FECHA CADUCIDAD ALTERNATIVO
				if (personaRepresentante.getFechaCaducidadAlternativo() != null && !personaRepresentante.getFechaCaducidadAlternativo().equals("")) {
					datosrepresentante.setFECHACADUCIDADDOCUMENTOSUSTITUTIVOREPRESENTANTETITULAR(personaRepresentante.getFechaCaducidadAlternativo().toString());
					hayRepresentante = true;
				}
				// TIPO DOCUMENTO SUSTITUTIVO
				if (personaRepresentante.getTipoDocumentoAlternativo() != null && !personaRepresentante.getTipoDocumentoAlternativo().equals("")) {
					datosrepresentante.setTIPODOCUMENTOSUSTITUTIVOREPRESENTANTETITULAR(personaRepresentante.getTipoDocumentoAlternativo().toString());
					hayRepresentante = true;
				}

				// DIRECCION DEL INTERVINIENTE : REPRESENTANTE
				Direccion direccion = personaRepresentante.getDireccion();
				if (direccion != null) {

					// TIPO VIA
					if (direccion.getTipoVia() != null && direccion.getTipoVia().getIdTipoVia() != null) {
						if (utils.getIdTipoViaDGTFromIdTipoVia(direccion.getTipoVia().getIdTipoVia()) != null && !utils.getIdTipoViaDGTFromIdTipoVia(direccion.getTipoVia().getIdTipoVia()).equals(
								"")) {
							datosrepresentante.setSIGLASDIRECCIONREP(utils.getIdTipoViaDGTFromIdTipoVia(direccion.getTipoVia().getIdTipoVia()));
						}
						hayRepresentante = true;
					}

					// NOMBRE VIA
					if (direccion.getNombreVia() != null && !direccion.getNombreVia().equals("")) {
						datosrepresentante.setNOMBREVIADIRECCIONREP(direccion.getNombreVia());
						hayRepresentante = true;
					}

					// NUMERO
					if (direccion.getNumero() != null && !direccion.getNumero().equals("")) {
						datosrepresentante.setNUMERODIRECCIONREP(direccion.getNumero());
						hayRepresentante = true;
					}

					// LETRA
					if (direccion.getLetra() != null && !direccion.getLetra().equals("")) {
						datosrepresentante.setLETRADIRECCIONREP(direccion.getLetra());
						hayRepresentante = true;
					}

					// ESCALERA
					if (direccion.getEscalera() != null && !direccion.getEscalera().equals("")) {
						datosrepresentante.setESCALERADIRECCIONREP(direccion.getEscalera());
						hayRepresentante = true;
					}

					// PISO
					if (direccion.getPlanta() != null && !direccion.getPlanta().equals("")) {
						datosrepresentante.setPISODIRECCIONREP(direccion.getPlanta());
						hayRepresentante = true;
					}

					// PUERTA
					if (direccion.getPuerta() != null && !direccion.getPuerta().equals("")) {
						datosrepresentante.setPUERTADIRECCIONREP(direccion.getPuerta());
						hayRepresentante = true;
					}

					// KILOMETRO
					if (direccion.getPuntoKilometrico() != null && !direccion.getPuntoKilometrico().equals("")) {
						datosrepresentante.setKMDIRECCIONREP(direccion.getPuntoKilometrico());
						hayRepresentante = true;
					}

					// HECTOMETRO
					if (direccion.getHm() != null && !direccion.getHm().equals("")) {
						datosrepresentante.setHECTOMETRODIRECCIONREP(direccion.getHm());
						hayRepresentante = true;
					}

					// BLOQUE
					if (direccion.getBloque() != null && !direccion.getBloque().equals("")) {
						datosrepresentante.setBLOQUEDIRECCIONREP(direccion.getBloque());
						hayRepresentante = true;
					}

					// MUNICIPIO
					if (direccion.getMunicipio() != null && direccion.getMunicipio().getNombre() != null && !direccion.getMunicipio().getNombre().equals("")) {
						datosrepresentante.setMUNICIPIOREP(direccion.getMunicipio().getNombre());
						hayRepresentante = true;
					}

					// PUEBLO
					if (direccion.getPuebloCorreos() != null && !direccion.getPuebloCorreos().equals("")) {
						datosrepresentante.setPUEBLOREP(direccion.getPuebloCorreos());
						hayRepresentante = true;
					}

					// PROVINCIA
					if (direccion.getMunicipio() != null && direccion.getMunicipio().getProvincia() != null && direccion.getMunicipio().getProvincia().getIdProvincia() != null && !direccion
							.getMunicipio().getProvincia().getIdProvincia().equals("") && utils.getSiglasFromIdProvinciaMatw(direccion.getMunicipio().getProvincia().getIdProvincia()) != null && !utils
									.getSiglasFromIdProvinciaMatw(direccion.getMunicipio().getProvincia().getIdProvincia()).equals("")) {

						datosrepresentante.setPROVINCIAREP(TipoProvincia.fromValue(utils.getSiglasFromIdProvinciaMatw(direccion.getMunicipio().getProvincia().getIdProvincia())));

						hayRepresentante = true;
					}

					// CODIGO POSTAL
					if (direccion.getCodPostalCorreos() != null && !direccion.getCodPostalCorreos().equals("")) {
						datosrepresentante.setCPREP(direccion.getCodPostalCorreos());
						hayRepresentante = true;
					}

				}
			}
		}

		// Comprueba el flag para setear titular o no en el elemento raiz mat:
		if (hayRepresentante) {
			mat.setDATOSREPRESENTANTETITULAR(datosrepresentante);
		}

		// DATOS ARRENDATARIO
		if ("true".equals(bean.getTramiteTraficoBean().getRenting())) {

			FORMATOGA.MATRICULACION.DATOSARRENDATARIO datosarrendatario = factory.createFORMATOGAMATRICULACIONDATOSARRENDATARIO();

			// Flag. Indica si meter arrendatario en la matriculación.
			boolean hayArrendatario = false;

			// INTERVINIENTE DE TRAFICO : ARRENDATARIO
			IntervinienteTrafico arrendatario = bean.getArrendatarioBean();
			if (arrendatario != null) {

				// CAMBIO DE DOMICILIO
				if (arrendatario.getCambioDomicilio() != null && !arrendatario.getCambioDomicilio().equals("false")) {
					datosarrendatario.setCAMBIODOMICILIOARR("SI");
					hayArrendatario = true;
				}

				// FECHA INICIO RENTING
				if (arrendatario.getFechaInicio() != null && !arrendatario.getFechaInicio().toString().equals("")) {
					datosarrendatario.setFECHAINICIORENTING(arrendatario.getFechaInicio().toString());
					hayArrendatario = true;
				}

				// FECHA FIN RENTING
				if (arrendatario.getFechaFin() != null && !arrendatario.getFechaFin().toString().equals("")) {
					datosarrendatario.setFECHAFINRENTING(arrendatario.getFechaFin().toString());
					hayArrendatario = true;
				}

				// HORA FIN RENTING
				// Comprueba que las horas tienen valor distinto de cadena vacía
				// Solo los setea para el xml si tienen longitud de 4 o 5 con dos puntos.
				if (arrendatario.getHoraFin() != null && !arrendatario.getHoraFin().equals("")) {
					if (arrendatario.getHoraFin().length() == 4) {
						datosarrendatario.setHORAFINRENTING(arrendatario.getHoraFin());
					}
					// Si tiene length 5 puede llevar dos puntos, si los lleva los quita:
					if (arrendatario.getHoraFin().length() == 5 && arrendatario.getHoraFin().contains(":")) {
						String horaFinSinPuntos = arrendatario.getHoraFin().replace(":", "");
						datosarrendatario.setHORAFINRENTING(horaFinSinPuntos);
					}
				}
				// HORA INICIO RENTING
				if (arrendatario.getHoraInicio() != null && !arrendatario.getHoraInicio().equals("")) {
					if (arrendatario.getHoraInicio().length() == 4) {
						datosarrendatario.setHORAINICIORENTING(arrendatario.getHoraInicio());
					}
					// Si tiene length 5 puede llevar dos puntos, si los lleva los quita:
					if (arrendatario.getHoraInicio().length() == 5 && arrendatario.getHoraInicio().contains(":")) {
						String horaInicioSinPuntos = arrendatario.getHoraInicio().replace(":", "");
						datosarrendatario.setHORAINICIORENTING(horaInicioSinPuntos);
					}
				}

				// PERSONA DEL INTERVINIENTE : ARRENDATARIO
				Persona personaArrendatario = arrendatario.getPersona();
				if (personaArrendatario != null) {

					// RAZÓN SOCIAL
					if (personaArrendatario.getNif() != null && !utils.isNifNie(personaArrendatario.getNif()) && personaArrendatario.getApellido1RazonSocial() != null && !personaArrendatario
							.getApellido1RazonSocial().equals("")) {
						datosarrendatario.setRAZONSOCIALARR(personaArrendatario.getApellido1RazonSocial());
						hayArrendatario = true;
					}

					// NIF
					if (personaArrendatario.getNif() != null && !personaArrendatario.getNif().equals("")) {
						datosarrendatario.setDNIARR(personaArrendatario.getNif());
						hayArrendatario = true;
					}

					// APELLIDO 1
					if (personaArrendatario.getNif() != null && utils.isNifNie(personaArrendatario.getNif()) && personaArrendatario.getApellido1RazonSocial() != null && !personaArrendatario
							.getApellido1RazonSocial().equals("")) {
						datosarrendatario.setAPELLIDO1ARR(personaArrendatario.getApellido1RazonSocial());
						hayArrendatario = true;
					}

					// APELLIDO 2
					if (personaArrendatario.getApellido2() != null && !personaArrendatario.getApellido2().equals("")) {
						datosarrendatario.setAPELLIDO2ARR(personaArrendatario.getApellido2());
						hayArrendatario = true;
					}

					// NOMBRE
					if (personaArrendatario.getNombre() != null && !personaArrendatario.getNombre().equals("")) {
						datosarrendatario.setNOMBREARR(personaArrendatario.getNombre());
						hayArrendatario = true;
					}

					// FECHA NACIMIENTO
					if (personaArrendatario.getFechaNacimiento() != null && !personaArrendatario.getFechaNacimiento().toString().equals("")) {
						datosarrendatario.setFECHANACIMIENTOARR(personaArrendatario.getFechaNacimientoBean().toString());
						hayArrendatario = true;
					}

					// SEXO
					if (personaArrendatario.getSexo() != null && !personaArrendatario.getSexo().equals("")) {
						datosarrendatario.setSEXOARR(SexoPersonaMatw.convertirAformatoGA(personaArrendatario.getSexo()));
						hayArrendatario = true;
					}
					// FECHA CADUCIDAD NIF
					if (personaArrendatario.getFechaCaducidadNif() != null && !personaArrendatario.getFechaCaducidadNif().equals("")) {
						datosarrendatario.setFECHACADUCIDADNIFARR(personaArrendatario.getFechaCaducidadNif().toString());
						hayArrendatario = true;
					}
					// FECHA CADUCIDAD ALTERNATIVO
					if (personaArrendatario.getFechaCaducidadAlternativo() != null && !personaArrendatario.getFechaCaducidadAlternativo().equals("")) {
						datosarrendatario.setFECHACADUCIDADDOCUMENTOSUSTITUTIVOARR(personaArrendatario.getFechaCaducidadAlternativo().toString());
						hayArrendatario = true;
					}
					// TIPO DOCUMENTO SUSTITUTIVO
					if (personaArrendatario.getTipoDocumentoAlternativo() != null && !personaArrendatario.getTipoDocumentoAlternativo().equals("")) {
						datosarrendatario.setTIPODOCUMENTOSUSTITUTIVOARR(personaArrendatario.getTipoDocumentoAlternativo().toString());
						hayArrendatario = true;
					}

					// DIRECCION DEL INTERVINIENTE : ARRENDATARIO
					Direccion direccion = personaArrendatario.getDireccion();
					if (direccion != null) {

						// TIPO VIA
						if (direccion.getTipoVia() != null && direccion.getTipoVia().getIdTipoVia() != null && utils.getIdTipoViaDGTFromIdTipoVia(direccion.getTipoVia().getIdTipoVia()) != null
								&& !utils.getIdTipoViaDGTFromIdTipoVia(direccion.getTipoVia().getIdTipoVia()).equals("")) {
							datosarrendatario.setSIGLASDIRECCIONARR(utils.getIdTipoViaDGTFromIdTipoVia(direccion.getTipoVia().getIdTipoVia()));
							hayArrendatario = true;
						}

						// NOMBRE VIA
						if (direccion.getNombreVia() != null && !direccion.getNombreVia().equals("")) {
							datosarrendatario.setNOMBREVIADIRECCIONARR(direccion.getNombreVia());
							hayArrendatario = true;
						}

						// NUMERO
						if (direccion.getNumero() != null && !direccion.getNumero().equals("")) {
							datosarrendatario.setNUMERODIRECCIONARR(direccion.getNumero());
							hayTitular = true;
						}

						// LETRA
						if (direccion.getLetra() != null && !direccion.getLetra().equals("")) {
							datosarrendatario.setLETRADIRECCIONARR(direccion.getLetra());
							hayArrendatario = true;
						}

						// ESCALERA
						if (direccion.getEscalera() != null && !direccion.getEscalera().equals("")) {
							datosarrendatario.setESCALERADIRECCIONARR(direccion.getEscalera());
							hayArrendatario = true;
						}

						// PISO
						if (direccion.getPlanta() != null && !direccion.getPlanta().equals("")) {
							datosarrendatario.setPISODIRECCIONARR(direccion.getPlanta());
							hayArrendatario = true;
						}

						// PUERTA
						if (direccion.getPuerta() != null && !direccion.getPuerta().equals("")) {
							datosarrendatario.setPUERTADIRECCIONARR(direccion.getPuerta());
							hayArrendatario = true;
						}

						// KILOMETRO
						if (direccion.getPuntoKilometrico() != null && !direccion.getPuntoKilometrico().equals("")) {
							datosarrendatario.setKMDIRECCIONARR(direccion.getPuntoKilometrico());
							hayArrendatario = true;
						}

						// HECTOMETRO
						if (direccion.getHm() != null && !direccion.getHm().equals("")) {
							datosarrendatario.setHECTOMETRODIRECCIONARR(direccion.getHm());
							hayArrendatario = true;
						}

						// BLOQUE
						if (direccion.getBloque() != null && !direccion.getBloque().equals("")) {
							datosarrendatario.setBLOQUEDIRECCIONARR(direccion.getBloque());
							hayArrendatario = true;
						}

						// MUNICIPIO
						if (direccion.getMunicipio() != null && direccion.getMunicipio().getNombre() != null && !direccion.getMunicipio().getNombre().equals("")) {
							datosarrendatario.setMUNICIPIOARR(direccion.getMunicipio().getNombre());
							hayArrendatario = true;
						}

						// PUEBLO
						if (direccion.getPueblo() != null && !direccion.getPueblo().equals("")) {
							datosarrendatario.setPUEBLOARR(direccion.getPueblo());
							hayArrendatario = true;
						}

						// PROVINCIA
						if (direccion.getMunicipio() != null && direccion.getMunicipio().getProvincia() != null && direccion.getMunicipio().getProvincia().getIdProvincia() != null && !direccion
								.getMunicipio().getProvincia().getIdProvincia().equals("") && utils.getSiglasFromIdProvinciaMatw(direccion.getMunicipio().getProvincia().getIdProvincia()) != null
								&& !utils.getSiglasFromIdProvinciaMatw(direccion.getMunicipio().getProvincia().getIdProvincia()).equals("")) {
							datosarrendatario.setPROVINCIAARR(TipoProvincia.fromValue(utils.getSiglasFromIdProvinciaMatw(direccion.getMunicipio().getProvincia().getIdProvincia())));
							hayArrendatario = true;
						}

						// CODIGO POSTAL
						if (direccion.getCodPostal() != null && !direccion.getCodPostal().equals("")) {
							datosarrendatario.setCPARR(direccion.getCodPostal());
							hayArrendatario = true;
						}

					}
				}

				// Comprueba el flag para setear arrendatario o no en el elemento raiz mat:
				if (hayArrendatario) {
					mat.setDATOSARRENDATARIO(datosarrendatario);
				}
			}

			// DATOS REPRESENTANTE ARRENDATARIO
			FORMATOGA.MATRICULACION.DATOSREPRESENTANTEARRENDATARIO datosrepresentanteArrendatario = factory.createFORMATOGAMATRICULACIONDATOSREPRESENTANTEARRENDATARIO();
			// Flag. Indica si meter representante del ARRENDATARIO en la matriculación.
			boolean hayRepresentanteArrendatario = false;

			// INTERVINIENTE DE TRAFICO : REPRESENTANTE DEL Arrendatario
			IntervinienteTrafico representanteArrendatario = bean.getRepresentanteArrendatarioBean();
			if (datosrepresentanteArrendatario != null) {

				// PERSONA DEL INTERVINIENTE : REPRESENTANTE DEL Arrendatario
				Persona personaRepresentanteArrendatario = representanteArrendatario.getPersona();
				if (representanteArrendatario != null) {

					// NIF
					if (personaRepresentanteArrendatario.getNif() != null && !personaRepresentanteArrendatario.getNif().equals("")) {
						datosrepresentanteArrendatario.setDNIREPRESARR(personaRepresentanteArrendatario.getNif());
						hayRepresentanteArrendatario = true;
					}

					// APELLIDO 1
					if (personaRepresentanteArrendatario.getNif() != null && utils.isNifNie(personaRepresentanteArrendatario.getNif()) && personaRepresentanteArrendatario
							.getApellido1RazonSocial() != null && !personaRepresentanteArrendatario.getApellido1RazonSocial().equals("")) {
						datosrepresentanteArrendatario.setAPELLIDO1REPRESARR(personaRepresentanteArrendatario.getApellido1RazonSocial());
						hayRepresentanteArrendatario = true;
					}

					// APELLIDO 2
					if (personaRepresentanteArrendatario.getApellido2() != null && !personaRepresentanteArrendatario.getApellido2().equals("")) {
						datosrepresentanteArrendatario.setAPELLIDO2REPRESARR(personaRepresentanteArrendatario.getApellido2());
						hayRepresentanteArrendatario = true;
					}

					// NOMBRE
					if (personaRepresentanteArrendatario.getNombre() != null && !personaRepresentanteArrendatario.getNombre().equals("")) {
						datosrepresentanteArrendatario.setNOMBREREPRESARR(personaRepresentanteArrendatario.getNombre());
						hayRepresentanteArrendatario = true;

					}
				}
			}

			// Comprueba el flag para setear repreasentante del ARRENDATARIO o no en el elemento raiz mat:
			if (hayRepresentanteArrendatario) {
				mat.setDATOSREPRESENTANTEARRENDATARIO(datosrepresentanteArrendatario);
			}

		}

		// CONDUCTOR HABITUAL
		FORMATOGA.MATRICULACION.DATOSCONDUCTORHABITUAL datosconductor = factory.createFORMATOGAMATRICULACIONDATOSCONDUCTORHABITUAL();
		IntervinienteTrafico conductor = bean.getConductorHabitualBean();

		// Flag. Indica si meter conductor habitual en la matriculación.
		boolean hayConductor = false;

		if (conductor != null) {

			// CAMBIO DE DOMICILIO
			if (conductor.getCambioDomicilio() != null && !conductor.getCambioDomicilio().equals("false")) {
				datosconductor.setCAMBIODOMICILIOCONDHABITUAL("SI");
				hayConductor = true;
			}

			// FECHA FIN CH
			if (conductor.getFechaFin() != null && !conductor.getFechaFin().toString().equals("")) {
				datosconductor.setFECHAFINCONDHABITUAL(conductor.getFechaFin().toString());
				hayConductor = true;
			}

			// HORA FIN CH
			// Comprueba que las horas tienen valor distinto de cadena vacía
			// Solo los setea para el xml si tienen longitud de 4 o 5 con dos puntos.
			if (conductor.getHoraFin() != null && !conductor.getHoraFin().equals("")) {
				if (conductor.getHoraFin().length() == 4) {
					datosconductor.setHORAFINCONDHABITUAL(conductor.getHoraFin());
				}
				// Si tiene length 5 puede llevar dos puntos, si los lleva los quita:
				if (conductor.getHoraFin().length() == 5 && conductor.getHoraFin().contains(":")) {
					String horaFinSinPuntos = conductor.getHoraFin().replace(":", "");
					datosconductor.setHORAFINCONDHABITUAL(horaFinSinPuntos);
				}
			}

			// PERSONA DEL INTERVINIENTE : conductor
			Persona personaconductor = conductor.getPersona();
			if (personaconductor != null) {

				// RAZÓN SOCIAL
				if (personaconductor.getNif() != null && !utils.isNifNie(personaconductor.getNif()) && personaconductor.getApellido1RazonSocial() != null && !personaconductor.getApellido1RazonSocial()
						.equals("")) {
					datosconductor.setRAZONSOCIALCONDHABITUAL(personaconductor.getApellido1RazonSocial());
					hayConductor = true;
				}

				// NIF
				if (personaconductor.getNif() != null && !personaconductor.getNif().equals("")) {
					datosconductor.setDNICONDHABITUAL(personaconductor.getNif());
					hayConductor = true;
				}

				// APELLIDO 1
				if (personaconductor.getNif() != null && utils.isNifNie(personaconductor.getNif()) && personaconductor.getApellido1RazonSocial() != null && !personaconductor.getApellido1RazonSocial()
						.equals("")) {
					datosconductor.setAPELLIDO1CONDHABITUAL(personaconductor.getApellido1RazonSocial());
					hayConductor = true;
				}

				// APELLIDO 2
				if (personaconductor.getApellido2() != null && !personaconductor.getApellido2().equals("")) {
					datosconductor.setAPELLIDO2CONDHABITUAL(personaconductor.getApellido2());
					hayConductor = true;
				}

				// NOMBRE
				if (personaconductor.getNombre() != null && !personaconductor.getNombre().equals("")) {
					datosconductor.setNOMBRECONDHABITUAL(personaconductor.getNombre());
					hayConductor = true;
				}

				// FECHA NACIMIENTO
				if (personaconductor.getFechaNacimiento() != null && !personaconductor.getFechaNacimiento().toString().equals("")) {
					datosconductor.setFECHANACIMIENTOCONDHABITUAL(personaconductor.getFechaNacimientoBean().toString());
					hayConductor = true;
				}

				// SEXO
				if (personaconductor.getSexo() != null && !personaconductor.getSexo().equals("")) {
					datosconductor.setSEXOCONDHABITUAL(SexoPersonaMatw.convertirAformatoGA(personaconductor.getSexo()));
					hayConductor = true;
				}
				// FECHA CADUCIDAD NIF
				if (personaconductor.getFechaCaducidadNif() != null && !personaconductor.getFechaCaducidadNif().equals("")) {
					datosconductor.setFECHACADUCIDADNIFCONDHABITUAL(personaconductor.getFechaCaducidadNif().toString());
					hayConductor = true;
				}
				// FECHA CADUCIDAD ALTERNATIVO
				if (personaconductor.getFechaCaducidadAlternativo() != null && !personaconductor.getFechaCaducidadAlternativo().equals("")) {
					datosconductor.setFECHACADUCIDADDOCUMENTOSUSTITUTIVOCONDHABITUAL(personaconductor.getFechaCaducidadAlternativo().toString());
					hayConductor = true;
				}
				// TIPO DOCUMENTO SUSTITUTIVO
				if (personaconductor.getTipoDocumentoAlternativo() != null && !personaconductor.getTipoDocumentoAlternativo().equals("")) {
					datosconductor.setTIPODOCUMENTOSUSTITUTIVOCONDHABITUAL(personaconductor.getTipoDocumentoAlternativo().toString());
					hayConductor = true;
				}

				// DIRECCION DEL INTERVINIENTE : conductor
				Direccion direccion = personaconductor.getDireccion();
				if (direccion != null) {

					// TIPO VIA
					if (direccion.getTipoVia() != null && direccion.getTipoVia().getIdTipoVia() != null && utils.getIdTipoViaDGTFromIdTipoVia(direccion.getTipoVia().getIdTipoVia()) != null && !utils
							.getIdTipoViaDGTFromIdTipoVia(direccion.getTipoVia().getIdTipoVia()).equals("")) {
						datosconductor.setSIGLASDIRECCIONCONDHABITUAL(utils.getIdTipoViaDGTFromIdTipoVia(direccion.getTipoVia().getIdTipoVia()));
						hayConductor = true;
					}

					// NOMBRE VIA
					if (direccion.getNombreVia() != null && !direccion.getNombreVia().equals("")) {
						datosconductor.setNOMBREVIADIRECCIONCONDHABITUAL(direccion.getNombreVia());
						hayConductor = true;
					}

					// NUMERO
					if (direccion.getNumero() != null && !direccion.getNumero().equals("")) {
						datosconductor.setNUMERODIRECCIONCONDHABITUAL(direccion.getNumero());
						hayConductor = true;
					}

					// LETRA
					if (direccion.getLetra() != null && !direccion.getLetra().equals("")) {
						datosconductor.setLETRADIRECCIONCONDHABITUAL(direccion.getLetra());
						hayConductor = true;
					}

					// ESCALERA
					if (direccion.getEscalera() != null && !direccion.getEscalera().equals("")) {
						datosconductor.setESCALERADIRECCIONCONDHABITUAL(direccion.getEscalera());
						hayConductor = true;
					}

					// PISO
					if (direccion.getPlanta() != null && !direccion.getPlanta().equals("")) {
						datosconductor.setPISODIRECCIONCONDHABITUAL(direccion.getPlanta());
						hayConductor = true;
					}

					// PUERTA
					if (direccion.getPuerta() != null && !direccion.getPuerta().equals("")) {
						datosconductor.setPUERTADIRECCIONCONDHABITUAL(direccion.getPuerta());
						hayConductor = true;
					}

					// KILOMETRO
					if (direccion.getPuntoKilometrico() != null && !direccion.getPuntoKilometrico().equals("")) {
						datosconductor.setKMDIRECCIONCONDHABITUAL(direccion.getPuntoKilometrico());
						hayConductor = true;
					}

					// HECTOMETRO
					if (direccion.getHm() != null && !direccion.getHm().equals("")) {
						datosconductor.setHECTOMETRODIRECCIONCONDHABITUAL(direccion.getHm());
						hayConductor = true;
					}

					// BLOQUE
					if (direccion.getBloque() != null && !direccion.getBloque().equals("")) {
						datosconductor.setBLOQUEDIRECCIONCONDHABITUAL(direccion.getBloque());
						hayConductor = true;
					}

					// MUNICIPIO
					if (direccion.getMunicipio() != null && direccion.getMunicipio().getNombre() != null && !direccion.getMunicipio().getNombre().equals("")) {
						datosconductor.setMUNICIPIOCONDHABITUAL(direccion.getMunicipio().getNombre());
						hayConductor = true;
					}

					// PUEBLO
					if (direccion.getPueblo() != null && !direccion.getPueblo().equals("")) {
						datosconductor.setPUEBLOCONDHABITUAL(direccion.getPueblo());
						hayConductor = true;
					}

					// PROVINCIA
					if (direccion.getMunicipio() != null && direccion.getMunicipio().getProvincia() != null && direccion.getMunicipio().getProvincia().getIdProvincia() != null && !direccion
							.getMunicipio().getProvincia().getIdProvincia().equals("") && utils.getSiglasFromIdProvinciaMatw(direccion.getMunicipio().getProvincia().getIdProvincia()) != null && !utils
									.getSiglasFromIdProvinciaMatw(direccion.getMunicipio().getProvincia().getIdProvincia()).equals("")) {
						datosconductor.setPROVINCIACONDHABITUAL(TipoProvincia.fromValue(utils.getSiglasFromIdProvinciaMatw(direccion.getMunicipio().getProvincia().getIdProvincia())));
						hayConductor = true;
					}

					// CODIGO POSTAL
					if (direccion.getCodPostal() != null && !direccion.getCodPostal().equals("")) {
						datosconductor.setCPCONDHABITUAL(direccion.getCodPostal());
						hayConductor = true;
					}

				}
			}

			// Comprueba el flag para setear conductor habitual o no en el elemento raiz mat:
			if (hayConductor) {
				mat.setDATOSCONDUCTORHABITUAL(datosconductor);
			}
		}

		// DATOS REPRESENTANTE COND HABITUAL
		FORMATOGA.MATRICULACION.DATOSREPRESENTANTECONDUCTORHABITUAL datosrepresentanteCH = factory.createFORMATOGAMATRICULACIONDATOSREPRESENTANTECONDUCTORHABITUAL();
		// Flag. Indica si meter representante del CH en la matriculación.
		boolean hayRepresentanteCH = false;

		// INTERVINIENTE DE TRAFICO : REPRESENTANTE DEL CH
		IntervinienteTrafico representanteCH = bean.getRepresentanteConductorHabitualBean();
		if (datosrepresentanteCH != null) {

			// PERSONA DEL INTERVINIENTE : REPRESENTANTE DEL CH
			Persona personaRepresentanteCH = representanteCH.getPersona();
			if (representanteCH != null) {

				// NIF
				if (personaRepresentanteCH.getNif() != null && !personaRepresentanteCH.getNif().equals("")) {
					datosrepresentanteCH.setDNIREPRESCONDHABITUAL(personaRepresentanteCH.getNif());
					hayRepresentanteCH = true;
				}

				// APELLIDO 1
				if (personaRepresentanteCH.getNif() != null && utils.isNifNie(personaRepresentanteCH.getNif()) && personaRepresentanteCH.getApellido1RazonSocial() != null && !personaRepresentanteCH
						.getApellido1RazonSocial().equals("")) {
					datosrepresentanteCH.setAPELLIDO1REPRESCONDHABITUAL(personaRepresentanteCH.getApellido1RazonSocial());
					hayRepresentanteCH = true;
				}

				// APELLIDO 2
				if (personaRepresentanteCH.getApellido2() != null && !personaRepresentanteCH.getApellido2().equals("")) {
					datosrepresentanteCH.setAPELLIDO2REPRESCONDHABITUAL(personaRepresentanteCH.getApellido2());
					hayRepresentanteCH = true;
				}

				// NOMBRE
				if (personaRepresentanteCH.getNombre() != null && !personaRepresentanteCH.getNombre().equals("")) {
					datosrepresentanteCH.setNOMBREREPRESCONDHABITUAL(personaRepresentanteCH.getNombre());
					hayRepresentanteCH = true;

				}
			}
		}

		// Comprueba el flag para setear repreasentante del CH o no en el elemento raiz mat:
		if (hayRepresentanteCH) {
			mat.setDATOSREPRESENTANTECONDUCTORHABITUAL(datosrepresentanteCH);
		}

		VehiculoBean vehiculoBean = bean.getTramiteTraficoBean().getVehiculo();

		boolean hayImportador = false;

		// DATOS IMPORTADOR
		FORMATOGA.MATRICULACION.DATOSIMPORTADOR datosImportador = factory.createFORMATOGAMATRICULACIONDATOSIMPORTADOR();

		// RAZÓN SOCIAL
		if (vehiculoBean.getIntegradorBean() != null && vehiculoBean.getIntegradorBean().getNif() != null && !utils.isNifNie(vehiculoBean.getIntegradorBean().getNif())) {
			datosImportador.setRAZONSOCIALIMPORTADOR(vehiculoBean.getIntegradorBean().getApellido1RazonSocial());
			hayImportador = true;
		}

		// DNI
		if (vehiculoBean.getIntegradorBean() != null && vehiculoBean.getIntegradorBean().getNif() != null) {
			datosImportador.setDNIIMPORTADOR(vehiculoBean.getIntegradorBean().getNif());
			hayImportador = true;
		}

		// APELLIDO 1
		if (vehiculoBean.getIntegradorBean() != null && vehiculoBean.getIntegradorBean().getNif() != null && utils.isNifNie(vehiculoBean.getIntegradorBean().getNif())) {
			datosImportador.setAPELLIDO1IMPORTADOR(vehiculoBean.getIntegradorBean().getApellido1RazonSocial());
			hayImportador = true;
		}

		// APELLIDO 2
		if (vehiculoBean.getIntegradorBean() != null && vehiculoBean.getIntegradorBean().getNif() != null && utils.isNifNie(vehiculoBean.getIntegradorBean().getNif())) {
			datosImportador.setAPELLIDO2IMPORTADOR(vehiculoBean.getIntegradorBean().getApellido2());
			hayImportador = true;
		}

		// NOMBRE
		if (vehiculoBean.getIntegradorBean() != null && vehiculoBean.getIntegradorBean().getNif() != null) {
			datosImportador.setNOMBREIMPORTADOR(vehiculoBean.getIntegradorBean().getNombre());
			hayImportador = true;
		}

		// Comprueba el flag para setear importador o no en el elemento raiz mat:
		if (hayImportador) {
			mat.setDATOSIMPORTADOR(datosImportador);
		}

		// DATOS VEHICULO
		FORMATOGA.MATRICULACION.DATOSVEHICULO vehiculo = factory.createFORMATOGAMATRICULACIONDATOSVEHICULO();

		// Flag. Indica si meter vehiculo en la matriculación.
		boolean hayVehiculo = false;

		// MATRÍCULA
		if (vehiculoBean.getMatricula() != null && !vehiculoBean.getMatricula().equals("")) {
			vehiculo.setMATRICULA(vehiculoBean.getMatricula());
			hayVehiculo = true;
		}

		// FECHA MATRICULACIÓN
		if (vehiculoBean.getFechaMatriculacion() != null && !vehiculoBean.getFechaMatriculacion().isfechaNula()) {
			vehiculo.setFECHAMATRICULACION(vehiculoBean.getFechaMatriculacion().toString());
			hayVehiculo = true;
		}

		// FECHA PRIMERA MATRICULACIÓN
		if (vehiculoBean.getFechaPrimMatri() != null && !vehiculoBean.getFechaPrimMatri().isfechaNula()) {
			vehiculo.setFECHAPRIMERAMATRICULACION(vehiculoBean.getFechaPrimMatri().toString());
			hayVehiculo = true;
		}

		// CODIGO ITV
		if (vehiculoBean.getCodItv() != null && !vehiculoBean.getCodItv().equals("")) {
			vehiculo.setCODIGOITV(vehiculoBean.getCodItv());
			hayVehiculo = true;
		}

		// FECHA ITV
		if (vehiculoBean.getFechaItv() != null && !vehiculoBean.getFechaItv().isfechaNula()) {
			vehiculo.setFECHAITV(vehiculoBean.getFechaItv().toString());
			hayVehiculo = true;
		}

		// NÚMERO DE BASTIDOR
		if (vehiculoBean.getBastidor() != null && !vehiculoBean.getBastidor().equals("")) {
			vehiculo.setNUMEROBASTIDOR(vehiculoBean.getBastidor());
			hayVehiculo = true;
		}

		// TIPO DE SERVICIO
		if (vehiculoBean.getServicioTraficoBean() != null && vehiculoBean.getServicioTraficoBean().getIdServicio() != null && !vehiculoBean.getServicioTraficoBean().getIdServicio().equals("")) {
			vehiculo.setSERVICIODESTINO(TipoServicio.fromValue(vehiculoBean.getServicioTraficoBean().getIdServicio()));
			hayVehiculo = true;
		}

		// CLASIFICACION DEL VEHICULO
		if ((vehiculoBean.getCriterioConstruccionBean() != null && vehiculoBean.getCriterioConstruccionBean().getIdCriterioConstruccion() != null && vehiculoBean.getCriterioConstruccionBean()
				.getIdCriterioConstruccion().length() == 2 && vehiculoBean.getCriterioUtilizacionBean() != null && vehiculoBean.getCriterioUtilizacionBean().getIdCriterioUtilizacion() != null
				&& vehiculoBean.getCriterioUtilizacionBean().getIdCriterioUtilizacion().length() == 2)) {
			vehiculo.setCLASIFICACIONVEHICULO(vehiculoBean.getCriterioConstruccionBean().getIdCriterioConstruccion() + vehiculoBean.getCriterioUtilizacionBean().getIdCriterioUtilizacion());
		}

		// NUEVO
		if (vehiculoBean.getVehiUsado() != null && !vehiculoBean.getVehiUsado().equals("false")) {
			vehiculo.setNUEVO("SI");
			hayVehiculo = true;
		}

		// USADO
		if (vehiculoBean.getVehiUsado() != null && vehiculoBean.getVehiUsado().equals("true")) {
			vehiculo.setUSADO("SI");
			hayVehiculo = true;
		}

		// EPÍGRAFE
		if (vehiculoBean.getEpigrafeBean() != null && vehiculoBean.getEpigrafeBean().getIdEpigrafe() != null && !vehiculoBean.getEpigrafeBean().getIdEpigrafe().equals("") && !vehiculoBean
				.getEpigrafeBean().getIdEpigrafe().equals("-1")) {
			vehiculo.setEPIGRAFE(vehiculoBean.getEpigrafeBean().getIdEpigrafe());
			hayVehiculo = true;
		}

		// KM
		if (vehiculoBean.getKmUso() != null) {
			vehiculo.setKM(vehiculoBean.getKmUso().toBigInteger());
			hayVehiculo = true;
		}

		// HORAS DE USO
		if (vehiculoBean.getHorasUso() != null) {
			vehiculo.setCUENTAHORAS(vehiculoBean.getHorasUso().toBigInteger());
			hayVehiculo = true;
		}

		// NUMERO DE SERIE ITV
		if (vehiculoBean.getNumSerie() != null && !vehiculoBean.getNumSerie().equals("")) {
			vehiculo.setNUMEROSERIEITV(vehiculoBean.getNumSerie());
			hayVehiculo = true;
		}

		// RENTING
		if (bean.getTramiteTraficoBean().getRenting() != null && bean.getTramiteTraficoBean().getRenting().equals("true")) {
			vehiculo.setRENTING("SI");
			hayVehiculo = true;
		}

		// CARSHARING
		if (bean.getTramiteTraficoBean().getCarsharing() != null && bean.getTramiteTraficoBean().getCarsharing().equals("true")) {
			vehiculo.setCarsharing("SI");
			hayVehiculo = true;
		}

		// LOCALIZACIÓN DEL VEHICULO
		Direccion direccion = vehiculoBean.getDireccionBean();
		if (direccion != null) {

			// TIPO VIA
			if (direccion.getTipoVia() != null && direccion.getTipoVia().getIdTipoVia() != null && utils.getIdTipoViaDGTFromIdTipoVia(direccion.getTipoVia().getIdTipoVia()) != null && !utils
					.getIdTipoViaDGTFromIdTipoVia(direccion.getTipoVia().getIdTipoVia()).equals("")) {
				vehiculo.setSIGLASDIRECCIONVEHICULO(utils.getIdTipoViaDGTFromIdTipoVia(direccion.getTipoVia().getIdTipoVia()));
				hayVehiculo = true;
			}

			// NOMBRE VIA
			if (direccion.getNombreVia() != null && !direccion.getNombreVia().equals("")) {
				vehiculo.setDOMICILIOVEHICULO(direccion.getNombreVia());
				hayVehiculo = true;
			}

			// NUMERO
			if (direccion.getNumero() != null && !direccion.getNumero().equals("")) {
				vehiculo.setNUMERODIRECCIONVEHICULO(direccion.getNumero());
				hayVehiculo = true;
			}

			// LETRA
			if (direccion.getLetra() != null && !direccion.getLetra().equals("")) {
				vehiculo.setLETRADIRECCIONVEHICULO(direccion.getLetra());
				hayVehiculo = true;
			}

			// ESCALERA
			if (direccion.getEscalera() != null && !direccion.getEscalera().equals("")) {
				vehiculo.setESCALERADIRECCIONVEHICULO(direccion.getEscalera());
				hayVehiculo = true;
			}

			// PISO
			if (direccion.getPlanta() != null && !direccion.getPlanta().equals("")) {
				vehiculo.setPISODIRECCIONVEHICULO(direccion.getPlanta());
				hayVehiculo = true;
			}

			// PUERTA
			if (direccion.getPuerta() != null && !direccion.getPuerta().equals("")) {
				vehiculo.setPUERTADIRECCIONVEHICULO(direccion.getPuerta());
				hayVehiculo = true;
			}

			// KILOMETRO
			if (direccion.getPuntoKilometrico() != null && !direccion.getPuntoKilometrico().equals("")) {
				vehiculo.setKMDIRECCIONVEHICULO(direccion.getPuntoKilometrico());
				hayVehiculo = true;
			}

			// HECTOMETRO
			if (direccion.getHm() != null && !direccion.getHm().equals("")) {
				vehiculo.setHECTOMETRODIRECCIONVEHICULO(direccion.getHm());
				hayVehiculo = true;
			}

			// BLOQUE
			if (direccion.getBloque() != null && !direccion.getBloque().equals("")) {
				vehiculo.setBLOQUEDIRECCIONVEHICULO(direccion.getBloque());
				hayVehiculo = true;
			}

			// MUNICIPIO
			if (direccion.getMunicipio() != null && direccion.getMunicipio().getNombre() != null && !direccion.getMunicipio().getNombre().equals("")) {
				vehiculo.setMUNICIPIOVEHICULO(direccion.getMunicipio().getNombre());
				hayVehiculo = true;
			}

			// PUEBLO
			if (direccion.getPueblo() != null && !direccion.getPueblo().equals("")) {
				vehiculo.setPUEBLOVEHICULO(direccion.getPueblo());
				hayVehiculo = true;
			}

			// PROVINCIA
			if (direccion.getMunicipio() != null && direccion.getMunicipio().getProvincia() != null && direccion.getMunicipio().getProvincia().getIdProvincia() != null && !direccion.getMunicipio()
					.getProvincia().getIdProvincia().equals("") && utils.getSiglasFromIdProvinciaMatw(direccion.getMunicipio().getProvincia().getIdProvincia()) != null && !utils
							.getSiglasFromIdProvinciaMatw(direccion.getMunicipio().getProvincia().getIdProvincia()).equals("")) {
				vehiculo.setPROVINCIAVEHICULO(TipoProvincia.fromValue(utils.getSiglasFromIdProvinciaMatw(direccion.getMunicipio().getProvincia().getIdProvincia())));
				hayVehiculo = true;
			}

			// CODIGO POSTAL
			if (direccion.getCodPostal() != null && !direccion.getCodPostal().equals("")) {
				vehiculo.setCPVEHICULO(direccion.getCodPostal());
				hayVehiculo = true;
			}

		}

		// ///*********************************** DATOS VEHICULO **********************************************
		// ****************************************************************************************************

		// ---------------------------------------> DATOS TÉCNICOS <-------------------------------------------

		// NUMERO HOMOLOGACION ITV
		if (vehiculoBean.getNumHomologacion() != null && !vehiculoBean.getNumHomologacion().equals("")) {
			vehiculo.setNUMEROHOMOLOGACIONITV(vehiculoBean.getNumHomologacion());
			hayVehiculo = true;
		}

		// CLASIFICACION ITV
		if (vehiculoBean.getClasificacionItv() != null && !vehiculoBean.getClasificacionItv().equals("")) {
			vehiculo.setCLASIFICACIONITV(Integer.valueOf(vehiculoBean.getClasificacionItv()));
			hayVehiculo = true;
		}

		// TIPO TARJETA ITV
		if (vehiculoBean.getTipoTarjetaItvBean() != null && vehiculoBean.getTipoTarjetaItvBean().getIdTipoTarjetaItv() != null && !vehiculoBean.getTipoTarjetaItvBean().getIdTipoTarjetaItv().equals(
				"")) {
			vehiculo.setTIPOTARJETAITV(TipoTarjetaItv.valueOf(vehiculoBean.getTipoTarjetaItvBean().getIdTipoTarjetaItv()));
			hayVehiculo = true;
		}

		// NIVE
		if (vehiculoBean.getNive() != null && !vehiculoBean.getNive().equals("")) {
			vehiculo.setNIVE(vehiculoBean.getNive());
		}

		// TIPO DE INDUSTRIA
		if (vehiculoBean.getTipoIndustria() != null && !vehiculoBean.getTipoIndustria().equals("")) {
			vehiculo.setTIPOINDUSTRIA(vehiculoBean.getTipoIndustria());
			hayVehiculo = true;
		}

		// FICHA TECNICA RD750 (FICHA NUEVA)

		if (vehiculoBean.getFichaTecnicaRD750() != null && !vehiculoBean.getFichaTecnicaRD750().equals("") && vehiculoBean.getFichaTecnicaRD750() == true) {
			vehiculo.setFICHATECNICARD750("SI");
			hayVehiculo = true;
		} else {
			vehiculo.setFICHATECNICARD750("NO");
		}

		// TIPO DE VEHICULO
		if (vehiculoBean.getTipoVehiculoBean() != null && vehiculoBean.getTipoVehiculoBean().getTipoVehiculo() != null && !vehiculoBean.getTipoVehiculoBean().getTipoVehiculo().equals("")) {
			vehiculo.setTIPOVEHICULO(vehiculoBean.getTipoVehiculoBean().getTipoVehiculo());
			hayVehiculo = true;
		}

		// ID_PROCEDENCIA (ANTES FABRICACION)
		if (vehiculoBean.getProcedencia() != null && !vehiculoBean.getProcedencia().equals("")) {
			vehiculo.setPROCEDENCIA(vehiculoBean.getProcedencia());
			hayVehiculo = true;
		}

		// MARCA
		if (vehiculoBean.getMarcaBean() != null && vehiculoBean.getMarcaBean().getMarca() != null && !vehiculoBean.getMarcaBean().getMarca().equals("")) {
			vehiculo.setMARCA(vehiculoBean.getMarcaBean().getMarca());
			hayVehiculo = true;
		}

		// FABRICANTE
		if (vehiculoBean.getFabricante() != null && !vehiculoBean.getFabricante().equals("")) {
			vehiculo.setFABRICANTE(vehiculoBean.getFabricante().toString());
			hayVehiculo = true;
		}

		// MODELO
		if (vehiculoBean.getModelo() != null && !vehiculoBean.getModelo().equals("")) {
			vehiculo.setMODELO(vehiculoBean.getModelo());
			hayVehiculo = true;
		}

		// COLOR
		if (vehiculoBean.getColorBean() != null && vehiculoBean.getColorBean().getValorEnum() != null && !vehiculoBean.getColorBean().getValorEnum().equals("-")) {
			vehiculo.setCOLOR(TipoColor.fromValue(vehiculoBean.getColorBean().getValorEnum()));
			hayVehiculo = true;
		}

		// TIPO ITV
		if (vehiculoBean.getTipoItv() != null && !vehiculoBean.getTipoItv().equals("")) {
			vehiculo.setTIPOITV(vehiculoBean.getTipoItv());
			hayVehiculo = true;
		}

		// VARIANTE ITV
		if (vehiculoBean.getVariante() != null && !vehiculoBean.getVariante().equals("")) {
			vehiculo.setVARIANTEITV(vehiculoBean.getVariante());
			hayVehiculo = true;
		}

		// VERSION ITV
		if (vehiculoBean.getVersion() != null && !vehiculoBean.getVersion().equals("")) {
			vehiculo.setVERSIONITV(vehiculoBean.getVersion());
			hayVehiculo = true;
		}

		// ---------------------------------------> CARACTERÍSTICAS <-------------------------------------------

		// POTENCIA FISCAL
		if (vehiculoBean.getPotenciaFiscal() != null && !vehiculoBean.getPotenciaFiscal().equals("")) {
			vehiculo.setPOTENCIAFISCAL(vehiculoBean.getPotenciaFiscal());
			hayVehiculo = true;
		}

		// CILINDRADA
		if (vehiculoBean.getCilindrada() != null && !vehiculoBean.getCilindrada().equals("")) {
			try {
				vehiculo.setCILINDRADA(new BigDecimal(vehiculoBean.getCilindrada()));
				hayVehiculo = true;
			} catch (NumberFormatException ex) {
				// No se ha seteado la cilindrada por no ser la cadena casteable a BigDecimal
				log.error("ERROR EXPORTACION :" + " La cilindrada debe ser casteable a BigDecimal. Valor: " + vehiculoBean.getCilindrada());
			}
		}

		// TARA
		if (vehiculoBean.getTara() != null && !vehiculoBean.getTara().equals("")) {
			try {
				vehiculo.setTARA(new BigInteger(vehiculoBean.getTara()));
				hayVehiculo = true;
			} catch (NumberFormatException ex) {
				// No se ha seteado la tara por no ser la cadena casteable a BigInteger
				log.error("ERROR EXPORTACION :" + " La tara debe ser casteable a BigInteger. Valor: " + vehiculoBean.getTara());
			}
		}

		// MASA
		if (vehiculoBean.getPesoMma() != null && !vehiculoBean.getPesoMma().equals("")) {
			try {
				vehiculo.setMASA(new BigInteger(vehiculoBean.getPesoMma()));
				hayVehiculo = true;
			} catch (NumberFormatException ex) {
				// No se ha seteado la masa por no ser la cadena casteable a BigInteger
				log.error("ERROR EXPORTACION :" + " La masa debe ser casteable a BigInteger. Valor: " + vehiculoBean.getPesoMma());
			}
		}

		// PLAZAS
		if (vehiculoBean.getPlazas() != null && !vehiculoBean.getPlazas().equals("")) {
			vehiculo.setPLAZAS(vehiculoBean.getPlazas().toBigInteger());
			hayVehiculo = true;
		}

		// CARBURANTE
		if (vehiculoBean.getCarburanteBean() != null && vehiculoBean.getCarburanteBean().getIdCarburante() != null && !vehiculoBean.getCarburanteBean().getIdCarburante().equals("")) {
			// Existe una diferencia en la nomenclatura del carburante "Otros", así que comprobamos si debe ser un TipoCombustible.O
			if (Combustible.Otros.getValorEnum().equals(vehiculoBean.getCarburanteBean().getIdCarburante())) {
				vehiculo.setIDCARBURANTE(TipoCombustible.O);
			} else {
				vehiculo.setIDCARBURANTE(TipoCombustible.fromValue(vehiculoBean.getCarburanteBean().getIdCarburante()));
			}
			hayVehiculo = true;
		}

		// RELACION POTENCIA PESO
		if (vehiculoBean.getPotenciaPeso() != null && !vehiculoBean.getPotenciaPeso().equals("")) {
			vehiculo.setRELACIONPOTENCIAPESO(vehiculoBean.getPotenciaPeso());
			hayVehiculo = true;
		}

		// PLAZAS DE PIE
		if (vehiculoBean.getNumPlazasPie() != null && !vehiculoBean.getNumPlazasPie().equals("")) {
			vehiculo.setPLAZASPIE(vehiculoBean.getNumPlazasPie().toBigInteger());
			hayVehiculo = true;
		}

		// CO2
		if (vehiculoBean.getCo2() != null && !"".equals(vehiculoBean.getCo2())) {
			try {
				vehiculo.setEMISIONCO2(new BigDecimal(vehiculoBean.getCo2()));
				hayVehiculo = true;
			} catch (NumberFormatException ex) {
				// No se ha seteado el co2 por no ser la cadena casteable a BigInteger
				log.error("ERROR EXPORTACION :" + "La parte entera del co2 debe ser " + "casteable a BigInteger. Valor: " + vehiculoBean.getCo2());
			}
		}

		// MOM
		if (vehiculoBean.getMom() != null && !vehiculoBean.getMom().equals("")) {
			try {
				vehiculo.setMOM(new BigInteger(vehiculoBean.getMom().toString()));
				hayVehiculo = true;
			} catch (NumberFormatException ex) {
				// No se ha seteado el MOM por no ser la cadena casteable a BigInteger
				log.error("ERROR EXPORTACION :" + " El MOM debe ser casteable a BigInteger. Valor: " + vehiculoBean.getMom());
			}
		}

		// CHECK FECHA CADUCIDAD ITV
		if (vehiculoBean.getCheckFechaCaducidadITV() != null && !vehiculoBean.getCheckFechaCaducidadITV().equals("")) {
			try {
				if (vehiculoBean.getCheckFechaCaducidadITV().equals("true")) {
					vehiculo.setCHECKCADUCIDADITV("SI");
				} else {
					vehiculo.setCHECKCADUCIDADITV("NO");
				}
			} catch (Exception e) {
				log.error("ERROR EXPORTACION :" + " El campo CHECKFECHACADUCIDAD. Valor: " + vehiculoBean.getCheckFechaCaducidadITV());
			}
		}

		// MATRICULA ORIGEN
		if (vehiculoBean.getMatriculaOrigen() != null && !vehiculoBean.getMatriculaOrigen().equals("")) {
			vehiculo.setMATRICULAORIGEN(vehiculoBean.getMatriculaOrigen());
			hayVehiculo = true;
		}

		if (vehiculoBean.getMatriculaOrigExtr() != null && !vehiculoBean.getMatriculaOrigExtr().equals("")) {
			vehiculo.setMATRICULAORIGEXTR(vehiculoBean.getMatriculaOrigExtr());
			hayVehiculo = true;
		}

		// TIPO INSPECCION ITV SE GUARDA EN MOTIVO ITV
		// EN MATRICULACIÓN SOLO SE EXPORTA TIPO DE INSPECCION ITV M Y E
		if (vehiculoBean.getMotivoBean() != null && vehiculoBean.getMotivoBean().getIdMotivo() != null && !vehiculoBean.getMotivoBean().getIdMotivo().equals("")) {

			if (TipoInspeccion.M.toString().equals(vehiculoBean.getMotivoBean().getIdMotivo()) || TipoInspeccion.E.toString().equals(vehiculoBean.getMotivoBean().getIdMotivo())) {
				vehiculo.setTIPOINSPECCIONITV(TipoInspeccion.fromValue(vehiculoBean.getMotivoBean().getIdMotivo()));
			} else {
				vehiculo.setTIPOINSPECCIONITV(null);
			}

			hayVehiculo = true;
		}

		// PAÍS DE FABRICACION
		if (vehiculoBean.getPaisFabricacionBean() != null) {
			vehiculo.setPAISFABRICACION(vehiculoBean.getPaisFabricacionBean().getIdPaisFabricacion());
			hayVehiculo = true;
		}

		// POTENCIA NETA
		if (vehiculoBean.getPotenciaNeta() != null && !vehiculoBean.getPotenciaNeta().equals("")) {
			vehiculo.setPOTENCIANETA(vehiculoBean.getPotenciaNeta());
			hayVehiculo = true;
		}

		// CARROCERIA
		if (vehiculoBean.getCarroceriaBean() != null && vehiculoBean.getCarroceriaBean().getIdCarroceria() != null && !vehiculoBean.getCarroceriaBean().getIdCarroceria().equals("")) {
			vehiculo.setIDCARROCERIA(TipoCarroceria.fromValue(vehiculoBean.getCarroceriaBean().getIdCarroceria()));
			hayVehiculo = true;
		}

		// HOMOLOGACIÓN UE
		if (vehiculoBean.getHomologacionBean() != null && vehiculoBean.getHomologacionBean().getIdHomologacion() != null && !vehiculoBean.getHomologacionBean().getIdHomologacion().equals("")) {
			// vehiculo.setIDHOMOLOGACION(TipoHomologacion.fromValue(vehiculoBean.getHomologacionBean().getIdHomologacion()));
			vehiculo.setIDHOMOLOGACION(vehiculoBean.getHomologacionBean().getIdHomologacion());
			hayVehiculo = true;
		}

		// DISTANCIA ENTRE EJES
		if (vehiculoBean.getDistanciaEntreEjes() != null && !vehiculoBean.getDistanciaEntreEjes().toString().equals("")) {
			vehiculo.setDISTANCIAENTREEJES(vehiculoBean.getDistanciaEntreEjes().intValue());
			hayVehiculo = true;
		}

		// MASA MÁXIMA ADMISIBLE
		if (vehiculoBean.getMtmaItv() != null && !vehiculoBean.getMtmaItv().equals("")) {
			try {
				vehiculo.setMASAMAXIMAADMISIBLE(new BigInteger(vehiculoBean.getMtmaItv()));
				hayVehiculo = true;
			} catch (NumberFormatException ex) {
				// No se ha seteado la masa maxima admisible por no ser la cadena casteable a BigInteger
				log.error("ERROR EXPORTACION :" + " La masa maxima admisible debe ser casteable a BigInteger. Valor: " + vehiculoBean.getMtmaItv());
			}
		}

		// VIA ANTERIOR
		if (vehiculoBean.getViaAnterior() != null && !vehiculoBean.getViaAnterior().equals("")) {
			vehiculo.setVIAANTERIOR(vehiculoBean.getViaAnterior().intValue());
			hayVehiculo = true;
		}

		// VIA POSTERIOR
		if (vehiculoBean.getViaPosterior() != null && !vehiculoBean.getViaPosterior().equals("")) {
			vehiculo.setVIAPOSTERIOR(vehiculoBean.getViaPosterior().intValue());
			hayVehiculo = true;
		}

		// CODIGO ECO
		if (vehiculoBean.getCodigoEco() != null && !vehiculoBean.getCodigoEco().equals("")) {
			vehiculo.setCODIGOECO(vehiculoBean.getCodigoEco());
			hayVehiculo = true;
		}

		// IMPORTADO
		if (vehiculoBean.getImportado() != null) {
			if (vehiculoBean.getImportado() == true) {
				vehiculo.setIMPORTADO(TipoSINO.SI.value());
				hayVehiculo = true;
			}
		}

		// SUBASTADO
		if (vehiculoBean.getSubasta() != null && !vehiculoBean.getSubasta().equals("")) {
			if (vehiculoBean.getSubasta() == true) {
				vehiculo.setSUBASTA(TipoSINO.SI.value());
				hayVehiculo = true;
			}
		}

		// CONSUMO
		if (vehiculoBean.getConsumo() != null && !vehiculoBean.getConsumo().toString().equals("")) {
			vehiculo.setCONSUMO(String.valueOf(vehiculoBean.getConsumo()));
			hayVehiculo = true;
		}

		// ECO INNOVACIÓN
		if (vehiculoBean.getEcoInnovacion() != null && !vehiculoBean.getEcoInnovacion().equals("")) {
			if (vehiculoBean.getEcoInnovacion().toString().trim().toUpperCase().equals(TipoSN.S.value()) || vehiculoBean.getEcoInnovacion().toString().trim().toUpperCase().equals(TipoSINO.SI
					.value())) {
				vehiculo.setECOINNOVACION(TipoSINO.SI.value());
			} else {
				vehiculo.setECOINNOVACION(TipoSINO.NO.value());
			}
			hayVehiculo = true;
		}

		// NIVEL DE EMISIONES
		if (vehiculoBean.getNivelEmisiones() != null && !vehiculoBean.getNivelEmisiones().equals("")) {
			vehiculo.setNIVELEMISIONES(vehiculoBean.getNivelEmisiones().toString());
			hayVehiculo = true;
		}

		// REDUCCIÓN ECO
		if (vehiculoBean.getReduccionEco() != null && !vehiculoBean.getReduccionEco().equals("")) {
			vehiculo.setREDUCCIONECO((vehiculoBean.getReduccionEco().intValue()));
			hayVehiculo = true;
		}

		// ALIMENTACION
		if (vehiculoBean.getAlimentacionBean() != null && vehiculoBean.getAlimentacionBean().getIdAlimentacion() != null && !vehiculoBean.getAlimentacionBean().getIdAlimentacion().equals("")) {
			vehiculo.setIDALIMENTACION(TipoAlimentacion.fromValue(vehiculoBean.getAlimentacionBean().getIdAlimentacion()));
			hayVehiculo = true;
		}

		// A PARTIR DE AHORA LA CATEGORIA ELECTRICA Y AUTONOMIA ELECTRICA NO DEPENDE DEL TIPO DE TARJETA, SIEMPRE HAY QUE ENVIAR ESTOS DOS CAMPOS INDEPENDIENTE
		// DEL TIPO DE TARJETA.

		// CATEGORIA ELECTRICA
		if (vehiculoBean.getCategoriaElectricaBean() != null && vehiculoBean.getCategoriaElectricaBean().getIdCategoriaElectrica() != null && !vehiculoBean.getCategoriaElectricaBean()
				.getIdCategoriaElectrica().isEmpty()) {
			vehiculo.setCATEGORIAELECTRICA(TipoCategoriaElectrica.fromValue(vehiculoBean.getCategoriaElectricaBean().getIdCategoriaElectrica()));
			hayVehiculo = true;
		}
		// AUTONOMIA ELECTRICA
		if (vehiculoBean.getAutonomiaElectrica() != null) {
			vehiculo.setAUTONOMIAELECTRICA(vehiculoBean.getAutonomiaElectrica().intValue());
			hayVehiculo = true;
		}

		// MATW 1 DE JULIO 2014 CAMBIOS ELECTRICOS
		if (null != vehiculoBean.getTipoTarjetaItvBean().getIdTipoTarjetaItv() && (vehiculoBean.getTipoTarjetaItvBean().getIdTipoTarjetaItv().equals(TipoTarjetaITV.A.getValorEnum()) || vehiculoBean
				.getTipoTarjetaItvBean().getIdTipoTarjetaItv().equals(TipoTarjetaITV.AL.getValorEnum()) || vehiculoBean.getTipoTarjetaItvBean().getIdTipoTarjetaItv().equals(TipoTarjetaITV.AR
						.getValorEnum()) || vehiculoBean.getTipoTarjetaItvBean().getIdTipoTarjetaItv().equals(TipoTarjetaITV.AT.getValorEnum()) || vehiculoBean.getTipoTarjetaItvBean()
								.getIdTipoTarjetaItv().equals(TipoTarjetaITV.D.getValorEnum()) || vehiculoBean.getTipoTarjetaItvBean().getIdTipoTarjetaItv().equals(TipoTarjetaITV.DL.getValorEnum())
				|| vehiculoBean.getTipoTarjetaItvBean().getIdTipoTarjetaItv().equals(TipoTarjetaITV.DR.getValorEnum()) || vehiculoBean.getTipoTarjetaItvBean().getIdTipoTarjetaItv().equals(
						TipoTarjetaITV.DT.getValorEnum()))) {

			if (vehiculoBean.getMarcaBaseBean() != null) {
				vehiculo.setMARCABASE(vehiculoBean.getMarcaBaseBean().getMarca());
				hayVehiculo = true;
			}
			if (vehiculoBean.getFabricanteBase() != null) {
				vehiculo.setFABRICANTEBASE(vehiculoBean.getFabricanteBase());
				hayVehiculo = true;
			}
			if (vehiculoBean.getTipoItvBase() != null) {
				vehiculo.setTIPOBASE(vehiculoBean.getTipoItvBase());
				hayVehiculo = true;
			}
			if (vehiculoBean.getVarianteBase() != null) {
				vehiculo.setVARIANTEBASE(vehiculoBean.getVarianteBase());
				hayVehiculo = true;
			}
			if (vehiculoBean.getVersionBase() != null) {
				vehiculo.setVERSIONBASE(vehiculoBean.getVersionBase());
				hayVehiculo = true;
			}
			if (vehiculoBean.getNumHomologacionBase() != null) {
				vehiculo.setNUMEROHOMOLOGACIONBASE(vehiculoBean.getNumHomologacionBase());
				hayVehiculo = true;
			}
			if (vehiculoBean.getMomBase() != null) {
				vehiculo.setMOMBASE(vehiculoBean.getMomBase().intValue());
				hayVehiculo = true;
			}

		}

		// Comprueba el flag para setear vehiculo o no en el elemento raiz mat:
		if (hayVehiculo) {
			mat.setDATOSVEHICULO(vehiculo);
		}

		// DATOS LIMITACION
		FORMATOGA.MATRICULACION.DATOSLIMITACION limitacion = factory.createFORMATOGAMATRICULACIONDATOSLIMITACION();

		// Flag. Indica si meter limitacion en la matriculación.
		boolean hayLimitacion = false;

		// TIPO LIMITACION
		if (bean.getTramiteTraficoBean().getIedtm() != null && !bean.getTramiteTraficoBean().getIedtm().equals("")) {
			limitacion.setTIPOLIMITACION(bean.getTramiteTraficoBean().getIedtm());
			hayLimitacion = true;
		}

		// FECHA LIMITACION
		if (bean.getTramiteTraficoBean().getFechaIedtm() != null && !bean.getTramiteTraficoBean().getFechaIedtm().toString().equals("")) {
			limitacion.setFECHALIMITACION(bean.getTramiteTraficoBean().getFechaIedtm().toString());
			hayLimitacion = true;
		}

		// NUMERO REGISTRO LIMITACION
		if (bean.getTramiteTraficoBean().getNumRegIedtm() != null && !bean.getTramiteTraficoBean().getNumRegIedtm().getValorEnum().equals("")) {
			limitacion.setNUMEROREGISTROLIMITACION(bean.getTramiteTraficoBean().getNumRegIedtm().getValorEnum());
			hayLimitacion = true;
		}

		// FINANCIERA LIMITACION
		if (bean.getTramiteTraficoBean().getFinancieraIedtm() != null && !bean.getTramiteTraficoBean().getFinancieraIedtm().equals("")) {
			limitacion.setFINANCIERALIMITACION(bean.getTramiteTraficoBean().getFinancieraIedtm());
			hayLimitacion = true;
		}

		// Comprueba el flag para setear limitacion o no en el elemento raiz mat:
		if (hayLimitacion) {
			mat.setDATOSLIMITACION(limitacion);
		}

		// DATOS IMPUESTOS
		FORMATOGA.MATRICULACION.DATOSIMPUESTOS impuestos = factory.createFORMATOGAMATRICULACIONDATOSIMPUESTOS();

		// Flag. Indica si meter impuestos en la matriculación.
		boolean hayImpuestos = false;

		// CODIGO ELECTRONICO AEAT
		if (bean.getTramiteTraficoBean().getCemIedtm() != null && !bean.getTramiteTraficoBean().getCemIedtm().equals("")) {
			impuestos.setCODIGOELECTRONICOAEAT(bean.getTramiteTraficoBean().getCemIedtm());
			hayImpuestos = true;
		}

		// CEMA
		if (bean.getTramiteTraficoBean().getCema() != null && !bean.getTramiteTraficoBean().getCema().equals("")) {
			impuestos.setCEMA(bean.getTramiteTraficoBean().getCema());
			hayImpuestos = true;
		}

		// EXENTO CEM
		if (bean.getTramiteTraficoBean().getExentoCem() != null && "true".equals(bean.getTramiteTraficoBean().getExentoCem())) {
			impuestos.setEXENTOCEM("SI");
			hayImpuestos = true;
		}

		// JUSTIFANTE_IVTM
		if (bean.getJustificado_IVTM() != null && !"".equals(bean.getJustificado_IVTM())) {
			if (bean.getJustificado_IVTM().toString().trim().equals("true")) {
				impuestos.setJUSTIFICADOIVTM(TipoSINO.SI.value());
			}
		}

		// --DATOS 0506
		FORMATOGA.MATRICULACION.DATOSIMPUESTOS.DATOS0506 datos0506 = factory.createFORMATOGAMATRICULACIONDATOSIMPUESTOSDATOS0506();

		// Flag. Indica si meter datos0506 en los impuestos.
		boolean hay0506 = false;

		// MOTIVO EXENCION 05
		if (bean.getIdReduccion05() != null && bean.getIdReduccion05().getValorEnum() != null && !bean.getIdReduccion05().getValorEnum().equals("")) {
			datos0506.setMOTIVOEXENCION05(Tipo05.fromValue(bean.getIdReduccion05().getValorEnum()));
			hay0506 = true;
			hayImpuestos = true;
		}

		// MOTIVO EXENCION 06
		if (bean.getIdNoSujeccion06() != null && bean.getIdNoSujeccion06().getValorEnum() != null && !bean.getIdNoSujeccion06().getValorEnum().equals("")) {
			datos0506.setMOTIVOEXENCION06(Tipo06.fromValue(bean.getIdNoSujeccion06().getValorEnum()));
			hay0506 = true;
			hayImpuestos = true;
		}

		// Comprueba el flag para setear datos0506 o no en el elemento raiz mat:
		if (hay0506) {
			impuestos.setDATOS0506(datos0506);
		}

		// --DATOS 576
		FORMATOGA.MATRICULACION.DATOSIMPUESTOS.DATOS576 datos576 = factory.createFORMATOGAMATRICULACIONDATOSIMPUESTOSDATOS576();

		// Flag. Indica si meter 576 en los impuestos.
		boolean hay576 = false;

		// IMPORTE TOTAL 576
		if (bean.getImporte576() != null && !bean.getImporte576().equals("")) {
			datos576.setIMPORTETOTAL576(bean.getImporte576());
			hay576 = true;
			hayImpuestos = true;
		}

		// EJERCICIO DEVENGO
		if (bean.getEjercicioDevengo576() != null && bean.getEjercicioDevengo576() != null) {
			datos576.setEJERCICIODEVENGO576(bean.getEjercicioDevengo576().intValue());
			hay576 = true;
			hayImpuestos = true;
		}

		// OBSERVACIONES 576
		if (bean.getObservaciones576() != null && !bean.getObservaciones576().equals("")) {
			datos576.setOBSERVACIONES576(bean.getObservaciones576().getValorEnum());
			hay576 = true;
			hayImpuestos = true;
		}

		// EXENTO 576
		if (bean.getExento576() != null && bean.getExento576().equals("true")) {
			datos576.setEXENTO576("SI");
			hay576 = true;
			hayImpuestos = true;
		}

		// BASE IMPONIBLE 576
		if (bean.getBaseImponible576() != null && !bean.getBaseImponible576().equals("") && bean.getBaseImponible576() != BigDecimal.ZERO) {
			try {
				datos576.setBASEIMPONIBLE576(new BigDecimal(bean.getBaseImponible576().toString()));
				hay576 = true;
				hayImpuestos = true;
			} catch (NumberFormatException ex) {
				// No se ha seteado la base imponible 576 por no ser la cadena casteable a BigDecimal
				log.error("ERROR EXPORTACION :" + " La base imponible 576 debe ser casteable a BigDecimal. Valor: " + bean.getBaseImponible576());
			}
		}

		// BASE IMPONIBLE REDUCIDA 576
		if (bean.getBaseImponibleReducida576() != null && !bean.getBaseImponibleReducida576().equals("") && bean.getBaseImponibleReducida576() != BigDecimal.ZERO) {
			try {
				datos576.setBASEIMPONIBLEREDUCIDA576(new BigDecimal(bean.getBaseImponibleReducida576().toString()));
				hay576 = true;
				hayImpuestos = true;
			} catch (NumberFormatException ex) {
				// No se ha seteado la base imponible reducida 576 por no ser la cadena casteable a BigDecimal
				log.error("ERROR EXPORTACION :" + " La base imponible reducida 576 debe ser casteable a BigDecimal. Valor: " + bean.getBaseImponibleReducida576());
			}
		}

		// TIPO DE GRAVAMEN
		if (bean.getTipoGravamen() != null && !bean.getTipoGravamen().equals("") && bean.getTipoGravamen() != BigDecimal.ZERO) {
			datos576.setTIPOGRAVAMEN576(bean.getTipoGravamen());
			hay576 = true;
			hayImpuestos = true;
		}

		// DEDUCCION LINEAL 576
		if (bean.getDeduccionLineal576() != null && !bean.getDeduccionLineal576().equals("") && bean.getDeduccionLineal576() != BigDecimal.ZERO) {
			try {
				datos576.setDEDUCCIONLINEAL576(new BigDecimal(bean.getDeduccionLineal576().toString()));
				hay576 = true;
				hayImpuestos = true;
			} catch (NumberFormatException ex) {
				// No se ha seteado la base deduccion lineal 576 por no ser la cadena casteable a BigDecimal
				log.error("ERROR EXPORTACION :" + " La deduccion lineal 576 debe ser casteable a BigDecimal. Valor: " + bean.getDeduccionLineal576());
			}
		}

		// CUOTA 576
		if (bean.getCuota576() != null && !bean.getCuota576().equals("") && bean.getCuota576() != BigDecimal.ZERO) {
			try {
				datos576.setCUOTA576(new BigDecimal(bean.getCuota576().toString()));
				hay576 = true;
				hayImpuestos = true;
			} catch (NumberFormatException ex) {
				// No se ha seteado la cuota 576 por no ser la cadena casteable a BigDecimal
				log.error("ERROR EXPORTACION :" + " La cuota 576 debe ser casteable a BigDecimal. Valor: " + bean.getCuota576());
			}
		}

		// CUOTA A INGRESAR 576
		if (bean.getCuotaIngresar576() != null && !bean.getCuotaIngresar576().equals("") && bean.getCuotaIngresar576() != BigDecimal.ZERO) {
			try {
				datos576.setCUOTAINGRESAR576(new BigDecimal(bean.getCuotaIngresar576().toString()));
				hay576 = true;
				hayImpuestos = true;
			} catch (NumberFormatException ex) {
				// No se ha seteado la cuota a ingresar 576 por no ser la cadena casteable a BigDecimal
				log.error("ERROR EXPORTACION :" + " La cuota a ingresar 576 debe ser casteable a BigDecimal. Valor: " + bean.getCuotaIngresar576());
			}
		}

		// A DEDUCIR 576
		if (bean.getDeducir576() != null && !bean.getDeducir576().equals("") && bean.getDeducir576() != BigDecimal.ZERO) {
			try {
				datos576.setADEDUCIR576(new BigDecimal(bean.getDeducir576().toString()));
				hay576 = true;
				hayImpuestos = true;
			} catch (NumberFormatException ex) {
				// No se ha seteado el 'a deducir 576' por no ser la cadena casteable a BigDecimal
				log.error("ERROR EXPORTACION :" + " El 'a deducir 576' debe ser casteable a BigDecimal. Valor: " + bean.getDeducir576());
			}
		}

		// LIQUIDACION 576
		if (bean.getLiquidacion576() != null && !bean.getLiquidacion576().equals("") && bean.getLiquidacion576() != BigDecimal.ZERO) {
			try {
				datos576.setRESULTADOLIQUIDACION576(new BigDecimal(bean.getLiquidacion576().toString()));
				hay576 = true;
				hayImpuestos = true;
			} catch (NumberFormatException ex) {
				// No se ha seteado el resultado de la liquidacion 576 por no ser la cadena casteable a BigDecimal
				log.error("ERROR EXPORTACION :" + " El resultado de la liquidacion 576 debe ser casteable a BigDecimal. Valor: " + bean.getLiquidacion576());
			}
		}

		// DECLARACIÓN COMPLEMENTARIO 576
		if (bean.getNumDeclaracionComplementaria576() != null && !bean.getNumDeclaracionComplementaria576().equals("")) {
			datos576.setDECLARACIONCOMPLEMENTARIA576(bean.getNumDeclaracionComplementaria576());
			hay576 = true;
			hayImpuestos = true;
		}

		// MATRICULA PREVER 576
		if (bean.getVehiculoPreverBean() != null && bean.getVehiculoPreverBean().getMatricula() != null && !bean.getVehiculoPreverBean().getMatricula().equals("")) {
			datos576.setMATRICULAPREVER576(bean.getVehiculoPreverBean().getMatricula());
			hay576 = true;
			hayImpuestos = true;
		}

		// TIPO ITV PREVER 576
		if (bean.getVehiculoPreverBean() != null && bean.getVehiculoPreverBean().getTipoItv() != null && !bean.getVehiculoPreverBean().getTipoItv().equals("")) {
			datos576.setTIPOITVPREVER576(bean.getVehiculoPreverBean().getTipoItv());
			hay576 = true;
			hayImpuestos = true;
		}

		// MARCA PREVER 576
		if (bean.getVehiculoPreverBean() != null && bean.getVehiculoPreverBean().getMarcaBean() != null && bean.getVehiculoPreverBean().getMarcaBean().getCodigoMarca() != null) {
			datos576.setMARCAPREVER576(bean.getVehiculoPreverBean().getMarcaBean().getCodigoMarca().toString());
			hay576 = true;
			hayImpuestos = true;
		}

		// MODELO PREVER 576
		if (bean.getVehiculoPreverBean() != null && bean.getVehiculoPreverBean().getModelo() != null && !bean.getVehiculoPreverBean().getModelo().equals("")) {
			datos576.setMODELOPREVER576(bean.getVehiculoPreverBean().getModelo());
			hay576 = true;
			hayImpuestos = true;
		}

		// BASTIDOR PREVER 576
		if (bean.getVehiculoPreverBean() != null && bean.getVehiculoPreverBean().getBastidor() != null && !bean.getVehiculoPreverBean().getBastidor().equals("")) {
			datos576.setNUMEROBASTIDORPREVER576(bean.getVehiculoPreverBean().getBastidor());
			hay576 = true;
			hayImpuestos = true;
		}

		// CLASIFICACION PREVER 576
		if ((bean.getVehiculoPreverBean().getCriterioConstruccionBean() != null && bean.getVehiculoPreverBean().getCriterioConstruccionBean().getIdCriterioConstruccion() != null && bean
				.getVehiculoPreverBean().getCriterioConstruccionBean().getIdCriterioConstruccion().length() == 2 && bean.getVehiculoPreverBean().getCriterioUtilizacionBean() != null && bean
						.getVehiculoPreverBean().getCriterioUtilizacionBean().getIdCriterioUtilizacion() != null && bean.getVehiculoPreverBean().getCriterioUtilizacionBean().getIdCriterioUtilizacion()
								.length() == 2)) {
			datos576.setCLASIFICACIONPREVER576(bean.getVehiculoPreverBean().getCriterioConstruccionBean().getIdCriterioConstruccion() + bean.getVehiculoPreverBean().getCriterioUtilizacionBean()
					.getIdCriterioUtilizacion());
			hay576 = true;
			hayImpuestos = true;
		}

		// CAUSA HECHO IMPONIBLE
		if (bean.getCausaHechoImponible576() != null && bean.getCausaHechoImponible576().getValorEnum() != null && !bean.getCausaHechoImponible576().getValorEnum().equals("")) {
			datos576.setCAUSAHECHOIMPONIBLE(bean.getCausaHechoImponible576() != null ? bean.getCausaHechoImponible576().getValorEnum() : "");
			hay576 = true;
			hayImpuestos = true;
		}

		// Comprueba el flag para setear datos0506 o no en el elemento raiz mat:
		if (hay576) {
			impuestos.setDATOS576(datos576);
		}

		// --DATOS NRC
		FORMATOGA.MATRICULACION.DATOSIMPUESTOS.DATOSNRC nrc = factory.createFORMATOGAMATRICULACIONDATOSIMPUESTOSDATOSNRC();

		// Flag. Indica si meter nrc en los impuestos.
		boolean hayNrc = false;

		// CODIGO NRC
		if (bean.getNrc576() != null && !bean.getNrc576().equals("")) {
			nrc.setCODIGONRC(bean.getNrc576());
			hayNrc = true;
			hayImpuestos = true;
		}

		// FECHA OPERACION NRC
		if (bean.getFechaPago576() != null && !bean.getFechaPago576().isfechaNula()) {
			nrc.setFECHAOPERACIONNRC(bean.getFechaPago576().toString());
			hayNrc = true;
			hayImpuestos = true;
		}

		// Comprueba el flag para setear datos nrc o no en el elemento raiz mat:
		if (hayNrc) {
			impuestos.setDATOSNRC(nrc);
		}

		// TODO MPC. Cambio IVTM.
		// DATOS IVTM
		IVTMModeloMatriculacionInterface modeloIVTM = new IVTMModeloMatriculacionImpl();
		FORMATOGA.MATRICULACION.DATOSIMPUESTOS.DATOSIMVTM ivtm = (FORMATOGA.MATRICULACION.DATOSIMPUESTOS.DATOSIMVTM) modeloIVTM.obtenerDatosParaImportacionMatw(bean.getTramiteTraficoBean()
				.getNumExpediente(), tienePermisoIVTM);
		if (ivtm != null) {
			impuestos.setDATOSIMVTM(ivtm);
			// TODO Hay que revisar este flag, ya que se podría hacer de otra forma.
			hayImpuestos = true;
		}

		// Comprueba el flag para setear impuestos o no en el elemento raiz mat:
		if (hayImpuestos) {
			mat.setDATOSIMPUESTOS(impuestos);
		}

		return mat;

	}

	private FORMATOGA.MATRICULACION convertirBeanToGaNuevo(TramiteTrafMatrDto dto, boolean tienePermisoIVTM, boolean tienePermisoLiberacion, boolean xmlSesion) throws Exception {
		ObjectFactory factory = new ObjectFactory();
		UtilesConversionesMatw utils = new UtilesConversionesMatw();

		FORMATOGA.MATRICULACION mat = factory.createFORMATOGAMATRICULACION();

		if (dto.getNumExpediente() != null) {
			mat.setNUMEROEXPEDIENTE(dto.getNumExpediente().toString());
		}

		if (dto.getRefPropia() != null && !dto.getRefPropia().isEmpty()) {
			mat.setREFERENCIAPROPIA(dto.getRefPropia());
		}

		// NUMERO PROFESIONAL
		// Si exporta xml de sesion se enviará como profesional el de la sesion , caso contrario el del tramite
		if (dto.getNumColegiado() != null && !dto.getNumColegiado().isEmpty() && !xmlSesion) {
			mat.setNUMEROPROFESIONAL(new BigInteger("0" + dto.getNumColegiado().toString()));
		} else {
			mat.setNUMEROPROFESIONAL(new BigInteger("0" + utilesColegiado.getNumColegiadoSession().toString()));
		}

		// FECHA CREACION
		if (dto.getFechaAlta() != null && !dto.getFechaAlta().isfechaNula()) {
			mat.setFECHACREACION(dto.getFechaAlta().toString());
		}

		// FECHA PRESENTACIÓN
		if (dto.getFechaPresentacion() != null && !dto.getFechaPresentacion().isfechaNula()) {
			mat.setFECHAPRESENTACION(dto.getFechaPresentacion().toString());
		}

		// JEFATURA
		if (dto.getJefaturaTraficoDto() != null && dto.getJefaturaTraficoDto().getJefaturaProvincial() != null && !dto.getJefaturaTraficoDto().getJefaturaProvincial().isEmpty()) {
			mat.setJEFATURA(TipoProvincia.fromValue(dto.getJefaturaTraficoDto().getJefaturaProvincial()));
		}

		// SUCURSAL
		if (dto.getJefaturaTraficoDto() != null && dto.getJefaturaTraficoDto().getSucursal() != null && !dto.getJefaturaTraficoDto().getSucursal().isEmpty()) {
			mat.setSUCURSAL(dto.getJefaturaTraficoDto().getSucursal());
		}

		// TIPO_TASA -Si el xml de sesion no se informara de este campo
		if (dto.getTasa() != null && dto.getTasa().getTipoTasa() != null && !dto.getTasa().getTipoTasa().isEmpty() && !xmlSesion) {
			mat.setTIPOTASA(dto.getTasa().getTipoTasa());
		}

		// CODIGO TASA -Si el xml de sesion no se informara de este campo
		if (dto.getTasa() != null && dto.getTasa().getCodigoTasa() != null && !dto.getTasa().getCodigoTasa().isEmpty() && !xmlSesion) {
			mat.setTASA(dto.getTasa().getCodigoTasa());
		}

		// OBSERVACIONES
		if (dto.getAnotaciones() != null && !dto.getAnotaciones().isEmpty()) {
			mat.setOBSERVACIONES(dto.getAnotaciones());
		}

		// ENTREGA_FACT_MATRICULACION
		if (dto.getEntregaFactMatriculacion() != null && dto.getEntregaFactMatriculacion()) {
			mat.setENTREGAFACTMATRICULACION(TipoSINO.SI.name());
		} else {
			mat.setENTREGAFACTMATRICULACION(TipoSINO.NO.name());
		}

		mat.setVERSIONMATE(Constantes.VERSION_ACTUAL_MATE);

		if (utilesColegiado.tienePermisoLiberarEEFF()) {
			convertirDtoToGaLiberacion(mat, dto, tienePermisoLiberacion, factory);
		}

		convertirDtoToGaTitular(mat, dto.getTitular(), factory, utils);

		convertirDtoToGaRepresentanteTitular(mat, dto.getRepresentanteTitular(), factory, utils);

		if (dto.getRenting() != null && dto.getRenting()) {
			convertirDtoToGaArrendatario(mat, dto.getArrendatario(), factory, utils);
			convertirDtoToGaRepresentanteArrendatario(mat, dto.getRepresentanteArrendatario(), factory, utils);
		}

		convertirDtoToGaConductorHabitual(mat, dto.getConductorHabitual(), factory, utils);

		convertirDtoToGaVehiculo(mat, dto, factory, utils);

		convertirDtoToGaLimitacion(mat, dto, factory);

		convertirDtoToGaImpuestos(mat, dto, factory, tienePermisoIVTM);

		return mat;
	}

	private void convertirDtoToGaLiberacion(MATRICULACION mat, TramiteTrafMatrDto tramiteTrafMatrDto, boolean tienePermisoLiberacion, ObjectFactory factory) {
		if (tienePermisoLiberacion && tramiteTrafMatrDto.getLiberacionEEFF() != null && !tramiteTrafMatrDto.getLiberacionEEFF().getExento()) {
			LiberacionEEFFDto liberacionEEFFDTO = servicioEEFF.getLiberacionEEFFDto(tramiteTrafMatrDto.getNumExpediente());
			mat.setEXENTOLIBERAR("NO");
			FORMATOGA.MATRICULACION.DATOSLIBERACION fmEEFF = factory.createFORMATOGAMATRICULACIONDATOSLIBERACION();
			fmEEFF.setCIFFIR(liberacionEEFFDTO.getFirCif());
			fmEEFF.setMARCAFIR(liberacionEEFFDTO.getFirMarca());
			fmEEFF.setNUMFACTURA(liberacionEEFFDTO.getNumFactura());
			if (liberacionEEFFDTO.getImporte() != null) {
				fmEEFF.setIMPORTE(liberacionEEFFDTO.getImporte().toString());
			}
			if (liberacionEEFFDTO.getNifRepresentado() != null) {
				fmEEFF.setNIFREPRESENTADO(liberacionEEFFDTO.getNifRepresentado().trim());
			}
			if (liberacionEEFFDTO.getFechaFactura() != null) {
				fmEEFF.setFECHAFACTURA(liberacionEEFFDTO.getFechaFactura().toString());
			}
			if (null != liberacionEEFFDTO.getFechaRealizacion()) {
				fmEEFF.setFECHAREALIZACION(liberacionEEFFDTO.getFechaRealizacion().toString());
			}
			mat.setDATOSLIBERACION(fmEEFF);
		} else {
			mat.setEXENTOLIBERAR("SI");
		}
	}

	private void convertirDtoToGaTitular(MATRICULACION mat, IntervinienteTraficoDto titular, ObjectFactory factory, UtilesConversionesMatw utils) throws ParseException {
		if (titular != null) {
			DATOSTITULAR datostitular = factory.createFORMATOGAMATRICULACIONDATOSTITULAR();

			if (titular.getAutonomo()) {
				datostitular.setAUTONOMO("SI");
			}

			datostitular.setCODIGOIAE(titular.getCodigoIAE());

			if (titular.getCambioDomicilio() != null && titular.getCambioDomicilio()) {
				datostitular.setCAMBIODOMICILIOTITULAR("SI");
			}

			PersonaDto persona = titular.getPersona();
			if (persona != null) {
				datostitular.setDNITITULAR(persona.getNif());

				if (persona.getNif() != null && persona.getApellido1RazonSocial() != null && !persona.getApellido1RazonSocial().isEmpty()) {
					if (!utils.isNifNie(persona.getNif())) {
						datostitular.setRAZONSOCIALTITULAR(persona.getApellido1RazonSocial());
					} else if (utils.isNifNie(persona.getNif())) {
						datostitular.setAPELLIDO1TITULAR(persona.getApellido1RazonSocial());
					}
				}

				datostitular.setAPELLIDO2TITULAR(persona.getApellido2());
				datostitular.setNOMBRETITULAR(persona.getNombre());
				datostitular.setANAGRAMATITULAR(persona.getAnagrama());

				if (persona.getFechaNacimiento() != null && !persona.getFechaNacimiento().toString().isEmpty()) {
					datostitular.setFECHANACIMIENTOTITULAR(utilesFecha.getFechaFracionada(persona.getFechaNacimiento().getDate()).toString());
				}

				if (persona.getSexo() != null && !persona.getSexo().isEmpty()) {
					datostitular.setSEXOTITULAR(SexoPersonaMatw.convertirAformatoGA(persona.getSexo()));
				}

				if (persona.getEstadoCivil() != null && !persona.getEstadoCivil().isEmpty()) {
					datostitular.setESTADOCIVILTITULAR(EstadoCivilMatw.convertirAFormatoGA(persona.getEstadoCivil()));
				}

				datostitular.setTELEFONOTITULAR(persona.getTelefonos());
				datostitular.setCORREOELECTRONICOTITULAR(persona.getCorreoElectronico());

				if (persona.getFechaCaducidadNif() != null) {
					datostitular.setFECHACADUCIDADNIFTITULAR(persona.getFechaCaducidadNif().toString());
				}

				if (persona.getFechaCaducidadAlternativo() != null) {
					datostitular.setFECHACADUCIDADDOCUMENTOSUSTITUTIVOTITULAR(persona.getFechaCaducidadAlternativo().toString());
				}

				datostitular.setTIPODOCUMENTOSUSTITUTIVOTITULAR(persona.getTipoDocumentoAlternativo());
			}

			DireccionDto direccion = titular.getDireccion();
			if (direccion != null) {
				if (direccion.getIdProvincia() != null) {
					if (utils.getSiglasFromIdProvinciaMatw(direccion.getIdProvincia()) != null && !utils.getSiglasFromIdProvinciaMatw(direccion.getIdProvincia()).isEmpty()) {
						datostitular.setPROVINCIATITULAR(TipoProvincia.fromValue(utils.getSiglasFromIdProvinciaMatw(direccion.getIdProvincia())));
					}

					if (direccion.getIdMunicipio() != null) {
						String nombreMunicipio = servicioDireccion.obtenerNombreMunicipio(direccion.getIdMunicipio(), direccion.getIdProvincia());
						datostitular.setMUNICIPIOTITULAR(nombreMunicipio);
					}
				}

				if (direccion.getIdTipoVia() != null) {
					if (utils.getIdTipoViaDGTFromIdTipoVia(direccion.getIdTipoVia()) != null && !utils.getIdTipoViaDGTFromIdTipoVia(direccion.getIdTipoVia()).isEmpty()) {
						datostitular.setSIGLASDIRECCIONTITULAR(utils.getIdTipoViaDGTFromIdTipoVia(direccion.getIdTipoVia()));
					}
				}

				datostitular.setNOMBREVIADIRECCIONTITULAR(direccion.getNombreVia());
				datostitular.setPUEBLOTITULAR(direccion.getPuebloCorreos());
				datostitular.setCPTITULAR(direccion.getCodPostalCorreos());
				datostitular.setNUMERODIRECCIONTITULAR(direccion.getNumero());
				datostitular.setLETRADIRECCIONTITULAR(direccion.getLetra());
				datostitular.setESCALERADIRECCIONTITULAR(direccion.getEscalera());
				datostitular.setPISODIRECCIONTITULAR(direccion.getPlanta());
				datostitular.setPUERTADIRECCIONTITULAR(direccion.getPuerta());

				if (direccion.getKm() != null) {
					datostitular.setKMDIRECCIONTITULAR(direccion.getKm().toString());
				}

				if (direccion.getHm() != null) {
					datostitular.setHECTOMETRODIRECCIONTITULAR(direccion.getHm().toString());
				}

				datostitular.setBLOQUEDIRECCIONTITULAR(direccion.getBloque());
			}
			mat.setDATOSTITULAR(datostitular);
		}
	}

	private void convertirDtoToGaRepresentanteTitular(MATRICULACION mat, IntervinienteTraficoDto representante, ObjectFactory factory, UtilesConversionesMatw utils) throws ParseException {
		if (representante != null) {
			DATOSREPRESENTANTETITULAR datosrepresentante = factory.createFORMATOGAMATRICULACIONDATOSREPRESENTANTETITULAR();

			if (representante.getConceptoRepre() != null && !representante.getConceptoRepre().isEmpty()) {
				datosrepresentante.setCONCEPTOREPTITULAR(representante.getConceptoRepre());
				if (ConceptoTutela.Tutela.getValorEnum().equals(representante.getConceptoRepre())) {
					if (representante.getIdMotivoTutela() != null) {
						datosrepresentante.setMOTIVOTUTELA(TipoMotivoTutela.valueOf(representante.getIdMotivoTutela()));
					}
				}
			}

			datosrepresentante.setACREDITACIONREPTITULAR(representante.getDatosDocumento());

			if (representante.getFechaFin() != null && !representante.getFechaFin().toString().isEmpty()) {
				datosrepresentante.setFECHAFINTUTELA(representante.getFechaFin().toString());
			}

			PersonaDto persona = representante.getPersona();
			if (persona != null) {
				datosrepresentante.setDNIREP(persona.getNif());

				if (persona.getNif() != null && persona.getApellido1RazonSocial() != null && !persona.getApellido1RazonSocial().isEmpty()) {
					if (!utils.isNifNie(persona.getNif())) {
						datosrepresentante.setRAZONSOCIALREP(persona.getApellido1RazonSocial());
					} else if (utils.isNifNie(persona.getNif())) {
						datosrepresentante.setAPELLIDO1REP(persona.getApellido1RazonSocial());
					}
				}

				datosrepresentante.setAPELLIDO2REP(persona.getApellido2());
				datosrepresentante.setNOMBREREP(persona.getNombre());

				if (persona.getFechaNacimiento() != null && !persona.getFechaNacimiento().toString().isEmpty()) {
					datosrepresentante.setFECHANACIMIENTOREP(utilesFecha.getFechaFracionada(persona.getFechaNacimiento().getDate()).toString());
				}

				if (persona.getSexo() != null && !persona.getSexo().isEmpty()) {
					datosrepresentante.setSEXOREP(SexoPersonaMatw.convertirAformatoGA(persona.getSexo()));
				}

				if (persona.getEstadoCivil() != null && !persona.getEstadoCivil().isEmpty()) {
					datosrepresentante.setESTADOCIVILREP(EstadoCivilMatw.convertirAFormatoGA(persona.getEstadoCivil()));
				}

				if (persona.getFechaCaducidadNif() != null) {
					datosrepresentante.setFECHACADUCIDADNIFREPRESENTANTETITULAR(persona.getFechaCaducidadNif().toString());
				}

				if (persona.getFechaCaducidadAlternativo() != null) {
					datosrepresentante.setFECHACADUCIDADDOCUMENTOSUSTITUTIVOREPRESENTANTETITULAR(persona.getFechaCaducidadAlternativo().toString());
				}

				datosrepresentante.setTIPODOCUMENTOSUSTITUTIVOREPRESENTANTETITULAR(persona.getTipoDocumentoAlternativo());
			}

			DireccionDto direccion = representante.getDireccion();
			if (direccion != null) {
				if (direccion.getIdProvincia() != null) {
					if (utils.getSiglasFromIdProvinciaMatw(direccion.getIdProvincia()) != null && !utils.getSiglasFromIdProvinciaMatw(direccion.getIdProvincia()).isEmpty()) {
						datosrepresentante.setPROVINCIAREP(TipoProvincia.fromValue(utils.getSiglasFromIdProvinciaMatw(direccion.getIdProvincia())));
					}

					if (direccion.getIdMunicipio() != null) {
						String nombreMunicipio = servicioDireccion.obtenerNombreMunicipio(direccion.getIdMunicipio(), direccion.getIdProvincia());
						datosrepresentante.setMUNICIPIOREP(nombreMunicipio);
					}
				}

				if (direccion.getIdTipoVia() != null) {
					if (utils.getIdTipoViaDGTFromIdTipoVia(direccion.getIdTipoVia()) != null && !utils.getIdTipoViaDGTFromIdTipoVia(direccion.getIdTipoVia()).isEmpty()) {
						datosrepresentante.setSIGLASDIRECCIONREP(utils.getIdTipoViaDGTFromIdTipoVia(direccion.getIdTipoVia()));
					}
				}

				datosrepresentante.setNOMBREVIADIRECCIONREP(direccion.getNombreVia());
				datosrepresentante.setPUEBLOREP(direccion.getPuebloCorreos());
				datosrepresentante.setCPREP(direccion.getCodPostalCorreos());
				datosrepresentante.setNUMERODIRECCIONREP(direccion.getNumero());
				datosrepresentante.setLETRADIRECCIONREP(direccion.getLetra());
				datosrepresentante.setESCALERADIRECCIONREP(direccion.getEscalera());
				datosrepresentante.setPISODIRECCIONREP(direccion.getPlanta());

				datosrepresentante.setPUERTADIRECCIONREP(direccion.getPuerta());
				datosrepresentante.setBLOQUEDIRECCIONREP(direccion.getBloque());

				if (direccion.getKm() != null) {
					datosrepresentante.setKMDIRECCIONREP(direccion.getKm().toString());
				}

				if (direccion.getHm() != null) {
					datosrepresentante.setHECTOMETRODIRECCIONREP(direccion.getHm().toString());
				}
			}
			mat.setDATOSREPRESENTANTETITULAR(datosrepresentante);
		}
	}

	private void convertirDtoToGaArrendatario(MATRICULACION mat, IntervinienteTraficoDto arrendatario, ObjectFactory factory, UtilesConversionesMatw utils) throws ParseException {
		if (arrendatario != null) {
			DATOSARRENDATARIO datosarrendatario = factory.createFORMATOGAMATRICULACIONDATOSARRENDATARIO();

			if (arrendatario.getCambioDomicilio() != null && arrendatario.getCambioDomicilio()) {
				datosarrendatario.setCAMBIODOMICILIOARR("SI");
			}

			if (arrendatario.getFechaInicio() != null && !arrendatario.getFechaInicio().toString().isEmpty()) {
				datosarrendatario.setFECHAINICIORENTING(arrendatario.getFechaInicio().toString());
			}

			if (arrendatario.getFechaFin() != null && !arrendatario.getFechaFin().toString().isEmpty()) {
				datosarrendatario.setFECHAFINRENTING(arrendatario.getFechaFin().toString());
			}

			if (arrendatario.getHoraFin() != null && !arrendatario.getHoraFin().isEmpty()) {
				if (arrendatario.getHoraFin().length() == 4) {
					datosarrendatario.setHORAFINRENTING(arrendatario.getHoraFin());
				}
				if (arrendatario.getHoraFin().length() == 5 && arrendatario.getHoraFin().contains(":")) {
					String horaFinSinPuntos = arrendatario.getHoraFin().replace(":", "");
					datosarrendatario.setHORAFINRENTING(horaFinSinPuntos);
				}
			}

			if (arrendatario.getHoraInicio() != null && !arrendatario.getHoraInicio().isEmpty()) {
				if (arrendatario.getHoraInicio().length() == 4) {
					datosarrendatario.setHORAINICIORENTING(arrendatario.getHoraInicio());
				}
				if (arrendatario.getHoraInicio().length() == 5 && arrendatario.getHoraInicio().contains(":")) {
					String horaInicioSinPuntos = arrendatario.getHoraInicio().replace(":", "");
					datosarrendatario.setHORAINICIORENTING(horaInicioSinPuntos);
				}
			}

			PersonaDto persona = arrendatario.getPersona();

			if (persona != null) {
				datosarrendatario.setDNIARR(persona.getNif());

				if (persona.getNif() != null && persona.getApellido1RazonSocial() != null && !persona.getApellido1RazonSocial().isEmpty()) {
					if (!utils.isNifNie(persona.getNif())) {
						datosarrendatario.setRAZONSOCIALARR(persona.getApellido1RazonSocial());
					} else if (utils.isNifNie(persona.getNif())) {
						datosarrendatario.setAPELLIDO1ARR(persona.getApellido1RazonSocial());
					}
				}

				datosarrendatario.setAPELLIDO2ARR(persona.getApellido2());
				datosarrendatario.setNOMBREARR(persona.getNombre());

				if (persona.getFechaNacimiento() != null && !persona.getFechaNacimiento().toString().isEmpty()) {
					datosarrendatario.setFECHANACIMIENTOARR(persona.getFechaNacimiento().toString());
				}

				if (persona.getSexo() != null && !persona.getSexo().isEmpty()) {
					datosarrendatario.setSEXOARR(SexoPersonaMatw.convertirAformatoGA(persona.getSexo()));
				}

				if (persona.getFechaCaducidadNif() != null) {
					datosarrendatario.setFECHACADUCIDADNIFARR(persona.getFechaCaducidadNif().toString());
				}

				if (persona.getFechaCaducidadAlternativo() != null) {
					datosarrendatario.setFECHACADUCIDADDOCUMENTOSUSTITUTIVOARR(persona.getFechaCaducidadAlternativo().toString());
				}

				datosarrendatario.setTIPODOCUMENTOSUSTITUTIVOARR(persona.getTipoDocumentoAlternativo());
			}

			DireccionDto direccion = arrendatario.getDireccion();

			if (direccion != null) {
				if (direccion.getIdProvincia() != null) {
					if (utils.getSiglasFromIdProvinciaMatw(direccion.getIdProvincia()) != null && !utils.getSiglasFromIdProvinciaMatw(direccion.getIdProvincia()).isEmpty()) {
						datosarrendatario.setPROVINCIAARR(TipoProvincia.fromValue(utils.getSiglasFromIdProvinciaMatw(direccion.getIdProvincia())));
					}

					if (direccion.getIdMunicipio() != null) {
						String nombreMunicipio = servicioDireccion.obtenerNombreMunicipio(direccion.getIdMunicipio(), direccion.getIdProvincia());
						datosarrendatario.setMUNICIPIOARR(nombreMunicipio);
					}
				}

				if (direccion.getIdTipoVia() != null) {
					if (utils.getIdTipoViaDGTFromIdTipoVia(direccion.getIdTipoVia()) != null && !utils.getIdTipoViaDGTFromIdTipoVia(direccion.getIdTipoVia()).isEmpty()) {
						datosarrendatario.setSIGLASDIRECCIONARR(utils.getIdTipoViaDGTFromIdTipoVia(direccion.getIdTipoVia()));
					}
				}

				datosarrendatario.setNOMBREVIADIRECCIONARR(direccion.getNombreVia());
				datosarrendatario.setPUEBLOARR(direccion.getPueblo());
				datosarrendatario.setCPARR(direccion.getCodPostal());
				datosarrendatario.setNUMERODIRECCIONARR(direccion.getNumero());
				datosarrendatario.setLETRADIRECCIONARR(direccion.getLetra());
				datosarrendatario.setESCALERADIRECCIONARR(direccion.getEscalera());
				datosarrendatario.setPISODIRECCIONARR(direccion.getPlanta());
				datosarrendatario.setPUERTADIRECCIONARR(direccion.getPuerta());
				datosarrendatario.setBLOQUEDIRECCIONARR(direccion.getBloque());

				if (direccion.getKm() != null) {
					datosarrendatario.setKMDIRECCIONARR(direccion.getKm().toString());
				}

				if (direccion.getHm() != null) {
					datosarrendatario.setHECTOMETRODIRECCIONARR(direccion.getHm().toString());
				}
			}
			mat.setDATOSARRENDATARIO(datosarrendatario);
		}
	}

	private void convertirDtoToGaRepresentanteArrendatario(MATRICULACION mat, IntervinienteTraficoDto representanteArrendatario, ObjectFactory factory, UtilesConversionesMatw utils) {
		if (representanteArrendatario != null) {
			PersonaDto persona = representanteArrendatario.getPersona();
			if (persona != null) {
				DATOSREPRESENTANTEARRENDATARIO datosrepresentanteArrendatario = factory.createFORMATOGAMATRICULACIONDATOSREPRESENTANTEARRENDATARIO();

				datosrepresentanteArrendatario.setDNIREPRESARR(persona.getNif());

				if (persona.getNif() != null && persona.getApellido1RazonSocial() != null && !persona.getApellido1RazonSocial().isEmpty() && utils.isNifNie(persona.getNif())) {
					datosrepresentanteArrendatario.setAPELLIDO1REPRESARR(persona.getApellido1RazonSocial());
				}

				datosrepresentanteArrendatario.setAPELLIDO2REPRESARR(persona.getApellido2());

				datosrepresentanteArrendatario.setNOMBREREPRESARR(persona.getNombre());

				mat.setDATOSREPRESENTANTEARRENDATARIO(datosrepresentanteArrendatario);
			}
		}
	}

	private void convertirDtoToGaConductorHabitual(MATRICULACION mat, IntervinienteTraficoDto conductorHabitual, ObjectFactory factory, UtilesConversionesMatw utils) throws ParseException {
		if (conductorHabitual != null) {
			DATOSCONDUCTORHABITUAL datosconductor = factory.createFORMATOGAMATRICULACIONDATOSCONDUCTORHABITUAL();

			if (conductorHabitual.getCambioDomicilio() != null && conductorHabitual.getCambioDomicilio()) {
				datosconductor.setCAMBIODOMICILIOCONDHABITUAL("SI");
			}

			if (conductorHabitual.getFechaFin() != null && !conductorHabitual.getFechaFin().toString().isEmpty()) {
				datosconductor.setFECHAFINCONDHABITUAL(conductorHabitual.getFechaFin().toString());
			}

			if (conductorHabitual.getHoraFin() != null && !conductorHabitual.getHoraFin().isEmpty()) {
				if (conductorHabitual.getHoraFin().length() == 4) {
					datosconductor.setHORAFINCONDHABITUAL(conductorHabitual.getHoraFin());
				}
				if (conductorHabitual.getHoraFin().length() == 5 && conductorHabitual.getHoraFin().contains(":")) {
					String horaFinSinPuntos = conductorHabitual.getHoraFin().replace(":", "");
					datosconductor.setHORAFINCONDHABITUAL(horaFinSinPuntos);
				}
			}

			PersonaDto persona = conductorHabitual.getPersona();
			if (persona != null) {
				datosconductor.setDNICONDHABITUAL(persona.getNif());

				if (persona.getNif() != null && persona.getApellido1RazonSocial() != null && !persona.getApellido1RazonSocial().isEmpty()) {
					if (!utils.isNifNie(persona.getNif())) {
						datosconductor.setRAZONSOCIALCONDHABITUAL(persona.getApellido1RazonSocial());
					} else if (utils.isNifNie(persona.getNif())) {
						datosconductor.setAPELLIDO1CONDHABITUAL(persona.getApellido1RazonSocial());
					}
				}

				datosconductor.setAPELLIDO2CONDHABITUAL(persona.getApellido2());
				datosconductor.setNOMBRECONDHABITUAL(persona.getNombre());

				if (persona.getFechaNacimiento() != null && !persona.getFechaNacimiento().toString().isEmpty()) {
					datosconductor.setFECHANACIMIENTOCONDHABITUAL(persona.getFechaNacimiento().toString());
				}

				if (persona.getSexo() != null && !persona.getSexo().isEmpty()) {
					datosconductor.setSEXOCONDHABITUAL(SexoPersonaMatw.convertirAformatoGA(persona.getSexo()));
				}

				if (persona.getFechaCaducidadNif() != null) {
					datosconductor.setFECHACADUCIDADNIFCONDHABITUAL(persona.getFechaCaducidadNif().toString());
				}

				if (persona.getFechaCaducidadAlternativo() != null) {
					datosconductor.setFECHACADUCIDADDOCUMENTOSUSTITUTIVOCONDHABITUAL(persona.getFechaCaducidadAlternativo().toString());
				}

				datosconductor.setTIPODOCUMENTOSUSTITUTIVOCONDHABITUAL(persona.getTipoDocumentoAlternativo());

			}

			DireccionDto direccion = conductorHabitual.getDireccion();
			if (direccion != null) {

				if (direccion.getIdProvincia() != null) {
					if (utils.getSiglasFromIdProvinciaMatw(direccion.getIdProvincia()) != null && !utils.getSiglasFromIdProvinciaMatw(direccion.getIdProvincia()).isEmpty()) {
						datosconductor.setPROVINCIACONDHABITUAL(TipoProvincia.fromValue(utils.getSiglasFromIdProvinciaMatw(direccion.getIdProvincia())));
					}

					if (direccion.getIdMunicipio() != null) {
						String nombreMunicipio = servicioDireccion.obtenerNombreMunicipio(direccion.getIdMunicipio(), direccion.getIdProvincia());
						datosconductor.setMUNICIPIOCONDHABITUAL(nombreMunicipio);
					}
				}

				if (direccion.getIdTipoVia() != null) {
					if (utils.getIdTipoViaDGTFromIdTipoVia(direccion.getIdTipoVia()) != null && !utils.getIdTipoViaDGTFromIdTipoVia(direccion.getIdTipoVia()).isEmpty()) {
						datosconductor.setSIGLASDIRECCIONCONDHABITUAL(utils.getIdTipoViaDGTFromIdTipoVia(direccion.getIdTipoVia()));
					}
				}

				datosconductor.setNOMBREVIADIRECCIONCONDHABITUAL(direccion.getNombreVia());
				datosconductor.setCPCONDHABITUAL(direccion.getCodPostal());
				datosconductor.setPUEBLOCONDHABITUAL(direccion.getPueblo());
				datosconductor.setNUMERODIRECCIONCONDHABITUAL(direccion.getNumero());
				datosconductor.setLETRADIRECCIONCONDHABITUAL(direccion.getLetra());
				datosconductor.setESCALERADIRECCIONCONDHABITUAL(direccion.getEscalera());
				datosconductor.setPISODIRECCIONCONDHABITUAL(direccion.getPlanta());
				datosconductor.setPUERTADIRECCIONCONDHABITUAL(direccion.getPuerta());
				datosconductor.setBLOQUEDIRECCIONCONDHABITUAL(direccion.getBloque());

				if (direccion.getKm() != null) {
					datosconductor.setKMDIRECCIONCONDHABITUAL(direccion.getKm().toString());
				}

				if (direccion.getHm() != null) {
					datosconductor.setHECTOMETRODIRECCIONCONDHABITUAL(direccion.getHm().toString());
				}

			}
			mat.setDATOSCONDUCTORHABITUAL(datosconductor);
		}
	}

	private void convertirDtoToGaVehiculo(MATRICULACION mat, TramiteTrafMatrDto tramiteTrafMatrDto, ObjectFactory factory, UtilesConversionesMatw utils) {
		VehiculoDto vehiculo = tramiteTrafMatrDto.getVehiculoDto();
		if (vehiculo != null) {
			DATOSVEHICULO datosVehiculo = factory.createFORMATOGAMATRICULACIONDATOSVEHICULO();

			// ConvertirIntegrador
			convertirDtoToGaVehiculoIntegrador(mat, vehiculo.getIntegrador(), factory, utils);

			datosVehiculo.setMATRICULA(vehiculo.getMatricula());

			if (vehiculo.getFechaMatriculacion() != null && !vehiculo.getFechaMatriculacion().isfechaNula()) {
				datosVehiculo.setFECHAMATRICULACION(vehiculo.getFechaMatriculacion().toString());
			}

			if (vehiculo.getFechaPrimMatri() != null && !vehiculo.getFechaPrimMatri().isfechaNula()) {
				datosVehiculo.setFECHAPRIMERAMATRICULACION(vehiculo.getFechaPrimMatri().toString());
			}

			datosVehiculo.setCODIGOITV(vehiculo.getCodItv());

			if (vehiculo.getFechaItv() != null && !vehiculo.getFechaItv().isfechaNula()) {
				datosVehiculo.setFECHAITV(vehiculo.getFechaItv().toString());
			}

			datosVehiculo.setNUMEROBASTIDOR(vehiculo.getBastidor());

			if (vehiculo.getServicioTrafico() != null && vehiculo.getServicioTrafico().getIdServicio() != null && !vehiculo.getServicioTrafico().getIdServicio().isEmpty()) {
				datosVehiculo.setSERVICIODESTINO(TipoServicio.fromValue(vehiculo.getServicioTrafico().getIdServicio()));
			}

			if (vehiculo.getCriterioConstruccion() != null && vehiculo.getCriterioConstruccion().length() == 2 && vehiculo.getCriterioUtilizacion() != null && vehiculo.getCriterioUtilizacion()
					.length() == 2) {
				datosVehiculo.setCLASIFICACIONVEHICULO(vehiculo.getCriterioConstruccion() + vehiculo.getCriterioUtilizacion());
			}

			if (vehiculo.getVehiUsado() != null && Boolean.TRUE.equals(vehiculo.getVehiUsado())) {
				datosVehiculo.setNUEVO("SI");
			}

			if (vehiculo.getEpigrafe() != null && !vehiculo.getEpigrafe().isEmpty() && !vehiculo.getEpigrafe().equals("-1")) {
				datosVehiculo.setEPIGRAFE(vehiculo.getEpigrafe());
			}

			if (vehiculo.getKmUso() != null) {
				datosVehiculo.setKM(vehiculo.getKmUso().toBigInteger());
			}

			if (vehiculo.getHorasUso() != null) {
				datosVehiculo.setCUENTAHORAS(vehiculo.getHorasUso().toBigInteger());
			}

			datosVehiculo.setNUMEROSERIEITV(vehiculo.getNumSerie());

			if (tramiteTrafMatrDto.getRenting() != null && tramiteTrafMatrDto.getRenting()) {
				datosVehiculo.setRENTING("SI");
			}

			if (tramiteTrafMatrDto.getCarsharing() != null && tramiteTrafMatrDto.getCarsharing()) {
				datosVehiculo.setCarsharing("SI");
			}

			DireccionDto direccion = vehiculo.getDireccion();
			if (direccion != null) {

				if (direccion.getIdProvincia() != null) {
					if (utils.getSiglasFromIdProvinciaMatw(direccion.getIdProvincia()) != null && !utils.getSiglasFromIdProvinciaMatw(direccion.getIdProvincia()).isEmpty()) {
						datosVehiculo.setPROVINCIAVEHICULO(TipoProvincia.fromValue(utils.getSiglasFromIdProvinciaMatw(direccion.getIdProvincia())));
					}

					if (direccion.getIdMunicipio() != null) {
						String nombreMunicipio = servicioDireccion.obtenerNombreMunicipio(direccion.getIdMunicipio(), direccion.getIdProvincia());
						datosVehiculo.setMUNICIPIOVEHICULO(nombreMunicipio);
					}
				}

				if (direccion.getIdTipoVia() != null) {
					if (utils.getIdTipoViaDGTFromIdTipoVia(direccion.getIdTipoVia()) != null && !utils.getIdTipoViaDGTFromIdTipoVia(direccion.getIdTipoVia()).isEmpty()) {
						datosVehiculo.setSIGLASDIRECCIONVEHICULO(utils.getIdTipoViaDGTFromIdTipoVia(direccion.getIdTipoVia()));
					}
				}

				datosVehiculo.setDOMICILIOVEHICULO(direccion.getNombreVia());
				datosVehiculo.setPUEBLOVEHICULO(direccion.getPueblo());
				datosVehiculo.setCPVEHICULO(direccion.getCodPostal());
				datosVehiculo.setNUMERODIRECCIONVEHICULO(direccion.getNumero());
				datosVehiculo.setLETRADIRECCIONVEHICULO(direccion.getLetra());
				datosVehiculo.setESCALERADIRECCIONVEHICULO(direccion.getEscalera());
				datosVehiculo.setPISODIRECCIONVEHICULO(direccion.getPlanta());
				datosVehiculo.setPUERTADIRECCIONVEHICULO(direccion.getPuerta());
				datosVehiculo.setBLOQUEDIRECCIONVEHICULO(direccion.getBloque());

				if (direccion.getKm() != null) {
					datosVehiculo.setKMDIRECCIONVEHICULO(direccion.getKm().toString());
				}

				if (direccion.getHm() != null) {
					datosVehiculo.setHECTOMETRODIRECCIONVEHICULO(direccion.getHm().toString());
				}
			}

			datosVehiculo.setNUMEROHOMOLOGACIONITV(vehiculo.getNumHomologacion());

			if (vehiculo.getClasificacionItv() != null && !vehiculo.getClasificacionItv().isEmpty()) {
				datosVehiculo.setCLASIFICACIONITV(Integer.valueOf(vehiculo.getClasificacionItv()));
			}

			if (vehiculo.getTipoTarjetaITV() != null && !vehiculo.getTipoTarjetaITV().isEmpty()) {
				datosVehiculo.setTIPOTARJETAITV(TipoTarjetaItv.valueOf(vehiculo.getTipoTarjetaITV()));
			}

			datosVehiculo.setNIVE(vehiculo.getNive());
			datosVehiculo.setTIPOINDUSTRIA(vehiculo.getTipoIndustria());

			if (vehiculo.getFichaTecnicaRD750() != null && Boolean.TRUE.equals(vehiculo.getFichaTecnicaRD750())) {
				datosVehiculo.setFICHATECNICARD750("SI");
			} else {
				datosVehiculo.setFICHATECNICARD750("NO");
			}

			datosVehiculo.setTIPOVEHICULO(vehiculo.getTipoVehiculo());
			datosVehiculo.setPROCEDENCIA(vehiculo.getProcedencia());

			if (vehiculo.getCodigoMarca() != null && !vehiculo.getCodigoMarca().isEmpty()) {
				String nombreMarca = servicioVehiculo.obtenerNombreMarca(vehiculo.getCodigoMarca(), true);
				datosVehiculo.setMARCA(nombreMarca);
			}

			datosVehiculo.setFABRICANTE(vehiculo.getFabricante());
			datosVehiculo.setMODELO(vehiculo.getModelo());

			if (vehiculo.getColor() != null && !vehiculo.getColor().equals("-")) {
				datosVehiculo.setCOLOR(TipoColor.fromValue(vehiculo.getColor()));
			}

			datosVehiculo.setTIPOITV(vehiculo.getTipoItv());
			datosVehiculo.setVARIANTEITV(vehiculo.getVariante());
			datosVehiculo.setVERSIONITV(vehiculo.getVersion());
			datosVehiculo.setPOTENCIAFISCAL(vehiculo.getPotenciaFiscal());

			if (vehiculo.getCilindrada() != null && !vehiculo.getCilindrada().isEmpty()) {
				try {
					datosVehiculo.setCILINDRADA(new BigDecimal(vehiculo.getCilindrada()));
				} catch (NumberFormatException ex) {
					log.error("ERROR EXPORTACION :" + " La cilindrada debe ser casteable a BigDecimal. Valor: " + vehiculo.getCilindrada());
				}
			}

			if (vehiculo.getTara() != null && !vehiculo.getTara().isEmpty()) {
				try {
					datosVehiculo.setTARA(new BigInteger(vehiculo.getTara()));
				} catch (NumberFormatException ex) {
					log.error("ERROR EXPORTACION :" + " La tara debe ser casteable a BigInteger. Valor: " + vehiculo.getTara());
				}
			}

			if (vehiculo.getPesoMma() != null && !vehiculo.getPesoMma().isEmpty()) {
				try {
					datosVehiculo.setMASA(new BigInteger(vehiculo.getPesoMma()));
				} catch (NumberFormatException ex) {
					log.error("ERROR EXPORTACION :" + " La masa debe ser casteable a BigInteger. Valor: " + vehiculo.getPesoMma());
				}
			}

			if (vehiculo.getPlazas() != null && !vehiculo.getPlazas().equals("")) {
				datosVehiculo.setPLAZAS(vehiculo.getPlazas().toBigInteger());
			}

			if (vehiculo.getCarburante() != null && !vehiculo.getCarburante().isEmpty()) {
				if (Combustible.Otros.getValorEnum().equals(vehiculo.getCarburante())) {
					datosVehiculo.setIDCARBURANTE(TipoCombustible.O);
				} else {
					datosVehiculo.setIDCARBURANTE(TipoCombustible.fromValue(vehiculo.getCarburante()));
				}
			}

			datosVehiculo.setRELACIONPOTENCIAPESO(vehiculo.getPotenciaPeso());

			if (vehiculo.getNumPlazasPie() != null) {
				datosVehiculo.setPLAZASPIE(vehiculo.getNumPlazasPie().toBigInteger());
			}

			if (vehiculo.getCo2() != null && !vehiculo.getCo2().isEmpty()) {
				try {
					datosVehiculo.setEMISIONCO2(new BigDecimal(vehiculo.getCo2()));
				} catch (NumberFormatException ex) {
					log.error("ERROR EXPORTACION :" + "La parte entera del co2 debe ser " + "casteable a BigInteger. Valor: " + vehiculo.getCo2());
				}
			}

			if (vehiculo.getMom() != null) {
				try {
					datosVehiculo.setMOM(new BigInteger(vehiculo.getMom().toString()));
				} catch (NumberFormatException ex) {
					log.error("ERROR EXPORTACION :" + " El MOM debe ser casteable a BigInteger. Valor: " + vehiculo.getMom());
				}
			}

			if (vehiculo.getCheckFechaCaducidadITV() != null && Boolean.TRUE.equals(vehiculo.getCheckFechaCaducidadITV())) {
				datosVehiculo.setCHECKCADUCIDADITV("SI");
			} else {
				datosVehiculo.setCHECKCADUCIDADITV("NO");
			}

			datosVehiculo.setMATRICULAORIGEN(vehiculo.getMatriculaOrigen());
			datosVehiculo.setMATRICULAORIGEXTR(vehiculo.getMatriculaOrigExtr());

			if (vehiculo.getMotivoItv() != null && !vehiculo.getMotivoItv().isEmpty()) {
				if (TipoInspeccion.M.toString().equals(vehiculo.getMotivoItv()) || TipoInspeccion.E.toString().equals(vehiculo.getMotivoItv())) {
					datosVehiculo.setTIPOINSPECCIONITV(TipoInspeccion.fromValue(vehiculo.getMotivoItv()));
				}
			}

			datosVehiculo.setPAISFABRICACION(vehiculo.getPaisFabricacion());
			datosVehiculo.setPOTENCIANETA(vehiculo.getPotenciaNeta());

			if (vehiculo.getCarroceria() != null && !vehiculo.getCarroceria().isEmpty()) {
				datosVehiculo.setIDCARROCERIA(TipoCarroceria.fromValue(vehiculo.getCarroceria()));
			}

			datosVehiculo.setIDHOMOLOGACION(vehiculo.getIdDirectivaCee());

			if (vehiculo.getDistanciaEjes() != null && !vehiculo.getDistanciaEjes().toString().isEmpty()) {
				datosVehiculo.setDISTANCIAENTREEJES(vehiculo.getDistanciaEjes().intValue());
			}

			if (vehiculo.getMtmaItv() != null && !vehiculo.getMtmaItv().isEmpty()) {
				try {
					datosVehiculo.setMASAMAXIMAADMISIBLE(new BigInteger(vehiculo.getMtmaItv()));
				} catch (NumberFormatException ex) {
					log.error("ERROR EXPORTACION :" + " La masa maxima admisible debe ser casteable a BigInteger. Valor: " + vehiculo.getMtmaItv());
				}
			}

			if (vehiculo.getViaAnterior() != null) {
				datosVehiculo.setVIAANTERIOR(vehiculo.getViaAnterior().intValue());
			}

			if (vehiculo.getViaPosterior() != null) {
				datosVehiculo.setVIAPOSTERIOR(vehiculo.getViaPosterior().intValue());
			}

			datosVehiculo.setCODIGOECO(vehiculo.getCodigoEco());

			if (vehiculo.getImportado() != null && Boolean.TRUE.equals(vehiculo.getImportado())) {
				datosVehiculo.setIMPORTADO(TipoSINO.SI.value());
			}

			if (vehiculo.getSubastado() != null && Boolean.TRUE.equals(vehiculo.getSubastado())) {
				datosVehiculo.setSUBASTA(TipoSINO.SI.value());
			}

			if (vehiculo.getConsumo() != null && !vehiculo.getConsumo().toString().isEmpty()) {
				datosVehiculo.setCONSUMO(String.valueOf(vehiculo.getConsumo()));
			}

			if (vehiculo.getEcoInnovacion() != null && !vehiculo.getEcoInnovacion().isEmpty()) {
				if (vehiculo.getEcoInnovacion().toString().trim().toUpperCase().equals(TipoSN.S.value()) || vehiculo.getEcoInnovacion().toString().trim().toUpperCase().equals(TipoSINO.SI.value())) {
					datosVehiculo.setECOINNOVACION(TipoSINO.SI.value());
				} else {
					datosVehiculo.setECOINNOVACION(TipoSINO.NO.value());
				}
			}

			datosVehiculo.setNIVELEMISIONES(vehiculo.getNivelEmisiones());

			if (vehiculo.getReduccionEco() != null) {
				datosVehiculo.setREDUCCIONECO((vehiculo.getReduccionEco().intValue()));
			}

			if (vehiculo.getTipoAlimentacion() != null && !vehiculo.getTipoAlimentacion().isEmpty()) {
				datosVehiculo.setIDALIMENTACION(TipoAlimentacion.fromValue(vehiculo.getTipoAlimentacion()));
			}

			if (vehiculo.getCategoriaElectrica() != null && !vehiculo.getCategoriaElectrica().isEmpty()) {
				datosVehiculo.setCATEGORIAELECTRICA(TipoCategoriaElectrica.fromValue(vehiculo.getCategoriaElectrica()));
			}

			if (vehiculo.getAutonomiaElectrica() != null) {
				datosVehiculo.setAUTONOMIAELECTRICA(vehiculo.getAutonomiaElectrica().intValue());
			}

			if (vehiculo.getTipoTarjetaITV() != null && (vehiculo.getTipoTarjetaITV().equals(TipoTarjetaITV.A.getValorEnum()) || vehiculo.getTipoTarjetaITV().equals(TipoTarjetaITV.AL.getValorEnum())
					|| vehiculo.getTipoTarjetaITV().equals(TipoTarjetaITV.AR.getValorEnum()) || vehiculo.getTipoTarjetaITV().equals(TipoTarjetaITV.AT.getValorEnum()) || vehiculo.getTipoTarjetaITV()
							.equals(TipoTarjetaITV.D.getValorEnum()) || vehiculo.getTipoTarjetaITV().equals(TipoTarjetaITV.DL.getValorEnum()) || vehiculo.getTipoTarjetaITV().equals(TipoTarjetaITV.DR
									.getValorEnum()) || vehiculo.getTipoTarjetaITV().equals(TipoTarjetaITV.DT.getValorEnum()))) {

				if (vehiculo.getCodigoMarcaBase() != null && !vehiculo.getCodigoMarcaBase().isEmpty()) {
					String nombreMarca = servicioVehiculo.obtenerNombreMarca(vehiculo.getCodigoMarcaBase(), true);
					datosVehiculo.setMARCABASE(nombreMarca);
				}

				datosVehiculo.setFABRICANTEBASE(vehiculo.getFabricanteBase());
				datosVehiculo.setTIPOBASE(vehiculo.getTipoBase());
				datosVehiculo.setVARIANTEBASE(vehiculo.getVarianteBase());

				datosVehiculo.setVERSIONBASE(vehiculo.getVersionBase());
				datosVehiculo.setNUMEROHOMOLOGACIONBASE(vehiculo.getNumHomologacionBase());

				if (vehiculo.getMomBase() != null) {
					datosVehiculo.setMOMBASE(vehiculo.getMomBase().intValue());
				}

			}
			mat.setDATOSVEHICULO(datosVehiculo);
		}
	}

	private void convertirDtoToGaVehiculoIntegrador(MATRICULACION mat, PersonaDto integrador, ObjectFactory factory, UtilesConversionesMatw utils) {
		if (integrador != null) {
			DATOSIMPORTADOR datosImportador = factory.createFORMATOGAMATRICULACIONDATOSIMPORTADOR();

			datosImportador.setDNIIMPORTADOR(integrador.getNif());

			if (integrador.getNif() != null && integrador.getApellido1RazonSocial() != null && !integrador.getApellido1RazonSocial().isEmpty()) {
				if (!utils.isNifNie(integrador.getNif())) {
					datosImportador.setRAZONSOCIALIMPORTADOR(integrador.getApellido1RazonSocial());
				} else if (utils.isNifNie(integrador.getNif())) {
					datosImportador.setAPELLIDO1IMPORTADOR(integrador.getApellido1RazonSocial());
				}
			}

			datosImportador.setAPELLIDO2IMPORTADOR(integrador.getApellido2());
			datosImportador.setNOMBREIMPORTADOR(integrador.getNombre());

			mat.setDATOSIMPORTADOR(datosImportador);
		}
	}

	private void convertirDtoToGaLimitacion(MATRICULACION mat, TramiteTrafMatrDto tramiteTrafMatrDto, ObjectFactory factory) {
		boolean hayLimitacion = false;
		DATOSLIMITACION limitacion = factory.createFORMATOGAMATRICULACIONDATOSLIMITACION();

		if (tramiteTrafMatrDto.getIedtm() != null && !tramiteTrafMatrDto.getIedtm().isEmpty()) {
			limitacion.setTIPOLIMITACION(tramiteTrafMatrDto.getIedtm());
		}

		if (tramiteTrafMatrDto.getFechaIedtm() != null && !tramiteTrafMatrDto.getFechaIedtm().toString().equals("")) {
			limitacion.setFECHALIMITACION(tramiteTrafMatrDto.getFechaIedtm().toString());
			hayLimitacion = true;
		}

		if (tramiteTrafMatrDto.getIedtm() != null && !tramiteTrafMatrDto.getIedtm().isEmpty()) {
			limitacion.setNUMEROREGISTROLIMITACION(tramiteTrafMatrDto.getIedtm());
		}

		if (tramiteTrafMatrDto.getFinancieraIedtm() != null && !tramiteTrafMatrDto.getFinancieraIedtm().isEmpty()) {
			limitacion.setFINANCIERALIMITACION(tramiteTrafMatrDto.getFinancieraIedtm());
			hayLimitacion = true;
		}

		if (hayLimitacion) {
			mat.setDATOSLIMITACION(limitacion);
		}
	}

	private void convertirDtoToGaImpuestos(MATRICULACION mat, TramiteTrafMatrDto tramiteTrafMatrDto, ObjectFactory factory, boolean tienePermisoIVTM) {
		boolean hayImpuestos = false;
		DATOSIMPUESTOS impuestos = factory.createFORMATOGAMATRICULACIONDATOSIMPUESTOS();

		if (tramiteTrafMatrDto.getCem() != null && !tramiteTrafMatrDto.getCem().isEmpty()) {
			impuestos.setCODIGOELECTRONICOAEAT(tramiteTrafMatrDto.getCem());
			hayImpuestos = true;
		}

		if (tramiteTrafMatrDto.getCema() != null && !tramiteTrafMatrDto.getCema().isEmpty()) {
			impuestos.setCEMA(tramiteTrafMatrDto.getCema());
			hayImpuestos = true;
		}

		if (tramiteTrafMatrDto.getExentoCem() != null && tramiteTrafMatrDto.getExentoCem()) {
			impuestos.setEXENTOCEM(TipoSINO.SI.value());
			hayImpuestos = true;
		}

		if (tramiteTrafMatrDto.getJustificadoIvtm() != null && tramiteTrafMatrDto.getJustificadoIvtm()) {
			impuestos.setJUSTIFICADOIVTM(TipoSINO.SI.value());
		}

		hayImpuestos = convertirDtoToGaImpuestos506(impuestos, tramiteTrafMatrDto, factory, hayImpuestos);

		hayImpuestos = convertirDtoToGaImpuestos576(impuestos, tramiteTrafMatrDto, factory, hayImpuestos);

		hayImpuestos = convertirDtoToGaImpuestosNrc(impuestos, tramiteTrafMatrDto, factory, hayImpuestos);

		hayImpuestos = convertirDtoToGaImpuestosIVTM(impuestos, tramiteTrafMatrDto, factory, hayImpuestos, tienePermisoIVTM);

		if (hayImpuestos) {
			mat.setDATOSIMPUESTOS(impuestos);
		}
	}

	private boolean convertirDtoToGaImpuestos506(DATOSIMPUESTOS impuestos, TramiteTrafMatrDto tramiteTrafMatrDto, ObjectFactory factory, boolean hayImpuestos) {
		boolean hay0506 = false;
		DATOS0506 datos0506 = factory.createFORMATOGAMATRICULACIONDATOSIMPUESTOSDATOS0506();

		if (tramiteTrafMatrDto.getIdReduccion05() != null && !tramiteTrafMatrDto.getIdReduccion05().isEmpty()) {
			datos0506.setMOTIVOEXENCION05(Tipo05.fromValue(tramiteTrafMatrDto.getIdReduccion05()));
			hay0506 = true;
			hayImpuestos = true;
		}

		if (tramiteTrafMatrDto.getIdNoSujeccion06() != null && !tramiteTrafMatrDto.getIdNoSujeccion06().isEmpty()) {
			datos0506.setMOTIVOEXENCION06(Tipo06.fromValue(tramiteTrafMatrDto.getIdNoSujeccion06()));
			hay0506 = true;
			hayImpuestos = true;
		}

		if (hay0506) {
			impuestos.setDATOS0506(datos0506);
		}
		return hayImpuestos;
	}

	private boolean convertirDtoToGaImpuestosIVTM(DATOSIMPUESTOS impuestos, TramiteTrafMatrDto tramiteTrafMatrDto, ObjectFactory factory, boolean hayImpuestos, boolean tienePermisoIVTM) {
		DATOSIMVTM ivtm = (DATOSIMVTM) servicioIvtmMatriculacion.obtenerDatosParaImportacion(tramiteTrafMatrDto.getNumExpediente(), tienePermisoIVTM);
		if (ivtm != null) {
			impuestos.setDATOSIMVTM(ivtm);
			hayImpuestos = true;
		}
		return hayImpuestos;
	}

	private boolean convertirDtoToGaImpuestos576(DATOSIMPUESTOS impuestos, TramiteTrafMatrDto tramiteTrafMatrDto, ObjectFactory factory, boolean hayImpuestos) {
		boolean hay576 = false;
		FORMATOGA.MATRICULACION.DATOSIMPUESTOS.DATOS576 datos576 = factory.createFORMATOGAMATRICULACIONDATOSIMPUESTOSDATOS576();

		if (tramiteTrafMatrDto.getImporte576() != null) {
			datos576.setIMPORTETOTAL576(tramiteTrafMatrDto.getImporte576());
			hay576 = true;
			hayImpuestos = true;
		}

		if (tramiteTrafMatrDto.getEjercicio576() != null) {
			datos576.setEJERCICIODEVENGO576(tramiteTrafMatrDto.getEjercicio576().intValue());
			hay576 = true;
			hayImpuestos = true;
		}

		if (tramiteTrafMatrDto.getObservaciones576() != null && !tramiteTrafMatrDto.getObservaciones576().isEmpty()) {
			datos576.setOBSERVACIONES576(tramiteTrafMatrDto.getObservaciones576());
			hay576 = true;
			hayImpuestos = true;
		}

		if (tramiteTrafMatrDto.getExento576() != null && tramiteTrafMatrDto.getExento576()) {
			datos576.setEXENTO576("SI");
			hay576 = true;
			hayImpuestos = true;
		}

		if (tramiteTrafMatrDto.getBaseImponible576() != null && tramiteTrafMatrDto.getBaseImponible576() != BigDecimal.ZERO) {
			try {
				datos576.setBASEIMPONIBLE576(tramiteTrafMatrDto.getBaseImponible576());
				hay576 = true;
				hayImpuestos = true;
			} catch (NumberFormatException ex) {
				log.error("ERROR EXPORTACION :" + " La base imponible 576 debe ser casteable a BigDecimal. Valor: " + tramiteTrafMatrDto.getBaseImponible576());
			}
		}

		if (tramiteTrafMatrDto.getBaseImpoReducida576() != null && tramiteTrafMatrDto.getBaseImpoReducida576() != BigDecimal.ZERO) {
			try {
				datos576.setBASEIMPONIBLEREDUCIDA576(tramiteTrafMatrDto.getBaseImpoReducida576());
				hay576 = true;
				hayImpuestos = true;
			} catch (NumberFormatException ex) {
				log.error("ERROR EXPORTACION :" + " La base imponible reducida 576 debe ser casteable a BigDecimal. Valor: " + tramiteTrafMatrDto.getBaseImpoReducida576());
			}
		}

		if (tramiteTrafMatrDto.getTipoGravamen576() != null && tramiteTrafMatrDto.getTipoGravamen576() != BigDecimal.ZERO) {
			datos576.setTIPOGRAVAMEN576(tramiteTrafMatrDto.getTipoGravamen576());
			hay576 = true;
			hayImpuestos = true;
		}

		if (tramiteTrafMatrDto.getDeduccionLineal576() != null && tramiteTrafMatrDto.getDeduccionLineal576() != BigDecimal.ZERO) {
			try {
				datos576.setDEDUCCIONLINEAL576(tramiteTrafMatrDto.getDeduccionLineal576());
				hay576 = true;
				hayImpuestos = true;
			} catch (NumberFormatException ex) {
				log.error("ERROR EXPORTACION :" + " La deduccion lineal 576 debe ser casteable a BigDecimal. Valor: " + tramiteTrafMatrDto.getDeduccionLineal576());
			}
		}

		if (tramiteTrafMatrDto.getCuota576() != null && tramiteTrafMatrDto.getCuota576() != BigDecimal.ZERO) {
			try {
				datos576.setCUOTA576(tramiteTrafMatrDto.getCuota576());
				hay576 = true;
				hayImpuestos = true;
			} catch (NumberFormatException ex) {
				log.error("ERROR EXPORTACION :" + " La cuota 576 debe ser casteable a BigDecimal. Valor: " + tramiteTrafMatrDto.getCuota576());
			}
		}

		if (tramiteTrafMatrDto.getCuotaIngresar576() != null && tramiteTrafMatrDto.getCuotaIngresar576() != BigDecimal.ZERO) {
			try {
				datos576.setCUOTAINGRESAR576(tramiteTrafMatrDto.getCuotaIngresar576());
				hay576 = true;
				hayImpuestos = true;
			} catch (NumberFormatException ex) {
				log.error("ERROR EXPORTACION :" + " La cuota a ingresar 576 debe ser casteable a BigDecimal. Valor: " + tramiteTrafMatrDto.getCuotaIngresar576());
			}
		}

		if (tramiteTrafMatrDto.getDeducir576() != null && tramiteTrafMatrDto.getDeducir576() != BigDecimal.ZERO) {
			try {
				datos576.setADEDUCIR576(tramiteTrafMatrDto.getDeducir576());
				hay576 = true;
				hayImpuestos = true;
			} catch (NumberFormatException ex) {
				log.error("ERROR EXPORTACION :" + " El 'a deducir 576' debe ser casteable a BigDecimal. Valor: " + tramiteTrafMatrDto.getDeducir576());
			}
		}

		if (tramiteTrafMatrDto.getLiquidacion576() != null && tramiteTrafMatrDto.getLiquidacion576() != BigDecimal.ZERO) {
			try {
				datos576.setRESULTADOLIQUIDACION576(tramiteTrafMatrDto.getLiquidacion576());
				hay576 = true;
				hayImpuestos = true;
			} catch (NumberFormatException ex) {
				log.error("ERROR EXPORTACION :" + " El resultado de la liquidacion 576 debe ser casteable a BigDecimal. Valor: " + tramiteTrafMatrDto.getLiquidacion576());
			}
		}

		if (tramiteTrafMatrDto.getNumDeclaracionComp576() != null && !tramiteTrafMatrDto.getNumDeclaracionComp576().isEmpty()) {
			datos576.setDECLARACIONCOMPLEMENTARIA576(tramiteTrafMatrDto.getNumDeclaracionComp576());
			hay576 = true;
			hayImpuestos = true;
		}

		if (tramiteTrafMatrDto.getVehiculoPrever() != null) {
			if (tramiteTrafMatrDto.getVehiculoPrever().getMatricula() != null && !tramiteTrafMatrDto.getVehiculoPrever().getMatricula().isEmpty()) {
				datos576.setMATRICULAPREVER576(tramiteTrafMatrDto.getVehiculoPrever().getMatricula());
				hay576 = true;
				hayImpuestos = true;
			}

			if (tramiteTrafMatrDto.getVehiculoPrever().getTipoItv() != null && !tramiteTrafMatrDto.getVehiculoPrever().getTipoItv().isEmpty()) {
				datos576.setTIPOITVPREVER576(tramiteTrafMatrDto.getVehiculoPrever().getTipoItv());
				hay576 = true;
				hayImpuestos = true;
			}

			if (tramiteTrafMatrDto.getVehiculoPrever().getCodigoMarca() != null && !tramiteTrafMatrDto.getVehiculoPrever().getCodigoMarca().isEmpty()) {
				String nombreMarca = servicioVehiculo.obtenerNombreMarca(tramiteTrafMatrDto.getVehiculoPrever().getCodigoMarca(), true);
				datos576.setMARCAPREVER576(nombreMarca);
				hay576 = true;
				hayImpuestos = true;
			}

			if (tramiteTrafMatrDto.getVehiculoPrever().getModelo() != null && !tramiteTrafMatrDto.getVehiculoPrever().getModelo().isEmpty()) {
				datos576.setMODELOPREVER576(tramiteTrafMatrDto.getVehiculoPrever().getModelo());
				hay576 = true;
				hayImpuestos = true;
			}

			if (tramiteTrafMatrDto.getVehiculoPrever().getBastidor() != null && !tramiteTrafMatrDto.getVehiculoPrever().getBastidor().isEmpty()) {
				datos576.setNUMEROBASTIDORPREVER576(tramiteTrafMatrDto.getVehiculoPrever().getBastidor());
				hay576 = true;
				hayImpuestos = true;
			}

			if ((tramiteTrafMatrDto.getVehiculoPrever().getCriterioConstruccion() != null && tramiteTrafMatrDto.getVehiculoPrever().getCriterioConstruccion().length() == 2 && tramiteTrafMatrDto
					.getVehiculoPrever().getCriterioUtilizacion() != null && tramiteTrafMatrDto.getVehiculoPrever().getCriterioUtilizacion().length() == 2)) {
				datos576.setCLASIFICACIONPREVER576(tramiteTrafMatrDto.getVehiculoPrever().getCriterioConstruccion() + tramiteTrafMatrDto.getVehiculoPrever().getCriterioUtilizacion());
				hay576 = true;
				hayImpuestos = true;
			}
		}

		if (tramiteTrafMatrDto.getCausaHechoImpon576() != null && !tramiteTrafMatrDto.getCausaHechoImpon576().isEmpty()) {
			datos576.setCAUSAHECHOIMPONIBLE(tramiteTrafMatrDto.getCausaHechoImpon576());
			hay576 = true;
			hayImpuestos = true;
		}

		if (hay576) {
			impuestos.setDATOS576(datos576);
		}

		return hayImpuestos;
	}

	private boolean convertirDtoToGaImpuestosNrc(DATOSIMPUESTOS impuestos, TramiteTrafMatrDto tramiteTrafMatrDto, ObjectFactory factory, boolean hayImpuestos) {
		boolean hayNrc = false;
		DATOSNRC nrc = factory.createFORMATOGAMATRICULACIONDATOSIMPUESTOSDATOSNRC();

		if (tramiteTrafMatrDto.getNrc576() != null && !tramiteTrafMatrDto.getNrc576().isEmpty()) {
			nrc.setCODIGONRC(tramiteTrafMatrDto.getNrc576());
			hayNrc = true;
			hayImpuestos = true;
		}

		if (tramiteTrafMatrDto.getFechaPago576() != null && !tramiteTrafMatrDto.getFechaPago576().isfechaNula()) {
			nrc.setFECHAOPERACIONNRC(tramiteTrafMatrDto.getFechaPago576().toString());
			hayNrc = true;
			hayImpuestos = true;
		}

		if (hayNrc) {
			impuestos.setDATOSNRC(nrc);
		}
		return hayImpuestos;
	}

	private FORMATOGA instanciarCompleto() {
		ObjectFactory factory = new ObjectFactory();

		FORMATOGA ga = factory.createFORMATOGA();

		ga.setCABECERA(factory.createFORMATOGACABECERA());
		ga.getCABECERA().setDATOSGESTORIA(factory.createFORMATOGACABECERADATOSGESTORIA());
		ga.setPlataforma("OEGAM");
		return ga;
	}
}
