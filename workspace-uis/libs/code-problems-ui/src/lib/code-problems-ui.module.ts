import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { CodeProblemsComponent } from './components/code-problems/code-problems.component';
import {ReactiveFormsModule} from "@angular/forms";
import {MatInputModule} from "@angular/material/input";

@NgModule({
  imports: [ CommonModule, ReactiveFormsModule, MatInputModule ],
  declarations: [CodeProblemsComponent],
  exports: [CodeProblemsComponent]
})
export class CodeProblemsUiModule {}
