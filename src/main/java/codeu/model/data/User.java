// Copyright 2017 Google Inc.
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//    http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

package codeu.model.data;

import java.time.Instant;
import java.util.ArrayList;
import java.util.UUID;

import javax.servlet.http.HttpSession;

/** Class representing a registered user. */
public class User extends Activity{
  private final UUID id;
  private final String name;
  private final String passwordHash;
  private final Instant creation;
  String bio;
  public ArrayList<Login> login = new ArrayList<Login>();
  public ArrayList<Logout> logout= new ArrayList<Logout>();

  /**
   * Constructs a new User.
   *
   * @param id the ID of this User
   * @param name the username of this User
   * @param passwordHash the password hash of this User
   * @param creation the creation time of this User
   * @param login the login time of this User
   * @param logout the logout time of this User
   */
  public User(UUID id, String name, String passwordHash, Instant creation) {
    this.id = id;
    this.name = name;
    this.passwordHash = passwordHash;
    this.creation = creation;
  }

  /** Returns the ID of this User. */
  public UUID getId() {
    return id;
  }

  /** Returns the username of this User. */
  public String getName() {
    return name;
  }
  
  /** Returns the password hash of this User. */
  public String getPasswordHash() {
    return passwordHash;
  }

  /** Returns the creation time of this User. */
  public Instant getCreationTime() {
    return creation;
  }

  /** Returns the bio of this User. */
  public void setBio(String bioNew) {
    this.bio = bioNew; 
    //System.out.println("bio info from User class" + this.bio);  

  }

/** Returns the bio of this User. */
  public String getBio() {
    return this.bio;
  
  }

  // Allows the use of the frequency method needed for 
//counting number of occurances

@Override
    public boolean equals(Object o)
    {
        User s;
        if(!(o instanceof User))
        {
            return false;
        }
         
        else
        {
            s = (User) o;
            if(this.name.equals(s.getName()) && this.id == s.getId() ) 
            //&& this.passwordHash == s.getPasswordHash() && this.creation = s.getCreationTime())
            {
                return true;
            }
        }
        return false;
    }

  
  /** Returns the Array List of Login Times */
  public ArrayList<Login> getLoginArr() {
    return login;
  }
  
  /** Sets the Array List of Login Times */
  public void setLoginArr(ArrayList<Login> login) {
    this.login = login;
  }
  
  /** Returns the Array List of Login Times */
  public ArrayList<Logout> getLogoutArr() {
    return logout;
  }
  
  /** Sets the Array List of Login Times */
  public void setLogoutArr(ArrayList<Logout> logout) {
    this.logout = logout;
  }
  
}
