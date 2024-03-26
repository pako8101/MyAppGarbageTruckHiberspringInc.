package hiberspring.domain.entities;



import javax.persistence.*;
import java.util.Set;


@Entity
@Table(name = "branches")
public class Branch extends BaseEntity{

    @Column(name = "name",nullable = false,unique = true)
    private String name;

    @ManyToOne (cascade = {CascadeType.MERGE})
    private Town town;

    @OneToMany (mappedBy = "branch",fetch = FetchType.EAGER)
    private Set<Product> products;


    public Branch() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Town getTown() {
        return town;
    }

    public void setTown(Town town) {
        this.town = town;
    }

    public Set<Product> getProducts() {
        return products;
    }

    public void setProducts(Set<Product> products) {
        this.products = products;
    }
}
