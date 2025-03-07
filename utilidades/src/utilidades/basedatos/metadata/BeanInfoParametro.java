package utilidades.basedatos.metadata;

public class BeanInfoParametro {

	private String nombre;
	private int tipoInOutParametro;
	private String sqlTypeName;

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public int getTipoInOutParametro() {
		return tipoInOutParametro;
	}

	public void setTipoInOutParametro(int tipoInOutParametro) {
		this.tipoInOutParametro = tipoInOutParametro;
	}

	public String getSqlTypeName() {
		return sqlTypeName;
	}

	public void setSqlTypeName(String sqlTypeName) {
		this.sqlTypeName = sqlTypeName;
	}

}
