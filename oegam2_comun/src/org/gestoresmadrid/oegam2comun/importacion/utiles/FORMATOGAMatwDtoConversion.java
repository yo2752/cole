package org.gestoresmadrid.oegam2comun.importacion.utiles;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.gestoresmadrid.core.ivtmMatriculacion.model.enumerados.EstadoIVTM;
import org.gestoresmadrid.core.model.enumerados.TipoInterviniente;
import org.gestoresmadrid.core.model.enumerados.TipoTramiteMatriculacion;
import org.gestoresmadrid.core.vehiculo.model.enumerados.ConceptoTutela;
import org.gestoresmadrid.core.vehiculo.model.enumerados.PaisFabricacion;
import org.gestoresmadrid.oegam2comun.conversion.Conversiones;
import org.gestoresmadrid.oegam2comun.ivtmMatriculacion.model.view.dto.IvtmMatriculacionDto;
import org.gestoresmadrid.oegam2comun.paises.model.service.ServicioPais;
import org.gestoresmadrid.oegam2comun.paises.view.dto.PaisDto;
import org.gestoresmadrid.oegam2comun.trafico.eeff.view.dto.LiberacionEEFFDto;
import org.gestoresmadrid.oegam2comun.trafico.view.dto.TramiteTrafMatrDto;
import org.gestoresmadrid.oegam2comun.vehiculo.model.service.ServicioVehiculo;
import org.gestoresmadrid.oegam2comun.vehiculo.view.dto.MarcaDgtDto;
import org.gestoresmadrid.oegamComun.direccion.view.dto.DireccionDto;
import org.gestoresmadrid.oegamComun.interviniente.view.dto.IntervinienteTraficoDto;
import org.gestoresmadrid.oegamComun.persona.view.dto.PersonaDto;
import org.gestoresmadrid.oegamComun.tasa.view.dto.TasaDto;
import org.gestoresmadrid.oegamComun.trafico.view.dto.JefaturaTraficoDto;
import org.gestoresmadrid.oegamComun.vehiculo.view.dto.ServicioTraficoDto;
import org.gestoresmadrid.oegamComun.vehiculo.view.dto.VehiculoDto;
import org.gestoresmadrid.utilidades.components.Utiles;
import org.gestoresmadrid.utilidades.components.UtilesFecha;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import general.utiles.Anagrama;
import trafico.beans.jaxb.matw.FORMATOGA;
import trafico.beans.jaxb.matw.FORMATOGA.MATRICULACION;
import trafico.beans.jaxb.matw.FORMATOGA.MATRICULACION.DATOSARRENDATARIO;
import trafico.beans.jaxb.matw.FORMATOGA.MATRICULACION.DATOSCONDUCTORHABITUAL;
import trafico.beans.jaxb.matw.FORMATOGA.MATRICULACION.DATOSIMPORTADOR;
import trafico.beans.jaxb.matw.FORMATOGA.MATRICULACION.DATOSIMPUESTOS.DATOS576;
import trafico.beans.jaxb.matw.FORMATOGA.MATRICULACION.DATOSREPRESENTANTEARRENDATARIO;
import trafico.beans.jaxb.matw.FORMATOGA.MATRICULACION.DATOSREPRESENTANTETITULAR;
import trafico.beans.jaxb.matw.FORMATOGA.MATRICULACION.DATOSTITULAR;
import trafico.beans.jaxb.matw.FORMATOGA.MATRICULACION.DATOSVEHICULO;
import trafico.beans.jaxb.matw.TipoTarjetaItv;
import trafico.beans.jaxb.matw.dgt.tipos.TipoSN;
import utilidades.estructuras.Fecha;
import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;

@Component
public class FORMATOGAMatwDtoConversion {

	private static final ILoggerOegam log = LoggerOegam.getLogger(FORMATOGAMatwDtoConversion.class);

	private static String sinNumero = "SN";

	@Autowired
	private ServicioVehiculo servicioVehiculo;
	
	@Autowired
	private ServicioPais servicioPais;

	@Autowired
	private Conversiones conversion;

	@Autowired
	UtilesFecha utilesFecha;

	@Autowired
	Utiles utiles;

	public List<TramiteTrafMatrDto> convertirFORMATOGAtoPQ(FORMATOGA ga, BigDecimal idContrato, BigDecimal contratoImportacion, Boolean tienePermisoLiberarEEFF) {
		List<TramiteTrafMatrDto> lista = new ArrayList<TramiteTrafMatrDto>();
		log.info("FORMATOGAMatwDtoConversion: ENTRA");
		for (int i = 0; i < ga.getMATRICULACION().size(); i++) {
			VehiculoDto vehiculo = new VehiculoDto();
			IntervinienteTraficoDto titular = new IntervinienteTraficoDto();
			IntervinienteTraficoDto representanteTitular = new IntervinienteTraficoDto();
			IntervinienteTraficoDto conductorHabitual = new IntervinienteTraficoDto();
			IntervinienteTraficoDto arrendatario = new IntervinienteTraficoDto();
			IntervinienteTraficoDto representanteArrendatario = new IntervinienteTraficoDto();
			TramiteTrafMatrDto tramite = new TramiteTrafMatrDto();

			if (null != ga.getMATRICULACION().get(i)) {
				if (null != ga.getMATRICULACION().get(i).getDATOSVEHICULO())
					vehiculo = convertirVehiculoGAtoDto(ga, ga.getMATRICULACION().get(i));
				if (null != ga.getMATRICULACION().get(i).getDATOSTITULAR())
					titular = convertirIntervinienteGAtoDto(ga, TipoInterviniente.Titular.getValorEnum(), ga.getMATRICULACION().get(i));
				if (null != ga.getMATRICULACION().get(i).getDATOSREPRESENTANTETITULAR())
					representanteTitular = convertirIntervinienteGAtoDto(ga, TipoInterviniente.RepresentanteTitular.getValorEnum(), ga.getMATRICULACION().get(i));
				if (null != ga.getMATRICULACION().get(i).getDATOSCONDUCTORHABITUAL())
					conductorHabitual = convertirIntervinienteGAtoDto(ga, TipoInterviniente.ConductorHabitual.getValorEnum(), ga.getMATRICULACION().get(i));
				if (null != ga.getMATRICULACION().get(i).getDATOSARRENDATARIO())
					arrendatario = convertirIntervinienteGAtoDto(ga, TipoInterviniente.Arrendatario.getValorEnum(), ga.getMATRICULACION().get(i));
				if (null != ga.getMATRICULACION().get(i).getDATOSREPRESENTANTEARRENDATARIO())
					representanteArrendatario = convertirIntervinienteGAtoDto(ga, TipoInterviniente.RepresentanteArrendatario.getValorEnum(), ga.getMATRICULACION().get(i));
				tramite = convertirTramiteGAtoDto(ga, ga.getMATRICULACION().get(i), idContrato, contratoImportacion);

				if(tienePermisoLiberarEEFF){
					convertirLiberacionEEFFGAtoDto(ga.getMATRICULACION().get(i), tramite);
				}
			}

			if (tramite != null) {
				tramite.setVehiculoDto(vehiculo);
				tramite.setTitular(titular);
				tramite.setRepresentanteTitular(representanteTitular);
				tramite.setConductorHabitual(conductorHabitual);
				tramite.setArrendatario(arrendatario);
				tramite.setRepresentanteArrendatario(representanteArrendatario);
				lista.add(tramite);
			}
		}
		return lista;
	}

	private void convertirLiberacionEEFFGAtoDto(MATRICULACION matriculacion, TramiteTrafMatrDto tramite) {
		try {
			if(matriculacion != null && tramite != null){
				LiberacionEEFFDto liberacionEEFFDto = new LiberacionEEFFDto();
				if("NO".equalsIgnoreCase(matriculacion.getEXENTOLIBERAR())){
					liberacionEEFFDto.setExento(Boolean.FALSE);
					if(matriculacion.getDATOSLIBERACION() != null) {
						liberacionEEFFDto.setFirCif(matriculacion.getDATOSLIBERACION().getCIFFIR());
						if(matriculacion.getDATOSLIBERACION().getFECHAFACTURA() != null && !matriculacion.getDATOSLIBERACION().getFECHAFACTURA().isEmpty()){
								liberacionEEFFDto.setFechaFactura(new Fecha(new SimpleDateFormat("dd/MM/yyyy").parse(matriculacion.getDATOSLIBERACION().getFECHAFACTURA())));
						}
						if(matriculacion.getDATOSLIBERACION().getFECHAREALIZACION() != null && !matriculacion.getDATOSLIBERACION().getFECHAREALIZACION().isEmpty()){
							liberacionEEFFDto.setFechaRealizacion(new Fecha(new SimpleDateFormat("dd/MM/yyyy").parse(matriculacion.getDATOSLIBERACION().getFECHAREALIZACION())));
						}
						if(matriculacion.getDATOSLIBERACION().getIMPORTE() != null && !matriculacion.getDATOSLIBERACION().getIMPORTE().isEmpty()){
							liberacionEEFFDto.setImporte(new BigDecimal(matriculacion.getDATOSLIBERACION().getIMPORTE()));
						}
						liberacionEEFFDto.setFirMarca(matriculacion.getDATOSLIBERACION().getMARCAFIR());
						liberacionEEFFDto.setNifRepresentado(matriculacion.getDATOSLIBERACION().getNIFREPRESENTADO());
						liberacionEEFFDto.setNumFactura(matriculacion.getDATOSLIBERACION().getNUMFACTURA());
					}

				} else {
					liberacionEEFFDto.setExento(Boolean.TRUE);
				}
				tramite.setLiberacionEEFF(liberacionEEFFDto);
			}
		} catch (Throwable e) {
			log.error("Ha sucedido un error a la hora de convertir los datos de liberacion, error: ",e);
		}
	}

	private TramiteTrafMatrDto convertirTramiteGAtoDto(FORMATOGA ga, MATRICULACION mat, BigDecimal idContrato, BigDecimal contratoImportacion) {
		TramiteTrafMatrDto tramite = new TramiteTrafMatrDto();

		try {
			if ("OEGAM".equals(ga.getPlataforma()))
				tramite.setNumExpediente(new BigDecimal(mat.getNUMEROEXPEDIENTE()));
			else
				tramite.setNumExpediente(null);
		} catch (Exception e) {
			tramite.setNumExpediente(null);
		}

		if (contratoImportacion == null) {
			tramite.setIdContrato(idContrato);
		} else {
			tramite.setIdContrato(contratoImportacion);
		}
		try {
			if (mat.getNUMEROPROFESIONAL() != null) {
				String valor = mat.getNUMEROPROFESIONAL().toString();
				if (valor.length() == 5) {
					valor = valor.substring(0, 4);
				}
				while (valor.length() < 4) {
					valor = "0" + valor;
				}
				tramite.setNumColegiado(valor);
			}
		} catch (Exception e) {
			tramite.setNumColegiado("");
		}

		if (mat.getFECHAPRESENTACION() != null && !mat.getFECHAPRESENTACION().isEmpty()) {
			try {
				Fecha fechaPresentacion = new Fecha(mat.getFECHAPRESENTACION());
				if (fechaPresentacion != null) {
					tramite.setFechaPresentacion(utilesFecha.getFechaFracionada(utilesFecha.DDMMAAAAToTimestamp(mat.getFECHAPRESENTACION())));
				}
			} catch (Exception pe) {
				log.error("Error de importacion en 'Fecha presentacion' : " + pe);
			}
		}

		tramite.setRefPropia(mat.getREFERENCIAPROPIA());
		tramite.setAnotaciones(mat.getOBSERVACIONES());

		if (mat.getENTREGAFACTMATRICULACION() != null) {
			tramite.setEntregaFactMatriculacion(mat.getENTREGAFACTMATRICULACION().equalsIgnoreCase("SI"));
		} else {
			tramite.setEntregaFactMatriculacion(false);
		}

		if (null != mat.getDATOSARRENDATARIO() && !mat.getDATOSARRENDATARIO().getDNIARR().isEmpty()) {
			tramite.setRenting(true);
		} else {
			tramite.setRenting(false);
		}

		if (null != mat.getDATOSARRENDATARIO() && mat.getDATOSARRENDATARIO().getDNIARR() != null && !mat.getDATOSARRENDATARIO().getDNIARR().isEmpty()) {
			tramite.setRenting(mat.getDATOSVEHICULO().getRENTING().equalsIgnoreCase("SI"));
		} else {
			tramite.setRenting(false);
		}

		if ("SI".equalsIgnoreCase(mat.getDATOSVEHICULO().getCarsharing())) {
			tramite.setCarsharing(true);
		} else {
			tramite.setCarsharing(false);
		}

		if (null != mat.getDATOSLIMITACION()) {
			tramite.setIedtm("E".equals(mat.getDATOSLIMITACION().getTIPOLIMITACION()) ? "E" : "");
			tramite.setFechaIedtm(utilesFecha.getFechaFracionada(utilesFecha.DDMMAAAAToTimestamp(mat.getDATOSLIMITACION().getFECHALIMITACION())));
			if (mat.getDATOSLIMITACION().getNUMEROREGISTROLIMITACION() != null && !mat.getDATOSLIMITACION().getNUMEROREGISTROLIMITACION().isEmpty()) {
				tramite.setRegIedtm(mat.getDATOSLIMITACION().getNUMEROREGISTROLIMITACION());
			}
			tramite.setFinancieraIedtm(mat.getDATOSLIMITACION().getFINANCIERALIMITACION());
		}

		if (mat.getDATOSIMPUESTOS() != null && mat.getDATOSIMPUESTOS().getCODIGOELECTRONICOAEAT() != null) {
			tramite.setCem(mat.getDATOSIMPUESTOS().getCODIGOELECTRONICOAEAT());
		}

		if (mat.getDATOSIMPUESTOS() != null && mat.getDATOSIMPUESTOS().getCEMA() != null && !mat.getDATOSIMPUESTOS().getCEMA().isEmpty()) {
			tramite.setCema(mat.getDATOSIMPUESTOS().getCEMA());
		}

		if (mat.getDATOSIMPUESTOS() != null && mat.getDATOSIMPUESTOS().getJUSTIFICADOIVTM() != null && !mat.getDATOSIMPUESTOS().getJUSTIFICADOIVTM().isEmpty()
				&& "SI".equalsIgnoreCase(mat.getDATOSIMPUESTOS().getJUSTIFICADOIVTM())) {
			tramite.setJustificadoIvtm(true);
		} else {
			tramite.setJustificadoIvtm(false);
		}

		if (mat.getDATOSIMPUESTOS() != null && mat.getDATOSIMPUESTOS().getDATOSIMVTM() != null) {
			IvtmMatriculacionDto ivtmMatriculacionDto = new IvtmMatriculacionDto();
			ivtmMatriculacionDto.setFechaPago(utilesFecha.getFechaFracionada(utilesFecha.DDMMAAAAToTimestamp(mat.getDATOSIMPUESTOS().getDATOSIMVTM().getFECHAALTAAUTOLIQUIDACIONIMVTM())));
			ivtmMatriculacionDto.setImporte(mat.getDATOSIMPUESTOS().getDATOSIMVTM().getCUOTAAPAGARIMVTM());
			ivtmMatriculacionDto.setNrc(mat.getDATOSIMPUESTOS().getDATOSIMVTM().getNRC());
			ivtmMatriculacionDto.setEstadoIvtm(new BigDecimal(EstadoIVTM.Ivtm_Importado.getValorEnum()));
			tramite.setIvtmMatriculacionDto(ivtmMatriculacionDto);
		}

		DATOS576 datos576 = null;
		if (mat.getDATOSIMPUESTOS() != null) {
			datos576 = mat.getDATOSIMPUESTOS().getDATOS576();
		}

		if (datos576 != null) {
			tramite.setBaseImponible576(datos576.getBASEIMPONIBLE576());
			tramite.setBaseImpoReducida576(datos576.getBASEIMPONIBLEREDUCIDA576());
			tramite.setTipoGravamen576(datos576.getTIPOGRAVAMEN576());
			tramite.setCuota576(datos576.getCUOTA576());
			tramite.setDeduccionLineal576(datos576.getDEDUCCIONLINEAL576());
			tramite.setCuotaIngresar576(datos576.getCUOTAINGRESAR576());
			tramite.setDeducir576(datos576.getADEDUCIR576());
			tramite.setImporte576(datos576.getIMPORTETOTAL576());
			tramite.setLiquidacion576(datos576.getRESULTADOLIQUIDACION576());
			tramite.setNumDeclaracionComp576(datos576.getDECLARACIONCOMPLEMENTARIA576());
			tramite.setCausaHechoImpon576(datos576.getCAUSAHECHOIMPONIBLE());
			tramite.setObservaciones576(datos576.getOBSERVACIONES576());

			if (datos576.getEXENTO576() != null && !datos576.getEXENTO576().isEmpty() && "SI".equalsIgnoreCase(datos576.getEXENTO576())) {
				tramite.setExento576(true);
			} else {
				tramite.setExento576(false);
			}

			if (null != tramite.getCuota576() && 0 == tramite.getCuota576().compareTo(BigDecimal.ZERO) && !tramite.getExento576()) {
				tramite.setExento576(true);
			}
			if (datos576.getEJERCICIODEVENGO576() != null && !"-1".equals(datos576.getEJERCICIODEVENGO576())) {
				tramite.setEjercicio576(new BigDecimal(datos576.getEJERCICIODEVENGO576()));
			}

		}
		if (mat.getDATOSIMPUESTOS() != null && mat.getDATOSIMPUESTOS().getDATOSNRC() != null) {
			tramite.setNrc576(mat.getDATOSIMPUESTOS().getDATOSNRC().getCODIGONRC());
			if (mat.getDATOSIMPUESTOS().getDATOSNRC().getFECHAOPERACIONNRC() != null)
				tramite.setFechaPago576(utilesFecha.getFechaFracionada(utilesFecha.DDMMAAAAToTimestamp(mat.getDATOSIMPUESTOS().getDATOSNRC().getFECHAOPERACIONNRC())));
		}

		if (mat.getDATOSIMPUESTOS() != null && mat.getDATOSIMPUESTOS().getDATOS0506() != null && mat.getDATOSIMPUESTOS().getDATOS0506().getMOTIVOEXENCION05() != null) {
			tramite.setIdReduccion05(mat.getDATOSIMPUESTOS().getDATOS0506().getMOTIVOEXENCION05().value());
		}

		if ((tramite.getIdReduccion05() == null || tramite.getIdReduccion05().isEmpty()) && (tramite.getRegIedtm() != null && !tramite.getRegIedtm().isEmpty())) {
			tramite.setIdReduccion05(conversion.numRegistroLimitacionToMotivo(tramite.getRegIedtm(), "05"));
		}

		if (mat.getDATOSIMPUESTOS() != null && mat.getDATOSIMPUESTOS().getDATOS0506() != null && mat.getDATOSIMPUESTOS().getDATOS0506().getMOTIVOEXENCION06() != null) {
			tramite.setIdNoSujeccion06(mat.getDATOSIMPUESTOS().getDATOS0506().getMOTIVOEXENCION06().value());
		}

		if ((tramite.getIdNoSujeccion06() == null || tramite.getIdNoSujeccion06().isEmpty()) && (tramite.getRegIedtm() != null && !tramite.getRegIedtm().isEmpty())) {
			tramite.setIdNoSujeccion06(conversion.numRegistroLimitacionToMotivo(tramite.getRegIedtm(), "06"));
		}

		if (mat.getDATOSIMPUESTOS() != null && (mat.getDATOSIMPUESTOS().getEXENTOCEM() != null) && !(mat.getDATOSIMPUESTOS().getEXENTOCEM().isEmpty())
				&& (("SI".equalsIgnoreCase(mat.getDATOSIMPUESTOS().getEXENTOCEM().trim())) || ("S".equalsIgnoreCase(mat.getDATOSIMPUESTOS().getEXENTOCEM().trim())))) {
			tramite.setExentoCem(true);
		} else {
			tramite.setExentoCem(false);
		}
		tramite.setTipoTramiteMatr(TipoTramiteMatriculacion.MATRICULAR_TIPO_DEFINITIVA.getValorEnum());

		TasaDto tasa = new TasaDto();
		tasa.setCodigoTasa(mat.getTASA());
		tasa.setTipoTasa(mat.getTIPOTASA());
		tramite.setTasa(tasa);

		if (mat.getJEFATURA() != null) {
			JefaturaTraficoDto jefatura = new JefaturaTraficoDto();
			jefatura.setJefaturaProvincial(mat.getJEFATURA().value());
			tramite.setJefaturaTraficoDto(jefatura);
		}

		return tramite;
	}

	private IntervinienteTraficoDto convertirIntervinienteGAtoDto(FORMATOGA ga, String tipoInterviniente, MATRICULACION mat) {
		IntervinienteTraficoDto interviniente = new IntervinienteTraficoDto();
		PersonaDto persona = new PersonaDto();

		if (TipoInterviniente.Titular.getValorEnum().equals(tipoInterviniente)) {
			DATOSTITULAR titular = mat.getDATOSTITULAR();

			// INFORMACION DEL INTERVINIENTE
			interviniente.setTipoInterviniente(TipoInterviniente.Titular.getValorEnum());
			if (titular.getCAMBIODOMICILIOTITULAR() != null && !titular.getCAMBIODOMICILIOTITULAR().isEmpty()
					&& (("SI").equalsIgnoreCase(titular.getCAMBIODOMICILIOTITULAR().trim()) || (TipoSN.S.value()).equals(titular.getCAMBIODOMICILIOTITULAR().trim().toUpperCase()))) {
				interviniente.setCambioDomicilio(true);
			} else {
				interviniente.setCambioDomicilio(false);
			}

			if (titular.getAUTONOMO() != null && !titular.getAUTONOMO().isEmpty()
					&& (("SI").equalsIgnoreCase(titular.getAUTONOMO().trim()) || (TipoSN.S.value()).equals(titular.getAUTONOMO().trim().toUpperCase()))) {
				interviniente.setAutonomo(true);
			} else {
				interviniente.setAutonomo(false);
			}

			interviniente.setCodigoIAE(titular.getCODIGOIAE());

			try {
				if (mat.getNUMEROPROFESIONAL() != null) {
					String valor = mat.getNUMEROPROFESIONAL().toString();
					if (valor.length() == 5) {
						valor = valor.substring(0, 4);
					}
					while (valor.length() < 4) {
						valor = "0" + valor;
					}
					interviniente.setNumColegiado(valor);
					persona.setNumColegiado(valor);
				}
			} catch (Exception e) {
				interviniente.setNumColegiado(null);
				persona.setNumColegiado(null);
			}
			interviniente.setNifInterviniente(titular.getDNITITULAR());

			persona.setNif(titular.getDNITITULAR());
			persona.setApellido1RazonSocial(titular.getAPELLIDO1TITULAR());
			if (persona.getApellido1RazonSocial() == null || persona.getApellido1RazonSocial().isEmpty()) {
				persona.setApellido1RazonSocial(titular.getRAZONSOCIALTITULAR());
			}

			persona.setApellido2(titular.getAPELLIDO2TITULAR());
			persona.setNombre(titular.getNOMBRETITULAR());
			if (conversion.isNifNie(persona.getNif()))
				persona.setAnagrama(titular.getANAGRAMATITULAR());

			persona.setFechaNacimiento(utilesFecha.getFechaFracionada(utilesFecha.DDMMAAAAToTimestamp(titular.getFECHANACIMIENTOTITULAR())));
			persona.setFechaCaducidadNif(utilesFecha.getFechaFracionada(utilesFecha.DDMMAAAAToTimestamp(titular.getFECHACADUCIDADNIFTITULAR())));
			persona.setFechaCaducidadAlternativo(utilesFecha.getFechaFracionada(utilesFecha.DDMMAAAAToTimestamp(titular.getFECHACADUCIDADDOCUMENTOSUSTITUTIVOTITULAR())));
			persona.setTipoDocumentoAlternativo(titular.getTIPODOCUMENTOSUSTITUTIVOTITULAR());

			if (titular.getINDEFINIDOTITULAR() != null && !titular.getINDEFINIDOTITULAR().isEmpty()
					&& (("SI").equalsIgnoreCase(titular.getINDEFINIDOTITULAR().trim()) || (TipoSN.S.value()).equals(titular.getINDEFINIDOTITULAR().trim().toUpperCase()))) {
				persona.setIndefinido(true);
			} else {
				persona.setIndefinido(false);
			}

			persona.setTelefonos(titular.getTELEFONOTITULAR());
			persona.setCorreoElectronico(titular.getCORREOELECTRONICOTITULAR());
			persona.setSexo(titular.getSEXOTITULAR() != null ? titular.getSEXOTITULAR().name() : "");

			interviniente.setPersona(persona);

			DireccionDto direccion = new DireccionDto();

			// INFORMACIÓN DE LA DIRECCION
			direccion.setIdProvincia(titular.getPROVINCIATITULAR() != null ? conversion.getIdProvinciaFromSiglas(titular.getPROVINCIATITULAR().name()) : "");
			direccion.setMunicipio(titular.getMUNICIPIOTITULAR());
			direccion.setIdTipoVia("");
			direccion.setVia(titular.getNOMBREVIADIRECCIONTITULAR());
			direccion.setNumero(titular.getNUMERODIRECCIONTITULAR());
			direccion.setCodPostalCorreos(titular.getCPTITULAR());
			direccion.setLetra(titular.getLETRADIRECCIONTITULAR());
			direccion.setEscalera(titular.getESCALERADIRECCIONTITULAR());
			direccion.setBloque(titular.getBLOQUEDIRECCIONTITULAR());
			direccion.setPlanta(titular.getPISODIRECCIONTITULAR());
			direccion.setPuerta(titular.getPUERTADIRECCIONTITULAR());
			try {
				direccion.setKm(new BigDecimal(titular.getKMDIRECCIONTITULAR()));
			} catch (Exception e) {
				direccion.setKm(null);
			}
			try {
				direccion.setHm(new BigDecimal(titular.getHECTOMETRODIRECCIONTITULAR()));
			} catch (Exception e) {
				direccion.setHm(null);
			}
			direccion.setAsignarPrincipal(titular.getDIRECCIONACTIVATITULAR());

			// INFORMACIÓN PARA LAS IMPORTACIONES
			String valor = titular.getSIGLASDIRECCIONTITULAR();
			if (valor == null)
				valor = "";
			if ("6".equals(valor.trim()))
				valor = "41"; // Calle
			if ("44".equals(valor.trim()))
				valor = "5";
			if ("24".equals(valor.trim()))
				valor = "29";
			if ("34".equals(valor.trim()))
				valor = "29";

			direccion.setIdTipoDgt(valor.trim());
			direccion.setPuebloLit(titular.getPUEBLOTITULAR());

			interviniente.setDireccion(direccion);

		} else if (TipoInterviniente.RepresentanteTitular.getValorEnum().equals(tipoInterviniente)) {

			DATOSREPRESENTANTETITULAR representante = mat.getDATOSREPRESENTANTETITULAR();

			interviniente.setTipoInterviniente(TipoInterviniente.RepresentanteTitular.getValorEnum());
			interviniente.setFechaFin(utilesFecha.getFechaFracionada(utilesFecha.DDMMAAAAToTimestamp(representante.getFECHAFINTUTELA())));

			if (representante.getCONCEPTOREPTITULAR() != null && !representante.getCONCEPTOREPTITULAR().isEmpty()) {
				interviniente.setConceptoRepre(representante.getCONCEPTOREPTITULAR());
				if (ConceptoTutela.convertir(representante.getCONCEPTOREPTITULAR()) == ConceptoTutela.Tutela &&
					representante.getMOTIVOTUTELA() != null) {
						interviniente.setIdMotivoTutela(representante.getMOTIVOTUTELA().value());
				}
			}
			interviniente.setDatosDocumento(representante.getACREDITACIONREPTITULAR());

			try {
				if (mat.getNUMEROPROFESIONAL() != null) {
					String valor = mat.getNUMEROPROFESIONAL().toString();
					if (valor.length() == 5) {
						valor = valor.substring(0, 4);
					}
					while (valor.length() < 4) {
						valor = "0" + valor;
					}
					interviniente.setNumColegiado(valor);
					persona.setNumColegiado("");
				}
			} catch (Exception e) {
				interviniente.setNumColegiado("");
				persona.setNumColegiado("");
			}
			interviniente.setNifInterviniente(representante.getDNIREP());

			persona.setNif(representante.getDNIREP());

			persona.setFechaNacimiento(utilesFecha.getFechaFracionada(utilesFecha.DDMMAAAAToTimestamp(representante.getFECHANACIMIENTOREP())));
			persona.setFechaCaducidadNif(utilesFecha.getFechaFracionada(utilesFecha.DDMMAAAAToTimestamp(representante.getFECHACADUCIDADNIFREPRESENTANTETITULAR())));
			persona.setFechaCaducidadAlternativo(utilesFecha.getFechaFracionada(utilesFecha.DDMMAAAAToTimestamp(representante.getFECHACADUCIDADDOCUMENTOSUSTITUTIVOREPRESENTANTETITULAR())));
			persona.setTipoDocumentoAlternativo(representante.getTIPODOCUMENTOSUSTITUTIVOREPRESENTANTETITULAR());
			if (representante.getINDEFINIDOREPRESENTANTETITULAR() != null
					&& !representante.getINDEFINIDOREPRESENTANTETITULAR().isEmpty()
					&& (("SI").equalsIgnoreCase(representante.getINDEFINIDOREPRESENTANTETITULAR().trim()) || (TipoSN.S.value()).equals(representante.getINDEFINIDOREPRESENTANTETITULAR().trim()
							.toUpperCase()))) {
				persona.setIndefinido(true);
			} else {
				persona.setIndefinido(false);
			}

			persona.setApellido1RazonSocial(representante.getAPELLIDO1REP());
			persona.setApellido2(representante.getAPELLIDO2REP());
			persona.setNombre(representante.getNOMBREREP());

			if (persona.getNif() != null && persona.getApellido1RazonSocial() != null && !persona.getNif().isEmpty() && !persona.getApellido1RazonSocial().isEmpty()
					&& conversion.isNifNie(persona.getNif())) {
				persona.setAnagrama(Anagrama.obtenerAnagramaFiscal(persona.getApellido1RazonSocial(), persona.getNif()));
			}

			interviniente.setPersona(persona);

			DireccionDto direccion = new DireccionDto();
			// INFORMACIÓN PARA LAS IMPORTACIONES
			String valor = representante.getSIGLASDIRECCIONREP();
			if (valor == null)
				valor = "";
			if ("6".equals(valor.trim()))
				valor = "41"; // Calle
			if ("44".equals(valor.trim()))
				valor = "5";
			if ("24".equals(valor.trim()))
				valor = "29";
			if ("34".equals(valor.trim()))
				valor = "29";
			direccion.setIdTipoDgt(valor.trim());

			direccion.setMunicipio(representante.getMUNICIPIOREP());

			if (representante.getPROVINCIAREP() != null) {
				direccion.setIdProvincia(conversion.getIdProvinciaFromSiglas(representante.getPROVINCIAREP().name()));
			}

			if (representante.getCPREP() != null) {
				direccion.setCodPostalCorreos(representante.getCPREP());
			}

			if (representante.getNUMERODIRECCIONREP() != null) {
				direccion.setNumero(representante.getNUMERODIRECCIONREP());
			}

			if (representante.getPUEBLOREP() != null) {
				direccion.setPuebloLit(representante.getPUEBLOREP());
			}

			if (representante.getNOMBREVIADIRECCIONREP() != null) {
				direccion.setVia(representante.getNOMBREVIADIRECCIONREP());
			}

			direccion.setPlanta(representante.getPISODIRECCIONREP());
			direccion.setLetra(representante.getLETRADIRECCIONREP());
			direccion.setEscalera(representante.getESCALERADIRECCIONREP());
			direccion.setBloque(representante.getBLOQUEDIRECCIONREP());
			direccion.setPlanta(representante.getPISODIRECCIONREP());
			direccion.setPuerta(representante.getPUERTADIRECCIONREP());
			try {
				direccion.setKm(new BigDecimal(representante.getKMDIRECCIONREP()));
			} catch (Exception e) {
				direccion.setKm(null);
			}
			try {
				direccion.setHm(new BigDecimal(representante.getHECTOMETRODIRECCIONREP()));
			} catch (Exception e) {
				direccion.setHm(null);
			}

			direccion.setAsignarPrincipal(representante.getDIRECCIONACTIVAREP());

			interviniente.setDireccion(direccion);
		} else if (TipoInterviniente.ConductorHabitual.getValorEnum().equals(tipoInterviniente)) {
			DATOSCONDUCTORHABITUAL conductor = mat.getDATOSCONDUCTORHABITUAL();

			// INFORMACION DEL INTERVINIENTE
			interviniente.setTipoInterviniente(TipoInterviniente.ConductorHabitual.getValorEnum());
			if (conductor.getCAMBIODOMICILIOCONDHABITUAL() != null && !conductor.getCAMBIODOMICILIOCONDHABITUAL().isEmpty()
					&& (("SI").equalsIgnoreCase(conductor.getCAMBIODOMICILIOCONDHABITUAL().trim()) || (TipoSN.S.value()).equals(conductor.getCAMBIODOMICILIOCONDHABITUAL().trim().toUpperCase()))) {
				interviniente.setCambioDomicilio(true);
			} else {
				interviniente.setCambioDomicilio(false);
			}
			interviniente.setFechaFin(utilesFecha.getFechaFracionada(utilesFecha.DDMMAAAAToTimestamp(conductor.getFECHAFINCONDHABITUAL())));
			interviniente.setHoraFin(conductor.getHORAFINCONDHABITUAL());

			try {
				if (mat.getNUMEROPROFESIONAL() != null) {
					String valor = mat.getNUMEROPROFESIONAL().toString();
					if (valor.length() == 5) {
						valor = valor.substring(0, 4);
					}
					while (valor.length() < 4) {
						valor = "0" + valor;
					}
					interviniente.setNumColegiado(valor);
					persona.setNumColegiado(valor);
				}
			} catch (Exception e) {
				interviniente.setNumColegiado(null);
				persona.setNumColegiado(null);
			}
			interviniente.setNifInterviniente(conductor.getDNICONDHABITUAL());

			persona.setNif(conductor.getDNICONDHABITUAL());

			persona.setFechaCaducidadNif(utilesFecha.getFechaFracionada(utilesFecha.DDMMAAAAToTimestamp(conductor.getFECHACADUCIDADNIFCONDHABITUAL())));
			persona.setFechaCaducidadAlternativo(utilesFecha.getFechaFracionada(utilesFecha.DDMMAAAAToTimestamp(conductor.getFECHACADUCIDADDOCUMENTOSUSTITUTIVOCONDHABITUAL())));
			persona.setTipoDocumentoAlternativo(conductor.getTIPODOCUMENTOSUSTITUTIVOCONDHABITUAL());

			if (conductor.getINDEFINIDOCONDHABITUAL() != null && !conductor.getINDEFINIDOCONDHABITUAL().isEmpty()
					&& (("SI").equalsIgnoreCase(conductor.getINDEFINIDOCONDHABITUAL().trim()) || (TipoSN.S.value()).equals(conductor.getINDEFINIDOCONDHABITUAL().trim().toUpperCase()))) {
				persona.setIndefinido(true);
			} else {
				persona.setIndefinido(false);
			}

			persona.setApellido1RazonSocial(conductor.getAPELLIDO1CONDHABITUAL());
			persona.setApellido2(conductor.getAPELLIDO2CONDHABITUAL());
			persona.setNombre(conductor.getNOMBRECONDHABITUAL());
			if (persona.getApellido1RazonSocial().isEmpty())
				persona.setApellido1RazonSocial(conductor.getRAZONSOCIALCONDHABITUAL());
			persona.setFechaNacimiento(utilesFecha.getFechaFracionada(utilesFecha.DDMMAAAAToTimestamp(conductor.getFECHANACIMIENTOCONDHABITUAL())));

			if (conductor.getSEXOCONDHABITUAL() != null) {
				persona.setSexo(conductor.getSEXOCONDHABITUAL().name());
			} else {
				persona.setSexo(null);
			}

			interviniente.setPersona(persona);

			DireccionDto direccion = new DireccionDto();

			// INFORMACIÓN DE LA DIRECCION
			if (conductor.getPROVINCIACONDHABITUAL() != null) {
				direccion.setIdProvincia(conversion.getIdProvinciaFromSiglas(conductor.getPROVINCIACONDHABITUAL().name()));
			} else {
				direccion.setIdProvincia(null);
			}

			direccion.setMunicipio(conductor.getMUNICIPIOCONDHABITUAL());

			direccion.setIdTipoVia("");
			direccion.setNumero(conductor.getNUMERODIRECCIONCONDHABITUAL());
			direccion.setCodPostalCorreos(conductor.getCPCONDHABITUAL());
			direccion.setVia(conductor.getNOMBREVIADIRECCIONCONDHABITUAL());
			direccion.setLetra(conductor.getLETRADIRECCIONCONDHABITUAL());
			direccion.setEscalera(conductor.getESCALERADIRECCIONCONDHABITUAL());
			direccion.setBloque(conductor.getBLOQUEDIRECCIONCONDHABITUAL());
			direccion.setPlanta(conductor.getPISODIRECCIONCONDHABITUAL());
			direccion.setPuerta(conductor.getPUERTADIRECCIONCONDHABITUAL());
			try {
				direccion.setKm(new BigDecimal(conductor.getKMDIRECCIONCONDHABITUAL()));
			} catch (Exception e) {
				direccion.setKm(null);
			}
			try {
				direccion.setHm(new BigDecimal(conductor.getHECTOMETRODIRECCIONCONDHABITUAL()));
			} catch (Exception e) {
				direccion.setHm(null);
			}

			// INFORMACIÓN PARA LAS IMPORTACIONES
			String valor = conductor.getSIGLASDIRECCIONCONDHABITUAL();
			if (valor == null)
				valor = "";
			if ("6".equals(valor.trim()))
				valor = "41"; // Calle
			if ("44".equals(valor.trim()))
				valor = "5";
			if ("24".equals(valor.trim()))
				valor = "29";
			if ("34".equals(valor.trim()))
				valor = "29";
			direccion.setIdTipoDgt(valor.trim());
			direccion.setPuebloLit(conductor.getPUEBLOCONDHABITUAL());

			direccion.setAsignarPrincipal(conductor.getDIRECCIONACTIVACONDHABITUAL());

			interviniente.setDireccion(direccion);

		} else if (TipoInterviniente.Arrendatario.getValorEnum().equals(tipoInterviniente)) {
			DATOSARRENDATARIO arrendatario = mat.getDATOSARRENDATARIO();

			// INFORMACION DEL INTERVINIENTE
			interviniente.setTipoInterviniente(TipoInterviniente.Arrendatario.getValorEnum());

			if (arrendatario.getCAMBIODOMICILIOARR() != null && !arrendatario.getCAMBIODOMICILIOARR().isEmpty()
					&& (("SI").equalsIgnoreCase(arrendatario.getCAMBIODOMICILIOARR().trim()) || (TipoSN.S.value()).equals(arrendatario.getCAMBIODOMICILIOARR().trim().toUpperCase()))) {
				interviniente.setCambioDomicilio(true);
			} else {
				interviniente.setCambioDomicilio(false);
			}
			interviniente.setFechaInicio(utilesFecha.getFechaFracionada(utilesFecha.DDMMAAAAToTimestamp(arrendatario.getFECHAINICIORENTING())));
			interviniente.setFechaFin(utilesFecha.getFechaFracionada(utilesFecha.DDMMAAAAToTimestamp(arrendatario.getFECHAFINRENTING())));
			interviniente.setHoraInicio(arrendatario.getHORAINICIORENTING());
			interviniente.setHoraFin(arrendatario.getHORAFINRENTING());

			// INFORMACIÓN DE LA PERSONA ASOCIADA AL INVOLUCRADO
			try {
				if (mat.getNUMEROPROFESIONAL() != null) {
					String valor = mat.getNUMEROPROFESIONAL().toString();
					if (valor.length() == 5) {
						valor = valor.substring(0, 4);
					}
					while (valor.length() < 4) {
						valor = "0" + valor;
					}
					interviniente.setNumColegiado(valor);
					persona.setNumColegiado(valor);
				}
			} catch (Exception e) {
				interviniente.setNumColegiado(null);
				persona.setNumColegiado(null);
			}

			interviniente.setNifInterviniente(arrendatario.getDNIARR());

			persona.setNif(arrendatario.getDNIARR());

			persona.setFechaCaducidadNif(utilesFecha.getFechaFracionada(utilesFecha.DDMMAAAAToTimestamp(arrendatario.getFECHACADUCIDADNIFARR())));
			persona.setFechaCaducidadAlternativo(utilesFecha.getFechaFracionada(utilesFecha.DDMMAAAAToTimestamp(arrendatario.getFECHACADUCIDADDOCUMENTOSUSTITUTIVOARR())));
			persona.setTipoDocumentoAlternativo(arrendatario.getTIPODOCUMENTOSUSTITUTIVOARR());

			if (arrendatario.getINDEFINIDOARR() != null && !arrendatario.getINDEFINIDOARR().isEmpty()
					&& (("SI").equalsIgnoreCase(arrendatario.getINDEFINIDOARR().trim()) || (TipoSN.S.value()).equals(arrendatario.getINDEFINIDOARR().trim().toUpperCase()))) {
				persona.setIndefinido(true);
			} else {
				persona.setIndefinido(false);
			}

			persona.setApellido1RazonSocial(arrendatario.getAPELLIDO1ARR());
			persona.setApellido2(arrendatario.getAPELLIDO2ARR());
			persona.setNombre(arrendatario.getNOMBREARR());
			if (persona.getApellido1RazonSocial() == null || persona.getApellido1RazonSocial().isEmpty())
				persona.setApellido1RazonSocial(arrendatario.getRAZONSOCIALARR());
			persona.setFechaNacimiento(utilesFecha.getFechaFracionada(utilesFecha.DDMMAAAAToTimestamp(arrendatario.getFECHANACIMIENTOARR())));

			if (arrendatario.getSEXOARR() != null) {
				persona.setSexo(arrendatario.getSEXOARR().name());
			} else {
				persona.setSexo(null);
			}

			interviniente.setPersona(persona);

			DireccionDto direccion = new DireccionDto();

			// INFORMACIÓN DE LA DIRECCION
			if (arrendatario.getPROVINCIAARR() != null) {
				direccion.setIdProvincia(conversion.getIdProvinciaFromSiglas(arrendatario.getPROVINCIAARR().name()));
			} else {
				direccion.setIdProvincia(null);
			}

			direccion.setMunicipio(arrendatario.getMUNICIPIOARR());
			direccion.setIdTipoVia("");
			direccion.setVia(arrendatario.getNOMBREVIADIRECCIONARR());
			direccion.setNumero(arrendatario.getNUMERODIRECCIONARR());
			direccion.setCodPostalCorreos(arrendatario.getCPARR());
			direccion.setLetra(arrendatario.getLETRADIRECCIONARR());
			direccion.setEscalera(arrendatario.getESCALERADIRECCIONARR());
			direccion.setBloque(arrendatario.getBLOQUEDIRECCIONARR());
			direccion.setPlanta(arrendatario.getPISODIRECCIONARR());
			direccion.setPuerta(arrendatario.getPUERTADIRECCIONARR());
			try {
				direccion.setKm(new BigDecimal(arrendatario.getKMDIRECCIONARR()));
			} catch (Exception e) {
				direccion.setKm(null);
			}
			try {
				direccion.setHm(new BigDecimal(arrendatario.getHECTOMETRODIRECCIONARR()));
			} catch (Exception e) {
				direccion.setHm(null);
			}

			// INFORMACIÓN PARA LAS IMPORTACIONES
			String valor = arrendatario.getSIGLASDIRECCIONARR();
			if (valor == null)
				valor = "";
			if ("6".equals(valor.trim()))
				valor = "41"; // calle
			if ("44".equals(valor.trim()))
				valor = "5";
			if ("24".equals(valor.trim()))
				valor = "29";
			if ("34".equals(valor.trim()))
				valor = "29";
			direccion.setIdTipoDgt(valor.trim());
			direccion.setPuebloLit(arrendatario.getPUEBLOARR());

			direccion.setAsignarPrincipal(arrendatario.getDIRECCIONACTIVAARR());

			interviniente.setDireccion(direccion);

		} else if (TipoInterviniente.RepresentanteArrendatario.getValorEnum().equals(tipoInterviniente)) {
			DATOSREPRESENTANTEARRENDATARIO reprArrendatario = mat.getDATOSREPRESENTANTEARRENDATARIO();

			interviniente.setTipoInterviniente(TipoInterviniente.RepresentanteArrendatario.getValorEnum());

			try {
				if (mat.getNUMEROPROFESIONAL() != null) {
					String valor = mat.getNUMEROPROFESIONAL().toString();
					if (valor.length() == 5) {
						valor = valor.substring(0, 4);
					}
					while (valor.length() < 4) {
						valor = "0" + valor;
					}
					interviniente.setNumColegiado(valor);
					persona.setNumColegiado(valor);
				}
			} catch (Exception e) {
				interviniente.setNumColegiado(null);
				persona.setNumColegiado(null);
			}
			interviniente.setNifInterviniente(reprArrendatario.getDNIREPRESARR());

			persona.setNif(reprArrendatario.getDNIREPRESARR());
			persona.setApellido1RazonSocial(reprArrendatario.getAPELLIDO1REPRESARR());
			persona.setApellido2(reprArrendatario.getAPELLIDO2REPRESARR());
			persona.setNombre(reprArrendatario.getNOMBREREPRESARR());

			interviniente.setPersona(persona);

		}

		if (interviniente.getDireccion() != null && (interviniente.getDireccion().getNumero() == null || interviniente.getDireccion().getNumero().isEmpty())) {
			interviniente.getDireccion().setNumero(sinNumero);
		}

		return interviniente;
	}

	private VehiculoDto convertirVehiculoGAtoDto(FORMATOGA ga, MATRICULACION mat) {
		VehiculoDto vehiculo = new VehiculoDto();
		DATOSVEHICULO vehiculoGa = mat.getDATOSVEHICULO();

		if (mat.getNUMEROPROFESIONAL() != null) {
			vehiculo.setNumColegiado(mat.getNUMEROPROFESIONAL().toString());
		}

		vehiculo.setBastidor(vehiculoGa.getNUMEROBASTIDOR());
		vehiculo.setMatricula(null);
		if (vehiculoGa.getNIVE() != null) {
			vehiculo.setNive(vehiculoGa.getNIVE().toUpperCase());
		} else {
			vehiculo.setNive(vehiculoGa.getNIVE());
		}
		vehiculo.setModelo(vehiculoGa.getMODELO());
		vehiculo.setTipoVehiculo(vehiculoGa.getTIPOVEHICULO());
		vehiculo.setTipoItv(vehiculoGa.getTIPOITV());

		if (vehiculoGa.getSERVICIODESTINO() != null) {
			ServicioTraficoDto servicio = new ServicioTraficoDto();
			servicio.setIdServicio(vehiculoGa.getSERVICIODESTINO().value());
			vehiculo.setServicioTrafico(servicio);
		}

		if (vehiculoGa.getCOLOR() != null) {
			vehiculo.setColor(vehiculoGa.getCOLOR().name());
		} else {
			vehiculo.setColor("-");
		}

		if (StringUtils.isNotBlank(vehiculoGa.getPROCEDENCIA())) {
			PaisDto pais = servicioPais.getIdPaisPorSigla(vehiculoGa.getPROCEDENCIA());
			if (pais != null && StringUtils.isNotBlank(pais.getSigla3())) {
				vehiculo.setProcedencia(pais.getSigla3());
			} else {
				vehiculo.setProcedencia(vehiculoGa.getPROCEDENCIA());
			}
		}

		if (vehiculoGa.getCLASIFICACIONITV() != null) {
			String clasificacion = String.valueOf(vehiculoGa.getCLASIFICACIONITV());
			if (clasificacion.length() == 3) {
				clasificacion = "0" + clasificacion;
			}
			vehiculo.setClasificacionItv(clasificacion);
		}

		if (vehiculoGa.getCLASIFICACIONVEHICULO() != null && vehiculoGa.getCLASIFICACIONVEHICULO().length() >= 4) {
			vehiculo.setCriterioConstruccion(vehiculoGa.getCLASIFICACIONVEHICULO().substring(0, 2));
			vehiculo.setCriterioUtilizacion(vehiculoGa.getCLASIFICACIONVEHICULO().substring(2, 4));
		}

		try {
			vehiculo.setPlazas(new BigDecimal(vehiculoGa.getPLAZAS()));
		} catch (Exception e) {
			vehiculo.setPlazas(null);
		}

		vehiculo.setCodItv(vehiculoGa.getCODIGOITV());

		vehiculo.setPotenciaFiscal(vehiculoGa.getPOTENCIAFISCAL());
		vehiculo.setPotenciaNeta(vehiculoGa.getPOTENCIANETA());

		if (vehiculoGa.getCILINDRADA() != null) {
			vehiculo.setCilindrada(vehiculoGa.getCILINDRADA().toString());
		}

		vehiculo.setPotenciaPeso(vehiculoGa.getRELACIONPOTENCIAPESO());

		if (vehiculoGa.getEMISIONCO2() != null) {
			vehiculo.setCo2(vehiculoGa.getEMISIONCO2().toString());
		}

		if (vehiculoGa.getTARA() != null) {
			vehiculo.setTara(vehiculoGa.getTARA().toString());
		}

		if (vehiculoGa.getMASA() != null) {
			vehiculo.setPesoMma(vehiculoGa.getMASA().toString());
		}

		if (vehiculoGa.getMASAMAXIMAADMISIBLE() != null) {
			vehiculo.setMtmaItv(vehiculoGa.getMASAMAXIMAADMISIBLE().toString());
		}

		vehiculo.setVersion(vehiculoGa.getVERSIONITV());
		vehiculo.setVariante(vehiculoGa.getVARIANTEITV());

		if (vehiculoGa.getFECHAITV() != null) {
			vehiculo.setFechaItv(utilesFecha.getFechaFracionada(utilesFecha.DDMMAAAAToTimestamp(vehiculoGa.getFECHAITV())));
		}

		if (vehiculoGa.getTIPOTARJETAITV() != null) {
			if (vehiculoGa.getTIPOTARJETAITV().equals(TipoTarjetaItv.A)) {
				vehiculo.setTipoTarjetaITV(TipoTarjetaItv.A.value());
			}
			if (vehiculoGa.getTIPOTARJETAITV().equals(TipoTarjetaItv.B)) {
				vehiculo.setTipoTarjetaITV(TipoTarjetaItv.B.value());
			}
			if (vehiculoGa.getTIPOTARJETAITV().equals(TipoTarjetaItv.BL)) {
				vehiculo.setTipoTarjetaITV(TipoTarjetaItv.BL.value());
			}
			if (vehiculoGa.getTIPOTARJETAITV().equals(TipoTarjetaItv.BR)) {
				vehiculo.setTipoTarjetaITV(TipoTarjetaItv.BR.value());
			}
			if (vehiculoGa.getTIPOTARJETAITV().equals(TipoTarjetaItv.BT)) {
				vehiculo.setTipoTarjetaITV(TipoTarjetaItv.BT.value());
			}
			if (vehiculoGa.getTIPOTARJETAITV().equals(TipoTarjetaItv.C)) {
				vehiculo.setTipoTarjetaITV(TipoTarjetaItv.C.value());
			}
			if (vehiculoGa.getTIPOTARJETAITV().equals(TipoTarjetaItv.D)) {
				vehiculo.setTipoTarjetaITV(TipoTarjetaItv.D.value());
			}
			if (vehiculoGa.getTIPOTARJETAITV().equals(TipoTarjetaItv.AL)) {
				vehiculo.setTipoTarjetaITV(TipoTarjetaItv.AL.value());
			}
			if (vehiculoGa.getTIPOTARJETAITV().equals(TipoTarjetaItv.AT)) {
				vehiculo.setTipoTarjetaITV(TipoTarjetaItv.AT.value());
			}
			if (vehiculoGa.getTIPOTARJETAITV().equals(TipoTarjetaItv.AR)) {
				vehiculo.setTipoTarjetaITV(TipoTarjetaItv.AR.value());
			}
			if (vehiculoGa.getTIPOTARJETAITV().equals(TipoTarjetaItv.DL)) {
				vehiculo.setTipoTarjetaITV(TipoTarjetaItv.DL.value());
			}
			if (vehiculoGa.getTIPOTARJETAITV().equals(TipoTarjetaItv.DR)) {
				vehiculo.setTipoTarjetaITV(TipoTarjetaItv.DR.value());
			}
			if (vehiculoGa.getTIPOTARJETAITV().equals(TipoTarjetaItv.DT)) {
				vehiculo.setTipoTarjetaITV(TipoTarjetaItv.DT.value());
			}
		}

		if (vehiculoGa.getPLAZASPIE() != null) {
			try {
				BigDecimal plazasPie = new BigDecimal(vehiculoGa.getPLAZASPIE());
				if (!plazasPie.equals(BigDecimal.ZERO)) {
					vehiculo.setNumPlazasPie(new BigDecimal(vehiculoGa.getPLAZASPIE()));
				} else {
					vehiculo.setNumPlazasPie(null);
				}
			} catch (NumberFormatException ex) {
				vehiculo.setNumPlazasPie(null);
			}
		}

		vehiculo.setNumHomologacion(vehiculoGa.getNUMEROHOMOLOGACIONITV());

		vehiculo.setNumSerie(vehiculoGa.getNUMEROSERIEITV());
		vehiculo.setEpigrafe(vehiculoGa.getEPIGRAFE());

		if ((vehiculoGa.getUSADO() != null) && !(vehiculoGa.getUSADO().isEmpty())
				&& (("SI".equalsIgnoreCase(vehiculoGa.getUSADO().trim())) || (TipoSN.S.value().equals(vehiculoGa.getUSADO().trim().toUpperCase())))) {
			vehiculo.setVehiUsado(true);
		} else {
			vehiculo.setVehiUsado(false);
		}

		if (vehiculoGa.getFECHAPRIMERAMATRICULACION() != null && (vehiculoGa.getUSADO() != null) && !(vehiculoGa.getUSADO().isEmpty())
				&& (("SI".equalsIgnoreCase(vehiculoGa.getUSADO().trim())) || (TipoSN.S.value().equals(vehiculoGa.getUSADO().trim().toUpperCase())))) {
			vehiculo.setFechaPrimMatri(utilesFecha.getFechaFracionada(utilesFecha.DDMMAAAAToTimestamp(vehiculoGa.getFECHAPRIMERAMATRICULACION())));
		} else {
			vehiculo.setFechaPrimMatri(null);
		}

		if (vehiculoGa.getLUGARMATRICULACION() != null && !vehiculoGa.getLUGARMATRICULACION().isEmpty()) {
			vehiculo.setLugarPrimeraMatriculacion(vehiculoGa.getLUGARMATRICULACION());
		} else {
			vehiculo.setLugarPrimeraMatriculacion("-1");
		}

		try {
			vehiculo.setKmUso(new BigDecimal(vehiculoGa.getKM()));
		} catch (Exception e) {
			vehiculo.setKmUso(null);
		}

		try {
			vehiculo.setHorasUso(new BigDecimal(vehiculoGa.getCUENTAHORAS()));
		} catch (Exception e) {
			vehiculo.setHorasUso(null);
		}

		vehiculo.setTipoIndustria(vehiculoGa.getTIPOINDUSTRIA());

		// DATOS DE LA DIRECCIÓN DEL VEHÍCULO

		DireccionDto direccion = new DireccionDto();
		if (vehiculoGa.getPROVINCIAVEHICULO() != null) {
			direccion.setIdProvincia(conversion.getIdProvinciaFromSiglas(vehiculoGa.getPROVINCIAVEHICULO().name()));
		}
		direccion.setMunicipio(vehiculoGa.getMUNICIPIOVEHICULO());
		direccion.setIdTipoVia("");
		direccion.setVia(vehiculoGa.getDOMICILIOVEHICULO());
		direccion.setNumero(vehiculoGa.getNUMERODIRECCIONVEHICULO());
		direccion.setCodPostalCorreos(vehiculoGa.getCPVEHICULO());
		direccion.setLetra(vehiculoGa.getLETRADIRECCIONVEHICULO());
		direccion.setEscalera(vehiculoGa.getESCALERADIRECCIONVEHICULO());
		direccion.setBloque(vehiculoGa.getBLOQUEDIRECCIONVEHICULO());
		direccion.setPlanta(vehiculoGa.getPISODIRECCIONVEHICULO());
		direccion.setPuerta(vehiculoGa.getPUERTADIRECCIONVEHICULO());
		try {
			direccion.setKm(new BigDecimal(vehiculoGa.getKMDIRECCIONVEHICULO()));
		} catch (Exception e) {
			direccion.setKm(null);
		}
		try {
			direccion.setHm(new BigDecimal(vehiculoGa.getHECTOMETRODIRECCIONVEHICULO()));
		} catch (Exception e) {
			direccion.setHm(null);
		}

		// INFORMACIÓN PARA LAS IMPORTACIONES
		String valor = vehiculoGa.getSIGLASDIRECCIONVEHICULO();
		if (valor == null)
			valor = "";
		if ("6".equals(valor.trim()))
			valor = "41"; // Calle
		if ("44".equals(valor.trim()))
			valor = "5";
		if ("24".equals(valor.trim()))
			valor = "29";
		if ("34".equals(valor.trim()))
			valor = "29";
		direccion.setIdTipoDgt(valor.trim());
		direccion.setPuebloLit(vehiculoGa.getPUEBLOVEHICULO());

		vehiculo.setDireccion(direccion);

		// DATOS DEL VEHICULO PREVER
		if (mat.getDATOSIMPUESTOS() != null && mat.getDATOSIMPUESTOS().getDATOS576() != null) {
			DATOS576 datos576 = mat.getDATOSIMPUESTOS().getDATOS576();
			if (datos576 != null) {
				VehiculoDto vehiculoPrever = new VehiculoDto();
				vehiculoPrever.setBastidor(datos576.getNUMEROBASTIDORPREVER576());
				vehiculoPrever.setMatricula(datos576.getMATRICULAPREVER576());
				vehiculoPrever.setModelo(datos576.getMODELOPREVER576());
				try {
					vehiculoPrever.setCodigoMarca(datos576.getMARCAPREVER576());
				} catch (Exception e) {
					vehiculoPrever.setCodigoMarca(null);
				}
				if (datos576.getCLASIFICACIONPREVER576() != null) {
					vehiculoPrever.setClasificacionItv(datos576.getCLASIFICACIONPREVER576());
					if (datos576.getCLASIFICACIONPREVER576().length() == 4) {
						vehiculoPrever.setCriterioConstruccion(datos576.getCLASIFICACIONPREVER576().substring(0, 2));
						vehiculoPrever.setCriterioUtilizacion(datos576.getCLASIFICACIONPREVER576().substring(2, 4));
					}
				}
				vehiculoPrever.setTipoItv(datos576.getTIPOITVPREVER576());

				vehiculo.setVehiculoPrever(vehiculoPrever);
			}
		}

		String marcaSinEditar = utiles.getMarcaSinEditar(vehiculoGa.getMARCA());
		vehiculo.setMarcaSinEditar(marcaSinEditar);

		// DATOS DEL INTEGRADOR
		DATOSIMPORTADOR integrador = mat.getDATOSIMPORTADOR();
		if (integrador != null) {
			PersonaDto personaIntegrador = new PersonaDto();
			if (null != integrador.getRAZONSOCIALIMPORTADOR() && !integrador.getRAZONSOCIALIMPORTADOR().isEmpty()) {
				personaIntegrador.setApellido1RazonSocial(integrador.getRAZONSOCIALIMPORTADOR());
			} else if (integrador.getAPELLIDO1IMPORTADOR() != null && !integrador.getAPELLIDO1IMPORTADOR().isEmpty()) {
				personaIntegrador.setApellido1RazonSocial(integrador.getAPELLIDO1IMPORTADOR());
			} else {
				personaIntegrador.setApellido1RazonSocial("");
			}

			personaIntegrador.setApellido2(integrador.getAPELLIDO2IMPORTADOR());
			personaIntegrador.setNombre(integrador.getNOMBREIMPORTADOR());
			personaIntegrador.setNif(integrador.getDNIIMPORTADOR());

			vehiculo.setIntegrador(personaIntegrador);
		}

		// MATE 2.5
		if (vehiculoGa.getIDALIMENTACION() != null) {
			vehiculo.setTipoAlimentacion(vehiculoGa.getIDALIMENTACION().value());
		}
		if (vehiculoGa.getIDCARROCERIA() != null) {
			vehiculo.setCarroceria(vehiculoGa.getIDCARROCERIA().value());
		}
		vehiculo.setCodigoEco(vehiculoGa.getCODIGOECO());
		try {
			if (vehiculoGa.getCONSUMO() != null) {
				vehiculo.setConsumo(new BigDecimal(vehiculoGa.getCONSUMO()));
			}
		} catch (NumberFormatException e) {
			log.error("No se ha podido parsear el CONSUMO a BigDecimal para el paquete: " + e);
			vehiculo.setConsumo(null);
		}
		try {
			if (vehiculoGa.getDISTANCIAENTREEJES() != null) {
				vehiculo.setDistanciaEjes(new BigDecimal(vehiculoGa.getDISTANCIAENTREEJES()));
			}
		} catch (NumberFormatException e) {
			log.error("No se ha podido parsear la DISTANCIA ENTRE EJES a BigDecimal para el paquete: " + e);
			vehiculo.setDistanciaEjes(null);
		}

		vehiculo.setEcoInnovacion(vehiculoGa.getECOINNOVACION());
		vehiculo.setNivelEmisiones(vehiculoGa.getNIVELEMISIONES());
		vehiculo.setFabricante(vehiculoGa.getFABRICANTE());
		if (vehiculoGa.getIDCARBURANTE() != null) {
			vehiculo.setCarburante(vehiculoGa.getIDCARBURANTE().value());
		}
		if (vehiculoGa.getIDHOMOLOGACION() != null) {
			vehiculo.setIdDirectivaCee(vehiculoGa.getIDHOMOLOGACION());
		}
		if (vehiculoGa.getIMPORTADO() != null && !vehiculoGa.getIMPORTADO().isEmpty()
				&& (vehiculoGa.getIMPORTADO().trim().toUpperCase().equals(TipoSN.S.value()) || vehiculoGa.getIMPORTADO().trim().equalsIgnoreCase("SI"))) {
			vehiculo.setImportado(true);
		} else {
			vehiculo.setImportado(false);
		}

		try {
			if (vehiculoGa.getMOM() != null) {
				vehiculo.setMom(new BigDecimal(vehiculoGa.getMOM()));
			}
		} catch (NumberFormatException e) {
			log.error("No se ha podido parsear la MOM a BigDecimal para el paquete: " + e);
			vehiculo.setMom(null);
		}
		if (vehiculoGa.getREDUCCIONECO() != null) {
			vehiculo.setReduccionEco(new BigDecimal(vehiculoGa.getREDUCCIONECO()));
		}

		if ((vehiculoGa.getSUBASTA() != null) && !(vehiculoGa.getSUBASTA().isEmpty())
				&& (("SI".equalsIgnoreCase(vehiculoGa.getSUBASTA().trim())) || (TipoSN.S.value().equals(vehiculoGa.getSUBASTA().trim().toUpperCase())))) {
			vehiculo.setSubastado(true);
		} else {
			vehiculo.setSubastado(false);
		}
		try {
			if (vehiculoGa.getVIAANTERIOR() != null) {
				vehiculo.setViaAnterior(new BigDecimal(vehiculoGa.getVIAANTERIOR()));
			}
		} catch (NumberFormatException e) {
			log.error("No se ha podido parsear la VIA ANTERIOR a BigDecimal para el paquete: " + e);
			vehiculo.setViaAnterior(null);
		}
		try {
			if (vehiculoGa.getVIAPOSTERIOR() != null) {
				vehiculo.setViaPosterior(new BigDecimal(vehiculoGa.getVIAPOSTERIOR()));
			}
		} catch (NumberFormatException e) {
			log.error("No se ha podido parsear la VIA POSTERIOR a BigDecimal para el paquete: " + e);
			vehiculo.setViaPosterior(null);
		}

		if ((vehiculoGa.getCHECKCADUCIDADITV() != null) && !(vehiculoGa.getCHECKCADUCIDADITV().isEmpty())
				&& (("SI".equalsIgnoreCase(vehiculoGa.getCHECKCADUCIDADITV().trim())) || (TipoSN.S.value().equals(vehiculoGa.getCHECKCADUCIDADITV().trim().toUpperCase())))) {
			vehiculo.setCheckFechaCaducidadITV(true);
		} else {
			vehiculo.setCheckFechaCaducidadITV(false);
		}

		if (vehiculoGa.getTIPOINSPECCIONITV() != null) {
			vehiculo.setMotivoItv(vehiculoGa.getTIPOINSPECCIONITV().value());
		}

		if (vehiculoGa.getMATRICULAORIGEN() != null) {
			vehiculo.setMatriculaOrigen(vehiculoGa.getMATRICULAORIGEN());
		}

		if (vehiculoGa.getESTACIONITV() != null) {
			vehiculo.setEstacionItv(vehiculoGa.getESTACIONITV());
		}

		if (vehiculoGa.getMATRICULAORIGEXTR() != null) {
			vehiculo.setMatriculaOrigExtr(vehiculoGa.getMATRICULAORIGEXTR());
		}

		if (vehiculoGa.getPAISFABRICACION() != null) {
			try {
				vehiculo.setPaisFabricacion(PaisFabricacion.getPaisFabricacion(vehiculoGa.getPAISFABRICACION()).getValorEnum());
			} catch (Exception e) {
				log.error("El pais de fabricacion no es correcto : " + vehiculoGa.getPAISFABRICACION());
				vehiculo.setPaisFabricacion(null);
			}
		}

		if (vehiculoGa.getFICHATECNICARD750() != null && !vehiculoGa.getFICHATECNICARD750().isEmpty() && vehiculoGa.getFICHATECNICARD750().equalsIgnoreCase("si")) {
			vehiculo.setFichaTecnicaRD750(true);
		} else {
			vehiculo.setFichaTecnicaRD750(false);
		}

		if (vehiculoGa.getMARCABASE() != null) {
			MarcaDgtDto marcaDgt = servicioVehiculo.getMarcaDgt(null, vehiculoGa.getMARCABASE(), true);
			if (marcaDgt != null) {
				vehiculo.setCodigoMarcaBase(marcaDgt.getCodigoMarca().toString());
			}
		}
		if (vehiculoGa.getFABRICANTEBASE() != null) {
			vehiculo.setFabricanteBase(vehiculoGa.getFABRICANTEBASE());
		}
		if (vehiculoGa.getTIPOBASE() != null) {
			vehiculo.setTipoBase(vehiculoGa.getTIPOBASE());
		}
		if (vehiculoGa.getVARIANTEBASE() != null) {
			vehiculo.setVarianteBase(vehiculoGa.getVARIANTEBASE());
		}
		if (vehiculoGa.getVERSIONBASE() != null) {
			vehiculo.setVersionBase(vehiculoGa.getVERSIONBASE());
		}
		if (vehiculoGa.getNUMEROHOMOLOGACIONBASE() != null) {
			vehiculo.setNumHomologacionBase(vehiculoGa.getNUMEROHOMOLOGACIONBASE());
		}
		if (vehiculoGa.getMOMBASE() != null) {
			vehiculo.setMomBase(new BigDecimal(vehiculoGa.getMOMBASE()));
		}
		if (vehiculoGa.getCATEGORIAELECTRICA() != null) {
			vehiculo.setCategoriaElectrica(vehiculoGa.getCATEGORIAELECTRICA().value());
		}
		if (vehiculoGa.getAUTONOMIAELECTRICA() != null) {
			vehiculo.setAutonomiaElectrica(new BigDecimal(vehiculoGa.getAUTONOMIAELECTRICA()));
		}
		if (vehiculoGa.getVELOCIDADMAXIMA() != null) {
			try {
				vehiculo.setVelocidadMaxima( new BigDecimal(vehiculoGa.getVELOCIDADMAXIMA()));                         
			} catch (NumberFormatException e) {
				log.error("No se ha podido parsear la VELOCIDAD MAXIMA a BigDecimal: " + e);
				vehiculo.setVelocidadMaxima(null);
			}
		}

		return vehiculo;
	}
}