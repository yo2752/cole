package trafico.beans.utiles;

import java.io.File;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.Marshaller;

import org.gestoresmadrid.core.springContext.ContextoSpring;
import org.gestoresmadrid.oegam2comun.contrato.model.service.ServicioContrato;
import org.gestoresmadrid.oegam2comun.trafico.view.dto.TramiteTrafBajaDto;
import org.gestoresmadrid.oegamComun.acceso.service.impl.UtilesColegiado;
import org.gestoresmadrid.oegamComun.contrato.view.dto.ContratoDto;
import org.gestoresmadrid.oegamComun.interviniente.view.dto.IntervinienteTraficoDto;
import org.gestoresmadrid.oegamComun.vehiculo.view.dto.VehiculoDto;
import org.gestoresmadrid.utilidades.components.GestorPropiedades;
import org.gestoresmadrid.utilidades.components.Utiles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import es.globaltms.gestorDocumentos.constantes.ConstantesGestorFicheros;
import escrituras.beans.ResultBean;
import trafico.beans.jaxb.baja.BAJA;
import trafico.beans.jaxb.baja.FORMATOOEGAM2BAJA;
import trafico.utiles.UtilesConversiones;
import trafico.utiles.XMLGAFactory;
import trafico.utiles.constantes.ConstantesTrafico;
import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;

public class XMLBaja {

	private static ILoggerOegam log = LoggerOegam.getLogger(XMLBaja.class);

	@Autowired
	private GestorPropiedades gestorPropiedades;
	
	@Autowired
	private Utiles utiles;

	@Autowired
	private UtilesConversiones utilesConversiones;

	@Autowired
	private UtilesColegiado utilesColegiado;
	
	public XMLBaja() {
		super();
		SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
	}

	/**
	 * Convierte el FORMATOGA a XML
	 * @param ga
	 */
	public ResultBean FORMATOGAtoXML(FORMATOOEGAM2BAJA ga, String idSession) {
		ResultBean resultado = new ResultBean();
		// Clasificar objeto en archivo.
		String nodo = gestorPropiedades.valorPropertie(ConstantesGestorFicheros.RUTA_ARCHIVOS_TEMP);
		File salida = new File(nodo + "baja" + idSession + ".xml");
		try {
			// Crear clasificador
			Marshaller m = new XMLGAFactory().getBajaExportacionContext().createMarshaller();
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
			salida.delete();
		}
		return resultado;
	}

	public FORMATOOEGAM2BAJA convertirAFORMATOGA(List<TramiteTrafBajaDto> listaBaja, boolean xmlSesion, Boolean tienePermisosBtv) {
		FORMATOOEGAM2BAJA ga = instanciarCompleto();


		// Si se importa el xml de la sesion del usuario
		if (xmlSesion) {
			ga.getCABECERA().getDATOSGESTORIA().setNIF(utilesColegiado.getNifUsuario());
			ga.getCABECERA().getDATOSGESTORIA().setNOMBRE(utilesColegiado.getApellidosNombreUsuario().replace(",", ""));
			ga.getCABECERA().getDATOSGESTORIA().setPROFESIONAL(utilesColegiado.getNumColegiadoSession());
			ga.getCABECERA().getDATOSGESTORIA().setPROVINCIA(utilesConversiones.getSiglaProvinciaFromNombre(utilesColegiado.getProvinciaDelContrato()));
			
			// Si se importa el xml del contrato de la gestoria del tramite a exportar
		} else {
			
			ServicioContrato servicioContrato = ContextoSpring.getInstance().getBean(ServicioContrato.class);
			ContratoDto contrato = servicioContrato.getContratoDto(listaBaja.get(0).getIdContrato());

			// Añado Cabecera
			ga.getCABECERA().getDATOSGESTORIA().setNIF(contrato.getCif());
			ga.getCABECERA().getDATOSGESTORIA().setNOMBRE(contrato.getRazonSocial());
			ga.getCABECERA().getDATOSGESTORIA().setPROFESIONAL(contrato.getColegiadoDto().getNumColegiado());
			String sigla = utilesConversiones.getSiglaProvinciaFromNombre(utilesConversiones.getNombreProvincia(contrato.getIdProvincia()));
			ga.getCABECERA().getDATOSGESTORIA().setPROVINCIA(sigla);
		}
		ga.setFechaCreacion(getFechaHoy());

		List<BAJA> lista = ga.getBAJA();
		for (int i = 0; i < listaBaja.size(); i++) {
			lista.add(convertirBeanToGa(listaBaja.get(i), tienePermisosBtv));
		}

		ga.setBAJA(lista);
		return ga;
	}

	private FORMATOOEGAM2BAJA instanciarCompleto() {
		trafico.beans.jaxb.baja.ObjectFactory factory = new trafico.beans.jaxb.baja.ObjectFactory();

		FORMATOOEGAM2BAJA ga = factory.createFORMATOOEGAM2BAJA();

		// CABECERA
		ga.setCABECERA(factory.createCABECERA());
		ga.getCABECERA().setDATOSGESTORIA(factory.createDATOSGESTORIA());
		ga.getCABECERA().getDATOSGESTORIA().setNIF("");
		ga.getCABECERA().getDATOSGESTORIA().setNOMBRE("");
		ga.getCABECERA().getDATOSGESTORIA().setPROFESIONAL("");
		ga.getCABECERA().getDATOSGESTORIA().setPROVINCIA("");

		ga.setFechaCreacion("");
		ga.setPlataforma("OEGAM");

		ga.setBAJA(new ArrayList<BAJA>());
		return ga;
	}

	private BAJA instanciarBAJA() {
		trafico.beans.jaxb.baja.ObjectFactory factory = new trafico.beans.jaxb.baja.ObjectFactory();
		BAJA baja = factory.createBAJA();

		baja.setNUMEROEXPEDIENTE("");
		baja.setREFERENCIAPROPIA("");
		baja.setPROFESIONAL("");
		baja.setFECHACREACION("");
		baja.setOBSERVACIONES("");
		baja.setMOTIVOBAJA("");
		baja.setPAISBAJA("");
		baja.setPERMISOCIRCULACION("");
		baja.setTARJETAINSPECCIONTECNICA("");
		baja.setBAJAIMVTM("");
		baja.setJUSTIFICANTEDENUNCIA("");
		baja.setDOCUMENTOPROPIEDAD("");
		baja.setJUSTIFICANTEIMVTM("");

		// VEHICULO
		baja.setDATOSVEHICULO(factory.createDATOSVEHICULO());
		baja.getDATOSVEHICULO().setMATRICULA("");
		baja.getDATOSVEHICULO().setNUMEROBASTIDOR("");
		baja.getDATOSVEHICULO().setFECHAMATRICULACION("");
		baja.getDATOSVEHICULO().setPROVINCIAVEHICULO("");
		baja.getDATOSVEHICULO().setMUNICIPIOVEHICULO("");
		baja.getDATOSVEHICULO().setPUEBLOVEHICULO("");
		baja.getDATOSVEHICULO().setCODIGOPOSTALVEHICULO("");
		baja.getDATOSVEHICULO().setTIPOVIAVEHICULO("");
		baja.getDATOSVEHICULO().setCALLEDIRECCIONVEHICULO("");
		baja.getDATOSVEHICULO().setNUMERODIRECCIONVEHICULO("");
		baja.getDATOSVEHICULO().setLETRADIRECCIONVEHICULO("");
		baja.getDATOSVEHICULO().setESCALERADIRECCIONVEHICULO("");
		baja.getDATOSVEHICULO().setPISODIRECCIONVEHICULO("");
		baja.getDATOSVEHICULO().setPUERTADIRECCIONVEHICULO("");
		baja.getDATOSVEHICULO().setBLOQUEDIRECCIONVEHICULO("");
		baja.getDATOSVEHICULO().setKMDIRECCIONVEHICULO("");
		baja.getDATOSVEHICULO().setHMDIRECCIONVEHICULO("");

		// TITULAR
		baja.setDATOSTITULAR(factory.createDATOSTITULAR());
		baja.getDATOSTITULAR().setDNITITULAR("");
		baja.getDATOSTITULAR().setSEXOTITULAR("");
		baja.getDATOSTITULAR().setAPELLIDO1RAZONSOCIALTITULAR("");
		baja.getDATOSTITULAR().setAPELLIDO2TITULAR("");
		baja.getDATOSTITULAR().setNOMBRETITULAR("");
		baja.getDATOSTITULAR().setPROVINCIATITULAR("");
		baja.getDATOSTITULAR().setMUNICIPIOTITULAR("");
		baja.getDATOSTITULAR().setPUEBLOTITULAR("");
		baja.getDATOSTITULAR().setCODIGOPOSTALTITULAR("");
		baja.getDATOSTITULAR().setTIPOVIATITULAR("");
		baja.getDATOSTITULAR().setCALLEDIRECCIONTITULAR("");
		baja.getDATOSTITULAR().setNUMERODIRECCIONTITULAR("");
		baja.getDATOSTITULAR().setLETRADIRECCIONTITULAR("");
		baja.getDATOSTITULAR().setESCALERADIRECCIONTITULAR("");
		baja.getDATOSTITULAR().setPISODIRECCIONTITULAR("");
		baja.getDATOSTITULAR().setPUERTADIRECCIONTITULAR("");
		baja.getDATOSTITULAR().setBLOQUEDIRECCIONTITULAR("");
		baja.getDATOSTITULAR().setKMDIRECCIONTITULAR("");
		baja.getDATOSTITULAR().setHMDIRECCIONTITULAR("");

		// REPRESENTANTE
		baja.setDATOSREPRESENTANTE(factory.createDATOSREPRESENTANTE());
		baja.getDATOSREPRESENTANTE().setDNIREPRESENTANTE("");
		baja.getDATOSREPRESENTANTE().setSEXOREPRESENTANTE("");
		baja.getDATOSREPRESENTANTE().setAPELLIDO1RAZONSOCIALREPRESENTANTE("");
		baja.getDATOSREPRESENTANTE().setAPELLIDO2REPRESENTANTE("");
		baja.getDATOSREPRESENTANTE().setNOMBREREPRESENTANTE("");
		baja.getDATOSREPRESENTANTE().setCONCEPTOREPRESENTANTE("");

		// ADQUIRIENTE
		baja.setDATOSADQUIRIENTE(factory.createDATOSADQUIRIENTE());
		baja.getDATOSADQUIRIENTE().setDNIADQUIRIENTE("");
		baja.getDATOSADQUIRIENTE().setSEXOADQUIRIENTE("");
		baja.getDATOSADQUIRIENTE().setAPELLIDO1RAZONSOCIALADQUIRIENTE("");
		baja.getDATOSADQUIRIENTE().setAPELLIDO2ADQUIRIENTE("");
		baja.getDATOSADQUIRIENTE().setNOMBREADQUIRIENTE("");
		baja.getDATOSADQUIRIENTE().setFECHANACIMIENTOADQUIRIENTE("");
		baja.getDATOSADQUIRIENTE().setPROVINCIAADQUIRIENTE("");
		baja.getDATOSADQUIRIENTE().setMUNICIPIOADQUIRIENTE("");
		baja.getDATOSADQUIRIENTE().setPUEBLOADQUIRIENTE("");
		baja.getDATOSADQUIRIENTE().setCODIGOPOSTALADQUIRIENTE("");
		baja.getDATOSADQUIRIENTE().setTIPOVIAADQUIRIENTE("");
		baja.getDATOSADQUIRIENTE().setCALLEDIRECCIONADQUIRIENTE("");
		baja.getDATOSADQUIRIENTE().setNUMERODIRECCIONADQUIRIENTE("");
		baja.getDATOSADQUIRIENTE().setLETRADIRECCIONADQUIRIENTE("");
		baja.getDATOSADQUIRIENTE().setESCALERADIRECCIONADQUIRIENTE("");
		baja.getDATOSADQUIRIENTE().setPISODIRECCIONADQUIRIENTE("");
		baja.getDATOSADQUIRIENTE().setPUERTADIRECCIONADQUIRIENTE("");
		baja.getDATOSADQUIRIENTE().setBLOQUEDIRECCIONADQUIRIENTE("");
		baja.getDATOSADQUIRIENTE().setKMDIRECCIONADQUIRIENTE("");
		baja.getDATOSADQUIRIENTE().setHMDIRECCIONADQUIRIENTE("");

		// REPRESENTANTE
		baja.setDATOSREPRESENTANTEADQUIRIENTE(factory.createDATOSREPRESENTANTEADQUIRIENTE());
		baja.getDATOSREPRESENTANTEADQUIRIENTE().setDNIREPRESENTANTEADQUIRIENTE("");
		baja.getDATOSREPRESENTANTEADQUIRIENTE().setSEXOREPRESENTANTEADQUIRIENTE("");
		baja.getDATOSREPRESENTANTEADQUIRIENTE().setAPELLIDO1RAZONSOCIALREPRESENTANTEADQUIRIENTE("");
		baja.getDATOSREPRESENTANTEADQUIRIENTE().setAPELLIDO2REPRESENTANTEADQUIRIENTE("");
		baja.getDATOSREPRESENTANTEADQUIRIENTE().setNOMBREREPRESENTANTEADQUIRIENTE("");
		baja.getDATOSREPRESENTANTEADQUIRIENTE().setCONCEPTOREPRESENTANTEADQUIRIENTE("");

		// PAGO PRESENTACION
		baja.setDATOSPAGOPRESENTACION(factory.createDATOSPAGOPRESENTACION());
		baja.getDATOSPAGOPRESENTACION().setCODIGOTASA("");
		baja.getDATOSPAGOPRESENTACION().setJEFATURAPROVINCIAL("");
		baja.getDATOSPAGOPRESENTACION().setFECHAPRESENTACION("");
		baja.getDATOSPAGOPRESENTACION().setCEMA("");
		return baja;
	}

	private BAJA convertirBeanToGa(TramiteTrafBajaDto dto, Boolean tienePermisosBtv) {
		BAJA baja = instanciarBAJA();

		cambiarNullsPorFalsesOVacios(dto);

		/****************************************************************************************************************
		 * RELLENAMOS EL FORMATOGA CON LOS DATOS QUE TENGAMOS EN EL BEAN DE PANTALLA ***********************************
		 ***************************************************************************************************************/
		baja.setNUMEROEXPEDIENTE(dto.getNumExpediente().toString());
		baja.setREFERENCIAPROPIA(dto.getRefPropia());
		baja.setPROFESIONAL(dto.getNumColegiado());
		baja.setFECHACREACION(dto.getFechaAlta() != null ? dto.getFechaAlta().toString() : "");
		baja.setOBSERVACIONES(dto.getAnotaciones());
		baja.setMOTIVOBAJA(dto.getMotivoBaja() != null && !dto.getMotivoBaja().equals("") ? dto.getMotivoBaja() : "");
		

			if (dto.getPais()!=null && !dto.getPais().equals("")) {
				baja.setPAISBAJA(dto.getPais().getSigla2());
			}
			

		
		baja.setPERMISOCIRCULACION(null != dto.getPermisoCircu() && true == dto.getPermisoCircu() ? "SI" : "NO");
		baja.setTARJETAINSPECCIONTECNICA(null != dto.getTarjetaInspeccion() && true == dto.getTarjetaInspeccion() ? "SI" : "NO");
		baja.setDECLARACIONJURADAEXTRAVIO(null != dto.getDeclaracionJuradaExtravio() && true == dto.getDeclaracionJuradaExtravio() ? "SI" : "NO");
		baja.setBAJAIMVTM(null != dto.getBajaImpMunicipal() && true == dto.getBajaImpMunicipal() ? "SI" : "NO");
		baja.setJUSTIFICANTEDENUNCIA(null != dto.getJustificanteDenuncia() && true == dto.getJustificanteDenuncia() ? "SI" : "NO");
		baja.setDOCUMENTOPROPIEDAD(null != dto.getPropiedadDesguace() && true == dto.getPropiedadDesguace() ? "SI" : "NO");
		baja.setJUSTIFICANTEIMVTM(null != dto.getPagoImpMunicipal() && true == dto.getPagoImpMunicipal() ? "SI" : "NO");
		if (null != dto.getDeclaracionResiduo()) {
			baja.setDECLARACIONNORESIDUO(dto.getDeclaracionResiduo() == Boolean.TRUE ? "SI" : "NO");
		}
		if (null != dto.getCartaDGTVehiculoMasDiezAn()) {
			baja.setDECLARACIONCARTADGT10ANIOS(dto.getCartaDGTVehiculoMasDiezAn() == Boolean.TRUE ? "SI" : "NO");
		}
		if (null != dto.getDeclaracionNoEntregaCatV()) {
			baja.setDECLARACIONNOENTREGACATV(dto.getDeclaracionNoEntregaCatV() == Boolean.TRUE ? "SI" : "NO");
		}
		if (null != dto.getCertificadoMedioAmbiental()) {
			baja.setCERTIFICADO_MEDIOAMBIENTAL(dto.getCertificadoMedioAmbiental() == Boolean.TRUE ? "SI" : "NO");
		}
		if (null != dto.getDeclaracionJuradaExtravio()) {
			baja.setCERTIFICADO_MEDIOAMBIENTAL(dto.getDeclaracionJuradaExtravio() == Boolean.TRUE ? "SI" : "NO");
		}
		baja.setSIMULTANEA(dto.getSimultanea() != null ? dto.getSimultanea() : new BigDecimal(-1));

		convertirDtoToGaVehiculo(baja, dto.getVehiculoDto());

		convertirDtoToGaTitular(baja, dto.getTitular());

		convertirDtoToGaRepresentanteTitular(baja, dto.getRepresentanteTitular());

		convertirDtoToGaAdquiriente(baja, dto.getAdquiriente());

		convertirDtoToGaRepresentanteAdquiriente(baja, dto.getRepresentanteAdquiriente());

		if (dto.getTasa() != null) {
			baja.getDATOSPAGOPRESENTACION().setCODIGOTASA(dto.getTasa().getCodigoTasa());
		}
		baja.getDATOSPAGOPRESENTACION().setJEFATURAPROVINCIAL(dto.getJefaturaTraficoDto().getJefaturaProvincial());
		baja.getDATOSPAGOPRESENTACION().setFECHAPRESENTACION(dto.getFechaPresentacion() != null ? dto.getFechaPresentacion().toString() : "");
		baja.getDATOSPAGOPRESENTACION().setCEMA(dto.getCema() != null ? dto.getCema().toString() : "");
		cambiarNullsPorVacios(baja);
		return baja;
	}

	private void convertirDtoToGaVehiculo(BAJA baja, VehiculoDto vehiculo) {
		if (vehiculo != null) {
			baja.getDATOSVEHICULO().setMATRICULA(vehiculo.getMatricula());
			baja.getDATOSVEHICULO().setNUMEROBASTIDOR(vehiculo.getBastidor());
			baja.getDATOSVEHICULO().setFECHAMATRICULACION(vehiculo.getFechaMatriculacion() != null ? vehiculo.getFechaMatriculacion().toString() : "");
			baja.getDATOSVEHICULO().setTIPOVEHICULO(vehiculo.getTipoVehiculo() != null ? vehiculo.getTipoVehiculo() : "");
			baja.getDATOSVEHICULO().setPESOMMA(vehiculo.getPesoMma() != null ? vehiculo.getPesoMma() : "");
			baja.getDATOSVEHICULO().setSERVICIO(vehiculo.getServicioTrafico() != null ? vehiculo.getServicioTrafico().getIdServicio() : "");

			if (vehiculo.getDireccion() != null) {
				baja.getDATOSVEHICULO().setPROVINCIAVEHICULO(vehiculo.getDireccion().getIdProvincia());
				baja.getDATOSVEHICULO().setMUNICIPIOVEHICULO(vehiculo.getDireccion().getIdMunicipio());
				baja.getDATOSVEHICULO().setPUEBLOVEHICULO(vehiculo.getDireccion().getPueblo());
				baja.getDATOSVEHICULO().setCODIGOPOSTALVEHICULO(vehiculo.getDireccion().getCodPostal());
				baja.getDATOSVEHICULO().setTIPOVIAVEHICULO(vehiculo.getDireccion().getIdTipoVia());
				baja.getDATOSVEHICULO().setCALLEDIRECCIONVEHICULO(vehiculo.getDireccion().getNombreVia());
				baja.getDATOSVEHICULO().setNUMERODIRECCIONVEHICULO(vehiculo.getDireccion().getNumero());
				baja.getDATOSVEHICULO().setLETRADIRECCIONVEHICULO(vehiculo.getDireccion().getLetra());
				baja.getDATOSVEHICULO().setESCALERADIRECCIONVEHICULO(vehiculo.getDireccion().getEscalera());
				baja.getDATOSVEHICULO().setPISODIRECCIONVEHICULO(vehiculo.getDireccion().getPlanta());
				baja.getDATOSVEHICULO().setPUERTADIRECCIONVEHICULO(vehiculo.getDireccion().getPuerta());
				baja.getDATOSVEHICULO().setBLOQUEDIRECCIONVEHICULO(vehiculo.getDireccion().getBloque());
				if (vehiculo.getDireccion().getKm() != null) {
					baja.getDATOSVEHICULO().setKMDIRECCIONVEHICULO(vehiculo.getDireccion().getKm().toString());
				}
				if (vehiculo.getDireccion().getHm() != null) {
					baja.getDATOSVEHICULO().setHMDIRECCIONVEHICULO(vehiculo.getDireccion().getHm().toString());
				}
			}
		}
	}

	private void convertirDtoToGaTitular(BAJA baja, IntervinienteTraficoDto titular) {
		if (titular != null) {
			baja.getDATOSTITULAR().setDNITITULAR(titular.getPersona().getNif());
			baja.getDATOSTITULAR().setSEXOTITULAR(titular.getPersona().getSexo());
			baja.getDATOSTITULAR().setAPELLIDO1RAZONSOCIALTITULAR(titular.getPersona().getApellido1RazonSocial());
			baja.getDATOSTITULAR().setAPELLIDO2TITULAR(titular.getPersona().getApellido2());
			baja.getDATOSTITULAR().setNOMBRETITULAR(titular.getPersona().getNombre());
			if (titular.getDireccion() != null) {
				baja.getDATOSTITULAR().setPROVINCIATITULAR(titular.getDireccion().getIdProvincia());
				baja.getDATOSTITULAR().setMUNICIPIOTITULAR(titular.getDireccion().getIdMunicipio());
				baja.getDATOSTITULAR().setPUEBLOTITULAR(titular.getDireccion().getPueblo());
				baja.getDATOSTITULAR().setCODIGOPOSTALTITULAR(titular.getDireccion().getCodPostal());
				baja.getDATOSTITULAR().setTIPOVIATITULAR(titular.getDireccion().getIdTipoVia());
				baja.getDATOSTITULAR().setCALLEDIRECCIONTITULAR(titular.getDireccion().getNombreVia());
				baja.getDATOSTITULAR().setNUMERODIRECCIONTITULAR(titular.getDireccion().getNumero());
				baja.getDATOSTITULAR().setLETRADIRECCIONTITULAR(titular.getDireccion().getLetra());
				baja.getDATOSTITULAR().setESCALERADIRECCIONTITULAR(titular.getDireccion().getEscalera());
				baja.getDATOSTITULAR().setPISODIRECCIONTITULAR(titular.getDireccion().getPlanta());
				baja.getDATOSTITULAR().setPUERTADIRECCIONTITULAR(titular.getDireccion().getPuerta());
				baja.getDATOSTITULAR().setBLOQUEDIRECCIONTITULAR(titular.getDireccion().getBloque());

				if (titular.getDireccion().getKm() != null) {
					baja.getDATOSTITULAR().setKMDIRECCIONTITULAR(titular.getDireccion().getKm().toString());
				}
				if (titular.getDireccion().getHm() != null) {
					baja.getDATOSTITULAR().setHMDIRECCIONTITULAR(titular.getDireccion().getHm().toString());
				}
			}
		}
	}

	private void convertirDtoToGaRepresentanteTitular(BAJA baja, IntervinienteTraficoDto representanteTitular) {
		if (representanteTitular != null) {
			baja.getDATOSREPRESENTANTE().setDNIREPRESENTANTE(representanteTitular.getPersona().getNif());
			baja.getDATOSREPRESENTANTE().setSEXOREPRESENTANTE(representanteTitular.getPersona().getSexo());
			baja.getDATOSREPRESENTANTE().setAPELLIDO1RAZONSOCIALREPRESENTANTE(representanteTitular.getPersona().getApellido1RazonSocial());
			baja.getDATOSREPRESENTANTE().setAPELLIDO2REPRESENTANTE(representanteTitular.getPersona().getApellido2());
			baja.getDATOSREPRESENTANTE().setNOMBREREPRESENTANTE(representanteTitular.getPersona().getNombre());
			baja.getDATOSREPRESENTANTE().setCONCEPTOREPRESENTANTE(representanteTitular.getConceptoRepre() != null ? representanteTitular.getConceptoRepre() : "");
		}
	}

	private void convertirDtoToGaAdquiriente(BAJA baja, IntervinienteTraficoDto adquiriente) {
		if (adquiriente != null) {
			baja.getDATOSADQUIRIENTE().setDNIADQUIRIENTE(adquiriente.getPersona().getNif());
			baja.getDATOSADQUIRIENTE().setSEXOADQUIRIENTE(adquiriente.getPersona().getSexo());
			baja.getDATOSADQUIRIENTE().setAPELLIDO1RAZONSOCIALADQUIRIENTE(adquiriente.getPersona().getApellido1RazonSocial());
			baja.getDATOSADQUIRIENTE().setAPELLIDO2ADQUIRIENTE(adquiriente.getPersona().getApellido2());
			baja.getDATOSADQUIRIENTE().setNOMBREADQUIRIENTE(adquiriente.getPersona().getNombre());
			baja.getDATOSADQUIRIENTE().setFECHANACIMIENTOADQUIRIENTE(adquiriente.getPersona().getFechaNacimiento() != null ? adquiriente.getPersona().getFechaNacimiento().toString() : "");
			if (adquiriente.getDireccion() != null) {
				baja.getDATOSADQUIRIENTE().setPROVINCIAADQUIRIENTE(adquiriente.getDireccion().getIdProvincia());
				baja.getDATOSADQUIRIENTE().setMUNICIPIOADQUIRIENTE(adquiriente.getDireccion().getIdMunicipio());
				baja.getDATOSADQUIRIENTE().setPUEBLOADQUIRIENTE(adquiriente.getDireccion().getPueblo());
				baja.getDATOSADQUIRIENTE().setCODIGOPOSTALADQUIRIENTE(adquiriente.getDireccion().getCodPostal());
				baja.getDATOSADQUIRIENTE().setTIPOVIAADQUIRIENTE(adquiriente.getDireccion().getIdTipoVia());
				baja.getDATOSADQUIRIENTE().setCALLEDIRECCIONADQUIRIENTE(adquiriente.getDireccion().getNombreVia());
				baja.getDATOSADQUIRIENTE().setNUMERODIRECCIONADQUIRIENTE(adquiriente.getDireccion().getNumero());
				baja.getDATOSADQUIRIENTE().setLETRADIRECCIONADQUIRIENTE(adquiriente.getDireccion().getLetra());
				baja.getDATOSADQUIRIENTE().setESCALERADIRECCIONADQUIRIENTE(adquiriente.getDireccion().getEscalera());
				baja.getDATOSADQUIRIENTE().setPISODIRECCIONADQUIRIENTE(adquiriente.getDireccion().getPlanta());
				baja.getDATOSADQUIRIENTE().setPUERTADIRECCIONADQUIRIENTE(adquiriente.getDireccion().getPuerta());
				baja.getDATOSADQUIRIENTE().setBLOQUEDIRECCIONADQUIRIENTE(adquiriente.getDireccion().getBloque());
				if (adquiriente.getDireccion().getKm() != null) {
					baja.getDATOSADQUIRIENTE().setKMDIRECCIONADQUIRIENTE(adquiriente.getDireccion().getKm().toString());
				}
				if (adquiriente.getDireccion().getHm() != null) {
					baja.getDATOSADQUIRIENTE().setHMDIRECCIONADQUIRIENTE(adquiriente.getDireccion().getHm().toString());
				}

			}
		}
	}

	private void convertirDtoToGaRepresentanteAdquiriente(BAJA baja, IntervinienteTraficoDto representanteAdquiriente) {
		if (representanteAdquiriente != null) {
			baja.getDATOSREPRESENTANTEADQUIRIENTE().setDNIREPRESENTANTEADQUIRIENTE(representanteAdquiriente.getPersona().getNif());
			baja.getDATOSREPRESENTANTEADQUIRIENTE().setSEXOREPRESENTANTEADQUIRIENTE(representanteAdquiriente.getPersona().getSexo());
			baja.getDATOSREPRESENTANTEADQUIRIENTE().setAPELLIDO1RAZONSOCIALREPRESENTANTEADQUIRIENTE(representanteAdquiriente.getPersona().getApellido1RazonSocial());
			baja.getDATOSREPRESENTANTEADQUIRIENTE().setAPELLIDO2REPRESENTANTEADQUIRIENTE(representanteAdquiriente.getPersona().getApellido2());
			baja.getDATOSREPRESENTANTEADQUIRIENTE().setNOMBREREPRESENTANTEADQUIRIENTE(representanteAdquiriente.getPersona().getNombre());
			baja.getDATOSREPRESENTANTEADQUIRIENTE().setCONCEPTOREPRESENTANTEADQUIRIENTE(representanteAdquiriente.getConceptoRepre() != null ? representanteAdquiriente.getConceptoRepre() : "");
		}

	}

	public String getFechaHoy() {
		java.util.Date date = new java.util.Date();
		java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("dd/MM/yyyy");
		String fecha = sdf.format(date);
		return fecha;
	}

	private void cambiarNullsPorVacios(Object objeto) {
		if (objeto == null || "".equals(objeto))
			return;
		/*
		 * Saco una lista de nombres de métodos de la clase en cuestión, para luego poder ver si un método pertenece a la clase sin usar getMethod, que me puede dar una noNuschMethodException
		 */
		ArrayList<String> methodList = new ArrayList<String>();
		if (objeto != null && objeto.getClass() != null) {
			for (int i = 0; i < objeto.getClass().getDeclaredMethods().length; i++) {
				methodList.add(objeto.getClass().getDeclaredMethods()[i].getName());
			}
		}

		Field[] campos = objeto.getClass().getDeclaredFields();
		for (int i = 0; i < campos.length; i++) {
			String parametro = campos[i].toString();
			String[] separar = parametro.split("\\.");
			parametro = separar[separar.length - 1];
			Class clase = objeto.getClass() != String.class && objeto.getClass() != BigDecimal.class && objeto.getClass() != Timestamp.class && objeto.getClass() != Boolean.class
					? dameClasedeParametro(objeto, parametro) : null;
			if (clase == String.class) {
				try {
					Method metodoGet = objeto != null && objeto.getClass() != null && methodList.contains("get" + parametro.toUpperCase()) ? objeto.getClass().getMethod(
							"get" + parametro.toUpperCase()) : null;
					if (metodoGet != null && (metodoGet.invoke(objeto, new Object[0]) == null || "-1".equals(metodoGet.invoke(objeto, new Object[0])))) {
						Method metodoSet = objeto.getClass().getMethod("set" + parametro.toUpperCase(), String.class);
						metodoSet.invoke(objeto, "");
					}
				} catch (Exception e) {

					try {
						Method metodoGet = objeto != null && objeto.getClass() != null && methodList.contains("get" + parametro.substring(0, 1).toUpperCase() + parametro.substring(1)) ? objeto
								.getClass().getMethod("get" + parametro.substring(0, 1).toUpperCase() + parametro.substring(1)) : null;
						if (metodoGet != null && (metodoGet.invoke(objeto, new Object[0]) == null || "-1".equals(metodoGet.invoke(objeto, new Object[0])))) {
							Method metodoSet = objeto.getClass().getMethod("set" + parametro.substring(0, 1).toUpperCase() + parametro.substring(1), String.class);
							metodoSet.invoke(objeto, "");
						}
					} catch (Exception e1) {
						log.error(e1);
					}
				}
			} else {
				try {
					Method metodoGet = objeto != null && objeto.getClass() != null && methodList.contains("get" + parametro.toUpperCase()) ? objeto.getClass().getMethod(
							"get" + parametro.toUpperCase()) : null;
					if (metodoGet != null) {
						cambiarNullsPorVacios(metodoGet.invoke(objeto, new Object[0]));
					}
				} catch (Exception e) {
					log.error(e);
					Method metodoGet;
					try {
						metodoGet = objeto != null && objeto.getClass() != null && methodList.contains("get" + parametro.substring(0, 1).toUpperCase() + parametro.substring(1)) ? objeto.getClass()
								.getMethod("get" + parametro.substring(0, 1).toUpperCase() + parametro.substring(1)) : null;
						if (metodoGet != null) {
							cambiarNullsPorVacios(metodoGet.invoke(objeto, new Object[0]));
						}
					} catch (Exception e1) {
						log.error(e1);
					}

				}
			}
		}
	}

	private void cambiarNullsPorFalsesOVacios(Object objeto) {
		if (objeto == null || "".equals(objeto))
			return;
		/*
		 * Saco una lista de nombres de métodos de la clase en cuestión, para luego poder ver si un método pertenece a la clase sin usar getMethod, que me puede dar una noNuschMethodException
		 */
		ArrayList<String> methodList = new ArrayList<String>();
		if (objeto != null && objeto.getClass() != null) {
			for (int i = 0; i < objeto.getClass().getDeclaredMethods().length; i++) {
				methodList.add(objeto.getClass().getDeclaredMethods()[i].getName());
			}
		}

		Field[] campos = objeto.getClass().getDeclaredFields();
		for (int i = 0; i < campos.length; i++) {
			String parametro = campos[i].toString();
			String[] separar = parametro.split("\\.");
			parametro = separar[separar.length - 1];
			Class clase = dameClasedeParametroBean(objeto, parametro);
			if (clase == Boolean.class) {
				try {
					Method metodoGet = objeto != null && objeto.getClass() != null && methodList.contains("get" + parametro.toUpperCase()) ? objeto.getClass().getMethod(
							"get" + parametro.toUpperCase()) : null;
					if (metodoGet != null && metodoGet.invoke(objeto, new Object[0]) == null) {
						Method metodoSet = objeto.getClass().getMethod("set" + parametro.toUpperCase(), Boolean.class);
						metodoSet.invoke(objeto, false);
					}
				} catch (Exception e) {

					try {
						Method metodoGet = objeto != null && objeto.getClass() != null && methodList.contains("get" + parametro.substring(0, 1).toUpperCase() + parametro.substring(1)) ? objeto
								.getClass().getMethod("get" + parametro.substring(0, 1).toUpperCase() + parametro.substring(1)) : null;
						if (metodoGet != null && metodoGet.invoke(objeto, new Object[0]) == null) {
							Method metodoSet = objeto.getClass().getMethod("set" + parametro.substring(0, 1).toUpperCase() + parametro.substring(1), Boolean.class);
							metodoSet.invoke(objeto, false);
						}
					} catch (Exception e1) {
						log.error(e1);
					}
				}
			} else if (clase == String.class) {
				try {
					Method metodoGet = objeto != null && objeto.getClass() != null && methodList.contains("get" + parametro.toUpperCase()) ? objeto.getClass().getMethod(
							"get" + parametro.toUpperCase()) : null;
					if (metodoGet != null && metodoGet.invoke(objeto, new Object[0]) == null) {
						Method metodoSet = objeto.getClass().getMethod("set" + parametro.toUpperCase(), String.class);
						metodoSet.invoke(objeto, "");
					}
				} catch (Exception e) {

					try {
						Method metodoGet = objeto != null && objeto.getClass() != null && methodList.contains("get" + parametro.substring(0, 1).toUpperCase() + parametro.substring(1)) ? objeto
								.getClass().getMethod("get" + parametro.substring(0, 1).toUpperCase() + parametro.substring(1)) : null;
						if (metodoGet != null && metodoGet.invoke(objeto, new Object[0]) == null) {
							Method metodoSet = objeto.getClass().getMethod("set" + parametro.substring(0, 1).toUpperCase() + parametro.substring(1), String.class);
							metodoSet.invoke(objeto, "");
						}
					} catch (Exception e1) {
						log.error(e1);
					}
				}
			} else {
				try {
					Method metodoGet = objeto != null && objeto.getClass() != null && methodList.contains("get" + parametro.toUpperCase()) ? objeto.getClass().getMethod(
							"get" + parametro.toUpperCase()) : null;
					if (metodoGet != null) {
						cambiarNullsPorVacios(metodoGet.invoke(objeto, new Object[0]));
					}
				} catch (Exception e) {
					log.error(e);
					Method metodoGet;
					try {
						metodoGet = objeto != null && objeto.getClass() != null && methodList.contains("get" + parametro.substring(0, 1).toUpperCase() + parametro.substring(1)) ? objeto.getClass()
								.getMethod("get" + parametro.substring(0, 1).toUpperCase() + parametro.substring(1)) : null;
						if (metodoGet != null) {
							cambiarNullsPorVacios(metodoGet.invoke(objeto, new Object[0]));
						}
					} catch (Exception e1) {
						log.error(e1);
					}
				}
			}
		}
	}

	private Class<?> dameClasedeParametro(Object bean, String nombreParametro) {
		Object value;
		Object entidad;
		try {
			entidad = bean.getClass().newInstance();
		} catch (Exception e) {
			log.error(e);
			return null;
		}

		/*
		 * Saco una lista de nombres de métodos de la clase en cuestión, para luego poder ver si un método pertenece a la clase sin usar getMethod, que me puede dar una noNuschMethodException
		 */
		ArrayList<String> methodList = new ArrayList<String>();
		if (entidad != null && entidad.getClass() != null) {
			for (int i = 0; i < entidad.getClass().getDeclaredMethods().length; i++) {
				methodList.add(entidad.getClass().getDeclaredMethods()[i].getName());
			}
		}

		try {
			Method getter = null;
			try {
				getter = entidad != null && entidad.getClass() != null && methodList.contains("get" + String.valueOf(nombreParametro).toUpperCase()) ? entidad.getClass().getMethod(
						"get" + String.valueOf(nombreParametro).toUpperCase()) : null;
			} catch (Exception e) {
				log.error(e);
			}
			value = getter != null ? getter.invoke(entidad, new Object[0]) : null;
			// Si el objeto obtenido es NULL, intentamos setearle uno de los parametros esperados
			if (value == null && getter != null) {
				Method setter = methodList.contains("set" + String.valueOf(nombreParametro).toUpperCase()) ? entidad.getClass().getMethod("set" + String.valueOf(nombreParametro).toUpperCase(),
						String.class) : null;
				if (setter != null)
					setter.invoke(entidad, new String(""));
				getter = methodList.contains("get" + String.valueOf(nombreParametro).toUpperCase()) ? entidad.getClass().getMethod("get" + String.valueOf(nombreParametro).toUpperCase()) : null;
				value = getter != null ? getter.invoke(entidad, new Object[0]) : null;
			}
			// System.out.println("class:"+value.getClass());
			entidad = null;
			return value != null ? value.getClass() : null;
		} catch (Exception ex) {
			try {
				Method setter = methodList.contains("set" + String.valueOf(nombreParametro)) ? entidad.getClass().getMethod("set" + String.valueOf(nombreParametro), BigDecimal.class) : null;
				if (setter != null)
					setter.invoke(entidad, new BigDecimal(0));
				Method getter = methodList.contains("get" + String.valueOf(nombreParametro)) ? entidad.getClass().getMethod("get" + String.valueOf(nombreParametro)) : null;
				value = getter != null ? getter.invoke(entidad, new Object[0]) : null;
				// System.out.println("class:"+value.getClass());
				entidad = null;
				return value != null ? value.getClass() : null;
			} catch (Exception ex2) {
				try {
					Method setter = methodList.contains("set" + String.valueOf(nombreParametro)) ? entidad.getClass().getMethod("set" + String.valueOf(nombreParametro), Timestamp.class) : null;
					if (setter != null)
						setter.invoke(entidad, Timestamp.valueOf("2000-01-01 00:00:00"));
					Method getter = methodList.contains("get" + String.valueOf(nombreParametro)) ? entidad.getClass().getMethod("get" + String.valueOf(nombreParametro)) : null;
					value = getter != null ? getter.invoke(entidad, new Object[0]) : null;
					// System.out.println("class:"+value.getClass());
					entidad = null;
					return value != null ? value.getClass() : null;
				} catch (Exception ex3) {
					try {
						Method setter = methodList.contains("set" + String.valueOf(nombreParametro)) ? entidad.getClass().getMethod("set" + String.valueOf(nombreParametro), Boolean.class) : null;
						if (setter != null)
							setter.invoke(entidad, false);
						Method getter = methodList.contains("get" + String.valueOf(nombreParametro)) ? entidad.getClass().getMethod("get" + String.valueOf(nombreParametro)) : null;
						value = getter != null ? getter.invoke(entidad, new Object[0]) : null;
						// System.out.println("class:"+value.getClass());
						entidad = null;
						return value != null ? value.getClass() : null;
					} catch (Exception ex4) {
						log.error(ex4);
						log.debug("El parametro " + nombreParametro + " no tiene una clase conocida (String, BigDecimal, Timestamp)");
						entidad = null;
						return null;
					}
				}
			}
		}
	}

	private Class<?> dameClasedeParametroBean(Object bean, String nombreParametro) {
		Object value;
		Object entidad;
		try {
			entidad = bean.getClass().newInstance();
		} catch (Exception e) {
			log.error(e);
			return null;
		}

		/*
		 * Saco una lista de nombres de métodos de la clase en cuestión, para luego poder ver si un método pertenece a la clase sin usar getMethod, que me puede dar una noNuschMethodException
		 */
		ArrayList<String> methodList = new ArrayList<String>();
		if (entidad != null && entidad.getClass() != null) {
			for (int i = 0; i < entidad.getClass().getDeclaredMethods().length; i++) {
				methodList.add(entidad.getClass().getDeclaredMethods()[i].getName());
			}
		}

		try {
			Method getter = null;
			try {
				getter = entidad != null && entidad.getClass() != null && methodList.contains("get" + nombreParametro.substring(0, 1).toUpperCase() + nombreParametro.substring(1)) ? entidad
						.getClass().getMethod("get" + nombreParametro.substring(0, 1).toUpperCase() + nombreParametro.substring(1)) : null;
			} catch (Exception e) {
				log.error(e);
			}
			value = getter != null ? getter.invoke(entidad, new Object[0]) : null;
			// Si el objeto obtenido es NULL, intentamos setearle uno de los parametros esperados
			if (value == null && getter != null) {
				Method setter = methodList.contains("set" + nombreParametro.substring(0, 1).toUpperCase() + nombreParametro.substring(1)) ? entidad.getClass().getMethod(
						"set" + nombreParametro.substring(0, 1).toUpperCase() + nombreParametro.substring(1), String.class) : null;
				if (setter != null)
					setter.invoke(entidad, new String(""));
				getter = methodList.contains("get" + nombreParametro.substring(0, 1).toUpperCase() + nombreParametro.substring(1)) ? entidad.getClass().getMethod(
						"get" + nombreParametro.substring(0, 1).toUpperCase() + nombreParametro.substring(1)) : null;
				value = getter != null ? getter.invoke(entidad, new Object[0]) : null;
			}
			// log.debug("class:"+value.getClass());
			entidad = null;
			return value != null ? value.getClass() : null;
		} catch (Exception ex) {
			try {
				Method setter = methodList.contains("set" + nombreParametro.substring(0, 1).toUpperCase() + nombreParametro.substring(1)) ? entidad.getClass().getMethod(
						"set" + nombreParametro.substring(0, 1).toUpperCase() + nombreParametro.substring(1), BigDecimal.class) : null;
				if (setter != null)
					setter.invoke(entidad, new BigDecimal(0));
				Method getter = methodList.contains("get" + nombreParametro.substring(0, 1).toUpperCase() + nombreParametro.substring(1)) ? entidad.getClass().getMethod(
						"get" + nombreParametro.substring(0, 1).toUpperCase() + nombreParametro.substring(1)) : null;
				value = getter != null ? getter.invoke(entidad, new Object[0]) : null;
				// log.debug("class:"+value.getClass());
				entidad = null;
				return value != null ? value.getClass() : null;
			} catch (Exception ex2) {
				try {
					Method setter = methodList.contains("set" + nombreParametro.substring(0, 1).toUpperCase() + nombreParametro.substring(1)) ? entidad.getClass().getMethod(
							"set" + nombreParametro.substring(0, 1).toUpperCase() + nombreParametro.substring(1), Timestamp.class) : null;
					if (setter != null)
						setter.invoke(entidad, Timestamp.valueOf("2000-01-01 00:00:00"));
					Method getter = methodList.contains("get" + nombreParametro.substring(0, 1).toUpperCase() + nombreParametro.substring(1)) ? entidad.getClass().getMethod(
							"get" + nombreParametro.substring(0, 1).toUpperCase() + nombreParametro.substring(1)) : null;
					value = getter != null ? getter.invoke(entidad, new Object[0]) : null;
					// log.debug("class:"+value.getClass());
					entidad = null;
					return value != null ? value.getClass() : null;
				} catch (Exception ex3) {
					try {
						Method setter = methodList.contains("set" + nombreParametro.substring(0, 1).toUpperCase() + nombreParametro.substring(1)) ? entidad.getClass().getMethod(
								"set" + nombreParametro.substring(0, 1).toUpperCase() + nombreParametro.substring(1), Boolean.class) : null;
						if (setter != null)
							setter.invoke(entidad, false);
						Method getter = methodList.contains("get" + nombreParametro.substring(0, 1).toUpperCase() + nombreParametro.substring(1)) ? entidad.getClass().getMethod(
								"get" + nombreParametro.substring(0, 1).toUpperCase() + nombreParametro.substring(1)) : null;
						value = getter != null ? getter.invoke(entidad, new Object[0]) : null;
						// log.debug("class:"+value.getClass());
						entidad = null;
						return value != null ? value.getClass() : null;
					} catch (Exception ex4) {
						// log.error(ex4);
						log.debug(Class.class + "XMLBaja: El parametro " + nombreParametro + " no tiene una clase conocida (String, BigDecimal, Timestamp)");
						entidad = null;
						return null;
					}
				}
			}
		}
	}
}
