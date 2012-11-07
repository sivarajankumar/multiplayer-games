package persistence;

import model.PlayerImpl;

public interface UserRepository {

	PlayerImpl getUserByUserName(String userName);
}
