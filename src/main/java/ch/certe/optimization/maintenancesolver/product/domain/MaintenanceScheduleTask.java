/*
 * Copyright 2017 JBoss by Red Hat.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package ch.certe.optimization.maintenancesolver.product.domain;

import ch.certe.optimization.maintenancesolver.common.domain.AbstractPersistable;
import ch.certe.optimization.maintenancesolver.product.domain.date.PlanDate;
import com.thoughtworks.xstream.annotations.XStreamConverter;
import java.time.LocalDate;
import java.util.ArrayList;
import javax.xml.bind.annotation.XmlRootElement;
import org.optaplanner.core.api.domain.solution.PlanningEntityCollectionProperty;
import org.optaplanner.core.api.domain.solution.PlanningScore;
import org.optaplanner.core.api.domain.solution.PlanningSolution;
import org.optaplanner.core.api.domain.solution.drools.ProblemFactCollectionProperty;
import org.optaplanner.core.api.domain.valuerange.ValueRangeProvider;
import org.optaplanner.core.api.score.buildin.hardsoft.HardSoftScore;
import org.optaplanner.persistence.xstream.api.score.buildin.hardsoft.HardSoftScoreXStreamConverter;

/**
 *
 * @author nalet
 */
@XmlRootElement
@PlanningSolution
public class MaintenanceScheduleTask extends AbstractPersistable {

    private ArrayList<MaintenanceObject> maintenanceObjects;
    private ArrayList<Task> tasks;
    private LocalDate startDate;
    private LocalDate endDate;
    private ArrayList<PlanDate> planDates;
    private ArrayList<Employee> employees;
    @PlanningEntityCollectionProperty
    private ArrayList<EmployeeTaskAssignment> employeeTaskAssignments;

    @XStreamConverter(HardSoftScoreXStreamConverter.class)
    private HardSoftScore score;

    public MaintenanceScheduleTask() {
    }

    public MaintenanceScheduleTask(ArrayList<MaintenanceObject> maintenanceObjects, ArrayList<Task> tasks, LocalDate startDate, LocalDate endDate, double accuracy, ArrayList<PlanDate> planDates, HardSoftScore score, long id) {
        super(id);
        this.maintenanceObjects = maintenanceObjects;
        this.tasks = tasks;
        this.startDate = startDate;
        this.endDate = endDate;
        this.planDates = planDates;
        this.score = score;
    }

    public ArrayList<PlanDate> getPlanDates() {
        return planDates;
    }

    public void setPlanDates(ArrayList<PlanDate> planDates) {
        this.planDates = planDates;
    }

    public ArrayList<MaintenanceObject> getMaintenanceObjects() {
        return maintenanceObjects;
    }

    public void setMaintenanceObjects(ArrayList<MaintenanceObject> maintenanceObjects) {
        this.maintenanceObjects = maintenanceObjects;
    }
    
    @ValueRangeProvider(id = "TaskRange")
    @ProblemFactCollectionProperty
    public ArrayList<Task> getTasks() {
        return tasks;
    }

    public void setTasks(ArrayList<Task> tasks) {
        this.tasks = tasks;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate EndDate) {
        this.endDate = EndDate;
    }

    @PlanningScore
    public HardSoftScore getScore() {
        return score;
    }

    public void setScore(HardSoftScore score) {
        this.score = score;
    }

    @ValueRangeProvider(id = "EmployeeRange")
    @ProblemFactCollectionProperty
    public ArrayList<Employee> getEmployees() {
        return employees;
    }

    public void setEmployees(ArrayList<Employee> employees) {
        this.employees = employees;
    }
    
    

//    @ProblemFactCollectionProperty
//    public List<ConflictPattern> calculateTaskConflict() {
//        List<ConflictPattern> conflictPatterns = new ArrayList<>();
//        for (MaintenanceObject maintenanceObject : this.maintenanceObjects) {
//            for(Interval interval : maintenanceObject.getIntervals()) {
//                for(Task task : interval.getTasks()) {
//                    ConflictPattern cp = ConflictPattern.createByTask(task, interval, this);
//                    if(cp != null) {
//                        conflictPatterns.add(cp);
//                    }
//                }
//            }
//        }
//        return conflictPatterns;
//    }

    public ArrayList<EmployeeTaskAssignment> getEmployeeTaskAssignments() {
        return employeeTaskAssignments;
    }

    public void setEmployeeTaskAssignments(ArrayList<EmployeeTaskAssignment> employeeTaskAssignments) {
        this.employeeTaskAssignments = employeeTaskAssignments;
    }
    
    public int getMax() {
        return (int) Math.ceil(this.getTasks().size() / this.getEmployees().size());
    }
}
