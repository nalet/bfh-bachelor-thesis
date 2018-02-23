/*
 * Copyright 2011 Red Hat, Inc. and/or its affiliates.
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
package ch.certe.optimization.maintenancesolver.product.domain.solution;

import ch.certe.optimization.maintenancesolver.product.domain.MaintenanceSchedule;
import ch.certe.optimization.maintenancesolver.product.domain.Task;
import java.util.List;
import org.apache.commons.lang3.builder.CompareToBuilder;
import org.optaplanner.core.impl.heuristic.selector.common.decorator.SelectionSorterWeightFactory;

public class TaskDifficultyWeightFactory implements SelectionSorterWeightFactory<MaintenanceSchedule, Task> {

    @Override
    public TaskDifficultyWeight createSorterWeight(MaintenanceSchedule maintenanceSchedule, Task task) {
        int distanceFromNearest = 0;
        if (this.inBeetween(task)) {
            distanceFromNearest = this.distanceFromInbeetween(task);
        } else {
            distanceFromNearest = this.distanceFromNearest(task);
        }
        return new TaskDifficultyWeight(task, distanceFromNearest);
    }

    private int distanceFromNearest(Task task) {
        int nearest = task.getDist(task.getNearestTask());
        int intervalMinDist = task.getMinDist();
        int intervalMaxDist = task.getMaxDist();

        if (intervalMinDist > nearest) {
            return intervalMinDist - nearest;
        }

        if (intervalMaxDist < nearest) {
            return nearest - intervalMaxDist;
        }
        return 0;
    }

    private boolean inBeetween(Task task) {
        List<Task> tasks = task.getInterval().getTasks();
        int maxNext = -1;
        int maxPrevios = -1;
        for (Task t : tasks) {
            if (t.equals(task) || t.getPlanDate().getDayIndex() == task.getPlanDate().getDayIndex()) {
                continue;
            }
            if (task.getPlanDate().getDayIndex() < t.getPlanDate().getDayIndex()) {
                maxNext = t.getPlanDate().getDayIndex();
            }
            if (task.getPlanDate().getDayIndex() > t.getPlanDate().getDayIndex()) {
                maxPrevios = t.getPlanDate().getDayIndex();
            }
        }
        if (maxNext != -1 && maxPrevios != -1) {
            return true;
        }
        return false;
    }

    private int distanceFromInbeetween(Task task) {
        List<Task> tasks = task.getInterval().getTasks();
        int maxNext = -1;
        int maxPrevios = -1;
        int dayIndex = task.getPlanDate().getDayIndex();
        int intervalMinDist = task.getMinDist();
        int intervalMaxDist = task.getMaxDist();
        int score = 0;
        for (Task t : tasks) {
            if (t.equals(task) || t.getPlanDate().getDayIndex() == task.getPlanDate().getDayIndex()) {
                continue;
            }
            if (task.getPlanDate().getDayIndex() < t.getPlanDate().getDayIndex()) {
                maxNext = t.getPlanDate().getDayIndex();
            }
            if (task.getPlanDate().getDayIndex() > t.getPlanDate().getDayIndex()) {
                maxPrevios = t.getPlanDate().getDayIndex();
            }
        }
        int nearestNext = maxNext - dayIndex;
        if (intervalMinDist > nearestNext) {
            score += intervalMinDist - nearestNext;
        }

        if (intervalMaxDist < nearestNext) {
            score += nearestNext - intervalMaxDist;
        }

        int nearestPrevious = dayIndex - maxPrevios;
        if (intervalMinDist > nearestPrevious) {
            score += intervalMinDist - nearestPrevious;
        }

        if (intervalMaxDist < nearestPrevious) {
            score += nearestPrevious - intervalMaxDist;
        }

        return score;
    }

    public static class TaskDifficultyWeight implements Comparable<TaskDifficultyWeight> {

        private final Task task;
        private final int score;

        public TaskDifficultyWeight(Task task, int score) {
            this.task = task;
            this.score = score;
        }

        @Override
        public int compareTo(TaskDifficultyWeight other) {
            return new CompareToBuilder()
                    // The more difficult queens have a lower distance to the middle
                    .append(other.score, score) // Decreasing
                    // Tie breaker
                    .append(task, other.task)
                    .toComparison();
        }

    }

}
