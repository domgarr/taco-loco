export class MenuItem {
    id : number;
    name: string;
    cost : number;

    constructor(){

    }

    get Id() : number {
        return this.id;
    }

    set Id(id : number){
        this.id = id;
    }

    get Name() : string {
        return this.name;
    }

    set Name(name : string){
        this.name = name;
    }

    get Cost() : number {
        return this.cost;
    }

    set Cost(cost : number){
        this.cost = cost;
    }
}
