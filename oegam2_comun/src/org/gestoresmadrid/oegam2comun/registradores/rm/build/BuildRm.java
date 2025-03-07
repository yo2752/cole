package org.gestoresmadrid.oegam2comun.registradores.rm.build;

import java.io.File;
import java.io.Serializable;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.xml.bind.JAXBContext;

import org.apache.commons.lang.StringUtils;
import org.gestoresmadrid.core.registradores.model.enumerados.TipoPersonaRegistro;
import org.gestoresmadrid.core.registradores.model.enumerados.TipoTramiteRegistro;
import org.gestoresmadrid.oegam2comun.registradores.corpme.xml.XmlCORPMEFactory;
import org.gestoresmadrid.oegam2comun.registradores.enums.TipoCategoria;
import org.gestoresmadrid.oegam2comun.registradores.enums.TipoClausula;
import org.gestoresmadrid.oegam2comun.registradores.rm.corpme.CORPMERMCeseNombramiento;
import org.gestoresmadrid.oegam2comun.registradores.rm.corpme.CORPMERMCeseNombramiento.Modelo1;
import org.gestoresmadrid.oegam2comun.registradores.rm.corpme.CORPMERMCeseNombramiento.Modelo2;
import org.gestoresmadrid.oegam2comun.registradores.rm.corpme.CORPMERMCeseNombramiento.Modelo3;
import org.gestoresmadrid.oegam2comun.registradores.rm.corpme.CORPMERMCeseNombramiento.Modelo4;
import org.gestoresmadrid.oegam2comun.registradores.rm.corpme.CORPMERMCeseNombramiento.Modelo5;
import org.gestoresmadrid.oegam2comun.registradores.rm.corpme.CeseType;
import org.gestoresmadrid.oegam2comun.registradores.rm.corpme.CesesType;
import org.gestoresmadrid.oegam2comun.registradores.rm.corpme.ClausulaLOPDType;
import org.gestoresmadrid.oegam2comun.registradores.rm.corpme.ModeloType;
import org.gestoresmadrid.oegam2comun.registradores.rm.corpme.NombramientoType;
import org.gestoresmadrid.oegam2comun.registradores.rm.corpme.NombramientosType;
import org.gestoresmadrid.oegam2comun.registradores.rm.corpme.ObjectFactory;
import org.gestoresmadrid.oegam2comun.registradores.rm.rbm.CesionType;
import org.gestoresmadrid.oegam2comun.registradores.rm.rbm.CesionType.Cesionarios;
import org.gestoresmadrid.oegam2comun.registradores.rm.rbm.CesionarioType;
import org.gestoresmadrid.oegam2comun.registradores.rm.rbm.CondicionesGeneralesType;
import org.gestoresmadrid.oegam2comun.registradores.rm.rbm.CondicionesParticularesType;
import org.gestoresmadrid.oegam2comun.registradores.rm.rbm.CondicionesParticularesType.ClausulasParticulares;
import org.gestoresmadrid.oegam2comun.registradores.rm.rbm.CondicionesParticularesType.Pactos;
import org.gestoresmadrid.oegam2comun.registradores.rm.rbm.CuadroAmortizacionFIType;
import org.gestoresmadrid.oegam2comun.registradores.rm.rbm.CuadroAmortizacionFSType;
import org.gestoresmadrid.oegam2comun.registradores.rm.rbm.CuadroAmortizacionLeasingType;
import org.gestoresmadrid.oegam2comun.registradores.rm.rbm.DatosCategoriaType;
import org.gestoresmadrid.oegam2comun.registradores.rm.rbm.DatosFinancierosFinanciacionType;
import org.gestoresmadrid.oegam2comun.registradores.rm.rbm.DatosFinancierosLeasingType.OtrosImportes;
import org.gestoresmadrid.oegam2comun.registradores.rm.rbm.DatosFinancierosRentingType;
import org.gestoresmadrid.oegam2comun.registradores.rm.rbm.DatosNotario2Type;
import org.gestoresmadrid.oegam2comun.registradores.rm.rbm.DatosRegistralesMercantil2Type;
import org.gestoresmadrid.oegam2comun.registradores.rm.rbm.DesBienMuebleType;
import org.gestoresmadrid.oegam2comun.registradores.rm.rbm.DomicilioINEType;
import org.gestoresmadrid.oegam2comun.registradores.rm.rbm.FinanciacionImpuestoAnticipadoType;
import org.gestoresmadrid.oegam2comun.registradores.rm.rbm.FinanciacionSeguroType;
import org.gestoresmadrid.oegam2comun.registradores.rm.rbm.IdenSujetoType;
import org.gestoresmadrid.oegam2comun.registradores.rm.rbm.IdentificacionBienCancelacionType;
import org.gestoresmadrid.oegam2comun.registradores.rm.rbm.IdentificacionRegistralType;
import org.gestoresmadrid.oegam2comun.registradores.rm.rbm.IntervinienteFiadorType;
import org.gestoresmadrid.oegam2comun.registradores.rm.rbm.IntervinienteType;
import org.gestoresmadrid.oegam2comun.registradores.rm.rbm.IntervinienteType.Telefonos;
import org.gestoresmadrid.oegam2comun.registradores.rm.rbm.ObjetoFinanciadoType;
import org.gestoresmadrid.oegam2comun.registradores.rm.rbm.PlazoFIType;
import org.gestoresmadrid.oegam2comun.registradores.rm.rbm.PlazoFSType;
import org.gestoresmadrid.oegam2comun.registradores.rm.rbm.PresentacionType;
import org.gestoresmadrid.oegam2comun.registradores.rm.rbm.RBMContratoFinanciacionComprador;
import org.gestoresmadrid.oegam2comun.registradores.rm.rbm.RBMContratoFinanciacionComprador.Compradores;
import org.gestoresmadrid.oegam2comun.registradores.rm.rbm.RBMContratoFinanciacionComprador.Fiadores;
import org.gestoresmadrid.oegam2comun.registradores.rm.rbm.RBMContratoFinanciacionComprador.Financiadores;
import org.gestoresmadrid.oegam2comun.registradores.rm.rbm.RBMContratoFinanciacionComprador.Firmas;
import org.gestoresmadrid.oegam2comun.registradores.rm.rbm.RBMContratoFinanciacionComprador.ObjetosFinanciados;
import org.gestoresmadrid.oegam2comun.registradores.rm.rbm.RBMContratoFinanciacionComprador.Vendedores;
import org.gestoresmadrid.oegam2comun.registradores.rm.rbm.RBMContratoLeasing;
import org.gestoresmadrid.oegam2comun.registradores.rm.rbm.RBMContratoRenting;
import org.gestoresmadrid.oegam2comun.registradores.rm.rbm.RBMContratoRenting.Arrendadores;
import org.gestoresmadrid.oegam2comun.registradores.rm.rbm.RBMContratoRenting.Arrendatarios;
import org.gestoresmadrid.oegam2comun.registradores.rm.rbm.RBMContratoRenting.BienesObjetoContrato;
import org.gestoresmadrid.oegam2comun.registradores.rm.rbm.RBMDesistimiento;
import org.gestoresmadrid.oegam2comun.registradores.rm.rbm.RBMDesistimiento.IdentificacionDocumento;
import org.gestoresmadrid.oegam2comun.registradores.rm.rbm.RBMDocumentoCancelacion;
import org.gestoresmadrid.oegam2comun.registradores.rm.rbm.Representante2Type;
import org.gestoresmadrid.oegam2comun.registradores.rm.rbm.SolicitanteCancelacionType;
import org.gestoresmadrid.oegam2comun.registradores.rm.rbm.SolicitanteType;
import org.gestoresmadrid.oegam2comun.registradores.rm.rbm.TelefonoType;
import org.gestoresmadrid.oegam2comun.registradores.rm.rbm.ValorType;
import org.gestoresmadrid.oegam2comun.registradores.utiles.UtilesValidaciones;
import org.gestoresmadrid.oegam2comun.registradores.view.dto.AcuerdoDto;
import org.gestoresmadrid.oegam2comun.registradores.view.dto.ClausulaDto;
import org.gestoresmadrid.oegam2comun.registradores.view.dto.CuadroAmortizacionDto;
import org.gestoresmadrid.oegam2comun.registradores.view.dto.IntervinienteRegistroDto;
import org.gestoresmadrid.oegam2comun.registradores.view.dto.PropiedadDto;
import org.gestoresmadrid.oegam2comun.registradores.view.dto.TelefonoDto;
import org.gestoresmadrid.oegam2comun.registradores.view.dto.TramiteRegRbmDto;
import org.gestoresmadrid.oegam2comun.registradores.view.dto.TramiteRegistroDto;
import org.gestoresmadrid.oegamComun.conversor.Conversor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import es.globaltms.gestorDocumentos.bean.FicheroBean;
import es.globaltms.gestorDocumentos.constantes.ConstantesGestorFicheros;
import es.globaltms.gestorDocumentos.interfaz.GestorDocumentos;
import es.globaltms.gestorDocumentos.util.Utilidades;
import escrituras.beans.ResultBean;
import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;
import utilidades.web.OegamExcepcion;

@Component
public class BuildRm implements Serializable {

	private static final long serialVersionUID = -2760126344048038087L;

	private static final ILoggerOegam log = LoggerOegam.getLogger("Registradores");
	private static final String IMPUESTOS_FINANCIADOS = "ANTICIPATED_FINANCING";
	private static final String SEGUROS_FINANCIADOS = "INSURANCE_FINANCING";
	private static final String TITULO_DOCUMENTO = "CANCELACIÓN TELEMÁTICA DE TITULARIDADES DOMINICALES, LIMITACIONES, RESERVAS DE DOMINIO Y PROHIBICIONES DE DISPONER INSCRITAS EN EL REGISTRO DE BIENES MUEBLES, A INSTANCIA DEL TITULAR O BENEFICIARIO DE DICHAS SITUACIONES JURÍDICAS";

	@Autowired
	private GestorDocumentos gestorDocumentos;

	@Autowired
	private Conversor conversor;

	public String construirXML(TramiteRegistroDto tramiteRegistro) throws Exception, OegamExcepcion {
		String tipoTramite = tramiteRegistro.getTipoTramite();
		String codigoTipoTramite = TipoTramiteRegistro.getIdentificadorRm(tipoTramite);
		String descripcionTipoTramite = TipoTramiteRegistro.convertirTexto(tipoTramite);

		XmlCORPMEFactory xmlFactory = new XmlCORPMEFactory();
		JAXBContext context = JAXBContext.newInstance("org.gestoresmadrid.oegam2comun.registradores.rm.corpme");
		ObjectFactory factory = new ObjectFactory();
		CORPMERMCeseNombramiento corpmeRM = factory.createCORPMERMCeseNombramiento();
		corpmeRM.setClausulaLOPD(ClausulaLOPDType.CLAUSULA_LOPD_REGISTRADORES);

		ModeloType modeloType = factory.createModeloType();
		modeloType.setCodigo(codigoTipoTramite);
		modeloType.setDescripcion(descripcionTipoTramite);
		corpmeRM.setModelo(modeloType);


		if (tramiteRegistro.getReunion() != null && "noSeleccionado".equals(tramiteRegistro.getReunion().getCaracter())) {
			tramiteRegistro.getReunion().setCaracter
			("");
		}

		if (TipoTramiteRegistro.MODELO_1.getValorEnum().equals(tipoTramite)) {
			corpmeRM.setModelo1(buildModelo1(tramiteRegistro, factory));
		} else if (TipoTramiteRegistro.MODELO_2.getValorEnum().equals(tipoTramite)) {
			corpmeRM.setModelo2(buildModelo2(tramiteRegistro, factory));
		} else if (TipoTramiteRegistro.MODELO_3.getValorEnum().equals(tipoTramite)) {
			corpmeRM.setModelo3(buildModelo3(tramiteRegistro, factory));
		} else if (TipoTramiteRegistro.MODELO_4.getValorEnum().equals(tipoTramite)) {
			corpmeRM.setModelo4(buildModelo4(tramiteRegistro, factory));
		} else if (TipoTramiteRegistro.MODELO_5.getValorEnum().equals(tipoTramite)) {
			corpmeRM.setModelo5(buildModelo5(tramiteRegistro, factory));
		}

		// Genera el string xml sin saltos ni espacios (requerimiento CORPME):
		String cadenaXML = xmlFactory.toXML(corpmeRM, context);
		cadenaXML = incorporarElementoEstilos(cadenaXML);

		guardarDocumento(cadenaXML, tramiteRegistro.getIdTramiteRegistro());

		// Valida la cadena xml contra el esquema xsd:
		if (xmlFactory.validar(cadenaXML, context, "CORPME_RM")) {
			return cadenaXML;
		} else {
			log.error("xmlFactory no ha validado correctamente el XML");
			return null;
		}
	}

	public ResultBean construirRbmXMLFinanciacion(TramiteRegRbmDto tramiteRegistro) throws Exception, OegamExcepcion {

		XmlCORPMEFactory xmlFactory = new XmlCORPMEFactory();
		JAXBContext context = JAXBContext.newInstance("org.gestoresmadrid.oegam2comun.registradores.rm.rbm");
		org.gestoresmadrid.oegam2comun.registradores.rm.rbm.ObjectFactory factory = new org.gestoresmadrid.oegam2comun.registradores.rm.rbm.ObjectFactory();
		RBMContratoFinanciacionComprador corpmeRM = factory.createRBMContratoFinanciacionComprador();

		Compradores compradores = factory.createRBMContratoFinanciacionCompradorCompradores();
		if(tramiteRegistro.getCompradores()!=null && !tramiteRegistro.getCompradores().isEmpty()){
			compradores.getComprador().addAll((List<org.gestoresmadrid.oegam2comun.registradores.rm.rbm.IntervinienteType>) conversorInterviniente(tramiteRegistro.getCompradores(), compradores));
		}
		corpmeRM.setCompradores(compradores);

		CondicionesGeneralesType condicionesGenerales = factory.createCondicionesGeneralesType();
		CondicionesParticularesType condicionesParticulares = null;
		List<ClausulaDto> clausulasGenerales = new ArrayList<ClausulaDto>();
		List<ClausulaDto> clausulasParticulares = new ArrayList<ClausulaDto>();
		for (ClausulaDto aux : tramiteRegistro.getClausulas()){
			if(aux.getTipoClausula().equalsIgnoreCase(TipoClausula.GENERAL.name()))
				clausulasGenerales.add(aux);
			else
				clausulasParticulares.add(aux);
		}

		if(clausulasGenerales!=null && !clausulasGenerales.isEmpty()){
			condicionesGenerales.getClausula().addAll(conversor.transform(clausulasGenerales, org.gestoresmadrid.oegam2comun.registradores.rm.rbm.ClausulaType.class));
		}else{
			clausulasGenerales=null;
			condicionesGenerales=null;
		}

		if(clausulasParticulares!=null && !clausulasParticulares.isEmpty()){
			condicionesParticulares = factory.createCondicionesParticularesType();
			ClausulasParticulares clausulasParticularesAux = new ClausulasParticulares();
			clausulasParticularesAux.getClausula().addAll(conversor.transform(clausulasParticulares, org.gestoresmadrid.oegam2comun.registradores.rm.rbm.ClausulaType.class));
			condicionesParticulares.setClausulasParticulares(clausulasParticularesAux);
		}

		if(tramiteRegistro.getPactos()!=null && !tramiteRegistro.getPactos().isEmpty()){
			Pactos pactosAux = new Pactos();
			pactosAux.getPacto().addAll(conversor.transform(tramiteRegistro.getPactos(), org.gestoresmadrid.oegam2comun.registradores.rm.rbm.PactoType.class));
			if(condicionesParticulares == null)
				condicionesParticulares = factory.createCondicionesParticularesType();
			condicionesParticulares.setPactos(pactosAux);
		}
		if(tramiteRegistro.getPersonaNoDeterminada()!=null && !tramiteRegistro.getPersonaNoDeterminada().trim().equalsIgnoreCase("")){
			CesionType cesionarioAux = new CesionType();
			if(condicionesParticulares == null)
				condicionesParticulares = factory.createCondicionesParticularesType();
			cesionarioAux.setCesionAPersonaNODeterminada(convertirSiNo(tramiteRegistro.getPersonaNoDeterminada()));
			cesionarioAux.setTipoCesionTercero(tramiteRegistro.getTipoCesionTercero());
			if(tramiteRegistro.getCesionarios()!=null && !tramiteRegistro.getCesionarios().isEmpty()){
				Cesionarios cesionariosAux = new Cesionarios();
				cesionariosAux.getCesionario().addAll((List<CesionarioType>) conversorCesionario(tramiteRegistro.getCesionarios(), "financiacion"));
				cesionarioAux.setCesionarios(cesionariosAux);
			}
			condicionesParticulares.setCesion(cesionarioAux);
		}

		corpmeRM.setCondicionesGenerales(condicionesGenerales);
		corpmeRM.setCondicionesParticulares(condicionesParticulares);

		DatosFinancierosFinanciacionType datosFinanciacion = factory.createDatosFinancierosFinanciacionType();

		datosFinanciacion = conversor.transform(tramiteRegistro.getDatosFinanciero(), org.gestoresmadrid.oegam2comun.registradores.rm.rbm.DatosFinancierosFinanciacionType.class, "financiacionDatosFinancieros");
		if(tramiteRegistro.getDatosFinanciero().getReconocimientoDeudas()!=null && !tramiteRegistro.getDatosFinanciero().getReconocimientoDeudas().isEmpty())
			datosFinanciacion.getReconocimientoDeuda().addAll(conversor.transform(tramiteRegistro.getDatosFinanciero().getReconocimientoDeudas(), org.gestoresmadrid.oegam2comun.registradores.rm.rbm.ReconocimientoDeudaFinanciacionType.class, "financiacionReconocimientoDeuda"));
		//Comisiones
		if(tramiteRegistro.getDatosFinanciero().getComisiones()!=null && !tramiteRegistro.getDatosFinanciero().getComisiones().isEmpty())
			datosFinanciacion.getComisiones().getComision().addAll(conversor.transform(tramiteRegistro.getDatosFinanciero().getComisiones(), org.gestoresmadrid.oegam2comun.registradores.rm.rbm.ComisionType.class, "financiacionComisiones"));
		else
			datosFinanciacion.setComisiones(null);
		// otrosImportes;
		if(tramiteRegistro.getDatosFinanciero().getOtroImportes()!=null && !tramiteRegistro.getDatosFinanciero().getOtroImportes().isEmpty())
			datosFinanciacion.getOtrosImportes().getOtroImporte().addAll(conversor.transform(tramiteRegistro.getDatosFinanciero().getOtroImportes(), org.gestoresmadrid.oegam2comun.registradores.rm.rbm.OtroImporteType.class, "financiacionOtroImporte"));
		//cuadroAmortizacion
		if(tramiteRegistro.getDatosFinanciero().getCuadrosAmortizacion()!=null && !tramiteRegistro.getDatosFinanciero().getCuadrosAmortizacion().isEmpty())
			datosFinanciacion.getCuadroAmortizacion().getPlazo().addAll(conversor.transform(tramiteRegistro.getDatosFinanciero().getCuadrosAmortizacion(), org.gestoresmadrid.oegam2comun.registradores.rm.rbm.PlazoFinanciacionType.class, "financiacionPlazo"));

		corpmeRM.setDatosFinancieros(datosFinanciacion);
		//FIN
		Fiadores fiadores = factory.createRBMContratoFinanciacionCompradorFiadores();
		if(tramiteRegistro.getAvalistas()!=null && !tramiteRegistro.getAvalistas().isEmpty()){
			fiadores.getFiador().addAll((List<org.gestoresmadrid.oegam2comun.registradores.rm.rbm.IntervinienteFiadorType>) conversorFiador(tramiteRegistro.getAvalistas(), fiadores));
		}else{
			fiadores = null;
		}
		corpmeRM.setFiadores(fiadores);

		Financiadores financiadores = factory.createRBMContratoFinanciacionCompradorFinanciadores();
		if(tramiteRegistro.getFinanciadores()!=null && !tramiteRegistro.getFinanciadores().isEmpty()){
			financiadores.getFinanciador().addAll((List<org.gestoresmadrid.oegam2comun.registradores.rm.rbm.IntervinienteType>) conversorInterviniente(tramiteRegistro.getFinanciadores(), financiadores));
		}
		corpmeRM.setFinanciadores(financiadores);

		Firmas firmas = factory.createRBMContratoFinanciacionCompradorFirmas();
		if(tramiteRegistro.getDatoFirmas()!=null && !tramiteRegistro.getDatoFirmas().isEmpty()){
			firmas.getFirma().addAll(conversor.transform(tramiteRegistro.getDatoFirmas(), org.gestoresmadrid.oegam2comun.registradores.rm.rbm.FirmaType.class, "financiacionFirma"));
		}
		corpmeRM.setFirmas(firmas);

		corpmeRM.setIdentificacion(conversor.transform(tramiteRegistro, org.gestoresmadrid.oegam2comun.registradores.rm.rbm.IdentificacionContratoType.class));

		ObjetosFinanciados objetosFinanciados = factory.createRBMContratoFinanciacionCompradorObjetosFinanciados();
		for (PropiedadDto propiedad : tramiteRegistro.getPropiedades()){
			ObjetoFinanciadoType objeto = new ObjetoFinanciadoType();
			DesBienMuebleType bien = new DesBienMuebleType();
			ValorType valor = new ValorType();
			DatosCategoriaType datosCat = new DatosCategoriaType();
			valor.setUnidadCuenta(propiedad.getUnidadCuenta());
			valor.setValorContado(String.valueOf(propiedad.getValor()).replace(".", ","));
			bien.setCategoria(propiedad.getCategoria());
			if(propiedad.getCategoria().equalsIgnoreCase(TipoCategoria.AERONAVES.getValorEnum()))
				datosCat.getAeronave().add(conversor.transform(propiedad.getAeronave(), org.gestoresmadrid.oegam2comun.registradores.rm.rbm.AeronaveType.class));
			else if(propiedad.getCategoria().equalsIgnoreCase(TipoCategoria.BUQUES.getValorEnum()))
				datosCat.getBuque().add(conversor.transform(propiedad.getBuque(), org.gestoresmadrid.oegam2comun.registradores.rm.rbm.BuqueType.class));
			else if(propiedad.getCategoria().equalsIgnoreCase(TipoCategoria.ESTABLECIMIENTOS_MERCANTILES.getValorEnum()))
				datosCat.getEstablecimiento().add(conversor.transform(propiedad.getEstablecimiento(), org.gestoresmadrid.oegam2comun.registradores.rm.rbm.EstablecimientoMercantilType.class));
			else if(propiedad.getCategoria().equalsIgnoreCase(TipoCategoria.PROPIEDAD_INDUSTRIAL.getValorEnum()))
				datosCat.getPropiedadIndustrial().add(conversor.transform(propiedad.getPropiedadIndustrial(), org.gestoresmadrid.oegam2comun.registradores.rm.rbm.PropiedadIndustrialType.class));
			else if(propiedad.getCategoria().equalsIgnoreCase(TipoCategoria.PROPIEDAD_INTELECTUAL.getValorEnum()))
				datosCat.getPropiedadIntelectual().add(conversor.transform(propiedad.getPropiedadIntelectual(), org.gestoresmadrid.oegam2comun.registradores.rm.rbm.PropiedadIntelectualType.class));
			else if(propiedad.getCategoria().equalsIgnoreCase(TipoCategoria.VEHICULOS_MOTOR.getValorEnum()))
				datosCat.getVehiculo().add(conversor.transform(propiedad.getVehiculo(), org.gestoresmadrid.oegam2comun.registradores.rm.rbm.VehiculoType.class));
			else if(propiedad.getCategoria().equalsIgnoreCase(TipoCategoria.MAQUINARIA_INDUSTRIAL.getValorEnum()))
				datosCat.getMaquinaria().add(conversor.transform(propiedad.getMaquinaria(), org.gestoresmadrid.oegam2comun.registradores.rm.rbm.MaquinariaType.class));
			else if(propiedad.getCategoria().equalsIgnoreCase(TipoCategoria.OTROS.getValorEnum()))
				datosCat.getOtrosBienes().add(conversor.transform(propiedad.getOtrosBienes(), org.gestoresmadrid.oegam2comun.registradores.rm.rbm.OtrosBienesType.class));
			bien.setDatosCategoria(datosCat);
			objeto.setDesBienMueble(bien);
			objeto.setValor(valor);
			objetosFinanciados.getObjetoFinanciado().add(objeto);
		}

		corpmeRM.setObjetosFinanciados(objetosFinanciados);

		Vendedores vendedores =  factory.createRBMContratoFinanciacionCompradorVendedores();
		if(tramiteRegistro.getVendedores()!=null && !tramiteRegistro.getVendedores().isEmpty()){
			vendedores.getVendedor().addAll(conversor.transform(tramiteRegistro.getVendedores(), org.gestoresmadrid.oegam2comun.registradores.rm.rbm.VendedorType.class, "financiacionVendedor"));
		}else{
			vendedores= null;
		}
		corpmeRM.setVendedores(vendedores);

		corpmeRM.setClausulaLOPD(ClausulaLOPDType.CLAUSULA_LOPD_REGISTRADORES.value());

		corpmeRM.setVersion("5.0.0");

		// Genera el string xml sin saltos ni espacios (requerimiento CORPME):
		String cadenaXML = xmlFactory.toXML(corpmeRM, context);
		//cadenaXML = incorporarElementoEstilos(cadenaXML);

		guardarDocumento(cadenaXML, tramiteRegistro.getIdTramiteRegistro());

		// Valida la cadena xml contra el esquema xsd:
		ResultBean resultValidacion = xmlFactory.validarRbm(cadenaXML, context, "RBM_FINANCIACION");
		if (!resultValidacion.getError()) {
			resultValidacion.addAttachment("xml", cadenaXML);			
		} else {
			log.error("xmlFactory no ha validado correctamente el XML");
		}
		return resultValidacion;
	}

	private String convertirSiNo(String personaNoDeterminada) {
		if (personaNoDeterminada == null) {
			return null;
		}
		if (personaNoDeterminada.equalsIgnoreCase("TRUE")) {
			return "SI";
		}
		return "NO";
	}

	public ResultBean construirRbmXMLRenting(TramiteRegRbmDto tramiteRegistro) throws Exception, OegamExcepcion {

		XmlCORPMEFactory xmlFactory = new XmlCORPMEFactory();
		JAXBContext context = JAXBContext.newInstance("org.gestoresmadrid.oegam2comun.registradores.rm.rbm");
		org.gestoresmadrid.oegam2comun.registradores.rm.rbm.ObjectFactory factory = new org.gestoresmadrid.oegam2comun.registradores.rm.rbm.ObjectFactory();
		RBMContratoRenting corpmeRM = factory.createRBMContratoRenting();

		Arrendadores arrendadores = factory.createRBMContratoRentingArrendadores();
		if(tramiteRegistro.getArrendadores()!=null && !tramiteRegistro.getArrendadores().isEmpty()){
			arrendadores.getArrendador().addAll((Collection<IntervinienteType>) conversorInterviniente(tramiteRegistro.getArrendadores(), arrendadores));
		}
		corpmeRM.setArrendadores(arrendadores);

		Arrendatarios arrendatarios = factory.createRBMContratoRentingArrendatarios();
		if(tramiteRegistro.getArrendatarios()!=null && !tramiteRegistro.getArrendatarios().isEmpty()){
			arrendatarios.getArrendatario().addAll((Collection<IntervinienteType>) conversorInterviniente(tramiteRegistro.getArrendatarios(), arrendatarios));
		}
		corpmeRM.setArrendatarios(arrendatarios);

		org.gestoresmadrid.oegam2comun.registradores.rm.rbm.CondicionesGeneralesType condicionesGenerales = factory.createCondicionesGeneralesType();
		org.gestoresmadrid.oegam2comun.registradores.rm.rbm.CondicionesParticularesType condicionesParticulares = null;
		List<ClausulaDto> clausulasGenerales = new ArrayList<ClausulaDto>();
		List<ClausulaDto> clausulasParticulares = new ArrayList<ClausulaDto>();
		for (ClausulaDto aux : tramiteRegistro.getClausulas()){
			if(aux.getTipoClausula().equalsIgnoreCase(TipoClausula.GENERAL.name()))
				clausulasGenerales.add(aux);
			else
				clausulasParticulares.add(aux);
		}

		if(clausulasGenerales!=null && !clausulasGenerales.isEmpty()){
			condicionesGenerales.getClausula().addAll(conversor.transform(clausulasGenerales, org.gestoresmadrid.oegam2comun.registradores.rm.rbm.ClausulaType.class));
		}else{
			clausulasGenerales=null;
			condicionesGenerales=null;
		}

		if(clausulasParticulares!=null && !clausulasParticulares.isEmpty()){
			org.gestoresmadrid.oegam2comun.registradores.rm.rbm.CondicionesParticularesType.ClausulasParticulares clausulasParticularesAux = new org.gestoresmadrid.oegam2comun.registradores.rm.rbm.CondicionesParticularesType.ClausulasParticulares ();
			clausulasParticularesAux.getClausula().addAll(conversor.transform(clausulasParticulares, org.gestoresmadrid.oegam2comun.registradores.rm.rbm.ClausulaType.class));
			condicionesParticulares = factory.createCondicionesParticularesType();
			condicionesParticulares.setClausulasParticulares(clausulasParticularesAux);
		}
		if(tramiteRegistro.getPactos()!=null && !tramiteRegistro.getPactos().isEmpty()){
			org.gestoresmadrid.oegam2comun.registradores.rm.rbm.CondicionesParticularesType.Pactos pactosAux = new org.gestoresmadrid.oegam2comun.registradores.rm.rbm.CondicionesParticularesType.Pactos();
			pactosAux.getPacto().addAll(conversor.transform(tramiteRegistro.getPactos(), org.gestoresmadrid.oegam2comun.registradores.rm.rbm.PactoType.class));
			if(condicionesParticulares == null)
				condicionesParticulares = factory.createCondicionesParticularesType();
			condicionesParticulares.setPactos(pactosAux);
		}

		if(tramiteRegistro.getPersonaNoDeterminada()!=null && !tramiteRegistro.getPersonaNoDeterminada().trim().equalsIgnoreCase("")){
			org.gestoresmadrid.oegam2comun.registradores.rm.rbm.CesionType cesionarioAux = new org.gestoresmadrid.oegam2comun.registradores.rm.rbm.CesionType();
			cesionarioAux.setCesionAPersonaNODeterminada(convertirSiNo(tramiteRegistro.getPersonaNoDeterminada()));
			cesionarioAux.setTipoCesionTercero(tramiteRegistro.getTipoCesionTercero());
			if(condicionesParticulares == null)
				condicionesParticulares = factory.createCondicionesParticularesType();
			if(tramiteRegistro.getCesionarios()!=null && !tramiteRegistro.getCesionarios().isEmpty()){
				org.gestoresmadrid.oegam2comun.registradores.rm.rbm.CesionType.Cesionarios cesionariosAux = new org.gestoresmadrid.oegam2comun.registradores.rm.rbm.CesionType.Cesionarios();
				cesionariosAux.getCesionario().addAll((List<org.gestoresmadrid.oegam2comun.registradores.rm.rbm.CesionarioType>) conversorCesionario(tramiteRegistro.getCesionarios(), "renting"));
				cesionarioAux.setCesionarios(cesionariosAux);
			}
			condicionesParticulares.setCesion(cesionarioAux);
		}
		corpmeRM.setCondicionesGenerales(condicionesGenerales);
		corpmeRM.setCondicionesParticulares(condicionesParticulares);

		DatosFinancierosRentingType datosRenting;	
		datosRenting = conversor.transform(tramiteRegistro.getDatosFinanciero(), org.gestoresmadrid.oegam2comun.registradores.rm.rbm.DatosFinancierosRentingType.class, "rentingDatosFinancieros");
		if(tramiteRegistro.getDatosFinanciero().getReconocimientoDeudas()!=null && !tramiteRegistro.getDatosFinanciero().getReconocimientoDeudas().isEmpty())
			datosRenting.getReconocimientoDeuda().addAll(conversor.transform(tramiteRegistro.getDatosFinanciero().getReconocimientoDeudas(), org.gestoresmadrid.oegam2comun.registradores.rm.rbm.ReconocimientoDeudaLeasingType.class, "rentingReconocimientoDeuda"));
		//Comisiones
		if(tramiteRegistro.getDatosFinanciero().getComisiones()!=null && !tramiteRegistro.getDatosFinanciero().getComisiones().isEmpty())
			datosRenting.getComisiones().getComision().addAll(conversor.transform(tramiteRegistro.getDatosFinanciero().getComisiones(), org.gestoresmadrid.oegam2comun.registradores.rm.rbm.ComisionType.class, "rentingComisiones"));
		else
			datosRenting.setComisiones(null);
		// otrosImportes;
		if(tramiteRegistro.getDatosFinanciero().getOtroImportes()!=null && !tramiteRegistro.getDatosFinanciero().getOtroImportes().isEmpty())
			datosRenting.getOtrosImportes().getOtroImporte().addAll(conversor.transform(tramiteRegistro.getDatosFinanciero().getOtroImportes(), org.gestoresmadrid.oegam2comun.registradores.rm.rbm.OtroImporteType.class, "rentingOtroImporte"));

		corpmeRM.setDatosFinancieros(datosRenting);

		org.gestoresmadrid.oegam2comun.registradores.rm.rbm.RBMContratoRenting.Fiadores fiadores = factory.createRBMContratoRentingFiadores();
		if(tramiteRegistro.getAvalistas()!=null && !tramiteRegistro.getAvalistas().isEmpty()){
			fiadores.getFiador().addAll((List<org.gestoresmadrid.oegam2comun.registradores.rm.rbm.IntervinienteFiadorType>) conversorFiador(tramiteRegistro.getAvalistas(), fiadores));
		}else{
			fiadores=null;
		}
		corpmeRM.setFiadores(fiadores);

		org.gestoresmadrid.oegam2comun.registradores.rm.rbm.RBMContratoRenting.Firmas firmas = factory.createRBMContratoRentingFirmas();
		if(tramiteRegistro.getDatoFirmas()!=null && !tramiteRegistro.getDatoFirmas().isEmpty()){
			firmas.getFirma().addAll(conversor.transform(tramiteRegistro.getDatoFirmas(), org.gestoresmadrid.oegam2comun.registradores.rm.rbm.FirmaType.class, "rentingFirma"));
		}
		corpmeRM.setFirmas(firmas);

		corpmeRM.setIdentificacion(conversor.transform(tramiteRegistro, org.gestoresmadrid.oegam2comun.registradores.rm.rbm.IdentificacionContratoType.class));

		BienesObjetoContrato objetosFinanciados = factory.createRBMContratoRentingBienesObjetoContrato();
		for (PropiedadDto propiedad : tramiteRegistro.getPropiedades()){
			org.gestoresmadrid.oegam2comun.registradores.rm.rbm.ObjetoFinanciadoType objeto = new org.gestoresmadrid.oegam2comun.registradores.rm.rbm.ObjetoFinanciadoType();
			org.gestoresmadrid.oegam2comun.registradores.rm.rbm.DesBienMuebleType bien = new org.gestoresmadrid.oegam2comun.registradores.rm.rbm.DesBienMuebleType();
			org.gestoresmadrid.oegam2comun.registradores.rm.rbm.ValorType valor = new org.gestoresmadrid.oegam2comun.registradores.rm.rbm.ValorType();
			org.gestoresmadrid.oegam2comun.registradores.rm.rbm.DatosCategoriaType datosCat = new org.gestoresmadrid.oegam2comun.registradores.rm.rbm.DatosCategoriaType();
			valor.setUnidadCuenta(propiedad.getUnidadCuenta());
			valor.setValorContado(String.valueOf(propiedad.getValor()).replace(".", ","));
			bien.setCategoria(propiedad.getCategoria());
			if(propiedad.getCategoria().equalsIgnoreCase(TipoCategoria.AERONAVES.getValorEnum()))
				datosCat.getAeronave().add(conversor.transform(propiedad.getAeronave(), org.gestoresmadrid.oegam2comun.registradores.rm.rbm.AeronaveType.class));
			else if(propiedad.getCategoria().equalsIgnoreCase(TipoCategoria.BUQUES.getValorEnum()))
				datosCat.getBuque().add(conversor.transform(propiedad.getBuque(), org.gestoresmadrid.oegam2comun.registradores.rm.rbm.BuqueType.class));
			else if(propiedad.getCategoria().equalsIgnoreCase(TipoCategoria.ESTABLECIMIENTOS_MERCANTILES.getValorEnum()))
				datosCat.getEstablecimiento().add(conversor.transform(propiedad.getEstablecimiento(), org.gestoresmadrid.oegam2comun.registradores.rm.rbm.EstablecimientoMercantilType.class));
			else if(propiedad.getCategoria().equalsIgnoreCase(TipoCategoria.PROPIEDAD_INDUSTRIAL.getValorEnum()))
				datosCat.getPropiedadIndustrial().add(conversor.transform(propiedad.getPropiedadIndustrial(), org.gestoresmadrid.oegam2comun.registradores.rm.rbm.PropiedadIndustrialType.class));
			else if(propiedad.getCategoria().equalsIgnoreCase(TipoCategoria.PROPIEDAD_INTELECTUAL.getValorEnum()))
				datosCat.getPropiedadIntelectual().add(conversor.transform(propiedad.getPropiedadIntelectual(), org.gestoresmadrid.oegam2comun.registradores.rm.rbm.PropiedadIntelectualType.class));
			else if(propiedad.getCategoria().equalsIgnoreCase(TipoCategoria.VEHICULOS_MOTOR.getValorEnum()))
				datosCat.getVehiculo().add(conversor.transform(propiedad.getVehiculo(), org.gestoresmadrid.oegam2comun.registradores.rm.rbm.VehiculoType.class));
			else if(propiedad.getCategoria().equalsIgnoreCase(TipoCategoria.MAQUINARIA_INDUSTRIAL.getValorEnum()))
				datosCat.getMaquinaria().add(conversor.transform(propiedad.getMaquinaria(), org.gestoresmadrid.oegam2comun.registradores.rm.rbm.MaquinariaType.class));
			else if(propiedad.getCategoria().equalsIgnoreCase(TipoCategoria.OTROS.getValorEnum()))
				datosCat.getOtrosBienes().add(conversor.transform(propiedad.getOtrosBienes(), org.gestoresmadrid.oegam2comun.registradores.rm.rbm.OtrosBienesType.class));
			bien.setDatosCategoria(datosCat);
			objeto.setDesBienMueble(bien);
			objeto.setValor(valor);
			objetosFinanciados.getBien().add(objeto);
		}

		corpmeRM.setBienesObjetoContrato(objetosFinanciados);

		corpmeRM.setClausulaLOPD(ClausulaLOPDType.CLAUSULA_LOPD_REGISTRADORES.value());

		corpmeRM.setVersion("5.0.0");

		// Genera el string xml sin saltos ni espacios (requerimiento CORPME):
		String cadenaXML = xmlFactory.toXML(corpmeRM, context);
		//cadenaXML = incorporarElementoEstilos(cadenaXML);

		guardarDocumento(cadenaXML, tramiteRegistro.getIdTramiteRegistro());

		// Valida la cadena xml contra el esquema xsd:
		ResultBean resultValidacion = xmlFactory.validarRbm(cadenaXML, context, "RBM_RENTING");
		if (!resultValidacion.getError()) {
			resultValidacion.addAttachment("xml", cadenaXML);			
		} else {
			log.error("xmlFactory no ha validado correctamente el XML");
		}
		return resultValidacion;
	}


	private Object conversorInterviniente(List<IntervinienteRegistroDto> intervinientes, Object intervinienteXml) {
		String clase = intervinienteXml.getClass().getName();
		if (clase.contains("RBMContratoRenting")){
			return convertirRentingInterviniente(intervinientes);
		}else if(clase.contains("RBMContratoFinanciacionComprador")){
			return convertirFinancingInterviniente(intervinientes);
		}else if(clase.contains("RBMContratoLeasing")){
			return convertirLeasingInterviniente(intervinientes);
		}else{
			return null;
		}

	}

	private Object conversorCesionario(List<IntervinienteRegistroDto> cesionarios, String cesionarioXml) {
		if (cesionarioXml.contains("renting")){
			return convertirRentingCesionario(cesionarios);
		}else if(cesionarioXml.contains("financiacion")){
			return convertirFinancingCesionario(cesionarios);
		}else if(cesionarioXml.contains("leasing")){
			return convertirLeasingCesionario(cesionarios);
		}else{
			return null;
		}

	}

	private Object conversorFiador(List<IntervinienteRegistroDto> fiadores, Object fiadorXml) {
		String clase = fiadorXml.getClass().getName();
		if (clase.contains("RBMContratoRenting")){
			return convertirRentingFiador(fiadores);
		}else if(clase.contains("RBMContratoFinanciacionComprador")){
			return convertirFinancingFiador(fiadores);
		}else if(clase.contains("RBMContratoLeasing")){
			return convertirLeasingFiador(fiadores);
		}else{
			return null;
		}

	}

	private Object convertirLeasingFiador(List<IntervinienteRegistroDto> fiadores) {
		List<IntervinienteFiadorType> fiadoresXml = new ArrayList<IntervinienteFiadorType>();
		for (IntervinienteRegistroDto interviniente : fiadores){
			org.gestoresmadrid.oegam2comun.registradores.rm.rbm.IntervinienteFiadorType intervinienteXml = new org.gestoresmadrid.oegam2comun.registradores.rm.rbm.IntervinienteFiadorType();
			intervinienteXml.setCorreoElectronico(interviniente.getPersona().getCorreoElectronico());

			if(interviniente.getDatRegMercantil()!=null){
				org.gestoresmadrid.oegam2comun.registradores.rm.rbm.DatosRegistralesMercantil2Type datosRegistrales = new org.gestoresmadrid.oegam2comun.registradores.rm.rbm.DatosRegistralesMercantil2Type();
				if(interviniente.getDatRegMercantil().getCodRegistroMercantil()!=null)
					datosRegistrales.setCodigoRMercantil(interviniente.getDatRegMercantil().getCodRegistroMercantil());
				if(interviniente.getDatRegMercantil().getFolio()!=null)
					datosRegistrales.setFolioRMercantil(interviniente.getDatRegMercantil().getFolio().toString());
				datosRegistrales.setHojaRMercantil(interviniente.getDatRegMercantil().getHoja());
				datosRegistrales.setInscripcionRMercantil(interviniente.getDatRegMercantil().getNumInscripcion());
				if(interviniente.getDatRegMercantil().getLibro()!=null)
					datosRegistrales.setLibroRMercantil(interviniente.getDatRegMercantil().getLibro().toString());
				if(interviniente.getDatRegMercantil().getTomo()!=null)
					datosRegistrales.setTomoRMercantil(interviniente.getDatRegMercantil().getTomo().toString());
				intervinienteXml.setDatosRegistralesMercantil(datosRegistrales);
			}

			org.gestoresmadrid.oegam2comun.registradores.rm.rbm.DomicilioINEType domicilio = new org.gestoresmadrid.oegam2comun.registradores.rm.rbm.DomicilioINEType();
			if(interviniente.getDireccion().getBloque()!=null)
				domicilio.setBloque(interviniente.getDireccion().getBloque());
			else
				domicilio.setBloque("");
			domicilio.setCodigoPostal(interviniente.getDireccion().getCodPostal());
			domicilio.setCodMunicipio(interviniente.getDireccion().getIdMunicipio());
			domicilio.setCodProvincia(interviniente.getDireccion().getIdProvincia());
			domicilio.setCodTipoVia(interviniente.getDireccion().getIdTipoVia());
			if(interviniente.getDireccion().getPueblo()!=null)
				domicilio.setEntidadLocalMenor(interviniente.getDireccion().getPueblo());
			else
				domicilio.setEntidadLocalMenor("");
			if(interviniente.getDireccion().getEscalera()!=null)
				domicilio.setEscalera(interviniente.getDireccion().getEscalera());
			else
				domicilio.setEscalera("");
			if(interviniente.getDireccion().getRegionExtranjera()!=null)
				domicilio.setEstadoRegionProvinciaExtranjera(interviniente.getDireccion().getRegionExtranjera());
			else
				domicilio.setEstadoRegionProvinciaExtranjera("");
			domicilio.setLugarUbicacion(interviniente.getDireccion().getLugarUbicacion());
			domicilio.setNombreVia(interviniente.getDireccion().getNombreVia());
			domicilio.setNumero(interviniente.getDireccion().getNumero());
			if(interviniente.getDireccion().getNumeroBis()!=null)
				domicilio.setNumeroBis(interviniente.getDireccion().getNumeroBis());
			else
				domicilio.setNumeroBis("");
			domicilio.setPais(interviniente.getDireccion().getPais());
			if(interviniente.getDireccion().getPlanta()!=null)
				domicilio.setPlanta(interviniente.getDireccion().getPlanta());
			else
				domicilio.setPlanta("");
			if(interviniente.getDireccion().getPortal()!=null)
				domicilio.setPortal(interviniente.getDireccion().getPortal());
			else
				domicilio.setPortal("");
			if(interviniente.getDireccion().getPuerta()!=null)
				domicilio.setPuerta(interviniente.getDireccion().getPuerta());
			else
				domicilio.setPuerta("");
			if(interviniente.getDireccion().getKm()!=null)
				domicilio.setPuntoKilometrico(interviniente.getDireccion().getKm().toString());
			else
				domicilio.setPuntoKilometrico("");		
			intervinienteXml.setDomicilio(domicilio);

			org.gestoresmadrid.oegam2comun.registradores.rm.rbm.IdenSujetoType idenSujeto = new org.gestoresmadrid.oegam2comun.registradores.rm.rbm.IdenSujetoType();
			idenSujeto.setNifCif(interviniente.getNif());
			if(interviniente.getTipoPersona().equals(TipoPersonaRegistro.Fisica.getValorXML())
					|| interviniente.getTipoPersona().equals(TipoPersonaRegistro.Extranjero.getValorXML())){
				idenSujeto.setNombre(interviniente.getPersona().getNombre());
				idenSujeto.setApellidos(interviniente.getPersona().getApellidos());
			}else{
				idenSujeto.setNombre(interviniente.getPersona().getApellido1RazonSocial());
			}
			idenSujeto.setTipoPersona(TipoPersonaRegistro.convertirValorXml(interviniente.getPersona().getTipoPersona()));			
			intervinienteXml.setIdenSujeto(idenSujeto);

			if(interviniente.getTelefonos()!=null && !interviniente.getTelefonos().isEmpty()){
				org.gestoresmadrid.oegam2comun.registradores.rm.rbm.IntervinienteFiadorType.Telefonos telefonos = new org.gestoresmadrid.oegam2comun.registradores.rm.rbm.IntervinienteFiadorType.Telefonos();
				for(TelefonoDto telefonoDto : interviniente.getTelefonos()){
					org.gestoresmadrid.oegam2comun.registradores.rm.rbm.TelefonoType telefono = new org.gestoresmadrid.oegam2comun.registradores.rm.rbm.TelefonoType();
					telefono.setNumero(telefonoDto.getNumero());
					telefono.setTipo(telefonoDto.getTipo());
					telefonos.getTelefono().add(telefono);
				}
				intervinienteXml.setTelefonos(telefonos);
			}			
			//REPRESENTANTES
			if(interviniente.getRepresentantes()!=null && !interviniente.getRepresentantes().isEmpty()){
				for(IntervinienteRegistroDto representante : interviniente.getRepresentantes()){
					org.gestoresmadrid.oegam2comun.registradores.rm.rbm.Representante2Type representanteXml = new org.gestoresmadrid.oegam2comun.registradores.rm.rbm.Representante2Type();

					representanteXml.setCargo(representante.getCargo());
					if(representante.getNotario()!=null){

						SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

						org.gestoresmadrid.oegam2comun.registradores.rm.rbm.DatosNotario2Type datosNotario = new org.gestoresmadrid.oegam2comun.registradores.rm.rbm.DatosNotario2Type();
						if(representante.getNotario().getFecOtorgamiento()!=null)
							datosNotario.setFechaOtorgamiento(sdf.format(representante.getNotario().getFecOtorgamiento()));
						datosNotario.setNumeroProtocolo(representante.getNotario().getNumProtocolo());
						datosNotario.setCodProvincia(representante.getNotario().getCodProvincia());
						datosNotario.setCodMunicipio(representante.getNotario().getCodMunicipio());
						org.gestoresmadrid.oegam2comun.registradores.rm.rbm.IdenSujetoType idenSujetoNotario = new org.gestoresmadrid.oegam2comun.registradores.rm.rbm.IdenSujetoType();
						idenSujetoNotario.setNifCif(representante.getNotario().getNif());

						if(representante.getNotario().getTipoPersona().equals(TipoPersonaRegistro.Fisica.getValorXML())
								|| representante.getNotario().getTipoPersona().equals(TipoPersonaRegistro.Extranjero.getValorXML())){
							idenSujetoNotario.setNombre(representante.getNotario().getNombre());
							idenSujetoNotario.setApellidos(representante.getNotario().getApellido1() +" "+representante.getNotario().getApellido2());
						}else{
							idenSujetoNotario.setNombre(representante.getNotario().getApellido1());
						}
						idenSujetoNotario.setTipoPersona(representante.getNotario().getTipoPersona());
						datosNotario.setIdenSujeto(idenSujetoNotario);
						representanteXml.setDatosNotario(datosNotario);
					}

					org.gestoresmadrid.oegam2comun.registradores.rm.rbm.DomicilioINEType domicilioRepresentante = new org.gestoresmadrid.oegam2comun.registradores.rm.rbm.DomicilioINEType();
					if(representante.getDireccion().getBloque()!=null)
						domicilioRepresentante.setBloque(representante.getDireccion().getBloque());
					else
						domicilioRepresentante.setBloque("");
					domicilioRepresentante.setCodigoPostal(representante.getDireccion().getCodPostal());
					domicilioRepresentante.setCodMunicipio(representante.getDireccion().getIdMunicipio());
					domicilioRepresentante.setCodProvincia(representante.getDireccion().getIdProvincia());
					domicilioRepresentante.setCodTipoVia(representante.getDireccion().getIdTipoVia());
					if(representante.getDireccion().getPueblo()!=null)
						domicilioRepresentante.setEntidadLocalMenor(representante.getDireccion().getPueblo());
					else
						domicilioRepresentante.setEntidadLocalMenor("");
					if(representante.getDireccion().getEscalera()!=null)
						domicilioRepresentante.setEscalera(representante.getDireccion().getEscalera());
					else
						domicilioRepresentante.setEscalera("");
					if(representante.getDireccion().getRegionExtranjera()!=null)
						domicilioRepresentante.setEstadoRegionProvinciaExtranjera(representante.getDireccion().getRegionExtranjera());
					else
						domicilioRepresentante.setEstadoRegionProvinciaExtranjera("");
					domicilioRepresentante.setLugarUbicacion(representante.getDireccion().getLugarUbicacion());
					domicilioRepresentante.setNombreVia(representante.getDireccion().getNombreVia());
					domicilioRepresentante.setNumero(representante.getDireccion().getNumero());
					if(representante.getDireccion().getNumeroBis()!=null)
						domicilioRepresentante.setNumeroBis(representante.getDireccion().getNumeroBis());
					else
						domicilioRepresentante.setNumeroBis("");
					domicilioRepresentante.setPais(representante.getDireccion().getPais());
					if(representante.getDireccion().getPlanta()!=null)
						domicilioRepresentante.setPlanta(representante.getDireccion().getPlanta());
					else
						domicilioRepresentante.setPlanta("");
					if(representante.getDireccion().getPortal()!=null)
						domicilioRepresentante.setPortal(representante.getDireccion().getPortal());
					else
						domicilioRepresentante.setPortal("");
					if(representante.getDireccion().getPuerta()!=null)
						domicilioRepresentante.setPuerta(representante.getDireccion().getPuerta());
					else
						domicilioRepresentante.setPuerta("");
					if(representante.getDireccion().getKm()!=null)
						domicilioRepresentante.setPuntoKilometrico(representante.getDireccion().getKm().toString());
					else
						domicilioRepresentante.setPuntoKilometrico("");					
					representanteXml.setDomicilio(domicilioRepresentante);

					org.gestoresmadrid.oegam2comun.registradores.rm.rbm.IdenSujetoType idenSujetoRepresentante = new org.gestoresmadrid.oegam2comun.registradores.rm.rbm.IdenSujetoType();

					idenSujetoRepresentante.setNifCif(representante.getNif());
					if(representante.getTipoPersona().equals(TipoPersonaRegistro.Fisica.getValorXML())
							|| representante.getTipoPersona().equals(TipoPersonaRegistro.Extranjero.getValorXML())){
						idenSujetoRepresentante.setNombre(representante.getPersona().getNombre());
						idenSujetoRepresentante.setApellidos(representante.getPersona().getApellidos());
					}else{
						idenSujetoRepresentante.setNombre(representante.getPersona().getApellido1RazonSocial());
					}
					idenSujetoRepresentante.setTipoPersona(TipoPersonaRegistro.convertirValorXml(representante.getPersona().getTipoPersona()));			
					representanteXml.setIdenSujeto(idenSujetoRepresentante);					
					intervinienteXml.getRepresentante().add(representanteXml);
				}
			}
			fiadoresXml.add(intervinienteXml);

		}
		return fiadoresXml;
	}

	private Object convertirFinancingFiador(List<IntervinienteRegistroDto> fiadores) {
		List<org.gestoresmadrid.oegam2comun.registradores.rm.rbm.IntervinienteFiadorType> fiadoresXml = new ArrayList<org.gestoresmadrid.oegam2comun.registradores.rm.rbm.IntervinienteFiadorType>();
		for (IntervinienteRegistroDto interviniente : fiadores){
			org.gestoresmadrid.oegam2comun.registradores.rm.rbm.IntervinienteFiadorType intervinienteXml = new org.gestoresmadrid.oegam2comun.registradores.rm.rbm.IntervinienteFiadorType();
			intervinienteXml.setCorreoElectronico(interviniente.getPersona().getCorreoElectronico());

			if(interviniente.getDatRegMercantil()!=null){
				org.gestoresmadrid.oegam2comun.registradores.rm.rbm.DatosRegistralesMercantil2Type datosRegistrales = new org.gestoresmadrid.oegam2comun.registradores.rm.rbm.DatosRegistralesMercantil2Type();
				if(interviniente.getDatRegMercantil().getCodRegistroMercantil()!=null)
					datosRegistrales.setCodigoRMercantil(interviniente.getDatRegMercantil().getCodRegistroMercantil());
				if(interviniente.getDatRegMercantil().getFolio()!=null)
					datosRegistrales.setFolioRMercantil(interviniente.getDatRegMercantil().getFolio().toString());
				datosRegistrales.setHojaRMercantil(interviniente.getDatRegMercantil().getHoja());
				datosRegistrales.setInscripcionRMercantil(interviniente.getDatRegMercantil().getNumInscripcion());
				if(interviniente.getDatRegMercantil().getLibro()!=null)
					datosRegistrales.setLibroRMercantil(interviniente.getDatRegMercantil().getLibro().toString());
				if(interviniente.getDatRegMercantil().getTomo()!=null)
					datosRegistrales.setTomoRMercantil(interviniente.getDatRegMercantil().getTomo().toString());
				intervinienteXml.setDatosRegistralesMercantil(datosRegistrales);
			}

			org.gestoresmadrid.oegam2comun.registradores.rm.rbm.DomicilioINEType domicilio = new org.gestoresmadrid.oegam2comun.registradores.rm.rbm.DomicilioINEType();
			if(interviniente.getDireccion().getBloque()!=null)
				domicilio.setBloque(interviniente.getDireccion().getBloque());
			else
				domicilio.setBloque("");
			domicilio.setCodigoPostal(interviniente.getDireccion().getCodPostal());
			domicilio.setCodMunicipio(interviniente.getDireccion().getIdMunicipio());
			domicilio.setCodProvincia(interviniente.getDireccion().getIdProvincia());
			domicilio.setCodTipoVia(interviniente.getDireccion().getIdTipoVia());
			if(interviniente.getDireccion().getPueblo()!=null)
				domicilio.setEntidadLocalMenor(interviniente.getDireccion().getPueblo());
			else
				domicilio.setEntidadLocalMenor("");
			if(interviniente.getDireccion().getEscalera()!=null)
				domicilio.setEscalera(interviniente.getDireccion().getEscalera());
			else
				domicilio.setEscalera("");
			if(interviniente.getDireccion().getRegionExtranjera()!=null)
				domicilio.setEstadoRegionProvinciaExtranjera(interviniente.getDireccion().getRegionExtranjera());
			else
				domicilio.setEstadoRegionProvinciaExtranjera("");
			domicilio.setLugarUbicacion(interviniente.getDireccion().getLugarUbicacion());
			domicilio.setNombreVia(interviniente.getDireccion().getNombreVia());
			domicilio.setNumero(interviniente.getDireccion().getNumero());
			if(interviniente.getDireccion().getNumeroBis()!=null)
				domicilio.setNumeroBis(interviniente.getDireccion().getNumeroBis());
			else
				domicilio.setNumeroBis("");
			domicilio.setPais(interviniente.getDireccion().getPais());
			if(interviniente.getDireccion().getPlanta()!=null)
				domicilio.setPlanta(interviniente.getDireccion().getPlanta());
			else
				domicilio.setPlanta("");
			if(interviniente.getDireccion().getPortal()!=null)
				domicilio.setPortal(interviniente.getDireccion().getPortal());
			else
				domicilio.setPortal("");
			if(interviniente.getDireccion().getPuerta()!=null)
				domicilio.setPuerta(interviniente.getDireccion().getPuerta());
			else
				domicilio.setPuerta("");
			if(interviniente.getDireccion().getKm()!=null)
				domicilio.setPuntoKilometrico(interviniente.getDireccion().getKm().toString());
			else
				domicilio.setPuntoKilometrico("");		
			intervinienteXml.setDomicilio(domicilio);

			org.gestoresmadrid.oegam2comun.registradores.rm.rbm.IdenSujetoType idenSujeto = new org.gestoresmadrid.oegam2comun.registradores.rm.rbm.IdenSujetoType();

			idenSujeto.setNifCif(interviniente.getNif());
			if(interviniente.getTipoPersona().equals(TipoPersonaRegistro.Fisica.getValorXML())
					|| interviniente.getTipoPersona().equals(TipoPersonaRegistro.Extranjero.getValorXML())){
				idenSujeto.setNombre(interviniente.getPersona().getNombre());
				idenSujeto.setApellidos(interviniente.getPersona().getApellidos());
			}else{
				idenSujeto.setNombre(interviniente.getPersona().getApellido1RazonSocial());
			}
			idenSujeto.setTipoPersona(TipoPersonaRegistro.convertirValorXml(interviniente.getPersona().getTipoPersona()));			
			intervinienteXml.setIdenSujeto(idenSujeto);

			if(interviniente.getTelefonos()!=null && !interviniente.getTelefonos().isEmpty()){
				org.gestoresmadrid.oegam2comun.registradores.rm.rbm.IntervinienteFiadorType.Telefonos telefonos = new org.gestoresmadrid.oegam2comun.registradores.rm.rbm.IntervinienteFiadorType.Telefonos();
				for(TelefonoDto telefonoDto : interviniente.getTelefonos()){
					org.gestoresmadrid.oegam2comun.registradores.rm.rbm.TelefonoType telefono = new org.gestoresmadrid.oegam2comun.registradores.rm.rbm.TelefonoType();
					telefono.setNumero(telefonoDto.getNumero());
					telefono.setTipo(telefonoDto.getTipo());
					telefonos.getTelefono().add(telefono);
				}
				intervinienteXml.setTelefonos(telefonos);
			}			
			//REPRESENTANTES
			if(interviniente.getRepresentantes()!=null && !interviniente.getRepresentantes().isEmpty()){
				for(IntervinienteRegistroDto representante : interviniente.getRepresentantes()){
					org.gestoresmadrid.oegam2comun.registradores.rm.rbm.Representante2Type representanteXml = new org.gestoresmadrid.oegam2comun.registradores.rm.rbm.Representante2Type();

					representanteXml.setCargo(representante.getCargo());
					if(representante.getNotario()!=null){

						SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

						org.gestoresmadrid.oegam2comun.registradores.rm.rbm.DatosNotario2Type datosNotario = new org.gestoresmadrid.oegam2comun.registradores.rm.rbm.DatosNotario2Type();
						if(representante.getNotario().getFecOtorgamiento()!=null)
							datosNotario.setFechaOtorgamiento(sdf.format(representante.getNotario().getFecOtorgamiento()));
						datosNotario.setNumeroProtocolo(representante.getNotario().getNumProtocolo());
						datosNotario.setCodProvincia(representante.getNotario().getCodProvincia());
						datosNotario.setCodMunicipio(representante.getNotario().getCodMunicipio());
						org.gestoresmadrid.oegam2comun.registradores.rm.rbm.IdenSujetoType idenSujetoNotario = new org.gestoresmadrid.oegam2comun.registradores.rm.rbm.IdenSujetoType();

						idenSujetoNotario.setNifCif(representante.getNotario().getNif());
						if(representante.getNotario().getTipoPersona().equals(TipoPersonaRegistro.Fisica.getValorXML())
								|| representante.getNotario().getTipoPersona().equals(TipoPersonaRegistro.Extranjero.getValorXML())){
							idenSujetoNotario.setNombre(representante.getNotario().getNombre());
							idenSujetoNotario.setApellidos(representante.getNotario().getApellido1() +" "+representante.getNotario().getApellido2());
						}else{
							idenSujetoNotario.setNombre(representante.getNotario().getApellido1());
						}
						idenSujetoNotario.setTipoPersona(representante.getNotario().getTipoPersona());
						datosNotario.setIdenSujeto(idenSujetoNotario);
						representanteXml.setDatosNotario(datosNotario);
					}

					org.gestoresmadrid.oegam2comun.registradores.rm.rbm.DomicilioINEType domicilioRepresentante = new org.gestoresmadrid.oegam2comun.registradores.rm.rbm.DomicilioINEType();
					if(representante.getDireccion().getBloque()!=null)
						domicilioRepresentante.setBloque(representante.getDireccion().getBloque());
					else
						domicilioRepresentante.setBloque("");
					domicilioRepresentante.setCodigoPostal(representante.getDireccion().getCodPostal());
					domicilioRepresentante.setCodMunicipio(representante.getDireccion().getIdMunicipio());
					domicilioRepresentante.setCodProvincia(representante.getDireccion().getIdProvincia());
					domicilioRepresentante.setCodTipoVia(representante.getDireccion().getIdTipoVia());
					if(representante.getDireccion().getPueblo()!=null)
						domicilioRepresentante.setEntidadLocalMenor(representante.getDireccion().getPueblo());
					else
						domicilioRepresentante.setEntidadLocalMenor("");
					if(representante.getDireccion().getEscalera()!=null)
						domicilioRepresentante.setEscalera(representante.getDireccion().getEscalera());
					else
						domicilioRepresentante.setEscalera("");
					if(representante.getDireccion().getRegionExtranjera()!=null)
						domicilioRepresentante.setEstadoRegionProvinciaExtranjera(representante.getDireccion().getRegionExtranjera());
					else
						domicilioRepresentante.setEstadoRegionProvinciaExtranjera("");
					domicilioRepresentante.setLugarUbicacion(representante.getDireccion().getLugarUbicacion());
					domicilioRepresentante.setNombreVia(representante.getDireccion().getNombreVia());
					domicilioRepresentante.setNumero(representante.getDireccion().getNumero());
					if(representante.getDireccion().getNumeroBis()!=null)
						domicilioRepresentante.setNumeroBis(representante.getDireccion().getNumeroBis());
					else
						domicilioRepresentante.setNumeroBis("");
					domicilioRepresentante.setPais(representante.getDireccion().getPais());
					if(representante.getDireccion().getPlanta()!=null)
						domicilioRepresentante.setPlanta(representante.getDireccion().getPlanta());
					else
						domicilioRepresentante.setPlanta("");
					if(representante.getDireccion().getPortal()!=null)
						domicilioRepresentante.setPortal(representante.getDireccion().getPortal());
					else
						domicilioRepresentante.setPortal("");
					if(representante.getDireccion().getPuerta()!=null)
						domicilioRepresentante.setPuerta(representante.getDireccion().getPuerta());
					else
						domicilioRepresentante.setPuerta("");
					if(representante.getDireccion().getKm()!=null)
						domicilioRepresentante.setPuntoKilometrico(representante.getDireccion().getKm().toString());
					else
						domicilioRepresentante.setPuntoKilometrico("");					
					representanteXml.setDomicilio(domicilioRepresentante);

					org.gestoresmadrid.oegam2comun.registradores.rm.rbm.IdenSujetoType idenSujetoRepresentante = new org.gestoresmadrid.oegam2comun.registradores.rm.rbm.IdenSujetoType();

					idenSujetoRepresentante.setNifCif(representante.getNif());
					if(representante.getTipoPersona().equals(TipoPersonaRegistro.Fisica.getValorXML())
							|| representante.getTipoPersona().equals(TipoPersonaRegistro.Extranjero.getValorXML())){
						idenSujetoRepresentante.setNombre(representante.getPersona().getNombre());
						idenSujetoRepresentante.setApellidos(representante.getPersona().getApellidos());
					}else{
						idenSujetoRepresentante.setNombre(representante.getPersona().getApellido1RazonSocial());
					}
					idenSujetoRepresentante.setTipoPersona(TipoPersonaRegistro.convertirValorXml(representante.getPersona().getTipoPersona()));			
					representanteXml.setIdenSujeto(idenSujetoRepresentante);					
					intervinienteXml.getRepresentante().add(representanteXml);

				}

			}
			fiadoresXml.add(intervinienteXml);
			//GC
		}
		return fiadoresXml;
	}

	private Object convertirRentingFiador(List<IntervinienteRegistroDto> fiadores) {
		List<org.gestoresmadrid.oegam2comun.registradores.rm.rbm.IntervinienteFiadorType> fiadoresXml = new ArrayList<org.gestoresmadrid.oegam2comun.registradores.rm.rbm.IntervinienteFiadorType>();
		for (IntervinienteRegistroDto interviniente : fiadores){
			org.gestoresmadrid.oegam2comun.registradores.rm.rbm.IntervinienteFiadorType intervinienteXml = new org.gestoresmadrid.oegam2comun.registradores.rm.rbm.IntervinienteFiadorType();
			intervinienteXml.setCorreoElectronico(interviniente.getPersona().getCorreoElectronico());

			if(interviniente.getDatRegMercantil()!=null){
				org.gestoresmadrid.oegam2comun.registradores.rm.rbm.DatosRegistralesMercantil2Type datosRegistrales = new org.gestoresmadrid.oegam2comun.registradores.rm.rbm.DatosRegistralesMercantil2Type();
				if(interviniente.getDatRegMercantil().getCodRegistroMercantil()!=null)
					datosRegistrales.setCodigoRMercantil(interviniente.getDatRegMercantil().getCodRegistroMercantil());
				if(interviniente.getDatRegMercantil().getFolio()!=null)
					datosRegistrales.setFolioRMercantil(interviniente.getDatRegMercantil().getFolio().toString());
				datosRegistrales.setHojaRMercantil(interviniente.getDatRegMercantil().getHoja());
				datosRegistrales.setInscripcionRMercantil(interviniente.getDatRegMercantil().getNumInscripcion());
				if(interviniente.getDatRegMercantil().getLibro()!=null)
					datosRegistrales.setLibroRMercantil(interviniente.getDatRegMercantil().getLibro().toString());
				if(interviniente.getDatRegMercantil().getTomo()!=null)
					datosRegistrales.setTomoRMercantil(interviniente.getDatRegMercantil().getTomo().toString());
				intervinienteXml.setDatosRegistralesMercantil(datosRegistrales);
			}

			org.gestoresmadrid.oegam2comun.registradores.rm.rbm.DomicilioINEType domicilio = new org.gestoresmadrid.oegam2comun.registradores.rm.rbm.DomicilioINEType();
			if(interviniente.getDireccion().getBloque()!=null)
				domicilio.setBloque(interviniente.getDireccion().getBloque());
			else
				domicilio.setBloque("");
			domicilio.setCodigoPostal(interviniente.getDireccion().getCodPostal());
			domicilio.setCodMunicipio(interviniente.getDireccion().getIdMunicipio());
			domicilio.setCodProvincia(interviniente.getDireccion().getIdProvincia());
			domicilio.setCodTipoVia(interviniente.getDireccion().getIdTipoVia());
			if(interviniente.getDireccion().getPueblo()!=null)
				domicilio.setEntidadLocalMenor(interviniente.getDireccion().getPueblo());
			else
				domicilio.setEntidadLocalMenor("");
			if(interviniente.getDireccion().getEscalera()!=null)
				domicilio.setEscalera(interviniente.getDireccion().getEscalera());
			else
				domicilio.setEscalera("");
			if(interviniente.getDireccion().getRegionExtranjera()!=null)
				domicilio.setEstadoRegionProvinciaExtranjera(interviniente.getDireccion().getRegionExtranjera());
			else
				domicilio.setEstadoRegionProvinciaExtranjera("");
			domicilio.setLugarUbicacion(interviniente.getDireccion().getLugarUbicacion());
			domicilio.setNombreVia(interviniente.getDireccion().getNombreVia());
			domicilio.setNumero(interviniente.getDireccion().getNumero());
			if(interviniente.getDireccion().getNumeroBis()!=null)
				domicilio.setNumeroBis(interviniente.getDireccion().getNumeroBis());
			else
				domicilio.setNumeroBis("");
			domicilio.setPais(interviniente.getDireccion().getPais());
			if(interviniente.getDireccion().getPlanta()!=null)
				domicilio.setPlanta(interviniente.getDireccion().getPlanta());
			else
				domicilio.setPlanta("");
			if(interviniente.getDireccion().getPortal()!=null)
				domicilio.setPortal(interviniente.getDireccion().getPortal());
			else
				domicilio.setPortal("");
			if(interviniente.getDireccion().getPuerta()!=null)
				domicilio.setPuerta(interviniente.getDireccion().getPuerta());
			else
				domicilio.setPuerta("");
			if(interviniente.getDireccion().getKm()!=null)
				domicilio.setPuntoKilometrico(interviniente.getDireccion().getKm().toString());
			else
				domicilio.setPuntoKilometrico("");		
			intervinienteXml.setDomicilio(domicilio);

			org.gestoresmadrid.oegam2comun.registradores.rm.rbm.IdenSujetoType idenSujeto = new org.gestoresmadrid.oegam2comun.registradores.rm.rbm.IdenSujetoType();

			idenSujeto.setNifCif(interviniente.getNif());
			if(interviniente.getTipoPersona().equals(TipoPersonaRegistro.Fisica.getValorXML())
					|| interviniente.getTipoPersona().equals(TipoPersonaRegistro.Extranjero.getValorXML())){
				idenSujeto.setApellidos(interviniente.getPersona().getApellidos());
				idenSujeto.setNombre(interviniente.getPersona().getNombre());
			}else{
				idenSujeto.setNombre(interviniente.getPersona().getApellido1RazonSocial());
			}
			idenSujeto.setTipoPersona(TipoPersonaRegistro.convertirValorXml(interviniente.getPersona().getTipoPersona()));			
			intervinienteXml.setIdenSujeto(idenSujeto);

			if(interviniente.getTelefonos()!=null && !interviniente.getTelefonos().isEmpty()){
				org.gestoresmadrid.oegam2comun.registradores.rm.rbm.IntervinienteFiadorType.Telefonos telefonos = new org.gestoresmadrid.oegam2comun.registradores.rm.rbm.IntervinienteFiadorType.Telefonos();
				for(TelefonoDto telefonoDto : interviniente.getTelefonos()){
					org.gestoresmadrid.oegam2comun.registradores.rm.rbm.TelefonoType telefono = new org.gestoresmadrid.oegam2comun.registradores.rm.rbm.TelefonoType();
					telefono.setNumero(telefonoDto.getNumero());
					telefono.setTipo(telefonoDto.getTipo());
					telefonos.getTelefono().add(telefono);
				}
				intervinienteXml.setTelefonos(telefonos);
			}			
			//REPRESENTANTES
			if(interviniente.getRepresentantes()!=null && !interviniente.getRepresentantes().isEmpty()){
				for(IntervinienteRegistroDto representante : interviniente.getRepresentantes()){
					org.gestoresmadrid.oegam2comun.registradores.rm.rbm.Representante2Type representanteXml = new org.gestoresmadrid.oegam2comun.registradores.rm.rbm.Representante2Type();

					representanteXml.setCargo(representante.getCargo());
					if(representante.getNotario()!=null){
						SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

						org.gestoresmadrid.oegam2comun.registradores.rm.rbm.DatosNotario2Type datosNotario = new org.gestoresmadrid.oegam2comun.registradores.rm.rbm.DatosNotario2Type();
						if(representante.getNotario().getFecOtorgamiento()!=null)
							datosNotario.setFechaOtorgamiento(sdf.format(representante.getNotario().getFecOtorgamiento()));
						datosNotario.setNumeroProtocolo(representante.getNotario().getNumProtocolo());
						datosNotario.setCodProvincia(representante.getNotario().getCodProvincia());
						datosNotario.setCodMunicipio(representante.getNotario().getCodMunicipio());
						org.gestoresmadrid.oegam2comun.registradores.rm.rbm.IdenSujetoType idenSujetoNotario = new org.gestoresmadrid.oegam2comun.registradores.rm.rbm.IdenSujetoType();

						idenSujetoNotario.setNifCif(representante.getNotario().getNif());
						if(representante.getNotario().getTipoPersona().equals(TipoPersonaRegistro.Fisica.getValorXML())
								|| representante.getNotario().getTipoPersona().equals(TipoPersonaRegistro.Extranjero.getValorXML())){
							idenSujetoNotario.setNombre(representante.getNotario().getNombre());
							idenSujetoNotario.setApellidos(representante.getNotario().getApellido1() +" "+representante.getNotario().getApellido2());
						}else{
							idenSujetoNotario.setNombre(representante.getNotario().getApellido1());
						}
						idenSujetoNotario.setTipoPersona(representante.getNotario().getTipoPersona());
						datosNotario.setIdenSujeto(idenSujetoNotario);
						representanteXml.setDatosNotario(datosNotario);
					}

					org.gestoresmadrid.oegam2comun.registradores.rm.rbm.DomicilioINEType domicilioRepresentante = new org.gestoresmadrid.oegam2comun.registradores.rm.rbm.DomicilioINEType();
					if(representante.getDireccion().getBloque()!=null)
						domicilioRepresentante.setBloque(representante.getDireccion().getBloque());
					else
						domicilioRepresentante.setBloque("");
					domicilioRepresentante.setCodigoPostal(representante.getDireccion().getCodPostal());
					domicilioRepresentante.setCodMunicipio(representante.getDireccion().getIdMunicipio());
					domicilioRepresentante.setCodProvincia(representante.getDireccion().getIdProvincia());
					domicilioRepresentante.setCodTipoVia(representante.getDireccion().getIdTipoVia());
					if(representante.getDireccion().getPueblo()!=null)
						domicilioRepresentante.setEntidadLocalMenor(representante.getDireccion().getPueblo());
					else
						domicilioRepresentante.setEntidadLocalMenor("");
					if(representante.getDireccion().getEscalera()!=null)
						domicilioRepresentante.setEscalera(representante.getDireccion().getEscalera());
					else
						domicilioRepresentante.setEscalera("");
					if(representante.getDireccion().getRegionExtranjera()!=null)
						domicilioRepresentante.setEstadoRegionProvinciaExtranjera(representante.getDireccion().getRegionExtranjera());
					else
						domicilioRepresentante.setEstadoRegionProvinciaExtranjera("");
					domicilioRepresentante.setLugarUbicacion(representante.getDireccion().getLugarUbicacion());
					domicilioRepresentante.setNombreVia(representante.getDireccion().getNombreVia());
					domicilioRepresentante.setNumero(representante.getDireccion().getNumero());
					if(representante.getDireccion().getNumeroBis()!=null)
						domicilioRepresentante.setNumeroBis(representante.getDireccion().getNumeroBis());
					else
						domicilioRepresentante.setNumeroBis("");
					domicilioRepresentante.setPais(representante.getDireccion().getPais());
					if(representante.getDireccion().getPlanta()!=null)
						domicilioRepresentante.setPlanta(representante.getDireccion().getPlanta());
					else
						domicilioRepresentante.setPlanta("");
					if(representante.getDireccion().getPortal()!=null)
						domicilioRepresentante.setPortal(representante.getDireccion().getPortal());
					else
						domicilioRepresentante.setPortal("");
					if(representante.getDireccion().getPuerta()!=null)
						domicilioRepresentante.setPuerta(representante.getDireccion().getPuerta());
					else
						domicilioRepresentante.setPuerta("");
					if(representante.getDireccion().getKm()!=null)
						domicilioRepresentante.setPuntoKilometrico(representante.getDireccion().getKm().toString());
					else
						domicilioRepresentante.setPuntoKilometrico("");					
					representanteXml.setDomicilio(domicilioRepresentante);

					org.gestoresmadrid.oegam2comun.registradores.rm.rbm.IdenSujetoType idenSujetoRepresentante = new org.gestoresmadrid.oegam2comun.registradores.rm.rbm.IdenSujetoType();

					idenSujetoRepresentante.setNifCif(representante.getNif());
					if(representante.getTipoPersona().equals(TipoPersonaRegistro.Fisica.getValorXML())
							|| representante.getTipoPersona().equals(TipoPersonaRegistro.Extranjero.getValorXML())){
						idenSujetoRepresentante.setNombre(representante.getPersona().getNombre());
						idenSujetoRepresentante.setApellidos(representante.getPersona().getApellidos());
					}else{
						idenSujetoRepresentante.setNombre(representante.getPersona().getApellido1RazonSocial());
					}
					idenSujetoRepresentante.setTipoPersona(TipoPersonaRegistro.convertirValorXml(representante.getPersona().getTipoPersona()));			
					representanteXml.setIdenSujeto(idenSujetoRepresentante);					
					intervinienteXml.getRepresentante().add(representanteXml);

				}

			}
			fiadoresXml.add(intervinienteXml);
		}
		return fiadoresXml;
	}

	private Object convertirFinancingCesionario(List<IntervinienteRegistroDto> cesionarios) {
		List<CesionarioType> cesionariosXml = new ArrayList<CesionarioType>();
		for (IntervinienteRegistroDto interviniente : cesionarios){
			CesionarioType cesionarioXml = new CesionarioType();
			org.gestoresmadrid.oegam2comun.registradores.rm.rbm.IdenSujetoType idenSujeto = new org.gestoresmadrid.oegam2comun.registradores.rm.rbm.IdenSujetoType();

			idenSujeto.setNifCif(interviniente.getNif());
			if(interviniente.getTipoPersona().equals(TipoPersonaRegistro.Fisica.getValorXML())
					|| interviniente.getTipoPersona().equals(TipoPersonaRegistro.Extranjero.getValorXML())){
				idenSujeto.setNombre(interviniente.getPersona().getNombre());
				idenSujeto.setApellidos(interviniente.getPersona().getApellidos());
			}else{
				idenSujeto.setNombre(interviniente.getPersona().getApellido1RazonSocial());
			}
			idenSujeto.setTipoPersona(TipoPersonaRegistro.convertirValorXml(interviniente.getPersona().getTipoPersona()));
			cesionarioXml.setIdenSujeto(idenSujeto);

			org.gestoresmadrid.oegam2comun.registradores.rm.rbm.DomicilioINEType domicilio = new org.gestoresmadrid.oegam2comun.registradores.rm.rbm.DomicilioINEType();
			if(interviniente.getDireccion().getBloque()!=null)
				domicilio.setBloque(interviniente.getDireccion().getBloque());
			else
				domicilio.setBloque("");
			domicilio.setCodigoPostal(interviniente.getDireccion().getCodPostal());
			domicilio.setCodMunicipio(interviniente.getDireccion().getIdMunicipio());
			domicilio.setCodProvincia(interviniente.getDireccion().getIdProvincia());
			domicilio.setCodTipoVia(interviniente.getDireccion().getIdTipoVia());
			if(interviniente.getDireccion().getPueblo()!=null)
				domicilio.setEntidadLocalMenor(interviniente.getDireccion().getPueblo());
			else
				domicilio.setEntidadLocalMenor("");
			if(interviniente.getDireccion().getEscalera()!=null)
				domicilio.setEscalera(interviniente.getDireccion().getEscalera());
			else
				domicilio.setEscalera("");
			if(interviniente.getDireccion().getRegionExtranjera()!=null)
				domicilio.setEstadoRegionProvinciaExtranjera(interviniente.getDireccion().getRegionExtranjera());
			else
				domicilio.setEstadoRegionProvinciaExtranjera("");
			domicilio.setLugarUbicacion(interviniente.getDireccion().getLugarUbicacion());
			domicilio.setNombreVia(interviniente.getDireccion().getNombreVia());
			domicilio.setNumero(interviniente.getDireccion().getNumero());
			if(interviniente.getDireccion().getNumeroBis()!=null)
				domicilio.setNumeroBis(interviniente.getDireccion().getNumeroBis());
			else
				domicilio.setNumeroBis("");
			domicilio.setPais(interviniente.getDireccion().getPais());
			if(interviniente.getDireccion().getPlanta()!=null)
				domicilio.setPlanta(interviniente.getDireccion().getPlanta());
			else
				domicilio.setPlanta("");
			if(interviniente.getDireccion().getPortal()!=null)
				domicilio.setPortal(interviniente.getDireccion().getPortal());
			else
				domicilio.setPortal("");
			if(interviniente.getDireccion().getPuerta()!=null)
				domicilio.setPuerta(interviniente.getDireccion().getPuerta());
			else
				domicilio.setPuerta("");
			if(interviniente.getDireccion().getKm()!=null)
				domicilio.setPuntoKilometrico(interviniente.getDireccion().getKm().toString());
			else
				domicilio.setPuntoKilometrico("");		
			cesionarioXml.setDomicilio(domicilio);

			if(interviniente.getDatRegMercantil()!=null){
				org.gestoresmadrid.oegam2comun.registradores.rm.rbm.DatosRegistralesMercantil2Type datosRegistrales = new org.gestoresmadrid.oegam2comun.registradores.rm.rbm.DatosRegistralesMercantil2Type();
				if(interviniente.getDatRegMercantil().getCodRegistroMercantil()!=null)
					datosRegistrales.setCodigoRMercantil(interviniente.getDatRegMercantil().getCodRegistroMercantil());
				if(interviniente.getDatRegMercantil().getFolio()!=null)
					datosRegistrales.setFolioRMercantil(interviniente.getDatRegMercantil().getFolio().toString());
				datosRegistrales.setHojaRMercantil(interviniente.getDatRegMercantil().getHoja());
				datosRegistrales.setInscripcionRMercantil(interviniente.getDatRegMercantil().getNumInscripcion());
				if(interviniente.getDatRegMercantil().getLibro()!=null)
					datosRegistrales.setLibroRMercantil(interviniente.getDatRegMercantil().getLibro().toString());
				if(interviniente.getDatRegMercantil().getTomo()!=null)
					datosRegistrales.setTomoRMercantil(interviniente.getDatRegMercantil().getTomo().toString());
				cesionarioXml.setDatosRegistralesMercantil(datosRegistrales);
			}

			//REPRESENTANTE SOLO PUEDE TENER 1
			if(interviniente.getRepresentantes()!=null && !interviniente.getRepresentantes().isEmpty()){
				org.gestoresmadrid.oegam2comun.registradores.rm.rbm.Representante2Type representanteXml = new org.gestoresmadrid.oegam2comun.registradores.rm.rbm.Representante2Type();

				IntervinienteRegistroDto representante = interviniente.getRepresentantes().get(0);
				representanteXml.setCargo(representante.getCargo());
				if(representante.getNotario()!=null){
					SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

					org.gestoresmadrid.oegam2comun.registradores.rm.rbm.DatosNotario2Type datosNotario = new org.gestoresmadrid.oegam2comun.registradores.rm.rbm.DatosNotario2Type();
					if(representante.getNotario().getFecOtorgamiento()!=null)
						datosNotario.setFechaOtorgamiento(sdf.format(representante.getNotario().getFecOtorgamiento()));
					datosNotario.setNumeroProtocolo(representante.getNotario().getNumProtocolo());
					datosNotario.setCodProvincia(representante.getNotario().getCodProvincia());
					datosNotario.setCodMunicipio(representante.getNotario().getCodMunicipio());
					org.gestoresmadrid.oegam2comun.registradores.rm.rbm.IdenSujetoType idenSujetoNotario = new org.gestoresmadrid.oegam2comun.registradores.rm.rbm.IdenSujetoType();

					idenSujetoNotario.setNifCif(representante.getNotario().getNif());
					if(representante.getNotario().getTipoPersona().equals(TipoPersonaRegistro.Fisica.getValorXML())
							|| representante.getNotario().getTipoPersona().equals(TipoPersonaRegistro.Extranjero.getValorXML())){
						idenSujetoNotario.setNombre(representante.getNotario().getNombre());
						idenSujetoNotario.setApellidos(representante.getNotario().getApellido1() +" "+representante.getNotario().getApellido2());
					}else{
						idenSujetoNotario.setNombre(representante.getNotario().getApellido1());
					}
					idenSujetoNotario.setTipoPersona(representante.getNotario().getTipoPersona());
					datosNotario.setIdenSujeto(idenSujetoNotario);
					representanteXml.setDatosNotario(datosNotario);
				}

				org.gestoresmadrid.oegam2comun.registradores.rm.rbm.DomicilioINEType domicilioRepresentante = new org.gestoresmadrid.oegam2comun.registradores.rm.rbm.DomicilioINEType();
				if(representante.getDireccion().getBloque()!=null)
					domicilioRepresentante.setBloque(representante.getDireccion().getBloque());
				else
					domicilioRepresentante.setBloque("");
				domicilioRepresentante.setCodigoPostal(representante.getDireccion().getCodPostal());
				domicilioRepresentante.setCodMunicipio(representante.getDireccion().getIdMunicipio());
				domicilioRepresentante.setCodProvincia(representante.getDireccion().getIdProvincia());
				domicilioRepresentante.setCodTipoVia(representante.getDireccion().getIdTipoVia());
				if(representante.getDireccion().getPueblo()!=null)
					domicilioRepresentante.setEntidadLocalMenor(representante.getDireccion().getPueblo());
				else
					domicilioRepresentante.setEntidadLocalMenor("");
				if(representante.getDireccion().getEscalera()!=null)
					domicilioRepresentante.setEscalera(representante.getDireccion().getEscalera());
				else
					domicilioRepresentante.setEscalera("");
				if(representante.getDireccion().getRegionExtranjera()!=null)
					domicilioRepresentante.setEstadoRegionProvinciaExtranjera(representante.getDireccion().getRegionExtranjera());
				else
					domicilioRepresentante.setEstadoRegionProvinciaExtranjera("");
				domicilioRepresentante.setLugarUbicacion(representante.getDireccion().getLugarUbicacion());
				domicilioRepresentante.setNombreVia(representante.getDireccion().getNombreVia());
				domicilioRepresentante.setNumero(representante.getDireccion().getNumero());
				if(representante.getDireccion().getNumeroBis()!=null)
					domicilioRepresentante.setNumeroBis(representante.getDireccion().getNumeroBis());
				else
					domicilioRepresentante.setNumeroBis("");
				domicilioRepresentante.setPais(representante.getDireccion().getPais());
				if(representante.getDireccion().getPlanta()!=null)
					domicilioRepresentante.setPlanta(representante.getDireccion().getPlanta());
				else
					domicilioRepresentante.setPlanta("");
				if(representante.getDireccion().getPortal()!=null)
					domicilioRepresentante.setPortal(representante.getDireccion().getPortal());
				else
					domicilioRepresentante.setPortal("");
				if(representante.getDireccion().getPuerta()!=null)
					domicilioRepresentante.setPuerta(representante.getDireccion().getPuerta());
				else
					domicilioRepresentante.setPuerta("");
				if(representante.getDireccion().getKm()!=null)
					domicilioRepresentante.setPuntoKilometrico(representante.getDireccion().getKm().toString());
				else
					domicilioRepresentante.setPuntoKilometrico("");					
				representanteXml.setDomicilio(domicilioRepresentante);

				org.gestoresmadrid.oegam2comun.registradores.rm.rbm.IdenSujetoType idenSujetoRepresentante = new org.gestoresmadrid.oegam2comun.registradores.rm.rbm.IdenSujetoType();

				idenSujetoRepresentante.setNifCif(representante.getNif());
				if(representante.getTipoPersona().equals(TipoPersonaRegistro.Fisica.getValorXML())
						|| representante.getTipoPersona().equals(TipoPersonaRegistro.Extranjero.getValorXML())){
					idenSujetoRepresentante.setNombre(representante.getPersona().getNombre());
					idenSujetoRepresentante.setApellidos(representante.getPersona().getApellidos());
				}else{
					idenSujetoRepresentante.setNombre(representante.getPersona().getApellido1RazonSocial());
				}

				idenSujetoRepresentante.setTipoPersona(TipoPersonaRegistro.convertirValorXml(representante.getPersona().getTipoPersona()));			
				representanteXml.setIdenSujeto(idenSujetoRepresentante);					
				cesionarioXml.setRepresentante(representanteXml);

			}

			cesionariosXml.add(cesionarioXml);

		}
		return cesionariosXml;
	}

	private Object convertirLeasingCesionario(List<IntervinienteRegistroDto> cesionarios) {
		List<org.gestoresmadrid.oegam2comun.registradores.rm.rbm.CesionarioType> cesionariosXml = new ArrayList<org.gestoresmadrid.oegam2comun.registradores.rm.rbm.CesionarioType>();
		for (IntervinienteRegistroDto interviniente : cesionarios){
			org.gestoresmadrid.oegam2comun.registradores.rm.rbm.CesionarioType cesionarioXml = new org.gestoresmadrid.oegam2comun.registradores.rm.rbm.CesionarioType();
			org.gestoresmadrid.oegam2comun.registradores.rm.rbm.IdenSujetoType idenSujeto = new org.gestoresmadrid.oegam2comun.registradores.rm.rbm.IdenSujetoType();

			idenSujeto.setNifCif(interviniente.getNif());
			if(interviniente.getTipoPersona().equals(TipoPersonaRegistro.Fisica.getValorXML())
					|| interviniente.getTipoPersona().equals(TipoPersonaRegistro.Extranjero.getValorXML())){
				idenSujeto.setNombre(interviniente.getPersona().getNombre());
				idenSujeto.setApellidos(interviniente.getPersona().getApellidos());
			}else{
				idenSujeto.setNombre(interviniente.getPersona().getApellido1RazonSocial());
			}
			idenSujeto.setTipoPersona(TipoPersonaRegistro.convertirValorXml(interviniente.getPersona().getTipoPersona()));
			cesionarioXml.setIdenSujeto(idenSujeto);

			org.gestoresmadrid.oegam2comun.registradores.rm.rbm.DomicilioINEType domicilio = new org.gestoresmadrid.oegam2comun.registradores.rm.rbm.DomicilioINEType();
			if(interviniente.getDireccion().getBloque()!=null)
				domicilio.setBloque(interviniente.getDireccion().getBloque());
			else
				domicilio.setBloque("");
			domicilio.setCodigoPostal(interviniente.getDireccion().getCodPostal());
			domicilio.setCodMunicipio(interviniente.getDireccion().getIdMunicipio());
			domicilio.setCodProvincia(interviniente.getDireccion().getIdProvincia());
			domicilio.setCodTipoVia(interviniente.getDireccion().getIdTipoVia());
			if(interviniente.getDireccion().getPueblo()!=null)
				domicilio.setEntidadLocalMenor(interviniente.getDireccion().getPueblo());
			else
				domicilio.setEntidadLocalMenor("");
			if(interviniente.getDireccion().getEscalera()!=null)
				domicilio.setEscalera(interviniente.getDireccion().getEscalera());
			else
				domicilio.setEscalera("");
			if(interviniente.getDireccion().getRegionExtranjera()!=null)
				domicilio.setEstadoRegionProvinciaExtranjera(interviniente.getDireccion().getRegionExtranjera());
			else
				domicilio.setEstadoRegionProvinciaExtranjera("");
			domicilio.setLugarUbicacion(interviniente.getDireccion().getLugarUbicacion());
			domicilio.setNombreVia(interviniente.getDireccion().getNombreVia());
			domicilio.setNumero(interviniente.getDireccion().getNumero());
			if(interviniente.getDireccion().getNumeroBis()!=null)
				domicilio.setNumeroBis(interviniente.getDireccion().getNumeroBis());
			else
				domicilio.setNumeroBis("");
			domicilio.setPais(interviniente.getDireccion().getPais());
			if(interviniente.getDireccion().getPlanta()!=null)
				domicilio.setPlanta(interviniente.getDireccion().getPlanta());
			else
				domicilio.setPlanta("");
			if(interviniente.getDireccion().getPortal()!=null)
				domicilio.setPortal(interviniente.getDireccion().getPortal());
			else
				domicilio.setPortal("");
			if(interviniente.getDireccion().getPuerta()!=null)
				domicilio.setPuerta(interviniente.getDireccion().getPuerta());
			else
				domicilio.setPuerta("");
			if(interviniente.getDireccion().getKm()!=null)
				domicilio.setPuntoKilometrico(interviniente.getDireccion().getKm().toString());
			else
				domicilio.setPuntoKilometrico("");		
			cesionarioXml.setDomicilio(domicilio);

			if(interviniente.getDatRegMercantil()!=null){
				org.gestoresmadrid.oegam2comun.registradores.rm.rbm.DatosRegistralesMercantil2Type datosRegistrales = new org.gestoresmadrid.oegam2comun.registradores.rm.rbm.DatosRegistralesMercantil2Type();
				if(interviniente.getDatRegMercantil().getCodRegistroMercantil()!=null)
					datosRegistrales.setCodigoRMercantil(interviniente.getDatRegMercantil().getCodRegistroMercantil());
				if(interviniente.getDatRegMercantil().getFolio()!=null)
					datosRegistrales.setFolioRMercantil(interviniente.getDatRegMercantil().getFolio().toString());
				datosRegistrales.setHojaRMercantil(interviniente.getDatRegMercantil().getHoja());
				datosRegistrales.setInscripcionRMercantil(interviniente.getDatRegMercantil().getNumInscripcion());
				if(interviniente.getDatRegMercantil().getLibro()!=null)
					datosRegistrales.setLibroRMercantil(interviniente.getDatRegMercantil().getLibro().toString());
				if(interviniente.getDatRegMercantil().getTomo()!=null)
					datosRegistrales.setTomoRMercantil(interviniente.getDatRegMercantil().getTomo().toString());
				cesionarioXml.setDatosRegistralesMercantil(datosRegistrales);
				datosRegistrales=null;
			}

			//REPRESENTANTE SOLO PUEDE TENER 1
			if(interviniente.getRepresentantes()!=null && !interviniente.getRepresentantes().isEmpty()){
				org.gestoresmadrid.oegam2comun.registradores.rm.rbm.Representante2Type representanteXml = new org.gestoresmadrid.oegam2comun.registradores.rm.rbm.Representante2Type();

				IntervinienteRegistroDto representante = interviniente.getRepresentantes().get(0);
				representanteXml.setCargo(representante.getCargo());
				if(representante.getNotario()!=null){
					SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

					org.gestoresmadrid.oegam2comun.registradores.rm.rbm.DatosNotario2Type datosNotario = new org.gestoresmadrid.oegam2comun.registradores.rm.rbm.DatosNotario2Type();
					if(representante.getNotario().getFecOtorgamiento()!=null)
						datosNotario.setFechaOtorgamiento(sdf.format(representante.getNotario().getFecOtorgamiento()));
					datosNotario.setNumeroProtocolo(representante.getNotario().getNumProtocolo());
					datosNotario.setCodProvincia(representante.getNotario().getCodProvincia());
					datosNotario.setCodMunicipio(representante.getNotario().getCodMunicipio());
					org.gestoresmadrid.oegam2comun.registradores.rm.rbm.IdenSujetoType idenSujetoNotario = new org.gestoresmadrid.oegam2comun.registradores.rm.rbm.IdenSujetoType();

					idenSujetoNotario.setNifCif(representante.getNotario().getNif());
					if(representante.getNotario().getTipoPersona().equals(TipoPersonaRegistro.Fisica.getValorXML())
							|| representante.getNotario().getTipoPersona().equals(TipoPersonaRegistro.Extranjero.getValorXML())){
						idenSujetoNotario.setNombre(representante.getNotario().getNombre());
						idenSujetoNotario.setApellidos(representante.getNotario().getApellido1() +" "+representante.getNotario().getApellido2());
					}else{
						idenSujetoNotario.setNombre(representante.getNotario().getApellido1());
					}
					idenSujetoNotario.setTipoPersona(representante.getNotario().getTipoPersona());
					datosNotario.setIdenSujeto(idenSujetoNotario);
					representanteXml.setDatosNotario(datosNotario);
				}

				org.gestoresmadrid.oegam2comun.registradores.rm.rbm.DomicilioINEType domicilioRepresentante = new org.gestoresmadrid.oegam2comun.registradores.rm.rbm.DomicilioINEType();
				if(representante.getDireccion().getBloque()!=null)
					domicilioRepresentante.setBloque(representante.getDireccion().getBloque());
				else
					domicilioRepresentante.setBloque("");
				domicilioRepresentante.setCodigoPostal(representante.getDireccion().getCodPostal());
				domicilioRepresentante.setCodMunicipio(representante.getDireccion().getIdMunicipio());
				domicilioRepresentante.setCodProvincia(representante.getDireccion().getIdProvincia());
				domicilioRepresentante.setCodTipoVia(representante.getDireccion().getIdTipoVia());
				if(representante.getDireccion().getPueblo()!=null)
					domicilioRepresentante.setEntidadLocalMenor(representante.getDireccion().getPueblo());
				else
					domicilioRepresentante.setEntidadLocalMenor("");
				if(representante.getDireccion().getEscalera()!=null)
					domicilioRepresentante.setEscalera(representante.getDireccion().getEscalera());
				else
					domicilioRepresentante.setEscalera("");
				if(representante.getDireccion().getRegionExtranjera()!=null)
					domicilioRepresentante.setEstadoRegionProvinciaExtranjera(representante.getDireccion().getRegionExtranjera());
				else
					domicilioRepresentante.setEstadoRegionProvinciaExtranjera("");
				domicilioRepresentante.setLugarUbicacion(representante.getDireccion().getLugarUbicacion());
				domicilioRepresentante.setNombreVia(representante.getDireccion().getNombreVia());
				domicilioRepresentante.setNumero(representante.getDireccion().getNumero());
				if(representante.getDireccion().getNumeroBis()!=null)
					domicilioRepresentante.setNumeroBis(representante.getDireccion().getNumeroBis());
				else
					domicilioRepresentante.setNumeroBis("");
				domicilioRepresentante.setPais(representante.getDireccion().getPais());
				if(representante.getDireccion().getPlanta()!=null)
					domicilioRepresentante.setPlanta(representante.getDireccion().getPlanta());
				else
					domicilioRepresentante.setPlanta("");
				if(representante.getDireccion().getPortal()!=null)
					domicilioRepresentante.setPortal(representante.getDireccion().getPortal());
				else
					domicilioRepresentante.setPortal("");
				if(representante.getDireccion().getPuerta()!=null)
					domicilioRepresentante.setPuerta(representante.getDireccion().getPuerta());
				else
					domicilioRepresentante.setPuerta("");
				if(representante.getDireccion().getKm()!=null)
					domicilioRepresentante.setPuntoKilometrico(representante.getDireccion().getKm().toString());
				else
					domicilioRepresentante.setPuntoKilometrico("");					
				representanteXml.setDomicilio(domicilioRepresentante);

				org.gestoresmadrid.oegam2comun.registradores.rm.rbm.IdenSujetoType idenSujetoRepresentante = new org.gestoresmadrid.oegam2comun.registradores.rm.rbm.IdenSujetoType();

				idenSujetoRepresentante.setNifCif(representante.getNif());
				if(representante.getTipoPersona().equals(TipoPersonaRegistro.Fisica.getValorXML())
						|| representante.getTipoPersona().equals(TipoPersonaRegistro.Extranjero.getValorXML())){
					idenSujetoRepresentante.setNombre(representante.getPersona().getNombre());
					idenSujetoRepresentante.setApellidos(representante.getPersona().getApellidos());
				}else{
					idenSujetoRepresentante.setNombre(representante.getPersona().getApellido1RazonSocial());
				}

				idenSujetoRepresentante.setTipoPersona(TipoPersonaRegistro.convertirValorXml(representante.getPersona().getTipoPersona()));			
				representanteXml.setIdenSujeto(idenSujetoRepresentante);					
				cesionarioXml.setRepresentante(representanteXml);

			}

			cesionariosXml.add(cesionarioXml);
			//GC		
		}
		return cesionariosXml;
	}

	private Object convertirRentingCesionario(List<IntervinienteRegistroDto> cesionarios) {
		List<org.gestoresmadrid.oegam2comun.registradores.rm.rbm.CesionarioType> cesionariosXml = new ArrayList<org.gestoresmadrid.oegam2comun.registradores.rm.rbm.CesionarioType>();
		for (IntervinienteRegistroDto interviniente : cesionarios){
			org.gestoresmadrid.oegam2comun.registradores.rm.rbm.CesionarioType cesionarioXml = new org.gestoresmadrid.oegam2comun.registradores.rm.rbm.CesionarioType();
			org.gestoresmadrid.oegam2comun.registradores.rm.rbm.IdenSujetoType idenSujeto = new org.gestoresmadrid.oegam2comun.registradores.rm.rbm.IdenSujetoType();

			idenSujeto.setNifCif(interviniente.getNif());
			if(interviniente.getTipoPersona().equals(TipoPersonaRegistro.Fisica.getValorXML())
					|| interviniente.getTipoPersona().equals(TipoPersonaRegistro.Extranjero.getValorXML())){
				idenSujeto.setNombre(interviniente.getPersona().getNombre());
				idenSujeto.setApellidos(interviniente.getPersona().getApellidos());
			}else{
				idenSujeto.setNombre(interviniente.getPersona().getApellido1RazonSocial());
			}
			idenSujeto.setTipoPersona(TipoPersonaRegistro.convertirValorXml(interviniente.getPersona().getTipoPersona()));
			cesionarioXml.setIdenSujeto(idenSujeto);

			org.gestoresmadrid.oegam2comun.registradores.rm.rbm.DomicilioINEType domicilio = new org.gestoresmadrid.oegam2comun.registradores.rm.rbm.DomicilioINEType();
			if(interviniente.getDireccion().getBloque()!=null)
				domicilio.setBloque(interviniente.getDireccion().getBloque());
			else
				domicilio.setBloque("");
			domicilio.setCodigoPostal(interviniente.getDireccion().getCodPostal());
			domicilio.setCodMunicipio(interviniente.getDireccion().getIdMunicipio());
			domicilio.setCodProvincia(interviniente.getDireccion().getIdProvincia());
			domicilio.setCodTipoVia(interviniente.getDireccion().getIdTipoVia());
			if(interviniente.getDireccion().getPueblo()!=null)
				domicilio.setEntidadLocalMenor(interviniente.getDireccion().getPueblo());
			else
				domicilio.setEntidadLocalMenor("");
			if(interviniente.getDireccion().getEscalera()!=null)
				domicilio.setEscalera(interviniente.getDireccion().getEscalera());
			else
				domicilio.setEscalera("");
			if(interviniente.getDireccion().getRegionExtranjera()!=null)
				domicilio.setEstadoRegionProvinciaExtranjera(interviniente.getDireccion().getRegionExtranjera());
			else
				domicilio.setEstadoRegionProvinciaExtranjera("");
			domicilio.setLugarUbicacion(interviniente.getDireccion().getLugarUbicacion());
			domicilio.setNombreVia(interviniente.getDireccion().getNombreVia());
			domicilio.setNumero(interviniente.getDireccion().getNumero());
			if(interviniente.getDireccion().getNumeroBis()!=null)
				domicilio.setNumeroBis(interviniente.getDireccion().getNumeroBis());
			else
				domicilio.setNumeroBis("");
			domicilio.setPais(interviniente.getDireccion().getPais());
			if(interviniente.getDireccion().getPlanta()!=null)
				domicilio.setPlanta(interviniente.getDireccion().getPlanta());
			else
				domicilio.setPlanta("");
			if(interviniente.getDireccion().getPortal()!=null)
				domicilio.setPortal(interviniente.getDireccion().getPortal());
			else
				domicilio.setPortal("");
			if(interviniente.getDireccion().getPuerta()!=null)
				domicilio.setPuerta(interviniente.getDireccion().getPuerta());
			else
				domicilio.setPuerta("");
			if(interviniente.getDireccion().getKm()!=null)
				domicilio.setPuntoKilometrico(interviniente.getDireccion().getKm().toString());
			else
				domicilio.setPuntoKilometrico("");		
			cesionarioXml.setDomicilio(domicilio);

			if(interviniente.getDatRegMercantil()!=null){
				org.gestoresmadrid.oegam2comun.registradores.rm.rbm.DatosRegistralesMercantil2Type datosRegistrales = new org.gestoresmadrid.oegam2comun.registradores.rm.rbm.DatosRegistralesMercantil2Type();
				if(interviniente.getDatRegMercantil().getCodRegistroMercantil()!=null)
					datosRegistrales.setCodigoRMercantil(interviniente.getDatRegMercantil().getCodRegistroMercantil());
				if(interviniente.getDatRegMercantil().getFolio()!=null)
					datosRegistrales.setFolioRMercantil(interviniente.getDatRegMercantil().getFolio().toString());
				datosRegistrales.setHojaRMercantil(interviniente.getDatRegMercantil().getHoja());
				datosRegistrales.setInscripcionRMercantil(interviniente.getDatRegMercantil().getNumInscripcion());
				if(interviniente.getDatRegMercantil().getLibro()!=null)
					datosRegistrales.setLibroRMercantil(interviniente.getDatRegMercantil().getLibro().toString());
				if(interviniente.getDatRegMercantil().getTomo()!=null)
					datosRegistrales.setTomoRMercantil(interviniente.getDatRegMercantil().getTomo().toString());
				cesionarioXml.setDatosRegistralesMercantil(datosRegistrales);
			}

			//REPRESENTANTE SOLO PUEDE TENER 1
			if(interviniente.getRepresentantes()!=null && !interviniente.getRepresentantes().isEmpty()){
				org.gestoresmadrid.oegam2comun.registradores.rm.rbm.Representante2Type representanteXml = new org.gestoresmadrid.oegam2comun.registradores.rm.rbm.Representante2Type();

				IntervinienteRegistroDto representante = interviniente.getRepresentantes().get(0);
				representanteXml.setCargo(representante.getCargo());
				if(representante.getNotario()!=null){
					SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

					org.gestoresmadrid.oegam2comun.registradores.rm.rbm.DatosNotario2Type datosNotario = new org.gestoresmadrid.oegam2comun.registradores.rm.rbm.DatosNotario2Type();
					if(representante.getNotario().getFecOtorgamiento()!=null)
						datosNotario.setFechaOtorgamiento(sdf.format(representante.getNotario().getFecOtorgamiento()));
					datosNotario.setNumeroProtocolo(representante.getNotario().getNumProtocolo());
					datosNotario.setCodProvincia(representante.getNotario().getCodProvincia());
					datosNotario.setCodMunicipio(representante.getNotario().getCodMunicipio());
					org.gestoresmadrid.oegam2comun.registradores.rm.rbm.IdenSujetoType idenSujetoNotario = new org.gestoresmadrid.oegam2comun.registradores.rm.rbm.IdenSujetoType();

					idenSujetoNotario.setNifCif(representante.getNotario().getNif());
					if(representante.getNotario().getTipoPersona().equals(TipoPersonaRegistro.Fisica.getValorXML())
							|| representante.getNotario().getTipoPersona().equals(TipoPersonaRegistro.Extranjero.getValorXML())){
						idenSujetoNotario.setNombre(representante.getNotario().getNombre());
						idenSujetoNotario.setApellidos(representante.getNotario().getApellido1() +" "+representante.getNotario().getApellido2());
					}else{
						idenSujetoNotario.setNombre(representante.getNotario().getApellido1());
					}
					idenSujetoNotario.setTipoPersona(representante.getNotario().getTipoPersona());
					datosNotario.setIdenSujeto(idenSujetoNotario);
					representanteXml.setDatosNotario(datosNotario);
				}

				org.gestoresmadrid.oegam2comun.registradores.rm.rbm.DomicilioINEType domicilioRepresentante = new org.gestoresmadrid.oegam2comun.registradores.rm.rbm.DomicilioINEType();
				if(representante.getDireccion().getBloque()!=null)
					domicilioRepresentante.setBloque(representante.getDireccion().getBloque());
				else
					domicilioRepresentante.setBloque("");
				domicilioRepresentante.setCodigoPostal(representante.getDireccion().getCodPostal());
				domicilioRepresentante.setCodMunicipio(representante.getDireccion().getIdMunicipio());
				domicilioRepresentante.setCodProvincia(representante.getDireccion().getIdProvincia());
				domicilioRepresentante.setCodTipoVia(representante.getDireccion().getIdTipoVia());
				if(representante.getDireccion().getPueblo()!=null)
					domicilioRepresentante.setEntidadLocalMenor(representante.getDireccion().getPueblo());
				else
					domicilioRepresentante.setEntidadLocalMenor("");
				if(representante.getDireccion().getEscalera()!=null)
					domicilioRepresentante.setEscalera(representante.getDireccion().getEscalera());
				else
					domicilioRepresentante.setEscalera("");
				if(representante.getDireccion().getRegionExtranjera()!=null)
					domicilioRepresentante.setEstadoRegionProvinciaExtranjera(representante.getDireccion().getRegionExtranjera());
				else
					domicilioRepresentante.setEstadoRegionProvinciaExtranjera("");
				domicilioRepresentante.setLugarUbicacion(representante.getDireccion().getLugarUbicacion());
				domicilioRepresentante.setNombreVia(representante.getDireccion().getNombreVia());
				domicilioRepresentante.setNumero(representante.getDireccion().getNumero());
				if(representante.getDireccion().getNumeroBis()!=null)
					domicilioRepresentante.setNumeroBis(representante.getDireccion().getNumeroBis());
				else
					domicilioRepresentante.setNumeroBis("");
				domicilioRepresentante.setPais(representante.getDireccion().getPais());
				if(representante.getDireccion().getPlanta()!=null)
					domicilioRepresentante.setPlanta(representante.getDireccion().getPlanta());
				else
					domicilioRepresentante.setPlanta("");
				if(representante.getDireccion().getPortal()!=null)
					domicilioRepresentante.setPortal(representante.getDireccion().getPortal());
				else
					domicilioRepresentante.setPortal("");
				if(representante.getDireccion().getPuerta()!=null)
					domicilioRepresentante.setPuerta(representante.getDireccion().getPuerta());
				else
					domicilioRepresentante.setPuerta("");
				if(representante.getDireccion().getKm()!=null)
					domicilioRepresentante.setPuntoKilometrico(representante.getDireccion().getKm().toString());
				else
					domicilioRepresentante.setPuntoKilometrico("");					
				representanteXml.setDomicilio(domicilioRepresentante);

				org.gestoresmadrid.oegam2comun.registradores.rm.rbm.IdenSujetoType idenSujetoRepresentante = new org.gestoresmadrid.oegam2comun.registradores.rm.rbm.IdenSujetoType();

				idenSujetoRepresentante.setNifCif(representante.getNif());
				if(representante.getTipoPersona().equals(TipoPersonaRegistro.Fisica.getValorXML())
						|| representante.getTipoPersona().equals(TipoPersonaRegistro.Extranjero.getValorXML())){
					idenSujetoRepresentante.setNombre(representante.getPersona().getNombre());
					idenSujetoRepresentante.setApellidos(representante.getPersona().getApellidos());
				}else{
					idenSujetoRepresentante.setNombre(representante.getPersona().getApellido1RazonSocial());
				}
				idenSujetoRepresentante.setTipoPersona(TipoPersonaRegistro.convertirValorXml(representante.getPersona().getTipoPersona()));			
				representanteXml.setIdenSujeto(idenSujetoRepresentante);					
				cesionarioXml.setRepresentante(representanteXml);

			}
			cesionariosXml.add(cesionarioXml);		
		}
		return cesionariosXml;
	}

	private SolicitanteCancelacionType convertirCancelacionSolicitante(IntervinienteRegistroDto solicitante) {
		org.gestoresmadrid.oegam2comun.registradores.rm.rbm.SolicitanteCancelacionType solicitanteXml = new org.gestoresmadrid.oegam2comun.registradores.rm.rbm.SolicitanteCancelacionType();
		solicitanteXml.setCodigoIdentificacionFiscal(solicitante.getNif());
		String razonSocial = (StringUtils.isNotBlank(solicitante.getPersona().getNombre())?solicitante.getPersona().getNombre():"")
				+ " " + 
				(StringUtils.isNotBlank(solicitante.getPersona().getApellido1RazonSocial())?solicitante.getPersona().getApellido1RazonSocial():"")
				+ " " + 
				(StringUtils.isNotBlank(solicitante.getPersona().getApellido2())?solicitante.getPersona().getApellido2():"");
		solicitanteXml.setRazonSocial(razonSocial);

		solicitanteXml.setCorreoElectronico(solicitante.getPersona().getCorreoElectronico());

		org.gestoresmadrid.oegam2comun.registradores.rm.rbm.DomicilioSocialCancelacionType domicilio = new org.gestoresmadrid.oegam2comun.registradores.rm.rbm.DomicilioSocialCancelacionType();

		domicilio.setCodigoPostal(solicitante.getDireccion().getCodPostal());
		domicilio.setMunicipio(solicitante.getDireccion().getIdMunicipio());
		domicilio.setProvincia(solicitante.getDireccion().getIdProvincia());

		StringBuffer stringDomicilio = new StringBuffer();
		stringDomicilio.append(solicitante.getDireccion().getNombreVia()).append(" ")
		.append(solicitante.getDireccion().getNumero()).append(" ");
		if(StringUtils.isNotBlank(solicitante.getDireccion().getBloque())){
			stringDomicilio.append("Bloque:").append(solicitante.getDireccion().getBloque()).append(" ");
		}
		if(StringUtils.isNotBlank(solicitante.getDireccion().getPlanta())){
			stringDomicilio.append("Planta:").append(solicitante.getDireccion().getPlanta()).append(" ");
		}
		if(StringUtils.isNotBlank(solicitante.getDireccion().getPuerta())){
			stringDomicilio.append("Puerta:").append(solicitante.getDireccion().getPuerta()).append(" ");
		}
		if(StringUtils.isNotBlank(solicitante.getDireccion().getEscalera())){
			stringDomicilio.append("Escalera:").append(solicitante.getDireccion().getEscalera()).append(" ");
		}
		if(StringUtils.isNotBlank(solicitante.getDireccion().getPortal())){
			stringDomicilio.append("Portal:").append(solicitante.getDireccion().getPortal()).append(" ");
		}

		domicilio.setDomicilio(stringDomicilio.toString());

		solicitanteXml.setDomicilioSocial(domicilio);


		//REPRESENTANTE SOLO PUEDE TENER 0 o 1
		if(solicitante.getRepresentantes()!=null && !solicitante.getRepresentantes().isEmpty()){
			org.gestoresmadrid.oegam2comun.registradores.rm.rbm.DatosNotarioType datosNotario = new org.gestoresmadrid.oegam2comun.registradores.rm.rbm.DatosNotarioType();
			org.gestoresmadrid.oegam2comun.registradores.rm.rbm.DatosPoderType datosPoder = new org.gestoresmadrid.oegam2comun.registradores.rm.rbm.DatosPoderType();
			IntervinienteRegistroDto representante = solicitante.getRepresentantes().get(0);
			org.gestoresmadrid.oegam2comun.registradores.rm.rbm.RepresentanteType representanteXml = new org.gestoresmadrid.oegam2comun.registradores.rm.rbm.RepresentanteType();
			representanteXml.setCodigoIdentificacionFiscal(representante.getNif());

			if(representante.getTipoPersona().equals(TipoPersonaRegistro.Fisica.getValorXML())
					|| representante.getTipoPersona().equals(TipoPersonaRegistro.Extranjero.getValorXML())){
				representanteXml.setNombre(representante.getPersona().getNombre());
				representanteXml.setPrimerApellido(representante.getPersona().getApellido1RazonSocial());
				representanteXml.setSegundoApellido(representante.getPersona().getApellido2());
			}else{
				representanteXml.setNombre(representante.getPersona().getApellido1RazonSocial());
				representanteXml.setPrimerApellido(representante.getPersona().getApellido1RazonSocial());
				representanteXml.setSegundoApellido(representante.getPersona().getApellido1RazonSocial());
			}

			if(representante.getNotario()!=null){
				SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
				if(null != representante.getNotario().getFecOtorgamiento()) 
					datosNotario.setFechaOtorgamiento(sdf.format(representante.getNotario().getFecOtorgamiento()));
				datosNotario.setNumeroProtocolo(representante.getNotario().getNumProtocolo());

				String notario = (StringUtils.isNotBlank(representante.getNotario().getNif())?representante.getNotario().getNif():"")
						+ " " + 
						(StringUtils.isNotBlank(representante.getNotario().getNombre())?representante.getNotario().getNombre():"")
						+ " " + 
						(StringUtils.isNotBlank(representante.getNotario().getApellido1())?representante.getNotario().getApellido1():"")
						+ " " + 
						(StringUtils.isNotBlank(representante.getNotario().getApellido2())?representante.getNotario().getApellido2():"");

				datosNotario.setNotario(notario);
				datosNotario.setPlaza(representante.getNotario().getPlazaNotario());
				datosPoder.setDatosNotario(datosNotario);
			}

			if(representante.getDatRegMercantil()!=null){
				org.gestoresmadrid.oegam2comun.registradores.rm.rbm.DatosRegistralesMercantilType datosRegistrales = new org.gestoresmadrid.oegam2comun.registradores.rm.rbm.DatosRegistralesMercantilType();
				datosRegistrales.setCodigoRMercantil(representante.getDatRegMercantil().getCodRegistroMercantil());
				datosRegistrales.setTomoRMercantil(representante.getDatRegMercantil().getTomo().toString());
				datosRegistrales.setFolioRMercantil(representante.getDatRegMercantil().getFolio().toString());
				datosRegistrales.setInscripcionRMercantil(representante.getDatRegMercantil().getNumInscripcion());
				datosPoder.setDatosRegistralesMercantil(datosRegistrales);

			}
			representanteXml.setDatosPoder(datosPoder);
			solicitanteXml.setRepresentante(representanteXml);
		}	

		return solicitanteXml;
	}


	private SolicitanteType convertirDesistimientoSolicitante(IntervinienteRegistroDto solicitante) {
		org.gestoresmadrid.oegam2comun.registradores.rm.rbm.SolicitanteType solicitanteXml = new org.gestoresmadrid.oegam2comun.registradores.rm.rbm.SolicitanteType();
		org.gestoresmadrid.oegam2comun.registradores.rm.rbm.IdenSujetoType idenSujeto = new org.gestoresmadrid.oegam2comun.registradores.rm.rbm.IdenSujetoType();

		idenSujeto.setNifCif(solicitante.getNif());
		if(solicitante.getTipoPersona().equals(TipoPersonaRegistro.Fisica.getValorXML())
				|| solicitante.getTipoPersona().equals(TipoPersonaRegistro.Extranjero.getValorXML())){
			idenSujeto.setNombre(solicitante.getPersona().getNombre());
			idenSujeto.setApellidos(solicitante.getPersona().getApellidos());
		}else{
			idenSujeto.setNombre(solicitante.getPersona().getApellido1RazonSocial());
		}
		idenSujeto.setTipoPersona(TipoPersonaRegistro.convertirValorXml(solicitante.getPersona().getTipoPersona()));
		solicitanteXml.setIdenSujeto(idenSujeto);
		solicitanteXml.setCorreoElectronico(solicitante.getPersona().getCorreoElectronico());

		org.gestoresmadrid.oegam2comun.registradores.rm.rbm.DomicilioINEType domicilio = new org.gestoresmadrid.oegam2comun.registradores.rm.rbm.DomicilioINEType();
		if(StringUtils.isNotBlank(solicitante.getDireccion().getBloque())){
			domicilio.setBloque(solicitante.getDireccion().getBloque());
		}else{
			domicilio.setBloque("");
		}

		domicilio.setCodigoPostal(solicitante.getDireccion().getCodPostal());
		domicilio.setCodMunicipio(solicitante.getDireccion().getIdMunicipio());
		domicilio.setCodProvincia(solicitante.getDireccion().getIdProvincia());
		domicilio.setCodTipoVia(solicitante.getDireccion().getIdTipoVia());

		if(StringUtils.isNotBlank(solicitante.getDireccion().getPueblo())){
			domicilio.setEntidadLocalMenor(solicitante.getDireccion().getPueblo());
		}else{
			domicilio.setEntidadLocalMenor("");
		}

		if(StringUtils.isNotBlank(solicitante.getDireccion().getEscalera())){
			domicilio.setEscalera(solicitante.getDireccion().getEscalera());
		}else{
			domicilio.setEscalera("");
		}

		if(StringUtils.isNotBlank(solicitante.getDireccion().getRegionExtranjera())){
			domicilio.setEstadoRegionProvinciaExtranjera(solicitante.getDireccion().getRegionExtranjera());
		}else{
			domicilio.setEstadoRegionProvinciaExtranjera("");
		}
		domicilio.setLugarUbicacion(solicitante.getDireccion().getLugarUbicacion());
		domicilio.setNombreVia(solicitante.getDireccion().getNombreVia());
		domicilio.setNumero(solicitante.getDireccion().getNumero());
		if(StringUtils.isNotBlank(solicitante.getDireccion().getNumeroBis())){
			domicilio.setNumeroBis(solicitante.getDireccion().getNumeroBis());
		}else{
			domicilio.setNumeroBis("");
		}
		domicilio.setPais(solicitante.getDireccion().getPais());
		if(StringUtils.isNotBlank(solicitante.getDireccion().getPlanta())){
			domicilio.setPlanta(solicitante.getDireccion().getPlanta());
		}else{
			domicilio.setPlanta("");
		}
		if(StringUtils.isNotBlank(solicitante.getDireccion().getPortal())){
			domicilio.setPortal(solicitante.getDireccion().getPortal());
		}else{
			domicilio.setPortal("");
		}
		if(StringUtils.isNotBlank(solicitante.getDireccion().getPuerta())){
			domicilio.setPuerta(solicitante.getDireccion().getPuerta());
		}else{
			domicilio.setPuerta("");
		}
		if(solicitante.getDireccion().getKm()!=null){
			domicilio.setPuntoKilometrico(solicitante.getDireccion().getKm().toString());
		}else{
			domicilio.setPuntoKilometrico("");	
		}
		solicitanteXml.setDomicilio(domicilio);

		if(solicitante.getDatRegMercantil()!=null){
			org.gestoresmadrid.oegam2comun.registradores.rm.rbm.DatosRegistralesMercantil2Type datosRegistrales = new org.gestoresmadrid.oegam2comun.registradores.rm.rbm.DatosRegistralesMercantil2Type();
			if(StringUtils.isNotBlank(solicitante.getDatRegMercantil().getCodRegistroMercantil()))
				datosRegistrales.setCodigoRMercantil(solicitante.getDatRegMercantil().getCodRegistroMercantil());
			if(solicitante.getDatRegMercantil().getFolio()!=null)
				datosRegistrales.setFolioRMercantil(solicitante.getDatRegMercantil().getFolio().toString());
			datosRegistrales.setHojaRMercantil(solicitante.getDatRegMercantil().getHoja());
			datosRegistrales.setInscripcionRMercantil(solicitante.getDatRegMercantil().getNumInscripcion());
			if(solicitante.getDatRegMercantil().getLibro()!=null)
				datosRegistrales.setLibroRMercantil(solicitante.getDatRegMercantil().getLibro().toString());
			if(solicitante.getDatRegMercantil().getTomo()!=null)
				datosRegistrales.setTomoRMercantil(solicitante.getDatRegMercantil().getTomo().toString());
			solicitanteXml.setDatosRegistralesMercantil(datosRegistrales);
		}

		//REPRESENTANTE
		if(solicitante.getRepresentantes()!=null && !solicitante.getRepresentantes().isEmpty()){
			for(IntervinienteRegistroDto representante : solicitante.getRepresentantes()){
				org.gestoresmadrid.oegam2comun.registradores.rm.rbm.Representante2Type representanteXml = new org.gestoresmadrid.oegam2comun.registradores.rm.rbm.Representante2Type();

				representanteXml.setCargo(representante.getCargo());
				if(representante.getNotario()!=null){
					SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

					org.gestoresmadrid.oegam2comun.registradores.rm.rbm.DatosNotario2Type datosNotario = new org.gestoresmadrid.oegam2comun.registradores.rm.rbm.DatosNotario2Type();
					if(representante.getNotario().getFecOtorgamiento()!=null)
						datosNotario.setFechaOtorgamiento(sdf.format(representante.getNotario().getFecOtorgamiento()));
					datosNotario.setNumeroProtocolo(representante.getNotario().getNumProtocolo());
					datosNotario.setCodProvincia(representante.getNotario().getCodProvincia());
					datosNotario.setCodMunicipio(representante.getNotario().getCodMunicipio());
					org.gestoresmadrid.oegam2comun.registradores.rm.rbm.IdenSujetoType idenSujetoNotario = new org.gestoresmadrid.oegam2comun.registradores.rm.rbm.IdenSujetoType();

					idenSujetoNotario.setNifCif(representante.getNotario().getNif());
					if(representante.getNotario().getTipoPersona().equals(TipoPersonaRegistro.Fisica.getValorXML())
							|| representante.getNotario().getTipoPersona().equals(TipoPersonaRegistro.Extranjero.getValorXML())){
						idenSujetoNotario.setNombre(representante.getNotario().getNombre());
						idenSujetoNotario.setApellidos(representante.getNotario().getApellido1() +" "+representante.getNotario().getApellido2());
					}else{
						idenSujetoNotario.setNombre(representante.getNotario().getApellido1());
					}
					idenSujetoNotario.setTipoPersona(representante.getNotario().getTipoPersona());
					datosNotario.setIdenSujeto(idenSujetoNotario);
					representanteXml.setDatosNotario(datosNotario);
				}

				org.gestoresmadrid.oegam2comun.registradores.rm.rbm.DomicilioINEType domicilioRepresentante = new org.gestoresmadrid.oegam2comun.registradores.rm.rbm.DomicilioINEType();
				if(representante.getDireccion().getBloque()!=null)
					domicilioRepresentante.setBloque(representante.getDireccion().getBloque());
				else
					domicilioRepresentante.setBloque("");
				domicilioRepresentante.setCodigoPostal(representante.getDireccion().getCodPostal());
				domicilioRepresentante.setCodMunicipio(representante.getDireccion().getIdMunicipio());
				domicilioRepresentante.setCodProvincia(representante.getDireccion().getIdProvincia());
				domicilioRepresentante.setCodTipoVia(representante.getDireccion().getIdTipoVia());
				if(representante.getDireccion().getPueblo()!=null)
					domicilioRepresentante.setEntidadLocalMenor(representante.getDireccion().getPueblo());
				else
					domicilioRepresentante.setEntidadLocalMenor("");
				if(representante.getDireccion().getEscalera()!=null)
					domicilioRepresentante.setEscalera(representante.getDireccion().getEscalera());
				else
					domicilioRepresentante.setEscalera("");
				if(representante.getDireccion().getRegionExtranjera()!=null)
					domicilioRepresentante.setEstadoRegionProvinciaExtranjera(representante.getDireccion().getRegionExtranjera());
				else
					domicilioRepresentante.setEstadoRegionProvinciaExtranjera("");
				domicilioRepresentante.setLugarUbicacion(representante.getDireccion().getLugarUbicacion());
				domicilioRepresentante.setNombreVia(representante.getDireccion().getNombreVia());
				domicilioRepresentante.setNumero(representante.getDireccion().getNumero());
				if(representante.getDireccion().getNumeroBis()!=null)
					domicilioRepresentante.setNumeroBis(representante.getDireccion().getNumeroBis());
				else
					domicilioRepresentante.setNumeroBis("");
				domicilioRepresentante.setPais(representante.getDireccion().getPais());
				if(representante.getDireccion().getPlanta()!=null)
					domicilioRepresentante.setPlanta(representante.getDireccion().getPlanta());
				else
					domicilioRepresentante.setPlanta("");
				if(representante.getDireccion().getPortal()!=null)
					domicilioRepresentante.setPortal(representante.getDireccion().getPortal());
				else
					domicilioRepresentante.setPortal("");
				if(representante.getDireccion().getPuerta()!=null)
					domicilioRepresentante.setPuerta(representante.getDireccion().getPuerta());
				else
					domicilioRepresentante.setPuerta("");
				if(representante.getDireccion().getKm()!=null)
					domicilioRepresentante.setPuntoKilometrico(representante.getDireccion().getKm().toString());
				else
					domicilioRepresentante.setPuntoKilometrico("");					
				representanteXml.setDomicilio(domicilioRepresentante);

				org.gestoresmadrid.oegam2comun.registradores.rm.rbm.IdenSujetoType idenSujetoRepresentante = new org.gestoresmadrid.oegam2comun.registradores.rm.rbm.IdenSujetoType();

				idenSujetoRepresentante.setNifCif(representante.getNif());
				if(representante.getTipoPersona().equals(TipoPersonaRegistro.Fisica.getValorXML())
						|| representante.getTipoPersona().equals(TipoPersonaRegistro.Extranjero.getValorXML())){
					idenSujetoRepresentante.setNombre(representante.getPersona().getNombre());
					idenSujetoRepresentante.setApellidos(representante.getPersona().getApellidos());
				}else{
					idenSujetoRepresentante.setNombre(representante.getPersona().getApellido1RazonSocial());
				}
				idenSujetoRepresentante.setTipoPersona(TipoPersonaRegistro.convertirValorXml(representante.getPersona().getTipoPersona()));			
				representanteXml.setIdenSujeto(idenSujetoRepresentante);					
				solicitanteXml.getRepresentante().add(representanteXml);
			}
		}
		return solicitanteXml;	
	}

	private Object convertirLeasingInterviniente(List<IntervinienteRegistroDto> intervinientes) {
		List<org.gestoresmadrid.oegam2comun.registradores.rm.rbm.IntervinienteType> intervinientesXml = new ArrayList<org.gestoresmadrid.oegam2comun.registradores.rm.rbm.IntervinienteType>();
		for (IntervinienteRegistroDto interviniente : intervinientes){
			org.gestoresmadrid.oegam2comun.registradores.rm.rbm.IntervinienteType intervinienteXml = new org.gestoresmadrid.oegam2comun.registradores.rm.rbm.IntervinienteType();
			intervinienteXml.setCorreoElectronico(interviniente.getPersona().getCorreoElectronico());

			if(interviniente.getDatRegMercantil()!=null){
				org.gestoresmadrid.oegam2comun.registradores.rm.rbm.DatosRegistralesMercantil2Type datosRegistrales = new org.gestoresmadrid.oegam2comun.registradores.rm.rbm.DatosRegistralesMercantil2Type();
				if(interviniente.getDatRegMercantil().getCodRegistroMercantil()!=null)
					datosRegistrales.setCodigoRMercantil(interviniente.getDatRegMercantil().getCodRegistroMercantil());
				if(interviniente.getDatRegMercantil().getFolio()!=null)
					datosRegistrales.setFolioRMercantil(interviniente.getDatRegMercantil().getFolio().toString());
				datosRegistrales.setHojaRMercantil(interviniente.getDatRegMercantil().getHoja());
				datosRegistrales.setInscripcionRMercantil(interviniente.getDatRegMercantil().getNumInscripcion());
				if(interviniente.getDatRegMercantil().getLibro()!=null)
					datosRegistrales.setLibroRMercantil(interviniente.getDatRegMercantil().getLibro().toString());
				if(interviniente.getDatRegMercantil().getTomo()!=null)
					datosRegistrales.setTomoRMercantil(interviniente.getDatRegMercantil().getTomo().toString());
				intervinienteXml.setDatosRegistralesMercantil(datosRegistrales);
			}

			org.gestoresmadrid.oegam2comun.registradores.rm.rbm.DomicilioINEType domicilio = new org.gestoresmadrid.oegam2comun.registradores.rm.rbm.DomicilioINEType();
			if(interviniente.getDireccion().getBloque()!=null)
				domicilio.setBloque(interviniente.getDireccion().getBloque());
			else
				domicilio.setBloque("");
			domicilio.setCodigoPostal(interviniente.getDireccion().getCodPostal());
			domicilio.setCodMunicipio(interviniente.getDireccion().getIdMunicipio());
			domicilio.setCodProvincia(interviniente.getDireccion().getIdProvincia());
			domicilio.setCodTipoVia(interviniente.getDireccion().getIdTipoVia());
			if(interviniente.getDireccion().getPueblo()!=null)
				domicilio.setEntidadLocalMenor(interviniente.getDireccion().getPueblo());
			else
				domicilio.setEntidadLocalMenor("");
			if(interviniente.getDireccion().getEscalera()!=null)
				domicilio.setEscalera(interviniente.getDireccion().getEscalera());
			else
				domicilio.setEscalera("");
			if(interviniente.getDireccion().getRegionExtranjera()!=null)
				domicilio.setEstadoRegionProvinciaExtranjera(interviniente.getDireccion().getRegionExtranjera());
			else
				domicilio.setEstadoRegionProvinciaExtranjera("");
			domicilio.setLugarUbicacion(interviniente.getDireccion().getLugarUbicacion());
			domicilio.setNombreVia(interviniente.getDireccion().getNombreVia());
			domicilio.setNumero(interviniente.getDireccion().getNumero());
			if(interviniente.getDireccion().getNumeroBis()!=null)
				domicilio.setNumeroBis(interviniente.getDireccion().getNumeroBis());
			else
				domicilio.setNumeroBis("");
			domicilio.setPais(interviniente.getDireccion().getPais());
			if(interviniente.getDireccion().getPlanta()!=null)
				domicilio.setPlanta(interviniente.getDireccion().getPlanta());
			else
				domicilio.setPlanta("");
			if(interviniente.getDireccion().getPortal()!=null)
				domicilio.setPortal(interviniente.getDireccion().getPortal());
			else
				domicilio.setPortal("");
			if(interviniente.getDireccion().getPuerta()!=null)
				domicilio.setPuerta(interviniente.getDireccion().getPuerta());
			else
				domicilio.setPuerta("");
			if(interviniente.getDireccion().getKm()!=null)
				domicilio.setPuntoKilometrico(interviniente.getDireccion().getKm().toString());
			else
				domicilio.setPuntoKilometrico("");		
			intervinienteXml.setDomicilio(domicilio);

			org.gestoresmadrid.oegam2comun.registradores.rm.rbm.IdenSujetoType idenSujeto = new org.gestoresmadrid.oegam2comun.registradores.rm.rbm.IdenSujetoType();

			idenSujeto.setNifCif(interviniente.getNif());
			if(interviniente.getTipoPersona().equals(TipoPersonaRegistro.Fisica.getValorXML())
					|| interviniente.getTipoPersona().equals(TipoPersonaRegistro.Extranjero.getValorXML())){
				idenSujeto.setNombre(interviniente.getPersona().getNombre());
				idenSujeto.setApellidos(interviniente.getPersona().getApellidos());
			}else{
				idenSujeto.setNombre(interviniente.getPersona().getApellido1RazonSocial());
			}
			idenSujeto.setTipoPersona(TipoPersonaRegistro.convertirValorXml(interviniente.getPersona().getTipoPersona()));			
			intervinienteXml.setIdenSujeto(idenSujeto);

			if(interviniente.getTelefonos()!=null && !interviniente.getTelefonos().isEmpty()){
				org.gestoresmadrid.oegam2comun.registradores.rm.rbm.IntervinienteType.Telefonos telefonos = new org.gestoresmadrid.oegam2comun.registradores.rm.rbm.IntervinienteType.Telefonos();
				for(TelefonoDto telefonoDto : interviniente.getTelefonos()){
					org.gestoresmadrid.oegam2comun.registradores.rm.rbm.TelefonoType telefono = new org.gestoresmadrid.oegam2comun.registradores.rm.rbm.TelefonoType();
					telefono.setNumero(telefonoDto.getNumero());
					telefono.setTipo(telefonoDto.getTipo());
					telefonos.getTelefono().add(telefono);
				}
				intervinienteXml.setTelefonos(telefonos);
			}			
			//REPRESENTANTES
			if(interviniente.getRepresentantes()!=null && !interviniente.getRepresentantes().isEmpty()){
				for(IntervinienteRegistroDto representante : interviniente.getRepresentantes()){
					org.gestoresmadrid.oegam2comun.registradores.rm.rbm.Representante2Type representanteXml = new org.gestoresmadrid.oegam2comun.registradores.rm.rbm.Representante2Type();

					representanteXml.setCargo(representante.getCargo());
					if(representante.getNotario()!=null){

						SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

						org.gestoresmadrid.oegam2comun.registradores.rm.rbm.DatosNotario2Type datosNotario = new org.gestoresmadrid.oegam2comun.registradores.rm.rbm.DatosNotario2Type();
						if(representante.getNotario().getFecOtorgamiento()!=null)
							datosNotario.setFechaOtorgamiento(sdf.format(representante.getNotario().getFecOtorgamiento()));
						datosNotario.setNumeroProtocolo(representante.getNotario().getNumProtocolo());
						datosNotario.setCodProvincia(representante.getNotario().getCodProvincia());
						datosNotario.setCodMunicipio(representante.getNotario().getCodMunicipio());
						org.gestoresmadrid.oegam2comun.registradores.rm.rbm.IdenSujetoType idenSujetoNotario = new org.gestoresmadrid.oegam2comun.registradores.rm.rbm.IdenSujetoType();

						idenSujetoNotario.setNifCif(representante.getNotario().getNif());
						if(representante.getNotario().getTipoPersona().equals(TipoPersonaRegistro.Fisica.getValorXML())
								|| representante.getNotario().getTipoPersona().equals(TipoPersonaRegistro.Extranjero.getValorXML())){
							idenSujetoNotario.setNombre(representante.getNotario().getNombre());
							idenSujetoNotario.setApellidos(representante.getNotario().getApellido1() +" "+representante.getNotario().getApellido2());
						}else{
							idenSujetoNotario.setNombre(representante.getNotario().getApellido1());
						}
						idenSujetoNotario.setTipoPersona(representante.getNotario().getTipoPersona());
						datosNotario.setIdenSujeto(idenSujetoNotario);
						representanteXml.setDatosNotario(datosNotario);
					}

					org.gestoresmadrid.oegam2comun.registradores.rm.rbm.DomicilioINEType domicilioRepresentante = new org.gestoresmadrid.oegam2comun.registradores.rm.rbm.DomicilioINEType();
					if(representante.getDireccion().getBloque()!=null)
						domicilioRepresentante.setBloque(representante.getDireccion().getBloque());
					else
						domicilioRepresentante.setBloque("");
					domicilioRepresentante.setCodigoPostal(representante.getDireccion().getCodPostal());
					domicilioRepresentante.setCodMunicipio(representante.getDireccion().getIdMunicipio());
					domicilioRepresentante.setCodProvincia(representante.getDireccion().getIdProvincia());
					domicilioRepresentante.setCodTipoVia(representante.getDireccion().getIdTipoVia());
					if(representante.getDireccion().getPueblo()!=null)
						domicilioRepresentante.setEntidadLocalMenor(representante.getDireccion().getPueblo());
					else
						domicilioRepresentante.setEntidadLocalMenor("");
					if(representante.getDireccion().getEscalera()!=null)
						domicilioRepresentante.setEscalera(representante.getDireccion().getEscalera());
					else
						domicilioRepresentante.setEscalera("");
					if(representante.getDireccion().getRegionExtranjera()!=null)
						domicilioRepresentante.setEstadoRegionProvinciaExtranjera(representante.getDireccion().getRegionExtranjera());
					else
						domicilioRepresentante.setEstadoRegionProvinciaExtranjera("");
					domicilioRepresentante.setLugarUbicacion(representante.getDireccion().getLugarUbicacion());
					domicilioRepresentante.setNombreVia(representante.getDireccion().getNombreVia());
					domicilioRepresentante.setNumero(representante.getDireccion().getNumero());
					if(representante.getDireccion().getNumeroBis()!=null)
						domicilioRepresentante.setNumeroBis(representante.getDireccion().getNumeroBis());
					else
						domicilioRepresentante.setNumeroBis("");
					domicilioRepresentante.setPais(representante.getDireccion().getPais());
					if(representante.getDireccion().getPlanta()!=null)
						domicilioRepresentante.setPlanta(representante.getDireccion().getPlanta());
					else
						domicilioRepresentante.setPlanta("");
					if(representante.getDireccion().getPortal()!=null)
						domicilioRepresentante.setPortal(representante.getDireccion().getPortal());
					else
						domicilioRepresentante.setPortal("");
					if(representante.getDireccion().getPuerta()!=null)
						domicilioRepresentante.setPuerta(representante.getDireccion().getPuerta());
					else
						domicilioRepresentante.setPuerta("");
					if(representante.getDireccion().getKm()!=null)
						domicilioRepresentante.setPuntoKilometrico(representante.getDireccion().getKm().toString());
					else
						domicilioRepresentante.setPuntoKilometrico("");					
					representanteXml.setDomicilio(domicilioRepresentante);

					org.gestoresmadrid.oegam2comun.registradores.rm.rbm.IdenSujetoType idenSujetoRepresentante = new org.gestoresmadrid.oegam2comun.registradores.rm.rbm.IdenSujetoType();

					idenSujetoRepresentante.setNifCif(representante.getNif());
					if(representante.getTipoPersona().equals(TipoPersonaRegistro.Fisica.getValorXML())
							|| representante.getTipoPersona().equals(TipoPersonaRegistro.Extranjero.getValorXML())){
						idenSujetoRepresentante.setNombre(representante.getPersona().getNombre());
						idenSujetoRepresentante.setApellidos(representante.getPersona().getApellidos());
					}else{
						idenSujetoRepresentante.setNombre(representante.getPersona().getApellido1RazonSocial());
					}
					idenSujetoRepresentante.setTipoPersona(TipoPersonaRegistro.convertirValorXml(representante.getPersona().getTipoPersona()));			
					representanteXml.setIdenSujeto(idenSujetoRepresentante);					
					intervinienteXml.getRepresentante().add(representanteXml);
				}
			}
			intervinientesXml.add(intervinienteXml);		
		}
		return intervinientesXml;
	}

	private Object convertirFinancingInterviniente(List<IntervinienteRegistroDto> intervinientes) {
		List<org.gestoresmadrid.oegam2comun.registradores.rm.rbm.IntervinienteType> intervinientesXml = new ArrayList<org.gestoresmadrid.oegam2comun.registradores.rm.rbm.IntervinienteType>();
		for (IntervinienteRegistroDto interviniente : intervinientes){
			org.gestoresmadrid.oegam2comun.registradores.rm.rbm.IntervinienteType intervinienteXml = new org.gestoresmadrid.oegam2comun.registradores.rm.rbm.IntervinienteType();
			intervinienteXml.setCorreoElectronico(interviniente.getPersona().getCorreoElectronico());

			if(interviniente.getDatRegMercantil()!=null){
				org.gestoresmadrid.oegam2comun.registradores.rm.rbm.DatosRegistralesMercantil2Type datosRegistrales = new org.gestoresmadrid.oegam2comun.registradores.rm.rbm.DatosRegistralesMercantil2Type();
				if(interviniente.getDatRegMercantil().getCodRegistroMercantil()!=null)
					datosRegistrales.setCodigoRMercantil(interviniente.getDatRegMercantil().getCodRegistroMercantil());
				if(interviniente.getDatRegMercantil().getFolio()!=null)
					datosRegistrales.setFolioRMercantil(interviniente.getDatRegMercantil().getFolio().toString());
				datosRegistrales.setHojaRMercantil(interviniente.getDatRegMercantil().getHoja());
				datosRegistrales.setInscripcionRMercantil(interviniente.getDatRegMercantil().getNumInscripcion());
				if(interviniente.getDatRegMercantil().getLibro()!=null)
					datosRegistrales.setLibroRMercantil(interviniente.getDatRegMercantil().getLibro().toString());
				if(interviniente.getDatRegMercantil().getTomo()!=null)
					datosRegistrales.setTomoRMercantil(interviniente.getDatRegMercantil().getTomo().toString());
				intervinienteXml.setDatosRegistralesMercantil(datosRegistrales);
			}

			org.gestoresmadrid.oegam2comun.registradores.rm.rbm.DomicilioINEType domicilio = new org.gestoresmadrid.oegam2comun.registradores.rm.rbm.DomicilioINEType();
			if(interviniente.getDireccion().getBloque()!=null)
				domicilio.setBloque(interviniente.getDireccion().getBloque());
			else
				domicilio.setBloque("");
			domicilio.setCodigoPostal(interviniente.getDireccion().getCodPostal());
			domicilio.setCodMunicipio(interviniente.getDireccion().getIdMunicipio());
			domicilio.setCodProvincia(interviniente.getDireccion().getIdProvincia());
			domicilio.setCodTipoVia(interviniente.getDireccion().getIdTipoVia());
			if(interviniente.getDireccion().getPueblo()!=null)
				domicilio.setEntidadLocalMenor(interviniente.getDireccion().getPueblo());
			else
				domicilio.setEntidadLocalMenor("");
			if(interviniente.getDireccion().getEscalera()!=null)
				domicilio.setEscalera(interviniente.getDireccion().getEscalera());
			else
				domicilio.setEscalera("");
			if(interviniente.getDireccion().getRegionExtranjera()!=null)
				domicilio.setEstadoRegionProvinciaExtranjera(interviniente.getDireccion().getRegionExtranjera());
			else
				domicilio.setEstadoRegionProvinciaExtranjera("");
			domicilio.setLugarUbicacion(interviniente.getDireccion().getLugarUbicacion());
			domicilio.setNombreVia(interviniente.getDireccion().getNombreVia());
			domicilio.setNumero(interviniente.getDireccion().getNumero());
			if(interviniente.getDireccion().getNumeroBis()!=null)
				domicilio.setNumeroBis(interviniente.getDireccion().getNumeroBis());
			else
				domicilio.setNumeroBis("");
			domicilio.setPais(interviniente.getDireccion().getPais());
			if(interviniente.getDireccion().getPlanta()!=null)
				domicilio.setPlanta(interviniente.getDireccion().getPlanta());
			else
				domicilio.setPlanta("");
			if(interviniente.getDireccion().getPortal()!=null)
				domicilio.setPortal(interviniente.getDireccion().getPortal());
			else
				domicilio.setPortal("");
			if(interviniente.getDireccion().getPuerta()!=null)
				domicilio.setPuerta(interviniente.getDireccion().getPuerta());
			else
				domicilio.setPuerta("");
			if(interviniente.getDireccion().getKm()!=null)
				domicilio.setPuntoKilometrico(interviniente.getDireccion().getKm().toString());
			else
				domicilio.setPuntoKilometrico("");		
			intervinienteXml.setDomicilio(domicilio);

			org.gestoresmadrid.oegam2comun.registradores.rm.rbm.IdenSujetoType idenSujeto = new org.gestoresmadrid.oegam2comun.registradores.rm.rbm.IdenSujetoType();

			idenSujeto.setNifCif(interviniente.getNif());
			if(interviniente.getTipoPersona().equals(TipoPersonaRegistro.Fisica.getValorXML())
					|| interviniente.getTipoPersona().equals(TipoPersonaRegistro.Extranjero.getValorXML())){
				idenSujeto.setNombre(interviniente.getPersona().getNombre());
				idenSujeto.setApellidos(interviniente.getPersona().getApellidos());
			}else{
				idenSujeto.setNombre(interviniente.getPersona().getApellido1RazonSocial());
			}
			idenSujeto.setTipoPersona(TipoPersonaRegistro.convertirValorXml(interviniente.getPersona().getTipoPersona()));			
			intervinienteXml.setIdenSujeto(idenSujeto);

			if(interviniente.getTelefonos()!=null && !interviniente.getTelefonos().isEmpty()){
				org.gestoresmadrid.oegam2comun.registradores.rm.rbm.IntervinienteType.Telefonos telefonos = new org.gestoresmadrid.oegam2comun.registradores.rm.rbm.IntervinienteType.Telefonos();
				for(TelefonoDto telefonoDto : interviniente.getTelefonos()){
					org.gestoresmadrid.oegam2comun.registradores.rm.rbm.TelefonoType telefono = new org.gestoresmadrid.oegam2comun.registradores.rm.rbm.TelefonoType();
					telefono.setNumero(telefonoDto.getNumero());
					telefono.setTipo(telefonoDto.getTipo());
					telefonos.getTelefono().add(telefono);
				}
				intervinienteXml.setTelefonos(telefonos);
			}			
			//REPRESENTANTES
			if(interviniente.getRepresentantes()!=null && !interviniente.getRepresentantes().isEmpty()){
				for(IntervinienteRegistroDto representante : interviniente.getRepresentantes()){
					org.gestoresmadrid.oegam2comun.registradores.rm.rbm.Representante2Type representanteXml = new org.gestoresmadrid.oegam2comun.registradores.rm.rbm.Representante2Type();

					representanteXml.setCargo(representante.getCargo());
					if(representante.getNotario()!=null){
						SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

						org.gestoresmadrid.oegam2comun.registradores.rm.rbm.DatosNotario2Type datosNotario = new org.gestoresmadrid.oegam2comun.registradores.rm.rbm.DatosNotario2Type();
						if(representante.getNotario().getFecOtorgamiento()!=null)
							datosNotario.setFechaOtorgamiento(sdf.format(representante.getNotario().getFecOtorgamiento()));
						datosNotario.setNumeroProtocolo(representante.getNotario().getNumProtocolo());
						datosNotario.setCodProvincia(representante.getNotario().getCodProvincia());
						datosNotario.setCodMunicipio(representante.getNotario().getCodMunicipio());
						org.gestoresmadrid.oegam2comun.registradores.rm.rbm.IdenSujetoType idenSujetoNotario = new org.gestoresmadrid.oegam2comun.registradores.rm.rbm.IdenSujetoType();

						idenSujetoNotario.setNifCif(representante.getNotario().getNif());
						if(representante.getNotario().getTipoPersona().equals(TipoPersonaRegistro.Fisica.getValorXML())
								|| representante.getNotario().getTipoPersona().equals(TipoPersonaRegistro.Extranjero.getValorXML())){
							idenSujetoNotario.setNombre(representante.getNotario().getNombre());
							idenSujetoNotario.setApellidos(representante.getNotario().getApellido1() +" "+representante.getNotario().getApellido2());
						}else{
							idenSujetoNotario.setNombre(representante.getNotario().getApellido1());
						}
						idenSujetoNotario.setTipoPersona(representante.getNotario().getTipoPersona());
						datosNotario.setIdenSujeto(idenSujetoNotario);
						representanteXml.setDatosNotario(datosNotario);
					}

					org.gestoresmadrid.oegam2comun.registradores.rm.rbm.DomicilioINEType domicilioRepresentante = new org.gestoresmadrid.oegam2comun.registradores.rm.rbm.DomicilioINEType();
					if(representante.getDireccion().getBloque()!=null)
						domicilioRepresentante.setBloque(representante.getDireccion().getBloque());
					else
						domicilioRepresentante.setBloque("");
					domicilioRepresentante.setCodigoPostal(representante.getDireccion().getCodPostal());
					domicilioRepresentante.setCodMunicipio(representante.getDireccion().getIdMunicipio());
					domicilioRepresentante.setCodProvincia(representante.getDireccion().getIdProvincia());
					domicilioRepresentante.setCodTipoVia(representante.getDireccion().getIdTipoVia());
					if(representante.getDireccion().getPueblo()!=null)
						domicilioRepresentante.setEntidadLocalMenor(representante.getDireccion().getPueblo());
					else
						domicilioRepresentante.setEntidadLocalMenor("");
					if(representante.getDireccion().getEscalera()!=null)
						domicilioRepresentante.setEscalera(representante.getDireccion().getEscalera());
					else
						domicilioRepresentante.setEscalera("");
					if(representante.getDireccion().getRegionExtranjera()!=null)
						domicilioRepresentante.setEstadoRegionProvinciaExtranjera(representante.getDireccion().getRegionExtranjera());
					else
						domicilioRepresentante.setEstadoRegionProvinciaExtranjera("");
					domicilioRepresentante.setLugarUbicacion(representante.getDireccion().getLugarUbicacion());
					domicilioRepresentante.setNombreVia(representante.getDireccion().getNombreVia());
					domicilioRepresentante.setNumero(representante.getDireccion().getNumero());
					if(representante.getDireccion().getNumeroBis()!=null)
						domicilioRepresentante.setNumeroBis(representante.getDireccion().getNumeroBis());
					else
						domicilioRepresentante.setNumeroBis("");
					domicilioRepresentante.setPais(representante.getDireccion().getPais());
					if(representante.getDireccion().getPlanta()!=null)
						domicilioRepresentante.setPlanta(representante.getDireccion().getPlanta());
					else
						domicilioRepresentante.setPlanta("");
					if(representante.getDireccion().getPortal()!=null)
						domicilioRepresentante.setPortal(representante.getDireccion().getPortal());
					else
						domicilioRepresentante.setPortal("");
					if(representante.getDireccion().getPuerta()!=null)
						domicilioRepresentante.setPuerta(representante.getDireccion().getPuerta());
					else
						domicilioRepresentante.setPuerta("");
					if(representante.getDireccion().getKm()!=null)
						domicilioRepresentante.setPuntoKilometrico(representante.getDireccion().getKm().toString());
					else
						domicilioRepresentante.setPuntoKilometrico("");					
					representanteXml.setDomicilio(domicilioRepresentante);

					org.gestoresmadrid.oegam2comun.registradores.rm.rbm.IdenSujetoType idenSujetoRepresentante = new org.gestoresmadrid.oegam2comun.registradores.rm.rbm.IdenSujetoType();

					idenSujetoRepresentante.setNifCif(representante.getNif());
					if(representante.getTipoPersona().equals(TipoPersonaRegistro.Fisica.getValorXML())
							|| representante.getTipoPersona().equals(TipoPersonaRegistro.Extranjero.getValorXML())){
						idenSujetoRepresentante.setNombre(representante.getPersona().getNombre());
						idenSujetoRepresentante.setApellidos(representante.getPersona().getApellidos());
					}else{
						idenSujetoRepresentante.setNombre(representante.getPersona().getApellido1RazonSocial());
					}
					idenSujetoRepresentante.setTipoPersona(TipoPersonaRegistro.convertirValorXml(representante.getPersona().getTipoPersona()));			
					representanteXml.setIdenSujeto(idenSujetoRepresentante);					
					intervinienteXml.getRepresentante().add(representanteXml);
				}

			}
			intervinientesXml.add(intervinienteXml);		
		}
		return intervinientesXml;
	}

	private Object convertirRentingInterviniente(List<IntervinienteRegistroDto> intervinientes) {
		List<IntervinienteType> intervinientesXml = new ArrayList<IntervinienteType>();
		for (IntervinienteRegistroDto interviniente : intervinientes){
			IntervinienteType intervinienteXml = new IntervinienteType();
			intervinienteXml.setCorreoElectronico(interviniente.getPersona().getCorreoElectronico());

			if(interviniente.getDatRegMercantil()!=null){
				DatosRegistralesMercantil2Type datosRegistrales = new DatosRegistralesMercantil2Type();
				if(interviniente.getDatRegMercantil().getCodRegistroMercantil()!=null)
					datosRegistrales.setCodigoRMercantil(interviniente.getDatRegMercantil().getCodRegistroMercantil());
				if(interviniente.getDatRegMercantil().getFolio()!=null)
					datosRegistrales.setFolioRMercantil(interviniente.getDatRegMercantil().getFolio().toString());
				datosRegistrales.setHojaRMercantil(interviniente.getDatRegMercantil().getHoja());
				datosRegistrales.setInscripcionRMercantil(interviniente.getDatRegMercantil().getNumInscripcion());
				if(interviniente.getDatRegMercantil().getLibro()!=null)
					datosRegistrales.setLibroRMercantil(interviniente.getDatRegMercantil().getLibro().toString());
				if(interviniente.getDatRegMercantil().getTomo()!=null)
					datosRegistrales.setTomoRMercantil(interviniente.getDatRegMercantil().getTomo().toString());
				intervinienteXml.setDatosRegistralesMercantil(datosRegistrales);
			}

			DomicilioINEType domicilio = new DomicilioINEType();
			if(interviniente.getDireccion().getBloque()!=null)
				domicilio.setBloque(interviniente.getDireccion().getBloque());
			else
				domicilio.setBloque("");
			domicilio.setCodigoPostal(interviniente.getDireccion().getCodPostal());
			domicilio.setCodMunicipio(interviniente.getDireccion().getIdMunicipio());
			domicilio.setCodProvincia(interviniente.getDireccion().getIdProvincia());
			domicilio.setCodTipoVia(interviniente.getDireccion().getIdTipoVia());
			if(interviniente.getDireccion().getPueblo()!=null)
				domicilio.setEntidadLocalMenor(interviniente.getDireccion().getPueblo());
			else
				domicilio.setEntidadLocalMenor("");
			if(interviniente.getDireccion().getEscalera()!=null)
				domicilio.setEscalera(interviniente.getDireccion().getEscalera());
			else
				domicilio.setEscalera("");
			if(interviniente.getDireccion().getRegionExtranjera()!=null)
				domicilio.setEstadoRegionProvinciaExtranjera(interviniente.getDireccion().getRegionExtranjera());
			else
				domicilio.setEstadoRegionProvinciaExtranjera("");
			domicilio.setLugarUbicacion(interviniente.getDireccion().getLugarUbicacion());
			domicilio.setNombreVia(interviniente.getDireccion().getNombreVia());
			domicilio.setNumero(interviniente.getDireccion().getNumero());
			if(interviniente.getDireccion().getNumeroBis()!=null)
				domicilio.setNumeroBis(interviniente.getDireccion().getNumeroBis());
			else
				domicilio.setNumeroBis("");
			domicilio.setPais(interviniente.getDireccion().getPais());
			if(interviniente.getDireccion().getPlanta()!=null)
				domicilio.setPlanta(interviniente.getDireccion().getPlanta());
			else
				domicilio.setPlanta("");
			if(interviniente.getDireccion().getPortal()!=null)
				domicilio.setPortal(interviniente.getDireccion().getPortal());
			else
				domicilio.setPortal("");
			if(interviniente.getDireccion().getPuerta()!=null)
				domicilio.setPuerta(interviniente.getDireccion().getPuerta());
			else
				domicilio.setPuerta("");
			if(interviniente.getDireccion().getKm()!=null)
				domicilio.setPuntoKilometrico(interviniente.getDireccion().getKm().toString());
			else
				domicilio.setPuntoKilometrico("");		
			intervinienteXml.setDomicilio(domicilio);

			IdenSujetoType idenSujeto = new IdenSujetoType();

			idenSujeto.setNifCif(interviniente.getNif());
			if(interviniente.getTipoPersona().equals(TipoPersonaRegistro.Fisica.getValorXML())
					|| interviniente.getTipoPersona().equals(TipoPersonaRegistro.Extranjero.getValorXML())){
				idenSujeto.setNombre(interviniente.getPersona().getNombre());
				idenSujeto.setApellidos(interviniente.getPersona().getApellidos());
			}else{
				idenSujeto.setNombre(interviniente.getPersona().getApellido1RazonSocial());
			}
			idenSujeto.setTipoPersona(TipoPersonaRegistro.convertirValorXml(interviniente.getPersona().getTipoPersona()));			
			intervinienteXml.setIdenSujeto(idenSujeto);


			if(interviniente.getTelefonos()!=null && !interviniente.getTelefonos().isEmpty()){
				Telefonos telefonos = new Telefonos();
				for(TelefonoDto telefonoDto : interviniente.getTelefonos()){
					TelefonoType telefono = new TelefonoType();
					telefono.setNumero(telefonoDto.getNumero());
					telefono.setTipo(telefonoDto.getTipo());
					telefonos.getTelefono().add(telefono);
				}
				intervinienteXml.setTelefonos(telefonos);
			}			
			//REPRESENTANTES
			if(interviniente.getRepresentantes()!=null && !interviniente.getRepresentantes().isEmpty()){
				for(IntervinienteRegistroDto representante : interviniente.getRepresentantes()){
					Representante2Type representanteXml = new Representante2Type();


					representanteXml.setCargo(representante.getCargo());
					if(representante.getNotario()!=null){
						SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

						DatosNotario2Type datosNotario = new DatosNotario2Type();
						if(representante.getNotario().getFecOtorgamiento()!=null)
							datosNotario.setFechaOtorgamiento(sdf.format(representante.getNotario().getFecOtorgamiento()));
						datosNotario.setNumeroProtocolo(representante.getNotario().getNumProtocolo());
						datosNotario.setCodProvincia(representante.getNotario().getCodProvincia());
						datosNotario.setCodMunicipio(representante.getNotario().getCodMunicipio());
						IdenSujetoType idenSujetoNotario = new IdenSujetoType();

						idenSujetoNotario.setNifCif(representante.getNotario().getNif());
						if(representante.getNotario().getTipoPersona().equals(TipoPersonaRegistro.Fisica.getValorXML())
								|| representante.getNotario().getTipoPersona().equals(TipoPersonaRegistro.Extranjero.getValorXML())){
							idenSujetoNotario.setNombre(representante.getNotario().getNombre());
							idenSujetoNotario.setApellidos(representante.getNotario().getApellido1() +" "+representante.getNotario().getApellido2());
						}else{
							idenSujetoNotario.setNombre(representante.getNotario().getApellido1());
						}
						idenSujetoNotario.setTipoPersona(representante.getNotario().getTipoPersona());
						datosNotario.setIdenSujeto(idenSujetoNotario);
						representanteXml.setDatosNotario(datosNotario);
					}


					DomicilioINEType domicilioRepresentante = new DomicilioINEType();
					if(representante.getDireccion().getBloque()!=null)
						domicilioRepresentante.setBloque(representante.getDireccion().getBloque());
					else
						domicilioRepresentante.setBloque("");
					domicilioRepresentante.setCodigoPostal(representante.getDireccion().getCodPostal());
					domicilioRepresentante.setCodMunicipio(representante.getDireccion().getIdMunicipio());
					domicilioRepresentante.setCodProvincia(representante.getDireccion().getIdProvincia());
					domicilioRepresentante.setCodTipoVia(representante.getDireccion().getIdTipoVia());
					if(representante.getDireccion().getPueblo()!=null)
						domicilioRepresentante.setEntidadLocalMenor(representante.getDireccion().getPueblo());
					else
						domicilioRepresentante.setEntidadLocalMenor("");
					if(representante.getDireccion().getEscalera()!=null)
						domicilioRepresentante.setEscalera(representante.getDireccion().getEscalera());
					else
						domicilioRepresentante.setEscalera("");
					if(representante.getDireccion().getRegionExtranjera()!=null)
						domicilioRepresentante.setEstadoRegionProvinciaExtranjera(representante.getDireccion().getRegionExtranjera());
					else
						domicilioRepresentante.setEstadoRegionProvinciaExtranjera("");
					domicilioRepresentante.setLugarUbicacion(representante.getDireccion().getLugarUbicacion());
					domicilioRepresentante.setNombreVia(representante.getDireccion().getNombreVia());
					domicilioRepresentante.setNumero(representante.getDireccion().getNumero());
					if(representante.getDireccion().getNumeroBis()!=null)
						domicilioRepresentante.setNumeroBis(representante.getDireccion().getNumeroBis());
					else
						domicilioRepresentante.setNumeroBis("");
					domicilioRepresentante.setPais(representante.getDireccion().getPais());
					if(representante.getDireccion().getPlanta()!=null)
						domicilioRepresentante.setPlanta(representante.getDireccion().getPlanta());
					else
						domicilioRepresentante.setPlanta("");
					if(representante.getDireccion().getPortal()!=null)
						domicilioRepresentante.setPortal(representante.getDireccion().getPortal());
					else
						domicilioRepresentante.setPortal("");
					if(representante.getDireccion().getPuerta()!=null)
						domicilioRepresentante.setPuerta(representante.getDireccion().getPuerta());
					else
						domicilioRepresentante.setPuerta("");
					if(representante.getDireccion().getKm()!=null)
						domicilioRepresentante.setPuntoKilometrico(representante.getDireccion().getKm().toString());
					else
						domicilioRepresentante.setPuntoKilometrico("");					
					representanteXml.setDomicilio(domicilioRepresentante);

					IdenSujetoType idenSujetoRepresentante = new IdenSujetoType();

					idenSujetoRepresentante.setNifCif(representante.getNif());

					if(representante.getTipoPersona().equals(TipoPersonaRegistro.Fisica.getValorXML())
							|| representante.getTipoPersona().equals(TipoPersonaRegistro.Extranjero.getValorXML())){
						idenSujetoRepresentante.setNombre(representante.getPersona().getNombre());
						idenSujetoRepresentante.setApellidos(representante.getPersona().getApellidos());
					}else{
						idenSujetoRepresentante.setNombre(representante.getPersona().getApellido1RazonSocial());
					}
					idenSujetoRepresentante.setTipoPersona(TipoPersonaRegistro.convertirValorXml(representante.getPersona().getTipoPersona()));			
					representanteXml.setIdenSujeto(idenSujetoRepresentante);					
					intervinienteXml.getRepresentante().add(representanteXml);
				}

			}
			intervinientesXml.add(intervinienteXml);		
		}
		return intervinientesXml;
	}

	public ResultBean construirRbmXMLLeasing(TramiteRegRbmDto tramiteRegistro) throws Exception, OegamExcepcion {
		XmlCORPMEFactory xmlFactory = new XmlCORPMEFactory();
		JAXBContext context = JAXBContext.newInstance("org.gestoresmadrid.oegam2comun.registradores.rm.rbm");
		org.gestoresmadrid.oegam2comun.registradores.rm.rbm.ObjectFactory factory = new org.gestoresmadrid.oegam2comun.registradores.rm.rbm.ObjectFactory();
		RBMContratoLeasing corpmeRM = factory.createRBMContratoLeasing();

		org.gestoresmadrid.oegam2comun.registradores.rm.rbm.RBMContratoLeasing.Arrendadores arrendadores = factory.createRBMContratoLeasingArrendadores();
		if(tramiteRegistro.getArrendadores()!=null && !tramiteRegistro.getArrendadores().isEmpty()){
			arrendadores.getArrendador().addAll((List<org.gestoresmadrid.oegam2comun.registradores.rm.rbm.IntervinienteType>) conversorInterviniente(tramiteRegistro.getArrendadores(), arrendadores));
			corpmeRM.setArrendadores(arrendadores);
		}

		org.gestoresmadrid.oegam2comun.registradores.rm.rbm.RBMContratoLeasing.Arrendatarios arrendatarios = factory.createRBMContratoLeasingArrendatarios();
		if(tramiteRegistro.getArrendatarios()!=null && !tramiteRegistro.getArrendatarios().isEmpty()){
			arrendatarios.getArrendatario().addAll((List<org.gestoresmadrid.oegam2comun.registradores.rm.rbm.IntervinienteType>) conversorInterviniente(tramiteRegistro.getArrendatarios(), arrendatarios));
			corpmeRM.setArrendatarios(arrendatarios);
		}	

		org.gestoresmadrid.oegam2comun.registradores.rm.rbm.RBMContratoLeasing.BienesObjetoContrato objetosFinanciados = factory.createRBMContratoLeasingBienesObjetoContrato();
		if(tramiteRegistro.getPropiedades()!=null){
			for (PropiedadDto propiedad : tramiteRegistro.getPropiedades()){
				org.gestoresmadrid.oegam2comun.registradores.rm.rbm.ObjetoLeasingType objeto = new org.gestoresmadrid.oegam2comun.registradores.rm.rbm.ObjetoLeasingType();
				org.gestoresmadrid.oegam2comun.registradores.rm.rbm.DesBienMuebleType bien = new org.gestoresmadrid.oegam2comun.registradores.rm.rbm.DesBienMuebleType();
				org.gestoresmadrid.oegam2comun.registradores.rm.rbm.DatosCategoriaType datosCat = new org.gestoresmadrid.oegam2comun.registradores.rm.rbm.DatosCategoriaType();
				org.gestoresmadrid.oegam2comun.registradores.rm.rbm.PrecioAdquisicionType precio = new org.gestoresmadrid.oegam2comun.registradores.rm.rbm.PrecioAdquisicionType();
				org.gestoresmadrid.oegam2comun.registradores.rm.rbm.ProveedorType proveedor = new org.gestoresmadrid.oegam2comun.registradores.rm.rbm.ProveedorType();
				bien.setCategoria(propiedad.getCategoria());
				objeto.setProveedor(proveedor);
				objeto.setPrecioAdquisicion(precio);
				precio.setImporteBase(String.valueOf(propiedad.getImpBase()).replace(".", ","));
				precio.setImporteImpuesto(String.valueOf(propiedad.getImpImpuesto()).replace(".", ","));
				precio.setImporteTotal(String.valueOf(propiedad.getImpTotal()).replace(".", ","));
				precio.setUnidadCuenta(propiedad.getUnidadCuenta());
				objeto.setProveedor(conversor.transform(propiedad.getIntervinienteRegistro(), org.gestoresmadrid.oegam2comun.registradores.rm.rbm.ProveedorType.class,"leasingProveedor"));

				//leasingProveedor

				if(propiedad.getCategoria().equalsIgnoreCase(TipoCategoria.AERONAVES.getValorEnum()))
					datosCat.getAeronave().add(conversor.transform(propiedad.getAeronave(), org.gestoresmadrid.oegam2comun.registradores.rm.rbm.AeronaveType.class));
				else if(propiedad.getCategoria().equalsIgnoreCase(TipoCategoria.BUQUES.getValorEnum()))
					datosCat.getBuque().add(conversor.transform(propiedad.getBuque(), org.gestoresmadrid.oegam2comun.registradores.rm.rbm.BuqueType.class));
				else if(propiedad.getCategoria().equalsIgnoreCase(TipoCategoria.ESTABLECIMIENTOS_MERCANTILES.getValorEnum()))
					datosCat.getEstablecimiento().add(conversor.transform(propiedad.getEstablecimiento(), org.gestoresmadrid.oegam2comun.registradores.rm.rbm.EstablecimientoMercantilType.class));
				else if(propiedad.getCategoria().equalsIgnoreCase(TipoCategoria.PROPIEDAD_INDUSTRIAL.getValorEnum()))
					datosCat.getPropiedadIndustrial().add(conversor.transform(propiedad.getPropiedadIndustrial(), org.gestoresmadrid.oegam2comun.registradores.rm.rbm.PropiedadIndustrialType.class));
				else if(propiedad.getCategoria().equalsIgnoreCase(TipoCategoria.PROPIEDAD_INTELECTUAL.getValorEnum()))
					datosCat.getPropiedadIntelectual().add(conversor.transform(propiedad.getPropiedadIntelectual(), org.gestoresmadrid.oegam2comun.registradores.rm.rbm.PropiedadIntelectualType.class));
				else if(propiedad.getCategoria().equalsIgnoreCase(TipoCategoria.VEHICULOS_MOTOR.getValorEnum()))
					datosCat.getVehiculo().add(conversor.transform(propiedad.getVehiculo(), org.gestoresmadrid.oegam2comun.registradores.rm.rbm.VehiculoType.class));
				else if(propiedad.getCategoria().equalsIgnoreCase(TipoCategoria.MAQUINARIA_INDUSTRIAL.getValorEnum()))
					datosCat.getMaquinaria().add(conversor.transform(propiedad.getMaquinaria(), org.gestoresmadrid.oegam2comun.registradores.rm.rbm.MaquinariaType.class));
				else if(propiedad.getCategoria().equalsIgnoreCase(TipoCategoria.OTROS.getValorEnum()))
					datosCat.getOtrosBienes().add(conversor.transform(propiedad.getOtrosBienes(), org.gestoresmadrid.oegam2comun.registradores.rm.rbm.OtrosBienesType.class));
				bien.setDatosCategoria(datosCat);
				objeto.setDesBienMueble(bien);
				objetosFinanciados.getBien().add(objeto);
			}

			corpmeRM.setBienesObjetoContrato(objetosFinanciados);
		}

		org.gestoresmadrid.oegam2comun.registradores.rm.rbm.CondicionesGeneralesType condicionesGenerales = factory.createCondicionesGeneralesType();
		org.gestoresmadrid.oegam2comun.registradores.rm.rbm.CondicionesParticularesType condicionesParticulares = null;
		List<ClausulaDto> clausulasGenerales = new ArrayList<ClausulaDto>();
		List<ClausulaDto> clausulasParticulares = new ArrayList<ClausulaDto>();
		for (ClausulaDto aux : tramiteRegistro.getClausulas()){
			if(aux.getTipoClausula().equalsIgnoreCase(TipoClausula.GENERAL.name()))
				clausulasGenerales.add(aux);
			else
				clausulasParticulares.add(aux);
		}

		if(clausulasGenerales!=null && !clausulasGenerales.isEmpty()){
			condicionesGenerales.getClausula().addAll(conversor.transform(clausulasGenerales, org.gestoresmadrid.oegam2comun.registradores.rm.rbm.ClausulaType.class));
		}else{
			clausulasGenerales=null;
			condicionesGenerales = null;
		}

		if(clausulasParticulares!=null && !clausulasParticulares.isEmpty()){
			org.gestoresmadrid.oegam2comun.registradores.rm.rbm.CondicionesParticularesType.ClausulasParticulares clausulasParticularesAux = new org.gestoresmadrid.oegam2comun.registradores.rm.rbm.CondicionesParticularesType.ClausulasParticulares ();
			clausulasParticularesAux.getClausula().addAll(conversor.transform(clausulasParticulares, org.gestoresmadrid.oegam2comun.registradores.rm.rbm.ClausulaType.class));
			condicionesParticulares = factory.createCondicionesParticularesType();
			condicionesParticulares.setClausulasParticulares(clausulasParticularesAux);
		}

		if(tramiteRegistro.getPactos()!=null && !tramiteRegistro.getPactos().isEmpty()){
			org.gestoresmadrid.oegam2comun.registradores.rm.rbm.CondicionesParticularesType.Pactos pactosAux = new org.gestoresmadrid.oegam2comun.registradores.rm.rbm.CondicionesParticularesType.Pactos();
			pactosAux.getPacto().addAll(conversor.transform(tramiteRegistro.getPactos(), org.gestoresmadrid.oegam2comun.registradores.rm.rbm.PactoType.class));
			if(condicionesParticulares == null)
				condicionesParticulares = factory.createCondicionesParticularesType();
			condicionesParticulares.setPactos(pactosAux);
		}
		if(tramiteRegistro.getCesionarios()!=null && !tramiteRegistro.getCesionarios().isEmpty()){
			org.gestoresmadrid.oegam2comun.registradores.rm.rbm.CesionType cesionarioAux = new org.gestoresmadrid.oegam2comun.registradores.rm.rbm.CesionType();
			cesionarioAux.setCesionAPersonaNODeterminada(convertirSiNo(tramiteRegistro.getPersonaNoDeterminada()));
			cesionarioAux.setTipoCesionTercero(tramiteRegistro.getTipoCesionTercero());

			org.gestoresmadrid.oegam2comun.registradores.rm.rbm.CesionType.Cesionarios cesionariosAux = new org.gestoresmadrid.oegam2comun.registradores.rm.rbm.CesionType.Cesionarios();
			cesionariosAux.getCesionario().addAll((List<org.gestoresmadrid.oegam2comun.registradores.rm.rbm.CesionarioType>) conversorCesionario(tramiteRegistro.getCesionarios(), "leasing"));
			cesionarioAux.setCesionarios(cesionariosAux);
			if(condicionesParticulares == null)
				condicionesParticulares = factory.createCondicionesParticularesType();
			condicionesParticulares.setCesion(cesionarioAux);
		}
		corpmeRM.setCondicionesGenerales(condicionesGenerales);
		corpmeRM.setCondicionesParticulares(condicionesParticulares);

		org.gestoresmadrid.oegam2comun.registradores.rm.rbm.DatosFinancierosLeasingType datosFinanciacion = factory.createDatosFinancierosLeasingType();

		datosFinanciacion = conversor.transform(tramiteRegistro.getDatosFinanciero(), org.gestoresmadrid.oegam2comun.registradores.rm.rbm.DatosFinancierosLeasingType.class, "leasingDatosFinancieros");
		if(tramiteRegistro.getDatosFinanciero().getReconocimientoDeudas()!=null && !tramiteRegistro.getDatosFinanciero().getReconocimientoDeudas().isEmpty())
			datosFinanciacion.getReconocimientoDeuda().addAll(conversor.transform(tramiteRegistro.getDatosFinanciero().getReconocimientoDeudas(), org.gestoresmadrid.oegam2comun.registradores.rm.rbm.ReconocimientoDeudaLeasingType.class, "leasingReconocimientoDeuda"));
		datosFinanciacion.setComisiones(null);
		// otrosImportes;
		if(tramiteRegistro.getDatosFinanciero().getOtroImportes()!=null && !tramiteRegistro.getDatosFinanciero().getOtroImportes().isEmpty()){
			OtrosImportes otros = new OtrosImportes();
			datosFinanciacion.setOtrosImportes(otros);

			datosFinanciacion.getOtrosImportes().getOtroImporte().addAll(conversor.transform(tramiteRegistro.getDatosFinanciero().getOtroImportes(), org.gestoresmadrid.oegam2comun.registradores.rm.rbm.OtroImporteType.class, "leasingOtroImporte"));
		}	
		//cuadroAmortizacion
		if(tramiteRegistro.getDatosFinanciero().getCuadrosAmortizacion()!=null && !tramiteRegistro.getDatosFinanciero().getCuadrosAmortizacion().isEmpty()){
			CuadroAmortizacionLeasingType amort = new CuadroAmortizacionLeasingType();
			datosFinanciacion.setCuadroAmortizacion(amort);
			SimpleDateFormat formateador = new SimpleDateFormat("dd/MM/yyyy");
			for (CuadroAmortizacionDto cuadro : tramiteRegistro.getDatosFinanciero().getCuadrosAmortizacion()) {


				if(cuadro.getTipoPlazo().equals(IMPUESTOS_FINANCIADOS)){
					FinanciacionImpuestoAnticipadoType impFinan = new FinanciacionImpuestoAnticipadoType();
					CuadroAmortizacionFIType cuadroFI = new CuadroAmortizacionFIType();
					impFinan.setCuadroAmortizacionFI(cuadroFI);
					impFinan.setImporteImpuesto(String.valueOf(tramiteRegistro.getDatosFinanciero().getImpImpuesto()).replace(".", ","));
					impFinan.setTasaAnualEquivalente(String.valueOf(tramiteRegistro.getDatosFinanciero().getTasaAnualEquivFi()).replace(".", ","));
					impFinan.setTipoInteresNominalAnual(String.valueOf(tramiteRegistro.getDatosFinanciero().getTipoInteresNominalAnualFi()).replace(".", ","));
					PlazoFIType plazoFI = new PlazoFIType();
					plazoFI.setFechaVencimiento(formateador.format(cuadro.getFechaVencimiento()));
					plazoFI.setImporteAmortizacion(String.valueOf(cuadro.getImpAmortizacion()).replace(".", ","));
					plazoFI.setImporteCapitalPendiente(String.valueOf(cuadro.getImpCapitalPendiente()).replace(".", ","));
					plazoFI.setImporteCargaFinanciera(String.valueOf(cuadro.getImpCargaFinan()).replace(".", ","));
					plazoFI.setImporteTotalCuota(String.valueOf(cuadro.getImpTotalCuota()).replace(".", ","));
					impFinan.getCuadroAmortizacionFI().getPlazoFI().add(plazoFI);
					datosFinanciacion.setFinanciacionImpuestoAnticipado(impFinan);
				}else if(cuadro.getTipoPlazo().equals(SEGUROS_FINANCIADOS)){
					FinanciacionSeguroType impFinan = new FinanciacionSeguroType();
					CuadroAmortizacionFSType cuadroFS = new CuadroAmortizacionFSType();
					PlazoFSType plazoFS = new PlazoFSType();
					impFinan.setImporteSeguro(String.valueOf(tramiteRegistro.getDatosFinanciero().getImpSeguro()).replace(".", ","));
					impFinan.setTasaAnualEquivalente(String.valueOf(tramiteRegistro.getDatosFinanciero().getTasaAnualEquivFs()).replace(".", ","));
					impFinan.setTipoInteresNominalAnual(String.valueOf(tramiteRegistro.getDatosFinanciero().getTipoInteresNominalAnualFs()).replace(".", ","));
					impFinan.setCuadroAmortizacionFS(cuadroFS);

					plazoFS.setFechaVencimiento(formateador.format(cuadro.getFechaVencimiento()));
					plazoFS.setImporteAmortizacion(String.valueOf(cuadro.getImpAmortizacion()).replace(".", ","));
					plazoFS.setImporteCapitalPendiente(String.valueOf(cuadro.getImpCapitalPendiente()).replace(".", ","));
					plazoFS.setImporteCargaFinanciera(String.valueOf(cuadro.getImpCargaFinan()).replace(".", ","));
					plazoFS.setImporteTotalCuota(String.valueOf(cuadro.getImpTotalCuota()).replace(".", ","));
					impFinan.getCuadroAmortizacionFS().getPlazoFS().add(plazoFS);
					datosFinanciacion.setFinanciacionSeguro(impFinan);
				}else{
					datosFinanciacion.getCuadroAmortizacion().getPlazo().add(conversor.transform(cuadro, org.gestoresmadrid.oegam2comun.registradores.rm.rbm.PlazoLeasingType.class, "leasingPlazo"));				
				}
			}


		}

		corpmeRM.setDatosFinancieros(datosFinanciacion);
		//FIN

		org.gestoresmadrid.oegam2comun.registradores.rm.rbm.RBMContratoLeasing.Fiadores fiadores = factory.createRBMContratoLeasingFiadores();
		if(tramiteRegistro.getAvalistas()!=null && !tramiteRegistro.getAvalistas().isEmpty()){
			fiadores.getFiador().addAll((List<IntervinienteFiadorType>) conversorFiador(tramiteRegistro.getAvalistas(), fiadores));
			corpmeRM.setFiadores(fiadores);
		}

		org.gestoresmadrid.oegam2comun.registradores.rm.rbm.RBMContratoLeasing.Firmas firmas = factory.createRBMContratoLeasingFirmas();
		if(tramiteRegistro.getDatoFirmas()!=null && !tramiteRegistro.getDatoFirmas().isEmpty()){
			firmas.getFirma().addAll(conversor.transform(tramiteRegistro.getDatoFirmas(), org.gestoresmadrid.oegam2comun.registradores.rm.rbm.FirmaType.class, "leasingFirma"));
		}
		corpmeRM.setFirmas(firmas);

		corpmeRM.setIdentificacion(conversor.transform(tramiteRegistro, org.gestoresmadrid.oegam2comun.registradores.rm.rbm.IdentificacionContratoType.class));

		corpmeRM.setVersion("5.0.0");

		corpmeRM.setClausulaLOPD(ClausulaLOPDType.CLAUSULA_LOPD_REGISTRADORES.value());

		String cadenaXML = xmlFactory.toXML(corpmeRM, context);

		guardarDocumento(cadenaXML, tramiteRegistro.getIdTramiteRegistro());

		return xmlFactory.validarRbm(cadenaXML, context, "RBM_LEASING");
	}


	public ResultBean construirRbmXMLCancelacion(TramiteRegRbmDto tramiteRegistro) throws Exception, OegamExcepcion {

		XmlCORPMEFactory xmlFactory = new XmlCORPMEFactory();
		JAXBContext context = JAXBContext.newInstance("org.gestoresmadrid.oegam2comun.registradores.rm.rbm");
		org.gestoresmadrid.oegam2comun.registradores.rm.rbm.ObjectFactory factory = new org.gestoresmadrid.oegam2comun.registradores.rm.rbm.ObjectFactory();
		RBMDocumentoCancelacion corpmeRM = factory.createRBMDocumentoCancelacion();

		corpmeRM.setCausaCancelacion(tramiteRegistro.getCausaCancelacion());
		corpmeRM.getSituacionJuridicaCancelada().addAll(tramiteRegistro.getSituacionesJuridicas());

		corpmeRM.setFirmaSolicitante(conversor.transform(tramiteRegistro.getDatoFirmaDto(), org.gestoresmadrid.oegam2comun.registradores.rm.rbm.FirmaSolicitanteType.class, "cancelacionFirma"));

		corpmeRM.setIdentificacionContratoInscrito(conversor.transform(tramiteRegistro, org.gestoresmadrid.oegam2comun.registradores.rm.rbm.IdentificacionContratoInscritoType.class));

		if(null != tramiteRegistro.getDatosInscripcion() && null != tramiteRegistro.getDatosInscripcion().getFechaInscripcion()){
			SimpleDateFormat sm = new SimpleDateFormat("dd/MM/yyyy");
			corpmeRM.getIdentificacionContratoInscrito().getDatosInscripcion().setFechaInscripcion(sm.format(tramiteRegistro.getDatosInscripcion().getFechaInscripcion()));
		}

		if(null != tramiteRegistro.getImporteComisionCancelacion() &&  !tramiteRegistro.getImporteComisionCancelacion().equals(BigDecimal.ZERO)){
			corpmeRM.setImporteComisionCancelacion((tramiteRegistro.getImporteComisionCancelacion().toString()).replace(".", ","));
		}
		corpmeRM.setModelo(tramiteRegistro.getModeloContrato());


		if(null != tramiteRegistro.getSolicitante()){
			corpmeRM.setSolicitanteCancelacion(convertirCancelacionSolicitante(tramiteRegistro.getSolicitante()));
		}

		IdentificacionBienCancelacionType objetosFinanciados = factory.createIdentificacionBienCancelacionType();
		for (PropiedadDto propiedad : tramiteRegistro.getPropiedades()){
			if(propiedad.getCategoria().equalsIgnoreCase(TipoCategoria.VEHICULOS_MOTOR.getValorEnum())){
				objetosFinanciados.setBastidor(propiedad.getVehiculo().getBastidor());
				objetosFinanciados.setClase(propiedad.getVehiculo().getTipo());
				objetosFinanciados.setMarca(propiedad.getVehiculo().getMarca());
				objetosFinanciados.setMatricula(propiedad.getVehiculo().getMatricula());
				objetosFinanciados.setModelo(propiedad.getVehiculo().getModelo());
				objetosFinanciados.setNumeroFabricacion(propiedad.getVehiculo().getNive());
			}else if(propiedad.getCategoria().equalsIgnoreCase(TipoCategoria.AERONAVES.getValorEnum())){
				objetosFinanciados.getAeronave().add(conversor.transform(propiedad.getAeronave(), org.gestoresmadrid.oegam2comun.registradores.rm.rbm.AeronaveType.class));
			}else if(propiedad.getCategoria().equalsIgnoreCase(TipoCategoria.BUQUES.getValorEnum())){
				objetosFinanciados.getBuque().add(conversor.transform(propiedad.getBuque(), org.gestoresmadrid.oegam2comun.registradores.rm.rbm.BuqueType.class));
			}else if(propiedad.getCategoria().equalsIgnoreCase(TipoCategoria.ESTABLECIMIENTOS_MERCANTILES.getValorEnum())){
				objetosFinanciados.getEstablecimiento().add(conversor.transform(propiedad.getEstablecimiento(), org.gestoresmadrid.oegam2comun.registradores.rm.rbm.EstablecimientoMercantilType.class));
			}else if(propiedad.getCategoria().equalsIgnoreCase(TipoCategoria.PROPIEDAD_INDUSTRIAL.getValorEnum())){
				objetosFinanciados.getPropiedadIndustrial().add(conversor.transform(propiedad.getPropiedadIndustrial(), org.gestoresmadrid.oegam2comun.registradores.rm.rbm.PropiedadIndustrialType.class));
			}else if(propiedad.getCategoria().equalsIgnoreCase(TipoCategoria.PROPIEDAD_INTELECTUAL.getValorEnum())){
				objetosFinanciados.getPropiedadIntelectual().add(conversor.transform(propiedad.getPropiedadIntelectual(), org.gestoresmadrid.oegam2comun.registradores.rm.rbm.PropiedadIntelectualType.class));
			}else if(propiedad.getCategoria().equalsIgnoreCase(TipoCategoria.MAQUINARIA_INDUSTRIAL.getValorEnum())){
				objetosFinanciados.getMaquinaria().add(conversor.transform(propiedad.getMaquinaria(), org.gestoresmadrid.oegam2comun.registradores.rm.rbm.MaquinariaType.class));
			}else if(propiedad.getCategoria().equalsIgnoreCase(TipoCategoria.OTROS.getValorEnum())){
				objetosFinanciados.getOtrosBienes().add(conversor.transform(propiedad.getOtrosBienes(), org.gestoresmadrid.oegam2comun.registradores.rm.rbm.OtrosBienesType.class));
			}
		}
		corpmeRM.setIdentificacionBien(objetosFinanciados);

		corpmeRM.setTituloDocumento(TITULO_DOCUMENTO);
		corpmeRM.setClausulaLOPD(ClausulaLOPDType.CLAUSULA_LOPD_REGISTRADORES.value());

		corpmeRM.setVersion("5.0.0");

		// Genera el string xml sin saltos ni espacios (requerimiento CORPME):
		String cadenaXML = xmlFactory.toXML(corpmeRM, context);

		guardarDocumento(cadenaXML, tramiteRegistro.getIdTramiteRegistro());

		// Valida la cadena xml contra el esquema xsd:
		ResultBean resultValidacion = xmlFactory.validarRbm(cadenaXML, context, "RBM_CANCELACION");
		if (!resultValidacion.getError()) {
			resultValidacion.addAttachment("xml", cadenaXML);			
		} else {
			log.error("xmlFactory no ha validado correctamente el XML");
		}
		return resultValidacion;
	}


	public ResultBean construirRbmXMLDesistimiento(TramiteRegRbmDto tramiteRegistro) throws Exception, OegamExcepcion {

		XmlCORPMEFactory xmlFactory = new XmlCORPMEFactory();
		JAXBContext context = JAXBContext.newInstance("org.gestoresmadrid.oegam2comun.registradores.rm.rbm");
		org.gestoresmadrid.oegam2comun.registradores.rm.rbm.ObjectFactory factory = new org.gestoresmadrid.oegam2comun.registradores.rm.rbm.ObjectFactory();
		RBMDesistimiento corpmeRM = factory.createRBMDocumentoDesistimiento();


		//Identificación del documento
		SimpleDateFormat sm = new SimpleDateFormat("dd/MM/yyyy");
		IdentificacionDocumento identificacionDocumento = new IdentificacionDocumento();

		IdentificacionRegistralType identificacionRegistral = new IdentificacionRegistralType();
		identificacionRegistral.setCodigoRBM(tramiteRegistro.getRegistro().getIdRegistro());

		if(null != tramiteRegistro.getFecEntradaOrigen()){
			identificacionRegistral.setFechaEntrada(sm.format(tramiteRegistro.getFecEntradaOrigen()));
		}

		if(UtilesValidaciones.validarObligatoriedad(tramiteRegistro.getNumEntradaOrigen())){
			identificacionRegistral.setNumeroEntrada(tramiteRegistro.getNumEntradaOrigen().toString());
		}

		identificacionDocumento.setEntrada(identificacionRegistral);

		PresentacionType presentacion = new PresentacionType();

		if(null != tramiteRegistro.getFecPresentacionOrigen()){
			presentacion.setFechaPresentacion(sm.format(tramiteRegistro.getFecPresentacionOrigen()));
		}

		if(UtilesValidaciones.validarObligatoriedad(tramiteRegistro.getNumPresentacionOrigen())){
			presentacion.setNumeroAsiento(tramiteRegistro.getNumPresentacionOrigen().toString());
		}
		identificacionDocumento.setPresentacion(presentacion);
		corpmeRM.setIdentificacionDocumento(identificacionDocumento);

		//Solicitante y sus representantes
		if(null != tramiteRegistro.getSolicitante()){
			corpmeRM.setSolicitante(convertirDesistimientoSolicitante(tramiteRegistro.getSolicitante()));
		}

		//Firma
		corpmeRM.setFirmaSolicitante(conversor.transform(tramiteRegistro.getDatoFirmaDto(), org.gestoresmadrid.oegam2comun.registradores.rm.rbm.FirmaSolicitanteType.class, "cancelacionFirma"));

		List<String> textoSolicitud = new ArrayList<String>();
		textoSolicitud.add("Por la presente y de conformidad con lo dispuesto en el artículo 433 del Reglamento Hipotecario, aplicable a los Registros de Bienes Muebles según dispone el apartado 6º de la disposición adicional única del Real Decreto 1828/1999, de 3 de diciembre, DESISTE de la solicitud de inscripción del documento presentado en ese Registro. Y en consecuencia SOLICITA SU CANCELACION.");
		corpmeRM.setTextoSolicitud(textoSolicitud);

		corpmeRM.setClausulaLOPD(ClausulaLOPDType.CLAUSULA_LOPD_REGISTRADORES.value());

		corpmeRM.setVersion("5.0.0");

		// Genera el string xml sin saltos ni espacios (requerimiento CORPME):
		String cadenaXML = xmlFactory.toXML(corpmeRM, context);

		guardarDocumento(cadenaXML, tramiteRegistro.getIdTramiteRegistro());

		// Valida la cadena xml contra el esquema xsd:
		ResultBean resultValidacion = xmlFactory.validarRbm(cadenaXML, context, "RBM_DESISTIMIENTO");
		if (!resultValidacion.getError()) {
			resultValidacion.addAttachment("xml", cadenaXML);			
		} else {
			log.error("xmlFactory no ha validado correctamente el XML");
		}
		return resultValidacion;
	}


	private void guardarDocumento(String cadenaXML, BigDecimal idTramiteRegistro) throws Exception, OegamExcepcion {
		FicheroBean ficheroBean = new FicheroBean();
		ficheroBean.setTipoDocumento(ConstantesGestorFicheros.REGISTRADORES);
		ficheroBean.setSubTipo(ConstantesGestorFicheros.REGISTRADORES_XML);
		ficheroBean.setExtension(ConstantesGestorFicheros.EXTENSION_XML);
		ficheroBean.setFicheroByte(cadenaXML.getBytes("UTF-8"));
		ficheroBean.setFecha(Utilidades.transformExpedienteFecha(idTramiteRegistro));
		ficheroBean.setSobreescribir(true);
		ficheroBean.setNombreDocumento(idTramiteRegistro + ConstantesGestorFicheros.NOMBRE_RM);
		File fichero = gestorDocumentos.guardarByte(ficheroBean);

		if (!fichero.exists()) {
			log.error("No se ha creado el fichero con el XML");
		}
	}

	private Modelo1 buildModelo1(TramiteRegistroDto tramiteRegistro, ObjectFactory factory) {
		Modelo1 modelo1;
		modelo1 = conversor.transform(tramiteRegistro, Modelo1.class);

		if (modelo1.getAcuerdos() == null) {
			modelo1.setAcuerdos(factory.createAcuerdosType());
		}
		modelo1.getAcuerdos().setNombramientos(incluirNombramientos(tramiteRegistro, factory));
		modelo1.getAcuerdos().setCeses(incluirCeses(tramiteRegistro, factory));
		return modelo1;
	}

	private Modelo2 buildModelo2(TramiteRegistroDto tramiteRegistro, ObjectFactory factory) {
		Modelo2 modelo2;
		modelo2 = conversor.transform(tramiteRegistro, Modelo2.class);
		modelo2.setNombramiento(incluirNombramientos2(tramiteRegistro));
		return modelo2;
	}

	private Modelo3 buildModelo3(TramiteRegistroDto tramiteRegistro, ObjectFactory factory) {
		Modelo3 modelo3;
		modelo3 = conversor.transform(tramiteRegistro, Modelo3.class);
		if (modelo3.getAcuerdos() == null) {
			modelo3.setAcuerdos(factory.createAcuerdos2Type());
		}
		modelo3.getAcuerdos().setNombramientos(incluirNombramientos(tramiteRegistro, factory));
		modelo3.getAcuerdos().setCeses(incluirCeses(tramiteRegistro, factory));
		return modelo3;
	}

	private Modelo4 buildModelo4(TramiteRegistroDto tramiteRegistro, ObjectFactory factory) {
		Modelo4 modelo4;
		modelo4 = conversor.transform(tramiteRegistro, Modelo4.class);
		if (modelo4.getAcuerdos() == null) {
			modelo4.setAcuerdos(factory.createAcuerdos2Type());
		}
		modelo4.getAcuerdos().setNombramientos(incluirNombramientos(tramiteRegistro, factory));
		modelo4.getAcuerdos().setCeses(incluirCeses(tramiteRegistro, factory));
		return modelo4;
	}

	private Modelo5 buildModelo5(TramiteRegistroDto tramiteRegistro, ObjectFactory factory) {
		Modelo5 modelo5;
		modelo5 = conversor.transform(tramiteRegistro, Modelo5.class);
		if (modelo5.getAcuerdos() == null) {
			modelo5.setAcuerdos(factory.createAcuerdosType());
		}
		modelo5.getAcuerdos().setNombramientos(incluirNombramientos(tramiteRegistro, factory));
		modelo5.getAcuerdos().setCeses(incluirCeses(tramiteRegistro, factory));
		return modelo5;
	}

	private NombramientoType incluirNombramientos2(TramiteRegistroDto tramiteRegistro) {
		if (tramiteRegistro.getNombramientos() != null && !tramiteRegistro.getNombramientos().isEmpty()) {
			for (AcuerdoDto acuerdo : tramiteRegistro.getNombramientos()) {
				NombramientoType nombramiento = conversor.transform(acuerdo, NombramientoType.class);
				if (nombramiento.getDuracionCargo().isIndefinida()) {
					nombramiento.getDuracionCargo().setDuracion(null);
				}else{
					nombramiento.getDuracionCargo().setIndefinida(null);
				}
				return nombramiento;
			}
		}
		return null;
	}

	private NombramientosType incluirNombramientos(TramiteRegistroDto tramiteRegistro, ObjectFactory factory) {
		if (tramiteRegistro.getNombramientos() != null && !tramiteRegistro.getNombramientos().isEmpty()) {
			NombramientosType nombramientosType = factory.createNombramientosType();
			for (AcuerdoDto acuerdo : tramiteRegistro.getNombramientos()) {
				NombramientoType nombramiento = conversor.transform(acuerdo, NombramientoType.class);
				if (nombramiento.getDuracionCargo().isIndefinida()) {
					nombramiento.getDuracionCargo().setDuracion(null);
				}else{
					nombramiento.getDuracionCargo().setIndefinida(null);
				}
				nombramientosType.getNombramiento().add(nombramiento);
			}
			return nombramientosType;
		}
		return null;
	}

	private CesesType incluirCeses(TramiteRegistroDto tramiteRegistro, ObjectFactory factory) {
		if (tramiteRegistro.getCeses() != null && !tramiteRegistro.getCeses().isEmpty()) {
			CesesType cesesType = factory.createCesesType();
			cesesType.getCese().addAll(conversor.transform(tramiteRegistro.getCeses(), CeseType.class));
			return cesesType;
		}
		return null;
	}

	// Método para incorporar el elemento que dota de estilos al xml RM:
	private String incorporarElementoEstilos(String cadenaXML) {
		// Cadena que ha de insertarse:
		String elemento = "<?xml-stylesheet type=\"text/xsl\" href=\"CORPME_RM_CeseyNombramiento_fc.xsl\"?>";
		StringBuilder modificada = new StringBuilder();
		// Para poder hacer inserciones en la cadena:
		modificada.append(cadenaXML);
		// Localiza la posición exacta de la inserción:
		int pos = modificada.indexOf("><");
		// Inserta la cadena en la posición:
		modificada.insert(pos + 1, elemento);
		return modificada.toString();
	}
}
