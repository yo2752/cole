package org.gestoresmadrid.oegam2.bienes.controller.action;

import java.math.BigDecimal;

import escrituras.beans.ResultBean;
import general.acciones.ActionBase;

import org.gestoresmadrid.core.modelos.model.enumerados.TipoBien;
import org.gestoresmadrid.oegam2comun.bien.model.service.ServicioBien;
import org.gestoresmadrid.oegam2comun.bien.view.dto.BienDto;
import org.gestoresmadrid.oegam2comun.tipoInmueble.view.dto.TipoInmuebleDto;
import org.springframework.beans.factory.annotation.Autowired;

import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;

public class AltaBienesAction extends ActionBase {

	private static final long serialVersionUID = 9123990432430237375L;

	private static final ILoggerOegam log = LoggerOegam.getLogger(AltaBienesAction.class);

	private static final String ALTA_BIEN_RUSTICO = "altaBienRustico";
	private static final String ALTA_BIEN_URBANO = "altaBienUrbano";
	private static final String ALTA_OTRO_BIEN = "altaOtroBien";
	private static final String CONSULTA_BIEN = "irAConsultaBien";
	private static final String VER_MODELO = "irAModelos";
	private static final String VER_REMESA = "irARemesas";
	private static final String VER_INMUEBLE = "irAInmuebles";

	private BienDto bien;
	private String tipoBien;
	private String idBien;
	private Boolean esEliminado = false;
	private BigDecimal numExpediente;

	@Autowired
	private ServicioBien servicioBien;

	public String verModelo() {
		String pagina = "";
		if (numExpediente != null) {
			pagina = VER_MODELO;
		} else {
			addActionError("Debe seleccionar un modelo para poder obtener su detalle.");
			ResultBean resultado = obtenerBien(bien.getIdBien());
			if (resultado.getError()) {
				aniadirMensajeError(resultado);
			}
			pagina = getPaginaPorBien();
		}
		return pagina;
	}

	public String verRemesa() {
		String pagina = "";
		if (numExpediente != null) {
			pagina = VER_REMESA;
		} else {
			addActionError("Debe seleccionar una remesa para poder obtener su detalle.");
			ResultBean resultado = obtenerBien(bien.getIdBien());
			if (resultado.getError()) {
				aniadirMensajeError(resultado);
			}
			pagina = getPaginaPorBien();
		}
		return pagina;
	}

	public String verInmueble() {
		String pagina = "";
		if (numExpediente != null) {
			pagina = VER_INMUEBLE;
		} else {
			addActionError("Debe seleccionar un inmueble para poder obtener su detalle.");
			ResultBean resultado = obtenerBien(bien.getIdBien());
			if (resultado.getError()) {
				aniadirMensajeError(resultado);
			}
			pagina = getPaginaPorBien();
		}
		return pagina;
	}

	public String alta() {
		if (idBien != null && !idBien.isEmpty()) {
			ResultBean resultado = obtenerBien(Long.parseLong(idBien));
			if (resultado.getError()) {
				aniadirMensajeError(resultado);
			}
		} else {
			cargarValoresIniciales();
		}
		return getPaginaPorBien();
	}

	private ResultBean obtenerBien(Long idBienBBDD) {
		ResultBean resultado = new ResultBean(false);
		if (idBienBBDD != null) {
			resultado = servicioBien.getBienPorId(idBienBBDD);
			if (!resultado.getError()) {
				bien = (BienDto) resultado.getAttachment("bienDto");
			}
		} else {
			cargarValoresIniciales();
		}
		return resultado;
	}

	public String guardar() {
		ResultBean resultado = null;
		Long idBien = null;
		try {
			idBien = bien.getIdBien();
			resultado = servicioBien.guardar(bien);
			if (!resultado.getError()) {
				addActionMessage("El bien se ha guardado correctamente");
				idBien = (Long) resultado.getAttachment("idBien");
			} else {
				aniadirMensajeError(resultado);
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de guardar el bien, error: ", e);
			addActionError("Ha sucedido un error a la hora de guardar el bien");
		}
		resultado = obtenerBien(idBien);
		if (resultado.getError()) {
			aniadirMensajeError(resultado);
		}
		return getPaginaPorBien();
	}

	public String eliminar() {
		ResultBean resultado = null;
		String pagina = "";
		try {
			resultado = servicioBien.eliminarBien(bien.getIdBien());
			if (!resultado.getError()) {
				pagina = CONSULTA_BIEN;
				esEliminado = true;
			} else {
				aniadirMensajeError(resultado);
				resultado = obtenerBien(bien.getIdBien());
				if (resultado.getError()) {
					aniadirMensajeError(resultado);
				}
				pagina = getPaginaPorBien();
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de eliminar el bien, error: ", e);
			addActionError("Ha sucedido un error a la hora de eliminar el bien");
		}
		return pagina;
	}

	private String getPaginaPorBien() {
		String pagina = "";
		if (TipoBien.Rustico.getValorEnum().equals(bien.getTipoInmueble().getIdTipoBien())) {
			pagina = ALTA_BIEN_RUSTICO;
		} else if (TipoBien.Urbano.getValorEnum().equals(bien.getTipoInmueble().getIdTipoBien())) {
			pagina = ALTA_BIEN_URBANO;
		} else if (TipoBien.Otro.getValorEnum().equals(bien.getTipoInmueble().getIdTipoBien())) {
			pagina = ALTA_OTRO_BIEN;
		}
		return pagina;
	}

	private void cargarValoresIniciales() {
		if (bien == null) {
			bien = new BienDto();
			TipoInmuebleDto tipoInmuebleDto = new TipoInmuebleDto();
			tipoInmuebleDto.setIdTipoBien(tipoBien);
			bien.setTipoInmueble(tipoInmuebleDto);
		}
	}

	public BienDto getBien() {
		return bien;
	}

	public void setBien(BienDto bien) {
		this.bien = bien;
	}

	public String getTipoBien() {
		return tipoBien;
	}

	public void setTipoBien(String tipoBien) {
		this.tipoBien = tipoBien;
	}

	public String getIdBien() {
		return idBien;
	}

	public void setIdBien(String idBien) {
		this.idBien = idBien;
	}

	public Boolean getEsEliminado() {
		return esEliminado;
	}

	public void setEsEliminado(Boolean esEliminado) {
		this.esEliminado = esEliminado;
	}

	public BigDecimal getNumExpediente() {
		return numExpediente;
	}

	public void setNumExpediente(BigDecimal numExpediente) {
		this.numExpediente = numExpediente;
	}

}