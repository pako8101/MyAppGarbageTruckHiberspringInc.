package hiberspring.domain.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Table(name = "towns")
@Entity
public class Town extends BaseEntity{
    @Column(name = "name",unique = true,nullable = false)
    private String name;
    @Column(name = "population",nullable = false)
    private Integer population;

    public Town() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getPopulation() {
        return population;
    }

    public void setPopulation(Integer population) {
        this.population = population;
    }
}
