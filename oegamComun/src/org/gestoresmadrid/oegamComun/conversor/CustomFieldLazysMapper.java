package org.gestoresmadrid.oegamComun.conversor;

import java.io.Serializable;

import org.dozer.CustomFieldMapper;
import org.dozer.classmap.ClassMap;
import org.dozer.fieldmap.FieldMap;
import org.hibernate.Hibernate;

public class CustomFieldLazysMapper implements CustomFieldMapper, Serializable {

	private static final long serialVersionUID = 2006957751094570670L;

	@Override
	public boolean mapField(Object source, Object destination, Object sourceFieldValue, ClassMap paramClassMap, FieldMap paramFieldMap) {
		if (sourceFieldValue != null && !Hibernate.isInitialized(sourceFieldValue)) {
			return true;
		} else {
			return false;
		}
	}
}
