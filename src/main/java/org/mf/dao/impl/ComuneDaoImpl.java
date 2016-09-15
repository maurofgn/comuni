package org.mf.dao.impl;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.mf.bean.jpa.ComuneEntity;
import org.mf.dao.AbstractDao;
import org.mf.dao.ComuneDao;
import org.springframework.stereotype.Repository;

@Repository("comuneDao")
public class ComuneDaoImpl extends AbstractDao<Integer, ComuneEntity> implements ComuneDao {

	@Override
	public List<ComuneEntity> retrieveByProvRegion(Integer start, Integer length, Integer regione, Integer provincia, String nomeCitta) {
		
		Criteria criteria = createEntityCriteria().addOrder(Order.asc("nome"));
		
		if (regione != null)
			criteria.add(Restrictions.eq("provincia.regione.regioneId", regione));
		if (provincia != null)
			criteria.add(Restrictions.eq("provincia.provinciaId", provincia));
		
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);	//To avoid duplicates.
		@SuppressWarnings("unchecked")
		List<ComuneEntity> audits = (List<ComuneEntity>) criteria.list();
		return audits;
	}

	
}
