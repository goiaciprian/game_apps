import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CodeProblemsComponent } from './code-problems.component';

describe('CodeProblemsComponent', () => {
  let component: CodeProblemsComponent;
  let fixture: ComponentFixture<CodeProblemsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [CodeProblemsComponent],
    }).compileComponents();

    fixture = TestBed.createComponent(CodeProblemsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
