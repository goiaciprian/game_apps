import {Component, Input, OnInit} from '@angular/core';
import {FormBuilder, FormGroup} from "@angular/forms";

@Component({
  selector: 'workspace-uis-code-problems',
  templateUrl: './code-problems.component.html',
  styleUrls: ['./code-problems.component.scss'],
})
export class CodeProblemsComponent implements OnInit {

  @Input() privileged: boolean = false;
  formGroup: FormGroup;

  constructor(private formBuilder: FormBuilder) {
    this.formGroup = this.formBuilder.group({
      runCode: [''],
      testCode: ['']
    });
  }

  ngOnInit(): void {}
}
