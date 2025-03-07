package trafico.tags;

import java.util.ArrayList;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import org.gestoresmadrid.oegamComun.acceso.service.impl.UtilesColegiado;
import org.gestoresmadrid.utilidades.components.GestorPropiedades;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import trafico.beans.BotonPaginaConsultaBean;

/**
 * Para añadir un nuevo botón a la página de la consulta de trámites de tráfico, solo hay que instanciar un nuevo BotonPaginaConsultaBean en el método getBotones()...
 */
public class BotonesPaginaConsultaTag extends TagSupport {

	private static final long serialVersionUID = 1L;

	private static final String TR_OPEN = "<tr>";
	private static final String TR_END = "</tr>";
	private static final String TD_OPEN = "<td>";
	private static final String TD_END = "</td>";
	private static final String TABLE_END = "</table>";

	private static final String CLASE_CSS_TABLE = "acciones";

//	private final String REF_PROP_METODOS_VALIDADORES_PERMISO = ".metodos.validadores.permiso";
//	private final String REF_PROP_USUARIOS_AUTORIZADOS = ".usuarios.autorizados";
//	private final String DELIMITADOR_ELEMENTOS_PROPIEDADES = ",";

	@Autowired
	private GestorPropiedades gestorPropiedades;

	@Autowired
	private UtilesColegiado utilesColegiado;

	public BotonesPaginaConsultaTag() {
		super();
		SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
	}

	@Override
	public int doStartTag() throws JspException {
		try {
			String aperturaTabla = "<table class=\"" + CLASE_CSS_TABLE + "\">";
			ArrayList<String> listaTds = new ArrayList<>();
			StringBuilder tablaCompleta = new StringBuilder();
			tablaCompleta.append(aperturaTabla);
			ArrayList<BotonPaginaConsultaBean> listaBotones = getBotones();

			for (BotonPaginaConsultaBean boton : listaBotones) {
//				boolean autorizado = usuarioAutorizado(boton.getName());
//				if (autorizado) {
					listaTds.add(TD_OPEN + boton.toString() + TD_END);
//				}
			}

			int numBotones = listaTds.size();

			tablaCompleta.append(TR_OPEN);
			for (int i = 0; i < numBotones; i++) {
				if (i > 0 && i % 5 == 0) {
					tablaCompleta.append(TR_END + TR_OPEN);
				}
				tablaCompleta.append(listaTds.get(i));
			}
			tablaCompleta.append(TR_END);

			tablaCompleta.append(TABLE_END);
			super.pageContext.getOut().print(tablaCompleta.toString());

		} catch (Exception ex) {
			throw new JspException(ex);
		}
		return SKIP_BODY;
	}

	/**
	 * El constructor de BotonPaginaConsultaBean debe recibir: name, id, value y onclick. Los demas valores se establecen por defecto pero se pueden cambiar con el set correspondiente Ids usuarios autorizados : en los properties : nombreBoton + '.usuarios.autorizados=' + lista ids usuarios
	 * autorizados separados por coma (',') Métodos validadores de permiso : en los properties : nombreBoton + '.metodos.validadores.permiso=' + lista métodos validadores de permiso separados por coma (',') Los métodos de validación de permisos deben devolver un boolean...
	 */
	private ArrayList<BotonPaginaConsultaBean> getBotones() {

		ArrayList<BotonPaginaConsultaBean> listaBotones = new ArrayList<>();

		if (!utilesColegiado.sinBotonesConsultaTramiteTrafico()) {
			// BOTÓN 'Validar'
			BotonPaginaConsultaBean botonValidar = new BotonPaginaConsultaBean("bValidarTramites", "bValidarTramites", "Validar", "return validarTramite(this);");
			listaBotones.add(botonValidar);

			// BOTÓN 'Comprobar'
			BotonPaginaConsultaBean botonComprobar = new BotonPaginaConsultaBean("bComprobar", "bComprobar", "Comprobar", "return comprobarBloque(this);");
			listaBotones.add(botonComprobar);

			// BOTÓN 'Duplicar'
			BotonPaginaConsultaBean botonDuplicar = new BotonPaginaConsultaBean("bDuplicar", "bDuplicar", "Duplicar", "return duplicarTramite(this);");
			listaBotones.add(botonDuplicar);

			// BOTÓN 'Eliminar'
			BotonPaginaConsultaBean botonEliminar = new BotonPaginaConsultaBean("bEliminar", "bEliminar", "Eliminar", "return eliminarTramite(this);");
			listaBotones.add(botonEliminar);

			// BOTÓN 'Tramitar Telemáticamente'
			BotonPaginaConsultaBean botonTramitarTelematicamente = new BotonPaginaConsultaBean("bTramitar", "bTramitar", "Tramitar Telemáticamente", "return preTramitarTelematicoTramite(this);");
			listaBotones.add(botonTramitarTelematicamente);

			String justificantesProfNuevos = gestorPropiedades.valorPropertie("justificantes.profesional.nuevos");
			if ("SI".equals(justificantesProfNuevos)) {
				// BOTÓN 'Generar Justificante'
				BotonPaginaConsultaBean botonGenerarJustificante = new BotonPaginaConsultaBean("bGenerarJustifProf", "idBGenerarJustifProf", "Generar Justificante", "return generarJustifProf(this);");
				listaBotones.add(botonGenerarJustificante);

				// BOTÓN 'Imprimir Justificante'
				BotonPaginaConsultaBean botonImprimirJustificante = new BotonPaginaConsultaBean("bImprimirJustifProf", "bImprimirJustifProf", "Imprimir Justificante",
						"return imprimirJustifProf(this);");
				listaBotones.add(botonImprimirJustificante);
			} else {
				// BOTÓN 'Generar Justificante'
				BotonPaginaConsultaBean botonGenerarJustificante = new BotonPaginaConsultaBean("bGenerarJustificante", "bGenerarJustificante", "Generar Justificante",
						"return generarJustificantesProfesionales(this);");
				listaBotones.add(botonGenerarJustificante);

				// BOTÓN 'Imprimir Justificante'
				BotonPaginaConsultaBean botonImprimirJustificante = new BotonPaginaConsultaBean("bImprimirJustificante", "bImprimirJustificante", "Imprimir Justificante",
						"return imprimirJustificantesProfesionales(this);");
				listaBotones.add(botonImprimirJustificante);
			}

			// BOTÓN 'Imprimir Documentos'
			BotonPaginaConsultaBean botonImprimirDocumentos = new BotonPaginaConsultaBean("bImpresionDocumentos", "bImpresionDocumentos", "Imprimir Documentos",
					"return impresionVariosTramites(this);");
			listaBotones.add(botonImprimirDocumentos);

			// BOTÓN 'Generar Certificado Tasas'
			BotonPaginaConsultaBean botonGenerarCertificadoTasas = new BotonPaginaConsultaBean("bGenerarCertificado", "bGenerarCertificado", "Generar Certificado Tasas",
					"return generarCertificados(this);");
			listaBotones.add(botonGenerarCertificadoTasas);

//			String ocultarBotonExportar = gestorPropiedades.valorPropertie("ocultar.boton.exportar");
//			if (!"SI".equals(ocultarBotonExportar)) {
//				// BOTÓN 'Exportar Datos'
//				BotonPaginaConsultaBean botonExportarDatos = new BotonPaginaConsultaBean("bExportarDatos", "bExportarDatos", "Exportar Datos", "abrirVentanaSeleccionTipo();");
//				listaBotones.add(botonExportarDatos);
//			}

			// BOTÓN 'Envío a Excel'
			if (utilesColegiado.esColegiadoEnvioExcel()) {
				BotonPaginaConsultaBean botonEnvioAExcel = new BotonPaginaConsultaBean("bPdteExcel", "bPdteExcel", "Envío a Excel", "return pendientesEnvioExcel(this);");
				listaBotones.add(botonEnvioAExcel);
			}

			// BOTÓN 'Generar XML 620'
			if (utilesColegiado.isColegiadoMadrid()) {
				BotonPaginaConsultaBean botonGenerarXml620 = new BotonPaginaConsultaBean("bGenerarXML", "bGenerarXML", "Generar XML 620", "return generarXML620Tramites(this);");
				listaBotones.add(botonGenerarXml620);
			}

			// BOTÓN 'Solicitar Placas'
			BotonPaginaConsultaBean botonSolicitarPlacas = new BotonPaginaConsultaBean("bSolicitarPlacas", "bSolicitarPlacas", "Solicitar Placas", "solicitarPlacasMatricula(this.form.id)");
			listaBotones.add(botonSolicitarPlacas);

			// BOTÓN 'Generar Documento Base'
			BotonPaginaConsultaBean botonGenerarDocBase = new BotonPaginaConsultaBean("bEnviarYerbabuena", "bEnviarYerbabuena", "Generar Documento Base", "return generarDocBase(this);");
			listaBotones.add(botonGenerarDocBase);

			// BOTÓN 'A. Masiva Tasas'
			if (utilesColegiado.tienePermisoColegio()) {
				BotonPaginaConsultaBean botonAMasivaTasas = new BotonPaginaConsultaBean("bAsignarTasasMasiva", "bAsignarTasasMasiva", "A. Masiva Tasas", "return asignacionMasivaTasas(this);");
				listaBotones.add(botonAMasivaTasas);
			}

			// BOTÓN 'Obtener XML de DGT'
			if (utilesColegiado.tienePermisoAdmin()) {
				BotonPaginaConsultaBean botonObtenerXmlDgt = new BotonPaginaConsultaBean("bObtenerXML", "bObtenerXML", "Obtener XML de DGT", "return obtenerXMLenviadoaDGT();");
				listaBotones.add(botonObtenerXmlDgt);
			}

			// BOTÓN 'Forzar Generar JP'
			if (utilesColegiado.tienePermisoAdmin()) {
				BotonPaginaConsultaBean botonForzarGenerarJp = new BotonPaginaConsultaBean("bGenerarJP", "bGenerarJP", "Forzar Generar JP", "return generarJP(this);");
				listaBotones.add(botonForzarGenerarJp);
			}

			// BOTÓN 'Cambiar Estado'
			if (utilesColegiado.tienePermisoColegio()) {
				BotonPaginaConsultaBean botonCambiarEstado = new BotonPaginaConsultaBean("bCambiarEstado", "bCambiarEstado", "Cambiar Estado",
						"javascript:abrirVentanaSeleccionEstados('bCambiarEstado');");
				listaBotones.add(botonCambiarEstado);
			}

			// BOTÓN 'Presentación 576'
			BotonPaginaConsultaBean botonPresentacion576 = new BotonPaginaConsultaBean("b576", "b576", "Presentación 576", "return presentacion576(this);");
			listaBotones.add(botonPresentacion576);

			// BOTÓN 'Consulta tarjeta eITV'
			BotonPaginaConsultaBean botonConsultaTarjetaEitv = new BotonPaginaConsultaBean("bConsultaTarjetaEitv", "bConsultaTarjetaEitv", "Consulta tarjeta eITV", "return consultaTarjetaEitv(this)");
			listaBotones.add(botonConsultaTarjetaEitv);

			// BOTÓN 'Liberación de EEFF'
			if (utilesColegiado.tienePermisoLiberarEEFF()) {
				BotonPaginaConsultaBean botonLiberarEEFF = new BotonPaginaConsultaBean("bLiberarEEFFMasivo", "bLiberarEEFFMasivo", "Liberar EEFF", "return liberarEEFFMasivo(this)");
				listaBotones.add(botonLiberarEEFF);
			}

			// BOTÓN 'Liberación de EEFF'
			if (utilesColegiado.tienePermisoConsultaEEFF()) {
				BotonPaginaConsultaBean botonConsultaEEFF = new BotonPaginaConsultaBean("bConsultaEEFF", "bConsultaEEFF", "Consulta Liberación", "return consultarEEFF(this)");
				listaBotones.add(botonConsultaEEFF);
			}

			// BOTÓN 'Generar Fichero AEAT'
			if (utilesColegiado.tienePermisoAdmin()) {
				BotonPaginaConsultaBean botonFicheroAEAT = new BotonPaginaConsultaBean("bFicheroAEAT", "bFicheroAEAT", "Generar Fichero AEAT", "return generarFicheroAEATTramites(this)");
				listaBotones.add(botonFicheroAEAT);
			}

			// BOTON 'Generar solicitud NRE-06'
			BotonPaginaConsultaBean botonGenerarNRE06 = new BotonPaginaConsultaBean("bGenerarNRE06", "bGenerarNRE06", "Generar Solicitud NRE-06", "return generarSolicitudNRE06(this)");
			listaBotones.add(botonGenerarNRE06);

			// TODO MPC. Cambio IVTM
			// BOTÓN 'Autoliquidar IVTM'
			if (utilesColegiado.tienePermisoAutoliquidarIvtm()) {
				BotonPaginaConsultaBean botonAutoliquidarIVTM = new BotonPaginaConsultaBean("bAutoliquidarIVTM", "bAutoliquidarIVTM", "Autoliquidar IVTM", "return autoliquidarMasivoIVTM(this);");
				listaBotones.add(botonAutoliquidarIVTM);
			}

			// TODO MPC. Cambio IVTM
			// BOTÓN 'Pago IVTM'
			if (utilesColegiado.tienePermisoAutoliquidarIvtm()) {
				BotonPaginaConsultaBean botonPagoIVTM = new BotonPaginaConsultaBean("bPagoIVTM", "bPagoIVTM", "Pago IVTM", "return pagoIVTM(this);");
				listaBotones.add(botonPagoIVTM);
			}

			// BOTON Clonar Trámites
			BotonPaginaConsultaBean botonClonarTramites = new BotonPaginaConsultaBean("bClonarTramite", "bClonarTramite", "Clonar Trámites", "return clonarTramite(this);");
			listaBotones.add(botonClonarTramites);

			// BOTON 'Generar solicitud 05'
			BotonPaginaConsultaBean botonGenerarSolicitud05 = new BotonPaginaConsultaBean("bGenerarSolicitud05", "bGenerarSolicitud05", "Generar Fichero Solicitud 05",
					"return generarFicheroSolicitud05(this)");
			listaBotones.add(botonGenerarSolicitud05);

			// BOTON Exportación datos modelo06
			BotonPaginaConsultaBean botonLiquidacionNRE06 = new BotonPaginaConsultaBean("bLiquidacionNRE06", "bLiquidacionNRE06", "Exportación Datos Modelo06",
					"return generarliquidacionNRE06(this);");
			listaBotones.add(botonLiquidacionNRE06);

			// BOTÓN Liberar Doc Base Nive
			if (utilesColegiado.tienePermisoAdmin()) {
				BotonPaginaConsultaBean botonLiberarDocBaseNive = new BotonPaginaConsultaBean("bLiberarDocBaseNive", "bLiberarDocBaseNive", "Liberar Doc Base Nive", "return liberarDocBaseNive(this);");
				listaBotones.add(botonLiberarDocBaseNive);
			}

			// Añadir boton cambio estado mantis 24455
			// BOTON Cambiar Estado Iniciado
			BotonPaginaConsultaBean botonCambiaEstadoIni = new BotonPaginaConsultaBean("bCambiaEstadoIni", "bCambiaEstadoIni", "Cambiar Estado a Iniciado", "return cambiaEstadoIni(this);");
			listaBotones.add(botonCambiaEstadoIni);

			// BOTON Matricula manual
			BotonPaginaConsultaBean botonCambiaMatricula = new BotonPaginaConsultaBean("bCambiaMatricula", "bCambiaMatricula", "Introducir Matrícula", "return generarPopPupMatriculaConsulta(this);");
			listaBotones.add(botonCambiaMatricula);

			// BOTON generar distintivo
			BotonPaginaConsultaBean botonGenerarDistintivo = new BotonPaginaConsultaBean("bGenerarDistintivo", "bGenerarDistintivo", "Generar Distintivo", "return generarDistintivo(this);");
			listaBotones.add(botonGenerarDistintivo);

			// BOTON descargar presentación 576
			BotonPaginaConsultaBean botonDescargarP576 = new BotonPaginaConsultaBean("bDescargarP576", "bDescargarP576", "Descargar 576", "return descargarPresentacion576(this);");
			listaBotones.add(botonDescargarP576);

			// BOTON autorización impresion baja
			if (utilesColegiado.tienePermisoColegio()) {
				BotonPaginaConsultaBean botonOkImprBaja = new BotonPaginaConsultaBean("bOkImprInformaBaja", "bOkImprInformaBaja", "Aut. Baja", "return autorizarImpresionBaja(this);");
				listaBotones.add(botonOkImprBaja);
			}

			// BOTÓN 'Imprimir Documentos Antiguos'
			/*if (utilesColegiado.tienePermisoAdmin()) {
				BotonPaginaConsultaBean botonImprimirDocumentosAntiguos = new BotonPaginaConsultaBean("bImprimirDocumentos", "bImprimirDocumentos", "Impresión Antigua",
						"return imprimirVariosTramites(this);");
				listaBotones.add(botonImprimirDocumentosAntiguos);
			}*/

			// BOTON Trámites Revisado
			BotonPaginaConsultaBean botonTramiteRevisados = new BotonPaginaConsultaBean("bTramiteRevisado", "bTramiteRevisado", "Trámite Revisado", "return tramiteRevisado(this);");
			listaBotones.add(botonTramiteRevisados);

			// BOTÓN 'Tramitar NRE06'
			if (utilesColegiado.tienePermisoAdmin()) {
				BotonPaginaConsultaBean botonTramitarNRE06 = new BotonPaginaConsultaBean("btramitarNre06", "btramitarNre06", "Tramitar NRE06",
						"return tramitarNRE06(this);");
				listaBotones.add(botonTramitarNRE06);
			}
			// BOTÓN cambiar fecha presentacion finalizado pdf'
			if (utilesColegiado.tienePermisoAdmin()) {
				BotonPaginaConsultaBean botonCambiarFechaFinalizadoPDF = new BotonPaginaConsultaBean("bfechaFinalizadoPdf", "bfechaFinalizadoPdf", "Fecha Presentación PDF",
						"return cambiarFechaPresentacion(this);");
				listaBotones.add(botonCambiarFechaFinalizadoPDF);
			}
			//Boton desasignar Tasa para el CAU
			if (utilesColegiado.tienePermisoAdmin()) {
				BotonPaginaConsultaBean botonDesasignarTasaCau = new BotonPaginaConsultaBean("bdesasignarTasaCau", "bdesasignarTasaCau", "Desasignar Tasa CAU",
						"return desasignarTasaCau(this);");
				listaBotones.add(botonDesasignarTasaCau);
			}
			BotonPaginaConsultaBean botonTramitarSolicitud05 = new BotonPaginaConsultaBean("btramitarSolicitud05", "btramitarSolicitud05", "Tramitar Solicitud 05",
					"return tramitarSolicitud05(this);");
			listaBotones.add(botonTramitarSolicitud05);

			// Botón modificar Referencia Propia
			BotonPaginaConsultaBean botonModificarReferenciaPropia = new BotonPaginaConsultaBean("bModificarRefernaciaPropia", "bModificarRefernaciaPropia", "Modificar Referencia Propia",
					"javascript:modificarReferenciaPropia();");
			listaBotones.add(botonModificarReferenciaPropia);

			if (utilesColegiado.tienePermisoMOVE()) {
				BotonPaginaConsultaBean botonFicheroMOVE = new BotonPaginaConsultaBean("bFicheroMOVE", "bFicheroMOVE", "Generar Fichero MOVE", "return generarFicheroMOVE(this)");
				listaBotones.add(botonFicheroMOVE);
			}
			// BOTÓN 'Autorizar Matriculacion Ficha tipo A'
			if (utilesColegiado.tienePermisoAdmin()) {
				BotonPaginaConsultaBean botonAutorizarTramitesColegio = new BotonPaginaConsultaBean("bAutorizarTramitesColegio", "bAutorizarTramitesColegio", "Autorizar Trámites ICOGAM", "return autorizarTramitesIcogam(this);");
				listaBotones.add(botonAutorizarTramitesColegio);
			}
			if (utilesColegiado.tienePermisoAdmin()) {
				BotonPaginaConsultaBean botonBorrarDatos = new BotonPaginaConsultaBean("bBorrarDatos", "bBorrarDatos", "Borrar Datos", "return borrarDatos(this);");
				listaBotones.add(botonBorrarDatos);
			}
			if (utilesColegiado.tienePermisoAdmin()) {
				BotonPaginaConsultaBean botonActualizarDatos = new BotonPaginaConsultaBean("bActualizarDatos", "bActualizarDatos", "Actualizar Datos", "return actualizarDatos(this);");
				listaBotones.add(botonActualizarDatos);
			}
			if (utilesColegiado.tienePermisoAdmin()) {
				BotonPaginaConsultaBean botonAsignarTasaXml = new BotonPaginaConsultaBean("bAsignarTasaXml", "bAsignarTasaXml", "Asignar Tasa XML", "return asignarTasaXml(this);");
				listaBotones.add(botonAsignarTasaXml);
			}
			/*if (utilesColegiado.tienePermisoAdmin()) {
				BotonPaginaConsultaBean botonModificarPueblo = new BotonPaginaConsultaBean("bModificarPueblo", "bModificarPueblo", "Modificar pueblo interviniente", "return actualizarPuebloInterviniente(this);");
				listaBotones.add(botonModificarPueblo);
			}*/
		}
		return listaBotones;
	}

//	private boolean usuarioAutorizado(String nombreBoton) throws OegamExcepcion {
//		boolean necesitaAutorizacion = false;
//		String refPropUsuariosAutorizados = nombreBoton + REF_PROP_USUARIOS_AUTORIZADOS;
//
//		if (gestorPropiedades.valorPropertie(refPropUsuariosAutorizados) != null && !gestorPropiedades.valorPropertie(refPropUsuariosAutorizados).equals("")) {
//			String[] listaUsuariosAutorizados = gestorPropiedades.valorPropertie(refPropUsuariosAutorizados).split(DELIMITADOR_ELEMENTOS_PROPIEDADES);
//			for (String cadIdAutorizado : listaUsuariosAutorizados) {
//				necesitaAutorizacion = true;
//				try {
//					if (utilesColegiado.getIdUsuarioSessionBigDecimal().compareTo(new BigDecimal(cadIdAutorizado)) == 0) {
//						return true;
//					}
//				} catch (NumberFormatException ex) {
//					continue;
//				}
//			}
//		}
//
//		String refPropMetodosValidadoresPermiso = nombreBoton + REF_PROP_METODOS_VALIDADORES_PERMISO;
//
//		if (gestorPropiedades.valorPropertie(refPropMetodosValidadoresPermiso) != null && !gestorPropiedades.valorPropertie(refPropMetodosValidadoresPermiso).equals("")) {
//			String[] listaMetodosValidadoresPermisos = gestorPropiedades.valorPropertie(refPropMetodosValidadoresPermiso).split(DELIMITADOR_ELEMENTOS_PROPIEDADES);
//			for (String metodoValidadorPermiso : listaMetodosValidadoresPermisos) {
//				necesitaAutorizacion = true;
//				try {
//					int situacionUltimoPunto = metodoValidadorPermiso.lastIndexOf(".");
//					String nombreClase = metodoValidadorPermiso.substring(0, situacionUltimoPunto);
//					String nombreMetodo = metodoValidadorPermiso.substring(situacionUltimoPunto, metodoValidadorPermiso.length());
//					nombreMetodo = nombreMetodo.replace("(", "");
//					nombreMetodo = nombreMetodo.replace(")", "");
//					nombreMetodo = nombreMetodo.replace(".", "");
//					Object tempClass = Class.forName(nombreClase).newInstance();
//					Class claseCargada = tempClass.getClass();
//					Method metodo = claseCargada.getDeclaredMethod(nombreMetodo);
//					Object resultado = metodo.invoke(tempClass);
//					if ((Boolean) resultado) {
//						return true;
//					}
//				} catch (Exception ex) {
//					continue;
//				}
//			}
//		}
//
//		if (!necesitaAutorizacion) {
//			return true;
//		} else {
//			return false;
//		}
//
//	}
}