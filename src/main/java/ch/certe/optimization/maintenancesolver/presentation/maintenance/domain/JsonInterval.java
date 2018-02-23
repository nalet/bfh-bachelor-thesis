/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.certe.optimization.maintenancesolver.presentation.maintenance.domain;

import ch.certe.optimization.maintenancesolver.product.domain.Interval;
import ch.certe.optimization.maintenancesolver.product.domain.Requirement;
import java.time.Period;
import java.util.ArrayList;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author nalet
 */
@XmlRootElement
public class JsonInterval {

    protected Period period;
    protected ArrayList<Requirement> requirements;
    protected ArrayList<JsonTask> tasks;

    public JsonInterval(Interval interval) {
        this.period = interval.getPeriod();
        this.requirements = interval.getRequirements();
        this.tasks = JsonTask.createFromList(interval.getTasks());
    }

    public static ArrayList<JsonInterval> createFromList(ArrayList<Interval> intervals) {
        ArrayList<JsonInterval> jsonIntervals = new ArrayList<>();
        for (Interval interval : intervals) {
            jsonIntervals.add(new JsonInterval(interval));
        }
        return jsonIntervals;
    }

    public Period getPeriod() {
        return period;
    }

    public void setPeriod(Period period) {
        this.period = period;
    }

    public ArrayList<Requirement> getRequirements() {
        return requirements;
    }

    public void setRequirements(ArrayList<Requirement> requirements) {
        this.requirements = requirements;
    }

    public ArrayList<JsonTask> getTasks() {
        return tasks;
    }

    public void setTasks(ArrayList<JsonTask> tasks) {
        this.tasks = tasks;
    }
}
