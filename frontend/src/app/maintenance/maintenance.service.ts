import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Status } from '../domain/status';
import { Observable } from 'rxjs/Observable';
import { MaintenanceSchedule } from '../domain/maintenance-schedule';
import { Message } from '../domain/message';

@Injectable()
export class MaintenanceService {

  constructor(private httpClient: HttpClient) {

  }

  public getStatus(): Observable<Status> {
    return this.httpClient.get<Status>('/api/maintenance/status');
  }

  public getSolution(): Observable<MaintenanceSchedule> {
    return this.httpClient.get<MaintenanceSchedule>('/api/maintenance/solution');
  }

  public solve(): Observable<Message> {
    return this.httpClient.get<Message>('/api/maintenance/solution/solve');
  }

  public terminateEarly(): Observable<Message> {
    return this.httpClient.get<Message>('/api/maintenance/solution/terminateEarly');
  }

  public reset(): Observable<Message> {
    return this.httpClient.get<Message>('/api/maintenance/solution/reset');
  }

}
