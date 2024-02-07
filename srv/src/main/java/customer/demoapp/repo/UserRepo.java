package customer.demoapp.repo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.sap.cds.Result;
import com.sap.cds.ql.Insert;
import com.sap.cds.ql.Select;
import com.sap.cds.ql.cqn.CqnSelect;
import com.sap.cds.services.persistence.PersistenceService;

import cds.gen.restaurantservice.Users_;
import cds.gen.restaurantservice.Users;

@Repository
public class UserRepo {

  @Autowired
  private PersistenceService db;

  public Users getUserByName(String name) {
    CqnSelect select = Select.from(Users_.class).where(u -> u.name().eq(name));
    Users user = null;
    Result result = db.run(select);
    if (result.rowCount() == 1) {
      user = result.single(Users.class);
    }
    return user;
  }

  public void addUser(String name, String password, String email, String roles) {
    Users user = Users.create();
    // user.setId(id);
    user.setName(name);
    user.setPassword(password);
    user.setEmail(email);
    user.setRole(roles);

    db.run(Insert.into(Users_.class).entry(user));
  }
}
