import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Car } from "../models/car.model";
import {ApiService} from "./api.service";

@Injectable({
  providedIn: 'root'
})
export class CarService {
  private endpoint = 'cars';

  constructor(private apiService: ApiService) { }

  getCars(): Observable<Car[]> {
    return this.apiService.getData<Car>(this.endpoint);
  }

  getCarById(id: number): Observable<Car> {
    return this.apiService.getItemById<Car>(this.endpoint, id);
  }
}
