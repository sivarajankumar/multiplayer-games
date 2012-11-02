package web;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.AsyncEvent;

public class AsyncListener implements javax.servlet.AsyncListener {

	private final String name;
	private final Map<String, List<Subscriber>> model;
	private final String channel;
	private final Subscriber subscriber;

	public AsyncListener(String name, Map<String, List<Subscriber>> model, String channel, Subscriber subscriber){
		this.name = name;
		this.model = model;
		this.channel = channel;
		this.subscriber = subscriber;
	}
	
	@Override
	public void onComplete(AsyncEvent event) throws IOException {
		removeFromModel();
		checkError(event);
	}

	@Override
	public void onTimeout(AsyncEvent event) throws IOException {
		removeFromModel();
		checkError(event);
	}

	@Override
	public void onError(AsyncEvent event) throws IOException {
		removeFromModel();
		checkError(event);
	}

	@Override
	public void onStartAsync(AsyncEvent event) throws IOException {
		checkError(event);
	}

	private void checkError(AsyncEvent event) {
		if(event.getThrowable() != null){
			System.out.println(name + " async listener:");
			event.getThrowable().printStackTrace();
		}
	}

	private void removeFromModel() {
		model.get(channel).remove(subscriber);
	}
	
}
