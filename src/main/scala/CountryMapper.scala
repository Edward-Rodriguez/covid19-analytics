package scala

import org.apache.hadoop.mapreduce.Mapper
import org.apache.hadoop.io.LongWritable
import org.apache.hadoop.io.IntWritable
import org.apache.hadoop.io.Text

class CountryMapper extends Mapper[LongWritable, Text, Text, IntWritable] {
  override def map(
      key: LongWritable,
      value: Text,
      context: Mapper[LongWritable, Text, Text, IntWritable]#Context
  ): Unit = {
    val countryName: Text = new Text();
    val confirmedCasesCount: IntWritable = new IntWritable();

    val line = value.toString
    val parsed = line.split(",", -1)
    countryName.set(parsed(2)) //country name field
    if (parsed(6) == "") confirmedCasesCount.set(0)
    else
      confirmedCasesCount.set(
        parsed(6).toInt
      ) //cumulative confirmed cases field
    context.write(countryName, confirmedCasesCount)
  }
}
