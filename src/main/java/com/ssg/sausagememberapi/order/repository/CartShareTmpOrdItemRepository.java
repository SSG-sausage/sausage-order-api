package com.ssg.sausagememberapi.order.repository;

import com.ssg.sausagememberapi.order.entity.CartShareTmpOdrItem;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartShareTmpOrdItemRepository extends JpaRepository<CartShareTmpOdrItem, Long> {

    List<CartShareTmpOdrItem> findAllByCartShareTmpOrdId(Long cartShareTmpOrdId);

}
