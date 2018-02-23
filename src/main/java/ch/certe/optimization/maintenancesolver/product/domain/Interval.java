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
import java.time.Period;
import java.util.ArrayList;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author nalet
 */
@XmlRootElement
public class Interval extends AbstractPersistable {

    public static final double MONTH_IN_DAYS = 30.4167;

    private Period period;
    private ArrayList<Requirement> requirements;
    private Task lastTask;
    private ArrayList<Task> tasks;
    private double accuracy;

    public Interval(Period period, ArrayList<Requirement> requirements, Task lastTask, ArrayList<Task> tasks, double accuracy, long id) {
        super(id);
        this.period = period;
        this.requirements = requirements;
        this.lastTask = lastTask;
        this.tasks = tasks;
        this.accuracy = accuracy;
    }
    
        public double getAccuracy() {
        return accuracy;
    }

    public void setAccuracy(double accuracy) {
        this.accuracy = accuracy;
    }

    public Task getLastTask() {
        return lastTask;
    }

    public void setLastTask(Task lastTask) {
        this.lastTask = lastTask;
    }

    public Period getPeriod() {
        return period;
    }

    public void setPeriod(Period duration) {
        this.period = duration;
    }

    public ArrayList<Requirement> getRequirements() {
        return requirements;
    }

    public void setRequirements(ArrayList<Requirement> requirements) {
        this.requirements = requirements;
    }

    public int intervalToDays() {
        int res = (int) (this.period.getYears() * 365 + this.period.getMonths() * MONTH_IN_DAYS + this.period.getDays());
        return res;
    }

    @Override
    public String toString() {
        return "Interval{Period: " + this.period + ", intervalToDays():" + this.intervalToDays() + "}";
    }

    public ArrayList<Task> getTasks() {
        return tasks;
    }

    public void setTasks(ArrayList<Task> tasks) {
        this.tasks = tasks;
    }

}
