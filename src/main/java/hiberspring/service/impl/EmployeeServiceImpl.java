package hiberspring.service.impl;

import hiberspring.domain.dtos.EmployeeSeedRootDto;
import hiberspring.domain.entities.Branch;
import hiberspring.domain.entities.Employee;
import hiberspring.domain.entities.EmployeeCard;
import hiberspring.repository.EmployeeRepository;
import hiberspring.service.BranchService;
import hiberspring.service.EmployeeCardService;
import hiberspring.service.EmployeeService;
import hiberspring.util.ValidationUtil;
import hiberspring.util.XmlParser;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.xml.bind.JAXBException;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.stream.Collectors;

import static hiberspring.common.GlobalConstants.*;

@Service
public class EmployeeServiceImpl implements EmployeeService {
    private final EmployeeRepository employeeRepository;
    private final ModelMapper modelMapper;
    private final ValidationUtil validationUtil;

    private final BranchService branchService;
    private final EmployeeCardService employeeCardService;
    private final XmlParser xmlParser;
@Autowired
    public EmployeeServiceImpl(EmployeeRepository employeeRepository, ModelMapper modelMapper, ValidationUtil validationUtil, BranchService branchService, EmployeeCardService employeeCardService, XmlParser xmlParser) {
        this.employeeRepository = employeeRepository;
        this.modelMapper = modelMapper;
        this.validationUtil = validationUtil;
    this.branchService = branchService;
    this.employeeCardService = employeeCardService;
    this.xmlParser = xmlParser;

    }

    @Override
    public Boolean employeesAreImported() {
        return this.employeeRepository.count()>0;
    }

    @Override
    public String readEmployeesXmlFile() throws IOException {
        return Files.readString(Path.of(PRODUCTS_FILE_PATH));
    }

    @Override
    public String importEmployees() throws JAXBException, FileNotFoundException {
    StringBuilder result = new StringBuilder();

        EmployeeSeedRootDto employeeSeedRootDto = this.
                xmlParser.parseXml(EmployeeSeedRootDto.class,PRODUCTS_FILE_PATH);

        employeeSeedRootDto.getEmployees()
                .forEach(employeeSeedDto -> {
                    if (this.validationUtil.isValid(employeeSeedDto)){
                        if (this.employeeRepository
                                .findByFirstNameAndLastNameAndPosition(employeeSeedDto.getFirstName(),
                                        employeeSeedDto.getLastname(),
                                        employeeSeedDto.getPosition())==null){
                            Employee employee = this.modelMapper.map(employeeSeedDto,Employee.class);

                            Branch branch = this.branchService.getBranchByName(employeeSeedDto.getBranch());
                            EmployeeCard employeeCard = this.employeeCardService
                                    .getCardByNumber(employeeSeedDto.getCard());
                            employee.setBranch(branch);
                            employee.setCard(employeeCard);
                            this.employeeRepository.saveAndFlush(employee);
                            result.append(String.format(
                                    "Successfully imported Employee %s %s.",employee.getFirstName(),employee.getLastName()));

                        }else {
                            result.append(IN_DB_MESSAGE);
                        }
                    }else {
                       result.append(INCORRECT_DATA_MESSAGE);
                    }
                    result.append(System.lineSeparator());
                });
        return result.toString();
    }

    @Override
    public String exportProductiveEmployees() {


        return this.employeeRepository
                .findAllByBranchWithMoreThenOneProduct()
                .stream()
                .map(employee -> String.format(
                        "%nName: " + employee.getFirstName()
                                + employee.getLastName()
                                + System.lineSeparator()+
                "Position: " + employee.getPosition() +
                                System.lineSeparator()+
                "Card Number: " + employee.getCard().getNumber() + System.lineSeparator()

                )).collect(Collectors.joining(".........................."));
    }
}
