package hiberspring.repository;

import hiberspring.domain.entities.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    Employee findByFirstNameAndLastName(String firstName, String lastName);

    @Query("select e from  Employee  e where e.branch.products.size > 0 " +
    "order by concat(e.firstName, ' ', e.lastName),length(e.Position)desc ")
    List<Employee> findAllByBranchWithMoreThenOneProduct();

}
