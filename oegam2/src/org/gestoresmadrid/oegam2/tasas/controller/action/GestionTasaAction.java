package org.gestoresmadrid.oegam2.tasas.controller.action;

import org.gestoresmadrid.core.tasas.model.enumeration.FormatoTasa;
import org.gestoresmadrid.oegam2comun.tasas.model.service.ServicioTasaNueva;
import org.gestoresmadrid.oegam2comun.tasas.view.beans.ConsultaTasaNuevaFilterBean;
import org.gestoresmadrid.oegam2comun.tasas.view.beans.ResultadoConsultaTasasBean;
import org.gestoresmadrid.oegam2comun.tasas.view.beans.ResumenTasasBean;
import org.gestoresmadrid.oegamComun.acceso.service.impl.UtilesColegiado;
import org.gestoresmadrid.oegamComun.tasa.view.dto.TasaDto;
import org.springframework.beans.factory.annotation.Autowired;

import general.acciones.ActionBase;
import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;

public class GestionTasaAction extends ActionBase {

	private static final long serialVersionUID = 2397453167735946294L;
	private static final ILoggerOegam log = LoggerOegam.getLogger(GestionTasaAction.class);

	private String codigoTasa;
	private String numExpedientes;
	private TasaDto tasaDto;
	private ResumenTasasBean resumen;

	@Autowired
	ServicioTasaNueva servicioTasa;

	@Autowired
	private UtilesColegiado utilesColegiado;
	
	ConsultaTasaNuevaFilterBean consultaTasa;

	public String detalle() {
		if (codigoTasa != null && !codigoTasa.isEmpty()) {
			ResultadoConsultaTasasBean result = servicioTasa.getTasaDto(codigoTasa);
			if (!result.getError()) {
				tasaDto = result.getTasaDto();
			} else {
				aniadirListaErrores(result.getListaError());
				cargarValoresIniciales();
			}
		} else {
			cargarValoresIniciales();
		}
		return SUCCESS;

	}

	/*
	 * public String desasignar() { if(numExpedientes != null &&
	 * !numExpedientes.isEmpty()){ ResultadoConsultaTasasBean resultado =
	 * servicioTasa.desasignarTasa(tasaDto.getCodigoTasa(),numExpedientes,
	 * utilesColegiado.getIdUsuarioDeSesion(),utilesColegiado.getIdContrato(),
	 * utilesColegiado.tienePermisoAdmin()); if(resultado.getError()){
	 * addActionError(resultado.getMensaje()); }else{
	 * rellenarResumen(resultado); ResultadoConsultaTasasBean result =
	 * servicioTasa.getTasaDto(codigoTasa); if(!result.getError()){ tasaDto =
	 * result.getTasaDto(); } else {
	 * aniadirListaErrores(result.getListaError()); cargarValoresIniciales(); }
	 * } }else{ addActionError("Se ha producido un error al desasignar la tasa"
	 * ); }
	 * 
	 * return SUCCESS; }
	 */
	private void rellenarResumen(ResultadoConsultaTasasBean resultado) {
		resumen = new ResumenTasasBean();
		resumen.setNumError(resultado.getNumError());
		resumen.setListaErrores(resultado.getListaError());
		resumen.setNumOk(resultado.getNumOk());
		resumen.setListaOk(resultado.getListaOk());

	}

	/*
	 * public String eliminar() { if(codigoTasa != null &&
	 * !codigoTasa.isEmpty()){ ResultadoConsultaTasasBean resultado =
	 * servicioTasa.eliminaTasa(codigoTasa,utilesColegiado.getIdUsuarioDeSesion(
	 * ),utilesColegiado.getIdContrato(), utilesColegiado.tienePermisoAdmin());
	 * if(resultado.getError()){ addActionError(resultado.getMensaje()); }else{
	 * ResultadoConsultaTasasBean result = servicioTasa.getTasaDto(codigoTasa);
	 * if(!result.getError()){ tasaDto = result.getTasaDto(); } else {
	 * aniadirListaErrores(result.getListaError()); cargarValoresIniciales(); }
	 * } }else{ addActionError("Se ha producido un error al desasignar la tasa"
	 * ); }
	 * 
	 * return SUCCESS; }
	 */

	private void cargarValoresIniciales() {
		if (tasaDto == null) {
			tasaDto = new TasaDto();
		}
		if (!utilesColegiado.tienePermisoAdmin()) {
			tasaDto.getContrato().setIdContrato(utilesColegiado.getIdContratoSessionBigDecimal());
			tasaDto.getUsuario().setNumColegiado(utilesColegiado.getNumColegiadoSession());
		}
		tasaDto.setFormato(FormatoTasa.ELECTRONICO.getCodigo());
	}

	public TasaDto getTasaDto() {
		return tasaDto;
	}

	public void setTasaDto(TasaDto tasaDto) {
		this.tasaDto = tasaDto;
	}

	public String getCodigoTasa() {
		return codigoTasa;
	}

	public void setCodigoTasa(String codigoTasa) {
		this.codigoTasa = codigoTasa;
	}

	public String getNumExpedientes() {
		return numExpedientes;
	}

	public void setNumExpedientes(String numExpedientes) {
		this.numExpedientes = numExpedientes;
	}

}
