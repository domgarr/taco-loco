import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class OrderService {

  private readonly orderEndpoint : string = "/order"  


  constructor(private http : HttpClient) { }



  calculateOrder(order : any[]) : Observable<any>{
    return this.http.post<any>("/api" + this.orderEndpoint, order);
  }

  
}
