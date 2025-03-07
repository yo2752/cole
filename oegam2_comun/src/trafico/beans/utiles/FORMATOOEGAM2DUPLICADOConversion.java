package trafico.beans.utiles;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.gestoresmadrid.core.springContext.ContextoSpring;
import org.gestoresmadrid.oegam2comun.tasas.view.beans.Tasa;
import org.gestoresmadrid.utilidades.components.UtilesFecha;

import escrituras.beans.Direccion;
import escrituras.beans.Municipio;
import escrituras.beans.Persona;
import escrituras.beans.Provincia;
import escrituras.beans.TipoVia;
import trafico.beans.IntervinienteTrafico;
import trafico.beans.JefaturaTrafico;
import trafico.beans.TramiteTraficoBean;
import trafico.beans.TramiteTraficoDuplicadoBean;
import trafico.beans.VehiculoBean;
import trafico.beans.jaxb.duplicados.DATOSCOTITULAR;
import trafico.beans.jaxb.duplicados.DATOSREPRESENTANTETITULAR;
import trafico.beans.jaxb.duplicados.DATOSTITULAR;
import trafico.beans.jaxb.duplicados.DATOSVEHICULO;
import trafico.beans.jaxb.duplicados.DIRECCION;
import trafico.beans.jaxb.duplicados.DUPLICADO;
import trafico.beans.jaxb.duplicados.FORMATOOEGAM2DUPLICADO;
import trafico.utiles.enumerados.TipoCreacion;
import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;

public class FORMATOOEGAM2DUPLICADOConversion {

	/* INICIO ATRIBUTOS */

	private static final ILoggerOegam log = LoggerOegam.getLogger(FORMATOOEGAM2DUPLICADOConversion.class);

	private static SimpleDateFormat formatoFechas = new SimpleDateFormat("dd/MM/yyyy");

	/* FIN ATRIBUTOS */

	/* INICIO MÉTODOS DE CONVERSIÓN */

	public List<TramiteTraficoDuplicadoBean> convertirFORMATOOEGAM2DUPLICADOtoListaTramiteTraficoDuplicadoBean(FORMATOOEGAM2DUPLICADO ga, String numColegiado, BigDecimal idContrato) {

		List<TramiteTraficoDuplicadoBean> listaTramiteTraficoDuplicado = new ArrayList<>();

		if (ga != null) {
			for (DUPLICADO duplicado : ga.getDUPLICADO()) {
				TramiteTraficoDuplicadoBean tramiteTraficoDuplicado = convertirFORMATOOEGAM2DUPLICADOtoTramiteTraficoDuplicadoBean(duplicado, numColegiado, idContrato);
				listaTramiteTraficoDuplicado.add(tramiteTraficoDuplicado);
			}
		}

		return listaTramiteTraficoDuplicado;

	}

	private TramiteTraficoDuplicadoBean convertirFORMATOOEGAM2DUPLICADOtoTramiteTraficoDuplicadoBean(DUPLICADO duplicado, String numColegiado, BigDecimal idContrato) {

		TramiteTraficoDuplicadoBean tramiteTraficoDuplicado = null;
		UtilesFecha utilesFecha = ContextoSpring.getInstance().getBean(UtilesFecha.class);

		if (duplicado != null) {
			TramiteTraficoBean tramiteTrafico = new TramiteTraficoBean();
			tramiteTrafico.setNumColegiado(numColegiado);
			tramiteTrafico.setIdContrato(idContrato);

			tramiteTraficoDuplicado = new TramiteTraficoDuplicadoBean();

			// Datos duplicado
			if (duplicado.getDATOSDUPLICADO() != null) {
				tramiteTraficoDuplicado.setMotivoDuplicado(duplicado.getDATOSDUPLICADO().getMOTIVODUPLICADO());
				tramiteTrafico.setReferenciaPropia(duplicado.getDATOSDUPLICADO().getREFERENCIAPROPIA());
				tramiteTraficoDuplicado.setImprPermisoCircu(duplicado.getDATOSDUPLICADO().isINDIMPRESIONPERMISOCIRCULACION());
				tramiteTraficoDuplicado.setImportacion(duplicado.getDATOSDUPLICADO().isINDVEHICULOIMPORTACION());
				tramiteTraficoDuplicado.setTasaDuplicado(duplicado.getDATOSDUPLICADO().getCODIGOTASAVEHICULOIMPORTACION());
				tramiteTrafico.setAnotaciones(duplicado.getDATOSDUPLICADO().getOBSERVACIONESDGT());
			}

			// Datos vehículo
			if (duplicado.getDATOSVEHICULO() != null) {
				tramiteTrafico.setVehiculo(convertirDATOSVEHICULOToVehiculoBean(duplicado.getDATOSVEHICULO()));
			}

			// Datos titular
			if (duplicado.getDATOSTITULAR() != null) {
				tramiteTraficoDuplicado.setTitular(convertirDATOSTITULARToIntervinienteTraficoBean(duplicado.getDATOSTITULAR()));
			}

			// Datos representante titular
			if (duplicado.getDATOSREPRESENTANTETITULAR() != null) {
				tramiteTraficoDuplicado.setRepresentante(convertirDATOSREPRESENTANTETITULARToRepresentanteBean(duplicado.getDATOSREPRESENTANTETITULAR()));
			}

			// Datos cotitular
			if (duplicado.getDATOSCOTITULAR() != null) {
				tramiteTraficoDuplicado.setCotitular(convertirDATOSCOTITULARToIntervinienteTraficoBean(duplicado.getDATOSCOTITULAR()));
			}

			// Datos pago/presentación
			if (duplicado.getDATOSPAGOPRESENTACION() != null) {
				
				String codigoTasa = duplicado.getDATOSPAGOPRESENTACION().getCODIGOTASAPERMISO() != null
						? duplicado.getDATOSPAGOPRESENTACION().getCODIGOTASAPERMISO()
						: duplicado.getDATOSPAGOPRESENTACION().getCODIGOTASAFICHA();

				Tasa tasa = new Tasa();
				tasa.setCodigoTasa(codigoTasa);
				tramiteTrafico.setTasa(tasa);

				JefaturaTrafico jefaturaTrafico = new JefaturaTrafico();
				jefaturaTrafico.setJefaturaProvincial(duplicado.getDATOSPAGOPRESENTACION().getJEFATURAPROVINCIAL());
				tramiteTrafico.setJefaturaTrafico(jefaturaTrafico);
				try {
					tramiteTrafico.setFechaPresentacion(utilesFecha.getFechaConDate(formatoFechas.parse(duplicado.getDATOSPAGOPRESENTACION().getFECHAPRESENTACION())));
				} catch (ParseException e) {
					e.printStackTrace();
				}

			}

			//DRC@15-02-2013 Incidencia Mantis: 2708
			tramiteTraficoDuplicado.setIdTipoCreacion(new BigDecimal(TipoCreacion.OEGAM.getValorEnum()));

			tramiteTraficoDuplicado.setTramiteTrafico(tramiteTrafico);
		}

		return tramiteTraficoDuplicado;

	}

	private IntervinienteTrafico convertirDATOSREPRESENTANTETITULARToRepresentanteBean(DATOSREPRESENTANTETITULAR datosRepresentanteTitular) {
		UtilesFecha utilesFecha = ContextoSpring.getInstance().getBean(UtilesFecha.class);

		Persona persona = new Persona();
		persona.setNif(datosRepresentanteTitular.getNIFCIF());
		persona.setApellido1RazonSocial(datosRepresentanteTitular.getAPELLIDO1RAZONSOCIAL());
		persona.setApellido2(datosRepresentanteTitular.getAPELLIDO2());
		persona.setNombre(datosRepresentanteTitular.getNOMBRE());

		IntervinienteTrafico representante = new IntervinienteTrafico();
		representante.setPersona(persona);
		representante.setConceptoRepre(datosRepresentanteTitular.getCONCEPTOTUTELA());
		representante.setIdMotivoTutela(datosRepresentanteTitular.getMOTIVOTUTELA());
		representante.setDatosDocumento(datosRepresentanteTitular.getDATOSDOCUMENTO());
		try {
			representante.setFechaInicio(utilesFecha.getFechaConDate(formatoFechas.parse(datosRepresentanteTitular.getFECHAINICIOTUTELA())));
			representante.setFechaFin(utilesFecha.getFechaConDate(formatoFechas.parse(datosRepresentanteTitular.getFECHAFINTUTELA())));
		} catch (ParseException e) {
			log.warn("Ha ocurrido un error al convertir las fechas de representante titular");
		}

		return representante;
	}

	private VehiculoBean convertirDATOSVEHICULOToVehiculoBean(DATOSVEHICULO datosVehiculo) {
		UtilesFecha utilesFecha = ContextoSpring.getInstance().getBean(UtilesFecha.class);
		VehiculoBean vehiculo = new VehiculoBean();
		vehiculo.setMatricula(datosVehiculo.getMATRICULA());
		try {
			vehiculo.setFechaMatriculacion(utilesFecha.getFechaConDate(formatoFechas.parse(datosVehiculo.getFECHAMATRICULACION())));
			vehiculo.setFechaItv(utilesFecha.getFechaConDate(formatoFechas.parse(datosVehiculo.getFECHAITV())));
		} catch (ParseException e) {
			log.warn("Ha ocurrido un error al convertir las fechas del vehiculo");
		}

		return vehiculo;
	}

	private static IntervinienteTrafico convertirDATOSTITULARToIntervinienteTraficoBean(DATOSTITULAR datosTitular) {
		Persona persona = new Persona();
		persona.setNif(datosTitular.getNIFCIF());
		persona.setTipoPersona(datosTitular.getTIPOPERSONA());
		persona.setSexo(datosTitular.getSEXO());
		persona.setApellido1RazonSocial(datosTitular.getAPELLIDO1RAZONSOCIAL());
		persona.setApellido2(datosTitular.getAPELLIDO2());
		persona.setNombre(datosTitular.getNOMBRE());

		if (datosTitular.getDIRECCION() != null) {
			persona.setDireccion(convertirDIRECCIONToDireccionBean(datosTitular.getDIRECCION()));
		}

		IntervinienteTrafico titular = new IntervinienteTrafico();
		titular.setPersona(persona);
		return titular;
	}


	private static IntervinienteTrafico convertirDATOSCOTITULARToIntervinienteTraficoBean(DATOSCOTITULAR datosCotitular) {
		Persona persona = new Persona();
		persona.setNif(datosCotitular.getNIFCIF());
		persona.setTipoPersona(datosCotitular.getTIPOPERSONA());
		persona.setSexo(datosCotitular.getSEXO());
		persona.setApellido1RazonSocial(datosCotitular.getAPELLIDO1RAZONSOCIAL());
		persona.setApellido2(datosCotitular.getAPELLIDO2());
		persona.setNombre(datosCotitular.getNOMBRE());

		if (datosCotitular.getDIRECCION() != null) {
			persona.setDireccion(convertirDIRECCIONToDireccionBean(datosCotitular.getDIRECCION()));
		}

		IntervinienteTrafico titular = new IntervinienteTrafico();
		titular.setPersona(persona);
		return titular;
	}

	private static Direccion convertirDIRECCIONToDireccionBean(DIRECCION direccion) {
		Provincia provincia = new Provincia();
		provincia.setIdProvincia(direccion.getPROVINCIA());

		Municipio municipio = new Municipio();
		municipio.setIdMunicipio(direccion.getMUNICIPIO());
		municipio.setProvincia(provincia);

		TipoVia tipoVia = new TipoVia();
		tipoVia.setIdTipoVia(direccion.getTIPOVIA());

		Direccion direccionBean = new Direccion();
		direccionBean.setMunicipio(municipio);
		direccionBean.setPueblo(direccion.getPUEBLO());
		direccionBean.setCodPostal(direccion.getCODIGOPOSTAL());
		direccionBean.setTipoVia(tipoVia);
		direccionBean.setNombreVia(direccion.getNOMBREVIA());
		direccionBean.setNumero(direccion.getNUMERO());
		direccionBean.setLetra(direccion.getLETRA());
		direccionBean.setEscalera(direccion.getESCALERA());
		direccionBean.setPlanta(direccion.getPISO());
		direccionBean.setPuerta(direccion.getPUERTA());
		direccionBean.setBloque(direccion.getBLOQUE());
		direccionBean.setPuntoKilometrico(direccion.getKM());
		direccionBean.setHm(direccion.getHM());

		return direccionBean;
	}
	/* FIN MÉTODOS DE CONVERSIÓN */

}