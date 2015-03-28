package de.tudarmstadt.linglit.linfw.core.annotator;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.TimeUnit;

import com.google.common.base.Stopwatch;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;

public class ForwardingAnnotatorExecutor implements AnnotatorExecutor {
	private static transient LoadingCache<Annotator<?>, ForkJoinPool> executors = CacheBuilder.newBuilder()
			.build(new CacheLoader<Annotator<?>, ForkJoinPool>() {
				@Override
				public ForkJoinPool load(Annotator<?> key) throws Exception {
					return new ForkJoinPool(8);
				}
			});
//	private transient ExecutorService executor;
	
	@Override
	public <A> void execute(final Runnable runnable, final Annotator<A> annotator) {
		try {
			this.executors.get(annotator).execute(new Runnable() {
				
				@Override
				public void run() {
					Stopwatch sw = Stopwatch.createStarted();
					System.out.println("Starting thread for "+annotator+"...");
					runnable.run();
					System.out.println("Finished thread for "+annotator+" in "+sw.elapsed(TimeUnit.SECONDS)+" seconds.");
				}
			});
		} catch (ExecutionException e) {
			e.printStackTrace();
		}
	}
	
	public void stop() {}
}
