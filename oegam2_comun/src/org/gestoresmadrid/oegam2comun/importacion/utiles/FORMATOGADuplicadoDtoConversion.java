package org.gestoresmadrid.oegam2comun.importacion.utiles;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.gestoresmadrid.core.model.enumerados.TipoInterviniente;
import org.gestoresmadrid.oegam2comun.conversion.Conversiones;
import org.gestoresmadrid.oegam2comun.trafico.view.dto.TramiteTrafDuplicadoDto;
import org.gestoresmadrid.oegamComun.direccion.view.dto.DireccionDto;
import org.gestoresmadrid.oegamComun.interviniente.view.dto.IntervinienteTraficoDto;
import org.gestoresmadrid.oegamComun.persona.view.dto.PersonaDto;
import org.gestoresmadrid.oegamComun.trafico.view.dto.JefaturaTraficoDto;
import org.gestoresmadrid.oegamComun.vehiculo.view.dto.VehiculoDto;
import org.gestoresmadrid.utilidades.components.UtilesFecha;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import trafico.beans.jaxb.duplicados.DATOSCOTITULAR;
import trafico.beans.jaxb.duplicados.DATOSREPRESENTANTETITULAR;
import trafico.beans.jaxb.duplicados.DATOSTITULAR;
import trafico.beans.jaxb.duplicados.DATOSVEHICULO;
import trafico.beans.jaxb.duplicados.DIRECCION;
import trafico.beans.jaxb.duplicados.DUPLICADO;
import trafico.beans.jaxb.duplicados.FORMATOOEGAM2DUPLICADO;

@Component
public class FORMATOGADuplicadoDtoConversion {

	private static String sinNumero = "SN";

	@Autowired
	private Conversiones conversion;

	@Autowired
	UtilesFecha utilesFecha;

	public List<TramiteTrafDuplicadoDto> convertirFORMATOGAtoDto(FORMATOOEGAM2DUPLICADO ga, BigDecimal idContrato, BigDecimal contratoImportacion) {
		List<TramiteTrafDuplicadoDto> lista = new ArrayList<>();

		for (int i = 0; i < ga.getDUPLICADO().size(); i++) {
			VehiculoDto vehiculo = new VehiculoDto();
			IntervinienteTraficoDto titular = new IntervinienteTraficoDto();
			IntervinienteTraficoDto representante = new IntervinienteTraficoDto();
			IntervinienteTraficoDto cotitular = new IntervinienteTraficoDto();

			TramiteTrafDuplicadoDto tramite = new TramiteTrafDuplicadoDto();
			if (null != ga.getDUPLICADO().get(i)) {
				if (null != ga.getDUPLICADO().get(i).getDATOSVEHICULO())
					vehiculo = convertirVehiculoGAtoDto(ga, ga.getDUPLICADO().get(i));
				if (null != ga.getDUPLICADO().get(i).getDATOSTITULAR())
					titular = convertirIntervinienteGAtoDto(ga, TipoInterviniente.Titular.getValorEnum(), ga.getDUPLICADO().get(i));
				if (null != ga.getDUPLICADO().get(i).getDATOSREPRESENTANTETITULAR())
					representante = convertirIntervinienteGAtoDto(ga, TipoInterviniente.RepresentanteTitular.getValorEnum(), ga.getDUPLICADO().get(i));
				if (null != ga.getDUPLICADO().get(i).getDATOSCOTITULAR())
					cotitular = convertirIntervinienteGAtoDto(ga, TipoInterviniente.CotitularTransmision.getValorEnum(), ga.getDUPLICADO().get(i));
				tramite = convertirTramiteDuplicadoToDto(ga, ga.getDUPLICADO().get(i), idContrato, contratoImportacion);
			}
			if (tramite != null) {
				tramite.setVehiculoDto(vehiculo);
				tramite.setCotitular(cotitular);
				tramite.setTitular(titular);
				tramite.setRepresentanteTitular(representante);
				lista.add(tramite);
			}
		}
		return lista;
	}

	private TramiteTrafDuplicadoDto convertirTramiteDuplicadoToDto(FORMATOOEGAM2DUPLICADO ga, DUPLICADO duplicado, BigDecimal idContrato, BigDecimal contratoImportacion) {
		TramiteTrafDuplicadoDto tramite = new TramiteTrafDuplicadoDto();
		if (contratoImportacion == null) {
			tramite.setIdContrato(idContrato);
		} else {
			tramite.setIdContrato(contratoImportacion);
		}

		// Datos duplicado
		if (duplicado.getDATOSDUPLICADO() != null) {
			tramite.setMotivoDuplicado(duplicado.getDATOSDUPLICADO().getMOTIVODUPLICADO());
			tramite.setTipoDocumento(duplicado.getDATOSDUPLICADO().getTIPODOCUMENTO());
			tramite.setRefPropia(duplicado.getDATOSDUPLICADO().getREFERENCIAPROPIA());
			tramite.setImprPermisoCirculacion(duplicado.getDATOSDUPLICADO().isINDIMPRESIONPERMISOCIRCULACION());
			tramite.setImportacion(duplicado.getDATOSDUPLICADO().isINDVEHICULOIMPORTACION());
			tramite.setTasaImportacion(duplicado.getDATOSDUPLICADO().getCODIGOTASAVEHICULOIMPORTACION());
			tramite.setAnotaciones(duplicado.getDATOSDUPLICADO().getOBSERVACIONESDGT());
		}

		if (duplicado.getDATOSPAGOPRESENTACION() != null) {
			
			tramite.setTasaPermiso(duplicado.getDATOSPAGOPRESENTACION().getCODIGOTASAPERMISO());
			tramite.setTasaFichaTecnica(duplicado.getDATOSPAGOPRESENTACION().getCODIGOTASAFICHA());

			JefaturaTraficoDto jefatura = new JefaturaTraficoDto();
			jefatura.setJefaturaProvincial(duplicado.getDATOSPAGOPRESENTACION().getJEFATURAPROVINCIAL());
			tramite.setJefaturaTraficoDto(jefatura);
		}

		tramite.setNumColegiado(ga.getCABECERA().getDATOSGESTORIA().getPROFESIONAL());
		tramite.setFechaPresentacion(utilesFecha.getFechaFracionada(utilesFecha.DDMMAAAAToTimestamp(duplicado.getDATOSPAGOPRESENTACION().getFECHAPRESENTACION())));

		return tramite;
	}

	private IntervinienteTraficoDto convertirIntervinienteGAtoDto(FORMATOOEGAM2DUPLICADO ga, String tipoInterviniente, DUPLICADO duplicado) {
		IntervinienteTraficoDto interviniente = new IntervinienteTraficoDto();

		if (TipoInterviniente.CotitularTransmision.getValorEnum().equals(tipoInterviniente)) {
			DATOSCOTITULAR cotitular = duplicado.getDATOSCOTITULAR();

			interviniente.setTipoInterviniente(TipoInterviniente.CotitularTransmision.getValorEnum());
			interviniente.setNumColegiado(ga.getCABECERA().getDATOSGESTORIA().getPROFESIONAL());
			interviniente.setNifInterviniente(cotitular.getNIFCIF());

			PersonaDto persona = new PersonaDto();
			persona.setNif(cotitular.getNIFCIF());
			persona.setApellido1RazonSocial(cotitular.getAPELLIDO1RAZONSOCIAL());
			persona.setApellido2(cotitular.getAPELLIDO2());
			persona.setNombre(cotitular.getNOMBRE());
			persona.setSexo(cotitular.getSEXO());
			interviniente.setPersona(persona);

			DireccionDto direccion = convertirDIRECCIONToDireccionDto(cotitular.getDIRECCION());
			interviniente.setDireccion(direccion);
		} else if (TipoInterviniente.RepresentanteTitular.getValorEnum().equals(tipoInterviniente)) {
			DATOSREPRESENTANTETITULAR repre = duplicado.getDATOSREPRESENTANTETITULAR();

			interviniente.setTipoInterviniente(TipoInterviniente.RepresentanteTitular.getValorEnum());
			interviniente.setConceptoRepre(repre.getCONCEPTOTUTELA());
			interviniente.setNumColegiado(ga.getCABECERA().getDATOSGESTORIA().getPROFESIONAL());
			interviniente.setNifInterviniente(repre.getNIFCIF());
			interviniente.setIdMotivoTutela(repre.getMOTIVOTUTELA());
			interviniente.setDatosDocumento(repre.getDATOSDOCUMENTO());

			interviniente.setFechaInicio(utilesFecha.getFechaFracionada(utilesFecha.DDMMAAAAToTimestamp(repre.getFECHAINICIOTUTELA())));
			interviniente.setFechaFin(utilesFecha.getFechaFracionada(utilesFecha.DDMMAAAAToTimestamp(repre.getFECHAFINTUTELA())));

			PersonaDto persona = new PersonaDto();
			persona.setNif(repre.getNIFCIF());
			persona.setApellido1RazonSocial(repre.getAPELLIDO1RAZONSOCIAL());
			persona.setApellido2(repre.getAPELLIDO2());
			persona.setNombre(repre.getNOMBRE());

			interviniente.setPersona(persona);
		} else if (TipoInterviniente.Titular.getValorEnum().equals(tipoInterviniente)) {
			DATOSTITULAR titular = duplicado.getDATOSTITULAR();

			interviniente.setTipoInterviniente(TipoInterviniente.Titular.getValorEnum());
			interviniente.setNumColegiado(ga.getCABECERA().getDATOSGESTORIA().getPROFESIONAL());
			interviniente.setNifInterviniente(titular.getNIFCIF());

			PersonaDto persona = new PersonaDto();
			persona.setNif(titular.getNIFCIF());
			persona.setApellido1RazonSocial(titular.getAPELLIDO1RAZONSOCIAL());
			persona.setApellido2(titular.getAPELLIDO2());
			persona.setNombre(titular.getNOMBRE());
			persona.setSexo(titular.getSEXO());
			interviniente.setPersona(persona);

			DireccionDto direccion = convertirDIRECCIONToDireccionDto(titular.getDIRECCION());
			interviniente.setDireccion(direccion);
		}

		return interviniente;
	}

	private DireccionDto convertirDIRECCIONToDireccionDto(DIRECCION direccion) {
		if (direccion != null) {
			DireccionDto direccionDto = new DireccionDto();
			direccionDto.setIdProvincia(direccion.getPROVINCIA() != null ? conversion.getIdProvinciaFromSiglas(direccion.getPROVINCIA()) : "");
			direccionDto.setMunicipio(direccion.getMUNICIPIO());
			direccionDto.setPueblo(direccion.getPUEBLO());
			direccionDto.setCodPostal(direccion.getCODIGOPOSTAL());
			direccionDto.setIdTipoVia(direccion.getTIPOVIA());
			direccionDto.setNombreVia(direccion.getNOMBREVIA());
			direccionDto.setNumero(direccion.getNUMERO());
			direccionDto.setLetra(direccion.getLETRA());
			direccionDto.setEscalera(direccion.getESCALERA());
			direccionDto.setPlanta(direccion.getPISO());
			direccionDto.setPuerta(direccion.getPUERTA());
			direccionDto.setBloque(direccion.getBLOQUE());
			if (direccion.getKM() != null && !"".equals(direccion.getKM())) {
				direccionDto.setKm(new BigDecimal(direccion.getKM()));
			}
			if (direccion.getHM() != null && !"".equals(direccion.getHM())) {
				direccionDto.setHm(new BigDecimal(direccion.getHM()));
			}

			if (direccionDto.getNumero() == null || "".equals(direccionDto.getNumero())) {
				direccionDto.setNumero(sinNumero);
			}
			return direccionDto;
		}
		return null;
	}

	/**
	 * Devuelve un beanPQ de Vehículo a partir de la información de un trámite ga
	 * @param ga
	 * @return
	 */
	private VehiculoDto convertirVehiculoGAtoDto(FORMATOOEGAM2DUPLICADO ga, DUPLICADO duplicado) {
		VehiculoDto vehiculo = new VehiculoDto();
		DATOSVEHICULO vehiculoGa = duplicado.getDATOSVEHICULO();

		if (vehiculoGa == null)
			return vehiculo;

		vehiculo.setNumColegiado(ga.getCABECERA().getDATOSGESTORIA().getPROFESIONAL());
		vehiculo.setMatricula(vehiculoGa.getMATRICULA());
		vehiculo.setFechaMatriculacion(utilesFecha.getFechaFracionada(utilesFecha.DDMMAAAAToTimestamp(vehiculoGa.getFECHAMATRICULACION())));
		vehiculo.setFechaItv(utilesFecha.getFechaFracionada(utilesFecha.DDMMAAAAToTimestamp(vehiculoGa.getFECHAITV())));

		return vehiculo;
	}
}