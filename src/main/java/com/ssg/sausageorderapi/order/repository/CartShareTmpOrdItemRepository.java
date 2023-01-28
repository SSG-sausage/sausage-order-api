package com.ssg.sausageorderapi.order.repository;

import com.ssg.sausageorderapi.order.entity.CartShareTmpOdrItem;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartShareTmpOrdItemRepository extends JpaRepository<CartShareTmpOdrItem, Long> {

    List<CartShareTmpOdrItem> findAllByCartShareTmpOrdId(Long cartShareTmpOrdId);

}
