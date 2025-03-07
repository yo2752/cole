package org.gestoresmadrid.oegam2.view.decorator;

import org.displaytag.decorator.TableDecorator;
import org.gestoresmadrid.core.docPermDistItv.model.enumerados.EstadoPermisoDistintivoItv;
import org.gestoresmadrid.oegam2comun.permisoDistintivoItv.view.bean.GestionPermisosFichasDgtBean;

public class DecoratorPermisosFichasDgt extends TableDecorator {

	public String addRowClass() {
		GestionPermisosFichasDgtBean gestionDgt = (GestionPermisosFichasDgtBean) getCurrentRowObject();
		if (EstadoPermisoDistintivoItv.Impresion_OK.getValorEnum().equals(gestionDgt.getEstadoPetImp())) {
			return "enlaceImpreso impreso";
		}
		return null;
	}

	public String getNumImpresiones() {
		GestionPermisosFichasDgtBean gestionDgt = (GestionPermisosFichasDgtBean) getCurrentRowObject();
		if (gestionDgt.getNumImpresiones() != null && !gestionDgt.getNumImpresiones().isEmpty()) {
			return gestionDgt.getNumImpresiones() + "/10";
		}
		return "0/10";
	}
}