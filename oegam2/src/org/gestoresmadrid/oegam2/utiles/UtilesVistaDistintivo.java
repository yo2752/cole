package org.gestoresmadrid.oegam2.utiles;

import java.util.ArrayList;
import java.util.List;

import org.gestoresmadrid.core.docPermDistItv.model.enumerados.EstadoPermisoDistintivoItv;
import org.gestoresmadrid.core.docPermDistItv.model.enumerados.TipoDistintivo;
import org.gestoresmadrid.core.model.beans.DatoMaestroBean;
import org.gestoresmadrid.core.tipoPermDistItv.model.enumerado.TipoDocumentoImprimirEnum;
import org.gestoresmadrid.oegam2comun.contrato.model.service.ServicioContrato;
import org.gestoresmadrid.oegam2comun.distintivo.view.dto.VehiculoNoMatriculadoOegamDto;
import org.gestoresmadrid.oegamComun.acceso.service.impl.UtilesColegiado;
import org.gestoresmadrid.utilidades.components.GestorPropiedades;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

public class UtilesVistaDistintivo {

	private static UtilesVistaDistintivo utilesVistaPermisoDistintivo;

	@Autowired
	ServicioContrato servicioContrato;

	@Autowired
	private GestorPropiedades gestorPropiedades;

	@Autowired
	private UtilesColegiado utilesColegiado;

	public static UtilesVistaDistintivo getInstance() {
		if (utilesVistaPermisoDistintivo == null) {
			utilesVistaPermisoDistintivo = new UtilesVistaDistintivo();
			SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(utilesVistaPermisoDistintivo);
		}
		return utilesVistaPermisoDistintivo;
	}

	public Boolean esColegiadoGenTitularMasivo() {
		if (!utilesColegiado.tienePermisoAdmin()) {
			String colegiadoPermitidos = gestorPropiedades.valorPropertie("colegiado.habilitados.genMasivo.titular");
			if (colegiadoPermitidos != null && !colegiadoPermitidos.isEmpty()) {
				if (!"TODOS".equals(colegiadoPermitidos)
						&& !colegiadoPermitidos.contains(utilesColegiado.getNumColegiadoSession())) {
					return Boolean.FALSE;
				}
			} else {
				return Boolean.FALSE;
			}
		}
		return Boolean.TRUE;
	}

	public List<EstadoPermisoDistintivoItv> getEstadosDistintivoConsulta() {
		return EstadoPermisoDistintivoItv.getEstadosConsulta();
	}

	public List<EstadoPermisoDistintivoItv> getEstadosImpresion() {
		return EstadoPermisoDistintivoItv.getEstadosImpresiones();
	}

	public EstadoPermisoDistintivoItv[] getEstadosDistintivo() {
		return EstadoPermisoDistintivoItv.values();
	}

	public TipoDistintivo[] getTiposDistintivos() {
		return TipoDistintivo.values();
	}

	public TipoDocumentoImprimirEnum[] getTiposDocumentos() {
		return TipoDocumentoImprimirEnum.values();
	}

	public List<DatoMaestroBean> getComboContratosHabilitados() {
		return servicioContrato.getComboContratosHabilitados();
	}

	public List<DatoMaestroBean> getListaTipoSINO() {
		List<DatoMaestroBean> listaDatosMaestro = new ArrayList<DatoMaestroBean>();
		listaDatosMaestro.add(new DatoMaestroBean("S", "SI"));
		listaDatosMaestro.add(new DatoMaestroBean("N", "NO"));
		return listaDatosMaestro;
	}

	public Boolean esEstadoGuardableVNMO(VehiculoNoMatriculadoOegamDto vhNotMatOegamDto) {
		if (vhNotMatOegamDto == null || vhNotMatOegamDto.getEstadoSolicitud() == null
				|| vhNotMatOegamDto.getEstadoSolicitud().isEmpty()
				|| EstadoPermisoDistintivoItv.Iniciado.getValorEnum().equals(vhNotMatOegamDto.getEstadoSolicitud())) {
			return Boolean.TRUE;
		}
		return Boolean.FALSE;
	}

	public Boolean esEstadoSolicitableVNMO(VehiculoNoMatriculadoOegamDto vhNotMatOegamDto) {
		if (vhNotMatOegamDto != null && vhNotMatOegamDto.getEstadoSolicitud() != null
				&& !vhNotMatOegamDto.getEstadoSolicitud().isEmpty()
				&& EstadoPermisoDistintivoItv.Iniciado.getValorEnum().equals(vhNotMatOegamDto.getEstadoSolicitud())) {
			return Boolean.TRUE;
		}
		return Boolean.FALSE;
	}

	public Boolean esEstadoGenerableVNMO(VehiculoNoMatriculadoOegamDto vhNotMatOegamDto) {
		if (vhNotMatOegamDto != null && vhNotMatOegamDto.getEstadoSolicitud() != null
				&& !vhNotMatOegamDto.getEstadoSolicitud().isEmpty()
				&& EstadoPermisoDistintivoItv.Doc_Recibido.getValorEnum().equals(vhNotMatOegamDto.getEstadoSolicitud())
				&& EstadoPermisoDistintivoItv.Iniciado.getValorEnum().equals(vhNotMatOegamDto.getEstadoImpresion())) {
			return Boolean.TRUE;
		}
		return Boolean.FALSE;
	}

	public Boolean esEstadoAutorizableVNMO(VehiculoNoMatriculadoOegamDto vhNotMatOegamDto) {
		if (vhNotMatOegamDto != null && vhNotMatOegamDto.getEstadoSolicitud() != null
				&& !vhNotMatOegamDto.getEstadoSolicitud().isEmpty() && EstadoPermisoDistintivoItv.PDTE_CONF_COLEGIADO
						.getValorEnum().equals(vhNotMatOegamDto.getEstadoImpresion())) {
			return Boolean.TRUE;
		}
		return Boolean.FALSE;
	}

	public Boolean esActivoEitv() {
		if ("SI".equals(gestorPropiedades.valorPropertie("generar.eitv'"))) {
			return Boolean.TRUE;
		}
		return Boolean.FALSE;
	}

}