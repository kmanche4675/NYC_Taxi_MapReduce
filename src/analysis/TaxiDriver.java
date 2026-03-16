package analysis;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.DoubleWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

/**
 * Configures and submits the MapReduce job for NYC Taxi Analysis.
 */
public class TaxiDriver {
    public static void main(String[] args) throws Exception {
        if (args.length != 2) {
            System.err.println("Usage: TaxiDriver <input path> <output path>");
            System.exit(-1);
        }

        Configuration conf = new Configuration();
        Job job = Job.getInstance(conf, "NYC Taxi Monthly Revenue");
        
        job.setJarByClass(TaxiDriver.class);
        job.setMapperClass(TaxiMapper.class);
        job.setPartitionerClass(TaxiPartitioner.class);
        job.setReducerClass(TaxiReducer.class);

        // Crucial: Set to 12 so we get one output file per month
        job.setNumReduceTasks(12);

        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(DoubleWritable.class);

        FileInputFormat.addInputPath(job, new Path(args[0]));
        FileOutputFormat.setOutputPath(job, new Path(args[1]));

        System.exit(job.waitForCompletion(true) ? 0 : 1);
    }
}