package org.example.springv3.reply;

import lombok.Data;

public class ReplyResponse {

    @Data
    public static class DTO {
        private Integer id;
        private String comment;
        private String username;

        public DTO(Reply reply) {
            this.id = reply.getId(); // 깊은 복사
            this.comment = reply.getComment(); // board랑 user가 없어도 레이지 로딩이 일어나서 조회함
            this.username = reply.getUser().getUsername();
        }
    }
}
