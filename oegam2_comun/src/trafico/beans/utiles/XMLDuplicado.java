package trafico.beans.utiles;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.Marshaller;

import org.gestoresmadrid.core.springContext.ContextoSpring;
import org.gestoresmadrid.oegam2comun.contrato.model.service.ServicioContrato;
import org.gestoresmadrid.oegam2comun.trafico.view.dto.TramiteTrafDuplicadoDto;
import org.gestoresmadrid.oegamComun.acceso.service.impl.UtilesColegiado;
import org.gestoresmadrid.oegamComun.contrato.view.dto.ContratoDto;
import org.gestoresmadrid.oegamComun.interviniente.view.dto.IntervinienteTraficoDto;
import org.gestoresmadrid.oegamComun.persona.view.dto.PersonaDto;
import org.gestoresmadrid.oegamComun.vehiculo.view.dto.VehiculoDto;
import org.gestoresmadrid.utilidades.components.GestorPropiedades;
import org.gestoresmadrid.utilidades.components.Utiles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import es.globaltms.gestorDocumentos.constantes.ConstantesGestorFicheros;
import escrituras.beans.ResultBean;
import trafico.beans.jaxb.duplicados.DATOSCOTITULAR;
import trafico.beans.jaxb.duplicados.DATOSDUPLICADO;
import trafico.beans.jaxb.duplicados.DATOSPAGOPRESENTACION;
import trafico.beans.jaxb.duplicados.DATOSREPRESENTANTETITULAR;
import trafico.beans.jaxb.duplicados.DATOSTITULAR;
import trafico.beans.jaxb.duplicados.DATOSVEHICULO;
import trafico.beans.jaxb.duplicados.DIRECCION;
import trafico.beans.jaxb.duplicados.DUPLICADO;
import trafico.beans.jaxb.duplicados.FORMATOOEGAM2DUPLICADO;
import trafico.utiles.UtilesConversiones;
import trafico.utiles.XMLGAFactory;
import trafico.utiles.constantes.ConstantesTrafico;
import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;

public class XMLDuplicado {

	private static ILoggerOegam log = LoggerOegam.getLogger(XMLDuplicado.class);

	@Autowired
	private GestorPropiedades gestorPropiedades;
	
	@Autowired
	private Utiles utiles;

	@Autowired
	private UtilesConversiones utilesConversiones;

	@Autowired
	private UtilesColegiado utilesColegiado;
	
	public XMLDuplicado() {
		super();
		SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
	}

	public FORMATOOEGAM2DUPLICADO convertirAFORMATOGA(List<TramiteTrafDuplicadoDto> listaTramitesDuplicados, boolean xmlSesion) {
		FORMATOOEGAM2DUPLICADO formatoGA = instanciarCompleto();

		// Si se importa el xml de la sesion del usuario
		if (xmlSesion) {
			formatoGA.getCABECERA().getDATOSGESTORIA().setNIF(utilesColegiado.getNifUsuario());
			formatoGA.getCABECERA().getDATOSGESTORIA().setNOMBRE(utilesColegiado.getApellidosNombreUsuario());
			formatoGA.getCABECERA().getDATOSGESTORIA().setPROFESIONAL(listaTramitesDuplicados.get(0).getNumColegiado());
			formatoGA.getCABECERA().getDATOSGESTORIA().setPROVINCIA(utilesConversiones.getSiglaProvinciaFromNombre(utilesColegiado.getProvinciaDelContrato()));
		} else {
			ServicioContrato servicioContrato = ContextoSpring.getInstance().getBean(ServicioContrato.class);
			ContratoDto contrato = servicioContrato.getContratoDto(listaTramitesDuplicados.get(0).getIdContrato());

			// Añado Cabecera
			formatoGA.getCABECERA().getDATOSGESTORIA().setNIF(contrato.getCif());
			formatoGA.getCABECERA().getDATOSGESTORIA().setNOMBRE(contrato.getRazonSocial());
			formatoGA.getCABECERA().getDATOSGESTORIA().setPROFESIONAL(contrato.getColegiadoDto().getNumColegiado());
			formatoGA.getCABECERA().getDATOSGESTORIA().setPROVINCIA(utilesConversiones.getSiglaProvinciaFromNombre(utilesConversiones.getNombreProvincia(contrato.getIdProvincia())));
		}
		formatoGA.setFechaCreacion(getFechaHoy());

		List<DUPLICADO> lista = formatoGA.getDUPLICADO();
		for (int i = 0; i < listaTramitesDuplicados.size(); i++) {
			lista.add(convertirBeanToGa(listaTramitesDuplicados.get(i)));
		}
		formatoGA.setDuplicado(lista);

		return formatoGA;
	}

	public ResultBean FORMATOGAtoXML(FORMATOOEGAM2DUPLICADO formatoDuplicado, String idSession) {
		ResultBean resultado = new ResultBean();
		// Clasificar objeto en archivo.
		String nodo = gestorPropiedades.valorPropertie(ConstantesGestorFicheros.RUTA_ARCHIVOS_TEMP);
		File salida = new File(nodo + "duplicado" + idSession + ".xml");
		try {
			// Crear clasificador
			Marshaller m = new XMLGAFactory().getDuplicadoExportacionContext().createMarshaller();
			m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
			m.setProperty(Marshaller.JAXB_ENCODING, "ISO-8859-1");
			m.marshal(formatoDuplicado, salida);
			byte[] bytes = utiles.getBytesFromFile(salida);
			resultado.addAttachment(ConstantesTrafico.BYTESXML, bytes);
		} catch (Exception e) {
			log.error(e);
			resultado.setError(true);
			resultado.setMensaje("Error al generar el fichero XML");
			return resultado;
		} finally {
			salida.delete();
		}
		return resultado;
	}

	private DUPLICADO convertirBeanToGa(TramiteTrafDuplicadoDto dto) {
		DUPLICADO duplicado = instanciarDUPLICADO();

		DATOSDUPLICADO datosDuplicado = duplicado.getDATOSDUPLICADO();

		datosDuplicado.setMOTIVODUPLICADO(dto.getMotivoDuplicado());
		datosDuplicado.setREFERENCIAPROPIA(dto.getRefPropia());
		datosDuplicado.setINDIMPRESIONPERMISOCIRCULACION(dto.getImprPermisoCirculacion() != null ? dto.getImprPermisoCirculacion() : null);
		datosDuplicado.setINDVEHICULOIMPORTACION(dto.getImportacion() != null ? dto.getImportacion() : null);
		datosDuplicado.setCODIGOTASAVEHICULOIMPORTACION(dto.getTasaImportacion());
		datosDuplicado.setOBSERVACIONESDGT(dto.getAnotaciones());
		
		duplicado.setDATOSDUPLICADO(datosDuplicado);

		convertirDtoToGaVehiculo(duplicado, dto.getVehiculoDto());

		convertirDtoToGaTitular(duplicado, dto.getTitular());

		convertirDtoToGaRepresentanteTitular(duplicado, dto.getRepresentanteTitular());

		convertirDtoToGaCoitular(duplicado, dto.getCotitular());

		DATOSPAGOPRESENTACION datosPago = duplicado.getDATOSPAGOPRESENTACION();
		datosPago.setCODIGOTASAPERMISO(dto.getTasaPermiso() != null ? dto.getTasaPermiso() : null);
		datosPago.setCODIGOTASAFICHA(dto.getTasaFichaTecnica() != null ? dto.getTasaFichaTecnica() : null);
		datosPago.setJEFATURAPROVINCIAL(dto.getJefaturaTraficoDto() != null ? dto.getJefaturaTraficoDto().getJefaturaProvincial() : null);
		datosPago.setFECHAPRESENTACION(dto.getFechaPresentacion() != null ? dto.getFechaPresentacion().toString() : null);

		duplicado.setDATOSPAGOPRESENTACION(datosPago);

		return duplicado;
	}

	private void convertirDtoToGaVehiculo(DUPLICADO duplicado, VehiculoDto vehiculo) {
		if (vehiculo != null) {
			DATOSVEHICULO datosVehiculo = duplicado.getDATOSVEHICULO();
			datosVehiculo.setMATRICULA(vehiculo.getMatricula());
			datosVehiculo.setFECHAMATRICULACION(vehiculo.getFechaMatriculacion() != null ? vehiculo.getFechaMatriculacion().toString() : null);
			datosVehiculo.setFECHAITV(vehiculo.getFechaItv() != null ? vehiculo.getFechaItv().toString() : null);
			duplicado.setDATOSVEHICULO(datosVehiculo);
		}
	}

	private void convertirDtoToGaTitular(DUPLICADO duplicado, IntervinienteTraficoDto titular) {
		if (titular != null) {
			PersonaDto persona = titular.getPersona();
			DATOSTITULAR datosTitular = duplicado.getDATOSTITULAR();
			if (persona != null) {
				datosTitular.setNIFCIF(persona.getNif());
				datosTitular.setTIPOPERSONA(persona.getTipoPersona());
				datosTitular.setSEXO(persona.getSexo());
				datosTitular.setAPELLIDO1RAZONSOCIAL(persona.getApellido1RazonSocial());
				datosTitular.setAPELLIDO2(persona.getApellido2());
				datosTitular.setNOMBRE(persona.getNombre());

				if (titular.getDireccion() != null) {
					DIRECCION direccion = new DIRECCION();
					direccion.setPROVINCIA(titular.getDireccion().getIdProvincia());
					direccion.setMUNICIPIO(titular.getDireccion().getIdMunicipio());
					direccion.setPUEBLO(titular.getDireccion().getPueblo());
					direccion.setCODIGOPOSTAL(titular.getDireccion().getCodPostal());
					direccion.setTIPOVIA(titular.getDireccion().getIdTipoVia());
					direccion.setNOMBREVIA(titular.getDireccion().getNombreVia());
					direccion.setNUMERO(titular.getDireccion().getNumero());
					direccion.setLETRA(titular.getDireccion().getLetra());
					direccion.setESCALERA(titular.getDireccion().getEscalera());
					direccion.setPISO(titular.getDireccion().getPlanta());
					direccion.setPUERTA(titular.getDireccion().getPuerta());
					direccion.setBLOQUE(titular.getDireccion().getBloque());

					if (titular.getDireccion().getKm() != null) {
						direccion.setKM(titular.getDireccion().getKm().toString());
					}
					if (titular.getDireccion().getHm() != null) {
						direccion.setHM(titular.getDireccion().getHm().toString());
					}

					datosTitular.setDIRECCION(direccion);
				}
			}
			duplicado.setDATOSTITULAR(datosTitular);
		}
	}

	private void convertirDtoToGaRepresentanteTitular(DUPLICADO duplicado, IntervinienteTraficoDto representante) {
		if (representante != null) {
			DATOSREPRESENTANTETITULAR datosRepresentanteTitular = duplicado.getDATOSREPRESENTANTETITULAR();
			PersonaDto persona = representante.getPersona();
			if (persona != null) {
				datosRepresentanteTitular.setNIFCIF(persona.getNif());
				datosRepresentanteTitular.setAPELLIDO1RAZONSOCIAL(persona.getApellido1RazonSocial());
				datosRepresentanteTitular.setAPELLIDO2(persona.getApellido2());
				datosRepresentanteTitular.setNOMBRE(persona.getNombre());
			}

			datosRepresentanteTitular.setCONCEPTOTUTELA(representante.getConceptoRepre());
			datosRepresentanteTitular.setMOTIVOTUTELA(representante.getIdMotivoTutela());
			datosRepresentanteTitular.setDATOSDOCUMENTO(representante.getDatosDocumento());
			datosRepresentanteTitular.setFECHAINICIOTUTELA(representante.getFechaInicio() != null ? representante.getFechaInicio().toString() : null);
			datosRepresentanteTitular.setFECHAFINTUTELA(representante.getFechaFin() != null ? representante.getFechaFin().toString() : null);

			duplicado.setDATOSREPRESENTANTETITULAR(datosRepresentanteTitular);
		}
	}

	private void convertirDtoToGaCoitular(DUPLICADO duplicado, IntervinienteTraficoDto cotitular) {
		if (cotitular != null) {
			DATOSCOTITULAR datosCoTitular = duplicado.getDATOSCOTITULAR();
			PersonaDto persona = cotitular.getPersona();
			if (persona != null) {
				datosCoTitular.setNIFCIF(persona.getNif());
				datosCoTitular.setAPELLIDO1RAZONSOCIAL(persona.getApellido1RazonSocial());
				datosCoTitular.setAPELLIDO2(persona.getApellido2());
				datosCoTitular.setNOMBRE(persona.getNombre());

				if (cotitular.getDireccion() != null) {
					DIRECCION direccion = new DIRECCION();
					direccion.setPROVINCIA(cotitular.getDireccion().getIdProvincia());
					direccion.setMUNICIPIO(cotitular.getDireccion().getIdMunicipio());
					direccion.setPUEBLO(cotitular.getDireccion().getPueblo());
					direccion.setCODIGOPOSTAL(cotitular.getDireccion().getCodPostal());
					direccion.setTIPOVIA(cotitular.getDireccion().getIdTipoVia());
					direccion.setNOMBREVIA(cotitular.getDireccion().getNombreVia());
					direccion.setNUMERO(cotitular.getDireccion().getNumero());
					direccion.setLETRA(cotitular.getDireccion().getLetra());
					direccion.setESCALERA(cotitular.getDireccion().getEscalera());
					direccion.setPISO(cotitular.getDireccion().getPlanta());
					direccion.setPUERTA(cotitular.getDireccion().getPuerta());
					direccion.setBLOQUE(cotitular.getDireccion().getBloque());

					if (cotitular.getDireccion().getKm() != null) {
						direccion.setKM(cotitular.getDireccion().getKm().toString());
					}
					if (cotitular.getDireccion().getHm() != null) {
						direccion.setHM(cotitular.getDireccion().getHm().toString());
					}

					datosCoTitular.setDIRECCION(direccion);
				}
			}
			duplicado.setDATOSCOTITULAR(datosCoTitular);
		}
	}

	private DUPLICADO instanciarDUPLICADO() {
		trafico.beans.jaxb.duplicados.ObjectFactory factory = new trafico.beans.jaxb.duplicados.ObjectFactory();
		DUPLICADO duplicado = factory.createDUPLICADO();

		// DATOS DUPLICADOS
		DATOSDUPLICADO datosDuplicado = factory.createDATOSDUPLICADO();
		datosDuplicado.setMOTIVODUPLICADO("");
		datosDuplicado.setREFERENCIAPROPIA("");
		datosDuplicado.setINDIMPRESIONPERMISOCIRCULACION(false);
		datosDuplicado.setINDVEHICULOIMPORTACION(false);
		datosDuplicado.setCODIGOTASAVEHICULOIMPORTACION("");
		datosDuplicado.setOBSERVACIONESDGT("");

		duplicado.setDATOSDUPLICADO(datosDuplicado);

		// DATOS VEHICULO
		DATOSVEHICULO datosVehiculo = factory.createDATOSVEHICULO();
		datosVehiculo.setMATRICULA("");
		datosVehiculo.setFECHAMATRICULACION("");
		datosVehiculo.setFECHAITV("");

		duplicado.setDATOSVEHICULO(datosVehiculo);

		// DATOS TITULAR
		DATOSTITULAR datosTitular = factory.createDATOSTITULAR();
		datosTitular.setNIFCIF("");
		datosTitular.setTIPOPERSONA("");
		datosTitular.setSEXO("");
		datosTitular.setAPELLIDO1RAZONSOCIAL("");
		datosTitular.setAPELLIDO2("");
		datosTitular.setNOMBRE("");
		datosTitular.setDIRECCION(null);

		duplicado.setDATOSTITULAR(datosTitular);

		// DATOS REPRESENTANTE TITULAR
		DATOSREPRESENTANTETITULAR datosRepresentanteTitular = factory.createDATOSREPRESENTANTETITULAR();
		datosRepresentanteTitular.setNIFCIF("");
		datosRepresentanteTitular.setAPELLIDO1RAZONSOCIAL("");
		datosRepresentanteTitular.setAPELLIDO2("");
		datosRepresentanteTitular.setNOMBRE("");
		datosRepresentanteTitular.setCONCEPTOTUTELA("");
		datosRepresentanteTitular.setMOTIVOTUTELA("");
		datosRepresentanteTitular.setDATOSDOCUMENTO("");
		datosRepresentanteTitular.setFECHAINICIOTUTELA("");
		datosRepresentanteTitular.setFECHAFINTUTELA("");

		duplicado.setDATOSREPRESENTANTETITULAR(datosRepresentanteTitular);

		// DATOS COTITULAR
		DATOSCOTITULAR datosCotitular = factory.createDATOSCOTITULAR();
		datosCotitular.setNIFCIF("");
		datosCotitular.setTIPOPERSONA("");
		datosCotitular.setSEXO("");
		datosCotitular.setAPELLIDO1RAZONSOCIAL("");
		datosCotitular.setAPELLIDO2("");
		datosCotitular.setNOMBRE("");
		datosCotitular.setDIRECCION(null);

		duplicado.setDATOSCOTITULAR(datosCotitular);

		// DATOS PAGO PRESENTACION
		DATOSPAGOPRESENTACION datosPago = factory.createDATOSPAGOPRESENTACION();
		datosPago.setCODIGOTASAPERMISO("");
		datosPago.setCODIGOTASAFICHA("");
		datosPago.setJEFATURAPROVINCIAL("");
		datosPago.setFECHAPRESENTACION("");

		duplicado.setDATOSPAGOPRESENTACION(datosPago);

		return duplicado;
	}

	private FORMATOOEGAM2DUPLICADO instanciarCompleto() {
		trafico.beans.jaxb.duplicados.ObjectFactory factory = new trafico.beans.jaxb.duplicados.ObjectFactory();

		FORMATOOEGAM2DUPLICADO ga = factory.createFORMATOOEGAM2DUPLICADO();

		// CABECERA
		ga.setCABECERA(factory.createCABECERA());
		ga.getCABECERA().setDATOSGESTORIA(factory.createDATOSGESTORIA());
		ga.getCABECERA().getDATOSGESTORIA().setNIF("");
		ga.getCABECERA().getDATOSGESTORIA().setNOMBRE("");
		ga.getCABECERA().getDATOSGESTORIA().setPROFESIONAL("");
		ga.getCABECERA().getDATOSGESTORIA().setPROVINCIA("");

		ga.setFechaCreacion("");
		ga.setPlataforma("OEGAM");

		ga.setDuplicado(new ArrayList<DUPLICADO>());
		return ga;
	}

	public String getFechaHoy() {
		java.util.Date date = new java.util.Date();
		java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("dd/MM/yyyy");
		String fecha = sdf.format(date);
		return fecha;
	}
}
