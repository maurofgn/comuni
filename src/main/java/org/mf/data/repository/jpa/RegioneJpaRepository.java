package org.mf.data.repository.jpa;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.mf.bean.jpa.RegioneEntity;

/**
 * Repository : Regione.
 */
public interface RegioneJpaRepository extends PagingAndSortingRepository<RegioneEntity, Integer> {

	/**
	 * @param pageable
	 * @return pagina di tutte le città
	 */
	@Query(value="select c from RegioneEntity c", countQuery="select count(c) from RegioneEntity c")
	Page<RegioneEntity> listPage(Pageable pageable);
	

}
