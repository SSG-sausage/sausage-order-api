package com.ssg.sausageorderapi.order.repository;

import com.ssg.sausageorderapi.order.entity.CartShareOrd;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartShareOrdRepository extends JpaRepository<CartShareOrd, Long> {

    List<CartShareOrd> findAllByCartShareId(Long cartShareId);


}
