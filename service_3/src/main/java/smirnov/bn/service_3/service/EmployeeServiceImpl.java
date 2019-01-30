package smirnov.bn.service_3.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import smirnov.bn.service_3.entity.Employee;
import smirnov.bn.service_3.model.EmployeeInfo;
import smirnov.bn.service_3.repository.EmployeeRepository;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Nonnull
    private EmployeeInfo buildlingEmployeeInfo(Employee employee) {
        EmployeeInfo info = new EmployeeInfo();
        info.setEmployeeId(employee.getEmployeeId());
        info.setEmployeeName(employee.getEmployeeName());
        info.setEmployeeEmail(employee.getEmployeeEmail());
        info.setEmployeeLogin(employee.getEmployeeLogin());
        info.setEmployeeUuid(employee.getEmployeeUuid());
        return info;
    }

    /*
    @Override
    @Transactional
    public void createEmployee(@Nonnull String employeeName, @Nonnull String employeeEmail, @Nonnull String employeeLogin) {
        Employee employee = new Employee();
        employee.setEmployeeName(employeeName);
        employee.setEmployeeEmail(employeeEmail);
        employee.setEmployeeLogin(employeeLogin);
        employee.setEmployeeUuid(UUID.randomUUID());
        employeeRepository.saveAndFlush(employee);
    }
    //*/

    @Override
    @Transactional
    public UUID createEmployee(@Nonnull EmployeeInfo employeeInfo) {
        Employee employee = new Employee();
        employee.setEmployeeName(employeeInfo.getEmployeeName());
        employee.setEmployeeEmail(employeeInfo.getEmployeeEmail());
        employee.setEmployeeLogin(employeeInfo.getEmployeeLogin());
        employee.setEmployeeUuid(UUID.randomUUID());
        employeeRepository.saveAndFlush(employee);
        return employee.getEmployeeUuid();
    }

    /*
    @Override
    @Transactional
    public void updateEmployee(@Nonnull String employeeName, @Nonnull String employeeEmail, @Nonnull String employeeLogin, @Nonnull UUID employeeUuid) {
        employeeRepository.updateEmployee(employeeName, employeeEmail, employeeLogin, employeeUuid);
    }
    //*/

    @Nullable
    @Override
    @Transactional(readOnly = true)
    public List<EmployeeInfo> findAllEmployees() {
        return employeeRepository.findAll()
                .stream()
                .map(this::buildlingEmployeeInfo)
                .collect(Collectors.toList());
    }

    @Nullable
    @Override
    @Transactional(readOnly = true)
    public List<EmployeeInfo> findAllEmployeesPaginated(int page, int sizeLimit) {
        List<EmployeeInfo> employeeInfoList = new ArrayList<>();
        Pageable pageableRequest = PageRequest.of(page, sizeLimit);
        Page<Employee> employeesPage = employeeRepository.findAll(pageableRequest);
        List<Employee> employees = employeesPage.getContent();
        for (Employee employee : employees) {
            employeeInfoList.add(this.buildlingEmployeeInfo(employee));
        }
        return employeeInfoList;
    }

    /*
    @Nullable
    @Override
    @Transactional(readOnly = true)
    public EmployeeInfo findEmployeeById(@Nonnull Integer employeeId) {
        return employeeRepository.findById(employeeId).map(this::buildlingEmployeeInfo).orElse(null);
    }
    //*/

    @Nullable
    @Override
    @Transactional(readOnly = true)
    public EmployeeInfo findEmployeeByUuid(@Nonnull UUID employeeUuid) {
        return this.buildlingEmployeeInfo(employeeRepository.findByUuid(employeeUuid));
    }

    @Override
    @Transactional
    public void updateEmployee(@Nonnull EmployeeInfo employeeInfo) {
        employeeRepository.updateEmployee(employeeInfo.getEmployeeName(), employeeInfo.getEmployeeEmail(), employeeInfo.getEmployeeLogin(), employeeInfo.getEmployeeUuid());
    }

    /*
    @Override
    @Transactional
    public void deleteEmployeeById(@Nonnull Integer employeeId) {
        employeeRepository.deleteById(employeeId);
    }
    //*/

    @Override
    @Transactional
    public void deleteEmployeeByUuid(@Nonnull UUID employeeUuid) {
        employeeRepository.deleteByUuid(employeeUuid);
    }
}
