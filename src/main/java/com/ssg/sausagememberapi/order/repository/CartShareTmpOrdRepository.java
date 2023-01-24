package com.ssg.sausagememberapi.order.repository;

import com.ssg.sausagememberapi.order.entity.CartShareTmpOdr;
import com.ssg.sausagememberapi.order.entity.TmpOrdStatCd;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartShareTmpOrdRepository extends JpaRepository<CartShareTmpOdr, Long> {

    Optional<CartShareTmpOdr> findFirstByCartShareIdAndTmpOrdStatCd(Long cartShareId, TmpOrdStatCd tmpOrdStatCd);

}
