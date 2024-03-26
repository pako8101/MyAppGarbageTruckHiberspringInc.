package hiberspring.domain.entities;

import javax.persistence.*;

@Entity
@Table(name = "products")
public class Product extends BaseEntity{
    @Column(name = "name",nullable = false,unique = true)
    private String name;
    @Column(name = "clients")
    private Integer clients;
    @ManyToOne //(cascade = {CascadeType.MERGE},fetch = FetchType.EAGER)
    private Branch branch;

    public Product() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getClients() {
        return clients;
    }

    public void setClients(Integer clients) {
        this.clients = clients;
    }

    public Branch getBranch() {
        return branch;
    }

    public void setBranch(Branch branch) {
        this.branch = branch;
    }
}
