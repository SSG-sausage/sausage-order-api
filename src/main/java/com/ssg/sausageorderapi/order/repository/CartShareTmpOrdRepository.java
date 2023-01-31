package com.ssg.sausageorderapi.order.repository;

import com.ssg.sausageorderapi.order.entity.CartShareTmpOdr;
import com.ssg.sausageorderapi.order.entity.TmpOrdStatCd;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartShareTmpOrdRepository extends JpaRepository<CartShareTmpOdr, Long> {

    Optional<CartShareTmpOdr> findFirstByCartShareIdAndTmpOrdStatCd(Long cartShareId, TmpOrdStatCd tmpOrdStatCd);

    Optional<CartShareTmpOdr> findFirstByCartShareIdAndTmpOrdStatCdOrderByCartShareTmpOrdRcpDts(Long cartShareId,
            TmpOrdStatCd tmpOrdStatCd);


}
