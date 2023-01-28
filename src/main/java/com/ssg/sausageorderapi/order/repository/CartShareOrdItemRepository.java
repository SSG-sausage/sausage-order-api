package com.ssg.sausageorderapi.order.repository;

import com.ssg.sausageorderapi.order.entity.CartShareOdrItem;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartShareOrdItemRepository extends JpaRepository<CartShareOdrItem, Long> {

    List<CartShareOdrItem> findAllByCartShareOrdId(Long cartShareOrdId);

}
