package org.gestoresmadrid.oegam2comun.importacion.utiles;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.gestoresmadrid.core.model.enumerados.TipoInterviniente;
import org.gestoresmadrid.core.springContext.ContextoSpring;
import org.gestoresmadrid.oegam2comun.paises.model.service.ServicioPais;
import org.gestoresmadrid.oegam2comun.paises.view.dto.PaisDto;
import org.gestoresmadrid.oegam2comun.trafico.view.dto.TramiteTrafBajaDto;
import org.gestoresmadrid.oegamComun.direccion.view.dto.DireccionDto;
import org.gestoresmadrid.oegamComun.interviniente.view.dto.IntervinienteTraficoDto;
import org.gestoresmadrid.oegamComun.persona.view.dto.PersonaDto;
import org.gestoresmadrid.oegamComun.tasa.view.dto.TasaDto;
import org.gestoresmadrid.oegamComun.trafico.view.dto.JefaturaTraficoDto;
import org.gestoresmadrid.oegamComun.vehiculo.view.dto.ServicioTraficoDto;
import org.gestoresmadrid.oegamComun.vehiculo.view.dto.VehiculoDto;
import org.gestoresmadrid.utilidades.components.UtilesFecha;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import trafico.beans.jaxb.baja.BAJA;
import trafico.beans.jaxb.baja.DATOSADQUIRIENTE;
import trafico.beans.jaxb.baja.DATOSREPRESENTANTE;
import trafico.beans.jaxb.baja.DATOSREPRESENTANTEADQUIRIENTE;
import trafico.beans.jaxb.baja.DATOSTITULAR;
import trafico.beans.jaxb.baja.DATOSVEHICULO;
import trafico.beans.jaxb.baja.FORMATOOEGAM2BAJA;
import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;

@Component
public class FORMATOGABajaDtoConversion {

	private static final ILoggerOegam log = LoggerOegam.getLogger(FORMATOGABajaDtoConversion.class);

	private static String sinNumero = "SN";

	@Autowired
	UtilesFecha utilesFecha;

	public List<TramiteTrafBajaDto> convertirFORMATOGAtoDto(FORMATOOEGAM2BAJA ga, BigDecimal idContrato, BigDecimal contratoImportacion) {
		List<TramiteTrafBajaDto> lista = new ArrayList<>();

		for (int i = 0; i < ga.getBAJA().size(); i++) {
			VehiculoDto vehiculo = new VehiculoDto();
			IntervinienteTraficoDto adquiriente = new IntervinienteTraficoDto();
			IntervinienteTraficoDto titular = new IntervinienteTraficoDto();
			IntervinienteTraficoDto representante = new IntervinienteTraficoDto();
			IntervinienteTraficoDto representanteAdquiriente = new IntervinienteTraficoDto();
			PaisDto paisBaja = new PaisDto();

			TramiteTrafBajaDto tramite = new TramiteTrafBajaDto();
			if (null != ga.getBAJA().get(i)) {
				if (null != ga.getBAJA().get(i).getDATOSVEHICULO())
					vehiculo = convertirVehiculoGAtoDto(ga, ga.getBAJA().get(i));
				if (null != ga.getBAJA().get(i).getDATOSADQUIRIENTE())
					adquiriente = convertirIntervinienteGAtoDto(ga, TipoInterviniente.Adquiriente.getValorEnum(), ga.getBAJA().get(i));
				if (null != ga.getBAJA().get(i).getDATOSTITULAR())
					titular = convertirIntervinienteGAtoDto(ga, TipoInterviniente.Titular.getValorEnum(), ga.getBAJA().get(i));
				if (null != ga.getBAJA().get(i).getDATOSREPRESENTANTE())
					representante = convertirIntervinienteGAtoDto(ga, TipoInterviniente.RepresentanteTitular.getValorEnum(), ga.getBAJA().get(i));
				if (null != ga.getBAJA().get(i).getDATOSREPRESENTANTEADQUIRIENTE())
					representanteAdquiriente = convertirIntervinienteGAtoDto(ga, TipoInterviniente.RepresentanteAdquiriente.getValorEnum(), ga.getBAJA().get(i));
				if (null != ga.getBAJA().get(i).getPAISBAJA()) {
					if (!"".equals(ga.getBAJA().get(i).getPAISBAJA()))
						paisBaja = convertirPaisBajaToDto(ga.getBAJA().get(i).getPAISBAJA());
				}
				tramite = convertirTramiteBajaToDto(ga, ga.getBAJA().get(i), idContrato, contratoImportacion);
			}
			if (tramite != null) {
				tramite.setVehiculoDto(vehiculo);
				tramite.setAdquiriente(adquiriente);
				tramite.setTitular(titular);
				tramite.setRepresentanteTitular(representante);
				tramite.setRepresentanteAdquiriente(representanteAdquiriente);
				tramite.setPais(paisBaja);
				lista.add(tramite);
			}
		}
		return lista;
	}

	// Convertir el pais de la baja
	private static PaisDto convertirPaisBajaToDto(String Sigla) {
		PaisDto paisBaja = new PaisDto();
		try {
			if (Sigla != null) {
				ServicioPais servicioPais = ContextoSpring.getInstance().getBean(ServicioPais.class);
				paisBaja = servicioPais.getIdPaisPorSigla(Sigla);
				if (paisBaja == null)

					throw new Exception("Error Pais Baja: ");
			}
			return paisBaja;
		} catch (Exception e) {
			log.debug("El código de Pais no es válido");
			return null;
		}
	}

	private TramiteTrafBajaDto convertirTramiteBajaToDto(FORMATOOEGAM2BAJA ga, BAJA baja, BigDecimal idContrato, BigDecimal contratoImportacion) {
		TramiteTrafBajaDto tramite = new TramiteTrafBajaDto();
		if (contratoImportacion == null) {
			tramite.setIdContrato(idContrato);
		} else {
			tramite.setIdContrato(contratoImportacion);
		}
		try {
			if ("OEGAM".equals(ga.getPlataforma()))
				tramite.setNumExpediente(new BigDecimal(baja.getNUMEROEXPEDIENTE()));
			else
				tramite.setNumExpediente(null);
		} catch (Exception e) {
			tramite.setNumExpediente(null);
			log.debug("El numero del documento no es un bigdecimal");
		}

		tramite.setNumColegiado(ga.getCABECERA().getDATOSGESTORIA().getPROFESIONAL());
		tramite.setRefPropia(baja.getREFERENCIAPROPIA());
		tramite.setFechaAlta(utilesFecha.getFechaFracionada(utilesFecha.DDMMAAAAToTimestamp(baja.getFECHACREACION())));
		tramite.setAnotaciones(baja.getOBSERVACIONES());
		tramite.setFechaPresentacion(utilesFecha.getFechaFracionada(utilesFecha.DDMMAAAAToTimestamp(baja.getDATOSPAGOPRESENTACION().getFECHAPRESENTACION())));
		tramite.setCema(baja.getDATOSPAGOPRESENTACION().getCEMA());
		tramite.setMotivoBaja(baja.getMOTIVOBAJA());

		if (StringUtils.isNotBlank(baja.getPERMISOCIRCULACION())) {
			tramite.setPermisoCircu(baja.getPERMISOCIRCULACION().equalsIgnoreCase("SI"));
		} else {
			tramite.setPermisoCircu(false);
		}

		if (StringUtils.isNotBlank(baja.getTARJETAINSPECCIONTECNICA())) {
			tramite.setTarjetaInspeccion(baja.getTARJETAINSPECCIONTECNICA().equalsIgnoreCase("SI"));
		} else {
			tramite.setTarjetaInspeccion(false);
		}

		if (StringUtils.isNotBlank(baja.getDECLARACIONJURADAEXTRAVIO())) {
			tramite.setDeclaracionJuradaExtravio(baja.getDECLARACIONJURADAEXTRAVIO().equalsIgnoreCase("SI"));
		} else {
			tramite.setDeclaracionJuradaExtravio(false);
		}

		if (StringUtils.isNotBlank(baja.getBAJAIMVTM())) {
			tramite.setBajaImpMunicipal(baja.getBAJAIMVTM().equalsIgnoreCase("SI"));
		} else {
			tramite.setBajaImpMunicipal(false);
		}

		if (StringUtils.isNotBlank(baja.getJUSTIFICANTEDENUNCIA())) {
			tramite.setJustificanteDenuncia(baja.getJUSTIFICANTEDENUNCIA().equalsIgnoreCase("SI"));
		} else {
			tramite.setJustificanteDenuncia(false);
		}

		if (StringUtils.isNotBlank(baja.getDOCUMENTOPROPIEDAD())) {
			tramite.setPropiedadDesguace(baja.getDOCUMENTOPROPIEDAD().equalsIgnoreCase("SI"));
		} else {
			tramite.setPropiedadDesguace(false);
		}

		if (StringUtils.isNotBlank(baja.getJUSTIFICANTEIMVTM())) {
			tramite.setPagoImpMunicipal(baja.getJUSTIFICANTEIMVTM().equalsIgnoreCase("SI"));
		} else {
			tramite.setPagoImpMunicipal(false);
		}

		if (StringUtils.isNotBlank(baja.getDECLARACIONNOENTREGACATV())) {
			tramite.setDeclaracionNoEntregaCatV(baja.getDECLARACIONNOENTREGACATV().equalsIgnoreCase("SI"));
		} else {
			tramite.setDeclaracionNoEntregaCatV(false);
		}

		if (StringUtils.isNotBlank(baja.getDECLARACIONCARTADGT10ANIOS())) {
			tramite.setCartaDGTVehiculoMasDiezAn(baja.getDECLARACIONCARTADGT10ANIOS().equalsIgnoreCase("SI"));
		} else {
			tramite.setCartaDGTVehiculoMasDiezAn(false);
		}

		if (StringUtils.isNotBlank(baja.getCERTIFICADO_MEDIOAMBIENTAL())) {
			tramite.setCertificadoMedioAmbiental(baja.getCERTIFICADO_MEDIOAMBIENTAL().equalsIgnoreCase("SI"));
		} else {
			tramite.setCertificadoMedioAmbiental(false);
		}

		if (StringUtils.isNotBlank(baja.getDECLARACIONNORESIDUO())) {
			tramite.setDeclaracionResiduo(baja.getDECLARACIONNORESIDUO().equalsIgnoreCase("SI"));
		} else {
			tramite.setDeclaracionResiduo(false);
		}

		if (StringUtils.isNotBlank(baja.getDECLARACIONNOENTREGACATV())) {
			tramite.setDeclaracionNoEntregaCatV(baja.getDECLARACIONNOENTREGACATV().equalsIgnoreCase("SI"));
		} else {
			tramite.setDeclaracionNoEntregaCatV(false);
		}
		if (StringUtils.isNotBlank(baja.getDECLARACIONCARTADGT10ANIOS())) {
			tramite.setCartaDGTVehiculoMasDiezAn(baja.getDECLARACIONCARTADGT10ANIOS().equalsIgnoreCase("SI"));
		} else {
			tramite.setCartaDGTVehiculoMasDiezAn(false);
		}
		if (StringUtils.isNotBlank(baja.getCERTIFICADO_MEDIOAMBIENTAL())) {
			tramite.setCertificadoMedioAmbiental(baja.getCERTIFICADO_MEDIOAMBIENTAL().equalsIgnoreCase("SI"));
		} else {
			tramite.setCertificadoMedioAmbiental(false);
		}

		TasaDto tasa = new TasaDto();
		tasa.setCodigoTasa(baja.getDATOSPAGOPRESENTACION().getCODIGOTASA());
		tramite.setTasa(tasa);

		JefaturaTraficoDto jefatura = new JefaturaTraficoDto();
		jefatura.setJefaturaProvincial(baja.getDATOSPAGOPRESENTACION().getJEFATURAPROVINCIAL());
		tramite.setJefaturaTraficoDto(jefatura);

		return tramite;
	}

	private IntervinienteTraficoDto convertirIntervinienteGAtoDto(FORMATOOEGAM2BAJA ga, String tipoInterviniente, BAJA baja) {
		IntervinienteTraficoDto interviniente = new IntervinienteTraficoDto();

		if (TipoInterviniente.Adquiriente.getValorEnum().equals(tipoInterviniente)) {
			DATOSADQUIRIENTE adquirente = baja.getDATOSADQUIRIENTE();

			interviniente.setTipoInterviniente(TipoInterviniente.Adquiriente.getValorEnum());

			interviniente.setNumColegiado(ga.getCABECERA().getDATOSGESTORIA().getPROFESIONAL());
			interviniente.setNifInterviniente(adquirente.getDNIADQUIRIENTE());

			PersonaDto persona = new PersonaDto();
			persona.setNif(adquirente.getDNIADQUIRIENTE());
			persona.setNumColegiado(ga.getCABECERA().getDATOSGESTORIA().getPROFESIONAL());
			persona.setApellido1RazonSocial(adquirente.getAPELLIDO1RAZONSOCIALADQUIRIENTE());
			persona.setApellido2(adquirente.getAPELLIDO2ADQUIRIENTE());
			persona.setNombre(adquirente.getNOMBREADQUIRIENTE());
			persona.setFechaNacimiento(utilesFecha.getFechaFracionada(utilesFecha.DDMMAAAAToTimestamp(adquirente.getFECHANACIMIENTOADQUIRIENTE())));
			persona.setSexo(adquirente.getSEXOADQUIRIENTE());

			interviniente.setPersona(persona);

			DireccionDto direccion = new DireccionDto();
			direccion.setIdProvincia(adquirente.getPROVINCIAADQUIRIENTE());
			direccion.setIdMunicipio(adquirente.getMUNICIPIOADQUIRIENTE());
			direccion.setIdTipoVia(adquirente.getTIPOVIAADQUIRIENTE());
			direccion.setNombreVia(adquirente.getCALLEDIRECCIONADQUIRIENTE());
			direccion.setNumero(adquirente.getNUMERODIRECCIONADQUIRIENTE());
			direccion.setCodPostal(adquirente.getCODIGOPOSTALADQUIRIENTE());
			direccion.setPueblo(adquirente.getPUEBLOADQUIRIENTE());
			direccion.setPuebloLit(adquirente.getPUEBLOADQUIRIENTE());
			direccion.setLetra(adquirente.getLETRADIRECCIONADQUIRIENTE());
			direccion.setEscalera(adquirente.getESCALERADIRECCIONADQUIRIENTE());
			direccion.setBloque(adquirente.getBLOQUEDIRECCIONADQUIRIENTE());
			direccion.setPlanta(adquirente.getPISODIRECCIONADQUIRIENTE());
			direccion.setPuerta(adquirente.getPUERTADIRECCIONADQUIRIENTE());
			if (adquirente.getKMDIRECCIONADQUIRIENTE() != null && !"".equals(adquirente.getKMDIRECCIONADQUIRIENTE())) {
				direccion.setKm(new BigDecimal(adquirente.getKMDIRECCIONADQUIRIENTE()));
			}
			if (adquirente.getHMDIRECCIONADQUIRIENTE() != null && !"".equals(adquirente.getHMDIRECCIONADQUIRIENTE())) {
				direccion.setHm(new BigDecimal(adquirente.getHMDIRECCIONADQUIRIENTE()));
			}

			direccion.setAsignarPrincipal(adquirente.getDIRECCIONACTIVAADQUIRIENTE());

			interviniente.setDireccion(direccion);
		} else if (TipoInterviniente.RepresentanteTitular.getValorEnum().equals(tipoInterviniente)) {
			DATOSREPRESENTANTE repre = baja.getDATOSREPRESENTANTE();

			interviniente.setTipoInterviniente(TipoInterviniente.RepresentanteTitular.getValorEnum());
			interviniente.setConceptoRepre(repre.getCONCEPTOREPRESENTANTE());

			interviniente.setNumColegiado(ga.getCABECERA().getDATOSGESTORIA().getPROFESIONAL());
			interviniente.setNifInterviniente(repre.getDNIREPRESENTANTE());

			PersonaDto persona = new PersonaDto();
			persona.setNif(repre.getDNIREPRESENTANTE());
			persona.setApellido1RazonSocial(repre.getAPELLIDO1RAZONSOCIALREPRESENTANTE());
			persona.setApellido2(repre.getAPELLIDO2REPRESENTANTE());
			persona.setNombre(repre.getNOMBREREPRESENTANTE());
			persona.setSexo(repre.getSEXOREPRESENTANTE());

			interviniente.setPersona(persona);
		} else if (TipoInterviniente.Titular.getValorEnum().equals(tipoInterviniente)) {
			DATOSTITULAR titular = baja.getDATOSTITULAR();

			interviniente.setTipoInterviniente(TipoInterviniente.Titular.getValorEnum());
			interviniente.setNumColegiado(ga.getCABECERA().getDATOSGESTORIA().getPROFESIONAL());
			interviniente.setNifInterviniente(titular.getDNITITULAR());

			PersonaDto persona = new PersonaDto();
			persona.setNif(titular.getDNITITULAR());
			persona.setApellido1RazonSocial(titular.getAPELLIDO1RAZONSOCIALTITULAR());
			persona.setApellido2(titular.getAPELLIDO2TITULAR());
			persona.setNombre(titular.getNOMBRETITULAR());
			persona.setFechaNacimiento(utilesFecha.getFechaFracionada(utilesFecha.DDMMAAAAToTimestamp(titular.getFECHANACIMIENTOTITULAR())));
			persona.setSexo(titular.getSEXOTITULAR());
			interviniente.setPersona(persona);

			DireccionDto direccion = new DireccionDto();
			direccion.setIdProvincia(titular.getPROVINCIATITULAR());
			direccion.setIdMunicipio(titular.getMUNICIPIOTITULAR());
			direccion.setIdTipoVia(titular.getTIPOVIATITULAR());
			direccion.setNombreVia(titular.getCALLEDIRECCIONTITULAR());
			direccion.setNumero(titular.getNUMERODIRECCIONTITULAR());
			direccion.setCodPostal(titular.getCODIGOPOSTALTITULAR());
			direccion.setPueblo(titular.getPUEBLOTITULAR());
			direccion.setPuebloLit(titular.getPUEBLOTITULAR());
			direccion.setLetra(titular.getLETRADIRECCIONTITULAR());
			direccion.setEscalera(titular.getESCALERADIRECCIONTITULAR());
			direccion.setBloque(titular.getBLOQUEDIRECCIONTITULAR());
			direccion.setPlanta(titular.getPISODIRECCIONTITULAR());
			direccion.setPuerta(titular.getPUERTADIRECCIONTITULAR());
			if (titular.getKMDIRECCIONTITULAR() != null && !"".equals(titular.getKMDIRECCIONTITULAR())) {
				direccion.setKm(new BigDecimal(titular.getKMDIRECCIONTITULAR()));
			}
			if (titular.getHMDIRECCIONTITULAR() != null && !"".equals(titular.getHMDIRECCIONTITULAR())) {
				direccion.setHm(new BigDecimal(titular.getHMDIRECCIONTITULAR()));
			}

			direccion.setAsignarPrincipal(titular.getDIRECCIONACTIVATITULAR());

			interviniente.setDireccion(direccion);
		} else if (TipoInterviniente.RepresentanteAdquiriente.getValorEnum().equals(tipoInterviniente)) {
			DATOSREPRESENTANTEADQUIRIENTE repre = baja.getDATOSREPRESENTANTEADQUIRIENTE();

			interviniente.setTipoInterviniente(TipoInterviniente.RepresentanteAdquiriente.getValorEnum());
			interviniente.setConceptoRepre(repre != null ? repre.getCONCEPTOREPRESENTANTEADQUIRIENTE() : null);

			interviniente.setNumColegiado(ga.getCABECERA().getDATOSGESTORIA().getPROFESIONAL());
			interviniente.setNifInterviniente(repre != null ? repre.getDNIREPRESENTANTEADQUIRIENTE() : null);

			PersonaDto persona = new PersonaDto();
			persona.setNif(repre.getDNIREPRESENTANTEADQUIRIENTE());
			persona.setApellido1RazonSocial(repre.getAPELLIDO1RAZONSOCIALREPRESENTANTEADQUIRIENTE());
			persona.setApellido2(repre.getAPELLIDO2REPRESENTANTEADQUIRIENTE());
			persona.setNombre(repre.getNOMBREREPRESENTANTEADQUIRIENTE());
			persona.setSexo(repre.getSEXOREPRESENTANTEADQUIRIENTE());

			interviniente.setPersona(persona);
		}

		if (interviniente.getDireccion() != null && (interviniente.getDireccion().getNumero() == null || "".equals(interviniente.getDireccion().getNumero()))) {
			interviniente.getDireccion().setNumero(sinNumero);
		}

		return interviniente;
	}

	/**
	 * Devuelve un beanPQ de Vehículo a partir de la información de un trámite ga (importado XML-MATE)
	 * @param ga
	 * @return
	 */
	private VehiculoDto convertirVehiculoGAtoDto(FORMATOOEGAM2BAJA ga, BAJA baja) {
		VehiculoDto vehiculo = new VehiculoDto();
		DATOSVEHICULO vehiculoGa = baja.getDATOSVEHICULO();

		if (vehiculoGa == null)
			return vehiculo;

		vehiculo.setNumColegiado(ga.getCABECERA().getDATOSGESTORIA().getPROFESIONAL());
		vehiculo.setMatricula(vehiculoGa.getMATRICULA());
		// Añadimos el bastidor
		vehiculo.setBastidor(vehiculoGa.getNUMEROBASTIDOR());
		vehiculo.setFechaMatriculacion(utilesFecha.getFechaFracionada(utilesFecha.DDMMAAAAToTimestamp(vehiculoGa.getFECHAMATRICULACION())));

		vehiculo.setTipoVehiculo(vehiculoGa.getTIPOVEHICULO());
		vehiculo.setPesoMma(vehiculoGa.getPESOMMA());

		ServicioTraficoDto servicio = new ServicioTraficoDto();
		servicio.setIdServicio(vehiculoGa.getSERVICIO());
		vehiculo.setServicioTrafico(servicio);

		DireccionDto direccion = new DireccionDto();
		direccion.setIdProvincia(vehiculoGa.getPROVINCIAVEHICULO());
		direccion.setIdMunicipio(vehiculoGa.getMUNICIPIOVEHICULO());
		direccion.setIdTipoVia(vehiculoGa.getTIPOVIAVEHICULO());
		direccion.setNombreVia(vehiculoGa.getCALLEDIRECCIONVEHICULO());
		direccion.setNumero(vehiculoGa.getNUMERODIRECCIONVEHICULO());
		direccion.setCodPostal(vehiculoGa.getCODIGOPOSTALVEHICULO());
		direccion.setPueblo(vehiculoGa.getPUEBLOVEHICULO());
		direccion.setPuebloLit(vehiculoGa.getPUEBLOVEHICULO());
		direccion.setLetra(vehiculoGa.getLETRADIRECCIONVEHICULO());
		direccion.setEscalera(vehiculoGa.getESCALERADIRECCIONVEHICULO());
		direccion.setBloque(vehiculoGa.getBLOQUEDIRECCIONVEHICULO());
		direccion.setPlanta(vehiculoGa.getPISODIRECCIONVEHICULO());
		direccion.setPuerta(vehiculoGa.getPUERTADIRECCIONVEHICULO());
		if (vehiculoGa.getKMDIRECCIONVEHICULO() != null && !"".equals(vehiculoGa.getKMDIRECCIONVEHICULO())) {
			direccion.setKm(new BigDecimal(vehiculoGa.getKMDIRECCIONVEHICULO()));
		}
		if (vehiculoGa.getHMDIRECCIONVEHICULO() != null && !"".equals(vehiculoGa.getHMDIRECCIONVEHICULO())) {
			direccion.setHm(new BigDecimal(vehiculoGa.getHMDIRECCIONVEHICULO()));
		}

		vehiculo.setDireccion(direccion);
		vehiculo.setFechaItv(utilesFecha.getFechaFracionada(utilesFecha.DDMMAAAAToTimestamp(vehiculoGa.getFECHAITV())));
		vehiculo.setEsSiniestro(Boolean.FALSE);
		if(StringUtils.isNotBlank(vehiculoGa.getESSINIESTRO()) && vehiculoGa.getESSINIESTRO().trim().equalsIgnoreCase("SI")){
			vehiculo.setEsSiniestro(Boolean.TRUE);
		}

		return vehiculo;
	}
}