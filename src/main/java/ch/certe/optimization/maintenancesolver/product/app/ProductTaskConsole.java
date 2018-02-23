/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.certe.optimization.maintenancesolver.product.app;

import ch.certe.optimization.maintenancesolver.product.domain.Employee;
import ch.certe.optimization.maintenancesolver.product.domain.EmployeeTaskAssignment;
import ch.certe.optimization.maintenancesolver.product.domain.MaintenanceSchedule;
import ch.certe.optimization.maintenancesolver.product.domain.MaintenanceScheduleTask;
import ch.certe.optimization.maintenancesolver.product.domain.Task;
import ch.certe.optimization.maintenancesolver.product.presistence.ProductGenerator;
import java.util.ArrayList;
import java.util.Comparator;
import org.optaplanner.core.api.solver.Solver;
import org.optaplanner.core.api.solver.SolverFactory;

/**
 *
 * @author nalet
 */
public class ProductTaskConsole {

    public static void main(String[] args) {
        // Build the Solver
        SolverFactory<MaintenanceScheduleTask> solverFactory = SolverFactory.createFromXmlResource(
                "ch/certe/optimization/maintenancesolver/product/solver/maintenanceScheduleTaskSolverConfig.xml");
        Solver<MaintenanceScheduleTask> solver = solverFactory.buildSolver();

        // Load a problem
        MaintenanceScheduleTask unsolvedMaintenanceScheduleTask = new ProductGenerator().createTaskProduct(50, 1);

        solver.addEventListener(event -> {
            MaintenanceScheduleTask bestSolution = event.getNewBestSolution();

            System.out.println("\nNew Solution:\n"
                    + toDisplayString(bestSolution));

        });

        // Solve the problem
        MaintenanceScheduleTask solvedMaintenanceSchedule = solver.solve(unsolvedMaintenanceScheduleTask);

        // Display the result
        System.out.println("\nFinal Solution:\n"
                + toDisplayString(solvedMaintenanceSchedule));
    }

    public static String toDisplayString(MaintenanceScheduleTask maintenanceScheduleTask) {
        StringBuilder displayString = new StringBuilder();
        ArrayList<Task> tasks = maintenanceScheduleTask.getTasks();

        Comparator<Task> comparator = Comparator.comparing(task -> task.getMaintenanceObject().getId());
        comparator = comparator.thenComparing(Comparator.comparing(task -> task.getPlanDate().getDayIndex()));
        tasks.sort(comparator);

        for (Task task : tasks) {
            displayString.append("\n ").append(task.getMaintenanceObject().getName()).append(" ").append(task).append(" ").append(ProductTaskConsole.getEmployee(task,maintenanceScheduleTask));
        }
        return displayString.toString();
    }

    private static String getEmployee(Task task, MaintenanceScheduleTask maintenanceScheduleTask) {
        for(EmployeeTaskAssignment eta : maintenanceScheduleTask.getEmployeeTaskAssignments()) {
            if(eta.getTask().equals(task)) {
                return eta.getEmployee().getName();
            }
        }
        return "";
    }
}
