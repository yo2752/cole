package org.gestoresmadrid.oegam2.datosBancariosFavoritos.controller.action;

import org.gestoresmadrid.core.datosBancariosFavoritos.model.enumerados.EstadoDatosBancarios;
import org.gestoresmadrid.oegam2comun.datosBancariosFavoritos.model.service.ServicioDatosBancariosFavoritos;
import org.gestoresmadrid.oegam2comun.datosBancariosFavoritos.view.dto.DatosBancariosFavoritosDto;
import org.gestoresmadrid.oegamComun.acceso.service.impl.UtilesColegiado;
import org.gestoresmadrid.oegamComun.contrato.view.dto.ContratoDto;
import org.springframework.beans.factory.annotation.Autowired;

import escrituras.beans.ResultBean;
import general.acciones.ActionBase;
import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;

public class AltaDatosBancariosAction extends ActionBase{

	private static final long serialVersionUID = 9173597043717486641L;
	private static final ILoggerOegam log = LoggerOegam.getLogger(AltaDatosBancariosAction.class);

	private static final String ALTA_DATO_BANCARIO = "altaDatoBancario";

	private Long idDatoBancario;
	private DatosBancariosFavoritosDto datosBancarios;
	private Boolean esModificado;

	@Autowired
	private ServicioDatosBancariosFavoritos servicioDatosBancariosFavoritos;

	@Autowired
	UtilesColegiado utilesColegiado;

	public String alta(){
		if (idDatoBancario != null) {
			obtenerDatoBancario(idDatoBancario);
		} else {
			cargarValoresIniciales();
		}
		return ALTA_DATO_BANCARIO;
	}

	private void cargarValoresIniciales() {
		if (datosBancarios == null) {
			datosBancarios = new DatosBancariosFavoritosDto();
		}
		if (!utilesColegiado.tienePermisoAdmin()) {
			datosBancarios.setEstado(EstadoDatosBancarios.HABILITADO.getValorEnum());
			ContratoDto contrato = new ContratoDto();
			contrato.setIdContrato(utilesColegiado.getIdContratoSessionBigDecimal());
			datosBancarios.setContrato(contrato);
		}
	}

	private void obtenerDatoBancario(Long id) {
		ResultBean resultado = servicioDatosBancariosFavoritos.getDatoBancario(id);
		if (!resultado.getError()) {
			datosBancarios = (DatosBancariosFavoritosDto) resultado.getAttachment("datosBancariosDto");
		} else {
			addActionError(resultado.getListaMensajes().get(0));
		}
	}

	public String guardar() {
		try {
			if (datosBancarios != null) {
				ResultBean resultado = servicioDatosBancariosFavoritos.guardarDatosPantalla(datosBancarios, esModificado, utilesColegiado.getIdUsuarioSessionBigDecimal());
				if (!resultado.getError()) {
					addActionMessage("El dato Bancario se ha guardado correctamente.");
					obtenerDatoBancario((Long) resultado.getAttachment("idDatoBancario"));
				} else {
					addActionError("Han sucedido los siguiente errores al intentar guardar el dato bancario: ");
					aniadirMensajeError(resultado);
				}
			} else {
				addActionError("No existen datos bancarios que guardar.");
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de guardar los datos bancarios, error: ",e);
			addActionError("Ha sucedido un error a la hora de guardar los datos bancarios.");
		}
		return ALTA_DATO_BANCARIO;
	}

	public Long getIdDatoBancario() {
		return idDatoBancario;
	}

	public void setIdDatoBancario(Long idDatoBancario) {
		this.idDatoBancario = idDatoBancario;
	}

	public DatosBancariosFavoritosDto getDatosBancarios() {
		return datosBancarios;
	}

	public void setDatosBancarios(DatosBancariosFavoritosDto datosBancarios) {
		this.datosBancarios = datosBancarios;
	}

	public Boolean getEsModificado() {
		return esModificado;
	}

	public void setEsModificado(Boolean esModificado) {
		this.esModificado = esModificado;
	}

}