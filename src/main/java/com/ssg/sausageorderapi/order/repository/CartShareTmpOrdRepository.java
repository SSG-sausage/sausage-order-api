package com.ssg.sausageorderapi.order.repository;

import com.ssg.sausageorderapi.order.entity.CartShareTmpOrd;
import com.ssg.sausageorderapi.order.entity.TmpOrdStatCd;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartShareTmpOrdRepository extends JpaRepository<CartShareTmpOrd, Long> {

    Optional<CartShareTmpOrd> findFirstByCartShareIdAndTmpOrdStatCd(Long cartShareId, TmpOrdStatCd tmpOrdStatCd);

    Optional<CartShareTmpOrd> findFirstByCartShareIdAndTmpOrdStatCdOrderByCartShareTmpOrdRcpDts(Long cartShareId,
            TmpOrdStatCd tmpOrdStatCd);


}
