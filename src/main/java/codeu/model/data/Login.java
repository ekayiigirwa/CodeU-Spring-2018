package codeu.model.data;
import java.io.Serializable;
import java.util.Comparator;
import java.time.Instant;

public class Login extends Activity implements Serializable{
	private Instant time;
	private String name;
	
	/**
	   * Constructs time of login.
	   *
	   * @param time the time of login of user
	   */
	public Login(Instant time, String name) {
		this.time = time;
		this.name = name;
	}
	
	public Instant getTime() {
	  return time;
	}
	
	@Override
    public String toString() {
        return time.toString();
    }
	
	int compareTo(Login time){
		return this.time.compareTo(time.getTime());
	}
	
	public boolean equals(Login time) {
	   if ((this.time.compareTo(time.getTime()) == 0))
		   return true;
	   return false;
	}
	
	/** Returns time of this Login. */
	public Instant getCreationTime() {
	  return time;
	}
	
	public String getName() {
	    return name;
	  }
	
	

}
