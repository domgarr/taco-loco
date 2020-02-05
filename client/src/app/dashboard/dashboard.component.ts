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
  menuItems : MenuItem[]; //Populated my MenuService
  order : any[] //Holds order to be send to OrderService (/order)

  @Output() orderChange = new EventEmitter<any>();

  constructor(private menuService: MenuService, private orderService : OrderService) {
    this.totalCost = 0.00;
    this.order = [];
    
    //Get the Tacoloco's menu items.
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

  //Event emitted by MenuComponent.
  onOrderChange(newOrder){
    let existingOrder = this.order.find( order => order.id == newOrder.id);
    if(existingOrder){
      //get index incase mutating or deletion is needed.
      let index = this.order.indexOf(existingOrder);
      //Check if customer has set quantity to 0.
      if(newOrder.quantity === 0){
        console.log(newOrder.quantity);
        //if so remove.
        this.order.splice(index,1);
      }else{
        //update quantity if customer updated qauntity of menuItem.
        this.order[index].quantity = newOrder.quantity;
      }
    }else{
      //If a MenuItem order is set with a 0 quantity exit exit the method as the /order endpoint doesn't allow for 0 quantities. 
      if(newOrder.quantity == 0){
        return;
      }
      //Add new menuItem order to array.
      this.order.push(newOrder);
    }

    //Update cost if all orders were removed from the array.
    if(this.order.length == 0){
      this.totalCost = 0;
      return;
    }

    this.orderService.calculateOrder(this.order).subscribe(response =>{
      this.totalCost = response.totalCost;
    },
    error => {
      console.log(error);
    })
  }

}
