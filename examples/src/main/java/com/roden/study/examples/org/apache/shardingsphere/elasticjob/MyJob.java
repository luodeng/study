package com.roden.study.examples.org.apache.shardingsphere.elasticjob;

import org.apache.shardingsphere.elasticjob.api.ShardingContext;
import org.apache.shardingsphere.elasticjob.simple.job.SimpleJob;

public class MyJob implements SimpleJob {
    
    @Override
    public void execute(ShardingContext context) {
        switch (context.getShardingItem()) {
            case 0:
                task(context);
                break;
            case 1:
                task(context);
                break;
            case 2:
                task(context);
                break;
            default: {
                task(context);
                break;
            }

        }
    }
    public void task(ShardingContext context){
        System.out.println("当前分片：" + context.getShardingItem() + "=====" + "参数：" + context.getShardingParameter() + " =====" + Thread.currentThread());
    }
}