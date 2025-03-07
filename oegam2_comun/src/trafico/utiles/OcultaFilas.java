package trafico.utiles;

import org.displaytag.decorator.TableDecorator;

import escrituras.beans.contratos.PermisoUsuarioContratoBean;

public class OcultaFilas extends TableDecorator {

	public OcultaFilas() {
	}

	@Override
	public String addRowId() {
		PermisoUsuarioContratoBean row = (PermisoUsuarioContratoBean) getCurrentRowObject();
		return row.getCodigo_Funcion();
	}

	public String addRowClass() {
		PermisoUsuarioContratoBean row = (PermisoUsuarioContratoBean) getCurrentRowObject();

		String salida = "";
		if (row.getCodigo_Funcion_Padre() != null) {
			salida = "hijo " + row.getCodigo_Funcion_Padre();
		}

		return salida;
	}

}