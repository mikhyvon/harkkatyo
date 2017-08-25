package fi.solita.harkka.mikkohyv.domain.model;

import fi.solita.harkka.mikkohyv.domain.shared.BaseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.persistence.*;

@Entity
public class User extends BaseEntity<UserId>{
    private String name;
    private String role;
    private String password;

    protected User(){ }

    public User(UserId id, String username, String userRole, String password){
        super(id);
        this.name = username;
        this.role = userRole;
        this.password = password;
    }

    public UserId id(){ return this.getId(); }
    public String name() {
        return this.name;
    }
    public String role() { return this.role; }

    public boolean updatePassword(String oldPassword, String newPassword) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        if(passwordEncoder.matches(oldPassword, this.password)){
            String hashedPassword = passwordEncoder.encode(newPassword);
            this.password = hashedPassword;
            return true;
        }
        return false;
    }

    public static User.UserBuilder builder(){
        return new User.UserBuilder();
    }


    public static class UserBuilder {
        private String name;
        private String role;
        private String password;

        public UserBuilder setName(String name) {
            this.name = name;
            return this;
        }

        public UserBuilder setRole(String role) {
            this.role = role;
            return this;
        }

        public UserBuilder setPassword(String password) {
            BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
            String hashedPassword = passwordEncoder.encode(password);
            this.password = hashedPassword;
            return this;
        }

        public User build() {
            return new User(new UserId(), this.name, this.role, this.password);
        }
    }
}
