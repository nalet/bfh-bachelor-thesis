/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.certe.optimization.maintenancesolver.presentation.maintenance.domain;

import ch.certe.optimization.maintenancesolver.product.domain.Task;
import ch.certe.optimization.maintenancesolver.product.domain.TaskType;
import ch.certe.optimization.maintenancesolver.product.domain.date.PlanDate;
import java.time.LocalDate;
import java.util.ArrayList;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author nalet
 */
@XmlRootElement
public class JsonTask {

    protected long id;
    protected LocalDate planDate;
    protected TaskType taskType;

    public JsonTask(Task task) {
        this.id = task.getId();
        this.planDate = LocalDate.from(task.getPlanDate().getLocalDate());
        this.taskType = task.getTaskType();
    }

    public static ArrayList<JsonTask> createFromList(ArrayList<Task> tasks) {
        ArrayList<JsonTask> jsonTasks = new ArrayList<>();
        for (Task task : tasks) {
            jsonTasks.add(new JsonTask(task));
        }
        return jsonTasks;
    }

    public LocalDate getPlanDate() {
        return planDate;
    }

    public void setPlanDate(LocalDate planDate) {
        this.planDate = planDate;
    }

    public TaskType getTaskType() {
        return taskType;
    }

    public void setTaskType(TaskType taskType) {
        this.taskType = taskType;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

}
