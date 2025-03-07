package org.gestoresmadrid.core.model.exceptions;

/**
 * Cubre las excepciones que ocurran en la capa de persistencia
 * que originan un rollback de la transaccion.
 *
 * @author erds
 *
 */
public class TransactionalException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 9157317498550053052L;

	public TransactionalException(String paramString) {
		super(paramString);
	}

	public TransactionalException(String paramString, Throwable paramThrowable) {
		super(paramString, paramThrowable);
	}

	public TransactionalException(Throwable paramThrowable) {
		super(paramThrowable);
	}
	
}
