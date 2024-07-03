import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { User } from '../models/user.model';
import { ApiService } from './api.service';

@Injectable({
  providedIn: 'root'
})
export class UserService {
  private endpoint = 'users';

  constructor(private apiService: ApiService) { }

  getUsers(): Observable<User[]> {
    return this.apiService.getData<User>(this.endpoint);
  }

  getUserById(id: number): Observable<User> {
    return this.apiService.getItemById<User>(this.endpoint, id);
  }
}
