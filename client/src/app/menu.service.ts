import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import {HttpClient, HttpHeaders, HttpResponse} from '@angular/common/http';
import { MenuItem } from './menu-item';

@Injectable({
  providedIn: 'root'
})
export class MenuService {

  constructor(private http : HttpClient) { }

  private readonly menuEndpoint : string = "/menu"  

  getMenu() : Observable<MenuItem[]> {
    return this.http.get<MenuItem[]>(this.menuEndpoint);
  }
}
