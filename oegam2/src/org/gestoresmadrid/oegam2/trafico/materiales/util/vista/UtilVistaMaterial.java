package org.gestoresmadrid.oegam2.trafico.materiales.util.vista;

import java.util.List;

import org.gestoresmadrid.core.docPermDistItv.model.enumerados.TipoDistintivo;
import org.gestoresmadrid.core.tipoPermDistItv.model.enumerado.TipoDocumentoImprimirEnum;
import org.gestoresmadrid.core.trafico.materiales.model.enumerados.EstadoIncidencia;
import org.gestoresmadrid.core.trafico.materiales.model.enumerados.EstadoMaterial;
import org.gestoresmadrid.core.trafico.materiales.model.enumerados.EstadoPedPaquete;
import org.gestoresmadrid.core.trafico.materiales.model.enumerados.TipoDocumentoEnum;
import org.gestoresmadrid.core.trafico.materiales.model.enumerados.TipoIncidencia;
import org.gestoresmadrid.oegam2comun.presentacion.jpt.enumerados.JefaturasJPTEnum;
import org.gestoresmadrid.oegam2comun.trafico.materiales.model.service.ServicioConsultaMateriales;
import org.gestoresmadrid.oegam2comun.trafico.materiales.view.dto.ComboMaterialDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

public class UtilVistaMaterial {

	private static UtilVistaMaterial utilesVista;

	private UtilVistaMaterial() {
		super();
	}

	public static UtilVistaMaterial getInstance() {
		if (utilesVista == null) {
			utilesVista = new UtilVistaMaterial();
			SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(utilesVista);
		}
		return utilesVista;
	}

	@Autowired private ServicioConsultaMateriales servicioConsultaMateriales;

	public List<ComboMaterialDto> getComboMateriales() {
		return servicioConsultaMateriales.getMateriales();
	}

	public TipoIncidencia[] getTipoIncidencia() {
		return TipoIncidencia.values();
	}

	public EstadoMaterial[] getEstadoMaterial() {
		return EstadoMaterial.values();
	}

	public TipoDocumentoImprimirEnum[] getTipoDocumentoImprimir(){
		return TipoDocumentoImprimirEnum.values();
	}

	public JefaturasJPTEnum[] getJefaturasJPTEnums() {
		return JefaturasJPTEnum.values();
	}

	public TipoDocumentoEnum[] getTipoDocumentoEnum() {
		return TipoDocumentoEnum.values();
	}

	public TipoDistintivo[] getTipoDistintivo(){
		return TipoDistintivo.values();
	}

	public EstadoPedPaquete[] getEstadoPedPaquete() {
		return EstadoPedPaquete.values();
	}

	public EstadoIncidencia[] getEstadoIncidencia() {
		return EstadoIncidencia.values();
	}

}