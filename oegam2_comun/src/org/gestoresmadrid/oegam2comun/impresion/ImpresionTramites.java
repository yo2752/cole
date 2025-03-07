package org.gestoresmadrid.oegam2comun.impresion;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.gestoresmadrid.core.contrato.model.vo.ContratoVO;
import org.gestoresmadrid.core.model.enumerados.TipoImpreso;
import org.gestoresmadrid.core.model.enumerados.TipoTramiteTrafico;
import org.gestoresmadrid.core.personas.model.enumerados.TipoPersona;
import org.gestoresmadrid.core.springContext.ContextoSpring;
import org.gestoresmadrid.core.vehiculo.model.enumerados.ConceptoTutela;
import org.gestoresmadrid.oegam2comun.personas.model.service.ServicioPersona;
import org.gestoresmadrid.oegam2comun.trafico.view.dto.TramiteTrafDto;
import org.gestoresmadrid.oegamComun.interviniente.view.dto.IntervinienteTraficoDto;
import org.gestoresmadrid.oegamComun.persona.view.dto.PersonaDto;
import org.gestoresmadrid.utilidades.components.GestorPropiedades;
import org.gestoresmadrid.utilidades.components.Utiles;
import org.springframework.beans.factory.annotation.Autowired;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Image;

import escrituras.beans.ColegioBean;
import escrituras.beans.Persona;
import escrituras.beans.ResultBean;
import escrituras.modelo.ModeloColegiado;
import general.utiles.BasicText;
import general.utiles.UtilesCadenaCaracteres;
import oegam.constantes.ConstantesPQ;
import trafico.beans.IntervinienteTrafico;
import trafico.beans.TramiteTraficoBean;
import trafico.beans.TramiteTraficoTransmisionBean;
import trafico.beans.avpobastigest.CargaGestBean;
import trafico.beans.avpobastigest.EmbargoGestBean;
import trafico.beans.avpobastigest.GestBean;
import trafico.beans.daos.BeanPQDatosColegio;
import trafico.modelo.ModeloCreditosTrafico;
import trafico.modelo.ModeloTransmision;
import trafico.utiles.ConstantesPDF;
import trafico.utiles.PdfMaker;
import trafico.utiles.UtilResources;
import trafico.utiles.UtilesConversiones;
import trafico.utiles.constantes.ConstantesTrafico;
import trafico.utiles.enumerados.MapeoTipoTransferencia;
import trafico.utiles.imprimir.ImprimirGeneral;
import utilidades.web.OegamExcepcion;

/**
 * Proceso para la impresión de trámites masivos
 */
public class ImpresionTramites extends ImprimirGeneral {

	private TramiteTraficoTransmisionBean detalleTransmision;
	private ArrayList<TramiteTraficoTransmisionBean> listaTramitesTrans;
	private byte[] byte1;

	private ModeloTransmision modeloTransmision;
	private ModeloCreditosTrafico modeloCreditosTrafico;

	private String ruta;
	private String rutaPlantillas;

	private static final String CLAVE_CIFRADO = "ClaveCifrado";

	@Autowired
	private GestorPropiedades gestorPropiedades;

	@Autowired
	private UtilesConversiones utilesConversiones;

	@Autowired
	Utiles utiles;

	public ResultBean impresionGeneral(String numExpediente, String numColegiado, BigDecimal idContrato, String tipoImpreso, BigDecimal idUsuario, TipoTramiteTrafico tipoTramite)
			throws OegamExcepcion, Throwable {
		ResultBean resultadoMetodo = new ResultBean();

		if (TipoTramiteTrafico.Matriculacion.equals(tipoTramite)) {
			resultadoMetodo = new ImpresionTramitesMatw().matriculacionGeneralMatw(numExpediente, tipoImpreso, tipoTramite);
		} else if (TipoTramiteTrafico.Transmision.equals(tipoTramite) || TipoTramiteTrafico.TransmisionElectronica.equals(tipoTramite)) {
			resultadoMetodo = new ImpresionTramitesTransmision().impresionGeneralTransmision(numExpediente, numColegiado, tipoImpreso, idContrato, tipoTramite, idUsuario);
		} else if (TipoTramiteTrafico.Baja.equals(tipoTramite)) {
			resultadoMetodo = new ImpresionTramitesBaja().impresionGeneralBaja(numExpediente, tipoImpreso, idUsuario, tipoTramite);
		}
		return resultadoMetodo;
	}

	public ResultBean impresionListadoBastidores(List<String> numExpedientes, String numColegiado, BigDecimal idUsuario, TipoTramiteTrafico tipoTramite, Long idContrato) throws OegamExcepcion,
			Throwable {
		ResultBean resultadoMetodo = new ResultBean();

		if (TipoTramiteTrafico.Matriculacion.equals(tipoTramite)) {
			resultadoMetodo = new ImpresionTramitesMatw().matriculacionListadoBastidoresMatriculacion(numExpedientes);
		} else if (TipoTramiteTrafico.Transmision.equals(tipoTramite) || TipoTramiteTrafico.TransmisionElectronica.equals(tipoTramite)) {
			resultadoMetodo = new ImpresionTramitesTransmision().impresionListadoBastidoresTransmision(numExpedientes, numColegiado, new BigDecimal(idContrato), tipoTramite.getValorEnum());
		} else if (TipoTramiteTrafico.Baja.equals(tipoTramite)) {
			resultadoMetodo = new ImpresionTramitesBaja().impresionListadoBastidoresBaja(utiles.transformListaToArrayString(numExpedientes), idUsuario);
		} else if (TipoTramiteTrafico.Duplicado.equals(tipoTramite)) {
			resultadoMetodo = new ImpresionTramitesDuplicado().matriculacionListadoBastidoresDuplicado(numExpedientes, idUsuario);
		}
		return resultadoMetodo;
	}

	/**
	 * Metodo para la impresión de documentos guardados con el gestor de ficheros, para documentos oficiales se descarga en "file" no en "pdf"
	 * @param numExpedientes
	 * @param tipo
	 * @param tipoImpresion
	 * @return
	 */
	public ResultBean obtenerDetalleTramite(String tipoTramite, String numExpediente, String numColegiado, String tipoImpreso) throws Throwable {
		ResultBean resultadoMetodo = new ResultBean();
		detalleTransmision = new TramiteTraficoTransmisionBean(true);
		Map<String, Object> resultadoDetalle = getModeloTransmision().obtenerDetalleConDescripciones(new BigDecimal(numExpediente));
		resultadoMetodo = (ResultBean) resultadoDetalle.get(ConstantesPQ.RESULTBEAN);
		//Aquí guarda los detalles de la transmision. En la clase padre
		detalleTransmision = (TramiteTraficoTransmisionBean) resultadoDetalle.get(ConstantesPQ.BEANPANTALLA);

		if (tipoImpreso.equals(TipoImpreso.MatriculacionListadoBastidores.toString())) {
			listaTramitesTrans.add(detalleTransmision);
		}
		return resultadoMetodo;
	}

	public byte[] rutaAgua(PdfMaker pdf, String tipoImpreso, int[] vectPags, String rutaJpg, String ruta, byte[] byte1) {
		UtilResources util = new UtilResources();
		// Formateamos el resto de las etiquetas de la plantilla, nubes y codigos de barras.
		String rutaMarcaAgua = util.getFilePath(ruta + rutaJpg);
		Image img = pdf.cargarImagen(rutaMarcaAgua);
		byte1 = pdf.insertarMarcaDeAgua(byte1, img, vectPags, ConstantesPDF._110, ConstantesPDF._250, ConstantesPDF._45);
		return byte1;
	}

	public void nubePuntos(String nube, String nube2, PdfMaker pdf, int[] vectPags, ContratoVO contrato) {
		nube = obtenerNubeTransmision(detalleTransmision, contrato);
		String caracteresNuevaAplicacion = gestorPropiedades.valorPropertie("sustituye.caracteres.nueva.aplicacion");
		if (caracteresNuevaAplicacion != null && "SI".equals(caracteresNuevaAplicacion)) {
			nube = UtilesCadenaCaracteres.sustituyeCaracteresNuevaAplicacion(nube);
		} else {
			nube = UtilesCadenaCaracteres.sustituyeCaracteres(nube);
		}
		byte1 = insertarNubePuntos(ConstantesTrafico.ID_NUBE_PUNTOS, byte1, vectPags, nube, pdf);
	}

	private String obtenerNubeTransmision(TramiteTraficoTransmisionBean tramite, ContratoVO contrato) {
		SimpleDateFormat formato = new SimpleDateFormat("yyyyMMdd");
		StringBuffer line = new StringBuffer();
		String nuevaVersion = gestorPropiedades.valorPropertie("version3.nube.transmision");
		String campo = "";
		line.append(ConstantesPDF.VERSION_TRANSMISION);
		line.append(ConstantesPDF.PROGRAMA_TRANSMISION);

		campo = tramite.getTramiteTraficoBean().getTasa().getCodigoTasa();
		if (campo == null) {
			line.append(BasicText.changeSize("", ConstantesTrafico.TAM_TASA));
		} else if (campo.equals("EXENTO")) {
			line.append(BasicText.changeSize(campo, ConstantesTrafico.TAM_TASA));
		} else {
			line.append(BasicText.changeSize(campo, ConstantesTrafico.TAM_TASA, '0', false));
		}

		campo = tramite.getTramiteTraficoBean().getVehiculo().getMatricula();
		if (campo == null) {
			line.append(BasicText.changeSize("", ConstantesTrafico.TAM_MATRICULA));
		} else {
			campo = utiles.cambiaFormatoMatricula(campo);
			line.append(BasicText.changeSize(campo, ConstantesTrafico.TAM_MATRICULA));
		}

		try {
			if (tramite.getTramiteTraficoBean().getVehiculo().getFechaMatriculacion() != null && !tramite.getTramiteTraficoBean().getVehiculo().getFechaMatriculacion().isfechaNula()) {
				campo = formato.format(tramite.getTramiteTraficoBean().getVehiculo().getFechaMatriculacion().getTimestamp());
			} else {
				campo = null;
			}
		} catch (ParseException e) {
			campo = null;
		}

		if (campo == null) {
			line.append(BasicText.changeSize("", ConstantesTrafico.TAM_FECHA_MATRICULACION));
		} else {
			line.append(BasicText.changeSize(campo, ConstantesTrafico.TAM_FECHA_MATRICULACION, '0', false));
		}

		try {
			if (tramite.getTramiteTraficoBean().getFechaPresentacion() != null && !tramite.getTramiteTraficoBean().getFechaPresentacion().isfechaNula()) {
				campo = formato.format(tramite.getTramiteTraficoBean().getFechaPresentacion().getTimestamp());
			} else {
				campo = null;
			}
		} catch (ParseException e) {
			campo = null;
		}

		if (campo == null) {
			line.append(BasicText.changeSize("", ConstantesTrafico.TAM_FECHA_PRESENTACION));
		} else {
			line.append(BasicText.changeSize(campo, ConstantesTrafico.TAM_FECHA_PRESENTACION, '0', false));
		}

		campo = tramite.getTramiteTraficoBean().getJefaturaTrafico().getJefatura();
		if (campo == null) {
			line.append(BasicText.changeSize("", ConstantesTrafico.TAM_JEFATURA_TRAFICO));
		} else {
			line.append(BasicText.changeSize(campo, ConstantesTrafico.TAM_JEFATURA_TRAFICO));
		}

		campo = tramite.getTransmitenteBean().getPersona().getNif();
		if (campo == null) {
			line.append(BasicText.changeSize("", ConstantesTrafico.TAM_DNI_TRANSMITENTE));
		} else {
			line.append(BasicText.changeSize(campo, ConstantesTrafico.TAM_DNI_TRANSMITENTE));
		}

		campo = tramite.getAdquirienteBean().getPersona().getNif();
		if (campo == null) {
			line.append(BasicText.changeSize("", ConstantesTrafico.TAM_DNI_ADQUIRENTE));
		} else {
			line.append(BasicText.changeSize(campo, ConstantesTrafico.TAM_DNI_ADQUIRENTE));
		}

		try {
			if (tramite.getAdquirienteBean().getPersona().getFechaNacimientoBean() != null && !tramite.getAdquirienteBean().getPersona().getFechaNacimientoBean().isfechaNula()) {
				campo = formato.format(tramite.getAdquirienteBean().getPersona().getFechaNacimientoBean().getTimestamp());
			} else {
				campo = null;
			}
		} catch (ParseException e) {
			campo = null;
		}

		if (campo == null) {
			line.append(BasicText.changeSize("", ConstantesTrafico.TAM_FECHA_NACIMIENTO_ADQUIRENTE));
		} else {
			line.append(BasicText.changeSize(campo, ConstantesTrafico.TAM_FECHA_NACIMIENTO_ADQUIRENTE, '0', false));
		}

		campo = null != tramite.getAdquirienteBean().getPersona().getTipoPersona() ? tramite.getAdquirienteBean().getPersona().getTipoPersona().getValorEnum() : null;
		if (campo == null || campo.equals(TipoPersona.Juridica.getValorEnum())) {
			campo = tramite.getAdquirienteBean().getPersona().getApellido1RazonSocial();
			if (campo == null) {
				line.append(BasicText.changeSize("", ConstantesPDF.TAM_PRIMER_APELLIDO_RAZON_SOCIAL));
			} else {
				line.append(BasicText.changeSize(campo, ConstantesPDF.TAM_PRIMER_APELLIDO_RAZON_SOCIAL));
			}
		} else {
			campo = tramite.getAdquirienteBean().getPersona().getApellido1RazonSocial();
			if (campo == null) {
				line.append(BasicText.changeSize("", ConstantesPDF.TAM_PRIMER_APELLIDO));
			} else {
				line.append(BasicText.changeSize(campo, ConstantesPDF.TAM_PRIMER_APELLIDO));
			}

			campo = tramite.getAdquirienteBean().getPersona().getApellido2();
			if (campo == null) {
				line.append(BasicText.changeSize("", ConstantesTrafico.TAM_SEGUNDO_APELLIDO_ADQUIRENTE));
			} else {
				line.append(BasicText.changeSize(campo, ConstantesTrafico.TAM_SEGUNDO_APELLIDO_ADQUIRENTE));
			}

			campo = tramite.getAdquirienteBean().getPersona().getNombre();
			if (campo == null) {
				line.append(BasicText.changeSize("", ConstantesTrafico.TAM_NOMBRE_ADQUIRENTE));
			} else {
				line.append(BasicText.changeSize(campo, ConstantesTrafico.TAM_NOMBRE_ADQUIRENTE));
			}
		}

		campo = tramite.getAdquirienteBean().getPersona().getSexo();
		if (campo == null) {
			line.append(BasicText.changeSize("", ConstantesTrafico.TAM_SEXO_ADQUIRENTE));
		} else if (campo.equals(ConstantesPDF.SEXO_HEMBRA)) {
			line.append(BasicText.changeSize(ConstantesPDF.SEXO_MUJER, ConstantesTrafico.TAM_SEXO_ADQUIRENTE));
		} else {
			line.append(BasicText.changeSize(campo, ConstantesTrafico.TAM_SEXO_ADQUIRENTE));
		}

		// ABRE DETALLE DEL ADQUIRIENTE
		// ***************************************************************
		String tipoVia = "";
		try {
			if (tramite.getAdquirienteBean().getPersona().getDireccion().getTipoVia().getIdTipoVia() != null) {
				tipoVia = tramite.getAdquirienteBean().getPersona().getDireccion().getTipoVia().getIdTipoVia();
			}
		} catch (Throwable e) {
			tipoVia = "";
		}

		line.append(BasicText.changeSize(tipoVia, ConstantesTrafico.TAM_TIPO_VIA_ADQUIRIENTE));

		String nombreVia = "";
		if (tramite.getAdquirienteBean().getPersona().getDireccion().getNombreVia() != null) {
			nombreVia = nombreVia + UtilesCadenaCaracteres.quitarCaracteresExtranios(utilesConversiones.ajustarCamposIne(tramite.getAdquirienteBean().getPersona().getDireccion().getNombreVia())) + "";
		}

		nombreVia = nombreVia.replaceAll("\\s\\s", " ");
		nombreVia = nombreVia.replaceAll(",", "");
		nombreVia = nombreVia.replaceAll("º", "");
		nombreVia = nombreVia.replaceAll("ª", "");
		nombreVia = nombreVia.replaceAll("/", "");

		if (nombreVia.length() > ConstantesTrafico.TAM_NOMBRE_VIA_ADQUIRIENTE) {
			nombreVia = nombreVia.substring(0, ConstantesTrafico.TAM_NOMBRE_VIA_ADQUIRIENTE);
		}

		line.append(BasicText.changeSize(nombreVia, ConstantesTrafico.TAM_NOMBRE_VIA_ADQUIRIENTE));

		String numVia = "";
		if (tramite.getAdquirienteBean().getPersona().getDireccion().getNumero() != null) {
			if (!tramite.getAdquirienteBean().getPersona().getDireccion().getNumero().equalsIgnoreCase("SN")
					&& !tramite.getAdquirienteBean().getPersona().getDireccion().getNumero().equalsIgnoreCase("S/N")) {
				numVia = numVia + tramite.getAdquirienteBean().getPersona().getDireccion().getNumero();
			} else {
				numVia = "SN";
			}
		} else {
			numVia = "SN";
		}
		line.append(BasicText.changeSize(numVia, ConstantesTrafico.TAM_NUM_VIA_ADQUIRIENTE));

		campo = tramite.getAdquirienteBean().getPersona().getDireccion().getPuntoKilometrico();
		if (campo == null) {
			line.append(BasicText.changeSize("", ConstantesTrafico.TAM_KILOMETRO_ADQUIRENTE));
		} else {
			line.append(BasicText.changeSize(campo, ConstantesTrafico.TAM_KILOMETRO_ADQUIRENTE));
		}

		campo = tramite.getAdquirienteBean().getPersona().getDireccion().getHm();
		if (campo == null) {
			line.append(BasicText.changeSize("", ConstantesTrafico.TAM_HECTOMETRO_ADQUIRENTE));
		} else {
			line.append(BasicText.changeSize(campo, ConstantesTrafico.TAM_HECTOMETRO_ADQUIRENTE));
		}

		campo = tramite.getAdquirienteBean().getPersona().getDireccion().getBloque();
		if (campo == null) {
			line.append(BasicText.changeSize("", ConstantesTrafico.TAM_BLOQUE_DOMICILIO_ADQUIRENTEE));
		} else {
			line.append(BasicText.changeSize(campo, ConstantesTrafico.TAM_BLOQUE_DOMICILIO_ADQUIRENTEE));
		}
		campo = tramite.getAdquirienteBean().getPersona().getDireccion().getLetra();
		if (campo == null) {
			line.append(BasicText.changeSize("", ConstantesTrafico.TAM_PORTAL_DOMICILIO_ADQUIRENTEE));
		} else {
			line.append(BasicText.changeSize(campo, ConstantesTrafico.TAM_PORTAL_DOMICILIO_ADQUIRENTEE));
		}

		campo = tramite.getAdquirienteBean().getPersona().getDireccion().getEscalera();
		if (campo == null) {
			line.append(BasicText.changeSize("", ConstantesTrafico.TAM_ESCALERA_DOMICILIO_ADQUIRENTE));
		} else {
			line.append(BasicText.changeSize(campo, ConstantesTrafico.TAM_ESCALERA_DOMICILIO_ADQUIRENTE));
		}

		campo = tramite.getAdquirienteBean().getPersona().getDireccion().getPlanta();
		if (campo == null) {
			line.append(BasicText.changeSize("", ConstantesTrafico.TAM_PLANTA_DOMICILIO_ADQUIRENTE));
		} else {
			line.append(BasicText.changeSize(campo, ConstantesTrafico.TAM_PLANTA_DOMICILIO_ADQUIRENTE));
		}

		campo = tramite.getAdquirienteBean().getPersona().getDireccion().getPuerta();
		if (campo == null) {
			line.append(BasicText.changeSize("", ConstantesTrafico.TAM_PUERTA_DOMICILIO_ADQUIRENTE));
		} else {
			line.append(BasicText.changeSize(campo, ConstantesTrafico.TAM_PUERTA_DOMICILIO_ADQUIRENTE));
		}

		campo = tramite.getAdquirienteBean().getPersona().getDireccion().getMunicipio().getProvincia().getIdProvincia();
		if (campo == null) {
			line.append(BasicText.changeSize("", ConstantesTrafico.TAM_PROVINCIA_ADQUIRENTE));
		} else {
			line.append(BasicText.changeSize(campo, ConstantesTrafico.TAM_PROVINCIA_ADQUIRENTE));
		}

		if (tramite.getAdquirienteBean().getPersona().getDireccion().getMunicipio().getIdMunicipio() == null) {
			line.append(BasicText.changeSize("", ConstantesTrafico.TAM_MUNICIPIO_ADQUIRENTE));
		} else {
			campo = campo + tramite.getAdquirienteBean().getPersona().getDireccion().getMunicipio().getIdMunicipio();
			line.append(BasicText.changeSize(campo, ConstantesTrafico.TAM_MUNICIPIO_ADQUIRENTE));
		}

		campo = utilesConversiones.ajustarCamposIne(tramite.getAdquirienteBean().getPersona().getDireccion().getPueblo());
		if (campo == null) {
			line.append(BasicText.changeSize("", ConstantesTrafico.TAM_PUEBLO_ADQUIRENTE));
		} else {
			line.append(BasicText.changeSize(campo, ConstantesTrafico.TAM_PUEBLO_ADQUIRENTE));
		}

		campo = tramite.getAdquirienteBean().getPersona().getDireccion().getCodPostal();
		if (campo == null) {
			line.append(BasicText.changeSize("", ConstantesTrafico.TAM_CP_ADQUIRENTE));
		} else {
			line.append(BasicText.changeSize(campo, ConstantesTrafico.TAM_CP_ADQUIRENTE, '0', BasicText.DETRAS));
		}

		// **********************************************************************
		// CIERRE DETALLE DEL ACQUIRIENTE

		// ABRE DETALLE DEL VEHICULO
		// *******************************************************
		// Si la informacion del vehiculo esta a null es que no ha rellenado la direccion en la pestaña vehiculo.
		// Si se da el caso se cogerá la dirección del adquiriente (trafico lo toma como el titular).
		if (tramite.getTramiteTraficoBean().getVehiculo().getDireccionBean().getIdDireccion() == null || !"TRUE".equalsIgnoreCase(tramite.getConsentimientoCambio())) {
			tipoVia = "";
			try {
				if (tramite.getAdquirienteBean().getPersona().getDireccion().getTipoVia().getIdTipoVia() != null) {
					tipoVia = tramite.getAdquirienteBean().getPersona().getDireccion().getTipoVia().getIdTipoVia();
				}
			} catch (Throwable e) {
				tipoVia = "";
			}

			line.append(BasicText.changeSize(tipoVia, ConstantesTrafico.TAM_TIPO_VIA_ADQUIRIENTE));

			nombreVia = "";
			if (tramite.getAdquirienteBean().getPersona().getDireccion().getNombreVia() != null) {
				nombreVia = nombreVia + UtilesCadenaCaracteres.quitarCaracteresExtranios(utilesConversiones.ajustarCamposIne(tramite.getAdquirienteBean().getPersona().getDireccion().getNombreVia()))
						+ "";
			}

			nombreVia = nombreVia.replaceAll("\\s\\s", " ");
			nombreVia = nombreVia.replaceAll(",", "");
			nombreVia = nombreVia.replaceAll("º", "");
			nombreVia = nombreVia.replaceAll("ª", "");
			nombreVia = nombreVia.replaceAll("/", "");

			if (nombreVia.length() > ConstantesTrafico.TAM_NOMBRE_VIA_ADQUIRIENTE) {
				nombreVia = nombreVia.substring(0, ConstantesTrafico.TAM_NOMBRE_VIA_ADQUIRIENTE);
			}

			line.append(BasicText.changeSize(nombreVia, ConstantesTrafico.TAM_NOMBRE_VIA_ADQUIRIENTE));

			numVia = "";
			if (tramite.getAdquirienteBean().getPersona().getDireccion().getNumero() != null) {
				if (!tramite.getAdquirienteBean().getPersona().getDireccion().getNumero().equalsIgnoreCase("SN")
						&& !tramite.getAdquirienteBean().getPersona().getDireccion().getNumero().equalsIgnoreCase("S/N")) {
					numVia = numVia + tramite.getAdquirienteBean().getPersona().getDireccion().getNumero();
				} else {
					numVia = "SN";
				}
			} else {
				numVia = "SN";
			}
			line.append(BasicText.changeSize(numVia, ConstantesTrafico.TAM_NUM_VIA_ADQUIRIENTE));

			campo = tramite.getAdquirienteBean().getPersona().getDireccion().getPuntoKilometrico();
			if (campo == null) {
				line.append(BasicText.changeSize("", ConstantesTrafico.TAM_KILOMETRO_ADQUIRENTE));
			} else {
				line.append(BasicText.changeSize(campo, ConstantesTrafico.TAM_KILOMETRO_ADQUIRENTE));
			}

			campo = tramite.getAdquirienteBean().getPersona().getDireccion().getHm();
			if (campo == null) {
				line.append(BasicText.changeSize("", ConstantesTrafico.TAM_HECTOMETRO_ADQUIRENTE));
			} else {
				line.append(BasicText.changeSize(campo, ConstantesTrafico.TAM_HECTOMETRO_ADQUIRENTE));
			}

			campo = tramite.getAdquirienteBean().getPersona().getDireccion().getBloque();
			if (campo == null) {
				line.append(BasicText.changeSize("", ConstantesTrafico.TAM_BLOQUE_DOMICILIO_ADQUIRENTEE));
			} else {
				line.append(BasicText.changeSize(campo, ConstantesTrafico.TAM_BLOQUE_DOMICILIO_ADQUIRENTEE));
			}
			campo = tramite.getAdquirienteBean().getPersona().getDireccion().getLetra();
			if (campo == null) {
				line.append(BasicText.changeSize("", ConstantesTrafico.TAM_PORTAL_DOMICILIO_ADQUIRENTEE));
			} else {
				line.append(BasicText.changeSize(campo, ConstantesTrafico.TAM_PORTAL_DOMICILIO_ADQUIRENTEE));
			}

			campo = tramite.getAdquirienteBean().getPersona().getDireccion().getEscalera();
			if (campo == null) {
				line.append(BasicText.changeSize("", ConstantesTrafico.TAM_ESCALERA_DOMICILIO_ADQUIRENTE));
			} else {
				line.append(BasicText.changeSize(campo, ConstantesTrafico.TAM_ESCALERA_DOMICILIO_ADQUIRENTE));
			}

			campo = tramite.getAdquirienteBean().getPersona().getDireccion().getPlanta();
			if (campo == null) {
				line.append(BasicText.changeSize("", ConstantesTrafico.TAM_PLANTA_DOMICILIO_ADQUIRENTE));
			} else {
				line.append(BasicText.changeSize(campo, ConstantesTrafico.TAM_PLANTA_DOMICILIO_ADQUIRENTE));
			}

			campo = tramite.getAdquirienteBean().getPersona().getDireccion().getPuerta();
			if (campo == null) {
				line.append(BasicText.changeSize("", ConstantesTrafico.TAM_PUERTA_DOMICILIO_ADQUIRENTE));
			} else {
				line.append(BasicText.changeSize(campo, ConstantesTrafico.TAM_PUERTA_DOMICILIO_ADQUIRENTE));
			}

			campo = tramite.getAdquirienteBean().getPersona().getDireccion().getMunicipio().getProvincia().getIdProvincia();
			if (campo == null) {
				line.append(BasicText.changeSize("", ConstantesTrafico.TAM_PROVINCIA_ADQUIRENTE));
			} else {
				line.append(BasicText.changeSize(campo, ConstantesTrafico.TAM_PROVINCIA_ADQUIRENTE));
			}

			if (tramite.getAdquirienteBean().getPersona().getDireccion().getMunicipio().getIdMunicipio() == null) {
				line.append(BasicText.changeSize("", ConstantesTrafico.TAM_MUNICIPIO_ADQUIRENTE));
			} else {
				campo = campo + tramite.getAdquirienteBean().getPersona().getDireccion().getMunicipio().getIdMunicipio();
				line.append(BasicText.changeSize(campo, ConstantesTrafico.TAM_MUNICIPIO_ADQUIRENTE));
			}

			campo = utilesConversiones.ajustarCamposIne(tramite.getAdquirienteBean().getPersona().getDireccion().getPueblo());
			if (campo == null) {
				line.append(BasicText.changeSize("", ConstantesTrafico.TAM_PUEBLO_ADQUIRENTE));
			} else {
				line.append(BasicText.changeSize(campo, ConstantesTrafico.TAM_PUEBLO_ADQUIRENTE));
			}

			campo = tramite.getAdquirienteBean().getPersona().getDireccion().getCodPostal();
			if (campo == null) {
				line.append(BasicText.changeSize("", ConstantesTrafico.TAM_CP_ADQUIRENTE));
			} else {
				line.append(BasicText.changeSize(campo, ConstantesTrafico.TAM_CP_ADQUIRENTE, '0', BasicText.DETRAS));
			}
		} else {
			String tipoViaVehiculo = "";
			try {
				if (tramite.getTramiteTraficoBean().getVehiculo().getDireccionBean().getTipoVia().getIdTipoVia() != null) {
					tipoViaVehiculo = tramite.getTramiteTraficoBean().getVehiculo().getDireccionBean().getTipoVia().getIdTipoVia();
				}
			} catch (Throwable e) {
				tipoViaVehiculo = "";
			}
			line.append(BasicText.changeSize(tipoViaVehiculo, ConstantesTrafico.TAM_TIPO_VIA_VEHICULO));

			String nombreViaVehiculo = "";
			if (tramite.getTramiteTraficoBean().getVehiculo().getDireccionBean().getNombreVia() != null) {
				nombreViaVehiculo = nombreViaVehiculo
						+ UtilesCadenaCaracteres.quitarCaracteresExtranios(utilesConversiones.ajustarCamposIne(tramite.getTramiteTraficoBean().getVehiculo().getDireccionBean().getNombreVia()));

				nombreViaVehiculo = nombreViaVehiculo.replaceAll("\\s\\s", " ");
				nombreViaVehiculo = nombreViaVehiculo.replaceAll(",", "");
				nombreViaVehiculo = nombreViaVehiculo.replaceAll("º", "");
				nombreViaVehiculo = nombreViaVehiculo.replaceAll("ª", "");
				nombreViaVehiculo = nombreViaVehiculo.replaceAll("/", "");

				if (nombreViaVehiculo.length() > ConstantesTrafico.TAM_VIA_VEHICULO) {
					nombreViaVehiculo = nombreViaVehiculo.substring(0, ConstantesTrafico.TAM_VIA_VEHICULO);
				}

			}
			line.append(BasicText.changeSize(nombreViaVehiculo, ConstantesTrafico.TAM_VIA_VEHICULO));

			String numViaVehiculo = "";
			if (tramite.getTramiteTraficoBean().getVehiculo().getDireccionBean().getNumero() != null) {
				if (!tramite.getTramiteTraficoBean().getVehiculo().getDireccionBean().getNumero().equalsIgnoreCase("SN")
						&& !tramite.getTramiteTraficoBean().getVehiculo().getDireccionBean().getNumero().equalsIgnoreCase("S/N")) {
					numViaVehiculo = numViaVehiculo + tramite.getTramiteTraficoBean().getVehiculo().getDireccionBean().getNumero();
				} else {
					numViaVehiculo = "SN";
				}
			} else {
				numViaVehiculo = "SN";
			}
			line.append(BasicText.changeSize(numViaVehiculo, ConstantesTrafico.TAM_NUM_VIA_ADQUIRIENTE));

			campo = tramite.getTramiteTraficoBean().getVehiculo().getDireccionBean().getPuntoKilometrico();
			if (campo == null) {
				line.append(BasicText.changeSize("", ConstantesTrafico.TAM_KM_VEHICULO));
			} else {
				campo = utiles.cambiaFormatoMatricula(campo);
				line.append(BasicText.changeSize(campo, ConstantesTrafico.TAM_KM_VEHICULO));
			}

			campo = tramite.getTramiteTraficoBean().getVehiculo().getDireccionBean().getHm();
			if (campo == null) {
				line.append(BasicText.changeSize("", ConstantesTrafico.TAM_HM_VEHICULO));
			} else {
				campo = utiles.cambiaFormatoMatricula(campo);
				line.append(BasicText.changeSize(campo, ConstantesTrafico.TAM_HM_VEHICULO));
			}

			campo = tramite.getTramiteTraficoBean().getVehiculo().getDireccionBean().getBloque();
			if (campo == null) {
				line.append(BasicText.changeSize("", ConstantesTrafico.TAM_BLOQUE_DOMICILIO_VEHICULO));
			} else {
				campo = utiles.cambiaFormatoMatricula(campo);
				line.append(BasicText.changeSize(campo, ConstantesTrafico.TAM_BLOQUE_DOMICILIO_VEHICULO));
			}
			campo = tramite.getTramiteTraficoBean().getVehiculo().getDireccionBean().getLetra();
			if (campo == null) {
				line.append(BasicText.changeSize("", ConstantesTrafico.TAM_PORTAL_DOMICILIO_VEHICULO));
			} else {
				campo = utiles.cambiaFormatoMatricula(campo);
				line.append(BasicText.changeSize(campo, ConstantesTrafico.TAM_PORTAL_DOMICILIO_VEHICULO));
			}

			campo = tramite.getTramiteTraficoBean().getVehiculo().getDireccionBean().getEscalera();
			if (campo == null) {
				line.append(BasicText.changeSize("", ConstantesTrafico.TAM_ESCALERA_DOMICILIO_VEHICULO));
			} else {
				campo = utiles.cambiaFormatoMatricula(campo);
				line.append(BasicText.changeSize(campo, ConstantesTrafico.TAM_ESCALERA_DOMICILIO_VEHICULO));
			}

			campo = tramite.getTramiteTraficoBean().getVehiculo().getDireccionBean().getPlanta();
			if (campo == null) {
				line.append(BasicText.changeSize("", ConstantesTrafico.TAM_PLANTA_DOMICILIO_VEHICULO));
			} else {
				campo = utiles.cambiaFormatoMatricula(campo);
				line.append(BasicText.changeSize(campo, ConstantesTrafico.TAM_PLANTA_DOMICILIO_VEHICULO));
			}

			campo = tramite.getTramiteTraficoBean().getVehiculo().getDireccionBean().getPuerta();
			if (campo == null) {
				line.append(BasicText.changeSize("", ConstantesTrafico.TAM_PUERTA_DOMICILIO_VEHICULO));
			} else {
				campo = utiles.cambiaFormatoMatricula(campo);
				line.append(BasicText.changeSize(campo, ConstantesTrafico.TAM_PUERTA_DOMICILIO_VEHICULO));
			}
			campo = tramite.getTramiteTraficoBean().getVehiculo().getDireccionBean().getMunicipio().getProvincia().getIdProvincia();
			if (campo == null) {
				line.append(BasicText.changeSize("", ConstantesTrafico.TAM_PROVINCIA_VEHICULO));
			} else {
				campo = utiles.cambiaFormatoMatricula(campo);
				line.append(BasicText.changeSize(campo, ConstantesTrafico.TAM_PROVINCIA_VEHICULO));
			}

			if (tramite.getTramiteTraficoBean().getVehiculo().getDireccionBean().getMunicipio().getIdMunicipio() == null) {
				line.append(BasicText.changeSize("", ConstantesTrafico.TAM_MUNICIPIO_VEHICULO));
			} else {
				campo = campo + tramite.getTramiteTraficoBean().getVehiculo().getDireccionBean().getMunicipio().getIdMunicipio();
				line.append(BasicText.changeSize(campo, ConstantesTrafico.TAM_MUNICIPIO_VEHICULO));
			}

			campo = tramite.getTramiteTraficoBean().getVehiculo().getDireccionBean().getPueblo();
			if (campo == null) {
				line.append(BasicText.changeSize("", ConstantesTrafico.TAM_PUEBLO_VEHICULO));
			} else {
				campo = utiles.cambiaFormatoMatricula(campo);
				line.append(BasicText.changeSize(campo, ConstantesTrafico.TAM_PUEBLO_VEHICULO));
			}

			campo = tramite.getTramiteTraficoBean().getVehiculo().getDireccionBean().getCodPostal();
			if (campo == null) {
				line.append(BasicText.changeSize("", ConstantesTrafico.TAM_CP_VEHICULO));
			} else {
				campo = utiles.cambiaFormatoMatricula(campo);
				line.append(BasicText.changeSize(campo, ConstantesTrafico.TAM_CP_VEHICULO));
			}
		}
		// *******************************************************
		// CIERRE DETALLE DEL VEHICULO

		try {
			if (tramite.getTramiteTraficoBean().getVehiculo().getFechaItv() != null && !tramite.getTramiteTraficoBean().getVehiculo().getFechaItv().isfechaNula()) {
				campo = formato.format(tramite.getTramiteTraficoBean().getVehiculo().getFechaItv().getTimestamp());
			} else {
				campo = null;
			}
		} catch (ParseException e) {
			campo = null;
		}
		if (campo == null) {
			line.append(BasicText.changeSize("EXENTO", ConstantesTrafico.TAM_FECHA_ITV));
		} else {
			line.append(BasicText.changeSize(campo, ConstantesTrafico.TAM_FECHA_ITV, '0', false));
		}

		if (nuevaVersion.equals(ConstantesPDF.VALOR_SI)) {
			campo = tramite.getTramiteTraficoBean().getVehiculo().getMotivoItv().getIdMotivo();
		} else {
			campo = tramite.getTramiteTraficoBean().getVehiculo().getMotivoItv().getDescripcion();
		}

		if (campo == null) {
			line.append(BasicText.changeSize("", ConstantesTrafico.TAM_MOTIVO_ITV));
		} else {
			line.append(BasicText.changeSize(campo, ConstantesTrafico.TAM_MOTIVO_ITV));
		}
		campo = MapeoTipoTransferencia.valorEnumTipoTransferenciaNotEnt(tramite.getTipoTransferencia().getValorEnum());
		if (campo == null) {
			line.append(BasicText.changeSize("", ConstantesTrafico.TAM_TIPO_TRANSFERENCIA));
		} else {
			if (campo.equals("1") && null != tramite.getImpresionPermiso() && !tramite.getImpresionPermiso().equals("true")) {
				campo = "N";
			}
			line.append(BasicText.changeSize(campo, ConstantesTrafico.TAM_TIPO_TRANSFERENCIA));
		}
		campo = tramite.getRepresentanteAdquirienteBean().getConceptoRepre() != null ? tramite.getRepresentanteAdquirienteBean().getConceptoRepre().getValorEnum() : null;
		if (campo != null && ConceptoTutela.Tutela.getValorEnum().equals(campo)) {
			line.append(BasicText.changeSize(ConstantesPDF.VALOR_TUTELA, ConstantesPDF.TAM_TUTELA));
		} else {
			line.append(BasicText.changeSize("", ConstantesPDF.TAM_TUTELA));
		}

		if(nuevaVersion.equals(ConstantesPDF.VALOR_SI)){
			campo = tramite.getTramiteTraficoBean().getIedtm() != null && tramite.getTramiteTraficoBean().getExentoIedtm().equals("true") ? ConstantesPDF.VALOR_E : null;
		} else {
			campo = tramite.getTramiteTraficoBean().getIedtm() != null && tramite.getTramiteTraficoBean().getIedtm().equals("true") ? ConstantesPDF.VALOR_S : null;
		}

		if (campo == null) {
			line.append(BasicText.changeSize("", ConstantesTrafico.TAM_TIPO_LIMITACION));
		} else {
			line.append(BasicText.changeSize(campo, ConstantesTrafico.TAM_TIPO_LIMITACION));
		}

		try {
			if (tramite.getTramiteTraficoBean().getFechaIedtm() != null && !tramite.getTramiteTraficoBean().getFechaIedtm().isfechaNula()) {
				campo = formato.format(tramite.getTramiteTraficoBean().getFechaIedtm().getTimestamp());
			} else {
				campo = null;
			}
		} catch (ParseException e) {
			campo = null;
		}
		if (campo == null) {
			line.append(BasicText.changeSize("", ConstantesTrafico.TAM_FECHA_LIMITACION));
		} else {
			line.append(BasicText.changeSize(campo, ConstantesTrafico.TAM_FECHA_LIMITACION, '0', false));
		}

		campo = null != tramite.getTramiteTraficoBean().getNumRegIedtm() && !"-1".equals(tramite.getTramiteTraficoBean().getNumRegIedtm().getValorEnum()) ? tramite.getTramiteTraficoBean()
				.getNumRegIedtm().getValorEnum() : null;

		if(nuevaVersion.equals(ConstantesPDF.VALOR_SI)){
			line.append(BasicText.changeSize("", ConstantesPDF.TAM_NUM_REG));
		}else {
			if (campo == null) {
				line.append(BasicText.changeSize("", ConstantesPDF.TAM_NUM_REG));
			} else {
				line.append(BasicText.changeSize(campo, ConstantesPDF.TAM_NUM_REG));
			}
		}

		campo = tramite.getTramiteTraficoBean().getFinancieraIedtm();
		if (campo == null) {
			line.append(BasicText.changeSize("", ConstantesTrafico.TAM_FINANCIERA_LIMITACION));
		} else {
			line.append(BasicText.changeSize(campo, ConstantesTrafico.TAM_FINANCIERA_LIMITACION));
		}

		campo = tramite.getCetItp();
		if (campo == null) {
			line.append(ConstantesTrafico.VALOR_CET_PREDETERMINADO);
		} else {
			line.append(BasicText.changeSize(campo, ConstantesTrafico.TAM_CET));
		}

		// Nuevo campo solicitado por la DGT, CEMA para Vehículos agrícolas
		campo = tramite.getTramiteTraficoBean().getCema();
		if (nuevaVersion.equals(ConstantesPDF.VALOR_SI)) {
			if (campo == null || campo.length() == 0) {
				//line.append(ConstantesTrafico.VALOR_CET_PREDETERMINADO);
				line.append(BasicText.changeSize("", ConstantesTrafico.TAM_CEM));
			} else {
				line.append(BasicText.changeSize(campo, ConstantesTrafico.TAM_CEM));
			}
		} else {
			if (campo == null || campo.length() == 0) {
				line.append(ConstantesTrafico.VALOR_CET_PREDETERMINADO);
			} else {
				line.append(BasicText.changeSize(campo, ConstantesTrafico.TAM_CEM));
			}
		}

		// Si el CET es nulo, vacío o está informado con ceros, no es necesario informar la provincia del CET
		if (tramite.getCetItp() == null || "".equals(tramite.getCetItp()) || "00000000".equals(tramite.getCetItp())) {
			line.append(BasicText.changeSize("", ConstantesTrafico.TAM_PROVINCIA_CET));
			// Si el CET no es nulo y coincide con la provincia del contrato del colegiado, no es necesario que informemos la provincia del CET
		} else if (tramite.getProvinciaCet() != null && tramite.getProvinciaCet().getIdProvincia() != null && tramite.getProvinciaCet().getIdProvincia().equals(contrato.getProvincia().getIdProvincia())) {
			line.append(BasicText.changeSize("", ConstantesTrafico.TAM_PROVINCIA_CET));
			// Si el CET no es nulo, pero no coincide con la provincia del colegiado, informamos la provincia del CET
		} else if (tramite.getProvinciaCet() != null && tramite.getProvinciaCet().getIdProvincia() != null && !tramite.getProvinciaCet().getIdProvincia().equals(contrato.getProvincia().getIdProvincia())
				&& !tramite.getProvinciaCet().getIdProvincia().equals("-1")) {

			campo = tramite.getProvinciaCet().getIdProvincia();
			String provincia = tramite.getProvinciaCet().getIdProvincia();
			provincia = utilesConversiones.getSiglasFromIdProvincia(provincia);
			line.append(BasicText.changeSize(provincia, ConstantesTrafico.TAM_PROVINCIA_CET));
		} else {
			line.append(BasicText.changeSize("", ConstantesTrafico.TAM_PROVINCIA_CET));
		}

		return line.toString();
	}

	/*public void procesarGESTErrorOk(ArrayList<CampoPdfBean> camposFormateados, String mensaje, String valorGEST) {
		CampoPdfBean campo = null;
		campo = new CampoPdfBean(ConstantesPDF.ID_GEST, valorGEST, true, false, 12);
		camposFormateados.add(campo);
		campo = new CampoPdfBean(ConstantesPDF.ID_ERROR_GEST, mensaje, false, false, 10);
		camposFormateados.add(campo);
	}

	public void procesarGESTRevisar(ArrayList<CampoPdfBean> camposFormateados, GestBean bean, String valorGEST, PdfMaker pdf) {
		boolean ivtm = false;
		CampoPdfBean campo = null;
		campo = new CampoPdfBean(ConstantesPDF.ID_GEST, valorGEST, true, false, 12);
		camposFormateados.add(campo);

		int lCargas = 0;
		int lEmbargos = 0;
		if (bean.getCargas() != null && bean.getCargas().size() > 0) {
			lCargas = bean.getCargas().size();
		}
		if (bean.getEmbargos() != null && bean.getEmbargos().size() > 0) {
			lEmbargos = bean.getEmbargos().size();
		}

		float separacionLineas = -40;
		float tamFuente = 7;
		if (lCargas > 7 || lEmbargos > 7 || (lCargas >= 1 && lEmbargos >= 1 && lCargas + lEmbargos > 4)) {
			separacionLineas = -30;
			tamFuente = 5;
		} else if (lCargas > 3 || lEmbargos > 3 || (lCargas >= 1 && lEmbargos >= 1 && lCargas + lEmbargos > 2)) {
			separacionLineas = -32;
			tamFuente = 6;
		}

		procesaCargas(bean, lCargas, separacionLineas, tamFuente, pdf, ivtm, valorGEST);
		procesarEmbargos(bean, lCargas, separacionLineas, tamFuente, pdf, ivtm, valorGEST);
	}*/

	public byte[] etiquetasPlantilla(Set<String> camposPlantilla, PdfMaker pdf, byte[] byte1, String tipoImpreso, String campoNube, String campoNube2) {
		if (tipoImpreso.equals(TipoImpreso.MatriculacionBorradorPDF417.toString())) {
			// Formateamos el resto de las etiquetas de la plantilla, nubes y codigos de barras.
			pdf.establecerFuente(ConstantesPDF.HELVETICA, true, true, ConstantesPDF._26);
			byte1 = pdf.setCampo(byte1, campoNube, "BORRADOR");
			if (camposPlantilla.contains(campoNube2)) {
				byte1 = pdf.setCampo(byte1, campoNube2, "BORRADOR");
			}
			pdf.establecerFuente(ConstantesPDF.HELVETICA, false, false, ConstantesPDF._13);
		}
		return byte1;
	}

	public void procesaCargas(GestBean bean, int lCargas, float separacionLineas, float tamFuente, PdfMaker pdf, boolean ivtm, String valorGEST) {
		if (bean.getCargas() != null && !bean.getCargas().isEmpty()) {
			/*
			 * Hacemos la comprobación si el servicio de la DGT, informa de pendiente de pago del IVTM. para imprimirlo en el informe.
			 */
			List<CargaGestBean> listaCargas = bean.getCargas();
			CargaGestBean cargas = listaCargas.get(0);
			if (cargas.getTipo().indexOf("I.V.T.M") > 0) {
				separacionLineas = -38;
				tamFuente = 7;
				StringBuffer buffer1 = new StringBuffer();
				pdf.establecerFuente(ConstantesPDF.COURIER, false, false, tamFuente);
				buffer1.append(cargas.getTipo());
				setByte1(pdf.escribirTextoColumna(getByte1(), buffer1.toString().replace('#', 'Ñ'), 1, 70, 172, 600, 10, separacionLineas));
				ivtm = true;
				separacionLineas = -40;
				tamFuente = 6;
			} else {
				// Sumo las dos líneas que se añaden
				lCargas += 2;

				StringBuffer buffer1 = new StringBuffer();
				StringBuffer buffer2 = new StringBuffer();
				StringBuffer buffer3 = new StringBuffer();
				StringBuffer buffer4 = new StringBuffer();

				buffer1.append(ConstantesPDF.VALOR_TIPO_GEST + "\n").append(ConstantesPDF.VALOR_LIN1_GEST + "\n");
				buffer2.append(ConstantesPDF.VALOR_REG_GEST + "\n").append(ConstantesPDF.VALOR_LIN2_GEST + "\n");
				buffer3.append(ConstantesPDF.VALOR_FECHA_GEST + "\n").append(ConstantesPDF.VALOR_LIN3_GEST + "\n");
				buffer4.append(ConstantesPDF.VALOR_FINAN_GEST + "\n").append(ConstantesPDF.VALOR_LIN4_GEST + "\n");
				pdf.establecerFuente(ConstantesPDF.COURIER, true, false, tamFuente);

				setByte1(pdf.escribirTextoColumna(getByte1(), buffer1.toString(), 1, 70, 172, 600, 10, separacionLineas));
				setByte1(pdf.escribirTextoColumna(getByte1(), buffer2.toString(), 1, 120, 172, 600, 10, separacionLineas));
				setByte1(pdf.escribirTextoColumna(getByte1(), buffer3.toString(), 1, 180, 172, 600, 10, separacionLineas));
				setByte1(pdf.escribirTextoColumna(getByte1(), buffer4.toString(), 1, 235, 172, 600, 10, separacionLineas));

				int i = 1;
				for (CargaGestBean carga : bean.getCargas()) {
					String tipo = carga.getTipo();
					String numReg = carga.getNumRegistro();

					buffer1 = new StringBuffer();
					buffer2 = new StringBuffer();
					buffer3 = new StringBuffer();
					buffer4 = new StringBuffer();

					if (tipo != null) {
						buffer1.append(tipo);
						if (tipo.equalsIgnoreCase("RESERVA") || tipo.equalsIgnoreCase("LEASING") || (tipo.equalsIgnoreCase("RENTING") && "REGIS.B.M.".equalsIgnoreCase(numReg))) {
							valorGEST = "ERROR";
						}
					}
					buffer1.append("\n");
					if (carga.getNumRegistro() != null) {
						buffer2.append(carga.getNumRegistro());
					}
					buffer2.append("\n");
					if (carga.getFecha() != null) {
						buffer3.append(carga.getFecha());
					}
					buffer3.append("\n");
					if (carga.getFinancieraYDomicilio() != null) {
						buffer4.append(carga.getFinancieraYDomicilio());
					}
					buffer4.append("\n");
					pdf.establecerFuente(ConstantesPDF.COURIER, false, false, tamFuente);
					setByte1(pdf.escribirTextoColumna(getByte1(), buffer1.toString(), 1, 70, 172, 600, 10, separacionLineas + i * 10));
					setByte1(pdf.escribirTextoColumna(getByte1(), buffer2.toString(), 1, 120, 172, 600, 10, separacionLineas + i * 10));
					setByte1(pdf.escribirTextoColumna(getByte1(), buffer3.toString(), 1, 180, 172, 600, 10, separacionLineas + i * 10));
					setByte1(pdf.escribirTextoColumna(getByte1(), buffer4.toString(), 1, 235, 172, 600, 10, separacionLineas + i * 10));
					i++;
				}
			}
		}
	}

	public void procesarEmbargos(GestBean bean, int lCargas, float separacionLineas, float tamFuente, PdfMaker pdf, boolean ivtm, String valorGEST) {
		if (bean.getEmbargos() != null && !bean.getEmbargos().isEmpty()) {
			// Añado una línea de espacio entre las limitaciones y los embargos
			if (lCargas != 0) {
				lCargas++;
			}

			// Controlamos si tb hay vargas por IVTM.
			if (ivtm) {
				separacionLineas = -30;
			}
			separacionLineas = separacionLineas + 20;

			StringBuffer buffer1 = new StringBuffer();
			StringBuffer buffer2 = new StringBuffer();
			StringBuffer buffer3 = new StringBuffer();
			StringBuffer buffer4 = new StringBuffer();
			StringBuffer buffer5 = new StringBuffer();

			buffer1.append(ConstantesPDF.VALOR_CONCEPTO_GEST + "\n").append(ConstantesPDF.VALOR_LIN1_GEST + "\n");
			buffer2.append(ConstantesPDF.VALOR_EXPED_GEST + "\n").append(ConstantesPDF.VALOR_LIN2_GEST + "\n");
			buffer3.append(ConstantesPDF.VALOR_FEMB_GEST + "\n").append(ConstantesPDF.VALOR_LIN3_GEST + "\n");
			buffer4.append(ConstantesPDF.VALOR_FMATERI_GEST + "\n").append(ConstantesPDF.VALOR_LIN3_GEST + "\n");
			buffer5.append(ConstantesPDF.VALOR_AUTORIDAD_GEST + "\n").append(ConstantesPDF.VALOR_LIN4_GEST + "\n");
			pdf.establecerFuente(ConstantesPDF.COURIER, true, false, tamFuente);

			setByte1(pdf.escribirTextoColumna(getByte1(), buffer1.toString(), 1, 70, 172, 600, 10, separacionLineas));
			setByte1(pdf.escribirTextoColumna(getByte1(), buffer2.toString(), 1, 120, 172, 600, 10, separacionLineas));
			setByte1(pdf.escribirTextoColumna(getByte1(), buffer3.toString(), 1, 180, 172, 600, 10, separacionLineas));
			setByte1(pdf.escribirTextoColumna(getByte1(), buffer4.toString(), 1, 235, 172, 600, 10, separacionLineas));
			setByte1(pdf.escribirTextoColumna(getByte1(), buffer5.toString(), 1, 315, 172, 600, 10, separacionLineas));

			int i = 1;
			for (EmbargoGestBean embargo : bean.getEmbargos()) {
				String concepto = embargo.getConcepto();
				buffer1 = new StringBuffer();
				buffer2 = new StringBuffer();
				buffer3 = new StringBuffer();
				buffer4 = new StringBuffer();
				buffer5 = new StringBuffer();

				if (concepto != null) {
					buffer1.append(concepto);
					if (concepto.equalsIgnoreCase("RESERVA") || concepto.equalsIgnoreCase("LEASING")) {
						valorGEST = "ERROR";
					}
				}
				buffer1.append("\n");

				if (embargo.getExpediente() != null) {
					buffer2.append(embargo.getExpediente());
				}
				buffer2.append("\n");
				if (embargo.getFecha() != null) {
					buffer3.append(embargo.getFecha());
				}
				buffer3.append("\n");
				if (embargo.getFmateri() != null) {
					buffer4.append(embargo.getFmateri());
				}
				buffer4.append("\n");
				if (embargo.getAutoridad() != null) {
					buffer5.append(embargo.getAutoridad());
				}
				buffer5.append("\n");

				pdf.establecerFuente(ConstantesPDF.COURIER, false, false, tamFuente);
				setByte1(pdf.escribirTextoColumna(getByte1(), buffer1.toString(), 1, 70, 172, 600, 10, separacionLineas + i * 10));
				setByte1(pdf.escribirTextoColumna(getByte1(), buffer2.toString(), 1, 120, 172, 600, 10, separacionLineas + i * 10));
				setByte1(pdf.escribirTextoColumna(getByte1(), buffer3.toString(), 1, 180, 172, 600, 10, separacionLineas + i * 10));
				setByte1(pdf.escribirTextoColumna(getByte1(), buffer4.toString(), 1, 235, 172, 600, 10, separacionLineas + i * 10));
				setByte1(pdf.escribirTextoColumna(getByte1(), buffer5.toString(), 1, 315, 172, 600, 10, separacionLineas + i * 10));
				i++;
			}

		}
	}

	public ResultBean finalizarGest(PdfMaker pdf, GestBean bean, String mensaje, boolean error, Long idContrato, BigDecimal idUsuario) {
		ResultBean resultado = new ResultBean();
		if (bean != null
				&& bean.getError().booleanValue()
				&& (bean.getMensaje().equals("Error en la invocación") || bean.getMensaje().equals("Se ha producido un error en la invocación al servicio de la D.G.T.") || bean.getMensaje().equals(
						"Error en los parámetros."))) {
			etiquetas(pdf);
			resultado.setMensaje(bean.getMensaje());
			resultado.setError(true);
		} else if (bean != null && bean.getError().booleanValue()) {
			etiquetas(pdf);
			descontarCreditosTransmision(mensaje, error, idContrato, idUsuario);
		} else {
			descontarCreditosTransmision(mensaje, error, idContrato, idUsuario);
		}
		return resultado;
	}

	public void descontarCreditosTransmision(String mensaje, boolean error, Long idContrato, BigDecimal idUsuario) {
		HashMap<String, Object> resultado = getModeloCreditosTrafico().descontarCreditos(idContrato.toString(), utiles.convertirIntegerABigDecimal(1), "T6", idUsuario);
		ResultBean resultadoProcedimiento = (ResultBean) resultado.get(ConstantesPQ.RESULTBEAN);
		if (resultadoProcedimiento.getError()) {
			mensaje = "Error al descontar créditos de la operación";
			error = true;
		}
	}

	public void etiquetas(PdfMaker pdf) {
		pdf.establecerFuente(ConstantesPDF.HELVETICA, true, false, 40);
		setByte1(pdf.escribirTextoRotado("ERROR VALIDACIÓN DGT", getByte1(), 1, 100, 300, ConstantesPDF.ANGULO, BaseColor.GRAY));
		pdf.establecerFuente(ConstantesPDF.HELVETICA, false, false, 13);
	}

	public int calcularNumPags(int numExpedientes) {
		if (numExpedientes % ConstantesPDF._25 == 0) {
			return numExpedientes / ConstantesPDF._25;
		} else {
			return (numExpedientes / ConstantesPDF._25) + ConstantesPDF._1;
		}
	}

	public byte[] insertarFirmasColegiadoYColegio(TramiteTraficoBean detalleTramite, IntervinienteTrafico detalleTitular, byte[] byte1, int[] vectPags, PdfMaker pdf, ContratoVO contrato)
			throws OegamExcepcion {
		String fechaDeHoy = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
		ModeloColegiado modeloColegiado = new ModeloColegiado();

		Persona colegiado = modeloColegiado.obtenerColegiadoCompleto(detalleTramite.getIdContrato() != null ? detalleTramite.getIdContrato() : new BigDecimal(contrato.getIdContrato()));

		BeanPQDatosColegio beanPQDatosColegio = new BeanPQDatosColegio();
		beanPQDatosColegio.setP_COLEGIO(contrato.getColegio().getColegio());
		HashMap<String, Object> datosColegio = getModeloTrafico().obtenerDatosColegio(beanPQDatosColegio);

		String firmaGestor = (detalleTramite.getVehiculo().getBastidor() != null && !detalleTramite.getVehiculo().getBastidor().equals("") ? detalleTramite.getVehiculo().getBastidor() : "") + "#"
				+ colegiado.getNif() + "#" + fechaDeHoy;
		// Ciframos la firma, convertimos a hexadecimal y restringimos a los
		// primeros _16 caracteres.
		firmaGestor = utiles.cifrarHMACSHA1(firmaGestor, CLAVE_CIFRADO);
		firmaGestor = utiles.stringToHex(firmaGestor);
		firmaGestor = firmaGestor.substring(0, ConstantesPDF._16);
		byte1 = insertarBarcodeFirma(ConstantesPDF.ID_FIRMA_GESTOR, byte1, vectPags, firmaGestor, pdf);

		// Insertamos Firma del Colegio:
		String firmaColegio = contrato.getCif() + "#"
				+ (detalleTramite.getVehiculo().getBastidor() != null && !detalleTramite.getVehiculo().getBastidor().equals("") ? detalleTramite.getVehiculo().getBastidor() : "") + "#"
				+ detalleTitular.getPersona().getNif() + "#" + colegiado.getNif() + "#" + ((ColegioBean) datosColegio.get(ConstantesPQ.BEANPANTALLA)).getCif() + "#" + fechaDeHoy;
		// Ciframos la firma, convertimos a hexadecimal y restringimos a los
		// primeros _16 caracteres.
		firmaColegio = utiles.cifrarHMACSHA1(firmaColegio, CLAVE_CIFRADO);
		firmaColegio = utiles.stringToHex(firmaColegio);
		firmaColegio = firmaColegio.substring(0, ConstantesPDF._16);
		byte1 = insertarBarcodeFirma(ConstantesPDF.ID_FIRMA_COLEGIO, byte1, vectPags, firmaColegio, pdf);

		return byte1;
	}

	public byte[] insertarFirmasColegiadoYColegioBaja(TramiteTrafDto detalleTramite, IntervinienteTraficoDto detalleTitular, byte[] byte1, int[] vectPags, PdfMaker pdf, ContratoVO contratoVO)
			throws OegamExcepcion {
		String fechaDeHoy = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());

		ServicioPersona servicioPersona = ContextoSpring.getInstance().getBean(ServicioPersona.class);
		PersonaDto colegiado = servicioPersona.obtenerColegiadoCompleto(detalleTramite.getNumColegiado(), detalleTramite.getIdContrato());

		String firmaGestor = (detalleTramite.getVehiculoDto().getBastidor() != null && !detalleTramite.getVehiculoDto().getBastidor().equals("") ? detalleTramite.getVehiculoDto().getBastidor() : "")
				+ "#" + colegiado.getNif() + "#" + fechaDeHoy;
		// Ciframos la firma, convertimos a hexadecimal y restringimos a los
		// primeros _16 caracteres.
		firmaGestor = utiles.cifrarHMACSHA1(firmaGestor, CLAVE_CIFRADO);
		firmaGestor = utiles.stringToHex(firmaGestor);
		firmaGestor = firmaGestor.substring(0, ConstantesPDF._16);
		byte1 = insertarBarcodeFirma(ConstantesPDF.ID_FIRMA_GESTOR, byte1, vectPags, firmaGestor, pdf);

		// Insertamos Firma del Colegio:
		String firmaColegio = contratoVO.getCif() + "#"
				+ (detalleTramite.getVehiculoDto().getBastidor() != null && !detalleTramite.getVehiculoDto().getBastidor().equals("") ? detalleTramite.getVehiculoDto().getBastidor() : "") + "#"
				+ detalleTitular.getPersona().getNif() + "#" + colegiado.getNif() + "#" + contratoVO.getColegio().getCif() + "#" + fechaDeHoy;
		// Ciframos la firma, convertimos a hexadecimal y restringimos a los
		// primeros _16 caracteres.
		firmaColegio = utiles.cifrarHMACSHA1(firmaColegio, CLAVE_CIFRADO);
		firmaColegio = utiles.stringToHex(firmaColegio);
		firmaColegio = firmaColegio.substring(0, ConstantesPDF._16);
		byte1 = insertarBarcodeFirma(ConstantesPDF.ID_FIRMA_COLEGIO, byte1, vectPags, firmaColegio, pdf);

		return byte1;
	}

	public String getRuta() {
		return ruta;
	}

	public void setRuta(String ruta) {
		this.ruta = ruta;
	}

	public String getRutaPlantillas() {
		return rutaPlantillas;
	}

	public void setRutaPlantillas(String rutaPlantillas) {
		this.rutaPlantillas = rutaPlantillas;
	}

	public TramiteTraficoTransmisionBean getDetalleTransmision() {
		return detalleTransmision;
	}

	public void setDetalleTransmision(TramiteTraficoTransmisionBean detalleTransmision) {
		this.detalleTransmision = detalleTransmision;
	}

	public ArrayList<TramiteTraficoTransmisionBean> getListaTramitesTrans() {
		return listaTramitesTrans;
	}

	public void setListaTramitesTrans(ArrayList<TramiteTraficoTransmisionBean> listaTramitesTrans) {
		this.listaTramitesTrans = listaTramitesTrans;
	}

	public byte[] getByte1() {
		return byte1;
	}

	public void setByte1(byte[] byte1) {
		this.byte1 = byte1;
	}

	/* *********************************************************************** */
	/* MODELOS *************************************************************** */
	/* *********************************************************************** */
	public ModeloTransmision getModeloTransmision() {
		if (modeloTransmision == null) {
			modeloTransmision = new ModeloTransmision();
		}
		return modeloTransmision;
	}

	public void setModeloTransmision(ModeloTransmision modeloTransmision) {
		this.modeloTransmision = modeloTransmision;
	}

	public ModeloCreditosTrafico getModeloCreditosTrafico() {
		if (modeloCreditosTrafico == null) {
			modeloCreditosTrafico = new ModeloCreditosTrafico();
		}
		return modeloCreditosTrafico;
	}

	public void setModeloCreditosTrafico(ModeloCreditosTrafico modeloCreditosTrafico) {
		this.modeloCreditosTrafico = modeloCreditosTrafico;
	}
}