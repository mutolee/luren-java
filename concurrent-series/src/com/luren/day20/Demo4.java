package com.luren.day20;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.*;
import java.util.function.Consumer;

/**
 * @author Lynn-God
 * @Description
 * @createTime 2021/6/30 16:10
 * @updateUser Lynn-God
 * @updateTime 2021/6/30 16:10
 * @desc
 */
public class Demo4 {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        ExecutorService executorService = Executors.newFixedThreadPool(5);
        List<Callable<Integer>> list = new ArrayList<>();
        int taskCount = 5;
        for (int i = taskCount; i > 0; i--) {
            int j = i * 2;
            list.add(() -> {
                TimeUnit.SECONDS.sleep(j);
                return j;
            });
        }

        solve(executorService,list,(t)->{
            System.out.println(t);
        });


    }

    public static <T> void solve(ExecutorService e, List<Callable<T>> solvers, Consumer<T> use) throws InterruptedException, ExecutionException {
        CompletionService<T> ecs = new ExecutorCompletionService<T>(e);
        for (Callable<T> s : solvers) {
            ecs.submit(s);
        }
        e.shutdown();
        int n = solvers.size();
        for (int i = 0; i < n; ++i) {
            T r = ecs.take().get();
            if (r != null) {
                use.accept(r);
            }
        }
    }

}