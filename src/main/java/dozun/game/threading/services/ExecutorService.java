//package dozun.game.services;
//
//import dozun.game.threading.models.TaskResult;
//
//import java.util.concurrent.CyclicBarrier;
//import java.util.concurrent.Executors;
//
//public class ExecutorService {
//    ExecutorService executor = (ExecutorService) Executors.newFixedThreadPool(3);
//    CyclicBarrier barrier = new CyclicBarrier(3);
//
//    TaskResult results = new TaskResult();
//executor.s(new MultiThreadTask());
//executor.submit(new MultiThreadTask());
//executor.submit(new MultiThreadTask());
//
//barrier.await();
//}
