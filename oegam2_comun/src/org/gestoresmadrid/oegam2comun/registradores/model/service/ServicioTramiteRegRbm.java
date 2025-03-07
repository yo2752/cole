package org.gestoresmadrid.oegam2comun.registradores.model.service;

import java.io.Serializable;
import java.math.BigDecimal;

import org.gestoresmadrid.oegam2comun.registradores.clases.ResultRegistro;
import org.gestoresmadrid.oegam2comun.registradores.importExport.jaxb.FORMATOGA.CONTRATOFINANCIACION;
import org.gestoresmadrid.oegam2comun.registradores.importExport.jaxb.FORMATOGA.CONTRATOLEASING;
import org.gestoresmadrid.oegam2comun.registradores.view.dto.TramiteRegRbmDto;
import org.gestoresmadrid.oegam2comun.registradores.view.dto.TramiteRegistroDto;
import org.gestoresmadrid.oegamComun.contrato.view.dto.ContratoDto;

import escrituras.beans.ResultBean;

public interface ServicioTramiteRegRbm extends Serializable {

	public ResultRegistro getTramiteRegRbm(String name);

	public ResultRegistro borrarTramiteRegRbm(String name);

	public ResultRegistro guardarTramiteRegRbm(TramiteRegRbmDto tramiteRegRbmDto);

	public ResultRegistro validarContrato(TramiteRegRbmDto tramiteRegRbmDto);

	public ResultRegistro getTramiteRegRbmCancelDesist(String name);

	public ResultRegistro getListasCancelacion(String name);

	ResultRegistro guardarTramiteRegRbmCancelDesist(TramiteRegRbmDto tramiteRegRbmDto, BigDecimal estadoAnterior, BigDecimal idUsuario);

	public ResultBean guardarFinanciacionImportacion(CONTRATOFINANCIACION contratofinanciacion, BigDecimal usuario, ContratoDto contrato);

	// IMPLEMENTED JVG. 24/05/2018.

	public ResultBean guardarLeasingImportacion(CONTRATOLEASING contratoleasing, BigDecimal usuario, ContratoDto contrato);

	ResultRegistro guardarTramiteRegRbmLeasing(TramiteRegRbmDto tramiteRegRbmDto, BigDecimal estadoAnterior, BigDecimal idUsuario);

	// END IMPLEMENTED.
	BigDecimal generarIdTramiteRegistro(String numColegiado, String tipoTramiteRegistro) throws Exception;

	String generarIdTramiteCorpme(String numColegiado, BigDecimal idContrato) throws Exception;

	void guardarEvolucionTramite(BigDecimal idTramiteRegistro, BigDecimal antiguoEstado, BigDecimal nuevoEstado, BigDecimal idUsuario);

	ResultRegistro construirDprFacturaEscritura(TramiteRegistroDto tramiteRegistroDto, String alias);

	ResultRegistro construirDpr(String idRegistro, String alias);
}
