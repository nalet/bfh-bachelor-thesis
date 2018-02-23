/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.certe.optimization.maintenancesolver.presentation.maintenance.domain;

import ch.certe.optimization.maintenancesolver.product.domain.MaintenanceObject;
import ch.certe.optimization.maintenancesolver.product.domain.MaintenanceObjectType;
import java.util.ArrayList;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author nalet
 */
@XmlRootElement
public class JsonMaintenanceObject {

    protected long id;
    protected String name;
    protected MaintenanceObjectType type;
    protected ArrayList<JsonInterval> intervals;

    JsonMaintenanceObject(MaintenanceObject maintenanceObject) {
        this.id = maintenanceObject.getId();
        this.name = maintenanceObject.getName();
        this.type = maintenanceObject.getType();
        this.intervals = JsonInterval.createFromList(maintenanceObject.getIntervals());
    }

    public static ArrayList<JsonMaintenanceObject> createFromList(ArrayList<MaintenanceObject> maintenanceObjects) {
        ArrayList<JsonMaintenanceObject> jsonMaintenanceObjects = new ArrayList<>();
        for (MaintenanceObject maintenanceObject : maintenanceObjects) {
            jsonMaintenanceObjects.add(new JsonMaintenanceObject(maintenanceObject));
        }
        return jsonMaintenanceObjects;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public MaintenanceObjectType getType() {
        return type;
    }

    public void setType(MaintenanceObjectType type) {
        this.type = type;
    }

    public ArrayList<JsonInterval> getIntervals() {
        return intervals;
    }

    public void setIntervals(ArrayList<JsonInterval> intervals) {
        this.intervals = intervals;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
