import Characteristics.Checker;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.mapreduce.lib.output.MultipleOutputs;
import org.apache.hadoop.mapreduce.lib.output.TextOutputFormat;

import java.io.IOException;

public class TotalPopulationByYear {

    public static void main(String[] args) throws Exception {
        Job job = Job.getInstance();
        job.setJarByClass(TotalPopulationByYear.class);
        job.setMapperClass(PopulationMapper.class);

        MultipleOutputs.addNamedOutput(job, "Output", TextOutputFormat.class, Text.class, Text.class);

        FileInputFormat.addInputPath(job, new Path("/census/input"));
        FileOutputFormat.setOutputPath(job, new Path("/census/output"));
        System.exit(job.waitForCompletion(true) ? 0 : 1);
    }

    public static class PopulationMapper
            extends Mapper<Object, Text, Text, Text> {
        private final Checker checker = new Checker();
        private MultipleOutputs<Text, Text> mos;

        public void setup(Context context) {
            mos = new MultipleOutputs<>(context);
        }

        public void cleanup(Context context) throws IOException, InterruptedException {
            mos.close();
        }

        public void map(Object key, Text value, Context context
        ) throws IOException, InterruptedException {
            String data = value.toString();
            String[] fields = data.split(",", -1);
            if (fields.length >= 6 && !fields[0].equals("Year") && fields[5].matches("-?\\d+")) {
                String year = fields[0];
                String age = fields[1];
                String ethnicity = fields[2];
                String gender = fields[3];
                String region = fields[4];
                int count = Integer.parseInt(fields[5]);

                String outputFileName = year + "/" + region + "/" + "result";

                if (checker.checkKey(year, region, age, ethnicity, gender)) {
                    mos.write("Output", new Text("Ethnicity:" + ethnicity + " Age:" + age + " Gender:" + gender), new Text(Integer.toString(count)), outputFileName);
                }
            }
        }
    }
}