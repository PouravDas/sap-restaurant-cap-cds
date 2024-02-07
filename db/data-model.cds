namespace demo.restaurant;

using {cuid} from '@sap/cds/common';

entity Restaurants : cuid {
  name        : String @mandatory;
  description : String @mandatory;
  averageCost : Integer @mandatory;
  address     : String;
}

entity MenuItems : cuid {
  item        : String @mandatory;
  description : String @mandatory;
  price       : Double @mandatory;
  category    : String @mandatory;
  veg         : Boolean default false;
  restaurant  : Association to one Restaurants @mandatory;
}

@assert.unique: {
  item: [ user, item ]
}
entity Carts : cuid {
  user     : Association to one Users @mandatory;
  item     : Association to one MenuItems @mandatory;
  quantity : Integer @mandatory;
}

entity Users : cuid {
  name     : String @mandatory;
  password : String @mandatory;
  email    : String @mandatory;
  role     : String @mandatory;
}
