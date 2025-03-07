package org.gestoresmadrid.core.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.gestoresmadrid.core.annotations.enumeration.FilterSimpleExpressionRestriction;

/**
 * Usado para especificar que el campo es parte de un filtro. Y se debe tener en
 * cuenta, si viene informado, a la hora de montar el criteria
 * 
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface FilterSimpleExpression {

	/**
	 * Nombre del atributo en la entidad, si no viene informado se toma el mismo
	 * nombre que el del campo
	 */
	String name() default "";

	/**
	 * Establecer a true cuando se quiera negar la restriccion
	 * @return
	 */
	boolean not() default false;

	/**
	 * Tipo de restriccion que aplica en el criteria.
	 */
	FilterSimpleExpressionRestriction restriction() default FilterSimpleExpressionRestriction.EQ;

}
