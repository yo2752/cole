package trafico.utiles;

import org.displaytag.decorator.TableDecorator;

import escrituras.beans.contratos.FuncionAplicacionContratoBean;

public class OcultaFilasAplicacion extends TableDecorator {

	public OcultaFilasAplicacion() {
	}

	@Override
	public String addRowId() {
		FuncionAplicacionContratoBean row = (FuncionAplicacionContratoBean) getCurrentRowObject();
		return row.getCodigo_Funcion();
	}

	public String addRowClass() {
		FuncionAplicacionContratoBean row = (FuncionAplicacionContratoBean) getCurrentRowObject();

		String salida = "";
		if (row.getCodigo_Funcion_Padre() != null) {
			salida = "hijo " + row.getCodigo_Funcion_Padre();
		}

		return salida;
	}

}