package trafico.decorators;

import org.displaytag.decorator.TableDecorator;

import trafico.beans.TramiteTraficoBean;

public class MatriculasSolInfoDecorator extends TableDecorator {
	
	public String getModificar() {
		String identificador = "";
		if(((TramiteTraficoBean)this.getCurrentRowObject()).getVehiculo().getMatricula() != null &&
				!((TramiteTraficoBean)this.getCurrentRowObject()).getVehiculo().getMatricula().equals("")){
			identificador = ((TramiteTraficoBean)this.getCurrentRowObject()).getVehiculo().getMatricula();
		}else if(((TramiteTraficoBean)this.getCurrentRowObject()).getVehiculo().getBastidor() != null &&
				!((TramiteTraficoBean)this.getCurrentRowObject()).getVehiculo().getBastidor().equals("")){
			identificador = ((TramiteTraficoBean)this.getCurrentRowObject()).getVehiculo().getBastidor();
		}
		String devolucion = "<input type=\"button\" class=\"botonpeque\" id=\"modificar_" + identificador + 
				 "\" onclick=\"modificarSolInfo(this)\" title=\"Modificar la matrícula\" value=\"Modificar\" />";
		return devolucion;

	}
	
	public String getMatricula(){
		String identificador = "";
		if(((TramiteTraficoBean)this.getCurrentRowObject()).getVehiculo().getMatricula() != null &&
				!((TramiteTraficoBean)this.getCurrentRowObject()).getVehiculo().getMatricula().equals("")){
			identificador = ((TramiteTraficoBean)this.getCurrentRowObject()).getVehiculo().getMatricula();
		}else if(((TramiteTraficoBean)this.getCurrentRowObject()).getVehiculo().getBastidor() != null &&
				!((TramiteTraficoBean)this.getCurrentRowObject()).getVehiculo().getBastidor().equals("")){
			identificador = ((TramiteTraficoBean)this.getCurrentRowObject()).getVehiculo().getBastidor();
		}
		String matricula = ((TramiteTraficoBean)this.getCurrentRowObject()).getVehiculo().getMatricula();
		if(matricula == null){
			matricula=""; 
		}
		String devolucion = "<input type=\"text\" size=\"8\" maxlength=\"7\"id=\"matricula_" + identificador + "\" value=\"" + matricula + "\"/>";
		return devolucion;
	}
	
	public String getBastidor(){
		if(((TramiteTraficoBean)this.getCurrentRowObject()).getReferenciaPropia() != null &&
				((TramiteTraficoBean)this.getCurrentRowObject()).getReferenciaPropia().equals("anuntis")){
			return "n.a";
		}
		String identificador = "";
		if(((TramiteTraficoBean)this.getCurrentRowObject()).getVehiculo().getMatricula() != null &&
				!((TramiteTraficoBean)this.getCurrentRowObject()).getVehiculo().getMatricula().equals("")){
			identificador = ((TramiteTraficoBean)this.getCurrentRowObject()).getVehiculo().getMatricula();
		}else if(((TramiteTraficoBean)this.getCurrentRowObject()).getVehiculo().getBastidor() != null &&
				!((TramiteTraficoBean)this.getCurrentRowObject()).getVehiculo().getBastidor().equals("")){
			identificador = ((TramiteTraficoBean)this.getCurrentRowObject()).getVehiculo().getBastidor();
		}
		String bastidor = ((TramiteTraficoBean)this.getCurrentRowObject()).getVehiculo().getBastidor();
		if(bastidor == null){
			bastidor=""; 
		}
		String devolucion = "<input type=\"text\" size=\"22\" maxlength=\"21\"id=\"bastidor_" + identificador + "\" value=\"" + bastidor + "\"/>";
		return devolucion;
	}

}
