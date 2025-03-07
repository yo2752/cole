package trafico.beans.utiles;

import java.io.File;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;

import javax.xml.bind.Marshaller;

import org.gestoresmadrid.core.springContext.ContextoSpring;
import org.gestoresmadrid.core.vehiculo.model.enumerados.Color;
import org.gestoresmadrid.oegam2comun.contrato.model.service.ServicioContrato;
import org.gestoresmadrid.oegamComun.contrato.view.dto.ContratoDto;
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
import escrituras.utiles.enumerados.SexoPersona;
import general.utiles.UtilesXML;
import trafico.beans.IntervinienteTrafico;
import trafico.beans.TramiteTraficoMatriculacionBean;
import trafico.beans.VehiculoBean;
import trafico.beans.jaxb.matriculacion.FORMATOGA;
import trafico.beans.jaxb.matriculacion.Tipo05;
import trafico.beans.jaxb.matriculacion.Tipo06;
import trafico.beans.jaxb.matriculacion.TipoAlimentacion;
import trafico.beans.jaxb.matriculacion.TipoCarroceria;
import trafico.beans.jaxb.matriculacion.TipoColor;
import trafico.beans.jaxb.matriculacion.TipoCombustible;
import trafico.beans.jaxb.matriculacion.TipoEstadoCivil;
import trafico.beans.jaxb.matriculacion.TipoFabricacion;
import trafico.beans.jaxb.matriculacion.TipoHomologacion;
import trafico.beans.jaxb.matriculacion.TipoMotivoExencion;
import trafico.beans.jaxb.matriculacion.TipoMotivoTutela;
import trafico.beans.jaxb.matriculacion.TipoPaisImportacion;
import trafico.beans.jaxb.matriculacion.TipoProvincia;
import trafico.beans.jaxb.matriculacion.TipoSINO;
import trafico.beans.jaxb.matriculacion.TipoServicio;
import trafico.beans.jaxb.matriculacion.TipoTarjetaItv;
import trafico.modelo.ivtm.IVTMModeloMatriculacionInterface;
import trafico.modelo.ivtm.impl.IVTMModeloMatriculacionImpl;
import trafico.utiles.UtilesConversiones;
import trafico.utiles.XMLGAFactory;
import trafico.utiles.constantes.Constantes;
import trafico.utiles.constantes.ConstantesTrafico;
import trafico.utiles.enumerados.Combustible;
import trafico.utiles.enumerados.ConceptoTutela;
import trafico.utiles.enumerados.Observaciones;
import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;
import utilidades.web.excepciones.XmlNoValidoExcepcion;

public class XMLMatriculacion {

	private static ILoggerOegam log = LoggerOegam.getLogger(XMLMatriculacion.class);

	@Autowired
	private GestorPropiedades gestorPropiedades;
	
	@Autowired
	UtilesFecha utilesFecha;

	@Autowired
	private Utiles utiles;

	@Autowired
	private UtilesConversiones utilesConversiones;

	public XMLMatriculacion() {
		super();
		SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
	}


	/**
	 * Convierte el FORMATOGA a XML
	 * @param ga
	 */
	public ResultBean FORMATOGAtoXML(FORMATOGA ga, String idSession) throws Exception{
		ResultBean resultado = new ResultBean();
		//Clasificar objeto en archivo.
		String nodo = gestorPropiedades.valorPropertie(ConstantesGestorFicheros.RUTA_ARCHIVOS_TEMP);
		File salida = new File(nodo+"/temp/matr" + idSession + ".xml");
		try {
			//Crear clasificador
			Marshaller m = new XMLGAFactory().getMatriculacionContext().createMarshaller();            
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
		}
		finally {
			try{
				// Validación interna contra el esquema antes de borrar el temporal:
				UtilesXML.validarXML(salida, gestorPropiedades.valorPropertie(Constantes.PROPERTIES_RUTA_ESQUEMA_MATRICULACION));
			} catch (XmlNoValidoExcepcion e) {
				log.error(e);
			} catch (Exception e) {
				log.error(e);
			}
			salida.delete();
		}

		return resultado;
	}


	public FORMATOGA convertirAFORMATOGA(List<TramiteTraficoMatriculacionBean> listaMatriculacion, boolean tienePermisoIVTM)throws Exception {

		trafico.beans.jaxb.matriculacion.ObjectFactory factory = new trafico.beans.jaxb.matriculacion.ObjectFactory();
		FORMATOGA ga = factory.createFORMATOGA();

		//CABECERA
		ga.setCABECERA(factory.createFORMATOGACABECERA());
		ga.getCABECERA().setDATOSGESTORIA(factory.createFORMATOGACABECERADATOSGESTORIA());
		ga.setPlataforma("OEGAM");
	

		//Añado Cabecera
		ServicioContrato servicioContrato = ContextoSpring.getInstance().getBean(ServicioContrato.class);
		ContratoDto contrato = servicioContrato.getContratoDto(listaMatriculacion.get(0).getTramiteTraficoBean().getIdContrato());

		
		ga.getCABECERA().getDATOSGESTORIA().setNIF(contrato.getCif());
		ga.getCABECERA().getDATOSGESTORIA().setNOMBRE(contrato.getRazonSocial());
		ga.getCABECERA().getDATOSGESTORIA().setPROFESIONAL(new BigInteger(contrato.getColegiadoDto().getNumColegiado()));
		ga.getCABECERA().getDATOSGESTORIA().setPROVINCIA(TipoProvincia.fromValue(utilesConversiones.getSiglasFromIdProvincia(contrato.getIdProvincia())));
		
		
		
		// Para evitar que se importen trámites exportados desde OEGAM:
		ga.getCABECERA().getDATOSGESTORIA().setOEGAMDGT(Base64.encodeBytes(Constantes.ORIGEN_OEGAM.getBytes()));

		//Añado Fecha de creación
		ga.setFechaCreacion(utilesFecha.getFechaHoy());

		//Añado los trámites
		for (int i=0;i<listaMatriculacion.size();i++) {
			ga.getMATRICULACION().add(convertirBeanToGa(listaMatriculacion.get(i), tienePermisoIVTM));
		}

		return ga;
	}
	
	private TipoEstadoCivil convertirAFormatoGA(String param){
		if (param == null)
			return null;

		if("CASADO(A)".equalsIgnoreCase(param))
			return TipoEstadoCivil.CASADO_A;

		if("SOLTERO(A)".equalsIgnoreCase(param))
			return TipoEstadoCivil.SOLTERO_A;

		if("VIUDO(A)".equalsIgnoreCase(param))
			return TipoEstadoCivil.VIUDO_A;

		if("DIVORCIADO(A)".equalsIgnoreCase(param))
			return TipoEstadoCivil.DIVORCIADO_A;

		if("SEPARADO(A)".equalsIgnoreCase(param))
			return TipoEstadoCivil.SEPARADO_A;

		return null;
	}

	private FORMATOGA.MATRICULACION convertirBeanToGa(TramiteTraficoMatriculacionBean bean, boolean tienePermisoIVTM) throws Exception{
		//FORMATOGA.MATRICULACION mat = instanciarMATRICULACION();
		trafico.beans.jaxb.matriculacion.ObjectFactory factory = 
			new trafico.beans.jaxb.matriculacion.ObjectFactory();
		FORMATOGA.MATRICULACION mat = factory.createFORMATOGAMATRICULACION();

		/****************************************************************************************************************
		 * RELLENAMOS EL FORMATOGA CON LOS DATOS QUE TENGAMOS EN EL BEAN DE PANTALLA ***********************************
		 ***************************************************************************************************************/

		// NÚMERO DE EXPEDIENTE
		if(bean.getTramiteTraficoBean().getNumExpediente()!= null && 
				!bean.getTramiteTraficoBean().getNumExpediente().equals("")){
			mat.setNUMEROEXPEDIENTE(bean.getTramiteTraficoBean().getNumExpediente().toString());
		}

		// REFERENCIA PROPIA
		if(bean.getTramiteTraficoBean().getReferenciaPropia()!= null && 
				!bean.getTramiteTraficoBean().getReferenciaPropia().equals("")){
			mat.setREFERENCIAPROPIA(bean.getTramiteTraficoBean().
					getReferenciaPropia());
		}

		// NUMERO PROFESIONAL
		if(bean.getTramiteTraficoBean().getNumColegiado()!= null && 
				!bean.getTramiteTraficoBean().getNumColegiado().equals("")){
			mat.setNUMEROPROFESIONAL(new BigInteger("0" + bean.getTramiteTraficoBean().
					getNumColegiado().toString()));
		}

		// FECHA CREACION
		if(bean.getTramiteTraficoBean().getFechaCreacion()!= null && 
				!bean.getTramiteTraficoBean().getFechaCreacion().isfechaNula()){
			mat.setFECHACREACION(bean.getTramiteTraficoBean().getFechaCreacion().toString());
		}

		// FECHA PRESENTACIÓN
		if(bean.getTramiteTraficoBean().getFechaPresentacion()!= null && 
				!bean.getTramiteTraficoBean().getFechaPresentacion().isfechaNula()){
			mat.setFECHAPRESENTACION(bean.getTramiteTraficoBean().getFechaPresentacion().toString());
		}

		// JEFATURA
		if(bean.getTramiteTraficoBean().getJefaturaTrafico() != null && 
				bean.getTramiteTraficoBean().getJefaturaTrafico().getJefaturaProvincial() != null && 
				!bean.getTramiteTraficoBean().getJefaturaTrafico().getJefaturaProvincial().equals("")){
			mat.setJEFATURA(bean.getTramiteTraficoBean().getJefaturaTrafico().getJefaturaProvincial());
		}

		// SUCURSAL
		if(bean.getTramiteTraficoBean().getJefaturaTrafico() != null && 
				bean.getTramiteTraficoBean().getJefaturaTrafico().getSucursal() != null && 
				!bean.getTramiteTraficoBean().getJefaturaTrafico().getSucursal().equals("")){
			mat.setSUCURSAL(bean.getTramiteTraficoBean().getJefaturaTrafico().getSucursal());
		}

		// TIPO_TASA
		if(bean.getTramiteTraficoBean().getTasa() != null && 
				bean.getTramiteTraficoBean().getTasa().getTipoTasa() != null && 
				!bean.getTramiteTraficoBean().getTasa().getTipoTasa().equals("")){  
			mat.setTIPOTASA(bean.getTramiteTraficoBean().getTasa().getTipoTasa());
		}

		// CODIGO TASA
		if(bean.getTramiteTraficoBean().getTasa() != null && 
				bean.getTramiteTraficoBean().getTasa().getCodigoTasa() != null && 
				!bean.getTramiteTraficoBean().getTasa().getCodigoTasa().equals("")){  
			mat.setTASA(bean.getTramiteTraficoBean().getTasa().getCodigoTasa());
		}

		// OBSERVACIONES
		if(bean.getTramiteTraficoBean().getAnotaciones() != null &&  
				!bean.getTramiteTraficoBean().getAnotaciones().equals("")){  
			mat.setOBSERVACIONES(bean.getTramiteTraficoBean().getAnotaciones());
		}

		// ENTREGA_FACT_MATRICULACION
		if(bean.getEntregaFactMatriculacion() != null &&  
				!bean.getEntregaFactMatriculacion().equals("")){
			
			mat.setENTREGAFACTMATRICULACION(bean.getEntregaFactMatriculacion().equalsIgnoreCase("true")?TipoSINO.SI.name():TipoSINO.NO.name());
		}
		
		// VERSION MATE (para evitar que se importen tramites con el xsd antiguo)
		mat.setVERSIONMATE(Constantes.VERSION_ELECTRONICA_MATE);

		// Flag. Indica si meter titular en la matriculación.
		boolean hayTitular = false;

		//DATOS TITULAR
		FORMATOGA.MATRICULACION.DATOSTITULAR datostitular = factory.createFORMATOGAMATRICULACIONDATOSTITULAR();

		// INTERVINIENTE DE TRAFICO : TITULAR
		IntervinienteTrafico titular = bean.getTitularBean();
		if(titular != null){

			// AUTONOMO
			if(titular.getAutonomo() != null && !titular.getAutonomo().equals("false")){
				datostitular.setAUTONOMO("SI");
				hayTitular = true;
			}

			// CODIGO IAE
			if(titular.getCodigoIAE() != null && !titular.getCodigoIAE().equals("false")){
				datostitular.setCODIGOIAE(titular.getCodigoIAE());
				hayTitular = true;
			}

			// CAMBIO DE DOMICILIO
			if(titular.getCambioDomicilio() != null && !titular.getCambioDomicilio().equals("false")){
				datostitular.setCAMBIODOMICILIO("SI");
				hayTitular = true;
			}

			// PERSONA DEL INTERVINIENTE : TITULAR
			Persona personaTitular = titular.getPersona();
			if(personaTitular != null){

				// RAZÓN SOCIAL
				if (personaTitular.getNif() != null && !utilesConversiones.isNifNie(personaTitular.getNif()) &&
						personaTitular.getApellido1RazonSocial() != null && 
						!personaTitular.getApellido1RazonSocial().equals("")){
					datostitular.setRAZONSOCIALTITULAR(personaTitular.getApellido1RazonSocial());
					hayTitular = true;
				}

				// NIF 
				if (personaTitular.getNif() != null && !personaTitular.getNif().equals("")){
					datostitular.setDNITITULAR(personaTitular.getNif());
					hayTitular = true;
				}

				// ANAGRAMA
				if (personaTitular.getAnagrama() != null && !personaTitular.getAnagrama().equals("")){
					datostitular.setANAGRAMATITULAR(personaTitular.getAnagrama());
					hayTitular = true;
				}

				// APELLIDO 1
				if (personaTitular.getNif() != null && utilesConversiones.isNifNie(personaTitular.getNif()) &&
						personaTitular.getApellido1RazonSocial() != null && 
						!personaTitular.getApellido1RazonSocial().equals("")){
					datostitular.setAPELLIDO1TITULAR(personaTitular.getApellido1RazonSocial());
					hayTitular = true;
				}

				// APELLIDO 2
				if (personaTitular.getApellido2() != null && !personaTitular.getApellido2().equals("")){
					datostitular.setAPELLIDO2TITULAR(personaTitular.getApellido2());
					hayTitular = true;
				}

				// NOMBRE
				if (personaTitular.getNombre() != null && !personaTitular.getNombre().equals("")){
					datostitular.setNOMBRETITULAR(personaTitular.getNombre());
					hayTitular = true;
				}

				// FECHA NACIMIENTO
				if (personaTitular.getFechaNacimiento() != null && !personaTitular.getFechaNacimiento().toString().equals("")){
					datostitular.setFECHANACIMIENTOTITULAR(utilesFecha.getFechaFracionada(personaTitular.getFechaNacimiento()).toString());
					hayTitular = true;
				}

				// SEXO
				if (personaTitular.getSexo() != null && !personaTitular.getSexo().equals("")){
					datostitular.setSEXOTITULAR(SexoPersona.convertirAformatoGA(personaTitular.getSexo()));
					hayTitular = true;
				}

				// ESTADO CIVIL
				if (personaTitular.getEstadoCivil() != null && personaTitular.getEstadoCivil().getValorEnum() != null &&
						!personaTitular.getEstadoCivil().getValorEnum().equals("")){
					datostitular.setESTADOCIVILTITULAR(convertirAFormatoGA(
							personaTitular.getEstadoCivil().getValorEnum()));
					hayTitular = true;
				}

				// TELEFONO TITULAR
				if(personaTitular.getTelefonos() != null && personaTitular.getTelefonos().equals("")){
					datostitular.setTELEFONOTITULAR(personaTitular.getTelefonos());
					hayTitular = true;
				}

				// CORREO ELECTRONICO
				if(personaTitular.getCorreoElectronico() != null && !personaTitular.getCorreoElectronico().equals("")){
					datostitular.setCORREOELECTRONICOTITULAR(personaTitular.getCorreoElectronico());
					hayTitular = true;
				}
				// FECHA CADUCIDAD NIF
				if(personaTitular.getFechaCaducidadNif() != null && !personaTitular.getFechaCaducidadNif().equals("")){
					datostitular.setFECHACADUCIDADNIFTITULAR(personaTitular.getFechaCaducidadNif().toString());
					hayTitular = true;
				}
				// FECHA CADUCIDAD ALTERNATIVO
				if(personaTitular.getFechaCaducidadAlternativo() != null && !personaTitular.getFechaCaducidadAlternativo().equals("")){
					datostitular.setFECHACADUCIDADDOCUMENTOSUSTITUTIVOTITULAR(personaTitular.getFechaCaducidadAlternativo().toString());
					hayTitular = true;
				}
				// TIPO DOCUMENTO SUSTITUTIVO
				if(personaTitular.getTipoDocumentoAlternativo() != null && !personaTitular.getTipoDocumentoAlternativo().equals("")){
					datostitular.setTIPODOCUMENTOSUSTITUTIVOTITULAR(personaTitular.getTipoDocumentoAlternativo().toString());
					hayTitular = true;
				}
				// DIRECCION DEL INTERVINIENTE : TITULAR
				Direccion direccion = personaTitular.getDireccion();
				if(direccion != null){

					// TIPO VIA
					if(direccion.getTipoVia() != null && direccion.getTipoVia().getIdTipoVia() != null){
						if(utilesConversiones.getIdTipoViaDGTFromIdTipoVia(direccion.getTipoVia().getIdTipoVia()) != null && 
								!utilesConversiones.getIdTipoViaDGTFromIdTipoVia(direccion.getTipoVia().getIdTipoVia()).equals("")){
							datostitular.setSIGLASDIRECCIONTITULAR(utilesConversiones.getIdTipoViaDGTFromIdTipoVia(
									direccion.getTipoVia().getIdTipoVia()));
							hayTitular = true;
						}
					}

					// NOMBRE VIA 
					if(direccion.getNombreVia() != null && !direccion.getNombreVia().equals("")){
						datostitular.setNOMBREVIADIRECCIONTITULAR(direccion.getNombreVia());
						hayTitular = true;
					}

					// NUMERO
					if(direccion.getNumero() != null && !direccion.getNumero().equals("")){
						datostitular.setNUMERODIRECCIONTITULAR(direccion.getNumero());
						hayTitular = true;
					}

					// LETRA
					if(direccion.getLetra() != null && !direccion.getLetra().equals("")){
						datostitular.setLETRADIRECCIONTITULAR(direccion.getLetra());
						hayTitular = true;
					}

					// ESCALERA
					if(direccion.getEscalera() != null && !direccion.getEscalera().equals("")){
						datostitular.setESCALERADIRECCIONTITULAR(direccion.getEscalera());
						hayTitular = true;
					}

					// PISO
					if(direccion.getPlanta() != null && !direccion.getPlanta().equals("")){
						datostitular.setPISODIRECCIONTITULAR(direccion.getPlanta());
						hayTitular = true;
					}

					// PUERTA
					if(direccion.getPuerta() != null && !direccion.getPuerta().equals("")){
						datostitular.setPUERTADIRECCIONTITULAR(direccion.getPuerta());
						hayTitular = true;
					}

					// KILOMETRO
					if(direccion.getPuntoKilometrico() != null && !direccion.getPuntoKilometrico().equals("")){
						datostitular.setKMDIRECCIONTITULAR(direccion.getPuntoKilometrico());
						hayTitular = true;
					}

					// HECTOMETRO
					if(direccion.getHm() != null && !direccion.getHm().equals("")){
						datostitular.setHECTOMETRODIRECCIONTITULAR(direccion.getHm());
						hayTitular = true;
					}

					// BLOQUE
					if(direccion.getBloque() != null && !direccion.getBloque().equals("")){
						datostitular.setBLOQUEDIRECCIONTITULAR(direccion.getBloque());
						hayTitular = true;
					}

					// MUNICIPIO
					if(direccion.getMunicipio() != null && direccion.getMunicipio().getNombre() != null &&
							!direccion.getMunicipio().getNombre().equals("")){
						datostitular.setMUNICIPIOTITULAR(direccion.getMunicipio().getNombre());
						hayTitular = true;
					}

					// PUEBLO
					if(direccion.getPueblo() != null && !direccion.getPueblo().equals("")){
						datostitular.setPUEBLOTITULAR(direccion.getPueblo());
						hayTitular = true;
					}

					// PROVINCIA
					if(direccion.getMunicipio() != null && direccion.getMunicipio().getProvincia() != null &&
							direccion.getMunicipio().getProvincia().getIdProvincia() != null && 
							!direccion.getMunicipio().getProvincia().getIdProvincia().equals("")){
						if(utilesConversiones.getSiglasFromIdProvincia(direccion.getMunicipio().getProvincia().getIdProvincia()) != null && 
								!utilesConversiones.getSiglasFromIdProvincia(direccion.getMunicipio().getProvincia().getIdProvincia()).equals("")){
							datostitular.setPROVINCIATITULAR(TipoProvincia.fromValue(utilesConversiones.getSiglasFromIdProvincia(direccion.getMunicipio().
									getProvincia().getIdProvincia())));
							hayTitular = true;
						}

						// CODIGO POSTAL
						if(direccion.getCodPostal() != null && !direccion.getCodPostal().equals("")){
							datostitular.setCPTITULAR(direccion.getCodPostal());
							hayTitular = true;
						}

					}
				}
			}
		}

		// Comprueba el flag para setear titular o no en el elemento raiz mat:
		if(hayTitular){
			mat.setDATOSTITULAR(datostitular);
		}

		//DATOS REPRESENTANTE TITULAR
		FORMATOGA.MATRICULACION.DATOSREPRESENTANTETITULAR datosrepresentante = factory.createFORMATOGAMATRICULACIONDATOSREPRESENTANTETITULAR();

		// Flag. Indica si meter representante del titular en la matriculación.
		boolean hayRepresentante = false;

		// INTERVINIENTE DE TRAFICO : REPRESENTANTE
		IntervinienteTrafico representante = bean.getRepresentanteTitularBean();
		if(representante != null){

			// CONCEPTO REPRESENTACION
			if(representante.getConceptoRepre() != null && representante.getConceptoRepre().getValorEnum() != null && 
					!representante.getConceptoRepre().getValorEnum().equals("")){
				datosrepresentante.setCONCEPTOREPTITULAR(representante.getConceptoRepre().getValorEnum());
				// Si el concepto es 'tutela o patria potestad' puede llevar 'MOTIVO'
				if(representante.getConceptoRepre() == ConceptoTutela.Tutela){
					if(representante.getIdMotivoTutela() != null){
						datosrepresentante.setMOTIVOTUTELA(TipoMotivoTutela.valueOf(
								representante.getIdMotivoTutela().getValorEnum()));
					}
				}
				hayRepresentante = true;
			}
			
			// MOTIVO REPRESENTACION
			

			// ACREDITACION
			if(representante.getDatosDocumento() != null && representante.getDatosDocumento().equals("")){
				datosrepresentante.setACREDITACIONREPTITULAR(representante.getDatosDocumento());
				hayRepresentante = true;
			}

			// FECHA INICIO TUTELA
			if (representante.getFechaInicio() != null && !representante.getFechaInicio().toString().equals("")){
				datosrepresentante.setFECHAINICIOTUTELA(representante.getFechaInicio().toString());
				hayRepresentante = true;
			}

			// FECHA FIN TUTELA
			if (representante.getFechaFin() != null && !representante.getFechaFin().toString().equals("")){
				datosrepresentante.setFECHAFINTUTELA(representante.getFechaFin().toString());
				hayRepresentante = true;
			}

			// PERSONA DEL INTERVINIENTE : REPRESENTANTE
			Persona personaRepresentante = representante.getPersona();
			if(personaRepresentante != null){

				// RAZÓN SOCIAL
				if (personaRepresentante.getNif() != null && !utilesConversiones.isNifNie(personaRepresentante.getNif()) &&
						personaRepresentante.getApellido1RazonSocial() != null && 
						!personaRepresentante.getApellido1RazonSocial().equals("")){
					datosrepresentante.setRAZONSOCIALREP(personaRepresentante.getApellido1RazonSocial());
					hayRepresentante = true;
				}

				// NIF 
				if (personaRepresentante.getNif() != null && !personaRepresentante.getNif().equals("")){
					datosrepresentante.setDNIREP(personaRepresentante.getNif());
					hayRepresentante = true;
				}

				// APELLIDO 1
				if (personaRepresentante.getNif() != null && utilesConversiones.isNifNie(personaRepresentante.getNif()) &&
						personaRepresentante.getApellido1RazonSocial() != null && 
						!personaRepresentante.getApellido1RazonSocial().equals("")){
					datosrepresentante.setAPELLIDO1REP(personaRepresentante.getApellido1RazonSocial());
					hayRepresentante = true;
				}

				// APELLIDO 2
				if (personaRepresentante.getApellido2() != null && !personaRepresentante.getApellido2().equals("")){
					datosrepresentante.setAPELLIDO2REP(personaRepresentante.getApellido2());
					hayRepresentante = true;
				}

				// NOMBRE
				if (personaRepresentante.getNombre() != null && !personaRepresentante.getNombre().equals("")){
					datosrepresentante.setNOMBREREP(personaRepresentante.getNombre());
					hayRepresentante = true;
				}

				// FECHA NACIMIENTO
				if (personaRepresentante.getFechaNacimiento() != null && !personaRepresentante.getFechaNacimiento().toString().equals("")){
					datosrepresentante.setFECHANACIMIENTOREP(utilesFecha.getFechaFracionada(personaRepresentante.getFechaNacimiento()).toString());
					hayRepresentante = true;
				}

				// SEXO
				if (personaRepresentante.getSexo() != null && !personaRepresentante.getSexo().equals("")){
					datosrepresentante.setSEXOREP(SexoPersona.convertirAformatoGA(personaRepresentante.getSexo()));
					hayRepresentante = true;
				}

				// ESTADO CIVIL
				if (personaRepresentante.getEstadoCivil() != null && personaRepresentante.getEstadoCivil().getValorEnum() != null && !personaRepresentante.getEstadoCivil().getValorEnum().equals("")) {
					datosrepresentante.setESTADOCIVILREP(convertirAFormatoGA(personaRepresentante.getEstadoCivil().getValorEnum()));
					hayRepresentante = true;
				}
				
				// FECHA CADUCIDAD NIF
				if(personaRepresentante.getFechaCaducidadNif() != null && !personaRepresentante.getFechaCaducidadNif().equals("")){
					datosrepresentante.setFECHACADUCIDADNIFREPRESENTANTETITULAR(personaRepresentante.getFechaCaducidadNif().toString());
					hayRepresentante = true;
				}
				// FECHA CADUCIDAD ALTERNATIVO
				if(personaRepresentante.getFechaCaducidadAlternativo() != null && !personaRepresentante.getFechaCaducidadAlternativo().equals("")){
					datosrepresentante.setFECHACADUCIDADDOCUMENTOSUSTITUTIVOREPRESENTANTETITULAR(personaRepresentante.getFechaCaducidadAlternativo().toString());
					hayRepresentante = true;
				}
				// TIPO DOCUMENTO SUSTITUTIVO
				if(personaRepresentante.getTipoDocumentoAlternativo() != null && !personaRepresentante.getTipoDocumentoAlternativo().equals("")){
					datosrepresentante.setTIPODOCUMENTOSUSTITUTIVOREPRESENTANTETITULAR(personaRepresentante.getTipoDocumentoAlternativo().toString());
					hayRepresentante = true;
				}

				// DIRECCION DEL INTERVINIENTE : REPRESENTANTE
				Direccion direccion = personaRepresentante.getDireccion();
				if(direccion != null){

					// TIPO VIA
					if(direccion.getTipoVia() != null && direccion.getTipoVia().getIdTipoVia() != null){
						if(utilesConversiones.getIdTipoViaDGTFromIdTipoVia(direccion.getTipoVia().getIdTipoVia()) != null && 
								!utilesConversiones.getIdTipoViaDGTFromIdTipoVia(direccion.getTipoVia().getIdTipoVia()).equals("")){
							datosrepresentante.setSIGLASDIRECCIONREP(utilesConversiones.getIdTipoViaDGTFromIdTipoVia(
									direccion.getTipoVia().getIdTipoVia()));
						}
						hayRepresentante = true;
					}

					// NOMBRE VIA 
					if(direccion.getNombreVia() != null && !direccion.getNombreVia().equals("")){
						datosrepresentante.setNOMBREVIADIRECCIONREP(direccion.getNombreVia());
						hayRepresentante = true;
					}

					// NUMERO
					if(direccion.getNumero() != null && !direccion.getNumero().equals("")){
						datosrepresentante.setNUMERODIRECCIONREP(direccion.getNumero());
						hayRepresentante = true;
					}

					// LETRA
					if(direccion.getLetra() != null && !direccion.getLetra().equals("")){
						datosrepresentante.setLETRADIRECCIONREP(direccion.getLetra());
						hayRepresentante = true;
					}

					// ESCALERA
					if(direccion.getEscalera() != null && !direccion.getEscalera().equals("")){
						datosrepresentante.setESCALERADIRECCIONREP(direccion.getEscalera());
						hayRepresentante = true;
					}

					// PISO
					if(direccion.getPlanta() != null && !direccion.getPlanta().equals("")){
						datosrepresentante.setPISODIRECCIONREP(direccion.getPlanta());
						hayRepresentante = true;
					}

					// PUERTA
					if(direccion.getPuerta() != null && !direccion.getPuerta().equals("")){
						datosrepresentante.setPUERTADIRECCIONREP(direccion.getPuerta());
						hayRepresentante = true;
					}

					// KILOMETRO
					if(direccion.getPuntoKilometrico() != null && !direccion.getPuntoKilometrico().equals("")){
						datosrepresentante.setKMDIRECCIONREP(direccion.getPuntoKilometrico());
						hayRepresentante = true;
					}

					// HECTOMETRO
					if(direccion.getHm() != null && !direccion.getHm().equals("")){
						datosrepresentante.setHECTOMETRODIRECCIONREP(direccion.getHm());
						hayRepresentante = true;
					}

					// BLOQUE
					if(direccion.getBloque() != null && !direccion.getBloque().equals("")){
						datosrepresentante.setBLOQUEDIRECCIONREP(direccion.getBloque());
						hayRepresentante = true;
					}

					// MUNICIPIO
					if(direccion.getMunicipio() != null && direccion.getMunicipio().getNombre() != null &&
							!direccion.getMunicipio().getNombre().equals("")){
						datosrepresentante.setMUNICIPIOREP(direccion.getMunicipio().getNombre());
						hayRepresentante = true;
					}

					// PUEBLO
					if(direccion.getPueblo() != null && !direccion.getPueblo().equals("")){
						datosrepresentante.setPUEBLOREP(direccion.getPueblo());
						hayRepresentante = true;
					}

					// PROVINCIA
					if(direccion.getMunicipio() != null && direccion.getMunicipio().getProvincia() != null &&
							direccion.getMunicipio().getProvincia().getIdProvincia() != null && 
							!direccion.getMunicipio().getProvincia().getIdProvincia().equals("") &&
							utilesConversiones.getSiglasFromIdProvincia(direccion.getMunicipio().getProvincia().getIdProvincia()) != null &&
							!utilesConversiones.getSiglasFromIdProvincia(direccion.getMunicipio().getProvincia().getIdProvincia()).equals("")){
						datosrepresentante.setPROVINCIAREP(TipoProvincia.fromValue(utilesConversiones.getSiglasFromIdProvincia(direccion.getMunicipio().
								getProvincia().getIdProvincia())));
						hayRepresentante = true;
					}

					// CODIGO POSTAL
					if(direccion.getCodPostal() != null && !direccion.getCodPostal().equals("")){
						datosrepresentante.setCPREP(direccion.getCodPostal());
						hayRepresentante = true;
					}

				}
			}
		}

		// Comprueba el flag para setear titular o no en el elemento raiz mat:
		if(hayRepresentante){
			mat.setDATOSREPRESENTANTETITULAR(datosrepresentante);
		}

		//DATOS ARRENDATARIO
		if ("true".equals(bean.getTramiteTraficoBean().getRenting())) {

			FORMATOGA.MATRICULACION.DATOSARRENDATARIO datosarrendatario = factory.createFORMATOGAMATRICULACIONDATOSARRENDATARIO();		

			// Flag. Indica si meter arrendatario en la matriculación.
			boolean hayArrendatario = false;		

			// INTERVINIENTE DE TRAFICO : ARRENDATARIO
			IntervinienteTrafico arrendatario = bean.getArrendatarioBean();
			if(arrendatario != null){

				// FECHA INICIO RENTING
				if (arrendatario.getFechaInicio() != null && !arrendatario.getFechaInicio().toString().equals("")){
					datosarrendatario.setFECHAINICIORENTING(arrendatario.getFechaInicio().toString());
					hayArrendatario = true;
				}

				// FECHA FIN RENTING
				if (arrendatario.getFechaFin() != null && !arrendatario.getFechaFin().toString().equals("")){
					datosarrendatario.setFECHAFINRENTING(arrendatario.getFechaFin().toString());
					hayArrendatario = true;
				}

				// HORA FIN RENTING
				// Comprueba que las horas tienen valor distinto de cadena vacía
				// Solo los setea para el xml si tienen longitud de 4 o 5 con dos puntos.
				if(arrendatario.getHoraFin() != null && !arrendatario.getHoraFin().equals("")){
					if(arrendatario.getHoraFin().length() == 4){
						datosarrendatario.setHORAFINRENTING(arrendatario.getHoraFin());
					}
					// Si tiene length 5 puede llevar dos puntos, si los lleva los quita:
					if(arrendatario.getHoraFin().length() == 5 && arrendatario.getHoraFin().contains(":")){
						String horaFinSinPuntos = arrendatario.getHoraFin().replace(":","");
						datosarrendatario.setHORAFINRENTING(horaFinSinPuntos);
					}
				}
				// HORA INICIO RENTING
				if(arrendatario.getHoraInicio() != null && !arrendatario.getHoraInicio().equals("")){
					if(arrendatario.getHoraInicio().length() == 4){
						datosarrendatario.setHORAINICIORENTING(arrendatario.getHoraInicio());
					}
					// Si tiene length 5 puede llevar dos puntos, si los lleva los quita:
					if(arrendatario.getHoraInicio().length() == 5 && arrendatario.getHoraInicio().contains(":")){
						String horaInicioSinPuntos = arrendatario.getHoraInicio().replace(":","");
						datosarrendatario.setHORAINICIORENTING(horaInicioSinPuntos);
					}
				}

				// PERSONA DEL INTERVINIENTE : ARRENDATARIO
				Persona personaArrendatario = arrendatario.getPersona();
				if(personaArrendatario != null){

					// RAZÓN SOCIAL
					if (personaArrendatario.getNif() != null && !utilesConversiones.isNifNie(personaArrendatario.getNif()) &&
							personaArrendatario.getApellido1RazonSocial() != null && 
							!personaArrendatario.getApellido1RazonSocial().equals("")){
						datosarrendatario.setRAZONSOCIALARR(personaArrendatario.getApellido1RazonSocial());
						hayArrendatario = true;
					}

					// NIF 
					if (personaArrendatario.getNif() != null && !personaArrendatario.getNif().equals("")){
						datosarrendatario.setDNIARR(personaArrendatario.getNif());
						hayArrendatario = true;
					}

					// APELLIDO 1
					if (personaArrendatario.getNif() != null && utilesConversiones.isNifNie(personaArrendatario.getNif()) &&
							personaArrendatario.getApellido1RazonSocial() != null && 
							!personaArrendatario.getApellido1RazonSocial().equals("")){
						datosarrendatario.setAPELLIDO1ARR(personaArrendatario.getApellido1RazonSocial());
						hayArrendatario = true;
					}

					// APELLIDO 2
					if (personaArrendatario.getApellido2() != null && !personaArrendatario.getApellido2().equals("")){
						datosarrendatario.setAPELLIDO2ARR(personaArrendatario.getApellido2());
						hayArrendatario = true;
					}

					// NOMBRE
					if (personaArrendatario.getNombre() != null && !personaArrendatario.getNombre().equals("")){
						datosarrendatario.setNOMBREARR(personaArrendatario.getNombre());
						hayArrendatario = true;
					}

					// FECHA NACIMIENTO
					if (personaArrendatario.getFechaNacimiento() != null && !personaArrendatario.getFechaNacimiento().toString().equals("")){
						datosarrendatario.setFECHANACIMIENTOARR(personaArrendatario.getFechaNacimientoBean().toString());
						hayArrendatario = true;
					}

					// SEXO
					if (personaArrendatario.getSexo() != null && !personaArrendatario.getSexo().equals("")){
						datosarrendatario.setSEXOARR(SexoPersona.convertirAformatoGA(personaArrendatario.getSexo()));
						hayArrendatario = true;
					}
					// FECHA CADUCIDAD NIF
					if(personaArrendatario.getFechaCaducidadNif() != null && !personaArrendatario.getFechaCaducidadNif().equals("")){
						datosarrendatario.setFECHACADUCIDADNIFARR(personaArrendatario.getFechaCaducidadNif().toString());
						hayArrendatario = true;
					}
					// FECHA CADUCIDAD ALTERNATIVO
					if(personaArrendatario.getFechaCaducidadAlternativo() != null && !personaArrendatario.getFechaCaducidadAlternativo().equals("")){
						datosarrendatario.setFECHACADUCIDADDOCUMENTOSUSTITUTIVOARR(personaArrendatario.getFechaCaducidadAlternativo().toString());
						hayArrendatario = true;
					}
					// TIPO DOCUMENTO SUSTITUTIVO
					if(personaArrendatario.getTipoDocumentoAlternativo() != null && !personaArrendatario.getTipoDocumentoAlternativo().equals("")){
						datosarrendatario.setTIPODOCUMENTOSUSTITUTIVOARR(personaArrendatario.getTipoDocumentoAlternativo().toString());
						hayArrendatario = true;
					}

					// DIRECCION DEL INTERVINIENTE : ARRENDATARIO
					Direccion direccion = personaArrendatario.getDireccion();
					if(direccion != null){

						// TIPO VIA
						if(direccion.getTipoVia() != null && direccion.getTipoVia().getIdTipoVia() != null &&
								utilesConversiones.getIdTipoViaDGTFromIdTipoVia(direccion.getTipoVia().getIdTipoVia()) != null &&
								!utilesConversiones.getIdTipoViaDGTFromIdTipoVia(direccion.getTipoVia().getIdTipoVia()).equals("")){
							datosarrendatario.setSIGLASDIRECCIONARR(utilesConversiones.getIdTipoViaDGTFromIdTipoVia(
									direccion.getTipoVia().getIdTipoVia()));
							hayArrendatario = true;
						}

						// NOMBRE VIA 
						if(direccion.getNombreVia() != null && !direccion.getNombreVia().equals("")){
							datosarrendatario.setNOMBREVIADIRECCIONARR(direccion.getNombreVia());
							hayArrendatario = true;
						}

						// NUMERO
						if(direccion.getNumero() != null && !direccion.getNumero().equals("")){
							datosarrendatario.setNUMERODIRECCIONARR(direccion.getNumero());
							hayTitular = true;
						}

						// LETRA
						if(direccion.getLetra() != null && !direccion.getLetra().equals("")){
							datosarrendatario.setLETRADIRECCIONARR(direccion.getLetra());
							hayArrendatario = true;
						}

						// ESCALERA
						if(direccion.getEscalera() != null && !direccion.getEscalera().equals("")){
							datosarrendatario.setESCALERADIRECCIONARR(direccion.getEscalera());
							hayArrendatario = true;
						}

						// PISO
						if(direccion.getPlanta() != null && !direccion.getPlanta().equals("")){
							datosarrendatario.setPISODIRECCIONARR(direccion.getPlanta());
							hayArrendatario = true;
						}

						// PUERTA
						if(direccion.getPuerta() != null && !direccion.getPuerta().equals("")){
							datosarrendatario.setPUERTADIRECCIONARR(direccion.getPuerta());
							hayArrendatario = true;
						}

						// KILOMETRO
						if(direccion.getPuntoKilometrico() != null && !direccion.getPuntoKilometrico().equals("")){
							datosarrendatario.setKMDIRECCIONARR(direccion.getPuntoKilometrico());
							hayArrendatario = true;
						}

						// HECTOMETRO
						if(direccion.getHm() != null && !direccion.getHm().equals("")){
							datosarrendatario.setHECTOMETRODIRECCIONARR(direccion.getHm());
							hayArrendatario = true;
						}

						// BLOQUE
						if(direccion.getBloque() != null && !direccion.getBloque().equals("")){
							datosarrendatario.setBLOQUEDIRECCIONARR(direccion.getBloque());
							hayArrendatario = true;
						}

						// MUNICIPIO
						if(direccion.getMunicipio() != null && direccion.getMunicipio().getNombre() != null &&
								!direccion.getMunicipio().getNombre().equals("")){
							datosarrendatario.setMUNICIPIOARR(direccion.getMunicipio().getNombre());
							hayArrendatario = true;
						}

						// PUEBLO
						if(direccion.getPueblo() != null && !direccion.getPueblo().equals("")){
							datostitular.setPUEBLOTITULAR(direccion.getPueblo());
							hayArrendatario = true;
						}

						// PROVINCIA
						if(direccion.getMunicipio() != null && direccion.getMunicipio().getProvincia() != null &&
								direccion.getMunicipio().getProvincia().getIdProvincia() != null && 
								!direccion.getMunicipio().getProvincia().getIdProvincia().equals("") &&
								utilesConversiones.getSiglasFromIdProvincia(direccion.getMunicipio().getProvincia().getIdProvincia()) != null &&
								!utilesConversiones.getSiglasFromIdProvincia(direccion.getMunicipio().getProvincia().getIdProvincia()).equals("")){
							datosarrendatario.setPROVINCIAARR(TipoProvincia.fromValue(utilesConversiones.getSiglasFromIdProvincia(direccion.getMunicipio().
									getProvincia().getIdProvincia())));
							hayArrendatario = true;
						}

						// CODIGO POSTAL
						if(direccion.getCodPostal() != null && !direccion.getCodPostal().equals("")){
							datosarrendatario.setCPARR(direccion.getCodPostal());
							hayArrendatario = true;
						}

					}
				}

				// Comprueba el flag para setear arrendatario o no en el elemento raiz mat:
				if(hayArrendatario){
					mat.setDATOSARRENDATARIO(datosarrendatario);
				}
			}

		}

		//CONDUCTOR HABITUAL
		FORMATOGA.MATRICULACION.CONDUCTORHABITUAL datosconductor = factory.createFORMATOGAMATRICULACIONCONDUCTORHABITUAL();
		IntervinienteTrafico conductor = bean.getConductorHabitualBean();

		// Flag. Indica si meter conductor habitual en la matriculación.
		boolean hayConductor = false;

		// NIF
		if(conductor.getPersona() != null && conductor.getPersona().getNif() != null &&
				!conductor.getPersona().getNif().equals("")){
			datosconductor.setDNICONDHABITUAL(conductor.getPersona().getNif());
			hayConductor = true;
		}

		// FECHA INICIO
		if(conductor.getFechaInicio() != null && !conductor.getFechaInicio().toString().equals("")){
			datosconductor.setFECHAINICIOCONDHABITUAL(conductor.getFechaInicio().toString());
			hayConductor = true;
		}

		// FECHA FIN
		if(conductor.getFechaFin() != null && !conductor.getFechaFin().toString().equals("")){
			datosconductor.setFECHAFINCONDHABITUAL(conductor.getFechaFin().toString());
			hayConductor = true;
		}
		
		// FECHA CADUCIDAD NIF
		if(conductor.getPersona().getFechaCaducidadNif() != null && !conductor.getPersona().getFechaCaducidadNif().equals("")){
			datosconductor.setFECHACADUCIDADNIFCONDHABITUAL(conductor.getPersona().getFechaCaducidadNif().toString());
			hayConductor = true;
		}
		// FECHA CADUCIDAD ALTERNATIVO
		if(conductor.getPersona().getFechaCaducidadAlternativo() != null && !conductor.getPersona().getFechaCaducidadAlternativo().equals("")){
			datosconductor.setFECHACADUCIDADDOCUMENTOSUSTITUTIVOCONDHABITUAL(conductor.getPersona().getFechaCaducidadAlternativo().toString());
			hayConductor = true;
		}
		// TIPO DOCUMENTO SUSTITUTIVO
		if(conductor.getPersona().getTipoDocumentoAlternativo() != null && !conductor.getPersona().getTipoDocumentoAlternativo().equals("")){
			datosconductor.setTIPODOCUMENTOSUSTITUTIVOCONDHABITUAL(conductor.getPersona().getTipoDocumentoAlternativo().toString());
			hayConductor = true;
		}

		// HORA FIN
		// Comprueba que las horas tienen valor distinto de cadena vacía
		// Solo los setea para el xml si tienen longitud de 4 o 5 con dos puntos.
		if(conductor.getHoraFin() != null && !conductor.getHoraFin().equals("")){
			if(conductor.getHoraFin().length() == 4){
				datosconductor.setHORAFINCONDHABITUAL(conductor.getHoraFin());
				hayConductor = true;
			}
			// Si tiene length 5 puede llevar dos puntos, si los lleva los quita:
			if(conductor.getHoraFin().length() == 5 && conductor.getHoraFin().contains(":")){
				String horaFinSinPuntos = conductor.getHoraFin().replace(":","");
				datosconductor.setHORAFINCONDHABITUAL(horaFinSinPuntos);
				hayConductor = true;
			}
		}

		// HORA INICIO
		if(conductor.getHoraInicio() != null && !conductor.getHoraInicio().equals("")){
			if(conductor.getHoraInicio().length() == 4){
				datosconductor.setHORAINICIOCONDHABITUAL(conductor.getHoraInicio());
				hayConductor = true;
			}
			// Si tiene length 5 puede llevar dos puntos, si los lleva los quita:
			if(conductor.getHoraInicio().length() == 5 && conductor.getHoraInicio().contains(":")){
				String horaInicioSinPuntos = conductor.getHoraInicio().replace(":","");
				datosconductor.setHORAINICIOCONDHABITUAL(horaInicioSinPuntos);
				hayConductor = true;
			}
		}

		// Comprueba el flag para setear conductor habitual o no en el elemento raiz mat:
		if(hayConductor){
			mat.setCONDUCTORHABITUAL(datosconductor);
		}

		VehiculoBean vehiculoBean = bean.getTramiteTraficoBean().getVehiculo();

		boolean hayImportador = false;

		//DATOS IMPORTADOR
		FORMATOGA.MATRICULACION.DATOSIMPORTADOR datosImportador = factory.createFORMATOGAMATRICULACIONDATOSIMPORTADOR();

		// RAZÓN SOCIAL
		if(vehiculoBean.getIntegradorBean() != null && vehiculoBean.getIntegradorBean().getNif() != null && 
				!utilesConversiones.isNifNie(vehiculoBean.getIntegradorBean().getNif())){
			datosImportador.setRAZONSOCIALIMPORTADOR(vehiculoBean.getIntegradorBean().getApellido1RazonSocial());
			hayImportador = true;
		}

		// DNI
		if(vehiculoBean.getIntegradorBean() != null && vehiculoBean.getIntegradorBean().getNif() != null){
			datosImportador.setDNIIMPORTADOR(vehiculoBean.getIntegradorBean().getNif());
			hayImportador = true;
		}

		// APELLIDO 1
		if(vehiculoBean.getIntegradorBean() != null && vehiculoBean.getIntegradorBean().getNif() != null && 
				utilesConversiones.isNifNie(vehiculoBean.getIntegradorBean().getNif())){
			datosImportador.setAPELLIDO1IMPORTADOR(vehiculoBean.getIntegradorBean().getApellido1RazonSocial());
			hayImportador = true;
		}

		// APELLIDO 2
		if(vehiculoBean.getIntegradorBean() != null && vehiculoBean.getIntegradorBean().getNif() != null &&
				utilesConversiones.isNifNie(vehiculoBean.getIntegradorBean().getNif())){
			datosImportador.setAPELLIDO2IMPORTADOR(vehiculoBean.getIntegradorBean().getApellido2());
			hayImportador = true;
		}

		// NOMBRE
		if(vehiculoBean.getIntegradorBean() != null && 
				vehiculoBean.getIntegradorBean().getNif() != null){
			datosImportador.setNOMBREIMPORTADOR(vehiculoBean.getIntegradorBean().getNombre());
			hayImportador = true;
		}

		// Comprueba el flag para setear importador o no en el elemento raiz mat:
		if(hayImportador){
			mat.setDATOSIMPORTADOR(datosImportador);
		}

		//DATOS VEHICULO
		FORMATOGA.MATRICULACION.DATOSVEHICULO vehiculo = factory.createFORMATOGAMATRICULACIONDATOSVEHICULO();

		// Flag. Indica si meter vehiculo en la matriculación.
		boolean hayVehiculo = false;

		// MATRÍCULA
		if(vehiculoBean.getMatricula() != null && !vehiculoBean.getMatricula().equals("")){
			vehiculo.setMATRICULA(vehiculoBean.getMatricula());
			hayVehiculo = true;
		}

		// FECHA MATRICULACIÓN
		if(vehiculoBean.getFechaMatriculacion() != null && 
				!vehiculoBean.getFechaMatriculacion().isfechaNula()){
			vehiculo.setFECHAMATRICULACION(vehiculoBean.getFechaMatriculacion().toString());
			hayVehiculo = true;
		}

		// FECHA PRIMERA MATRICULACIÓN
		if(vehiculoBean.getFechaPrimMatri() != null && 
				!vehiculoBean.getFechaPrimMatri().isfechaNula()){
			vehiculo.setFECHAPRIMERAMATRICULACION(vehiculoBean.getFechaPrimMatri().toString());
			hayVehiculo = true;
		}

		// FECHA MATRÍCULA TURÍSTICA
		if(vehiculoBean.getLimiteMatrTuris() != null && 
				!vehiculoBean.getLimiteMatrTuris().isfechaNula()){
			vehiculo.setFECHAMATRICULATURISTICA(vehiculoBean.getLimiteMatrTuris().toString());
			hayVehiculo = true;
		}

		// MATRÍCULA DIPLOMÁTICA
		if(vehiculoBean.getDiplomatico() != null && vehiculoBean.getDiplomatico().equals("true")){
			vehiculo.setMATRICULADIPLOMATICA("SI");
			hayVehiculo = true;
		}

		// MATRICULA AYUNTAMIENTO
		if(vehiculoBean.getMatriAyuntamiento() != null && !vehiculoBean.getMatriAyuntamiento().equals("")){
			vehiculo.setMATRICULAAYUNTAMIENTO(vehiculoBean.getMatriAyuntamiento());
			hayVehiculo = true;
		}

		// CODIGO ITV
		if(vehiculoBean.getCodItv() != null && !vehiculoBean.getCodItv().equals("")){
			vehiculo.setCODIGOITV(vehiculoBean.getCodItv());
			hayVehiculo = true;
		}

		// FECHA ITV
		if(vehiculoBean.getFechaItv() != null && 
				!vehiculoBean.getFechaItv().isfechaNula()){
			vehiculo.setFECHAITV(vehiculoBean.getFechaItv().toString());
			hayVehiculo = true;
		}

		// TIPO ITV
		if(vehiculoBean.getTipoItv() != null && !vehiculoBean.getTipoItv().equals("")){
			vehiculo.setTIPOITV(vehiculoBean.getTipoItv());
			hayVehiculo = true;
		}

		// VARIANTE ITV
		if(vehiculoBean.getVariante() != null && !vehiculoBean.getVariante().equals("")){
			vehiculo.setVARIANTEITV(vehiculoBean.getVariante());
			hayVehiculo = true;
		}

		// VERSION ITV
		if(vehiculoBean.getVersion() != null && !vehiculoBean.getVersion().equals("")){
			vehiculo.setVERSIONITV(vehiculoBean.getVersion());
			hayVehiculo = true;
		}

		// NUMERO HOMOLOGACION ITV
		if(vehiculoBean.getNumHomologacion() != null && !vehiculoBean.getNumHomologacion().equals("")){
			vehiculo.setNUMEROHOMOLOGACIONITV(vehiculoBean.getNumHomologacion());
			hayVehiculo = true;
		}

		// MARCA
		if(vehiculoBean.getMarcaBean() != null && vehiculoBean.getMarcaBean().getMarca() != null && 
				!vehiculoBean.getMarcaBean().getMarca().equals("")){
			vehiculo.setMARCA(vehiculoBean.getMarcaBean().getMarca());
			hayVehiculo = true;
		}

		// MODELO
		if(vehiculoBean.getModelo() != null && !vehiculoBean.getModelo().equals("")){
			vehiculo.setMODELO(vehiculoBean.getModelo());
			hayVehiculo = true;
		}

		// NÚMERO DE BASTIDOR
		if(vehiculoBean.getBastidor() != null && !vehiculoBean.getBastidor().equals("")){
			vehiculo.setNUMEROBASTIDOR(vehiculoBean.getBastidor());
			hayVehiculo = true;
		}

		// COLOR
		if(vehiculoBean.getColorBean() != null && vehiculoBean.getColorBean().getValorEnum() != null && 
				!vehiculoBean.getColorBean().getValorEnum().equals(Color.Indefinido.getValorEnum())&&
				!vehiculoBean.getColorBean().getValorEnum().equals(Color.No_disponible.getValorEnum())){
			vehiculo.setCOLOR(TipoColor.fromValue(vehiculoBean.getColorBean().getValorEnum()));
			hayVehiculo = true;
		}

		// POTENCIA FISCAL
		if(vehiculoBean.getPotenciaFiscal() != null && !vehiculoBean.getPotenciaFiscal().equals("")){
			vehiculo.setPOTENCIAFISCAL(vehiculoBean.getPotenciaFiscal());
			hayVehiculo = true;
		}

		// POTENCIA NETA
		if(vehiculoBean.getPotenciaNeta() != null && !vehiculoBean.getPotenciaNeta().equals("")){
			vehiculo.setPOTENCIANETA(vehiculoBean.getPotenciaNeta());
			hayVehiculo = true;
		}

		// CILINDRADA
		if(vehiculoBean.getCilindrada() != null && !vehiculoBean.getCilindrada().equals("")){
			try{
				vehiculo.setCILINDRADA(new BigDecimal(vehiculoBean.getCilindrada()));
				hayVehiculo = true;
			}catch(NumberFormatException ex){
				// No se ha seteado la cilindrada por no ser la cadena casteable a BigDecimal
				log.error("ERROR EXPORTACION :" + " La cilindrada debe ser casteable a BigDecimal. Valor: " + 
					vehiculoBean.getCilindrada());
			}
		}

		// PLAZAS 
		if(vehiculoBean.getPlazas() != null && !vehiculoBean.getPlazas().equals("")){
			vehiculo.setPLAZAS(vehiculoBean.getPlazas().toBigInteger());
			hayVehiculo = true;
		}

		// PLAZAS DE PIE 
		if(vehiculoBean.getNumPlazasPie() != null && !vehiculoBean.getNumPlazasPie().equals("")){
			vehiculo.setPLAZASPIE(vehiculoBean.getNumPlazasPie().toBigInteger());
			hayVehiculo = true;
		}

		// TARA
		if(vehiculoBean.getTara() != null && !vehiculoBean.getTara().equals("")){
			try{
				vehiculo.setTARA(new BigInteger(vehiculoBean.getTara()));
				hayVehiculo = true;
			}catch(NumberFormatException ex){
				// No se ha seteado la tara por no ser la cadena casteable a BigInteger
				log.error("ERROR EXPORTACION :" + " La tara debe ser casteable a BigInteger. Valor: " +
					vehiculoBean.getTara());
			}
		}

		// MASA
		if(vehiculoBean.getPesoMma() != null && !vehiculoBean.getPesoMma().equals("")){
			try{
				vehiculo.setMASA(new BigInteger(vehiculoBean.getPesoMma()));
				hayVehiculo = true;
			}catch(NumberFormatException ex){
				// No se ha seteado la masa por no ser la cadena casteable a BigInteger
				log.error("ERROR EXPORTACION :" + " La masa debe ser casteable a BigInteger. Valor: " +
					vehiculoBean.getPesoMma());
			}
		}

		// MASA MÁXIMA ADMISIBLE
		if(vehiculoBean.getMtmaItv() != null && !vehiculoBean.getMtmaItv().equals("")){
			try{
				vehiculo.setMASAMAXIMAADMISIBLE(new BigInteger(vehiculoBean.getMtmaItv()));
				hayVehiculo = true;
			}catch(NumberFormatException ex){
				// No se ha seteado la masa maxima admisible por no ser la cadena casteable a BigInteger
				log.error("ERROR EXPORTACION :" + " La masa maxima admisible debe ser casteable a BigInteger. Valor: " + 
					vehiculoBean.getMtmaItv());
			}
		}

		// RELACION POTENCIA PESO
		if(vehiculoBean.getPotenciaPeso() != null && !vehiculoBean.getPotenciaPeso().equals("")){
			vehiculo.setRELACIONPOTENCIAPESO(vehiculoBean.getPotenciaPeso());
			hayVehiculo = true;
		}

		// EXCESO DE PESO
		if(vehiculoBean.getExcesoPeso() != null && vehiculoBean.getExcesoPeso().equals("true")){
			vehiculo.setEXCESOPESO("SI");
			hayVehiculo = true;
		}

		// TIPO DE VEHICULO
		if(vehiculoBean.getTipoVehiculoBean() != null && vehiculoBean.getTipoVehiculoBean().getTipoVehiculo() != null &&
				!vehiculoBean.getTipoVehiculoBean().getTipoVehiculo().equals("")){
			vehiculo.setTIPOVEHICULO(vehiculoBean.getTipoVehiculoBean().getTipoVehiculo());
			hayVehiculo = true;
		}

		// TIPO DE SERVICIO
		if(vehiculoBean.getServicioTraficoBean() != null && vehiculoBean.getServicioTraficoBean().getIdServicio() != null &&
				!vehiculoBean.getServicioTraficoBean().getIdServicio().equals("")){
			vehiculo.setSERVICIODESTINO(TipoServicio.fromValue(vehiculoBean.getServicioTraficoBean().getIdServicio()));
			hayVehiculo = true;
		}
		
		// CLASIFICACION DEL VEHICULO
		if ((vehiculoBean.getCriterioConstruccionBean() != null && 
				vehiculoBean.getCriterioConstruccionBean().getIdCriterioConstruccion() != null &&
				vehiculoBean.getCriterioConstruccionBean().getIdCriterioConstruccion().length() == 2 &&
				vehiculoBean.getCriterioUtilizacionBean() != null && 
				vehiculoBean.getCriterioUtilizacionBean().getIdCriterioUtilizacion() != null &&
				vehiculoBean.getCriterioUtilizacionBean().getIdCriterioUtilizacion().length() == 2)){
			vehiculo.setCLASIFICACIONVEHICULO(vehiculoBean.getCriterioConstruccionBean().getIdCriterioConstruccion()
					+ vehiculoBean.getCriterioUtilizacionBean().getIdCriterioUtilizacion());
		}

		// CLASIFICACION ITV
		if(vehiculoBean.getClasificacionItv() != null && !vehiculoBean.getClasificacionItv().equals("")){
			vehiculo.setCLASIFICACIONITV(Integer.valueOf(vehiculoBean.getClasificacionItv()));
			hayVehiculo = true;
		}

		// NUEVO
		if(vehiculoBean.getVehiUsado() != null && !vehiculoBean.getVehiUsado().equals("false")){
			vehiculo.setNUEVO("SI");
			hayVehiculo = true;
		}

		// USADO
		if(vehiculoBean.getVehiUsado() != null && vehiculoBean.getVehiUsado().equals("true")){
			vehiculo.setUSADO("SI");
			hayVehiculo = true;
		}

		// CO2
		if(vehiculoBean.getCo2() != null && !vehiculoBean.getCo2().equals("")){
			try{
				vehiculo.setEMISIONCO2(new BigDecimal(vehiculoBean.getCo2()));
				hayVehiculo = true;
			}catch(NumberFormatException ex){
				// No se ha seteado el co2 por no ser la cadena casteable a BigDecimal
				log.error("ERROR EXPORTACION :" + " El co2 debe ser casteable a BigDecimal. Valor: " +
					vehiculoBean.getCo2());
			}
		}

		// EPÍGRAFE
		if(vehiculoBean.getEpigrafeBean() != null && vehiculoBean.getEpigrafeBean().getIdEpigrafe() != null && 
				!vehiculoBean.getEpigrafeBean().getIdEpigrafe().equals("") &&
				!vehiculoBean.getEpigrafeBean().getIdEpigrafe().equals("-1")){
			vehiculo.setEPIGRAFE(vehiculoBean.getEpigrafeBean().getIdEpigrafe());
			hayVehiculo = true;
		}

		// KM
		if(vehiculoBean.getKmUso() != null && vehiculoBean.getKmUso().equals("true")){
			vehiculo.setKM(vehiculoBean.getKmUso().intValue());
			hayVehiculo = true;
		}

		// NÚMERO DE RUEDAS
		if(vehiculoBean.getNumRuedas() != null && !vehiculoBean.getNumRuedas().equals("")){
			try{
				vehiculo.setNUMERORUEDAS(new BigInteger(vehiculoBean.getNumRuedas().toString()));
				hayVehiculo = true;
			}catch(NumberFormatException ex){
				// No se ha seteado el número de ruedas por no ser la cadena casteable a BigInteger
				log.error("ERROR EXPORTACION :" + " El numero de ruedas debe ser casteable a BigInteger. Valor: " + 
					vehiculoBean.getNumRuedas());
			}
		}

		// HORAS DE USO
		if(vehiculoBean.getHorasUso() != null && vehiculoBean.getHorasUso().equals("true")){
			vehiculo.setCUENTAHORAS(vehiculoBean.getHorasUso().intValue());
			hayVehiculo = true;
		}

		// NUMERO DE SERIE ITV
		if(vehiculoBean.getNumSerie() != null && !vehiculoBean.getNumSerie().equals("")){
			vehiculo.setNUMEROSERIEITV(vehiculoBean.getNumSerie());
			hayVehiculo = true;
		}

		// TIPO TARJETA ITV
		if(vehiculoBean.getTipoTarjetaItvBean() != null && vehiculoBean.getTipoTarjetaItvBean().getIdTipoTarjetaItv() != null &&
				!vehiculoBean.getTipoTarjetaItvBean().getIdTipoTarjetaItv().equals("")){
			vehiculo.setTIPOTARJETAITV(TipoTarjetaItv.valueOf(vehiculoBean.getTipoTarjetaItvBean().getIdTipoTarjetaItv()));
			hayVehiculo = true;
		}

		// RENTING
		if(bean.getTramiteTraficoBean().getRenting() != null && bean.getTramiteTraficoBean().getRenting().equals("true")){
			vehiculo.setRENTING("SI");
			hayVehiculo = true;
		}

		// NIVE
		if(vehiculoBean.getNive() != null && !vehiculoBean.getNive().equals("")){
			vehiculo.setNIVE(vehiculoBean.getNive());
		}

		// LOCALIZACIÓN DEL VEHICULO
		Direccion direccion = vehiculoBean.getDireccionBean();
		if(direccion != null){

			// TIPO VIA
			if(direccion.getTipoVia() != null && direccion.getTipoVia().getIdTipoVia() != null &&
					utilesConversiones.getIdTipoViaDGTFromIdTipoVia(direccion.getTipoVia().getIdTipoVia()) != null &&
					!utilesConversiones.getIdTipoViaDGTFromIdTipoVia(direccion.getTipoVia().getIdTipoVia()).equals("")){
				vehiculo.setSIGLASDIRECCIONVEHICULO(utilesConversiones.getIdTipoViaDGTFromIdTipoVia(
						direccion.getTipoVia().getIdTipoVia()));
				hayVehiculo = true;
			}

			// NOMBRE VIA 
			if(direccion.getNombreVia() != null && !direccion.getNombreVia().equals("")){
				vehiculo.setDOMICILIOVEHICULO(direccion.getNombreVia());
				hayVehiculo = true;
			}

			// NUMERO
			if(direccion.getNumero() != null && !direccion.getNumero().equals("")){
				vehiculo.setNUMERODIRECCIONVEHICULO(direccion.getNumero());
				hayVehiculo = true;
			}

			// LETRA
			if(direccion.getLetra() != null && !direccion.getLetra().equals("")){
				vehiculo.setLETRADIRECCIONVEHICULO(direccion.getLetra());
				hayVehiculo = true;
			}

			// ESCALERA
			if(direccion.getEscalera() != null && !direccion.getEscalera().equals("")){
				vehiculo.setESCALERADIRECCIONVEHICULO(direccion.getEscalera());
				hayVehiculo = true;
			}

			// PISO
			if(direccion.getPlanta() != null && !direccion.getPlanta().equals("")){
				vehiculo.setPISODIRECCIONVEHICULO(direccion.getPlanta());
				hayVehiculo = true;
			}

			// PUERTA
			if(direccion.getPuerta() != null && !direccion.getPuerta().equals("")){
				vehiculo.setPUERTADIRECCIONVEHICULO(direccion.getPuerta());
				hayVehiculo = true;
			}

			// KILOMETRO
			if(direccion.getPuntoKilometrico() != null && !direccion.getPuntoKilometrico().equals("")){
				vehiculo.setKMDIRECCIONVEHICULO(direccion.getPuntoKilometrico());
				hayVehiculo = true;
			}

			// HECTOMETRO
			if(direccion.getHm() != null && !direccion.getHm().equals("")){
				vehiculo.setHECTOMETRODIRECCIONVEHICULO(direccion.getHm());
				hayVehiculo = true;
			}

			// BLOQUE
			if(direccion.getBloque() != null && !direccion.getBloque().equals("")){
				vehiculo.setBLOQUEDIRECCIONVEHICULO(direccion.getBloque());
				hayVehiculo = true;
			}

			// MUNICIPIO
			if(direccion.getMunicipio() != null && direccion.getMunicipio().getNombre() != null &&
					!direccion.getMunicipio().getNombre().equals("")){
				vehiculo.setMUNICIPIOVEHICULO(direccion.getMunicipio().getNombre());
				hayVehiculo = true;
			}

			// PUEBLO
			if(direccion.getPueblo() != null && !direccion.getPueblo().equals("")){
				vehiculo.setPUEBLOVEHICULO(direccion.getPueblo());
				hayVehiculo = true;
			}

			// PROVINCIA
			if(direccion.getMunicipio() != null && direccion.getMunicipio().getProvincia() != null &&
					direccion.getMunicipio().getProvincia().getIdProvincia() != null && 
					!direccion.getMunicipio().getProvincia().getIdProvincia().equals("") && 
					utilesConversiones.getSiglasFromIdProvincia(direccion.getMunicipio().getProvincia().getIdProvincia()) != null &&
					!utilesConversiones.getSiglasFromIdProvincia(direccion.getMunicipio().getProvincia().getIdProvincia()).equals("")){
				vehiculo.setPROVINCIAVEHICULO(TipoProvincia.fromValue(utilesConversiones.getSiglasFromIdProvincia(direccion.getMunicipio().
						getProvincia().getIdProvincia())));
				hayVehiculo = true;
			}

			// CODIGO POSTAL
			if(direccion.getCodPostal() != null && !direccion.getCodPostal().equals("")){
				vehiculo.setCPVEHICULO(direccion.getCodPostal());
				hayVehiculo = true;
			}

		}

		// MATE 2.5

		// CODIGO ECO
		if(vehiculoBean.getCodigoEco() != null && !vehiculoBean.getCodigoEco().equals("")){
			vehiculo.setCODIGOECO(vehiculoBean.getCodigoEco());
			hayVehiculo = true;
		}

		// CONSUMO
		if(vehiculoBean.getConsumo() != null && !vehiculoBean.getConsumo().toString().equals("")){
			vehiculo.setCONSUMO(vehiculoBean.getConsumo().intValue());
			hayVehiculo = true;
		}

		// DISTANCIA ENTRE EJES
		if(vehiculoBean.getDistanciaEntreEjes() != null && !vehiculoBean.getDistanciaEntreEjes().toString().equals("")){
			vehiculo.setDISTANCIAENTREEJES(vehiculoBean.getDistanciaEntreEjes().intValue());
			hayVehiculo = true;
		}

		// ECO INNOVACIÓN
		if(vehiculoBean.getEcoInnovacion() != null && !vehiculoBean.getEcoInnovacion().equals("")){
			vehiculo.setECOINNOVACION(vehiculoBean.getEcoInnovacion().toString());
			hayVehiculo = true;
		}

		// FABRICANTE
		if(vehiculoBean.getFabricante() != null && !vehiculoBean.getFabricante().equals("")){
			vehiculo.setFABRICANTE(vehiculoBean.getFabricante().toString());
			hayVehiculo = true;
		}

		// ALIMENTACION
		if(vehiculoBean.getAlimentacionBean() != null && 
				vehiculoBean.getAlimentacionBean().getIdAlimentacion() != null &&
				!vehiculoBean.getAlimentacionBean().getIdAlimentacion().equals("")){
			vehiculo.setIDALIMENTACION(TipoAlimentacion.fromValue(vehiculoBean.
					getAlimentacionBean().getIdAlimentacion()));
			hayVehiculo = true;
		}

		// CARBURANTE
		if(vehiculoBean.getCarburanteBean() != null && 
				vehiculoBean.getCarburanteBean().getIdCarburante() != null &&
				!vehiculoBean.getCarburanteBean().getIdCarburante().equals("")){
			// Existe una diferencia en la nomenclatura del carburante "Otros", así que comprobamos si debe ser un TipoCombustible.O
			if (Combustible.Otros.getValorEnum().equals(vehiculoBean.getCarburanteBean().getIdCarburante())) {
				vehiculo.setIDCARBURANTE(TipoCombustible.O);
			} else {
				vehiculo.setIDCARBURANTE(TipoCombustible.fromValue(vehiculoBean.getCarburanteBean().getIdCarburante()));
			}
			hayVehiculo = true;
		}

		// CARROCERIA
		if(vehiculoBean.getCarroceriaBean() != null && 
				vehiculoBean.getCarroceriaBean().getIdCarroceria() != null &&
				!vehiculoBean.getCarroceriaBean().getIdCarroceria().equals("")){
			vehiculo.setIDCARROCERIA(TipoCarroceria.fromValue(vehiculoBean.
					getCarroceriaBean().getIdCarroceria()));
			hayVehiculo = true;
		}

		// FABRICACION
		if(vehiculoBean.getFabricacionBean() != null && 
				vehiculoBean.getFabricacionBean().getIdFabricacion() != null &&
				!vehiculoBean.getFabricacionBean().getIdFabricacion().equals("")){
			vehiculo.setIDFABRICACION(TipoFabricacion.fromValue(vehiculoBean.
					getFabricacionBean().getIdFabricacion()));
			hayVehiculo = true;
		}

		// HOMOLOGACIÓN UE
		if(vehiculoBean.getHomologacionBean() != null && 
				vehiculoBean.getHomologacionBean().getIdHomologacion() != null &&
				!vehiculoBean.getHomologacionBean().getIdHomologacion().equals("")){
			vehiculo.setIDHOMOLOGACION(TipoHomologacion.fromValue(vehiculoBean.
					getHomologacionBean().getIdHomologacion()));
			hayVehiculo = true;
		}

		// PAÍS DE IMPORTACIÓN
		if(vehiculoBean.getPaisImportacionBean() != null && 
				vehiculoBean.getPaisImportacionBean().getIdPaisImportacion() != null &&
				!vehiculoBean.getPaisImportacionBean().getIdPaisImportacion().equals("")){
			vehiculo.setIDPAISIMPORTACION(TipoPaisImportacion.fromValue(vehiculoBean.
					getPaisImportacionBean().getIdPaisImportacion()));
			hayVehiculo = true;
		}

		// IMPORTADO
		if(vehiculoBean.getImportado() != null){
			if(vehiculoBean.getImportado() == true){
				vehiculo.setIMPORTADO(TipoSINO.SI.value());
				hayVehiculo = true;
			}
		}

		// MOM
		if(vehiculoBean.getMom() != null && !vehiculoBean.getMom().equals("")){
			try{
				vehiculo.setMOM(new BigInteger(vehiculoBean.getMom().toString()));
				hayVehiculo = true;
			}catch(NumberFormatException ex){
				// No se ha seteado el MOM por no ser la cadena casteable a BigInteger
				log.error("ERROR EXPORTACION :" + " El MOM debe ser casteable a BigInteger. Valor: " +
					vehiculoBean.getMom());
			}
		}
		
		// DRC@26-02-2013 Incidencia: 3658
		// CHECK FECHA CADUCIDAD ITV
		if(vehiculoBean.getCheckFechaCaducidadITV() != null && !vehiculoBean.getCheckFechaCaducidadITV().equals("")) {
			try {
				if (vehiculoBean.getCheckFechaCaducidadITV().equals("true")){
					vehiculo.setCHECKCADUCIDADITV("SI");
				} else {
					vehiculo.setCHECKCADUCIDADITV("NO");
				}		
			} catch (Exception e) {
				log.error("ERROR EXPORTACION :" + " El campo CHECKFECHACADUCIDAD. Valor: " + vehiculoBean.getCheckFechaCaducidadITV());
			}
		}

		// NIVEL DE EMISIONES
		if(vehiculoBean.getNivelEmisiones() != null && !vehiculoBean.getNivelEmisiones().equals("")){
			vehiculo.setNIVELEMISIONES(vehiculoBean.getNivelEmisiones().toString());
			hayVehiculo = true;
		}

		// REDUCCIÓN ECO
		if(vehiculoBean.getReduccionEco() != null && !vehiculoBean.getReduccionEco().equals("")){
			vehiculo.setREDUCCIONECO(vehiculoBean.getReduccionEco());
			hayVehiculo = true;
		}

		// SUBASTADO
		if(vehiculoBean.getSubasta() != null && !vehiculoBean.getSubasta().equals("")){
			if(vehiculoBean.getSubasta() == true){
				vehiculo.setSUBASTA(TipoSINO.SI.value());
				hayVehiculo = true;
			}
		}

		// VIA ANTERIOR
		if(vehiculoBean.getViaAnterior() != null && !vehiculoBean.getViaAnterior().equals("")){
			vehiculo.setVIAANTERIOR(vehiculoBean.getViaAnterior().intValue());
			hayVehiculo = true;
		}

		// VIA POSTERIOR
		if(vehiculoBean.getViaPosterior() != null && !vehiculoBean.getViaPosterior().equals("")){
			vehiculo.setVIAPOSTERIOR(vehiculoBean.getViaPosterior().intValue());
			hayVehiculo = true;
		}
		
		// TIPO DE INDUSTRIA
		if(vehiculoBean.getTipoIndustria() != null && !vehiculoBean.getTipoIndustria().equals("")){
			vehiculo.setTIPOINDUSTRIA(vehiculoBean.getTipoIndustria());
			hayVehiculo = true;
		}
		
		// FIN MATE 2.5

		// Comprueba el flag para setear vehiculo o no en el elemento raiz mat:
		if(hayVehiculo){
			mat.setDATOSVEHICULO(vehiculo);
		}

		//DATOS LIMITACION
		FORMATOGA.MATRICULACION.DATOSLIMITACION limitacion = factory.createFORMATOGAMATRICULACIONDATOSLIMITACION();

		// Flag. Indica si meter limitacion en la matriculación.
		boolean hayLimitacion = false;

		// TIPO LIMITACION
		if(bean.getTramiteTraficoBean().getIedtm() != null && 
				!bean.getTramiteTraficoBean().getIedtm().equals("")){
			limitacion.setTIPOLIMITACION(bean.getTramiteTraficoBean().getIedtm());
			hayLimitacion = true;
		}

		// FECHA LIMITACION
		if(bean.getTramiteTraficoBean().getFechaIedtm() != null && 
				!bean.getTramiteTraficoBean().getFechaIedtm().toString().equals("")){
			limitacion.setFECHALIMITACION(bean.getTramiteTraficoBean().getFechaIedtm().toString());
			hayLimitacion = true;
		}

		// NUMERO REGISTRO LIMITACION
		if(bean.getTramiteTraficoBean().getNumRegIedtm() != null && 
				!bean.getTramiteTraficoBean().getNumRegIedtm().getValorEnum().equals("")){
			limitacion.setNUMEROREGISTROLIMITACION(TipoMotivoExencion.valueOf(bean.getTramiteTraficoBean().getNumRegIedtm().getValorEnum()));
			hayLimitacion = true;
		}

		// FINANCIERA LIMITACION
		if(bean.getTramiteTraficoBean().getFinancieraIedtm() != null && 
				!bean.getTramiteTraficoBean().getFinancieraIedtm().equals("")){
			limitacion.setFINANCIERALIMITACION(bean.getTramiteTraficoBean().getFinancieraIedtm());
			hayLimitacion = true;
		}

		// Comprueba el flag para setear limitacion o no en el elemento raiz mat:
		if(hayLimitacion){
			mat.setDATOSLIMITACION(limitacion);
		}

		//DATOS IMPUESTOS
		FORMATOGA.MATRICULACION.DATOSIMPUESTOS impuestos = factory.createFORMATOGAMATRICULACIONDATOSIMPUESTOS();

		// Flag. Indica si meter impuestos en la matriculación.
		boolean hayImpuestos = false;
		
		// Ricardo Rodriguez. 26/12/2013. Mantis 6587
		// CEMA
		if(bean.getTramiteTraficoBean() != null && bean.getTramiteTraficoBean().getCema() != null && 
				!bean.getTramiteTraficoBean().getCema().equals("")){
			impuestos.setCEMA(bean.getTramiteTraficoBean().getCema());
		}
		// Fin Mantis 6587.

		// FECHA PRESENTACION IMPUESTO
		if(bean.getTramiteTraficoBean().getFechaPresentacion() != null && 
				!bean.getTramiteTraficoBean().getFechaPresentacion().isfechaNula()){
			impuestos.setFECHAPRESENTACIONIMPUESTO(bean.getTramiteTraficoBean().getFechaPresentacion().toString());
			hayImpuestos = true;
		}

		// CODIGO ELECTRONICO AEAT
		if(bean.getTramiteTraficoBean().getCemIedtm() != null && 
				!bean.getTramiteTraficoBean().getCemIedtm().equals("")){
			impuestos.setCODIGOELECTRONICOAEAT(bean.getTramiteTraficoBean().getCemIedtm());
			hayImpuestos = true;
		}

		//--DATOS 0506
		FORMATOGA.MATRICULACION.DATOSIMPUESTOS.DATOS0506 datos0506 = factory.createFORMATOGAMATRICULACIONDATOSIMPUESTOSDATOS0506();

		// Flag. Indica si meter datos0506 en los impuestos.
		boolean hay0506 = false;

		// MOTIVO EXENCION 05
		if(bean.getIdReduccion05() != null && bean.getIdReduccion05().getValorEnum() != null && 
				!bean.getIdReduccion05().getValorEnum().equals("")){
			datos0506.setMOTIVOEXENCION05(Tipo05.fromValue(bean.getIdReduccion05().getValorEnum()));
			hay0506 = true;
			hayImpuestos = true;
		}

		// MOTIVO EXENCION 06
		if(bean.getIdNoSujeccion06() != null && bean.getIdNoSujeccion06().getValorEnum() != null && 
				!bean.getIdNoSujeccion06().getValorEnum().equals("")){
			datos0506.setMOTIVOEXENCION06(Tipo06.fromValue(bean.getIdNoSujeccion06().getValorEnum()));
			hay0506 = true;
			hayImpuestos = true;
		}

		// Comprueba el flag para setear datos0506 o no en el elemento raiz mat:
		if(hay0506){
			impuestos.setDATOS0506(datos0506);
		}

		//--DATOS 576
		FORMATOGA.MATRICULACION.DATOSIMPUESTOS.DATOS576 datos576 = factory.createFORMATOGAMATRICULACIONDATOSIMPUESTOSDATOS576();

		// Flag. Indica si meter 576 en los impuestos.
		boolean hay576 = false;

		// IMPORTE TOTAL 576
		if(bean.getImporte576() != null && !bean.getImporte576().equals("")){
			datos576.setIMPORTETOTAL576(bean.getImporte576());
			hay576 = true;
			hayImpuestos = true;
		}

		// EJERCICIO DEVENGO
		if(bean.getEjercicioDevengo576() != null && bean.getEjercicioDevengo576() != null){
			try{
				int anioDevengo = bean.getEjercicioDevengo576().intValue();
				datos576.setEJERCICIODEVENGO576(anioDevengo);
				hay576 = true;
				hayImpuestos = true;
			}catch(Exception ex){
				log.error("No se ha podido parsear a Integer el ejercicio de devengo: " + 
					String.valueOf(bean.getEjercicioDevengo576()));
			}
		}

		// OBSERVACIONES 576
		if(bean.getObservaciones576() != null && !bean.getObservaciones576().equals("")){
			Observaciones observaciones = Observaciones.convertir(bean.getObservaciones576().getValorEnum());
			if(observaciones != null){
				datos576.setOBSERVACIONES576(Observaciones.valorExportacion(observaciones));
				hay576 = true;
				hayImpuestos = true;
			}
		}

		// EXENTO 576
		if(bean.getExento576() != null && bean.getExento576().equals("true")){
			datos576.setEXENTO576("SI");
			hay576 = true;
			hayImpuestos = true;
		}

		// BASE IMPONIBLE 576
		if(bean.getBaseImponible576() != null && !bean.getBaseImponible576().equals("") &&
			bean.getBaseImponible576() != BigDecimal.ZERO){
			try{
				datos576.setBASEIMPONIBLE576(new BigDecimal(bean.getBaseImponible576().toString()));
				hay576 = true;
				hayImpuestos = true;
			}catch(NumberFormatException ex){
				// No se ha seteado la base imponible 576 por no ser la cadena casteable a BigDecimal
				log.error("ERROR EXPORTACION :" + " La base imponible 576 debe ser casteable a BigDecimal. Valor: " +
					bean.getBaseImponible576());
			}
		}

		// BASE IMPONIBLE REDUCIDA 576
		if(bean.getBaseImponibleReducida576() != null && !bean.getBaseImponibleReducida576().equals("") &&
			bean.getBaseImponibleReducida576() != BigDecimal.ZERO){
			try{
				datos576.setBASEIMPONIBLEREDUCIDA576(new BigDecimal(bean.getBaseImponibleReducida576().toString()));
				hay576 = true;
				hayImpuestos = true;
			}catch(NumberFormatException ex){
				// No se ha seteado la base imponible reducida 576 por no ser la cadena casteable a BigDecimal
				log.error("ERROR EXPORTACION :" + " La base imponible reducida 576 debe ser casteable a BigDecimal. Valor: " +
					bean.getBaseImponibleReducida576());
			}
		}

		// TIPO DE GRAVAMEN
		if(bean.getTipoGravamen() != null && !bean.getTipoGravamen().equals("") &&
			bean.getTipoGravamen() != BigDecimal.ZERO){
			datos576.setTIPOGRAVAMEN576(bean.getTipoGravamen());
			hay576 = true;
			hayImpuestos = true;
		}

		// DEDUCCION LINEAL 576
		if(bean.getDeduccionLineal576() != null && !bean.getDeduccionLineal576().equals("") &&
			bean.getDeduccionLineal576() != BigDecimal.ZERO){
			try{
				datos576.setDEDUCCIONLINEAL576(new BigDecimal(bean.getDeduccionLineal576().toString()));
				hay576 = true;
				hayImpuestos = true;
			}catch(NumberFormatException ex){
				// No se ha seteado la base deduccion lineal 576 por no ser la cadena casteable a BigDecimal
				log.error("ERROR EXPORTACION :" + " La deduccion lineal 576 debe ser casteable a BigDecimal. Valor: " +
					bean.getDeduccionLineal576());
			}
		}

		// CUOTA 576
		if(bean.getCuota576() != null && !bean.getCuota576().equals("") &&
			bean.getCuota576() != BigDecimal.ZERO){
			try{
				datos576.setCUOTA576(new BigDecimal(bean.getCuota576().toString()));
				hay576 = true;
				hayImpuestos = true;
			}catch(NumberFormatException ex){
				// No se ha seteado la cuota 576 por no ser la cadena casteable a BigDecimal
				log.error("ERROR EXPORTACION :" + " La cuota 576 debe ser casteable a BigDecimal. Valor: " +
					bean.getCuota576());
			}
		}

		// CUOTA A INGRESAR 576
		if(bean.getCuotaIngresar576() != null && !bean.getCuotaIngresar576().equals("") &&
			bean.getCuotaIngresar576() != BigDecimal.ZERO){
			try{
				datos576.setCUOTAINGRESAR576(new BigDecimal(bean.getCuotaIngresar576().toString()));
				hay576 = true;
				hayImpuestos = true;
			}catch(NumberFormatException ex){
				// No se ha seteado la cuota a ingresar 576 por no ser la cadena casteable a BigDecimal
				log.error("ERROR EXPORTACION :" + " La cuota a ingresar 576 debe ser casteable a BigDecimal. Valor: " +
					bean.getCuotaIngresar576());
			}
		}

		// A DEDUCIR 576
		if(bean.getDeducir576() != null && !bean.getDeducir576().equals("") &&
			bean.getDeducir576() != BigDecimal.ZERO){
			try{
				datos576.setADEDUCIR576(new BigDecimal(bean.getDeducir576().toString()));
				hay576 = true;
				hayImpuestos = true;
			}catch(NumberFormatException ex){
				// No se ha seteado el 'a deducir 576' por no ser la cadena casteable a BigDecimal
				log.error("ERROR EXPORTACION :" + " El 'a deducir 576' debe ser casteable a BigDecimal. Valor: " +
					bean.getDeducir576());
			}
		}

		// LIQUIDACION 576
		if(bean.getLiquidacion576() != null && !bean.getLiquidacion576().equals("") &&
			bean.getLiquidacion576() != BigDecimal.ZERO){
			try{
				datos576.setRESULTADOLIQUIDACION576(new BigDecimal(bean.getLiquidacion576().toString()));
				hay576 = true;
				hayImpuestos = true;
			}catch(NumberFormatException ex){
				// No se ha seteado el resultado de la liquidacion 576 por no ser la cadena casteable a BigDecimal
				log.error("ERROR EXPORTACION :" + " El resultado de la liquidacion 576 debe ser casteable a BigDecimal. Valor: " +
					bean.getLiquidacion576());
			}
		}

		// DECLARACIÓN COMPLEMENTARIO 576
		if(bean.getNumDeclaracionComplementaria576() != null && !bean.getNumDeclaracionComplementaria576().equals("")){
			datos576.setDECLARACIONCOMPLEMENTARIA576(bean.getNumDeclaracionComplementaria576());
			hay576 = true;
			hayImpuestos = true;
		}

		// MATRICULA PREVER 576
		if(bean.getVehiculoPreverBean() != null && bean.getVehiculoPreverBean().getMatricula() != null && 
				!bean.getVehiculoPreverBean().getMatricula().equals("")){
			datos576.setMATRICULAPREVER576(bean.getVehiculoPreverBean().getMatricula());
			hay576 = true;
			hayImpuestos = true;
		}

		// TIPO ITV PREVER 576
		if(bean.getVehiculoPreverBean() != null && bean.getVehiculoPreverBean().getTipoItv() != null && 
				!bean.getVehiculoPreverBean().getTipoItv().equals("")){
			datos576.setTIPOITVPREVER576(bean.getVehiculoPreverBean().getTipoItv());
			hay576 = true;
			hayImpuestos = true;
		}

		// MARCA PREVER 576
		if(bean.getVehiculoPreverBean() != null && bean.getVehiculoPreverBean().getMarcaBean() != null && 
				bean.getVehiculoPreverBean().getMarcaBean().getCodigoMarca() != null){
			datos576.setMARCAPREVER576(bean.getVehiculoPreverBean().getMarcaBean().getCodigoMarca().toString());
			hay576 = true;
			hayImpuestos = true;
		}

		// MODELO PREVER 576
		if(bean.getVehiculoPreverBean() != null && bean.getVehiculoPreverBean().getModelo() != null && 
				!bean.getVehiculoPreverBean().getModelo().equals("")){
			datos576.setMODELOPREVER576(bean.getVehiculoPreverBean().getModelo());
			hay576 = true;
			hayImpuestos = true;
		}

		// BASTIDOR PREVER 576
		if(bean.getVehiculoPreverBean() != null && bean.getVehiculoPreverBean().getBastidor() != null && 
				!bean.getVehiculoPreverBean().getBastidor().equals("")){
			datos576.setNUMEROBASTIDORPREVER576(bean.getVehiculoPreverBean().getBastidor());
			hay576 = true;
			hayImpuestos = true;
		}

		// CLASIFICACION PREVER 576
		if ((bean.getVehiculoPreverBean().getCriterioConstruccionBean() != null && 
				bean.getVehiculoPreverBean().getCriterioConstruccionBean().getIdCriterioConstruccion() != null &&
				bean.getVehiculoPreverBean().getCriterioConstruccionBean().getIdCriterioConstruccion().length() == 2 &&
				bean.getVehiculoPreverBean().getCriterioUtilizacionBean() != null && 
				bean.getVehiculoPreverBean().getCriterioUtilizacionBean().getIdCriterioUtilizacion() != null &&
				bean.getVehiculoPreverBean().getCriterioUtilizacionBean().getIdCriterioUtilizacion().length() == 2)){
			datos576.setCLASIFICACIONPREVER576(bean.getVehiculoPreverBean().getCriterioConstruccionBean().getIdCriterioConstruccion()
					+ bean.getVehiculoPreverBean().getCriterioUtilizacionBean().getIdCriterioUtilizacion());
			hay576 = true;
			hayImpuestos = true;
		}

		// CAUSA HECHO IMPONIBLE
		if(bean.getCausaHechoImponible576() != null && bean.getCausaHechoImponible576().getValorEnum() != null && 
				!bean.getCausaHechoImponible576().getValorEnum().equals("")){
			datos576.setCAUSAHECHOIMPONIBLE(bean.getCausaHechoImponible576()!=null?bean.getCausaHechoImponible576().getValorEnum():"");
			hay576 = true;
			hayImpuestos = true;
		}

		// Comprueba el flag para setear datos0506 o no en el elemento raiz mat:
		if(hay576){
			impuestos.setDATOS576(datos576);
		}

		//--DATOS NRC
		FORMATOGA.MATRICULACION.DATOSIMPUESTOS.DATOSNRC nrc = factory.createFORMATOGAMATRICULACIONDATOSIMPUESTOSDATOSNRC();

		// Flag. Indica si meter nrc en los impuestos.
		boolean hayNrc = false;

		// CODIGO NRC
		if(bean.getNrc576() != null && !bean.getNrc576().equals("")){
			nrc.setCODIGONRC(bean.getNrc576());
			hayNrc = true;
			hayImpuestos = true;
		}

		// FECHA OPERACION NRC
		if(bean.getFechaPago576() != null && !bean.getFechaPago576().isfechaNula()){
			nrc.setFECHAOPERACIONNRC(bean.getFechaPago576().toString());
			hayNrc = true;
			hayImpuestos = true;
		}

		// Comprueba el flag para setear datos nrc o no en el elemento raiz mat:
		if(hayNrc){
			impuestos.setDATOSNRC(nrc);
		}

		//TODO MPC. Cambio IVTM.
		//DATOS IVTM
		IVTMModeloMatriculacionInterface modeloIVTM = new IVTMModeloMatriculacionImpl();
		FORMATOGA.MATRICULACION.DATOSIMPUESTOS.DATOSIMVTM  ivtm = (FORMATOGA.MATRICULACION.DATOSIMPUESTOS.DATOSIMVTM) modeloIVTM.obtenerDatosParaImportacion(bean.getTramiteTraficoBean().getNumExpediente(), tienePermisoIVTM);
		if ( ivtm!=null){
			impuestos.setDATOSIMVTM(ivtm);
			//TODO Hay que revisar este flag, ya que se podría hacer de otra forma.
			hayImpuestos=true;
		}

		// Comprueba el flag para setear datos ivtm o no en el elemento raiz mat:
		if(ivtm!=null){
			//TODO Hay que revisar este flag, ya que se podría hacer de otra forma.
			impuestos.setDATOSIMVTM(ivtm);
		}

		// Comprueba el flag para setear impuestos o no en el elemento raiz mat:
		if(hayImpuestos){
			mat.setDATOSIMPUESTOS(impuestos);
		}

		return mat;
	}

}
