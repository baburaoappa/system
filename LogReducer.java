package log;

import java.io.IOException;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

public class LogReducer extends  Reducer<Text, IntWritable, Text, IntWritable>{
	
	public void reduce(Text ip,Iterable <IntWritable> values,Context con) throws IOException,InterruptedException {
	   	
		int sum=0;
		for(IntWritable value:values)
		{
			sum += value.get();
 		}
		con.write(ip, new IntWritable(sum));
	}

}