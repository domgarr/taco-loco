import { Component, OnInit, Input, EventEmitter, Output} from '@angular/core';
import { MenuItem } from '../menu-item';
import { FormsModule } from "@angular/forms";

@Component({
  selector: 'app-menu-item',
  templateUrl: './menu-item.component.html',
  styleUrls: ['./menu-item.component.css']
})
export class MenuItemComponent implements OnInit {
  quantity : number;

  @Input()  menuItem : MenuItem;
  @Output() orderChange = new EventEmitter<any>(); //An event consumed by dashboard componenet.  
  constructor() { 
    this.quantity = 0;
  }

  ngOnInit() {
    
  }

  onQuantityChange(){
    if(!this.quantity){
      return;
    }
    if(this.quantity < 0){
      this.quantity = 0;
    }

    let reqObj = {
      "id":this.menuItem.id,
      "quantity": Number(this.quantity)
    };

   this.orderChange.emit(reqObj);

 }

}
