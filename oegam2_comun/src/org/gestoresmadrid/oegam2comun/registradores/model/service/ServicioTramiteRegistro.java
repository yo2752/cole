package org.gestoresmadrid.oegam2comun.registradores.model.service;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.gestoresmadrid.oegam2comun.registradores.clases.ResultRegistro;
import org.gestoresmadrid.oegam2comun.registradores.importExport.jaxb.FORMATOGA.ESCRITURA;
import org.gestoresmadrid.oegam2comun.registradores.view.dto.FicheroInfo;
import org.gestoresmadrid.oegam2comun.registradores.view.dto.TramiteRegistroDto;
import org.gestoresmadrid.oegamComun.contrato.view.dto.ContratoDto;

import escrituras.beans.ResultBean;
import utilidades.web.OegamExcepcion;

public interface ServicioTramiteRegistro extends Serializable {

	public static String TRAMITE_REGISTRO = "tramiteRegistro";
	public static String ID_TRAMITE_REGISTRO = "IDTRAMITEREGISTRO";
	public static String ID_DIRECCION_DESTINATARIO = "IDDIRECCIONDESTINATARIO";

	TramiteRegistroDto getTramite(BigDecimal numExpediente);

	TramiteRegistroDto getTramiteIdCorpme(String idTramiteCorpme);

	List<TramiteRegistroDto> getTramiteIdCorpmeConCodigoRegistro(String idTramiteCorpme, String codigoRegistro);

	ResultBean guardarTramiteRegistro(TramiteRegistroDto tramiteRegistroDto, BigDecimal estadoAnterior, BigDecimal idUsuario);

	ResultBean solicitarFirmas(TramiteRegistroDto tramiteRegistroDto, BigDecimal idUsuario);

	BigDecimal generarIdTramiteRegistro(String numColegiado, String tipoTramiteRegistro) throws Exception;

	String generarIdTramiteCorpme(String numColegiado, BigDecimal idContrato) throws Exception;

	void actualizarFicheroSubido(BigDecimal idTramiteRegistro);

	ResultBean cambiarEstado(boolean evolucion, BigDecimal numExpediente, BigDecimal antiguoEstado, BigDecimal nuevoEstado, boolean notificar, String textoNotificacion, BigDecimal idUsuario);

	ResultBean firmar(BigDecimal idTramiteRegistro, String nifCertificante, String idFirma, BigDecimal idUsuario);

	String comprobarCorpme(TramiteRegistroDto tramiteRegistro, BigDecimal idUsuario);

	ResultBean duplicar(TramiteRegistroDto tramiteRegistro, BigDecimal idUsuario);

	ResultBean subsanar(TramiteRegistroDto tramiteRegistro, BigDecimal idUsuario);

	ResultBean guardarTramiteRegistroEscritura(TramiteRegistroDto tramiteRegistro, BigDecimal idUsuario);

	ArrayList<FicheroInfo> recuperarDocumentos(BigDecimal idTramite, String tipoTramite);

	ArrayList<FicheroInfo> recuperarDocumentosRecibidos(BigDecimal idTramite, String tipoTramite);

	ResultRegistro validarRegistro(TramiteRegistroDto tramiteRegistroDto);

	ResultRegistro enviarCorreo(TramiteRegistroDto tramiteRegistro) throws Throwable;

	void incluirInmuebles(TramiteRegistroDto tramiteRegistroDto);

	ResultBean guardarEscrituraImportacion(ESCRITURA escritura, BigDecimal idUsuario, ContratoDto contrato);

	void guardarEvolucionTramite(BigDecimal idTramiteRegistro, BigDecimal antiguoEstado, BigDecimal nuevoEstado, BigDecimal idUsuario);

	TramiteRegistroDto getTramiteJustificante(String identificador);

	ResultRegistro construirAcuse(String idRegistro, String alias, String estadoAcusePendiente, String idRegistroFueraSecuencia);

	ResultRegistro construirRm(TramiteRegistroDto tramiteRegistro, boolean rmFirmar, String alias) throws Throwable;

	ResultRegistro confirmarPagoFactura(TramiteRegistroDto tramiteRegistroDto, String alias);

	ResultRegistro validarZIPLibros(String idTramiteRegistro, File zip);

	File extraerFicheroZipFile(File zip, String nombreFicheroBuscar) throws IOException, OegamExcepcion;

	ResultBean guardarTramiteRegistroLibroCuenta(TramiteRegistroDto tramiteRegistro, BigDecimal idUsuario);

	void inicializarParametros(TramiteRegistroDto tramiteRegistro);

	void incluirDatosSubsanacion(TramiteRegistroDto tramiteRegistro);

	ResultRegistro validarZIPCuentas(String idTramiteRegistro, File ficheroZIP, String nombreFicheroZIP);
	
	public String cleanString(String texto);
}
