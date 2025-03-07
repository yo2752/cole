package utilidades.basedatos.metadata;

public class BeanMetadataProcedure {

	private String nombreProcedure;
	private String schema;
	private String catalog;

	public String getNombreProcedure() {
		return nombreProcedure;
	}

	public void setNombreProcedure(String nombreProcedure) {
		this.nombreProcedure = nombreProcedure;
	}

	public String getSchema() {
		return schema;
	}

	public void setSchema(String schema) {
		this.schema = schema;
	}

	public String getCatalog() {
		return catalog;
	}

	public void setCatalog(String catalog) {
		this.catalog = catalog;
	}

}