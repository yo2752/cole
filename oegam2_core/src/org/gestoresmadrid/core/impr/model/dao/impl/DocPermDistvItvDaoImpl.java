package org.gestoresmadrid.core.impr.model.dao.impl;

import org.gestoresmadrid.core.docPermDistItv.model.vo.DocPermDistItvVO;
import org.gestoresmadrid.core.impr.model.dao.DocPermDistvItvDao;
import org.gestoresmadrid.core.model.dao.impl.GenericDaoImplHibernate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

@Repository
public class DocPermDistvItvDaoImpl extends GenericDaoImplHibernate<DocPermDistItvVO> implements DocPermDistvItvDao {

	private static final long serialVersionUID = -2224512707014098430L;

	private static final Logger log = LoggerFactory.getLogger(DocPermDistvItvDaoImpl.class);

}