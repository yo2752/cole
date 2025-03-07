package trafico.decorators;

import org.gestoresmadrid.core.model.enumerados.EstadoTramiteTrafico;

import trafico.beans.ConsultaTramiteTraficoFilaTablaResultadoBean;
import trafico.utiles.DecoradorTablasConsultaTramiteTrafico;
import trafico.utiles.enumerados.TipoTramiteTrafico;

public class ImprimirTramiteTraficoDecorator  extends DecoradorTablasConsultaTramiteTrafico {

	public String getEstado(){
		ConsultaTramiteTraficoFilaTablaResultadoBean fila = (ConsultaTramiteTraficoFilaTablaResultadoBean) getCurrentRowObject();
		String result = "<input type=\"hidden\" id=\"estado"+fila.getNumExpediente()+"\" value=\""+EstadoTramiteTrafico.convertirAValor(fila.getEstado())+"\"/>"+fila.getEstado();
		return result;
	}
	public String getCheckbox(){
		ConsultaTramiteTraficoFilaTablaResultadoBean fila = (ConsultaTramiteTraficoFilaTablaResultadoBean) getCurrentRowObject(); 
		String resultado ="<table align=\"center\"><tr>"
				+ "<td style=\"border: 0px;\"><input type=\"checkbox\" name=\"listaChecksConsultaTramite\" id=\"listaChecksConsultaTramite\" "
				+"value=\""
				+fila.getNumExpediente()
				+"\" checked=\"checked\" onchange=\"return marcarImprimir('"+TipoTramiteTrafico.convertirNombreAValor(fila.getTipoTramite())+"');\"/>"
				+"</td><td style=\"border: 0px;\">"
				+ "<img src=\"img/history.png\" alt=\"ver evolución\" style=\"margin-right:10px;height:20px;width:20px;cursor:pointer;\" "+
		  		"onclick=\"abrirEvolucionTramite('"+fila.getNumExpediente()+"');\" title=\"Ver evolución\"/>"+
		  	"</td></tr></table>";
		return resultado;
	}
	
	@Override
	public String addRowClass(){
		//Hacemos que los trámites impresos no se muestren en rojo.
		return null;
	}
	
		
	
}
