package org.gestoresmadrid.oegam2comun.modeloCAYC.model.service.impl;

import java.io.File;
import java.math.BigDecimal;
import java.util.Date;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import org.gestoresmadrid.oegam2comun.arrendatarios.model.service.ServicioArrendatario;
import org.gestoresmadrid.oegam2comun.arrendatarios.view.beans.ResultConsultaArrendatarioBean;
import org.gestoresmadrid.oegam2comun.arrendatarios.view.dto.ArrendatarioDto;
import org.gestoresmadrid.oegam2comun.conductor.model.service.ServicioConductor;
import org.gestoresmadrid.oegam2comun.conductor.view.beans.ResultConsultaConductorBean;
import org.gestoresmadrid.oegam2comun.conductor.view.dto.ConductorDto;
import org.gestoresmadrid.oegam2comun.contrato.model.service.ServicioContrato;
import org.gestoresmadrid.oegam2comun.modeloCAYC.AA.XML.FORMATOAltaArrendamiento;
import org.gestoresmadrid.oegam2comun.modeloCAYC.AA.XML.FORMATOAltaArrendamiento.AltaArrendamiento;
import org.gestoresmadrid.oegam2comun.modeloCAYC.AC.XML.FORMATOAltaConductor;
import org.gestoresmadrid.oegam2comun.modeloCAYC.AC.XML.FORMATOAltaConductor.AltaConductor;
import org.gestoresmadrid.oegam2comun.modeloCAYC.MA.XML.FORMATOModiArrendamiento;
import org.gestoresmadrid.oegam2comun.modeloCAYC.MA.XML.FORMATOModiArrendamiento.ModiArrendamiento;
import org.gestoresmadrid.oegam2comun.modeloCAYC.MC.XML.FORMATOModiConductor;
import org.gestoresmadrid.oegam2comun.modeloCAYC.MC.XML.FORMATOModiConductor.ModiConductor;
import org.gestoresmadrid.oegam2comun.modeloCAYC.model.service.ServicioModeloCAYC;
import org.gestoresmadrid.oegam2comun.modeloCAYC.model.service.ServicioResultadoModeloCAYC;
import org.gestoresmadrid.oegam2comun.modeloCAYC.view.bean.ResultBeanTotal;
import org.gestoresmadrid.oegamComun.contrato.view.dto.ContratoDto;
import org.gestoresmadrid.oegamComun.direccion.view.dto.DireccionDto;
import org.gestoresmadrid.oegamComun.persona.view.dto.PersonaDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import utilidades.estructuras.Fecha;
import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;

@Service(value = "servicioModeloCAYC")
public class ServicioModeloCAYCImpl implements ServicioModeloCAYC {

	private static final long serialVersionUID = -3483676133746336005L;
	private static final ILoggerOegam log = LoggerOegam.getLogger(ServicioModeloCAYCImpl.class);
	public static String ERROR_IMPORTAR_FICHERO = "Error al importar el fichero";
	public static String ERROR_VALIDAR_FICHERO = "Error al validar el fichero";

	@Autowired
	private ServicioResultadoModeloCAYC servicioResultadoModeloCAYC;
	@Autowired
	private ServicioArrendatario servicioArrendatario;
	@Autowired
	private ServicioConductor servicioConductor;
	@Autowired
	private ServicioContrato servicioContrato;

	@Override
	public ResultBeanTotal importarModeloCAYC(File fichero, String idContrato, BigDecimal idUsuario, String tipo) {
		ResultBeanTotal resultado = new ResultBeanTotal();

		// Dependiendo del tipo que se ha seleccionado por el usuario se hace un
		// tipo de importación diferente
		// Realmente lo unico que cambia es el tipo y el servicio al que se llama
		// existe mucho codigo duplicado, pero para futuros cambios, validaciones
		// diferene o formatos diferentes de ficheros
		// se dejará así
		switch (tipo) {
			case "AA": // Alta de arrendamiento
				resultado = importarModeloCAYC_AA(fichero, idContrato, idUsuario);
				break;
			case "MA": // Modificacion de arrendamientos
				resultado = importarModeloCAYC_MA(fichero, idContrato, idUsuario);
				break;
			case "AC": // Alta Conductor habitual
				resultado = importarModeloCAYC_AC(fichero, idContrato, idUsuario);
				break;
			case "MC": // Modificacion de conductor habitual
				resultado = importarModeloCAYC_MC(fichero, idContrato, idUsuario);
				break;
		}
		return resultado;
	}

	public ResultBeanTotal importarModeloCAYC_AA(File fichero, String idContrato, BigDecimal idUsuario) {
		ResultBeanTotal resultado = new ResultBeanTotal();
		int guardadosMal = 0; // Utilizado para el informe de pantalla
		int guardadosBien = 0; // Utilizado para el informe de pantalla
		int totalImportados = 0; // Utilizado para el informe de pantalla
		ContratoDto contrato = servicioContrato.getContratoDto(new BigDecimal(idContrato));
		String numColegiado = contrato.getColegiadoDto().getNumColegiado();
		JAXBContext jc = null;
		try {
			jc = JAXBContext.newInstance(FORMATOAltaArrendamiento.class);
			Unmarshaller unmarshaller = jc.createUnmarshaller();
			FORMATOAltaArrendamiento remesa = (FORMATOAltaArrendamiento) unmarshaller.unmarshal(fichero);
			// Validaciones obligatorias para poder iniciar la importacion
			if (remesa.getCABECERA() == null) {
				resultado.setError(true);
				resultado.addMensajeALista(ERROR_VALIDAR_FICHERO + " falta cabecera");
				return resultado;
			}
			if (remesa.getAltaArrendamiento() == null) {
				resultado.setError(true);
				resultado.addMensajeALista(ERROR_VALIDAR_FICHERO + " es necesario minimo un tramite");
				return resultado;
			}
			if (remesa.getCABECERA().getDATOSGESTORIA().getPROFESIONAL().compareTo(numColegiado) != 0) {
				resultado.setError(true);
				resultado.addMensajeALista(ERROR_VALIDAR_FICHERO + " Contrato no valido");
				return resultado;
			}
			// Fin de la validaciones obligatorias para realizar la imporación
			// Recorro la lista
			for (AltaArrendamiento arrendamientodatos : remesa.getAltaArrendamiento()) {
				ArrendatarioDto arrendatarioDto = new ArrendatarioDto();
				// Datos arrendamiento
				Fecha fechafin = new Fecha();
				Fecha fechaini = new Fecha();
				Date fechaAlta = new Date();
				try {
					int dia = arrendamientodatos.getDatosArrendamiento().getFechaFin().getDay();
					fechafin.setDia(Integer.toString(dia));
					int mes = arrendamientodatos.getDatosArrendamiento().getFechaFin().getMonth();
					fechafin.setMes(Integer.toString(mes));
					int anio = arrendamientodatos.getDatosArrendamiento().getFechaFin().getYear();
					fechafin.setAnio(Integer.toString(anio));
					arrendatarioDto.setFechaFin(fechafin);
				} catch (Exception e) {

				}
				try {
					int dia = arrendamientodatos.getDatosArrendamiento().getFechaIni().getDay();
					fechaini.setDia(Integer.toString(dia));
					int mes = arrendamientodatos.getDatosArrendamiento().getFechaIni().getMonth();
					fechaini.setMes(Integer.toString(mes));
					int anio = arrendamientodatos.getDatosArrendamiento().getFechaIni().getYear();
					fechaini.setAnio(Integer.toString(anio));
					arrendatarioDto.setFechaIni(fechaini);
				} catch (Exception e) {

				}
				arrendatarioDto.setFechaAlta(fechaAlta);
				// Datos Vehiculo
				arrendatarioDto.setDoiVehiculo(arrendamientodatos.getDatosVehiculo().getDoiTitular());
				arrendatarioDto.setBastidor(arrendamientodatos.getDatosVehiculo().getBastidor());
				arrendatarioDto.setMatricula(arrendamientodatos.getDatosVehiculo().getMatricula());
				arrendatarioDto.setContrato(contrato);
				arrendatarioDto.setNif(contrato.getCif());
				arrendatarioDto.setNumColegiado(numColegiado);
				arrendatarioDto.setNumExpediente(new BigDecimal(contrato.getNumero()));
				PersonaDto persona = new PersonaDto();
				// Obligatorios
				persona.setNumColegiado(numColegiado);
				persona.setNif(arrendamientodatos.getDatosPersonaCompleta().getDoi());
				persona.setApellido1RazonSocial(arrendamientodatos.getDatosPersonaCompleta().getPrimerApellido());
				persona.setEstado("1");
				persona.setTipoPersona("FISICA");
				persona.setPoderesEnFicha(new BigDecimal(0));
				persona.setEstadoCivil(arrendamientodatos.getDatosPersonaCompleta().getEstadocivil());
				persona.setApellido2(arrendamientodatos.getDatosPersonaCompleta().getSegundoApellido());
				persona.setNombre(arrendamientodatos.getDatosPersonaCompleta().getNombre());
				DireccionDto direccionDto = new DireccionDto();
				// Obligatorios
				direccionDto.setIdDireccion(new BigDecimal(0));
				direccionDto.setIdProvincia(arrendamientodatos.getDatosDomicilio().getProvincia());
				direccionDto.setIdMunicipio(arrendamientodatos.getDatosDomicilio().getMunicipio());
				direccionDto.setNombreVia(arrendamientodatos.getDatosDomicilio().getVia());
				direccionDto.setIdTipoVia(arrendamientodatos.getDatosDomicilio().getTipoVia());
				direccionDto.setVia(arrendamientodatos.getDatosDomicilio().getVia());
				direccionDto.setNumero(arrendamientodatos.getDatosDomicilio().getNumero());
				direccionDto.setEscalera(arrendamientodatos.getDatosDomicilio().getEscalera());
				direccionDto.setPlanta(arrendamientodatos.getDatosDomicilio().getPlanta());
				direccionDto.setPortal(arrendamientodatos.getDatosDomicilio().getPortal());
				direccionDto.setPuerta(arrendamientodatos.getDatosDomicilio().getPuerta());
				direccionDto.setCodPostal(arrendamientodatos.getDatosDomicilio().getCp());
				direccionDto.setNombreProvincia(arrendamientodatos.getDatosDomicilio().getProvincia());
				direccionDto.setNombreMunicipio(arrendamientodatos.getDatosDomicilio().getMunicipio());
				direccionDto.setBloque(arrendamientodatos.getDatosDomicilio().getBloque());
				try {
					direccionDto.setKm(new BigDecimal(arrendamientodatos.getDatosDomicilio().getKm()));
				} catch (Exception e) {
				}
				try {
					direccionDto.setHm(new BigDecimal(arrendamientodatos.getDatosDomicilio().getHm()));
				} catch (Exception e) {
				}
				persona.setDireccionDto(direccionDto);
				arrendatarioDto.setPersona(persona);
				arrendatarioDto.setRespuesta("");
				arrendatarioDto.setSolicitud("");
				arrendatarioDto.setIdArrendatario(null);
				BigDecimal expediente = new BigDecimal(-1);
				arrendatarioDto.setNumExpediente(expediente);
				arrendatarioDto.setOperacion("AA");
				ResultConsultaArrendatarioBean resultado_tran = new ResultConsultaArrendatarioBean(false);
				try {
					resultado_tran = servicioArrendatario.guardarArrendatario(arrendatarioDto, idUsuario);
				} catch (Exception e) {
					resultado_tran.setError(true);
					resultado_tran.setMensaje("Error al guardar el arrendatario");
				}
				if (resultado_tran.getError()) {

					guardadosMal++;
					resultado.setError(true);
					resultado.addMensajeALista(
							arrendamientodatos.getDatosVehiculo().getDoiTitular() + " " + resultado_tran.getMensaje());
				} else {
					guardadosBien++;
				}
				totalImportados++;
			}
		} catch (JAXBException e) {
			log.error("Error validación del fichero, error:" + e.getMessage());
			resultado.setError(true);
			resultado.addMensajeALista(ERROR_VALIDAR_FICHERO + ":" + e.getMessage());
		}

		resultado.setGuardadosBien(guardadosBien);
		resultado.setGuardadosMal(guardadosMal);
		resultado.setTotalImportados(totalImportados);
		return resultado;
	}

	public ResultBeanTotal importarModeloCAYC_MA(File fichero, String idContrato, BigDecimal idUsuario) {
		ResultBeanTotal resultado = new ResultBeanTotal();
		int guardadosMal = 0; // Utilizado para el informe de pantalla
		int guardadosBien = 0; // Utilizado para el informe de pantalla
		int totalImportados = 0; // Utilizado para el informe de pantalla
		ContratoDto contrato = servicioContrato.getContratoDto(new BigDecimal(idContrato));
		String numColegiado = contrato.getColegiadoDto().getNumColegiado();
		JAXBContext jc = null;
		try {
			jc = JAXBContext.newInstance(FORMATOModiArrendamiento.class);
			Unmarshaller unmarshaller = jc.createUnmarshaller();
			FORMATOModiArrendamiento remesa = (FORMATOModiArrendamiento) unmarshaller.unmarshal(fichero);
			// Validaciones obligatorias para poder iniciar la importacion
			if (remesa.getCABECERA() == null) {
				resultado.setError(true);
				resultado.addMensajeALista(ERROR_VALIDAR_FICHERO + " falta cabecera");
				return resultado;
			}
			if (remesa.getModiArrendamiento() == null) {
				resultado.setError(true);
				resultado.addMensajeALista(ERROR_VALIDAR_FICHERO + " es necesario minimo un tramite");
				return resultado;
			}
			if (remesa.getCABECERA().getDATOSGESTORIA().getPROFESIONAL().compareTo(numColegiado) != 0) {
				resultado.setError(true);
				resultado.addMensajeALista(ERROR_VALIDAR_FICHERO + " Contrato no valido");
				return resultado;
			}
			// Fin de la validaciones obligatorias para realizar la imporación
			// Recorro la lista
			for (ModiArrendamiento arrendamientodatos : remesa.getModiArrendamiento()) {
				ArrendatarioDto arrendatarioDto = new ArrendatarioDto();
				// Datos arrendamiento
				Fecha fechafin = new Fecha();
				Fecha fechaini = new Fecha();
				Date fechaAlta = new Date();
				try {
					int dia = arrendamientodatos.getDatosArrendamiento().getFechaFin().getDay();
					fechafin.setDia(Integer.toString(dia));
					int mes = arrendamientodatos.getDatosArrendamiento().getFechaFin().getMonth();
					fechafin.setMes(Integer.toString(mes));
					int anio = arrendamientodatos.getDatosArrendamiento().getFechaFin().getYear();
					fechafin.setAnio(Integer.toString(anio));
					arrendatarioDto.setFechaFin(fechafin);
				} catch (Exception e) {
				}
				try {
					int dia = arrendamientodatos.getDatosArrendamiento().getFechaIni().getDay();
					fechaini.setDia(Integer.toString(dia));
					int mes = arrendamientodatos.getDatosArrendamiento().getFechaIni().getMonth();
					fechaini.setMes(Integer.toString(mes));
					int anio = arrendamientodatos.getDatosArrendamiento().getFechaIni().getYear();
					fechaini.setAnio(Integer.toString(anio));
					arrendatarioDto.setFechaIni(fechaini);
				} catch (Exception e) {

				}
				arrendatarioDto.setFechaAlta(fechaAlta);
				// Datos Vehiculo
				arrendatarioDto.setDoiVehiculo(arrendamientodatos.getDatosVehiculo().getDoiTitular());
				arrendatarioDto.setBastidor(arrendamientodatos.getDatosVehiculo().getBastidor());
				arrendatarioDto.setMatricula(arrendamientodatos.getDatosVehiculo().getMatricula());
				arrendatarioDto.setContrato(contrato);
				arrendatarioDto.setNif(contrato.getCif());
				arrendatarioDto.setNumColegiado(numColegiado);
				arrendatarioDto.setNumExpediente(new BigDecimal(contrato.getNumero()));
				PersonaDto persona = new PersonaDto();
				// Obligatorios
				persona.setNumColegiado(numColegiado);
				persona.setNif(arrendamientodatos.getDatosPersonaCompleta().getDoi());
				persona.setApellido1RazonSocial(arrendamientodatos.getDatosPersonaCompleta().getPrimerApellido());
				persona.setEstado("1");
				persona.setTipoPersona("FISICA");
				persona.setPoderesEnFicha(new BigDecimal(0));
				persona.setEstadoCivil(arrendamientodatos.getDatosPersonaCompleta().getEstadocivil());
				persona.setApellido2(arrendamientodatos.getDatosPersonaCompleta().getSegundoApellido());
				persona.setNombre(arrendamientodatos.getDatosPersonaCompleta().getNombre());
				DireccionDto direccionDto = new DireccionDto();
				// Obligatorios
				direccionDto.setIdDireccion(new BigDecimal(0));
				direccionDto.setIdProvincia(arrendamientodatos.getDatosDomicilio().getProvincia());
				direccionDto.setIdMunicipio(arrendamientodatos.getDatosDomicilio().getMunicipio());
				direccionDto.setNombreVia(arrendamientodatos.getDatosDomicilio().getVia());
				direccionDto.setIdTipoVia(arrendamientodatos.getDatosDomicilio().getTipoVia());
				direccionDto.setVia(arrendamientodatos.getDatosDomicilio().getVia());
				direccionDto.setNumero(arrendamientodatos.getDatosDomicilio().getNumero());
				direccionDto.setEscalera(arrendamientodatos.getDatosDomicilio().getEscalera());
				direccionDto.setPlanta(arrendamientodatos.getDatosDomicilio().getPlanta());
				direccionDto.setPortal(arrendamientodatos.getDatosDomicilio().getPortal());
				direccionDto.setPuerta(arrendamientodatos.getDatosDomicilio().getPuerta());
				direccionDto.setCodPostal(arrendamientodatos.getDatosDomicilio().getCp());
				direccionDto.setNombreProvincia(arrendamientodatos.getDatosDomicilio().getProvincia());
				direccionDto.setNombreMunicipio(arrendamientodatos.getDatosDomicilio().getMunicipio());
				direccionDto.setBloque(arrendamientodatos.getDatosDomicilio().getBloque());
				try {
					direccionDto.setKm(new BigDecimal(arrendamientodatos.getDatosDomicilio().getKm()));
				} catch (Exception e) {
				}
				try {
					direccionDto.setHm(new BigDecimal(arrendamientodatos.getDatosDomicilio().getHm()));
				} catch (Exception e) {
				}
				persona.setDireccionDto(direccionDto);
				arrendatarioDto.setPersona(persona);
				arrendatarioDto.setRespuesta("");
				arrendatarioDto.setSolicitud("");
				arrendatarioDto.setIdArrendatario(null);
				BigDecimal expediente = new BigDecimal(-1);
				arrendatarioDto.setNumExpediente(expediente);
				arrendatarioDto.setOperacion("MA");
				ResultConsultaArrendatarioBean resultado_tran = new ResultConsultaArrendatarioBean(false);
				try {
					resultado_tran = servicioArrendatario.guardarArrendatario(arrendatarioDto, idUsuario);
				} catch (Exception e) {
					resultado_tran.setError(true);
					resultado_tran.setMensaje("Error al guardar el arrendatario");
				}

				if (resultado_tran.getError()) {
					guardadosMal++;
					resultado.setError(true);
					resultado.addMensajeALista(
							arrendamientodatos.getDatosVehiculo().getDoiTitular() + " " + resultado_tran.getMensaje());
				} else {
					guardadosBien++;
				}
				totalImportados++;
			}
		} catch (JAXBException e) {
			log.error("Error validación del fichero, error:" + e.getMessage());
			resultado.setError(true);
			resultado.addMensajeALista(ERROR_VALIDAR_FICHERO + ":" + e.getMessage());
		}

		resultado.setGuardadosBien(guardadosBien);
		resultado.setGuardadosMal(guardadosMal);
		resultado.setTotalImportados(totalImportados);
		return resultado;
	}

	public ResultBeanTotal importarModeloCAYC_AC(File fichero, String idContrato, BigDecimal idUsuario) {
		ResultBeanTotal resultado = new ResultBeanTotal();
		int guardadosMal = 0; // Utilizado para el informe de pantalla
		int guardadosBien = 0; // Utilizado para el informe de pantalla
		int totalImportados = 0; // Utilizado para el informe de pantalla
		ContratoDto contrato = servicioContrato.getContratoDto(new BigDecimal(idContrato));
		String numColegiado = contrato.getColegiadoDto().getNumColegiado();
		JAXBContext jc = null;
		try {
			jc = JAXBContext.newInstance(FORMATOAltaConductor.class);
			Unmarshaller unmarshaller = jc.createUnmarshaller();
			FORMATOAltaConductor remesa = (FORMATOAltaConductor) unmarshaller.unmarshal(fichero);
			// Validaciones obligatorias para poder iniciar la importacion
			if (remesa.getCABECERA() == null) {
				resultado.setError(true);
				resultado.addMensajeALista(ERROR_VALIDAR_FICHERO + " falta cabecera");
				return resultado;
			}
			if (remesa.getAltaConductor() == null) {
				resultado.setError(true);
				resultado.addMensajeALista(ERROR_VALIDAR_FICHERO + " es necesario minimo un tramite");
				return resultado;
			}
			if (remesa.getCABECERA().getDATOSGESTORIA().getPROFESIONAL().compareTo(numColegiado) != 0) {
				resultado.setError(true);
				resultado.addMensajeALista(ERROR_VALIDAR_FICHERO + " Contrato no valido");
				return resultado;
			}
			// Fin de la validaciones obligatorias para realizar la imporación
			// Recorro la lista
			for (AltaConductor conductordatos : remesa.getAltaConductor()) {
				ConductorDto conductorDto = new ConductorDto();
				// Datos arrendamiento
				Fecha fechafin = new Fecha();
				Fecha fechaini = new Fecha();
				Date fechaAlta = new Date();
				try {
					int dia = conductordatos.getDatosConductorHabitual().getFechaFin().getDay();
					fechafin.setDia(Integer.toString(dia));
					int mes = conductordatos.getDatosConductorHabitual().getFechaFin().getMonth();
					fechafin.setMes(Integer.toString(mes));
					int anio = conductordatos.getDatosConductorHabitual().getFechaFin().getYear();
					fechafin.setAnio(Integer.toString(anio));
					conductorDto.setFechaFin(fechafin);
				} catch (Exception e) {

				}
				try {
					int dia = conductordatos.getDatosConductorHabitual().getFechaIni().getDay();
					fechaini.setDia(Integer.toString(dia));
					int mes = conductordatos.getDatosConductorHabitual().getFechaIni().getMonth();
					fechaini.setMes(Integer.toString(mes));
					int anio = conductordatos.getDatosConductorHabitual().getFechaIni().getYear();
					fechaini.setAnio(Integer.toString(anio));
					conductorDto.setFechaIni(fechaini);
				} catch (Exception e) {
				}
				conductorDto.setFechaAlta(fechaAlta);
				// Datos Vehiculo
				conductorDto.setDoiVehiculo(conductordatos.getDatosConductorHabitual().getDoi());
				conductorDto.setBastidor(conductordatos.getDatosVehiculo().getBastidor());
				conductorDto.setMatricula(conductordatos.getDatosVehiculo().getMatricula());
				conductorDto.setContrato(contrato);
				if (conductordatos.getDatosConductorHabitual().getExisteConsentimiento() == null) {
					conductorDto.setConsentimiento(true);
				} else if (conductordatos.getDatosConductorHabitual().getExisteConsentimiento() != "1"
						&& conductordatos.getDatosConductorHabitual().getExisteConsentimiento() != "2") {
					conductorDto.setConsentimiento(true);
				}
				conductorDto.setNif(contrato.getCif());
				conductorDto.setNumColegiado(numColegiado);
				conductorDto.setNumExpediente(new BigDecimal(contrato.getNumero()));
				PersonaDto persona = new PersonaDto();
				// Obligatorios
				persona.setNumColegiado(numColegiado);
				persona.setNif(conductordatos.getDatosPersonaCompleta().getDoi());
				persona.setApellido1RazonSocial(conductordatos.getDatosPersonaCompleta().getPrimerApellido());
				persona.setEstado("1");
				persona.setTipoPersona("FISICA");
				persona.setPoderesEnFicha(new BigDecimal(0));
				persona.setEstadoCivil(conductordatos.getDatosPersonaCompleta().getEstadocivil());
				persona.setApellido2(conductordatos.getDatosPersonaCompleta().getSegundoApellido());
				persona.setNombre(conductordatos.getDatosPersonaCompleta().getNombre());
				DireccionDto direccionDto = new DireccionDto();
				// Obligatorios
				direccionDto.setIdDireccion(new BigDecimal(0));
				direccionDto.setIdProvincia(conductordatos.getDatosDomicilio().getProvincia());
				direccionDto.setIdMunicipio(conductordatos.getDatosDomicilio().getMunicipio());
				direccionDto.setNombreVia(conductordatos.getDatosDomicilio().getVia());
				direccionDto.setIdTipoVia(conductordatos.getDatosDomicilio().getTipoVia());
				direccionDto.setVia(conductordatos.getDatosDomicilio().getVia());
				direccionDto.setNumero(conductordatos.getDatosDomicilio().getNumero());
				direccionDto.setEscalera(conductordatos.getDatosDomicilio().getEscalera());
				direccionDto.setPlanta(conductordatos.getDatosDomicilio().getPlanta());
				direccionDto.setPortal(conductordatos.getDatosDomicilio().getPortal());
				direccionDto.setPuerta(conductordatos.getDatosDomicilio().getPuerta());
				direccionDto.setCodPostal(conductordatos.getDatosDomicilio().getCp());
				direccionDto.setNombreProvincia(conductordatos.getDatosDomicilio().getProvincia());
				direccionDto.setNombreMunicipio(conductordatos.getDatosDomicilio().getMunicipio());
				direccionDto.setBloque(conductordatos.getDatosDomicilio().getBloque());
				try {
					direccionDto.setKm(new BigDecimal(conductordatos.getDatosDomicilio().getKm()));
				} catch (Exception e) {
				}
				try {
					direccionDto.setHm(new BigDecimal(conductordatos.getDatosDomicilio().getHm()));
				} catch (Exception e) {
				}
				persona.setDireccionDto(direccionDto);
				conductorDto.setPersona(persona);
				conductorDto.setRespuesta("");
				conductorDto.setSolicitud("");
				conductorDto.setIdConductor(null);
				BigDecimal expediente = new BigDecimal(-1);
				conductorDto.setNumExpediente(expediente);
				conductorDto.setOperacion("AC");

				ResultConsultaConductorBean resultado_tran = new ResultConsultaConductorBean(false);
				try {
					resultado_tran = servicioConductor.guardarConductor(conductorDto, idUsuario);
				} catch (Exception e) {
					resultado_tran.setError(true);
					resultado_tran.setMensaje("Error al guardar el conductor");
				}

				if (resultado_tran.getError()) {
					guardadosMal++;
					resultado.setError(true);
					resultado.addMensajeALista(
							conductordatos.getDatosConductorHabitual().getDoi() + " " + resultado_tran.getMensaje());
				} else {
					guardadosBien++;
				}
				totalImportados++;
			}
		} catch (JAXBException e) {
			log.error("Error validación del fichero, error:" + e.getMessage());
			resultado.setError(true);
			resultado.addMensajeALista(ERROR_VALIDAR_FICHERO + ":" + e.getMessage());
		}
		resultado.setGuardadosBien(guardadosBien);
		resultado.setGuardadosMal(guardadosMal);
		resultado.setTotalImportados(totalImportados);
		return resultado;

	}

	public ResultBeanTotal importarModeloCAYC_MC(File fichero, String idContrato, BigDecimal idUsuario) {
		ResultBeanTotal resultado = new ResultBeanTotal();
		int guardadosMal = 0; // Utilizado para el informe de pantalla
		int guardadosBien = 0; // Utilizado para el informe de pantalla
		int totalImportados = 0; // Utilizado para el informe de pantalla
		ContratoDto contrato = servicioContrato.getContratoDto(new BigDecimal(idContrato));
		String numColegiado = contrato.getColegiadoDto().getNumColegiado();
		JAXBContext jc = null;
		try {
			jc = JAXBContext.newInstance(FORMATOModiConductor.class);
			Unmarshaller unmarshaller = jc.createUnmarshaller();
			FORMATOModiConductor remesa = (FORMATOModiConductor) unmarshaller.unmarshal(fichero);
			// Validaciones obligatorias para poder iniciar la importacion
			if (remesa.getCABECERA() == null) {
				resultado.setError(true);
				resultado.addMensajeALista(ERROR_VALIDAR_FICHERO + " falta cabecera");
				return resultado;
			}
			if (remesa.getModiConductor() == null) {
				resultado.setError(true);
				resultado.addMensajeALista(ERROR_VALIDAR_FICHERO + " es necesario minimo un tramite");
				return resultado;
			}
			if (remesa.getCABECERA().getDATOSGESTORIA().getPROFESIONAL().compareTo(numColegiado) != 0) {
				resultado.setError(true);
				resultado.addMensajeALista(ERROR_VALIDAR_FICHERO + " Contrato no valido");
				return resultado;
			}
			// Fin de la validaciones obligatorias para realizar la imporación
			// Recorro la lista
			for (ModiConductor conductordatos : remesa.getModiConductor()) {
				ConductorDto conductorDto = new ConductorDto();
				// Datos arrendamiento
				Fecha fechafin = new Fecha();
				Fecha fechaini = new Fecha();
				Date fechaAlta = new Date();
				try {
					int dia = conductordatos.getDatosConductorHabitual().getFechaFin().getDay();
					fechafin.setDia(Integer.toString(dia));
					int mes = conductordatos.getDatosConductorHabitual().getFechaFin().getMonth();
					fechafin.setMes(Integer.toString(mes));
					int anio = conductordatos.getDatosConductorHabitual().getFechaFin().getYear();
					fechafin.setAnio(Integer.toString(anio));
					conductorDto.setFechaFin(fechafin);
				} catch (Exception e) {
				}
				try {
					int dia = conductordatos.getDatosConductorHabitual().getFechaIni().getDay();
					fechaini.setDia(Integer.toString(dia));
					int mes = conductordatos.getDatosConductorHabitual().getFechaIni().getMonth();
					fechaini.setMes(Integer.toString(mes));
					int anio = conductordatos.getDatosConductorHabitual().getFechaIni().getYear();
					fechaini.setAnio(Integer.toString(anio));
					conductorDto.setFechaIni(fechaini);
				} catch (Exception e) {

				}
				conductorDto.setFechaAlta(fechaAlta);
				// Datos Vehiculo
				conductorDto.setDoiVehiculo(conductordatos.getDatosConductorHabitual().getDoi());
				conductorDto.setBastidor(conductordatos.getDatosVehiculo().getBastidor());
				conductorDto.setMatricula(conductordatos.getDatosVehiculo().getMatricula());
				conductorDto.setContrato(contrato);
				if (conductordatos.getDatosConductorHabitual().getExisteConsentimiento() == null) {
					conductorDto.setConsentimiento(true);
				} else if (conductordatos.getDatosConductorHabitual().getExisteConsentimiento() != "1"
						&& conductordatos.getDatosConductorHabitual().getExisteConsentimiento() != "2") {
					conductorDto.setConsentimiento(true);
				}
				conductorDto.setNif(contrato.getCif());
				conductorDto.setNumColegiado(numColegiado);
				conductorDto.setNumExpediente(new BigDecimal(contrato.getNumero()));
				PersonaDto persona = new PersonaDto();
				// Obligatorios
				persona.setNumColegiado(numColegiado);
				persona.setNif(conductordatos.getDatosPersonaCompleta().getDoi());
				persona.setApellido1RazonSocial(conductordatos.getDatosPersonaCompleta().getPrimerApellido());
				persona.setEstado("1");
				persona.setTipoPersona("FISICA");
				persona.setPoderesEnFicha(new BigDecimal(0));
				persona.setEstadoCivil(conductordatos.getDatosPersonaCompleta().getEstadocivil());
				persona.setApellido2(conductordatos.getDatosPersonaCompleta().getSegundoApellido());
				persona.setNombre(conductordatos.getDatosPersonaCompleta().getNombre());
				DireccionDto direccionDto = new DireccionDto();
				// Obligatorios
				direccionDto.setIdDireccion(new BigDecimal(0));
				direccionDto.setIdProvincia(conductordatos.getDatosDomicilio().getProvincia());
				direccionDto.setIdMunicipio(conductordatos.getDatosDomicilio().getMunicipio());
				direccionDto.setNombreVia(conductordatos.getDatosDomicilio().getVia());
				direccionDto.setIdTipoVia(conductordatos.getDatosDomicilio().getTipoVia());
				direccionDto.setVia(conductordatos.getDatosDomicilio().getVia());
				direccionDto.setNumero(conductordatos.getDatosDomicilio().getNumero());
				direccionDto.setEscalera(conductordatos.getDatosDomicilio().getEscalera());
				direccionDto.setPlanta(conductordatos.getDatosDomicilio().getPlanta());
				direccionDto.setPortal(conductordatos.getDatosDomicilio().getPortal());
				direccionDto.setPuerta(conductordatos.getDatosDomicilio().getPuerta());
				direccionDto.setCodPostal(conductordatos.getDatosDomicilio().getCp());
				direccionDto.setNombreProvincia(conductordatos.getDatosDomicilio().getProvincia());
				direccionDto.setNombreMunicipio(conductordatos.getDatosDomicilio().getMunicipio());
				direccionDto.setBloque(conductordatos.getDatosDomicilio().getBloque());
				try {
					direccionDto.setKm(new BigDecimal(conductordatos.getDatosDomicilio().getKm()));
				} catch (Exception e) {
				}
				try {
					direccionDto.setHm(new BigDecimal(conductordatos.getDatosDomicilio().getHm()));
				} catch (Exception e) {
				}
				persona.setDireccionDto(direccionDto);
				conductorDto.setPersona(persona);
				conductorDto.setRespuesta("");
				conductorDto.setSolicitud("");
				conductorDto.setIdConductor(null);
				BigDecimal expediente = new BigDecimal(-1);
				conductorDto.setNumExpediente(expediente);
				conductorDto.setOperacion("MC");
				ResultConsultaConductorBean resultado_tran = new ResultConsultaConductorBean(false);
				try {
					resultado_tran = servicioConductor.guardarConductor(conductorDto, idUsuario);
				} catch (Exception e) {
					resultado_tran.setError(true);
					resultado_tran.setMensaje("Error al guardar el conductor");
				}

				if (resultado_tran.getError()) {
					guardadosMal++;
					resultado.setError(true);
					resultado.addMensajeALista(
							conductordatos.getDatosConductorHabitual().getDoi() + " " + resultado_tran.getMensaje());
				} else {
					guardadosBien++;
				}
				totalImportados++;
			}
		} catch (JAXBException e) {
			log.error("Error validación del fichero, error:" + e.getMessage());
			resultado.setError(true);
			resultado.addMensajeALista(ERROR_VALIDAR_FICHERO + ":" + e.getMessage());
		}
		resultado.setGuardadosBien(guardadosBien);
		resultado.setGuardadosMal(guardadosMal);
		resultado.setTotalImportados(totalImportados);
		return resultado;
	}

	public ServicioResultadoModeloCAYC getServicioResultadoModeloCAYC() {
		return servicioResultadoModeloCAYC;
	}

	public void setServicioResultadoModeloCAYC(ServicioResultadoModeloCAYC servicioResultadoModeloCAYC) {
		this.servicioResultadoModeloCAYC = servicioResultadoModeloCAYC;
	}

}