package org.gestoresmadrid.oegam2.utiles;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;

import org.gestoresmadrid.core.model.enumerados.EstadoTramiteTrafico;
import org.gestoresmadrid.core.model.enumerados.MotivoBaja;
import org.gestoresmadrid.core.paises.model.enumerados.TipoPais;
import org.gestoresmadrid.oegam2comun.paises.model.service.ServicioPais;
import org.gestoresmadrid.oegam2comun.paises.view.dto.PaisDto;
import org.gestoresmadrid.oegam2comun.trafico.model.service.ServicioTramiteTraficoBaja;
import org.gestoresmadrid.oegam2comun.trafico.view.dto.TramiteTrafBajaDto;
import org.gestoresmadrid.oegamComun.acceso.service.impl.UtilesColegiado;
import org.gestoresmadrid.utilidades.components.GestorPropiedades;
import org.gestoresmadrid.utilidades.components.Utiles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import trafico.utiles.UtilesVistaTrafico;
import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;

public class UtilesVistaBaja {

	private static ILoggerOegam log = LoggerOegam.getLogger(UtilesVistaBaja.class);

	private static UtilesVistaBaja utilesVistaBaja;

	@Autowired
	private ServicioTramiteTraficoBaja servicioTraficoBaja;

	@Autowired
	ServicioPais servicioPais;

	@Autowired
	private GestorPropiedades gestorPropiedades;

	@Autowired
	private UtilesColegiado utilesColegiado;
	
	@Autowired
	private Utiles utiles;

	private String numExpedientes;
	
	public static UtilesVistaBaja getInstance() {
		if (utilesVistaBaja == null) {
			utilesVistaBaja = new UtilesVistaBaja();
			SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(utilesVistaBaja);
		}
		return utilesVistaBaja;
	}

	// Visibilidad de la pestania facturacion tramites de bajas:
	public boolean esFacturableLaTasaBaja(TramiteTrafBajaDto tramiteBaja) {
		if (utilesColegiado.tienePermisoMantenimientoBajas()) {
			if (tramiteBaja.getEstado() != null) {
				if (tramiteBaja.getEstado().equals(EstadoTramiteTrafico.Finalizado_Excel.getValorEnum())
						|| tramiteBaja.getEstado().equals(EstadoTramiteTrafico.Finalizado_Excel_Impreso.getValorEnum())
						|| tramiteBaja.getEstado().equals(EstadoTramiteTrafico.Finalizado_PDF.getValorEnum())
						|| tramiteBaja.getEstado()
								.equals(EstadoTramiteTrafico.Finalizado_Telematicamente.getValorEnum())
						|| tramiteBaja.getEstado()
								.equals(EstadoTramiteTrafico.Finalizado_Telematicamente_Impreso.getValorEnum())) {
					return true;
				}
			}
		}
		return false;
	}

	// 190117 Mantis 0025205. Se añade Finalizado_Excel_Incidencia para que muestre
	// botón de guardar
	public boolean esConsultableOGuardableBaja(TramiteTrafBajaDto tramiteBaja) {
		if (!utilesColegiado.tienePermisoEspecial()) {
			if (utilesColegiado.tienePermisoMantenimientoBajas()) {
				if (tramiteBaja.getEstado() == null || tramiteBaja.getEstado().equals("")
						|| tramiteBaja.getEstado().equals(EstadoTramiteTrafico.Iniciado.getValorEnum())
						|| tramiteBaja.getEstado().equals(EstadoTramiteTrafico.No_Tramitable.getValorEnum())
						|| tramiteBaja.getEstado().equals(EstadoTramiteTrafico.Tramitable_Incidencias.getValorEnum())
						|| tramiteBaja.getEstado().equals(EstadoTramiteTrafico.Tramitable.getValorEnum())
						|| tramiteBaja.getEstado().equals(EstadoTramiteTrafico.Validado_Telematicamente.getValorEnum())
						|| tramiteBaja.getEstado().equals(EstadoTramiteTrafico.Validado_PDF.getValorEnum())
						|| tramiteBaja.getEstado().equals(EstadoTramiteTrafico.Tramitable_Jefatura.getValorEnum())
						|| tramiteBaja.getEstado().equals(EstadoTramiteTrafico.Finalizado_Con_Error.getValorEnum())
						|| tramiteBaja.getEstado()
								.equals(EstadoTramiteTrafico.Finalizado_Excel_Incidencia.getValorEnum())) {
					return true;
				}
			}
			return false;
		}
		return false;
	}

	public boolean esValidable(TramiteTrafBajaDto tramiteBaja) {
		// Bajas checkBTV
		if (esNuevasBajas() && utilesColegiado.tienePermisoBTV()) {
			if (tramiteBaja.getEstado() != null && !tramiteBaja.getEstado().equals("")
					&& (EstadoTramiteTrafico.Tramitable.getValorEnum().equals(tramiteBaja.getEstado())
							|| EstadoTramiteTrafico.No_Tramitable.getValorEnum().equals(tramiteBaja.getEstado())
							|| EstadoTramiteTrafico.Tramitable_Jefatura.getValorEnum()
									.equals(tramiteBaja.getEstado()))) {
				return true;
			}
			if (tramiteBaja.getMotivoBaja() != null
					&& (MotivoBaja.DefE.getValorEnum().equals(tramiteBaja.getMotivoBaja())
							|| MotivoBaja.DefTC.getValorEnum().equals(tramiteBaja.getMotivoBaja()))) {
				if (!esProvinciaDuaTelematico(tramiteBaja)
						&& EstadoTramiteTrafico.Iniciado.getValorEnum().equals(tramiteBaja.getEstado())) {
					return true;
				}
			} else {
				if (tramiteBaja.getEstado() != null && !tramiteBaja.getEstado().equals("")
						&& (tramiteBaja.getEstado().equals(EstadoTramiteTrafico.Iniciado.getValorEnum()) || tramiteBaja
								.getEstado().equals(EstadoTramiteTrafico.Finalizado_Con_Error.getValorEnum()))) {
					return true;
				}
			}
		} else {
			if (tramiteBaja.getEstado() != null && !tramiteBaja.getEstado().equals("")
					&& (tramiteBaja.getEstado().equals(EstadoTramiteTrafico.Iniciado.getValorEnum()) || tramiteBaja
							.getEstado().equals(EstadoTramiteTrafico.Finalizado_Con_Error.getValorEnum()))) {
				return true;
			}
		}
		return false;
	}

	public boolean esConsultaBTV(TramiteTrafBajaDto tramiteBaja) {
		// Bajas checkBTV
		if (esNuevasBajas() && utilesColegiado.tienePermisoBTV()) {
			if (EstadoTramiteTrafico.Iniciado.getValorEnum().equals(tramiteBaja.getEstado())
					&& tramiteBaja.getMotivoBaja() != null
					&& (MotivoBaja.DefE.getValorEnum().equals(tramiteBaja.getMotivoBaja())
							|| MotivoBaja.DefTC.getValorEnum().equals(tramiteBaja.getMotivoBaja()))) {
				if (esProvinciaDuaTelematico(tramiteBaja)) {
					return true;
				}
			}
		}
		return false;
	}

	private boolean esProvinciaDuaTelematico(TramiteTrafBajaDto tramiteBaja) {
		boolean esProvinciaDua = true;
		// Validación para que los vehículos de Melilla y Ceuta no se puedan realizar
		// telemáticamente la baja
		if (tramiteBaja.getVehiculoDto() != null && tramiteBaja.getVehiculoDto().getDireccion() != null
				&& ("51".equals(tramiteBaja.getVehiculoDto().getDireccion().getIdProvincia())
						|| "52".equals(tramiteBaja.getVehiculoDto().getDireccion().getIdProvincia()))) {
			esProvinciaDua = false;
		}
		// Validación para que los titulares de Melilla y Ceuta no puedan realizar
		// telemáticamente la baja
		if (tramiteBaja.getTitular() != null && tramiteBaja.getTitular().getDireccion() != null
				&& ("51".equals(tramiteBaja.getTitular().getDireccion().getIdProvincia())
						|| "52".equals(tramiteBaja.getTitular().getDireccion().getIdProvincia()))) {
			esProvinciaDua = false;
		}
		return esProvinciaDua;
	}

	public boolean esTramitableTelematicamente(TramiteTrafBajaDto tramiteBaja) {
		if (esNuevasBajas() && utilesColegiado.tienePermisoBTV() &&
			EstadoTramiteTrafico.Validado_Telematicamente.getValorEnum().equals(tramiteBaja.getEstado())) {
			return true;
		}
		return false;
	}

	public boolean esImprimible(TramiteTrafBajaDto tramiteBaja) {
		if (!utilesColegiado.tienePermisoEspecial()) {
			if (utilesColegiado.tienePermiso(UtilesColegiado.PERMISO_MANTENIMIENTO_DUPLICADOS)) {
				if (tramiteBaja.getEstado() != null) {
					if (tramiteBaja.getEstado().equals(EstadoTramiteTrafico.Validado_PDF.getValorEnum())
							|| tramiteBaja.getEstado().equals(EstadoTramiteTrafico.Finalizado_PDF.getValorEnum())
							|| tramiteBaja.getEstado().equals(EstadoTramiteTrafico.Validado_Telematicamente.getValorEnum())
							|| tramiteBaja.getEstado().equals(EstadoTramiteTrafico.Finalizado_Telematicamente.getValorEnum())) {
						return true;
					}
				}
			}
			return false;
		}
		return false;
	}

	// Si la baja no es excel será baja presencial y no se enviará el fichero, se
	// podrá imprimir el pdf
	// Motivos Baja: 2,3,4,5,6 (2 --> Temporal Sustraccion (TempS), 3 --> Temporal
	// finalizado contrato (TempFCA)
	// 4 --> Temporal Transferencia (TempT), 5 --> Definitiva voluntaria (DefV).
	// Solo furgonetas <=3500kg y turismos > 15 años
	// 6 --> Definitiva renovación parque.(DefRP))
	public boolean esBajaPresencial(TramiteTrafBajaDto tramiteBaja) {
		if (null == tramiteBaja || null == tramiteBaja.getVehiculoDto()) {
			return false;
		}

		if (!utilesColegiado.tienePermisoEspecial()) {
			if (utilesColegiado.tienePermisoMantenimientoBajas()) {
				if (tramiteBaja.getEstado().equals(EstadoTramiteTrafico.Validado_PDF.getValorEnum())
						&& (MotivoBaja.TempS.getValorEnum().equals(tramiteBaja.getMotivoBaja())
								|| MotivoBaja.TempFCA.getValorEnum().equals(tramiteBaja.getMotivoBaja())
								|| MotivoBaja.TempT.getValorEnum().equals(tramiteBaja.getMotivoBaja())
								|| MotivoBaja.DefRP.getValorEnum().equals(tramiteBaja.getMotivoBaja())
								|| MotivoBaja.DefTC.getValorEnum().equals(tramiteBaja.getMotivoBaja())
								|| MotivoBaja.DefE.getValorEnum().equals(tramiteBaja.getMotivoBaja()))) {
					return true;
				}else if (utiles.esUsuarioBajaSive(tramiteBaja.getNumColegiado()) && tramiteBaja.getEstado().equals(EstadoTramiteTrafico.Validado_PDF.getValorEnum())
						&& (MotivoBaja.TempV.getValorEnum().equals(tramiteBaja.getMotivoBaja())
								|| MotivoBaja.TempR.getValorEnum().equals(tramiteBaja.getMotivoBaja()))) {
					return true;
				}
			}

			if (tramiteBaja.getEstado().equals(EstadoTramiteTrafico.Validado_PDF.getValorEnum())) {
				// Comprobamos que el colegiado pueda tramitar online en la provincia de MADRID
				// por SITEX
				String jefatura = tramiteBaja.getJefaturaTraficoDto().getJefaturaProvincial();
				String provContrato = "";

				if (tramiteBaja.getIdContrato() != null) {
					provContrato = utilesColegiado.getProvinciaDelContrato(tramiteBaja.getIdContrato());
				} else {
					provContrato = utilesColegiado.getProvinciaDelContrato();
				}
				if (!"SI".equalsIgnoreCase(gestorPropiedades.valorPropertie("nuevas.jefaturas.envioExcel"))) {
					if (!"28".equalsIgnoreCase(provContrato)
							&& (UtilesVistaTrafico.getInstance().esJefaturaMadrid(jefatura))) {
						// Si el colegiado no es de MADRID y la jefatura pertenece a MADRID es
						// presencial
						return true;
					} else if ("28".equalsIgnoreCase(provContrato)
							&& (!UtilesVistaTrafico.getInstance().esJefaturaMadrid(jefatura))) {
						// Si el colegiado es de MADRID y la jefatura no pertenece a MADRID
						return true;
					}
				}
			}

			if (null != tramiteBaja.getMotivoBaja() && tramiteBaja.getMotivoBaja().equals(MotivoBaja.DefV)
					&& tramiteBaja.getEstado().equals(EstadoTramiteTrafico.Validado_PDF.getValorEnum())) {
				// Para las bajas definitivas voluntarias es obligatorio el tipo de vehículo.
				if (null != tramiteBaja.getVehiculoDto().getTipoVehiculo()) {
					String tipoVehiculo = tramiteBaja.getVehiculoDto().getTipoVehiculo();
					// Si el vehículo a dar de baja definitiva voluntaria es una furgoneta (20) de
					// hasta 3500KG, tenga más de 15 años
					// y marquemos la Declaración jurada No Existe No Circula lo podemos tramitar,
					// en caso contrario NO.
					if (tipoVehiculo.equalsIgnoreCase("20") && null != tramiteBaja.getVehiculoDto().getPesoMma()) {
						if (Integer.parseInt(tramiteBaja.getVehiculoDto().getPesoMma()) > 3500) {
							return true;
						}
						// Si es peso_mma <= 3500kg
						else if (!tramiteBaja.getDeclaracionNoEntregaCatV()
								|| !servicioTraficoBaja.vehiculoMayorDe15Anios(tramiteBaja.getVehiculoDto())) {
							return true;
						}
					} else if (tipoVehiculo.equalsIgnoreCase("40") && (!tramiteBaja.getDeclaracionNoEntregaCatV()
							|| !servicioTraficoBaja.vehiculoMayorDe15Anios(tramiteBaja.getVehiculoDto()))) {
						// Si el vehículo a dar de baja definitiva voluntaria es un turismo (40) tenga
						// más de 15 años
						// y marquemos la Declaración jurada No Existe No Circula lo podemos tramitar,
						// en caso contrario NO.
						return true;
					}
				}
			}
		}
		return false;
	}

	public boolean esNuevasBajas() {
		return true;
	}

	public boolean esEnvioExcelBajas(TramiteTrafBajaDto tramiteBaja) {
		if (tramiteBaja != null && !utilesColegiado.tienePermisoEspecial()
				&& utilesColegiado.tienePermisoMantenimientoBajas()
				&& EstadoTramiteTrafico.Validado_PDF.getValorEnum().equals(tramiteBaja.getEstado())
				&& !esBajaPresencial(tramiteBaja)) {
			return utilesColegiado.esColegiadoEnvioExcel();
		}
		return false;
	}

	public List<PaisDto> listaPaises(TramiteTrafBajaDto tramiteTrafBajaDto) {
		if (tramiteTrafBajaDto != null) {
			if (MotivoBaja.DefE.getValorEnum().equals(tramiteTrafBajaDto.getMotivoBaja())) {
				return servicioPais.listaPaises(new BigDecimal(TipoPais.Pais_Exportacion.getValorEnum()));
			} else if (MotivoBaja.DefTC.getValorEnum().equals(tramiteTrafBajaDto.getMotivoBaja())) {
				return servicioPais.listaPaises(new BigDecimal(TipoPais.Pais_Transito_CEE.getValorEnum()));
			}
		}
		return Collections.emptyList();
	}
	
	public boolean esTramiteBajaSive(String numExpedientes) {
		if(numExpedientes != null) {
				TramiteTrafBajaDto trafBajaDto = servicioTraficoBaja.getTramiteBaja(new BigDecimal(numExpedientes), Boolean.FALSE);
				if(MotivoBaja.TempV.getValorEnum().equals(trafBajaDto.getMotivoBaja()) || MotivoBaja.TempR.getValorEnum().equals(trafBajaDto.getMotivoBaja())) {
					return true;
				}else {
					return false;
				}
		}
		
		return false;
	}

	public String getNumExpedientes() {
		return numExpedientes;
	}

	public void setNumExpedientes(String numExpedientes) {
		this.numExpedientes = numExpedientes;
	}
}