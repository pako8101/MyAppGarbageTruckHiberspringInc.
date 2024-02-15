package hiberspring.domain.entities;

import javax.persistence.*;

@Entity
@Table(name = "employees")
public class Employee extends BaseEntity {
    @Column(name = "first_name", nullable = false, unique = true)
    private String firstName;
    @Column(name = "last_name", nullable = false, unique = true)
    private String lastName;
    @Column(name = "position", nullable = false)
    private String Position;
    @OneToOne(cascade = {CascadeType.MERGE})
    private EmployeeCard card;
    @ManyToOne(cascade = {CascadeType.MERGE})
    private Branch branch;

    public Employee() {
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPosition() {
        return Position;
    }

    public void setPosition(String position) {
        Position = position;
    }

    public EmployeeCard getCard() {
        return card;
    }

    public void setCard(EmployeeCard card) {
        this.card = card;
    }

    public Branch getBranch() {
        return branch;
    }

    public void setBranch(Branch branch) {
        this.branch = branch;
    }
}
