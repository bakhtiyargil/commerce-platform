package az.baxtiyargil.commerce.product.adapter.out.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.Set;

public interface ProductJpaRepository extends JpaRepository<ProductJpaEntity, Long> {

    @Query(value = "select p.PRODUCT_ID from co.PRODUCTS p\n" +
            "where p.PRODUCT_ID in (:ids)", nativeQuery = true)
    Set<Long> findExistingIds(@Param("ids") Set<Long> ids);
}
