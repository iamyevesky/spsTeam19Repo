package com.google.sps.classes;

import java.io.IOException;
import java.io.PrintWriter;
import com.pusher.client.PresenceUser;
import com.pusher.client.Pusher;
import java.io.UnsupportedEncodingException;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonNull;
import com.google.gson.GsonBuilder;
import java.util.Map;
import java.util.HashMap;
import java.net.URLDecoder;
import com.google.common.io.CharStreams;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;

public class SendMessageServlet extends HttpServlet {

  private Gson gson = new GsonBuilder().create();
  private TypeReference<Map<String, String>> typeReference =
      new TypeReference<Map<String, String>>() {};

  @Override
  public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
    // Parse POST request body received in the format:
    // [{"message": "my-message", "socket_id": "1232.24", "channel": "presence-my-channel"}]

    String body = CharStreams.readLines(request.getReader()).toString();
    String json = body.replaceFirst("^\\[", "").replaceFirst("\\]$", "");
    Map<String, String> data = gson.fromJson(json, typeReference.getType());
    String message = data.get("message");
    String socketId = data.get("socket_id");
    String channelId = data.get("channel_id");

    User user = UserServiceFactory.getUserService().getCurrentUser();
    // User email prefix as display name for currently authenticated user
    String displayName = user.getNickname().replaceFirst("@.*", "");

    // Create a message including the user email prefix to display in the chat window
    String taggedMessage = "<strong><" + displayName + "></strong> " + message;
    Map<String, String> messageData = new HashMap<>();
    messageData.put("message", taggedMessage);

    // Send a message over the Pusher channel (maximum size of a message is 10KB)
    Result result =
        PusherService.getInstance()
            .trigger(
                channelId,
                "new_message", // name of event
                messageData,
                socketId); // (Optional) Use client socket_id to exclude the sender from receiving the message

    // result.getStatus() == SUCCESS indicates successful transmission
    messageData.put("status", result.getStatus().name());

    response.getWriter().println(gson.toJson(messageData));
  }
}