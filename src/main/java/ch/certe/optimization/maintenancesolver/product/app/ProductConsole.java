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
package ch.certe.optimization.maintenancesolver.product.app;

import ch.certe.optimization.maintenancesolver.product.domain.MaintenanceSchedule;
import ch.certe.optimization.maintenancesolver.product.domain.Task;
import ch.certe.optimization.maintenancesolver.product.presistence.ProductGenerator;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Timer;
import java.util.TimerTask;
import org.optaplanner.core.api.solver.Solver;
import org.optaplanner.core.api.solver.SolverFactory;

/**
 *
 * @author nalet
 */
public class ProductConsole {

    public static void main(String[] args) {
        // Build the Solver
        SolverFactory<MaintenanceSchedule> solverFactory = SolverFactory.createFromXmlResource(
                "ch/certe/optimization/maintenancesolver/product/solver/maintenanceScheduleSolverConfig.xml");
        Solver<MaintenanceSchedule> solver = solverFactory.buildSolver();

        // Load a problem
        MaintenanceSchedule unsolvedMaintenanceSchedule = new ProductGenerator().createProduct(5, 1);

        solver.addEventListener(event -> {
            MaintenanceSchedule bestSolution = event.getNewBestSolution();

            System.out.println("\nNew Solution:\n"
                    + toDisplayString(bestSolution));

        });

        // Solve the problem
        MaintenanceSchedule solvedMaintenanceSchedule = solver.solve(unsolvedMaintenanceSchedule);
        // Display the result
        System.out.println("\nFinal Solution:\n"
                + toDisplayString(solvedMaintenanceSchedule));
    }

    public static String toDisplayString(MaintenanceSchedule maintenanceSchedule) {
        StringBuilder displayString = new StringBuilder();
        ArrayList<Task> tasks = maintenanceSchedule.getTasks();

        Comparator<Task> comparator = Comparator.comparing(task -> task.getMaintenanceObject().getId());
        comparator = comparator.thenComparing(Comparator.comparing(task -> task.getPlanDate().getDayIndex()));
        tasks.sort(comparator);

        for (Task task : tasks) {
            displayString.append("\n ").append(task.getMaintenanceObject().getName()).append(" ").append(task);
        }
        return displayString.toString();
    }
}
