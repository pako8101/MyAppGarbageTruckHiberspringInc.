package hiberspring.domain.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "employee_cards")
public class EmployeeCard  extends BaseEntity{
    @Column(name = "number",unique = true,nullable = false)
    private String number;

    public EmployeeCard() {
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }
}
