package trafico.generarPeticiones.ivtm;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.Normalizer;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.gestoresmadrid.core.contrato.model.vo.ContratoVO;
import org.gestoresmadrid.core.personas.model.enumerados.TipoPersona;
import org.gestoresmadrid.oegam2comun.trafico.dto.ivtm.IvtmConsultaMatriculacionDto;
import org.gestoresmadrid.oegam2comun.trafico.dto.ivtm.IvtmMatriculacionDto;
import org.gestoresmadrid.oegamComun.acceso.service.impl.UtilesColegiado;
import org.gestoresmadrid.utilidades.components.GestorPropiedades;
import org.jfree.util.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import es.map.scsp.esquemas.datosespecificos.DatosEspecificosAltaProyectoIVTMEntrada;
import es.map.scsp.esquemas.datosespecificos.DatosEspecificosConsultaDeudaIVTMReq;
import es.map.scsp.esquemas.datosespecificos.DatosEspecificosModificacionProyectoIVTMEntrada;
import es.map.scsp.esquemas.datosespecificos.DomicilioTipo;
import es.map.scsp.esquemas.datosespecificos.SujetoType;
import es.map.scsp.esquemas.datosespecificos.VehiculoType;
import es.map.scsp.esquemas.v2.Atributos;
import es.map.scsp.esquemas.v2.Emisor;
import es.map.scsp.esquemas.v2.Estado;
import es.map.scsp.esquemas.v2.Funcionario;
import es.map.scsp.esquemas.v2.Solicitante;
import es.map.scsp.esquemas.v2.Titular;
import es.map.scsp.esquemas.v2.peticion.DatosGenericos;
import es.map.scsp.esquemas.v2.peticion.Transmision;
import es.map.scsp.esquemas.v2.peticion.altaivtm.Peticion;
import es.map.scsp.esquemas.v2.peticion.altaivtm.SolicitudTransmision;
import es.map.scsp.esquemas.v2.peticion.altaivtm.Solicitudes;
//TODO MPC. Cambio IVTM. Clase añadida.
import hibernate.entities.personas.Direccion;
import hibernate.entities.personas.Municipio;
import hibernate.entities.personas.Persona;
import hibernate.entities.trafico.IntervinienteTrafico;
import hibernate.entities.trafico.TipoInterviniente;
import hibernate.entities.trafico.Vehiculo;
import trafico.dto.TramiteTraficoDto;
import trafico.dto.matriculacion.TramiteTrafMatrDto;
import trafico.modelo.ivtm.IVTMModeloMatriculacionInterface;
import trafico.modelo.ivtm.impl.IVTMModeloMatriculacionImpl;
import trafico.utiles.constantes.ConstantesIVTM;
import trafico.utiles.enumerados.Combustible;
import trafico.utiles.enumerados.TipoVehiculoIvtm;
import utilidades.estructuras.Fecha;
import utilidades.validaciones.NIFValidator;

public class GenerarPeticionIVTM {

	@Autowired
	private GestorPropiedades gestorPropiedades;

	@Autowired
	private UtilesColegiado utilesColegiado;

	public GenerarPeticionIVTM() {
		super();
		SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
	}

	/*
	* Métodos para crear las peticiones al WS
	*
	*/
	public Peticion damePeticionAlta(TramiteTraficoDto tramiteTrafico) {
		Peticion peticion = new Peticion();
		if (tramiteTrafico == null || tramiteTrafico.getTramiteTrafMatr() == null
				|| tramiteTrafico.getTramiteTrafMatr().getIvtmMatriculacionDto() == null) {
			return peticion;
		}
		TramiteTrafMatrDto tramiteMatriculacion = tramiteTrafico.getTramiteTrafMatr();
		IvtmMatriculacionDto ivtmMatriculacion = tramiteMatriculacion.getIvtmMatriculacionDto();
		peticion.setAtributos(obtenerAtributos(obtenerIdPeticion(ivtmMatriculacion), ConstantesIVTM.TIPO_ALTA_IVTM_WS));
		Solicitudes solicitudes = new Solicitudes();
		solicitudes.getSolicitudTransmision().add(obtenerSolicitud(tramiteTrafico));
		peticion.setSolicitudes(solicitudes);
		return peticion;
	}

	public es.map.scsp.esquemas.v2.peticion.modificacionivtm.Peticion damePeticionModificacion(
			TramiteTraficoDto tramiteTrafico) {
		es.map.scsp.esquemas.v2.peticion.modificacionivtm.Peticion peticion = new es.map.scsp.esquemas.v2.peticion.modificacionivtm.Peticion();
		if (tramiteTrafico == null || tramiteTrafico.getTramiteTrafMatr() == null
				|| tramiteTrafico.getTramiteTrafMatr().getIvtmMatriculacionDto() == null) {
			return peticion;
		}
		TramiteTrafMatrDto tramiteMatriculacion = tramiteTrafico.getTramiteTrafMatr();
		IvtmMatriculacionDto ivtmMatriculacion = tramiteMatriculacion.getIvtmMatriculacionDto();
		peticion.setAtributos(obtenerAtributos(obtenerIdPeticion(ivtmMatriculacion), ConstantesIVTM.TIPO_MODIFICACION_IVTM_WS));
		es.map.scsp.esquemas.v2.peticion.modificacionivtm.Solicitudes solicitudes = new es.map.scsp.esquemas.v2.peticion.modificacionivtm.Solicitudes();
		solicitudes.getSolicitudTransmision().add(obtenerSolicitudModificacion(tramiteTrafico));
		peticion.setSolicitudes(solicitudes);
		return peticion;
	}

	public es.map.scsp.esquemas.v2.peticion.consultaivtm.Peticion damePeticionConsulta(IvtmConsultaMatriculacionDto ivtmConsultaMatriculacionDto) {
		es.map.scsp.esquemas.v2.peticion.consultaivtm.Peticion peticion = new es.map.scsp.esquemas.v2.peticion.consultaivtm.Peticion();
		if (ivtmConsultaMatriculacionDto== null){
			return peticion;
		}
		peticion.setAtributos(obtenerAtributos(obtenerIdPeticion(ivtmConsultaMatriculacionDto), ConstantesIVTM.TIPO_CONSULTA_IVTM_WS));
		es.map.scsp.esquemas.v2.peticion.consultaivtm.Solicitudes solicitudes = new es.map.scsp.esquemas.v2.peticion.consultaivtm.Solicitudes();
		solicitudes.getSolicitudTransmision().add(obtenerSolicitudConsulta(ivtmConsultaMatriculacionDto));
		peticion.setSolicitudes(solicitudes);
		return peticion;
	}

	private SolicitudTransmision obtenerSolicitud (TramiteTraficoDto tramiteTrafico) {
		SolicitudTransmision solicitud = new SolicitudTransmision();
		IvtmMatriculacionDto ivtmMatriculacion = tramiteTrafico.getTramiteTrafMatr().getIvtmMatriculacionDto();
		solicitud.setDatosGenericos(obtenerDatosGenericos(obtenerIdPeticion(ivtmMatriculacion), obtenerNIFDelColegiado(ivtmMatriculacion.getNumExpediente()), ConstantesIVTM.TIPO_ALTA_IVTM_WS));
		if (tramiteTrafico != null && tramiteTrafico.getTramiteTrafMatr() != null
				&& tramiteTrafico.getTramiteTrafMatr().getIvtmMatriculacionDto() != null) {
			solicitud.setDatosEspecificos(obtenerDatosEspecificos(tramiteTrafico));
		}
		return solicitud;
	}

	private es.map.scsp.esquemas.v2.peticion.modificacionivtm.SolicitudTransmision obtenerSolicitudModificacion (TramiteTraficoDto tramiteTrafico) {
		es.map.scsp.esquemas.v2.peticion.modificacionivtm.SolicitudTransmision solicitud = new es.map.scsp.esquemas.v2.peticion.modificacionivtm.SolicitudTransmision();
		IvtmMatriculacionDto ivtmMatriculacion = tramiteTrafico.getTramiteTrafMatr().getIvtmMatriculacionDto();
		solicitud.setDatosGenericos(obtenerDatosGenericos(obtenerIdPeticion(ivtmMatriculacion), obtenerNIFDelColegiado(ivtmMatriculacion.getNumExpediente()), ConstantesIVTM.TIPO_MODIFICACION_IVTM_WS));
		if (tramiteTrafico != null && tramiteTrafico.getTramiteTrafMatr() != null
				&& tramiteTrafico.getTramiteTrafMatr().getIvtmMatriculacionDto() != null) {
			solicitud.setDatosEspecificos(obtenerDatosEspecificosModificacion(tramiteTrafico));
		}
		return solicitud;
	}

	private es.map.scsp.esquemas.v2.peticion.consultaivtm.SolicitudTransmision obtenerSolicitudConsulta(IvtmConsultaMatriculacionDto ivtmConsultaMatriculacionDto) {
		es.map.scsp.esquemas.v2.peticion.consultaivtm.SolicitudTransmision solicitud = new es.map.scsp.esquemas.v2.peticion.consultaivtm.SolicitudTransmision();
		solicitud.setDatosGenericos(obtenerDatosGenericos(obtenerIdPeticion(ivtmConsultaMatriculacionDto), obtenerNIFDelColegiado(ivtmConsultaMatriculacionDto.getNumColegiado()), ConstantesIVTM.TIPO_CONSULTA_IVTM_WS));
		solicitud.setDatosEspecificos(obtenerDatosEspecificosConsulta(ivtmConsultaMatriculacionDto));
		return solicitud;
	}

	private DatosEspecificosAltaProyectoIVTMEntrada obtenerDatosEspecificos(TramiteTraficoDto tramiteTrafico) {
		DatosEspecificosAltaProyectoIVTMEntrada datosEspecificos = new DatosEspecificosAltaProyectoIVTMEntrada();
		datosEspecificos.setDocautorizado(obtenerDocAutorizado(tramiteTrafico));
		List<IntervinienteTrafico> intervinientes = tramiteTrafico.getIntervinienteTraficos();
		SujetoType sujeto = null;
		SujetoType osujeto = null;
		for (IntervinienteTrafico interviniente : intervinientes) {
			TipoInterviniente tipo = interviniente.getTipoIntervinienteBean();
			String tipoInterviniente = tipo.getTipoInterviniente();
			if (tipoInterviniente.equals(org.gestoresmadrid.core.model.enumerados.TipoInterviniente.Titular.getValorEnum())) {
				sujeto = obtenerSujeto (interviniente);
			} else if (tipoInterviniente.equals(org.gestoresmadrid.core.model.enumerados.TipoInterviniente.RepresentanteTitular.getValorEnum())) {
				osujeto = obtenerSujeto (interviniente);
			}
		}
		if (osujeto != null) {
			datosEspecificos.setOsujeto(osujeto);
		}
		if (sujeto != null) {
			datosEspecificos.setSujeto(sujeto);
		}
		if (tramiteTrafico.getVehiculo() != null) {
			datosEspecificos.setVehiculo (obtenerVehiculo(tramiteTrafico.getVehiculo(), tramiteTrafico));
		}
		return datosEspecificos;
	}

	private DatosEspecificosModificacionProyectoIVTMEntrada obtenerDatosEspecificosModificacion(TramiteTraficoDto tramiteTrafico) {
		DatosEspecificosModificacionProyectoIVTMEntrada datosEspecificos = new DatosEspecificosModificacionProyectoIVTMEntrada();
		datosEspecificos.setDocautorizado(obtenerDocAutorizado(tramiteTrafico));
		List<IntervinienteTrafico> intervinientes = tramiteTrafico.getIntervinienteTraficos();
		SujetoType sujeto = null;
		SujetoType osujeto = null;
		for (IntervinienteTrafico interviniente : intervinientes) {
			TipoInterviniente tipo = interviniente.getTipoIntervinienteBean();
			String tipoInterviniente = tipo.getTipoInterviniente();
			if (tipoInterviniente.equals(org.gestoresmadrid.core.model.enumerados.TipoInterviniente.Titular.getValorEnum())) {
				sujeto = obtenerSujeto (interviniente);
			} else if (tipoInterviniente.equals(org.gestoresmadrid.core.model.enumerados.TipoInterviniente.RepresentanteTitular.getValorEnum())) {
				osujeto = obtenerSujeto (interviniente);
			}
		}
		if (osujeto != null) {
			datosEspecificos.setOsujeto(osujeto);
		}
		if (sujeto != null) {
			datosEspecificos.setSujeto(sujeto);
		}
		if (tramiteTrafico.getVehiculo() != null) {
			datosEspecificos.setVehiculo(obtenerVehiculo(tramiteTrafico.getVehiculo(), tramiteTrafico));
		}
		datosEspecificos.setNumautoliquidacion(tramiteTrafico.getTramiteTrafMatr().getIvtmMatriculacionDto().getNrc());
		return datosEspecificos;
	}

	private DatosEspecificosConsultaDeudaIVTMReq obtenerDatosEspecificosConsulta(IvtmConsultaMatriculacionDto ivtmConsultaMatriculacionDto) {
		DatosEspecificosConsultaDeudaIVTMReq datosEspecificos = new DatosEspecificosConsultaDeudaIVTMReq();
		datosEspecificos.setDocautorizado(obtenerDocAutorizado(ivtmConsultaMatriculacionDto));
		datosEspecificos.setMatricula(obtenerMatricula(ivtmConsultaMatriculacionDto));
		return datosEspecificos;
	}

	private String obtenerMatricula(IvtmConsultaMatriculacionDto ivtmConsultaMatriculacionDto) {
		if (ivtmConsultaMatriculacionDto != null && ivtmConsultaMatriculacionDto.getMatricula() != null) {
			return ivtmConsultaMatriculacionDto.getMatricula();
		}
		return "";
	}

	private VehiculoType obtenerVehiculo(Vehiculo vehiculo, TramiteTraficoDto tramiteTrafico) {
		VehiculoType vehiculoType = new VehiculoType();
		// Ponemos la fecha de pago/presentación del IVTM.
		if (tramiteTrafico == null || tramiteTrafico.getTramiteTrafMatr() == null
				|| tramiteTrafico.getTramiteTrafMatr().getIvtmMatriculacionDto() == null || vehiculo == null) {
			return null;
		}
		IvtmMatriculacionDto ivtmMatriculacion = tramiteTrafico.getTramiteTrafMatr().getIvtmMatriculacionDto();
		vehiculoType.setFchalta(obtenerFechaPago(ivtmMatriculacion.getFechaPago())); // TODO Hay que revisar cómo se generan las fechas
		vehiculoType.setFchpresentacion(obtenerFechaPresentacion()); // TODO Hay que revisar cómo se generan las fechas	
		vehiculoType.setNumbastidor(obtenerBastidor(vehiculo.getBastidor()));
		if (vehiculo.getMarcaDgt()!=null){
			vehiculoType.setMarca(obtenerMarca(vehiculo.getMarcaDgt().getMarca()));
		}
		vehiculoType.setModelo(obtenerModelo(vehiculo.getModelo()));
		IVTMModeloMatriculacionInterface ivtmModelo = new IVTMModeloMatriculacionImpl();
		if (vehiculo.getTipoVehiculoBean() != null && vehiculo.getTipoVehiculoBean().getTipoVehiculo() != null) {
			TipoVehiculoIvtm tipoVehiculo = ivtmModelo.obtenerTipoVehiculo(vehiculo.getTipoVehiculoBean().getTipoVehiculo());
			vehiculoType.setTipoveh(tipoVehiculo.getValorEnum());
			vehiculoType.setPotencia(obtenerPotencia(tipoVehiculo, vehiculo.getPotenciaFiscal()));
			vehiculoType.setPlazas(obtenerPlazas(tipoVehiculo, vehiculo.getPlazas(), vehiculo.getNPlazasPie()));
			vehiculoType.setTonelaje(obtenerTonelaje(tipoVehiculo, vehiculo.getPesoMma(), vehiculo.getTara(), vehiculo.getMom()));
			vehiculoType.setCubicaje(obtenerCubicaje(tipoVehiculo, vehiculo.getCilindrada()));
		}
		vehiculoType.setServicio(obtenerServicio(vehiculo.getIdServicio()));
		vehiculoType.setTipocarburante(obtenerCarburante(vehiculo.getIdCarburante()));
		vehiculoType.setCo2(obtenerCO2(vehiculo.getCo2()));
		if (ivtmMatriculacion.isBonmedam() != null) {
			vehiculoType.setBodmedamb(obtenerBonificacionMedioAmbiente(ivtmMatriculacion.isBonmedam()));
			vehiculoType.setPorcentajebodmedamb(obtenerPorcentajeBonificacionMedioAmbiente(ivtmMatriculacion.isBonmedam(), ivtmMatriculacion.getPorcentajebonmedam()));
		} else {
			vehiculoType.setBodmedamb(ConstantesIVTM.BONIFICACION_NO);
			vehiculoType.setPorcentajebodmedamb("");
		}
		vehiculoType.setIban(obtenerIban(ivtmMatriculacion.getIban()));
		return vehiculoType;
	}

	private String obtenerIban(String iban) {
		return iban;
	}

	private String obtenerPorcentajeBonificacionMedioAmbiente(boolean bonmedam, BigDecimal porcentajebonmedam) {
		DecimalFormat formatter = new DecimalFormat("000.00"); 
		if (bonmedam && porcentajebonmedam != null) {
			try {
				return formatter.format(Double.parseDouble(porcentajebonmedam.toString()));
			} catch (Exception e) {
				Log.error("El porcentaje de bonificación de medio ambiente no es un número");
			}
		}
		return "";
	}

	private String obtenerBonificacionMedioAmbiente(boolean bonmedam) {
		if (bonmedam) {
			return ConstantesIVTM.BONIFICACION_SI;
		}
		return ConstantesIVTM.BONIFICACION_NO;
	}

	private String obtenerCO2(String co2) {
		DecimalFormat formatter = new DecimalFormat("000");
		if (co2 != null && !"".equals(co2)) {
			try {
				return formatter.format(Integer.parseInt(co2));
			} catch (NumberFormatException e) {
				Log.error("El CO2 no es un número");
			}
		}
		return "";
	}

	private String obtenerCarburante(String carburante) {
		// Los carburantes del Enumerado se corresponden a los del IVTM
		if (carburante != null) {
			String c = Combustible.convertir(carburante).getValorEnum();
			// Si el combustible es Otros, el Ayuntamiento requiere la cadena vacía.
			if (Combustible.Otros.getValorEnum().equals(c)) {
				c = "";
			}
			return c;
		}
		return "";
	}

	private String obtenerServicio(String idServicio) {
		// Actualmente los valores que se guardan se corresponden con los que requiere el IVTM
		if (idServicio!= null){
			if (idServicio.length() > 3) {
				return idServicio.substring(0,3);
			}
			return idServicio;
		}
		return "";
	}

	private String obtenerCubicaje(TipoVehiculoIvtm tipoVehiculo, String cilindrada) {
		DecimalFormat formatter = new DecimalFormat("000000");
		if (tipoVehiculo == TipoVehiculoIvtm.Motocicletas || tipoVehiculo == TipoVehiculoIvtm.Ciclomotores) {
			try {
				if (cilindrada != null && !"".equals(cilindrada)) {
					return formatter.format(Double.parseDouble(cilindrada));
				}
			} catch (NumberFormatException e) {
			}
		}
		return "";
	}

	private String obtenerTonelaje(TipoVehiculoIvtm tipoVehiculo, String pesoMma, String tara, BigDecimal mom) {
		DecimalFormat formatter = new DecimalFormat("0000000");
		if (tipoVehiculo == TipoVehiculoIvtm.Camiones || tipoVehiculo == TipoVehiculoIvtm.Remolques) {
			if (pesoMma == null || "".equals(pesoMma)) {
				return "";
			}
			try {
				if (tara != null && !"".equals(tara)) {
					return formatter.format(Long.parseLong(pesoMma)-Long.parseLong(tara));
				}
			} catch (NumberFormatException e) {
				Log.error("La tara no es un número, y no se puede generar el tonelaje a partir de la tara.");
			}
			try {
				if (mom != null) {
					return formatter.format(Long.parseLong(pesoMma) - (Long.parseLong(mom.toString()) - 75));
				}
			} catch (NumberFormatException e) {
				Log.error("El MOM no es un número, y no se puede generar el tonelaje.");
			}
		}
		return "";
	}

	private String obtenerPlazas(TipoVehiculoIvtm tipoVehiculo, BigDecimal plazas, BigDecimal nPlazasPie) {
		// Se envía el número de plazas, pero observando la BD, aparecen casos en los que el número de plazas de pie es mayor que el número de plazas.
		// Por conversación con Jose, se decide usar el número de plazas.
		DecimalFormat formatter = new DecimalFormat("0000");
		if (tipoVehiculo == TipoVehiculoIvtm.Autobuses) {
			try {
				if (plazas != null) {
					return formatter.format(plazas);
				} else if (nPlazasPie != null) {
					return formatter.format(nPlazasPie);
				}
			} catch (NumberFormatException e) {
			}
		}
		return "";
	}

	private String obtenerPotencia(TipoVehiculoIvtm tipoVehiculo, BigDecimal potenciaFiscal) {
		DecimalFormat formatter = new DecimalFormat("0000.000");
		if ((tipoVehiculo == TipoVehiculoIvtm.Turismo || tipoVehiculo == TipoVehiculoIvtm.Tractores)
				&& potenciaFiscal != null) {
			// Se formatea el número para que envíe 4 enteros y 3 decimales
			return formatter.format(potenciaFiscal.doubleValue());
		}
		return "";
	}

	private String obtenerMarca(String marca) {
		if (marca != null) {
			if (marca.length() > 16) {
				return marca.substring(0, 16);
			}
			return marca;
		}
		return "";
	}

	private String obtenerModelo(String modelo) {
		if (modelo != null) {
			if (modelo.length() > 16) {
				return modelo.substring(0, 16);
			}
			return modelo;
		}
		return "";
	}

	private String obtenerBastidor(String bastidor) {
		if (bastidor == null) {
			return "";
		}
		return bastidor;
	}

	private String obtenerFechaPresentacion() {
		return new SimpleDateFormat("yyyyMMdd").format(new Date());
	}

	private String obtenerFechaPago(Fecha fechaPago) {
		if (fechaPago != null && !fechaPago.isfechaNula()) {
			try {
				return new SimpleDateFormat("yyyyMMdd").format(fechaPago.getFecha());
			} catch (ParseException e) {
				return new SimpleDateFormat("yyyyMMdd").format(new Date());
			}
		}
		return new SimpleDateFormat("yyyyMMdd").format(new Date());
	}

	private SujetoType obtenerSujeto(IntervinienteTrafico interviniente) {
		SujetoType sujeto = new SujetoType();
		if (interviniente == null || interviniente.getPersonaDireccion() == null
				|| interviniente.getPersonaDireccion().getPersona() == null) {
			return null;
		}
		Persona persona = interviniente.getPersonaDireccion().getPersona();
		if (persona.getId() == null || persona.getId().getNif() == null || "".equals(persona.getId().getNif())) {
			return null;
		}
		String nif = persona.getId().getNif();
		sujeto.setTipodocumento(getTipoDocumentoGA(nif));
		sujeto.setNumdocumento(nif);
		rellenarNombresyApellidos(sujeto, persona);
		sujeto.setDomicilio(obtenerDomicilio(interviniente.getPersonaDireccion().getDireccion()));
		sujeto.setEmail(obtenerEmail(persona.getCorreoElectronico()));
		sujeto.setTelefono1(obtenerTelefono(persona.getTelefonos()));
		sujeto.setTelefono2(""); // OEGAM no guarda un segundo teléfono
		return sujeto;
	}

	private String obtenerTelefono(String telefono) {
		if (telefono == null) {
			return "";
		}
		if (telefono.length() > 9) {
			telefono = telefono.substring(0, 9);
		}
		return telefono;
	}

	private String obtenerEmail(String correoElectronico) {
		if (correoElectronico == null) {
			return "";
		}
		if (correoElectronico.length() > 50) {
			correoElectronico = correoElectronico.substring(0, 50);
		}
		return correoElectronico;
	}

	private DomicilioTipo obtenerDomicilio(Direccion direccion) {
		if (direccion == null){
			return null;
		}
		DomicilioTipo domicilio = new DomicilioTipo();
		domicilio.setClasevial(obtenerClaseVial(direccion.getIdTipoVia()));
		domicilio.setNombrevial(obtenerNombreVia(direccion.getNombreVia()));
		rellenarAPP(domicilio, direccion);
		domicilio.setCalapp(obtenerLetra(direccion.getLetra()));
		domicilio.setPiso(obtenerPlanta(direccion.getPlanta()));
		domicilio.setPuerta(obtenerPuerta(direccion.getPuerta()));
		domicilio.setEscalera(obtenerEscalera(direccion.getEscalera()));
		domicilio.setCodpostal(obtenerCodigoPostal(direccion.getCodPostal()));
		rellenarPoblacionProvincia(domicilio, direccion.getMunicipio());
		return domicilio;
	}

	private void rellenarPoblacionProvincia(DomicilioTipo domicilio, Municipio municipio) {
		if (municipio == null) {
			domicilio.setPoblacion("");
			domicilio.setProvincia("");
		} else {
			if (municipio.getNombre() != null) {
				domicilio.setPoblacion(municipio.getNombre().length() > 70 ? municipio.getNombre().substring(0, 70) : municipio.getNombre());
			} else {
				domicilio.setPoblacion("");
			}
			if (municipio.getProvincia() != null && municipio.getProvincia().getNombre() != null) {
				if (municipio.getProvincia().getNombre().length() > 25) {
					domicilio.setProvincia(municipio.getProvincia().getNombre().substring(0, 25));
				} else {
					domicilio.setProvincia(municipio.getProvincia().getNombre());
				}
			} else {
				domicilio.setProvincia("");
			}
		}
	}

	private String obtenerCodigoPostal(String codPostal) {
		if (codPostal != null) {
			return codPostal.length() > 5 ? codPostal.substring(0, 5) : codPostal;
		}
		return "";
	}

	private String obtenerEscalera(String escalera) {
		if (escalera != null) {
			return escalera.length() > 4 ? escalera.substring(0, 4) : escalera;
		}
		return "";
	}

	private String obtenerPuerta(String puerta) {
		if (puerta != null) {
			return puerta.length() > 4 ? puerta.substring(0, 4) : puerta;
		}
		return "";
	}

	private String obtenerPlanta(String planta) {
		if (planta != null) {
			return planta.length() > 4 ? planta.substring(0, 4) : planta;
		}
		return "";
	}

	private String obtenerLetra(String letra) {
		if (letra != null) {
			return letra.length() > 2 ? letra.substring(0, 2) : letra;
		}
		return "";
	}

	private void rellenarAPP(DomicilioTipo domicilio, Direccion direccion) {
		if (direccion == null) {
			domicilio.setNomapp("");
			domicilio.setNumapp("");
		} else if (direccion.getNumero() != null && !direccion.getNumero().equals("")) {
			String numero = direccion.getNumero();
			if (numero.equalsIgnoreCase("SN") || numero.equalsIgnoreCase("S/N")) {
				if (direccion.getKm() != null) {
					domicilio.setNomapp("KM");
					if (direccion.getKm().toString().length() > 6) {
						domicilio.setNumapp(direccion.getKm().toString().substring(0, 6));
					} else {
						domicilio.setNumapp(direccion.getKm().toString());
					}
				} else {
					domicilio.setNomapp("S/N");
					domicilio.setNumapp("");
				}
			} else {
				domicilio.setNomapp("NUM");
				if (direccion.getNumero().length() > 6)
					domicilio.setNumapp(direccion.getNumero().substring(0, 6));
				else
					domicilio.setNumapp(direccion.getNumero());
			}
		} else if (direccion.getKm() != null) {
			domicilio.setNomapp("KM");
			if (direccion.getKm().toString().length() > 6) {
				domicilio.setNumapp(direccion.getKm().toString().substring(0, 6));
			} else {
				domicilio.setNumapp(direccion.getKm().toString());
			}
		} else {
			domicilio.setNomapp("");
			domicilio.setNomapp("");
		}
	}

	private String obtenerNombreVia(String nombreVia) {
		if (nombreVia == null) {
			return "";
		}
		return nombreVia.length() > 60 ? nombreVia.substring(0, 60) : nombreVia;
	}

	private String obtenerClaseVial(String idTipoVia) {
		if (idTipoVia == null){
			return "";
		}
		return idTipoVia.length() > 25 ? idTipoVia.substring(0, 25) : idTipoVia;
	}

	private void rellenarNombresyApellidos(SujetoType sujeto, Persona persona) {
		if (esPersonaFisica(persona.getTipoPersona())) {
			String nombre = stripAccents(persona.getNombre());

			sujeto.setNombre(nombre.length() > 20 ? nombre.substring(0, 20) : nombre);

			String apellido = stripAccents(persona.getApellido1RazonSocial());

			sujeto.setApellido1(apellido.length() > 25 ? apellido.substring(0, 25) : apellido);

			if (persona.getApellido2() != null) {
				if (stripAccents(persona.getApellido2()).length() > 25)
					sujeto.setApellido2(stripAccents(persona.getApellido2()).substring(0, 25));
				else
					sujeto.setApellido2(stripAccents(persona.getApellido2()));
			} else {
				sujeto.setApellido2("");
			}
			sujeto.setRazonsocial("");
		} else if (esPersonaJuridica(persona.getTipoPersona())) {
			String razonSocial = stripAccents(persona.getApellido1RazonSocial());
			if (razonSocial.length() > 71)
				sujeto.setRazonsocial(razonSocial.substring(0, 70));
			else
				sujeto.setRazonsocial(razonSocial);
			sujeto.setNombre("");
			sujeto.setApellido1("");
			sujeto.setApellido2("");
		} else {
			sujeto.setNombre("");
			sujeto.setApellido1("");
			sujeto.setApellido2("");
			sujeto.setRazonsocial("");
		}
	}

	private boolean esPersonaFisica(String tipoPersona) {
		return tipoPersona != null && tipoPersona.equalsIgnoreCase(TipoPersona.Fisica.getValorEnum());
	}

	private boolean esPersonaJuridica (String tipoPersona) {
		return tipoPersona != null && tipoPersona.equalsIgnoreCase(TipoPersona.Juridica.getValorEnum());
	}

	private String obtenerDocAutorizado(TramiteTraficoDto tramiteTrafico) {
		if (tramiteTrafico != null && tramiteTrafico.getNumExpediente() != null) {
			try {
				return obtenerNIFDelColegiado(new BigDecimal(tramiteTrafico.getNumExpediente()));
			} catch (NumberFormatException e) {
				return "";
			}
		}
		return "";
	}

	private String obtenerDocAutorizado(IvtmConsultaMatriculacionDto ivtmConsultaMatriculacionDto) {
		if (ivtmConsultaMatriculacionDto != null && ivtmConsultaMatriculacionDto.getNumColegiado() != null
				&& !"".equals(ivtmConsultaMatriculacionDto.getNumColegiado())) {
			return obtenerNIFDelColegiado(ivtmConsultaMatriculacionDto.getNumColegiado());
		}
		return "";
	}

	private DatosGenericos obtenerDatosGenericos (String idPeticion, String nif, String tipo) {
		DatosGenericos datosgenericos = new DatosGenericos();
		datosgenericos.setEmisor(obtenerEmisor());
		datosgenericos.setSolicitante(obtenerSolicitante());
		datosgenericos.setTitular(obtenerTitular(nif));
		datosgenericos.setTransmision(obtenerTransmision(idPeticion, tipo));
		return datosgenericos;
	}

	private Transmision obtenerTransmision(String idPeticion, String tipo) {
		Transmision transmision = new Transmision();
		transmision.setCodigoCertificado(tipo);
		transmision.setIdSolicitud(idPeticion);
		transmision.setFechaGeneracion(generarFechaHoyParaXML());
		return transmision;
	}

	private Solicitante obtenerSolicitante() {
		Solicitante solicitante = new Solicitante();
		solicitante.setConsentimiento(gestorPropiedades.valorPropertie(ConstantesIVTM.KEY_SOLICITANTE_CONSENTIMIENTO));
		solicitante.setFinalidad(gestorPropiedades.valorPropertie(ConstantesIVTM.KEY_SOLICITANTE_FINALIDAD));
		solicitante.setIdentificadorSolicitante(gestorPropiedades.valorPropertie(ConstantesIVTM.KEY_SOLICITANTE_IDENTIFICADOR));
		solicitante.setNombreSolicitante(gestorPropiedades.valorPropertie(ConstantesIVTM.KEY_SOLICITANTE_NOMBRE));
		solicitante.setFuncionario(obtenerFuncionario());
		return solicitante;
	}

	private Titular obtenerTitular (String nif) {
		Titular titular = new Titular();
		titular.setTipoDocumentacion(obtenerTipoDocumento(nif));
		titular.setDocumentacion(nif);
		return titular;
	}

	private String obtenerNIFDelColegiado(BigDecimal numExpediente) {
		return utilesColegiado.getNifColegiadoDelContrato(numExpediente);
	}

	private String obtenerNIFDelColegiado(String numColegiado) {
		if (numColegiado!=null && !"".equals(numColegiado)) {
			ContratoVO c = utilesColegiado.getDetalleColegiado(numColegiado);
			if (c != null) {
				return c.getCif();
			}
		}
		return "";
	}

	private String obtenerTipoDocumento(String documento) {
		if (NIFValidator.isValidDNI(documento)) {
			return "NIF";
		} else if (NIFValidator.isValidCIF(documento)) {
			return "CIF";
		} else if (NIFValidator.isValidNIE(documento)) {
			return "NIE";
		}
		return "";
	}

	private String obtenerIdPeticion(IvtmMatriculacionDto ivtmMatriculacion) {
		return ivtmMatriculacion.getIdPeticion() != null ? ivtmMatriculacion.getIdPeticion().toString() : "";
	}

	private String obtenerIdPeticion(IvtmConsultaMatriculacionDto ivtmConsulta) {
		return ivtmConsulta.getIdPeticion() != null ? ivtmConsulta.getIdPeticion().toString() : "";
	}

	private String generarFechaHoyParaXML() {
		return new java.text.SimpleDateFormat(ConstantesIVTM.DATE_FORMAT_TIMESTAMP).format(new java.util.Date());
	}

	private Atributos obtenerAtributos (String idPeticion, String tipo) {
		Atributos atributos = new Atributos();
		atributos.setIdPeticion(idPeticion);
		atributos.setNumElementos(ConstantesIVTM.NUMERO_ELEMENTOS_PETICION);
		atributos.setTimeStamp(generarFechaHoyParaXML());
		atributos.setCodigoCertificado(tipo);
		atributos.setEstado(obtenerEstado());
		return atributos;
	}

	private Estado obtenerEstado() {
		Estado estado = new Estado();
		estado.setCodigoEstado(gestorPropiedades.valorPropertie(ConstantesIVTM.KEY_CODE_INICIAL));
		estado.setTiempoEstimadoRespuesta(ConstantesIVTM.TIEMPO_ESTIMADO_RESPUESTA_XML);
		return estado;
	}

	private Funcionario obtenerFuncionario() {
		Funcionario funcionario = new Funcionario();
		funcionario.setNifFuncionario(gestorPropiedades.valorPropertie(ConstantesIVTM.KEY_FUNCIONARIO_NIF));
		funcionario.setNombreCompletoFuncionario(gestorPropiedades.valorPropertie(ConstantesIVTM.KEY_FUNCIONARIO_NOMBRE));
		return funcionario;
	}

	private Emisor obtenerEmisor() {
		Emisor emisor = new Emisor();
		emisor.setNifEmisor(gestorPropiedades.valorPropertie(ConstantesIVTM.KEY_AYUNTAMIENTO_NIF));
		emisor.setNombreEmisor(gestorPropiedades.valorPropertie(ConstantesIVTM.KEY_AYUNTAMIENTO_NOMBRE));
		return emisor;
	}

	/**
	 * Método que normaliza los caracteres acentuados
	 * 
	 */
	private String stripAccents(String strToStrip) {
		if (strToStrip == null) {
			return "";
		}
		String strStripped = null;
		// Normalizamos en la forma NFD (Canonical decomposition)
		strToStrip = Normalizer.normalize(strToStrip, Normalizer.Form.NFD);
		// Reemplazamos los acentos con una una expresión regular de Bloque Unicode
		strStripped = strToStrip.replaceAll("\\p{IsM}+", "");
		return strStripped;
	}

	public static String getTipoDocumentoGA(String doc) {
		if (NIFValidator.isValidDNI(doc)) {
			return "N";
		} else if (NIFValidator.isValidCIF(doc)) {
			return "C";
		} else if (NIFValidator.isValidNIE(doc)) {
			return "T";
		}
		return "";
	}

}