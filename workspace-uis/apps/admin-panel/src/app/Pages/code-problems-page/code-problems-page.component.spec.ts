import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CodeProblemsPageComponent } from './code-problems-page.component';

describe('CodeProblemsPageComponent', () => {
  let component: CodeProblemsPageComponent;
  let fixture: ComponentFixture<CodeProblemsPageComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [CodeProblemsPageComponent],
    }).compileComponents();

    fixture = TestBed.createComponent(CodeProblemsPageComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
