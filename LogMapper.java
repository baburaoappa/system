
package log;

import java.io.IOException;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

public class LogMapper extends Mapper<LongWritable, Text, Text, IntWritable> {
	
	public void map(LongWritable key, Text val,Context con)throws IOException,InterruptedException
	{
		String[] lines = val.toString().split("\n");
		String ip = lines[0].substring(0,lines[0].indexOf(" "));
		con.write(new Text(ip), new IntWritable(1));
	}

}