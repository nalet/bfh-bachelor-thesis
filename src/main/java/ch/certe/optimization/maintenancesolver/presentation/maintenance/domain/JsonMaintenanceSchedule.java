/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.certe.optimization.maintenancesolver.presentation.maintenance.domain;

import ch.certe.optimization.maintenancesolver.product.domain.MaintenanceObject;
import ch.certe.optimization.maintenancesolver.product.domain.MaintenanceSchedule;
import ch.certe.optimization.maintenancesolver.product.domain.Task;
import java.time.LocalDate;
import java.util.ArrayList;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author nalet
 */
@XmlRootElement
public class JsonMaintenanceSchedule {

    protected ArrayList<JsonMaintenanceObject> maintenanceObjects;
    protected LocalDate startDate;
    protected LocalDate endDate;
    
    public JsonMaintenanceSchedule() {
        
    }
    
    public JsonMaintenanceSchedule(MaintenanceSchedule maintenanceSchedule) {
        this.startDate = maintenanceSchedule.getStartDate();
        this.endDate = maintenanceSchedule.getEndDate();
        this.maintenanceObjects = JsonMaintenanceObject.createFromList(maintenanceSchedule.getMaintenanceObjects());
    }

    public ArrayList<JsonMaintenanceObject> getMaintenanceObjects() {
        return maintenanceObjects;
    }

    public void setMaintenanceObjects(ArrayList<JsonMaintenanceObject> maintenanceObjects) {
        this.maintenanceObjects = maintenanceObjects;
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

}
