import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { MaintenanceSolverComponent } from './maintenance-solver.component';

describe('MaintenanceSolverComponent', () => {
  let component: MaintenanceSolverComponent;
  let fixture: ComponentFixture<MaintenanceSolverComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ MaintenanceSolverComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(MaintenanceSolverComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
