import {Component, Input, OnInit, ViewEncapsulation} from '@angular/core';
import {ActivatedRoute, Router} from "@angular/router";

@Component({
  selector: 'workspace-uis-login-register-page',
  templateUrl: './login-register-page.component.html',
  styleUrls: ['./login-register-page.component.scss'],
  encapsulation: ViewEncapsulation.Emulated,
})
export class LoginRegisterPageComponent implements OnInit {
  @Input() type?: "register" | "login";

  constructor(private router: ActivatedRoute) { }

  ngOnInit(): void {
    this.type = this.router.snapshot.data['type']
  }
}
