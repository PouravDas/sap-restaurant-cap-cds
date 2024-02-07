using demo.restaurant as demo from '../db/data-model';

service RestaurantService {

  entity Users       as projection on demo.Users;
  entity Restaurants as projection on demo.Restaurants;
  entity MenuItems   as projection on demo.MenuItems;
  entity Carts       as projection on demo.Carts;
}

service customapi {
  function hello()                                             returns String;
  function generateToken(username : String, password : String) returns String;
  function currentUser()                                       returns String;
  action   signup(username : String, password : String, email : String);
}

annotate RestaurantService.Restaurants with @restrict: [
  {
    grant: 'READ',
    to   : 'any'
  },
  {
    grant: 'CREATE',
    to   : 'ADMIN'
  },
  {
    grant: 'UPDATE',
    to   : 'ADMIN',
  },
  {
    grant: 'DELETE',
    to   : 'ADMIN'
  }
];

annotate RestaurantService.MenuItems with @restrict: [
  {
    grant: 'READ',
    to   : 'any'
  },
  {
    grant: 'CREATE',
    to   : 'ADMIN'
  },
  {
    grant: 'UPDATE',
    to   : 'ADMIN',
  },
  {
    grant: 'DELETE',
    to   : 'ADMIN'
  }
];

annotate RestaurantService.Carts with @restrict: [
  {
    grant: 'READ',
    to   : 'any'
  },
  {
    grant: 'CREATE',
    to   : 'USER'
  },
  {
    grant: 'UPDATE',
    to   : 'USER',
  },
  {
    grant: 'DELETE',
    to   : 'USER'
  }
];

annotate RestaurantService.Users with @restrict: [
  {
    grant: 'READ',
    to   : 'any'
  },
  {
    grant: 'CREATE',
    to   : 'any'
  },
  {
    grant: 'UPDATE',
    to   : 'any',
  },
  {
    grant: 'DELETE',
    to   : 'ADMIN'
  }
];
