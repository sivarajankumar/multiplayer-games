package web;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.servlet.AsyncContext;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import constants.Keys;

public class ChannelUpdater {

	public void sendMessageOnChannel(HttpServletRequest request, final String message, final String channel, final String... excludeUsers) {
		// get the application scoped model, and copy the list of subscribers, so that the
		// long running task of publishing doesnt interfere with new logins
		
//		final AsyncContext publisherAsyncCtx = request.startAsync();
		ServletContext appScope = request.getServletContext();
		@SuppressWarnings("unchecked")
		final Map<String, List<Subscriber>> clients = (Map<String, List<Subscriber>>) appScope.getAttribute(Keys.CLIENTS);
		final List<Subscriber> subscribers = new ArrayList<Subscriber>(clients.get(channel));

		// here is the logic for publishing - it will be passed to the container
		// for execution sometime in the future
//		Runnable r = new Runnable() {
//			@Override
//			public void run() {
				System.out.println("updating games channel for " + subscribers.size() + " subscribers...");
				long start = System.currentTimeMillis();

				// keep a list of failed subscribers so we can remove them at
				// the end
				List<Subscriber> toRemove = new ArrayList<Subscriber>();
				for (Subscriber s : subscribers) {
					if (null != excludeUsers && excludeUsers.length > 0 && Arrays.asList(excludeUsers).contains(s.getUser().getUserName())){
						continue;
					}
					synchronized (s) {
						AsyncContext aCtx = s.getaCtx();
						try {
							aCtx.getResponse().getOutputStream().print(message);
							aCtx.complete();
						} catch (Exception e) {
							System.err.println("failed to send to client - removing from list of subscribers on this channel");
							toRemove.add(s);
						}
					}
				}

				// remove the failed subscribers from the model in app scope,
				// not our copy of them
				synchronized (clients) {
					clients.get(channel).removeAll(toRemove);
				}

				// log success
				long ms = System.currentTimeMillis() - start;
				String ok = "finished updating games channel " + channel + " in " + ms + " ms.";
				System.out.println(ok);

//				publisherAsyncCtx.complete(); // we are done, the connection can
//												// be closed now
//			}
//		};
//
//		// start the async processing (using a pool controlled by the container)
//		publisherAsyncCtx.start(r);
	}
}
