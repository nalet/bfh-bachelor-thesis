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
import java.util.ArrayList;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author nalet
 */
@XmlRootElement
public class MaintenanceObject extends AbstractPersistable{
    
    private String name;
    private MaintenanceObjectType type;
    private ArrayList<Interval> intervals;

    public MaintenanceObject(String name, MaintenanceObjectType type, ArrayList<Interval> intervals, long id) {
        super(id);
        this.name = name;
        this.type = type;
        this.intervals = intervals;
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

    public ArrayList<Interval> getIntervals() {
        return intervals;
    }

    public void setIntervals(ArrayList<Interval> intervals) {
        this.intervals = intervals;
    }
    
    
}
