package org.gestoresmadrid.oegam2comun.registradores.view.dto;

import java.io.Serializable;

import org.gestoresmadrid.oegamComun.vehiculo.view.dto.VehiculoDto;

/**
 * Este recurso representa los contractos de Renting
 */
public class ContratoLeasingRentingDto implements Serializable {

	private static final long serialVersionUID = -4327015975599741341L;
	
	//Objeto Trámite Registro
	private TramiteRegistroDto tramiteRegistroDto;
	
	//Objeto Dossier
	private TramiteRegRbmDto dossierDto;
	
	//Objeto Reconocimiento Deuda
	private ReconocimientoDeudaDto reconocimientoDeudaDto;
	
	//Objeto Cuadro Amortización
	private CuadroAmortizacionDto cuadroAmortizacionDto;
	
	//Objeto Cláusula
	private ClausulaDto clausulaDto;
	
	//Objeto Bienes
	private PropiedadDto propiedadDto;
	 
	//Objeto Vehículo
	private VehiculoDto vehiculoDto;
	
	//Objeto Acuerdo
	private AcuerdoDto acuerdoDto;
	

	public TramiteRegistroDto getTramiteRegistroDto() {
		return tramiteRegistroDto;
	}
	public void setTramiteRegistroDto(TramiteRegistroDto tramiteRegistroDto) {
		this.tramiteRegistroDto = tramiteRegistroDto;
	}
	public TramiteRegRbmDto getDossierDto() {
		if(null == dossierDto){
			dossierDto = new TramiteRegRbmDto();
		}
		return dossierDto;
	}
	public void setDossierDto(TramiteRegRbmDto dossierDto) {
		this.dossierDto = dossierDto;
	}
	
	public ReconocimientoDeudaDto getReconocimientoDeudaDto() {
		return reconocimientoDeudaDto;
	}
	public void setReconocimientoDeudaDto(ReconocimientoDeudaDto reconocimientoDeudaDto) {
		this.reconocimientoDeudaDto = reconocimientoDeudaDto;
	}

	public CuadroAmortizacionDto getCuadroAmortizacionDto() {
		return cuadroAmortizacionDto;
	}
	public void setCuadroAmortizacionDto(CuadroAmortizacionDto cuadroAmortizacionDto) {
		this.cuadroAmortizacionDto = cuadroAmortizacionDto;
	}
	public ClausulaDto getClausulaDto() {
		return clausulaDto;
	}
	public void setClausulaDto(ClausulaDto clausulaDto) {
		this.clausulaDto = clausulaDto;
	}
	public PropiedadDto getPropiedadDto() {
		return propiedadDto;
	}
	public void setPropiedadDto(PropiedadDto propiedadDto) {
		this.propiedadDto = propiedadDto;
	}
	public VehiculoDto getVehiculoDto() {
		return vehiculoDto;
	}
	public void setVehiculoDto(VehiculoDto vehiculoDto) {
		this.vehiculoDto = vehiculoDto;
	}
	public AcuerdoDto getAcuerdoDto() {
		return acuerdoDto;
	}
	public void setAcuerdoDto(AcuerdoDto acuerdoDto) {
		this.acuerdoDto = acuerdoDto;
	}


}
