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
import ch.certe.optimization.maintenancesolver.product.presistence.UniqueIdProvider;
import java.time.LocalDate;
import java.time.Month;
import java.time.Period;
import java.time.Year;
import static java.time.temporal.ChronoUnit.DAYS;
import java.util.ArrayList;

/**
 *
 * @author meinenn_adm
 */
public class TestDataProvider {

    MaintenanceObjectType genericMaintenanceObjectType;
    Requirement genericRequirement;
    Interval genericInterval;
    TaskType genericTaskType;
    UniqueIdProvider uniqueIdProvider;
    LocalDate startDate = LocalDate.of(Year.now().getValue(), Month.JANUARY, 1);
    LocalDate endDate = LocalDate.of(Year.now().getValue(), Month.DECEMBER, 31);
    ArrayList<PlanDate> planDates = new ArrayList();

    public TestDataProvider() {
        this.initSampleGenerics();
    }

    private void initSampleGenerics() {
        this.uniqueIdProvider = new UniqueIdProvider();
        this.genericMaintenanceObjectType = new MaintenanceObjectType("genericType", 0L);
        this.genericRequirement = new Requirement("genericRequirement", 0L);
        ArrayList<Requirement> requirements = new ArrayList<>();
        requirements.add(this.genericRequirement);
       // this.genericInterval = new Interval(Period.ofMonths(3), requirements, null, 0L);
        this.genericTaskType = new TaskType("genericMaintenanceTask", 0L);
        this.generatePlanDays();
    }

    private void generatePlanDays() {
        int days = Math.toIntExact(DAYS.between(this.startDate, this.endDate));
        LocalDate currentDate = this.startDate;

        for (int i = 0; i < days; i++) {
            this.planDates.add(i, new PlanDate(currentDate, i, this.uniqueIdProvider.getId()));
            currentDate = currentDate.plusDays(1);
        }
    }

    public MaintenanceObjectType getGenericMaintenanceObjectType() {
        return genericMaintenanceObjectType;
    }

    public void setGenericMaintenanceObjectType(MaintenanceObjectType genericMaintenanceObjectType) {
        this.genericMaintenanceObjectType = genericMaintenanceObjectType;
    }

    public Requirement getGenericRequirement() {
        return genericRequirement;
    }

    public void setGenericRequirement(Requirement genericRequirement) {
        this.genericRequirement = genericRequirement;
    }

    public Interval getGenericInterval() {
        return genericInterval;
    }

    public void setGenericInterval(Interval genericInterval) {
        this.genericInterval = genericInterval;
    }

    public TaskType getGenericTaskType() {
        return genericTaskType;
    }

    public void setGenericTaskType(TaskType genericTaskType) {
        this.genericTaskType = genericTaskType;
    }

    public UniqueIdProvider getUniqueIdProvider() {
        return uniqueIdProvider;
    }

    public void setUniqueIdProvider(UniqueIdProvider uniqueIdProvider) {
        this.uniqueIdProvider = uniqueIdProvider;
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

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public ArrayList<PlanDate> getPlanDates() {
        return planDates;
    }

    public void setPlanDates(ArrayList<PlanDate> planDates) {
        this.planDates = planDates;
    }

}
