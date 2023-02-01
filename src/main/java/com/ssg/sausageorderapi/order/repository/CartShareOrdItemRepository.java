package com.ssg.sausageorderapi.order.repository;

import com.ssg.sausageorderapi.order.entity.CartShareOrdItem;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartShareOrdItemRepository extends JpaRepository<CartShareOrdItem, Long> {

    List<CartShareOrdItem> findAllByCartShareOrdId(Long cartShareOrdId);

}
