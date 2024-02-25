package com.sky.service;

import com.sky.dto.*;
import com.sky.entity.Employee;
import com.sky.result.PageResult;

public interface EmployeeService {

    /**
     * 员工登录
     * @param employeeLoginDTO
     * @return
     */
    Employee login(EmployeeLoginDTO employeeLoginDTO);

    /**
     * 新增员工
     * @param employeeDTO
     * @return
     */
    void save(EmployeeDTO employeeDTO);
    PageResult pageQuery(EmployeePageQueryDTO employeePageQueryDTO);

    /**
     * 新增员工
     * @param status
     * @param id
     */
    void startOrStop(Integer status, Long id);

    /**
     * GetByID
     * @param id
     */
    Employee selectById(Long id);

    /**
     * Update employee
     * @param employee
     */
    void update(EmployeeDTO empDTO);

}
