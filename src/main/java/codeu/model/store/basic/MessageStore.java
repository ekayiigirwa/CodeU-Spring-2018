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

package codeu.model.store.basic;

import codeu.model.data.Message;
import codeu.model.store.persistence.PersistentStorageAgent;

import codeu.model.data.User;
import codeu.model.store.basic.UserStore;
import java.util.*;


/**
 * Store class that uses in-memory data structures to hold values and automatically loads from and
 * saves to PersistentStorageAgent. It's a singleton so all servlet classes can access the same
 * instance.
 */
public class MessageStore {
UserStore userStore = UserStore.getInstance();
  /** Singleton instance of MessageStore. */
  private static MessageStore instance;

  /**
   * Returns the singleton instance of MessageStore that should be shared between all servlet
   * classes. Do not call this function from a test; use getTestInstance() instead.
   */
  public static MessageStore getInstance() {
    if (instance == null) {
      instance = new MessageStore(PersistentStorageAgent.getInstance());
    }
    return instance;
  }

  /**
   * Instance getter function used for testing. Supply a mock for PersistentStorageAgent.
   *
   * @param persistentStorageAgent a mock used for testing
   */
  public static MessageStore getTestInstance(PersistentStorageAgent persistentStorageAgent) {
    return new MessageStore(persistentStorageAgent);
  }

  /**
   * The PersistentStorageAgent responsible for loading Messages from and saving Messages to
   * Datastore.
   */
  private PersistentStorageAgent persistentStorageAgent;

  /** The in-memory list of Messages. */
  private List<Message> messages;


  /** This class is a singleton, so its constructor is private. Call getInstance() instead. */
  private MessageStore(PersistentStorageAgent persistentStorageAgent) {
    this.persistentStorageAgent = persistentStorageAgent;
    messages = new ArrayList<>();
  }

  /** Add a new message to the current set of messages known to the application. */
  public void addMessage(Message message) {
    messages.add(message);
    persistentStorageAgent.writeThrough(message);
  }

  /** Access the current set of Messages within the given Conversation. */
  public ArrayList<Message> getMessagesInConversation(UUID conversationId) {

    ArrayList<Message> messagesInConversation = new ArrayList<>();

    for (Message message : messages) {
      if (message.getConversationId().equals(conversationId)) {
        messagesInConversation.add(message);
      }
    }

    return messagesInConversation;
  }

  /** Sets the List of Messages stored by this MessageStore. */
  public void setMessages(List<Message> messages) {
    this.messages = messages;
  }


  /** Returns number of messages */
  public int count(){
  return messages.size();
  }

 /** given a particular message, returns the user that authored the message */
  public User getUserFromMsg(Message msg){
       //currentMsg = messages.get(i);
    User currentUser = null;
    UUID currentId = new UUID(0L, 0L);

    currentId = msg.getAuthorId();
    currentUser = userStore.getUser(currentId);
    return currentUser;
  }

/**
  * returns a list of users who created messages. users are repeated in  the list accordance
  * with the number of messages they sent
  */
public ArrayList<User> getUsersPlusMsgs(){
  ArrayList<User> usersWithMessages = new ArrayList<User>();
    // get list of users who created messages and the amount of times they did so
    User user = null;
    for (Message message : messages) {   
        //userNameId = message.getAuthorId();
        user = getUserFromMsg(message);
        usersWithMessages.add(user);
    }

    return usersWithMessages;
}

/**
  *accepts an array with the lsit of all messages in a conversation
  * returns a list of users in the conversation's messages
  */

public ArrayList<User> getUsersInConversation(ArrayList<Message> messageList){

  ArrayList<User> usersInConversation = new ArrayList<User>();
  
    User user = null;
    for (Message message : messageList) { 

        user = getUserFromMsg(message);
  
      if(usersInConversation.contains(user)){
        continue;
      }else{
         usersInConversation.add(user);
      }
    }

    return usersInConversation;
}



/** Returns wordiest user based on amount of messages sent */
  public String wordyUser(){

  UserStore userStore = UserStore.getInstance();

    User currentUser = null;
    User tempUser = null;  

    String wordiestUser = null;

    int i;
    int tempCount = 0;
    int maxCount = 0;

    ArrayList<User> usersWithMessages = this.getUsersPlusMsgs();

    /** Stores users that run through for loop */
    ArrayList<User> userCheckList = new ArrayList<User>();

    for(i = 0; i < messages.size();i++){

    currentUser = getUserFromMsg(messages.get(i));

    /** Tests to ensure that current user is not the same as any previous users */
       if(userCheckList.contains(currentUser)){
        continue;
      }else{
         userCheckList.add(currentUser);
      }
    tempUser = currentUser;

    /** Find number of occurances for currentUser */
    tempCount = Collections.frequency(usersWithMessages, currentUser);
    
      /** Initialize maxCount and keep track of the user with the highest count of messages */
      if(tempCount > maxCount){
            maxCount = tempCount;
            wordiestUser = currentUser.getName();
          }
      }
    return wordiestUser;
}

}
