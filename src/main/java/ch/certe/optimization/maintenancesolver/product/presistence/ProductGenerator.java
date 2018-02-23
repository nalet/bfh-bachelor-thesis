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
package ch.certe.optimization.maintenancesolver.product.presistence;

import ch.certe.optimization.maintenancesolver.common.app.LoggingMain;
import ch.certe.optimization.maintenancesolver.product.domain.Employee;
import ch.certe.optimization.maintenancesolver.product.domain.EmployeeTaskAssignment;
import ch.certe.optimization.maintenancesolver.product.domain.Interval;
import ch.certe.optimization.maintenancesolver.product.domain.MaintenanceObject;
import ch.certe.optimization.maintenancesolver.product.domain.MaintenanceObjectType;
import ch.certe.optimization.maintenancesolver.product.domain.MaintenanceSchedule;
import ch.certe.optimization.maintenancesolver.product.domain.MaintenanceScheduleTask;
import ch.certe.optimization.maintenancesolver.product.domain.Requirement;
import ch.certe.optimization.maintenancesolver.product.domain.Task;
import ch.certe.optimization.maintenancesolver.product.domain.TaskType;
import ch.certe.optimization.maintenancesolver.product.domain.date.PlanDate;
import java.time.LocalDate;
import java.time.Month;
import java.time.Period;
import static java.time.temporal.ChronoUnit.DAYS;
import java.util.ArrayList;
import java.util.Calendar;

/**
 *
 * @author nalet
 */
public class ProductGenerator extends LoggingMain {

    //private final SolutionDao solutionDao;
    //private final File outputDir;
    private UniqueIdProvider uniqueIdProvider;

    private MaintenanceObjectType genericMaintenanceObjectType;
    private Requirement genericRequirement;
    private Interval genericInterval;
    private TaskType genericTaskType;

    public ProductGenerator() {
        //this.solutionDao = new ProductDao();
        //this.outputDir = new File(this.solutionDao.getDataDir(), "unsolved");
        this.uniqueIdProvider = new UniqueIdProvider();
        this.initSampleGenerics();
    }

    private void initSampleGenerics() {
        this.genericMaintenanceObjectType = new MaintenanceObjectType("genericType", 0L);
        this.genericRequirement = new Requirement("genericRequirement", 0L);
        ArrayList<Requirement> requirements = new ArrayList<>();
        requirements.add(this.genericRequirement);
        this.genericInterval = new Interval(Period.ofMonths(3), requirements, null, null, 0.8, 0L);
        this.genericTaskType = new TaskType("genericMaintenanceTask", 0L);
    }

    public MaintenanceScheduleTask createTaskProduct(int maintenanceObjectsCount, int years) {
        MaintenanceSchedule maintenanceSchedule = this.createProduct(maintenanceObjectsCount, years);
        MaintenanceScheduleTask maintenanceScheduleTask = new MaintenanceScheduleTask();
        maintenanceScheduleTask.setPlanDates(maintenanceSchedule.getPlanDates());
        maintenanceScheduleTask.setMaintenanceObjects(maintenanceSchedule.getMaintenanceObjects());
        maintenanceScheduleTask.setTasks(maintenanceSchedule.getTasks());
        maintenanceScheduleTask.setId(this.uniqueIdProvider.getId());
        maintenanceScheduleTask.setEmployees(maintenanceSchedule.getEmployees());
        maintenanceScheduleTask.setEmployeeTaskAssignments(new ArrayList<>());
        this.createTaskEmployeeAssignments(maintenanceScheduleTask);
        return maintenanceScheduleTask;
    }

    public MaintenanceSchedule createProduct(int maintenanceObjectsCount, int years) {
        MaintenanceSchedule maintenanceSchedule = new MaintenanceSchedule();
        maintenanceSchedule.setPlanDates(new ArrayList<>());
        maintenanceSchedule.setMaintenanceObjects(new ArrayList<>());
        maintenanceSchedule.setTasks(new ArrayList<>());
        maintenanceSchedule.setId(this.uniqueIdProvider.getId());
        maintenanceSchedule.setEmployees(new ArrayList<>());
        this.setDates(maintenanceSchedule, years);
        this.generatePlanDays(maintenanceSchedule);
        this.createSampleMaintenanceObjects(maintenanceSchedule, maintenanceObjectsCount);
        this.createSampleTasks(maintenanceSchedule);
        this.createSampleEmployee(maintenanceSchedule);
        this.logger.info("MaintenanceSchedule has {} Objects and {} Task, with a Period from {} to {}",
                maintenanceSchedule.getMaintenanceObjects().size(),
                maintenanceSchedule.getTasks().size(),
                maintenanceSchedule.getStartDate(),
                maintenanceSchedule.getEndDate());
        return maintenanceSchedule;
    }

    private void createSampleMaintenanceObjects(MaintenanceSchedule maintenanceSchedule, int maintenanceObjectsCount) {
        for (int i = 0; i < maintenanceObjectsCount; i++) {
            MaintenanceObject maintenanceObject = generateMaintenanceObject(i);
            maintenanceSchedule.getMaintenanceObjects().add(maintenanceObject);
        }
    }

    private void setDates(MaintenanceSchedule maintenanceSchedule, int years) {
        int year = Calendar.getInstance().get(Calendar.YEAR);

        LocalDate startDate = LocalDate.of(year + 1, Month.JANUARY, 1);
        LocalDate endDate = LocalDate.of(year + years + 1, Month.JANUARY, 1);
        maintenanceSchedule.setStartDate(startDate);
        maintenanceSchedule.setEndDate(endDate);
    }

    private MaintenanceObject generateMaintenanceObject(int id) {
        return new MaintenanceObject("MA" + String.format("%010d", id), this.genericMaintenanceObjectType, this.generateInterval(), (long) id);
    }

    private ArrayList<Interval> generateInterval() {
        ArrayList<Interval> intervals = new ArrayList<>();
        intervals.add(new Interval(this.genericInterval.getPeriod(), this.genericInterval.getRequirements(), this.genericInterval.getLastTask(), new ArrayList<>(), this.genericInterval.getAccuracy(), 0));
        return intervals;
    }

    private void createSampleTasks(MaintenanceSchedule maintenanceSchedule) {
        Period planningPeriod = Period.between(maintenanceSchedule.getStartDate(), maintenanceSchedule.getEndDate());
        double periodDays = this.periodToDays(planningPeriod);

        for (MaintenanceObject maintenanceObject : maintenanceSchedule.getMaintenanceObjects()) {
            for (Interval interval : maintenanceObject.getIntervals()) {
                double taskAmount = periodDays / this.periodToDays(interval.getPeriod());
                for (int i = 0; i < (int) taskAmount; i++) {
                    Task t = new Task(null, maintenanceObject, this.genericTaskType, interval, null, this.uniqueIdProvider.getId());
                    t.setPlanDate(maintenanceSchedule.getPlanDates().get(i * (interval.intervalToDays() - 1)));
                    maintenanceSchedule.getTasks().add(t);
                    interval.getTasks().add(t);
                }
            }
        }
    }

    private double periodToDays(Period period) {
        return (period.toTotalMonths() * Interval.MONTH_IN_DAYS);
    }

    private void generatePlanDays(MaintenanceSchedule maintenanceSchedule) {
        int days = Math.toIntExact(DAYS.between(maintenanceSchedule.getStartDate(), maintenanceSchedule.getEndDate()));
        LocalDate currentDate = maintenanceSchedule.getStartDate();

        for (int i = 0; i < days; i++) {
            maintenanceSchedule.getPlanDates().add(new PlanDate(currentDate, i, this.uniqueIdProvider.getId()));
            currentDate = currentDate.plusDays(1);
        }
    }

    private void createSampleEmployee(MaintenanceSchedule maintenanceSchedule) {
        for (int i = 0; i < 10; i++) {
            maintenanceSchedule.getEmployees().add(new Employee("Mitarbeiter" + i, this.genericInterval.getRequirements(), this.uniqueIdProvider.getId()));
        }

    }

    private void createTaskEmployeeAssignments(MaintenanceScheduleTask maintenanceScheduleTask) {
        for(Task t : maintenanceScheduleTask.getTasks()) {
            maintenanceScheduleTask.getEmployeeTaskAssignments().add(new EmployeeTaskAssignment(t, null,this.uniqueIdProvider.getId()));
        }
        for(Employee e : maintenanceScheduleTask.getEmployees()) {
            e.setMaintenanceScheduleTask(maintenanceScheduleTask);
        }
    }

}
