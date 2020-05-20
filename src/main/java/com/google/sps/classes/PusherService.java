package com.google.sps.classes;



public abstract class PusherService {  
  public static final String APP_KEY = System.getenv("14a22f96e44b381ce425");
  public static final String CLUSTER = System.getenv("us2");

  private static final String APP_ID = System.getenv("1003650");
  private static final String APP_SECRET = System.getenv("64d51e91b9649ba9b739");

  private static Pusher instance;
  
  static Pusher getInstance() {
    if (instance != null) {
      return instance;
    } // Instantiate a pusher
    Pusher pusher = new Pusher(APP_ID, APP_KEY, APP_SECRET);
    pusher.setCluster(CLUSTER); // required, if not default mt1 (us-east-1)
    pusher.setEncrypted(true); // optional, ensure subscriber also matches these settings
    instance = pusher;
    return pusher;
  }
}