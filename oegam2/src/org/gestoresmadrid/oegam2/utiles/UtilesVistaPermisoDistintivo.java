package org.gestoresmadrid.oegam2.utiles;

import java.util.ArrayList;
import java.util.List;

import org.gestoresmadrid.core.docPermDistItv.model.enumerados.EstadoPermisoDistintivoItv;
import org.gestoresmadrid.core.docPermDistItv.model.enumerados.TipoDistintivo;
import org.gestoresmadrid.core.docPermDistItv.model.enumerados.TipoTramite;
import org.gestoresmadrid.core.model.beans.DatoMaestroBean;
import org.gestoresmadrid.core.model.enumerados.EstadoConSinNive;
import org.gestoresmadrid.core.model.enumerados.TipoTramiteTrafico;
import org.gestoresmadrid.core.tipoPermDistItv.model.enumerado.TipoDocumentoImprimirEnum;
import org.gestoresmadrid.oegam2comun.contrato.model.service.ServicioContrato;
import org.gestoresmadrid.oegam2comun.permisoDistintivoItv.view.bean.GestionPermisoFilterBean;
import org.gestoresmadrid.oegam2comun.presentacion.jpt.enumerados.JefaturasJPTEnum;
import org.gestoresmadrid.oegamComun.acceso.service.impl.UtilesColegiado;
import org.gestoresmadrid.utilidades.components.GestorPropiedades;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import trafico.utiles.enumerados.TipoTransferencia;

public class UtilesVistaPermisoDistintivo {

	private static UtilesVistaPermisoDistintivo utilesVistaPermisoDistintivo;

	@Autowired
	ServicioContrato servicioContrato;

	@Autowired
	private GestorPropiedades gestorPropiedades;

	@Autowired
	private UtilesColegiado utilesColegiado;

	public static UtilesVistaPermisoDistintivo getInstance(){
		if (utilesVistaPermisoDistintivo == null) {
			utilesVistaPermisoDistintivo = new UtilesVistaPermisoDistintivo();
			SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(utilesVistaPermisoDistintivo);
		}
		return utilesVistaPermisoDistintivo;
	}

	public List<EstadoPermisoDistintivoItv> getEstadosPermisoDistintivoConsulta() {
		return EstadoPermisoDistintivoItv.getEstadosConsulta();
	}

	public List<EstadoPermisoDistintivoItv> getEstadosImpresion() {
		if (utilesColegiado.tienePermisosCiudadReal_IMPR()|| utilesColegiado.tienePermisosAlcala_IMPR()
				|| utilesColegiado.tienePermisosSegovia_IMPR() || utilesColegiado.tienePermisosCuenca_IMPR()
				|| utilesColegiado.tienePermisosGuadalajara_IMPR() || utilesColegiado.tienePermisosAvila_IMPR()
				|| utilesColegiado.tienePermisosMadrid_IMPR() || utilesColegiado.tienePermisosAlcorcon_IMPR()
				|| utilesColegiado.tienePermisoAdmin()) {
			return EstadoPermisoDistintivoItv.getEstadosImpresionesJefatura();
		}
		return EstadoPermisoDistintivoItv.getEstadosImpresiones();
	}

	public List<EstadoPermisoDistintivoItv> getEstadosEitvConsulta() {
		return EstadoPermisoDistintivoItv.getEstadosEitvConsulta();
	}
	public List<EstadoPermisoDistintivoItv> getEstadosJustificante() {
		return EstadoPermisoDistintivoItv.getEstadosJustificante();
	}

	public EstadoPermisoDistintivoItv[] getEstadosPermisoDistintivoEitv() {
		return EstadoPermisoDistintivoItv.values();
	}

	public TipoDistintivo[] getTiposDistintivos() {
		return TipoDistintivo.values();
	}

	public TipoTramite[] getTipoTramite() {
		return TipoTramite.values();
	}
	public TipoTramite[] getTipoTramiteFT() {
		TipoTramite[] tipo = new TipoTramite[2];
		tipo[0] = TipoTramite.Matriculacion;
		tipo[1] = TipoTramite.Duplicado;
		return tipo;
	}

	public TipoTransferencia[] getTipoTransferencia() {
		return TipoTransferencia.values();
	}

	public JefaturasJPTEnum[] getJefaturasJPTEnum() {
		return JefaturasJPTEnum.values();
	}

	public TipoDocumentoImprimirEnum[] getTiposDocumentos() {
		return TipoDocumentoImprimirEnum.values();
	}

	public List<DatoMaestroBean> getComboContratosHabilitados() {
		return servicioContrato.getComboContratosHabilitados();
	}

	public List<DatoMaestroBean> getListaTipoSINO() {
		List<DatoMaestroBean> listaDatosMaestro = new ArrayList<>();
		listaDatosMaestro.add(new DatoMaestroBean("S", "SI"));
		listaDatosMaestro.add(new DatoMaestroBean("N", "NO"));
		return listaDatosMaestro;
	}

	public Boolean esActivoEitv() {
		if ("SI".equals(gestorPropiedades.valorPropertie("generar.eitv'"))) {
			return Boolean.TRUE;
		}
		return Boolean.FALSE;
	}

	public Boolean tienePermisoImprJefatura() {
		if(utilesColegiado.tienePermisosAlcala_IMPR()
			|| utilesColegiado.tienePermisosAlcorcon_IMPR()
			|| utilesColegiado.tienePermisosAvila_IMPR()
			|| utilesColegiado.tienePermisosCiudadReal_IMPR()
			|| utilesColegiado.tienePermisosCuenca_IMPR()
			|| utilesColegiado.tienePermisosGuadalajara_IMPR()
			|| utilesColegiado.tienePermisosMadrid_IMPR()
			|| utilesColegiado.tienePermisosSegovia_IMPR()) {
			return Boolean.TRUE;
		}
		return Boolean.FALSE;
	}

	public Boolean esTipoTramiteCtit(GestionPermisoFilterBean gestionPermisos) {
		if (gestionPermisos != null && TipoTramiteTrafico.TransmisionElectronica.getValorEnum().equals(gestionPermisos.getTipoTramite())) {
			return Boolean.TRUE;
		}
		return Boolean.FALSE;
	}

	public static EstadoConSinNive[] getEstadoConSinNive() {
		return EstadoConSinNive.values();
	}
}