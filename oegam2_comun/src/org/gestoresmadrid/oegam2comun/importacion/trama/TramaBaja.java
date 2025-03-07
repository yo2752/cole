package org.gestoresmadrid.oegam2comun.importacion.trama;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;

import org.gestoresmadrid.core.model.enumerados.TipoInterviniente;
import org.gestoresmadrid.oegam2comun.conversion.Conversiones;
import org.gestoresmadrid.oegam2comun.paises.model.service.ServicioPais;
import org.gestoresmadrid.oegam2comun.paises.view.dto.PaisDto;
import org.gestoresmadrid.oegam2comun.personas.model.service.ServicioPersona;
import org.gestoresmadrid.oegam2comun.trafico.view.dto.TramiteTrafBajaDto;
import org.gestoresmadrid.oegam2comun.vehiculo.model.service.ServicioVehiculo;
import org.gestoresmadrid.oegamComun.acceso.service.impl.UtilesColegiado;
import org.gestoresmadrid.oegamComun.direccion.view.dto.DireccionDto;
import org.gestoresmadrid.oegamComun.interviniente.view.dto.IntervinienteTraficoDto;
import org.gestoresmadrid.oegamComun.persona.view.dto.PersonaDto;
import org.gestoresmadrid.oegamComun.tasa.view.dto.TasaDto;
import org.gestoresmadrid.oegamComun.trafico.view.dto.JefaturaTraficoDto;
import org.gestoresmadrid.oegamComun.vehiculo.view.dto.VehiculoDto;
import org.gestoresmadrid.utilidades.components.UtilesFecha;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import escrituras.beans.ResultBean;
import general.utiles.Anagrama;
import trafico.utiles.constantes.ConstantesDGT;
import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;

@Component
public class TramaBaja {

	private static final ILoggerOegam log = LoggerOegam.getLogger(TramaBaja.class);

	private static String sinNumero = "SN";
	private static String TIPO_DGT_BLANCO = "23";
	
	@Autowired
	private Conversiones conversiones;
	
	@Autowired
	private ServicioVehiculo servicioVehiculo;
	
	@Autowired
	private ServicioPersona servicioPersona;
	
	@Autowired
	private ServicioPais servicioPais;
	
	@Autowired
	UtilesFecha utilesFecha;

	@Autowired
	private UtilesColegiado utilesColegiado;
	
	public ResultBean obtenerDto(String linea, String colegiadoCabecera) {
		ResultBean beanResult = new ResultBean();

		int pos = 0;
		Boolean error = false;
		String mensajeError = "";

		TramiteTrafBajaDto tramite = new TramiteTrafBajaDto();
		VehiculoDto vehiculo = new VehiculoDto();
		
		DireccionDto direccionVehiculo = new DireccionDto();
		IntervinienteTraficoDto adquiriente = new IntervinienteTraficoDto();
		IntervinienteTraficoDto titular = new IntervinienteTraficoDto();
		PersonaDto personaTitular = new PersonaDto();
		DireccionDto direccionTitular = new DireccionDto();
		IntervinienteTraficoDto representante = new IntervinienteTraficoDto();
		PersonaDto personaRepre = new PersonaDto();
		DireccionDto direccionRepre = new DireccionDto();

		titular.setTipoInterviniente(TipoInterviniente.Titular.getValorEnum());
		representante.setTipoInterviniente(TipoInterviniente.RepresentanteTitular.getValorEnum());
		adquiriente.setTipoInterviniente(TipoInterviniente.Adquiriente.getValorEnum());

		String valor = linea.substring(pos, pos + ConstantesDGT.TAM_TIPO_REG);
		pos = pos + ConstantesDGT.TAM_TIPO_REG;

		valor = linea.substring(pos, pos + ConstantesDGT.TAM_TASA);
		TasaDto tasa = new TasaDto();
		tasa.setCodigoTasa(valor.trim());
		tramite.setTasa(tasa);
		pos = pos + ConstantesDGT.TAM_TASA;

		valor = linea.substring(pos, pos + ConstantesDGT.TAM_FECHA_PRESENTACION);
		if (!"".equals(valor.trim()))
			tramite.setFechaPresentacion(utilesFecha.getFechaFracionada(aaaammddToTimestamp(valor.trim())));
		pos = pos + ConstantesDGT.TAM_FECHA_PRESENTACION;

		valor = linea.substring(pos, pos + ConstantesDGT.TAM_MATRICULA);
		if (valor != null && !valor.isEmpty()) {
			VehiculoDto vehiculoAux = servicioVehiculo.getVehiculoDto(null, colegiadoCabecera, valor.trim(), null, null, null);
			if (vehiculoAux != null) {
				vehiculo = vehiculoAux;
			}
		}
		vehiculo.setMatricula(valor.trim());
		pos = pos + ConstantesDGT.TAM_MATRICULA;

		valor = linea.substring(pos, pos + ConstantesDGT.TAM_FECHA_MATRICULACION);
		if (valor != null && !valor.trim().isEmpty()) {
			vehiculo.setFechaMatriculacion(utilesFecha.getFechaFracionada(aaaammddToTimestamp(valor)));
		}
		pos = pos + ConstantesDGT.TAM_FECHA_MATRICULACION;

		valor = linea.substring(pos, pos + ConstantesDGT.TAM_DNI_TITULAR);
		if (valor != null && !valor.isEmpty()) {
			PersonaDto personaAux = servicioPersona.getPersona(valor, colegiadoCabecera);
			if (personaAux != null) {
				personaTitular = personaAux;
			}
		}
		titular.setNifInterviniente(valor.trim());
		personaTitular.setNif(valor.trim());
		pos = pos + ConstantesDGT.TAM_DNI_TITULAR;

		valor = linea.substring(pos, pos + ConstantesDGT.TAM_MOTIVO_BAJA);
		tramite.setMotivoBaja(valor.trim());
		pos = pos + ConstantesDGT.TAM_MOTIVO_BAJA;

		if (linea.length() == ConstantesDGT.TAM_LINEA_BAJA_MAYOR) {
			// Estos campos solo aparecen si el motivo de baja es 4 y la linea del fichero sera la mayor

			PersonaDto personaAd = new PersonaDto();
			DireccionDto direccionAd = new DireccionDto();

			valor = linea.substring(pos, pos + ConstantesDGT.TAM_DNI_ADQUIRENTE);
			adquiriente.setNifInterviniente(valor.trim());
			personaAd.setNif(valor.trim());
			pos = pos + ConstantesDGT.TAM_DNI_ADQUIRENTE;

			valor = linea.substring(pos, pos + ConstantesDGT.TAM_PRIMER_APELLIDO_ADQUIRENTE);
			personaAd.setApellido1RazonSocial(valor.trim());
			pos = pos + ConstantesDGT.TAM_PRIMER_APELLIDO_ADQUIRENTE;

			valor = linea.substring(pos, pos + ConstantesDGT.TAM_SEGUNDO_APELLIDO_ADQUIRENTE);
			personaAd.setApellido2(valor.trim());
			pos = pos + ConstantesDGT.TAM_SEGUNDO_APELLIDO_ADQUIRENTE;

			valor = linea.substring(pos, pos + ConstantesDGT.TAM_NOMBRE_ADQUIRENTE);
			personaAd.setNombre(valor.trim());
			pos = pos + ConstantesDGT.TAM_NOMBRE_ADQUIRENTE;

			valor = linea.substring(pos, pos + ConstantesDGT.TAM_DOMICILIO_ADQUIRENTE);
			HashMap<String, String> separado = conversiones.separarDomicilio(valor.trim());
			direccionAd.setVia(separado.get("calle"));
			direccionAd.setIdTipoDgt(!"".equals(separado.get("tipoVia")) ? separado.get("tipoVia") : "");
			direccionAd.setNumero(separado.get("numero"));
			direccionAd.setPuerta(separado.get("puerta"));
			pos = pos + ConstantesDGT.TAM_DOMICILIO_ADQUIRENTE;

			valor = linea.substring(pos, pos + ConstantesDGT.TAM_MUNICIPIO_ADQUIRENTE);
			direccionAd.setMunicipio(valor.trim());
			pos = pos + ConstantesDGT.TAM_MUNICIPIO_ADQUIRENTE;

			valor = linea.substring(pos, pos + ConstantesDGT.TAM_PUEBLO_ADQUIRENTE);
			direccionAd.setPuebloLit(valor.trim());
			pos = pos + ConstantesDGT.TAM_PUEBLO_ADQUIRENTE;

			valor = linea.substring(pos, pos + ConstantesDGT.TAM_PROVINCIA_ADQUIRENTE);
			direccionAd.setIdProvincia(conversiones.getIdProvinciaFromSiglas(valor.trim())); 
			pos = pos + ConstantesDGT.TAM_PROVINCIA_ADQUIRENTE;

			valor = linea.substring(pos, pos + ConstantesDGT.TAM_CP_ADQUIRENTE);
			direccionAd.setCodPostal(valor.trim());
			pos = pos + ConstantesDGT.TAM_CP_ADQUIRENTE;

			valor = linea.substring(pos, pos + ConstantesDGT.TAM_SEXO_ADQUIRENTE);
			personaAd.setSexo(valor.trim().toUpperCase());
			pos = pos + ConstantesDGT.TAM_SEXO_ADQUIRENTE;

			valor = linea.substring(pos, pos + ConstantesDGT.TAM_FECHA_NACIMIENTO_ADQUIRENTE);
			personaAd.setFechaNacimiento(utilesFecha.getFechaFracionada(aaaammddToTimestamp(valor.trim())));
			pos = pos + ConstantesDGT.TAM_FECHA_NACIMIENTO_ADQUIRENTE;

			adquiriente.setDireccion(direccionAd);
			adquiriente.setPersona(personaAd);
		}

		valor = linea.substring(pos, pos + ConstantesDGT.TAM_NUM_DOCUMENTO);
		tramite.setRefPropia(valor.trim());
		pos = pos + ConstantesDGT.TAM_NUM_DOCUMENTO;

		valor = linea.substring(pos, pos + ConstantesDGT.TAM_NOMBRE_JEFATURA_TRAFICO);
		JefaturaTraficoDto jefatura = new JefaturaTraficoDto();
		jefatura.setJefaturaProvincial(conversiones.getJefaturaProvincialFromNombre(valor.trim()));
		tramite.setJefaturaTraficoDto(jefatura);
		pos = pos + ConstantesDGT.TAM_NOMBRE_JEFATURA_TRAFICO;

		valor = linea.substring(pos, pos + ConstantesDGT.TAM_PRIMER_APELLIDO);
		personaTitular.setApellido1RazonSocial(valor.trim());
		pos = pos + ConstantesDGT.TAM_PRIMER_APELLIDO;

		valor = linea.substring(pos, pos + ConstantesDGT.TAM_SEGUNDO_APELLIDO);
		personaTitular.setApellido2(valor.trim());
		pos = pos + ConstantesDGT.TAM_SEGUNDO_APELLIDO;

		valor = linea.substring(pos, pos + ConstantesDGT.TAM_NOMBRE_TITULAR);
		personaTitular.setNombre(valor.trim());
		pos = pos + ConstantesDGT.TAM_NOMBRE_TITULAR;

		valor = linea.substring(pos, pos + ConstantesDGT.TAM_DOMICILIO_TITULAR);
		HashMap<String, String> separado = conversiones.separarDomicilio(valor.trim());
		direccionTitular.setVia(separado.get("calle"));
		direccionTitular.setIdTipoDgt(!separado.get("tipoVia").isEmpty() ? separado.get("tipoVia") : "");
		direccionTitular.setNumero(separado.get("numero"));
		direccionTitular.setPuerta(separado.get("puerta"));
		pos = pos + ConstantesDGT.TAM_DOMICILIO_TITULAR;

		valor = linea.substring(pos, pos + ConstantesDGT.TAM_NUM_DOMICILIO_TITULAR);
		if (!"".equals(valor.trim()))
			direccionTitular.setNumero(valor.trim());
		pos = pos + ConstantesDGT.TAM_NUM_DOMICILIO_TITULAR;

		valor = linea.substring(pos, pos + ConstantesDGT.TAM_MUNICIPIO_TITULAR);
		direccionTitular.setMunicipio(valor.trim());
		pos = pos + ConstantesDGT.TAM_MUNICIPIO_TITULAR;

		valor = linea.substring(pos, pos + ConstantesDGT.TAM_PUEBLO_TITULAR);
		direccionTitular.setPuebloLit(valor.trim());
		pos = pos + ConstantesDGT.TAM_PUEBLO_TITULAR;

		valor = linea.substring(pos, pos + ConstantesDGT.TAM_NOMBRE_PROVINCIA_TITULAR);
		direccionTitular.setIdProvincia(conversiones.getIdProvinciaFromNombre(valor.trim()));
		pos = pos + ConstantesDGT.TAM_NOMBRE_PROVINCIA_TITULAR;

		valor = linea.substring(pos, pos + ConstantesDGT.TAM_CP_TITULAR);
		direccionTitular.setCodPostal(valor.trim());
		pos = pos + ConstantesDGT.TAM_CP_TITULAR;

		String valorApellidoRazonSocial = linea.substring(pos, pos + ConstantesDGT.TAM_REPRESENTANTE_BAJA);
		personaRepre.setApellido1RazonSocial(valor.trim());
		pos = pos + ConstantesDGT.TAM_REPRESENTANTE_BAJA;

		valor = linea.substring(pos, pos + ConstantesDGT.TAM_DNI_REPRESENTANTE);
		if (valor != null && !valor.isEmpty()) {
			PersonaDto personaAux = servicioPersona.getPersona(valor, colegiadoCabecera);
			if (personaAux != null) {
				personaRepre = personaAux;
			}
			if (valorApellidoRazonSocial != null && !valorApellidoRazonSocial.isEmpty()) {
				personaRepre.setApellido1RazonSocial(valorApellidoRazonSocial.trim());
			}
		}
		representante.setNifInterviniente(valor.trim());
		personaRepre.setNif(valor.trim());
		pos = pos + ConstantesDGT.TAM_DNI_REPRESENTANTE;

		valor = linea.substring(pos, pos + ConstantesDGT.TAM_CONCEPTO_REPR);
		representante.setConceptoRepre(valor.trim());
		pos = pos + ConstantesDGT.TAM_CONCEPTO_REPR;

		valor = linea.substring(pos, pos + ConstantesDGT.TAM_LOCALIDAD_VEHICULO_BAJA);
		direccionVehiculo.setMunicipio(valor.trim());
		pos = pos + ConstantesDGT.TAM_LOCALIDAD_VEHICULO_BAJA;

		valor = linea.substring(pos, pos + ConstantesDGT.TAM_NOMBRE_PROVINCIA_VEHICULO);
		direccionVehiculo.setIdProvincia(conversiones.getIdProvinciaFromNombre(valor.trim()));
		pos = pos + ConstantesDGT.TAM_NOMBRE_PROVINCIA_VEHICULO;

		valor = linea.substring(pos, pos + ConstantesDGT.TAM_NOMBRE_CALLE_VEHICULO_BAJA);
		direccionVehiculo.setVia(valor.trim());
		pos = pos + ConstantesDGT.TAM_NOMBRE_CALLE_VEHICULO_BAJA;

		valor = linea.substring(pos, pos + ConstantesDGT.TAM_NUM_CALLE_VEHICULO);
		direccionVehiculo.setNumero(valor.trim());
		pos = pos + ConstantesDGT.TAM_NUM_CALLE_VEHICULO;

		valor = linea.substring(pos, pos + ConstantesDGT.TAM_DOCUMENTOS_ACOMPAÑANTES);
		documentosAcom(tramite, valor.trim());
		pos = pos + ConstantesDGT.TAM_DOCUMENTOS_ACOMPAÑANTES;

		valor = linea.substring(pos, pos + ConstantesDGT.TAM_NUM_PROFESIONAL);
		try {
			if (valor != null && !valor.trim().isEmpty()) {
				valor = (new Integer(valor.trim())).toString();
			}
			if (valor.length() == 5) {
				valor = valor.substring(0, 4);
			}
			while (valor.length() < 4) {
				valor = "0" + valor;
			}
		} catch (Exception e) {
			log.error("el numero de colegiado no es un valor númerico válido");
			valor = "";
		}

		if (!colegiadoCabecera.equals(valor)) {
			if (error)
				mensajeError = mensajeError + ". El usuario de la cabecera del fichero no coincide con el del trámite";
			else
				mensajeError = "El usuario de la cabecera del fichero no coincide con el del trámite";
			error = true;
			beanResult.setError(error);
			beanResult.setMensaje(mensajeError);
			return beanResult;
		}

		if (utilesColegiado.tienePermisoAdmin()) {
			tramite.setNumColegiado(valor);
			vehiculo.setNumColegiado(valor);
			titular.setNumColegiado(valor);
			personaTitular.setNumColegiado(valor);
			representante.setNumColegiado(valor);
			personaRepre.setNumColegiado(valor);
			adquiriente.setNumColegiado(valor);
			if (adquiriente.getPersona() != null) {
				adquiriente.getPersona().setNumColegiado(valor);
			}
		} else if (utilesColegiado.tienePermisoColegio()) {
			List<String> listaColegiados = utilesColegiado.getNumColegiadosDelContrato();
			Boolean puede = false;
			int i = 0;
			while ((!puede) && (listaColegiados.size() > i)) {
				puede = puede || valor.equals(listaColegiados.get(i));
				i++;
			}
			if (puede) {
				tramite.setNumColegiado(valor);
				vehiculo.setNumColegiado(valor);
				titular.setNumColegiado(valor);
				personaTitular.setNumColegiado(valor);
				representante.setNumColegiado(valor);
				personaRepre.setNumColegiado(valor);
				adquiriente.setNumColegiado(valor);
				if (adquiriente.getPersona() != null) {
					adquiriente.getPersona().setNumColegiado(valor);
				}
			} else {
				if (error)
					mensajeError = mensajeError + ". El usuario (" + utilesColegiado.getNumColegiadoSession() + ") no tiene permisos para realizar el trámite de baja para el colegiado del fichero (" + valor
							+ ")";
				else
					mensajeError = "El usuario (" + utilesColegiado.getNumColegiadoSession() + ") no tiene permisos para realizar el trámite de baja para el colegiado del fichero (" + valor + ")";
				error = true;
				beanResult.setError(error);
				beanResult.setMensaje(mensajeError);
				return beanResult;
			}
		} else {
			if (valor.equals(utilesColegiado.getNumColegiadoSession())) {
				tramite.setNumColegiado(valor);
				vehiculo.setNumColegiado(valor);
				titular.setNumColegiado(valor);
				personaTitular.setNumColegiado(valor);
				representante.setNumColegiado(valor);
				personaRepre.setNumColegiado(valor);
				adquiriente.setNumColegiado(valor);
				if (adquiriente.getPersona() != null) {
					adquiriente.getPersona().setNumColegiado(valor);
				}
			} else {
				if (error)
					mensajeError = mensajeError + ". El usuario (" + utilesColegiado.getNumColegiadoSession() + ") no es el mismo colegiado que el del trámite del fichero (" + valor + ")";
				else
					mensajeError = "El usuario (" + utilesColegiado.getNumColegiadoSession() + ") no es el mismo colegiado que el del trámite del fichero (" + valor + ")";
				error = true;
				beanResult.setError(error);
				beanResult.setMensaje(mensajeError);
				return beanResult;
			}
		}
		pos = pos + ConstantesDGT.TAM_NUM_PROFESIONAL;

		valor = linea.substring(pos, pos + ConstantesDGT.TAM_ULT_CIFRAS_DOCUMENTO);
		pos = pos + ConstantesDGT.TAM_ULT_CIFRAS_DOCUMENTO;

		valor = linea.substring(pos, pos + ConstantesDGT.TAM_FECHA_CREACION);
		pos = pos + ConstantesDGT.TAM_FECHA_CREACION;

		valor = linea.substring(pos, pos + ConstantesDGT.TAM_ACRED_REPRESENTANTE_BAJA);
		representante.setDatosDocumento(valor.trim());
		pos = pos + ConstantesDGT.TAM_ACRED_REPRESENTANTE_BAJA;

		valor = linea.substring(pos, pos + ConstantesDGT.TAM_CONCEPTO_SOLICITANTE);
		pos = pos + ConstantesDGT.TAM_CONCEPTO_SOLICITANTE;

		valor = linea.substring(pos, pos + ConstantesDGT.TAM_OBSERVACIONES);
		tramite.setAnotaciones(valor.trim());
		pos = pos + ConstantesDGT.TAM_OBSERVACIONES;

		valor = linea.substring(pos, pos + ConstantesDGT.TAM_RESERVADO);
		pos = pos + ConstantesDGT.TAM_RESERVADO;

		valor = linea.substring(pos, pos + ConstantesDGT.TAM_TIPO_TASA);
		pos = pos + ConstantesDGT.TAM_TIPO_TASA;

		if (((vehiculo.getFechaPrimMatri() != null) && (!esVacio(vehiculo.getFechaPrimMatri().toString(), false)))
				|| ((vehiculo.getMatriAyuntamiento() != null) && (!esVacio(vehiculo.getMatriAyuntamiento(), false)))
				|| ((vehiculo.getLimiteMatrTuris() != null) && (!esVacio(vehiculo.getLimiteMatrTuris().toString(), false)))) {
			vehiculo.setVehiUsado(true);
		}

		if (personaTitular.getNif() != null && personaTitular.getApellido1RazonSocial() != null && !personaTitular.getNif().isEmpty() && !personaTitular.getApellido1RazonSocial().isEmpty()
				&& Character.isLetter(personaTitular.getNif().charAt(0))) {
			personaTitular.setAnagrama(Anagrama.obtenerAnagramaFiscal(personaTitular.getApellido1RazonSocial(), personaTitular.getNif()));
		}

		if (personaRepre.getNif() != null && personaRepre.getApellido1RazonSocial() != null && !personaRepre.getNif().isEmpty() && !personaRepre.getApellido1RazonSocial().isEmpty()
				&& Character.isLetter(personaRepre.getNif().charAt(0))) {
			personaRepre.setAnagrama(Anagrama.obtenerAnagramaFiscal(personaRepre.getApellido1RazonSocial(), personaRepre.getNif()));
		}

		if (adquiriente.getPersona() != null) {
			if (adquiriente.getPersona().getNif() != null && adquiriente.getPersona().getApellido1RazonSocial() != null && !adquiriente.getPersona().getNif().isEmpty()
					&& !"".equals(adquiriente.getPersona().getApellido1RazonSocial()) && Character.isLetter(adquiriente.getPersona().getNif().charAt(0))) {
				adquiriente.getPersona().setAnagrama(Anagrama.obtenerAnagramaFiscal(adquiriente.getPersona().getApellido1RazonSocial(), adquiriente.getPersona().getNif()));
			}
		}

		if (direccionVehiculo.getVia() != null && !direccionVehiculo.getVia().isEmpty() && direccionVehiculo.getMunicipio() != null && !direccionVehiculo.getMunicipio().isEmpty()
				&& direccionVehiculo.getCodPostal() != null && !direccionVehiculo.getCodPostal().isEmpty() && direccionVehiculo.getIdProvincia() != null
				&& !direccionVehiculo.getIdProvincia().isEmpty()) {
			if (direccionVehiculo.getIdTipoDgt() != null || direccionVehiculo.getIdTipoDgt().isEmpty()) {
				direccionVehiculo.setIdTipoDgt(TIPO_DGT_BLANCO);
			}
			if (direccionVehiculo.getNumero() == null || direccionVehiculo.getNumero().isEmpty()) {
				direccionVehiculo.setNumero(sinNumero);
			}
		}

		if (direccionTitular.getVia() != null && !direccionTitular.getVia().isEmpty() && direccionTitular.getMunicipio() != null && !direccionTitular.getMunicipio().isEmpty()
				&& direccionTitular.getCodPostal() != null && !direccionTitular.getCodPostal().isEmpty() && direccionTitular.getIdProvincia() != null && !direccionTitular.getIdProvincia().isEmpty()) {
			if (direccionTitular.getIdTipoDgt() == null || direccionTitular.getIdTipoDgt().isEmpty()) {
				direccionTitular.setIdTipoDgt(TIPO_DGT_BLANCO);
			}
			if (direccionTitular.getNumero() == null || direccionTitular.getNumero().isEmpty()) {
				direccionTitular.setNumero(sinNumero);
			}
		}

		if (linea.length() == ConstantesDGT.TAM_LINEA_BAJA_MAYOR_BTV || linea.length() == ConstantesDGT.TAM_LINEA_BAJA_MENOR_BTV) {
			valor = linea.substring(pos, pos + ConstantesDGT.TAM_BASTIDOR);
			vehiculo.setBastidor(valor.trim());
			pos = pos + ConstantesDGT.TAM_BASTIDOR;
 			valor = linea.substring(pos, pos + ConstantesDGT.TAM_PAIS_BAJA);
			//hacer que busque por cualquiera de las dos siglas dependiendo del tamaño
			PaisDto paisDto = servicioPais.getPaisPorSiglasImportDgt(valor.trim());
			if(paisDto != null){
				tramite.setPais(paisDto);
				pos = pos + ConstantesDGT.TAM_PAIS_BAJA;
			}
		}
		
		
		BigDecimal idContrato = utilesColegiado.getIdContratoSessionBigDecimal();
		tramite.setIdContrato(idContrato);

		vehiculo.setDireccion(direccionVehiculo);
		tramite.setVehiculoDto(vehiculo);
		titular.setPersona(personaTitular);
		titular.setDireccion(direccionTitular);
		tramite.setTitular(titular);
		representante.setPersona(personaRepre);
		representante.setDireccion(direccionRepre);
		tramite.setRepresentanteTitular(representante);
		tramite.setAdquiriente(adquiriente);

		beanResult.addAttachment(ConstantesDGT.DTO_BAJA, tramite);

		return beanResult;
	}

	public static Timestamp aaaammddToTimestamp(String valor) {
		if (valor != null && !valor.trim().isEmpty()) {
			String valorToTimestamp = valor.substring(0, 4) + "-" + valor.substring(4, 6) + "-" + valor.substring(6, 8) + " 00:00:00";
			return Timestamp.valueOf(valorToTimestamp);
		}
		return null;
	}

	public static boolean esVacio(String valor, boolean conEspacios) {
		boolean vacio = (valor == null || valor.length() == 0);
		if (!vacio && !conEspacios) {
			vacio = "".equals(valor.trim());
		}
		return vacio;
	}

	public static void documentosAcom(TramiteTrafBajaDto tramite, String valor) {
		char[] car = valor.toCharArray();
		for (int i = 0; i < car.length; i++) {
			if ("1".equals(car[i]))
				tramite.setPermisoCircu(true);
			else if ("2".equals(car[i]))
				tramite.setTarjetaInspeccion(true);
			else if ("3".equals(car[i]))
				tramite.setBajaImpMunicipal(true);
			else if ("4".equals(car[i]))
				tramite.setJustificanteDenuncia(true);
			else if ("5".equals(car[i]))
				tramite.setPropiedadDesguace(true);
			else if ("6".equals(car[i]))
				tramite.setPagoImpMunicipal(true);
		}
	}
}