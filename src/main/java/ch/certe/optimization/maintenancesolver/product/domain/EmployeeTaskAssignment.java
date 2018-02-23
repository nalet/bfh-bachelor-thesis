/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.certe.optimization.maintenancesolver.product.domain;

import ch.certe.optimization.maintenancesolver.common.domain.AbstractPersistable;
import javax.xml.bind.annotation.XmlRootElement;
import org.optaplanner.core.api.domain.entity.PlanningEntity;
import org.optaplanner.core.api.domain.variable.PlanningVariable;

/**
 *
 * @author nalet
 */
@XmlRootElement
@PlanningEntity
public class EmployeeTaskAssignment extends AbstractPersistable {
    
    private Task task;
    @PlanningVariable(valueRangeProviderRefs = {"EmployeeRange"})
    private Employee employee;

    public EmployeeTaskAssignment() {
        
    }
    
    public EmployeeTaskAssignment(Task task, Employee employee) {
        this.task = task;
        this.employee = employee;
    }

    public EmployeeTaskAssignment(Task task, Employee employee, long id) {
        super(id);
        this.task = task;
        this.employee = employee;
    }

    public Task getTask() {
        return task;
    }

    public void setTask(Task task) {
        this.task = task;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }
}
