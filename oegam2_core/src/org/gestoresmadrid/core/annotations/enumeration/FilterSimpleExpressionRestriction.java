package org.gestoresmadrid.core.annotations.enumeration;

import java.util.Collection;
import java.util.Date;

import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;

import utilidades.estructuras.FechaFraccionada;

public enum FilterSimpleExpressionRestriction {
	IN {
		public Criterion getRestriction(String propertyName, Object value) {
			if (value instanceof Collection<?>) {
				return Restrictions.in(propertyName, (Collection<?>) value);
			} else {
				return Restrictions.in(propertyName, (Object[]) value);
			}
		}
	},
	IN_WITH_NULL {
		public Criterion getRestriction(String propertyName, Object value) {
			if (value instanceof Collection<?>) {
				return Restrictions.or(Restrictions.in(propertyName, (Collection<?>) value), Restrictions.isNull(propertyName));
			} else {
				return Restrictions.or(Restrictions.in(propertyName, (Object[]) value), Restrictions.isNull(propertyName));
			}
		}
	},
	EQ {
		public Criterion getRestriction(String propertyName, Object value) {
			if (value != null && value instanceof String) {
				return Restrictions.eq(propertyName, ((String) value).trim());
			} else {
				return Restrictions.eq(propertyName, value);
			}
		}
	},
	EQ_WITH_NULL {
		public Criterion getRestriction(String propertyName, Object value) {
			if (value != null && value instanceof String) {
				return Restrictions.or(Restrictions.eq(propertyName, ((String) value).trim()), Restrictions.isNull(propertyName));
			} else {
				return Restrictions.or(Restrictions.eq(propertyName, value), Restrictions.isNull(propertyName));
			}
		}
	},
	NE {
		public Criterion getRestriction(String propertyName, Object value) {
			return Restrictions.ne(propertyName, value);
		}
	},
	LIKE {
		public Criterion getRestriction(String propertyName, Object value) {
			return Restrictions.like(propertyName, value);
		}
	},
	LIKE_PORCENTAJE{ //LIKE %__%
		public Criterion getRestriction(String propertyName, Object value) {
			return Restrictions.like(propertyName, "%"+ value + "%");
		}
	},
	ILIKE_PORCENTAJE{ //ILIKE %__%
		public Criterion getRestriction(String propertyName, Object value) {
			return Restrictions.ilike(propertyName, "%"+ value + "%");
		}
	},
	GT {
		public Criterion getRestriction(String propertyName, Object value) {
			return Restrictions.gt(propertyName, value);
		}
	},
	LT {
		public Criterion getRestriction(String propertyName, Object value) {
			return Restrictions.lt(propertyName, value);
		}
	},
	LE {
		public Criterion getRestriction(String propertyName, Object value) {
			return Restrictions.le(propertyName, value);
		}
	},
	GE {
		public Criterion getRestriction(String propertyName, Object value) {
			return Restrictions.ge(propertyName, value);
		}
	},
	BETWEEN {
		public Criterion getRestriction(String propertyName, Object value) {
			if (value instanceof FechaFraccionada) {
				FechaFraccionada fechaFraccionada = (FechaFraccionada) value;
				Date fechaIni = fechaFraccionada.getFechaMinInicio();
				Date fechaFin = fechaFraccionada.getFechaMaxFin();
				if (fechaIni != null && fechaFin != null) {
					return Restrictions.between(propertyName, fechaIni, fechaFin);
				} else if (fechaIni != null) {
					return Restrictions.ge(propertyName, fechaIni);
				} else if (fechaFin != null) {
					return Restrictions.le(propertyName, fechaFin);
				}
			}
			return null;
		}
	},
	BETWEEN_WITH_NULL {
		public Criterion getRestriction(String propertyName, Object value) {
			if (value instanceof FechaFraccionada) {
				FechaFraccionada fechaFraccionada = (FechaFraccionada) value;
				Date fechaIni = fechaFraccionada.getFechaMinInicio();
				Date fechaFin = fechaFraccionada.getFechaMaxFin();
				if (fechaIni != null && fechaFin != null) {
					return Restrictions.or(Restrictions.between(propertyName, fechaIni, fechaFin), Restrictions.isNull(propertyName));
				} else if (fechaIni != null) {
					return Restrictions.or(Restrictions.ge(propertyName, fechaIni), Restrictions.isNull(propertyName));
				} else if (fechaFin != null) {
					return Restrictions.or(Restrictions.le(propertyName, fechaFin), Restrictions.isNull(propertyName));
				}
			}
			return null;
		}
	},
	ISNULL {
		public Criterion getRestriction(String propertyName, Object value) {
			if (value != null && value instanceof Boolean && ((Boolean) value)) {
				return Restrictions.isNull(propertyName);
			}
			return null;
		}
	},
	ISNOTNULL {
		public Criterion getRestriction(String propertyName, Object value) {
			if (value != null && value instanceof Boolean && ((Boolean) value)) {
				return Restrictions.isNotNull(propertyName);
			}
			return null;
		}
	};

	public Criterion getRestriction(String propertyName, Object value) {
		return null;
	}
}
