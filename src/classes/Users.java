package classes;

import javax.persistence.*;
import java.util.*;

@Entity
@Table (name = "Users")
public class Users {
    @Id
    @Column (name="userID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer userID;

    @Column (name = "nickname")
    private String nickname;

    @Column (name = "email")
    private String email;

    @Column (name = "userPassword")
    private String userPassword;

    @Column (name = "photoUrl")
    private String photoUrl;

    @OneToMany(mappedBy = "user")
    private Set<Route> routes = new HashSet<>(0);

    public Users(String nickname, String email, String userPass){
        this.nickname = nickname;
        this.email = email;
        this.userPassword = userPass;
    }

    public Users() {
    }

    public Integer getUserID() {
        return userID;
    }

    public void setUserID(Integer id) {
        this.userID = id;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getuserPassword() {
        return userPassword;
    }

    public void setuserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }

    public Set<Route> getRoutes() {
        return routes;
    }

    public void setRoutes(Set<Route> routes) {
        this.routes = routes;
    }
}
