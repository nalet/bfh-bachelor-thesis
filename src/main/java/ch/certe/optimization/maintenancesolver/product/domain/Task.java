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
import ch.certe.optimization.maintenancesolver.product.domain.solution.PlanDateStrengthWeightFactory;
import ch.certe.optimization.maintenancesolver.product.domain.solution.TaskDifficultyWeightFactory;
import javax.xml.bind.annotation.XmlRootElement;
import org.optaplanner.core.api.domain.entity.PlanningEntity;
import org.optaplanner.core.api.domain.variable.PlanningVariable;

/**
 *
 * @author nalet
 */

@PlanningEntity(difficultyWeightFactoryClass = TaskDifficultyWeightFactory.class)
@XmlRootElement
public class Task extends AbstractPersistable {

    public final static int LAST_OR_FIRST = -1;

    private PlanDate planDate;
    private MaintenanceObject maintenanceObject;
    private TaskType taskType;
    private Interval interval;
    private Employee employee;
    
    public Task() {
    
    }

    public Task(PlanDate planDate, MaintenanceObject maintenanceObject, TaskType taskType, Interval interval, Employee employee, long id) {
        super(id);
        this.planDate = planDate;
        this.maintenanceObject = maintenanceObject;
        this.taskType = taskType;
        this.interval = interval;
        this.employee = employee;
    }

    @PlanningVariable(valueRangeProviderRefs = {"PlanDateRange"} /*, strengthWeightFactoryClass = PlanDateStrengthWeightFactory.class*/)
    public PlanDate getPlanDate() {
        return planDate;
    }

    public void setPlanDate(PlanDate planDate) {
        this.planDate = planDate;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public MaintenanceObject getMaintenanceObject() {
        return maintenanceObject;
    }

    public void setMaintenanceObject(MaintenanceObject maintenanceObject) {
        this.maintenanceObject = maintenanceObject;
    }

    public TaskType getTaskType() {
        return taskType;
    }

    public void setTaskType(TaskType taskType) {
        this.taskType = taskType;
    }

    public Interval getInterval() {
        return interval;
    }

    public void setInterval(Interval interval) {
        this.interval = interval;
    }

    @Override
    public String toString() {
        return "Task{id: "+this.id+", planDate: "+this.planDate+"}";
    }
    
//    public boolean taskIsConsecutive(Task task) {        
//        if(this.equals(task)) {
//            return false;
//        }
//        
//        if(this.planDate == null || task.planDate == null) {
//            return false;
//        }
//        
//        if(this.planDate.getDayIndex() == task.planDate.getDayIndex() ) {
//            return false;
//        }
//        
//        int difference = task.planDate.getDayIndex() - this.planDate.getDayIndex();
//        
//        if(difference < 0) {
//            return false;
//        }
//        
//        for (Task t : this.maintenanceObject.getTasks()) {
//            if(t.interval.equals(this.interval) && t.planDate != null && !t.equals(this) && !t.equals(task)) {
//                int d = t.planDate.getDayIndex() - this.planDate.getDayIndex();
//                if(d > 0 && d < difference) {
//                    return false;
//                }
//            }
//        }
//        
//        return true;
//    }
    
    public int getDist(Task t) {
        int dist = this.getPlanDate().getDayIndex() - t.getPlanDate().getDayIndex();
        return Math.abs(dist);
    }
    
    public int getMinDist() {
        return (int)(this.interval.getAccuracy() * this.interval.intervalToDays());
    }
    
    public int getMaxDist() {
        return (int) this.interval.intervalToDays();
    }
    
    public boolean isNearest(Task t) {
        Task n = this.getNearestTask();
        if(t.getDist(n) < this.getDist(t)) {
            return false;
        }
        return true;
    }
    
    public Task getNearestTask() {
        int select = 0;
        if(this.getInterval().getTasks().get(select).equals(this)) {
            select++;
        }
        Task nearestTask = this.getInterval().getTasks().get(select);
        int nearest = this.getDist(nearestTask);
        for(Task t : this.getInterval().getTasks()) {
            if(t.equals(this)) {
                continue;
            }
            int dist = this.getDist(t);
            if(dist < nearest) {
                nearest = dist;
                nearestTask = t;
            }
        }
        return nearestTask;
    }
    
    public boolean isNext(Task task) {
        int dayIndexThis = this.getPlanDate().getDayIndex();
        int dayIndexNext = task.getPlanDate().getDayIndex();
        if(dayIndexThis > dayIndexNext || dayIndexThis == dayIndexNext) {
            return false;
        }
        for(Task t : this.getInterval().getTasks()) {
            if(t.equals(this) || t.equals(task)) {
                continue;
            }
            if(t.getPlanDate().getDayIndex() == dayIndexNext || t.getPlanDate().getDayIndex() == dayIndexThis) {
                return false;
            }
            if(t.getPlanDate().getDayIndex() < dayIndexThis && t.getPlanDate().getDayIndex() > dayIndexNext) {
                return false;
            }
        }
        return true;
    }
    
}
