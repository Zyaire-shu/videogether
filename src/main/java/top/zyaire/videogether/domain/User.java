package top.zyaire.videogether.domain;

import java.io.Serializable;

import lombok.*;

/**
 * User
 * @author 
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class User implements Serializable {
    private Integer userid;

    private String username;

    private String password;

    private String auth;

    private static final long serialVersionUID = 1L;

    public User(String username, String password, String auth) {
        this.username = username;
        this.password = password;
        this.auth = auth;
    }
}