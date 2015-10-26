package frontEnd;


public interface FrontEndService {

    public void register(String name, String password);

    public boolean isRegistered(String name);

   // public String authenticate(String name, String password);

    public boolean isAuthenticated(String sessionId);

}
