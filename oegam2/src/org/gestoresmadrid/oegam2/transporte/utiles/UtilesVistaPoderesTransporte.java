package org.gestoresmadrid.oegam2.transporte.utiles;

import java.util.Collections;
import java.util.List;

import org.gestoresmadrid.core.model.beans.DatoMaestroBean;
import org.gestoresmadrid.oegam2comun.contrato.model.service.ServicioContrato;
import org.gestoresmadrid.oegam2comun.direccion.model.service.ServicioMunicipio;
import org.gestoresmadrid.oegam2comun.direccion.model.service.ServicioProvincia;
import org.gestoresmadrid.oegam2comun.direccion.model.service.ServicioPueblo;
import org.gestoresmadrid.oegam2comun.direccion.model.service.ServicioTipoVia;
import org.gestoresmadrid.oegam2comun.direccion.view.dto.PuebloDto;
import org.gestoresmadrid.oegam2comun.direccion.view.dto.TipoViaDto;
import org.gestoresmadrid.oegam2comun.transporte.view.dto.PoderTransporteDto;
import org.gestoresmadrid.oegamComun.direccion.view.dto.MunicipioDto;
import org.gestoresmadrid.oegamComun.direccion.view.dto.ProvinciaDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

public class UtilesVistaPoderesTransporte {

	private static UtilesVistaPoderesTransporte utilesVistaPoderesTransporte;
	
	@Autowired
	ServicioProvincia servicioProvincia;
	
	@Autowired
	ServicioMunicipio servicioMunicipio;
	
	@Autowired
	ServicioPueblo servicioPueblo;
	
	@Autowired
	ServicioTipoVia servicioTipoVia;
	
	@Autowired
	ServicioContrato servicioContrato;

	public static UtilesVistaPoderesTransporte getInstance() {
		if (utilesVistaPoderesTransporte == null) {
			utilesVistaPoderesTransporte = new UtilesVistaPoderesTransporte();
			SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(utilesVistaPoderesTransporte);
		}
		return utilesVistaPoderesTransporte;
	}
	
	public List<ProvinciaDto> getListaProvincias(){
		return servicioProvincia.getListaProvincias();
	}
	
	public List<MunicipioDto> getListaMunicipios(PoderTransporteDto poderTransporteDto){
		if(poderTransporteDto != null && poderTransporteDto.getIdProvincia() != null && !poderTransporteDto.getIdProvincia().isEmpty()){
			return servicioMunicipio.listaMunicipios(poderTransporteDto.getIdProvincia());
		}
		return Collections.emptyList();
	}
	
	public List<TipoViaDto> getListaTipoVias(PoderTransporteDto poderTransporteDto){
		if(poderTransporteDto != null && poderTransporteDto.getIdProvincia() != null && !poderTransporteDto.getIdProvincia().isEmpty()){
			return servicioTipoVia.listadoTipoVias(poderTransporteDto.getIdProvincia());
		}
		return Collections.emptyList();
	}
	
	public List<PuebloDto> getListaPueblos(PoderTransporteDto poderTransporteDto){
		if(poderTransporteDto != null && poderTransporteDto.getIdProvincia() != null && !poderTransporteDto.getIdProvincia().isEmpty()
			&& poderTransporteDto.getIdMunicipio() != null && !poderTransporteDto.getIdMunicipio().isEmpty()){
			return servicioPueblo.listaPueblos(poderTransporteDto.getIdProvincia(), poderTransporteDto.getIdMunicipio());
		}
		return Collections.emptyList();
	}
	
	public List<DatoMaestroBean> getComboContratosHabilitados() {
		return servicioContrato.getComboContratosHabilitados();
	}
}
