package org.gestoresmadrid.oegamComun.utiles;

import java.io.Serializable;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import org.gestoresmadrid.core.docPermDistItv.model.enumerados.EstadoPermisoDistintivoItv;
import org.gestoresmadrid.core.enumerados.EstadoImpr;
import org.gestoresmadrid.core.enumerados.JefaturasJPTEnum;
import org.gestoresmadrid.core.enumerados.TipoImpr;
import org.gestoresmadrid.core.model.enumerados.TipoTramiteTrafico;
import org.gestoresmadrid.utilidades.components.GestorPropiedades;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;


@Component
public class UtilesVistaTramites implements Serializable{

	private static final long serialVersionUID = 2657389683402021926L;
	
	private static UtilesVistaTramites utilesVistaTramites;
	
	private TipoTramiteTrafico[] listaTipoTramiteTrafico = null;

	@Autowired
	GestorPropiedades gestorPropiedades;
	
	public static UtilesVistaTramites getInstance() {
		if (utilesVistaTramites == null) {
			utilesVistaTramites = new UtilesVistaTramites();
			SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(utilesVistaTramites);

		}
		return utilesVistaTramites;
	}
	
	public TipoTramiteTrafico[] getComboTipoTramite() {
		if (listaTipoTramiteTrafico == null) {
			TipoTramiteTrafico[] listaTipoTramiteTraficoAux = null;
			int x = 0;
			String[] arrayComboTipoTramiteDesechar = null;
			String comboTipoTramiteDesechar = "";
			boolean permitido = true;
			comboTipoTramiteDesechar = gestorPropiedades.valorPropertie("combo.tipoTramite.desechar");
			arrayComboTipoTramiteDesechar = comboTipoTramiteDesechar.split(",");
			listaTipoTramiteTrafico = new TipoTramiteTrafico[TipoTramiteTrafico.values().length - arrayComboTipoTramiteDesechar.length];
			listaTipoTramiteTraficoAux = new TipoTramiteTrafico[listaTipoTramiteTrafico.length];
			listaTipoTramiteTrafico = TipoTramiteTrafico.values();
			for (TipoTramiteTrafico tipoTramiteTrafico : listaTipoTramiteTrafico) {
				for (String cadenaDesechar : arrayComboTipoTramiteDesechar) {
					if (tipoTramiteTrafico.getNombreEnum().equals(cadenaDesechar)) {
						permitido = false;
					}
				}
				if (permitido) {
					listaTipoTramiteTraficoAux[x] = tipoTramiteTrafico;
					x++;
				}
				permitido = true;
			}
			listaTipoTramiteTrafico = listaTipoTramiteTraficoAux;
		}
		return listaTipoTramiteTrafico;
	}
	public static List<TipoTramiteTrafico> getListaTipoTramitesIMPR() {
		List<TipoTramiteTrafico> listaBuscador = new ArrayList<>();
		listaBuscador.add(TipoTramiteTrafico.Matriculacion);
		listaBuscador.add(TipoTramiteTrafico.TransmisionElectronica);
		return listaBuscador;
	}
	public static List<TipoImpr> getListaTipoImprConsulta() {
		List<TipoImpr> listaBuscador = new ArrayList<>();
		listaBuscador.add(TipoImpr.Permiso_Circulacion);
		listaBuscador.add(TipoImpr.Ficha_Tecnica);
		return listaBuscador;
	} 
	public EstadoPermisoDistintivoItv[] getEstadosSolicitud(){
		return EstadoPermisoDistintivoItv.values();
	}
	public JefaturasJPTEnum[] getJefatura(){
		return JefaturasJPTEnum.values();
	}
	public static EstadoImpr[] listadoOrdenado() {
	    EstadoImpr[] estadosOrdenados = EstadoImpr.values();
		return estadosOrdenados;
	}
	public static List<EstadoPermisoDistintivoItv> getEstadosImpresionesImpr(){
		List<EstadoPermisoDistintivoItv> listaEstados = new ArrayList<EstadoPermisoDistintivoItv>();
		listaEstados.add(EstadoPermisoDistintivoItv.Iniciado);
		listaEstados.add(EstadoPermisoDistintivoItv.Generado);
		listaEstados.add(EstadoPermisoDistintivoItv.GENERADO_JEFATURA);
		listaEstados.add(EstadoPermisoDistintivoItv.ORDENADO_DOC);
		listaEstados.add(EstadoPermisoDistintivoItv.Finalizado_Error);
		listaEstados.add(EstadoPermisoDistintivoItv.Pendiente_Impresion);
		listaEstados.add(EstadoPermisoDistintivoItv.IMPR_No_Encontrado);
		listaEstados.add(EstadoPermisoDistintivoItv.Impresion_OK);
		listaEstados.add(EstadoPermisoDistintivoItv.IMPRESO_GESTORIA);
		listaEstados.add(EstadoPermisoDistintivoItv.IMPRIMIENDO);
		listaEstados.add(EstadoPermisoDistintivoItv.ANULADO);
		listaEstados.add(EstadoPermisoDistintivoItv.DOCUMENTO_ORDENADO);
		return listaEstados;
	}
	
	public List<Long> convertirArrayStringToListLong(String[] listaString) {
		List<Long> listaConvertido = new ArrayList<Long>();
		for (String stringConversor : listaString) {
			listaConvertido.add(new Long(stringConversor));
		}
		return listaConvertido;
	}
	
	public Long stringToLong(String cadena) {
		if (cadena == null || cadena.trim().isEmpty()) {
			return (long) 0;
		}
		DecimalFormat formateador = new DecimalFormat("#,##0.00");
		try {
			return new Long(formateador.parse(cadena).toString());
		} catch (ParseException e) {
			return null;
		}
	}

	public Long[] convertirStringArrayToLong(String[] elementos) {
		if (elementos == null) {
			return null;
		}
		List<Long> convertidos = new ArrayList<Long>();
		for (int i = 0; i < elementos.length; i++) {
			Long l = stringToLong(elementos[i]);
			if (l != null) {
				convertidos.add(l);
			}
		}
		Long[] result = new Long[convertidos.size()];
		return convertidos.toArray(result);
	}
	
	public static List<EstadoPermisoDistintivoItv> getEstadosSolicitudDocImpr() {
		List<EstadoPermisoDistintivoItv> listaEstados = new ArrayList<EstadoPermisoDistintivoItv>();
		listaEstados.add(EstadoPermisoDistintivoItv.Iniciado);
		listaEstados.add(EstadoPermisoDistintivoItv.Doc_Recibido);
		listaEstados.add(EstadoPermisoDistintivoItv.Finalizado_Error);
		listaEstados.add(EstadoPermisoDistintivoItv.Pendiente_Impresion);
		listaEstados.add(EstadoPermisoDistintivoItv.IMPR_No_Encontrado);
		listaEstados.add(EstadoPermisoDistintivoItv.Impresion_OK);
		listaEstados.add(EstadoPermisoDistintivoItv.IMPRIMIENDO);
		listaEstados.add(EstadoPermisoDistintivoItv.Pendiente_Respuesta);
		return listaEstados;
	}
	
}
