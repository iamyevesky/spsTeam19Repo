package com.google.sps.classes;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Query.SortDirection;
import com.google.appengine.api.datastore.EntityNotFoundException;
import com.google.appengine.api.datastore.DatastoreNeedIndexException;
import com.google.appengine.api.datastore.FetchOptions;
import java.io.IOException;
import com.google.cloud.Timestamp;
import com.google.cloud.language.v1.Document;
import com.google.cloud.language.v1.LanguageServiceClient;
import com.google.cloud.language.v1.Sentiment;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@WebServlet("/updateSentiment")
public class RunSentimentAnalysisServlet extends HttpServlet
{
    DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException{
        Query query = new Query("Message");
        List<Entity> result = datastore.prepare(query).asList(FetchOptions.Builder.withChunkSize(5000));
        LanguageServiceClient languageService = LanguageServiceClient.create();
        for (Entity entity : result)
        {
            Document doc = Document.newBuilder().setContent((String) entity.getProperty("message")).setType(Document.Type.PLAIN_TEXT).build();
            Sentiment sentiment = languageService.analyzeSentiment(doc).getDocumentSentiment();
            float score = sentiment.getScore();
            entity.setProperty("sentiment", score);
            datastore.put(entity);        
        }
        languageService.close();
        response.sendRedirect("/");
    }
}