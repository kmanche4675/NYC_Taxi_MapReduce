package analysis;

import java.io.IOException;
import org.apache.hadoop.io.DoubleWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

/**
 * Extracts the month and total fare from NYC Taxi CSV data.
 */
public class TaxiMapper extends Mapper<LongWritable, Text, Text, DoubleWritable> {

    @Override
    public void map(LongWritable key, Text value, Context context)
            throws IOException, InterruptedException {
        
        String line = value.toString();
        
        // Skip the header row
        if (line.contains("VendorID")) return;

        String[] columns = line.split(",");

        try {
            // Index 1: tpep_pickup_datetime (e.g., 2024-01-01 00:00:00)
            // Index 16: total_amount
            if (columns.length > 16) {
                String pickupTime = columns[1];
                String month = pickupTime.substring(5, 7); // Gets the "MM" part
                
                double fare = Double.parseDouble(columns[16]);
                
                context.write(new Text(month), new DoubleWritable(fare));
            }
        } catch (Exception e) {
            // Skip bad records
        }
    }
}