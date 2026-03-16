package analysis;

import java.io.IOException;
import org.apache.hadoop.io.DoubleWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

/**
 * Sums the fare amounts for each month to calculate total revenue.
 */
public class TaxiReducer extends Reducer<Text, DoubleWritable, Text, DoubleWritable> {

    @Override
    public void reduce(Text key, Iterable<DoubleWritable> values, Context context)
            throws IOException, InterruptedException {
        
        double totalRevenue = 0;
        
        for (DoubleWritable val : values) {
            totalRevenue += val.get();
        }
        
        context.write(key, new DoubleWritable(totalRevenue));
    }
}