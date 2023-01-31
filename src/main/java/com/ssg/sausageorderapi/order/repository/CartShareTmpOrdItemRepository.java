package com.ssg.sausageorderapi.order.repository;

import com.ssg.sausageorderapi.order.entity.CartShareTmpOrdItem;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartShareTmpOrdItemRepository extends JpaRepository<CartShareTmpOrdItem, Long> {

    List<CartShareTmpOrdItem> findAllByCartShareTmpOrdId(Long cartShareTmpOrdId);

}
