package trafico.matriculacion.acciones;

import java.io.File;

import escrituras.beans.ResultBean;
import general.acciones.ActionBase;

public class EtiquetasMatriculaAction extends ActionBase {

	private static final long serialVersionUID = 1L;

	private ResultBean resultBean;

	// Importacion Etiquetas
	private File fileUpload; // Fichero a importar desde la pagina JSP
	private String fileUploadFileName; // nombre del fichero importado
	private String fileUploadContentType; // tipo del fichero importado
	private Boolean menorTamMax; // Indica si se llega al action, ya que si se excede el tamaño máximo, el
									// interceptor devuelve directamente input

	public ResultBean getResultBean() {
		return resultBean;
	}

	public void setResultBean(ResultBean resultBean) {
		this.resultBean = resultBean;
	}

	public File getFileUpload() {
		return fileUpload;
	}

	public void setFileUpload(File fileUpload) {
		this.fileUpload = fileUpload;
	}

	public String getFileUploadFileName() {
		return fileUploadFileName;
	}

	public void setFileUploadFileName(String fileUploadFileName) {
		this.fileUploadFileName = fileUploadFileName;
	}

	public String getFileUploadContentType() {
		return fileUploadContentType;
	}

	public void setFileUploadContentType(String fileUploadContentType) {
		this.fileUploadContentType = fileUploadContentType;
	}

	public Boolean getMenorTamMax() {
		return menorTamMax;
	}

	public void setMenorTamMax(Boolean menorTamMax) {
		this.menorTamMax = menorTamMax;
	}

	@Override
	public String execute() throws Exception {
		return super.execute();
	}

	public String inicio() {
		return "etiquetasMatricula";
	}

	public String importar() {
		// Si no se rellena el input file se direcciona de nuevo a la pagina de cargar
		// fichero
		if (getFileUpload() == null) {
			// log.error("Error al importar el fichero");
			return "etiquetasMatricula";
		}

		return "etiquetasMatricula";
	}

}