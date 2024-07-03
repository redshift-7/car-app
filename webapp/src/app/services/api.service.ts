import { Injectable } from '@angular/core';
import { Observable, of } from 'rxjs';
import { HttpClient } from "@angular/common/http";
import { map, catchError } from 'rxjs/operators';
import { dev } from '../../environments/dev';
import { EnvironmentService } from './environment.service';
import { User } from '../models/user.model';
import { Car } from '../models/car.model';

@Injectable({
  providedIn: 'root'
})
export class ApiService {
  private baseUrl = `${dev.apiBaseUrl}`;

  constructor(private http: HttpClient, private environmentService: EnvironmentService) { }

  demoDataPath = 'assets/demo-data-ee.json';

  getData<T>(endpoint: string): Observable<T[]> {
    const url = this.environmentService.isGitHubPages() ? this.demoDataPath : `${this.baseUrl}/${endpoint}`;

    return this.http.get<any[]>(url).pipe(
      map(data => {
        if (this.environmentService.isGitHubPages()) {
          return endpoint === 'users' ? this.addUserToCars(data) : this.extractCars(data);
        }
        return data;
      })
    ) as Observable<T[]>;
  }

  getItemById<T>(endpoint: string, id: number): Observable<T> {
    const url = this.environmentService.isGitHubPages() ? this.demoDataPath : `${this.baseUrl}/${endpoint}/${id}`;

    return this.http.get<any[]>(url).pipe(
      map(data => {
        if (this.environmentService.isGitHubPages()) {
          if (endpoint === 'users') {
            const user = data.find((user: User) => user.id === id);
            if (user) {
              return this.addUserToSingleUser(user) as T;
            }
          } else if (endpoint === 'cars') {
            const cars = this.extractCars(data);
            const car = cars.find((car: Car) => car.id === id);
            if (car) {
              return car as T;
            }
          }
        } else {
          return data as T;
        }
        throw new Error(`${endpoint.slice(0, -1).toUpperCase()} with id ${id} not found`);
      }),
      catchError(error => {
        console.error(error);
        return of(undefined as T); // Return an observable with a default value
      })
    );
  }

  private addUserToCars(users: User[]): User[] {
    return users.map(user => ({
      ...user,
      cars: user.cars.map(car => ({
        ...car,
        user: user
      }))
    }));
  }

  private addUserToSingleUser(user: User): User {
    return {
      ...user,
      cars: user.cars.map(car => ({
        ...car,
        user: user
      }))
    };
  }

  private extractCars(users: User[]): Car[] {
    return users.flatMap(user =>
      user.cars.map(car => ({
        ...car,
        user: user
      }))
    );
  }
}
