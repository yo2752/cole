package trafico.beans.utiles;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.gestoresmadrid.core.model.enumerados.TipoInterviniente;
import org.gestoresmadrid.oegam2comun.paises.model.service.ServicioPais;
import org.gestoresmadrid.oegam2comun.paises.view.dto.PaisDto;
import org.gestoresmadrid.oegam2comun.vehiculo.model.service.ServicioMarcaDgt;
import org.gestoresmadrid.utilidades.components.Utiles;
import org.gestoresmadrid.utilidades.components.UtilesFecha;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import general.utiles.Anagrama;
import trafico.beans.daos.BeanPQAltaMatriculacionImport;
import trafico.beans.daos.BeanPQTramiteTraficoGuardarIntervinienteImport;
import trafico.beans.daos.BeanPQTramiteTraficoGuardarMatriculacion;
import trafico.beans.daos.BeanPQVehiculosGuardarImport;
import trafico.beans.jaxb.matw.TipoTarjetaItv;
import trafico.beans.jaxb.matw.dgt.tipos.TipoSN;
import trafico.beans.schemas.generated.transTelematica.TipoSINO;
import trafico.utiles.UtilesConversiones;
import trafico.utiles.enumerados.ConceptoTutela;
import trafico.utiles.enumerados.PaisFabricacion;
import trafico.utiles.enumerados.TipoTramiteMatriculacion;
import utilidades.estructuras.Fecha;
import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;

@Component
public class FormatoGaMatriculacionMatwPqConversion {

	private static final ILoggerOegam log = LoggerOegam.getLogger(FormatoGaMatriculacionMatwPqConversion.class);
	private static final String TIPO_DGT_BLANCO = "23";
	private static String sinNumero = "SN";

	@Autowired
	UtilesFecha utilesFecha;

	@Autowired
	Utiles utiles;

	@Autowired
	ServicioMarcaDgt servicioMarcaDgt;

	@Autowired
	UtilesConversiones utilesConversiones;

	@Autowired
	private ServicioPais servicioPais;

	public List<BeanPQAltaMatriculacionImport> convertirFORMATOGAtoPQ(trafico.beans.jaxb.matw.FORMATOGA ga, BigDecimal idUsuario, BigDecimal idContrato) {
		List<BeanPQAltaMatriculacionImport> lista = new ArrayList<>();

		for (int i = 0; i < ga.getMATRICULACION().size(); i++) {
			BeanPQVehiculosGuardarImport vehiculo = new BeanPQVehiculosGuardarImport();
			BeanPQTramiteTraficoGuardarIntervinienteImport titular = new BeanPQTramiteTraficoGuardarIntervinienteImport();
			BeanPQTramiteTraficoGuardarIntervinienteImport representanteTitular = new BeanPQTramiteTraficoGuardarIntervinienteImport();
			BeanPQTramiteTraficoGuardarIntervinienteImport conductorHabitual = new BeanPQTramiteTraficoGuardarIntervinienteImport();
			BeanPQTramiteTraficoGuardarIntervinienteImport representanteConductorHabitual = new BeanPQTramiteTraficoGuardarIntervinienteImport();
			BeanPQTramiteTraficoGuardarIntervinienteImport arrendatario = new BeanPQTramiteTraficoGuardarIntervinienteImport();
			BeanPQTramiteTraficoGuardarIntervinienteImport representanteArrendatario = new BeanPQTramiteTraficoGuardarIntervinienteImport();
			BeanPQTramiteTraficoGuardarMatriculacion tramite = new BeanPQTramiteTraficoGuardarMatriculacion();

			if (null != ga.getMATRICULACION().get(i)) {
				if (null != ga.getMATRICULACION().get(i).getDATOSVEHICULO())
					vehiculo = convertirVehiculoGAtoPQ(ga, ga.getMATRICULACION().get(i));
				if (null != ga.getMATRICULACION().get(i).getDATOSTITULAR())
					titular = convertirIntervinienteGAtoPQ(TipoInterviniente.Titular.getValorEnum(), ga.getMATRICULACION().get(i), idUsuario, idContrato);
				if (null != ga.getMATRICULACION().get(i).getDATOSREPRESENTANTETITULAR())
					representanteTitular = convertirIntervinienteGAtoPQ(TipoInterviniente.RepresentanteTitular.getValorEnum(), ga.getMATRICULACION().get(i), idUsuario, idContrato);
				if (null != ga.getMATRICULACION().get(i).getDATOSCONDUCTORHABITUAL())
					conductorHabitual = convertirIntervinienteGAtoPQ(TipoInterviniente.ConductorHabitual.getValorEnum(), ga.getMATRICULACION().get(i), idUsuario, idContrato);
				if (null != ga.getMATRICULACION().get(i).getDATOSARRENDATARIO())
					arrendatario = convertirIntervinienteGAtoPQ(TipoInterviniente.Arrendatario.getValorEnum(), ga.getMATRICULACION().get(i), idUsuario, idContrato);
				if (null != ga.getMATRICULACION().get(i).getDATOSREPRESENTANTEARRENDATARIO())
					representanteArrendatario = convertirIntervinienteGAtoPQ(TipoInterviniente.RepresentanteArrendatario.getValorEnum(), ga.getMATRICULACION().get(i), idUsuario, idContrato);
				tramite = convertirTramiteGAtoPQ(ga, ga.getMATRICULACION().get(i), idUsuario, idContrato);
			}

			BeanPQAltaMatriculacionImport beanTramiteMatriculacion = null;

			if (tramite != null) {
				beanTramiteMatriculacion = new BeanPQAltaMatriculacionImport();
				beanTramiteMatriculacion.setBeanGuardarMatriculacion(tramite);
				beanTramiteMatriculacion.setBeanGuardarVehiculo(vehiculo);
				beanTramiteMatriculacion.setBeanGuardarTitular(titular);
				beanTramiteMatriculacion.setBeanGuardarRepresentante(representanteTitular);
				beanTramiteMatriculacion.setBeanGuardarConductorHabitual(conductorHabitual);
				beanTramiteMatriculacion.setBeanGuardarReprConductorHabitual(representanteConductorHabitual);
				beanTramiteMatriculacion.setBeanGuardarArrendatario(arrendatario);
				beanTramiteMatriculacion.setBeanGuardarReprArrendatario(representanteArrendatario);
				lista.add(beanTramiteMatriculacion);
			}
		}
		return lista;
	}

	/**
	 * Devuelve el BeanPQ del trámite-tráfico de matriculación a partir de un formatoga (importado XML-MATE)
	 * @param ga
	 * @param mat
	 * @param idUsuario
	 * @param idContrato
	 * @return
	 */
	private BeanPQTramiteTraficoGuardarMatriculacion convertirTramiteGAtoPQ(trafico.beans.jaxb.matw.FORMATOGA ga, trafico.beans.jaxb.matw.FORMATOGA.MATRICULACION mat, BigDecimal idUsuario,
			BigDecimal idContrato) {
		BeanPQTramiteTraficoGuardarMatriculacion tramite = new BeanPQTramiteTraficoGuardarMatriculacion();

		tramite.setP_ID_USUARIO(idUsuario);
		try {
			tramite.setP_NUM_EXPEDIENTE("OEGAM".equals(ga.getPlataforma()) ? new BigDecimal(mat.getNUMEROEXPEDIENTE()) : null);
		} catch (Exception e) {
			tramite.setP_NUM_EXPEDIENTE(null);
		}
		tramite.setP_ID_CONTRATO_SESSION(idContrato);
		try {
			if (mat.getNUMEROPROFESIONAL() != null) {
				String valor = mat.getNUMEROPROFESIONAL().toString();
				if (valor.length() == 5) {
					valor = valor.substring(0, 4);
				}
				while (valor.length() < 4) {
					valor = "0" + valor;
				}
				tramite.setP_NUM_COLEGIADO(valor);
			}
		} catch (Exception e) {
			tramite.setP_NUM_COLEGIADO("");
		}

		// JRG Se quita este campo por la última actualización de MateW 27/08
		if (mat.getFECHAPRESENTACION() != null && !mat.getFECHAPRESENTACION().equals("")) {
			try {
				Fecha fechaPresentacion = new Fecha(mat.getFECHAPRESENTACION());
				if (fechaPresentacion != null) {
					tramite.setP_FECHA_PRESENTACION(fechaPresentacion.getTimestamp());
				}
			} catch (ParseException pe) {
				log.error("Error de importacion en 'Fecha presentacion' : " + pe);
			}
		}
		tramite.setP_CODIGO_TASA(mat.getTASA());
		tramite.setP_TIPO_TASA(mat.getTIPOTASA());
		tramite.setP_REF_PROPIA(mat.getREFERENCIAPROPIA());
		if (mat.getJEFATURA() != null) {
			tramite.setP_JEFATURA_PROVINCIAL(mat.getJEFATURA().value());
		}
		tramite.setP_ANOTACIONES(mat.getOBSERVACIONES());

		if (mat.getENTREGAFACTMATRICULACION() != null && !mat.getENTREGAFACTMATRICULACION().equals("")) {
			tramite.setP_ENTREGA_FACT_MATRICULACION(mat.getENTREGAFACTMATRICULACION().toUpperCase());
		} else {
			tramite.setP_ENTREGA_FACT_MATRICULACION("NO");
		}

		tramite.setP_RENTING(null != mat.getDATOSARRENDATARIO() && !"".equals(mat.getDATOSARRENDATARIO().getDNIARR()) ? "SI" : "NO");

		tramite.setP_CARSHARING("SI".equalsIgnoreCase(mat.getDATOSVEHICULO().getCarsharing()) ? "S" : "N");

		// RENTING DE VEHÍCULO
		if (mat.getDATOSVEHICULO() != null && null != mat.getDATOSVEHICULO().getRENTING()) {
			tramite.setP_RENTING(mat.getDATOSVEHICULO().getRENTING().toUpperCase());
		}

		if (null != mat.getDATOSLIMITACION()) {
			tramite.setP_IEDTM("E".equals(mat.getDATOSLIMITACION().getTIPOLIMITACION()) ? "E" : "");
			tramite.setP_FECHA_IEDTM(utilesFecha.DDMMAAAAToTimestamp(mat.getDATOSLIMITACION().getFECHALIMITACION()));
			if (mat.getDATOSLIMITACION().getNUMEROREGISTROLIMITACION() != null
					&& !mat.getDATOSLIMITACION().getNUMEROREGISTROLIMITACION().equals("")) {
				tramite.setP_N_REG_IEDTM(mat.getDATOSLIMITACION().getNUMEROREGISTROLIMITACION());
			}
			tramite.setP_FINANCIERA_IEDTM(mat.getDATOSLIMITACION().getFINANCIERALIMITACION());
		}
		if (mat.getDATOSIMPUESTOS() != null && mat.getDATOSIMPUESTOS().getCODIGOELECTRONICOAEAT() != null) {
			tramite.setP_CEM(mat.getDATOSIMPUESTOS().getCODIGOELECTRONICOAEAT());
		}
		if (mat.getDATOSIMPUESTOS() != null && mat.getDATOSIMPUESTOS().getCODIGOELECTRONICOAEAT() != null) {
			tramite.setP_CEM(mat.getDATOSIMPUESTOS().getCODIGOELECTRONICOAEAT());
		}
		if (mat.getDATOSIMPUESTOS() != null && mat.getDATOSIMPUESTOS().getCEMA() != null) {
			tramite.setP_CEMA(mat.getDATOSIMPUESTOS().getCEMA());
		}
		// if(mat.getDATOSIMPUESTOS() != null &&
		// mat.getDATOSIMPUESTOS().getFECHAPRESENTACIONIMPUESTO()!=null &&
		// !("").equals(mat.getDATOSIMPUESTOS().getFECHAPRESENTACIONIMPUESTO())){
		// tramite.setP_FECHA_PRESENTACION(utilesFecha.getTimestamp(new
		// Date(mat.getDATOSIMPUESTOS().getFECHAPRESENTACIONIMPUESTO())));
		// }
		if (mat.getDATOSIMPUESTOS() != null && mat.getDATOSIMPUESTOS().getJUSTIFICADOIVTM() != null
				&& !("").equals(mat.getDATOSIMPUESTOS().getJUSTIFICADOIVTM())
				&& ("SI").equalsIgnoreCase(mat.getDATOSIMPUESTOS().getJUSTIFICADOIVTM())) {
			tramite.setP_JUSTIFICADO_IVTM(TipoSINO.SI.value());
		} else {
			tramite.setP_JUSTIFICADO_IVTM(TipoSINO.NO.value());
		}
		// Gilberto Pedroso
		// Mantis 0002797 - No Importa el CEMA, se han añadido estas líneas de
		// código para que lo Importe
		// Inicio
		if (mat.getDATOSIMPUESTOS() != null && mat.getDATOSIMPUESTOS().getCEMA() != null
				&& !"".equals(mat.getDATOSIMPUESTOS().getCEMA())) {
			tramite.setP_CEMA(mat.getDATOSIMPUESTOS().getCEMA());
		}
		// Fin

		// VALORES PROPIOS DE MATRICULACIÓN
		trafico.beans.jaxb.matw.FORMATOGA.MATRICULACION.DATOSIMPUESTOS.DATOS576 datos576 = null;
		if (mat.getDATOSIMPUESTOS() != null) {
			datos576 = mat.getDATOSIMPUESTOS().getDATOS576();
		}

		if (datos576 != null) {
			tramite.setP_BASE_IMPONIBLE_576(datos576.getBASEIMPONIBLE576());
			tramite.setP_BASE_IMPO_REDUCIDA_576(datos576.getBASEIMPONIBLEREDUCIDA576());
			tramite.setP_TIPO_GRAVAMEN_576(datos576.getTIPOGRAVAMEN576());
			tramite.setP_CUOTA_576(datos576.getCUOTA576());
			tramite.setP_DEDUCCION_LINEAL_576(datos576.getDEDUCCIONLINEAL576());
			tramite.setP_CUOTA_INGRESAR_576(datos576.getCUOTAINGRESAR576());
			tramite.setP_A_DEDUCIR_576(datos576.getADEDUCIR576());
			if (datos576.getIMPORTETOTAL576() != null && datos576.getIMPORTETOTAL576() != new BigDecimal("0")) {
				tramite.setP_IMPORTE_576(datos576.getIMPORTETOTAL576());
			}
			tramite.setP_LIQUIDACION_576(datos576.getRESULTADOLIQUIDACION576());
			tramite.setP_N_DECLARACION_COMP_576(datos576.getDECLARACIONCOMPLEMENTARIA576());
			tramite.setP_CAUSA_HECHO_IMPON_576(datos576.getCAUSAHECHOIMPONIBLE());
			tramite.setP_OBSERVACIONES_576(datos576.getOBSERVACIONES576());
			tramite.setP_EXENTO_576(datos576.getEXENTO576());
			if (null != tramite.getP_CUOTA_576() && 0 == tramite.getP_CUOTA_576().compareTo(BigDecimal.ZERO)
					&& (null == tramite.getP_EXENTO_576() || "".equals(tramite.getP_EXENTO_576()))) {
				tramite.setP_EXENTO_576("SI");
			}
			if (datos576.getEJERCICIODEVENGO576() != null) {
				tramite.setP_EJERCICIO_576(new BigDecimal(datos576.getEJERCICIODEVENGO576()));
			}

		}
		if (mat.getDATOSIMPUESTOS() != null && mat.getDATOSIMPUESTOS().getDATOSNRC() != null) {
			tramite.setP_NRC_576(mat.getDATOSIMPUESTOS().getDATOSNRC().getCODIGONRC());
			if (null != mat.getDATOSIMPUESTOS().getDATOSNRC().getFECHAOPERACIONNRC())
				tramite.setP_FECHA_PAGO_576(utilesFecha.DDMMAAAAToTimestamp(mat.getDATOSIMPUESTOS().getDATOSNRC().getFECHAOPERACIONNRC()));
		}

		if (mat.getDATOSIMPUESTOS() != null && mat.getDATOSIMPUESTOS().getDATOS0506() != null && mat.getDATOSIMPUESTOS().getDATOS0506().getMOTIVOEXENCION05() != null) {
			tramite.setP_ID_REDUCCION_05(mat.getDATOSIMPUESTOS().getDATOS0506().getMOTIVOEXENCION05().value());
		}

		if ((tramite.getP_ID_REDUCCION_05() == null || tramite.getP_ID_REDUCCION_05().equals("")) && tramite.getP_N_REG_IEDTM() != null && !"".equals(tramite.getP_N_REG_IEDTM())) {
			tramite.setP_ID_REDUCCION_05(utilesConversiones.numRegistroLimitacionToMotivo(tramite.getP_N_REG_IEDTM(), "05"));
		}

		if (mat.getDATOSIMPUESTOS() != null && mat.getDATOSIMPUESTOS().getDATOS0506() != null && mat.getDATOSIMPUESTOS().getDATOS0506().getMOTIVOEXENCION06() != null) {
			tramite.setP_ID_NO_SUJECCION_06(mat.getDATOSIMPUESTOS().getDATOS0506().getMOTIVOEXENCION06().value());
		}

		if ((tramite.getP_ID_NO_SUJECCION_06() == null || tramite.getP_ID_NO_SUJECCION_06().equals("")) && tramite.getP_N_REG_IEDTM() != null && !"".equals(tramite.getP_N_REG_IEDTM())) {
			tramite.setP_ID_NO_SUJECCION_06(utilesConversiones.numRegistroLimitacionToMotivo(tramite.getP_N_REG_IEDTM(), "06"));
		}

		// Nuevos Campos Matw
		if (mat.getDATOSIMPUESTOS() != null && (mat.getDATOSIMPUESTOS().getEXENTOCEM() != null) && !("".equals(mat.getDATOSIMPUESTOS().getEXENTOCEM()))
				&& (("SI".equalsIgnoreCase(mat.getDATOSIMPUESTOS().getEXENTOCEM().trim())) || ("S".equalsIgnoreCase(mat.getDATOSIMPUESTOS().getEXENTOCEM().trim())))) {
			tramite.setP_EXENTO_CEM("SI");
		} else {
			tramite.setP_EXENTO_CEM("NO");
		}
		tramite.setP_TIPO_TRAMITE_MATR(TipoTramiteMatriculacion.MATRICULAR_TIPO_DEFINITIVA.getValorEnum());
		// JRG: Se quitan campos según la última documentación de MateW 27/08
		// tramite.setP_CEMU(mat.getDATOSIMPUESTOS().getCEMU());

		// if ((mat.getDATOSIMPUESTOS().getSATISFECHOCET() != null) &&
		// !("".equals(mat.getDATOSIMPUESTOS().getSATISFECHOCET())) &&
		// (("SI".equals(mat.getDATOSIMPUESTOS().getSATISFECHOCET().trim().toUpperCase()))
		// ||
		// ("S".equals(mat.getDATOSIMPUESTOS().getSATISFECHOCET().trim().toUpperCase())))){
		//
		// tramite.setP_SATISFECHO_CET("S");
		// } else {
		// tramite.setP_SATISFECHO_CET("N");
		// }

		return tramite;
	}

	/**
	 * Devuelve el BeanPQ del interviniente a partir de un trámite ga (importado XML-MATE)
	 * @param tipoInterviniente
	 * @param mat
	 * @param idUsuario
	 * @param idContrato
	 * @return
	 */
	private BeanPQTramiteTraficoGuardarIntervinienteImport convertirIntervinienteGAtoPQ(String tipoInterviniente,
			trafico.beans.jaxb.matw.FORMATOGA.MATRICULACION mat, BigDecimal idUsuario, BigDecimal idContrato) {
		BeanPQTramiteTraficoGuardarIntervinienteImport interviniente = new BeanPQTramiteTraficoGuardarIntervinienteImport();

		if (TipoInterviniente.Titular.getValorEnum().equals(tipoInterviniente)) {
			trafico.beans.jaxb.matw.FORMATOGA.MATRICULACION.DATOSTITULAR titular = mat.getDATOSTITULAR();

			interviniente.setP_ID_USUARIO(idUsuario);
			interviniente.setP_ID_CONTRATO_SESSION(idContrato);

			// INFORMACION DEL INTERVINIENTE
			interviniente.setP_TIPO_INTERVINIENTE(TipoInterviniente.Titular.getValorEnum());
			interviniente.setP_CAMBIO_DOMICILIO(null != titular.getCAMBIODOMICILIOTITULAR() ? titular.getCAMBIODOMICILIOTITULAR().toUpperCase() : null);
			if (titular.getAUTONOMO() != null && !"".equals(titular.getAUTONOMO())
					&& (("SI").equalsIgnoreCase(titular.getAUTONOMO().trim()) || (TipoSN.S.value()).equals(titular.getAUTONOMO().trim().toUpperCase()))) {
				interviniente.setP_AUTONOMO(TipoSN.S.value());
			} else {
				interviniente.setP_AUTONOMO(TipoSN.N.value());
			}

			// interviniente.setP_AUTONOMO(titular.getAUTONOMO());
			interviniente.setP_CODIGO_IAE(titular.getCODIGOIAE());

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
					interviniente.setP_NUM_COLEGIADO(valor);
				}
			} catch (Exception e) {
				interviniente.setP_NUM_COLEGIADO(null);
			}
			interviniente.setP_NIF(titular.getDNITITULAR());
			interviniente.setP_APELLIDO1_RAZON_SOCIAL(titular.getAPELLIDO1TITULAR());
			interviniente.setP_APELLIDO2(titular.getAPELLIDO2TITULAR());
			interviniente.setP_NOMBRE(titular.getNOMBRETITULAR());
			// JF- se añade la verificación de que el 1er. apellido sea null, ya
			// que se puede dar el caso de que no esté presente el tag de primer
			// Apellido en el caso de que la persona sea jurídica
			if (interviniente.getP_APELLIDO1_RAZON_SOCIAL() == null || "".equals(interviniente.getP_APELLIDO1_RAZON_SOCIAL()))
				interviniente.setP_APELLIDO1_RAZON_SOCIAL(titular.getRAZONSOCIALTITULAR());
			if (utilesConversiones.isNifNie(interviniente.getP_NIF()))
				interviniente.setP_ANAGRAMA(titular.getANAGRAMATITULAR());
			interviniente.setP_FECHA_NACIMIENTO(utilesFecha.DDMMAAAAToTimestamp(titular.getFECHANACIMIENTOTITULAR()));
			// Yerbabuena
			interviniente.setP_FECHA_CADUCIDAD_NIF(utilesFecha.DDMMAAAAToTimestamp(titular.getFECHACADUCIDADNIFTITULAR()));
			interviniente.setP_FECHA_CADUCIDAD_ALTERNATIVO(utilesFecha.DDMMAAAAToTimestamp(titular.getFECHACADUCIDADDOCUMENTOSUSTITUTIVOTITULAR()));
			interviniente.setP_TIPO_DOCUMENTO_ALTERNATIVO(titular.getTIPODOCUMENTOSUSTITUTIVOTITULAR());
			interviniente.setP_INDEFINIDO(titular.getINDEFINIDOTITULAR() != null
					&& (titular.getINDEFINIDOTITULAR().toUpperCase().equals(TipoSN.S.value()) || titular.getINDEFINIDOTITULAR().equalsIgnoreCase("SI")) ? TipoSN.S.value() : TipoSN.N.value());
			//
			interviniente.setP_TELEFONOS(titular.getTELEFONOTITULAR());
			interviniente.setP_SEXO(titular.getSEXOTITULAR() != null ? titular.getSEXOTITULAR().name() : "");
			interviniente.setP_CORREO_ELECTRONICO(titular.getCORREOELECTRONICOTITULAR());

			// INFORMACIÓN DE LA DIRECCION
			interviniente.setP_ID_PROVINCIA(titular.getPROVINCIATITULAR() != null ? utilesConversiones.getIdProvinciaFromSiglas(titular.getPROVINCIATITULAR().name()) : "");
			interviniente.setP_ID_TIPO_VIA("");
			interviniente.setP_NUMERO(titular.getNUMERODIRECCIONTITULAR());
			interviniente.setP_COD_POSTAL(titular.getCPTITULAR());
			interviniente.setP_LETRA(titular.getLETRADIRECCIONTITULAR());
			interviniente.setP_ESCALERA(titular.getESCALERADIRECCIONTITULAR());
			interviniente.setP_BLOQUE(titular.getBLOQUEDIRECCIONTITULAR());
			interviniente.setP_PLANTA(titular.getPISODIRECCIONTITULAR());
			interviniente.setP_PUERTA(titular.getPUERTADIRECCIONTITULAR());
			try {
				interviniente.setP_KM(new BigDecimal(titular.getKMDIRECCIONTITULAR()));
			} catch (Exception e) {
				interviniente.setP_KM(null);
			}
			try {
				interviniente.setP_HM(new BigDecimal(titular.getHECTOMETRODIRECCIONTITULAR()));
			} catch (Exception e) {
				interviniente.setP_HM(null);
			}

			// INFORMACIÓN PARA LAS IMPORTACIONES
			String valor = titular.getSIGLASDIRECCIONTITULAR();
			if (valor == null)
				valor = "";
			if ("6".equals(valor.trim()))
				valor = "41"; // calle
			else if ("44".equals(valor.trim()))
				valor = "5";
			else if ("24".equals(valor.trim()) || "34".equals(valor.trim()))
				valor = "29";

			interviniente.setP_ID_TIPO_DGT(valor.trim());
			interviniente.setP_MUNICIPIO(titular.getMUNICIPIOTITULAR());
			interviniente.setP_PUEBLO_LIT(titular.getPUEBLOTITULAR());
			interviniente.setP_VIA(titular.getNOMBREVIADIRECCIONTITULAR());

			// Mantis 17701
			if (titular.getDIRECCIONACTIVATITULAR() != null && !titular.getDIRECCIONACTIVATITULAR().isEmpty()
					&& (("SI").equalsIgnoreCase(titular.getDIRECCIONACTIVATITULAR().trim()) || (TipoSN.S.value()).equals(titular.getDIRECCIONACTIVATITULAR().trim().toUpperCase()))) {
				interviniente.setP_DIRECCION_ACTIVA(TipoSN.S.value());
			} else if (titular.getDIRECCIONACTIVATITULAR() != null && !titular.getDIRECCIONACTIVATITULAR().isEmpty()
					&& (("NO").equalsIgnoreCase(titular.getDIRECCIONACTIVATITULAR().trim()) || (TipoSN.N.value()).equals(titular.getDIRECCIONACTIVATITULAR().trim().toUpperCase()))) {
				interviniente.setP_DIRECCION_ACTIVA(TipoSN.N.value());
			}

			interviniente.setP_TELEFONOS(titular.getTELEFONOTITULAR());

			// Nuevos Campos Matw
			// if(titular.getNACIONALIDADTITULAR() != null){
			// interviniente.setP_NACIONALIDAD(titular.getNACIONALIDADTITULAR());
			// }
		} else if (TipoInterviniente.RepresentanteTitular.getValorEnum().equals(tipoInterviniente)) {
			trafico.beans.jaxb.matw.FORMATOGA.MATRICULACION.DATOSREPRESENTANTETITULAR representante = mat.getDATOSREPRESENTANTETITULAR();

			interviniente.setP_ID_USUARIO(idUsuario);
			interviniente.setP_ID_CONTRATO_SESSION(idContrato);

			// INFORMACIÓN DEL INTERVINIENTE
			interviniente.setP_TIPO_INTERVINIENTE(TipoInterviniente.RepresentanteTitular.getValorEnum());
			// JRG: Se quita la fecha de presentación segun la útima validación
			// de MateW 27/08
			// interviniente.setP_FECHA_INICIO(utilesFecha.DDMMAAAAToTimestamp(representante.getFECHAINICIOTUTELA()));
			interviniente.setP_FECHA_FIN(utilesFecha.DDMMAAAAToTimestamp(representante.getFECHAFINTUTELA()));
			// Yerbabuena
			interviniente.setP_FECHA_CADUCIDAD_NIF(utilesFecha.DDMMAAAAToTimestamp(representante.getFECHACADUCIDADNIFREPRESENTANTETITULAR()));
			interviniente.setP_FECHA_CADUCIDAD_ALTERNATIVO(utilesFecha.DDMMAAAAToTimestamp(representante.getFECHACADUCIDADDOCUMENTOSUSTITUTIVOREPRESENTANTETITULAR()));
			interviniente.setP_TIPO_DOCUMENTO_ALTERNATIVO(representante.getTIPODOCUMENTOSUSTITUTIVOREPRESENTANTETITULAR());
			interviniente.setP_INDEFINIDO(representante.getINDEFINIDOREPRESENTANTETITULAR() != null
					&& (representante.getINDEFINIDOREPRESENTANTETITULAR().toUpperCase().equals(TipoSN.S.value()) || representante.getINDEFINIDOREPRESENTANTETITULAR().equalsIgnoreCase("SI"))
					? TipoSN.S.value() : TipoSN.N.value());
			//

			if (representante.getCONCEPTOREPTITULAR() != null && !representante.getCONCEPTOREPTITULAR().equals("")) {
				interviniente.setP_CONCEPTO_REPRE(representante.getCONCEPTOREPTITULAR());
				// Solo si el 'concepto' es 'tutela' puede haber 'motivo de la
				// tutela'
				if (ConceptoTutela.convertir(representante.getCONCEPTOREPTITULAR()) == ConceptoTutela.Tutela
						&& representante.getMOTIVOTUTELA() != null) {
					interviniente.setP_ID_MOTIVO_TUTELA(representante.getMOTIVOTUTELA().value());
				}
			}
			interviniente.setP_DATOS_DOCUMENTO(representante.getACREDITACIONREPTITULAR());

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
					interviniente.setP_NUM_COLEGIADO(valor);
				}
			} catch (Exception e) {
				interviniente.setP_NUM_COLEGIADO("");
			}
			interviniente.setP_NIF(representante.getDNIREP());

			interviniente.setP_APELLIDO1_RAZON_SOCIAL(representante.getAPELLIDO1REP());
			interviniente.setP_APELLIDO2(representante.getAPELLIDO2REP());
			interviniente.setP_NOMBRE(representante.getNOMBREREP());

			if (interviniente.getP_NIF() != null && interviniente.getP_APELLIDO1_RAZON_SOCIAL() != null && !"".equals(interviniente.getP_NIF())
					&& !"".equals(interviniente.getP_APELLIDO1_RAZON_SOCIAL()) && utilesConversiones.isNifNie(interviniente.getP_NIF())) {
				interviniente.setP_ANAGRAMA(Anagrama.obtenerAnagramaFiscal(interviniente.getP_APELLIDO1_RAZON_SOCIAL(), interviniente.getP_NIF()));
			}

			// INFORMACIÓN PARA LAS IMPORTACIONES
			String valor = representante.getSIGLASDIRECCIONREP();
			if (valor == null)
				valor = "";
			if ("6".equals(valor.trim()))
				valor = "41"; // calle
			else if ("44".equals(valor.trim()))
				valor = "5";
			else if ("24".equals(valor.trim()) || "34".equals(valor.trim()))
				valor = "29";

			interviniente.setP_ID_TIPO_DGT(valor.trim());

			interviniente.setP_MUNICIPIO(representante.getMUNICIPIOREP());

			if (representante.getPROVINCIAREP() != null) {
				interviniente.setP_ID_PROVINCIA(utilesConversiones.getIdProvinciaFromSiglas(representante.getPROVINCIAREP().name()));
			}

			if (representante.getCPREP() != null) {
				interviniente.setP_COD_POSTAL(representante.getCPREP());
			}

			if (representante.getNUMERODIRECCIONREP() != null) {
				interviniente.setP_NUMERO(representante.getNUMERODIRECCIONREP());
			}

			if (representante.getPUEBLOREP() != null) {
				interviniente.setP_PUEBLO_LIT(representante.getPUEBLOREP());
			}

			if (representante.getNOMBREVIADIRECCIONREP() != null) {
				interviniente.setP_VIA(representante.getNOMBREVIADIRECCIONREP());
			}

			// Mantis 17701
			if (representante.getDIRECCIONACTIVAREP() != null && !representante.getDIRECCIONACTIVAREP().isEmpty()
					&& (("SI").equalsIgnoreCase(representante.getDIRECCIONACTIVAREP().trim()) || (TipoSN.S.value()).equals(representante.getDIRECCIONACTIVAREP().trim().toUpperCase()))) {
				interviniente.setP_DIRECCION_ACTIVA(TipoSN.S.value());
			} else if (representante.getDIRECCIONACTIVAREP() != null && !representante.getDIRECCIONACTIVAREP().isEmpty()
					&& (("NO").equalsIgnoreCase(representante.getDIRECCIONACTIVAREP().trim()) || (TipoSN.N.value()).equals(representante.getDIRECCIONACTIVAREP().trim().toUpperCase()))) {
				interviniente.setP_DIRECCION_ACTIVA(TipoSN.N.value());
			}
		} else if (TipoInterviniente.ConductorHabitual.getValorEnum().equals(tipoInterviniente)) {
			trafico.beans.jaxb.matw.FORMATOGA.MATRICULACION.DATOSCONDUCTORHABITUAL conductor = mat.getDATOSCONDUCTORHABITUAL();

			interviniente.setP_ID_USUARIO(idUsuario);
			interviniente.setP_ID_CONTRATO_SESSION(idContrato);

			// INFORMACIÓN DEL INTERVINIENTE
			interviniente.setP_TIPO_INTERVINIENTE(TipoInterviniente.ConductorHabitual.getValorEnum());
			interviniente.setP_CAMBIO_DOMICILIO(null != conductor.getCAMBIODOMICILIOCONDHABITUAL() ? conductor.getCAMBIODOMICILIOCONDHABITUAL().toUpperCase() : null);
			// JRG: Se quita la fecha de presentación segun la útima validación
			// de MateW 27/08
			// interviniente.setP_FECHA_INICIO(utilesFecha.DDMMAAAAToTimestamp(conductor.getFECHAINICIOCONDHABITUAL()));
			interviniente.setP_FECHA_FIN(utilesFecha.DDMMAAAAToTimestamp(conductor.getFECHAFINCONDHABITUAL()));
			// Yerbabuena
			interviniente.setP_FECHA_CADUCIDAD_NIF(utilesFecha.DDMMAAAAToTimestamp(conductor.getFECHACADUCIDADNIFCONDHABITUAL()));
			interviniente.setP_FECHA_CADUCIDAD_ALTERNATIVO(utilesFecha.DDMMAAAAToTimestamp(conductor.getFECHACADUCIDADDOCUMENTOSUSTITUTIVOCONDHABITUAL()));
			interviniente.setP_TIPO_DOCUMENTO_ALTERNATIVO(conductor.getTIPODOCUMENTOSUSTITUTIVOCONDHABITUAL());
			interviniente.setP_INDEFINIDO(conductor.getINDEFINIDOCONDHABITUAL() != null
					&& (conductor.getINDEFINIDOCONDHABITUAL().toUpperCase().equals(TipoSN.S.value()) || conductor.getINDEFINIDOCONDHABITUAL().equalsIgnoreCase("SI")) ? TipoSN.S.value() : TipoSN.N
					.value());
			// JRG: Se quita la fecha de presentación segun la útima validación
			// de MateW 27/08
			// interviniente.setP_HORA_INICIO(conductor.getHORAINICIOCONDHABITUAL());
			interviniente.setP_HORA_FIN(conductor.getHORAFINCONDHABITUAL());

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
					interviniente.setP_NUM_COLEGIADO(valor);
				}
			} catch (Exception e) {
				interviniente.setP_NUM_COLEGIADO(null);
			}
			interviniente.setP_NIF(conductor.getDNICONDHABITUAL());
			interviniente.setP_APELLIDO1_RAZON_SOCIAL(conductor.getAPELLIDO1CONDHABITUAL());
			interviniente.setP_APELLIDO2(conductor.getAPELLIDO2CONDHABITUAL());
			interviniente.setP_NOMBRE(conductor.getNOMBRECONDHABITUAL());
			if ("".equals(interviniente.getP_APELLIDO1_RAZON_SOCIAL()))
				interviniente.setP_APELLIDO1_RAZON_SOCIAL(conductor.getRAZONSOCIALCONDHABITUAL());
			interviniente.setP_FECHA_NACIMIENTO(utilesFecha.DDMMAAAAToTimestamp(conductor.getFECHANACIMIENTOCONDHABITUAL()));

			interviniente.setP_SEXO(conductor.getSEXOCONDHABITUAL() != null ? conductor.getSEXOCONDHABITUAL().name() : null);

			// INFORMACIÓN DE LA DIRECCIÓN
			if (conductor.getPROVINCIACONDHABITUAL() != null) {
				interviniente.setP_ID_PROVINCIA(utilesConversiones.getIdProvinciaFromSiglas(conductor.getPROVINCIACONDHABITUAL().name()));
			} else {
				interviniente.setP_ID_PROVINCIA(null);
			}

			// interviniente.setP_ID_MUNICIPIO();
			interviniente.setP_ID_TIPO_VIA("");
			// interviniente.setP_NOMBRE_VIA(conductor.getNOMBREVIADIRECCIONTITULAR());
			interviniente.setP_NUMERO(conductor.getNUMERODIRECCIONCONDHABITUAL());
			interviniente.setP_COD_POSTAL(conductor.getCPCONDHABITUAL());
			// interviniente.setP_PUEBLO();
			interviniente.setP_LETRA(conductor.getLETRADIRECCIONCONDHABITUAL());
			interviniente.setP_ESCALERA(conductor.getESCALERADIRECCIONCONDHABITUAL());
			interviniente.setP_BLOQUE(conductor.getBLOQUEDIRECCIONCONDHABITUAL());
			interviniente.setP_PLANTA(conductor.getPISODIRECCIONCONDHABITUAL());
			interviniente.setP_PUERTA(conductor.getPUERTADIRECCIONCONDHABITUAL());
			// interviniente.setP_NUM_LOCAL();
			try {
				interviniente.setP_KM(new BigDecimal(conductor.getKMDIRECCIONCONDHABITUAL()));
			} catch (Exception e) {
				interviniente.setP_KM(null);
			}
			try {
				interviniente.setP_HM(new BigDecimal(conductor.getHECTOMETRODIRECCIONCONDHABITUAL()));
			} catch (Exception e) {
				interviniente.setP_HM(null);
			}

			// INFORMACIÓN PARA LAS IMPORTACIONES
			String valor = conductor.getSIGLASDIRECCIONCONDHABITUAL();
			if (valor == null)
				valor = "";
			if ("6".equals(valor.trim()))
				valor = "41"; // calle
			else if ("44".equals(valor.trim()))
				valor = "5";
			else if ("24".equals(valor.trim()) || "34".equals(valor.trim()))
				valor = "29";

			interviniente.setP_ID_TIPO_DGT(valor.trim());
			interviniente.setP_MUNICIPIO(conductor.getMUNICIPIOCONDHABITUAL());
			interviniente.setP_PUEBLO_LIT(conductor.getPUEBLOCONDHABITUAL());
			interviniente.setP_VIA(conductor.getNOMBREVIADIRECCIONCONDHABITUAL());

			// Nuevos Campos Matw
			// if(conductor.getNACIONALIDADCONDHABITUAL() != null){
			// interviniente.setP_NACIONALIDAD(conductor.getNACIONALIDADCONDHABITUAL());
			// }

			// Mantis 17701
			if (conductor.getDIRECCIONACTIVACONDHABITUAL() != null && !conductor.getDIRECCIONACTIVACONDHABITUAL().isEmpty()
					&& (("SI").equalsIgnoreCase(conductor.getDIRECCIONACTIVACONDHABITUAL().trim()) || (TipoSN.S.value()).equals(conductor.getDIRECCIONACTIVACONDHABITUAL().trim().toUpperCase()))) {
				interviniente.setP_DIRECCION_ACTIVA(TipoSN.S.value());
			} else if (conductor.getDIRECCIONACTIVACONDHABITUAL() != null && !conductor.getDIRECCIONACTIVACONDHABITUAL().isEmpty()
					&& (("NO").equalsIgnoreCase(conductor.getDIRECCIONACTIVACONDHABITUAL().trim()) || (TipoSN.N.value()).equals(conductor.getDIRECCIONACTIVACONDHABITUAL().trim().toUpperCase()))) {
				interviniente.setP_DIRECCION_ACTIVA(TipoSN.N.value());
			}

		} else if (TipoInterviniente.Arrendatario.getValorEnum().equals(tipoInterviniente)) {
			trafico.beans.jaxb.matw.FORMATOGA.MATRICULACION.DATOSARRENDATARIO arrendatario = mat.getDATOSARRENDATARIO();

			interviniente.setP_ID_USUARIO(idUsuario);
			interviniente.setP_ID_CONTRATO_SESSION(idContrato);

			// INFORMACIÓN DEL INTERVINIENTE
			interviniente.setP_TIPO_INTERVINIENTE(TipoInterviniente.Arrendatario.getValorEnum());
			interviniente.setP_CAMBIO_DOMICILIO(null != arrendatario.getCAMBIODOMICILIOARR() ? arrendatario.getCAMBIODOMICILIOARR().toUpperCase() : null);
			interviniente.setP_FECHA_INICIO(utilesFecha.DDMMAAAAToTimestamp(arrendatario.getFECHAINICIORENTING()));
			interviniente.setP_FECHA_FIN(utilesFecha.DDMMAAAAToTimestamp(arrendatario.getFECHAFINRENTING()));
			// Yerbabuena
			interviniente.setP_FECHA_CADUCIDAD_NIF(utilesFecha.DDMMAAAAToTimestamp(arrendatario.getFECHACADUCIDADNIFARR()));
			interviniente.setP_FECHA_CADUCIDAD_ALTERNATIVO(utilesFecha.DDMMAAAAToTimestamp(arrendatario.getFECHACADUCIDADDOCUMENTOSUSTITUTIVOARR()));
			interviniente.setP_TIPO_DOCUMENTO_ALTERNATIVO(arrendatario.getTIPODOCUMENTOSUSTITUTIVOARR());
			interviniente.setP_INDEFINIDO(arrendatario.getINDEFINIDOARR() != null
					&& (arrendatario.getINDEFINIDOARR().toUpperCase().equals(TipoSN.S.value()) || arrendatario.getINDEFINIDOARR().equalsIgnoreCase("SI")) ? TipoSN.S.value() : TipoSN.N.value());
			//
			interviniente.setP_HORA_INICIO(arrendatario.getHORAINICIORENTING());
			interviniente.setP_HORA_FIN(arrendatario.getHORAFINRENTING());

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
					interviniente.setP_NUM_COLEGIADO(valor);
				}
			} catch (Exception e) {
				interviniente.setP_NUM_COLEGIADO(null);
			}
			interviniente.setP_NIF(arrendatario.getDNIARR());
			interviniente.setP_APELLIDO1_RAZON_SOCIAL(arrendatario.getAPELLIDO1ARR());
			interviniente.setP_APELLIDO2(arrendatario.getAPELLIDO2ARR());
			interviniente.setP_NOMBRE(arrendatario.getNOMBREARR());
			if (interviniente.getP_APELLIDO1_RAZON_SOCIAL() == null || interviniente.getP_APELLIDO1_RAZON_SOCIAL().isEmpty())
				interviniente.setP_APELLIDO1_RAZON_SOCIAL(arrendatario.getRAZONSOCIALARR());
			interviniente.setP_FECHA_NACIMIENTO(utilesFecha.DDMMAAAAToTimestamp(arrendatario.getFECHANACIMIENTOARR()));

			interviniente.setP_SEXO(arrendatario.getSEXOARR() != null ? arrendatario.getSEXOARR().name() : null);

			// INFORMACIÓN DE LA DIRECCIÓN
			if (arrendatario.getPROVINCIAARR() != null) {
				interviniente.setP_ID_PROVINCIA(utilesConversiones.getIdProvinciaFromSiglas(arrendatario.getPROVINCIAARR().name()));
			} else {
				interviniente.setP_ID_PROVINCIA(null);
			}

			// interviniente.setP_ID_MUNICIPIO();
			interviniente.setP_ID_TIPO_VIA("");
			// interviniente.setP_NOMBRE_VIA(arrendatario.getNOMBREVIADIRECCIONTITULAR());
			interviniente.setP_NUMERO(arrendatario.getNUMERODIRECCIONARR());
			interviniente.setP_COD_POSTAL(arrendatario.getCPARR());
			// interviniente.setP_PUEBLO();
			interviniente.setP_LETRA(arrendatario.getLETRADIRECCIONARR());
			interviniente.setP_ESCALERA(arrendatario.getESCALERADIRECCIONARR());
			interviniente.setP_BLOQUE(arrendatario.getBLOQUEDIRECCIONARR());
			interviniente.setP_PLANTA(arrendatario.getPISODIRECCIONARR());
			interviniente.setP_PUERTA(arrendatario.getPUERTADIRECCIONARR());
			// interviniente.setP_NUM_LOCAL();
			try {
				interviniente.setP_KM(new BigDecimal(arrendatario.getKMDIRECCIONARR()));
			} catch (Exception e) {
				interviniente.setP_KM(null);
			}
			try {
				interviniente.setP_HM(new BigDecimal(arrendatario.getHECTOMETRODIRECCIONARR()));
			} catch (Exception e) {
				interviniente.setP_HM(null);
			}

			// INFORMACIÓN PARA LAS IMPORTACIONES
			String valor = arrendatario.getSIGLASDIRECCIONARR();
			if (valor == null)
				valor = "";
			if ("6".equals(valor.trim()))
				valor = "41"; // calle
			else if ("44".equals(valor.trim()))
				valor = "5";
			else if ("24".equals(valor.trim()) || "34".equals(valor.trim()))
				valor = "29";

			interviniente.setP_ID_TIPO_DGT(valor.trim());
			interviniente.setP_MUNICIPIO(arrendatario.getMUNICIPIOARR());
			interviniente.setP_PUEBLO_LIT(arrendatario.getPUEBLOARR());
			interviniente.setP_VIA(arrendatario.getNOMBREVIADIRECCIONARR());

			// Mantis 17701
			if (arrendatario.getDIRECCIONACTIVAARR() != null && !arrendatario.getDIRECCIONACTIVAARR().isEmpty()
					&& (("SI").equalsIgnoreCase(arrendatario.getDIRECCIONACTIVAARR().trim()) || (TipoSN.S.value()).equals(arrendatario.getDIRECCIONACTIVAARR().trim().toUpperCase()))) {
				interviniente.setP_DIRECCION_ACTIVA(TipoSN.S.value());
			} else if (arrendatario.getDIRECCIONACTIVAARR() != null && !arrendatario.getDIRECCIONACTIVAARR().isEmpty()
					&& (("NO").equalsIgnoreCase(arrendatario.getDIRECCIONACTIVAARR().trim()) || (TipoSN.N.value()).equals(arrendatario.getDIRECCIONACTIVAARR().trim().toUpperCase()))) {
				interviniente.setP_DIRECCION_ACTIVA(TipoSN.N.value());
			}

			// Nuevos Campos Matw
			// if(arrendatario.getNACIONALIDADARR() != null){
			// interviniente.setP_NACIONALIDAD(arrendatario.getNACIONALIDADARR());
			// }
		} else if (TipoInterviniente.RepresentanteArrendatario.getValorEnum().equals(tipoInterviniente)) {
			trafico.beans.jaxb.matw.FORMATOGA.MATRICULACION.DATOSREPRESENTANTEARRENDATARIO reprArrendatario = mat.getDATOSREPRESENTANTEARRENDATARIO();

			interviniente.setP_ID_USUARIO(idUsuario);
			interviniente.setP_ID_CONTRATO_SESSION(idContrato);

			// INFORMACIÓN DEL INTERVINIENTE
			interviniente.setP_TIPO_INTERVINIENTE(TipoInterviniente.RepresentanteArrendatario.getValorEnum());

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
					interviniente.setP_NUM_COLEGIADO(valor);
				}
			} catch (Exception e) {
				interviniente.setP_NUM_COLEGIADO(null);
			}
			interviniente.setP_NIF(reprArrendatario.getDNIREPRESARR());
			interviniente.setP_APELLIDO1_RAZON_SOCIAL(reprArrendatario.getAPELLIDO1REPRESARR());
			interviniente.setP_APELLIDO2(reprArrendatario.getAPELLIDO2REPRESARR());
			interviniente.setP_NOMBRE(reprArrendatario.getNOMBREREPRESARR());
		}

		// Añadimos el tipo de vía y el número a la dirección del interviniente:
		if (interviniente.getP_VIA() != null && !"".equals(interviniente.getP_VIA()) && interviniente.getP_MUNICIPIO() != null && !"".equals(interviniente.getP_MUNICIPIO())
				&& interviniente.getP_COD_POSTAL() != null && !"".equals(interviniente.getP_COD_POSTAL())
				&& interviniente.getP_ID_PROVINCIA() != null && !"".equals(interviniente.getP_ID_PROVINCIA())) {
			if (!(interviniente.getP_ID_TIPO_DGT() != null && !"".equals(interviniente.getP_ID_TIPO_DGT())))
				interviniente.setP_ID_TIPO_DGT(TIPO_DGT_BLANCO);
			if (!(interviniente.getP_NUMERO() != null && !"".equals(interviniente.getP_NUMERO())))
				interviniente.setP_NUMERO(sinNumero);
		}

		return interviniente;
	}

	/**
	 * Devuelve un beanPQ de Vehículo a partir de la información de un trámite ga (importado XML-MATW)
	 * @param ga
	 * @return
	 */
	public BeanPQVehiculosGuardarImport convertirVehiculoGAtoPQ(trafico.beans.jaxb.matw.FORMATOGA ga, trafico.beans.jaxb.matw.FORMATOGA.MATRICULACION mat) {
		BeanPQVehiculosGuardarImport vehiculo = new BeanPQVehiculosGuardarImport();

		trafico.beans.jaxb.matw.FORMATOGA.MATRICULACION.DATOSVEHICULO vehiculoGa = mat.getDATOSVEHICULO();

		try {
			vehiculo.setP_NUM_EXPEDIENTE("OEGAM".equals(ga.getPlataforma()) ? new BigDecimal(mat.getNUMEROEXPEDIENTE()) : null);
		} catch (Exception e) {
			vehiculo.setP_NUM_EXPEDIENTE(null);
		}

		if (mat.getNUMEROPROFESIONAL() != null) {
			vehiculo.setP_NUM_COLEGIADO(mat.getNUMEROPROFESIONAL().toString());
		}

		vehiculo.setP_BASTIDOR(vehiculoGa.getNUMEROBASTIDOR());
		// La matricula no se importa en MATW
		vehiculo.setP_MATRICULA(null);

		vehiculo.setP_NIVE(vehiculoGa.getNIVE() != null ? vehiculoGa.getNIVE().toUpperCase() : vehiculoGa.getNIVE());

		vehiculo.setP_MODELO(vehiculoGa.getMODELO());
		vehiculo.setP_TIPO_VEHICULO(vehiculoGa.getTIPOVEHICULO());
		vehiculo.setP_TIPO_ITV(vehiculoGa.getTIPOITV());

		if (vehiculoGa.getSERVICIODESTINO() != null) {
			vehiculo.setP_ID_SERVICIO(vehiculoGa.getSERVICIODESTINO().value());
		}

		if (vehiculoGa.getCOLOR() != null) {
			vehiculo.setP_ID_COLOR(vehiculoGa.getCOLOR().name());
		}

		if (StringUtils.isNotBlank(vehiculoGa.getPROCEDENCIA())) {
			PaisDto pais = servicioPais.getIdPaisPorSigla(vehiculoGa.getPROCEDENCIA());
			if (pais != null && StringUtils.isNotBlank(pais.getSigla3())) {
				vehiculo.setP_PROCEDENCIA(pais.getSigla3());
			} else {
				vehiculo.setP_PROCEDENCIA(vehiculoGa.getPROCEDENCIA());
			}
		}

		vehiculo.setP_CLASIFICACION_ITV(String.valueOf(vehiculoGa.getCLASIFICACIONITV()));

		if (vehiculoGa.getCLASIFICACIONVEHICULO() != null && vehiculoGa.getCLASIFICACIONVEHICULO().length() >= 4) {
			vehiculo.setP_ID_CRITERIO_CONSTRUCCION(vehiculoGa.getCLASIFICACIONVEHICULO().substring(0, 2));
			vehiculo.setP_ID_CRITERIO_UTILIZACION(vehiculoGa.getCLASIFICACIONVEHICULO().substring(2, 4));
		}

		try {
			vehiculo.setP_PLAZAS(new BigDecimal(vehiculoGa.getPLAZAS()));
		} catch (Exception e) {
			vehiculo.setP_PLAZAS(null);
		}

		vehiculo.setP_CODITV(vehiculoGa.getCODIGOITV());

		vehiculo.setP_POTENCIA_FISCAL(vehiculoGa.getPOTENCIAFISCAL());
		vehiculo.setP_POTENCIA_NETA(vehiculoGa.getPOTENCIANETA());

		if (vehiculoGa.getCILINDRADA() != null) {
			vehiculo.setP_CILINDRADA(vehiculoGa.getCILINDRADA().toString());
		}

		vehiculo.setP_POTENCIA_PESO(vehiculoGa.getRELACIONPOTENCIAPESO());

		if (vehiculoGa.getEMISIONCO2() != null) {
			vehiculo.setP_CO2(vehiculoGa.getEMISIONCO2().toString());
		}

		if (vehiculoGa.getTARA() != null) {
			vehiculo.setP_TARA(vehiculoGa.getTARA().toString());
		}

		if (vehiculoGa.getMASA() != null) {
			vehiculo.setP_PESO_MMA(vehiculoGa.getMASA().toString());
		}

		if (vehiculoGa.getMASAMAXIMAADMISIBLE() != null) {
			vehiculo.setP_MTMA_ITV(vehiculoGa.getMASAMAXIMAADMISIBLE().toString());
		}

		vehiculo.setP_VERSION(vehiculoGa.getVERSIONITV());
		vehiculo.setP_VARIANTE(vehiculoGa.getVARIANTEITV());

		if (vehiculoGa.getFECHAITV() != null) {
			vehiculo.setP_FECHA_ITV(utilesFecha.DDMMAAAAToTimestamp(vehiculoGa.getFECHAITV()));
		}

		if (vehiculoGa.getTIPOTARJETAITV() != null) {
			if (vehiculoGa.getTIPOTARJETAITV().equals(TipoTarjetaItv.A)) {
				vehiculo.setP_ID_TIPO_TARJETA_ITV(TipoTarjetaItv.A.value());
			}
			if (vehiculoGa.getTIPOTARJETAITV().equals(TipoTarjetaItv.B)) {
				vehiculo.setP_ID_TIPO_TARJETA_ITV(TipoTarjetaItv.B.value());
			}
			if (vehiculoGa.getTIPOTARJETAITV().equals(TipoTarjetaItv.BL)) {
				vehiculo.setP_ID_TIPO_TARJETA_ITV(TipoTarjetaItv.BL.value());
			}
			if (vehiculoGa.getTIPOTARJETAITV().equals(TipoTarjetaItv.BR)) {
				vehiculo.setP_ID_TIPO_TARJETA_ITV(TipoTarjetaItv.BR.value());
			}
			if (vehiculoGa.getTIPOTARJETAITV().equals(TipoTarjetaItv.BT)) {
				vehiculo.setP_ID_TIPO_TARJETA_ITV(TipoTarjetaItv.BT.value());
			}
			if (vehiculoGa.getTIPOTARJETAITV().equals(TipoTarjetaItv.C)) {
				vehiculo.setP_ID_TIPO_TARJETA_ITV(TipoTarjetaItv.C.value());
			}
			if (vehiculoGa.getTIPOTARJETAITV().equals(TipoTarjetaItv.D)) {
				vehiculo.setP_ID_TIPO_TARJETA_ITV(TipoTarjetaItv.D.value());
			}
			if (vehiculoGa.getTIPOTARJETAITV().equals(TipoTarjetaItv.AL)) {
				vehiculo.setP_ID_TIPO_TARJETA_ITV(TipoTarjetaItv.AL.value());
			}
			if (vehiculoGa.getTIPOTARJETAITV().equals(TipoTarjetaItv.AT)) {
				vehiculo.setP_ID_TIPO_TARJETA_ITV(TipoTarjetaItv.AT.value());
			}
			if (vehiculoGa.getTIPOTARJETAITV().equals(TipoTarjetaItv.AR)) {
				vehiculo.setP_ID_TIPO_TARJETA_ITV(TipoTarjetaItv.AR.value());
			}
			if (vehiculoGa.getTIPOTARJETAITV().equals(TipoTarjetaItv.DL)) {
				vehiculo.setP_ID_TIPO_TARJETA_ITV(TipoTarjetaItv.DL.value());
			}
			if (vehiculoGa.getTIPOTARJETAITV().equals(TipoTarjetaItv.DR)) {
				vehiculo.setP_ID_TIPO_TARJETA_ITV(TipoTarjetaItv.DR.value());
			}
			if (vehiculoGa.getTIPOTARJETAITV().equals(TipoTarjetaItv.DT)) {
				vehiculo.setP_ID_TIPO_TARJETA_ITV(TipoTarjetaItv.DT.value());
			}
		}

		if (vehiculoGa.getPLAZASPIE() != null) {
			try {
				BigDecimal plazasPie = new BigDecimal(vehiculoGa.getPLAZASPIE());
				vehiculo.setP_N_PLAZAS_PIE(!plazasPie.equals(BigDecimal.ZERO) ? new BigDecimal(vehiculoGa.getPLAZASPIE()) : null);
			} catch (NumberFormatException ex) {
				vehiculo.setP_N_PLAZAS_PIE(null);
			}
		}

		vehiculo.setP_N_HOMOLOGACION(vehiculoGa.getNUMEROHOMOLOGACIONITV());
		vehiculo.setP_N_SERIE(vehiculoGa.getNUMEROSERIEITV());
		vehiculo.setP_ID_EPIGRAFE(vehiculoGa.getEPIGRAFE());

		if ((vehiculoGa.getUSADO() != null) && !("".equals(vehiculoGa.getUSADO()))
				&& (("SI".equalsIgnoreCase(vehiculoGa.getUSADO().trim())) || (TipoSN.S.value().equals(vehiculoGa.getUSADO().trim().toUpperCase())))) {
			vehiculo.setP_VEHI_USADO(TipoSN.S.value());
		} else {
			vehiculo.setP_VEHI_USADO(TipoSN.N.value());
		}

		if (vehiculoGa.getFECHAPRIMERAMATRICULACION() != null && vehiculoGa.getUSADO() != null && !("".equals(vehiculoGa.getUSADO()))
				&& (("SI".equalsIgnoreCase(vehiculoGa.getUSADO().trim())) || (TipoSN.S.value().equals(vehiculoGa.getUSADO().trim().toUpperCase())))) {
			vehiculo.setP_FECHA_PRIM_MATRI(utilesFecha.DDMMAAAAToTimestamp(vehiculoGa.getFECHAPRIMERAMATRICULACION()));
		} else {
			vehiculo.setP_FECHA_PRIM_MATRI(null);
		}

		if (vehiculoGa.getLUGARMATRICULACION() != null && !("".equals(vehiculoGa.getLUGARMATRICULACION()))) {
			vehiculo.setP_ID_LUGAR_MATRICULACION(vehiculoGa.getLUGARMATRICULACION());
		} else {
			vehiculo.setP_ID_LUGAR_MATRICULACION("-1");
		}
		try {
			vehiculo.setP_KM_USO(new BigDecimal(vehiculoGa.getKM()));
		} catch (Exception e) {
			vehiculo.setP_KM_USO(null);
		}
		try {
			vehiculo.setP_HORAS_USO(new BigDecimal(vehiculoGa.getCUENTAHORAS()));
		} catch (Exception e) {
			vehiculo.setP_HORAS_USO(null);
		}

		// TIPO DE INDUSTRIA
		vehiculo.setP_TIPO_INDUSTRIA(vehiculoGa.getTIPOINDUSTRIA());

		// DATOS DE LA DIRECCION DEL VEHÍCULO

		if (vehiculoGa.getPROVINCIAVEHICULO() != null) {
			vehiculo.setP_ID_PROVINCIA(utilesConversiones.getIdProvinciaFromSiglas(vehiculoGa.getPROVINCIAVEHICULO().name()));
		}
		vehiculo.setP_ID_TIPO_VIA("");
		vehiculo.setP_NUMERO(vehiculoGa.getNUMERODIRECCIONVEHICULO());
		vehiculo.setP_COD_POSTAL(vehiculoGa.getCPVEHICULO());
		vehiculo.setP_LETRA(vehiculoGa.getLETRADIRECCIONVEHICULO());
		vehiculo.setP_ESCALERA(vehiculoGa.getESCALERADIRECCIONVEHICULO());
		vehiculo.setP_BLOQUE(vehiculoGa.getBLOQUEDIRECCIONVEHICULO());
		vehiculo.setP_PLANTA(vehiculoGa.getPISODIRECCIONVEHICULO());
		vehiculo.setP_PUERTA(vehiculoGa.getPUERTADIRECCIONVEHICULO());
		try {
			vehiculo.setP_KM(new BigDecimal(vehiculoGa.getKMDIRECCIONVEHICULO()));
		} catch (Exception e) {
			vehiculo.setP_KM(null);
		}
		try {
			vehiculo.setP_HM(new BigDecimal(vehiculoGa.getHECTOMETRODIRECCIONVEHICULO()));
		} catch (Exception e) {
			vehiculo.setP_HM(null);
		}

		// DATOS DEL VEHICULO PREVER
		if (mat.getDATOSIMPUESTOS() != null && mat.getDATOSIMPUESTOS().getDATOS576() != null) {
			trafico.beans.jaxb.matw.FORMATOGA.MATRICULACION.DATOSIMPUESTOS.DATOS576 datos576 = mat.getDATOSIMPUESTOS().getDATOS576();

			if (datos576 != null) {
				vehiculo.setP_PREV_BASTIDOR(datos576.getNUMEROBASTIDORPREVER576());
				vehiculo.setP_PREV_MATRICULA(datos576.getMATRICULAPREVER576());
				vehiculo.setP_PREV_MODELO(datos576.getMODELOPREVER576());

				try {
					vehiculo.setP_PREV_CODIGO_MARCA(new BigDecimal(datos576.getMARCAPREVER576()));
				} catch (Exception e) {
					vehiculo.setP_PREV_CODIGO_MARCA(null);
				}

				if (datos576.getCLASIFICACIONPREVER576() != null) {
					vehiculo.setP_PREV_CLASIFICACION_ITV(datos576.getCLASIFICACIONPREVER576());
					if (datos576.getCLASIFICACIONPREVER576().length() == 4) {
						vehiculo.setP_PREV_ID_CONSTRUCCION(datos576.getCLASIFICACIONPREVER576().substring(0, 2));
						vehiculo.setP_PREV_ID_UTILIZACION(datos576.getCLASIFICACIONPREVER576().substring(2, 4));
					}
				}

				vehiculo.setP_PREV_TIPO_ITV(datos576.getTIPOITVPREVER576());
			}
		}

		// INFORMACIÓN PARA LAS IMPORTACIONES
		String valor = vehiculoGa.getSIGLASDIRECCIONVEHICULO();
		if (valor == null)
			valor = "";
		if ("6".equals(valor.trim()))
			valor = "41"; // calle
		else if ("44".equals(valor.trim()))
			valor = "5";
		else if ("24".equals(valor.trim()) || "34".equals(valor.trim()))
			valor = "29";

		vehiculo.setP_ID_TIPO_DGT(valor.trim());
		vehiculo.setP_MUNICIPIO(vehiculoGa.getMUNICIPIOVEHICULO());
		vehiculo.setP_PUEBLO_LIT(vehiculoGa.getPUEBLOVEHICULO());
		vehiculo.setP_VIA(vehiculoGa.getDOMICILIOVEHICULO());
		// Ricardo Rodriguez. Incidencia Mantis 6635
		String marcaSinEditar = utiles.getMarcaSinEditar(vehiculoGa.getMARCA());
		vehiculo.setP_MARCA_SIN_EDITAR(marcaSinEditar);
		vehiculo.setP_MARCA(vehiculoGa.getMARCA());
		// Fin de incidencia 6635

		// DATOS DEL INTEGRADOR
		trafico.beans.jaxb.matw.FORMATOGA.MATRICULACION.DATOSIMPORTADOR integrador = mat.getDATOSIMPORTADOR();
		if (integrador != null) {
			if (null != integrador.getRAZONSOCIALIMPORTADOR() && (!integrador.getRAZONSOCIALIMPORTADOR().equals("")))
				vehiculo.setP_APELLIDO1_RAZON_SOCIAL_INTE(integrador.getRAZONSOCIALIMPORTADOR());
			else if (null != integrador.getAPELLIDO1IMPORTADOR() && (!integrador.getAPELLIDO1IMPORTADOR().equals("")))
				vehiculo.setP_APELLIDO1_RAZON_SOCIAL_INTE(integrador.getAPELLIDO1IMPORTADOR());
			else
				vehiculo.setP_APELLIDO1_RAZON_SOCIAL_INTE("");

			vehiculo.setP_APELLIDO2_INTE(integrador.getAPELLIDO2IMPORTADOR());
			vehiculo.setP_NOMBRE_INTE(integrador.getNOMBREIMPORTADOR());
			vehiculo.setP_NIF_INTEGRADOR(integrador.getDNIIMPORTADOR());
		}

		// Añadimos el tipo de vía y el número a la dirección del vehículo:
		if ((vehiculo.getP_VIA() != null && !"".equals(vehiculo.getP_VIA())) && (vehiculo.getP_MUNICIPIO() != null && !"".equals(vehiculo.getP_MUNICIPIO()))
				&& (vehiculo.getP_COD_POSTAL() != null && !"".equals(vehiculo.getP_COD_POSTAL())) && (vehiculo.getP_ID_PROVINCIA() != null && !"".equals(vehiculo.getP_ID_PROVINCIA()))) {
			if (!(vehiculo.getP_ID_TIPO_DGT() != null && !"".equals(vehiculo.getP_ID_TIPO_DGT())))
				vehiculo.setP_ID_TIPO_DGT(TIPO_DGT_BLANCO);
			if (!(vehiculo.getP_NUMERO() != null && !"".equals(vehiculo.getP_NUMERO())))
				vehiculo.setP_NUMERO(sinNumero);
		}

		// MATE 2.5
		if (vehiculoGa.getIDALIMENTACION() != null) {
			vehiculo.setP_ALIMENTACION(vehiculoGa.getIDALIMENTACION().value());
		}
		if (vehiculoGa.getIDCARROCERIA() != null) {
			vehiculo.setP_CARROCERIA(vehiculoGa.getIDCARROCERIA().value());
		}
		vehiculo.setP_CODIGO_ECO(vehiculoGa.getCODIGOECO());
		try {
			if (vehiculoGa.getCONSUMO() != null) {
				vehiculo.setP_CONSUMO(new BigDecimal(vehiculoGa.getCONSUMO()));
			}
		} catch (NumberFormatException e) {
			log.error("No se ha podido parsear el CONSUMO a BigDecimal para el paquete: " + e);
			vehiculo.setP_CONSUMO(null);
		}
		try {
			if (vehiculoGa.getDISTANCIAENTREEJES() != null) {
				vehiculo.setP_DIST_EJES(new BigDecimal(vehiculoGa.getDISTANCIAENTREEJES()));
			}
		} catch (NumberFormatException e) {
			log.error("No se ha podido parsear la DISTANCIA ENTRE EJES a BigDecimal para el paquete: " + e);
			vehiculo.setP_DIST_EJES(null);
		}

		vehiculo.setP_ECO_INNOVACION(vehiculoGa.getECOINNOVACION());
		vehiculo.setP_EMISIONES(vehiculoGa.getNIVELEMISIONES());
		vehiculo.setP_FABRICANTE(vehiculoGa.getFABRICANTE());
		if (vehiculoGa.getIDCARBURANTE() != null) {
			vehiculo.setP_ID_CARBURANTE(vehiculoGa.getIDCARBURANTE().value());
		}
		if (vehiculoGa.getIDHOMOLOGACION() != null) {
			vehiculo.setP_ID_DIRECTIVA_CEE(vehiculoGa.getIDHOMOLOGACION());
		}
		if (vehiculoGa.getIMPORTADO() != null && !"".equals(vehiculoGa.getIMPORTADO())
				&& (vehiculoGa.getIMPORTADO().trim().toUpperCase().equals(TipoSN.S.value()) || vehiculoGa.getIMPORTADO().trim().equalsIgnoreCase("SI"))) {
			vehiculo.setP_IMPORTADO(TipoSN.S.value());
		} else {
			vehiculo.setP_IMPORTADO(TipoSN.N.value());
		}

		try {
			if (vehiculoGa.getMOM() != null) {
				vehiculo.setP_MOM(new BigDecimal(vehiculoGa.getMOM()));
			}
		} catch (NumberFormatException e) {
			log.error("No se ha podido parsear la MOM a BigDecimal para el paquete: " + e);
			vehiculo.setP_MOM(null);
		}
		if (vehiculoGa.getREDUCCIONECO() != null) {
			vehiculo.setP_REDUCCION_ECO(new BigDecimal(vehiculoGa.getREDUCCIONECO()));
		}

		if (vehiculoGa.getSUBASTA() != null && !("".equals(vehiculoGa.getSUBASTA()))
				&& (("SI".equalsIgnoreCase(vehiculoGa.getSUBASTA().trim())) || (TipoSN.S.value().equals(vehiculoGa.getSUBASTA().trim().toUpperCase())))) {
			vehiculo.setP_SUBASTADO(TipoSN.S.value());
		} else {
			vehiculo.setP_SUBASTADO(TipoSN.N.value());
		}
		try {
			if (vehiculoGa.getVIAANTERIOR() != null) {
				vehiculo.setP_VIA_ANT(new BigDecimal(vehiculoGa.getVIAANTERIOR()));
			}
		} catch (NumberFormatException e) {
			log.error("No se ha podido parsear la VIA ANTERIOR a BigDecimal para el paquete: " + e);
			vehiculo.setP_VIA_ANT(null);
		}
		try {
			if (vehiculoGa.getVIAPOSTERIOR() != null) {
				vehiculo.setP_VIA_POS(new BigDecimal(vehiculoGa.getVIAPOSTERIOR()));
			}
		} catch (NumberFormatException e) {
			log.error("No se ha podido parsear la VIA POSTERIOR a BigDecimal para el paquete: " + e);
			vehiculo.setP_VIA_POS(null);
		}

		if (vehiculoGa.getCHECKCADUCIDADITV() != null) {
			vehiculo.setP_CHECK_FECHA_CADUCIDAD_ITV(vehiculoGa.getCHECKCADUCIDADITV());
		}

		if (vehiculoGa.getTIPOINSPECCIONITV() != null) {
			vehiculo.setP_ID_MOTIVO_ITV(vehiculoGa.getTIPOINSPECCIONITV().value());
		}

		if (vehiculoGa.getMATRICULAORIGEN() != null) {
			vehiculo.setP_MATRICULA_ORIGEN(vehiculoGa.getMATRICULAORIGEN().trim());
		}

		if (vehiculoGa.getESTACIONITV() != null) {
			vehiculo.setP_ESTACION_ITV(vehiculoGa.getESTACIONITV());
		}

		if (vehiculoGa.getMATRICULAORIGEXTR() != null) {
			vehiculo.setP_MATRICULA_ORIG_EXTR(vehiculoGa.getMATRICULAORIGEXTR().trim());
		}

		if (vehiculoGa.getPAISFABRICACION() != null) {
			try {
				vehiculo.setP_PAIS_FABRICACION(PaisFabricacion.getPaisFabricacion(vehiculoGa.getPAISFABRICACION()).getValorEnum());
			} catch (Exception e) {
				log.error("El pais de fabricacion no es correcto: " + vehiculoGa.getPAISFABRICACION());
				vehiculo.setP_PAIS_FABRICACION(null);
			}
		}

		if (vehiculoGa.getFICHATECNICARD750() != null && !vehiculoGa.getFICHATECNICARD750().equals("")
				&& vehiculoGa.getFICHATECNICARD750().equalsIgnoreCase("si")) {
			vehiculo.setP_FICHA_TECNICA_RD750(new BigDecimal(1));
		} else {
			vehiculo.setP_FICHA_TECNICA_RD750(new BigDecimal(0));
		}

		// Cambios para pase 1 de julio
		if (vehiculoGa.getMARCABASE() != null) {
			Long idMarca = servicioMarcaDgt.getCodigoFromMarca(vehiculoGa.getMARCABASE());
			if (idMarca != null) {
				vehiculo.setP_CODIGO_MARCA_BASE(new BigDecimal(idMarca));
			}
		}
		if (vehiculoGa.getFABRICANTEBASE() != null) {
			vehiculo.setP_FABRICANTE_BASE(vehiculoGa.getFABRICANTEBASE());
		}
		if (vehiculoGa.getTIPOBASE() != null) {
			vehiculo.setP_TIPO_BASE(vehiculoGa.getTIPOBASE());
		}
		if (vehiculoGa.getVARIANTEBASE() != null) {
			vehiculo.setP_VARIANTE_BASE(vehiculoGa.getVARIANTEBASE());
		}
		if (vehiculoGa.getVERSIONBASE() != null) {
			vehiculo.setP_VERSION_BASE(vehiculoGa.getVERSIONBASE());
		}
		if (vehiculoGa.getNUMEROHOMOLOGACIONBASE() != null) {
			vehiculo.setP_N_HOMOLOGACION_BASE(vehiculoGa.getNUMEROHOMOLOGACIONBASE());
		}
		if (vehiculoGa.getMOMBASE() != null) {
			vehiculo.setP_MOM_BASE(new BigDecimal(vehiculoGa.getMOMBASE()));
		}
		if (vehiculoGa.getCATEGORIAELECTRICA() != null) {
			vehiculo.setP_CATEGORIA_ELECTRICA(vehiculoGa.getCATEGORIAELECTRICA().value());
		}
		if (vehiculoGa.getAUTONOMIAELECTRICA() != null) {
			vehiculo.setP_AUTONOMIA_ELECTRICA(new BigDecimal(vehiculoGa.getAUTONOMIAELECTRICA()));
		}
		if (vehiculoGa.getVELOCIDADMAXIMA() != null) {
			try {
				vehiculo.setP_VELOCIDAD_MAXIMA(new BigDecimal(vehiculoGa.getVELOCIDADMAXIMA()));
			} catch (NumberFormatException e) {
				log.error("No se ha podido parsear la VELOCIDAD MAXIMA a BigDecimal: " + e);
				vehiculo.setP_VELOCIDAD_MAXIMA(null);
			}
		}

		return vehiculo;
	}
}