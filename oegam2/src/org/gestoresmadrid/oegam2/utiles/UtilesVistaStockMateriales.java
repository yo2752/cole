package org.gestoresmadrid.oegam2.utiles;

import java.util.ArrayList;
import java.util.List;

import org.gestoresmadrid.core.docPermDistItv.model.enumerados.EstadoPermisoDistintivoItv;
import org.gestoresmadrid.core.docPermDistItv.model.enumerados.TipoDistintivo;
import org.gestoresmadrid.core.docPermDistItv.model.enumerados.TipoTramite;
import org.gestoresmadrid.core.model.beans.DatoMaestroBean;
import org.gestoresmadrid.core.model.enumerados.TipoMaterial;
import org.gestoresmadrid.core.tipoPermDistItv.model.enumerado.TipoDocumentoImprimirEnum;
import org.gestoresmadrid.oegam2comun.contrato.model.service.ServicioContrato;
import org.gestoresmadrid.oegam2comun.presentacion.jpt.enumerados.JefaturasJPTEnum;
import org.gestoresmadrid.oegam2comun.registradores.enums.TipoOperacion;
import org.gestoresmadrid.oegamComun.acceso.service.impl.UtilesColegiado;
import org.gestoresmadrid.utilidades.components.GestorPropiedades;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import trafico.utiles.enumerados.TipoTransferencia;

public class UtilesVistaStockMateriales {

	private static UtilesVistaStockMateriales utilesVistaPermisoDistintivo;

	@Autowired
	ServicioContrato servicioContrato;

	@Autowired
	private GestorPropiedades gestorPropiedades;

	@Autowired
	private UtilesColegiado utilesColegiado;

	private static final String topeResultadosPorPagina = "999";
	private static List<String> comboResultadosPorPagina = null;
	private static List<String> comboResultadosPorPaginaTope = null;
	private static final String[] resultadosPorPagina = new String[] { "5", "10", "15", "20", "50", "100", "200", "500" };

	public static UtilesVistaStockMateriales getInstance() {
		if (utilesVistaPermisoDistintivo == null) {
			utilesVistaPermisoDistintivo = new UtilesVistaStockMateriales();
			SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(utilesVistaPermisoDistintivo);
		}
		return utilesVistaPermisoDistintivo;
	}

	public List<EstadoPermisoDistintivoItv> getEstadosPermisoDistintivoConsulta() {
		return EstadoPermisoDistintivoItv.getEstadosConsulta();
	}

	public List<EstadoPermisoDistintivoItv> getEstadosImpresion() {
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

	public TipoTransferencia[] getTipoTransferencia() {
		return TipoTransferencia.values();
	}

	public JefaturasJPTEnum[] getJefaturasJPTEnum() {
		return JefaturasJPTEnum.values();
	}

	public TipoMaterial[] getTipoMateriales() {
		return TipoMaterial.values();
	}

	public TipoDocumentoImprimirEnum[] getTiposDocumentos() {
		return TipoDocumentoImprimirEnum.values();
	}

	public List<DatoMaestroBean> getComboContratosHabilitados() {
		return servicioContrato.getComboContratosHabilitados();
	}

	public TipoOperacion[] getTipoOperacion(){
		return TipoOperacion.values();
	}

	public List<DatoMaestroBean> getListaTipoSINO() {
		List<DatoMaestroBean> listaDatosMaestro = new ArrayList<>();
		listaDatosMaestro.add(new DatoMaestroBean("S", "SI"));
		listaDatosMaestro.add(new DatoMaestroBean("N", "NO"));
		return listaDatosMaestro;
	}

	public Boolean esActivoEitv(){
		if ("SI".equals(gestorPropiedades.valorPropertie("generar.eitv'"))) {
			return Boolean.TRUE;
		}
		return Boolean.FALSE;
	}

	public List<String> getComboResultadosPorPagina() {
		if (comboResultadosPorPagina == null) {
			comboResultadosPorPagina = new ArrayList<>();
			for (int i = 0; i < resultadosPorPagina.length; i++) {
				comboResultadosPorPagina.add(resultadosPorPagina[i]);
			}
		}
		if (comboResultadosPorPaginaTope == null) {
			comboResultadosPorPaginaTope = new ArrayList<>();
			for (int i = 0; i < resultadosPorPagina.length; i++) {
				comboResultadosPorPaginaTope.add(resultadosPorPagina[i]);
			}
			comboResultadosPorPaginaTope.add(topeResultadosPorPagina);
		}
		String colegiadosSinCorreo = gestorPropiedades.valorPropertie("resultadosPagina.tope.permisos");
		if (utilesColegiado.tienePermisoAdmin() || (colegiadosSinCorreo != null
				&& colegiadosSinCorreo.contains(utilesColegiado.getNumColegiadoSession()))) {
			return comboResultadosPorPaginaTope;
		} else {
			return comboResultadosPorPagina;
		}
	}
}