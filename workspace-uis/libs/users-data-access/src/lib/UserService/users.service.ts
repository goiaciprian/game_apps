import { Injectable } from '@angular/core';
import {Observable, of} from "rxjs";
import {UsersEntity} from "@workspace-uis/users-data-access";

@Injectable({
  providedIn: 'root'
})
export class UsersService {
  constructor() { }

  getUsers(): Observable<UsersEntity[]> {
    return of([])
  }
}
