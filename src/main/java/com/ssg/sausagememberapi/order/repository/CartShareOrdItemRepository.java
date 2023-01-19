package com.ssg.sausagememberapi.order.repository;

import com.ssg.sausagememberapi.order.entity.CartShareOdrItem;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartShareOrdItemRepository extends JpaRepository<CartShareOdrItem, Long> {

    List<CartShareOdrItem> findAllByCartShareOrdId(Long cartShareOrdId);

}
