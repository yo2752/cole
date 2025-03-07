package org.gestoresmadrid.oegamComun.vehiculo.service.impl;

import java.io.Serializable;

import org.gestoresmadrid.core.vehiculo.model.vo.VehiculoVO;
import org.gestoresmadrid.oegamComun.vehiculo.view.bean.ResultadoValVehiculoBean;
import org.gestoresmadrid.utilidades.components.Utiles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;

@Component
public class ServicioValidacionVehiculo implements Serializable{

	private static final long serialVersionUID = -1681297633772611611L;
	
	private static final ILoggerOegam log = LoggerOegam.getLogger(ServicioValidacionVehiculo.class);

	@Autowired
	Utiles utiles;
	
	public ResultadoValVehiculoBean esModificadoVehiculo(VehiculoVO vehiculoVO, VehiculoVO vehiculoBBDD) {
		ResultadoValVehiculoBean resultado = new ResultadoValVehiculoBean(Boolean.FALSE);
		try {
			if (!utiles.sonIgualesObjetos(vehiculoVO.getFichaTecnicaRd750(), vehiculoBBDD.getFichaTecnicaRd750())) {
				resultado.addTextoModificado( "Ficha TecnicaRD 750,");
				if (vehiculoBBDD.getFichaTecnicaRd750() != null) {
					resultado.setValorModificado(new Long(2));
				} else if (resultado.getValorModificado() != 2) {
					resultado.setValorModificado(new Long(1));
				}
			}
			if (!utiles.sonIgualesObjetos(vehiculoVO.getSubastado(), vehiculoBBDD.getSubastado())) {
				resultado.addTextoModificado( "Subastado,");
				if (vehiculoBBDD.getSubastado() != null) {
					resultado.setValorModificado(new Long(2));
				} else if (resultado.getValorModificado() != 2) {
					resultado.setValorModificado(new Long(1));
				}
			}
			if (!utiles.sonIgualesString(vehiculoVO.getVehiculoAgricola(), vehiculoBBDD.getVehiculoAgricola())) {
				resultado.addTextoModificado( "Vehiculo Agricola,");
				if (vehiculoBBDD.getVehiculoAgricola() != null) {
					resultado.setValorModificado(new Long(2));
				} else if (resultado.getValorModificado() != 2) {
					resultado.setValorModificado(new Long(1));
				}
			}
			if (!utiles.sonIgualesString(vehiculoVO.getVehiculoTransporte(), vehiculoBBDD.getVehiculoTransporte())) {
				resultado.addTextoModificado( "Vehiculo Transporte,");
				if (vehiculoBBDD.getVehiculoTransporte() != null) {
					resultado.setValorModificado(new Long(2));
				} else if (resultado.getValorModificado() != 2) {
					resultado.setValorModificado(new Long(1));
				}
			}
			if (!utiles.sonIgualesString(vehiculoVO.getAnioFabrica(), vehiculoBBDD.getAnioFabrica())) {
				resultado.addTextoModificado( "Vehiculo Transporte,");
				if (vehiculoBBDD.getAnioFabrica() != null) {
					resultado.setValorModificado(new Long(2));
				} else if (resultado.getValorModificado() != 2) {
					resultado.setValorModificado(new Long(1));
				}
			}
			if (!utiles.sonIgualesObjetos(vehiculoVO.getAutonomiaElectrica(), vehiculoBBDD.getAutonomiaElectrica())) {
				resultado.addTextoModificado( "Año Fabrica,");
				if (vehiculoBBDD.getAutonomiaElectrica() != null) {
					resultado.setValorModificado(new Long(2));
				} else if (resultado.getValorModificado() != 2) {
					resultado.setValorModificado(new Long(1));
				}
			}
			if (!utiles.sonIgualesString(vehiculoVO.getBastidor() != null ? vehiculoVO.getBastidor().toUpperCase() : null, vehiculoBBDD.getBastidor() != null ? vehiculoBBDD.getBastidor().toUpperCase()
					: null)) {
				resultado.setBastidorAnt(vehiculoBBDD.getBastidor());
				if (vehiculoBBDD.getBastidor() != null) {
					resultado.setValorModificado(new Long(2));
				} else if (resultado.getValorModificado() != 2) {
					resultado.setValorModificado(new Long(1));
				}
				resultado.setBastidorNue(vehiculoVO.getBastidor());
			}
			if (!utiles.sonIgualesObjetos(vehiculoVO.getBastidorMatriculado(), vehiculoBBDD.getBastidorMatriculado())) {
				resultado.addTextoModificado( "Bastidor Matriculado,");
				if (vehiculoBBDD.getBastidorMatriculado() != null) {
					resultado.setValorModificado(new Long(2));
				} else if (resultado.getValorModificado() != 2) {
					resultado.setValorModificado(new Long(1));
				}
			}
			if (!utiles.sonIgualesString(vehiculoVO.getCaracteristicas(), vehiculoBBDD.getCaracteristicas())) {
				resultado.addTextoModificado( "Caracteristicas,");
				if (vehiculoBBDD.getCaracteristicas() != null) {
					resultado.setValorModificado(new Long(2));
				} else if (resultado.getValorModificado() != 2) {
					resultado.setValorModificado(new Long(1));
				}
			}
			if (!utiles.sonIgualesString(vehiculoVO.getIdCarburante(), vehiculoBBDD.getIdCarburante())) {
				resultado.addTextoModificado( "Carburante,");
				if (vehiculoBBDD.getIdCarburante() != null) {
					resultado.setValorModificado(new Long(2));
				} else if (resultado.getValorModificado() != 2) {
					resultado.setValorModificado(new Long(1));
				}
			}
			if (!utiles.sonIgualesString(vehiculoVO.getCarroceria(), vehiculoBBDD.getCarroceria())) {
				resultado.addTextoModificado( "Carroceria,");
				if (vehiculoBBDD.getCarroceria() != null) {
					resultado.setValorModificado(new Long(2));
				} else if (resultado.getValorModificado() != 2) {
					resultado.setValorModificado(new Long(1));
				}
			}
			if (!utiles.sonIgualesString(vehiculoVO.getCategoriaElectrica(), vehiculoBBDD.getCategoriaElectrica())) {
				resultado.addTextoModificado( "Categoria Electrica,");
				if (vehiculoBBDD.getCategoriaElectrica() != null) {
					resultado.setValorModificado(new Long(2));
				} else if (resultado.getValorModificado() != 2) {
					resultado.setValorModificado(new Long(1));
				}
			}
			if (!utiles.sonIgualesString(vehiculoVO.getCdmarca(), vehiculoBBDD.getCdmarca())) {
				resultado.addTextoModificado( "Marca Vehiculo,");
				if (vehiculoBBDD.getCdmarca() != null) {
					resultado.setValorModificado(new Long(2));
				} else if (resultado.getValorModificado() != 2) {
					resultado.setValorModificado(new Long(1));
				}
			}
			if (!utiles.sonIgualesString(vehiculoVO.getCdmodveh(), vehiculoBBDD.getCdmodveh())) {
				resultado.addTextoModificado( "Modelo Vehiculo,");
				if (vehiculoBBDD.getCdmodveh() != null) {
					resultado.setValorModificado(new Long(2));
				} else if (resultado.getValorModificado() != 2) {
					resultado.setValorModificado(new Long(1));
				}
			}
			if (!utiles.sonIgualesString(vehiculoVO.getCheckFechaCaducidadItv(), vehiculoBBDD.getCheckFechaCaducidadItv())) {
				resultado.addTextoModificado( "Check Fecha Caducidad,");
				if (vehiculoBBDD.getCheckFechaCaducidadItv() != null) {
					resultado.setValorModificado(new Long(2));
				} else if (resultado.getValorModificado() != 2) {
					resultado.setValorModificado(new Long(1));
				}
			}
			if (!utiles.sonIgualesString(vehiculoVO.getCilindrada(), vehiculoBBDD.getCilindrada())) {
				resultado.addTextoModificado( "Cilindrada,");
				if (vehiculoBBDD.getCilindrada() != null) {
					resultado.setValorModificado(new Long(2));
				} else if (resultado.getValorModificado() != 2) {
					resultado.setValorModificado(new Long(1));
				}
			}
			if (!utiles.sonIgualesString(vehiculoVO.getClasificacionItv(), vehiculoBBDD.getClasificacionItv())) {
				resultado.addTextoModificado( "Clasificación Itv,");
				if (vehiculoBBDD.getClasificacionItv() != null) {
					resultado.setValorModificado(new Long(2));
				} else if (resultado.getValorModificado() != 2) {
					resultado.setValorModificado(new Long(1));
				}
			}
			if (!utiles.sonIgualesString(vehiculoVO.getCo2(), vehiculoBBDD.getCo2())) {
				resultado.addTextoModificado( "Co2,");
				if (vehiculoBBDD.getCo2() != null) {
					resultado.setValorModificado(new Long(2));
				} else if (resultado.getValorModificado() != 2) {
					resultado.setValorModificado(new Long(1));
				}
			}
			if (!utiles.sonIgualesString(vehiculoVO.getCodigoEco(), vehiculoBBDD.getCodigoEco())) {
				resultado.addTextoModificado( "Codigo Eco,");
				if (vehiculoBBDD.getCodigoEco() != null) {
					resultado.setValorModificado(new Long(2));
				} else if (resultado.getValorModificado() != 2) {
					resultado.setValorModificado(new Long(1));
				}
			}
			if (!utiles.sonIgualesString(vehiculoVO.getCoditv(), vehiculoBBDD.getCoditv())) {
				resultado.setCodigoItvAnt(vehiculoBBDD.getCoditv());
				if (vehiculoBBDD.getCoditv() != null) {
					resultado.setValorModificado(new Long(2));
				} else if (resultado.getValorModificado() != 2) {
					resultado.setValorModificado(new Long(1));
				}
				resultado.setCodigoItvNue(vehiculoVO.getCoditv());
			}
			if (!utiles.sonIgualesString(vehiculoVO.getIdColor(), vehiculoBBDD.getIdColor())) {
				resultado.addTextoModificado( "Color,");
				if (vehiculoBBDD.getIdColor() != null) {
					resultado.setValorModificado(new Long(2));
				} else if (resultado.getValorModificado() != 2) {
					resultado.setValorModificado(new Long(1));
				}
			}
			if (!utiles.sonIgualesString(vehiculoVO.getConceptoItv(), vehiculoBBDD.getConceptoItv())) {
				resultado.addTextoModificado( "Concepto Itv,");
				if (vehiculoBBDD.getConceptoItv() != null) {
					resultado.setValorModificado(new Long(2));
				} else if (resultado.getValorModificado() != 2) {
					resultado.setValorModificado(new Long(1));
				}
			}
			if (!utiles.sonIgualesObjetos(vehiculoVO.getConsumo(), vehiculoBBDD.getConsumo())) {
				resultado.addTextoModificado( "Consumo,");
				if (vehiculoBBDD.getConsumo() != null) {
					resultado.setValorModificado(new Long(2));
				} else if (resultado.getValorModificado() != 2) {
					resultado.setValorModificado(new Long(1));
				}
			}
			if (!utiles.sonIgualesString(vehiculoVO.getIdCriterioConstruccion(), vehiculoBBDD.getIdCriterioConstruccion())) {
				resultado.addTextoModificado( "Criterio Construcción,");
				if (vehiculoBBDD.getIdCriterioConstruccion() != null) {
					resultado.setValorModificado(new Long(2));
				} else if (resultado.getValorModificado() != 2) {
					resultado.setValorModificado(new Long(1));
				}
			}
			if (!utiles.sonIgualesString(vehiculoVO.getIdCriterioUtilizacion(), vehiculoBBDD.getIdCriterioUtilizacion())) {
				resultado.addTextoModificado( "Cristerion Utilización,");
				if (vehiculoBBDD.getIdCriterioUtilizacion() != null) {
					resultado.setValorModificado(new Long(2));
				} else if (resultado.getValorModificado() != 2) {
					resultado.setValorModificado(new Long(1));
				}
			}
			if (!utiles.sonIgualesObjetos(vehiculoVO.getDiplomatico(), vehiculoBBDD.getDiplomatico())) {
				resultado.addTextoModificado( "Diplomatico,");
				if (vehiculoBBDD.getDiplomatico() != null) {
					resultado.setValorModificado(new Long(2));
				} else if (resultado.getValorModificado() != 2) {
					resultado.setValorModificado(new Long(1));
				}
			}
			if (!utiles.diferenciaNulls(vehiculoVO.getDireccion(), vehiculoBBDD.getDireccion()) || (vehiculoVO.getDireccion() != null && vehiculoBBDD.getDireccion() != null && !utiles
					.sonIgualesObjetos(vehiculoVO.getDireccion().getIdDireccion(), vehiculoBBDD.getDireccion().getIdDireccion()))) {
				resultado.addTextoModificado( "Direccion,");
				if (vehiculoBBDD.getDireccion() != null && vehiculoBBDD.getDireccion().getIdDireccion() != null) {
					resultado.setValorModificado(new Long(2));
				} else if (resultado.getValorModificado() != 2) {
					resultado.setValorModificado(new Long(1));
				}
			}
			if (!utiles.sonIgualesString(vehiculoVO.getIdDirectivaCee(), vehiculoBBDD.getIdDirectivaCee())) {
				resultado.addTextoModificado( "Directiva Cee,");
				if (vehiculoBBDD.getIdDirectivaCee() != null) {
					resultado.setValorModificado(new Long(2));
				} else if (resultado.getValorModificado() != 2) {
					resultado.setValorModificado(new Long(1));
				}
			}
			if (!utiles.sonIgualesObjetos(vehiculoVO.getDistanciaEjes(), vehiculoBBDD.getDistanciaEjes())) {
				resultado.addTextoModificado( "Distancia Ejes,");
				if (vehiculoBBDD.getDistanciaEjes() != null) {
					resultado.setValorModificado(new Long(2));
				} else if (resultado.getValorModificado() != 2) {
					resultado.setValorModificado(new Long(1));
				}
			}
			if (!utiles.sonIgualesString(vehiculoVO.getEcoInnovacion(), vehiculoBBDD.getEcoInnovacion())) {
				resultado.addTextoModificado( "Eco Innovación,");
				if (vehiculoBBDD.getEcoInnovacion() != null) {
					resultado.setValorModificado(new Long(2));
				} else if (resultado.getValorModificado() != 2) {
					resultado.setValorModificado(new Long(1));
				}
			}
			if (!utiles.sonIgualesString(vehiculoVO.getIdEpigrafe(), vehiculoBBDD.getIdEpigrafe())) {
				resultado.addTextoModificado( "Epigrafe,");
				if (vehiculoBBDD.getIdEpigrafe() != null) {
					resultado.setValorModificado(new Long(2));
				} else if (resultado.getValorModificado() != 2) {
					resultado.setValorModificado(new Long(1));
				}
			}
			if (!utiles.sonIgualesString(vehiculoVO.getEstacionItv(), vehiculoBBDD.getEstacionItv())) {
				resultado.addTextoModificado( "Estación Itv,");
				if (vehiculoBBDD.getEstacionItv() != null) {
					resultado.setValorModificado(new Long(2));
				} else if (resultado.getValorModificado() != 2) {
					resultado.setValorModificado(new Long(1));
				}
			}
			if (!utiles.sonIgualesObjetos(vehiculoVO.getExcesoPeso(), vehiculoBBDD.getExcesoPeso())) {
				resultado.addTextoModificado( "Exceso Peso,");
				if (vehiculoBBDD.getExcesoPeso() != null) {
					resultado.setValorModificado(new Long(2));
				} else if (resultado.getValorModificado() != 2) {
					resultado.setValorModificado(new Long(1));
				}
			}
			if (!utiles.sonIgualesString(vehiculoVO.getFabricacion(), vehiculoBBDD.getFabricacion())) {
				resultado.addTextoModificado( "Fabricación,");
				if (vehiculoBBDD.getFabricacion() != null) {
					resultado.setValorModificado(new Long(2));
				} else if (resultado.getValorModificado() != 2) {
					resultado.setValorModificado(new Long(1));
				}
			}
			if (!utiles.sonIgualesString(vehiculoVO.getFabricante(), vehiculoBBDD.getFabricante())) {
				resultado.addTextoModificado( "Fabricante,");
				if (vehiculoBBDD.getFabricante() != null) {
					resultado.setValorModificado(new Long(2));
				} else if (resultado.getValorModificado() != 2) {
					resultado.setValorModificado(new Long(1));
				}
			}
			if (!utiles.sonIgualesString(vehiculoVO.getFabricanteBase(), vehiculoBBDD.getFabricanteBase())) {
				resultado.addTextoModificado( "Fabricante Base,");
				if (vehiculoBBDD.getFabricanteBase() != null) {
					resultado.setValorModificado(new Long(2));
				} else if (resultado.getValorModificado() != 2) {
					resultado.setValorModificado(new Long(1));
				}
			}
			if (utiles.compararFechaDate(vehiculoVO.getFecdesde(), vehiculoBBDD.getFecdesde()) != 0) {
				resultado.addTextoModificado( "Fecha desde,");
				if (vehiculoBBDD.getFecdesde() != null) {
					resultado.setValorModificado(new Long(2));
				} else if (resultado.getValorModificado() != 2) {
					resultado.setValorModificado(new Long(1));
				}
			}
			if (utiles.compararFechaDate(vehiculoVO.getFechaInspeccion(), vehiculoBBDD.getFechaInspeccion()) != 0) {
				resultado.addTextoModificado( "Fecha Inspección,");
				if (vehiculoBBDD.getFechaInspeccion() != null) {
					resultado.setValorModificado(new Long(2));
				} else if (resultado.getValorModificado() != 2) {
					resultado.setValorModificado(new Long(1));
				}
			}
			if (utiles.compararFechaDate(vehiculoVO.getFechaItv(), vehiculoBBDD.getFechaItv()) != 0) {
				resultado.addTextoModificado( "Fecha Itv,");
				if (vehiculoBBDD.getFechaItv() != null) {
					resultado.setValorModificado(new Long(2));
				} else if (resultado.getValorModificado() != 2) {
					resultado.setValorModificado(new Long(1));
				}
			}
			if (!utiles.sonIgualesObjetos(vehiculoVO.getEsSiniestro(), vehiculoBBDD.getEsSiniestro())) {
				resultado.addTextoModificado( "Siniestro total,");
				if (vehiculoBBDD.getEsSiniestro() != null) {
					resultado.setValorModificado(new Long(2));
				} else if (resultado.getValorModificado() != 2) {
					resultado.setValorModificado(new Long(1));
				}
			}
			if (utiles.compararFechaDate(vehiculoVO.getFechaMatriculacion(), vehiculoBBDD.getFechaMatriculacion()) != 0) {
				resultado.addTextoModificado( "Fecha Matriculación,");
				if (vehiculoBBDD.getFechaMatriculacion() != null) {
					resultado.setValorModificado(new Long(2));
				} else if (resultado.getValorModificado() != 2) {
					resultado.setValorModificado(new Long(1));
				}
			}
			if (!utiles.sonIgualesObjetos(vehiculoVO.getIdVehiculoPrever(), vehiculoBBDD.getIdVehiculoPrever())) {
				resultado.addTextoModificado( "Vehiculo Prever,");
				if (vehiculoBBDD.getIdVehiculoPrever() != null) {
					resultado.setValorModificado(new Long(2));
				} else if (resultado.getValorModificado() != 2) {
					resultado.setValorModificado(new Long(1));
				}
			}
			if (!utiles.sonIgualesString(vehiculoVO.getIdLugarAdquisicion(), vehiculoBBDD.getIdLugarAdquisicion())) {
				resultado.addTextoModificado( "Lugar Adquisición,");
				if (vehiculoBBDD.getIdLugarAdquisicion() != null) {
					resultado.setValorModificado(new Long(2));
				} else if (resultado.getValorModificado() != 2) {
					resultado.setValorModificado(new Long(1));
				}
			}
			if (!utiles.sonIgualesString(vehiculoVO.getCodigoMarca(), vehiculoBBDD.getCodigoMarca())) {
				resultado.addTextoModificado( "Marca,");
				if (vehiculoBBDD.getCodigoMarca() != null) {
					resultado.setValorModificado(new Long(2));
				} else if (resultado.getValorModificado() != 2) {
					resultado.setValorModificado(new Long(1));
				}
			}
			if (!utiles.sonIgualesString(vehiculoVO.getCodigoMarcaBase(), vehiculoBBDD.getCodigoMarcaBase())) {
				resultado.addTextoModificado( "Marca Base,");
				if (vehiculoBBDD.getCodigoMarcaBase() != null) {
					resultado.setValorModificado(new Long(2));
				} else if (resultado.getValorModificado() != 2) {
					resultado.setValorModificado(new Long(1));
				}
			}
			if (!utiles.sonIgualesString(vehiculoVO.getMatricula() != null ? vehiculoVO.getMatricula().toUpperCase() : null, vehiculoBBDD.getMatricula() != null ? vehiculoBBDD.getMatricula()
					.toUpperCase() : null)) {
				resultado.setMatriculaAnt(vehiculoBBDD.getMatricula() != null ? vehiculoBBDD.getMatricula().toUpperCase() : null);
				if (vehiculoBBDD.getMatricula() != null) {
					resultado.setValorModificado(new Long(2));
				} else if (resultado.getValorModificado() != 2) {
					resultado.setValorModificado(new Long(1));
				}
				resultado.setMatriculaNue(vehiculoVO.getMatricula() != null ? vehiculoVO.getMatricula() : null);
			}
			if (!utiles.sonIgualesString(vehiculoVO.getModelo() != null ? vehiculoVO.getModelo().toUpperCase() : null, vehiculoBBDD.getModelo() != null ? vehiculoBBDD.getModelo().toUpperCase()
					: null)) {
				resultado.addTextoModificado( "Modelo,");
				if (vehiculoBBDD.getModelo() != null) {
					resultado.setValorModificado(new Long(2));
				} else if (resultado.getValorModificado() != 2) {
					resultado.setValorModificado(new Long(1));
				}
			}
			if (!utiles.sonIgualesObjetos(vehiculoVO.getMom(), vehiculoBBDD.getMom())) {
				resultado.addTextoModificado( "Mom,");
				if (vehiculoBBDD.getMom() != null) {
					resultado.setValorModificado(new Long(2));
				} else if (resultado.getValorModificado() != 2) {
					resultado.setValorModificado(new Long(1));
				}
			}
			if (!utiles.sonIgualesObjetos(vehiculoVO.getMomBase(), vehiculoBBDD.getMomBase())) {
				resultado.addTextoModificado( "Mom Base,");
				if (vehiculoBBDD.getMomBase() != null) {
					resultado.setValorModificado(new Long(2));
				} else if (resultado.getValorModificado() != 2) {
					resultado.setValorModificado(new Long(1));
				}
			}
			if (!utiles.sonIgualesString(vehiculoVO.getIdMotivoItv(), vehiculoBBDD.getIdMotivoItv())) {
				resultado.addTextoModificado( "Motivo Itv,");
				if (vehiculoBBDD.getIdMotivoItv() != null) {
					resultado.setValorModificado(new Long(2));
				} else if (resultado.getValorModificado() != 2) {
					resultado.setValorModificado(new Long(1));
				}
			}
			if (!utiles.sonIgualesString(vehiculoVO.getMtmaItv(), vehiculoBBDD.getMtmaItv())) {
				resultado.addTextoModificado( "Mtma Itv,");
				if (vehiculoBBDD.getMtmaItv() != null) {
					resultado.setValorModificado(new Long(2));
				} else if (resultado.getValorModificado() != 2) {
					resultado.setValorModificado(new Long(1));
				}
			}
			if (!utiles.sonIgualesObjetos(vehiculoVO.getnCilindros(), vehiculoBBDD.getnCilindros())) {
				resultado.addTextoModificado( "Numero Cilindros,");
				if (vehiculoBBDD.getnCilindros() != null) {
					resultado.setValorModificado(new Long(2));
				} else if (resultado.getValorModificado() != 2) {
					resultado.setValorModificado(new Long(1));
				}
			}
			if (!utiles.sonIgualesString(vehiculoVO.getnHomologacion() != null ? vehiculoVO.getnHomologacion().toUpperCase() : null, vehiculoBBDD.getnHomologacion() != null ? vehiculoBBDD
					.getnHomologacion().toUpperCase() : null)) {
				resultado.addTextoModificado( "Número Homologación,");
				if (vehiculoBBDD.getnHomologacion() != null) {
					resultado.setValorModificado(new Long(2));
				} else if (resultado.getValorModificado() != 2) {
					resultado.setValorModificado(new Long(1));
				}
			}
			if (!utiles.sonIgualesString(vehiculoVO.getnHomologacionBase() != null ? vehiculoVO.getnHomologacionBase().toUpperCase() : null, vehiculoBBDD.getnHomologacionBase() != null ? vehiculoBBDD
					.getnHomologacionBase().toUpperCase() : null)) {
				resultado.addTextoModificado( "Número Homologación Base,");
				if (vehiculoBBDD.getnHomologacionBase() != null) {
					resultado.setValorModificado(new Long(2));
				} else if (resultado.getValorModificado() != 2) {
					resultado.setValorModificado(new Long(1));
				}
			}
			if (!utiles.sonIgualesString(vehiculoVO.getNifIntegrador() != null ? vehiculoVO.getNifIntegrador().toUpperCase() : null, vehiculoBBDD.getNifIntegrador() != null ? vehiculoBBDD
					.getNifIntegrador().toUpperCase() : null)) {
				resultado.addTextoModificado( "Nif Integrador,");
				if (vehiculoBBDD.getNifIntegrador() != null) {
					resultado.setValorModificado(new Long(2));
				} else if (resultado.getValorModificado() != 2) {
					resultado.setValorModificado(new Long(1));
				}
			}
			if (!utiles.sonIgualesString(vehiculoVO.getNive(), vehiculoBBDD.getNive())) {
				resultado.addTextoModificado( "Nive,");
				if (vehiculoBBDD.getNive() != null) {
					resultado.setValorModificado(new Long(2));
				} else if (resultado.getValorModificado() != 2) {
					resultado.setValorModificado(new Long(1));
				}
			}
			if (!utiles.sonIgualesString(vehiculoVO.getNivelEmisiones(), vehiculoBBDD.getNivelEmisiones())) {
				resultado.addTextoModificado( "Nivel Emisiones,");
				if (vehiculoBBDD.getNivelEmisiones() != null) {
					resultado.setValorModificado(new Long(2));
				} else if (resultado.getValorModificado() != 2) {
					resultado.setValorModificado(new Long(1));
				}
			}
			if (!utiles.sonIgualesObjetos(vehiculoVO.getnPlazasPie(), vehiculoBBDD.getnPlazasPie())) {
				resultado.addTextoModificado( "Num Plazas de Pie,");
				if (vehiculoBBDD.getnPlazasPie() != null) {
					resultado.setValorModificado(new Long(2));
				} else if (resultado.getValorModificado() != 2) {
					resultado.setValorModificado(new Long(1));
				}
			}
			if (!utiles.sonIgualesObjetos(vehiculoVO.getnRuedas(), vehiculoBBDD.getnRuedas())) {
				resultado.addTextoModificado( "Número Ruedas,");
				if (vehiculoBBDD.getnRuedas() != null) {
					resultado.setValorModificado(new Long(2));
				} else if (resultado.getValorModificado() != 2) {
					resultado.setValorModificado(new Long(1));
				}
			}
			if (!utiles.sonIgualesString(vehiculoVO.getnSerie(), vehiculoBBDD.getnSerie())) {
				resultado.addTextoModificado( "Número serie,");
				if (vehiculoBBDD.getnSerie() != null) {
					resultado.setValorModificado(new Long(2));
				} else if (resultado.getValorModificado() != 2) {
					resultado.setValorModificado(new Long(1));
				}
			}
			if (!utiles.sonIgualesString(vehiculoVO.getPaisFabricacion(), vehiculoBBDD.getPaisFabricacion())) {
				resultado.addTextoModificado( "Pais Fabricación,");
				if (vehiculoBBDD.getPaisFabricacion() != null) {
					resultado.setValorModificado(new Long(2));
				} else if (resultado.getValorModificado() != 2) {
					resultado.setValorModificado(new Long(1));
				}
			}
			if (!utiles.sonIgualesString(vehiculoVO.getPaisImportacion(), vehiculoBBDD.getPaisImportacion())) {
				resultado.addTextoModificado( "Pais Importación,");
				if (vehiculoBBDD.getPaisImportacion() != null) {
					resultado.setValorModificado(new Long(2));
				} else if (resultado.getValorModificado() != 2) {
					resultado.setValorModificado(new Long(1));
				}
			}
			if (!utiles.sonIgualesString(vehiculoVO.getPesoMma(), vehiculoBBDD.getPesoMma())) {
				resultado.addTextoModificado( "Peso Mma,");
				if (vehiculoBBDD.getPesoMma() != null) {
					resultado.setValorModificado(new Long(2));
				} else if (resultado.getValorModificado() != 2) {
					resultado.setValorModificado(new Long(1));
				}
			}
			if (!utiles.sonIgualesObjetos(vehiculoVO.getPlazas(), vehiculoBBDD.getPlazas())) {
				resultado.addTextoModificado( "Plazas,");
				if (vehiculoBBDD.getPlazas() != null) {
					resultado.setValorModificado(new Long(2));
				} else if (resultado.getValorModificado() != 2) {
					resultado.setValorModificado(new Long(1));
				}
			}
			if (!utiles.sonIgualesBigDecimal(vehiculoVO.getPotenciaFiscal(), vehiculoBBDD.getPotenciaFiscal())) {
				resultado.addTextoModificado( "Potencia Fiscal,");
				if (vehiculoBBDD.getPotenciaFiscal() != null) {
					resultado.setValorModificado(new Long(2));
				} else if (resultado.getValorModificado() != 2) {
					resultado.setValorModificado(new Long(1));
				}
			}
			if (!utiles.sonIgualesBigDecimal(vehiculoVO.getPotenciaNeta(), vehiculoBBDD.getPotenciaNeta())) {
				resultado.addTextoModificado( "Potencia Neta,");
				if (vehiculoBBDD.getPotenciaNeta() != null) {
					resultado.setValorModificado(new Long(2));
				} else if (resultado.getValorModificado() != 2) {
					resultado.setValorModificado(new Long(1));
				}
			}
			if (!utiles.sonIgualesBigDecimal(vehiculoVO.getPotenciaPeso(), vehiculoBBDD.getPotenciaPeso())) {
				resultado.addTextoModificado( "Potencia Peso,");
				if (vehiculoBBDD.getPotenciaPeso() != null) {
					resultado.setValorModificado(new Long(2));
				} else if (resultado.getValorModificado() != 2) {
					resultado.setValorModificado(new Long(1));
				}
			}
			if (!utiles.sonIgualesString(vehiculoVO.getProcedencia(), vehiculoBBDD.getProcedencia())) {
				resultado.addTextoModificado( "Procedencia,");
				if (vehiculoBBDD.getProcedencia() != null) {
					resultado.setValorModificado(new Long(2));
				} else if (resultado.getValorModificado() != 2) {
					resultado.setValorModificado(new Long(1));
				}
			}
			if (!utiles.sonIgualesObjetos(vehiculoVO.getProvinciaPrimeraMatricula(), vehiculoBBDD.getProvinciaPrimeraMatricula())) {
				resultado.addTextoModificado( "Provincia Primera Matricula,");
				if (vehiculoBBDD.getProvinciaPrimeraMatricula() != null) {
					resultado.setValorModificado(new Long(2));
				} else if (resultado.getValorModificado() != 2) {
					resultado.setValorModificado(new Long(1));
				}
			}
			if (!utiles.sonIgualesObjetos(vehiculoVO.getReduccionEco(), vehiculoBBDD.getReduccionEco())) {
				resultado.addTextoModificado( "Reducción Eco,");
				if (vehiculoBBDD.getReduccionEco() != null) {
					resultado.setValorModificado(new Long(2));
				} else if (resultado.getValorModificado() != 2) {
					resultado.setValorModificado(new Long(1));
				}
			}
			if (!utiles.sonIgualesCombo(vehiculoVO.getIdServicio(), vehiculoBBDD.getIdServicio())) {
				resultado.addTextoModificado( "Servicio Trafico,");
				if (vehiculoBBDD.getIdServicio() != null) {
					resultado.setValorModificado(new Long(2));
				} else if (resultado.getValorModificado() != 2) {
					resultado.setValorModificado(new Long(1));
				}
			}
			if (!utiles.sonIgualesString(vehiculoVO.getIdServicioAnterior(), vehiculoBBDD.getIdServicioAnterior())) {
				resultado.addTextoModificado( "Servicio Trafico Anterior,");
				if (vehiculoBBDD.getIdServicioAnterior() != null) {
					resultado.setValorModificado(new Long(2));
				} else if (resultado.getValorModificado() != 2) {
					resultado.setValorModificado(new Long(1));
				}
			}
			if (!utiles.sonIgualesString(vehiculoVO.getTara(), vehiculoBBDD.getTara())) {
				resultado.addTextoModificado( "Tara,");
				if (vehiculoBBDD.getTara() != null) {
					resultado.setValorModificado(new Long(2));
				} else if (resultado.getValorModificado() != 2) {
					resultado.setValorModificado(new Long(1));
				}
			}
			if (!utiles.sonIgualesString(vehiculoVO.getTipoAlimentacion(), vehiculoBBDD.getTipoAlimentacion())) {
				resultado.addTextoModificado( "Tipo Alimentación,");
				if (vehiculoBBDD.getTipoAlimentacion() != null) {
					resultado.setValorModificado(new Long(2));
				} else if (resultado.getValorModificado() != 2) {
					resultado.setValorModificado(new Long(1));
				}
			}
			if (!utiles.sonIgualesString(vehiculoVO.getTipoBase(), vehiculoBBDD.getTipoBase())) {
				resultado.addTextoModificado( "Tipo Base,");
				if (vehiculoBBDD.getTipoBase() != null) {
					resultado.setValorModificado(new Long(2));
				} else if (resultado.getValorModificado() != 2) {
					resultado.setValorModificado(new Long(1));
				}
			}
			if (!utiles.sonIgualesString(vehiculoVO.getTipoIndustria(), vehiculoBBDD.getTipoIndustria())) {
				resultado.addTextoModificado( "Tipo Industria,");
				if (vehiculoBBDD.getTipoIndustria() != null) {
					resultado.setValorModificado(new Long(2));
				} else if (resultado.getValorModificado() != 2) {
					resultado.setValorModificado(new Long(1));
				}
			}
			if (!utiles.sonIgualesString(vehiculoVO.getTipoItv(), vehiculoBBDD.getTipoItv())) {
				resultado.addTextoModificado( "Tipo Itv,");
				if (vehiculoBBDD.getTipoItv() != null) {
					resultado.setValorModificado(new Long(2));
				} else if (resultado.getValorModificado() != 2) {
					resultado.setValorModificado(new Long(1));
				}
			}
			if (!utiles.sonIgualesString(vehiculoVO.getIdTipoTarjetaItv(), vehiculoBBDD.getIdTipoTarjetaItv())) {
				resultado.addTextoModificado( "Tipo Tarjeta ITV,");
				if (vehiculoBBDD.getIdTipoTarjetaItv() != null) {
					resultado.setValorModificado(new Long(2));
				} else if (resultado.getValorModificado() != 2) {
					resultado.setValorModificado(new Long(1));
				}
			}
			if (!utiles.sonIgualesCombo(vehiculoVO.getTipoVehiculo(), vehiculoBBDD.getTipoVehiculo())) {
				resultado.setTipoVehiculoAnt(vehiculoBBDD.getTipoVehiculo());
				if (vehiculoBBDD.getTipoVehiculo() != null) {
					resultado.setValorModificado(new Long(2));
				} else if (resultado.getValorModificado() != 2) {
					resultado.setValorModificado(new Long(1));
				}
				resultado.setTipoVehiculoNue(vehiculoVO.getTipoVehiculo());
			}
			if (!utiles.sonIgualesString(vehiculoVO.getTipvehi(), vehiculoBBDD.getTipvehi())) {
				resultado.addTextoModificado( "Tipo Vehículo cam,");
				if (vehiculoBBDD.getTipvehi() != null) {
					resultado.setValorModificado(new Long(2));
				} else if (resultado.getValorModificado() != 2) {
					resultado.setValorModificado(new Long(1));
				}
			}
			if (!utiles.sonIgualesString(vehiculoVO.getVariante(), vehiculoBBDD.getVariante())) {
				resultado.addTextoModificado( "Variante,");
				if (vehiculoBBDD.getVariante() != null) {
					resultado.setValorModificado(new Long(2));
				} else if (resultado.getValorModificado() != 2) {
					resultado.setValorModificado(new Long(1));
				}
			}
			if (!utiles.sonIgualesString(vehiculoVO.getVarianteBase(), vehiculoBBDD.getVarianteBase())) {
				resultado.addTextoModificado( "Variante Base,");
				if (vehiculoBBDD.getVarianteBase() != null) {
					resultado.setValorModificado(new Long(2));
				} else if (resultado.getValorModificado() != 2) {
					resultado.setValorModificado(new Long(1));
				}
			}
			if (!utiles.sonIgualesObjetos(vehiculoVO.getVehiUsado(), vehiculoBBDD.getVehiUsado())) {
				resultado.addTextoModificado( "Vehículo Usado,");
				if (vehiculoBBDD.getVehiUsado() != null) {
					resultado.setValorModificado(new Long(2));
				} else if (resultado.getValorModificado() != 2) {
					resultado.setValorModificado(new Long(1));
				}
			}
			if (!utiles.sonIgualesString(vehiculoVO.getMatriAyuntamiento(), vehiculoBBDD.getMatriAyuntamiento())) {
				resultado.addTextoModificado( "Matriculacion Ayuntamiento,");
				if (vehiculoBBDD.getMatriAyuntamiento() != null) {
					resultado.setValorModificado(new Long(2));
				} else if (resultado.getValorModificado() != 2) {
					resultado.setValorModificado(new Long(1));
				}
			}
			if (utiles.compararFechaDate(vehiculoVO.getLimiteMatrTuris(), vehiculoBBDD.getLimiteMatrTuris()) != 0) {
				resultado.addTextoModificado( "Limite Matriculación Turismo,");
				if (vehiculoBBDD.getLimiteMatrTuris() != null) {
					resultado.setValorModificado(new Long(2));
				} else if (resultado.getValorModificado() != 2) {
					resultado.setValorModificado(new Long(1));
				}
			}
			if (utiles.compararFechaDate(vehiculoVO.getFechaPrimMatri(), vehiculoBBDD.getFechaPrimMatri()) != 0) {
				resultado.addTextoModificado( "Fecha Primera Matriculación,");
				if (vehiculoBBDD.getFechaPrimMatri() != null) {
					resultado.setValorModificado(new Long(2));
				} else if (resultado.getValorModificado() != 2) {
					resultado.setValorModificado(new Long(1));
				}
			}
			if (!utiles.sonIgualesString(vehiculoVO.getIdLugarMatriculacion(), vehiculoBBDD.getIdLugarMatriculacion())) {
				resultado.addTextoModificado( "Lugar Primera Matriculación,");
				if (vehiculoBBDD.getIdLugarMatriculacion() != null) {
					resultado.setValorModificado(new Long(2));
				} else if (resultado.getValorModificado() != 2) {
					resultado.setValorModificado(new Long(1));
				}
			}
			if (!utiles.sonIgualesString(vehiculoVO.getMatriculaOrigen(), vehiculoBBDD.getMatriculaOrigen())) {
				resultado.addTextoModificado( "Matricula Origen,");
				if (vehiculoBBDD.getMatriculaOrigen() != null) {
					resultado.setValorModificado(new Long(2));
				} else if (resultado.getValorModificado() != 2) {
					resultado.setValorModificado(new Long(1));
				}
			}
			if (!utiles.sonIgualesObjetos(vehiculoVO.getKmUso(), vehiculoBBDD.getKmUso())) {
				resultado.addTextoModificado( "Km Uso,");
				if (vehiculoBBDD.getKmUso() != null) {
					resultado.setValorModificado(new Long(2));
				} else if (resultado.getValorModificado() != 2) {
					resultado.setValorModificado(new Long(1));
				}
			}
			if (!utiles.sonIgualesObjetos(vehiculoVO.getHorasUso(), vehiculoBBDD.getHorasUso())) {
				resultado.addTextoModificado( "Horas uso,");
				if (vehiculoBBDD.getHorasUso() != null) {
					resultado.setValorModificado(new Long(2));
				} else if (resultado.getValorModificado() != 2) {
					resultado.setValorModificado(new Long(1));
				}
			}
			if (!utiles.sonIgualesObjetos(vehiculoVO.getImportado(), vehiculoBBDD.getImportado())) {
				resultado.addTextoModificado( "Importado,");
				if (vehiculoBBDD.getImportado() != null) {
					resultado.setValorModificado(new Long(2));
				} else if (resultado.getValorModificado() != 2) {
					resultado.setValorModificado(new Long(1));
				}
			}
			if (!utiles.sonIgualesString(vehiculoVO.getIdProcedencia(), vehiculoBBDD.getIdProcedencia())) {
				resultado.addTextoModificado( "Procedencia,");
				if (vehiculoBBDD.getIdProcedencia() != null) {
					resultado.setValorModificado(new Long(2));
				} else if (resultado.getValorModificado() != 2) {
					resultado.setValorModificado(new Long(1));
				}
			}
			if (!utiles.sonIgualesString(vehiculoVO.getVersion(), vehiculoBBDD.getVersion())) {
				resultado.addTextoModificado( "Versión,");
				if (vehiculoBBDD.getVersion() != null) {
					resultado.setValorModificado(new Long(2));
				} else if (resultado.getValorModificado() != 2) {
					resultado.setValorModificado(new Long(1));
				}
			}
			if (!utiles.sonIgualesString(vehiculoVO.getVersionBase(), vehiculoBBDD.getVersionBase())) {
				resultado.addTextoModificado( "Versión Base,");
				if (vehiculoBBDD.getVersionBase() != null) {
					resultado.setValorModificado(new Long(2));
				} else if (resultado.getValorModificado() != 2) {
					resultado.setValorModificado(new Long(1));
				}
			}
			if (!utiles.sonIgualesObjetos(vehiculoVO.getViaAnterior(), vehiculoBBDD.getViaAnterior())) {
				resultado.addTextoModificado( "Via Anterior,");
				if (vehiculoBBDD.getViaAnterior() != null) {
					resultado.setValorModificado(new Long(2));
				} else if (resultado.getValorModificado() != 2) {
					resultado.setValorModificado(new Long(1));
				}
			}
			if (!utiles.sonIgualesObjetos(vehiculoVO.getViaPosterior(), vehiculoBBDD.getViaPosterior())) {
				resultado.addTextoModificado( "Via Posterior,");
				if (vehiculoBBDD.getViaPosterior() != null) {
					resultado.setValorModificado(new Long(2));
				} else if (resultado.getValorModificado() != 2) {
					resultado.setValorModificado(new Long(1));
				}
			}
			if (!utiles.sonIgualesString(vehiculoVO.getMatriculaOrigExtr(), vehiculoBBDD.getMatriculaOrigExtr())) {
				resultado.addTextoModificado( "Matricula Origen Extranjera,");
				if (vehiculoBBDD.getMatriculaOrigExtr() != null) {
					resultado.setValorModificado(new Long(2));
				} else if (resultado.getValorModificado() != 2) {
					resultado.setValorModificado(new Long(1));
				}
			}
		} catch (Exception e) {
			log.error("Error al comparar los vehiculo, error: ", e);
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("Error al comparar los vehiculo.");
		}
		return resultado;
	}

}
