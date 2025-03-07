package trafico.beans.utiles;

import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;

public class XMLBajaCustodia {
	
	private static ILoggerOegam log = LoggerOegam.getLogger(XMLBajaCustodia.class);
	
	/*public FORMATOOEGAM2BAJA convertirAFORMATOGA(TramiteTraficoBajaBean baja) {
		FORMATOOEGAM2BAJA ga = instanciarCompleto();
		
		//Añado Cabecera
		ga.getCABECERA().getDATOSGESTORIA().setNIF(utilesColegiado.getUsuarioDeSesion().getNif());
		ga.getCABECERA().getDATOSGESTORIA().setNOMBRE(utilesColegiado.getNombreApellidosUsuario().replace(",",""));
		ga.getCABECERA().getDATOSGESTORIA().setPROFESIONAL(utilesColegiado.getNumColegiado());
		ga.getCABECERA().getDATOSGESTORIA().setPROVINCIA(utils.getSiglaProvinciaFromNombre(utilesColegiado.getProvinciaContratoSesion()));
		//Añado Fecha de creación
		ga.setFechaCreacion(getFechaHoy());
		
		List<BAJA> lista = ga.getBAJA();
		//Añado los trámites
		lista.add(convertirBeanToGa(baja));
		
		ga.setBAJA(lista);
		return ga;
	}
	*/
	
	/**
	 * 
	 * @return LIMPIEZA BAJAS ANTIGUAS
	 
	private FORMATOOEGAM2BAJA instanciarCompleto() {
		ObjectFactory factory = new ObjectFactory();
		FORMATOOEGAM2BAJA ga = factory.createFORMATOOEGAM2BAJA();
		
		//CABECERA
		ga.setCABECERA(factory.createCABECERA());
		ga.getCABECERA().setDATOSGESTORIA(factory.createDATOSGESTORIA());
		ga.getCABECERA().getDATOSGESTORIA().setNIF("");
		ga.getCABECERA().getDATOSGESTORIA().setNOMBRE("");
		ga.getCABECERA().getDATOSGESTORIA().setPROFESIONAL("");
		ga.getCABECERA().getDATOSGESTORIA().setPROVINCIA("");
		
		ga.setFechaCreacion("");
		ga.setPlataforma("OEGAM");
		
		ga.setBAJA(new ArrayList<BAJA>());
		ga.setFIRMA(factory.createFIRMA());
		return ga;
	}
	
	private BAJA instanciarBAJA() {
		ObjectFactory factory = new ObjectFactory();
		BAJA baja = factory.createBAJA();
		
		baja.setNUMEROEXPEDIENTE("");
		baja.setREFERENCIAPROPIA("");
		baja.setPROFESIONAL("");
		baja.setFECHACREACION("");
		baja.setOBSERVACIONES("");
		baja.setMOTIVOBAJA("");
		baja.setPERMISOCIRCULACION("");
		baja.setTARJETAINSPECCIONTECNICA("");
		baja.setBAJAIMVTM("");
		baja.setJUSTIFICANTEDENUNCIA("");
		baja.setDOCUMENTOPROPIEDAD("");
		baja.setJUSTIFICANTEIMVTM("");
		
		//VEHICULO		
		baja.setDATOSVEHICULO(factory.createDATOSVEHICULO());
		baja.getDATOSVEHICULO().setMATRICULA("");
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
		
		//TITULAR
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
		
		//REPRESENTANTE
		baja.setDATOSREPRESENTANTE(factory.createDATOSREPRESENTANTE());		
		baja.getDATOSREPRESENTANTE().setDNIREPRESENTANTE("");
		baja.getDATOSREPRESENTANTE().setSEXOREPRESENTANTE("");
		baja.getDATOSREPRESENTANTE().setAPELLIDO1RAZONSOCIALREPRESENTANTE("");
		baja.getDATOSREPRESENTANTE().setAPELLIDO2REPRESENTANTE("");
		baja.getDATOSREPRESENTANTE().setNOMBREREPRESENTANTE("");				
		baja.getDATOSREPRESENTANTE().setCONCEPTOREPRESENTANTE("");
		
		//ADQUIRIENTE
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
		
		//PAGO PRESENTACION
		baja.setDATOSPAGOPRESENTACION(factory.createDATOSPAGOPRESENTACION());
		baja.getDATOSPAGOPRESENTACION().setCODIGOTASA("");
		baja.getDATOSPAGOPRESENTACION().setJEFATURAPROVINCIAL("");
		baja.getDATOSPAGOPRESENTACION().setFECHAPRESENTACION("");
		
		return baja;
	}
	*/
	
	/**
	 * 
	 * @param bean
	 * @return LIMPIEZA BAJAS ANTIGUAS
	 
	private BAJA convertirBeanToGa(TramiteTraficoBajaBean bean) {
		BAJA baja = instanciarBAJA();

		cambiarNullsPorFalsesOVacios(bean);
		
		//****************************************************************************************************************
		// * RELLENAMOS EL FORMATOGA CON LOS DATOS QUE TENGAMOS EN EL BEAN DE PANTALLA ***********************************
		// 
		baja.setNUMEROEXPEDIENTE(bean.getTramiteTrafico().getNumExpediente().toString());
		baja.setREFERENCIAPROPIA(bean.getTramiteTrafico().getReferenciaPropia());
		baja.setPROFESIONAL(bean.getTramiteTrafico().getNumColegiado());
		baja.setFECHACREACION(bean.getTramiteTrafico().getFechaCreacion()!=null?bean.getTramiteTrafico().getFechaCreacion().toString():"");
		baja.setOBSERVACIONES(bean.getTramiteTrafico().getAnotaciones());
		baja.setMOTIVOBAJA(bean.getMotivoBaja().getValorEnum());
		baja.setPERMISOCIRCULACION(true==bean.getPermisoCirculacion()?"SI":"NO");
		baja.setTARJETAINSPECCIONTECNICA(true==bean.getTarjetaInspeccion()?"SI":"NO");
		baja.setBAJAIMVTM(true==bean.getBajaImpuestoMunicipal()?"SI":"NO");
		baja.setJUSTIFICANTEDENUNCIA(true==bean.getJustificanteDenuncia()?"SI":"NO");
		baja.setDOCUMENTOPROPIEDAD(true==bean.getPropiedadDesguace()?"SI":"NO");
		baja.setJUSTIFICANTEIMVTM(true==bean.getPagoImpuestoMunicipal()?"SI":"NO");
		
		//VEHICULO
		VehiculoBean vehi = bean.getTramiteTrafico().getVehiculo();
		baja.getDATOSVEHICULO().setMATRICULA(vehi.getMatricula());
		baja.getDATOSVEHICULO().setFECHAMATRICULACION(vehi.getFechaMatriculacion()!=null?vehi.getFechaMatriculacion().toString():"");
		if (vehi.getDireccionBean()!=null) {
			baja.getDATOSVEHICULO().setPROVINCIAVEHICULO(vehi.getDireccionBean().getMunicipio().getProvincia().getIdProvincia());
			baja.getDATOSVEHICULO().setMUNICIPIOVEHICULO(vehi.getDireccionBean().getMunicipio().getIdMunicipio());
			baja.getDATOSVEHICULO().setPUEBLOVEHICULO(vehi.getDireccionBean().getPueblo());
			baja.getDATOSVEHICULO().setCODIGOPOSTALVEHICULO(vehi.getDireccionBean().getCodPostal());
			baja.getDATOSVEHICULO().setTIPOVIAVEHICULO(vehi.getDireccionBean().getTipoVia().getIdTipoVia());
			baja.getDATOSVEHICULO().setCALLEDIRECCIONVEHICULO(vehi.getDireccionBean().getNombreVia());
			baja.getDATOSVEHICULO().setNUMERODIRECCIONVEHICULO(vehi.getDireccionBean().getNumero());
			baja.getDATOSVEHICULO().setLETRADIRECCIONVEHICULO(vehi.getDireccionBean().getLetra());
			baja.getDATOSVEHICULO().setESCALERADIRECCIONVEHICULO(vehi.getDireccionBean().getEscalera());
			baja.getDATOSVEHICULO().setPISODIRECCIONVEHICULO(vehi.getDireccionBean().getPlanta());
			baja.getDATOSVEHICULO().setPUERTADIRECCIONVEHICULO(vehi.getDireccionBean().getPuerta());
			baja.getDATOSVEHICULO().setBLOQUEDIRECCIONVEHICULO(vehi.getDireccionBean().getBloque());
			baja.getDATOSVEHICULO().setKMDIRECCIONVEHICULO(vehi.getDireccionBean().getPuntoKilometrico());
			baja.getDATOSVEHICULO().setHMDIRECCIONVEHICULO(vehi.getDireccionBean().getHm());
		}
		
		//TITULAR
		IntervinienteTrafico titular = bean.getTitular();
		baja.getDATOSTITULAR().setDNITITULAR(titular.getPersona().getNif());
		baja.getDATOSTITULAR().setSEXOTITULAR(titular.getPersona().getSexo());
		baja.getDATOSTITULAR().setAPELLIDO1RAZONSOCIALTITULAR(titular.getPersona().getApellido1RazonSocial());
		baja.getDATOSTITULAR().setAPELLIDO2TITULAR(titular.getPersona().getApellido2());
		baja.getDATOSTITULAR().setNOMBRETITULAR(titular.getPersona().getNombre());
		if (titular.getPersona().getDireccion()!=null) {
			baja.getDATOSTITULAR().setPROVINCIATITULAR(titular.getPersona().getDireccion().getMunicipio().getProvincia().getIdProvincia());
			baja.getDATOSTITULAR().setMUNICIPIOTITULAR(titular.getPersona().getDireccion().getMunicipio().getIdMunicipio());
			baja.getDATOSTITULAR().setPUEBLOTITULAR(titular.getPersona().getDireccion().getPueblo());
			baja.getDATOSTITULAR().setCODIGOPOSTALTITULAR(titular.getPersona().getDireccion().getCodPostal());
			baja.getDATOSTITULAR().setTIPOVIATITULAR(titular.getPersona().getDireccion().getTipoVia().getIdTipoVia());
			baja.getDATOSTITULAR().setCALLEDIRECCIONTITULAR(titular.getPersona().getDireccion().getNombreVia());
			baja.getDATOSTITULAR().setNUMERODIRECCIONTITULAR(titular.getPersona().getDireccion().getNumero());
			baja.getDATOSTITULAR().setLETRADIRECCIONTITULAR(titular.getPersona().getDireccion().getLetra());
			baja.getDATOSTITULAR().setESCALERADIRECCIONTITULAR(titular.getPersona().getDireccion().getEscalera());
			baja.getDATOSTITULAR().setPISODIRECCIONTITULAR(titular.getPersona().getDireccion().getPlanta());
			baja.getDATOSTITULAR().setPUERTADIRECCIONTITULAR(titular.getPersona().getDireccion().getPuerta());
			baja.getDATOSTITULAR().setBLOQUEDIRECCIONTITULAR(titular.getPersona().getDireccion().getBloque());
			baja.getDATOSTITULAR().setKMDIRECCIONTITULAR(titular.getPersona().getDireccion().getPuntoKilometrico());
			baja.getDATOSTITULAR().setHMDIRECCIONTITULAR(titular.getPersona().getDireccion().getHm());
		}
		
		//REPRESENTANTE
		IntervinienteTrafico repre = bean.getRepresentanteTitular();
		baja.getDATOSREPRESENTANTE().setDNIREPRESENTANTE(repre.getPersona().getNif());
		baja.getDATOSREPRESENTANTE().setSEXOREPRESENTANTE(repre.getPersona().getSexo());
		baja.getDATOSREPRESENTANTE().setAPELLIDO1RAZONSOCIALREPRESENTANTE(repre.getPersona().getApellido1RazonSocial());
		baja.getDATOSREPRESENTANTE().setAPELLIDO2REPRESENTANTE(repre.getPersona().getApellido2());
		baja.getDATOSREPRESENTANTE().setNOMBREREPRESENTANTE(repre.getPersona().getNombre());
		if(repre.getConceptoRepre()!=null){
			baja.getDATOSREPRESENTANTE().setCONCEPTOREPRESENTANTE(repre.getConceptoRepre().getValorEnum());
		}else{
			baja.getDATOSREPRESENTANTE().setCONCEPTOREPRESENTANTE("");
		}
		
		//ADQUIRIENTE
		IntervinienteTrafico adqui = bean.getAdquiriente();
		baja.getDATOSADQUIRIENTE().setDNIADQUIRIENTE(adqui.getPersona().getNif());
		baja.getDATOSADQUIRIENTE().setSEXOADQUIRIENTE(adqui.getPersona().getSexo());
		baja.getDATOSADQUIRIENTE().setAPELLIDO1RAZONSOCIALADQUIRIENTE(adqui.getPersona().getApellido1RazonSocial());
		baja.getDATOSADQUIRIENTE().setAPELLIDO2ADQUIRIENTE(adqui.getPersona().getApellido2());
		baja.getDATOSADQUIRIENTE().setNOMBREADQUIRIENTE(adqui.getPersona().getNombre());
		baja.getDATOSADQUIRIENTE().setFECHANACIMIENTOADQUIRIENTE(adqui.getPersona().getFechaNacimientoBean()!=null?adqui.getPersona().getFechaNacimientoBean().toString():"");
		if (adqui.getPersona().getDireccion()!=null){
			baja.getDATOSADQUIRIENTE().setPROVINCIAADQUIRIENTE(adqui.getPersona().getDireccion().getMunicipio().getProvincia().getIdProvincia());
			baja.getDATOSADQUIRIENTE().setMUNICIPIOADQUIRIENTE(adqui.getPersona().getDireccion().getMunicipio().getIdMunicipio());
			baja.getDATOSADQUIRIENTE().setPUEBLOADQUIRIENTE(adqui.getPersona().getDireccion().getPueblo());
			baja.getDATOSADQUIRIENTE().setCODIGOPOSTALADQUIRIENTE(adqui.getPersona().getDireccion().getCodPostal());
			baja.getDATOSADQUIRIENTE().setTIPOVIAADQUIRIENTE(adqui.getPersona().getDireccion().getTipoVia().getIdTipoVia());
			baja.getDATOSADQUIRIENTE().setCALLEDIRECCIONADQUIRIENTE(adqui.getPersona().getDireccion().getNombreVia());
			baja.getDATOSADQUIRIENTE().setNUMERODIRECCIONADQUIRIENTE(adqui.getPersona().getDireccion().getNumero());
			baja.getDATOSADQUIRIENTE().setLETRADIRECCIONADQUIRIENTE(adqui.getPersona().getDireccion().getLetra());
			baja.getDATOSADQUIRIENTE().setESCALERADIRECCIONADQUIRIENTE(adqui.getPersona().getDireccion().getEscalera());
			baja.getDATOSADQUIRIENTE().setPISODIRECCIONADQUIRIENTE(adqui.getPersona().getDireccion().getPlanta());
			baja.getDATOSADQUIRIENTE().setPUERTADIRECCIONADQUIRIENTE(adqui.getPersona().getDireccion().getPuerta());
			baja.getDATOSADQUIRIENTE().setBLOQUEDIRECCIONADQUIRIENTE(adqui.getPersona().getDireccion().getBloque());
			baja.getDATOSADQUIRIENTE().setKMDIRECCIONADQUIRIENTE(adqui.getPersona().getDireccion().getPuntoKilometrico());
			baja.getDATOSADQUIRIENTE().setHMDIRECCIONADQUIRIENTE(adqui.getPersona().getDireccion().getHm());
		}
		
		//PAGO PRESENTACION
		baja.getDATOSPAGOPRESENTACION().setCODIGOTASA(bean.getTramiteTrafico().getTasa().getCodigoTasa());
		baja.getDATOSPAGOPRESENTACION().setJEFATURAPROVINCIAL(bean.getTramiteTrafico().getJefaturaTrafico().getJefaturaProvincial());
		baja.getDATOSPAGOPRESENTACION().setFECHAPRESENTACION(bean.getTramiteTrafico().getFechaPresentacion()!=null?bean.getTramiteTrafico().getFechaPresentacion().toString():"");
		
		cambiarNullsPorVacios(baja);
		return baja;
	}
	*/
	
/**
 *  LIMPIEZA BAJAS ANTIGUAS 	
 * @return
 
	public String getFechaHoy() {
		java.util.Date date = new java.util.Date();
		java.text.SimpleDateFormat sdf=new java.text.SimpleDateFormat("dd/MM/yyyy");
		String fecha = sdf.format(date);
		return fecha;
	}
	
	private void cambiarNullsPorVacios(Object objeto) {
		if (objeto==null || "".equals(objeto)) return;
		Field[] campos = objeto.getClass().getDeclaredFields();
		for (int i=0;i<campos.length;i++) {
			String parametro = campos[i].toString();
			String[] separar = parametro.split("\\.");
			parametro = separar[separar.length-1];
			Class clase = dameClasedeParametro(objeto, parametro);
			if (clase==String.class) {
				try {
					Method metodoGet = objeto.getClass().getMethod("get"+parametro.toUpperCase());
					if (metodoGet.invoke(objeto, new Object[0])==null || "-1".equals(metodoGet.invoke(objeto, new Object[0]))) {
						Method metodoSet = objeto.getClass().getMethod("set"+parametro.toUpperCase(),String.class);
						metodoSet.invoke(objeto, "");
					}
				} catch (Exception e) {
					
					try {
						Method metodoGet = objeto.getClass().getMethod("get"+parametro.substring(0, 1).toUpperCase()+parametro.substring(1));
						if (metodoGet.invoke(objeto, new Object[0])==null || "-1".equals(metodoGet.invoke(objeto, new Object[0]))) {
							Method metodoSet = objeto.getClass().getMethod("set"+parametro.substring(0, 1).toUpperCase()+parametro.substring(1),String.class);
							metodoSet.invoke(objeto, "");
						}
					} catch (Exception e1) {
						log.error(e1);
					}
				}					
			}
			else {
				try {
					Method metodoGet = objeto.getClass().getMethod("get"+parametro.toUpperCase());
					cambiarNullsPorVacios(metodoGet.invoke(objeto, new Object[0]));
				} catch (Exception e) {
					log.error(e);
					Method metodoGet;
					try {
						metodoGet = objeto.getClass().getMethod("get"+parametro.substring(0, 1).toUpperCase()+parametro.substring(1));
						cambiarNullsPorVacios(metodoGet.invoke(objeto, new Object[0]));
					} catch (Exception e1) {
						log.error(e1);
					}
				}
			}
		}
	}
	
	private void cambiarNullsPorFalsesOVacios(Object objeto) {
		if (objeto==null || "".equals(objeto)) return;
		Field[] campos = objeto.getClass().getDeclaredFields();
		for (int i=0;i<campos.length;i++) {
			String parametro = campos[i].toString();
			String[] separar = parametro.split("\\.");
			parametro = separar[separar.length-1];
			Class clase = dameClasedeParametroBean(objeto, parametro);
			if (clase==Boolean.class) {
				try {
					Method metodoGet = objeto.getClass().getMethod("get"+parametro.toUpperCase());
					if (metodoGet.invoke(objeto, new Object[0])==null) {
						Method metodoSet = objeto.getClass().getMethod("set"+parametro.toUpperCase(),Boolean.class);
						metodoSet.invoke(objeto, false);
					}
				} catch (Exception e) {

					try {
						Method metodoGet = objeto.getClass().getMethod("get"+parametro.substring(0, 1).toUpperCase()+parametro.substring(1));
						if (metodoGet.invoke(objeto, new Object[0])==null) {
							Method metodoSet = objeto.getClass().getMethod("set"+parametro.substring(0, 1).toUpperCase()+parametro.substring(1),Boolean.class);
							metodoSet.invoke(objeto, false);
						}
					} catch (Exception e1) {
						log.error(e1);
					}
				}					
			}
			else if (clase==String.class) {
				try {
					Method metodoGet = objeto.getClass().getMethod("get"+parametro.toUpperCase());
					if (metodoGet.invoke(objeto, new Object[0])==null) {
						Method metodoSet = objeto.getClass().getMethod("set"+parametro.toUpperCase(),String.class);
						metodoSet.invoke(objeto, "");
					}
				} catch (Exception e) {

					try {
						Method metodoGet = objeto.getClass().getMethod("get"+parametro.substring(0, 1).toUpperCase()+parametro.substring(1));
						if (metodoGet.invoke(objeto, new Object[0])==null) {
							Method metodoSet = objeto.getClass().getMethod("set"+parametro.substring(0, 1).toUpperCase()+parametro.substring(1),String.class);
							metodoSet.invoke(objeto, "");
						}
					} catch (Exception e1) {
						log.error(e1);
					}
				}					
			}
			else {
				try {
					Method metodoGet = objeto.getClass().getMethod("get"+parametro.toUpperCase());
					cambiarNullsPorVacios(metodoGet.invoke(objeto, new Object[0]));
				} catch (Exception e) {
					log.error(e);
					Method metodoGet;
					try {
						metodoGet = objeto.getClass().getMethod("get"+parametro.substring(0, 1).toUpperCase()+parametro.substring(1));
						cambiarNullsPorVacios(metodoGet.invoke(objeto, new Object[0]));
					} catch (Exception e1) {
						log.error(e1);
					}
				}
			}
		}
	}
	
	private Class<?> dameClasedeParametro(Object bean,String nombreParametro) {
		Object value;
		Object entidad;
		try {
			entidad = bean.getClass().newInstance();
		} catch (Exception e) {
			log.error(e);
			return null;
		}
		try {
		    Method getter = null;
			try {
				getter = entidad.getClass().getMethod("get" + String.valueOf(nombreParametro).toUpperCase());
			} catch (Exception e) {
				log.error(e);
			}
		    value = getter.invoke(entidad, new Object[0]);
		    //Si el objeto obtenido es NULL, intentamos setearle uno de los parametros esperados
		    if(value == null){
		    	Method setter = entidad.getClass().getMethod("set" + String.valueOf(nombreParametro).toUpperCase(),String.class);
		        setter.invoke(entidad, new String(""));
		        getter = entidad.getClass().getMethod("get" + String.valueOf(nombreParametro).toUpperCase());
		        value = getter.invoke(entidad, new Object[0]);
		    }
		    //System.out.println("class:"+value.getClass());
		    entidad = null;
		    return value.getClass();
		} catch(Exception ex) {
			try {
				Method setter = entidad.getClass().getMethod("set" + String.valueOf(nombreParametro),BigDecimal.class);
				setter.invoke(entidad, new BigDecimal(0));
				Method getter = entidad.getClass().getMethod("get" + String.valueOf(nombreParametro));
				value = getter.invoke(entidad, new Object[0]);
				//System.out.println("class:"+value.getClass());
				entidad = null;
			    return value.getClass();
			} catch(Exception ex2) {
				try {
					Method setter = entidad.getClass().getMethod("set" + String.valueOf(nombreParametro),Timestamp.class);
					setter.invoke(entidad, Timestamp.valueOf("2000-01-01 00:00:00"));
					Method getter = entidad.getClass().getMethod("get" + String.valueOf(nombreParametro));
					value = getter.invoke(entidad, new Object[0]);
					//System.out.println("class:"+value.getClass());
					entidad = null;
				    return value.getClass();
				} catch(Exception ex3) {
					try {
						Method setter = entidad.getClass().getMethod("set" + String.valueOf(nombreParametro),Boolean.class);
						setter.invoke(entidad, false);
						Method getter = entidad.getClass().getMethod("get" + String.valueOf(nombreParametro));
						value = getter.invoke(entidad, new Object[0]);
						//System.out.println("class:"+value.getClass());
						entidad = null;
					    return value.getClass();
					}catch(Exception ex4) {
						log.error(ex4);
						log.debug("El parametro " + nombreParametro + " no tiene una clase conocida (String, BigDecimal, Timestamp");
						entidad = null;
					    return null;
					}
				}
			}
		}
	}
	
	private Class<?> dameClasedeParametroBean(Object bean,String nombreParametro) {
		Object value;
		Object entidad;
		try {
			entidad = bean.getClass().newInstance();
		} catch (Exception e) {
			log.error(e);
			return null;
		}
		try{
		    Method getter = null;
			try {
				getter = entidad.getClass().getMethod("get" + nombreParametro.substring(0, 1).toUpperCase()+nombreParametro.substring(1));
			} catch (Exception e) {
				log.error(e);
			}
		    value = getter.invoke(entidad, new Object[0]);
		    //Si el objeto obtenido es NULL, intentamos setearle uno de los parametros esperados
		    if(value == null){
		    	Method setter = entidad.getClass().getMethod("set" + nombreParametro.substring(0, 1).toUpperCase()+nombreParametro.substring(1),String.class);
		        setter.invoke(entidad, new String(""));
		        getter = entidad.getClass().getMethod("get" + nombreParametro.substring(0, 1).toUpperCase()+nombreParametro.substring(1));
		        value = getter.invoke(entidad, new Object[0]);
		    }
		    //log.debug("class:"+value.getClass());
		    entidad = null;
		    return value.getClass();
		} catch(Exception ex) {
			try {
				Method setter = entidad.getClass().getMethod("set" + nombreParametro.substring(0, 1).toUpperCase()+nombreParametro.substring(1),BigDecimal.class);
				setter.invoke(entidad, new BigDecimal(0));
				Method getter = entidad.getClass().getMethod("get" + nombreParametro.substring(0, 1).toUpperCase()+nombreParametro.substring(1));
				value = getter.invoke(entidad, new Object[0]);
				//log.debug("class:"+value.getClass());
				entidad = null;
			    return value.getClass();
			} catch(Exception ex2) {
				try {
					Method setter = entidad.getClass().getMethod("set" + nombreParametro.substring(0, 1).toUpperCase()+nombreParametro.substring(1),Timestamp.class);
					setter.invoke(entidad, Timestamp.valueOf("2000-01-01 00:00:00"));
					Method getter = entidad.getClass().getMethod("get" + nombreParametro.substring(0, 1).toUpperCase()+nombreParametro.substring(1));
					value = getter.invoke(entidad, new Object[0]);
					//log.debug("class:"+value.getClass());
					entidad = null;
				    return value.getClass();
				}catch(Exception ex3) {
					try {
						Method setter = entidad.getClass().getMethod("set" + nombreParametro.substring(0, 1).toUpperCase()+nombreParametro.substring(1),Boolean.class);
						setter.invoke(entidad, false);
						Method getter = entidad.getClass().getMethod("get" + nombreParametro.substring(0, 1).toUpperCase()+nombreParametro.substring(1));
						value = getter.invoke(entidad, new Object[0]);
						//log.debug("class:"+value.getClass());
						entidad = null;
					    return value.getClass();
					}catch(Exception ex4) {
						log.error(ex4);
						log.debug("El parametro " + nombreParametro + " no tiene una clase conocida (String, BigDecimal, Timestamp");
						entidad = null;
					    return null;
					}
				}
			}
		}
	}
	*/
}
