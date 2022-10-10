import {Component, OnInit} from '@angular/core';
import {UsersFacade} from "@workspace-uis/users-data-access";

@Component({
  selector: 'workspace-uis-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss'],
})
export class AppComponent implements OnInit{
  title = 'admin-panel';

  constructor( private userFacade: UsersFacade) { }

  ngOnInit() {
    this.userFacade.init();
  }
}
