package escrituras.beans;

import java.util.ArrayList;
import java.util.List;

public class ResultBeanImportacion {

	//IMPLEMENTED JVG - 03/04/2018. No necesitamos sacar información adicional acerca del nombre del fichero, tipo fichero y formato fichero.
	
	private List<String> listaMensajes;
	private List<String> listaMensajesError;
	private boolean error;

	/**Constructor
	 * 
	 */
	
	public ResultBeanImportacion(){
		
		init();
		
	}
	
	
	private void init() {
		// TODO Auto-generated method stub
		setListaMensajes(new ArrayList<String>());
		setListaMensajesError (new ArrayList<String>());
	}


	/**
	 * @return the listaMensajes
	 */
	public List<String> getListaMensajes() {
		return listaMensajes;
	}
	/**
	 * @param listaMensajes the listaMensajes to set
	 */
	public void setListaMensajes(List<String> listaMensajes) {
		this.listaMensajes = listaMensajes;
	}

	
	public List<String> getListaMensajesError() {
		return listaMensajesError;
	}

	public void setListaMensajesError(List<String> listaMensajesError) {
		this.listaMensajesError = listaMensajesError;
	}

	public boolean isError() {
		return error;
	}

	public void setError(boolean error) {
		this.error = error;
	}

	/**
	 * Añadimos un mensaje a la lista
	 * @param mensaje2
	 */
	public void addMensaje(String mensaje){
		this.listaMensajes.add(mensaje);
	}
	
	/**Añadimos un mensaje de warning a la lista como añadido a listaMensajes.
	 * @param mensaje
	 */
	public void addMensajeError( String mensaje){
		this.listaMensajesError.add(mensaje);
	}


}
