package saxon;

import java.io.Serializable;
// MEMBER CLASS
public class Member implements Serializable {
    private static final long serialVersionUID = 1L;
    private String name;
    private String email;
    private int age;
    private final int id;
    
    
    public Member(String name, String email, int age, int id) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.age = age;
   
    }
    
    public int getId() {
        return id;
    }
    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public int getAge() {
        return age;
    }
    
    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setAge(int age) {
        this.age = age;
    }
   
}
