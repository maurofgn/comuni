package org.mf.dao;

import java.util.Arrays;
import java.util.List;

import org.dbunit.dataset.CompositeDataSet;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.Restrictions;
import org.junit.Assert;
import org.junit.Test;
import org.mf.bean.Comune;
import org.mf.bean.jpa.ComuneEntity;
import org.mf.business.service.mapping.ComuneServiceMapper;
import org.mf.data.repository.jpa.ComuneJpaRepository;
import org.mf.mock.ComuneEntityMock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.Order;

/**
 * JUnit test case for Comune Jpa Repository service
 * 
 */
public class ComuneDaoImplTest extends EntityDaoImplTest {
	
	@Autowired
	private ComuneJpaRepository comuneJpaRepository;
	
	@Autowired
	private ComuneServiceMapper comuneServiceMapper;

	
//	private MockValues mockValues = new MockValues();
	
	ComuneEntityMock comuneEntityMock = new ComuneEntityMock();

	private static final int MAX_ROW_FOR_PAGE = 20;

	@Override
	protected String getTableName() {
		return "comune";
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
				flatXmlDataSetBuilder.build(this.getClass().getClassLoader().getResourceAsStream("comune.xml"))
			  };

		CompositeDataSet retValue = new CompositeDataSet(datasets);
		
		return retValue;
	}

	/**
	 * count the existing records, create a new instance, save, read, re count, delete the record created and check it not exist
	 */
	@Test
	public void findById() {

		long totalCount = comuneJpaRepository.count();
//		System.out.println("Record/s found in comune: " + totalCount);

		ComuneEntity mock = comuneEntityMock.createNewInstance();
		mock = comuneJpaRepository.save(mock);

		ComuneEntity comuneEntity = comuneJpaRepository.findOne(mock.getComuneId());

		Assert.assertNotNull(comuneEntity);

		long c2 = comuneJpaRepository.count();
		Assert.assertEquals("", totalCount+1, c2);

		comuneJpaRepository.delete(mock);

		comuneEntity = comuneJpaRepository.findOne(mock.getComuneId());

		Assert.assertNull(comuneEntity);
	}

	@Test
	public void firstPage() {
		
		long totalCount = comuneJpaRepository.count();

		PageRequest pageable = new PageRequest(
				1,	//0 based
				MAX_ROW_FOR_PAGE,
				new Sort(
						new Order(Direction.ASC, "nome"),
						new Order(Direction.DESC, "codicecatastale")
						)
		);

		Page<ComuneEntity> page = comuneJpaRepository.findAll(pageable);
		Assert.assertTrue(page.getNumberOfElements() <= totalCount && page.getNumberOfElements() <= MAX_ROW_FOR_PAGE);	//elements in page are less or equal of the total

//		int nrRows = 0;
//		for (ComuneEntity comuneEntity : page) {
//			System.out.println(comuneEntity);
//			nrRows++;
//		}
//		Assert.assertEquals(nrRows, page.getNumberOfElements());
	}
	
	@Test
	public void filteredPage() {
		
		PageRequest pageable = new PageRequest(0, MAX_ROW_FOR_PAGE,
				new Sort(
						new Order(Direction.ASC, "nome"),
						new Order(Direction.DESC, "codicecatastale")
						)
		);
		
		List<Integer> province = Arrays.asList(62, 63);
//		@SuppressWarnings("serial")
//		List<Integer> province = new ArrayList<Integer>() {{add(62); add(63);}};
		
		String nomeCitta = "%Ci%";
		Page<ComuneEntity> page = comuneJpaRepository.listPage(pageable, province, nomeCitta);
		
		System.out.println(page.toString() + " rows:\n" + page.getContent());
		
//		Page<ComuneEntity> listPage(Pageable pageable
//				, @Param("province") List<Integer> province
//				, @Param("nome") String nomeCitta
//				);
		
		Query query = getSession().getNamedQuery("ComuneEntity.countAll");
		long totRec = (Long)query.uniqueResult();
		Assert.assertTrue(totRec >= 0);
	}
	
	@Test
	public void testJpa() {
		Integer regioneId = 11;
		Integer provinciaId = null;	//62;
		PageRequest pageable = new PageRequest(
				1,	//0 based
				MAX_ROW_FOR_PAGE,
				new Sort(
						new Order(Direction.ASC, "nome"),
						new Order(Direction.DESC, "abitanti")
						)
		);

		Page<ComuneEntity> pageEntity = comuneJpaRepository.retrieveByProvRegion(pageable, regioneId, provinciaId, "%i%");
		
		pageEntity.forEach(x -> System.out.println(x.toString()));
		Assert.assertTrue(pageEntity.getTotalElements() > 0);
		
		Page<Comune> page = pageEntity.map(new Converter<ComuneEntity, Comune>() {
			@Override
			public Comune convert(ComuneEntity comuneEntity) {
				return comuneServiceMapper.mapComuneEntityToComune(comuneEntity);
			}
		});

		page.forEach(x -> System.out.println(x.toString()));
		Assert.assertTrue(page.getTotalElements() > 0);
	}

	@Test
	public void select() {
		
		Criteria criteria = getSession().createCriteria(ComuneEntity.class, "e")
				.setMaxResults(MAX_ROW_FOR_PAGE)
				.add(Restrictions.ilike("e.nome", addPercentage("n")))
				.addOrder(org.hibernate.criterion.Order.asc("nome"))
				;
		
		@SuppressWarnings("unchecked")
		List<ComuneEntity> critList = criteria.list();
		
		Assert.assertTrue(critList.size() >= 0 && critList.size() <= MAX_ROW_FOR_PAGE);

//		critList.forEach(c -> System.out.println(c));


		Query query = getSession().getNamedQuery("ComuneEntity.countAll");
		long totRec = (Long)query.uniqueResult();
		Assert.assertTrue(totRec >= 0);

		
		Query q = getSession().createQuery("Select x from ComuneEntity x order by x.nome");
		q.setMaxResults(MAX_ROW_FOR_PAGE);

		@SuppressWarnings("unchecked")
		List<ComuneEntity> l = q.list();
		
		Assert.assertTrue(l.size() >= 0 && l.size() <= MAX_ROW_FOR_PAGE);
//		l.forEach(c -> System.out.println(c));
		
	}

}
