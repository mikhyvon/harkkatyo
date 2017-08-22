package fi.solita.harkka.mikkohyv.domain.model;

import fi.solita.harkka.mikkohyv.domain.shared.BaseEntity;

import javax.persistence.Entity;

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

    public String name() {
        return this.name;
    }

    public boolean updatePassword(String oldPassword, String newPassword) {
        if (this.password.equals(oldPassword)){
            this.password = newPassword;
            return true;
        }
        return false;
    }

    public boolean checkUsernameAndPassword(String username, String password){
        if (this.name.equals(username) && this.password.equals(password)){
            return true;
        }else{
            return false;
        }
    }

}
