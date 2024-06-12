package il.test.TestWithReact.data.entity.db;

import il.test.TestWithReact.data.entity.UserEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.util.Date;
import java.util.Objects;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "users")
public class User implements UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "email")
    private String email;

    @Column(name = "firstName")
    private String firstName;

    @Column(name = "lastName")
    private String lastName;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "birthDate")
    private Date birthDate;

    @Column(name = "address")
    private String address;

    @Column(name = "phoneNumber")
    private String phoneNumber;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        User user = (User) o;
        return id != null && Objects.equals(id, user.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

    public String toString() {
        return "User(id=" + this.getId() + ", email=" + this.getEmail() + ", firstName=" + this.getFirstName() + ", lastName=" + this.getLastName() + ", birthDate=" + this.getBirthDate() + ", address=" + this.getAddress() + ", phoneNumber=" + this.getPhoneNumber() + ")";
    }
}
