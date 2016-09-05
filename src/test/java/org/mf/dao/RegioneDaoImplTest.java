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
import org.mf.bean.jpa.RegioneEntity;
import org.mf.data.repository.jpa.RegioneJpaRepository;
import org.mf.mock.RegioneEntityMock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.Order;

/**
 * JUnit test case for Regione Jpa Repository service
 * 
 */
public class RegioneDaoImplTest extends EntityDaoImplTest {
	
	@Autowired
	private RegioneJpaRepository regioneJpaRepository;
	
//	private MockValues mockValues = new MockValues();
	
	RegioneEntityMock regioneEntityMock = new RegioneEntityMock();

	private static final int MAX_ROW_FOR_PAGE = 20;

	@Override
	protected String getTableName() {
		return "regione";
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
				flatXmlDataSetBuilder.build(this.getClass().getClassLoader().getResourceAsStream("regione.xml"))
			  };

		CompositeDataSet retValue = new CompositeDataSet(datasets);
		
		return retValue;
	}

	/**
	 * count the existing records, create a new instance, save, read, re count, delete the record created and check it not exist
	 */
	@Test
	public void findById() {

		long totalCount = regioneJpaRepository.count();
//		System.out.println("Record/s found in regione: " + totalCount);

		RegioneEntity mock = regioneEntityMock.createNewInstance();
		mock = regioneJpaRepository.save(mock);

		RegioneEntity regioneEntity = regioneJpaRepository.findOne(mock.getRegioneId());

		Assert.assertNotNull(regioneEntity);

		long c2 = regioneJpaRepository.count();
		Assert.assertEquals("", totalCount+1, c2);

		regioneJpaRepository.delete(mock);

		regioneEntity = regioneJpaRepository.findOne(mock.getRegioneId());

		Assert.assertNull(regioneEntity);
	}

	@Test
	public void firstPage() {
		
		long totalCount = regioneJpaRepository.count();

		PageRequest pageable = new PageRequest(0, MAX_ROW_FOR_PAGE,
				new Sort(
						new Order(Direction.ASC, "nome"),
						new Order(Direction.DESC, "nose")
						)
		);

		Page<RegioneEntity> page = regioneJpaRepository.findAll(pageable);
		Assert.assertTrue(page.getNumberOfElements() <= totalCount && page.getNumberOfElements() <= MAX_ROW_FOR_PAGE);	//elements in page are less or equal of the total

//		int nrRows = 0;
//		for (RegioneEntity regioneEntity : page) {
//			System.out.println(regioneEntity);
//			nrRows++;
//		}
//		Assert.assertEquals(nrRows, page.getNumberOfElements());
	}

	@Test
	public void select() {
		
		Criteria criteria = getSession().createCriteria(RegioneEntity.class, "e")
				.setMaxResults(MAX_ROW_FOR_PAGE)
				.add(Restrictions.ilike("e.nome", addPercentage("n")))
				.addOrder(org.hibernate.criterion.Order.asc("nome"))
				;
		
		@SuppressWarnings("unchecked")
		List<RegioneEntity> critList = criteria.list();
		
		Assert.assertTrue(critList.size() >= 0 && critList.size() <= MAX_ROW_FOR_PAGE);

//		critList.forEach(c -> System.out.println(c));


		Query query = getSession().getNamedQuery("RegioneEntity.countAll");
		long totRec = (Long)query.uniqueResult();
		Assert.assertTrue(totRec >= 0);

		
		Query q = getSession().createQuery("Select x from RegioneEntity x order by x.nome");
		q.setMaxResults(MAX_ROW_FOR_PAGE);

		@SuppressWarnings("unchecked")
		List<RegioneEntity> l = q.list();
		
		Assert.assertTrue(l.size() >= 0 && l.size() <= MAX_ROW_FOR_PAGE);
//		l.forEach(c -> System.out.println(c));
		
	}

}
