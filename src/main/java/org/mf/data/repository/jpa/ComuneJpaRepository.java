package org.mf.data.repository.jpa;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.mf.bean.jpa.ComuneEntity;
import org.mf.enu.Nose;

/**
 * Repository : Comune.
 */
public interface ComuneJpaRepository extends PagingAndSortingRepository<ComuneEntity, Integer> {
	
//	List<Person> findByEmailAddressAndLastname(EmailAddress emailAddress, String lastname);
//
//	  // Enables the distinct flag for the query
//	  List<Person> findDistinctPeopleByLastnameOrFirstname(String lastname, String firstname);
//	  List<Person> findPeopleDistinctByLastnameOrFirstname(String lastname, String firstname);
//
//	  // Enabling ignoring case for an individual property
//	  List<Person> findByLastnameIgnoreCase(String lastname);
//	  // Enabling ignoring case for all suitable properties
//	  List<Person> findByLastnameAndFirstnameAllIgnoreCase(String lastname, String firstname);
//
//	  // Enabling static ORDER BY for a query
//	  List<Person> findByLastnameOrderByFirstnameAsc(String lastname);
//	  List<Person> findByLastnameOrderByFirstnameDesc(String lastname);

	List<ComuneEntity> findByNomeIgnoreCaseOrderByNomeAsc(String nome);
	
	List<ComuneEntity> findByNomeIgnoreCaseLike(String lastname, Sort sort);

	/**
	 * @param pageable
	 * @return pagina di tutte le città
	 */
	@Query(value="select c from ComuneEntity c", countQuery="select count(c) from ComuneEntity c")
	Page<ComuneEntity> listPage(Pageable pageable);
	
	/**
	 * @param pageable
	 * @param province
	 * @param nomeCitta
	 * @return pagina di città delle province passate e che soddifano il like sul nome
	 */
	@Query(value="SELECT c FROM ComuneEntity c "
			+ "WHERE c.provincia.provinciaId IN (:province) "
			+ "and LOWER(c.nome) like LOWER(:nome) "
			+ "order by c.abitanti desc, c.nome asc"
			, countQuery="select count(c) from ComuneEntity c WHERE c.provincia.provinciaId IN (:province) and LOWER(c.nome) like LOWER(:nome)"
			)
	Page<ComuneEntity> listPage(Pageable pageable
			, @Param("province") List<Integer> province
			, @Param("nome") String nomeCitta
			);
	
	
//	Nel caso di un collection type, non è possibile usare IS NULL ma JPQL fornisce gli operatori IS EMPTY o IS NOT EMPTY. 
	
	@Query(value="SELECT c FROM ComuneEntity as c "
			+ "WHERE (LOWER(c.nome) like LOWER(:nome) or :nome is null) "
			+ "and (c.provincia.provinciaId = (:provincia) or (:provincia) is null) "
			+ "and (c.provincia.regione.regioneId = (:regione) or (:regione) is Null) "
			+ "order by c.nome"
			,
			countQuery="select count(c) from ComuneEntity c " 
					+ "WHERE (LOWER(c.nome) like LOWER(:nome) or :nome is null) "
					+ "and (c.provincia.provinciaId = (:provincia) or (:provincia) is null) "
					+ "and (c.provincia.regione.regioneId = (:regione) or (:regione) is Null) "
			)
	Page<ComuneEntity> retrieveByProvRegion(Pageable pageable, @Param("regione") Integer regione, @Param("provincia") Integer provincia, @Param("nome") String nomeCitta);
	

	/**
	 * 
	 * @param nome nome provincia
	 * @return elenco città appartenenti alle province che soddisfano il like nome passato 
	 */
	@Query("SELECT c FROM ComuneEntity as c join c.provincia as p WHERE LOWER(p.nome) like LOWER(:nome)")
	List<ComuneEntity> retrieveByProvName(@Param("nome") String nome);
	
	/**
	 * 
	 * @param province elenco di provinciaId
	 * @return città di province passate
	 */
	/**
	 * 
	 * @param province
	 * @param nomeCitta
	 * @return città delle province passate. Il nome delle città devono soddisfare il like del parametro nomeCitta (key unsensitive)
	 */
	@Query("SELECT c FROM ComuneEntity as c "
			+ "WHERE c.provincia.provinciaId IN (:province) and LOWER(c.nome) like LOWER(:nome) "
			+ "order by c.nome")
	List<ComuneEntity> retrieveByProvList(@Param("province") List<Integer> regioni, @Param("nome") String nomeCitta);
	
	/**
	 * 
	 * @param nomeCitta
	 * @return città che contengono il parametro nel nome
	 */
	@Query("SELECT c FROM ComuneEntity as c "
			+ "WHERE LOWER(c.nome) like LOWER(:nome) "
			+ "order by c.nome")
	List<ComuneEntity> retrieveByName(@Param("nome") String nomeCitta);

	/**
	 * 
	 * @param regioni
	 * @param nomeCitta
	 * @return lista di coppia di oggetti (citta e regione) delle province passate. Il nome delle città devono soddisfare il like del parametro nomeCitta (key unsensitive)
	 */
	@Query("SELECT c.nome, c.abitanti, r.nome FROM ComuneEntity as c "
			+ "join c.provincia as p "
			+ "join p.regione as r "
			+ "WHERE c.provincia.regione.regioneId IN (:regioni) and LOWER(c.nome) like LOWER(:nome) "
			+ "order by c.abitanti desc, c.nome")
	List<Object[]> retrieveByRegion(@Param("regioni") List<Integer> regioni, @Param("nome") String nomeCitta);
	
	@Query("SELECT c FROM ComuneEntity as c "
			+ "WHERE c.provincia.regione.nose = :none and LOWER(c.nome) like LOWER(:nome) "
			+ "order by c.nome")
	List<ComuneEntity> retrieveByNose(@Param("none") Nose nose, @Param("nome") String nomeCitta);
	
}
