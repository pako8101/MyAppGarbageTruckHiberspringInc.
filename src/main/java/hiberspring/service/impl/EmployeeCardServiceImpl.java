package hiberspring.service.impl;

import com.google.gson.Gson;
import hiberspring.domain.dtos.BranchSeedDto;
import hiberspring.domain.dtos.EmployeeCardsDto;
import hiberspring.domain.entities.Branch;
import hiberspring.domain.entities.EmployeeCard;
import hiberspring.domain.entities.Town;
import hiberspring.repository.EmployeeCardRepository;
import hiberspring.service.EmployeeCardService;
import hiberspring.util.ValidationUtil;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;

import static hiberspring.common.GlobalConstants.*;
import static hiberspring.common.GlobalConstants.INCORRECT_DATA_MESSAGE;

@Service
public class EmployeeCardServiceImpl implements EmployeeCardService {
    private final EmployeeCardRepository employeeCardRepository;
    private final ModelMapper modelMapper;
    private final ValidationUtil validationUtil;
    private final Gson gson;

    @Autowired

    public EmployeeCardServiceImpl(EmployeeCardRepository employeeCardRepository, ModelMapper modelMapper, ValidationUtil validationUtil, Gson gson) {
        this.employeeCardRepository = employeeCardRepository;
        this.modelMapper = modelMapper;
        this.validationUtil = validationUtil;
        this.gson = gson;
    }

    @Override
    public Boolean employeeCardsAreImported() {
        return this.employeeCardRepository.count()>0;
    }

    @Override
    public String readEmployeeCardsJsonFile() throws IOException {
        return Files.readString(Path.of(EMPLOYEE_CARDS_FILE_PATH));
    }

    @Override
    public String importEmployeeCards(String employeeCardsFileContent) throws FileNotFoundException {

        StringBuilder result = new StringBuilder();

        EmployeeCardsDto[] dtos = this.gson
                .fromJson(new FileReader(EMPLOYEE_CARDS_FILE_PATH), EmployeeCardsDto[].class);

        Arrays.stream(dtos)
                .forEach(employeeCardsDto -> {
                    if (this.validationUtil.isValid(employeeCardsDto)) {
                        if (this.employeeCardRepository.findByNumber(employeeCardsDto.getNumber()) == null) {
                            EmployeeCard employeeCard = this.modelMapper.map(employeeCardsDto, EmployeeCard.class);


                            result.append("Successfully imported Employee Card ")
                                    .append(employeeCard.getNumber())
                                    .append(".");
                            this.employeeCardRepository.saveAndFlush(employeeCard);
                        } else {
                            result.append(IN_DB_MESSAGE);
                        }
                    } else {
                        result.append(INCORRECT_DATA_MESSAGE);
                    }
                    result.append(System.lineSeparator());
                });

        return result.toString();
    }

    @Override
    public EmployeeCard getCardByNumber(String number) {
        return this.employeeCardRepository.findByNumber(number);
    }
}
