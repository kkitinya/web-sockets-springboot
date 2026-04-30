package com.curro.chat.user;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Setter
@Getter
@Document
public class User {
    @Id
    private String nickName;
    private String fullName;
    private Status status;
}
