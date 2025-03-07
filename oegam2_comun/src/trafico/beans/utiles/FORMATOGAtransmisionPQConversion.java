package trafico.beans.utiles;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.gestoresmadrid.core.model.enumerados.TipoInterviniente;
import org.gestoresmadrid.core.springContext.ContextoSpring;
import org.gestoresmadrid.utilidades.components.Utiles;

import general.utiles.Anagrama;
import trafico.beans.daos.BeanPQTramiteTraficoGuardarIntervinienteImport;
import trafico.beans.daos.BeanPQTramiteTraficoGuardarTransmision;
import trafico.beans.daos.BeanPQTramiteTransmisionImport;
import trafico.beans.daos.BeanPQVehiculosGuardarImport;
import trafico.beans.jaxb.matw.dgt.tipos.TipoSN;
import trafico.beans.schemas.generated.transTelematica.TipoSINO;
import trafico.utiles.UtilesConversiones;
import trafico.utiles.enumerados.TipoTransferencia;
import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;

public class FORMATOGAtransmisionPQConversion {

	private static final ILoggerOegam log = LoggerOegam.getLogger(FORMATOGAtransmisionPQConversion.class);
	private static final String TIPO_DGT_BLANCO = "23";
	private static final String SIN_NUMERO = "SN";
	private static final String S = "S";
	private static final String N = "N";

	public static List<BeanPQTramiteTransmisionImport> convertirFORMATOGAtoPQ(trafico.beans.jaxb.transmision.FORMATOGA ga, Boolean electronica, BigDecimal idUsuario, BigDecimal idContrato) {
		List<BeanPQTramiteTransmisionImport> lista = new ArrayList<>();

		for (int i=0; i<ga.getTRANSMISION().size(); i++) {
			BeanPQVehiculosGuardarImport vehiculo = new BeanPQVehiculosGuardarImport();
			BeanPQTramiteTraficoGuardarIntervinienteImport adquiriente = new BeanPQTramiteTraficoGuardarIntervinienteImport();
			BeanPQTramiteTraficoGuardarIntervinienteImport representanteAdquiriente = new BeanPQTramiteTraficoGuardarIntervinienteImport();
			// DRC@02-10-2012 Incidencia: 1801
			//BeanPQTramiteTraficoGuardarIntervinienteImport compraventa = new BeanPQTramiteTraficoGuardarIntervinienteImport();
			//BeanPQTramiteTraficoGuardarIntervinienteImport representanteCompraventa = new BeanPQTramiteTraficoGuardarIntervinienteImport();
			BeanPQTramiteTraficoGuardarIntervinienteImport arrendador = new BeanPQTramiteTraficoGuardarIntervinienteImport();
			BeanPQTramiteTraficoGuardarIntervinienteImport representanteArrendador = new BeanPQTramiteTraficoGuardarIntervinienteImport();
			BeanPQTramiteTraficoGuardarIntervinienteImport compraventa = new BeanPQTramiteTraficoGuardarIntervinienteImport();
			BeanPQTramiteTraficoGuardarIntervinienteImport representanteCompraventa = new BeanPQTramiteTraficoGuardarIntervinienteImport();

			BeanPQTramiteTraficoGuardarIntervinienteImport presentador = new BeanPQTramiteTraficoGuardarIntervinienteImport();
			BeanPQTramiteTraficoGuardarIntervinienteImport transmitente = new BeanPQTramiteTraficoGuardarIntervinienteImport();
			BeanPQTramiteTraficoGuardarIntervinienteImport conductorHabitual = new BeanPQTramiteTraficoGuardarIntervinienteImport();
			BeanPQTramiteTraficoGuardarIntervinienteImport primerCotitular = new BeanPQTramiteTraficoGuardarIntervinienteImport();
			BeanPQTramiteTraficoGuardarIntervinienteImport segundoCotitular = new BeanPQTramiteTraficoGuardarIntervinienteImport();
			BeanPQTramiteTraficoGuardarIntervinienteImport representanteTransmitente = new BeanPQTramiteTraficoGuardarIntervinienteImport();
			BeanPQTramiteTraficoGuardarTransmision tramite = new BeanPQTramiteTraficoGuardarTransmision();

			if (null!=ga.getTRANSMISION().get(i)) {
				if (null!=ga.getTRANSMISION().get(i).getDATOSVEHICULO()) vehiculo = convertirVehiculoGAtoPQ(ga,ga.getTRANSMISION().get(i));
				if (null!=ga.getTRANSMISION().get(i).getDATOSADQUIRIENTE()) adquiriente = convertirIntervinienteGAtoPQ(ga,TipoInterviniente.Adquiriente.getValorEnum(),ga.getTRANSMISION().get(i),idUsuario,idContrato);
				if (null!=ga.getTRANSMISION().get(i).getDATOSADQUIRIENTE()) representanteAdquiriente = convertirIntervinienteGAtoPQ(ga,TipoInterviniente.RepresentanteAdquiriente.getValorEnum(),ga.getTRANSMISION().get(i),idUsuario,idContrato);
				// DRC@02-10-2012 Incidencia: 1801
				//if (null!=ga.getTRANSMISION().get(i).getDATOSARRENDADORCOMPRAVENTA()) compraventa = convertirIntervinienteGAtoPQ(ga,TipoInterviniente.Compraventa.getValorEnum(),ga.getTRANSMISION().get(i),idUsuario,idContrato);
				//if (null!=ga.getTRANSMISION().get(i).getDATOSARRENDADORCOMPRAVENTA()) representanteCompraventa = convertirIntervinienteGAtoPQ(ga,TipoInterviniente.RepresentanteCompraventa.getValorEnum(),ga.getTRANSMISION().get(i),idUsuario,idContrato);
				if (null!=ga.getTRANSMISION().get(i).getDATOSARRENDADOR()) arrendador = convertirIntervinienteGAtoPQ(ga,TipoInterviniente.Arrendatario.getValorEnum(),ga.getTRANSMISION().get(i),idUsuario,idContrato);
				if (null!=ga.getTRANSMISION().get(i).getDATOSARRENDADOR()) representanteArrendador = convertirIntervinienteGAtoPQ(ga,TipoInterviniente.RepresentanteArrendatario.getValorEnum(),ga.getTRANSMISION().get(i),idUsuario,idContrato);
				if (null!=ga.getTRANSMISION().get(i).getDATOSCOMPRAVENTA()) compraventa = convertirIntervinienteGAtoPQ(ga,TipoInterviniente.Compraventa.getValorEnum(),ga.getTRANSMISION().get(i),idUsuario,idContrato);
				if (null!=ga.getTRANSMISION().get(i).getDATOSCOMPRAVENTA()) representanteCompraventa = convertirIntervinienteGAtoPQ(ga,TipoInterviniente.RepresentanteCompraventa.getValorEnum(),ga.getTRANSMISION().get(i),idUsuario,idContrato);

				if (null!=ga.getTRANSMISION().get(i).getDATOSPRESENTADOR()) presentador = convertirIntervinienteGAtoPQ(ga,TipoInterviniente.PresentadorTrafico.getValorEnum(),ga.getTRANSMISION().get(i),idUsuario,idContrato);
				if (null!=ga.getTRANSMISION().get(i).getDATOSTRANSMITENTE()) transmitente = convertirIntervinienteGAtoPQ(ga,TipoInterviniente.TransmitenteTrafico.getValorEnum(),ga.getTRANSMISION().get(i),idUsuario,idContrato);
				if (null!=ga.getTRANSMISION().get(i).getDATOSADQUIRIENTE()) conductorHabitual = convertirIntervinienteGAtoPQ(ga,TipoInterviniente.ConductorHabitual.getValorEnum(),ga.getTRANSMISION().get(i),idUsuario,idContrato);
				if (null!=ga.getTRANSMISION().get(i).getDATOSTRANSMITENTE()) primerCotitular = convertirIntervinienteGAtoPQ(ga,TipoInterviniente.CotitularTransmision.getValorEnum(),ga.getTRANSMISION().get(i),idUsuario,idContrato);
				if (null!=ga.getTRANSMISION().get(i).getDATOSTRANSMITENTE()) segundoCotitular = convertirIntervinienteGAtoPQ(ga,"segundo" + TipoInterviniente.CotitularTransmision.getValorEnum(),ga.getTRANSMISION().get(i),idUsuario,idContrato);
				if (null!=ga.getTRANSMISION().get(i).getDATOSTRANSMITENTE()) representanteTransmitente = convertirIntervinienteGAtoPQ(ga,TipoInterviniente.RepresentanteTransmitente.getValorEnum(),ga.getTRANSMISION().get(i),idUsuario,idContrato);
				tramite = convertirTramiteTransmisionToPQ(ga,ga.getTRANSMISION().get(i), electronica, idUsuario , idContrato);
			}

			BeanPQTramiteTransmisionImport beanTramiteTransmision = null;

			if (tramite != null) {
				beanTramiteTransmision = new BeanPQTramiteTransmisionImport();
				beanTramiteTransmision.setBeanGuardarTransmision(tramite);
				beanTramiteTransmision.setBeanGuardarVehiculo(vehiculo);
				beanTramiteTransmision.setBeanGuardarAdquiriente(adquiriente);
				beanTramiteTransmision.setBeanGuardarRepresentanteAdquiriente(representanteAdquiriente);
				// DRC@02-10-2012 Incidencia: 1801
				beanTramiteTransmision.setBeanGuardarPoseedor(compraventa);
				beanTramiteTransmision.setBeanGuardarArrendador(arrendador);
				//beanTramiteTransmision.setBeanGuardarCompraventa(compraventa);
				beanTramiteTransmision.setBeanGuardarRepresentantePoseedor(representanteCompraventa);
				beanTramiteTransmision.setBeanGuardarRepresentanteArrendador(representanteArrendador);
				//beanTramiteTransmision.setBeanGuardarRepresentanteCompraVenta(representanteCompraventa);

				beanTramiteTransmision.setBeanGuardarPresentador(presentador);
				beanTramiteTransmision.setBeanGuardarTransmitente(transmitente);
				beanTramiteTransmision.setBeanGuardarRepresentanteTransmitente(representanteTransmitente);
				beanTramiteTransmision.setBeanGuardarConductorHabitual(conductorHabitual);
				beanTramiteTransmision.setBeanGuardarPrimerCotitular(primerCotitular);
				beanTramiteTransmision.setBeanGuardarSegundoCotitular(segundoCotitular);
				lista.add(beanTramiteTransmision);
			}
		}
		return lista;
	}

	private static BeanPQTramiteTraficoGuardarTransmision convertirTramiteTransmisionToPQ(
			trafico.beans.jaxb.transmision.FORMATOGA ga,
			trafico.beans.jaxb.transmision.TRANSMISION transmision,
			Boolean electronica,BigDecimal idUsuario, BigDecimal idContrato) {

		UtilesConversiones utilesConversiones = ContextoSpring.getInstance().getBean(UtilesConversiones.class);

		BeanPQTramiteTraficoGuardarTransmision tramite = new BeanPQTramiteTraficoGuardarTransmision();

		tramite.setP_ID_USUARIO(idUsuario);
		try {
			if ("OEGAM".equals(ga.getPlataforma())) tramite.setP_NUM_EXPEDIENTE(new BigDecimal(transmision.getNUMERODOCUMENTO()));
			else tramite.setP_NUM_EXPEDIENTE(null);
		} catch(Exception e){
			tramite.setP_NUM_EXPEDIENTE(null);
			log.debug("El numero del documento no es un bigDecimal");
		}
		if (electronica) {
			tramite.setP_ELECTRONICA("S");
		} else {
			tramite.setP_ELECTRONICA("N");
		}
		tramite.setP_ID_CONTRATO_SESSION(idContrato);
		try {
			if (new BigDecimal(ga.getCABECERA().getDATOSGESTORIA().getPROFESIONAL())!=null) {
				String valor = new BigDecimal(ga.getCABECERA().getDATOSGESTORIA().getPROFESIONAL()).toString();
				if (valor.length() == 5) {
					valor = valor.substring(0, 4);
				}
				while (valor.length()<4) {
					valor = "0" + valor;
				}
				tramite.setP_NUM_COLEGIADO(valor);
			}
		} catch (Exception e) {
			tramite.setP_NUM_COLEGIADO(null);
		}
		//tramite.setP_ID_VEHICULO(); //Se setea después de guardar el vehículo

		tramite.setP_REF_PROPIA(transmision.getREFERENCIAPROPIA());
		tramite.setP_FECHA_ALTA(DDMMAAAAToTimestamp(transmision.getFECHACREACION()));
		if (transmision.getDATOSPRESENTACION() != null && transmision.getDATOSPRESENTACION().getJEFATURAPROVINCIAL() != null) {
			tramite.setP_JEFATURA_PROVINCIAL(transmision.getDATOSPRESENTACION().getJEFATURAPROVINCIAL().trim());
		}
		tramite.setP_ANOTACIONES(transmision.getOBSERVACIONES());

		if (transmision.getDATOSLIMITACION() != null) {
			try {
				tramite.setP_IEDTM("E".equalsIgnoreCase(transmision.getDATOSLIMITACION().getTIPOLIMITACION())?"E":"");
			} catch (Exception e) {
				tramite.setP_IEDTM("");
			}

			tramite.setP_FECHA_IEDTM(DDMMAAAAToTimestamp(transmision.getDATOSLIMITACION().getFECHALIMITACION()));
			tramite.setP_N_REG_IEDTM(transmision.getDATOSLIMITACION().getNUMEROREGISTROLIMITACION());
			tramite.setP_FINANCIERA_IEDTM(transmision.getDATOSLIMITACION().getFINANCIERALIMITACION());
		}
		//Valores propios de la Transmisión
		tramite.setP_MODO_ADJUDICACION(transmision.getDATOSVEHICULO().getMODOADJUDICACION());
		tramite.setP_TIPO_TRANSFERENCIA(transmision.getDATOSVEHICULO().getTIPOTRANSFERENCIA());
		try {
			tramite.setP_ACEPTACION_RESPONS_ITV("SI".equalsIgnoreCase(transmision.getDATOSVEHICULO().getDECLARACIONRESPONSABILIDAD())?"SI":"NO");
		} catch (Exception e) {
			tramite.setP_ACEPTACION_RESPONS_ITV("");
		}

		//Incidencia 3038 Localización Vehículo
	/*	try {
			tramite.setP_CONSENTIMIENTO_CAMBIO("SI".equals(transmision.getDATOSVEHICULO().getCONSENTIMIENTOCAMBIO())?"SI":"NO");
		} catch (Exception e) {
			tramite.setP_CONSENTIMIENTO_CAMBIO("");
		}*/
		// Fin 3038

		try {
			tramite.setP_RENTING("SI".equalsIgnoreCase(transmision.getRENTING())?"SI":"NO");
		} catch (Exception e) {
			tramite.setP_RENTING("");
		}

		try {
			tramite.setP_SIMULTANEA(transmision.getSIMULTANEA()!=null?new BigDecimal(transmision.getSIMULTANEA()):new BigDecimal(-1));
		} catch (Exception e) {
			tramite.setP_SIMULTANEA(null);
		}

		try {
			tramite.setP_CAMBIO_SERVICIO("SI".equalsIgnoreCase(transmision.getCAMBIOSERVICIO())?"SI":"NO");
		} catch (Exception e) {
			tramite.setP_CAMBIO_SERVICIO("");
		}

		if (transmision.getDATOSPRESENTACION()!=null) {
			tramite.setP_FECHA_PRESENTACION(DDMMAAAAToTimestamp(transmision.getDATOSPRESENTACION().getFECHAPRESENTACION()));
			tramite.setP_CODIGO_TASA(transmision.getDATOSPRESENTACION().getCODIGOTASA());
			tramite.setP_CODIGO_TASA_INF(transmision.getDATOSPRESENTACION().getCODIGOTASAINFORME());
			tramite.setP_CODIGO_TASA_CAMSER(transmision.getDATOSPRESENTACION().getCODIGOTASACAMBIO());
			tramite.setP_TIPO_TASA(transmision.getDATOSPRESENTACION().getTIPOTASA());

			tramite.setP_MODELO_ITP(transmision.getDATOSPRESENTACION().getMODELOITP());
			try {
				tramite.setP_EXENTO_ITP(transmision.getDATOSPRESENTACION().getEXENTOITP().toUpperCase());
			} catch (Exception e) {
				tramite.setP_EXENTO_ITP(null);
			}
			try {
				tramite.setP_NO_SUJETO_ITP(transmision.getDATOSPRESENTACION().getNOSUJETOITP().toUpperCase());
			} catch (Exception e) {
				tramite.setP_NO_SUJETO_ITP(null);
			}

			tramite.setP_CET_ITP(transmision.getDATOSPRESENTACION().getCODIGOELECTRONICOTRANSFERENCIA());
			tramite.setP_NUM_AUTO_ITP(transmision.getDATOSPRESENTACION().getNUMAUTOITP());

			tramite.setP_NUM_AUTO_ISD(transmision.getDATOSPRESENTACION().getNUMADJUDICACIONISD());
			tramite.setP_MODELO_ISD(transmision.getDATOSPRESENTACION().getMODELOISD());
			try {
				tramite.setP_EXENCION_ISD(transmision.getDATOSPRESENTACION().getEXENTOISD().toUpperCase());
			} catch (Exception e) {
				tramite.setP_EXENCION_ISD(null);
			}
			try {
				tramite.setP_NO_SUJETO_ISD(transmision.getDATOSPRESENTACION().getNOSUJETOISD().toUpperCase());
			} catch (Exception e) {
				tramite.setP_NO_SUJETO_ISD(null);
			}

			tramite.setP_CEM(transmision.getDATOSPRESENTACION().getCODIGOELECTRONICOMATRICULACION());
			tramite.setP_MODELO_IEDTM(transmision.getDATOSPRESENTACION().getMODELOIEDMT());
			tramite.setP_CEMA(transmision.getDATOSPRESENTACION().getCEMA());
			try {
				tramite.setP_EXENTO_CEM(transmision.getDATOSPRESENTACION().getEXENTOCEM().toUpperCase());
			} catch (Exception e) {
				tramite.setP_EXENTO_CEM(null);
			}
			try {
				tramite.setP_EXENTO_IEDTM(transmision.getDATOSPRESENTACION().getEXENTOIEDMT().toUpperCase());
			} catch (Exception e) {
				tramite.setP_EXENTO_IEDTM(null);
			}
			try {
				tramite.setP_NO_SUJECION_IEDTM(transmision.getDATOSPRESENTACION().getNOSUJETOIEDMT().toUpperCase());
			} catch (Exception e) {
				tramite.setP_NO_SUJECION_IEDTM(null);
			}

			tramite.setP_FUNDAMENTO_EXENCION(transmision.getDATOSPRESENTACION().getIDREDUCCION05());
			tramite.setP_ID_REDUCCION_05(transmision.getDATOSPRESENTACION().getIDREDUCCION05());
			tramite.setP_ID_NO_SUJECCION_06(transmision.getDATOSPRESENTACION().getIDNOSUJECCION06());

			tramite.setP_DUA(transmision.getDATOSPRESENTACION().getDUA());
			tramite.setP_ALTA_IVTM(transmision.getDATOSPRESENTACION().getALTAIVTM());
			tramite.setP_FACTURA(transmision.getDATOSPRESENTACION().getNUMEROFACTURA());

			//DRC@31-10-2012 Incidencia: 2765
			if (transmision != null && transmision.getDATOSPRESENTACION() != null && transmision.getDATOSPRESENTACION().getPROVINCIACET() != null)
				tramite.setP_ID_PROVINCIA_CET(utilesConversiones.getIdProvinciaFromSiglas(transmision.getDATOSPRESENTACION().getPROVINCIACET()));
			else
				tramite.setP_ID_PROVINCIA_CET("");

			//DRC@25-01-2013 Incidencia: 3437
			if (transmision != null && transmision.getDATOSPRESENTACION() != null && transmision.getDATOSPRESENTACION().getPROVINCIACEM() != null)
				tramite.setP_ID_PROVINCIA_CEM(utilesConversiones.getIdProvinciaFromSiglas(transmision.getDATOSPRESENTACION().getPROVINCIACEM()));
			else
				tramite.setP_ID_PROVINCIA_CEM("");

			if (transmision != null && transmision.getDATOSPRESENTACION() != null && transmision.getDATOSPRESENTACION().getFECHAFACTURA() != null)
				tramite.setP_FECHA_FACTURA(DDMMAAAAToTimestamp(transmision.getDATOSPRESENTACION().getFECHAFACTURA()));

			if (transmision != null && transmision.getDATOSPRESENTACION() != null && transmision.getDATOSPRESENTACION().getFECHACONTRATO() != null)
				tramite.setP_FECHA_CONTRATO(DDMMAAAAToTimestamp(transmision.getDATOSPRESENTACION().getFECHACONTRATO()));
		}

		if (transmision.getDATOS620() != null) {
			try {
				tramite.setP_ESTADO_620(new BigDecimal(transmision.getDATOS620().getESTADO620()));
			} catch (Exception e) {
				tramite.setP_ESTADO_620(null);
			}
			tramite.setP_FUNDAMENTO_EXENCION(transmision.getDATOS620().getFUNDAMENTOEXENCION());
			tramite.setP_EXENTO_ITP(transmision.getDATOS620().getEXENTO());
			tramite.setP_NO_SUJETO_ITP(transmision.getDATOS620().getNOSUJETO());
			tramite.setP_FUNDAMENTO_NO_SUJETO_ITP(transmision.getDATOS620().getFUNDAMENTONOSUJETO());
			tramite.setP_FECHA_DEVENGO_ITP(DDMMAAAAToTimestamp(transmision.getDATOS620().getFECHADEVENGO()));
			try {
				tramite.setP_OFICINA_LIQUIDADORA(transmision.getDATOS620().getOFICINALIQUIDADORA());
			} catch (Exception e) {
				tramite.setP_OFICINA_LIQUIDADORA(null);
			}
			try {
				tramite.setP_P_REDUCCION_ANUAL(stringDecimalToBigDecimal(transmision.getDATOS620().getPORCENTAJEREDUCCIONANUAL()));
			} catch (Exception e) {
				tramite.setP_P_REDUCCION_ANUAL(null);
			}
			try {
				tramite.setP_VALOR_DECLARADO(stringDecimalToBigDecimal(transmision.getDATOS620().getVALORDECLARADO()));
			} catch (Exception e) {
				tramite.setP_VALOR_DECLARADO(null);
			}
			try {
				tramite.setP_P_ADQUISICION(stringDecimalToBigDecimal(transmision.getDATOS620().getPORCENTAJEADQUISICION()));
			} catch (Exception e) {
				tramite.setP_P_ADQUISICION(null);
			}
			try {
				tramite.setP_BASE_IMPONIBLE(stringDecimalToBigDecimal(transmision.getDATOS620().getBASEIMPONIBLE()));
			} catch (Exception e) {
				tramite.setP_BASE_IMPONIBLE(null);
			}
			try {
				tramite.setP_TIPO_GRAVAMEN(stringDecimalToBigDecimal(transmision.getDATOS620().getTIPOGRAVAMEN()));
			} catch (Exception e) {
				tramite.setP_TIPO_GRAVAMEN(null);
			}
			try {
				tramite.setP_CUOTA_TRIBUTARIA(stringDecimalToBigDecimal(transmision.getDATOS620().getCUOTATRIBUTARIA()));
			} catch (Exception e) {
				tramite.setP_CUOTA_TRIBUTARIA(null);
			}
			tramite.setP_SUBASTA(transmision.getDATOS620().getSUBASTA());
			tramite.setP_TIPO_MOTOR(transmision.getDATOS620().getTIPOMOTOR());
			tramite.setP_PROCEDENCIA_620(transmision.getDATOS620().getPROCEDENCIA620());
			tramite.setP_TIPO_REDUCCION(transmision.getDATOS620().getREDUCCIONCODIGO());
//			if (transmision.getDATOS620().getDIRECCIONDISTINTAADQUIRIENTE()!=null){
//				tramite.setP_CONSENTIMIENTO_CAMBIO("SI");
//			}
		}

		try {
			if (tramite.getP_TIPO_TRANSFERENCIA() != null && !tramite.getP_TIPO_TRANSFERENCIA().isEmpty()) {
				if (TipoTransferencia.tipo4.getValorEnum().equals(tramite.getP_TIPO_TRANSFERENCIA()) || TipoTransferencia.tipo5.getValorEnum().equals(tramite.getP_TIPO_TRANSFERENCIA())) {
					tramite.setP_IMPR_PERMISO_CIRCU("NO");
				} else {
					tramite.setP_IMPR_PERMISO_CIRCU(transmision.getIMPRESOPERMISO().trim().toUpperCase());
				}
			}
		} catch (Exception e) {
			tramite.setP_IMPR_PERMISO_CIRCU(null);
		}
		try {
			tramite.setP_CONSENTIMIENTO(transmision.getCONSENTIMIENTO().toUpperCase());
		} catch (Exception e) {
			tramite.setP_CONSENTIMIENTO(null);
		}
		try {
			tramite.setP_CONTRATO_FACTURA(transmision.getCONTRATOCOMPRAVENTA().toUpperCase());
		} catch (Exception e) {
			tramite.setP_CONTRATO_FACTURA(null);
		}
		// DVV: Descomentarse para unificación
//		try {
//			tramite.setP_ADJUDICACION(transmision.getADJUDICACION());
//		} catch (Exception e) {
//			tramite.setP_ADJUDICACION(null);
//		}
		// DVV: Comentarse para unificación
		try {
			tramite.setP_ACTA_SUBASTA(transmision.getACTAADJUDICACION().toUpperCase());
		} catch (Exception e) {
			tramite.setP_ACTA_SUBASTA(null);
		}
		try {
			tramite.setP_SENTENCIA_JUDICIAL(transmision.getSENTENCIAADJUDICACION().toUpperCase());
		} catch (Exception e) {
			tramite.setP_SENTENCIA_JUDICIAL(null);
		}

		try {
			tramite.setP_ACREDITA_HERENCIA_DONACION(transmision.getACREDITACION());
		} catch (Exception e) {
			tramite.setP_ACREDITA_HERENCIA_DONACION(null);
		}

		//Mantis 25415
		try {
			tramite.setP_VALOR_REAL(transmision.getDATOSPRESENTACION().getVALORREAL());
		} catch (Exception e) {
			tramite.setP_VALOR_REAL(null);
		}

		//DVV
//		try {
//			tramite.setP_CAMBIO_SOCIETARIO(transmision.get);
//		} catch (Exception e) {
//			tramite.setP_CAMBIO_SOCIETARIO(null);
//		}
//		tramite.setP_CAMBIO_SOCIETARIO(new BigDecimal(1));
//		tramite.setP_MODIFICACION_NO_CONSTANTE(new BigDecimal(1));
		return tramite;
	}

	private static BeanPQTramiteTraficoGuardarIntervinienteImport convertirIntervinienteGAtoPQ(
			trafico.beans.jaxb.transmision.FORMATOGA ga, String tipoInterviniente,
			trafico.beans.jaxb.transmision.TRANSMISION trans, BigDecimal idUsuario, BigDecimal idContrato) {
		UtilesConversiones utilesConversiones = ContextoSpring.getInstance().getBean(UtilesConversiones.class);

		BeanPQTramiteTraficoGuardarIntervinienteImport interviniente = new BeanPQTramiteTraficoGuardarIntervinienteImport();
		if (TipoInterviniente.Adquiriente.getValorEnum().equals(tipoInterviniente)) {
			trafico.beans.jaxb.transmision.DATOSADQUIRIENTE adquirente = trans.getDATOSADQUIRIENTE();

			interviniente.setP_ID_USUARIO(idUsuario);
			//interviniente.setP_NUM_EXPEDIENTE(); // Se setea en el modelo tras guardar el trámite
			interviniente.setP_ID_CONTRATO_SESSION(idContrato);

			//INFORMACIÓN DEL INTERVINIENTE
			interviniente.setP_TIPO_INTERVINIENTE(TipoInterviniente.Adquiriente.getValorEnum());
			interviniente.setP_CAMBIO_DOMICILIO(null!=adquirente.getACTUALIZACIONDOMICILIOADQUIRIENTE()?
					adquirente.getACTUALIZACIONDOMICILIOADQUIRIENTE().toUpperCase():null);
			interviniente.setP_AUTONOMO(null!=adquirente.getAUTONOMOADQUIRIENTE()?
					adquirente.getAUTONOMOADQUIRIENTE().toUpperCase():null);
			interviniente.setP_CODIGO_IAE(adquirente.getCODIGOIAEADQUIRIENTE());

			//INFORMACIÓN DE LA PERSONA ASOCIADA AL INVOLUCRADO
			try {
				if (new BigDecimal(ga.getCABECERA().getDATOSGESTORIA().getPROFESIONAL()) != null) {
					String valor = new BigDecimal(ga.getCABECERA().getDATOSGESTORIA().getPROFESIONAL()).toString();
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

			interviniente.setP_NIF(adquirente.getDNIADQUIRIENTE());
			interviniente.setP_APELLIDO1_RAZON_SOCIAL(adquirente.getAPELLIDO1RAZONSOCIALADQUIRIENTE());
			interviniente.setP_APELLIDO2(adquirente.getAPELLIDO2ADQUIRIENTE());
			interviniente.setP_NOMBRE(adquirente.getNOMBREADQUIRIENTE());
			if (interviniente.getP_NIF()!=null && interviniente.getP_APELLIDO1_RAZON_SOCIAL() != null
					&& !"".equals(interviniente.getP_NIF()) && !"".equals(interviniente.getP_APELLIDO1_RAZON_SOCIAL())
					&& utilesConversiones.isNifNie(interviniente.getP_NIF())) {
				interviniente.setP_ANAGRAMA(Anagrama.obtenerAnagramaFiscal(interviniente.getP_APELLIDO1_RAZON_SOCIAL(), interviniente.getP_NIF()));
			}
			interviniente.setP_FECHA_NACIMIENTO(DDMMAAAAToTimestamp(adquirente.getFECHANACIMIENTOADQUIRIENTE()));
			//Cambios urgentes de Yerbabuena
			interviniente.setP_TIPO_DOCUMENTO_ALTERNATIVO(adquirente.getTIPODOCUMENTOSUSTITUTIVOADQUIRIENTE());
			interviniente.setP_FECHA_CADUCIDAD_NIF(DDMMAAAAToTimestamp(adquirente.getFECHACADUCIDADNIFADQUIRIENTE()));
			interviniente.setP_FECHA_CADUCIDAD_ALTERNATIVO(DDMMAAAAToTimestamp(adquirente.getFECHACADUCIDADDOCUMENTOSUSTITUTIVOADQUIRIENTE()));
			interviniente.setP_INDEFINIDO(adquirente.getINDEFINIDOADQUIRIENTE()!=null?(adquirente.getINDEFINIDOADQUIRIENTE().equalsIgnoreCase(TipoSINO.SI.value()) ||adquirente.getINDEFINIDOADQUIRIENTE().equalsIgnoreCase("S")?S:N):N);
			/**/
			interviniente.setP_TELEFONOS(adquirente.getTELEFONOADQUIRIENTE());
			interviniente.setP_SEXO(adquirente.getSEXOADQUIRIENTE());

			// INFORMACIÓN DE LA DIRECCIÓN
			interviniente.setP_ID_PROVINCIA(utilesConversiones.getIdProvinciaFromSiglas(adquirente.getPROVINCIAADQUIRIENTE()));
			interviniente.setP_NUMERO(adquirente.getNUMERODIRECCIONADQUIRIENTE());
			interviniente.setP_COD_POSTAL(adquirente.getCPADQUIRIENTE());
			interviniente.setP_LETRA(adquirente.getLETRADIRECCIONADQUIRIENTE());
			interviniente.setP_ESCALERA(adquirente.getESCALERADIRECCIONADQUIRIENTE());
			interviniente.setP_PLANTA(adquirente.getPISODIRECCIONADQUIRIENTE());
			interviniente.setP_PUERTA(adquirente.getPUERTADIRECCIONADQUIRIENTE());

			// Nuevo XSD
			interviniente.setP_BLOQUE(adquirente.getBLOQUEDIRECCIONADQUIRIENTE());

			BigDecimal kmAux = null != adquirente.getKMDIRECCIONADQUIRIENTE()
				&& !"".equals(adquirente.getKMDIRECCIONADQUIRIENTE()) ?
					new BigDecimal(adquirente.getKMDIRECCIONADQUIRIENTE()) : null;
			interviniente.setP_KM(kmAux);

			BigDecimal hmAux = null != adquirente.getHMDIRECCIONADQUIRIENTE()
				&& !"".equals(adquirente.getHMDIRECCIONADQUIRIENTE()) ?
					new BigDecimal(adquirente.getHMDIRECCIONADQUIRIENTE()) : null;
			interviniente.setP_HM(hmAux);

			// Mantis 17701
			if (adquirente.getDIRECCIONACTIVAADQUIRIENTE() !=null && !adquirente.getDIRECCIONACTIVAADQUIRIENTE().isEmpty()
					&& (("SI").equalsIgnoreCase(adquirente.getDIRECCIONACTIVAADQUIRIENTE().trim())
					|| (TipoSN.S.value()).equals(adquirente.getDIRECCIONACTIVAADQUIRIENTE().trim().toUpperCase()))) {
				interviniente.setP_DIRECCION_ACTIVA(TipoSN.S.value());
			} else if (adquirente.getDIRECCIONACTIVAADQUIRIENTE() !=null && !adquirente.getDIRECCIONACTIVAADQUIRIENTE().isEmpty()
					&& (("NO").equalsIgnoreCase(adquirente.getDIRECCIONACTIVAADQUIRIENTE().trim())
					|| (TipoSN.N.value()).equals(adquirente.getDIRECCIONACTIVAADQUIRIENTE().trim().toUpperCase()))) {
				interviniente.setP_DIRECCION_ACTIVA(TipoSN.N.value());
			}

			//INFORMACIÓN PARA LAS IMPORTACIONES
			String valor = adquirente.getSIGLASDIRECCIONADQUIRIENTE();
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
			interviniente.setP_ID_TIPO_DGT(valor.trim());
			interviniente.setP_MUNICIPIO(adquirente.getMUNICIPIOADQUIRIENTE());
			interviniente.setP_PUEBLO_LIT(adquirente.getPUEBLOADQUIRIENTE());
			interviniente.setP_VIA(adquirente.getNOMBREVIADIRECCIONADQUIRIENTE());
		}

		if (TipoInterviniente.RepresentanteAdquiriente.getValorEnum().equals(tipoInterviniente)) {
			trafico.beans.jaxb.transmision.DATOSADQUIRIENTE adquirente = trans.getDATOSADQUIRIENTE();

			interviniente.setP_ID_USUARIO(idUsuario);
			//interviniente.setP_NUM_EXPEDIENTE(); // Se setea en el modelo tras guardar el trámite
			interviniente.setP_ID_CONTRATO_SESSION(idContrato);

			//INFORMACION DEL INTERVINIENTE
			interviniente.setP_TIPO_INTERVINIENTE(TipoInterviniente.RepresentanteAdquiriente.getValorEnum());
			interviniente.setP_FECHA_INICIO(DDMMAAAAToTimestamp(adquirente.getFECHAINICIOTUTELAADQUIERIENTE()));
			interviniente.setP_FECHA_FIN(DDMMAAAAToTimestamp(adquirente.getFECHAFINTUTELAADQUIERIENTE()));
			interviniente.setP_CONCEPTO_REPRE(adquirente.getCONCEPTOREPRESENTANTEADQUIRIENTE());
			interviniente.setP_ID_MOTIVO_TUTELA(adquirente.getMOTIVOREPRESENTANTEADQUIRIENTE());
			interviniente.setP_DATOS_DOCUMENTO(adquirente.getDOCUMENTOSREPRESENTANTEADQUIRIENTE());

			//INFORMACIÓN DE LA PERSONA ASOCIADA AL INVOLUCRADO
			try {
				if (new BigDecimal(ga.getCABECERA().getDATOSGESTORIA().getPROFESIONAL())!=null) {
					String valor = new BigDecimal(ga.getCABECERA().getDATOSGESTORIA().getPROFESIONAL()).toString();
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

			interviniente.setP_NIF(adquirente.getDNIREPRESENTANTEADQUIRIENTE());

			interviniente.setP_APELLIDO2(adquirente.getAPELLIDO2REPRESENTANTEADQUIRIENTE());
			interviniente.setP_NOMBRE(adquirente.getNOMBREREPRESENTANTEADQUIRIENTE());
			interviniente.setP_APELLIDO1_RAZON_SOCIAL(adquirente.getAPELLIDO1RAZONSOCIALREPRESENTANTEADQUIRIENTE());
			//Cambios urgentes de Yerbabuena
			interviniente.setP_TIPO_DOCUMENTO_ALTERNATIVO(adquirente.getTIPODOCUMENTOSUSTITUTIVOREPRESENTANTEADQUIRIENTE());
			interviniente.setP_FECHA_CADUCIDAD_NIF(DDMMAAAAToTimestamp(adquirente.getFECHACADUCIDADNIFREPRESENTANTEADQUIRIENTE()));
			interviniente.setP_FECHA_CADUCIDAD_ALTERNATIVO(DDMMAAAAToTimestamp(adquirente.getFECHACADUCIDADDOCUMENTOSUSTITUTIVOREPRESENTANTEADQUIRIENTE()));
			interviniente.setP_INDEFINIDO(adquirente.getINDEFINIDOREPRESENTANTEADQUIRIENTE()!=null?(adquirente.getINDEFINIDOREPRESENTANTEADQUIRIENTE().equalsIgnoreCase(TipoSINO.SI.value()) ||adquirente.getINDEFINIDOREPRESENTANTEADQUIRIENTE().equalsIgnoreCase("S")?S:N):N);
			/**/
			if (!"".equals(adquirente.getNOMBREAPELLIDOSREPRESENTANTEADQUIRIENTE()) && (
				("".equals(adquirente.getNOMBREREPRESENTANTEADQUIRIENTE())|| null==adquirente.getNOMBREREPRESENTANTEADQUIRIENTE()) &&
				("".equals(adquirente.getAPELLIDO1RAZONSOCIALREPRESENTANTEADQUIRIENTE())|| null==adquirente.getAPELLIDO1RAZONSOCIALREPRESENTANTEADQUIRIENTE())
				)){
				/***CAMBIO DEL NOMBRE CON COMAS*********/
				separarNombreConComas(interviniente,adquirente.getNOMBREAPELLIDOSREPRESENTANTEADQUIRIENTE().replace(" ",","));
			}

		}

		if (TipoInterviniente.ConductorHabitual.getValorEnum().equals(tipoInterviniente)) {
			trafico.beans.jaxb.transmision.DATOSADQUIRIENTE adquirente = trans.getDATOSADQUIRIENTE();

			interviniente.setP_ID_USUARIO(idUsuario);
			//interviniente.setP_NUM_EXPEDIENTE(); // Se setea en el modelo tras guardar el trámite
			interviniente.setP_ID_CONTRATO_SESSION(idContrato);

			//INFORMACION DEL INTERVINIENTE
			interviniente.setP_TIPO_INTERVINIENTE(TipoInterviniente.ConductorHabitual.getValorEnum());
			interviniente.setP_FECHA_INICIO(DDMMAAAAToTimestamp(adquirente.getFECHAINICIOCONDUCTORHABITUAL()));
			interviniente.setP_FECHA_FIN(DDMMAAAAToTimestamp(adquirente.getFECHAFINCONDUCTORHABITUAL()));
			//Cambios urgentes de Yerbabuena
			interviniente.setP_TIPO_DOCUMENTO_ALTERNATIVO(adquirente.getTIPODOCUMENTOSUSTITUTIVOCONDUCTORHABITUAL());
			interviniente.setP_FECHA_CADUCIDAD_NIF(DDMMAAAAToTimestamp(adquirente.getFECHACADUCIDADNIFCONDUCTORHABITUAL()));
			interviniente.setP_FECHA_CADUCIDAD_ALTERNATIVO(DDMMAAAAToTimestamp(adquirente.getFECHACADUCIDADDOCUMENTOSUSTITUTIVOCONDUCTORHABITUAL()));
			interviniente.setP_INDEFINIDO(adquirente.getINDEFINIDOCONDUCTORHABITUAL() != null ? (adquirente.getINDEFINIDOCONDUCTORHABITUAL().equalsIgnoreCase(TipoSINO.SI.value()) ||adquirente.getINDEFINIDOCONDUCTORHABITUAL().equalsIgnoreCase("S")?S:N):N);
			/**/

			//INFORMACIÓN DE LA PERSONA ASOCIADA AL INVOLUCRADO
			try {
				if (new BigDecimal(ga.getCABECERA().getDATOSGESTORIA().getPROFESIONAL()) != null) {
					String valor = new BigDecimal(ga.getCABECERA().getDATOSGESTORIA().getPROFESIONAL()).toString();
					if (valor.length()==5) {
						valor = valor.substring(0, 4);
					}
					while (valor.length()<4) {
						valor = "0" + valor;
					}
					interviniente.setP_NUM_COLEGIADO(valor);
				}
			} catch (Exception e) {
				interviniente.setP_NUM_COLEGIADO(null);
			}

			interviniente.setP_NIF(adquirente.getDNICONDUCTORHABITUAL());

			interviniente.setP_APELLIDO2(adquirente.getAPELLIDO2CONDUCTORHABITUAL());
			interviniente.setP_NOMBRE(adquirente.getNOMBRECONDUCTORHABITUAL());
			interviniente.setP_APELLIDO1_RAZON_SOCIAL(adquirente.getAPELLIDO1RAZONSOCIALCONDUCTORHABITUAL());

			interviniente.setP_FECHA_NACIMIENTO(DDMMAAAAToTimestamp(adquirente.getFECHANACIMIENTOCONDUCTORHABITUAL()));
		}

		// DRC@02-10-2012 Incidencia: 1801
		if (TipoInterviniente.Arrendatario.getValorEnum().equals(tipoInterviniente)) {
			trafico.beans.jaxb.transmision.DATOSARRENDADOR arrendador = trans.getDATOSARRENDADOR();
			if (arrendador == null)
				return interviniente;

			interviniente.setP_ID_USUARIO(idUsuario);
			//interviniente.setP_NUM_EXPEDIENTE(); // Se setea en el modelo tras guardar el trámite
			interviniente.setP_ID_CONTRATO_SESSION(idContrato);

			//INFORMACIÓN DEL INTERVINIENTE
			interviniente.setP_TIPO_INTERVINIENTE(TipoInterviniente.Arrendatario.getValorEnum());
			interviniente.setP_CAMBIO_DOMICILIO(null!=arrendador.getACTUALIZACIONDOMICILIOARRENDADOR() ? arrendador.getACTUALIZACIONDOMICILIOARRENDADOR().toUpperCase() : null);
			interviniente.setP_AUTONOMO(null!=arrendador.getAUTONOMOARRENDADOR()?arrendador.getAUTONOMOARRENDADOR().toUpperCase():null);
			interviniente.setP_CODIGO_IAE(null!=arrendador.getCODIGOIAEARRENDADOR()?arrendador.getCODIGOIAEARRENDADOR().toUpperCase():null);
			// INFORMACIÓN DE LA PERSONA ASOCIADA AL INVOLUCRADO
			try {
				if (new BigDecimal(ga.getCABECERA().getDATOSGESTORIA().getPROFESIONAL()) != null) {
					String valor = new BigDecimal(ga.getCABECERA().getDATOSGESTORIA().getPROFESIONAL()).toString();
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

			interviniente.setP_NIF(arrendador.getDNIARRENDADOR());
			interviniente.setP_APELLIDO2(arrendador.getAPELLIDO2ARRENDADOR());
			interviniente.setP_NOMBRE(arrendador.getNOMBREARRENDADOR());
			interviniente.setP_APELLIDO1_RAZON_SOCIAL(arrendador.getAPELLIDO1RAZONSOCIALARRENDADOR());

			if (utilesConversiones.isNifNie(arrendador.getDNIARRENDADOR())) {
				if (!"".equals(arrendador.getNOMBREAPELLIDOSARRENDADOR())
						&& (("".equals(arrendador.getNOMBREARRENDADOR()) || null == arrendador.getNOMBREARRENDADOR())
								&& ("".equals(arrendador.getAPELLIDO1RAZONSOCIALARRENDADOR())
										|| null == arrendador.getNOMBREARRENDADOR()))) {
					/*** CAMBIO DEL NOMBRE CON COMAS *********/
					separarNombreConComas(interviniente, arrendador.getNOMBREAPELLIDOSARRENDADOR().replace(" ", ","));
				}
			} else {
				interviniente.setP_APELLIDO1_RAZON_SOCIAL(arrendador.getNOMBREAPELLIDOSARRENDADOR());
				interviniente.setP_APELLIDO2(null);
				interviniente.setP_NOMBRE(null);
			}

			// 0004759. SCL. La fecha de inicio del renting, si no está marcada, es igual a la fecha de presentación
			if (trans.getRENTING() != null && trans.getRENTING().equals(TipoSINO.SI.value())
					&& interviniente.getP_FECHA_INICIO() == null) {
				interviniente.setP_FECHA_INICIO(trans != null && trans.getDATOSPRESENTACION() != null
						&& trans.getDATOSPRESENTACION().getFECHAPRESENTACION() != null
								? DDMMAAAAToTimestamp(trans.getDATOSPRESENTACION().getFECHAPRESENTACION())
								: null);
			} else {
				interviniente.setP_FECHA_INICIO(null);
			}

			if (interviniente.getP_NIF()!=null && interviniente.getP_APELLIDO1_RAZON_SOCIAL() != null
					&& !"".equals(interviniente.getP_NIF()) && !"".equals(interviniente.getP_APELLIDO1_RAZON_SOCIAL())
					&& utilesConversiones.isNifNie(interviniente.getP_NIF())) {
				interviniente.setP_ANAGRAMA(Anagrama.obtenerAnagramaFiscal(interviniente.getP_APELLIDO1_RAZON_SOCIAL(), interviniente.getP_NIF()));
			}
			interviniente.setP_FECHA_NACIMIENTO(DDMMAAAAToTimestamp(arrendador.getFECHANACIMIENTOARRENDADOR()));
			//Cambios urgentes de Yerbabuena
			interviniente.setP_TIPO_DOCUMENTO_ALTERNATIVO(arrendador.getTIPODOCUMENTOSUSTITUTIVOARRENDADOR());
			interviniente.setP_FECHA_CADUCIDAD_NIF(DDMMAAAAToTimestamp(arrendador.getFECHACADUCIDADNIFARRENDADOR()));
			interviniente.setP_FECHA_CADUCIDAD_ALTERNATIVO(DDMMAAAAToTimestamp(arrendador.getFECHACADUCIDADDOCUMENTOSUSTITUTIVOARRENDADOR()));
			interviniente.setP_INDEFINIDO(arrendador.getINDEFINIDOARRENDADOR() != null ? (arrendador.getINDEFINIDOARRENDADOR().equalsIgnoreCase(TipoSINO.SI.value()) ||arrendador.getINDEFINIDOARRENDADOR().equalsIgnoreCase("S") ? S : N) : N);
			/**/
			interviniente.setP_TELEFONOS(arrendador.getTELEFONOARRENDADOR());
			interviniente.setP_SEXO(arrendador.getSEXOARRENDADOR());
			interviniente.setP_ID_PROVINCIA(utilesConversiones.getIdProvinciaFromSiglas(arrendador.getPROVINCIAARRENDADOR()));
			interviniente.setP_NUMERO(arrendador.getNUMERODIRECCIONARRENDADOR());
			interviniente.setP_COD_POSTAL(arrendador.getCPARRENDADOR());
			interviniente.setP_LETRA(arrendador.getLETRADIRECCIONARRENDADOR());
			interviniente.setP_ESCALERA(arrendador.getESCALERADIRECCIONARRENDADOR());
			interviniente.setP_PLANTA(arrendador.getPISODIRECCIONARRENDADOR());
			interviniente.setP_PUERTA(arrendador.getPUERTADIRECCIONARRENDADOR());

			// Nuevo XSD
			interviniente.setP_BLOQUE(arrendador.getBLOQUEDIRECCIONARRENDADOR());

			BigDecimal kmAux = null != arrendador.getKMDIRECCIONARRENDADOR()
					&& !"".equals(arrendador.getKMDIRECCIONARRENDADOR())?
					new BigDecimal(arrendador.getKMDIRECCIONARRENDADOR()) : null;
			interviniente.setP_KM(kmAux);

			BigDecimal hmAux = null != arrendador.getHMDIRECCIONARRENDADOR()
					&& !"".equals(arrendador.getHMDIRECCIONARRENDADOR())?
					new BigDecimal(arrendador.getHMDIRECCIONARRENDADOR()) : null;
			interviniente.setP_HM(hmAux);

			// Mantis 17701
			if (arrendador.getDIRECCIONACTIVAARRENDADOR() !=null && !arrendador.getDIRECCIONACTIVAARRENDADOR().isEmpty()
					&& (("SI").equalsIgnoreCase(arrendador.getDIRECCIONACTIVAARRENDADOR().trim())
					|| (TipoSN.S.value()).equals(arrendador.getDIRECCIONACTIVAARRENDADOR().trim().toUpperCase()))) {
				interviniente.setP_DIRECCION_ACTIVA(TipoSN.S.value());
			} else if (arrendador.getDIRECCIONACTIVAARRENDADOR() !=null && !arrendador.getDIRECCIONACTIVAARRENDADOR().isEmpty()
					&& (("NO").equalsIgnoreCase(arrendador.getDIRECCIONACTIVAARRENDADOR().trim())
					|| (TipoSN.N.value()).equals(arrendador.getDIRECCIONACTIVAARRENDADOR().trim().toUpperCase()))) {
				interviniente.setP_DIRECCION_ACTIVA(TipoSN.N.value());
			}

			//INFORMACIÓN PARA LAS IMPORTACIONES
			String valor = arrendador.getSIGLASDIRECCIONARRENDADOR();
			if (valor == null)
				valor = "";
			if ("6".equals(valor.trim()))
				valor = "41"; // calle
			if ("44".equals(valor.trim()))
				valor = "5";
			if ("24".equals(valor.trim()) || "34".equals(valor.trim()))
				valor = "29";

			interviniente.setP_ID_TIPO_DGT(valor.trim());
			interviniente.setP_MUNICIPIO(arrendador.getMUNICIPIOARRENDADOR());
			interviniente.setP_PUEBLO_LIT(arrendador.getPUEBLODIRECCIONARRENDADOR());
			interviniente.setP_VIA(arrendador.getNOMBREVIADIRECCIONARRENDADOR());
		}

		if (TipoInterviniente.RepresentanteArrendatario.getValorEnum().equals(tipoInterviniente)) {
			trafico.beans.jaxb.transmision.DATOSARRENDADOR repreArrendador = trans.getDATOSARRENDADOR();

			interviniente.setP_ID_USUARIO(idUsuario);
			//interviniente.setP_NUM_EXPEDIENTE(); // Se setea en el modelo tras guardar el trámite
			interviniente.setP_ID_CONTRATO_SESSION(idContrato);

			//INFORMACIÓN DEL INTERVINIENTE
			interviniente.setP_TIPO_INTERVINIENTE(TipoInterviniente.RepresentanteArrendatario.getValorEnum());
			interviniente.setP_FECHA_INICIO(DDMMAAAAToTimestamp(repreArrendador.getFECHAINICIOTUTELAARRENDADOR()));
			interviniente.setP_FECHA_FIN(DDMMAAAAToTimestamp(repreArrendador.getFECHAFINTUTELAARRENDADOR()));
			// Cambios urgentes de Yerbabuena
			interviniente.setP_TIPO_DOCUMENTO_ALTERNATIVO(repreArrendador.getTIPODOCUMENTOSUSTITUTIVOREPRESENTANTEARRENDADOR());
			interviniente.setP_FECHA_CADUCIDAD_NIF(DDMMAAAAToTimestamp(repreArrendador.getFECHACADUCIDADNIFREPRESENTANTEARRENDADOR()));
			interviniente.setP_FECHA_CADUCIDAD_ALTERNATIVO(DDMMAAAAToTimestamp(repreArrendador.getFECHACADUCIDADDOCUMENTOSUSTITUTIVOREPRESENTANTEARRENDADOR()));
			interviniente.setP_INDEFINIDO(repreArrendador.getINDEFINIDOREPRESENTANTEARRENDADOR()!=null?(repreArrendador.getINDEFINIDOREPRESENTANTEARRENDADOR().equalsIgnoreCase(TipoSINO.SI.value()) ||repreArrendador.getINDEFINIDOREPRESENTANTEARRENDADOR().equalsIgnoreCase("S")?S:N):N);
			/**/
			interviniente.setP_CONCEPTO_REPRE(repreArrendador.getCONCEPTOREPRESENTANTEARRENDADOR());
			interviniente.setP_ID_MOTIVO_TUTELA(repreArrendador.getMOTIVOREPRESENTANTEARRENDADOR());
			interviniente.setP_DATOS_DOCUMENTO(repreArrendador.getDOCUMENTOSREPRESENTANTEARRENDADOR());

			//INFORMACIÓN DE LA PERSONA ASOCIADA AL INVOLUCRADO
			try {
				if (new BigDecimal(ga.getCABECERA().getDATOSGESTORIA().getPROFESIONAL()) != null) {
					String valor = new BigDecimal(ga.getCABECERA().getDATOSGESTORIA().getPROFESIONAL()).toString();
					if (valor.length() == 5) {
						valor = valor.substring(0, 4);
					}
					while (valor.length() < 4) {
						valor = "0" + valor;
					}
					interviniente.setP_NUM_COLEGIADO(valor);
				}
			} catch (Exception e){
				interviniente.setP_NUM_COLEGIADO(null);
			}

			interviniente.setP_NIF(repreArrendador.getDNIREPRESENTANTEARRENDADOR());

			if (!"".equals(repreArrendador.getNOMBREAPELLIDOSREPRESENTANTEARRENDADOR()) && (
					("".equals(repreArrendador.getNOMBREREPRESENTANTEARRENDADOR())|| null==repreArrendador.getNOMBREREPRESENTANTEARRENDADOR()) &&
					("".equals(repreArrendador.getAPELLIDO1RAZONSOCIALREPRESENTANTEARRENDADOR())||null==repreArrendador.getAPELLIDO1RAZONSOCIALREPRESENTANTEARRENDADOR()))) {
				/***CAMBIO DEL NOMBRE CON COMAS*********/
				separarNombreConComas(interviniente,repreArrendador.getNOMBREAPELLIDOSREPRESENTANTEARRENDADOR().replace(" ", ","));
			} else {
				interviniente.setP_NOMBRE(repreArrendador.getNOMBREREPRESENTANTEARRENDADOR());
				interviniente.setP_APELLIDO1_RAZON_SOCIAL(repreArrendador.getAPELLIDO1RAZONSOCIALREPRESENTANTEARRENDADOR());
				// INCIDENCIA 1721: SIGA: Error en el representante del compraventa 
				interviniente.setP_APELLIDO2(repreArrendador.getAPELLIDO2REPRESENTANTEARRENDADOR());
			}
		}

		// DRC@02-10-2012 Incidencia: 1801
		if (TipoInterviniente.Compraventa.getValorEnum().equals(tipoInterviniente)) {
			trafico.beans.jaxb.transmision.DATOSCOMPRAVENTA compraventa = trans.getDATOSCOMPRAVENTA();
			if (compraventa == null)
				return interviniente;

			interviniente.setP_ID_USUARIO(idUsuario);
			//interviniente.setP_NUM_EXPEDIENTE(); //se setea en el modelo tras guardar el trámite
			interviniente.setP_ID_CONTRATO_SESSION(idContrato);

			//INFORMACIÓN DEL INTERVINIENTE
			interviniente.setP_TIPO_INTERVINIENTE(TipoInterviniente.Compraventa.getValorEnum());
			interviniente.setP_CAMBIO_DOMICILIO(null!=compraventa.getACTUALIZACIONDOMICILIOCOMPRAVENTA()?compraventa.getACTUALIZACIONDOMICILIOCOMPRAVENTA().toUpperCase():null);
			interviniente.setP_AUTONOMO(null != compraventa.getAUTONOMOCOMPRAVENTA() ? compraventa.getAUTONOMOCOMPRAVENTA().toUpperCase() : null);
			interviniente.setP_CODIGO_IAE(null != compraventa.getCODIGOIAECOMPRAVENTA() ? compraventa.getCODIGOIAECOMPRAVENTA().toUpperCase() : null);

			//INFORMACIÓN DE LA PERSONA ASOCIADA AL INVOLUCRADO
			try {
				if (new BigDecimal(ga.getCABECERA().getDATOSGESTORIA().getPROFESIONAL())!=null) {
					String valor = new BigDecimal(ga.getCABECERA().getDATOSGESTORIA().getPROFESIONAL()).toString();
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

			interviniente.setP_NIF(compraventa.getDNICOMPRAVENTA());
			interviniente.setP_APELLIDO2(compraventa.getAPELLIDO2COMPRAVENTA());
			interviniente.setP_NOMBRE(compraventa.getNOMBRECOMPRAVENTA());
			interviniente.setP_APELLIDO1_RAZON_SOCIAL(compraventa.getAPELLIDO1RAZONSOCIALCOMPRAVENTA());

			if (!compraventa.getSEXOCOMPRAVENTA().equals("X")) {
				if (!"".equals(compraventa.getNOMBREAPELLIDOSCOMPRAVENTA()) && (
						("".equals(compraventa.getNOMBRECOMPRAVENTA())|| null == compraventa.getNOMBRECOMPRAVENTA()) &&
						("".equals(compraventa.getAPELLIDO1RAZONSOCIALCOMPRAVENTA()) || null == compraventa.getNOMBRECOMPRAVENTA())
						)) {
					/***CAMBIO DEL NOMBRE CON COMAS*********/
					separarNombreConComas(interviniente,compraventa.getNOMBREAPELLIDOSCOMPRAVENTA().replace(" ", ","));
				}
			} else {
				interviniente.setP_APELLIDO1_RAZON_SOCIAL(compraventa.getNOMBREAPELLIDOSCOMPRAVENTA());
				interviniente.setP_APELLIDO2(null);
				interviniente.setP_NOMBRE(null);
			}

			if (interviniente.getP_NIF() != null && interviniente.getP_APELLIDO1_RAZON_SOCIAL() != null
					&& !"".equals(interviniente.getP_NIF()) && !"".equals(interviniente.getP_APELLIDO1_RAZON_SOCIAL())
					&& utilesConversiones.isNifNie(interviniente.getP_NIF())) {
				interviniente.setP_ANAGRAMA(Anagrama.obtenerAnagramaFiscal(interviniente.getP_APELLIDO1_RAZON_SOCIAL(), interviniente.getP_NIF()));
			}
			interviniente.setP_FECHA_NACIMIENTO(DDMMAAAAToTimestamp(compraventa.getFECHANACIMIENTOCOMPRAVENTA()));
			//Cambios urgentes de Yerbabuena
			interviniente.setP_TIPO_DOCUMENTO_ALTERNATIVO(compraventa.getTIPODOCUMENTOSUSTITUTIVOCOMPRAVENTA());
			interviniente.setP_FECHA_CADUCIDAD_NIF(DDMMAAAAToTimestamp(compraventa.getFECHACADUCIDADNIFCOMPRAVENTA()));
			interviniente.setP_FECHA_CADUCIDAD_ALTERNATIVO(DDMMAAAAToTimestamp(compraventa.getFECHACADUCIDADDOCUMENTOSUSTITUTIVOCOMPRAVENTA()));
			interviniente.setP_INDEFINIDO(compraventa.getINDEFINIDOCOMPRAVENTA()!=null?(compraventa.getINDEFINIDOCOMPRAVENTA().equalsIgnoreCase(TipoSINO.SI.value()) ||compraventa.getINDEFINIDOCOMPRAVENTA().equalsIgnoreCase("S")?S:N):N);
			/**/
			interviniente.setP_TELEFONOS(compraventa.getTELEFONOCOMPRAVENTA());
			interviniente.setP_SEXO(compraventa.getSEXOCOMPRAVENTA());
			interviniente.setP_ID_PROVINCIA(utilesConversiones.getIdProvinciaFromSiglas(compraventa.getPROVINCIACOMPRAVENTA()));
			interviniente.setP_NUMERO(compraventa.getNUMERODIRECCIONCOMPRAVENTA());
			interviniente.setP_COD_POSTAL(compraventa.getCPCOMPRAVENTA());
			interviniente.setP_LETRA(compraventa.getLETRADIRECCIONCOMPRAVENTA());
			interviniente.setP_ESCALERA(compraventa.getESCALERADIRECCIONCOMPRAVENTA());
			interviniente.setP_PLANTA(compraventa.getPISODIRECCIONCOMPRAVENTA());
			interviniente.setP_PUERTA(compraventa.getPUERTADIRECCIONCOMPRAVENTA());

			// Nuevo XSD
			interviniente.setP_BLOQUE(compraventa.getBLOQUEDIRECCIONCOMPRAVENTA());

			BigDecimal kmAux = null != compraventa.getKMDIRECCIONCOMPRAVENTA()
			&& !"".equals(compraventa.getKMDIRECCIONCOMPRAVENTA()) ?
					new BigDecimal(compraventa.getKMDIRECCIONCOMPRAVENTA()) : null;
			interviniente.setP_KM(kmAux);

			BigDecimal hmAux = null != compraventa.getHMDIRECCIONCOMPRAVENTA()
			&& !"".equals(compraventa.getHMDIRECCIONCOMPRAVENTA())?
					new BigDecimal(compraventa.getHMDIRECCIONCOMPRAVENTA()) : null;
			interviniente.setP_HM(hmAux);

			// Mantis 17701
			if (compraventa.getDIRECCIONACTIVACOMPRAVENTA() !=null && !compraventa.getDIRECCIONACTIVACOMPRAVENTA().isEmpty()
					&& (("SI").equalsIgnoreCase(compraventa.getDIRECCIONACTIVACOMPRAVENTA().trim())
					|| (TipoSN.S.value()).equals(compraventa.getDIRECCIONACTIVACOMPRAVENTA().trim().toUpperCase()))) {
				interviniente.setP_DIRECCION_ACTIVA(TipoSN.S.value());
			} else if (compraventa.getDIRECCIONACTIVACOMPRAVENTA() !=null && !compraventa.getDIRECCIONACTIVACOMPRAVENTA().isEmpty()
					&& (("NO").equalsIgnoreCase(compraventa.getDIRECCIONACTIVACOMPRAVENTA().trim())
					|| (TipoSN.N.value()).equals(compraventa.getDIRECCIONACTIVACOMPRAVENTA().trim().toUpperCase()))) {
				interviniente.setP_DIRECCION_ACTIVA(TipoSN.N.value());
			}

			//INFORMACIÓN PARA LAS IMPORTACIONES
			String valor = compraventa.getSIGLASDIRECCIONCOMPRAVENTA();
			if (valor == null) valor = "";
			if ("6".equals(valor.trim())) valor = "41"; // calle
			if ("44".equals(valor.trim())) valor = "5";
			if ("24".equals(valor.trim()) || "34".equals(valor.trim())) valor = "29";

			interviniente.setP_ID_TIPO_DGT(valor.trim());
			interviniente.setP_MUNICIPIO(compraventa.getMUNICIPIOCOMPRAVENTA());
			interviniente.setP_PUEBLO_LIT(compraventa.getPUEBLODIRECCIONCOMPRAVENTA());
			interviniente.setP_VIA(compraventa.getNOMBREVIADIRECCIONCOMPRAVENTA());
		}

		if (TipoInterviniente.RepresentanteCompraventa.getValorEnum().equals(tipoInterviniente)) {
			trafico.beans.jaxb.transmision.DATOSCOMPRAVENTA repreCompraventa = trans.getDATOSCOMPRAVENTA();

			interviniente.setP_ID_USUARIO(idUsuario);
			//interviniente.setP_NUM_EXPEDIENTE(); // Se setea en el modelo tras guardar el trámite
			interviniente.setP_ID_CONTRATO_SESSION(idContrato);

			//INFORMACIÓN DEL INTERVINIENTE
			interviniente.setP_TIPO_INTERVINIENTE(TipoInterviniente.RepresentanteCompraventa.getValorEnum());
			interviniente.setP_FECHA_INICIO(DDMMAAAAToTimestamp(repreCompraventa.getFECHAINICIOTUTELACOMPRAVENTA()));
			interviniente.setP_FECHA_FIN(DDMMAAAAToTimestamp(repreCompraventa.getFECHAFINTUTELACOMPRAVENTA()));
			interviniente.setP_CONCEPTO_REPRE(repreCompraventa.getCONCEPTOREPRESENTANTECOMPRAVENTA());
			interviniente.setP_ID_MOTIVO_TUTELA(repreCompraventa.getMOTIVOREPRESENTANTECOMPRAVENTA());
			interviniente.setP_DATOS_DOCUMENTO(repreCompraventa.getDOCUMENTOSREPRESENTANTECOMPRAVENTA());

			//INFORMACIÓN DE LA PERSONA ASOCIADA AL INVOLUCRADO
			try {
				if (new BigDecimal(ga.getCABECERA().getDATOSGESTORIA().getPROFESIONAL()) != null) {
					String valor = new BigDecimal(ga.getCABECERA().getDATOSGESTORIA().getPROFESIONAL()).toString();
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

			interviniente.setP_NIF(repreCompraventa.getDNIREPRESENTANTECOMPRAVENTA());

			//inicio incidencia M-5624
			interviniente.setP_TIPO_DOCUMENTO_ALTERNATIVO(repreCompraventa.getTIPODOCUMENTOSUSTITUTIVOREPRESENTANTECOMPRAVENTA());
			interviniente.setP_FECHA_CADUCIDAD_NIF(DDMMAAAAToTimestamp(repreCompraventa.getFECHACADUCIDADNIFREPRESENTANTECOMPRAVENTA()));
			interviniente.setP_FECHA_CADUCIDAD_ALTERNATIVO(DDMMAAAAToTimestamp(repreCompraventa.getFECHACADUCIDADDOCUMENTOSUSTITUTIVOREPRESENTANTECOMPRAVENTA()));
			interviniente.setP_INDEFINIDO(repreCompraventa.getINDEFINIDOREPRESENTANTECOMPRAVENTA()!=null?(repreCompraventa.getINDEFINIDOREPRESENTANTECOMPRAVENTA().equalsIgnoreCase(TipoSINO.SI.value()) ||repreCompraventa.getINDEFINIDOREPRESENTANTECOMPRAVENTA().equalsIgnoreCase("S")?S:N):N);
			// fin incidencia M-5624

			if (!"".equals(repreCompraventa.getNOMBREAPELLIDOSREPRESENTANTECOMPRAVENTA()) && (
					("".equals(repreCompraventa.getNOMBREREPRESENTANTECOMPRAVENTA())|| null==repreCompraventa.getNOMBREREPRESENTANTECOMPRAVENTA()) &&
					("".equals(repreCompraventa.getAPELLIDO1RAZONSOCIALREPRESENTANTECOMPRAVENTA())||null==repreCompraventa.getAPELLIDO1RAZONSOCIALREPRESENTANTECOMPRAVENTA())
			)){
				/***CAMBIO DEL NOMBRE CON COMAS*********/
				separarNombreConComas(interviniente,repreCompraventa.getNOMBREAPELLIDOSREPRESENTANTECOMPRAVENTA().replace(" ", ","));
			} else {
				interviniente.setP_NOMBRE(repreCompraventa.getNOMBREREPRESENTANTECOMPRAVENTA());
				interviniente.setP_APELLIDO1_RAZON_SOCIAL(repreCompraventa.getAPELLIDO1RAZONSOCIALREPRESENTANTECOMPRAVENTA());
				// INCIDENCIA 1721: SIGA: Error en el representante del compraventa 
				interviniente.setP_APELLIDO2(repreCompraventa.getAPELLIDO2REPRESENTANTECOMPRAVENTA());
			}
		}

		if (TipoInterviniente.TransmitenteTrafico.getValorEnum().equals(tipoInterviniente)) {
			trafico.beans.jaxb.transmision.DATOSTRANSMITENTE transmitente = trans.getDATOSTRANSMITENTE();

			interviniente.setP_ID_USUARIO(idUsuario);
			//interviniente.setP_NUM_EXPEDIENTE(); // Se setea en el modelo tras guardar el trámite
			interviniente.setP_ID_CONTRATO_SESSION(idContrato);

			//INFORMACIÓN DEL INTERVINIENTE
			interviniente.setP_TIPO_INTERVINIENTE(TipoInterviniente.TransmitenteTrafico.getValorEnum());

			interviniente.setP_AUTONOMO(null!=transmitente.getAUTONOMOTRANSMITENTE()?
					transmitente.getAUTONOMOTRANSMITENTE().toUpperCase():null);
			interviniente.setP_CODIGO_IAE(null!=transmitente.getCODIGOIAETRANSMITENTE()?
					transmitente.getCODIGOIAETRANSMITENTE().toUpperCase():null);

			//INFORMACIÓN DE LA PERSONA ASOCIADA AL INVOLUCRADO
			try {
				if (new BigDecimal(ga.getCABECERA().getDATOSGESTORIA().getPROFESIONAL())!=null) {
					String valor = new BigDecimal(ga.getCABECERA().getDATOSGESTORIA().getPROFESIONAL()).toString();
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

			interviniente.setP_NIF(transmitente.getDNITRANSMITENTE());
			interviniente.setP_APELLIDO1_RAZON_SOCIAL(transmitente.getAPELLIDO1RAZONSOCIALTRANSMITENTE());
			interviniente.setP_APELLIDO2(transmitente.getAPELLIDO2TRANSMITENTE());
			interviniente.setP_NOMBRE(transmitente.getNOMBRETRANSMITENTE());
			if (interviniente.getP_NIF()!=null && interviniente.getP_APELLIDO1_RAZON_SOCIAL() != null
					&& !"".equals(interviniente.getP_NIF()) && !"".equals(interviniente.getP_APELLIDO1_RAZON_SOCIAL())
					&& utilesConversiones.isNifNie(interviniente.getP_NIF())) {
				interviniente.setP_ANAGRAMA(Anagrama.obtenerAnagramaFiscal(interviniente.getP_APELLIDO1_RAZON_SOCIAL(), interviniente.getP_NIF()));
			}
			interviniente.setP_FECHA_NACIMIENTO(DDMMAAAAToTimestamp(transmitente.getFECHANACIMIENTOTRANSMITENTE()));
			// Cambios urgentes de Yerbabuena
			interviniente.setP_TIPO_DOCUMENTO_ALTERNATIVO(transmitente.getTIPODOCUMENTOSUSTITUTIVOTRANSMITENTE());
			interviniente.setP_FECHA_CADUCIDAD_NIF(DDMMAAAAToTimestamp(transmitente.getFECHACADUCIDADNIFTRANSMITENTE()));
			interviniente.setP_FECHA_CADUCIDAD_ALTERNATIVO(DDMMAAAAToTimestamp(transmitente.getFECHACADUCIDADDOCUMENTOSUSTITUTIVOTRANSMITENTE()));
			interviniente.setP_INDEFINIDO(transmitente.getINDEFINIDOTRANSMITENTE() != null ? (transmitente.getINDEFINIDOTRANSMITENTE().equalsIgnoreCase(TipoSINO.SI.value()) ||transmitente.getINDEFINIDOTRANSMITENTE().equalsIgnoreCase("S")?S:N):N);
			/**/
			interviniente.setP_TELEFONOS(transmitente.getTELEFONOTRANSMITENTE());
			interviniente.setP_SEXO(transmitente.getSEXOTRANSMITENTE());

			//INFORMACIÓN DE LA DIRECCIÓN
			interviniente.setP_ID_PROVINCIA(utilesConversiones.getIdProvinciaFromSiglas(transmitente.getPROVINCIATRANSMITENTE()));
			interviniente.setP_NUMERO(transmitente.getNUMERODIRECCIONTRANSMITENTE());
			interviniente.setP_COD_POSTAL(transmitente.getCPTRANSMITENTE());
			interviniente.setP_LETRA(transmitente.getLETRADIRECCIONTRANSMITENTE());
			interviniente.setP_ESCALERA(transmitente.getESCALERADIRECCIONTRANSMITENTE());
			interviniente.setP_PLANTA(transmitente.getPISODIRECCIONTRANSMITENTE());
			interviniente.setP_PUERTA(transmitente.getPUERTADIRECCIONTRANSMITENTE());

			// Nuevo XSD
			interviniente.setP_BLOQUE(transmitente.getBLOQUEDIRECCIONTRANSMITENTE());

			BigDecimal kmAux = null != transmitente.getKMDIRECCIONTRANSMITENTE()
					&& !"".equals(transmitente.getKMDIRECCIONTRANSMITENTE()) ?
					new BigDecimal(transmitente.getKMDIRECCIONTRANSMITENTE()) : null;
			interviniente.setP_KM(kmAux);

			BigDecimal hmAux = null != transmitente.getHMDIRECCIONTRANSMITENTE()
			&& !"".equals(transmitente.getHMDIRECCIONTRANSMITENTE())?
					new BigDecimal(transmitente.getHMDIRECCIONTRANSMITENTE()) : null;
			interviniente.setP_HM(hmAux);

			// Mantis 17701
			if (transmitente.getDIRECCIONACTIVATRANSMITENTE() !=null && !transmitente.getDIRECCIONACTIVATRANSMITENTE().isEmpty()
					&& (("SI").equalsIgnoreCase(transmitente.getDIRECCIONACTIVATRANSMITENTE().trim())
					|| (TipoSN.S.value()).equals(transmitente.getDIRECCIONACTIVATRANSMITENTE().trim().toUpperCase()))) {
				interviniente.setP_DIRECCION_ACTIVA(TipoSN.S.value());
			} else if (transmitente.getDIRECCIONACTIVATRANSMITENTE() !=null && !transmitente.getDIRECCIONACTIVATRANSMITENTE().isEmpty()
					&& (("NO").equalsIgnoreCase(transmitente.getDIRECCIONACTIVATRANSMITENTE().trim()) 
					|| (TipoSN.N.value()).equals(transmitente.getDIRECCIONACTIVATRANSMITENTE().trim().toUpperCase()))) {
				interviniente.setP_DIRECCION_ACTIVA(TipoSN.N.value());
			}

			// INFORMACIÓN PARA LAS IMPORTACIONES
			String valor = transmitente.getSIGLASDIRECCIONTRANSMITENTE();
			if (valor == null)
				valor = "";
			if ("6".equals(valor.trim()))
				valor = "41"; // calle
			else if ("44".equals(valor.trim()))
				valor = "5";
			else if ("24".equals(valor.trim()) || "34".equals(valor.trim()))
				valor = "29";

			interviniente.setP_ID_TIPO_DGT(valor.trim());
			interviniente.setP_MUNICIPIO(transmitente.getMUNICIPIOTRANSMITENTE());
			interviniente.setP_PUEBLO_LIT(transmitente.getPUEBLOTRANSMITENTE());
			interviniente.setP_VIA(transmitente.getNOMBREVIADIRECCIONTRANSMITENTE());

			try {
				interviniente.setP_NUM_TITULARES(new BigDecimal(transmitente.getNUMEROTITULARES()));
			} catch (Exception e) {
				interviniente.setP_NUM_TITULARES(null);
			}
		}

		if (TipoInterviniente.CotitularTransmision.getValorEnum().equals(tipoInterviniente)) {
			trafico.beans.jaxb.transmision.DATOSTRANSMITENTE transmitente = trans.getDATOSTRANSMITENTE();

			interviniente.setP_ID_USUARIO(idUsuario);
			//interviniente.setP_NUM_EXPEDIENTE(); //se setea en el modelo tras guardar el trámite
			interviniente.setP_ID_CONTRATO_SESSION(idContrato);

			//INFORMACIÓN DEL INTERVINIENTE
			interviniente.setP_TIPO_INTERVINIENTE(TipoInterviniente.CotitularTransmision.getValorEnum());

			//INFORMACIÓN DE LA PERSONA ASOCIADA AL INVOLUCRADO
			try {
				if (new BigDecimal(ga.getCABECERA().getDATOSGESTORIA().getPROFESIONAL())!=null) {
					String valor = new BigDecimal(ga.getCABECERA().getDATOSGESTORIA().getPROFESIONAL()).toString();
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

			interviniente.setP_NIF(transmitente.getDNIPRIMERCOTITULARTRANSMITENTE());
			interviniente.setP_APELLIDO1_RAZON_SOCIAL(transmitente.getAPELLIDO1RAZONSOCIALPRIMERCOTITULARTRANSMITENTE());
			interviniente.setP_APELLIDO2(transmitente.getAPELLIDO2PRIMERCOTITULARTRANSMITENTE());
			interviniente.setP_NOMBRE(transmitente.getNOMBREPRIMERCOTITULARTRANSMITENTE());
			if (interviniente.getP_NIF()!=null && interviniente.getP_APELLIDO1_RAZON_SOCIAL() != null
					&& !"".equals(interviniente.getP_NIF()) && !"".equals(interviniente.getP_APELLIDO1_RAZON_SOCIAL())
					&& utilesConversiones.isNifNie(interviniente.getP_NIF())) {
				interviniente.setP_ANAGRAMA(Anagrama.obtenerAnagramaFiscal(interviniente.getP_APELLIDO1_RAZON_SOCIAL(), interviniente.getP_NIF()));
			}
			interviniente.setP_FECHA_NACIMIENTO(DDMMAAAAToTimestamp(transmitente.getFECHANACIMIENTOPRIMERCOTITULARTRANSMITENTE()));
			//Cambios urgentes de Yerbabuena
			interviniente.setP_TIPO_DOCUMENTO_ALTERNATIVO(transmitente.getTIPODOCUMENTOSUSTITUTIVOPRIMERCOTITULARTRANSMITENTE());
			interviniente.setP_FECHA_CADUCIDAD_NIF(DDMMAAAAToTimestamp(transmitente.getFECHACADUCIDADNIFPRIMERCOTITULARTRANSMITENTE()));
			interviniente.setP_FECHA_CADUCIDAD_ALTERNATIVO(DDMMAAAAToTimestamp(transmitente.getFECHACADUCIDADDOCUMENTOSUSTITUTIVOPRIMERCOTITULARTRANSMITENTE()));
			interviniente.setP_INDEFINIDO(transmitente.getINDEFINIDOPRIMERCOTITULARTRANSMITENTE()!=null?(transmitente.getINDEFINIDOPRIMERCOTITULARTRANSMITENTE().equalsIgnoreCase(TipoSINO.SI.value()) ||transmitente.getINDEFINIDOPRIMERCOTITULARTRANSMITENTE().equalsIgnoreCase("S")?S:N):N);
			/**/
			interviniente.setP_TELEFONOS(transmitente.getTELEFONOPRIMERCOTITULARTRANSMITENTE());
			interviniente.setP_SEXO(transmitente.getSEXOPRIMERCOTITULARTRANSMITENTE());

			// INFORMACIÓN DE LA DIRECCIÓN
			interviniente.setP_ID_PROVINCIA(utilesConversiones.getIdProvinciaFromSiglas(transmitente.getPROVINCIAPRIMERCOTITULARTRANSMITENTE()));
			interviniente.setP_NUMERO(transmitente.getNUMERODIRECCIONPRIMERCOTITULARTRANSMITENTE());
			interviniente.setP_COD_POSTAL(transmitente.getCPPRIMERCOTITULARTRANSMITENTE());
			interviniente.setP_LETRA(transmitente.getLETRADIRECCIONPRIMERCOTITULARTRANSMITENTE());
			interviniente.setP_ESCALERA(transmitente.getESCALERADIRECCIONPRIMERCOTITULARTRANSMITENTE());
			interviniente.setP_PLANTA(transmitente.getPISODIRECCIONPRIMERCOTITULARTRANSMITENTE());
			interviniente.setP_PUERTA(transmitente.getPUERTADIRECCIONPRIMERCOTITULARTRANSMITENTE());

			// Nuevo XSD
			interviniente.setP_BLOQUE(transmitente.getBLOQUEDIRECCIONPRIMERCOTITULARTRANSMITENTE());

			BigDecimal kmAux = null != transmitente.getKMDIRECCIONPRIMERCOTITULARTRANSMITENTE()
					&& !"".equals(transmitente.getKMDIRECCIONPRIMERCOTITULARTRANSMITENTE())?
					new BigDecimal(transmitente.getKMDIRECCIONPRIMERCOTITULARTRANSMITENTE()) : null;
			interviniente.setP_KM(kmAux);

			BigDecimal hmAux = null != transmitente.getHMDIRECCIONPRIMERCOTITULARTRANSMITENTE()
					&& !"".equals(transmitente.getHMDIRECCIONPRIMERCOTITULARTRANSMITENTE())?
					new BigDecimal(transmitente.getHMDIRECCIONPRIMERCOTITULARTRANSMITENTE()) : null;
			interviniente.setP_HM(hmAux);

			// Mantis 17701
			if (transmitente.getDIRECCIONACTIVAPRIMERCOTITULARTRANSMITENTE() !=null && !transmitente.getDIRECCIONACTIVAPRIMERCOTITULARTRANSMITENTE().isEmpty()
					&& (("SI").equalsIgnoreCase(transmitente.getDIRECCIONACTIVAPRIMERCOTITULARTRANSMITENTE().trim())
					|| (TipoSN.S.value()).equals(transmitente.getDIRECCIONACTIVAPRIMERCOTITULARTRANSMITENTE().trim().toUpperCase()))) {
				interviniente.setP_DIRECCION_ACTIVA(TipoSN.S.value());
			} else if (transmitente.getDIRECCIONACTIVAPRIMERCOTITULARTRANSMITENTE() !=null && !transmitente.getDIRECCIONACTIVAPRIMERCOTITULARTRANSMITENTE().isEmpty()
					&& (("NO").equalsIgnoreCase(transmitente.getDIRECCIONACTIVAPRIMERCOTITULARTRANSMITENTE().trim())
					|| (TipoSN.N.value()).equals(transmitente.getDIRECCIONACTIVAPRIMERCOTITULARTRANSMITENTE().trim().toUpperCase()))) {
				interviniente.setP_DIRECCION_ACTIVA(TipoSN.N.value());
			}

			// INFORMACIÓN PARA LAS IMPORTACIONES
			String valor = transmitente.getSIGLASDIRECCIONPRIMERCOTITULARTRANSMITENTE();
			if (valor == null) valor = "";
			if ("6".equals(valor.trim())) valor = "41"; //calle
			if ("44".equals(valor.trim())) valor = "5";
			if ("24".equals(valor.trim()) || "34".equals(valor.trim())) valor = "29";
			interviniente.setP_ID_TIPO_DGT(valor.trim());
			interviniente.setP_MUNICIPIO(transmitente.getMUNICIPIOPRIMERCOTITULARTRANSMITENTE());
			interviniente.setP_PUEBLO_LIT(transmitente.getPUEBLOPRIMERCOTITULARTRANSMITENTE());
			interviniente.setP_VIA(transmitente.getNOMBREVIADIRECCIONPRIMERCOTITULARTRANSMITENTE());
		}

		if (("segundo" + TipoInterviniente.CotitularTransmision.getValorEnum()).equals(tipoInterviniente)) {
			trafico.beans.jaxb.transmision.DATOSTRANSMITENTE transmitente = trans.getDATOSTRANSMITENTE();

			interviniente.setP_ID_USUARIO(idUsuario);
			// interviniente.setP_NUM_EXPEDIENTE(); // Se setea en el modelo tras guardar el trámite
			interviniente.setP_ID_CONTRATO_SESSION(idContrato);

			// INFORMACION DEL INTERVINIENTE
			interviniente.setP_TIPO_INTERVINIENTE(TipoInterviniente.CotitularTransmision.getValorEnum());

			// INFORMACIÓN DE LA PERSONA ASOCIADA AL INVOLUCRADO
			try {
				if (new BigDecimal(ga.getCABECERA().getDATOSGESTORIA().getPROFESIONAL()) != null) {
					String valor = new BigDecimal(ga.getCABECERA().getDATOSGESTORIA().getPROFESIONAL()).toString();
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

			interviniente.setP_NIF(transmitente.getDNISEGUNDOCOTITULARTRANSMITENTE());
			interviniente.setP_APELLIDO1_RAZON_SOCIAL(transmitente.getAPELLIDO1RAZONSOCIALSEGUNDOCOTITULARTRANSMITENTE());
			interviniente.setP_APELLIDO2(transmitente.getAPELLIDO2SEGUNDOCOTITULARTRANSMITENTE());
			interviniente.setP_NOMBRE(transmitente.getNOMBRESEGUNDOCOTITULARTRANSMITENTE());
			if (interviniente.getP_NIF() != null && interviniente.getP_APELLIDO1_RAZON_SOCIAL() != null
					&& !"".equals(interviniente.getP_NIF()) && !"".equals(interviniente.getP_APELLIDO1_RAZON_SOCIAL())
					&& utilesConversiones.isNifNie(interviniente.getP_NIF())) {
				interviniente.setP_ANAGRAMA(Anagrama.obtenerAnagramaFiscal(interviniente.getP_APELLIDO1_RAZON_SOCIAL(),
						interviniente.getP_NIF()));
			}
			interviniente.setP_FECHA_NACIMIENTO(
					DDMMAAAAToTimestamp(transmitente.getFECHANACIMIENTOSEGUNDOCOTITULARTRANSMITENTE()));
			// Cambios urgentes de Yerbabuena
			interviniente.setP_TIPO_DOCUMENTO_ALTERNATIVO(
					transmitente.getTIPODOCUMENTOSUSTITUTIVOSEGUNDOCOTITULARTRANSMITENTE());
			interviniente.setP_FECHA_CADUCIDAD_NIF(
					DDMMAAAAToTimestamp(transmitente.getFECHACADUCIDADNIFSEGUNDOCOTITULARTRANSMITENTE()));
			interviniente.setP_FECHA_CADUCIDAD_ALTERNATIVO(DDMMAAAAToTimestamp(
					transmitente.getFECHACADUCIDADDOCUMENTOSUSTITUTIVOSEGUNDOCOTITULARTRANSMITENTE()));
			interviniente.setP_INDEFINIDO(transmitente.getINDEFINIDOSEGUNDOCOTITULARTRANSMITENTE() != null
					? (transmitente.getINDEFINIDOSEGUNDOCOTITULARTRANSMITENTE().equalsIgnoreCase(TipoSINO.SI.value())
							|| transmitente.getINDEFINIDOSEGUNDOCOTITULARTRANSMITENTE().equalsIgnoreCase("S") ? S : N)
					: N);
			/**/
			interviniente.setP_TELEFONOS(transmitente.getTELEFONOSEGUNDOCOTITULARTRANSMITENTE());
			interviniente.setP_SEXO(transmitente.getSEXOSEGUNDOCOTITULARTRANSMITENTE());

			// INFORMACIÓN DE LA DIRECCIÓN
			interviniente.setP_ID_PROVINCIA(utilesConversiones.getIdProvinciaFromSiglas(transmitente.getPROVINCIASEGUNDOCOTITULARTRANSMITENTE()));
			interviniente.setP_NUMERO(transmitente.getNUMERODIRECCIONSEGUNDOCOTITULARTRANSMITENTE());
			interviniente.setP_COD_POSTAL(transmitente.getCPSEGUNDOCOTITULARTRANSMITENTE());
			interviniente.setP_LETRA(transmitente.getLETRADIRECCIONSEGUNDOCOTITULARTRANSMITENTE());
			interviniente.setP_ESCALERA(transmitente.getESCALERADIRECCIONSEGUNDOCOTITULARTRANSMITENTE());
			interviniente.setP_PLANTA(transmitente.getPISODIRECCIONSEGUNDOCOTITULARTRANSMITENTE());
			interviniente.setP_PUERTA(transmitente.getPUERTADIRECCIONSEGUNDOCOTITULARTRANSMITENTE());

			// Nuevo XSD
			interviniente.setP_BLOQUE(transmitente.getBLOQUEDIRECCIONSEGUNDOCOTITULARTRANSMITENTE());

			BigDecimal kmAux = null != transmitente.getKMDIRECCIONSEGUNDOCOTITULARTRANSMITENTE()
				&& !"".equals(transmitente.getKMDIRECCIONSEGUNDOCOTITULARTRANSMITENTE())?
					new BigDecimal(transmitente.getKMDIRECCIONSEGUNDOCOTITULARTRANSMITENTE()) : null;
			interviniente.setP_KM(kmAux);

			BigDecimal hmAux = null != transmitente.getHMDIRECCIONSEGUNDOCOTITULARTRANSMITENTE()
				&& !"".equals(transmitente.getHMDIRECCIONSEGUNDOCOTITULARTRANSMITENTE())?
					new BigDecimal(transmitente.getHMDIRECCIONSEGUNDOCOTITULARTRANSMITENTE()) : null;
			interviniente.setP_HM(hmAux);

			// Mantis 17701
			if (transmitente.getDIRECCIONACTIVASEGUNDOCOTITULARTRANSMITENTE() != null && !transmitente.getDIRECCIONACTIVASEGUNDOCOTITULARTRANSMITENTE().isEmpty()
					&& (("SI").equalsIgnoreCase(transmitente.getDIRECCIONACTIVASEGUNDOCOTITULARTRANSMITENTE().trim())
					|| (TipoSN.S.value()).equals(transmitente.getDIRECCIONACTIVASEGUNDOCOTITULARTRANSMITENTE().trim().toUpperCase()))) {
				interviniente.setP_DIRECCION_ACTIVA(TipoSN.S.value());
			} else if (transmitente.getDIRECCIONACTIVASEGUNDOCOTITULARTRANSMITENTE() != null && !transmitente.getDIRECCIONACTIVASEGUNDOCOTITULARTRANSMITENTE().isEmpty()
					&& (("NO").equalsIgnoreCase(transmitente.getDIRECCIONACTIVASEGUNDOCOTITULARTRANSMITENTE().trim())
					|| (TipoSN.N.value()).equals(transmitente.getDIRECCIONACTIVASEGUNDOCOTITULARTRANSMITENTE().trim().toUpperCase()))) {
				interviniente.setP_DIRECCION_ACTIVA(TipoSN.N.value());
			}

			// INFORMACIÓN PARA LAS IMPORTACIONES
			String valor = transmitente.getSIGLASDIRECCIONSEGUNDOCOTITULARTRANSMITENTE();
			if (valor == null)
				valor = "";
			if ("6".equals(valor.trim()))
				valor = "41"; // calle
			else if ("44".equals(valor.trim()))
				valor = "5";
			else if ("24".equals(valor.trim()) || "34".equals(valor.trim()))
				valor = "29";

			interviniente.setP_ID_TIPO_DGT(valor.trim());
			interviniente.setP_MUNICIPIO(transmitente.getMUNICIPIOSEGUNDOCOTITULARTRANSMITENTE());
			interviniente.setP_PUEBLO_LIT(transmitente.getPUEBLOSEGUNDOCOTITULARTRANSMITENTE());
			interviniente.setP_VIA(transmitente.getNOMBREVIADIRECCIONSEGUNDOCOTITULARTRANSMITENTE());
		}

		if (TipoInterviniente.RepresentanteTransmitente.getValorEnum().equals(tipoInterviniente)) {
			trafico.beans.jaxb.transmision.DATOSTRANSMITENTE transmitente = trans.getDATOSTRANSMITENTE();

			interviniente.setP_ID_USUARIO(idUsuario);
			// interviniente.setP_NUM_EXPEDIENTE(); //se setea en el modelo tras guardar el
			// trámite
			interviniente.setP_ID_CONTRATO_SESSION(idContrato);

			// INFORMACION DEL INTERVINIENTE
			interviniente.setP_TIPO_INTERVINIENTE(TipoInterviniente.RepresentanteTransmitente.getValorEnum());
			interviniente.setP_FECHA_INICIO(DDMMAAAAToTimestamp(transmitente.getFECHAINICIOTUTELATRANSMITENTE()));
			// Cambios urgentes de Yerbabuena
			interviniente.setP_TIPO_DOCUMENTO_ALTERNATIVO(
					transmitente.getTIPODOCUMENTOSUSTITUTIVOREPRESENTANTETRANSMITENTE());
			interviniente.setP_FECHA_CADUCIDAD_NIF(
					DDMMAAAAToTimestamp(transmitente.getFECHACADUCIDADNIFREPRESENTANTETRANSMITENTE()));
			interviniente.setP_FECHA_CADUCIDAD_ALTERNATIVO(
					DDMMAAAAToTimestamp(transmitente.getFECHACADUCIDADDOCUMENTOSUSTITUTIVOREPRESENTANTETRANSMITENTE()));
			interviniente.setP_INDEFINIDO(transmitente.getINDEFINIDOREPRESENTANTETRANSMITENTE() != null
					? (transmitente.getINDEFINIDOREPRESENTANTETRANSMITENTE().equalsIgnoreCase(TipoSINO.SI.value())
							|| transmitente.getINDEFINIDOREPRESENTANTETRANSMITENTE().equalsIgnoreCase("S") ? S : N)
					: N);
			/**/
			interviniente.setP_FECHA_FIN(DDMMAAAAToTimestamp(transmitente.getFECHAFINTUTELATRANSMITENTE()));
			interviniente.setP_CONCEPTO_REPRE(transmitente.getCONCEPTOREPRESENTANTETRANSMITENTE());
			interviniente.setP_ID_MOTIVO_TUTELA(transmitente.getMOTIVOREPRESENTANTETRANSMITENTE());
			interviniente.setP_DATOS_DOCUMENTO(transmitente.getDOCUMENTOSREPRESENTANTETRANSMITENTE());

			// INFORMACIÓN DE LA PERSONA ASOCIADA AL INVOLUCRADO
			try {
				if (new BigDecimal(ga.getCABECERA().getDATOSGESTORIA().getPROFESIONAL()) != null) {
					String valor = new BigDecimal(ga.getCABECERA().getDATOSGESTORIA().getPROFESIONAL()).toString();
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

			interviniente.setP_NIF(transmitente.getDNIREPRESENTANTETRANSMITENTE());

			interviniente.setP_APELLIDO1_RAZON_SOCIAL(transmitente.getAPELLIDO1RAZONSOCIALREPRESENTANTETRANSMITENTE());
			interviniente.setP_APELLIDO2(transmitente.getAPELLIDO2REPRESENTANTETRANSMITENTE());
			interviniente.setP_NOMBRE(transmitente.getNOMBREREPRESENTANTETRANSMITENTE());

			if (!"".equals(transmitente.getNOMBREAPELLIDOSREPRESENTANTETRANSMITENTE())
					&& (("".equals(transmitente.getNOMBREREPRESENTANTETRANSMITENTE())
							|| null == transmitente.getNOMBREREPRESENTANTETRANSMITENTE())
							&& ("".equals(transmitente.getAPELLIDO1RAZONSOCIALREPRESENTANTETRANSMITENTE())
									|| null == transmitente.getNOMBREREPRESENTANTETRANSMITENTE()))) {
				/*** CAMBIO DEL NOMBRE CON COMAS *********/
				separarNombreConComas(interviniente,
						transmitente.getNOMBREAPELLIDOSREPRESENTANTETRANSMITENTE().replace(" ", ","));
			}

		}

		//El presentador, aquí cogemos el nif del presentador y sólo guardamos ese, ya que siempre va a estar.
		if (TipoInterviniente.PresentadorTrafico.getValorEnum().equals(tipoInterviniente)) {
			trafico.beans.jaxb.transmision.DATOSPRESENTADOR presentador = trans.getDATOSPRESENTADOR();

			if (presentador == null)
				return interviniente;

			interviniente.setP_ID_USUARIO(idUsuario);
			// interviniente.setP_NUM_EXPEDIENTE(); //se setea en el modelo tras guardar el trámite
			interviniente.setP_ID_CONTRATO_SESSION(idContrato);

			// INFORMACION DEL INTERVINIENTE
			interviniente.setP_TIPO_INTERVINIENTE(TipoInterviniente.PresentadorTrafico.getValorEnum());

			// INFORMACIÓN DE LA PERSONA ASOCIADA AL INVOLUCRADO
			try {
				if (new BigDecimal(ga.getCABECERA().getDATOSGESTORIA().getPROFESIONAL()) != null) {
					String valor = new BigDecimal(ga.getCABECERA().getDATOSGESTORIA().getPROFESIONAL()).toString();
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

			interviniente.setP_NIF(presentador.getDNIPRESENTADOR());
			/**
			 * Se ha comentado la inserción del presentador, puesto que siempre debería
			 * existir al ser quien ha realizado el alta del trámite.
			 */
			interviniente.setP_APELLIDO1_RAZON_SOCIAL(presentador.getAPELLIDO1RAZONSOCIALPRESENTADOR());
			interviniente.setP_APELLIDO2(presentador.getAPELLIDO2PRESENTADOR());
			interviniente.setP_NOMBRE(presentador.getNOMBREPRESENTADOR());
			if (interviniente.getP_NIF() != null && interviniente.getP_APELLIDO1_RAZON_SOCIAL() != null
					&& !"".equals(interviniente.getP_NIF()) && !"".equals(interviniente.getP_APELLIDO1_RAZON_SOCIAL())
					&& utilesConversiones.isNifNie(interviniente.getP_NIF())) {
				interviniente.setP_ANAGRAMA(Anagrama.obtenerAnagramaFiscal(interviniente.getP_APELLIDO1_RAZON_SOCIAL(),
						interviniente.getP_NIF()));
			}
			interviniente.setP_FECHA_NACIMIENTO(DDMMAAAAToTimestamp(presentador.getFECHANACIMIENTOPRESENTADOR()));
			// Cambios urgentes de Yerbabuena
			interviniente.setP_TIPO_DOCUMENTO_ALTERNATIVO(presentador.getTIPODOCUMENTOSUSTITUTIVOPRESENTADOR());
			interviniente.setP_FECHA_CADUCIDAD_NIF(DDMMAAAAToTimestamp(presentador.getFECHACADUCIDADNIFPRESENTADOR()));
			interviniente.setP_FECHA_CADUCIDAD_ALTERNATIVO(
					DDMMAAAAToTimestamp(presentador.getFECHACADUCIDADDOCUMENTOSUSTITUTIVOPRESENTADOR()));
			/**/
			interviniente.setP_TELEFONOS(presentador.getTELEFONOPRESENTADOR());
			interviniente.setP_SEXO(presentador.getSEXOPRESENTADOR());

			// INFORMACIÓN DE LA DIRECCION
			interviniente.setP_ID_PROVINCIA(
					utilesConversiones.getIdProvinciaFromSiglas(presentador.getPROVINCIAPRESENTADOR()));
			interviniente.setP_NUMERO(presentador.getNUMERODIRECCIONPRESENTADOR());
			interviniente.setP_COD_POSTAL(presentador.getCPPRESENTADOR());
			interviniente.setP_PUEBLO(presentador.getPUEBLOPRESENTADOR());
			interviniente.setP_LETRA(presentador.getLETRADIRECCIONPRESENTADOR());
			interviniente.setP_ESCALERA(presentador.getESCALERADIRECCIONPRESENTADOR());

			interviniente.setP_PLANTA(presentador.getPISODIRECCIONPRESENTADOR());
			interviniente.setP_PUERTA(presentador.getPUERTADIRECCIONPRESENTADOR());
			// Nuevo XSD
			interviniente.setP_BLOQUE(presentador.getBLOQUEDIRECCIONPRESENTADOR());

			BigDecimal kmAux = null != presentador.getKMDIRECCIONPRESENTADOR()
				&& !"".equals(presentador.getKMDIRECCIONPRESENTADOR())?
					new BigDecimal(presentador.getKMDIRECCIONPRESENTADOR()) : null;
			interviniente.setP_KM(kmAux);

			BigDecimal hmAux = null != presentador.getHMDIRECCIONPRESENTADOR() 
				&& !"".equals(presentador.getHMDIRECCIONPRESENTADOR())?
					new BigDecimal(presentador.getHMDIRECCIONPRESENTADOR()) : null;
			interviniente.setP_HM(hmAux);

			// Mantis 17701
			if (presentador.getDIRECCIONACTIVAPRESENTADOR() != null && !presentador.getDIRECCIONACTIVAPRESENTADOR().isEmpty()
					&& (("SI").equalsIgnoreCase(presentador.getDIRECCIONACTIVAPRESENTADOR().trim())
					|| (TipoSN.S.value()).equals(presentador.getDIRECCIONACTIVAPRESENTADOR().trim().toUpperCase()))) {
				interviniente.setP_DIRECCION_ACTIVA(TipoSN.S.value());
			} else if (presentador.getDIRECCIONACTIVAPRESENTADOR() != null && !presentador.getDIRECCIONACTIVAPRESENTADOR().isEmpty()
					&& (("NO").equalsIgnoreCase(presentador.getDIRECCIONACTIVAPRESENTADOR().trim())
					|| (TipoSN.N.value()).equals(presentador.getDIRECCIONACTIVAPRESENTADOR().trim().toUpperCase()))) {
				interviniente.setP_DIRECCION_ACTIVA(TipoSN.N.value());
			}

			//INFORMACIÓN PARA LAS IMPORTACIONES
			String valor = presentador.getSIGLASDIRECCIONPRESENTADOR();
			if (valor == null)
				valor = "";
			if ("6".equals(valor.trim()))
				valor = "41"; // calle
			else if ("44".equals(valor.trim()))
				valor = "5";
			else if ("24".equals(valor.trim()) || "34".equals(valor.trim()))
				valor = "29";

			interviniente.setP_ID_TIPO_DGT(valor.trim());
			interviniente.setP_MUNICIPIO(presentador.getMUNICIPIOPRESENTADOR());
			interviniente.setP_VIA(presentador.getNOMBREVIADIRECCIONPRESENTADOR());
		}

		//Añadimos el tipo de vía y el número a la dirección del interviniente:
		if ((interviniente.getP_VIA() != null && !"".equals(interviniente.getP_VIA()))
				&& (interviniente.getP_MUNICIPIO() != null && !"".equals(interviniente.getP_MUNICIPIO()))
				&& (interviniente.getP_COD_POSTAL() != null && !"".equals(interviniente.getP_COD_POSTAL()))
				&& (interviniente.getP_ID_PROVINCIA() != null && !"".equals(interviniente.getP_ID_PROVINCIA()))) {
			if (!(interviniente.getP_ID_TIPO_DGT() != null && !"".equals(interviniente.getP_ID_TIPO_DGT())))
				interviniente.setP_ID_TIPO_DGT(TIPO_DGT_BLANCO);
			if (!(interviniente.getP_NUMERO() != null && !"".equals(interviniente.getP_NUMERO())))
				interviniente.setP_NUMERO(SIN_NUMERO);
		}
		return interviniente;
	}

	private static void separarNombreConComas(BeanPQTramiteTraficoGuardarIntervinienteImport interviniente,
			String nombreEntero) {

		if (null == nombreEntero || "".equals(nombreEntero))
			return;
		String[] separado = nombreEntero.split("\\,");

		if (separado.length == 3) {
			interviniente.setP_APELLIDO1_RAZON_SOCIAL(separado[0].trim());
			interviniente.setP_APELLIDO2(separado[1].trim());
			interviniente.setP_NOMBRE(separado[2].trim());
		} else if (separado.length == 2) {
			interviniente.setP_APELLIDO1_RAZON_SOCIAL(separado[0].trim());
			interviniente.setP_APELLIDO2("");
			interviniente.setP_NOMBRE(separado[1].trim());
		} else if (separado.length == 1) {
			interviniente.setP_APELLIDO1_RAZON_SOCIAL(separado[0].trim());
			interviniente.setP_APELLIDO2("");
			interviniente.setP_NOMBRE("");
		}
	}

	/**
	 * Devuelve un beanPQ de Vehículo a partir de la información de un trámite ga (importado XML-MATE)
	 * @param ga
	 * @return
	 */
	public static BeanPQVehiculosGuardarImport convertirVehiculoGAtoPQ(trafico.beans.jaxb.transmision.FORMATOGA ga,trafico.beans.jaxb.transmision.TRANSMISION trans) {
		BeanPQVehiculosGuardarImport vehiculo = new BeanPQVehiculosGuardarImport();

		UtilesConversiones utilesConversiones = ContextoSpring.getInstance().getBean(UtilesConversiones.class);
		trafico.beans.jaxb.transmision.DATOSVEHICULO vehiculoGa = trans.getDATOSVEHICULO();
		if (vehiculoGa == null)
			return vehiculo;
		trafico.beans.jaxb.transmision.DATOS620 datos620 = trans.getDATOS620();

		try {
			if ("OEGAM".equals(ga.getPlataforma())) vehiculo.setP_NUM_EXPEDIENTE(new BigDecimal(trans.getNUMERODOCUMENTO()));
			else vehiculo.setP_NUM_EXPEDIENTE(null);
		} catch (Exception e) {
			vehiculo.setP_NUM_EXPEDIENTE(null);
			log.debug("El numero del documento no es un bigDecimal");
		}

		try {
			if (new BigDecimal(ga.getCABECERA().getDATOSGESTORIA().getPROFESIONAL()) != null) {
				String valor = new BigDecimal(ga.getCABECERA().getDATOSGESTORIA().getPROFESIONAL()).toString();
				if (valor.length() == 5) {
					valor = valor.substring(0, 4);
				}
				while (valor.length() < 4) {
					valor = "0" + valor;
				}
				vehiculo.setP_NUM_COLEGIADO(valor);
			}
		} catch (Exception e) {
			vehiculo.setP_NUM_COLEGIADO(null);
		}
		vehiculo.setP_BASTIDOR(vehiculoGa.getNUMEROBASTIDOR());
		vehiculo.setP_MATRICULA(StringUtils.isNotBlank(trans.getMATRICULA()) ? trans.getMATRICULA().trim() : trans.getMATRICULA());
		vehiculo.setP_MODELO(vehiculoGa.getMODELO());
		/**
		 * Incidencia 1831. Julio Molina. Se seteaba el campo que no era
		 */
		vehiculo.setP_TIPO_VEHICULO(vehiculoGa.getTIPOIDVEHICULO());

		// EL SERVICIO DESTINO NO SE VA A IMPORTAR SOLO EL SERVICIO y SERVICIO ANTERIOR

		if (null != vehiculoGa.getSERVICIO() && !"".equals(vehiculoGa.getSERVICIO())) {
			vehiculo.setP_ID_SERVICIO(vehiculoGa.getSERVICIO());
		}

		if (null != vehiculoGa.getSERVICIOANTERIOR() && !"".equals(vehiculoGa.getSERVICIOANTERIOR())){
			vehiculo.setP_ID_SERVICIO_ANTERIOR(vehiculoGa.getSERVICIOANTERIOR());
		}

		vehiculo.setP_FECHA_MATRICULACION(DDMMAAAAToTimestamp(vehiculoGa.getFECHAMATRICULACION()));

		vehiculo.setP_ID_MOTIVO_ITV(vehiculoGa.getMOTIVOITV());
		vehiculo.setP_FECHA_ITV(DDMMAAAAToTimestamp(vehiculoGa.getFECHACADUCIDADITV()));
		vehiculo.setP_CONCEPTO_ITV(vehiculoGa.getCONCEPTOITV());
		vehiculo.setP_ESTACION_ITV(vehiculoGa.getESTACIONITV());
		vehiculo.setP_FECHA_INSPECCION(DDMMAAAAToTimestamp(vehiculoGa.getFECHAITV()));

		if (vehiculoGa.getCHECKCADUCIDADITV() != null) {
			vehiculo.setP_CHECK_FECHA_CADUCIDAD_ITV(vehiculoGa.getCHECKCADUCIDADITV());
		}

		if (datos620 != null) {
			vehiculo.setP_CDMARCA(datos620.getMARCA620());
			vehiculo.setP_CDMODVEH(datos620.getMODELO620());

			vehiculo.setP_FECDESDE(fechaDevengotoFECDESDE(datos620.getFECHADEVENGO()));
			vehiculo.setP_TIPVEHI(null!=datos620.getTIPOVEHICULO()?utilesConversiones.tipVehiNuevo(datos620.getTIPOVEHICULO()) : null);

			if (null != datos620.getTIPOVEHICULO() && !"".equals(datos620.getTIPOVEHICULO())
					&& ("A".equals(datos620.getTIPOVEHICULO()) || "B".equals(datos620.getTIPOVEHICULO())
							|| "T".equals(datos620.getTIPOVEHICULO()) || "X".equals(datos620.getTIPOVEHICULO()))) {
				vehiculo.setP_CDMARCA(datos620.getMARCA620());
				vehiculo.setP_CDMODVEH(datos620.getMODELO620());
			} else {
				vehiculo.setP_CDMARCA(datos620.getMARCA620DESC());
				vehiculo.setP_CDMODVEH(datos620.getMODELO620DESC());
			}

			vehiculo.setP_ID_CARBURANTE(datos620.getCARBURANTE());
			vehiculo.setP_POTENCIA_FISCAL(ContextoSpring.getInstance().getBean(Utiles.class).stringToBigDecimalDosDecimales(datos620.getPOTENCIAFISCAL()));
			try {
				vehiculo.setP_CILINDRADA(quitarDecimales(datos620.getCILINDRADA()));
			} catch (Exception e) {
				vehiculo.setP_CILINDRADA("");
			}

			vehiculo.setP_FECHA_PRIM_MATRI(DDMMAAAAToTimestamp(datos620.getFECHAPRIMERAMATRICULACION()));
			try {
				vehiculo.setP_N_CILINDROS(new BigDecimal(datos620.getNUMCILINDROS()));
			} catch (Exception e) {
				vehiculo.setP_N_CILINDROS(null);
			}
			vehiculo.setP_CARACTERISTICAS(datos620.getCARACTERISTICAS());

			//DATOS DE LA DIRECCIÓN DEL VEHÍCULO

			if (datos620.getDIRECCIONDISTINTAADQUIRIENTE() != null) {
				/*
				vehiculo.setP_ID_PROVINCIA(utilesConversiones.getIdProvinciaFromSiglas(datos620.getDIRECCIONDISTINTAADQUIRIENTE().getPROVINCIADISTINTAADQUIRIENTE()));
				//vehiculo.setP_ID_MUNICIPIO();
				vehiculo.setP_ID_TIPO_VIA("");
				//vehiculo.setP_NOMBRE_VIA); //importación
				vehiculo.setP_NUMERO(datos620.getDIRECCIONDISTINTAADQUIRIENTE().getNUMERODIRECCIONDISTINTAADQUIRIENTE());
				vehiculo.setP_COD_POSTAL(datos620.getDIRECCIONDISTINTAADQUIRIENTE().getCPDISTINTAADQUIRIENTE());
				//vehiculo.setP_PUEBLO(); //importacion
				vehiculo.setP_LETRA(datos620.getDIRECCIONDISTINTAADQUIRIENTE().getLETRADIRECCIONDISTINTAADQUIRIENTE());
				vehiculo.setP_ESCALERA(datos620.getDIRECCIONDISTINTAADQUIRIENTE().getESCALERADIRECCIONDISTINTAADQUIRIENTE());

				vehiculo.setP_PLANTA(datos620.getDIRECCIONDISTINTAADQUIRIENTE().getPISODIRECCIONDISTINTAADQUIRIENTE());
				vehiculo.setP_PUERTA(datos620.getDIRECCIONDISTINTAADQUIRIENTE().getPUERTADIRECCIONDISTINTAADQUIRIENTE());
				//vehiculo.setP_NUM_LOCAL();
				//Nuevo xsd
				vehiculo.setP_BLOQUE(datos620.getDIRECCIONDISTINTAADQUIRIENTE().getBLOQUEDIRECCIONDISTINTAADQUIRIENTE());

				BigDecimal kmAux = null != datos620.getDIRECCIONDISTINTAADQUIRIENTE().getKMDIRECCIONDISTINTAADQUIRIENTE() 
				&& !"".equals(datos620.getDIRECCIONDISTINTAADQUIRIENTE().getKMDIRECCIONDISTINTAADQUIRIENTE())?
						new BigDecimal(datos620.getDIRECCIONDISTINTAADQUIRIENTE().getKMDIRECCIONDISTINTAADQUIRIENTE()) : null;
				vehiculo.setP_KM(kmAux);

				BigDecimal hmAux = null != datos620.getDIRECCIONDISTINTAADQUIRIENTE().getHMDIRECCIONDISTINTAADQUIRIENTE() 
				&& !"".equals(datos620.getDIRECCIONDISTINTAADQUIRIENTE().getHMDIRECCIONDISTINTAADQUIRIENTE())?
						new BigDecimal(datos620.getDIRECCIONDISTINTAADQUIRIENTE().getHMDIRECCIONDISTINTAADQUIRIENTE()) : null;
				vehiculo.setP_HM(hmAux);

				//DATOS DEL VEHICULO PREVER

				//vehiculo.setP_PREV_MATRICULA();
				//vehiculo.setP_PREV_BASTIDOR();
				//vehiculo.setP_PREV_CODIGO_MARCA();
				//vehiculo.setP_PREV_MODELO();
				//vehiculo.setP_PREV_CLASIFICACION_ITV();
				//vehiculo.setP_PREV_ID_TIPO_TARJETA_ITV();

				//INFORMACIÓN PARA LAS IMPORTACIONES
				String valor = datos620.getDIRECCIONDISTINTAADQUIRIENTE().getSIGLASDIRECCIONDISTINTAADQUIRIENTE();
				if (valor==null) valor="";
				if ("6".equals(valor.trim())) valor="41"; //calle
				if ("44".equals(valor.trim())) valor="5";
				if ("24".equals(valor.trim()) || "34".equals(valor.trim())) valor="29";
				//vehiculo.setP_ID_TIPO_DGT(!"".equals(valor.trim())?valor.trim():TIPO_DGT_BLANCO);
				vehiculo.setP_ID_TIPO_DGT(valor.trim());
				vehiculo.setP_MUNICIPIO(datos620.getDIRECCIONDISTINTAADQUIRIENTE().getMUNICIPIODISTINTAADQUIRIENTE());
				vehiculo.setP_PUEBLO_LIT(datos620.getDIRECCIONDISTINTAADQUIRIENTE().getPUEBLODISTINTAADQUIRIENTE());
				vehiculo.setP_VIA(datos620.getDIRECCIONDISTINTAADQUIRIENTE().getNOMBREVIADIRECCIONDISTINTAADQUIRIENTE());
				*/
			}
		}

		String marcaSinEditar = ContextoSpring.getInstance().getBean(Utiles.class).getMarcaSinEditar(vehiculoGa.getMARCA());
		vehiculo.setP_MARCA_SIN_EDITAR(marcaSinEditar);
		vehiculo.setP_MARCA(vehiculoGa.getMARCA());
		// DRC@21-09-2012 Incidencia: 2358
		vehiculo.setP_TARA(vehiculoGa.getTARA());
		vehiculo.setP_PLAZAS(vehiculoGa.getPLAZAS() != null && !vehiculoGa.getPLAZAS().equalsIgnoreCase("") ? new BigDecimal(vehiculoGa.getPLAZAS()) : null);
		vehiculo.setP_PESO_MMA(vehiculoGa.getPESOMMA());
		vehiculo.setP_KILOMETRAJE(vehiculoGa.getKILOMETRAJE());
		if (vehiculoGa.getPROVINCIAPRIMERAMATRICULA()!=null && !vehiculoGa.getPROVINCIAPRIMERAMATRICULA().equals("")
				&& !vehiculoGa.getPROVINCIAPRIMERAMATRICULA().equals("-1")) {
			BigDecimal codigoProvincia = new BigDecimal(utilesConversiones.getIdProvinciaFromSiglas(vehiculoGa.getPROVINCIAPRIMERAMATRICULA()));
			vehiculo.setP_PROVINCIA_PRIMERA_MATRICULA(codigoProvincia);
		} else {
			vehiculo.setP_PROVINCIA_PRIMERA_MATRICULA(new BigDecimal(-1));
		}

		//Añadimos el tipo de vía y el número a la dirección del vehículo:
		if ((vehiculo.getP_VIA() != null && !"".equals(vehiculo.getP_VIA()))
				&& (vehiculo.getP_MUNICIPIO() != null && !"".equals(vehiculo.getP_MUNICIPIO()))
				&& (vehiculo.getP_COD_POSTAL() != null && !"".equals(vehiculo.getP_COD_POSTAL()))
				&& (vehiculo.getP_ID_PROVINCIA() != null && !"".equals(vehiculo.getP_ID_PROVINCIA()))) {
			if (!(vehiculo.getP_ID_TIPO_DGT() != null && !"".equals(vehiculo.getP_ID_TIPO_DGT())))
				vehiculo.setP_ID_TIPO_DGT(TIPO_DGT_BLANCO);
			if (!(vehiculo.getP_NUMERO() != null && !"".equals(vehiculo.getP_NUMERO())))
				vehiculo.setP_NUMERO(SIN_NUMERO);
		}

		// DVV
		if(vehiculo.getP_ES_SINIESTRO() != null){
			vehiculo.setP_ES_SINIESTRO(Boolean.TRUE);
		}

//		if(vehiculo.getP_TIENE_CARGA_FINANCIERA() != null){
//			vehiculo.setP_TIENE_CARGA_FINANCIERA(Boolean.TRUE);
//		}

		return vehiculo;
	}

	/**
	 * Convierte una fecha String DD/MM/AAAA en un Timestamp. Ponemos manualmente la hora a 00:00:00
	 */
	public static Timestamp fechaDevengotoFECDESDE(String valor) {
		if (valor!=null && (!"".equals(valor.trim()))) {
			String[] fecha = valor.split("/");
			String anyo = fecha[2];
			String mes = "01";
			String dia = "01";
			String valorToTimestamp = anyo + "-" + mes + "-" + dia + " 00:00:00";
			try {
				return Timestamp.valueOf(valorToTimestamp);
			} catch (Exception e) {
				return null;
			}
		}
		return null;
	}

	/**
	 * Convierte una fecha String DD/MM/AAAA en un Timestamp. Ponemos manualmente la hora a 00:00:00
	 */
	public static Timestamp DDMMAAAAToTimestamp(String valor) {
		if (valor != null && !"".equals(valor.trim())) {
			String[] fecha = valor.split("/");
			String anyo = fecha[2];
			String mes = fecha[1].length()==2?fecha[1]:("0"+fecha[1]);
			String dia = fecha[0].length()==2?fecha[0]:("0"+fecha[0]);
			String valorToTimestamp = anyo + "-" + mes + "-" + dia + " 00:00:00";
			return Timestamp.valueOf(valorToTimestamp);
		}
		return null;
	}

	public static String quitarDecimales(String valor) {
		if (valor == null || valor.equals("")) 
			return "";
		valor = valor.replaceAll("\\.", "");
		valor = valor.replaceAll(",", ".");
		BigDecimal valorDecimal;
		try {
			valorDecimal = new BigDecimal(valor);
		} catch (Exception e) {
			return "";
		}
		return valorDecimal.toBigInteger().toString();
	}

	/**
	 * Pasa por ejemplo un string como 20.000,12 a BigDecimal
	 * @param valor
	 * @return
	 */
	public static BigDecimal stringDecimalToBigDecimal(String valor) {
		if (valor == null || valor.equals("")) 
			return null;
		Float num = null;
		valor = valor.replace(".", "");
		valor = valor.replace(",", "");
		num = new Float(valor);
		num = num/100;
		return new BigDecimal(num.toString());
	}
}