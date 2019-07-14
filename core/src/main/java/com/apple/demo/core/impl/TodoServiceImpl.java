package com.apple.demo.core.impl;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.ConfigurationPolicy;
import org.osgi.service.metatype.annotations.Designate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.apple.demo.core.TodoService;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

@Component(service = TodoService.class, configurationPolicy = ConfigurationPolicy.OPTIONAL)
@Designate(ocd = TodoServiceConfiguration.class)
public class TodoServiceImpl implements TodoService {

	private static final Logger log = LoggerFactory.getLogger(TodoServiceImpl.class);

	
	private String serviceURL = null;

	@Override
	public JsonObject getTodo() {
		StringBuilder res = new StringBuilder();
		try {
			HttpClient client = new DefaultHttpClient();
			HttpGet request = new HttpGet(serviceURL);
			HttpResponse response = client.execute(request);
			BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
			String line = "";

			while ((line = rd.readLine()) != null) {
				res = res.append(line);
			}
		} catch (Exception e) {
			log.error("::exception::"+e);
		}
		return new JsonParser().parse(res.toString()).getAsJsonObject();
	}

	@Activate
	public void activate(TodoServiceConfiguration config) {
		serviceURL = config.configValue();
	}

}
