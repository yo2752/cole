package org.gestoresmadrid.oegamImportacion.schemas.utiles;

import java.io.File;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.gestoresmadrid.core.contrato.model.vo.ContratoVO;
import org.gestoresmadrid.core.direccion.model.vo.DireccionVO;
import org.gestoresmadrid.core.general.model.vo.UsuarioVO;
import org.gestoresmadrid.core.intervinienteTrafico.model.vo.IntervinienteTraficoPK;
import org.gestoresmadrid.core.intervinienteTrafico.model.vo.IntervinienteTraficoVO;
import org.gestoresmadrid.core.model.enumerados.EstadoTramiteTrafico;
import org.gestoresmadrid.core.model.enumerados.TipoInterviniente;
import org.gestoresmadrid.core.model.enumerados.TipoTramiteTrafico;
import org.gestoresmadrid.core.personas.model.enumerados.SexoPersona;
import org.gestoresmadrid.core.personas.model.enumerados.SubtipoPersona;
import org.gestoresmadrid.core.personas.model.enumerados.TipoPersona;
import org.gestoresmadrid.core.personas.model.vo.PersonaPK;
import org.gestoresmadrid.core.personas.model.vo.PersonaVO;
import org.gestoresmadrid.core.tasas.model.vo.TasaVO;
import org.gestoresmadrid.core.trafico.model.vo.TramiteTrafBajaVO;
import org.gestoresmadrid.core.vehiculo.model.vo.VehiculoVO;
import org.gestoresmadrid.oegamConversiones.conversion.ParametrosConversiones;
import org.gestoresmadrid.oegamConversiones.jaxb.baja.BAJA;
import org.gestoresmadrid.oegamConversiones.jaxb.baja.DATOSADQUIRIENTE;
import org.gestoresmadrid.oegamConversiones.jaxb.baja.DATOSREPRESENTANTE;
import org.gestoresmadrid.oegamConversiones.jaxb.baja.DATOSREPRESENTANTEADQUIRIENTE;
import org.gestoresmadrid.oegamConversiones.jaxb.baja.DATOSTITULAR;
import org.gestoresmadrid.oegamConversiones.jaxb.baja.DATOSVEHICULO;
import org.gestoresmadrid.oegamConversiones.jaxb.baja.FORMATOOEGAM2BAJA;
import org.gestoresmadrid.oegamConversiones.schemas.utiles.XMLBajaFactory;
import org.gestoresmadrid.oegamImportacion.bean.ResultadoImportacionBean;
import org.gestoresmadrid.oegamImportacion.constantes.Constantes;
import org.gestoresmadrid.oegamImportacion.contrato.service.ServicioContratoImportacion;
import org.gestoresmadrid.oegamImportacion.direccion.service.ServicioPaisImportacion;
import org.gestoresmadrid.oegamImportacion.persona.service.ServicioPersonaImportacion;
import org.gestoresmadrid.oegamImportacion.utiles.UtilidadesAnagramaImportacion;
import org.gestoresmadrid.oegamImportacion.utiles.UtilidadesConversionesImportacion;
import org.gestoresmadrid.oegamImportacion.utiles.UtilidadesNIFValidatorImportacion;
import org.gestoresmadrid.utilidades.components.GestorPropiedades;
import org.gestoresmadrid.utilidades.components.UtilesFecha;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;
import utilidades.web.excepciones.XmlNoValidoExcepcion;

@Component
public class ConversionFormatoImpGABaja implements Serializable {

	private static final long serialVersionUID = -5306915677645033775L;

	private static final ILoggerOegam log = LoggerOegam.getLogger(ConversionFormatoImpGABaja.class);

	@Autowired
	XMLBajaFactory xmlBajaFactory;

	@Autowired
	ServicioPaisImportacion servicioPais;

	@Autowired
	UtilidadesAnagramaImportacion utilidadesAnagrama;

	@Autowired
	UtilidadesNIFValidatorImportacion utilidadesNIFValidator;

	@Autowired
	ParametrosConversiones conversiones;

	@Autowired
	ServicioContratoImportacion servicioContrato;

	@Autowired
	ServicioPersonaImportacion servicioPersona;

	@Autowired
	GestorPropiedades gestorPropiedades;

	@Autowired
	UtilesFecha utilesFecha;

	@Autowired
	UtilidadesConversionesImportacion utilidadesConversiones;

	public ResultadoImportacionBean convertirFicheroFormatoGA(File fichero, ContratoVO contrato, Boolean tienePermisoAdmin) {
		ResultadoImportacionBean resultado = new ResultadoImportacionBean(Boolean.FALSE);
		try {
			xmlBajaFactory.validarXMLFORMATOOEGAM2BAJA(fichero, gestorPropiedades.valorPropertie(Constantes.PROPERTIES_RUTA_ESQUEMA_BAJA));
			FORMATOOEGAM2BAJA ficheroBaja = xmlBajaFactory.getFormatoOegam2Baja(fichero);
			if (ficheroBaja.getCABECERA() == null || ficheroBaja.getCABECERA().getDATOSGESTORIA() == null || ficheroBaja.getCABECERA().getDATOSGESTORIA().getPROFESIONAL() == null || "".equals(
					ficheroBaja.getCABECERA().getDATOSGESTORIA().getPROFESIONAL())) {
				resultado.setError(Boolean.TRUE);
				resultado.setMensaje("Debe de indicar los datos de la cabecera para poder importar el fichero.");
			} else if (!contrato.getColegiado().getNumColegiado().equals(ficheroBaja.getCABECERA().getDATOSGESTORIA().getPROFESIONAL()) && !tienePermisoAdmin) {
				resultado.setError(Boolean.TRUE);
				resultado.setMensaje("Solo se pueden importar tramites del mismo gestor.");
			} else {
				resultado.setFormatoOegam2Baja(ficheroBaja);
			}
		} catch (XmlNoValidoExcepcion e) {
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("No se puede importar el fichero por los siguientes errores: " + e.getMensajeError1());
		} catch (Throwable e) {
			log.error("Ha sucedido un error a la hora convertir el fichero, error", e);
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("Ha sucedido un error a la hora convertir el fichero.");
		}
		return resultado;
	}

	public ResultadoImportacionBean convertirFormatoGAToVO(FORMATOOEGAM2BAJA formatoOegam2Baja, ContratoVO contrato, Boolean tienePermisoAdmin, String tipoCreacion, Long idUsuario) {
		ResultadoImportacionBean resultado = new ResultadoImportacionBean(Boolean.FALSE);
		int posicion = 0;
		int cont = 1;
		for (BAJA bajaImportar : formatoOegam2Baja.getBAJA()) {
			log.error("INICIO CONVERTIR VO Baja tramite " + cont + " del colegiado " + contrato.getColegiado().getNumColegiado());
			try {
				TramiteTrafBajaVO tramiteBaja = convertirTramiteFormatoOegam2BajaToVO(formatoOegam2Baja.getCABECERA().getDATOSGESTORIA().getPROFESIONAL(), bajaImportar, contrato, tienePermisoAdmin,
						tipoCreacion, idUsuario);
				List<IntervinienteTraficoVO> listaIntervinientes = new ArrayList<IntervinienteTraficoVO>();
				if (bajaImportar.getDATOSVEHICULO() != null) {
					tramiteBaja.setVehiculo(convertirVehiculoGAtoVO(bajaImportar.getDATOSVEHICULO(), contrato));
				}
				if (bajaImportar.getDATOSADQUIRIENTE() != null) {
					IntervinienteTraficoVO interviniente = convertirAdquirienteGAtoVO(bajaImportar.getDATOSADQUIRIENTE(), contrato);
					if (interviniente != null) {
						listaIntervinientes.add(interviniente);
					}
				}
				if (bajaImportar.getDATOSTITULAR() != null) {
					IntervinienteTraficoVO interviniente = convertirTitularGAtoVO(bajaImportar.getDATOSTITULAR(), contrato);
					if (interviniente != null) {
						listaIntervinientes.add(interviniente);
					}
				}
				if (bajaImportar.getDATOSREPRESENTANTE() != null) {
					IntervinienteTraficoVO interviniente = convertirRepreTitularGAtoVO(bajaImportar.getDATOSREPRESENTANTE(), contrato);
					if (interviniente != null) {
						listaIntervinientes.add(interviniente);
					}
				}
				if (bajaImportar.getDATOSREPRESENTANTEADQUIRIENTE() != null) {
					IntervinienteTraficoVO interviniente = convertirRepreAdquirienteGAtoVO(bajaImportar.getDATOSREPRESENTANTEADQUIRIENTE(), contrato);
					if (interviniente != null) {
						listaIntervinientes.add(interviniente);
					}
				}

				tramiteBaja.setIntervinienteTraficos(listaIntervinientes);

				if (bajaImportar.getPAISBAJA() != null) {
					tramiteBaja.setPais(servicioPais.getIdPaisPorSigla(bajaImportar.getPAISBAJA()));
				}
				resultado.addListaTramiteBaja(tramiteBaja, posicion);
				log.error("FIN CONVERTIR VO Baja tramite " + cont + " del colegiado " + contrato.getColegiado().getNumColegiado());
			} catch (Throwable th) {
				log.error("Error al convertir Baja tramite " + cont + " colegiado " + contrato.getColegiado().getNumColegiado(), th);
				resultado.addConversionesError(gestionarMensajeErrorConversion(bajaImportar));
			}
			cont++;
			posicion++;
		}
		return resultado;
	}

	private String gestionarMensajeErrorConversion(BAJA bajaImportar) {
		String mensaje = "";
		if (bajaImportar != null) {
			if (bajaImportar.getDATOSVEHICULO() != null && StringUtils.isNotBlank(bajaImportar.getDATOSVEHICULO().getNUMEROBASTIDOR())) {
				mensaje = "Error el convertir el trámite con bastidor: " + bajaImportar.getDATOSVEHICULO().getNUMEROBASTIDOR();
			} else if (bajaImportar.getDATOSVEHICULO() != null && StringUtils.isNotBlank(bajaImportar.getDATOSVEHICULO().getMATRICULA())) {
				mensaje = "Error el convertir el trámite con matrícula: " + bajaImportar.getDATOSVEHICULO().getMATRICULA();
			}
		}
		return mensaje;
	}

	private IntervinienteTraficoVO convertirRepreAdquirienteGAtoVO(DATOSREPRESENTANTEADQUIRIENTE repreAdquiriente, ContratoVO contrato) {
		IntervinienteTraficoVO interviniente = null;
		if (repreAdquiriente != null && StringUtils.isNotBlank(repreAdquiriente.getDNIREPRESENTANTEADQUIRIENTE())) {
			interviniente = new IntervinienteTraficoVO();
			IntervinienteTraficoPK idInterviniente = new IntervinienteTraficoPK();
			idInterviniente.setNumColegiado(contrato.getColegiado().getNumColegiado());
			idInterviniente.setNif(repreAdquiriente.getDNIREPRESENTANTEADQUIRIENTE().toUpperCase());
			idInterviniente.setTipoInterviniente(TipoInterviniente.RepresentanteAdquiriente.getValorEnum());
			interviniente.setId(idInterviniente);
			interviniente.setConceptoRepre(repreAdquiriente.getCONCEPTOREPRESENTANTEADQUIRIENTE());

			PersonaVO persona = new PersonaVO();
			PersonaPK idPersona = new PersonaPK();
			idPersona.setNif(repreAdquiriente.getDNIREPRESENTANTEADQUIRIENTE().toUpperCase());
			idPersona.setNumColegiado(contrato.getColegiado().getNumColegiado());
			persona.setId(idPersona);

			if (StringUtils.isNotBlank(repreAdquiriente.getAPELLIDO1RAZONSOCIALREPRESENTANTEADQUIRIENTE())) {
				persona.setApellido1RazonSocial(repreAdquiriente.getAPELLIDO1RAZONSOCIALREPRESENTANTEADQUIRIENTE().toUpperCase());
			} else {
				PersonaVO personaBBDD = servicioPersona.getPersonaVO(persona.getId().getNif(), persona.getId().getNumColegiado());
				if (personaBBDD != null) {
					persona.setApellido1RazonSocial(personaBBDD.getApellido1RazonSocial());
				}
			}

			persona.setApellido2(repreAdquiriente.getAPELLIDO2REPRESENTANTEADQUIRIENTE());
			persona.setNombre(repreAdquiriente.getNOMBREREPRESENTANTEADQUIRIENTE());
			persona.setSexo(repreAdquiriente.getSEXOREPRESENTANTEADQUIRIENTE());
			String anagrama = utilidadesAnagrama.obtenerAnagramaFiscal(persona.getApellido1RazonSocial(), persona.getId().getNif());
			if (anagrama != null && !anagrama.isEmpty()) {
				persona.setAnagrama(anagrama);
			}
			persona.setEstado(new Long(1));
			interviniente.setPersona(persona);
		}
		return interviniente;
	}

	private IntervinienteTraficoVO convertirRepreTitularGAtoVO(DATOSREPRESENTANTE repreTitular, ContratoVO contrato) {
		IntervinienteTraficoVO interviniente = null;
		if (repreTitular != null && StringUtils.isNotBlank(repreTitular.getDNIREPRESENTANTE())) {
			interviniente = new IntervinienteTraficoVO();
			IntervinienteTraficoPK idInterviniente = new IntervinienteTraficoPK();
			idInterviniente.setNumColegiado(contrato.getColegiado().getNumColegiado());
			idInterviniente.setNif(repreTitular.getDNIREPRESENTANTE().toUpperCase());
			idInterviniente.setTipoInterviniente(TipoInterviniente.RepresentanteTitular.getValorEnum());
			interviniente.setId(idInterviniente);
			interviniente.setConceptoRepre(repreTitular.getCONCEPTOREPRESENTANTE());

			PersonaVO persona = new PersonaVO();
			PersonaPK idPersona = new PersonaPK();
			idPersona.setNif(repreTitular.getDNIREPRESENTANTE().toUpperCase());
			idPersona.setNumColegiado(contrato.getColegiado().getNumColegiado());
			persona.setId(idPersona);

			if (StringUtils.isNotBlank(repreTitular.getAPELLIDO1RAZONSOCIALREPRESENTANTE())) {
				persona.setApellido1RazonSocial(repreTitular.getAPELLIDO1RAZONSOCIALREPRESENTANTE().toUpperCase());
			} else {
				PersonaVO personaBBDD = servicioPersona.getPersonaVO(persona.getId().getNif(), persona.getId().getNumColegiado());
				if (personaBBDD != null) {
					persona.setApellido1RazonSocial(personaBBDD.getApellido1RazonSocial());
				}
			}

			persona.setApellido2(repreTitular.getAPELLIDO2REPRESENTANTE());
			persona.setNombre(repreTitular.getNOMBREREPRESENTANTE());
			persona.setSexo(repreTitular.getSEXOREPRESENTANTE());

			setDatosPersona(persona);

			persona.setEstado(new Long(1));
			interviniente.setPersona(persona);
		}
		return interviniente;
	}

	private IntervinienteTraficoVO convertirTitularGAtoVO(DATOSTITULAR titular, ContratoVO contrato) {
		IntervinienteTraficoVO interviniente = null;
		if (titular != null && StringUtils.isNotBlank(titular.getDNITITULAR())) {
			interviniente = new IntervinienteTraficoVO();
			IntervinienteTraficoPK idInterviniente = new IntervinienteTraficoPK();
			idInterviniente.setNumColegiado(contrato.getColegiado().getNumColegiado());
			idInterviniente.setNif(titular.getDNITITULAR().toUpperCase());
			idInterviniente.setTipoInterviniente(TipoInterviniente.Titular.getValorEnum());
			interviniente.setId(idInterviniente);

			PersonaVO persona = new PersonaVO();
			PersonaPK idPersona = new PersonaPK();
			if (titular.getDNITITULAR() != null && !titular.getDNITITULAR().isEmpty()) {
				idPersona.setNif(titular.getDNITITULAR().toUpperCase());
			}
			idPersona.setNumColegiado(contrato.getColegiado().getNumColegiado());
			persona.setId(idPersona);

			if (StringUtils.isNotBlank(titular.getAPELLIDO1RAZONSOCIALTITULAR())) {
				persona.setApellido1RazonSocial(titular.getAPELLIDO1RAZONSOCIALTITULAR().toUpperCase());
			} else {
				PersonaVO personaBBDD = servicioPersona.getPersonaVO(persona.getId().getNif(), persona.getId().getNumColegiado());
				if (personaBBDD != null) {
					persona.setApellido1RazonSocial(personaBBDD.getApellido1RazonSocial());
				}
			}

			if (titular.getAPELLIDO2TITULAR() != null && !titular.getAPELLIDO2TITULAR().isEmpty()) {
				persona.setApellido2(titular.getAPELLIDO2TITULAR().toUpperCase());
			}
			if (titular.getNOMBRETITULAR() != null && !titular.getNOMBRETITULAR().isEmpty()) {
				persona.setNombre(titular.getNOMBRETITULAR().toUpperCase());
			}
			if (StringUtils.isNotBlank(titular.getFECHANACIMIENTOTITULAR())) {
				persona.setFechaNacimiento(utilesFecha.formatoFecha("dd/MM/yyyy", titular.getFECHANACIMIENTOTITULAR()));
			}

			persona.setSexo(titular.getSEXOTITULAR());

			setDatosPersona(persona);

			persona.setEstado(new Long(1));
			interviniente.setPersona(persona);

			if (StringUtils.isNotBlank(titular.getPROVINCIATITULAR())) {
				DireccionVO direccion = new DireccionVO();

				direccion.setIdProvincia(titular.getPROVINCIATITULAR());
				direccion.setIdMunicipio(titular.getMUNICIPIOTITULAR());
				direccion.setIdTipoVia(titular.getTIPOVIATITULAR());
				if (titular.getCALLEDIRECCIONTITULAR() != null && !titular.getCALLEDIRECCIONTITULAR().isEmpty()) {
					direccion.setNombreVia(titular.getCALLEDIRECCIONTITULAR());
					direccion.setViaSineditar(utilidadesConversiones.getViaSinEditar(direccion.getNombreVia()));
				}
				direccion.setNumero(titular.getNUMERODIRECCIONTITULAR());
				direccion.setCodPostal(titular.getCODIGOPOSTALTITULAR());
				if (titular.getPUEBLOTITULAR() != null) {
					direccion.setPueblo(titular.getPUEBLOTITULAR().toUpperCase());
				}
				direccion.setLetra(titular.getLETRADIRECCIONTITULAR());
				direccion.setEscalera(titular.getESCALERADIRECCIONTITULAR());
				direccion.setBloque(titular.getBLOQUEDIRECCIONTITULAR());
				direccion.setPlanta(titular.getPISODIRECCIONTITULAR());
				direccion.setPuerta(titular.getPUERTADIRECCIONTITULAR());
				if (StringUtils.isNotBlank(titular.getKMDIRECCIONTITULAR())) {
					try {
						direccion.setKm(new BigDecimal(titular.getKMDIRECCIONTITULAR()));
					} catch (Exception e) {
						direccion.setKm(null);
					}
				}
				if (StringUtils.isNotBlank(titular.getHMDIRECCIONTITULAR())) {
					try {
						direccion.setHm(new BigDecimal(titular.getHMDIRECCIONTITULAR()));
					} catch (Exception e) {
						direccion.setKm(null);
					}
				}
				direccion.setAsignarPrincipal(titular.getDIRECCIONACTIVATITULAR());

				interviniente.setDireccion(direccion);
			}
		}
		return interviniente;
	}

	private IntervinienteTraficoVO convertirAdquirienteGAtoVO(DATOSADQUIRIENTE adquirente, ContratoVO contrato) {
		IntervinienteTraficoVO interviniente = null;
		if (adquirente != null && StringUtils.isNotBlank(adquirente.getDNIADQUIRIENTE())) {
			interviniente = new IntervinienteTraficoVO();
			IntervinienteTraficoPK idInterviniente = new IntervinienteTraficoPK();
			idInterviniente.setNumColegiado(contrato.getColegiado().getNumColegiado());
			idInterviniente.setNif(adquirente.getDNIADQUIRIENTE().toUpperCase());
			idInterviniente.setTipoInterviniente(TipoInterviniente.Adquiriente.getValorEnum());
			interviniente.setId(idInterviniente);

			PersonaVO persona = new PersonaVO();
			PersonaPK idPersona = new PersonaPK();
			if (adquirente.getDNIADQUIRIENTE() != null && !adquirente.getDNIADQUIRIENTE().isEmpty()) {
				idPersona.setNif(adquirente.getDNIADQUIRIENTE().toUpperCase());
			}
			idPersona.setNumColegiado(contrato.getColegiado().getNumColegiado());
			persona.setId(idPersona);

			if (StringUtils.isNotBlank(adquirente.getAPELLIDO1RAZONSOCIALADQUIRIENTE())) {
				persona.setApellido1RazonSocial(adquirente.getAPELLIDO1RAZONSOCIALADQUIRIENTE().toUpperCase());
			} else {
				PersonaVO personaBBDD = servicioPersona.getPersonaVO(persona.getId().getNif(), persona.getId().getNumColegiado());
				if (personaBBDD != null) {
					persona.setApellido1RazonSocial(personaBBDD.getApellido1RazonSocial());
				}
			}

			if (adquirente.getAPELLIDO2ADQUIRIENTE() != null && !adquirente.getAPELLIDO2ADQUIRIENTE().isEmpty()) {
				persona.setApellido2(adquirente.getAPELLIDO2ADQUIRIENTE());
			}
			if (adquirente.getNOMBREADQUIRIENTE() != null && !adquirente.getNOMBREADQUIRIENTE().isEmpty()) {
				persona.setNombre(adquirente.getNOMBREADQUIRIENTE());
			}
			if (StringUtils.isNotBlank(adquirente.getFECHANACIMIENTOADQUIRIENTE())) {
				persona.setFechaNacimiento(utilesFecha.formatoFecha("dd/MM/yyyy", adquirente.getFECHANACIMIENTOADQUIRIENTE()));
			}

			persona.setSexo(adquirente.getSEXOADQUIRIENTE());

			setDatosPersona(persona);

			persona.setEstado(new Long(1));
			interviniente.setPersona(persona);

			if (StringUtils.isNotBlank(adquirente.getPROVINCIAADQUIRIENTE())) {
				DireccionVO direccion = new DireccionVO();
				direccion.setIdProvincia(adquirente.getPROVINCIAADQUIRIENTE());
				direccion.setIdMunicipio(adquirente.getMUNICIPIOADQUIRIENTE());
				direccion.setIdTipoVia(adquirente.getTIPOVIAADQUIRIENTE());
				if (adquirente.getCALLEDIRECCIONADQUIRIENTE() != null && !adquirente.getCALLEDIRECCIONADQUIRIENTE().isEmpty()) {
					direccion.setNombreVia(adquirente.getCALLEDIRECCIONADQUIRIENTE());
					direccion.setViaSineditar(utilidadesConversiones.getViaSinEditar(direccion.getNombreVia()));
				}
				direccion.setNumero(adquirente.getNUMERODIRECCIONADQUIRIENTE());
				direccion.setCodPostal(adquirente.getCODIGOPOSTALADQUIRIENTE());
				if (adquirente.getPUEBLOADQUIRIENTE() != null) {
					direccion.setPueblo(adquirente.getPUEBLOADQUIRIENTE().toUpperCase());
				}
				direccion.setLetra(adquirente.getLETRADIRECCIONADQUIRIENTE());
				direccion.setEscalera(adquirente.getESCALERADIRECCIONADQUIRIENTE());
				direccion.setBloque(adquirente.getBLOQUEDIRECCIONADQUIRIENTE());
				direccion.setPlanta(adquirente.getPISODIRECCIONADQUIRIENTE());
				direccion.setPuerta(adquirente.getPUERTADIRECCIONADQUIRIENTE());
				if (StringUtils.isNotBlank(adquirente.getKMDIRECCIONADQUIRIENTE())) {
					try {
						direccion.setKm(new BigDecimal(adquirente.getKMDIRECCIONADQUIRIENTE()));
					} catch (Exception e) {
						direccion.setKm(null);
					}
				}
				if (StringUtils.isNotBlank(adquirente.getHMDIRECCIONADQUIRIENTE())) {
					try {
						direccion.setHm(new BigDecimal(adquirente.getHMDIRECCIONADQUIRIENTE()));
					} catch (Exception e) {
						direccion.setKm(null);
					}
				}
				direccion.setAsignarPrincipal(adquirente.getDIRECCIONACTIVAADQUIRIENTE());

				interviniente.setDireccion(direccion);
			}
		}
		return interviniente;
	}

	private VehiculoVO convertirVehiculoGAtoVO(DATOSVEHICULO vehiculoGa, ContratoVO contrato) {
		VehiculoVO vehiculo = new VehiculoVO();
		vehiculo.setNumColegiado(contrato.getColegiado().getNumColegiado());
		if (vehiculoGa.getMATRICULA() != null && !vehiculoGa.getMATRICULA().isEmpty()) {
			vehiculo.setMatricula(vehiculoGa.getMATRICULA().replace(" ", "").replace("-", "").replace("_", ""));
		}
		if (vehiculoGa.getNUMEROBASTIDOR() != null && !vehiculoGa.getNUMEROBASTIDOR().isEmpty()) {
			vehiculo.setBastidor(vehiculoGa.getNUMEROBASTIDOR().toUpperCase());
		}
		vehiculo.setFechaMatriculacion(utilesFecha.formatoFecha("dd/MM/yyyy", vehiculoGa.getFECHAMATRICULACION()));
		vehiculo.setTipoVehiculo(vehiculoGa.getTIPOVEHICULO());
		vehiculo.setPesoMma(vehiculoGa.getPESOMMA());
		vehiculo.setIdServicio(vehiculoGa.getSERVICIO());

		if (StringUtils.isNotBlank(vehiculoGa.getPROVINCIAVEHICULO())) {
			DireccionVO direccion = new DireccionVO();
			direccion.setIdProvincia(vehiculoGa.getPROVINCIAVEHICULO());
			direccion.setIdMunicipio(vehiculoGa.getMUNICIPIOVEHICULO());
			direccion.setIdTipoVia(vehiculoGa.getTIPOVIAVEHICULO());
			if (vehiculoGa.getCALLEDIRECCIONVEHICULO() != null && !vehiculoGa.getCALLEDIRECCIONVEHICULO().isEmpty()) {
				direccion.setNombreVia(vehiculoGa.getCALLEDIRECCIONVEHICULO());
				direccion.setViaSineditar(utilidadesConversiones.getViaSinEditar(direccion.getNombreVia()));
			}
			direccion.setNumero(vehiculoGa.getNUMERODIRECCIONVEHICULO());
			direccion.setCodPostal(vehiculoGa.getCODIGOPOSTALVEHICULO());
			if (vehiculoGa.getPUEBLOVEHICULO() != null) {
				direccion.setPueblo(vehiculoGa.getPUEBLOVEHICULO().toUpperCase());
			}
			direccion.setLetra(vehiculoGa.getLETRADIRECCIONVEHICULO());
			direccion.setEscalera(vehiculoGa.getESCALERADIRECCIONVEHICULO());
			direccion.setBloque(vehiculoGa.getBLOQUEDIRECCIONVEHICULO());
			direccion.setPlanta(vehiculoGa.getPISODIRECCIONVEHICULO());
			direccion.setPuerta(vehiculoGa.getPUERTADIRECCIONVEHICULO());
			if (StringUtils.isNotBlank(vehiculoGa.getKMDIRECCIONVEHICULO())) {
				try {
					direccion.setKm(new BigDecimal(vehiculoGa.getKMDIRECCIONVEHICULO()));
				} catch (Exception e) {
					direccion.setKm(null);
				}
			}
			if (StringUtils.isNotBlank(vehiculoGa.getHMDIRECCIONVEHICULO())) {
				try {
					direccion.setHm(new BigDecimal(vehiculoGa.getHMDIRECCIONVEHICULO()));
				} catch (Exception e) {
					direccion.setKm(null);
				}
			}
			vehiculo.setDireccion(direccion);
		}
		return vehiculo;
	}

	private TramiteTrafBajaVO convertirTramiteFormatoOegam2BajaToVO(String numColegiadoImportacion, BAJA baja, ContratoVO contrato, Boolean tienePermisoAdmin, String tipoCreacion, Long idUsuario) {
		TramiteTrafBajaVO tramite = new TramiteTrafBajaVO();
		tramite.setContrato(contrato);
		tramite.setNumExpediente(null);
		tramite.setNumColegiado(contrato.getColegiado().getNumColegiado());
		tramite.setRefPropia(baja.getREFERENCIAPROPIA());
		tramite.setYbestado(BigDecimal.ZERO);
		tramite.setFechaAlta(utilesFecha.getFechaActualDesfaseBBDD());
		tramite.setFechaUltModif(tramite.getFechaAlta());
		tramite.setAnotaciones(baja.getOBSERVACIONES());
		tramite.setTipoTramite(TipoTramiteTrafico.Baja.getValorEnum());
		UsuarioVO usuario = new UsuarioVO();
		usuario.setIdUsuario(idUsuario.longValue());
		tramite.setUsuario(usuario);
		if (baja.getDATOSPAGOPRESENTACION().getFECHAPRESENTACION() != null && !baja.getDATOSPAGOPRESENTACION().getFECHAPRESENTACION().isEmpty()) {
			tramite.setFechaPresentacion(utilesFecha.formatoFecha("dd/MM/yyyy", baja.getDATOSPAGOPRESENTACION().getFECHAPRESENTACION()));
		} else {
			tramite.setFechaPresentacion(new Date());
		}
		tramite.setEstado(new BigDecimal(EstadoTramiteTrafico.Iniciado.getValorEnum()));
		tramite.setCema(baja.getDATOSPAGOPRESENTACION().getCEMA());
		tramite.setMotivoBaja(baja.getMOTIVOBAJA());
		if (StringUtils.isNotBlank(baja.getPERMISOCIRCULACION())) {
			tramite.setPermisoCircu(baja.getPERMISOCIRCULACION().toUpperCase());
		} else {
			tramite.setPermisoCircu("NO");
		}
		if (StringUtils.isNotBlank(baja.getTARJETAINSPECCIONTECNICA())) {
			tramite.setTarjetaInspeccion(baja.getTARJETAINSPECCIONTECNICA().toUpperCase());
		} else {
			tramite.setTarjetaInspeccion("NO");
		}
		if (StringUtils.isNotBlank(baja.getDECLARACIONJURADAEXTRAVIO())) {
			tramite.setDeclaracionJuradaExtravio(baja.getDECLARACIONJURADAEXTRAVIO().toUpperCase());
		} else {
			tramite.setDeclaracionJuradaExtravio("NO");
		}
		if (StringUtils.isNotBlank(baja.getBAJAIMVTM())) {
			tramite.setBajaImpMunicipal(baja.getBAJAIMVTM().toUpperCase());
		} else {
			tramite.setBajaImpMunicipal("NO");
		}
		if (StringUtils.isNotBlank(baja.getJUSTIFICANTEDENUNCIA())) {
			tramite.setJustificanteDenuncia(baja.getJUSTIFICANTEDENUNCIA().toUpperCase());
		} else {
			tramite.setJustificanteDenuncia("NO");
		}
		if (StringUtils.isNotBlank(baja.getDOCUMENTOPROPIEDAD())) {
			tramite.setPropiedadDesguace(baja.getDOCUMENTOPROPIEDAD().toUpperCase());
		} else {
			tramite.setPropiedadDesguace("NO");
		}
		if (StringUtils.isNotBlank(baja.getJUSTIFICANTEIMVTM())) {
			tramite.setPagoImpMunicipal(baja.getJUSTIFICANTEIMVTM().toUpperCase());
		} else {
			tramite.setPagoImpMunicipal("NO");
		}
		if (StringUtils.isNotBlank(baja.getDECLARACIONNOENTREGACATV())) {
			tramite.setDeclaracionNoEntregaCatV(baja.getDECLARACIONNOENTREGACATV().equalsIgnoreCase("SI") ? Boolean.TRUE : Boolean.FALSE);
		} else {
			tramite.setDeclaracionNoEntregaCatV(Boolean.FALSE);
		}
		if (StringUtils.isNotBlank(baja.getDECLARACIONCARTADGT10ANIOS())) {
			tramite.setCartaDGTVehiculoMasDiezAnios(baja.getDECLARACIONCARTADGT10ANIOS().equalsIgnoreCase("SI") ? Boolean.TRUE : Boolean.FALSE);
		} else {
			tramite.setCartaDGTVehiculoMasDiezAnios(Boolean.FALSE);
		}
		if (StringUtils.isNotBlank(baja.getCERTIFICADOMEDIOAMBIENTAL())) {
			tramite.setCertificadoMedioambiental(baja.getCERTIFICADOMEDIOAMBIENTAL().equalsIgnoreCase("SI") ? Boolean.TRUE : Boolean.FALSE);
		} else {
			tramite.setCertificadoMedioambiental(Boolean.FALSE);
		}
		if (StringUtils.isNotBlank(baja.getDECLARACIONNORESIDUO())) {
			tramite.setDeclaracionResiduo(baja.getDECLARACIONNORESIDUO().toUpperCase());
		} else {
			tramite.setDeclaracionResiduo("NO");
		}
		if (StringUtils.isNotBlank(baja.getDECLARACIONNOENTREGACATV())) {
			tramite.setDeclaracionNoEntregaCatV(baja.getDECLARACIONNOENTREGACATV().equalsIgnoreCase("SI") ? Boolean.TRUE : Boolean.FALSE);
		} else {
			tramite.setDeclaracionNoEntregaCatV(Boolean.FALSE);
		}
		if (baja.getDATOSPAGOPRESENTACION().getCODIGOTASA() != null && !baja.getDATOSPAGOPRESENTACION().getCODIGOTASA().isEmpty()) {
			if (tienePermisoAdmin) {
				if (contrato.getColegiado().getNumColegiado().equals(numColegiadoImportacion)) {
					TasaVO tasa = new TasaVO();
					tasa.setCodigoTasa(baja.getDATOSPAGOPRESENTACION().getCODIGOTASA());
					tramite.setTasa(tasa);
				}
			} else {
				TasaVO tasa = new TasaVO();
				tasa.setCodigoTasa(baja.getDATOSPAGOPRESENTACION().getCODIGOTASA());
				tramite.setTasa(tasa);
			}
		}
		tramite.setJefaturaTrafico(contrato.getJefaturaTrafico());
		tramite.setIdTipoCreacion(new BigDecimal(tipoCreacion));
		return tramite;

	}

	private PersonaVO setDatosPersona(PersonaVO persona) {
		int tipo = utilidadesNIFValidator.isValidDniNieCif(persona.getId().getNif().toUpperCase());
		if (tipo >= 0) {
			if (tipo == utilidadesNIFValidator.FISICA) {
				String anagrama = utilidadesAnagrama.obtenerAnagramaFiscal(persona.getApellido1RazonSocial(), persona.getId().getNif());
				if (anagrama != null && !anagrama.isEmpty()) {
					persona.setAnagrama(anagrama);
				}
				persona.setTipoPersona(TipoPersona.Fisica.getValorEnum());
				persona.setSubtipo(null);
			} else {
				persona.setSexo(SexoPersona.Juridica.getNombreEnum());
				persona.setApellido2(null);
				persona.setAnagrama(null);
				persona.setFechaNacimiento(null);
				persona.setTipoPersona(TipoPersona.Juridica.getValorEnum());
				if ((persona.getSubtipo() == null || persona.getSubtipo().isEmpty()) && tipo == utilidadesNIFValidator.JURIDICA_PUBLICA) {
					persona.setSubtipo(SubtipoPersona.Publica.getNombreEnum());
				} else if ((persona.getSubtipo() == null || persona.getSubtipo().isEmpty()) && tipo == utilidadesNIFValidator.JURIDICA_PRIVADA) {
					persona.setSubtipo(SubtipoPersona.Privada.getNombreEnum());
				}
			}
		}
		return persona;
	}

}
