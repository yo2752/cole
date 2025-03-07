package org.gestoresmadrid.oegam2comun.impresion;

import static trafico.utiles.ConstantesPDF.PERSONA_JURIDICA;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import org.gestoresmadrid.core.contrato.model.vo.ContratoVO;
import org.gestoresmadrid.core.model.enumerados.EstadoTramiteTrafico;
import org.gestoresmadrid.core.model.enumerados.MotivoBaja;
import org.gestoresmadrid.core.model.enumerados.TextoNotificacion;
import org.gestoresmadrid.core.model.enumerados.TipoImpreso;
import org.gestoresmadrid.core.model.enumerados.TipoTramiteTrafico;
import org.gestoresmadrid.core.springContext.ContextoSpring;
import org.gestoresmadrid.oegam2comun.contrato.model.service.ServicioContrato;
import org.gestoresmadrid.oegam2comun.conversion.Conversiones;
import org.gestoresmadrid.oegam2comun.direccion.model.service.ServicioDireccion;
import org.gestoresmadrid.oegam2comun.impresion.util.ImpresionGeneral;
import org.gestoresmadrid.oegam2comun.personas.model.service.ServicioPersona;
import org.gestoresmadrid.oegam2comun.trafico.model.service.ServicioTramiteTrafico;
import org.gestoresmadrid.oegam2comun.trafico.model.service.ServicioTramiteTraficoBaja;
import org.gestoresmadrid.oegam2comun.trafico.view.dto.TramiteTrafBajaDto;
import org.gestoresmadrid.oegamComun.contrato.view.dto.ContratoDto;
import org.gestoresmadrid.oegamComun.interviniente.view.dto.IntervinienteTraficoDto;
import org.gestoresmadrid.oegamComun.persona.view.dto.PersonaDto;
import org.gestoresmadrid.oegamComun.vehiculo.view.dto.VehiculoDto;
import org.gestoresmadrid.utilidades.components.GestorPropiedades;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;

import es.globaltms.gestorDocumentos.bean.FileResultBean;
import es.globaltms.gestorDocumentos.constantes.ConstantesGestorFicheros;
import es.globaltms.gestorDocumentos.enums.FileResultStatus;
import es.globaltms.gestorDocumentos.interfaz.GestorDocumentos;
import es.globaltms.gestorDocumentos.util.Utilidades;
import escrituras.beans.ResultBean;
import general.utiles.UtilesCadenaCaracteres;
import trafico.beans.IntervinienteTrafico;
import trafico.beans.TramiteTraficoBajaBean;
import trafico.beans.VehiculoBean;
import trafico.beans.avpobastigest.CargaGestBean;
import trafico.beans.avpobastigest.EmbargoGestBean;
import trafico.beans.avpobastigest.GestBean;
import trafico.beans.utiles.CampoPdfBean;
import trafico.utiles.ConstantesPDF;
import trafico.utiles.PdfMaker;
import trafico.utiles.UtilResources;
import trafico.utiles.constantes.ConstantesTrafico;
import utilidades.estructuras.Fecha;
import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;
import utilidades.web.OegamExcepcion;

public class ImpresionTramitesBaja extends ImpresionGeneral {

	private static ILoggerOegam log = LoggerOegam.getLogger(ImpresionTramitesBaja.class);

	private TramiteTrafBajaDto detalleBaja;
	private TramiteTraficoBajaBean detalleBajaBean;
	private ArrayList<TramiteTrafBajaDto> listaTramitesBaja;

	private ArrayList<CampoPdfBean> camposFormateados;
	private Set<String> camposPlantilla;

	private String ruta;
	private String rutaPlantillas;

	private byte[] bytePdf;
	private PdfMaker pdf;

	private String mensaje;

	@Autowired
	private GestorPropiedades gestorPropiedades;

	@Autowired
	private GestorDocumentos gestorDocumentos;

	public ResultBean impresionGeneralBajaParaTransmisiones(TramiteTraficoBajaBean tramiteBaja, String tipoImpreso, ContratoVO contrato, BigDecimal idUsuario, String numColegiado)
			throws OegamExcepcion {
		ResultBean resultadoMetodo = new ResultBean();

		camposFormateados = new ArrayList<>();
		pdf = new PdfMaker();

		String nube = null;
		mensaje = "";

		boolean error = false;

		int[] vectPags = new int[ConstantesPDF._1];
		vectPags[0] = ConstantesPDF._1;
		// -------------------------------------------------------------------------------------------

		if (tramiteBaja.getTramiteTrafico() != null) {
			detalleBajaBean = tramiteBaja;
			calcularRuta(TipoImpreso.BajasPDF417.toString(), true, false);

			bytePdf = pdf.abrirPdf(ruta);
			camposPlantilla = pdf.getAllFields(bytePdf);

			if (detalleBajaBean.getMotivoBaja() != null) {
				procesarTipoTransferencia(detalleBajaBean.getMotivoBaja().getNombreEnum(), detalleBajaBean.getCet());
			}

			bytePdf = pdf.setCampos(bytePdf, camposFormateados);

			procesarDatosPresentacion();
			procesarFechaMatriculacion();
			procesarIntervinientes(true);
			// TODO: No se muestra para las transmisiones por Baja, se queda comentado por si se quiere añadir
			// procesarStatusTelematico();

			if (!error && bytePdf != null) {
				// imprimimos datos de la direccin del vehiculo
				camposFormateados = obtenerValoresDireccionVehiculoBaja(ConstantesPDF._12, camposPlantilla, detalleBajaBean);
				bytePdf = pdf.setCampos(bytePdf, camposFormateados);
				bytePdf = insertarFirmasColegiadoYColegio(detalleBajaBean.getTramiteTrafico(), detalleBajaBean.getTitular(), bytePdf, vectPags, pdf, contrato);
				if (tipoImpreso.equals(TipoImpreso.BajasPDF417.toString())) {
					nubePuntos(nube, vectPags);
					bytePdf = rutaAgua(pdf, tipoImpreso, vectPags, ConstantesPDF.RUTA_MARCA_AGUA, rutaPlantillas, bytePdf);
				} else if (tipoImpreso.equals(TipoImpreso.BajasBorradorPDF417.toString())) {
					bytePdf = etiquetasPlantilla(camposPlantilla, pdf, bytePdf, tipoImpreso, ConstantesPDF.ID_NUBE_PUNTOS, ConstantesPDF.ID_NUBE_PUNTOS_2);
				}
			}
		} else {
			error = true;
			mensaje = "Los datos del trámite están vacíos";
		}

		if (error == true || bytePdf == null) {
			mensaje += ". Error al imprimir el PDF";
		}

		resultadoMetodo.setError(error);
		resultadoMetodo.setMensaje(mensaje);
		resultadoMetodo.addAttachment(ResultBean.TIPO_PDF, bytePdf);
		return resultadoMetodo;
	}

	public ResultBean impresionGeneralBajaParaTransmisionesNuevo(TramiteTrafBajaDto tramiteBaja, String tipoTramite, String tipoImpreso, ContratoVO contratoVO, BigDecimal idUsuario)
			throws OegamExcepcion {
		ResultBean resultadoMetodo = new ResultBean();

		camposFormateados = new ArrayList<>();
		pdf = new PdfMaker();

		String nube = null;
		mensaje = "";

		boolean error = false;

		int[] vectPags = new int[ConstantesPDF._1];
		vectPags[0] = ConstantesPDF._1;
		// -------------------------------------------------------------------------------------------------
		if (tramiteBaja != null) {
			detalleBaja = tramiteBaja;
			calcularRuta(TipoImpreso.BajasPDF417.toString(), true, false);

			bytePdf = pdf.abrirPdf(ruta);
			camposPlantilla = pdf.getAllFields(bytePdf);

			procesarTipoTransferencia(detalleBaja.getMotivoBaja(), null);

			bytePdf = pdf.setCampos(bytePdf, camposFormateados);

			procesarDatosPresentacionBaja(ConstantesPDF._9, contratoVO);

			procesarFechaMatriculacionBaja();
			procesarIntervinientesBaja(true);
			// TODO: No se muestra para las transmisiones por Baja, se queda comentado por si se quiere añadir
			// procesarStatusTelematicoBaja();

			if (!error && bytePdf != null) {
				bytePdf = insertarFirmasColegiadoYColegioBaja(detalleBaja, detalleBaja.getTitular(), bytePdf, vectPags, pdf, contratoVO);
				if (tipoImpreso.equals(TipoImpreso.BajasPDF417.toString())) {
					nubePuntosBaja(nube, vectPags);
					bytePdf = rutaAgua(pdf, tipoImpreso, vectPags, ConstantesPDF.RUTA_MARCA_AGUA, rutaPlantillas, bytePdf);
				} else if (tipoImpreso.equals(TipoImpreso.BajasBorradorPDF417.toString())) {
					bytePdf = etiquetasPlantilla(camposPlantilla, pdf, bytePdf, tipoImpreso, ConstantesPDF.ID_NUBE_PUNTOS, ConstantesPDF.ID_NUBE_PUNTOS_2);
				}
			}
		} else {
			error = true;
			mensaje = "Los datos del trámite están vacíos";
		}

		if (error || bytePdf == null) {
			mensaje += ". Error al imprimir el PDF";
		}

		resultadoMetodo.setError(error);
		resultadoMetodo.setMensaje(mensaje);
		resultadoMetodo.addAttachment(ResultBean.TIPO_PDF, bytePdf);
		return resultadoMetodo;
	}

	// Tipo impresos: PDF417 y Borrado417
	public ResultBean impresionGeneralBaja(String numsExpedientes, String tipoImpreso, BigDecimal idUsuario, TipoTramiteTrafico tipoTramite) throws OegamExcepcion, Throwable {
		ResultBean resultadoMetodo = new ResultBean();

		camposFormateados = new ArrayList<>();
		pdf = new PdfMaker();

		String nube = null;
		mensaje = "";

		boolean error = false;

		int[] vectPags = new int[ConstantesPDF._1];
		vectPags[0] = ConstantesPDF._1;
		// -------------------------------------------------------------------------------------------------

		obtenerDetalleTramiteBaja(numsExpedientes, tipoImpreso);

		if (detalleBaja != null) {

			ServicioContrato servicioContrato = ContextoSpring.getInstance().getBean(ServicioContrato.class);
			ContratoVO contratoVO = servicioContrato.getContrato(detalleBaja.getIdContrato());

			if (detalleBaja.getMotivoBaja().equalsIgnoreCase(MotivoBaja.TranCTIT.getValorEnum()) || detalleBaja.getMotivoBaja().equalsIgnoreCase(MotivoBaja.ExpCTIT.getValorEnum())) {
				error = true;
				mensaje = "Este trámite de baja no permite la impresión por pdf, ya que sólo admite su presentación por Excel";
			} else {
				// Si el motivo de la baja es Temporal Transferencia, tendremos que imprimir una transmisión
				// Aunque no se puede dar de alta ya una baja Temporal Transferencia
				if (detalleBaja.getMotivoBaja().equals(MotivoBaja.TempT.getValorEnum())) {
					return impresionGeneralBajaParaTransmisionesNuevo(detalleBaja, tipoTramite.getValorEnum(), tipoImpreso, contratoVO, idUsuario);
				}
				calcularRuta(tipoImpreso, false, false);

				bytePdf = pdf.abrirPdf(ruta);
				camposPlantilla = pdf.getAllFields(bytePdf);

				procesarDatosPresentacionBaja(ConstantesPDF._9, contratoVO);

				if (tipoImpreso.equals(TipoImpreso.BajasPDF417.toString())) {
					procesarIdTitulo();
				}

				procesarIdNumProfesional();
				procesarIdMotivo();
				procesarIdTipoBaja();
				procesarGEST();
				procesarFechaMatriculacionBaja();
				procesarIntervinientesBaja(false);
				procesarResultadoTramitabilidadBTV();
				procesarResultadoBtv();
				// Inserta las firmas del colegiado y colegio.
				if (TipoImpreso.BajasTelematicas.toString().equals(tipoImpreso)) {
					if (!error && bytePdf != null) {
						nubePuntosBaja(nube, vectPags);
						bytePdf = insertarFirmasColegiadoYColegioBaja(detalleBaja, detalleBaja.getTitular(), bytePdf, vectPags, pdf, contratoVO);
					}
				} else if (TipoImpreso.BajasPDF417.toString().equals(tipoImpreso)) {
					if (!error && bytePdf != null) {
						nubePuntosBaja(nube, vectPags);
						bytePdf = rutaAgua(pdf, tipoImpreso, vectPags, ConstantesPDF.RUTA_MARCA_AGUA, rutaPlantillas, bytePdf);
					}
				} else if (TipoImpreso.BajasBorradorPDF417.toString().equals(tipoImpreso)) {
					bytePdf = etiquetasPlantilla(camposPlantilla, pdf, bytePdf, tipoImpreso, ConstantesPDF.ID_NUBE_PUNTOS, ConstantesPDF.ID_NUBE_PUNTOS_2);
				}
			}
		} else {
			error = true;
			mensaje = "Los datos del trámite están vacíos";
		}

		if (error || bytePdf == null) {
			mensaje += ". Error al imprimir el PDF";
		}

		resultadoMetodo.setError(error);
		resultadoMetodo.setMensaje(mensaje);
		resultadoMetodo.addAttachment(ResultBean.TIPO_PDF, bytePdf);
		return resultadoMetodo;
	}

	// Tipo impreso: Listado de bastidores
	public ResultBean impresionListadoBastidoresBaja(String[] numExpedientes, BigDecimal idUsuario) throws OegamExcepcion, Throwable {
		ResultBean resultadoMetodo = new ResultBean();

		listaTramitesBaja = new ArrayList<>();

		pdf = new PdfMaker();

		int numExpedientesEscritos = 0;
		// ----------------------------------------------------------------------------

		for (String numExpediente : numExpedientes) {
			obtenerDetalleTramiteBaja(numExpediente, TipoImpreso.MatriculacionListadoBastidores.toString());
		}

		TramiteTrafBajaDto tramite = listaTramitesBaja.get(0);
		boolean mismoTitular = esMismoTitular(tramite);

		calcularRuta(TipoImpreso.MatriculacionListadoBastidores.toString(), false, mismoTitular);

		byte[] byteFinal = generarPDFListadoBastidores(tramite, mismoTitular, numExpedientesEscritos, resultadoMetodo, idUsuario);

		if (byteFinal != null) {
			resultadoMetodo.addAttachment(ResultBean.TIPO_PDF, byteFinal);
			resultadoMetodo.addAttachment(ResultBean.NOMBRE_FICHERO, "listadoMatriculas.pdf");
			resultadoMetodo.setError(false);
		} else {
			resultadoMetodo.setError(true);
			resultadoMetodo.setMensaje("Error al generar el PDF del listado de Matrículas");
		}

		return resultadoMetodo;
	}

	public ResultBean impresionMandatosBaja(String numExpediente, String tipoImpreso) throws OegamExcepcion, Throwable {
		ResultBean resultadoMetodo = new ResultBean();

		camposFormateados = new ArrayList<CampoPdfBean>();
		pdf = new PdfMaker();

		mensaje = "";
		boolean error = false;
		// ----------------------------------------------------------------------------

		obtenerDetalleTramiteBaja(numExpediente, tipoImpreso);

		if (detalleBaja != null) {
			calcularRuta(tipoImpreso, false, false);
			bytePdf = pdf.abrirPdf(ruta);
			camposPlantilla = pdf.getAllFields(bytePdf);
			camposFormateados.addAll(obtenerValoresMandatoBaja(ConstantesPDF._11, camposPlantilla));
			if (TipoImpreso.MatriculacionMandatoEspecifico.toString().equals(tipoImpreso)) {
				procesarAsuntos();
			}
			bytePdf = pdf.setCampos(bytePdf, camposFormateados);
		} else {
			error = true;
			mensaje = "Los datos del trámite están vacíos";
		}

		if (error || bytePdf == null) {
			error = true;
			mensaje += ". Puede que no esté completo el trámite impreso.";
		}

		resultadoMetodo.setError(error);
		resultadoMetodo.setMensaje(mensaje);
		resultadoMetodo.addAttachment(ResultBean.TIPO_PDF, bytePdf);
		return resultadoMetodo;
	}

	private void procesarAsuntos() {
		if (camposPlantilla.contains(ConstantesPDF.ID_ASUNTO1_EXPLICACION)) {
			String texto = "BAJA: ";
			if (detalleBaja.getVehiculoDto().getMatricula() != null && !detalleBaja.getVehiculoDto().getMatricula().equals("")) {
				texto = texto + detalleBaja.getVehiculoDto().getMatricula();
			} else if (detalleBaja.getVehiculoDto().getBastidor() != null && !"".equals(detalleBaja.getVehiculoDto().getBastidor())) {
				texto = texto + detalleBaja.getVehiculoDto().getBastidor();
			}
			camposFormateados.add(new CampoPdfBean(ConstantesPDF.ID_ASUNTO1_EXPLICACION, texto, false, false, ConstantesPDF._11));
		}
		// if (camposPlantilla.contains(ConstantesPDF.ID_ASUNTO2)) {
		// if (detalleBaja.getVehiculoDto() != null) {
		// camposFormateados.add(new CampoPdfBean(ConstantesPDF.ID_ASUNTO2, detalleBaja.getVehiculoDto().getMatricula(), false, false, ConstantesPDF._11));
		// }
		// }
	}

	protected Collection<? extends CampoPdfBean> obtenerValoresMandatoBaja(Integer tamCampos, Set<String> camposPlantilla) throws OegamExcepcion {
		ArrayList<CampoPdfBean> camposFormateados = new ArrayList<>();
		CampoPdfBean campoAux = null;
		PersonaDto personaTitular = null;
		PersonaDto personaRepresentante = null;
		String municipio = "";

		ServicioDireccion servicioDireccion = ContextoSpring.getInstance().getBean(ServicioDireccion.class);
		Conversiones conversiones = ContextoSpring.getInstance().getBean(Conversiones.class);

		if (detalleBaja.getTitular() != null) {
			personaTitular = detalleBaja.getTitular().getPersona();
			personaTitular.setDireccionDto(detalleBaja.getTitular().getDireccion());
			if (personaTitular.getDireccionDto() != null) {
				municipio = servicioDireccion.obtenerNombreMunicipio(personaTitular.getDireccionDto().getIdMunicipio(), personaTitular.getDireccionDto().getIdProvincia());
			}

			if (personaTitular != null && personaTitular.getSexo() != null && personaTitular.getSexo().equals(PERSONA_JURIDICA)) {
				if (detalleBaja.getRepresentanteTitular() != null && detalleBaja.getRepresentanteTitular().getPersona() != null) {
					personaRepresentante = detalleBaja.getRepresentanteTitular().getPersona();
					// Datos de persona física
					if (camposPlantilla.contains(ConstantesPDF.ID_NOMBRE_APELLIDOS_TITULAR)) {
						String nombreApellidos = (null != personaRepresentante.getNombre() ? personaRepresentante.getNombre() : "") + " " + (null != personaRepresentante.getApellido1RazonSocial()
								? personaRepresentante.getApellido1RazonSocial() : "") + " " + (null != personaRepresentante.getApellido2() ? personaRepresentante.getApellido2() : "");
						campoAux = new CampoPdfBean(ConstantesPDF.ID_NOMBRE_APELLIDOS_TITULAR, nombreApellidos, false, false, tamCampos);
						camposFormateados.add(campoAux);
					}

					if (camposPlantilla.contains(ConstantesPDF.ID_DNI_TITULAR)) {
						campoAux = new CampoPdfBean(ConstantesPDF.ID_DNI_TITULAR, personaRepresentante.getNif(), false, false, tamCampos);
						camposFormateados.add(campoAux);
					}
				}

				// Datos de persona jurídica
				if (camposPlantilla.contains(ConstantesPDF.ID_NOMBRE_ENTIDAD)) {
					campoAux = new CampoPdfBean(ConstantesPDF.ID_NOMBRE_ENTIDAD, personaTitular.getApellido1RazonSocial(), false, false, tamCampos);
					camposFormateados.add(campoAux);
				}

				if (camposPlantilla.contains(ConstantesPDF.CIF_ENTIDAD)) {
					campoAux = new CampoPdfBean(ConstantesPDF.CIF_ENTIDAD, personaTitular.getNif(), false, false, tamCampos);
					camposFormateados.add(campoAux);
				}

				if (camposPlantilla.contains(ConstantesPDF.ID_MUNICIPIO_ENTIDAD)) {
					campoAux = new CampoPdfBean(ConstantesPDF.ID_MUNICIPIO_ENTIDAD, municipio, false, false, tamCampos);
					camposFormateados.add(campoAux);
				}

				if (camposPlantilla.contains(ConstantesPDF.ID_DOMICILIO_ENTIDAD)) {
					campoAux = new CampoPdfBean(ConstantesPDF.ID_DOMICILIO_ENTIDAD, conversiones.ajustarCamposIne(personaTitular.getDireccionDto().getNombreVia()), false, false, tamCampos);
					camposFormateados.add(campoAux);
				}

				if (camposPlantilla.contains(ConstantesPDF.ID_NUM_DOMICILIO_ENTIDAD)) {
					campoAux = new CampoPdfBean(ConstantesPDF.ID_NUM_DOMICILIO_ENTIDAD, personaTitular.getDireccionDto().getNumero(), false, false, tamCampos);
					camposFormateados.add(campoAux);
				}

				if (camposPlantilla.contains(ConstantesPDF.ID_CP_ENTIDAD)) {
					campoAux = new CampoPdfBean(ConstantesPDF.ID_CP_ENTIDAD, personaTitular.getDireccionDto().getCodPostal(), false, false, tamCampos);
					camposFormateados.add(campoAux);
				}

			} else {
				// Datos de persona física
				if (camposPlantilla.contains(ConstantesPDF.ID_NOMBRE_APELLIDOS_TITULAR)) {
					String nombreApellidos = (null != personaTitular.getNombre() ? personaTitular.getNombre() : "") + " " + (null != personaTitular.getApellido1RazonSocial() ? personaTitular
							.getApellido1RazonSocial() : "") + " " + (null != personaTitular.getApellido2() ? personaTitular.getApellido2() : "");
					campoAux = new CampoPdfBean(ConstantesPDF.ID_NOMBRE_APELLIDOS_TITULAR, nombreApellidos, false, false, tamCampos);
					camposFormateados.add(campoAux);
				}

				if (camposPlantilla.contains(ConstantesPDF.ID_DNI_TITULAR)) {
					campoAux = new CampoPdfBean(ConstantesPDF.ID_DNI_TITULAR, personaTitular.getNif(), false, false, tamCampos);
					camposFormateados.add(campoAux);
				}
			}

			if (personaTitular != null && personaTitular.getDireccionDto() != null) {
				String nombreVia = conversiones.ajustarCamposIne(personaTitular.getDireccionDto().getNombreVia());
				String nombreViaYMunicipio = "";

				// Picar la dirección si tiene más de 13 caracteres
				if (null != nombreVia && nombreVia.length() > 13) {
					nombreVia = nombreVia.substring(0, 13);
					int ultimoEspacio = nombreVia.lastIndexOf(' ');

					if (conversiones.ajustarCamposIne(personaTitular.getDireccionDto().getNombreVia()).charAt(13) == ' ') {
						ultimoEspacio = 13;
					}

					if (ultimoEspacio == -1) {
						nombreVia += "-";
						nombreViaYMunicipio = (null != personaTitular.getDireccionDto().getNombreVia() ? conversiones.ajustarCamposIne(personaTitular.getDireccionDto().getNombreVia()).substring(13)
								: "");

					} else {
						nombreVia = nombreVia.substring(0, ultimoEspacio);
						nombreViaYMunicipio = (null != personaTitular.getDireccionDto().getNombreVia() ? conversiones.ajustarCamposIne(personaTitular.getDireccionDto().getNombreVia()).substring(
								ultimoEspacio) : "");
					}
					nombreViaYMunicipio += (null != nombreViaYMunicipio || !("").equals(nombreViaYMunicipio) ? ", " : "");
					nombreViaYMunicipio += (null != municipio ? municipio : "");
				} else {
					nombreViaYMunicipio = (null != municipio ? municipio : "");
				}

				if (camposPlantilla.contains(ConstantesPDF.ID_PROVINCIA_ENTIDAD)) {
					String provincia = servicioDireccion.obtenerNombreProvincia(personaTitular.getDireccionDto().getIdProvincia());
					campoAux = new CampoPdfBean(ConstantesPDF.ID_PROVINCIA_ENTIDAD, provincia, false, false, tamCampos);
					camposFormateados.add(campoAux);
				}

				if (camposPlantilla.contains(ConstantesPDF.ID_DOMICILIO_ENTIDAD)) {
					campoAux = new CampoPdfBean(ConstantesPDF.ID_DOMICILIO_ENTIDAD, nombreVia, false, false, tamCampos);
					camposFormateados.add(campoAux);
				}

				if (camposPlantilla.contains(ConstantesPDF.ID_NUM_DOMICILIO_ENTIDAD)) {
					campoAux = new CampoPdfBean(ConstantesPDF.ID_NUM_DOMICILIO_ENTIDAD, personaTitular.getDireccionDto().getNumero(), false, false, tamCampos);
					camposFormateados.add(campoAux);
				}

				if (camposPlantilla.contains(ConstantesPDF.ID_DOMICILIO_Y_MUNICIPIO_ENTIDAD)) {
					campoAux = new CampoPdfBean(ConstantesPDF.ID_DOMICILIO_Y_MUNICIPIO_ENTIDAD, nombreViaYMunicipio, false, false, tamCampos);
					camposFormateados.add(campoAux);
				}

				if (camposPlantilla.contains(ConstantesPDF.ID_CP_ENTIDAD)) {
					campoAux = new CampoPdfBean(ConstantesPDF.ID_CP_ENTIDAD, personaTitular.getDireccionDto().getCodPostal(), false, false, tamCampos);
					camposFormateados.add(campoAux);
				}
			}
		}

		ServicioPersona servicioPersona = ContextoSpring.getInstance().getBean(ServicioPersona.class);
		PersonaDto colegiado = servicioPersona.obtenerColegiadoCompleto(detalleBaja.getNumColegiado(), detalleBaja.getIdContrato());

		ServicioContrato servicioContrato = ContextoSpring.getInstance().getBean(ServicioContrato.class);
		ContratoDto contrato = servicioContrato.getContratoDto(detalleBaja.getIdContrato());

		if (camposPlantilla.contains(ConstantesPDF.ID_NOMBRE_APELLIDOS_GESTOR)) {
			campoAux = new CampoPdfBean(ConstantesPDF.ID_NOMBRE_APELLIDOS_GESTOR, colegiado.getNombre() + " " + colegiado.getApellido1RazonSocial() + " " + colegiado.getApellido2(), false, false,
					tamCampos);
			camposFormateados.add(campoAux);
		}

		if (camposPlantilla.contains(ConstantesPDF.ID_NUM_COLEGIADO_GESTOR)) {
			campoAux = new CampoPdfBean(ConstantesPDF.ID_NUM_COLEGIADO_GESTOR, detalleBaja.getNumColegiado(), false, false, tamCampos);
			camposFormateados.add(campoAux);
		}

		if (camposPlantilla.contains(ConstantesPDF.ID_COLEGIO_GESTOR)) {
			campoAux = new CampoPdfBean(ConstantesPDF.ID_COLEGIO_GESTOR, contrato.getColegioDto().getColegio(), false, false, tamCampos);
			camposFormateados.add(campoAux);
		}

		if (camposPlantilla.contains(ConstantesPDF.ID_MUNICIPIO_GESTOR)) {
			String nombreMunicipio = servicioDireccion.obtenerNombreMunicipio(contrato.getIdMunicipio(), contrato.getIdProvincia());
			campoAux = new CampoPdfBean(ConstantesPDF.ID_MUNICIPIO_GESTOR, nombreMunicipio, false, false, tamCampos);
			camposFormateados.add(campoAux);
		}

		if (camposPlantilla.contains(ConstantesPDF.ID_COLEGIO)) {
			if (contrato.getColegioDto() != null) {
				campoAux = new CampoPdfBean(ConstantesPDF.ID_COLEGIO, contrato.getColegioDto().getColegio(), false, false, tamCampos);
			} else {
				campoAux = new CampoPdfBean(ConstantesPDF.ID_COLEGIO, "", false, false, tamCampos);
			}
			camposFormateados.add(campoAux);
		}

		if (camposPlantilla.contains(ConstantesPDF.ID_DOMICILIO_GESTOR)) {
			campoAux = new CampoPdfBean(ConstantesPDF.ID_DOMICILIO_GESTOR, conversiones.ajustarCamposIne(contrato.getVia()), false, false, tamCampos);
			camposFormateados.add(campoAux);
		}
		if (camposPlantilla.contains(ConstantesPDF.ID_NUM_DOMICILIO_GESTOR)) {
			campoAux = new CampoPdfBean(ConstantesPDF.ID_NUM_DOMICILIO_GESTOR, contrato.getNumero(), false, false, tamCampos);
			camposFormateados.add(campoAux);
		}
		if (camposPlantilla.contains(ConstantesPDF.ID_CP_GESTOR)) {
			campoAux = new CampoPdfBean(ConstantesPDF.ID_CP_GESTOR, contrato.getCodPostal(), false, false, tamCampos);
			camposFormateados.add(campoAux);
		}
		if (camposPlantilla.contains(ConstantesPDF.ID_MATRICULA) && detalleBaja.getVehiculoDto() != null) {
			camposFormateados.add(new CampoPdfBean(ConstantesPDF.ID_MATRICULA,
					detalleBaja.getVehiculoDto().getMatricula(), false, false, ConstantesPDF._11));
		}

		// Mantis 25624
		if (camposPlantilla.contains(ConstantesPDF.NIF_GA)) {
			campoAux = new CampoPdfBean(ConstantesPDF.NIF_GA, contrato.getCif(), false, false, tamCampos);
			camposFormateados.add(campoAux);
		}

		if (camposPlantilla.contains(ConstantesPDF.ID_DOMICILIO_GA)) {
			campoAux = new CampoPdfBean(ConstantesPDF.ID_DOMICILIO_GA, ConstantesPDF.VALOR_PROVINCIA_MADRID, false, false, tamCampos);
			camposFormateados.add(campoAux);
		}

		if (camposPlantilla.contains(ConstantesPDF.ID_VIA_MANDATARIO)) {
			campoAux = new CampoPdfBean(ConstantesPDF.ID_VIA_MANDATARIO, ConstantesPDF.VALOR_DIRECCION_COLEGIO, false, false, tamCampos);
			camposFormateados.add(campoAux);
		}

		if (camposPlantilla.contains(ConstantesPDF.ID_VIA_GA)) {
			campoAux = new CampoPdfBean(ConstantesPDF.ID_VIA_GA, ConstantesPDF.VALOR_NUMERO_DIRECCION_COLEGIO, false, false, tamCampos);
			camposFormateados.add(campoAux);
		}

		if (camposPlantilla.contains(ConstantesPDF.ID_CP_GA)) {
			campoAux = new CampoPdfBean(ConstantesPDF.ID_CP_GA, ConstantesPDF.VALOR_CODIGO_POSTAL_COLEGIO, false, false, tamCampos);
			camposFormateados.add(campoAux);
		}

		return camposFormateados;
	}

	private void calcularRuta(String tipoImpreso, boolean transmision, boolean mismoTitular) throws OegamExcepcion {
		UtilResources util = new UtilResources();
		ruta = gestorPropiedades.valorPropertie(ConstantesPDF.RUTA_PLANTILLAS);

		if (tipoImpreso.equals(TipoImpreso.BajasPDF417.toString())) {
			rutaPlantillas = ruta;
		}

		if (tipoImpreso.equals(TipoImpreso.MatriculacionListadoBastidores.toString())) {
			if (mismoTitular) {
				ruta = util.getFilePath(ruta + TipoImpreso.ListadoMatriculas1_BD.getNombreEnum());
			} else {
				ruta = util.getFilePath(ruta + TipoImpreso.ListadoMatriculas2_BD.getNombreEnum());
			}
		} else if (tipoImpreso.equals(TipoImpreso.MatriculacionMandatoGenerico.toString())) {
			ruta = util.getFilePath(ruta + TipoImpreso.MatriculacionMandatoGenerico.getNombreEnum());
		} else if (tipoImpreso.equals(TipoImpreso.MatriculacionMandatoEspecifico.toString())) {
			String nuevosMandatosBaja = gestorPropiedades.valorPropertie("mandatos.bajas.nuevos");
			if ("SI".equalsIgnoreCase(nuevosMandatosBaja)) {
				ruta = util.getFilePath(ruta + TipoImpreso.BajaMandatoEspecifico.getNombreEnum());
			} else {
				ruta = util.getFilePath(ruta + TipoImpreso.MatriculacionMandatoEspecifico.getNombreEnum());
			}
		} else if (transmision) {
			if (tipoImpreso.equals(TipoImpreso.BajasBorradorPDF417.toString())) {
				ruta = util.getFilePath(ruta + TipoImpreso.TransmisionBorradorPDF417.getNombreEnum());
			} else if (tipoImpreso.equals(TipoImpreso.BajasPDF417.toString())) {
				ruta = util.getFilePath(ruta + TipoImpreso.TransmisionPDF417.getNombreEnum());
			}
		} else if (!transmision) {
			if (tipoImpreso.equals(TipoImpreso.BajasBorradorPDF417.toString())) {
				ruta = util.getFilePath(ruta + TipoImpreso.BajasBorradorPDF417.getNombreEnum());
			} else if (tipoImpreso.equals(TipoImpreso.BajasPDF417.toString())) {
				ruta = util.getFilePath(ruta + TipoImpreso.BajasPDF417.getNombreEnum());
			} else if (tipoImpreso.equals(TipoImpreso.BajasTelematicas.toString())) {
				ruta = util.getFilePath(ruta + TipoImpreso.BajasTelematicas.getNombreEnum());
			}

		}
	}

	private void obtenerDetalleTramiteBaja(String numExpediente, String tipoImpreso) throws Throwable {
		detalleBaja = new TramiteTrafBajaDto();
		ServicioTramiteTraficoBaja servicioTraficoBaja = ContextoSpring.getInstance().getBean(ServicioTramiteTraficoBaja.class);
		detalleBaja = servicioTraficoBaja.getTramiteBaja(new BigDecimal(numExpediente), true);
		if (tipoImpreso != null && tipoImpreso.equals(TipoImpreso.MatriculacionListadoBastidores.toString())) {
			listaTramitesBaja.add(detalleBaja);
		}
	}

	private void nubePuntos(String nube, int[] vectPags) {
		nube = obtenerNubeBaja(detalleBajaBean);
		nube = UtilesCadenaCaracteres.sustituyeCaracteres_Plus(nube);
		nube = UtilesCadenaCaracteres.sustituyeCaracteres(nube);
		bytePdf = insertarNubePuntos(ConstantesTrafico.ID_NUBE_PUNTOS, bytePdf, vectPags, nube, pdf);
	}

	private void nubePuntosBaja(String nube, int[] vectPags) {
		nube = obtenerNubeBajaNuevo(detalleBaja);
		nube = UtilesCadenaCaracteres.sustituyeCaracteres_Plus(nube);
		bytePdf = insertarNubePuntos(ConstantesTrafico.ID_NUBE_PUNTOS, bytePdf, vectPags, nube, pdf);
	}

	private void procesarIdTitulo() {
		CampoPdfBean campoAux = null;
		if (camposPlantilla.contains(ConstantesPDF.ID_TITULO)) {
			campoAux = new CampoPdfBean(ConstantesPDF.ID_TITULO, "BAJAS", false, false, ConstantesPDF._15);
			camposFormateados.add(campoAux);
		}
	}

	private void procesarIdNumProfesional() {
		CampoPdfBean campoAux = null;
		if (camposPlantilla.contains(ConstantesTrafico.ID_NUM_PROFESIONAL)) {
			campoAux = new CampoPdfBean(ConstantesTrafico.ID_NUM_PROFESIONAL, detalleBaja.getNumColegiado(), false, false, ConstantesPDF._13);
			camposFormateados.add(campoAux);
		}
	}

	private void procesarIdMotivo() {
		CampoPdfBean campoAux = null;
		if (camposPlantilla.contains(ConstantesTrafico.ID_MOTIVO_BAJA)) {
			campoAux = new CampoPdfBean(ConstantesTrafico.ID_MOTIVO_BAJA, null != detalleBaja.getMotivoBaja() ? MotivoBaja.convertirTexto(detalleBaja.getMotivoBaja()) : "", false, false,
					ConstantesPDF._9);
			camposFormateados.add(campoAux);
		}

		if (camposPlantilla.contains(ConstantesTrafico.ID_PAIS_DESTINO)) {
			campoAux = new CampoPdfBean(ConstantesTrafico.ID_PAIS_DESTINO, null != detalleBaja.getPais() ? detalleBaja.getPais().getNombre() : "", false, false, ConstantesPDF._9);
			camposFormateados.add(campoAux);
		}

		bytePdf = pdf.setCampos(bytePdf, camposFormateados);
	}

	private void procesarIdTipoBaja() {
		CampoPdfBean campoAux = null;
		if (camposPlantilla.contains(ConstantesTrafico.ID_TIPO_BAJA)) {
			String tipoBaja = "";
			if (detalleBaja.getMotivoBaja() != null) {
				if (MotivoBaja.convertirTexto(detalleBaja.getMotivoBaja()).toUpperCase().contains(ConstantesTrafico.TEMPORAL)) {
					tipoBaja = ConstantesTrafico.TEMPORAL;
				} else if (MotivoBaja.convertirTexto(detalleBaja.getMotivoBaja()).toUpperCase().contains(ConstantesTrafico.DEFINITIVA)) {
					tipoBaja = ConstantesTrafico.DEFINITIVA;
				}
			}
			campoAux = new CampoPdfBean(ConstantesTrafico.ID_TIPO_BAJA, tipoBaja, false, false, ConstantesPDF._12);
			camposFormateados.add(campoAux);
		}
	}

	private void procesarGEST() {
		CampoPdfBean campoAux = null;

		if (detalleBaja != null && detalleBaja.getResCheckBtv() != null && !detalleBaja.getResCheckBtv().isEmpty()) {
			campoAux = new CampoPdfBean(ConstantesPDF.ID_ERROR_GEST, detalleBaja.getResCheckBtv(), false, false, ConstantesPDF._f9);
			camposFormateados.add(campoAux);
		}
		/*
		 * if (camposPlantilla.contains(ConstantesPDF.ID_GEST)) { valorGEST = ConstantesPDF.VALOR_GEST_OK; campoAux = new CampoPdfBean(ConstantesPDF.ID_GEST, ConstantesPDF.VALOR_GEST + valorGEST, false, false, ConstantesPDF._13); camposFormateados.add(campoAux); }
		 */
	}

	private void procesarFechaMatriculacion() {
		CampoPdfBean campoAux = null;
		if (detalleBajaBean.getTramiteTrafico() != null) {
			VehiculoBean vehiculo = detalleBajaBean.getTramiteTrafico().getVehiculo();
			if (camposPlantilla.contains(ConstantesPDF.ID_FECHA_MATRI) &&
				null != vehiculo.getFechaMatriculacion() && vehiculo.getFechaMatriculacion().getAnio() != null && vehiculo.getFechaMatriculacion().getMes() != null && vehiculo
						.getFechaMatriculacion().getDia() != null) {
				Fecha fecha = vehiculo.getFechaMatriculacion();
				campoAux = new CampoPdfBean(ConstantesTrafico.ID_FECHA_MATRI, fecha.toString(), false, false, ConstantesPDF._9);
				camposFormateados.add(campoAux);
			}
		}
	}

	private void procesarResultadoTramitabilidadBTV() {
		CampoPdfBean campoAux = null;
		if (camposPlantilla.contains(ConstantesTrafico.ID_RESULTADO_TRAMITABILIDAD)
				&& detalleBaja.getResCheckBtv() != null) {
			if (EstadoTramiteTrafico.Finalizado_Telematicamente.equals(detalleBaja.getEstado()) || EstadoTramiteTrafico.Finalizado_Telematicamente_Impreso.equals(detalleBaja.getEstado())) {
				campoAux = new CampoPdfBean(ConstantesTrafico.ID_RESULTADO_TRAMITABILIDAD, detalleBaja.getResCheckBtv(), true, false, ConstantesPDF._12);
			}
			campoAux = new CampoPdfBean(ConstantesTrafico.ID_RESULTADO_TRAMITABILIDAD, detalleBaja.getResCheckBtv(), true, false, ConstantesPDF._12);
			camposFormateados.add(campoAux);
			bytePdf = pdf.setCampos(bytePdf, camposFormateados);
		}
	}

	private void procesarResultadoBtv() throws OegamExcepcion {
		CampoPdfBean campoAux = null;
		if (camposPlantilla.contains(ConstantesTrafico.ID_RESULT_BTV) && detalleBaja.getRespuesta() != null) {
			campoAux = new CampoPdfBean(ConstantesTrafico.ID_RESULT_BTV, detalleBaja.getRespuesta(), true, false,
					ConstantesPDF._9);
			camposFormateados.add(campoAux);
			bytePdf = pdf.setCampos(bytePdf, camposFormateados);
		}
	}

	private void procesarFechaMatriculacionBaja() {
		CampoPdfBean campoAux = null;
		VehiculoDto vehiculo = detalleBaja.getVehiculoDto();
		if (camposPlantilla.contains(ConstantesPDF.ID_FECHA_MATRI) && null != vehiculo.getFechaMatriculacion()
				&& vehiculo.getFechaMatriculacion().getAnio() != null
				&& vehiculo.getFechaMatriculacion().getMes() != null
				&& vehiculo.getFechaMatriculacion().getDia() != null) {
			Fecha fecha = vehiculo.getFechaMatriculacion();
			campoAux = new CampoPdfBean(ConstantesTrafico.ID_FECHA_MATRI, fecha.toString(), false, false,
					ConstantesPDF._9);
			camposFormateados.add(campoAux);
		}
	}

	private void procesarIntervinientes(boolean transmision) {
		procesarTitular(mensaje, transmision);
		procesarAdquiriente(mensaje, transmision);
	}

	private void procesarTitular(String mensaje, boolean transmision) {
		if (null == detalleBajaBean.getTitular()) {
			mensaje = "Los datos del titular están vacíos";
		} else {
			if (transmision) {
				procesarInterviniente(detalleBajaBean.getTitular(), transmision, ConstantesPDF._TRANSMITENTE);
			} else {
				procesarInterviniente(detalleBajaBean.getTitular(), transmision, ConstantesPDF._TITULAR);
			}
		}
		if (detalleBajaBean.getRepresentanteTitular() != null) {
			if (transmision) {
				procesarRepresentante(detalleBajaBean.getRepresentanteTitular(), transmision, ConstantesPDF._REPR_TRANSMITENTE);
			} else {
				procesarRepresentante(detalleBajaBean.getRepresentanteTitular(), transmision, ConstantesPDF._REPR_TITULAR);
			}
		}
	}

	private void procesarAdquiriente(String mensaje, boolean transmision) {
		if (null == detalleBajaBean.getAdquiriente()) {
			mensaje = "Los datos del adquiriente están vacíos";
		} else {
			procesarInterviniente(detalleBajaBean.getAdquiriente(), transmision, ConstantesPDF._ADQUIRIENTE);

		}
		if (detalleBajaBean.getRepresentanteAdquiriente() != null) {
			procesarRepresentante(detalleBajaBean.getRepresentanteAdquiriente(), transmision, ConstantesPDF._REPR_ADQUIRIENTE);
		}
	}

	private void procesarInterviniente(IntervinienteTrafico interviniente, boolean transmision, String constanteInterviniente) {
		if (transmision) {
			camposFormateados.addAll(obtenerValoresInterviniente(ConstantesPDF._9, interviniente, constanteInterviniente, camposPlantilla));
			bytePdf = pdf.setCampos(bytePdf, camposFormateados);
		} else {
			camposFormateados.addAll(obtenerValoresInterviniente(ConstantesPDF._9, interviniente, constanteInterviniente, camposPlantilla));
			camposFormateados.addAll(obtenerValoresDireccion(ConstantesPDF._8, interviniente.getPersona().getDireccion(), constanteInterviniente, camposPlantilla));
			bytePdf = pdf.setCampos(bytePdf, camposFormateados);
		}
	}

	private void procesarRepresentante(IntervinienteTrafico interviniente, boolean transmision, String constanteInterviniente) {
		if (interviniente != null) {
			if (transmision) {
				camposFormateados.addAll(obtenerValoresInterviniente(ConstantesPDF._9, interviniente, constanteInterviniente, camposPlantilla));
				bytePdf = pdf.setCampos(bytePdf, camposFormateados);
			} else {
				camposFormateados.addAll(obtenerValoresInterviniente(ConstantesPDF._13, interviniente, constanteInterviniente, camposPlantilla));
				bytePdf = pdf.setCampos(bytePdf, camposFormateados);
			}
		}
	}

	private void procesarIntervinientesBaja(boolean transmision) {
		procesarTitularBaja(mensaje, transmision);
		procesarAdquirienteBaja(mensaje, transmision);
	}

	private void procesarTitularBaja(String mensaje, boolean transmision) {
		if (null == detalleBaja.getTitular()) {
			mensaje = "Los datos del titular están vacíos";
		} else {
			if (transmision) {
				procesarIntervinienteBaja(detalleBaja.getTitular(), transmision, ConstantesPDF._TRANSMITENTE);
			} else {
				procesarIntervinienteBaja(detalleBaja.getTitular(), transmision, ConstantesPDF._TITULAR);
			}
		}
		if (detalleBaja.getRepresentanteTitular() != null) {
			if (transmision) {
				procesarRepresentanteBaja(detalleBaja.getRepresentanteTitular(), transmision, ConstantesPDF._REPR_TRANSMITENTE);
			} else {
				procesarRepresentanteBaja(detalleBaja.getRepresentanteTitular(), transmision, ConstantesPDF._REPR_TITULAR);
			}
		}
	}

	private void procesarAdquirienteBaja(String mensaje, boolean transmision) {
		if (null == detalleBaja.getAdquiriente()) {
			mensaje = "Los datos del adquiriente están vacíos";
		} else {
			procesarIntervinienteBaja(detalleBaja.getAdquiriente(), transmision, ConstantesPDF._ADQUIRIENTE);
			// Actualizar el campo Acredita Suficiente Propiedad
			if (camposPlantilla.contains(ConstantesPDF.ID_CV)) {
				camposFormateados.add(new CampoPdfBean(ConstantesPDF.ID_CV, "SI", false, false, ConstantesPDF._9));
				bytePdf = pdf.setCampos(bytePdf, camposFormateados);
			}
		}
		if (detalleBaja.getRepresentanteAdquiriente() != null) {
			procesarRepresentanteBaja(detalleBaja.getRepresentanteAdquiriente(), transmision, ConstantesPDF._REPR_ADQUIRIENTE);
		}
	}

	private void procesarIntervinienteBaja(IntervinienteTraficoDto interviniente, boolean transmision, String constanteInterviniente) {
		if (transmision) {
			camposFormateados.addAll(obtenerValoresIntervinienteBaja(ConstantesPDF._9, interviniente, ConstantesPDF._TRANSMITENTE, camposPlantilla));
			bytePdf = pdf.setCampos(bytePdf, camposFormateados);
		} else {
			camposFormateados.addAll(obtenerValoresIntervinienteBaja(ConstantesPDF._9, interviniente, constanteInterviniente, camposPlantilla));
			camposFormateados.addAll(obtenerValoresDireccionBaja(ConstantesPDF._8, interviniente.getDireccion(), constanteInterviniente, camposPlantilla));
			bytePdf = pdf.setCampos(bytePdf, camposFormateados);
		}
	}

	private void procesarRepresentanteBaja(IntervinienteTraficoDto interviniente, boolean transmision, String constanteInterviniente) {
		if (interviniente != null) {
			camposFormateados.addAll(obtenerValoresIntervinienteBaja(ConstantesPDF._9, interviniente, constanteInterviniente, camposPlantilla));
			bytePdf = pdf.setCampos(bytePdf, camposFormateados);
		}
	}

	private void procesarDatosPresentacion() throws OegamExcepcion {
		camposFormateados = obtenerValoresCampos(ConstantesPDF._12, camposPlantilla, detalleBajaBean.getTramiteTrafico());
		bytePdf = pdf.setCampos(bytePdf, camposFormateados);
	}

	private void procesarDatosPresentacionBaja(int tamanioLetra, ContratoVO contratoVO) throws OegamExcepcion {
		camposFormateados = obtenerValoresCamposBaja(tamanioLetra, camposPlantilla, detalleBaja, contratoVO);
		bytePdf = pdf.setCampos(bytePdf, camposFormateados);
	}

	private boolean esMismoTitular(TramiteTrafBajaDto tramite) {
		boolean mismoTitular = true;
		String dniTitular = null != tramite.getTitular().getPersona().getNif() ? tramite.getTitular().getPersona().getNif() : "";
		for (TramiteTrafBajaDto linea : listaTramitesBaja) {
			if (null == linea.getTitular().getPersona().getNif() || !linea.getTitular().getPersona().getNif().equals(dniTitular)) {
				mismoTitular = false;
				break;
			}
		}
		return mismoTitular;
	}

	private byte[] generarPDFListadoBastidores(TramiteTrafBajaDto tramite, boolean mismoTitular, int numExpedientesEscritos, ResultBean resultadoMetodo, BigDecimal idUsuario) {
		byte[] byteFinal = null;

		int numLinea = 0;
		int numPag = 0;
		float xPosInicial = ConstantesPDF._f73;
		float xPosFinal = ConstantesPDF._f537;
		float yPos = ConstantesPDF._f612;
		// -------------------------------------------------------------------------------------------
		int numExpetientesTotales = listaTramitesBaja.size();
		int numPags = calcularNumPags(numExpetientesTotales);
		bytePdf = pdf.abrirPdf(ruta);
		numPag++;
		ServicioContrato servicioContrato = ContextoSpring.getInstance().getBean(ServicioContrato.class);
		ContratoVO contrato = servicioContrato.getContrato(tramite.getIdContrato());

		procesarCabeceraListadoBastidores(tramite, contrato.getRazonSocial(), numPags, numPag, numExpetientesTotales, contrato.getCif());
		if (mismoTitular) {
			procesarTitularListadoBastidores(tramite);
		}

		for (TramiteTrafBajaDto tramiteLista : listaTramitesBaja) {
			cambiarEstadoTramitesDelListadoMatriculas(tramiteLista, resultadoMetodo, idUsuario);
			if (numLinea > ConstantesPDF._24) {
				if (numExpedientesEscritos == ConstantesPDF._25) {
					byteFinal = bytePdf;
				} else {
					byteFinal = pdf.concatenarPdf(byteFinal, bytePdf);
				}
				numLinea = 0;
				yPos = ConstantesPDF._f612;
				pdf = new PdfMaker();
				bytePdf = pdf.abrirPdf(ruta);
				numPag++;
				procesarCabeceraListadoBastidores(tramiteLista, contrato.getRazonSocial(), numPags, numPag, numExpetientesTotales, contrato.getCif());
				if (mismoTitular) {
					procesarTitularListadoBastidores(tramiteLista);
				}
			}
			numExpedientesEscritos++;
			procesarDatosListadoBastidores(mismoTitular, tramite, numLinea, tramiteLista, numExpedientesEscritos);
			pdf.establecerFuente(ConstantesPDF.HELVETICA, false, false, ConstantesPDF._10);
			bytePdf = pdf.dibujarLinea(bytePdf, ConstantesPDF._1, ConstantesPDF._0_5F, xPosInicial, yPos, xPosFinal, yPos);
			yPos = yPos - ConstantesPDF._18_8F;
			numLinea++;
		}

		if (numExpedientesEscritos <= ConstantesPDF._25) {
			byteFinal = bytePdf;
		} else {
			vaciarDatosListadoBastidores(numLinea);
			byteFinal = pdf.concatenarPdf(byteFinal, bytePdf);
		}

		return byteFinal;
	}

	private void vaciarDatosListadoBastidores(int numLinea) {
		for (int i = numLinea; i <= ConstantesPDF._25; i++) {
			bytePdf = pdf.setCampo(bytePdf, ConstantesPDF.ID_NUM + "." + i, "");
			bytePdf = pdf.setCampo(bytePdf, ConstantesPDF.ID_NUM_EXPEDIENTE + "." + i, "");
			bytePdf = pdf.setCampo(bytePdf, ConstantesPDF.ID_FECHA + "." + i, "");
			bytePdf = pdf.setCampo(bytePdf, ConstantesPDF.ID_DGT + "." + i, "");
			bytePdf = pdf.setCampo(bytePdf, ConstantesPDF.ID_BASTIDOR + "." + i, "");
			bytePdf = pdf.setCampo(bytePdf, ConstantesPDF.ID_NIF + "." + i, "");
			bytePdf = pdf.setCampo(bytePdf, ConstantesPDF.ID_TITULAR + "." + i, "");
		}
	}

	private void procesarCabeceraListadoBastidores(TramiteTrafBajaDto tramite, String razonSocial, int numPags, int numPag, int numExpetientesTotales, String cif) {
		pdf.establecerFuente(ConstantesPDF.HELVETICA, false, false, ConstantesPDF._10);

		bytePdf = pdf.setCampo(bytePdf, ConstantesPDF.ID_TOTAL_PAG, Integer.toString(numPags));
		bytePdf = pdf.setCampo(bytePdf, ConstantesPDF.ID_PAG, Integer.toString(numPag));
		bytePdf = pdf.setCampo(bytePdf, ConstantesPDF.ID_TOTAL_EXPEDIENTES, Integer.toString(numExpetientesTotales));
		bytePdf = pdf.setCampo(bytePdf, ConstantesPDF.ID_CODIGO_COLEGIADO, tramite.getNumColegiado());
		bytePdf = pdf.setCampo(bytePdf, ConstantesPDF.ID_GESTORIA, razonSocial);
		bytePdf = pdf.setCampo(bytePdf, ConstantesPDF.ID_NIF_GESTORIA, cif);
		bytePdf = pdf.setCampo(bytePdf, ConstantesPDF.ID_TRAMITE, " BAJA ");
	}

	private void procesarDatosListadoBastidores(boolean mismoTitular, TramiteTrafBajaDto tramite, int numLinea, TramiteTrafBajaDto tramiteLista, int numExpedientesEscritos) {
		bytePdf = pdf.setCampo(bytePdf, ConstantesPDF.ID_NUM + "." + numLinea, Integer.toString(numExpedientesEscritos));
		pdf.establecerFuente(ConstantesPDF.HELVETICA, false, false, mismoTitular ? ConstantesPDF._10 : ConstantesPDF._8);
		bytePdf = pdf.setCampo(bytePdf, ConstantesPDF.ID_NUM_EXPEDIENTE + "." + numLinea, tramiteLista.getNumExpediente().toString());
		bytePdf = pdf.setCampo(bytePdf, ConstantesPDF.ID_FECHA + "." + numLinea, tramiteLista.getFechaPresentacion().toString());
		if (null != tramiteLista.getRespuesta() && "" != tramiteLista.getRespuesta()) {
			pdf.establecerFuente(ConstantesPDF.HELVETICA, false, false, ConstantesPDF._6);
			bytePdf = pdf.setCampo(bytePdf, ConstantesPDF.ID_DGT + "." + numLinea, tramiteLista.getRespuesta());
		} else {
			pdf.establecerFuente(ConstantesPDF.HELVETICA, false, false, ConstantesPDF._6);
			bytePdf = pdf.setCampo(bytePdf, ConstantesPDF.ID_DGT + "." + numLinea, EstadoTramiteTrafico.convertirTexto(tramiteLista.getEstado()));
		}
		pdf.establecerFuente(ConstantesPDF.HELVETICA, false, false, ConstantesPDF._10);
		bytePdf = pdf.setCampo(bytePdf, ConstantesPDF.ID_BASTIDOR + "." + numLinea, tramiteLista.getVehiculoDto().getMatricula());
		if (!mismoTitular) {
			pdf.establecerFuente(ConstantesPDF.HELVETICA, false, false, ConstantesPDF._8);
			bytePdf = pdf.setCampo(bytePdf, ConstantesPDF.ID_NIF + "." + numLinea, tramiteLista.getTitular().getPersona().getNif());
			bytePdf = pdf.setCampo(bytePdf, ConstantesPDF.ID_TITULAR + "." + numLinea, (null != tramiteLista.getTitular().getPersona().getApellido1RazonSocial() ? tramiteLista.getTitular()
					.getPersona().getApellido1RazonSocial() : "") + (null != tramiteLista.getTitular().getPersona().getApellido2() ? " " + tramiteLista.getTitular().getPersona().getApellido2() : "")
					+ (null != tramiteLista.getTitular().getPersona().getNombre() ? ", " + tramiteLista.getTitular().getPersona().getNombre() : ""));
		}
	}

	private void procesarTitularListadoBastidores(TramiteTrafBajaDto tramite) {
		if (null != tramite && null != tramite.getTitular() && null != tramite.getTitular().getPersona()) {
			bytePdf = pdf.setCampo(bytePdf, ConstantesPDF.ID_NIF, tramite.getTitular().getPersona().getNif());
			bytePdf = pdf.setCampo(bytePdf, ConstantesPDF.ID_TITULAR, (null != tramite.getTitular().getPersona().getApellido1RazonSocial() ? tramite.getTitular().getPersona().getApellido1RazonSocial()
					: "") + (null != tramite.getTitular().getPersona().getApellido2() ? " " + tramite.getTitular().getPersona().getApellido2() : "") + (null != tramite.getTitular().getPersona()
							.getNombre() ? ", " + tramite.getTitular().getPersona().getNombre() : ""));
		}
	}

	private void cambiarEstadoTramitesDelListadoMatriculas(TramiteTrafBajaDto tramite, ResultBean resultadoMetodo, BigDecimal idUsuario) {
		if (tramite != null && tramite.getMotivoBaja() != null) {
			String estado = tramite.getEstado();
			if (MotivoBaja.DefE.getValorEnum().equals(tramite.getMotivoBaja()) || MotivoBaja.DefTC.getValorEnum().endsWith(tramite.getMotivoBaja()) ||
					MotivoBaja.TempR.getValorEnum().endsWith(tramite.getMotivoBaja()) ||
					MotivoBaja.TempV.getValorEnum().equals(tramite
					.getMotivoBaja()) || MotivoBaja.DefA.getValorEnum().equals(tramite.getMotivoBaja()) || MotivoBaja.ExpCTIT.getValorEnum().equals(tramite.getMotivoBaja()) || MotivoBaja.TranCTIT
							.getValorEnum().equals(tramite.getMotivoBaja()) || MotivoBaja.DefV.getValorEnum().equals(tramite.getMotivoBaja()) || MotivoBaja.DefCT.getValorEnum().equals(tramite
									.getMotivoBaja())) {
				if (estado.equals(EstadoTramiteTrafico.Finalizado_Excel.getValorEnum())) {
					cambiarEstadoTramiteMostrandoResultadoParaImprimir(tramite, resultadoMetodo, EstadoTramiteTrafico.Finalizado_Excel_Impreso, idUsuario);
				}
			} else if (estado.equals(EstadoTramiteTrafico.Validado_PDF.getValorEnum())) {
				cambiarEstadoTramiteMostrandoResultadoParaImprimir(tramite, resultadoMetodo, EstadoTramiteTrafico.Finalizado_PDF, idUsuario);
			}
		}
	}

	private void cambiarEstadoTramiteMostrandoResultadoParaImprimir(TramiteTrafBajaDto tramite, ResultBean resultadoMetodo, EstadoTramiteTrafico estado, BigDecimal idUsuario) {
		ServicioTramiteTrafico servicioTramiteTrafico = ContextoSpring.getInstance().getBean(ServicioTramiteTrafico.class);
		resultadoMetodo = servicioTramiteTrafico.cambiarEstadoConEvolucion(tramite.getNumExpediente(), EstadoTramiteTrafico.convertir(tramite.getEstado()), estado, true,
				TextoNotificacion.Cambio_Estado.getNombreEnum(), idUsuario);
		if (resultadoMetodo.getError()) {
			resultadoMetodo.setMensaje(" realizando el cambio del estado del trámite impreso " + tramite.getNumExpediente());
			resultadoMetodo.setError(true);
		}
	}

	private void procesarTipoTransferencia(String motivoBaja, String cet) {
		String tipoTransferencia = (null != motivoBaja) ? motivoBaja : "";
		camposFormateados.add(new CampoPdfBean(ConstantesPDF.ID_TIPO_TRANSFERENCIA, tipoTransferencia, false, false, ConstantesPDF._9));

		if (cet == null || cet.isEmpty()) {
			cet = "00000000";
		}

		String transfer_type = "Solicitud Baja Temporal por Transferencia";
		camposFormateados.add(new CampoPdfBean(ConstantesPDF.ID_CET, cet, false, false, ConstantesPDF._9));
		camposFormateados.add(new CampoPdfBean(ConstantesPDF.ID_TIPO_TRANSFERENCIA, transfer_type.toUpperCase(), true, false, ConstantesPDF._11));
		camposFormateados.add(new CampoPdfBean(ConstantesPDF.ID_TIPO_SOLICITUD, transfer_type, false, false, ConstantesPDF._7));
		camposFormateados.add(new CampoPdfBean(ConstantesPDF.ID_TIPO_TRANSFERENCIA_2, transfer_type, false, false, ConstantesPDF._9));
		camposFormateados.add(new CampoPdfBean(ConstantesPDF.ID_CV, "SI", true, false, ConstantesPDF._15));
	}

	/*
	 * TODO private void procesarStatusTelematico() { if (rejectedCTIT(detalleBajaBean.getTramiteTrafico())) { String respuesta = detalleBajaBean.getTramiteTrafico().getRespuesta(); if (respuesta.length() > 85) { respuesta = respuesta.substring(0, 85) + "..."; } camposFormateados.add(new
	 * CampoPdfBean(ConstantesPDF.ID_STATUS_TRANSFERIBLE, respuesta, false, false, ConstantesPDF._9)); bytePdf = pdf.setCampos(bytePdf, camposFormateados); } } private void procesarStatusTelematicoBaja() { if (rejectedCTITBaja(detalleBaja)) { String respuesta = detalleBaja.getRespuesta(); if
	 * (respuesta.length() > 85) { respuesta = respuesta.substring(0, 85) + "..."; } camposFormateados.add(new CampoPdfBean(ConstantesPDF.ID_STATUS_TRANSFERIBLE, respuesta, false, false, ConstantesPDF._9)); bytePdf = pdf.setCampos(bytePdf, camposFormateados); } }
	 */

	public void procesarGESTRevisarBaja(GestBean bean, String valorGEST) {
		boolean ivtm = false;
		CampoPdfBean campo = null;
		campo = new CampoPdfBean(ConstantesPDF.ID_GEST, valorGEST, true, false, 12);
		camposFormateados.add(campo);

		int lCargas = 0;
		int lEmbargos = 0;
		if (bean.getCargas() != null && !bean.getCargas().isEmpty()) {
			lCargas = bean.getCargas().size();
		}
		if (bean.getEmbargos() != null && !bean.getEmbargos().isEmpty()) {
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

		procesaCargas(bean, lCargas, separacionLineas, tamFuente, ivtm, valorGEST);
		procesarEmbargos(bean, lCargas, separacionLineas, tamFuente, ivtm, valorGEST);
	}

	public void procesaCargas(GestBean bean, int lCargas, float separacionLineas, float tamFuente, boolean ivtm, String valorGEST) {
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
				bytePdf = pdf.escribirTextoColumna(bytePdf, buffer1.toString().replace('#', 'Ñ'), 1, 70, 172, 600, 10, separacionLineas);
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

				bytePdf = pdf.escribirTextoColumna(bytePdf, buffer1.toString(), 1, 70, 172, 600, 10, separacionLineas);
				bytePdf = pdf.escribirTextoColumna(bytePdf, buffer2.toString(), 1, 120, 172, 600, 10, separacionLineas);
				bytePdf = pdf.escribirTextoColumna(bytePdf, buffer3.toString(), 1, 180, 172, 600, 10, separacionLineas);
				bytePdf = pdf.escribirTextoColumna(bytePdf, buffer4.toString(), 1, 235, 172, 600, 10, separacionLineas);

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
					bytePdf = pdf.escribirTextoColumna(bytePdf, buffer1.toString(), 1, 70, 172, 600, 10, separacionLineas + i * 10);
					bytePdf = pdf.escribirTextoColumna(bytePdf, buffer2.toString(), 1, 120, 172, 600, 10, separacionLineas + i * 10);
					bytePdf = pdf.escribirTextoColumna(bytePdf, buffer3.toString(), 1, 180, 172, 600, 10, separacionLineas + i * 10);
					bytePdf = pdf.escribirTextoColumna(bytePdf, buffer4.toString(), 1, 235, 172, 600, 10, separacionLineas + i * 10);
					i++;
				}
			}
		}
	}

	public void procesarEmbargos(GestBean bean, int lCargas, float separacionLineas, float tamFuente, boolean ivtm, String valorGEST) {
		if (bean.getEmbargos() != null && !bean.getEmbargos().isEmpty()) {
			// Añado una línea de espacio entre las limitaciones y los embargos
			if (lCargas != 0) {
				lCargas++;
			}

			// Controlamos si también hay cargas por IVTM.
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

			bytePdf = pdf.escribirTextoColumna(bytePdf, buffer1.toString(), 1, 70, 172, 600, 10, separacionLineas);
			bytePdf = pdf.escribirTextoColumna(bytePdf, buffer2.toString(), 1, 120, 172, 600, 10, separacionLineas);
			bytePdf = pdf.escribirTextoColumna(bytePdf, buffer3.toString(), 1, 180, 172, 600, 10, separacionLineas);
			bytePdf = pdf.escribirTextoColumna(bytePdf, buffer4.toString(), 1, 235, 172, 600, 10, separacionLineas);
			bytePdf = pdf.escribirTextoColumna(bytePdf, buffer5.toString(), 1, 315, 172, 600, 10, separacionLineas);

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
				bytePdf = pdf.escribirTextoColumna(bytePdf, buffer1.toString(), 1, 70, 172, 600, 10, separacionLineas + i * 10);
				bytePdf = pdf.escribirTextoColumna(bytePdf, buffer2.toString(), 1, 120, 172, 600, 10, separacionLineas + i * 10);
				bytePdf = pdf.escribirTextoColumna(bytePdf, buffer3.toString(), 1, 180, 172, 600, 10, separacionLineas + i * 10);
				bytePdf = pdf.escribirTextoColumna(bytePdf, buffer4.toString(), 1, 235, 172, 600, 10, separacionLineas + i * 10);
				bytePdf = pdf.escribirTextoColumna(bytePdf, buffer5.toString(), 1, 315, 172, 600, 10, separacionLineas + i * 10);
				i++;
			}
		}
	}

	public ResultBean imprimirInformeBajaPorExpedientes(String[] numsExpedientes, TipoTramiteTrafico tipoTramite) {
		ResultBean resultado = new ResultBean(false);
		ZipOutputStream zip = null;
		FileOutputStream out = null;
		List<String> listaMensajes = new ArrayList<>();
		String url = null;
		byte[] byte1 = null;
		try {
			url = gestorPropiedades.valorPropertie("RUTA_ARCHIVOS_TEMP") + "zip" + System.currentTimeMillis();
			out = new FileOutputStream(url);
			zip = new ZipOutputStream(out);
			for (String numExpediente : numsExpedientes) {
				ResultBean resultBean = imprimirInformeBajaPorExpediente(numExpediente, tipoTramite);
				if (resultBean != null && !resultado.getError()) {
					String nombreFichero = (String) resultBean.getAttachment(ResultBean.NOMBRE_FICHERO);
					byte[] byte2 = (byte[]) resultBean.getAttachment(ResultBean.TIPO_PDF);
					ZipEntry zipEntry = new ZipEntry(nombreFichero);
					zip.putNextEntry(zipEntry);
					zip.write(byte2);
					zip.closeEntry();
				} else {
					listaMensajes.add(resultBean.getListaMensajes().get(0));
				}
			}
			resultado.setListaMensajes(listaMensajes);
			zip.close();
			File fichero = new File(url);
			resultado.addAttachment(ResultBean.NOMBRE_FICHERO, TipoImpreso.InformeBajaTelematica.getNombreEnum() + ConstantesGestorFicheros.EXTENSION_ZIP);

			byte1 = gestorDocumentos.transformFiletoByte(fichero);
			if (fichero.delete()) {
				log.info("Se ha eliminado correctamente el fichero");
			} else {
				log.info("No se ha eliminado el fichero");
			}
		} catch (FileNotFoundException e) {
			log.error("Ha sucedido un error a la hora de imprimir los informes de baja, error: ", e);
			resultado = new ResultBean(true, "Ha sucedido un error a la hora de imprimir los informes de baja");
		} catch (IOException e) {
			log.error("Ha sucedido un error a la hora de imprimir los informes de baja, error: ", e);
			resultado = new ResultBean(true, "Ha sucedido un error a la hora de imprimir los informes de baja");
		} finally {
			try {
				out.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			File eliminarZip = new File(url);
			eliminarZip.delete();
		}

		if (byte1 != null) {
			resultado.addAttachment(ResultBean.TIPO_PDF, byte1);
		} else {
			resultado = new ResultBean(true, "Ha sucedido un error a la hora de generar el zip con los informes de bajas");
		}
		return resultado;
	}

	public ResultBean imprimirInformeBajaPorExpediente(String numExpediente, TipoTramiteTrafico tipoTramite) {
		ResultBean resultado = null;
		byte[] byte1 = null;
		Fecha fecha;
		try {
			if (numExpediente.contains("_")) {
				fecha = Utilidades.transformExpedienteFecha(numExpediente.split("_")[1]);
			} else {
				fecha = Utilidades.transformExpedienteFecha(numExpediente);
			}
			String nombreFichero = TipoImpreso.InformeBajaTelematica.getNombreEnum() + "_" + numExpediente;
			FileResultBean fileResultBean = gestorDocumentos.buscarFicheroPorNombreTipo(ConstantesGestorFicheros.TRAMITAR_BTV, ConstantesGestorFicheros.INFORMES, fecha, nombreFichero,
					ConstantesGestorFicheros.EXTENSION_PDF);
			if (fileResultBean.getFile() != null) {
				byte1 = gestorDocumentos.transformFiletoByte(fileResultBean.getFile());
				resultado = new ResultBean(false);
				resultado.addAttachment(ResultBean.NOMBRE_FICHERO, nombreFichero + ConstantesGestorFicheros.EXTENSION_PDF);
			} else if (FileResultStatus.ON_DEMAND_FILE.equals(fileResultBean.getStatus())) {
				resultado = new ResultBean(true, "No se ha podido recuperar el documento para el número Expediente " + numExpediente + ". " + fileResultBean.getMessage());
			} else {
				resultado = new ResultBean(true, "No se ha podido recuperar el documento para el número Expediente " + numExpediente);
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de imprimir el informe de baja, error: ", e);
			resultado = new ResultBean(true, "Ha sucedido un error a la hora de imprimir el informe de baja");
		} catch (OegamExcepcion e) {
			log.error("Ha sucedido un error a la hora de imprimir el informe de baja, error: ", e);
			resultado = new ResultBean(true, "Ha sucedido un error a la hora de imprimir el informe de baja");
		}

		if (byte1 != null) {
			resultado.addAttachment(ResultBean.TIPO_PDF, byte1);
		}
		return resultado;
	}

	public ResultBean imprimirDeclaracionJurada(BigDecimal numExpediente, BigDecimal idUsuario, String interviniente, String tipoImpreso) throws Throwable {
		ResultBean result = null;

		obtenerDetalleTramiteBaja(numExpediente.toString(), tipoImpreso);

		if (detalleBaja != null) {
			result = imprimirDeclaracion(numExpediente.toString(), idUsuario, tipoImpreso, interviniente);
		} else {
			result = new ResultBean(true, "Los datos del trámite están vacíos. Puede que no esté completo el trámite impreso.");
		}
		return result;
	}

	private ResultBean imprimirDeclaracion(String numExpediente, BigDecimal idUsuario, String tipoImpreso, String interviniente) {
		ruta = gestorPropiedades.valorPropertie(ConstantesPDF.RUTA_PLANTILLAS);
		ruta += tipoImpreso;
		ResultBean result = null;
		// -- Inicio comprobación de datos sensibles
		try {
			IntervinienteTraficoDto tipoRepresenta = new IntervinienteTraficoDto();
			IntervinienteTraficoDto tipoRepresentanteRepresenta = new IntervinienteTraficoDto();

			if ("Adquiriente".equals(interviniente)) {
				tipoRepresenta = detalleBaja.getAdquiriente();
				if (tipoRepresenta != null) {
					tipoRepresenta.getPersona().setDireccionDto(detalleBaja.getAdquiriente().getDireccion());
				}
				tipoRepresentanteRepresenta = detalleBaja.getRepresentanteAdquiriente();
			} else if ("Titular".equals(interviniente)) {
				tipoRepresenta = detalleBaja.getTitular();
				if (tipoRepresenta != null) {
					tipoRepresenta.getPersona().setDireccionDto(detalleBaja.getTitular().getDireccion());
				}
				tipoRepresentanteRepresenta = detalleBaja.getRepresentanteTitular();
			}

			result = comprobarTipoRepresenta(tipoRepresenta, interviniente);
			if (result != null) {
				return result;
			}

			result = comprobarTipoRepresentanteRepresenta(tipoRepresenta, tipoRepresentanteRepresenta, interviniente);
			if (result != null) {
				return result;
			}

			bytePdf = null;
			camposFormateados = new ArrayList<CampoPdfBean>();

			UtilResources util = new UtilResources();
			ruta = util.getFilePath(ruta);

			pdf = new PdfMaker();
			bytePdf = pdf.abrirPdf(ruta);

			camposPlantilla = pdf.getAllFields(bytePdf);

			camposFormateados.addAll(obtenerValoresDeclaracionJurada(ConstantesPDF._11, tipoRepresenta, tipoRepresentanteRepresenta, camposPlantilla));

			bytePdf = pdf.setCampos(bytePdf, camposFormateados);

			if (bytePdf == null) {
				result = new ResultBean(true);
				result.setMensaje("No se ha cargado la plantilla de la declaración.");
				return result;
			}
			result = new ResultBean(false);
			result.addAttachment("pdf", bytePdf);

		} catch (Exception e) {
			result = new ResultBean(true);
			result.setMensaje("Se ha producido un error a la hora de imprimir el trámite: " + numExpediente);
			log.error("Se ha producido un error a la hora de imprimir el trámite: " + numExpediente + ", error: " + e.getMessage());
		}
		return result;
	}

	private Collection<? extends CampoPdfBean> obtenerValoresDeclaracionJurada(Integer tamCampos, IntervinienteTraficoDto interviniente, IntervinienteTraficoDto repInterviniente,
			Set<String> camposPlantilla) {
		ArrayList<CampoPdfBean> camposFormateados = new ArrayList<>();
		CampoPdfBean campoAux = null;

		PersonaDto persona = interviniente.getPersona();
		String provincia = "";

		ServicioDireccion servicioDireccion = ContextoSpring.getInstance().getBean(ServicioDireccion.class);
		Conversiones conversiones = ContextoSpring.getInstance().getBean(Conversiones.class);

		// Si es persona jurídica se cogen los datos del representante y como entidad la razón social.
		if (null != persona && null != persona.getSexo() && persona.getSexo().equals(PERSONA_JURIDICA)) {
			// Declara entidad
			if (camposPlantilla.contains(ConstantesPDF.ID_ENTIDAD)) {
				campoAux = new CampoPdfBean(ConstantesPDF.ID_ENTIDAD, persona.getApellido1RazonSocial(), false, false, tamCampos);
				camposFormateados.add(campoAux);
			}

			// Datos de la persona física
			PersonaDto personaRepresentante = repInterviniente.getPersona();

			if (camposPlantilla.contains(ConstantesPDF.ID_NOMBRE_GESTOR)) {
				String nombreApellidos = (null != personaRepresentante.getNombre() ? personaRepresentante.getNombre() : "") + " " + (null != personaRepresentante.getApellido1RazonSocial()
						? personaRepresentante.getApellido1RazonSocial() : "") + " " + (null != personaRepresentante.getApellido2() ? personaRepresentante.getApellido2() : "");

				campoAux = new CampoPdfBean(ConstantesPDF.ID_NOMBRE_GESTOR, nombreApellidos, false, false, tamCampos);
				camposFormateados.add(campoAux);
			}

			if (camposPlantilla.contains(ConstantesPDF.ID_NIF)) {
				campoAux = new CampoPdfBean(ConstantesPDF.ID_NIF, personaRepresentante.getNif(), false, false, tamCampos);
				camposFormateados.add(campoAux);
			}

		} else {
			// Si la persona es física el campo entidad deberá ir vacío.
			if (camposPlantilla.contains(ConstantesPDF.ID_ENTIDAD)) {
				campoAux = new CampoPdfBean(ConstantesPDF.ID_ENTIDAD, "", false, false, tamCampos);
				camposFormateados.add(campoAux);
			}

			if (camposPlantilla.contains(ConstantesPDF.ID_NOMBRE_GESTOR)) {
				String nombreApellidos = (null != persona.getNombre() ? persona.getNombre() : "") + " " + (null != persona.getApellido1RazonSocial() ? persona.getApellido1RazonSocial() : "") + " "
						+ (null != persona.getApellido2() ? persona.getApellido2() : "");

				campoAux = new CampoPdfBean(ConstantesPDF.ID_NOMBRE_GESTOR, nombreApellidos, false, false, tamCampos);
				camposFormateados.add(campoAux);
			}

			if (camposPlantilla.contains(ConstantesPDF.ID_NIF)) {
				campoAux = new CampoPdfBean(ConstantesPDF.ID_NIF, persona.getNif(), false, false, tamCampos);
				camposFormateados.add(campoAux);
			}
		}

		String nombreVia = conversiones.ajustarCamposIne(persona.getDireccionDto().getNombreVia());
		String municipio = "";
		if (nombreVia != null && !nombreVia.isEmpty()) {
			municipio = servicioDireccion.obtenerNombreMunicipio(persona.getDireccionDto().getIdMunicipio(), persona.getDireccionDto().getIdProvincia());
		}

		if (camposPlantilla.contains(ConstantesTrafico.ID_CALLE)) {
			campoAux = new CampoPdfBean(ConstantesTrafico.ID_CALLE, nombreVia, false, false, tamCampos);
			camposFormateados.add(campoAux);
		}
		if (camposPlantilla.contains(ConstantesTrafico.ID_MUNICIPIO)) {
			campoAux = new CampoPdfBean(ConstantesTrafico.ID_MUNICIPIO, municipio, false, false, tamCampos);
			camposFormateados.add(campoAux);
		}

		if (camposPlantilla.contains(ConstantesTrafico.ID_PROVINCIA)) {
			provincia = servicioDireccion.obtenerNombreProvincia(persona.getDireccionDto().getIdProvincia());
			campoAux = new CampoPdfBean(ConstantesTrafico.ID_PROVINCIA, provincia, false, false, tamCampos);
			camposFormateados.add(campoAux);
		}

		if (camposPlantilla.contains(ConstantesPDF.ID_NUMERO)) {
			campoAux = new CampoPdfBean(ConstantesPDF.ID_NUMERO, persona.getDireccionDto().getNumero(), false, false, tamCampos);
			camposFormateados.add(campoAux);
		}

		if (camposPlantilla.contains(ConstantesPDF.ID_CODIGO)) {
			campoAux = new CampoPdfBean(ConstantesPDF.ID_CODIGO, persona.getDireccionDto().getCodPostal(), false, false, tamCampos);
			camposFormateados.add(campoAux);
		}

		if (camposPlantilla.contains(ConstantesPDF.ID_DOMICILIO_ENTIDAD)) {
			campoAux = new CampoPdfBean(ConstantesPDF.ID_DOMICILIO_ENTIDAD, nombreVia, false, false, tamCampos);
			camposFormateados.add(campoAux);
		}

		if (detalleBaja.getVehiculoDto() != null && detalleBaja.getVehiculoDto().getMatricula() != null) {
			camposFormateados.add(new CampoPdfBean(ConstantesPDF.ID_MATRICULA, detalleBaja.getVehiculoDto().getMatricula(), false, false, ConstantesPDF._11));
		}

		// Firma
		// Obtener una fecha a partir de su representación
		DateTime dt = new DateTime();
		String dia = dt.toString("dd");
		String mes = dt.toString("MMMM").toUpperCase();
		String anio = dt.toString("yyyy");

		if (camposPlantilla.contains(ConstantesPDF.ID_LUGAR)) {
			campoAux = new CampoPdfBean(ConstantesPDF.ID_LUGAR, provincia, false, false, tamCampos);
			camposFormateados.add(campoAux);
		}

		if (camposPlantilla.contains(ConstantesPDF.ID_DIA)) {
			campoAux = new CampoPdfBean(ConstantesPDF.ID_DIA, dia, false, false, tamCampos);
			camposFormateados.add(campoAux);
		}

		if (camposPlantilla.contains(ConstantesPDF.ID_MES)) {
			campoAux = new CampoPdfBean(ConstantesPDF.ID_MES, mes, false, false, tamCampos);
			camposFormateados.add(campoAux);
		}

		if (camposPlantilla.contains(ConstantesPDF.ID_ANIO)) {
			campoAux = new CampoPdfBean(ConstantesPDF.ID_ANIO, anio, false, false, tamCampos);
			camposFormateados.add(campoAux);
		}

		return camposFormateados;
	}

	private ResultBean comprobarTipoRepresenta(IntervinienteTraficoDto tipoRepresenta, String interviniente) {
		ResultBean resultado = null;
		if (tipoRepresenta == null || tipoRepresenta.getPersona() == null || tipoRepresenta.getPersona().getNif() == null || tipoRepresenta.getPersona().getNif().isEmpty() || tipoRepresenta
				.getPersona().getApellido1RazonSocial() == null || tipoRepresenta.getPersona().getApellido1RazonSocial().isEmpty() || tipoRepresenta.getPersona().getDireccionDto() == null) {
			resultado = new ResultBean(true);
			resultado.setMensaje("No se han recuperado los datos mínimos requeridos del " + interviniente);
		}
		return resultado;
	}

	private ResultBean comprobarTipoRepresentanteRepresenta(IntervinienteTraficoDto tipoRepresenta, IntervinienteTraficoDto tipoRepresentanteRepresenta, String interviniente) {
		ResultBean resultado = null;
		if ("X".equalsIgnoreCase(tipoRepresenta.getPersona().getSexo())) {
			// El interviniente es persona jurídica. Verifica los datos mínimos de su representante:
			if (tipoRepresentanteRepresenta == null || tipoRepresentanteRepresenta.getPersona() == null || tipoRepresentanteRepresenta.getPersona().getNif() == null || tipoRepresentanteRepresenta
					.getPersona().getNif().isEmpty() || tipoRepresentanteRepresenta.getPersona().getApellido1RazonSocial() == null || tipoRepresentanteRepresenta.getPersona().getApellido1RazonSocial()
							.isEmpty()) {
				resultado = new ResultBean(true);
				resultado.setMensaje("No se han recuperado los datos mínimos requeridos del " + " representante del " + interviniente);
			}
		}
		return resultado;
	}

	public TramiteTrafBajaDto getDetalleBaja() {
		return detalleBaja;
	}

	public void setDetalleBaja(TramiteTrafBajaDto detalleBaja) {
		this.detalleBaja = detalleBaja;
	}

	public TramiteTraficoBajaBean getDetalleBajaBean() {
		return detalleBajaBean;
	}

	public void setDetalleBajaBean(TramiteTraficoBajaBean detalleBajaBean) {
		this.detalleBajaBean = detalleBajaBean;
	}

	public ArrayList<TramiteTrafBajaDto> getListaTramitesBaja() {
		return listaTramitesBaja;
	}

	public void setListaTramitesBaja(ArrayList<TramiteTrafBajaDto> listaTramitesBaja) {
		this.listaTramitesBaja = listaTramitesBaja;
	}

	public ArrayList<CampoPdfBean> getCamposFormateados() {
		return camposFormateados;
	}

	public void setCamposFormateados(ArrayList<CampoPdfBean> camposFormateados) {
		this.camposFormateados = camposFormateados;
	}

	public Set<String> getCamposPlantilla() {
		return camposPlantilla;
	}

	public void setCamposPlantilla(Set<String> camposPlantilla) {
		this.camposPlantilla = camposPlantilla;
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

	public byte[] getBytePdf() {
		return bytePdf;
	}

	public void setBytePdf(byte[] bytePdf) {
		this.bytePdf = bytePdf;
	}

	public PdfMaker getPdf() {
		return pdf;
	}

	public void setPdf(PdfMaker pdf) {
		this.pdf = pdf;
	}

	public String getMensaje() {
		return mensaje;
	}

	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}
}