package com.google.sps.classes;

import com.example.common.ExampleUtils;
import com.apache.beam.sdk.io.DatastoreIO;
import com.apache.beam.sdk.io.DatastoreV1;
import org.apache.beam.sdk.Pipeline;
import org.apache.beam.sdk.io.TextIO;
import org.apache.beam.sdk.metrics.Counter;
import org.apache.beam.sdk.metrics.Distribution;
import org.apache.beam.sdk.metrics.Metrics;
import org.apache.beam.sdk.options.Default;
import org.apache.beam.sdk.options.Description;
import org.apache.beam.sdk.options.PipelineOptions;
import org.apache.beam.sdk.options.PipelineOptionsFactory;
import org.apache.beam.sdk.options.Validation.Required;
import org.apache.beam.sdk.transforms.Count;
import org.apache.beam.sdk.transforms.DoFn;
import org.apache.beam.sdk.transforms.MapElements;
import org.apache.beam.sdk.transforms.PTransform;
import org.apache.beam.sdk.transforms.ParDo;
import org.apache.beam.sdk.transforms.SimpleFunction;
import org.apache.beam.sdk.values.KV;
import org.apache.beam.sdk.values.PCollection;


public class LoadColleges()
{
    public static void main(String [] args)
    {
        PipelineOptions options = PipelineOptionsFactory.fromArgs(args).create();
        Pipeline p = Pipeline.create(options);
        p.apply("ReadColleges", TextIO.read().from("gs://spsteam19/usCollege.csv"))
        .apply
        (
            ParDo.of(
                new DoFn<String, Entity>()
                {
                    @ProcessElement
                    public void processElement(ProcessContext c)
                    {
                        String row = c.element();
                        Entity entity = new Entity("College");
                        entity.setProperty("name", row);
                        c.output(entity);
                    }
                }
            )
        ).apply(DatastoreIO.v1().write.().withProjectId("spsTeam19"));
    }
}