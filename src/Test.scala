import org.apache.spark.sql.SparkSession

object Test {


  def main(args: Array[String]): Unit = {
    val spark = SparkSession.builder().appName("Spark_SQL_FIRST").getOrCreate()


    // 加入隐式变换，将RDD转换为DataFrame
    import spark.implicits._

    // 读取json文件
    val df = spark.read.json("person1.json")
    df.show()

    // 打印表结构
    df.printSchema()

    // 执行查询出操作
    df.select($"name", $"age" + 1).show()
    df.filter($"name" > 21).show()
    df.groupBy("age").count().show()

    // 根据 df 创建名称为 people 的临时表，并执行查询操作
    df.createOrReplaceTempView("people")
    spark.sql("SELECT * FROM people").show
    spark.sql("SELECT * FROM people WHERE age<20").show

    // 常用的 DataFrame 的 Action操作

    // 使用collect 以 Array 形式返回 DataFrame 的所有Rows
    println("rows", df.collect())

    // 使用 count 返回 DataFrame 的所有 Rows 数目
    println("rows count", df.count())

    // 返回第一行
    println("第一行", df.first())
    println("第一行", df.head())

    // 返回前N行
    println("前两行", df.take(2))

    // columns 查询列名, 参数为列的索引
    println("第一列名", df.columns(0))

    // dtyps， 返回所有列名和数据结构， 不指定索引表示所有，以数组返回
    println("所有的列", df.dtypes)
    println("第一列", df.dtypes(0))

    // explain 打印执行计划
    println(df.explain())

    // persist 数据持久话
    df.persist()

    // 打印树形结构的schema
    df.printSchema()

    // 类似于 SQL 函数
    df.filter($"age">20).show()
    df.sort("age").show
    df.limit(2).show

    // intersect 与另外一个 DataFrame 交集
    val df2 = spark.read.json("person2.json")
    df.intersect(df2).show

    //
  }
}
