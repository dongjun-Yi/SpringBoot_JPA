package jpashop.jpapractice.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
public class Member extends BaseEntity {
    @Id
    @GeneratedValue
    @Column(name = "member_id")
    private Long id;

    private String loginId;
    private String name;
    @Embedded
    private Address address;
    @OneToMany(mappedBy = "member")
    @JsonIgnore
    private List<Order> orders = new ArrayList<>();
    public String password;
}
