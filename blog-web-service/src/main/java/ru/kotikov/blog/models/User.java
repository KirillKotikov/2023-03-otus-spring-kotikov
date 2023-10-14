package ru.kotikov.blog.models;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@Document(collection = "users")
public class User {

    @Transient
    public static final String SEQUENCE_NAME = "user_sequence";

    @Id
    private String id;

    private String nickName;

    private String login;

    private char[] password;

    private List<GrantedAuthority> authorities;

    public User(String nickName, String login,
                char[] password, List<GrantedAuthority> authorities) {
        this.nickName = nickName;
        this.login = login;
        this.password = password;
        this.authorities = authorities;
    }

    public void setAuthorities(List<String> authorities) {
        this.authorities = authorities.stream()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
    }

    public String getPassword() {
        return new String(password);
    }

    public void setPassword(String password) {
        this.password = password.toCharArray();
    }

}
