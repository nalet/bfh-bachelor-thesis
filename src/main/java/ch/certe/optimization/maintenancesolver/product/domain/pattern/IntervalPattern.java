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
package ch.certe.optimization.maintenancesolver.product.domain.pattern;

import ch.certe.optimization.maintenancesolver.product.domain.Interval;
import ch.certe.optimization.maintenancesolver.product.domain.MaintenanceObject;
import ch.certe.optimization.maintenancesolver.product.domain.Task;

/**
 *
 * @author meinenn_adm
 */
public class IntervalPattern {
    private Task task1;
    private Task task2;
    private MaintenanceObject maintenanceObject;
    private Interval interval;

    public Task getTask1() {
        return task1;
    }

    public void setTask1(Task task1) {
        this.task1 = task1;
    }

    public Task getTask2() {
        return task2;
    }

    public void setTask2(Task task2) {
        this.task2 = task2;
    }

    public MaintenanceObject getMaintenanceObject() {
        return maintenanceObject;
    }

    public void setMaintenanceObject(MaintenanceObject maintenanceObject) {
        this.maintenanceObject = maintenanceObject;
    }

    public Interval getInterval() {
        return interval;
    }

    public void setInterval(Interval interval) {
        this.interval = interval;
    }
    
}
