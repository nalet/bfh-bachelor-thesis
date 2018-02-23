import { Component, OnInit } from '@angular/core';
import { MaintenanceService } from '../maintenance/maintenance.service';
import { MaintenanceSchedule } from '../domain/maintenance-schedule';
import { Status } from '../domain/status';

@Component({
  selector: 'app-maintenance-solver',
  templateUrl: './maintenance-solver.component.html',
  styleUrls: ['./maintenance-solver.component.scss']
})
export class MaintenanceSolverComponent implements OnInit {

  solvingState: boolean = false;
  maintenanceSchedule: MaintenanceSchedule;
  status: Status;

  constructor(private maintenanceService: MaintenanceService) {
    this.refreshData();
  }

  ngOnInit(): void {

  }

  refreshData(): void {
    this.maintenanceService.getSolution().subscribe(data => { this.maintenanceSchedule = data });
    this.maintenanceService.getStatus().subscribe(data => { this.status = data; this.checkStatus() });
  }

  checkStatus(): void {
    if (!this.solvingState && this.status.solvingState) {
      this.solvingState = this.status.solvingState;
      this.update();
    }
    if (this.solvingState && !this.status.solvingState) {
      this.solvingState = this.status.solvingState;
      this.update();
    }
  }

  update(): void {
    this.refreshData();
    if (this.solvingState) {
      setTimeout(() => { this.update() }, 1000);
    }
  }

  startSolve(): void {
    this.solvingState = true;
    this.maintenanceService.solve().subscribe();
    this.update();
  }

  terminateEarly(): void {
    this.maintenanceService.terminateEarly().subscribe(data => { this.solvingState = false });
  }

  reset(): void {
    this.maintenanceService.reset().subscribe(data => { this.solvingState = false });
  }

}
