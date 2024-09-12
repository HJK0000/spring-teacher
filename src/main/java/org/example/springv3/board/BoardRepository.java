package org.example.springv3.board;

import org.example.springv3.user.User;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.repository.query.Param;


import java.util.List;
import java.util.Optional;

import java.util.Optional;

public interface BoardRepository extends JpaRepository<Board, Integer> {

    //페이징
    @Query("select b from Board b where b.title like %:title% order by b.id desc")
    Page<Board> mFindAll(@Param("title") String title, Pageable pageable); // pageable을 넣으면 page를 반환한다.

    // 이걸 쓰면 이제 mFindById가 필요없어질거다. 댓글을 무조건 상세보기로 올때 조회하니까
    @Query("select b from Board b join fetch b.user left join fetch b.replies r left join fetch r.user where b.id=:id")
    Optional<Board> mFindByIdWithReply(@Param("id") int id);

    // 이 네이티브 쿼리를 할 줄 알아야 한다.
    //@Query(value = "select * from board_tb bt inner join user_tb ut on bt.user_id = ut.id where bt.id = ?", nativeQuery = true)
    @Query("select b from Board b join fetch b.user u where b.id=:id")
    Optional<Board> mFindById(@Param("id") int id); // 조인을 하는게 낫다. 그래야 레이지 로딩 안하니까.

}

