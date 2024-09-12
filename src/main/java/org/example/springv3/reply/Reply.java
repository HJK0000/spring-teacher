package org.example.springv3.reply;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.springv3.board.Board;
import org.example.springv3.user.User;
import org.hibernate.annotations.CreationTimestamp;


import java.sql.Timestamp;


@NoArgsConstructor
@Entity // DB에서 조회하면 자동 매핑이됨
@Getter
@Setter
@Table(name = "reply_tb") // 테이블 명 설정해주기
public class Reply {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id // PK 설정
    private Integer id;

    private String comment; // 댓글 내용

    @ManyToOne(fetch = FetchType.LAZY)
    private Board board;

    @JsonIgnoreProperties({"password"})
    @ManyToOne(fetch = FetchType.LAZY) // 이걸 안적으면 하이버네이트가 오브젝트로 인식. 이걸 적어줘야 아 fk구나 하고 이해한다.
    private User user;


    @CreationTimestamp // em.persist 할때만 발동. 네이티브 쿼리 쓰면 발동안한다. 네이티브 쿼리쓰면 now()라고 내가 넣어줘야한다.
    private Timestamp createdAt;

    @Builder
    public Reply(Integer id, String comment, Board board, User user, Timestamp createdAt) {
        this.id = id;
        this.comment = comment;
        this.board = board;
        this.user = user;
        this.createdAt = createdAt;
    }

    @Override
    public String toString() {
        return "Reply{" +
                "id=" + id +
                ", comment='" + comment + '\'' +
                ", user=" + user +
                ", createdAt=" + createdAt +
                '}';
    }
}
