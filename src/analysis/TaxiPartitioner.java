package analysis;

import org.apache.hadoop.io.DoubleWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Partitioner;

/**
 * Assigns each month (01-12) to its own specific Reducer.
 */
public class TaxiPartitioner extends Partitioner<Text, DoubleWritable> {

    @Override
    public int getPartition(Text key, DoubleWritable value, int numReduceTasks) {
        // Convert month string "01" through "12" to int
        int month = Integer.parseInt(key.toString());

        // Map months to partitions 0 through 11
        if (numReduceTasks >= 12) {
            return (month - 1);
        } else {
            return (month - 1) % numReduceTasks;
        }
    }
}