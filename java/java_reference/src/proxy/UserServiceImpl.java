package proxy;

public class UserServiceImpl implements UserService {
    public void select() {
        System.out.println("select called");
    }
    public void update() {
        System.out.println("update called");
    }
}