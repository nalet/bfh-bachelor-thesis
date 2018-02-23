import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';

import { AppRoutingModule } from './app-routing.module';

import { AppComponent } from './app.component';
import { IndexComponent } from './index/index.component';
import { MaintenanceSolverComponent } from './maintenance-solver/maintenance-solver.component';
import { MaintenanceService } from './maintenance/maintenance.service';
import { HttpClientModule } from '@angular/common/http';


@NgModule({
  declarations: [
    AppComponent,
    IndexComponent,
    MaintenanceSolverComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule
  ],
  providers: [MaintenanceService],
  bootstrap: [AppComponent]
})
export class AppModule { }
