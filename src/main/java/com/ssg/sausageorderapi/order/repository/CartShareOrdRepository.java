package com.ssg.sausageorderapi.order.repository;

import com.ssg.sausageorderapi.order.entity.CartShareOdr;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartShareOrdRepository extends JpaRepository<CartShareOdr, Long> {

    List<CartShareOdr> findAllByCartShareId(Long cartShareId);


}
