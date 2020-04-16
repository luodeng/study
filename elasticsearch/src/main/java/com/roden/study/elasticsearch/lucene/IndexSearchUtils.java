package com.roden.study.elasticsearch.lucene;
 
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
 
import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
 
/**
 * Created by Administrator on 2018/8/29 0029.
 * Lucene 索引搜索工具类
 */
public class IndexSearchUtils {
 
    /**
     * 索引查询
     *
     * @param indexDir  ：Lucene 索引文件所在目录
     * @param queryWord ：检索的内容，默认从文章内容进行查询
     * @throws Exception
     */
    public static void indexSearch(File indexDir, String queryWord) throws Exception {
        if (indexDir == null || queryWord == null || "".equals(queryWord)) {
            return;
        }
        /** 创建分词器
         * 1）创建索引 与 查询索引 所用的分词器必须一致
         */
        Analyzer analyzer = new StandardAnalyzer();
 
        /**创建查询对象(QueryParser)：QueryParser(String f, Analyzer a)
         *  第一个参数：默认搜索域，与创建索引时的域名称必须相同
         *  第二个参数：分词器
         * 默认搜索域作用：
         *  如果搜索语法parse(String query)中指定了域名，则从指定域中搜索
         *  如果搜索语法parse(String query)中只指定了查询关键字，则从默认搜索域中进行搜索
         */
        QueryParser queryParser = new QueryParser("fileName", analyzer);
 
        /** parse 表示解析查询语法，查询语法为："域名:搜索的关键字"
         *  parse("fileName:web")：则从fileName域中进行检索 web 字符串
         * 如果为 parse("web")：则从默认搜索域 fileContext 中进行检索
         * 1)查询不区分大小写
         * 2)因为使用的是 StandardAnalyzer(标准分词器)，所以对英文效果很好，如果此时检索中文，基本是行不通的
         */
        Query query = queryParser.parse("fileContext:" + queryWord);
 
        /** 与创建 索引 和 Lucene 文档 时一样，指定 索引和文档 的目录
         * 即指定查询的索引库
         */
        Path path = Paths.get(indexDir.toURI());
        Directory dir = FSDirectory.open(path);
 
        /*** 创建 索引库读 对象
         * DirectoryReader 继承于org.apache.lucene.index.IndexReader
         * */
        DirectoryReader directoryReader = DirectoryReader.open(dir);
 
        /** 根据 索引对象创建 索引搜索对象
         **/
        IndexSearcher indexSearcher = new IndexSearcher(directoryReader);
 
        /**search(Query query, int n) 搜索
         * 第一个参数：查询语句对象
         * 第二个参数：指定查询最多返回多少条数据，此处则表示返回个数最多5条
         */
        TopDocs topdocs = indexSearcher.search(query, 5);
 
        System.out.println("查询结果总数：：：=====" + topdocs.totalHits);
 
        /**从搜索结果对象中获取结果集
         * 如果没有查询到值，则 ScoreDoc[] 数组大小为 0
         * */
        ScoreDoc[] scoreDocs = topdocs.scoreDocs;
 
        ScoreDoc loopScoreDoc = null;
        for (int i = 0; i < scoreDocs.length; i++) {
 
            System.out.println("=======================" + (i + 1) + "=====================================");
            loopScoreDoc = scoreDocs[i];
 
            /**获取 文档 id 值
             * 这是 Lucene 存储时自动为每个文档分配的值，相当于 Mysql 的主键 id
             * */
            int docID = loopScoreDoc.doc;
 
            /**通过文档ID从硬盘中读取出对应的文档*/
            Document document = directoryReader.document(docID);
 
            /**get方法 获取对应域名的值
             * 如域名 key 值不存在，返回 null*/
            System.out.println("doc id：" + docID);
            System.out.println("fileName:" + document.get("fileName"));
            System.out.println("fileSize:" + document.get("fileSize"));
            /**防止内容太多影响阅读，只取前20个字*/
            System.out.println("fileContext:" + document.get("fileContext").substring(0, 20) + "......");
        }
    }
 
    public static void main(String[] args) throws Exception {
        File indexDir = new File("/tmp/luceneIndex");
        indexSearch(indexDir, "思考");
        System.out.println("\n");
        indexSearch(indexDir, "互联网");
    }
}