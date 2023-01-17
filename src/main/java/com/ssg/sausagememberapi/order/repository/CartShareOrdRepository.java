package com.ssg.sausagememberapi.order.repository;

import com.ssg.sausagememberapi.order.entity.CartShareOdr;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartShareOrdRepository extends JpaRepository<CartShareOdr, Long> {

    List<CartShareOdr> findAllByCartShareId(Long cartShareId);

}
