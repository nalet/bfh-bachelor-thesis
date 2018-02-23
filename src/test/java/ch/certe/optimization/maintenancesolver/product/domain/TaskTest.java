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

import ch.certe.optimization.maintenancesolver.product.domain.date.PlanDate;
import java.util.ArrayList;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author meinenn_adm
 */
public class TaskTest {
    
    TestDataProvider testDataProvider;
    
    MaintenanceObject maintenanceObject;
    ArrayList<Task> tasks = new ArrayList();
    ArrayList<Interval> intervals = new ArrayList();
    
    public TaskTest() {
        this.testDataProvider = new TestDataProvider();
      //  this.maintenanceObject = new MaintenanceObject("TEST00001", this.testDataProvider.getGenericMaintenanceObjectType(), intervals, tasks, 0L);
        
        intervals.add(0, this.testDataProvider.getGenericInterval());
        /*
        tasks.add(0, new Task(this.testDataProvider.getPlanDates().get(0), maintenanceObject, this.testDataProvider.getGenericTaskType(), this.testDataProvider.getGenericInterval(), 0));
        tasks.add(1, new Task(this.testDataProvider.getPlanDates().get(40), maintenanceObject, this.testDataProvider.getGenericTaskType(), this.testDataProvider.getGenericInterval(), 0));
        tasks.add(2, new Task(this.testDataProvider.getPlanDates().get(80), maintenanceObject, this.testDataProvider.getGenericTaskType(), this.testDataProvider.getGenericInterval(), 0));
        tasks.add(3, new Task(this.testDataProvider.getPlanDates().get(120), maintenanceObject, this.testDataProvider.getGenericTaskType(), this.testDataProvider.getGenericInterval(), 0));
        tasks.add(4, new Task(this.testDataProvider.getPlanDates().get(120), maintenanceObject, this.testDataProvider.getGenericTaskType(), this.testDataProvider.getGenericInterval(), 0));
        tasks.add(5, new Task(this.testDataProvider.getPlanDates().get(160), maintenanceObject, this.testDataProvider.getGenericTaskType(), this.testDataProvider.getGenericInterval(), 0));
        tasks.add(6, new Task(this.testDataProvider.getPlanDates().get(200), maintenanceObject, this.testDataProvider.getGenericTaskType(), this.testDataProvider.getGenericInterval(), 0));
        tasks.add(7, new Task(this.testDataProvider.getPlanDates().get(240), maintenanceObject, this.testDataProvider.getGenericTaskType(), this.testDataProvider.getGenericInterval(), 0));
        tasks.add(8, new Task(this.testDataProvider.getPlanDates().get(280), maintenanceObject, this.testDataProvider.getGenericTaskType(), this.testDataProvider.getGenericInterval(), 0));
        tasks.add(9, new Task(this.testDataProvider.getPlanDates().get(320), maintenanceObject, this.testDataProvider.getGenericTaskType(), this.testDataProvider.getGenericInterval(), 0));
    */}

    /**
     * Test of taskIsConsecutive method, of class Task.
     */
    @Test
    public void testTaskIsConsecutive() {
        System.out.println("taskIsConsecutive");
/*
        assertEquals(this.tasks.get(0).taskIsConsecutive(this.tasks.get(1)), true);
        assertEquals(this.tasks.get(1).taskIsConsecutive(this.tasks.get(2)), true);
        assertEquals(this.tasks.get(2).taskIsConsecutive(this.tasks.get(3)), true);
        assertEquals(this.tasks.get(3).taskIsConsecutive(this.tasks.get(4)), false);
        assertEquals(this.tasks.get(4).taskIsConsecutive(this.tasks.get(5)), true);
        assertEquals(this.tasks.get(5).taskIsConsecutive(this.tasks.get(6)), true);
        assertEquals(this.tasks.get(6).taskIsConsecutive(this.tasks.get(7)), true);
        assertEquals(this.tasks.get(7).taskIsConsecutive(this.tasks.get(8)), true);
        assertEquals(this.tasks.get(8).taskIsConsecutive(this.tasks.get(9)), true);
        
        assertEquals(this.tasks.get(0).taskIsConsecutive(this.tasks.get(2)), false);
        assertEquals(this.tasks.get(1).taskIsConsecutive(this.tasks.get(3)), false);
        assertEquals(this.tasks.get(2).taskIsConsecutive(this.tasks.get(4)), true);
        assertEquals(this.tasks.get(3).taskIsConsecutive(this.tasks.get(5)), true);
        assertEquals(this.tasks.get(4).taskIsConsecutive(this.tasks.get(6)), false);
        assertEquals(this.tasks.get(5).taskIsConsecutive(this.tasks.get(7)), false);
        assertEquals(this.tasks.get(6).taskIsConsecutive(this.tasks.get(8)), false);
        assertEquals(this.tasks.get(7).taskIsConsecutive(this.tasks.get(9)), false);
        assertEquals(this.tasks.get(8).taskIsConsecutive(this.tasks.get(0)), false);
        assertEquals(this.tasks.get(9).taskIsConsecutive(this.tasks.get(1)), false);
*/
    }
    
}
