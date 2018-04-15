package log;



import java.io.IOException;
import java.net.URI;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataInputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

public class LogDriver {

		@SuppressWarnings("deprecation")
		public static void main(String[] args) throws Exception {
			
			Configuration conf = new Configuration();
			Job job = new Job(conf, "LogCounting");
			job.setJarByClass(LogDriver.class);
			job.setMapperClass(LogMapper.class);
			job.setReducerClass(LogReducer.class);
			job.setOutputKeyClass(Text.class);
			job.setOutputValueClass(IntWritable.class);
			FileInputFormat.addInputPath(job ,new Path(args[0]));
			FileOutputFormat.setOutputPath(job,new Path(args[1]));
		    job.waitForCompletion(true);
		    
		    //Done Counting.
		 	
		    FileSystem fs = FileSystem.get(URI.create("hdfs://localhost:9000"+args[1]), conf);
		    String Fname = fs.listStatus(new Path(args[1]))[1].getPath().toString();
		    
		    FSDataInputStream in=null;
		    
		    try
		    {
		    	in = fs.open(new Path(Fname));
			    
		    	String maxIp = null; 
		    	int maxCount=-1,intCount=-1;
		    	int i=0;
		    		
		    	String line = null;
				
		    	while ((line = in.readLine()) != null) 
				{
					String words[]= line.split("\t");
					String ip = words[0];
					String cnt = words[1];
					
					intCount = Integer.parseInt(cnt);
					
					if(i == 0)
					{
						maxCount = intCount;
					    maxIp = ip;
						i++;
					}
					
					else
					{
						if(intCount>maxCount)
						{
							maxCount = intCount;
							maxIp = ip;
						}
					}
					
				}
				
		    	in.close();
				System.out.println("Max. number of logged in IP : "+maxIp+" with Count : "+maxCount);
		    }
		    
		    catch(IOException except)
		    {
		    	except.printStackTrace();
		    }
		}
}


/*
 
 OUTPUT :
 
 Max. number of logged in IP : 10.82.30.199 with Count : 63	
 
 */