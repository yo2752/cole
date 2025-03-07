package org.gestoresmadrid.oegam2comun.registradores.utiles;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;
import org.gestoresmadrid.core.registradores.model.enumerados.TipoEntrada;
import org.gestoresmadrid.core.registradores.model.enumerados.TipoSubsanacion;
import org.gestoresmadrid.core.registradores.model.enumerados.TipoTramiteRegistro;
import org.gestoresmadrid.oegam2comun.registradores.clases.ResultRegistro;
import org.gestoresmadrid.oegam2comun.registradores.importExport.jaxb.AeronaveType;
import org.gestoresmadrid.oegam2comun.registradores.importExport.jaxb.BienEscrituraType;
import org.gestoresmadrid.oegam2comun.registradores.importExport.jaxb.BuqueType;
import org.gestoresmadrid.oegam2comun.registradores.importExport.jaxb.CesionType;
import org.gestoresmadrid.oegam2comun.registradores.importExport.jaxb.ClausulaType;
import org.gestoresmadrid.oegam2comun.registradores.importExport.jaxb.ComisionType;
import org.gestoresmadrid.oegam2comun.registradores.importExport.jaxb.CondicionesGeneralesType;
import org.gestoresmadrid.oegam2comun.registradores.importExport.jaxb.CondicionesParticularesType;
import org.gestoresmadrid.oegam2comun.registradores.importExport.jaxb.DatosFinancierosFinanciacionType;
import org.gestoresmadrid.oegam2comun.registradores.importExport.jaxb.DomicilioINEType;
import org.gestoresmadrid.oegam2comun.registradores.importExport.jaxb.EstablecimientoMercantilType;
import org.gestoresmadrid.oegam2comun.registradores.importExport.jaxb.FORMATOGA.CONTRATOCANCELACION;
import org.gestoresmadrid.oegam2comun.registradores.importExport.jaxb.FORMATOGA.CONTRATOFINANCIACION;
import org.gestoresmadrid.oegam2comun.registradores.importExport.jaxb.FORMATOGA.ESCRITURA;
import org.gestoresmadrid.oegam2comun.registradores.importExport.jaxb.IdentificacionContratoType;
import org.gestoresmadrid.oegam2comun.registradores.importExport.jaxb.IdentificacionSujetoType;
import org.gestoresmadrid.oegam2comun.registradores.importExport.jaxb.IntervinienteFiadorType;
import org.gestoresmadrid.oegam2comun.registradores.importExport.jaxb.IntervinienteType;
import org.gestoresmadrid.oegam2comun.registradores.importExport.jaxb.MaquinariaType;
import org.gestoresmadrid.oegam2comun.registradores.importExport.jaxb.ObjetoFinanciadoType;
import org.gestoresmadrid.oegam2comun.registradores.importExport.jaxb.OtroImporteType;
import org.gestoresmadrid.oegam2comun.registradores.importExport.jaxb.OtrosBienesType;
import org.gestoresmadrid.oegam2comun.registradores.importExport.jaxb.PactoType;
import org.gestoresmadrid.oegam2comun.registradores.importExport.jaxb.PlazoFinanciacionType;
import org.gestoresmadrid.oegam2comun.registradores.importExport.jaxb.PropiedadIndustrialType;
import org.gestoresmadrid.oegam2comun.registradores.importExport.jaxb.PropiedadIntelectualType;
import org.gestoresmadrid.oegam2comun.registradores.importExport.jaxb.ReconocimientoDeudaFinanciacionType;
import org.gestoresmadrid.oegam2comun.registradores.importExport.jaxb.RegistroType;
import org.gestoresmadrid.oegam2comun.registradores.importExport.jaxb.Representante2Type;
import org.gestoresmadrid.oegam2comun.registradores.importExport.jaxb.VehiculoType;
import org.gestoresmadrid.oegam2comun.registradores.importExport.jaxb.VendedorType;

import utilidades.validaciones.NIFValidator;

public class ValidacionImportacionRegistro {

	public static ResultRegistro validaMinimoEscrituras(ESCRITURA escritura) {
		ResultRegistro resultado = new ResultRegistro();
		if (StringUtils.isNotBlank(escritura.getEscritura().getTipoInscripcion().getPrimeraEntrada())) {
			if ("NO".equalsIgnoreCase(escritura.getEscritura().getTipoInscripcion().getPrimeraEntrada())) {
				resultado.setError(Boolean.TRUE);
				resultado.setMensaje("Si informa primera entrada, debe ser SI");
			} else {
				// Se pone en primera entrada el valor que luego ir� a subsanaci�n en el mapping de dozer
				escritura.getEscritura().getTipoInscripcion().setPrimeraEntrada(TipoEntrada.PRIMERA.getValorEnum());
			}
		} else if (null != escritura.getEscritura().getTipoInscripcion().getIdentificacionNotarial()) {
			String mensaje = "";
			if (null == escritura.getEscritura().getTipoInscripcion().getIdentificacionNotarial().getCodigoNotaria() || escritura.getEscritura().getTipoInscripcion().getIdentificacionNotarial()
					.getCodigoNotaria().equals(BigDecimal.ZERO)) {
				mensaje += "C�digo notar�a,";
			}
			if (null == escritura.getEscritura().getTipoInscripcion().getIdentificacionNotarial().getAnyoProtocolo() || escritura.getEscritura().getTipoInscripcion().getIdentificacionNotarial()
					.getAnyoProtocolo().longValue() < 1000) {
				mensaje += "A�o protocolo,";
			}
			if (null == escritura.getEscritura().getTipoInscripcion().getIdentificacionNotarial().getCodigoNotario() || escritura.getEscritura().getTipoInscripcion().getIdentificacionNotarial()
					.getCodigoNotario().equals(BigDecimal.ZERO)) {
				mensaje += "C�digo notario,";
			}
			if (null == escritura.getEscritura().getTipoInscripcion().getIdentificacionNotarial().getNumeroProtocolo() || escritura.getEscritura().getTipoInscripcion().getIdentificacionNotarial()
					.getNumeroProtocolo().equals(BigDecimal.ZERO)) {
				mensaje += "N�mero de protocolo,";
			}
			if (!mensaje.isEmpty()) {
				resultado.setError(Boolean.TRUE);
				resultado.setMensaje("Error en el tipo de inscripci�n, faltan los siguientes campos:" + mensaje.substring(0, mensaje.length() - 1));
			} else {
				// Se pone en primera entrada el valor que luego ir� a subsanaci�n en el mapping de dozer
				escritura.getEscritura().getTipoInscripcion().setPrimeraEntrada(TipoSubsanacion.DESDE_INICIO.getValorEnum());
			}
		} else if (null != escritura.getEscritura().getTipoInscripcion().getIdentificacionNumeroEntrada()) {
			String mensaje = "";
			if (null == escritura.getEscritura().getTipoInscripcion().getIdentificacionNumeroEntrada().getAnyo() || escritura.getEscritura().getTipoInscripcion().getIdentificacionNumeroEntrada()
					.getAnyo().longValue() < 1000) {
				mensaje += "A�o de entrada,";
			}
			if (null == escritura.getEscritura().getTipoInscripcion().getIdentificacionNumeroEntrada().getNumero() || escritura.getEscritura().getTipoInscripcion().getIdentificacionNumeroEntrada()
					.getNumero().equals(BigDecimal.ZERO)) {
				mensaje += "N�mero de entrada,";
			}
			if (!mensaje.isEmpty()) {
				resultado.setError(Boolean.TRUE);
				resultado.setMensaje("Error en el tipo de inscripci�n, faltan los siguientes campos: " + mensaje.substring(0, mensaje.length() - 1));
			} else {
				// Se pone en primera entrada el valor que luego ir� a subsanaci�n en el mapping de dozer
				escritura.getEscritura().getTipoInscripcion().setPrimeraEntrada(TipoSubsanacion.DESDE_INICIO.getValorEnum());
			}
		} else {
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("El tipo de inscripci�n es obligatorio");
		}
		return resultado;
	}

	public static ResultRegistro validaIdentificacionSujeto(IdentificacionSujetoType identificacionSujeto, TipoTramiteRegistro tipoContrato) {
		ResultRegistro resultado = new ResultRegistro();
		if (null != identificacionSujeto) {
			String mensaje = "";
			if (StringUtils.isBlank(identificacionSujeto.getNumeroDocumento()) || !NIFValidator.isValidNIF(identificacionSujeto.getNumeroDocumento().toUpperCase())) {
				mensaje += "N�mero de documento,";
			}
			if (NIFValidator.isValidDniNieCif(identificacionSujeto.getNumeroDocumento().toUpperCase()) == 0) {
				if (StringUtils.isBlank(identificacionSujeto.getNombre())) {
					mensaje += "Nombre,";
				}
			}
			if (StringUtils.isBlank(identificacionSujeto.getApellido1RazonSocial())) {
				mensaje += "Apellido o Raz�n Social,";
			}
			if (!mensaje.isEmpty()) {
				resultado.setError(Boolean.TRUE);
				resultado.setMensaje("Error en la identificaci�n del sujeto, faltan o son erroneos los siguientes campos: " + mensaje.substring(0, mensaje.length() - 1));
			}
		} else {
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("La identificaci�n del sujeto viene vacia, ");
		}
		return resultado;
	}

	public static ResultRegistro validaDomicilioIne(DomicilioINEType domicilio, TipoTramiteRegistro tipoContrato) {
		ResultRegistro resultado = new ResultRegistro();
		if (null != domicilio) {
			String mensaje = "";
			if (!Pattern.matches("S?|[A-Z]{2}", domicilio.getPais()) || domicilio.getPais().trim().isEmpty()) {
				domicilio.setPais("ES");
			}
			if (!domicilio.getPais().equalsIgnoreCase("ES")) {
				if (domicilio.getEstadoRegionProvinciaExtranjera().isEmpty()) {
					mensaje += "Regi�n o provincia extranjera,";
				}
			}
			if (!Pattern.matches("\\d{2}", domicilio.getCodProvincia())) {
				mensaje += "C�digo provincia,";
			}
			if (!Pattern.matches("\\d{3}", domicilio.getCodMunicipio())) {
				mensaje += "C�digo municipio,";
			}
			if (!Pattern.matches("\\d{5}", domicilio.getCodigoPostal())) {
				mensaje += "C�digo postal,";
			}
			if (!mensaje.isEmpty()) {
				resultado.setError(Boolean.TRUE);
				resultado.setMensaje("Error en el domicilio, faltan o son erroneos los siguientes campos: " + mensaje.substring(0, mensaje.length() - 1));
			}
		} else {
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("El domicilio viene vac�o, ");
		}
		return resultado;
	}

	public static ResultRegistro validaRegistro(RegistroType registro, TipoTramiteRegistro tipoContrato) {
		ResultRegistro resultado = new ResultRegistro();
		if (null != registro) {
			String mensaje = "";
			if (!Pattern.matches("\\d{2}", registro.getCodProvincia())) {
				mensaje += "C�digo provincia,";
			}
			if (!Pattern.matches("\\d{3}", registro.getCodRegistro())) {
				mensaje += "C�digo registro,";
			}
			if (!mensaje.isEmpty()) {
				resultado.setError(Boolean.TRUE);
				resultado.setMensaje("Error en los datos del registro, faltan o son erroneos los siguientes campos: " + mensaje.substring(0, mensaje.length() - 1));
			}
		} else {
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("El registro viene vacio");
		}
		return resultado;
	}

	public static ResultRegistro validaBienesEscritura(List<BienEscrituraType> bienes, TipoTramiteRegistro tipoContrato) {
		ResultRegistro resultado = new ResultRegistro();
		List<BienEscrituraType> lista = new ArrayList<BienEscrituraType>();
		String mensajeSalida = "";
		if (null != bienes && !bienes.isEmpty()) {
			String mensaje = "";
			int numero = 0;
			for (BienEscrituraType elemento : bienes) {
				mensaje = validaBien(elemento);
				numero++;
				if (!mensaje.isEmpty()) {
					mensajeSalida += "Error en los datos del bien " + numero + ", faltan o son erroneos los siguientes campos: " + mensaje.substring(0, mensaje.length() - 1);
				} else {
					lista.add(elemento);
				}
			}
		} else {
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("No tiene bienes");
		}
		if (!mensajeSalida.isEmpty()) {
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje(mensajeSalida);
		}
		bienes = lista;
		return resultado;
	}

	private static String validaBien(BienEscrituraType bien) {
		String mensaje = "";
		if (null == bien.getCodigoProvincia() || !Pattern.matches("\\d{2}", bien.getCodigoProvincia())) {
			mensaje += "C�digo provincia,";
		}
		if (null == bien.getCodigoMunicipio() || !Pattern.matches("\\d{3}", bien.getCodigoMunicipio())) {
			mensaje += "C�digo municipio,";
		}
		if (null == bien.getSeccionRegistral()) {
			mensaje += "Secci�n registral,";
		}
		if (null == bien.getNumeroFinca()) {
			mensaje += "N�mero Finca,";
		}
		if (null == bien.getIdufir() || !Pattern.matches("\\d{14}", bien.getIdufir())) {
			mensaje += "IDUFIR/CRU,";
		}
		return mensaje;
	}

	public static ResultRegistro validaMinimoCancelacion(CONTRATOCANCELACION cancelacion) {
		ResultRegistro resultado = new ResultRegistro();
		String mensaje = "";
		if (StringUtils.isBlank(cancelacion.getModelo())) {
			mensaje += "Modelo,";
		}
		if (StringUtils.isBlank(cancelacion.getCausaCancelacion().name())) {
			mensaje += "Causa cancelaci�n,";
		}
		if (!mensaje.isEmpty()) {
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("Error, faltan los siguientes campos m�nimos: " + mensaje.substring(0, mensaje.length() - 1));
		}
		return resultado;
	}

	// IMPLEMENTED JVG. 11/05/2018.

	private static String validaBienFinanciacion(ObjetoFinanciadoType objetosFinanciados) {
		String mensaje = "";

		if (null == objetosFinanciados.getDesBienMueble().getCategoria() || !Pattern.matches("[0-9]{2}", objetosFinanciados.getDesBienMueble().getCategoria())) {

			mensaje += "Categoria Objetos Financiados,";
		}
		if (null == objetosFinanciados.getDesBienMueble().getDatosCategoria().getVehiculo()) {

			mensaje += "Vehiculo Datos Categoria Objetos Financiados,";
		}
		if (null == objetosFinanciados.getDesBienMueble().getDatosCategoria().getMaquinaria()) {

			mensaje += "Maquinaria Datos Categoria Objetos Financiados,";
		}
		if (null == objetosFinanciados.getDesBienMueble().getDatosCategoria().getEstablecimiento()) {

			mensaje += "Establecimiento Mercantil Datos Categoria Objetos Financiados,";
		}
		if (null == objetosFinanciados.getDesBienMueble().getDatosCategoria().getBuque()) {

			mensaje += "Buque Datos Categoria Objetos Financiados,";
		}
		if (null == objetosFinanciados.getDesBienMueble().getDatosCategoria().getAeronave()) {

			mensaje += "Aeronave Datos Categoria Objetos Financiados,";
		}
		if (null == objetosFinanciados.getDesBienMueble().getDatosCategoria().getPropiedadIndustrial()) {

			mensaje += "Propiedad Industrial Datos Categoria Objetos Financiados,";
		}
		if (null == objetosFinanciados.getDesBienMueble().getDatosCategoria().getOtrosBienes()) {

			mensaje += "Otros Bienes Datos Categoria Objetos Financiados,";
		}
		if (null == objetosFinanciados.getValor().getValorContado() || !Pattern.matches("S?|(\\d{1,10}(,\\d{1,2}))|\\d{1,10}", objetosFinanciados.getValor().getValorContado())) {

			mensaje += "Valor Contado Objetos Financiados,";
		}
		if (null == objetosFinanciados.getValor().getUnidadCuenta() || !Pattern.matches("S?|[A-Z]{3}", objetosFinanciados.getValor().getUnidadCuenta())) {

			mensaje += "Unidad Cuenta Objetos Financiados,";

		}

		return mensaje;
	}

	// IMPLEMENTED JVG. 21/05/2018.

	private static String validaCompradorFinanciacion(IntervinienteType compradores) {
		String mensaje = "";

		if (null == compradores.getIdenSujeto().getTipoPersona() || !Pattern.matches("[A-Z]{1}", compradores.getIdenSujeto().getTipoPersona())) {

			mensaje += "Tipo Persona compradores,";
		}
		if (null == compradores.getIdenSujeto().getNifCif()) {

			mensaje += "NifCif compradores,";
		}
		if (null == compradores.getIdenSujeto().getNombre()) {

			mensaje += "Nombre compradores,";
		}
		if (null == compradores.getIdenSujeto().getApellido1RazonSocial()) {

			mensaje += "Apellido1,Raz�n Social compradores,";
		}
		if (null == compradores.getIdenSujeto().getApellido2()) {

			mensaje += "Apellido2,";
		}

		String mensajeDomi = validaDomicilioINE(compradores.getDomicilio());
		if (null == compradores.getDomicilio()) {

			mensaje += "Domicilio compradores,";
		}

		if (null == compradores.getCorreoElectronico() || !Pattern.matches("S?|([^@]+@[^@]+\\.[^@]+)", compradores.getCorreoElectronico())) {

			mensaje += "Correo Electr�nico compradores,";
		}

		if (null != compradores.getDatosRegistralesMercantil()) {
			if (null == compradores.getDatosRegistralesMercantil().getCodigoRMercantil() || !Pattern.matches("S?|\\d{1,5}", compradores.getDatosRegistralesMercantil().getCodigoRMercantil())) {

				mensaje += "C�digoR Mercantil datos registrales comprador,";
			}
			if (null == compradores.getDatosRegistralesMercantil().getTomoRMercantil() || !Pattern.matches("S?|\\d{1,15}", compradores.getDatosRegistralesMercantil().getTomoRMercantil())) {

				mensaje += "TomoR Mercantil datos registrales comprador,";
			}
			if (null == compradores.getDatosRegistralesMercantil().getLibroRMercantil() || !Pattern.matches("S?|\\d{1,15}", compradores.getDatosRegistralesMercantil().getLibroRMercantil())) {

				mensaje += "LibroR Mercantil datos registrales comprador,";
			}
			if (null == compradores.getDatosRegistralesMercantil().getFolioRMercantil() || !Pattern.matches("S?|\\d{1,15}", compradores.getDatosRegistralesMercantil().getFolioRMercantil())) {

				mensaje += "FolioR Mercantil datos registrales comprador,";
			}
			if (null == compradores.getDatosRegistralesMercantil().getHojaRMercantil()) {

				mensaje += "HojaR Mercantil datos registrales comprador,";
			}
			if (null == compradores.getDatosRegistralesMercantil().getInscripcionRMercantil()) {

				mensaje += "Inscripci�nR Mercantil datos registrales comprador,";
			}

		}

		for (Representante2Type representante : compradores.getRepresentante()) {

			if (null == compradores.getIdenSujeto().getTipoPersona() || !Pattern.matches("[A-Z]{1}", compradores.getIdenSujeto().getTipoPersona())) {

				mensaje += "Tipo Persona representante del comprador,";
			}
			if (null == compradores.getIdenSujeto().getNifCif()) {

				mensaje += "NifCif representante del comprardor,";
			}
			if (null == compradores.getIdenSujeto().getNombre()) {

				mensaje += "Nombre representante del comprador,";
			}
			if (null == compradores.getIdenSujeto().getApellido1RazonSocial()) {

				mensaje += "Apellido1,Raz�nSocial representante el comprador,";
			}
			if (null == compradores.getIdenSujeto().getApellido2()) {

				mensaje += "Apellido2 representante del comprador,";
			}
		}

		if (null == compradores.getTelefono()) {

			mensaje += "Tel�fono compradores,";
		}

		return mensaje;
	}

	private static String validaFiadorFinanciacion(IntervinienteFiadorType fiador) {
		String mensaje = "";

		if (null == fiador.getIdenSujeto().getTipoPersona() || !Pattern.matches("[A-Z]{1}", fiador.getIdenSujeto().getTipoPersona())) {

			mensaje += "Tipo Persona compradores,";
		}
		if (null == fiador.getIdenSujeto().getNifCif()) {

			mensaje += "NifCif compradores,";
		}
		if (null == fiador.getIdenSujeto().getNombre()) {

			mensaje += "Nombre compradores,";
		}
		if (null == fiador.getIdenSujeto().getApellido1RazonSocial()) {

			mensaje += "Apellido1,Raz�n Social compradores,";
		}
		if (null == fiador.getIdenSujeto().getApellido2()) {

			mensaje += "Apellido2,";
		}

		String mensajeDomi = validaDomicilioINE(fiador.getDomicilio());
		if (null == fiador.getDomicilio()) {

			mensaje += "Domicilio compradores,";
		}

		if (null == fiador.getCorreoElectronico() || !Pattern.matches("S?|([^@]+@[^@]+\\.[^@]+)", fiador.getCorreoElectronico())) {

			mensaje += "Correo Electr�nico compradores,";
		}

		if (null != fiador.getDatosRegistralesMercantil()) {
			if (null == fiador.getDatosRegistralesMercantil().getCodigoRMercantil() || !Pattern.matches("S?|\\d{1,5}", fiador.getDatosRegistralesMercantil().getCodigoRMercantil())) {

				mensaje += "C�digoR Mercantil datos registrales comprador,";
			}
			if (null == fiador.getDatosRegistralesMercantil().getTomoRMercantil() || !Pattern.matches("S?|\\d{1,15}", fiador.getDatosRegistralesMercantil().getTomoRMercantil())) {

				mensaje += "TomoR Mercantil datos registrales comprador,";
			}
			if (null == fiador.getDatosRegistralesMercantil().getLibroRMercantil() || !Pattern.matches("S?|\\d{1,15}", fiador.getDatosRegistralesMercantil().getLibroRMercantil())) {

				mensaje += "LibroR Mercantil datos registrales comprador,";
			}
			if (null == fiador.getDatosRegistralesMercantil().getFolioRMercantil() || !Pattern.matches("S?|\\d{1,15}", fiador.getDatosRegistralesMercantil().getFolioRMercantil())) {

				mensaje += "FolioR Mercantil datos registrales comprador,";
			}
			if (null == fiador.getDatosRegistralesMercantil().getHojaRMercantil()) {

				mensaje += "HojaR Mercantil datos registrales comprador,";
			}
			if (null == fiador.getDatosRegistralesMercantil().getInscripcionRMercantil()) {

				mensaje += "Inscripci�nR Mercantil datos registrales comprador,";
			}

		}
		for (Representante2Type representante : fiador.getRepresentante()) {

			if (null == fiador.getIdenSujeto().getTipoPersona() || !Pattern.matches("[A-Z]{1}", fiador.getIdenSujeto().getTipoPersona())) {

				mensaje += "Tipo Persona representante del comprador,";
			}
			if (null == fiador.getIdenSujeto().getNifCif()) {

				mensaje += "NifCif representante del comprardor,";
			}
			if (null == fiador.getIdenSujeto().getNombre()) {

				mensaje += "Nombre representante del comprador,";
			}
			if (null == fiador.getIdenSujeto().getApellido1RazonSocial()) {

				mensaje += "Apellido1,Raz�nSocial representante el comprador,";
			}
			if (null == fiador.getIdenSujeto().getApellido2()) {

				mensaje += "Apellido2 representante del comprador,";
			}
		}

		if (null == fiador.getTelefono()) {

			mensaje += "Tel�fono compradores,";
		}

		return mensaje;
	}

	private static String validaFinanciadorFinan(IntervinienteType financiadores) {
		String mensaje = "";

		if (null == financiadores.getIdenSujeto().getTipoPersona() || !Pattern.matches("[A-Z]{1}", financiadores.getIdenSujeto().getTipoPersona())) {

			mensaje += "Tipo Persona compradores,";
		}
		if (null == financiadores.getIdenSujeto().getNifCif()) {

			mensaje += "NifCif compradores,";
		}
		if (null == financiadores.getIdenSujeto().getNombre()) {

			mensaje += "Nombre compradores,";
		}
		if (null == financiadores.getIdenSujeto().getApellido1RazonSocial()) {

			mensaje += "Apellido1,Raz�n Social compradores,";
		}
		if (null == financiadores.getIdenSujeto().getApellido2()) {

			mensaje += "Apellido2,";
		}

		String mensajeDomi = validaDomicilioINE(financiadores.getDomicilio());
		if (null == financiadores.getDomicilio()) {

			mensaje += "Domicilio compradores,";
		}

		if (null == financiadores.getCorreoElectronico() || !Pattern.matches("S?|([^@]+@[^@]+\\.[^@]+)", financiadores.getCorreoElectronico())) {

			mensaje += "Correo Electr�nico compradores,";
		}

		if (null != financiadores.getDatosRegistralesMercantil()) {
			if (null == financiadores.getDatosRegistralesMercantil().getCodigoRMercantil() || !Pattern.matches("S?|\\d{1,5}", financiadores.getDatosRegistralesMercantil().getCodigoRMercantil())) {

				mensaje += "C�digoR Mercantil datos registrales comprador,";
			}
			if (null == financiadores.getDatosRegistralesMercantil().getTomoRMercantil() || !Pattern.matches("S?|\\d{1,15}", financiadores.getDatosRegistralesMercantil().getTomoRMercantil())) {

				mensaje += "TomoR Mercantil datos registrales comprador,";
			}
			if (null == financiadores.getDatosRegistralesMercantil().getLibroRMercantil() || !Pattern.matches("S?|\\d{1,15}", financiadores.getDatosRegistralesMercantil().getLibroRMercantil())) {

				mensaje += "LibroR Mercantil datos registrales comprador,";
			}
			if (null == financiadores.getDatosRegistralesMercantil().getFolioRMercantil() || !Pattern.matches("S?|\\d{1,15}", financiadores.getDatosRegistralesMercantil().getFolioRMercantil())) {

				mensaje += "FolioR Mercantil datos registrales comprador,";
			}
			if (null == financiadores.getDatosRegistralesMercantil().getHojaRMercantil()) {

				mensaje += "HojaR Mercantil datos registrales comprador,";
			}
			if (null == financiadores.getDatosRegistralesMercantil().getInscripcionRMercantil()) {

				mensaje += "Inscripci�nR Mercantil datos registrales comprador,";
			}

		}

		for (Representante2Type representante : financiadores.getRepresentante()) {

			if (null == financiadores.getIdenSujeto().getTipoPersona() || !Pattern.matches("[A-Z]{1}", financiadores.getIdenSujeto().getTipoPersona())) {

				mensaje += "Tipo Persona representante del comprador,";
			}
			if (null == financiadores.getIdenSujeto().getNifCif()) {

				mensaje += "NifCif representante del comprardor,";
			}
			if (null == financiadores.getIdenSujeto().getNombre()) {

				mensaje += "Nombre representante del comprador,";
			}
			if (null == financiadores.getIdenSujeto().getApellido1RazonSocial()) {

				mensaje += "Apellido1,Raz�nSocial representante el comprador,";
			}
			if (null == financiadores.getIdenSujeto().getApellido2()) {

				mensaje += "Apellido2 representante del comprador,";
			}
		}

		if (null == financiadores.getTelefono()) {

			mensaje += "Tel�fono compradores,";
		}

		return mensaje;
	}

	private static String validaVendedorFinan(VendedorType financiadores) {
		String mensaje = "";

		if (null == financiadores.getIdenSujeto().getTipoPersona() || !Pattern.matches("[A-Z]{1}", financiadores.getIdenSujeto().getTipoPersona())) {

			mensaje += "Tipo Persona vendedores,";
		}
		if (null == financiadores.getIdenSujeto().getNifCif()) {

			mensaje += "NifCif vendedores,";
		}
		if (null == financiadores.getIdenSujeto().getNombre()) {

			mensaje += "Nombre vendedores,";
		}
		if (null == financiadores.getIdenSujeto().getApellido1RazonSocial()) {

			mensaje += "Apellido1,Raz�n Social vendedores,";
		}

		String mensajeDomi = validaDomicilioINE(financiadores.getDomicilio());
		if (null == financiadores.getDomicilio()) {

			mensaje += "Domicilio vendedores,";
		}

		return mensaje;
	}

	// END IMPLEMENTED.

	/*
	 * IMPLEMENTED JVG-- 05/04/2018. JVG. CONTRATOFINANCIACI�N.
	 */

	public static ResultRegistro validaMinimoImportacionRegistroFinan(CONTRATOFINANCIACION contratofinanciacion) {

		ResultRegistro resultado = new ResultRegistro();

		if (null == contratofinanciacion) {

			String mensaje = "";

			resultado.setError(Boolean.TRUE);
			resultado.addValidacion("El registro de financiaci�n es err�neo � inv�lido");

			String mensajeNumeroContratoFinanciacion = "Por favor introduzca un n�mero de contrato correcto para registro de financiaciones.,";

			// IDENTIFICACI�N CONTRATO.

		} else if (StringUtils.isBlank(contratofinanciacion.getRegistro().getCodRegistro())) {

			resultado.setError(Boolean.TRUE);
			resultado.addValidacion("C�digo Registro Contrato Financiaci�n,");

		} else if (StringUtils.isBlank(contratofinanciacion.getRegistro().getCodProvincia())) {

			resultado.setError(Boolean.TRUE);
			resultado.addValidacion("C�digo Provincia Contrato Financiaci�n,");

		} else if (StringUtils.isBlank(contratofinanciacion.getIdentificacion().getNumeroOperacion())) {

			resultado.setError(Boolean.TRUE);
			resultado.addValidacion("N�mero de Operaci�n campo obligatorio.");

			// }else if (null== contratofinanciacion.getIdentificacion().getNombreAsociacion()){
		} else if (StringUtils.isBlank(contratofinanciacion.getIdentificacion().getNombreAsociacion())) {

			resultado.setError(Boolean.TRUE);
			resultado.addValidacion("Nombre Asociaci�n campo obligatorio,");

			// }else if (null== contratofinanciacion.getIdentificacion().getNumeroEjemplar()
		} else if (StringUtils.isBlank(contratofinanciacion.getIdentificacion().getNumeroEjemplar())) {

			resultado.setError(Boolean.TRUE);
			resultado.addValidacion("N�mero de Ejemplar campo obligatorio,");

			// }else if (null== contratofinanciacion.getIdentificacion().getModeloContrato()){
		} else if (StringUtils.isBlank(contratofinanciacion.getIdentificacion().getModeloContrato())) {

			resultado.setError(Boolean.TRUE);
			resultado.addValidacion("Modelo Contrato campo obligatorio,");

			// }else if (null== contratofinanciacion.getIdentificacion().getNombreDocumento()){
		} else if (StringUtils.isBlank(contratofinanciacion.getIdentificacion().getNombreDocumento())) {

			resultado.setError(Boolean.TRUE);
			resultado.addValidacion("Nombre Documento campo obligatorio,");

			// }else if (null== contratofinanciacion.getIdentificacion().getAprobadoDGRN()){
		} else if (StringUtils.isBlank(contratofinanciacion.getIdentificacion().getAprobadoDGRN())) {

			resultado.setError(Boolean.TRUE);
			resultado.addValidacion("Aprobado DGRN campo obligatorio,");

			// }else if (null== contratofinanciacion.getIdentificacion().getNumeroImpreso()){
		} else if (StringUtils.isBlank(contratofinanciacion.getIdentificacion().getNumeroImpreso())) {

			resultado.setError(Boolean.TRUE);
			resultado.addValidacion("N�mero Impreso campo obligatorio,");

			// REGISTRO.

		} else if (null != contratofinanciacion.getRegistro().getCodRegistro()

				|| Pattern.matches("S?|\\d{1,5}", contratofinanciacion.getRegistro().getCodRegistro())) {

			String mensaje = "C�digo Registro,";

		} else if (null != contratofinanciacion.getRegistro().getCodProvincia()

				|| Pattern.matches("S?|\\d{2}", contratofinanciacion.getRegistro().getCodRegistro())) {

			String mensaje = "C�digo Provincia de Registro,";

			// COMPRADORES.

		} else if (null != contratofinanciacion.getCompradores() && null != contratofinanciacion.getCompradores().getComprador()) {

			String mensaje = "";

			for (IntervinienteType comprador : contratofinanciacion.getCompradores().getComprador()) {

				if (null != comprador.getIdenSujeto()

						|| Pattern.matches("[A-Z]{1}", (CharSequence) comprador.getIdenSujeto())) {

					mensaje += "Identificaci�n Sujeto,";

				} else if (null != comprador.getIdenSujeto().getTipoPersona()

						|| Pattern.matches("[A-Z]{1}", (CharSequence) comprador.getIdenSujeto().getTipoPersona())) {

					mensaje += "Tipo Persona,";

				} else if (null != comprador.getIdenSujeto().getNifCif()) {

					mensaje += "Nif - Cif,";

				} else if (null != comprador.getIdenSujeto().getNombre()) {

					mensaje += "Nombre,";

				} else if (null != comprador.getIdenSujeto().getApellido1RazonSocial()) {

					mensaje += "Apellido 1,Raz�n Social compradores,";

				} else if (null != comprador.getIdenSujeto().getApellido2()) {

					mensaje += "Apellido 2,";

					String mensajeDomi = validaDomicilioINE(comprador.getDomicilio());

					// }else if(!mensajeDomi.isEmpty()){

					mensaje += "mensajeDomi,";

				} else if (null != comprador.getCorreoElectronico()

						|| Pattern.matches("S?|([^@]+@[^@]+\\.[^@]+)", (CharSequence) comprador.getCorreoElectronico())) {

					mensaje += "Correo Electr�nico,";

				} else if (null != comprador.getDatosRegistralesMercantil()

						|| Pattern.matches("[A-Z]{1}", (CharSequence) comprador.getDatosRegistralesMercantil())) {

					mensaje += "Datos Registrales Mercantil,";

				} else if (null != comprador.getDatosRegistralesMercantil().getCodigoRMercantil()

						|| Pattern.matches("S?|\\d{1,5}", (CharSequence) comprador.getDatosRegistralesMercantil().getCodigoRMercantil())) {

					mensaje += "C�digoR Mercantil,";

				} else if (null != comprador.getDatosRegistralesMercantil().getTomoRMercantil()

						|| Pattern.matches("S?|\\d{1,15}", (CharSequence) comprador.getDatosRegistralesMercantil().getTomoRMercantil())) {

					mensaje += "TomoR Mercantil,";

				} else if (null != comprador.getDatosRegistralesMercantil().getLibroRMercantil()

						|| Pattern.matches("S?|\\d{1,15}", (CharSequence) comprador.getDatosRegistralesMercantil().getLibroRMercantil())) {

					mensaje += "LibroR Mercantil,";

				} else if (null != comprador.getDatosRegistralesMercantil().getFolioRMercantil()

						|| Pattern.matches("S?|\\d{1,15}", (CharSequence) comprador.getDatosRegistralesMercantil().getFolioRMercantil())) {

					mensaje += "FolioR Mercantil,";

				} else if (null != comprador.getDatosRegistralesMercantil().getHojaRMercantil()) {

					mensaje += "HojaR Mercantil,";

				} else if (null != comprador.getDatosRegistralesMercantil().getInscripcionRMercantil()) {

					mensaje += "Inscripci�nR Mercantil,";

				} else if (null != comprador.getTelefono()) {

					mensaje += "Datos Tel�fono,";

				} else if (null != comprador.getRepresentante()

						|| Pattern.matches("[A-Z]{1}", (CharSequence) comprador.getRepresentante())) {

					mensaje += "Representante,";

				} else if (null != comprador.getIdenSujeto().getTipoPersona()

						|| Pattern.matches("[A-Z]{1}", (CharSequence) comprador.getIdenSujeto().getTipoPersona())) {

					mensaje += "Tipo Persona,";

				} else if (null != comprador.getIdenSujeto().getNifCif()) {

					mensaje += "Nif-Cif,";

				} else if (null != comprador.getIdenSujeto().getNombre()) {

					mensaje += "Nombre,";

				} else if (null != comprador.getIdenSujeto().getApellido1RazonSocial()) {

					mensaje += "Apellido 1, Raz�n Social compradores,";

				} else if (null != comprador.getIdenSujeto().getApellido2()) {

					mensaje += "Apellido 2,";

				} else {

					for (Representante2Type representante : comprador.getRepresentante()) {

						if (null != representante.getCargo()) {

							// String mensaje = "Cargo,";
							mensaje += "Cargo,";

						} else if (null != representante.getDatosNotario()

								|| Pattern.matches("[A-Z]{1}", (CharSequence) representante.getDatosNotario())) {

							mensaje += "Datos Notario,";

						} else if (null != representante.getIdenSujeto()

								|| Pattern.matches("[A-Z]{1}", (CharSequence) representante.getIdenSujeto())) {

							mensaje += "Identificaci�n Sujeto,";

						} else if (null != representante.getDatosNotario().getCodProvincia()

								|| Pattern.matches("S?|\\d{1,2}", (CharSequence) representante.getDatosNotario().getCodProvincia())) {

							mensaje += "C�digo Provincia,";

						} else if (null != representante.getDatosNotario().getCodMunicipio()

								|| Pattern.matches("S?|\\d{1,4}", (CharSequence) representante.getDatosNotario().getCodMunicipio())) {

							mensaje += "C�digo Municipio,";

						} else if (null != representante.getDatosNotario().getFechaOtorgamiento()

								|| Pattern.matches("S?|\\d{1,4}", (CharSequence) representante.getDatosNotario().getFechaOtorgamiento())) {

							mensaje += "Fecha Otorgamiento,";

						} else if (null != representante.getDatosNotario().getNumeroProtocolo()) {

							mensaje += "N�mero Protocolo,";

						}
					}
				}
			}

			// FIADORES.

		} else if (null != contratofinanciacion.getFiadores().getFiador()) {

			String mensaje = "";

			for (IntervinienteFiadorType fiador : contratofinanciacion.getFiadores().getFiador()) {

				if (null != fiador.getIdenSujeto()) {

					mensaje += "Identificaci�n Sujeto Fiadores,";

				} else if (null != fiador.getIdenSujeto().getTipoPersona()

						|| Pattern.matches("[A-Z]{1}", (CharSequence) fiador.getIdenSujeto().getTipoPersona())) {

					mensaje += "Tipo Persona,";

				} else if (null != fiador.getIdenSujeto().getNifCif()) {

					mensaje += "Nif - Cif,";

				} else if (null != fiador.getIdenSujeto().getNombre()) {

					mensaje += "Nombre,";

				} else if (null != fiador.getIdenSujeto().getApellido2()) {

					mensaje += "Apellidos,";

				}

				String mensajeDomicilioIneFiador = validaDomicilioINE(fiador.getDomicilio());

				if (!mensajeDomicilioIneFiador.isEmpty()) {

					mensaje += mensajeDomicilioIneFiador;

				}

				if (null != fiador.getCorreoElectronico()

						|| Pattern.matches("S?|([^@]+@[^@]+\\.[^@]+)", (CharSequence) fiador.getCorreoElectronico())) {

					mensaje += "Correo Electr�nico,";

				}

				if (null != fiador.getDatosRegistralesMercantil()) {

					mensaje += "Datos Registrales Mercantil,";

				} else if (null != fiador.getDatosRegistralesMercantil().getCodigoRMercantil()

						|| Pattern.matches("S?|\\d{1,5}", (CharSequence) fiador.getDatosRegistralesMercantil().getCodigoRMercantil())) {

					mensaje += "C�digoR Mercantil,";

				} else if (null != fiador.getDatosRegistralesMercantil().getTomoRMercantil()

						|| Pattern.matches("S?|\\d{1,15}", (CharSequence) fiador.getDatosRegistralesMercantil().getTomoRMercantil())) {

					mensaje += "TomoR Mercantil,";

				} else if (null != fiador.getDatosRegistralesMercantil().getLibroRMercantil()

						|| Pattern.matches("S?|\\d{1,15}", (CharSequence) fiador.getDatosRegistralesMercantil().getLibroRMercantil())) {

					mensaje += "LibroR Mercantil,";

				} else if (null != fiador.getDatosRegistralesMercantil().getFolioRMercantil()

						|| Pattern.matches("S?|\\d{1,15}", (CharSequence) fiador.getDatosRegistralesMercantil().getFolioRMercantil())) {

					mensaje += "FolioR Mercantil,";

				} else if (null != fiador.getDatosRegistralesMercantil().getHojaRMercantil()) {

					mensaje += "HojaR Mercantil,";

				} else if (null != fiador.getDatosRegistralesMercantil().getInscripcionRMercantil()) {

					mensaje += "Inscripci�nR Mercantil,";

				}

				if (null != fiador.getRepresentante()) {

					mensaje += "Representante,";

				} else if (null != fiador.getIdenSujeto().getTipoPersona()

						|| Pattern.matches("[A-Z]{1}", (CharSequence) fiador.getIdenSujeto().getTipoPersona())) {

					mensaje += "Tipo Persona,";

				} else if (null != fiador.getIdenSujeto().getNifCif()) {

					mensaje += "Nif-Cif,";

				} else if (null != fiador.getIdenSujeto().getNombre()) {

					mensaje += "Nombre,";

				} else if (null != fiador.getIdenSujeto().getApellido2()) {

					mensaje += "Apellidos,";

				} else if (null != fiador.getTelefono()) {

					mensaje += "Tel�fono,";
				}
			}
			// FINANCIADORES.

		} else if (null != contratofinanciacion.getFinanciadores().getFinanciador()) {

			String mensaje = "";

			for (IntervinienteType financiador : contratofinanciacion.getFinanciadores().getFinanciador()) {

				if (null != financiador.getIdenSujeto()) {

					mensaje += "Identificaci�n Sujeto,";

				} else if (null != financiador.getIdenSujeto().getTipoPersona()

						|| Pattern.matches("[A-Z]{1}", (CharSequence) financiador.getIdenSujeto().getTipoPersona())) {

					mensaje += "Tipo Persona,";

				} else if (null != financiador.getIdenSujeto().getNifCif()) {

					mensaje += "Nif - Cif,";

				} else if (null != financiador.getIdenSujeto().getNombre()) {

					mensaje += "Nombre,";

				} else if (null != financiador.getIdenSujeto().getApellido1RazonSocial()) {

					mensaje += "Apellido1 o Raz�n Social financiadores,";
				}

				// DomicilioINEType.
				String mensajeDomicilioIneFinanciador = validaDomicilioINE(financiador.getDomicilio());

				if (!mensajeDomicilioIneFinanciador.isEmpty()) {

					mensaje += mensajeDomicilioIneFinanciador;

				} else if (null != financiador.getCorreoElectronico()

						|| Pattern.matches("S?|([^@]+@[^@]+\\.[^@]+)", (CharSequence) financiador.getCorreoElectronico())) {

					mensaje += "Correo Electr�nico,";

				} else if (null != financiador.getDatosRegistralesMercantil()) {

					mensaje += "Datos Registrales Mercantil,";

				} else if (null != financiador.getDatosRegistralesMercantil().getCodigoRMercantil()

						|| Pattern.matches("S?|\\d{1,5}", (CharSequence) financiador.getDatosRegistralesMercantil().getCodigoRMercantil())) {

					mensaje += "C�digoR Mercantil,";

				} else if (null != financiador.getDatosRegistralesMercantil().getTomoRMercantil()

						|| Pattern.matches("S?|\\d{1,15}", (CharSequence) financiador.getDatosRegistralesMercantil().getTomoRMercantil())) {

					mensaje += "TomoR Mercantil,";

				} else if (null != financiador.getDatosRegistralesMercantil().getLibroRMercantil()

						|| Pattern.matches("S?|\\d{1,15}", (CharSequence) financiador.getDatosRegistralesMercantil().getLibroRMercantil())) {

					mensaje += "LibroR Mercantil,";

				} else if (null != financiador.getDatosRegistralesMercantil().getFolioRMercantil()

						|| Pattern.matches("S?|\\d{1,15}", (CharSequence) financiador.getDatosRegistralesMercantil().getFolioRMercantil())) {

					mensaje += "FolioR Mercantil,";

				} else if (null != financiador.getDatosRegistralesMercantil().getHojaRMercantil()) {

					mensaje += "HojaR Mercantil,";

				} else if (null != financiador.getDatosRegistralesMercantil().getInscripcionRMercantil()) {

					mensaje += "Inscripci�nR Mercantil,";

				} else if (null != financiador.getRepresentante()) {

					mensaje += "Representante,";

				} else if (null != financiador.getIdenSujeto().getTipoPersona()

						|| Pattern.matches("[A-Z]{1}", (CharSequence) financiador.getIdenSujeto().getTipoPersona())) {

					mensaje += "Tipo Persona,";

				} else if (null != financiador.getIdenSujeto().getNifCif()) {

					mensaje += "Nif-Cif,";

				} else if (null != financiador.getIdenSujeto().getNombre()) {

					mensaje += "Nombre,";

				} else if (null != financiador.getIdenSujeto().getApellido2()) {

					mensaje += "Apellidos,";

				} else if (null != financiador.getTelefono()) {

					mensaje += "Tel�fono,";

				}
			}
			// VENDEDORES.

		} else if (null != contratofinanciacion.getVendedores().getVendedor()) {

			String mensaje = "";

			for (VendedorType vendedor : contratofinanciacion.getVendedores().getVendedor()) {

				if (null != vendedor.getIdenSujeto()) {

					mensaje += "Identificaci�n Sujeto,";

				} else if (null != vendedor.getIdenSujeto().getTipoPersona()

						|| Pattern.matches("[A-Z]{1}", (CharSequence) vendedor.getIdenSujeto().getTipoPersona())) {

					mensaje += "Tipo Persona,";

				} else if (null != vendedor.getIdenSujeto().getNifCif()) {

					mensaje += "Cif-Nif,";

				} else if (null != vendedor.getIdenSujeto().getNombre()) {

					mensaje += "Nombre,";

				} else if (null != vendedor.getIdenSujeto().getApellido2()) {

					mensaje += "Apellidos,";
				}

				// DomicilioINEType.

				String mensajeDomicilioIneVendedor = validaDomicilioINE(vendedor.getDomicilio());

				if (!mensajeDomicilioIneVendedor.isEmpty()) {

					mensaje += mensajeDomicilioIneVendedor;
				}

			}

			// OBJETOS FINANCIADOS.

		} else if (null != contratofinanciacion.getObjetosFinanciados().getObjetoFinanciado()) {

			String mensaje = "";

			for (ObjetoFinanciadoType objetosfinanciados : contratofinanciacion.getObjetosFinanciados().getObjetoFinanciado()) {

				if (null != objetosfinanciados.getDesBienMueble().getCategoria()

						|| Pattern.matches("[0-9]{2}", (CharSequence) objetosfinanciados.getDesBienMueble().getCategoria())) {

					mensaje += "Categor�a Bien Mueble,";

				} else if (null != objetosfinanciados.getDesBienMueble().getDatosCategoria().getVehiculo()) {

					for (VehiculoType vehiculo : objetosfinanciados.getDesBienMueble().getDatosCategoria().getVehiculo()) {

						if (null != vehiculo.getTipo()) {

							mensaje += "Tipo Veh�culo,";

						} else if (null != vehiculo.getMarca()) {

							mensaje += "Marca Veh�culo,";

						} else if (null != vehiculo.getModelo()) {

							mensaje += "Modelo Veh�culo,";

						} else if (null != vehiculo.getGrupo()) {

							mensaje += "Grupo Veh�culo,";

						} else if (null != vehiculo.getMatricula()) {

							mensaje += "Matr�cula Veh�culo,";

						} else if (null != vehiculo.getBastidor()) {

							mensaje += "String mensaje =eh�culo,";

						} else if (null != vehiculo.getNIVE()) {

							mensaje += "NIVE Veh�culo,";
						}

					}

				} else if (null != objetosfinanciados.getDesBienMueble().getDatosCategoria().getMaquinaria()) {

					for (MaquinariaType maquinaria : objetosfinanciados.getDesBienMueble().getDatosCategoria().getMaquinaria()) {

						if (null != maquinaria.getTipo()) {

							mensaje += "Tipo Maquinaria,";

						} else if (null != maquinaria.getMarca()) {

							mensaje += "Marca Maquinaria,";

						} else if (null != maquinaria.getModelo()) {

							mensaje += "Modelo Maquinaria,";

						} else if (null != maquinaria.getGrupo()) {

							mensaje += "Grupo Maquinaria,";

						} else if (null != maquinaria.getNumSerie()) {

							mensaje += "N�mero Serie Maquinaria,";

						} else if (null != maquinaria.getUbicacion().getDomicilio()) {

							// DomicilioINEType.

							String mensajeDomicilioIne = validaDomicilioINE(maquinaria.getUbicacion().getDomicilio());

							if (!mensajeDomicilioIne.isEmpty()) {

								mensaje += mensajeDomicilioIne;

							}

						} else if (null != maquinaria.getUbicacion().getDatosRegistrales().getCodRegistroPropiedad()

								|| Pattern.matches("S?|\\d{1,5}", (CharSequence) maquinaria.getUbicacion().getDatosRegistrales().getCodRegistroPropiedad())) {

							mensaje += "C�digo Registro Propiedad,";

						} else if (null != maquinaria.getUbicacion().getDatosRegistrales().getCodProvincia()

								|| Pattern.matches("S?|\\d{2}", (CharSequence) maquinaria.getUbicacion().getDatosRegistrales().getCodProvincia())) {

							mensaje += "C�digo Provincia,";

						} else if (null != maquinaria.getUbicacion().getDatosRegistrales().getCodMunicipio()

								|| Pattern.matches("S?|\\d{3}", (CharSequence) maquinaria.getUbicacion().getDatosRegistrales().getCodMunicipio())) {

							mensaje += "C�digo Municipio,";

						} else if (null != maquinaria.getUbicacion().getDatosRegistrales().getSeccionPropiedad()) {

							mensaje += "Secci�n Propiedad,";

						} else if (null != maquinaria.getUbicacion().getDatosRegistrales().getNumFinca()

								|| Pattern.matches("S?|\\d{1,15}", (CharSequence) maquinaria.getUbicacion().getDatosRegistrales().getNumFinca())) {

							mensaje += "N�mero Finca,";

						} else if (null != maquinaria.getUbicacion().getDatosRegistrales().getNumFincaBis()) {

							mensaje += "N�mero Finca Bis,";

						} else if (null != maquinaria.getUbicacion().getDatosRegistrales().getNumSubFinca()) {

							mensaje += "N�mero SubFinca,";

						} else if (null != maquinaria.getUbicacion().getDatosRegistrales().getIDUFIR()) {

							mensaje += "IDUFIR,";

						} else if (null != maquinaria.getUbicacion().getReferenciaCatastral()) {

							mensaje += "Referencia Catastral,";

						}
					}

				} else if (null != objetosfinanciados.getDesBienMueble().getDatosCategoria().getEstablecimiento()) {

					for (EstablecimientoMercantilType establecimiento : objetosfinanciados.getDesBienMueble().getDatosCategoria().getEstablecimiento()) {

						if (null != establecimiento.getNombreEstablecimiento()) {

							mensaje += "Nombre Establecimiento,";

						} else if (null != establecimiento.getClaseEstablecimiento()) {

							mensaje += "Clase Establecimiento,";

						} else if (null != establecimiento.getNumeroEstablecimiento()) {

							mensaje += "N�mero Establecimiento,";

						} else if (null != establecimiento.getUbicacion().getDomicilio()) {

							// DomicilioINEType.

							String mensajeDomicilioIne = validaDomicilioINE(establecimiento.getUbicacion().getDomicilio());

							if (!mensajeDomicilioIne.isEmpty()) {

								mensaje += mensajeDomicilioIne;

							}

						} else if (null != establecimiento.getUbicacion().getDatosRegistrales().getCodRegistroPropiedad()

								|| Pattern.matches("S?|\\d{1,5}", (CharSequence) establecimiento.getUbicacion().getDatosRegistrales().getCodRegistroPropiedad())) {

							mensaje += "C�digo Registro Propiedad,";

						} else if (null != establecimiento.getUbicacion().getDatosRegistrales().getCodProvincia()

								|| Pattern.matches("S?|\\d{2}", (CharSequence) establecimiento.getUbicacion().getDatosRegistrales().getCodProvincia())) {

							mensaje += "C�digo Provincia,";

						} else if (null != establecimiento.getUbicacion().getDatosRegistrales().getCodMunicipio()

								|| Pattern.matches("S?|\\d{3}", (CharSequence) establecimiento.getUbicacion().getDatosRegistrales().getCodMunicipio())) {

							mensaje += "C�digo Municipio,";

						} else if (null != establecimiento.getUbicacion().getDatosRegistrales().getSeccionPropiedad()) {

							mensaje += "Secci�n Propiedad,";

						} else if (null != establecimiento.getUbicacion().getDatosRegistrales().getNumFinca()

								|| Pattern.matches("S?|\\d{1,15}", (CharSequence) establecimiento.getUbicacion().getDatosRegistrales().getNumFinca())) {

							mensaje += "N�mero Finca,";

						} else if (null != establecimiento.getUbicacion().getDatosRegistrales().getNumFincaBis()) {

							mensaje += "N�mero Finca Bis,";

						} else if (null != establecimiento.getUbicacion().getDatosRegistrales().getNumSubFinca()) {

							mensaje += "N�mero SubFinca,";

						} else if (null != establecimiento.getUbicacion().getDatosRegistrales().getIDUFIR()) {

							mensaje += "IDUFIR,";

						} else if (null != establecimiento.getUbicacion().getReferenciaCatastral()) {

							mensaje += "Referencia Catastral,";
						}

					}
				} else if (null != objetosfinanciados.getDesBienMueble().getDatosCategoria().getBuque()) {

					for (BuqueType buque : objetosfinanciados.getDesBienMueble().getDatosCategoria().getBuque()) {

						if (null != buque.getNombreBuque()) {

							mensaje += "Nombre Buque,";

						} else if (null != buque.getMatriculaBuque()

								|| Pattern.matches("S?|\\d{1,2}", (CharSequence) buque.getMatriculaBuque().getMatriculaMaritima())) {

							mensaje += "Matr�cula Mar�tima Buque,";

						} else if (null != buque.getMatriculaBuque().getMatriculaMaritima().getCapitaniaMaritima()

								|| Pattern.matches("S?|\\d{1,2}", buque.getMatriculaBuque().getMatriculaMaritima().getCapitaniaMaritima())) {

							mensaje += "Capitan�a Mar�tima,";

						} else if (null != buque.getMatriculaBuque().getMatriculaMaritima().getProvinciaMaritima()) {

							mensaje += "Provincia Mar�tima,";

						} else if (null != buque.getMatriculaBuque().getMatriculaMaritima().getDistritoMaritimo()) {

							mensaje += "Distrito Mar�timo,";

						} else if (null != buque.getMatriculaBuque().getMatriculaMaritima().getNumLista()

								|| Pattern.matches("S?|[L]{1}[0-9]{1}", buque.getMatriculaBuque().getMatriculaMaritima().getNumLista())) {

							mensaje += "N�mero Lista,";

						} else if (null != buque.getMatriculaBuque().getMatriculaMaritima().getFolioInscripcion()

								|| Pattern.matches("S?|\\d{1,15}", buque.getMatriculaBuque().getMatriculaMaritima().getFolioInscripcion())) {

							mensaje += "Folio Inscripci�n,";

						} else if (null != buque.getMatriculaBuque().getMatriculaMaritima().getAnioInscripcion()

								|| Pattern.matches("S?|\\d{1,15}", buque.getMatriculaBuque().getMatriculaMaritima().getAnioInscripcion())) {

							mensaje += "A�o Inscripci�n,";

						} else if (null != buque.getMatriculaBuque().getMatriculaFluvial()) {

							mensaje += "Matr�cula Fluvial,";

						} else if (null != buque.getNIB()) {

							mensaje += "NIB buque,";

						} else if (null != buque.getPabellon()) {

							mensaje += "Pabell�n Buque,";

						} else if (null != buque.getOMI()) {

							mensaje += "OMI Buque,";

						} else if (null != buque.getClasificacionBuque().getTipoEmbarcacion()) {

							mensaje += "Tipo Embarcaci�n,";

						} else if (null != buque.getClasificacionBuque().getSubTipoEmbarcacion()) {

							mensaje += "SubTipo Embarcaci�n,";

						} else if (null != buque.getDimensiones().getEslora()

								|| Pattern.matches("S?|(\\d{1,10}(,\\d{2}))|\\d{1,10}", buque.getDimensiones().getEslora())) {

							mensaje += "Eslora,";

						} else if (null != buque.getDimensiones().getManga()

								|| Pattern.matches("S?|(\\d{1,10}(,\\d{2}))|\\d{1,10}", buque.getDimensiones().getManga())) {

							mensaje += "Manga,";

						} else if (null != buque.getDimensiones().getManga()

								|| Pattern.matches("S?|(\\d{1,10}(,\\d{2}))|\\d{1,10}", buque.getDimensiones().getManga())) {

							mensaje += "Puntal,";

						} else if (null != buque.getDimensiones().getCaladoMaximo()

								|| Pattern.matches("S?|(\\d{1,10}(,\\d{2}))|\\d{1,10}", buque.getDimensiones().getCaladoMaximo())) {

							mensaje += "Calado M�ximo,";

						} else if (null != buque.getTonelaje().getBruto()

								|| Pattern.matches("S?|(\\d{1,10}(,\\d{3}))|\\d{1,10}", buque.getTonelaje().getBruto())) {

							mensaje += "Tonelaje Bruto,";

						} else if (null != buque.getTonelaje().getRegistradoBruto()

								|| Pattern.matches("S?|(\\d{1,10}(,\\d{3}))|\\d{1,10}", buque.getTonelaje().getRegistradoBruto())) {

							mensaje += "Registrado Bruto,";

						} else if (null != buque.getTonelaje().getNeto()

								|| Pattern.matches("S?|(\\d{1,10}(,\\d{3}))|\\d{1,10}", buque.getTonelaje().getNeto())) {

							mensaje += "Neto,";

						} else if (null != buque.getTonelaje().getRegistradoNeto()

								|| Pattern.matches("S?|(\\d{1,10}(,\\d{3}))|\\d{1,10}", buque.getTonelaje().getRegistradoNeto())) {

							mensaje += "Registrado Neto,";

						} else if (null != buque.getTonelaje().getDesplazamiento()

								|| Pattern.matches("S?|(\\d{1,10}(,\\d{3}))|\\d{1,10}", buque.getTonelaje().getDesplazamiento())) {

							mensaje += "Desplazamiento,";

						} else if (null != buque.getTonelaje().getPesoMuerto()

								|| Pattern.matches("S?|(\\d{1,10}(,\\d{3}))|\\d{1,10}", buque.getTonelaje().getPesoMuerto())) {

							mensaje += "Peso Muerto,";

						} else if (null != buque.getTonelaje().getCargaMaxima()

								|| Pattern.matches("S?|(\\d{1,10}(,\\d{3}))|\\d{1,10}", buque.getTonelaje().getCargaMaxima())) {

							mensaje += "Carga M�xima,";

						} else if (null != buque.getCasco().getAstillero()) {

							mensaje += "Astillero,";

						} else if (null != buque.getCasco().getAnioConstruccion()) {

							mensaje += "A�o Construcci�n,";

						} else if (null != buque.getCasco().getPais()) {

							mensaje += "Pa�s,";

						} else if (null != buque.getCasco().getMarca()) {

							mensaje += "Marca,";

						} else if (null != buque.getCasco().getModelo()) {

							mensaje += "Modelo,";

						} else if (null != buque.getCasco().getNumSerie()) {

							mensaje += "N�mero Serie,";

						} else if (null != buque.getCasco().getMaterial()) {

							mensaje += "Material,";

						} else if (null != buque.getEstado()) {

							mensaje += "Estado buque,";

						} else if (null != buque.getOtrosDatos()) {

							mensaje += "Otros Datos Buque,";
						}

					}

				} else if (null != objetosfinanciados.getDesBienMueble().getDatosCategoria().getAeronave()) {

					for (AeronaveType aeronave : objetosfinanciados.getDesBienMueble().getDatosCategoria().getAeronave()) {

						if (null != aeronave.getMatricula()) {

							mensaje += "Matr�cula aeronave,";

						} else if (null != aeronave.getTipo()) {

							mensaje += "Tipo Aeronave,";

						} else if (null != aeronave.getMarca()) {

							mensaje += "Marca Aeronave,";

						} else if (null != aeronave.getModelo()) {

							mensaje += "Modelo Aeronave,";

						} else if (null != aeronave.getNumSerie()) {

							mensaje += "N�mero Serie Aeronave,";
						}

					}

				} else if (null != objetosfinanciados.getDesBienMueble().getDatosCategoria().getPropiedadIndustrial()) {

					for (PropiedadIndustrialType propiedadindustrial : objetosfinanciados.getDesBienMueble().getDatosCategoria().getPropiedadIndustrial()) {

						if (null != propiedadindustrial.getNombreMarca()) {

							mensaje += "Nombre Marca,";

						} else if (null != propiedadindustrial.getTipoMarca()) {

							mensaje += "Tipo Marca,";

						} else if (null != propiedadindustrial.getNumRegAdm()) {

							mensaje += "N�mero Reg. Adm.,";

						} else if (null != propiedadindustrial.getClaseMarca()) {

							mensaje += "Clase Marca,";

						} else if (null != propiedadindustrial.getSubClaseMarca()) {

							mensaje += "Subclase Marca,";

						} else if (null != propiedadindustrial.getDescripcion()) {

							mensaje += "Descripci�n,";
						}

					}
				} else if (null != objetosfinanciados.getDesBienMueble().getDatosCategoria().getPropiedadIntelectual()) {

					for (PropiedadIntelectualType propiedadintelectual : objetosfinanciados.getDesBienMueble().getDatosCategoria().getPropiedadIntelectual()) {

						if (null != propiedadintelectual.getNumRegAdm()) {

							mensaje += "N�m.Reg. Adm,";

						} else if (null != propiedadintelectual.getClase()

								|| Pattern.matches("S?|[A-Z]{3}", propiedadintelectual.getClase())) {

							mensaje += "Clase,";

						} else if (null != propiedadintelectual.getDescripcion()) {

							mensaje += "Descripci�n,";
						}

					}

				} else if (null != objetosfinanciados.getDesBienMueble().getDatosCategoria().getOtrosBienes()) {

					for (OtrosBienesType otrosbienes : objetosfinanciados.getDesBienMueble().getDatosCategoria().getOtrosBienes()) {

						if (null != otrosbienes.getDesOtrosBienes().getTipo()) {

							mensaje += "Tipo,";

						} else if (null != otrosbienes.getDesOtrosBienes().getMarca()) {

							mensaje += "Marca,";

						} else if (null != otrosbienes.getDesOtrosBienes().getModelo()) {

							mensaje += "Modelo,";

						} else if (null != otrosbienes.getDesOtrosBienes().getNumSerie()) {

							mensaje += "N�mero Serie,";

						} else if (null != otrosbienes.getRegistroAdministrativo().getTipoRegAdm()

								|| Pattern.matches("S?|[A-Z]{3,4}", otrosbienes.getRegistroAdministrativo().getTipoRegAdm())) {

							mensaje += "Tipo Reg.Adm.,";

						} else if (null != otrosbienes.getRegistroAdministrativo().getNumRegAdm()) {

							mensaje += "N�m.Reg.Adm.,";

						} else if (null != otrosbienes.getUbicacion().getDomicilio()) {

							// DomicilioINEType.

							String mensajeDomicilioIne = validaDomicilioINE(otrosbienes.getUbicacion().getDomicilio());

							if (!mensajeDomicilioIne.isEmpty()) {

								mensaje += mensajeDomicilioIne;

							}
						} else if (null != otrosbienes.getUbicacion().getReferenciaCatastral()) {

							mensaje += "Referencia Catastral,";

						} else if (null != otrosbienes.getUbicacion().getDatosRegistrales().getCodRegistroPropiedad()

								|| Pattern.matches("S?|\\d{1,5}", (CharSequence) otrosbienes.getUbicacion().getDatosRegistrales().getCodRegistroPropiedad())) {

							mensaje += "C�digo Registro Propiedad,";

						} else if (null != otrosbienes.getUbicacion().getDatosRegistrales().getCodProvincia()

								|| Pattern.matches("S?|\\d{2}", (CharSequence) otrosbienes.getUbicacion().getDatosRegistrales().getCodProvincia())) {

							mensaje += "C�digo Provincia,";

						} else if (null != otrosbienes.getUbicacion().getDatosRegistrales().getCodMunicipio()

								|| Pattern.matches("S?|\\d{3}", (CharSequence) otrosbienes.getUbicacion().getDatosRegistrales().getCodMunicipio())) {

							mensaje += "C�digo Municipio,";

						} else if (null != otrosbienes.getUbicacion().getDatosRegistrales().getSeccionPropiedad()) {

							mensaje += "Secci�n Propiedad,";

						} else if (null != otrosbienes.getUbicacion().getDatosRegistrales().getNumFinca()

								|| Pattern.matches("S?|\\d{1,15}", (CharSequence) otrosbienes.getUbicacion().getDatosRegistrales().getNumFinca())) {

							mensaje += "N�mero Finca,";

						} else if (null != otrosbienes.getUbicacion().getDatosRegistrales().getNumFincaBis()) {

							mensaje += "N�mero Finca Bis,";

						} else if (null != otrosbienes.getUbicacion().getDatosRegistrales().getNumSubFinca()) {

							mensaje += "N�mero SubFinca,";

						} else if (null != otrosbienes.getUbicacion().getDatosRegistrales().getIDUFIR()) {

							mensaje += "IDUFIR,";

						} else if (null != objetosfinanciados.getDesBienMueble().getDatosCategoria().getOtrosBienes()) {

							for (OtrosBienesType sociedad : objetosfinanciados.getDesBienMueble().getDatosCategoria().getOtrosBienes()) {

								if (null == sociedad.getSociedad().getNombreSociedad()) {

									mensaje += "Nombre Sociedad,";

								} else if (null != sociedad.getSociedad().getCifSociedad()) {

									mensaje += "Cif Sociedad,";

								} else if (null != sociedad.getSociedad().getCNAE()) {

									mensaje += "CNAE,";

								} else if (null != sociedad.getSociedad().getInsRegMercantil()

										|| Pattern.matches("S?|\\d{1,5}", (CharSequence) sociedad.getSociedad().getInsRegMercantil().getCodRegMercantil())) {

									mensaje += "C�digo Registro Mercantil,";

								} else if (null != sociedad.getSociedad().getInsRegMercantil().getSeccionMercantil()

										|| Pattern.matches("S?|\\d{1,15}", (CharSequence) sociedad.getSociedad().getInsRegMercantil().getSeccionMercantil())) {

									mensaje += "Secci�n Mercantil,";

								} else if (null != sociedad.getSociedad().getInsRegMercantil().getSeccionMercantil()

										|| Pattern.matches("S?|\\d{1,15}", (CharSequence) sociedad.getSociedad().getInsRegMercantil().getNumHoja())) {

									mensaje += "N�mero Hoja,";

								} else if (null != sociedad.getSociedad().getInsRegMercantil().getSeccionMercantil()

										|| Pattern.matches("S?|\\d{1,15}", (CharSequence) sociedad.getSociedad().getInsRegMercantil().getNumHojaDup())) {

									mensaje += "N�mero HojaDup,";

								} else if (null != sociedad.getSociedad().getInsRegMercantil().getSeccionMercantil()) {

									mensaje += "N�mero SubHojaDup,";

								} else if (null != sociedad.getSociedad().getInsRegMercantil().getTomoSociedad()

										|| Pattern.matches("S?|\\d{1,15}", (CharSequence) sociedad.getSociedad().getInsRegMercantil().getTomoSociedad())) {

									mensaje += "Tomo Sociedad,";

								} else if (null != sociedad.getSociedad().getInsRegMercantil().getFolioSociedad()

										|| Pattern.matches("S?|\\d{1,15}", (CharSequence) sociedad.getSociedad().getInsRegMercantil().getFolioSociedad())) {

									mensaje += "Folio Sociedad,";

								} else if (null != sociedad.getSociedad().getInsRegMercantil().getInsSociedad()

										|| Pattern.matches("S?|\\d{1,15}", (CharSequence) sociedad.getSociedad().getInsRegMercantil().getInsSociedad())) {

									mensaje += "Inscripci�n Sociedad,";

								} else if (null != otrosbienes.getOtraDescripcion()) {

									mensaje += "Otra Descripci�n,";

								}

							}

						}

					}

				} else if (null != objetosfinanciados.getValor().getValorContado()

						|| Pattern.matches("S?|(\\d{1,10}(,\\d{1,2}))|\\d{1,10}", (CharSequence) objetosfinanciados.getValor().getValorContado())) {

					mensaje += "Valor Contado,";

				} else if (null != objetosfinanciados.getValor().getUnidadCuenta()

						|| Pattern.matches("S?|[A-Z]{3}", (CharSequence) objetosfinanciados.getValor().getUnidadCuenta())) {

					mensaje += "Unidad Cuenta,";

				}
			}
			// DATOS FINANCIEROS.

		} else if (StringUtils.isBlank((contratofinanciacion.getDatosFinancieros().getUnidadCuenta()))) {
			if ("NO".equalsIgnoreCase(contratofinanciacion.getDatosFinancieros().getUnidadCuenta())) {

				resultado.setError(Boolean.TRUE);
				resultado.addValidacion("Campo Datos Financieros Unidad Cuenta debe tener datos.");

			} else if (null != contratofinanciacion.getDatosFinancieros().getUnidadCuenta()

					|| Pattern.matches("S?|[A-Z]{3}", contratofinanciacion.getDatosFinancieros().getUnidadCuenta())) {

				// mensaje += "Unidad Cuenta,";
				String mensaje = "";

			} else if (null != contratofinanciacion.getDatosFinancieros().getImportePrecioCompraventa()

					|| Pattern.matches("S?|(\\d{1,10}(,\\d{1,2}))|\\d{1,10}", contratofinanciacion.getDatosFinancieros().getImportePrecioCompraventa())) {

				String mensaje = "Importe Precio CompraVenta,";

			} else if (null != contratofinanciacion.getDatosFinancieros().getImporteDesembolsoInicial()

					|| Pattern.matches("S?|(\\d{1,10}(,\\d{1,2}))|\\d{1,10}", contratofinanciacion.getDatosFinancieros().getImporteDesembolsoInicial())) {

				String mensaje = "Importe Desembolsado Inicial,";

			} else if (null != contratofinanciacion.getDatosFinancieros().getImporteCapitalPrestamo()

					|| Pattern.matches("S?|(\\d{1,10}(,\\d{1,2}))|\\d{1,10}", contratofinanciacion.getDatosFinancieros().getImporteCapitalPrestamo())) {

				String mensaje = "Importe Capital Prestamo,";

			} else if (null != contratofinanciacion.getDatosFinancieros().getImporteTotalPrestamo()

					|| Pattern.matches("S?|(\\d{1,10}(,\\d{1,2}))|\\d{1,10}", contratofinanciacion.getDatosFinancieros().getImporteTotalPrestamo())) {

				String mensaje = "Importe Total Pr�stamo,";

			} else if (null != contratofinanciacion.getDatosFinancieros().getImporteIntereses()

					|| Pattern.matches("S?|(\\d{1,10}(,\\d{1,2}))|\\d{1,10}", contratofinanciacion.getDatosFinancieros().getImporteIntereses())) {

				String mensaje = "Importe Intereses,";

			} else if (null != contratofinanciacion.getDatosFinancieros().getTipoDeudor()

					|| Pattern.matches("S?|(\\d{1,3}(,\\d{1,10}))|\\d{1,3}", contratofinanciacion.getDatosFinancieros().getTipoDeudor())) {

				String mensaje = "Tipo Deudor,";

			} else if (null != contratofinanciacion.getDatosFinancieros().getTipoInteresNominalAnual()

					|| Pattern.matches("S?|(\\d{1,3}(,\\d{1,10}))|\\d{1,3}", contratofinanciacion.getDatosFinancieros().getTipoInteresNominalAnual())) {

				String mensaje = "Tipo Inter�s Nominal Anual,";

			} else if (null != contratofinanciacion.getDatosFinancieros().getInteresesDemora()

					|| Pattern.matches("S?|(\\d{1,3}(,\\d{1,10}))|\\d{1,3}", contratofinanciacion.getDatosFinancieros().getInteresesDemora())) {

				String mensaje = "Intereses Demora,";

			} else if (null != contratofinanciacion.getDatosFinancieros().getNumeroMeses()

					|| Pattern.matches("S?|\\d{1,15}", contratofinanciacion.getDatosFinancieros().getNumeroMeses())) {

				String mensaje = "N�mero meses,";

			} else if (null != contratofinanciacion.getDatosFinancieros().getFechaUltimoVencimiento()) {

				String mensaje = "Fecha �ltimo Vencimiento,";

			} else if (null != contratofinanciacion.getDatosFinancieros().getImporteImpuestoMatriculacion()

					|| Pattern.matches("S?|(\\d{1,10}(,\\d{1,2}))|\\d{1,10}", contratofinanciacion.getDatosFinancieros().getImporteImpuestoMatriculacion())) {

				String mensaje = "Importe Impuesto Matriculaci�n,";

			} else if (null != contratofinanciacion.getDatosFinancieros().getImporteCancelacionRDLD()

					|| Pattern.matches("S?|(\\d{1,10}(,\\d{1,2}))|\\d{1,10}", contratofinanciacion.getDatosFinancieros().getImporteCancelacionRDLD())) {

				String mensaje = "Importe Cancelaci�n RD LD,";

			} else if (null != contratofinanciacion.getDatosFinancieros().getImporteTotalAdeudado()

					|| Pattern.matches("S?|(\\d{1,10}(,\\d{1,2}))|\\d{1,10}", contratofinanciacion.getDatosFinancieros().getImporteTotalAdeudado())) {

				String mensaje = "Importe Total Adeudado,";

			} else if (null != contratofinanciacion.getDatosFinancieros().getImporteDerechoDesistimiento()

					|| Pattern.matches("S?|(\\d{1,10}(,\\d{1,2}))|\\d{1,10}", contratofinanciacion.getDatosFinancieros().getImporteDerechoDesistimiento())) {

				String mensaje = "Importe Derecho Desistimiento,";

			} else {
				for (ComisionType comision : contratofinanciacion.getDatosFinancieros().getComisiones().getComision()) {

					if (null != comision.getTipoComision()

							|| Pattern.matches("[A-Z]{3}", comision.getTipoComision())) {

						String mensaje = "Tipo Comisi�n,";

					} else if (null != comision.getPorcentaje()

							|| Pattern.matches("S?|(\\d{1,3}(,\\d{1,10}))|\\d{1,3}", comision.getPorcentaje())) {

						String mensaje = "Porcentaje,";

					} else if (null != comision.getImporteMinimo()

							|| Pattern.matches("S?|(\\d{1,10}(,\\d{1,2}))|\\d{1,10}", comision.getImporteMinimo())) {

						String mensaje = "Importe M�nimo,";

					} else if (null != comision.getImporteMaximo()

							|| Pattern.matches("S?|(\\d{1,10}(,\\d{1,2}))|\\d{1,10}", comision.getImporteMaximo())) {

						String mensaje = "Importe M�ximo,";

					} else if (null != comision.getFinanciado()

							|| Pattern.matches("S?|SI|NO", comision.getFinanciado())) {

						String mensaje = "Financiado,";

					} else if (null != comision.getCondicionAplicacion()) {

						String mensaje = "Condicion Aplicaci�n,";

					}

				}

				if (null != contratofinanciacion.getDatosFinancieros().getOtrosImportes().getOtroImporte()

						|| Pattern.matches("S?|\\d{1,2}", (CharSequence) contratofinanciacion.getDatosFinancieros().getOtrosImportes().getOtroImporte())) {

					String mensaje = "Otros Importes,";

				}
				for (OtroImporteType otroImporte : contratofinanciacion.getDatosFinancieros().getOtrosImportes().getOtroImporte()) {

					if (null != otroImporte.getTipoOtroImporte()

							|| Pattern.matches("S?|\\d{1,2}", otroImporte.getTipoOtroImporte())) {

						String mensaje = "OtroImporte,";

					} else if (null != otroImporte.getImporte()

							|| Pattern.matches("S?|(\\d{1,10}(,\\d{1,2}))|\\d{1,10}", otroImporte.getImporte())) {

						String mensaje = "Importe,";

					} else if (null != otroImporte.getPorcentaje().getPorcentaje()

							|| Pattern.matches("S?|(\\d{1,3}(,\\d{1,10}))|\\d{1,3}", (CharSequence) contratofinanciacion.getDatosFinancieros().getOtrosImportes().getOtroImporte())) {

						String mensaje = "Porcentaje,";

					} else if (null != otroImporte.getPorcentaje().getBase()

							|| Pattern.matches("S?|(\\d{1,3}(,\\d{1,10}))|\\d{1,3}", (CharSequence) contratofinanciacion.getDatosFinancieros().getOtrosImportes().getOtroImporte())) {

						String mensaje = "Porcentaje Base,";

					} else if (null != otroImporte.getObservaciones()) {

						String mensaje = "Observaciones,";

					} else if (null != otroImporte.getCondicionante()

							|| Pattern.matches("S?|SI|NO", otroImporte.getCondicionante())) {

						String mensaje = "Condicionante,";

					}

				}

				if (null != contratofinanciacion.getDatosFinancieros().getVariabilidadTipoInteres()) {

					if (null != contratofinanciacion.getDatosFinancieros().getVariabilidadTipoInteres().getFechaRevision()

							|| Pattern.matches("\\d{2}/\\d{2}/\\d{4}", contratofinanciacion.getDatosFinancieros().getVariabilidadTipoInteres().getFechaRevision())) {

						String mensaje = "Fecha Revisi�n Variabilidad Tipo Inter�s Datos Financieros,";

					} else if (null != contratofinanciacion.getDatosFinancieros().getVariabilidadTipoInteres().getPeriodoRevision()

							|| Pattern.matches("S?|\\d{1,15}", contratofinanciacion.getDatosFinancieros().getVariabilidadTipoInteres().getPeriodoRevision())) {

						String mensaje = "Per�odo Revisi�n Variabilidad Tipo Inter�s Datos Financieros,";

					} else if (null != contratofinanciacion.getDatosFinancieros().getVariabilidadTipoInteres().getTipoInteresReferencia()

							|| Pattern.matches("S?|\\d{1,2}", contratofinanciacion.getDatosFinancieros().getVariabilidadTipoInteres().getTipoInteresReferencia())) {

						String mensaje = "Tipo Inter�s Referencia Variabilidad Tipo Inter�s Datos Financieros,";

					} else if (null != contratofinanciacion.getDatosFinancieros().getVariabilidadTipoInteres()

							.getDiferencialFijo()

							|| Pattern.matches("S?|(\\d{1,3}(,\\d{1,10}))|\\d{1,3}", contratofinanciacion.getDatosFinancieros().getVariabilidadTipoInteres().getDiferencialFijo())) {

						String mensaje = "Diferencial Fijo Variabilidad Tipo Inter�s Datos Financieros,";

					} else if (null != contratofinanciacion.getDatosFinancieros().getVariabilidadTipoInteres()

							.getTopeMaximoInteresNominal()

							|| Pattern.matches("S?|(\\d{1,3}(,\\d{1,10}))|\\d{1,3}", contratofinanciacion.getDatosFinancieros().getVariabilidadTipoInteres().getTopeMaximoInteresNominal())) {

						String mensaje = "Diferencial Fijo Variabilidad Tipo Inter�s Datos Financieros,";
					}

				}

				for (ReconocimientoDeudaFinanciacionType reconocimientoDeudaFinan : contratofinanciacion.getDatosFinancieros().getReconocimientoDeuda()) {

					if (null != reconocimientoDeudaFinan.getImporteReconocido()

							|| Pattern.matches("S?|(\\d{1,10}(,\\d{1,2}))|\\d{1,10}", reconocimientoDeudaFinan.getImporteReconocido())) {

						String mensaje = "Importe Reconocido Reconocimiento Deuda,";

					} else if (null != reconocimientoDeudaFinan.getNumeroPlazos()

							|| Pattern.matches("S?|\\d{1,15}", reconocimientoDeudaFinan.getNumeroPlazos())) {

						String mensaje = "N�mero Plazos Reconocimiento Deuda,";

					} else if (null != reconocimientoDeudaFinan.getImportePlazos()

							|| Pattern.matches("S?|\\d{1,2}", reconocimientoDeudaFinan.getImportePlazos())) {

						String mensaje = "Importe Plazos Reconocimiento Deuda,";

					}

				}

				if (null != contratofinanciacion.getDatosFinancieros().getDomicilioPago()) {

					String mensaje = "Domicilio Pago Datos Financieros,";

				}

				if (null != contratofinanciacion.getDatosFinancieros().getDomicilioPago().getEntidadPago()) {

					String mensaje = "Entidad Pago Datos Financieros,";
				}

				if (null != contratofinanciacion.getDatosFinancieros().getDomicilioPago().getDomicilioEntidadPago()) {

					String mensaje = "Domicilio Entidad Pago Datos Financieros,";
				}

				if (null != contratofinanciacion.getDatosFinancieros().getDomicilioPago().getMunicipio()) {

					String mensaje = "Municipio Datos Financieros,";
				}

				if (null != contratofinanciacion.getDatosFinancieros().getDomicilioPago().getProvincia()) {

					String mensaje = "Provincia Datos Financieros,";
				}

				if (null != contratofinanciacion.getDatosFinancieros().getDomicilioPago().getNumeroCuentaCorriente()) {

					String mensaje = "N�mero Cuenta Corriente Datos Financieros,";
				}

				if (null != contratofinanciacion.getDatosFinancieros().getDomicilioPago().getPorcentajeTAE()) {

					String mensaje = "PorcentajeTAE Datos Financieros,";
				}

				if (null != contratofinanciacion.getDatosFinancieros().getCuadroAmortizacion()) {

					String mensaje = "";

					for (PlazoFinanciacionType plazoFinan : contratofinanciacion.getDatosFinancieros().getCuadroAmortizacion().getPlazo()) {

						if (null != plazoFinan.getFechaVencimiento()) {

							mensaje += "Fecha Vencimiento,";

						}

						if (null != plazoFinan.getImportePlazo()) {

							mensaje += "Importe Plazo Cuadro Amortizaci�n,";

						}

						if (null != plazoFinan.getImporteCapitalAmortizado()) {

							mensaje += "Importe Capital Amortizado Cuadro Amortizaci�n,";
						}

						if (null != plazoFinan.getImporteInteresesPlazo()) {

							mensaje += "Importe Intereses Plazo Cuadro Amortizaci�n,";
						}

						if (null != plazoFinan.getIntsDevengadosIncorpAlCapital()) {

							mensaje += "Intereses Devengados Incorporados Al Capital Cuadro Amortizaci�n,";
						}

						if (null != plazoFinan.getImporteCapitalPendiente()) {

							mensaje += "Importe Capital Pendiente Cuadro Amortizaci�n,";
						}

					}

				}
			}

			// CONDICIONES PARTICULARES.

			if (null != contratofinanciacion.getCondicionesParticulares() && null != contratofinanciacion.getCondicionesParticulares().getPactos()

					|| Pattern.matches("S?|[A-Z]{3}", (CharSequence) contratofinanciacion.getCondicionesParticulares().getPactos())) {

				String mensaje = "";

				mensaje += "Pactos,";

				for (PactoType pacto : contratofinanciacion.getCondicionesParticulares().getPactos().getPacto()) {

					if (null != pacto.getTipoPacto()

							|| Pattern.matches("S?|[A-Z]{3}", (CharSequence) pacto.getTipoPacto())) {

						mensaje += "Tipo Pacto,";

					} else if (null != pacto.getPactadoSN()

							|| Pattern.matches("S?|SI|NO", pacto.getPactadoSN())) {

						mensaje += "Pactado SN,";

					}

				}

			} else if (null != contratofinanciacion.getCondicionesParticulares().getCesion().getCesionAPersonaNODeterminada()

					|| Pattern.matches("S?|SI|NO", contratofinanciacion.getCondicionesParticulares().getCesion().getCesionAPersonaNODeterminada())) {

				String mensaje = "";
				mensaje += "Cesi�n A Persona No Determinada";

			} else if (null != contratofinanciacion.getCondicionesParticulares().getCesion().getTipoCesionTercero()

					|| Pattern.matches("S?|[A-Z]{2,3}", contratofinanciacion.getCondicionesParticulares().getCesion().getTipoCesionTercero())) {

				String mensaje = "";
				mensaje += "Tipo Cesi�n Tercero,";

			} else if (null != contratofinanciacion.getCondicionesParticulares().getCesion().getCesionarios().getCesionario()) {

				String mensaje = "";
				mensaje += "N�mero Cl�usula,";

			} else if (null != contratofinanciacion.getCondicionesParticulares().getClausulasParticulares().getClausula()) {

				for (ClausulaType clausula : contratofinanciacion.getCondicionesParticulares().getClausulasParticulares().getClausula()) {

					if (null != clausula.getNumeroClausula()

							|| Pattern.matches("S?|\\d{1,15}", clausula.getNumeroClausula())) {

						String mensaje = "";
						mensaje += "N�mero Cl�usula,";

					} else if (null != clausula.getNombreClausula()) {

						String mensaje = "";
						mensaje += "Nombre Cl�usula,";

					} else if (null != clausula.getDescripcionClausula()) {

						String mensaje = "";
						mensaje += "Descripci�n Cl�usula,";
					}
				}
			}

			// CONDICIONES GENERALES.

			if (null != contratofinanciacion.getCondicionesGenerales().getClausula()) {

				for (ClausulaType clausula : contratofinanciacion.getCondicionesGenerales().getClausula()) {

					if (null != clausula.getNumeroClausula()

							|| Pattern.matches("S?|\\d{1,15}", clausula.getNumeroClausula())) {

						String mensaje = "";
						mensaje += "N�mero Cl�usula,";

					} else if (null != clausula.getNombreClausula()) {

						String mensaje = "";
						mensaje += "Nombre Cl�usula,";

					} else if (null != clausula.getDescripcionClausula()) {

						String mensaje = "";
						mensaje += "Descripci�n Cl�usula,";
					}

				}
			}
			// FIRMAS.
			/*
			 * }else if( null != contratofinanciacion.getFirmas().getFirma() || contratofinanciacion.getFirmas().getFirma().isEmpty()){ mensaje += "Firma,"; }else if (null != contratofinanciacion.getFirmas().getFirma()){ for (FirmaType firma : contratofinanciacion.getFirmas().getFirma()){ if (null
			 * != firma.getLugarFirma()){ mensaje += "Lugar Firma,"; }else if (null != firma.getFechaFirma() || Pattern.matches("S?|\\d{1,15}", firma.getFechaFirma())){ mensaje += "Fecha Firma,"; }else if (null != firma.getTipoIntervencion() || Pattern.matches("S?|[A-Z]{1,2}",
			 * firma.getTipoIntervencion())){ mensaje += "Tipo Intervenci�n,"; }else if (null != firma.getDerechoDesistimiento() || Pattern.matches("S?|SI|NO", firma.getDerechoDesistimiento())){ mensaje += "Derecho Desistimiento,"; }else if (null== firma.getConsumidor() ||
			 * Pattern.matches("S?|SI|NO", firma.getConsumidor())){ mensaje += "Consumidor,"; }else if (null== firma.getInformado() || Pattern.matches("S?|SI|NO", firma.getInformado())){ mensaje += "Informado,"; }else if (null== firma.getTipoFirma() || Pattern.matches("S?|\\d{1,1}",
			 * firma.getTipoFirma())){ mensaje += "Informado,"; }
			 */
		}
		return resultado;
	}

	private static String validaDomicilioINE(DomicilioINEType domicilio) {
		String mensaje = "";
		if (null == domicilio.getPais()

				|| Pattern.matches("S?|[A-Z]{2}", domicilio.getPais())) {

			mensaje = "Pa�s,";

		} else if (null == domicilio.getEstadoRegionProvinciaExtranjera()) {

			mensaje = "Estado - Regi�n - Provincia Extranjera,";

		} else if (null == domicilio.getCodProvincia()

				|| Pattern.matches("S?|\\d{1,2}", (CharSequence) domicilio.getCodProvincia())) {

			mensaje = "C�digo Provincia,";

		} else if (null == domicilio.getCodMunicipio()

				|| Pattern.matches("S?|\\d{1,4}", (CharSequence) domicilio.getCodMunicipio())) {

			mensaje = "C�digo Municipio,";

		} else if (null == domicilio.getEntidadLocalMenor()) {

			mensaje = "Entidad Local Menor,";

		} else if (null == domicilio.getCodTipoVia()

				|| Pattern.matches("S?|[A-Z]{1,10}", (CharSequence) domicilio.getCodTipoVia())) {

			mensaje = "C�digo Tipo V�a,";

		} else if (null == domicilio.getNombreVia()) {

			mensaje = "Nombre V�a,";

		} else if (null == domicilio.getPuntoKilometrico()) {

			mensaje = "Punto Kilom�trico,";

		} else if (null == domicilio.getNumero()

				|| Pattern.matches("S?|\\d{1,15}", (CharSequence) domicilio.getNumero())) {

			mensaje = "N�mero,";

		} else if (null == domicilio.getNumeroBis()) {

			mensaje = "N�mero Bis,";

		} else if (null == domicilio.getPortal()) {

			mensaje = "Portal,";

		} else if (null == domicilio.getBloque()) {

			mensaje = "Bloque,";

		} else if (null == domicilio.getEscalera()) {

			mensaje = "Escalera,";

		} else if (null == domicilio.getPlanta()) {

			mensaje = "Planta,";

		} else if (null == domicilio.getPuerta()) {

			mensaje = "Puerta,";

		} else if (null == domicilio.getCodigoPostal()) {

			mensaje = "C�digo Postal,";
		}
		return mensaje;
	}

	public static ResultRegistro validaIdentificacionSujetoFinanciacion(IdentificacionContratoType identificacionSujeto, TipoTramiteRegistro tipoContrato) {
		// TODO Auto-generated method stub

		ResultRegistro resultado = new ResultRegistro();
		if (null != identificacionSujeto) {
			String mensaje = "";
			if (StringUtils.isBlank(identificacionSujeto.getNumeroOperacion()) || !NIFValidator.isValidNIF(identificacionSujeto.getNumeroOperacion().toUpperCase())) {
				mensaje += "N�mero de Operacion,";
			}
			if (NIFValidator.isValidDniNieCif(identificacionSujeto.getNombreAsociacion().toUpperCase()) == 0) {
				if (StringUtils.isBlank(identificacionSujeto.getNombreDocumento())) {
					mensaje += "Nombre Asociaci�n,";
				}
			}
			if (StringUtils.isBlank(identificacionSujeto.getNumeroEjemplar())) {
				mensaje += "N�mero Ejemplar,";
			}
			if (StringUtils.isBlank(identificacionSujeto.getModeloContrato())) {

				mensaje += "Modelo de Contrato,";
			}
			if (StringUtils.isBlank(identificacionSujeto.getNombreDocumento())) {

				mensaje += "Nombre de Documento,";
			}
			if (StringUtils.isBlank(identificacionSujeto.getAprobadoDGRN())) {

				mensaje += "Aprobado DGRN,";
			}
			if (StringUtils.isBlank(identificacionSujeto.getNumeroImpreso())) {

				mensaje += "N�mero Impreso,";
			}
			// if( !mensaje.isEmpty()){
			// resultado.setMensaje("Error en la identificaci�n del sujeto, faltan o son erroneos los siguientes campos: " + mensaje.substring(0,mensaje.length() -1));
			// }
		} else {
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("La identificaci�n del sujeto viene vac�a, ");
		}
		return resultado;
	}

	// IMPLEMENTED JVG. 22/05/2018.

	public static ResultRegistro validaDatosFinancierosFinan(DatosFinancierosFinanciacionType datosFinancieros, TipoTramiteRegistro tipoContrato) {
		// TODO Auto-generated method stub

		ResultRegistro resultado = new ResultRegistro();
		if (null != datosFinancieros) {

			String mensaje = "";
			if ((StringUtils.isBlank(datosFinancieros.getUnidadCuenta()))) {
				mensaje += "Unidad Cuenta Datos Financieros,";
			}
			if ((StringUtils.isBlank(datosFinancieros.getImportePrecioCompraventa()))) {

				mensaje += "Importe Precio Compraventa Datos Financieros,";
			}

			if (StringUtils.isBlank(datosFinancieros.getImporteDesembolsoInicial())) {

				mensaje += "Importe Desembolso Inicial Datos Financieros,";
			}

			if (StringUtils.isBlank(datosFinancieros.getImporteCapitalPrestamo())) {

				mensaje += "Importe Capital Pr�stamo Datos Financieros,";
			}

			if (StringUtils.isBlank(datosFinancieros.getImporteTotalPrestamo())) {

				mensaje += "Importe Total Pr�stamo Datos Financieros,";
			}

			if (StringUtils.isBlank(datosFinancieros.getImporteIntereses())) {

				mensaje += "Importe Intereses Datos Financieros,";
			}

			if (StringUtils.isBlank(datosFinancieros.getTipoDeudor())) {

				mensaje += "Tipo Deudor Datos Financieros,";
			}

			if (StringUtils.isBlank(datosFinancieros.getTipoInteresNominalAnual())) {

				mensaje += "Tipo Inter�s Nominal Anual Datos Financieros,";
			}

			if (StringUtils.isBlank(datosFinancieros.getInteresesDemora())) {

				mensaje += "Intereses Demora Datos Financieros,";
			}

			if (StringUtils.isBlank(datosFinancieros.getNumeroMeses())) {

				mensaje += "N�mero Meses Datos Financieros,";
			}

			if (StringUtils.isBlank(datosFinancieros.getFechaUltimoVencimiento())) {

				mensaje += "Fecha �ltimo Vencimiento Datos Financieros,";
			}

			if (StringUtils.isBlank(datosFinancieros.getImporteImpuestoMatriculacion())) {

				mensaje += "Importe Impuesto Matriculaci�n Datos Financieros,";
			}

			if (StringUtils.isBlank(datosFinancieros.getImporteCancelacionRDLD())) {

				mensaje += "Importe Cancelaci�n RDLD Datos Financieros,";
			}

			if (StringUtils.isBlank(datosFinancieros.getImporteTotalAdeudado())) {

				mensaje += "Importe Total Adeudado Datos Financieros,";
			}

			if (StringUtils.isBlank(datosFinancieros.getImporteDerechoDesistimiento())) {

				mensaje += "Importe Derecho Desistimiento Datos Financieros,";
			}
			/*
			 * PENDING FOR VALIDATE IN SCHEMA XSD-XML. DATE COMISIONTYPE. if(StringUtils.isBlank(datosFinancieros.getComisiones().getComision())){ mensaje += "Tipo Deudor Datos Financieros,"; }
			 */
			if (null == datosFinancieros.getOtrosImportes().getOtroImporte()) {

				mensaje += "Tipo Deudor Datos Financieros,";
			}

			if (null == datosFinancieros.getVariabilidadTipoInteres().getFechaRevision()) {

				mensaje += "Fecha Revisi�n Variabilidad Tipo Inter�s,";

			} else if (null == datosFinancieros.getVariabilidadTipoInteres().getPeriodoRevision()) {

				mensaje += "Per�odo Revisi�n Variabilidad Tipo Inter�s,";

			} else if (null == datosFinancieros.getVariabilidadTipoInteres().getTipoInteresReferencia()) {

				mensaje += "Tipo Inter�s Referencia Variabilidad Tipo Inter�s,";

			} else if (null == datosFinancieros.getVariabilidadTipoInteres().getDiferencialFijo()) {

				mensaje += "Diferencial Fijo Variabilidad Tipo Inter�s,";

			} else if (null == datosFinancieros.getVariabilidadTipoInteres().getTopeMaximoInteresNominal()) {

				mensaje += "Tope M�ximo Inter�s Nominal Variabilidad Tipo Inter�s,";
			}

			if (null == datosFinancieros.getReconocimientoDeuda()) {

				mensaje += "Reconocimiento Deuda,";
			}

			if (null == datosFinancieros.getDomicilioPago().getEntidadPago()) {

				mensaje += "Entidad Pago Domicilio,";
			}

			if (null == datosFinancieros.getDomicilioPago().getDomicilioEntidadPago()) {

				mensaje += "Domicilio Entidad Pago Domicilio,";
			}

			if (null == datosFinancieros.getDomicilioPago().getMunicipio()) {

				mensaje += "Municipio Domicilio Pago,";
			}

			if (null == datosFinancieros.getDomicilioPago().getProvincia()) {

				mensaje += "Provincia Domicilio Pago,";
			}

			if (null == datosFinancieros.getDomicilioPago().getNumeroCuentaCorriente()) {

				mensaje += "N�mero Cuenta Corriente Domicilio Pago,";
			}

			if (null == datosFinancieros.getDomicilioPago().getPorcentajeTAE()) {

				mensaje += "PorcentajeTAE Domicilio Pago,";
			}

		} else {
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("La identificaci�n del sujeto viene vacia, ");
		}
		return resultado;
	}

	// IMPLEMENTED JVG. 23/05/2018.

	public static ResultRegistro validaCondicionesParticulares(CondicionesParticularesType condicionesParticulares, TipoTramiteRegistro tipoContrato) {
		// TODO Auto-generated method stub
		ResultRegistro resultado = new ResultRegistro();

		if (null != condicionesParticulares) {
			String mensaje = "";
			List<CesionType> lista = new ArrayList<CesionType>();
			List<CondicionesParticularesType> condicionesParticularesLista = new ArrayList<CondicionesParticularesType>();
			List<CondicionesParticularesType> clausulasParticularesLista = new ArrayList<CondicionesParticularesType>();

			for (CondicionesParticularesType elemento : condicionesParticularesLista) {

				if (condicionesParticulares.getPactos().getPacto().isEmpty()) {

					mensaje += "Pactos Condiciones Particulares,";
					condicionesParticularesLista.add(elemento);
				}
			}

			for (CesionType elemento : lista) {

				if (condicionesParticulares.getCesion().getCesionAPersonaNODeterminada().isEmpty()) {
					mensaje += "Cesi�n A Persona No Determinada Condiciones Particulares,";
					lista.add(elemento);
				}

				if (condicionesParticulares.getCesion().getTipoCesionTercero().isEmpty()) {

					mensaje += "Tipo Cesi�n Tercero Condiciones Particulares,";
					lista.add(elemento);
				}
			}

			for (CondicionesParticularesType elemento : clausulasParticularesLista) {

				if (condicionesParticulares.getClausulasParticulares().getClausula().isEmpty()) {

					mensaje += "Cl�usula Condiciones Particulares,";
					clausulasParticularesLista.add(elemento);
				}
			}

			if (!mensaje.isEmpty()) {
				resultado.setError(Boolean.TRUE);
				resultado.setMensaje("Error en los datos del registro de Financiaci�n, faltan o son erroneos los siguientes campos: " + mensaje.substring(0, mensaje.length() - 1));
			}
		}

		return resultado;
	}

	// IMPLEMENTED JVG. 23/05/2018.

	public static ResultRegistro validaCondicionesGenerales(CondicionesGeneralesType condicionesGenerales, TipoTramiteRegistro tipoContrato) {
		// TODO Auto-generated method stub
		ResultRegistro resultado = new ResultRegistro();

		if (null != condicionesGenerales) {
			String mensaje = "";

			List<ClausulaType> lista = new ArrayList<ClausulaType>();

			for (ClausulaType elemento : lista) {

				if (condicionesGenerales.getClausula().isEmpty()) {

					mensaje += "Cl�usula Condiciones Generales,";
					lista.add(elemento);
				}
			}

			if (!mensaje.isEmpty()) {
				resultado.setError(Boolean.TRUE);
				resultado.setMensaje("Error en los datos del registro de Financiaci�n, faltan o son erroneos los siguientes campos: " + mensaje.substring(0, mensaje.length() - 1));
			}
		}

		return resultado;
	}

	// END IMPLEMENTED.

	public static ResultRegistro validaDomicilioFinanciacIne(DomicilioINEType domicilio, TipoTramiteRegistro tipoContrato) {
		ResultRegistro resultado = new ResultRegistro();
		if (null != domicilio) {
			String mensaje = "";
			if (!Pattern.matches("S?|[A-Z]{2}", domicilio.getPais()) || domicilio.getPais().trim().isEmpty()) {
				domicilio.setPais("ES");
			}
			if (!domicilio.getPais().equalsIgnoreCase("ES")) {
				if (domicilio.getEstadoRegionProvinciaExtranjera().isEmpty()) {
					mensaje += "Regi�n o provincia extranjera,";
				}
			}
			if (!Pattern.matches("\\d{2}", domicilio.getCodProvincia())) {
				mensaje += "C�digo provincia,";
			}
			if (!Pattern.matches("\\d{3}", domicilio.getCodMunicipio())) {
				mensaje += "C�digo municipio,";
			}
			if (!Pattern.matches("\\d{5}", domicilio.getCodigoPostal())) {
				mensaje += "C�digo postal,";
			}
			if (!mensaje.isEmpty()) {
				resultado.setError(Boolean.TRUE);
				resultado.setMensaje("Error en el domicilio, faltan o son erroneos los siguientes campos: " + mensaje.substring(0, mensaje.length() - 1));
			}
		} else {
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("El domicilio viene vac�o, ");
		}
		return resultado;
	}

	// IMPLEMENTED JVG. 10/05/2018.

	public static ResultRegistro validaRegistroFinanciacion(RegistroType registro, TipoTramiteRegistro tipoContrato) {
		// TODO Auto-generated method stub
		ResultRegistro resultado = new ResultRegistro();

		if (null != registro) {
			String mensaje = "";
			if (!Pattern.matches("\\d{2}", registro.getCodProvincia())) {
				mensaje += "C�digo provincia,";
			}
			if (!Pattern.matches("S?|\\d{1,5}", registro.getCodRegistro())) {
				mensaje += "C�digo registro,";
			}
			if (!mensaje.isEmpty()) {
				resultado.setError(Boolean.TRUE);
				resultado.setMensaje("Error en los datos del registro de Financiaci�n, faltan o son erroneos los siguientes campos: " + mensaje.substring(0, mensaje.length() - 1));
			}
		}

		return resultado;
	}

	public static ResultRegistro validaObjetosFinanciados(List<ObjetoFinanciadoType> objetosFinanciados, TipoTramiteRegistro tipoContrato) {
		// TODO Auto-generated method stub

		ResultRegistro resultado = new ResultRegistro();
		List<ObjetoFinanciadoType> lista = new ArrayList<ObjetoFinanciadoType>();

		String mensajeSalida = "";
		if (null != objetosFinanciados && !objetosFinanciados.isEmpty()) {
			String mensaje = "";
			int numero = 0;
			for (ObjetoFinanciadoType elemento : objetosFinanciados) {
				mensaje = validaBienFinanciacion(elemento);
				numero++;
				if (!mensaje.isEmpty()) {

					mensajeSalida += "Error en los datos de los Objetos Financiados " + numero + ", faltan o son erroneos los siguientes campos: " + mensaje.substring(0, mensaje.length() - 1);
				} else {
					lista.add(elemento);
				}
			}
		} else {
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("No tiene Objetos Financiados");
		}
		if (!mensajeSalida.isEmpty()) {
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje(mensajeSalida);
		}
		objetosFinanciados = lista;
		return resultado;
	}

	// IMPLEMENTED JVG. 21/05/2018.

	public static ResultRegistro validaComprador(List<IntervinienteType> compradores, TipoTramiteRegistro tipoContrato) {
		// TODO Auto-generated method stub
		ResultRegistro resultado = new ResultRegistro();
		List<IntervinienteType> lista = new ArrayList<IntervinienteType>();

		String mensajeSalida = "";

		if (null != compradores && !compradores.isEmpty()) {
			String mensaje = "";
			int numero = 0;
			for (IntervinienteType elemento : compradores) {
				mensaje = validaCompradorFinanciacion(elemento);
				numero++;
				if (!mensaje.isEmpty()) {
					mensajeSalida += "Error en los datos del Comprador " + numero + ", faltan o son erroneos los siguientes campos: " + mensaje.substring(0, mensaje.length() - 1);
				} else {
					lista.add(elemento);
				}
			}
		} else {
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("No tiene Comprador");
		}
		if (!mensajeSalida.isEmpty()) {
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje(mensajeSalida);
		}

		compradores = lista;
		return resultado;

	}

	public static ResultRegistro validaFiador(List<IntervinienteFiadorType> fiador, TipoTramiteRegistro tipoContrato) {
		// TODO Auto-generated method stub
		ResultRegistro resultado = new ResultRegistro();
		List<IntervinienteFiadorType> lista = new ArrayList<IntervinienteFiadorType>();

		String mensajeSalida = "";

		if (null != fiador && !fiador.isEmpty()) {
			String mensaje = "";
			int numero = 0;
			for (IntervinienteFiadorType elemento : fiador) {
				mensaje = validaFiadorFinanciacion(elemento);
				validaFiadorFinanciacion(elemento);
				numero++;
				if (!mensaje.isEmpty()) {

					mensajeSalida += "Error en los datos del Fiador " + numero + ", faltan o son erroneos los siguientes campos: " + mensaje.substring(0, mensaje.length() - 1);
				} else {
					lista.add(elemento);
				}
			}
		} else {
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("No tiene Objetos Financiados");
		}
		if (!mensajeSalida.isEmpty()) {
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje(mensajeSalida);
		}

		fiador = lista;
		return resultado;

	}

	public static ResultRegistro validaFinanciador(List<IntervinienteType> financiadores, TipoTramiteRegistro tipoContrato) {
		// TODO Auto-generated method stub
		ResultRegistro resultado = new ResultRegistro();
		List<IntervinienteType> lista = new ArrayList<IntervinienteType>();

		String mensajeSalida = "";

		if (null != financiadores && !financiadores.isEmpty()) {
			String mensaje = "";
			int numero = 0;
			for (IntervinienteType elemento : financiadores) {
				mensaje = validaFinanciadorFinan(elemento);
				validaFinanciadorFinan(elemento);
				numero++;
				if (!mensaje.isEmpty()) {

					mensajeSalida += "Error en los datos del Financiador " + numero + ", faltan o son erroneos los siguientes campos: " + mensaje.substring(0, mensaje.length() - 1);
				} else {
					lista.add(elemento);
				}
			}
		} else {
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("No tiene Financiador.");
		}
		if (!mensajeSalida.isEmpty()) {
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje(mensajeSalida);
		}

		financiadores = lista;
		return resultado;

	}

	public static ResultRegistro validaVendedor(List<VendedorType> vendedores, TipoTramiteRegistro tipoContrato) {
		// TODO Auto-generated method stub
		ResultRegistro resultado = new ResultRegistro();
		List<VendedorType> lista = new ArrayList<VendedorType>();

		String mensajeSalida = "";

		if (null != vendedores && !vendedores.isEmpty()) {
			String mensaje = "";
			int numero = 0;
			for (VendedorType elemento : vendedores) {
				mensaje = validaVendedorFinan(elemento);
				validaVendedorFinan(elemento);
				numero++;
				if (!mensaje.isEmpty()) {

					mensajeSalida += "Error en los datos del Vendedor " + numero + ", faltan o son erroneos los siguientes campos: " + mensaje.substring(0, mensaje.length() - 1);
				} else {
					lista.add(elemento);
				}
			}
		} else {
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("No tiene Vendedor.");
		}
		if (!mensajeSalida.isEmpty()) {
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje(mensajeSalida);
		}

		vendedores = lista;
		return resultado;

	}

}
