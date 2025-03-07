package org.gestoresmadrid.oegam2.transporte.controller.action;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import org.gestoresmadrid.oegam2comun.transporte.model.service.ServicioPoderesTransporte;
import org.gestoresmadrid.oegam2comun.transporte.view.beans.ResultadoTransporteBean;
import org.gestoresmadrid.oegam2comun.transporte.view.dto.PoderTransporteDto;
import org.gestoresmadrid.oegamComun.acceso.service.impl.UtilesColegiado;
import org.gestoresmadrid.utilidades.components.Utiles;
import org.springframework.beans.factory.annotation.Autowired;

import general.acciones.ActionBase;
import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;

public class AltaPoderesTransporteAction extends ActionBase{

	private static final long serialVersionUID = -2226763261130274237L;
	
	private static final ILoggerOegam LOG = LoggerOegam.getLogger(AltaPoderesTransporteAction.class);
	
	private static final String ALTA_PODER = "altaPoderTransporte";
	private static final String DESCARGAR_PODER = "descargarPoder";
	
	private Boolean esGenerado;
	private PoderTransporteDto poderTransporte;
	
	private InputStream inputStream;
	private String fileName;
	
	@Autowired
	ServicioPoderesTransporte servicioPoderesTransporte;
	
	@Autowired
	private UtilesColegiado utilesColegiado;
	@Autowired
	private Utiles utiles;
	
	public String alta(){
		if(utiles.esUsuarioOegam3(utilesColegiado.getIdUsuarioSessionBigDecimal().toString())) {
			return "redireccionOegam3";
		}else {
			if(poderTransporte == null){
				poderTransporte = new PoderTransporteDto();
			} else {
				ResultadoTransporteBean resultadoTransporteBean = servicioPoderesTransporte.getPoderTransportePantalla(poderTransporte);
				if(!resultadoTransporteBean.getError()){
					poderTransporte = resultadoTransporteBean.getPoderTransporte();
				} else {
					addActionError(resultadoTransporteBean.getMensaje());
					poderTransporte = new PoderTransporteDto();
				}
			}
			return ALTA_PODER;
		}
		
	}
	
	public String generarPoder(){
		ResultadoTransporteBean resultado = servicioPoderesTransporte.generarPoderTransporte(poderTransporte, utilesColegiado.getIdContratoSessionBigDecimal(), utilesColegiado.getIdUsuarioSessionBigDecimal());
		if(resultado.getError()){
			addActionError(resultado.getMensaje());
		} else {
			poderTransporte = resultado.getPoderTransporte();
			addActionMessage("El fichero pdf se ha generado correctamente.");
		}
		return ALTA_PODER;
	}
	
	public String descargarPdf(){
		String pagina = ALTA_PODER;
		ResultadoTransporteBean resultado = servicioPoderesTransporte.descargarPdf(poderTransporte);
		if(resultado.getError()){
			addActionError(resultado.getMensaje());
		} else {
			try {
				inputStream = new FileInputStream((File) resultado.getFichero());
				fileName = resultado.getNombreFichero();
				pagina = DESCARGAR_PODER;
			} catch (FileNotFoundException e) {
				LOG.error("Ha sucedido un error a la hora de descargar el pdf, error: ",e);
				addActionError("Ha sucedido un error a la hora de descargar el pdf.");
			}
		}
		return pagina;
	}
	
	public String obtenerPoder(){
		return DESCARGAR_PODER;
	}
	
	public PoderTransporteDto getPoderTransporte() {
		return poderTransporte;
	}

	public void setPoderTransporte(PoderTransporteDto poderTransporte) {
		this.poderTransporte = poderTransporte;
	}

	public Boolean getEsGenerado() {
		return esGenerado;
	}

	public void setEsGenerado(Boolean esGenerado) {
		this.esGenerado = esGenerado;
	}

	public InputStream getInputStream() {
		return inputStream;
	}

	public void setInputStream(InputStream inputStream) {
		this.inputStream = inputStream;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	
}
