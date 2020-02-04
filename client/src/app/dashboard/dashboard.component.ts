import { Component, OnInit, Output, EventEmitter} from '@angular/core';
import { MenuService } from '../menu.service';
import { OrderService } from '../order.service';
import { MenuItem } from '../menu-item';
import { ObjectUnsubscribedError } from 'rxjs';

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.css']
})
export class DashboardComponent implements OnInit {

  totalCost : number;
  menuItems : MenuItem[];
  order : any[]

  @Output() orderChange = new EventEmitter<any>();

  constructor(private menuService: MenuService, private orderService : OrderService) {
    this.totalCost = 0.00;
    this.order = [];
    
    this.menuService.getMenu().subscribe(
      menuItems =>{
      this.menuItems = menuItems;
     },
      error => {
       console.log(error);
     });
   }

  ngOnInit() {
  }

  onOrderChange(newOrder){
    let existingOrder = this.order.find( order => order.id == newOrder.id);
    if(existingOrder){
      //get index
      let index = this.order.indexOf(existingOrder);
      //Check if customer has set quantity to 0.
      if(newOrder.quantity === 0){
        //if so remove.
        this.order.splice(index,1);
      }else{
        //update quantity if customer updated qauntity of order.
        this.order[index].quantity = newOrder.quantity;
      }
    }else{
      //Add new menu item order to array.
      this.order.push(newOrder);
    }

    if(this.order.length == 0){
      return;
    }

    this.orderService.calculateOrder(this.order).subscribe(response =>{
      this.totalCost = response.totalCost;
    })
  }

}
