import { Component, OnInit, Input, EventEmitter, Output, NgModule} from '@angular/core';
import { MenuItem } from '../menu-item';


@Component({
  selector: 'app-menu-item',
  templateUrl: './menu-item.component.html',
  styleUrls: ['./menu-item.component.css']
})
export class MenuItemComponent implements OnInit {
  quantity : number;

  @Input()  menuItem : MenuItem;
  @Output() orderChange = new EventEmitter<any>(); //An event consumed by the dashboard componenet.  
  constructor() { 
    
  }

  ngOnInit() {
    
  }

  //Trigger any time the user changes an input.
  onQuantityChange(event){
    console.log(event);
    //Prevent users from entering negative values.
    if(this.quantity == null || this.quantity < 0){
      this.quantity = 0;
    }
   
    //Set max order to 100.
    if(this.quantity > 100){
      this.quantity = 100;
    }
    
    //Update input value this way, since 2-way binding doesn't want to work or i just don't understand it.
    event.target.value = this.quantity;

    console.log(this.quantity);
    // Create an object with the two mandatory fields needed to calculate totalCost.
    let reqObj = {
      "id":this.menuItem.id,
      "quantity": Number(this.quantity)
    };
    //Dashboard, not named very well, contains the event called when a user changes the order.
   this.orderChange.emit(reqObj);
 }

}
