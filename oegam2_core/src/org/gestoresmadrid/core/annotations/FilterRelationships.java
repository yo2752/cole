package org.gestoresmadrid.core.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Listado ordenado de entidades relacionadas, hasta llegar al atributo por el que se
 * aplica la restriccion
 * 
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface FilterRelationships {

	FilterRelationship[] value();

}
