export class MaintenanceSchedule {
  maintenanceObjects: MaintenanceObject[];
}

interface MaintenanceObject {
  name: string;
  intervals: Interval[];
}

interface Interval {
  period: Period;
  tasks: Task[];
}

interface Task {
  taskType: string;
  planDate: PlanDate;
}

interface PlanDate {
  dayOfMonth: string;
  monthValue: string;
  year: string;
}

interface Period {
  years: number;
  months: number;
  days: number;
}