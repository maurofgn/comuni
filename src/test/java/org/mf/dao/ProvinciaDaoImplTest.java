package org.mf.dao;

import java.util.List;

import org.dbunit.dataset.CompositeDataSet;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.Restrictions;
import org.junit.Assert;
import org.junit.Test;
import org.mf.bean.jpa.ProvinciaEntity;
import org.mf.data.repository.jpa.ProvinciaJpaRepository;
import org.mf.mock.ProvinciaEntityMock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.Order;

/**
 * JUnit test case for Provincia Jpa Repository service
 * 
 */
public class ProvinciaDaoImplTest extends EntityDaoImplTest {
	
	@Autowired
	private ProvinciaJpaRepository provinciaJpaRepository;
	
//	private MockValues mockValues = new MockValues();
	
	ProvinciaEntityMock provinciaEntityMock = new ProvinciaEntityMock();

	private static final int MAX_ROW_FOR_PAGE = 20;

	@Override
	protected String getTableName() {
		return "provincia";
	}

	/* In case you need multiple datasets (mapping different tables) and you do prefer to keep them in separate XML's */
	@Override
	protected IDataSet getDataSet() throws Exception {
		
		FlatXmlDataSetBuilder flatXmlDataSetBuilder = new FlatXmlDataSetBuilder();
		
		/**
		 * without this flag the columns are defined only from first row,
		 * therefore columns without value in the first row are not defined
		 *
		 */
		flatXmlDataSetBuilder.setColumnSensing(true);
		
		IDataSet[] datasets = new IDataSet[] {
				flatXmlDataSetBuilder.build(this.getClass().getClassLoader().getResourceAsStream("provincia.xml"))
			  };

		CompositeDataSet retValue = new CompositeDataSet(datasets);
		
		return retValue;
	}

	/**
	 * count the existing records, create a new instance, save, read, re count, delete the record created and check it not exist
	 */
	@Test
	public void findById() {

		long totalCount = provinciaJpaRepository.count();
//		System.out.println("Record/s found in provincia: " + totalCount);

		ProvinciaEntity mock = provinciaEntityMock.createNewInstance();
		mock = provinciaJpaRepository.save(mock);

		ProvinciaEntity provinciaEntity = provinciaJpaRepository.findOne(mock.getProvinciaId());

		Assert.assertNotNull(provinciaEntity);

		long c2 = provinciaJpaRepository.count();
		Assert.assertEquals("", totalCount+1, c2);

		provinciaJpaRepository.delete(mock);

		provinciaEntity = provinciaJpaRepository.findOne(mock.getProvinciaId());

		Assert.assertNull(provinciaEntity);
	}

	@Test
	public void firstPage() {
		
		long totalCount = provinciaJpaRepository.count();

		PageRequest pageable = new PageRequest(0, MAX_ROW_FOR_PAGE,
				new Sort(
						new Order(Direction.ASC, "nome"),
						new Order(Direction.DESC, "sigla")
						)
		);

		Page<ProvinciaEntity> page = provinciaJpaRepository.findAll(pageable);
		Assert.assertTrue(page.getNumberOfElements() <= totalCount && page.getNumberOfElements() <= MAX_ROW_FOR_PAGE);	//elements in page are less or equal of the total

//		int nrRows = 0;
//		for (ProvinciaEntity provinciaEntity : page) {
//			System.out.println(provinciaEntity);
//			nrRows++;
//		}
//		Assert.assertEquals(nrRows, page.getNumberOfElements());
	}

	@Test
	public void select() {
		
		Criteria criteria = getSession().createCriteria(ProvinciaEntity.class, "e")
				.setMaxResults(MAX_ROW_FOR_PAGE)
				.add(Restrictions.ilike("e.nome", addPercentage("n")))
				.addOrder(org.hibernate.criterion.Order.asc("nome"))
				;
		
		@SuppressWarnings("unchecked")
		List<ProvinciaEntity> critList = criteria.list();
		
		Assert.assertTrue(critList.size() >= 0 && critList.size() <= MAX_ROW_FOR_PAGE);

//		critList.forEach(c -> System.out.println(c));


		Query query = getSession().getNamedQuery("ProvinciaEntity.countAll");
		long totRec = (Long)query.uniqueResult();
		Assert.assertTrue(totRec >= 0);

		
		Query q = getSession().createQuery("Select x from ProvinciaEntity x order by x.nome");
		q.setMaxResults(MAX_ROW_FOR_PAGE);

		@SuppressWarnings("unchecked")
		List<ProvinciaEntity> l = q.list();
		
		Assert.assertTrue(l.size() >= 0 && l.size() <= MAX_ROW_FOR_PAGE);
//		l.forEach(c -> System.out.println(c));
		
	}

}
