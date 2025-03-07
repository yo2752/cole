package org.gestoresmadrid.oegam2.creditos.controller.action;

import java.math.BigDecimal;
import java.util.List;

import org.gestoresmadrid.oegam2comun.creditos.model.service.ServicioCredito;
import org.gestoresmadrid.oegam2comun.creditos.view.dto.CreditoDto;
import org.gestoresmadrid.oegamComun.acceso.service.impl.UtilesColegiado;
import org.springframework.beans.factory.annotation.Autowired;

import escrituras.beans.ResultBean;
import general.acciones.ActionBase;
import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;

public class ConsultaCreditoCargarAction extends ActionBase {

	private static final long serialVersionUID = -2503436335729506736L;

	private static final ILoggerOegam log = LoggerOegam.getLogger(ConsultaCreditoCargarAction.class);

	private Long idContrato;

	private String idContratoProvincia;

	private String tipoCreditoCantidad;

	List<CreditoDto> listaCreditos;

	@Autowired
	private ServicioCredito servicioCredito;

	@Autowired
	UtilesColegiado utilesColegiado;

	public String buscar() {
		if (idContrato == null) {
			if (idContratoProvincia != null && !idContratoProvincia.isEmpty()) {
				idContrato = new Long(idContratoProvincia.split("_")[0]);
			} else if (!utilesColegiado.tienePermisoAdmin()) {
				idContrato = utilesColegiado.getIdContratoSession();
			}
		}

		listaCreditos = servicioCredito.busquedaCreditosPorContrato(idContrato);

		return SUCCESS;
	}

	public String guardar() throws Throwable {
		ResultBean resultCredidos = new ResultBean();
		if (tipoCreditoCantidad != null && !tipoCreditoCantidad.isEmpty()) {
			String[] valores = tipoCreditoCantidad.split(",");
			for (String fila : valores) {
				if (fila != null && !fila.isEmpty()) {
					Integer cantidad = Integer.valueOf(fila.split(";")[0]);

					if (!BigDecimal.ZERO.equals(cantidad)) {
						String tipoCredito = fila.split(";")[1];
						ResultBean result = servicioCredito.cargoCreditos(tipoCredito, new BigDecimal(idContrato), cantidad, utilesColegiado.getIdUsuarioSessionBigDecimal());
						if (result != null && result.getError()) {
							addActionError("Error al cargar los creditos de " + tipoCredito);
							if (result.getListaMensajes() != null && !result.getListaMensajes().isEmpty()) {
								for (String mensaje : result.getListaMensajes()) {
									addActionError(mensaje);
								}
							}
							resultCredidos.setError(true);
						}
					}
				}
			}
			if (resultCredidos != null && !resultCredidos.getError()) {
				addActionMessage("Se han cargado los creditos correctamente.");
			}
		}
		return buscar();
	}

	public String getIdContratoProvincia() {
		return idContratoProvincia;
	}

	public void setIdContratoProvincia(String idContratoProvincia) {
		this.idContratoProvincia = idContratoProvincia;
	}

	public String getTipoCreditoCantidad() {
		return tipoCreditoCantidad;
	}

	public void setTipoCreditoCantidad(String tipoCreditoCantidad) {
		this.tipoCreditoCantidad = tipoCreditoCantidad;
	}

	public Long getIdContrato() {
		return idContrato;
	}

	public void setIdContrato(Long idContrato) {
		this.idContrato = idContrato;
	}

	public List<CreditoDto> getListaCreditos() {
		return listaCreditos;
	}

	public void setListaCreditos(List<CreditoDto> listaCreditos) {
		this.listaCreditos = listaCreditos;
	}
}