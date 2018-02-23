/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.certe.optimization.maintenancesolver.product.domain.pattern;

import ch.certe.optimization.maintenancesolver.product.domain.Interval;
import ch.certe.optimization.maintenancesolver.product.domain.MaintenanceSchedule;
import ch.certe.optimization.maintenancesolver.product.domain.Task;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author nalet
 */
@XmlRootElement
public class ConflictPattern {

    private Task leftTask;
    private Task rightTask;
    private int conflictCount;

    public ConflictPattern(Task leftTask, Task rightTash, int conflictCount) {
        this.leftTask = leftTask;
        this.rightTask = rightTash;
        this.conflictCount = conflictCount;
    }

    public Task getLeftTask() {
        return leftTask;
    }

    public void setLeftTask(Task leftTask) {
        this.leftTask = leftTask;
    }

    public Task getRightTask() {
        return rightTask;
    }

    public void setRightTask(Task rightTask) {
        this.rightTask = rightTask;
    }    

    public int getConflictCount() {
        return conflictCount;
    }

    public void setConflictCount(int conflictCount) {
        this.conflictCount = conflictCount;
    }    

    public static ConflictPattern createByTask(Task task, Interval interval, MaintenanceSchedule maintenanceSchedule) {
        if (task == null) {
            return null;
        }
        if (task.getPlanDate() == null) {
            return null;
        }

        Task minTask = null;
        for (Task t : interval.getTasks()) {
            if (minTask == null) {
                minTask = t;
                continue;
            }
            if (t.getPlanDate() != null && minTask.getPlanDate() != null && minTask.getPlanDate().getDayIndex() > t.getPlanDate().getDayIndex()) {
                minTask = t;
            }
        }
        if (minTask == null) {
            return null;
        }
        if (minTask.getPlanDate() == null) {
            return null;
        }
        
        int intMaxDay = interval.intervalToDays();
        int intMinDay = (int) (intMaxDay * interval.getAccuracy());
        int diff = minTask.getPlanDate().getDayIndex() - task.getPlanDate().getDayIndex();
        
        if (intMaxDay > diff) {
            return new ConflictPattern(task, minTask, intMaxDay - diff);
        }
        if (intMinDay > diff) {
            return new ConflictPattern(task, minTask, intMinDay - diff);
        }
        return null;
    }

}
