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
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.ling.CoreAnnotations.SentencesAnnotation;
import edu.stanford.nlp.util.CoreMap;
import edu.stanford.nlp.sentiment.SentimentCoreAnnotations.SentimentAnnotatedTree;
import edu.stanford.nlp.neural.rnn.RNNCoreAnnotations;
import edu.stanford.nlp.trees.Tree;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;

@WebServlet("/updateSentiment")
public class RunSentimentAnalysisServlet extends HttpServlet
{
    DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException{
        Query query = new Query("Message");
        List<Entity> result = datastore.prepare(query).asList(FetchOptions.Builder.withChunkSize(5000));
        Properties props = new Properties();
        props.setProperty("annotators", "tokenize, ssplit, parse, sentiment");
        StanfordCoreNLP pipeline = new StanfordCoreNLP(props);

        for (Entity entity : result)
        {
            Annotation annotation = pipeline.process((String) entity.getProperty("message"));
            float maxSentiment = 0;
            for (CoreMap sentence : annotation.get(SentencesAnnotation.class))
            {
                Tree tree = sentence.get(SentimentAnnotatedTree.class);
                int currSentiment = RNNCoreAnnotations.getPredictedClass(tree);
                if (currSentiment >= 0)
                {
                    if (currSentiment > maxSentiment)
                    {
                        maxSentiment = currSentiment;
                    }
                }
            }
            float score = maxSentiment;
            entity.setProperty("sentiment", score);
            datastore.put(entity);        
        }
        response.sendRedirect("/");
    }
}