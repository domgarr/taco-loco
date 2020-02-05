import { Component, OnInit, Input, EventEmitter, Output, NgModule} from '@angular/core';
import { MenuItem } from '../menu-item';


@Component({
  selector: 'app-menu-item',
  templateUrl: './menu-item.component.html',
  styleUrls: ['./menu-item.component.css']
})

/**
 * MenuItemComponent contains input boxes denoting quantity of an item ordered, anytime the quantity is changed an event is emitted to the DashboardComponent
 * where the total cost is updated.
 *
 */

export class MenuItemComponent implements OnInit {
  quantity : number;

  @Input()  menuItem : MenuItem;
  @Output() orderChange = new EventEmitter<any>(); //An event consumed by the dashboard componenet.  
  constructor() { 
    
  }

  ngOnInit() {
    
  }

  /**
   *  Trigger any time the user changes an input.
   * 
   *  A max quantity ordered for a single item is 100 (This was set by me to have some limitation) and negative quantites are valid quantites.
   */ 
  onQuantityChange(event){
    //Prevent users from entering negative values.
    if(this.quantity == null || this.quantity < 0){
      this.quantity = 0;
    }
   
    //Set max order to 100.
    if(this.quantity > 100){
      this.quantity = 100;
    }
    
    //Update input value this way. I attempted to use 2-way binding here, but it wasn't working. Remember to re-look into 2-way binding.
    event.target.value = this.quantity;

    // Create an object with the two mandatory fields needed to calculate totalCost.
    let reqObj = {
      "id":this.menuItem.id,
      "quantity": Number(this.quantity) //Convert quantity back to a number.
    };
    //Dashboard, not named very well, contains the event called when a user changes the order.
   this.orderChange.emit(reqObj);
 }

}
