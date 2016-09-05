package org.mf.data.repository.jpa;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.mf.bean.jpa.ProvinciaEntity;

/**
 * Repository : Provincia.
 */
public interface ProvinciaJpaRepository extends PagingAndSortingRepository<ProvinciaEntity, Integer> {

	/**
	 * @param pageable
	 * @return pagina di tutte le città
	 */
	@Query(value="select c from ProvinciaEntity c", countQuery="select count(c) from ProvinciaEntity c")
	Page<ProvinciaEntity> listPage(Pageable pageable);
	

}
