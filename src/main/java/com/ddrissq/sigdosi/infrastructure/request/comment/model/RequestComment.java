package com.ddrissq.sigdosi.infrastructure.request.comment.model;

import com.ddrissq.sigdosi.common.persistence.model.BaseEntity;
import com.ddrissq.sigdosi.iam.user.model.User;
import com.ddrissq.sigdosi.infrastructure.request.model.Request;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class RequestComment extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_account_id")
    private User user;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "request_id")
    private Request request;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "request_comment_id")
    private RequestComment parent;
    private String content;
    @Builder.Default
    private Boolean deleted = Boolean.FALSE;

}
