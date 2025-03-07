package org.gestoresmadrid.core.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.hibernate.Criteria;

/**
 * Entidad relacionada y tipo de relacion
 * 
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface FilterRelationship {

	/**
	 * Nombre del atributo en la entidad
	 */
	String name();

	/**
	 * Tipo de join que debe realizarse. Por defecto INNER_JOIN
	 */
	int joinType() default Criteria.INNER_JOIN;

}
